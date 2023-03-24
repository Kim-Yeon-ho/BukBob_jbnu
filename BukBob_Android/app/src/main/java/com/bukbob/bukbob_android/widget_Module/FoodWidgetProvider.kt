/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.widget_Module

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.*

class FoodWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val widgetController = context?.let { FoodWidgetController(it,appWidgetManager,appWidgetIds) }

        context?.let {
            CoroutineScope(Dispatchers.Main).launch {
                widgetController!!.setFoodList()
            }
        }
    }

    /**
     * onUpdate()?
     * 위젯에서 업데이트가 일어날 때 발생할 이벤트를 정의한 함수입니다.
     * 기본적으로 식단 업데이트를 실시합니다.
     * */

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context!!,FoodWidgetProvider::class.java))
        val widgetController = context?.let { FoodWidgetController(it,appWidgetManager,appWidgetIds) }

        CoroutineScope(Dispatchers.Main).launch {
            widgetController!!.setFoodList()
        }
    }

    /**
     * onReceive()?
     * 사용자가 지정한 식당 즐겨찾기 내용을 바로 반영해주기 위해 설정한 Receive함수입니다.
     * 브로드캐스트 방식으로 인텐트를 전달하여 호출합니다.
     * */


}