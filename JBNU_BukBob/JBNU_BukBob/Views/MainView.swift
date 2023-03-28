//
//  MainView.swift
//  JBNU_BukBob
//
//  Created by 김연호 on 2023/03/12.
// listView!! 

import SwiftUI

struct MainView: View {
    @State var cafeterias: Cafeteria
    @State var isChecking: Bool = false
    //식당의 인덱스 순서를 구별하기 위해 인덱스값과 bool값을 튜플로 저장

    var body: some View {
        TabView {
            BreakfastView(cafeterias: $cafeterias, isChecking: $isChecking)

            LunchView(cafeterias: $cafeterias, isChecking: $isChecking)

            DinnerView(cafeterias: $cafeterias, isChecking: $isChecking)
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
    @Binding var cafeterias: Cafeteria
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
            List() {
                ForEach(0..<4, id: \.self) { index in
                    MenuCardView(cafeteria: $cafeterias, isChecking: $isChecking)
                        .frame(height: DeviceFrame.screenHeight * 0.16)
                        .padding()
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
        }//VStack
    }
}

struct LunchView: View {
    @Binding var cafeterias: Cafeteria
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
            List() {
                ForEach(0..<4, id: \.self) { index in
                    MenuCardView(cafeteria: $cafeterias, isChecking: $isChecking)
                        .frame(height: DeviceFrame.screenHeight * 0.16)
                        .padding()
                }
            }.frame(height: DeviceFrame.screenHeight * 0.6)
        }//VStack
    }
}

struct DinnerView: View {
    @Binding var cafeterias: Cafeteria
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
            List() {
                ForEach(0..<4, id: \.self) { index in
                    MenuCardView(cafeteria: $cafeterias, isChecking: $isChecking)
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
