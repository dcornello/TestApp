import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()
    
    var test = Greeting().getEnumTest()
    
    var txt : String {
        switch test {
        

        case .none:
            return "sono nil"
        case let .some(valore):
            switch valore {
            case .pippo :
                return "sono pippo"
            default :
                return valore.name
            }
        }
    }

	var body: some View {
        Text(txt)
        let signUp = {
            let _ = print("click!")
        }
        Button(action: signUp) {
            Text("Sign Up")
        }
	}
        
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
