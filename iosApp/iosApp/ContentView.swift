import SwiftUI
import shared

struct ContentView: View {
    let greet = GreetingHelper().greet()
    @StateObject private var navigator = LoginNavigator()

    var body: some View {
        NavigationStack(path: $navigator.path){
            DashbardScreen().environmentObject(navigator)
                .navigationDestination(for: Destination.self){destination in
                    switch destination {
                        case .dashboardScreen : DashbardScreen().environmentObject(navigator)
                        case .loginScreen : LoginScreen().environmentObject(navigator)
                        case .signupScreen : LoginScreen().environmentObject(navigator)
                    }
                }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}




