/**
 * 2023 / 03 / 24 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.widget_Module

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.SharedPreferences
import android.widget.RemoteViews
import com.bukbob.bukbob_android.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import java.text.SimpleDateFormat
import java.util.*

class FoodWidgetController(context: Context, private val appWidgetManager: AppWidgetManager?, private val Ids : IntArray?) {
    private var db = FirebaseFirestore.getInstance()
    private var widgetViews = RemoteViews(context.packageName, R.layout.food_list_item_widget_view)
    private var title : String?= getDbTitle()
    private val date = SimpleDateFormat("E", Locale.KOREA).format(Date())
    private val pref: SharedPreferences = context.getSharedPreferences("checkTitle", Context.MODE_PRIVATE)

    /**
     * db => 파이어베이스 인스턴스를 받아옵니다.
     * widgetViews => 위젯을 컨트롤 하기 위한 리모트 뷰 객체입니다.
     * title => 사용자가 즐겨찾기를 추가한 식당의 이름 변수입니다.
     * date => 현재 핸드폰의 시간정보입니다.
     * pref => checkTitle이라는 이름의 pref를 제어하기 위한 객체입니다.
     * */


    private fun getDbTitle() : String?{
        title = try {
            pref.getString("title", "없음")
        }catch (e : NullPointerException){
            "진수원"
        }

        title = when (title) {
            "진수원" -> "Jinswo"
            "의대" -> "Medical"
            "후생관" -> "Husaeng"
            else -> "Jinswo"
        }

        return title
    }

    /**
     * getDbTitle()?
     * pref에서 사용자가 즐겨찾기한 식당의 정보를 가져오고, DB 호출에 사용할 이름을 리턴해줍니다.
     * */

    private fun getPrefTitle() : String? {
        return if (pref.getString("title", "없음") == "없음"){
            "진수원"
        }else{
            pref.getString("title", "없음")
        }
    }

    /**
     * getPrefTitle()?
     * 사용자가 즐겨찾기한 이름을 가져옵니다. 없을 경우 기본값으로 진수원을 설정했습니다.
     * */

    fun setFoodList(){
        dbCacheController(false)

        widgetViews.setTextViewText(R.id.food_market_widget, getPrefTitle())
        title?.let { it ->
            db.collection(it).document(date).get().addOnSuccessListener {
                widgetViews.setTextViewText(
                    R.id.foodList_widget,
                    it.data!!["List"].toString()
                )
                appWidgetManager?.updateAppWidget(Ids, widgetViews)
                db.terminate()
            }
            db = FirebaseFirestore.getInstance()
            dbCacheController(true)
        }

    }

    /**
     * setFoodList()?
     * 해당 함수는 함수명 그대로 식단 정보를 받아와 설정해주는 함수입니다.
     *
     * 위젯의 정보를 설정하기 위해서 기존 파이어베이스 오프라인 캐쉬를 잠시 off하고 설정이 끝나면 다시 On 해줌으로써
     * 그날 받아온 최신 식단 캐시 정보를 유지해줍니다.
     * */

    private fun dbCacheController(isTrue : Boolean){
        getDbTitle()
        if(isTrue) {
            val settings = firestoreSettings {
                isPersistenceEnabled = true
            }

            db.firestoreSettings = settings
        }else{
            val settings = firestoreSettings {
                isPersistenceEnabled = true
            }

            db.firestoreSettings = settings
        }
    }

    /**
     * dbCacheController()?
     * 해당 함수는 파이어베이스의 오프라인 캐시 정보의 사용 유무를 제어하는 함수로써
     * off 할 경우 기존 캐시 데이터는 변경없이 유지되고 on 할 경우 차후 캐시 데이터가
     * 새로 기록됩니다. ( 업데이트에 유용합니다. )
     * */

}