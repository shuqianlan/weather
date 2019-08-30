package com.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveDataViewModel : ViewModel() {
    val livedata by lazy {
        MutableLiveData<Int>().also {
            println("HAHAHAH 开始咯")
            it.value = 36
        }
    }
}