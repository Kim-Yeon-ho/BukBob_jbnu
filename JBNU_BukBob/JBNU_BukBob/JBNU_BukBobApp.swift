//
//  JBNU_BukBobApp.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
//

import SwiftUI

@main
struct JBNU_BukBobApp: App {
    let persistenceController = PersistenceController.shared
    private let defaultCafeteria: Cafeteria = Cafeteria(cafeteria: [], menus: [], mealTime: "", time: "")
    var body: some Scene {
        WindowGroup {
            MainView(cafeterias: defaultCafeteria)
//            ContentView()
//                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
