//
//  Model.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/16.
//

import Foundation

class UserInfo {
    static let shared = UserInfo()

    var isChecking: Bool = false

    private init() {}

}
