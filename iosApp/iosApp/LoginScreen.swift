//
//  LoginPage.swift
//  iosApp
//
//  Created by Diego Cornello on 5/5/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct LoginScreen: View {
    
    @StateObject
    var navigator : LoginNavigator
    
    var body: some View {
        Text("LoginScreen")
    }
}

struct LoginScreen_Previews: PreviewProvider {
    static var previews: some View {
        LoginScreen(navigator: LoginNavigator())
    }
}

class LoginScreenViewModel {
    
//    let usecase = LoginHelper().signUpUseCase
//
//    func createAccount() {
//        Task.init {
//            do {
//                try await usecase.invoke(email: "cornello.diego89@gmail.com", password: "Password123456")
//                print("done")
//            } catch {
//                print("error \(error)")
//            }
//        }
//    }
}
