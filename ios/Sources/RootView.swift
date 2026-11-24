import SwiftUI

struct RootView: View {
    var body: some View {
        TabView {
            NavigationStack { ChatView() }
                .tabItem { Label("对话", systemImage: "bubble.left.and.bubble.right") }
            NavigationStack { ConsoleView() }
                .tabItem { Label("终端", systemImage: "terminal") }
            NavigationStack { SettingsView() }
                .tabItem { Label("设置", systemImage: "gear") }
        }
    }
}
