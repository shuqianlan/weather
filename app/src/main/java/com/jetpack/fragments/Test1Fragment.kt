package com.jetpack.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ilifesmart.weather.R


class Test1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test1, container, false).apply {
            findViewById<Button>(R.id.navigation_button1).setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_test1Fragment_to_test2Fragment)
            }
        }
    }


}
