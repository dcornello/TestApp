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
            }else{
                Button("goToLogin", action: {viewModel.goToLogin()})
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

class DashbardViewModel : DashboardScreenViewModelPact, ObservableObject {
        
    @Published var sideEffects: [DashboardScreenSideEffect] = []
        
    override var isUserLoggedInUseCase: IsUserLoggedInUseCase {
        return LoginHelper().isUserLoggedInUseCase
    }
    
    override init() {
        super.init()
        self.uiState = DashboardScreenUIState(showLoggedInView: isUserLoggedInUseCase.invoke())
    }
    
    deinit {
        sideEffects.removeAll()
    }
    
    override func sendSideEffect(sideEffect: DashboardScreenSideEffect) {
        sideEffects.append(sideEffect)
    }

}

class TTTEst : Test{
    func test() -> String {
        ""
    }
}
