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
    
    @State private var showingAlert = false
    
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
            if(viewModel.uiState.showLoading){
                ProgressView()
            }else{
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
            }
        }
        .alert("Opps something was wrong", isPresented: $showingAlert) {
            Button("OK", role: .cancel) { }
        }
        .onChange(of: viewModel.sideEffects, perform: {value in
            if let event = value.last {
                switch event {
                    case is LoginScreenSideEffect.GoToLogoutScreen : navigator.navigateToRoot()
                    case is LoginScreenSideEffect.ShowLogInError : showingAlert = true
                    default: print("not handled type \(event)")
                }
            }
        })
        .onDisappear(){
            viewModel.sideEffects.removeAll()
        }
    }
}

class LoginViewModel: LoginScreenViewModel, ObservableObject {
    
    @Published
    var sideEffects: [LoginScreenSideEffect] = []
    
    @Published
    public private(set) var uiState :LoginScreenUIState = LoginScreenUIState(email: "", password: "", emailError: nil, passwordError: nil, showLoading: false)
    
    override var __uiState: LoginScreenUIState {
        get{
            return self.uiState
        }
        set{
            DispatchQueue.main.async {
                self.uiState = newValue
            }
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
        DispatchQueue.main.async {
            self.sideEffects.append(sideEffect_)
        }
    }
    
    override func launchInViewModelScope(catchErrors: @escaping () -> Void, function: KotlinSuspendFunction0) {
        Task.init {
            do {
                try await function.invoke()
            }catch {
                catchErrors()
            }
        }
    }
    
    deinit {
        sideEffects.removeAll()
    }
}

extension LoginScreenUIState{
    
    func changeValues(email: String? = nil, password: String? = nil, emailError: EmailError?=nil, passwordError: PasswordError?=nil, showLoading: Bool? = nil) -> LoginScreenUIState {
        return self.doCopy(email: email ?? self.email, password: password ?? self.password, emailError: emailError ?? self.emailError, passwordError: passwordError ?? self.passwordError, showLoading: showLoading ?? self.showLoading)
    }
}
