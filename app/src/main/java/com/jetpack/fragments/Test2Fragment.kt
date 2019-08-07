package com.jetpack.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ilifesmart.weather.R

class Test2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test2, container, false).apply {
            findViewById<Button>(R.id.navigation_button2).setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_test2Fragment_to_test3Fragment)
            }
        }
    }


}
