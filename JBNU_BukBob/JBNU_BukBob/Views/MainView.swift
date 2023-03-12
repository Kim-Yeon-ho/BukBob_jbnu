//
//  MainView.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
//

import SwiftUI

struct MainView: View {
    var body: some View {
        TabView {
            BreakfastView()
            LunchView()
            DinnerView()
        }
        .tabViewStyle(PageTabViewStyle())
        .onAppear {
            setupAppearance()
        }
//        .indexViewStyle(PageIndexViewStyle(backgroundDisplayMode: .always))
    }

    func setupAppearance() {
        UIPageControl.appearance().currentPageIndicatorTintColor = .mainPurple
        UIPageControl.appearance().pageIndicatorTintColor = UIColor.black.withAlphaComponent(0.2)
      }
}

struct BreakfastView: View {
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
                        MenuCardView()
                            .frame(height: DeviceFrame.screenHeight * 0.16)
                            .padding()
                    }
                    //식단 메뉴카드
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
            //ScrollView
            //화면 전환 컴포넌트
        }//VStack
    }
}

struct LunchView: View {
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
                        MenuCardView()
                            .frame(height: DeviceFrame.screenHeight * 0.16)
                            .padding()
                    }
                    //식단 메뉴카드
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
            //ScrollView
            //화면 전환 컴포넌트
        }//VStack
    }
}

struct DinnerView: View {
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
                        MenuCardView()
                            .frame(height: DeviceFrame.screenHeight * 0.16)
                            .padding()
                    }
                    //식단 메뉴카드
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
            //ScrollView
            //화면 전환 컴포넌트
        }//VStack
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}

