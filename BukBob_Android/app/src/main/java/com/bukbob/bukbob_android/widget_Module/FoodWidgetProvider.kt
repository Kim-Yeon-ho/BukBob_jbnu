/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.widget_Module

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.bukbob.bukbob_android.R

class FoodWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        context?.let {
            var widgetViews = RemoteViews(context.packageName, R.layout.food_list_item_widget_view)
            widgetViews.setTextViewText(R.id.foodList_widget,"test")
            appWidgetManager?.updateAppWidget(appWidgetIds,widgetViews)
        }

        /**
         * 해당 코드는 위젯의 업데이트를 담당하는 함수입니다.
         * views 변수에는 RemoteViews를 사용한 View의 변화를 지원한다.
         * views에서 setTextViewText(해당 레이아웃, "텍스트 값")을 사용하여 식단을 변경한다.
         * appWidgetManager는 업데이트 된 뷰를 적용해줍니다.
         *
         * 해당 코드는 30분 단위로 진행됩니다.
         * 해당 코드는 인터넷 연결 또는 DB가 없을때에 대한 예외 처리를 해야합니다.
         * */

    }
}