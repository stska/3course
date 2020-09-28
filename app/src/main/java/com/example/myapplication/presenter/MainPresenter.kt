package com.example.myapplication.presenter

import com.example.myapplication.ButtonIndex
import com.example.myapplication.model.CounterModel
import com.example.myapplication.view.MainView

class MainPresenter (val view : MainView){
    var mModel = CounterModel()

    fun counterClick(id: Int){
        when(id){
            ButtonIndex.FIRST.index -> {
                val nextValue = mModel.next(0)
                view.setButtonText(0, nextValue.toString())
            }
            ButtonIndex.SECOND.index-> {
                val nextValue = mModel.next(1)
                view.setButtonText(1, nextValue.toString())
            }
            ButtonIndex.THIRD.index -> {
                val nextValue = mModel.next(2)
                view.setButtonText(2, nextValue.toString())
            }
        }
    }
}