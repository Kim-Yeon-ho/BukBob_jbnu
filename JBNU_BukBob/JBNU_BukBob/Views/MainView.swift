//
//  MainView.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
// listView!! 

import SwiftUI

struct MainView: View {
    @State var isChecking: Bool = false
    @State var indexCount: Int = 0
    var cafeterias = Cafeterias(cafeteria: nil, menus: nil, mealTime: nil, time: nil)
    //cafeteria 타입으로 변수 선언

    var body: some View {
        TabView {
            BreakfastView(isChecking: $isChecking, indexCount: $indexCount)

            LunchView(isChecking: $isChecking, indexCount: $indexCount)

            DinnerView(isChecking: $isChecking, indexCount: $indexCount)
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

struct BreakfastView: View {
    @Binding var isChecking: Bool
    @Binding var indexCount: Int
    var body: some View {
        VStack {
            HStack {
                Text("조식")
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
            List() {
                ForEach(0..<4, id: \.self) { index in
                    MenuCardView(isChecking: $isChecking, indexCount: $indexCount)
                        .frame(height: DeviceFrame.screenHeight * 0.17)
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
        }//VStack
    }
}

struct LunchView: View {
    @Binding var isChecking: Bool
    @Binding var indexCount: Int
    var body: some View {
        VStack {
            HStack {
                Text("중식")
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
            List() {
                ForEach(0..<4, id: \.self) { index in
                    MenuCardView(isChecking: $isChecking, indexCount: $indexCount)
                        .frame(height: DeviceFrame.screenHeight * 0.16)
                        .padding()
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
        }//VStack
    }
}

struct DinnerView: View {
    @Binding var isChecking: Bool
    @Binding var indexCount: Int
    var body: some View {
        VStack {
            HStack {
                Text("석식")
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
            List() {
                ForEach(0..<4, id: \.self) { index in
                    MenuCardView(isChecking: $isChecking, indexCount: $indexCount)
                        .frame(height: DeviceFrame.screenHeight * 0.16)
                        .padding()
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
        }//VStack
    }
}

//struct MainView_Previews: PreviewProvider {
//    static var previews: some View {
//        MainView()
//    }
//}
//
