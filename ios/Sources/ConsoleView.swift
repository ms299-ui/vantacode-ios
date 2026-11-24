import SwiftUI
import UIKit

final class Shell: ObservableObject {
    @Published var cwd: URL
    @Published var lines: [String] = []
    let home: URL

    init() {
        let docs = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)[0]
        home = docs
        cwd = docs
        lines = ["VantaCode Shell (iOS 本地)", "输入 help 查看可用命令"]
    }

    func run(_ raw: String) {
        let cmd = raw.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !cmd.isEmpty else { return }
        if cmd == "clear" {
            lines.removeAll()
            return
        }
        lines.append("$ \(cmd)")
        let out = execute(cmd)
        if !out.isEmpty { lines.append(out) }
    }

    private func resolve(_ path: String) -> URL {
        if path.hasPrefix("/") { return URL(fileURLWithPath: path).standardizedFileURL }
        return cwd.appendingPathComponent(path).standardizedFileURL
    }

    private func displayPath(_ url: URL) -> String {
        let p = url.path
        if p == home.path { return "~" }
        if p.hasPrefix(home.path) { return "~" + p.dropFirst(home.path.count) }
        return p
    }

    private func execute(_ cmd: String) -> String {
        let parts = cmd.split(separator: " ", omittingEmptySubsequences: true).map(String.init)
        guard let name = parts.first else { return "" }
        let args = Array(parts.dropFirst())
        let fm = FileManager.default

        switch name {
        case "help":
            return """
            help          显示帮助
            pwd           当前目录
            ls [-l]       列目录
            cd <dir>      切换目录
            cat <file>    查看文件
            echo <text>   输出文本（支持 > / >> 写文件）
            mkdir <dir>   新建目录
            touch <file>  新建空文件
            rm <p>        删除
            cp <a> <b>    复制
            mv <a> <b>    移动 / 改名
            date          当前时间
            uname         系统信息
            whoami        当前用户
            clear         清屏
            """
        case "pwd":
            return displayPath(cwd)
        case "cd":
            guard let target = args.first else { cwd = home; return "" }
            let url = resolve(target)
            var isDir: ObjCBool = false
            if fm.fileExists(atPath: url.path, isDirectory: &isDir), isDir.boolValue {
                cwd = url
                return ""
            }
            return "cd: \(target): 不是目录"
        case "ls":
            let long = args.contains("-l")
            let target = args.last(where: { !$0.hasPrefix("-") }).map(resolve) ?? cwd
            guard let items = try? fm.contentsOfDirectory(atPath: target.path) else {
                return "ls: 无法读取 \(displayPath(target))"
            }
            if long {
                return items.sorted().map { n -> String in
                    let u = target.appendingPathComponent(n)
                    let attrs = (try? fm.attributesOfItem(atPath: u.path)) ?? [:]
                    let size = (attrs[.size] as? NSNumber)?.intValue ?? 0
                    let isDir = (attrs[.type] as? FileAttributeType) == .typeDirectory
                    return "\(isDir ? "d" : "-") \(size)\t\(n)"
                }.joined(separator: "\n")
            }
            return items.sorted().joined(separator: "  ")
        case "cat":
            guard let p = args.first else { return "cat: 缺少文件" }
            guard let s = try? String(contentsOf: resolve(p), encoding: .utf8) else {
                return "cat: 无法读取 \(p)"
            }
            return s
        case "echo":
            if let gt = args.firstIndex(where: { $0 == ">" || $0 == ">>" }) {
                let text = args[..<gt].joined(separator: " ")
                let target = resolve(args[(gt + 1)...].joined(separator: " "))
                let data = (text + "\n").data(using: .utf8) ?? Data()
                if args[gt] == ">>" , let h = try? FileHandle(forWritingTo: target) {
                    h.seekToEndOfFile()
                    h.write(data)
                    try? h.close()
                } else {
                    try? data.write(to: target)
                }
                return ""
            }
            return args.joined(separator: " ")
        case "mkdir":
            guard let p = args.first else { return "mkdir: 缺少目录名" }
            do {
                try fm.createDirectory(at: resolve(p), withIntermediateDirectories: true)
                return ""
            } catch { return "mkdir: \(error.localizedDescription)" }
        case "touch":
            guard let p = args.first else { return "touch: 缺少文件名" }
            let url = resolve(p)
            if !fm.fileExists(atPath: url.path) { fm.createFile(atPath: url.path, contents: Data()) }
            return ""
        case "rm":
            guard let p = args.last(where: { !$0.hasPrefix("-") }) else { return "rm: 缺少路径" }
            do {
                try fm.removeItem(at: resolve(p))
                return ""
            } catch { return "rm: \(error.localizedDescription)" }
        case "cp", "mv":
            guard args.count >= 2 else { return "\(name): 需要两个参数" }
            let a = resolve(args[0])
            let b = resolve(args[1])
            do {
                if name == "cp" { try fm.copyItem(at: a, to: b) } else { try fm.moveItem(at: a, to: b) }
                return ""
            } catch { return "\(name): \(error.localizedDescription)" }
        case "date":
            let f = DateFormatter()
            f.dateFormat = "yyyy-MM-dd HH:mm:ss"
            return f.string(from: Date())
        case "uname":
            return "iOS \(UIDevice.current.systemVersion) arm64"
        case "whoami":
            return "mobile"
        default:
            return "\(name): 命令不存在（输入 help 查看内置命令）"
        }
    }
}

struct ConsoleView: View {
    @StateObject private var shell = Shell()
    @State private var input = ""

    var body: some View {
        VStack(spacing: 0) {
            ScrollViewReader { proxy in
                ScrollView {
                    Text(shell.lines.joined(separator: "\n"))
                        .font(.system(.footnote, design: .monospaced))
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(10)
                        .textSelection(.enabled)
                        .id("bottom")
                }
                .onChange(of: shell.lines.count) { _ in
                    proxy.scrollTo("bottom", anchor: .bottom)
                }
            }
            Divider()
            HStack(spacing: 6) {
                Text("$").font(.system(.body, design: .monospaced))
                TextField("输入命令", text: $input)
                    .font(.system(.body, design: .monospaced))
                    .textInputAutocapitalization(.never)
                    .autocorrectionDisabled()
                    .onSubmit { shell.run(input); input = "" }
                Button("运行") { shell.run(input); input = "" }
                    .disabled(input.trimmingCharacters(in: .whitespaces).isEmpty)
            }
            .padding(8)
        }
        .navigationTitle("终端")
    }
}
