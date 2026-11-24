import SwiftUI

struct SettingsView: View {
    @EnvironmentObject var settings: AppSettings

    var body: some View {
        Form {
            Section("API（兼容 OpenAI /chat/completions）") {
                TextField("Base URL", text: $settings.baseURL)
                    .textInputAutocapitalization(.never)
                    .autocorrectionDisabled()
                    .keyboardType(.URL)
                SecureField("API Key", text: $settings.apiKey)
                TextField("模型", text: $settings.model)
                    .textInputAutocapitalization(.never)
                    .autocorrectionDisabled()
            }
            Section("说明") {
                Text("例：https://api.openai.com/v1，或你自己的中转地址。终端目前是 App 内置的轻量 shell，不调用系统 sh。")
                    .font(.footnote)
            }
            Section("关于") {
                LabeledContent("版本", value: "0.1.0 (MVP)")
                LabeledContent("终端", value: "iOS 本地内置命令")
                LabeledContent("来源", value: "VantaCode-Desktop (GPL-3.0)")
            }
        }
        .navigationTitle("设置")
    }
}
