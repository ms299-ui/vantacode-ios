import Foundation
import SwiftUI

struct ChatMessage: Identifiable, Codable, Equatable {
    enum Role: String, Codable { case user, assistant, system }
    var id = UUID()
    var role: Role
    var text: String
}

final class AppSettings: ObservableObject {
    static let shared = AppSettings()

    @Published var baseURL: String { didSet { save() } }
    @Published var apiKey: String { didSet { save() } }
    @Published var model: String { didSet { save() } }

    private let d = UserDefaults.standard

    private init() {
        baseURL = d.string(forKey: "vc.baseURL") ?? "https://api.openai.com/v1"
        apiKey = d.string(forKey: "vc.apiKey") ?? ""
        model = d.string(forKey: "vc.model") ?? "gpt-4o-mini"
    }

    private func save() {
        d.set(baseURL, forKey: "vc.baseURL")
        d.set(apiKey, forKey: "vc.apiKey")
        d.set(model, forKey: "vc.model")
    }

    var chatEndpoint: URL? {
        var s = baseURL.trimmingCharacters(in: .whitespacesAndNewlines)
        while s.hasSuffix("/") { s.removeLast() }
        if s.hasSuffix("/chat/completions") { return URL(string: s) }
        return URL(string: s + "/chat/completions")
    }
}

@MainActor
final class ChatEngine: ObservableObject {
    @Published var messages: [ChatMessage] = []
    @Published var isStreaming = false
    @Published var errorText: String?

    func clear() {
        messages.removeAll()
        errorText = nil
    }

    func send(_ input: String, settings: AppSettings) {
        let text = input.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !text.isEmpty, !isStreaming else { return }
        errorText = nil
        messages.append(ChatMessage(role: .user, text: text))
        let assistant = ChatMessage(role: .assistant, text: "")
        messages.append(assistant)
        isStreaming = true
        Task { await run(assistantId: assistant.id, settings: settings) }
    }

    private func run(assistantId: UUID, settings: AppSettings) async {
        defer { isStreaming = false }
        guard let url = settings.chatEndpoint else {
            errorText = "API 地址无效"
            return
        }
        var req = URLRequest(url: url)
        req.httpMethod = "POST"
        req.timeoutInterval = 180
        req.setValue("application/json", forHTTPHeaderField: "Content-Type")
        if !settings.apiKey.isEmpty {
            req.setValue("Bearer \(settings.apiKey)", forHTTPHeaderField: "Authorization")
        }
        let history = messages.filter { !($0.role == .assistant && $0.text.isEmpty) }
            .map { ["role": $0.role.rawValue, "content": $0.text] }
        let payload: [String: Any] = [
            "model": settings.model,
            "stream": true,
            "messages": history
        ]
        guard let body = try? JSONSerialization.data(withJSONObject: payload) else {
            errorText = "请求构造失败"
            return
        }
        req.httpBody = body
        do {
            let (bytes, response) = try await URLSession.shared.bytes(for: req)
            guard let http = response as? HTTPURLResponse else {
                errorText = "无响应"
                return
            }
            if http.statusCode != 200 {
                var raw = ""
                for try await line in bytes.lines { raw += line }
                errorText = "HTTP \(http.statusCode) \(raw.prefix(240))"
                return
            }
            for try await line in bytes.lines {
                guard line.hasPrefix("data:") else { continue }
                let piece = line.dropFirst(5).trimmingCharacters(in: .whitespaces)
                if piece == "[DONE]" { break }
                guard let data = piece.data(using: .utf8),
                      let obj = try? JSONSerialization.jsonObject(with: data) as? [String: Any],
                      let choices = obj["choices"] as? [[String: Any]],
                      let delta = choices.first?["delta"] as? [String: Any] else { continue }
                if let chunk = delta["content"] as? String, !chunk.isEmpty {
                    append(chunk, to: assistantId)
                }
            }
        } catch {
            errorText = error.localizedDescription
        }
    }

    private func append(_ chunk: String, to id: UUID) {
        guard let idx = messages.firstIndex(where: { $0.id == id }) else { return }
        messages[idx].text += chunk
    }
}
