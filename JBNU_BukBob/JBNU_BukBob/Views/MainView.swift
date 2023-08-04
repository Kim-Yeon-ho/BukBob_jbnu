//
//  MainView.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
// listView!! 

import SwiftUI

struct MainView: View {
    private let tabViewIndex = [0,1,2]
    var cafeterias = Cafeterias(cafeteria: nil, menus: nil, mealTime: nil, time: nil, bookmark: [nil])
    var body: some View {
        TabView {
            ForEach(tabViewIndex, id: \.self) { index in
                if index == 0 {
                    MenuView(tabViewIndex: index)
                }
                if index == 1 {
                    MenuView(tabViewIndex: index)
                }
                if index == 2 {
                    MenuView(tabViewIndex: index)
                }
            }
        }
        .tabViewStyle(PageTabViewStyle())
        .onAppear {
            setupAppearance()
        }
    }

    func setupAppearance() {
        UIPageControl.appearance().currentPageIndicatorTintColor = .mainPurple
        UIPageControl.appearance().pageIndicatorTintColor = UIColor.black.withAlphaComponent(0.2)
    }
}

struct MenuView: View {
    var tabViewIndex: Int
    var body: some View {
        VStack {
            HStack {
                Text("\(MealTime.allCases[tabViewIndex].rawValue)")
                    .font(.system(size: 50, weight: .semibold))
                    .foregroundColor(.mainPurple)
                    .padding()

                Spacer()
                VStack {
                    Text("03/04 토")
                        .font(.system(size: 35, weight: .medium))
                        .padding(.horizontal)

                    Text("07:00 ~ 08:30")
                        .font(.system(size: 20, weight: .medium))
                        .padding(.horizontal)
                }
            } //HStack
            ScrollView {
                ForEach(0..<4, id: \.self) { index in
                    MenuCardView()
                        .frame(height: DeviceFrame.screenHeight * 0.17)
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
                .padding(.horizontal)
        }//VStack
    }
}

//struct MainView_Previews: PreviewProvider {
//    static var previews: some View {
//        MainView()
//    }
//}
//
