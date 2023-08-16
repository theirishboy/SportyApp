import SwiftUI
import shared
import UIKit

struct ContentView: View {
   // @ObservedObject private(set) var viewModel: ViewModel

    var body: some View {
     ComposeView().ignoresSafeArea(.all, edges: .bottom)
     //   Text(ViewModelApiCall.text)
    }
}
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
extension ContentView {
    class ViewModelApiCall: ObservableObject {
        @Published var text = "Loading..."
        init() {
            Greeting().greeting { greeting, error in
                DispatchQueue.main.async {
                    if let greeting = greeting {
                        self.text = greeting
                    } else {
                        self.text = error?.localizedDescription ?? "error"
                    }
                }
            }
        }
    }

}
