//
//  LoginPage.swift
//  iosApp
//
//  Created by Diego Cornello on 5/5/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine

struct LoginScreen: View {
    
    @StateObject
    var viewModel : LoginViewModel = LoginViewModel()
    
    @EnvironmentObject
    var navigator : LoginNavigator
    
    var body: some View {
        let mailBinding = Binding<String>(get: {
            self.viewModel.uiState.email
        }, set: { txt in
            // do whatever you want here
            self.viewModel.userActions.onEmailValueChanged(txt)
        })
        
        let passwordBinding = Binding<String>(get: {
            self.viewModel.uiState.password
        }, set: { txt in
            // do whatever you want here
            self.viewModel.userActions.onPasswordValueChanged(txt)
        })
        
        VStack(spacing: 28){
            Text("LoginScreen")
            VStack(alignment: .leading, spacing: 11) {
                Text("Login")
                    .font(.system(size: 13, weight: .light))
                    .padding(.horizontal,12)
                    .foregroundColor(.secondary)
                    .frame(height: 15, alignment: .leading)
                TextField("", text: mailBinding)
                    .font(.system(size: 17, weight: .thin))
                    .foregroundColor(.primary)
                    .frame(height: 44)
                    .padding(.horizontal, 12)
                    .background(Color.gray)
                    .cornerRadius(4.0)
                if let errorMail = self.viewModel.uiState.emailError{
                    Text("error \(errorMail.name)")
                        .font(.system(size: 13, weight: .light))
                        .padding(.horizontal,12)
                        .foregroundColor(.red)
                }
            }
            
            VStack(alignment: .leading, spacing: 11) {
                Text("Password")
                    .font(.system(size: 13, weight: .light))
                    .padding(.horizontal,12)
                    .foregroundColor(.secondary)
                    .frame(height: 15, alignment: .leading)
                SecureField("", text: passwordBinding)
                    .font(.system(size: 17, weight: .thin))
                    .foregroundColor(.primary)
                    .frame(height: 44)
                    .padding(.horizontal, 12)
                    .background(Color.gray)
                    .cornerRadius(4.0)
                if let errorPassword = self.viewModel.uiState.passwordError{
                    Text("error \(errorPassword.name)")
                        .font(.system(size: 13, weight: .light))
                        .padding(.horizontal,12)
                        .foregroundColor(.red)
                }
            }
            
            Button("LOGIN", action: viewModel.userActions.onSignupClicked)
            
        }.onChange(of: viewModel.sideEffects, perform: {value in
            if let event = value.last {
                switch event {
                    case is LoginScreenSideEffect.GoToLogoutScreen : navigator.navigateToRoot()
                    default: print("not handled type \(event)")
                }
            }
        })
        .onDisappear(){
            viewModel.sideEffects.removeAll()
        }
    }
}

struct LoginScreen_Previews: PreviewProvider {
    static var previews: some View {
        LoginScreen()
    }
}

class LoginViewModel: LoginScreenViewModel, ObservableObject {
    
    @Published
    var sideEffects: [LoginScreenSideEffect] = []
    
    @Published
    public private(set) var uiState :LoginScreenUIState = LoginScreenUIState(email: "", password: "", emailError: nil, passwordError: nil, showLoading: false, userErrorMessage: nil)
    
    override var __uiState: LoginScreenUIState {
        get{
            return self.uiState
        }
        set{
            self.uiState = newValue
        }
    }
    
    override var logInUseCase : LogInUseCase {
        return LoginHelper().logInUseCase
    }
    
    override var signUpUseCase: SignUpUseCase{
        return LoginHelper().signUpUseCase
    }
    
    override var emailValidationUseCase: EmailValidationUseCase{
        return EmailValidationUseCase()
    }
    
    override var passwordValidationUseCase: PasswordValidationUseCase{
        return PasswordValidationUseCase()
    }
    
    override func sendSideEffect(sideEffect_: LoginScreenSideEffect) {
        sideEffects.append(sideEffect_)
    }
    
    override func login() {
        let email = __uiState.email
        let password = __uiState.password
        print("email \(email)")
        print("password \(password)")
        let emailCheck = checkValidityEmail(email: email)
        print("emailCheck \(String(describing: emailCheck))")
        let passwordCheck = checkValidityPassword(password: password)
        if (emailCheck.isSuccess() && passwordCheck.isSuccess()) {
            __uiState = __uiState.changeValues(showLoading: true)
            Task.init {
                do {
                    let result = try await logInUseCase.invoke(email: email, password: password)
                    result.fold(
                        failed: { error in
                            print("logginError \(String(describing: error))")
                            self.__uiState = self.__uiState.changeValues(
                                showLoading: false,
                                userErrorMessage: SignupError.unknown
                            )
                            return nil
                        },
                        succeeded: { user in
                            print("logginSuccess \(String(describing: user))")
                            self.__uiState = self.__uiState.changeValues(showLoading: false)
                            self.sendSideEffect(sideEffect_: LoginScreenSideEffect.GoToLogoutScreen())
                            return nil
                        }
                    )
                }catch {
                    print("catchedError \(error)")
                    self.__uiState = self.__uiState.changeValues(
                        showLoading: false,
                        userErrorMessage: SignupError.unknown
                    )
                }
            }
        }
    }
    

    deinit {
        sideEffects.removeAll()
    }
    
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

extension LoginScreenUIState{
    
    func changeValues(email: String? = nil, password: String? = nil, emailError: EmailError?=nil, passwordError: PasswordError?=nil, showLoading: Bool? = nil, userErrorMessage: SignupError? = nil) -> LoginScreenUIState {
        return self.doCopy(email: email ?? self.email, password: password ?? self.password, emailError: emailError ?? self.emailError, passwordError: passwordError ?? self.passwordError, showLoading: showLoading ?? self.showLoading, userErrorMessage: userErrorMessage ?? self.userErrorMessage)
    }
}
