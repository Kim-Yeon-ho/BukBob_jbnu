/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.mainList_Module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.R
import com.bukbob.bukbob_android.main_Module.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// 해당 함수는 FoodListAdapter에 의해 호출됩니다.
class FoodListViewController(owner: MainActivity, private val holder: FoodListAdapter.ViewHolder) {

    private val menu : Array<String> = arrayOf("찌개","돌솥","특식","도시락","덮밥/비빔밥","샐러드","돈까스류","오므라이스류","오므라이스류","김밥","라면","우동")
    private val listViewModel : MainViewModel = ViewModelProvider(owner)[MainViewModel::class.java]
    private val checkName = checkTitleSave()

    fun checkStarButton(position: Int,foodMarketName: String){
        val isDifferentStart = isDifferentStartCheck(position)
        listViewModel.setPosition(position)

        if(isDifferentStart){
            setStartButtonState(foodMarketName)
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

    private fun setStartButtonState(foodMarketName: String){
        if(listViewModel.isButtonCheck.value == false){
            holder.binding.widgetButton.setImageResource(R.drawable.starfill)
            listViewModel.setIsCheck(true)
            editTitlePref(foodMarketName)
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

    fun setButton(position: Int,title: String,isSave : Boolean){
        if(isSave){
            listViewModel.setIsCheck(true)
            listViewModel.setPosition(position)
            setStartButtonState(title)
        }else{
            checkStarButton(position, title)
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
        }
    }

    /**
     * saveCheckTitle()?
     * 해당 함수는 사용자가 즐겨찾기한 항목을 pref에 저장힙니다.
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

        if(foodMarketName == "후생관"){
            setHuseangFoodList(foodMarketName, foodListArray)
        }else {
            foodListArray.forEach {
                foodText += it.toString().replace("&amp;", "").replace("&nbsp;", "") + '\n'
            }
            holder.binding.foodMarket.text = foodMarketName
            holder.binding.FoodList.text = foodText
        }
    }

    /**
     * setFoodList()?
     * 해당 항목은 후생관을 제외한 식단 리스트를 View에 설정하는 함수입니다.
     * */

    private fun setHuseangFoodList(foodMarketName: String,foodList: ArrayList<*>){
        var menuIndex = 0
        var foodListArray = foodList[0].toString().split("</br>")
        var foodText = ""
        foodListArray.forEach {
            if(it !="" && it != ",") {
                foodText += "${menu[menuIndex]} \n${it.replace("&amp;", "").replace("&nbsp;", "")}" + "\n\n"
                menuIndex++
            }else if(it == ","){
                foodText += "${menu[menuIndex]} \n운영없음" + "\n\n"
            }
        }

        holder.binding.foodMarket.text = foodMarketName
        holder.binding.FoodList.text = foodText
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