package com.jetpack.databinding;

import android.util.Log;
import android.view.View;
import androidx.databinding.BindingAdapter;

public class Utils {

    /*
    * BindingAdapter: 是对绑定数据的触发器的监听器，
    * 通过它只要改变绑定的数据，这个触发器就会被触发。它的函数体就会被执行
    * 布置:
    *   仅需将一个public static method 添加注解 @BindingAdapter("监听数据名')
    *   绑定数据发生改变就会触发bindIsGone函数调用.
    *   xml布局中对应一个自定义属性:
    *
    *   <data>
    *     <variable
    *       name="hasDatas"
    *       type="boolean"
    *       />
    *   </data>
    *
    *   ...
    *
    *   <TextView
    *     ...
    *     app:isGone="@{!hasDatas}"
    *     />
    *
    *     ...
    * */

    /*
    * args:
    * v: 绑定的控件本身
    * isGone: 可传递的自定义参数(绑定的数据)
    * */
    @BindingAdapter("isGone")
    public static void bindIsGone(View v, boolean isGone) {
        Log.d("BBBB", "bindIsGone: isGone " + isGone);
        if (isGone) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.VISIBLE);
        }
    }
}
