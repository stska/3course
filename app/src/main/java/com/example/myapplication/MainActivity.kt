package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.presenter.MainPresenter
import com.example.myapplication.view.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    private val mPresenter = MainPresenter(this)

    companion object {
        const val FIRST_BTN = 0
        const val SECOND_BTN = 1
        const val THIRD_BTN = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listener = View.OnClickListener {
            mPresenter.counterClick(it.id)
        }
        btn_counter1.setOnClickListener(listener)
        btn_counter2.setOnClickListener(listener)
        btn_counter3.setOnClickListener(listener)

    }
    override fun setButtonText(index: Int, text: String) {
        when (index) {
            FIRST_BTN -> btn_counter1.text = text
            SECOND_BTN -> btn_counter2.text = text
            THIRD_BTN -> btn_counter3.text = text
        }

    }
}