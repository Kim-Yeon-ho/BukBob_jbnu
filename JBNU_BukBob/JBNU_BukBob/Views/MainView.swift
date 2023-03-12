//
//  MainView.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
//

import SwiftUI

struct MainView: View {
    var body: some View {
        VStack {
            VStack {
                HStack {

                } //HStack
            } //VStack
            ScrollView {
                VStack {
                    MenuCardView().padding()
                    //식단 메뉴카드
                }
            } //ScrollView
            //화면 전환 컴포넌트
        }//VStack
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}

