import SwiftUI

struct ChatView: View {
    @EnvironmentObject var settings: AppSettings
    @StateObject private var engine = ChatEngine()
    @State private var input = ""

    var body: some View {
        VStack(spacing: 0) {
            ScrollViewReader { proxy in
                ScrollView {
                    LazyVStack(alignment: .leading, spacing: 10) {
                        ForEach(engine.messages) { m in
                            VStack(alignment: .leading, spacing: 3) {
                                Text(m.role == .user ? "你" : "助手")
                                    .font(.caption2)
                                    .foregroundColor(.secondary)
                                Text(m.text.isEmpty ? "…" : m.text)
                                    .font(.body)
                                    .textSelection(.enabled)
                            }
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding(10)
                            .background(m.role == .user ? Color.accentColor.opacity(0.12) : Color(.secondarySystemBackground))
                            .cornerRadius(10)
                            .id(m.id)
                        }
                        if let err = engine.errorText {
                            Text(err)
                                .font(.caption)
                                .foregroundColor(.red)
                        }
                        Color.clear.frame(height: 1).id("bottom")
                    }
                    .padding()
                }
                .onChange(of: engine.messages.last?.text) { _ in
                    proxy.scrollTo("bottom", anchor: .bottom)
                }
            }

            if engine.isStreaming {
                ProgressView().padding(2)
            }

            HStack(alignment: .bottom, spacing: 8) {
                TextField("问点什么…", text: $input, axis: .vertical)
                    .lineLimit(1...4)
                    .textFieldStyle(.roundedBorder)
                    .textInputAutocapitalization(.never)
                    .autocorrectionDisabled()
                Button {
                    engine.send(input, settings: settings)
                    input = ""
                } label: {
                    Image(systemName: "paperplane.fill")
                        .padding(6)
                }
                .disabled(engine.isStreaming || input.trimmingCharacters(in: .whitespaces).isEmpty)
            }
            .padding(8)
        }
        .navigationTitle("对话")
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button("清空") { engine.clear() }
            }
        }
    }
}
