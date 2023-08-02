//
//  Model.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/16.
//

import Foundation

class Cafeterias: Identifiable {
    var id = UUID()
    var cafeteria: String?
    var menus: [String]?
    var mealTime: String?
    var time: String?
    var isBookmarked: Bool
}

enum Cafeteria: String, CaseIterable {
    case jinsu = "진수당"
    case husaeng = "후생관"
    case cham = "참빛관"
}

enum MealTime: String, CaseIterable {
    case breakfast = "조식"
    case lunch = "중식"
    case dinner = "석식"
}

enum Time: String, CaseIterable {
    case breakfast = "07:00 ~ 08:30"
    case lunch = "13:00 ~ 14:00"
    case dinner = "18:00 ~ 19:00"
}
