import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        PlatformKt.getPlatform().initialize(context: nil)
        GreetingHelperKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
