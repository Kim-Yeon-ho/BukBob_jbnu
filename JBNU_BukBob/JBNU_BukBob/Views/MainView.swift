//
//  MainView.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
//

import SwiftUI

struct MainView: View {
    @State var isFirstChecking: Bool = false
    @State var isSecondChecking: Bool = false
    @State var isThirdChecking: Bool = false
    @State var isFourChecking: Bool = false
    @State var indexCount: Int = 0

    var body: some View {
        TabView {
            BreakfastView(isFirstChecking: $isFirstChecking, isSecondChecking: $isSecondChecking, isThirdChecking: $isThirdChecking, isFourChecking: $isFourChecking, indexCount: $indexCount)

            LunchView(isFirstChecking: $isFirstChecking, isSecondChecking: $isSecondChecking, isThirdChecking: $isThirdChecking, isFourChecking: $isFourChecking, indexCount: $indexCount)

            DinnerView(isFirstChecking: $isFirstChecking, isSecondChecking: $isSecondChecking, isThirdChecking: $isThirdChecking, isFourChecking: $isFourChecking, indexCount: $indexCount)
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
    @Binding var isFirstChecking: Bool
    @Binding var isSecondChecking: Bool
    @Binding var isThirdChecking: Bool
    @Binding var isFourChecking: Bool
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
            ScrollView {
                VStack {
                    ForEach (0..<4) {_ in
                        MenuCardView(isFirstChecking: $isFirstChecking, isSecondChecking: $isSecondChecking, isThirdChecking: $isThirdChecking, isFourChecking: $isFourChecking, indexCount: $indexCount)
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
    @Binding var isFirstChecking: Bool
    @Binding var isSecondChecking: Bool
    @Binding var isThirdChecking: Bool
    @Binding var isFourChecking: Bool
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
            ScrollView {
                VStack {
                    ForEach (0..<4) {_ in
                        MenuCardView(isFirstChecking: $isFirstChecking, isSecondChecking: $isSecondChecking, isThirdChecking: $isThirdChecking, isFourChecking: $isFourChecking, indexCount: $indexCount)
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
    @Binding var isFirstChecking: Bool
    @Binding var isSecondChecking: Bool
    @Binding var isThirdChecking: Bool
    @Binding var isFourChecking: Bool
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
            ScrollView {
                VStack {
                    ForEach (0..<4) {_ in
                        MenuCardView(isFirstChecking: $isFirstChecking, isSecondChecking: $isSecondChecking, isThirdChecking: $isThirdChecking, isFourChecking: $isFourChecking, indexCount: $indexCount)
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

//struct MainView_Previews: PreviewProvider {
//    static var previews: some View {
//        MainView()
//    }
//}
//
