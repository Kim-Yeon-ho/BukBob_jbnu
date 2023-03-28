//
//  Model.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/16.
//

import Foundation

struct Cafeteria: Codable, Identifiable {
    var id = UUID()
    let cafeteria: [String]
    let menus: [String]
    let mealTime: String
    //아침, 점심, 저녁
    let time: String
    //07:00 ~ 08:00 같은 시간

    init(id: UUID = UUID(), cafeteria: [String], menus: [String], mealTime: String, time: String) {
        self.id = id
        self.cafeteria = cafeteria
        self.menus = menus
        self.mealTime = mealTime
        self.time = time
    }
}
