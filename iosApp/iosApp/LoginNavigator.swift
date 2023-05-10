//
//  LoginNavigator.swift
//  iosApp
//
//  Created by Diego Cornello on 5/5/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

class LoginNavigator: ObservableObject {
    
    @Published var path = NavigationPath()
    
    func goToDashboard(){
        path.append(Destination.dashboardScreen)
    }
    
    func goToLogin(){
        path.append(Destination.loginScreen)
    }
    
    func goToSignup(){
        path.append(Destination.signupScreen)
    }
    
    func navigateBack() {
        path.removeLast()
    }
    
    func navigateToRoot() {
        path = NavigationPath()
    }
}

enum Destination: Hashable {
    case dashboardScreen
    case loginScreen
    case signupScreen
}
