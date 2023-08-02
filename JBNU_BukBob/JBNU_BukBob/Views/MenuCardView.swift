//
//  MenuCardView.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
//

import SwiftUI

struct MenuCardView: View {
    var cafeteria: Cafeterias = Cafeterias(cafeteria: nil, menus: nil, mealTime: nil, time: nil, isBookmarked: false)
//    @Binding var isFirstChecking: (Int,Bool) {
//        didSet {
//            isFirstChecking.1 ? Image("starFill").padding(.horizontal)
//            : Image("starNonFill").padding(.horizontal)
//        }
//    }
//    @Binding var isSecondChecking: (Int,Bool)
//    @Binding var isThirdChecking: (Int,Bool)
//    @Binding var isFourChecking: (Int,Bool)
    @Binding var indexCount: Int
    //인덱스 카운트르 새서 그 순서에 있을때 자동합병, 되기로
    static var padding = 20
    var menu = ["맛있는 밥", "커리돈까스", "겉절이 김치", "요플레"]
    
    var body: some View {
            VStack {
                HStack {
                    Text("\(Cafeteria.allCases[indexCount].rawValue)")
                        .font(.system(size: 25, weight: .semibold))
                        .padding(.horizontal)
                    Spacer()

                    cafeteria.isBookmarked ? Image("starFill").padding(.horizontal)
                    : Image("starNonFill").padding(.horizontal)
                }//HStack
                HStack {
                    VStack(alignment: .leading) {
                        ForEach (menu, id: \.self) { menu in
                            Text(menu)
                                .font(.system(size: 18))
                                .padding(.horizontal)
                        }
                    }//VStack
                    Spacer()
                }//HStack
            }//VStack
            .padding(10)
            .overlay(RoundedRectangle(cornerRadius: 15)
                .stroke(Color.mainPurple, lineWidth: 5))
            .contentShape(Rectangle())
            .onTapGesture(count: 2) {
                cafeteria.isBookmarked.toggle()
            }
    }
}
//
//struct MenuCardView_Previews: PreviewProvider {
//    static var previews: some View {
//        MenuCardView(isChecking: .constant(false))
//    }
//}
