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
    var viewModel = DashbardViewModel()
    
    @StateObject
    var navigator : LoginNavigator
    
    var body: some View {
        
        Text("Dashboard")
        if(viewModel.uiState.showLoggedInView){
            Text("loggedIn")
        }else{
            Button("goToLogin", action: {navigator.goToLogin()})
        }
    }
}

struct DashboardScreen_Previews: PreviewProvider {

    static var previews: some View {
        DashbardScreen(navigator: LoginNavigator())
    }
}

class DashbardViewModel : DashboardScreenViewModelPact, ObservableObject {
        
    @Published var sideEffects: Publishers.Sequence<[DashboardScreenSideEffect], Never> = [].publisher
    var cancellable = Set<AnyCancellable>()
    
    override var isUserLoggedInUseCase: IsUserLoggedInUseCase {
        return LoginHelper().isUserLoggedInUseCase
    }
    
    override init() {
        super.init()
        self.uiState = DashboardScreenUIState(showLoggedInView: isUserLoggedInUseCase.invoke())
    }
    
    override func sendSideEffect(sideEffect: DashboardScreenSideEffect) {
        self.sideEffects
            .append(sideEffect)
            .sink { <#DashboardScreenSideEffect#> in
                
            }.store(in: &cancellable)
    }

}

class TTTEst : Test{
    func test() -> String {
        ""
    }
}
