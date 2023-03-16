//
//  MainView.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
//

import SwiftUI

struct MainView: View {
//    @Binding var isChecking: Bool
    @State var isChecking: Bool = false
    var body: some View {
        TabView {
            BreakfastView(isChecking: $isChecking)
            LunchView(isChecking: $isChecking)
            DinnerView(isChecking: $isChecking)
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
            ScrollView {
                VStack {
                    ForEach (0..<4) {_ in
                        MenuCardView(isChecking: $isChecking)
                            .frame(height: DeviceFrame.screenHeight * 0.16)
                            .padding()
                    }
                    //식단 메뉴카드
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
            //ScrollView
        }//VStack
    }
}

struct LunchView: View {
    @Binding var isChecking: Bool
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
            ScrollView {
                VStack {
                    ForEach (0..<4) {_ in
                        MenuCardView(isChecking: $isChecking)
                            .frame(height: DeviceFrame.screenHeight * 0.16)
                            .padding()
                    }
                    //식단 메뉴카드
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
            //ScrollView
        }//VStack
    }
}

struct DinnerView: View {
    @Binding var isChecking: Bool
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
            ScrollView {
                VStack {
                    ForEach (0..<4) {_ in
                        MenuCardView(isChecking: $isChecking)
                            .frame(height: DeviceFrame.screenHeight * 0.16)
                            .padding()
                    }
                    //식단 메뉴카드
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
            //ScrollView
        }//VStack
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}

