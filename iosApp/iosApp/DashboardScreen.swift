//
//  DashBoard.swift
//  iosApp
//
//  Created by Diego Cornello on 5/5/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine

struct DashbardScreen: View {
    
    @StateObject
    var viewModel : DashbardViewModel = DashbardViewModel()
    
    @EnvironmentObject
    var navigator : LoginNavigator
        
    var body: some View {
        VStack{
            Text("Dashboard")
            if(viewModel.uiState.showLoggedInView){
                Text("loggedIn")
                Button("LogOut", action: viewModel.userActions.onLogOutButtonClicked)
            }else{
                Button("goToLogin", action: viewModel.userActions.onGoLogInButtonClicked)
            }
        }.onChange(of: viewModel.sideEffects, perform: {value in
            if let event = value.last {
                print("sideEffects: \(value)")
                print("sideEffect: \(event)")
                switch event {
                    case is DashboardScreenSideEffect.GoToLoginScreen : navigator.goToLogin()
                    default: print("not handled type \(event)")
                }
            }
        })
        .onAppear{
            viewModel.refresh()
        }
        .onDisappear{
            viewModel.sideEffects.removeAll()
        }
    }
}

struct DashboardScreen_Previews: PreviewProvider {

    static var previews: some View {
        DashbardScreen()
    }
}

class DashbardViewModel : DashboardScreenViewModel, ObservableObject {
    
    @Published
    var sideEffects: [DashboardScreenSideEffect] = []
    
    @Published
    public private(set) var uiState :DashboardScreenUIState = DashboardScreenUIState(showLoggedInView: false)
    
    override var __uiState: DashboardScreenUIState {
        get{
            return self.uiState
        }
        set{
            self.uiState = newValue
        }
    }
    
    override var isUserLoggedInUseCase: IsUserLoggedInUseCase {
        return LoginHelper().isUserLoggedInUseCase
    }
    
    override var logOutUseCase: LogOutUseCase {
        return LoginHelper().logOutUseCase
    }
    
    override var fetchUserUseCase: FetchUserUseCase{
        return LoginHelper().fetchUserUseCase
    }
    
    override init() {
        super.init()
        self.__uiState = uiState.doCopy(showLoggedInView: isUserLoggedInUseCase.invoke())
    }
    
    deinit {
        sideEffects.removeAll()
    }
    
    func refresh(){
        self.__uiState = uiState.doCopy(showLoggedInView: isUserLoggedInUseCase.invoke())
    }
    
    override func sendSideEffect(sideEffect: DashboardScreenSideEffect) {
        sideEffects.append(sideEffect)
    }
  
    override func logout() {
        Task.init {
            do {
                let result = try await logOutUseCase.invoke()
//                result.fold(
//                    failed: {_ in
//
//                    },
//                    succeeded : {_ in
//
//                    }
//                )
                __uiState = uiState.doCopy(showLoggedInView: isUserLoggedInUseCase.invoke())
                print("isUserLoggedInUseCase \(String(describing: isUserLoggedInUseCase.invoke()))")
                print("fetchUserUseCase \(String(describing: fetchUserUseCase.invoke()))")
            } catch {
                print("error \(error)")
            }
        }
    }
}
