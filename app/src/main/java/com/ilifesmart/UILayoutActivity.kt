package com.ilifesmart

import android.app.Activity
import android.view.View
import android.widget.Toast
import com.ilifesmart.ui.CarouseLayout
import com.ilifesmart.weather.R
import com.imou.BaseActivity
import kotlinx.android.synthetic.main.activity_uilayout.*

fun Activity.toast(msg:String, dura:Int=Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, dura).show()
}

class UILayoutActivity : BaseActivity() {

    override fun layoutResId() = R.layout.activity_uilayout

    override fun initView() {
        frame_cont.setOnClickListener {
            simple_group.autoPlay()
        }

        simple_group.setOnScrollListener(object :CarouseLayout.OnScrollListener {
            override fun onScrollTo(distanceX: Float, percent: Int, index: Int) {
                println("onScrollTo distanceX:$distanceX, percent:$percent, currIndex: $index")
            }

            override fun onScrollToEnd(index: Int) {
                println("onScrollToEnd newIndex: $index")
            }

            override fun callOnClick(index: Int, view: View) {
                println("onScrollTo onClickIndex: $index")
            }

        })

    }
}
