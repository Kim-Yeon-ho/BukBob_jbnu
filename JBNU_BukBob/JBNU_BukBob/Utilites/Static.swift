//
//  Static.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
//

import SwiftUI

final class DeviceFrame {
    static let screenWidth = UIScreen.main.bounds.size.width
    static let screenHeight = UIScreen.main.bounds.size.height

    static var devicePadding: CGFloat {
        return screenWidth - 40
    }
}
