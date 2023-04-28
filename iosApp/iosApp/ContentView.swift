import SwiftUI
import shared

struct ContentView: View {
    let greet = GreetingHelper().greet()
    let viewModel = LoginScreenViewModel()

    var body: some View {
        Text(greet)
        Button("login", action: {
            viewModel.createAccount()
        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

class LoginScreenViewModel {
    
    let usecase = LoginHelper().signUpUseCase
    
    func createAccount() {
        Task.init {
            do {
                try await usecase.invoke(email: "cornello.diego89@gmail.com", password: "Password123456")
                print("done")
            } catch {
                print("error \(error)")
            }
        }
    }
}
