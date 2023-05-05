import SwiftUI
import shared

struct ContentView: View {
    let greet = GreetingHelper().greet()
//    let viewModel = LoginScreenViewModel()
    @StateObject private var navigator = LoginNavigator()

    var body: some View {
        NavigationStack(path: $navigator.path){
            DashbardScreen(navigator: navigator)
                .navigationDestination(for: Destination.self){destination in
                    switch destination {
                    case .dashboardScreen : DashbardScreen(navigator: navigator)
                    case .loginScreen : LoginScreen(navigator: navigator)
                    case .signupScreen : LoginScreen(navigator:navigator)
                    }
                }
        }
        
//        NavigationView {
//            if(isLoggedIn){
//                DashbardScreen()
//            }else{
//                LoginScreen()
//            }
//        }
//        Button("login", action: {
//            viewModel.createAccount()
//        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}




