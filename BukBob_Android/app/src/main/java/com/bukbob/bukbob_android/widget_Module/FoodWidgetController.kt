/**
 * 2023 / 03 / 24 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.widget_Module

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.SharedPreferences
import android.text.Html
import android.util.Log
import android.widget.RemoteViews
import com.bukbob.bukbob_android.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FoodWidgetController(private val context: Context, private val appWidgetManager: AppWidgetManager?, private val Ids : IntArray?) {
    private var widgetViews = RemoteViews(context.packageName, R.layout.food_list_item_widget_view)
    private var title : String?= getDbTitle()
    private val date = SimpleDateFormat("E", Locale.KOREA).format(Date())
    private val today = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date())
    private var time = SimpleDateFormat("kk",Locale.KOREA).format(Date())
    private val pref: SharedPreferences = context.getSharedPreferences("checkTitle", Context.MODE_PRIVATE)
    private val prefKey = "title"
    private val menu : Array<String> = arrayOf("찌개","돌솥","특식","도시락","덮밥/비빔밥","샐러드","돈까스류","오므라이스류","오므라이스류1","김밥","라면","우동")

    /**
     * widgetViews => 위젯을 컨트롤 하기 위한 리모트 뷰 객체 입니다.
     * title => 사용자가 즐겨찾기를 추가한 식당의 이름 변수 입니다.
     * today => 현재 핸드폰의 날짜 정보 입니다.
     * date => 현재 핸드폰의 요일 정보 입니다.
     * pref => checkTitle이라는 이름의 pref를 제어하기 위한 객체 입니다.
     * prefKey => pref.getString()함수에서 공통으로 사용되는 key값입니다. 단, getSharedPreferences("updateInfo",..) 함수에서 사용되는 key는 아닙니다.
     * */


    private fun getDbTitle(): String? {
        title = try {
            pref.getString("title", "없음")
        }catch (e : NullPointerException){
            "진수원"
        }

        title = when (title) {
            "진수원" -> "Jinswo"
            "의대" -> "Medical"
            "후생관" -> "Husaeng"
            "참빛관" -> "Chame"
            "직영관" -> "Jigyeong"
            else -> "Jinswo"
        }
        return title
    }

    /**
     * getDbTitle()?
     * pref에서 사용자가 즐겨찾기한 식당의 정보를 가져오고, DB 호출에 사용할 이름을 리턴해줍니다.
     * */

    private fun getPrefTitle() : String? {
        return if (pref.getString(prefKey, "없음") == "없음"){
            "진수원"
        }else{
            pref.getString(prefKey, "없음")
        }
    }

    /**
     * getPrefTitle()?
     * 사용자가 즐겨찾기 한 이름을 가져 옵니다. 없을 경우 기본값 으로 진수원을 설정 했습니다.
     * */

    fun setFoodList(){
        getDbTitle()
        checkTime()

        if(readDbCallDate(context) == today){
            val db = Firebase.firestore
            dbNetWorkDisconnect(db)
            setDB(title,db)
        }else{
            dbNetWorkConnect(title)
        }

    }

    /**
     * setFoodList()?
     * 해당 함수는 함수명 그대로 식단 정보를 받아와 설정해주는 함수입니다.
     *
     * 위젯의 정보를 설정하기 위해서 기존 파이어베이스 인터넷 사용 여부를 조정 하여 캐시 사용 또는 업데이트를 조정 합니다.
     * 그날 받아온 최신 식단 캐시 정보를 유지해 줍니다.
     *
     * 차후 이곳에 시간에 따라 아침,점심,저녁을 분기하는 구간을 추가해야합니다.
     * */

    private fun checkTime(){
        val timeNumber = Integer.parseInt(time)

        if(title != "Husaeng") {
            title += if (timeNumber < 11) {
                widgetViews.setTextViewText(R.id.food_market_widget, "${getPrefTitle()} - 조식")
                "-breakfast"
            } else if (timeNumber in 11..16) {
                widgetViews.setTextViewText(R.id.food_market_widget, "${getPrefTitle()} - 중식")
                "-lunch"
            } else {
                widgetViews.setTextViewText(R.id.food_market_widget, "${getPrefTitle()} - 석식")
                "-night"
            }
        }else{
            widgetViews.setTextViewText(R.id.food_market_widget, "${getPrefTitle()}")
        }
    }

    private fun setDB(collectionName: String?,db : FirebaseFirestore){
        var foodList: String
        db.collection(collectionName!!).document(date).get().addOnSuccessListener {
            if(it.data!!["Title"] == "참빛관"){
                setChamFoodList(it.data!!["List"] as ArrayList<*>)
            }else {
                if (it.data!!["Title"] != null) {
                    foodList = if (it.data!!["Title"].toString() == "후생관") {
                        setHuseangFoodList(it.data!!["List"].toString())
                    } else {
                        reMakeFoodListText(
                            it.data!!["Title"].toString(),
                            it.data!!["List"] as ArrayList<*>
                        )
                    }

                    widgetViews.setTextViewText(
                        R.id.foodList_widget,
                        foodList
                    )
                } else {
                    widgetViews.setTextViewText(
                        R.id.foodList_widget,
                        "DB 데이터 누락 차후에 다시 시도해 주세요."
                    )
                }
                appWidgetManager?.updateAppWidget(Ids, widgetViews)
            }
        }.addOnFailureListener {
            widgetViews.setTextViewText(
                R.id.foodList_widget,
                "식단 정보가 존재하지 않습니다."
            )
            appWidgetManager?.updateAppWidget(Ids, widgetViews)
        }
    }

    /**
     * setDB()?
     * 위젯에 DB를 설정해주는 함수입니다. setFoodList() 함수에서 업데이트 유/무에 따라
     * 캐시를 사용 또는 인터넷에서 새로운 정보를 받아와 위젯에 설정을 진행합니다.
     *
     * 기본적으로 title의 Null검사를 진행하고 후생관인지 다른 식당인지 검사를 진행합니다.
     * 이후에 새로 FoodList의 Text를 제조하여 위젯에 설정합니다.
     * */

    private fun setChamFoodList(foodList: ArrayList<*>){
        var widgetViews = RemoteViews(context.packageName, R.layout.food_list_item_widget_view2)
        var filterFoodText = ""
        var foodText1 = ""
        var foodText2 = ""

        foodList.forEach {
            filterFoodText += if(it != "" && it != "," && !(it.toString().contains('(')) && !(it.toString().contains(')'))) {
                "${it}<br>"
            }else if(it.toString().contains('(') && it.toString().contains(')')){
                "<br><b>${it}</b><br>"
            } else {
                ""
            }
        }
        var foodArray = filterFoodText.split("<br><br><b>") as ArrayList<String>

        foodArray[0].forEach {
            foodText1 += it.toString()
        }
        foodArray[1].forEach {
            foodText2 += it.toString()
        }

        when(title) {
            "Chame-breakfast" -> widgetViews.setTextViewText(R.id.food_market_widget, "참빛관 - 조식")
            "Chame-lunch" -> widgetViews.setTextViewText(R.id.food_market_widget, "참빛관 - 중식")
            "Chame-night" -> widgetViews.setTextViewText(R.id.food_market_widget, "참빛관 - 석식")
        }
        widgetViews.setTextViewText(R.id.foodList_widget,Html.fromHtml(foodText1,Html.FROM_HTML_MODE_LEGACY))
        widgetViews.setTextViewText(R.id.foodList_widget2,Html.fromHtml(foodText2,Html.FROM_HTML_MODE_LEGACY))
        appWidgetManager?.updateAppWidget(Ids,widgetViews)

    }

    private fun reMakeFoodListText(foodMarketName: String, foodListArray: ArrayList<*>) : String{
        var foodText = ""

        if(foodMarketName == "후생관"){
            setHuseangFoodList(foodListArray.toString())
        }else {
            foodListArray.forEach {
                if (it.toString() != "") {
                    foodText += it.toString().replace("&amp;", "").replace("&nbsp;", "") + '\n'
                }
            }
        }

        return foodText.substring(0,foodText.length-1)
    }

    /**
     * reMakreFoodListText()?
     * 후생관을 제외한 나머지 식당의 식단 리스트를 제작하는 함수입니다.
     * */

    private fun setHuseangFoodList(foodList: String) : String{
        var menuIndex = 0
        val foodListArray = foodList.split("</br>")
        var foodText = ""
        foodListArray.forEach {
            if(it !="" && it != ",") {
                when (menu[menuIndex]) {
                    "찌개" -> foodText +=  "${menu[menuIndex]} : ${
                        it.replace("&amp;", "").replace("&nbsp;", "")
                    }" + "\n"
                    "돌솥" -> foodText +=  "${menu[menuIndex]} : ${
                        it.replace("&amp;", "").replace("&nbsp;", "")
                    }" + "\n"
                    "특식" -> foodText +=  "${menu[menuIndex]} : ${
                        it.replace("&amp;", "").replace("&nbsp;", "")
                    }" + "\n"
                    "샐러드" -> foodText +=  "${menu[menuIndex]} : ${
                        it.replace("&amp;", "").replace("&nbsp;", "")
                    }" + "\n"
                    "오므라이스류1" -> foodText +=  "오므라이스류 : ${
                        it.replace("&amp;", "").replace("&nbsp;", "")
                    }"
                }
                menuIndex++
            }else{
                menuIndex++
            }
        }
        return foodText
    }
    /**
     * setHuseangFoodList()?
     * 해당 함수는 후생관에서 주마다 변하는 찌개,돌솥,특식,샐러드,오므라이스류를 설정해줍니다.
     * */

    private fun readDbCallDate(context: Context): String? {
        val shared = context.getSharedPreferences("updateInfo", Context.MODE_PRIVATE)

        return shared.getString("Date", "없음")
    }
    /**
     * readDbCallDate()?
     * 해당 함수는 사용자가 디비에서 새로운 정보를 받아왔는지 날짜를 가져오는 함수입니다.
     * */

    private fun dbNetWorkDisconnect(db: FirebaseFirestore) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                db.disableNetwork()
            }
        }
    }

    /**
     * dbNetWorkDisconnect()?
     * 해당 함수는 fireBase의 네트워크를 차단하는 함수입니다.
     * 이는, 파이어베이스 자체 캐시 데이터를 사용할 수 있도록 유도합니다.
     * */

    private fun dbNetWorkConnect(title : String?){
        CoroutineScope(Dispatchers.Main).launch {
            val db = Firebase.firestore
            db.enableNetwork()
            setDB(title,db)
        }
    }

    /**
     * dbNetWorkConnect()?
     * 해당 함수는 fireBase의 네트워크를 허용하는 함수입니다.
     * 최초에 캐시를 사용하기 위해 차단된 네트워크를 다시 허용해줍니다.
     * */




}