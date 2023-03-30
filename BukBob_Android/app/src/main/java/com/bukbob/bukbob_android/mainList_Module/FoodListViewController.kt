/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.mainList_Module

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.text.Html
import androidx.lifecycle.ViewModelProvider
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.R
import com.bukbob.bukbob_android.main_Module.MainViewModel
import com.bukbob.bukbob_android.widget_Module.FoodWidgetProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// 해당 함수는 FoodListAdapter에 의해 호출됩니다.
class FoodListViewController(owner: MainActivity, private val holder: FoodListAdapter.ViewHolder) {

    private val menu : Array<String> = arrayOf("찌개","돌솥","특식","도시락","덮밥/비빔밥","샐러드","돈까스류","오므라이스류0","오므라이스류","김밥","라면","우동")
    private val listViewModel : MainViewModel = ViewModelProvider(owner)[MainViewModel::class.java]
    private val checkName = checkTitleSave()

    fun checkStarButton(position: Int,foodMarketName: String, state: String){
        val isDifferentStart = isDifferentStartCheck(position)
        listViewModel.setPosition(position)

        if(isDifferentStart){
            setStartButtonState(foodMarketName, state)
        }else{
            listViewModel.setIsCheck(true)
            holder.binding.widgetButton.setImageResource(R.drawable.starfill)
            editTitlePref(foodMarketName)
        }
    }

    /**
     * checkStarButton 함수()?
     *
     * 해당 함수는 리사이클러뷰 안에 위젯 버튼이 서로 다른 위치에서 눌린건지, 같은 위치에서 눌린건지 판단하는 함수입니다.
     *
     * 첫번째로 똑같은 위치의 위젯 즐겨찾기 버튼을 눌렀을 때 값을 비교하고 적절하게 버튼의 상태 변경을 호출합니다.
     * 두번째로 다른 위치의 위젯 즐겨찾기 버튼을 눌렀을 때 라이브 데이터를 변경하고 클릭한 버튼을 즐겨찾기 처리합니다.
     * */

    private fun setStartButtonState(foodMarketName: String, state: String){
        if(listViewModel.isButtonCheck.value == false){
            holder.binding.widgetButton.setImageResource(R.drawable.starfill)
            listViewModel.setIsCheck(true)
            editTitlePref(foodMarketName)
            editStatePref(state)
        }else{
            holder.binding.widgetButton.setImageResource(R.drawable.starnonfill)
            listViewModel.setIsCheck(false)
            delCheckTitle()
        }
    }

    /**
     * setStartButtonState()?
     *
     * 해당 함수는 checkStarButton()함수에서 같은 위치의 버튼을 클릭한 것인지,
     * 다른 위치의 버튼을 클릭한 것인지 분기가 정해지고 같은 위치의 버튼을 클릭 했을때 처리해주는 함수입니다.
     * */

    fun setButton(position: Int,title: String,isSave : Boolean, state: String){
        if(isSave){
            listViewModel.setIsCheck(true)
            listViewModel.setPosition(position)
            setStartButtonState(title,state)
        }else{
            checkStarButton(position, title, state)
        }
    }

    /**
     * setButton()?
     * 최초 위젯 버튼을 클릭하면 해당 버튼이 사용자가 즐겨찾기한 항목인지 판단하고 적절하게 메소드를 실행합니다.
     * */

    fun checkPref(title: String) : Boolean{
        return if (title == checkName) {
            holder.binding.widgetButton.setImageResource(R.drawable.starfill)
            true
        }else{
            false
        }
    }

    /**
     * checkPref()?
     * 해당 함수는 pref에 사용자가 즐겨찾기 한 위젯 타이틀이 있는지 확인하고 true false로 리턴합니다.
     * */

    private fun editTitlePref(foodMarketName : String){
        CoroutineScope(Dispatchers.IO).launch{
            val saveTitle =
                holder.binding.root.context.getSharedPreferences("checkTitle", Context.MODE_PRIVATE)
            val edit = saveTitle.edit()
            edit.putString("title", foodMarketName)
            edit.apply()

            val widgetProvider = FoodWidgetProvider()
            val widgetUpdateIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            widgetProvider.onReceive(holder.binding.root.context,widgetUpdateIntent)

        }
    }

    /**
     * editTitlePref()?
     * 해당 함수는 사용자가 즐겨찾기한 항목을 pref에 저장힙니다.
     * 또한, 사용자가 즐겨찾기한 항목을 위젯에 반영하여 최신 버전으로 업데이트 하기 위해
     * 브로드 캐스트 리시버를 사용해 업데이트를 호출합니다.
     * */

    private fun editStatePref(state : String){
        CoroutineScope(Dispatchers.IO).launch{
            val saveState =
                holder.binding.root.context.getSharedPreferences("checkState", Context.MODE_PRIVATE)
            val edit = saveState.edit()
            edit.putString("state", state)
            edit.apply()
        }
    }

    /**
     * editStatePref()?
     * 해당 함수는 사용자가 즐겨찾기한 항목의 아침/점심/저녁 상태를 pref에 저장힙니다.
     * */

    private fun delCheckTitle(){
        CoroutineScope(Dispatchers.IO).launch {
            val delTitle = holder.binding.FoodList.context.getSharedPreferences(
                "checkTitle",
                Context.MODE_PRIVATE
            )
            val edit = delTitle.edit()
            edit.remove("title")
            edit.apply()
        }
    }

    /**
     * delCheckTitle()?
     * 해당 함수는 사용자가 즐겨찾기 취소한 항목을 pref에서 제거합니다.
     * */

    private fun checkTitleSave() :String?{
        val getTitle = holder.binding.root.context.getSharedPreferences("checkTitle", Context.MODE_PRIVATE)
        return getTitle.getString("title","없음")
    }

    /**
     * checkTitleSave()?
     * 해당 항목은 사용자가 즐겨찾기한 내용이 존재하는지 확인하고 있으면 해당값을 리턴해줍니다.
     * */

    fun setFoodList(foodMarketName:String, foodListArray:ArrayList<*>){
        var foodText = ""
        when (foodMarketName) {
            "후생관" -> {
                setHuseangFoodList(foodMarketName, foodListArray)
            }
            "참빛관"->{
                setChamFoodList(foodMarketName,foodListArray)
            }
            else -> {
                foodListArray.forEach {
                    if(it.toString() != "") {
                        foodText += it.toString().replace("&amp;", "").replace("&nbsp;", "") + '\n'
                    }
                }
                holder.binding.foodMarket.text = foodMarketName
                holder.binding.FoodList.text = foodText.substring(0,foodText.length-1)
            }
        }
    }

    /**
     * setFoodList()?
     * 해당 항목은 후생관을 제외한 식단 리스트를 View에 설정하는 함수입니다.
     * */

    private fun setChamFoodList(foodMarketName: String,foodList: ArrayList<*>){
        var foodText = ""
        foodList.forEach {
            foodText += if(it != "" && it != "," && !(it.toString().contains('(')) && !(it.toString().contains(')'))) {
                "${it}<br>"
            }else if(it.toString().contains('(') && it.toString().contains(')')){
                "<br><b>${it}</b><br>"
            } else {
                ""
            }
        }

        holder.binding.foodMarket.text = foodMarketName
        holder.binding.FoodList.text = Html.fromHtml(foodText.substring(0,foodText.length-2),Html.FROM_HTML_MODE_LEGACY)
    }

    private fun setHuseangFoodList(foodMarketName: String,foodList: ArrayList<*>){
        var menuIndex = 0
        val foodListArray = foodList[0].toString().split("</br>")
        var foodText = ""
        foodListArray.forEach {
            if(it !="" && it != ",") {
                foodText += "<b>${menu[menuIndex]}</b> <br>${it.replace("&amp;", "").replace("&nbsp;", "")}" + "<br><br>"
                menuIndex++
            }else if(it == ","){
                foodText += "<b>${menu[menuIndex]}</b> <br>운영없음" + "<br><br>"
                menuIndex++
            }
        }

        holder.binding.foodMarket.text = foodMarketName
        holder.binding.FoodList.text = Html.fromHtml(foodText.substring(0,foodText.length-2),Html.FROM_HTML_MODE_LEGACY)
    }

    /**
     * setHuseangFoodList()?
     * 해당 함수는 후생관 식단 리스트를 설정하는 함수입니다.
     * */
    private fun isDifferentStartCheck(position: Int) : Boolean{
        return listViewModel.position.value == position
    }

    /**
     * isDifferentStartCheck()?
     *
     * 해당 함수는 서로 다른 위치의 위젯 즐겨찾기 버튼을 눌렀는지 비교하는 함수입니다.
     * */

}