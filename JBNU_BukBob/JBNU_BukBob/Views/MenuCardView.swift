//
//  MenuCardView.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
//

import SwiftUI

struct MenuCardView: View {
    @State var isChecking = false
    static var padding = 20
    var menu = ["맛있는 밥", "커리돈까스", "겉절이 김치", "요플레"]

    var body: some View {
        VStack {
            HStack {
                Text("진수당")
                    .font(.system(size: 25, weight: .semibold))
                    .padding(.horizontal)

                Spacer()

                isChecking ? Image("starFill").padding(.horizontal)
                : Image("starNonFill").padding(.horizontal)
            }
            //HStack
            HStack {
                VStack {
                    ForEach (menu, id: \.self) { menu in
                        Text(menu)
                            .font(.system(size: 18))
                            .padding(.horizontal)
                    }
                }
                //                .padding(.vertical)
                Spacer()
            }
            //HStack
        }
        .padding(10)
        .overlay(RoundedRectangle(cornerRadius: 15)
            .stroke(Color.mainPurple, lineWidth: 5))
        //        .border(Color.mainPurple, width: 5)
        //        .cornerRadius(15)
        //VStack
    }
}

struct MenuCardView_Previews: PreviewProvider {
    static var previews: some View {
        MenuCardView()
    }
}
