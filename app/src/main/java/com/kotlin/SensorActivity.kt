package com.kotlin

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ilifesmart.weather.R
import java.lang.Exception


class SensorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sensor)

        try {
            val mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            if (null != mSensorManager) {
                Log.d("BBBB", "onCreate: getSensorList ${mSensorManager.getSensorList(Sensor.TYPE_LIGHT)}")
                val adcSensor: Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) // 26: Sensor.TYPE_ADC
                Log.d("BBBB", "onCreate: adcSensor $adcSensor")
                if (adcSensor != null) {
                    val mAdcSensorEventListener = object : SensorEventListener {
                        override fun onSensorChanged(event: SensorEvent) {
                            val voltage = event.values[0]
                            with(findViewById<TextView>(R.id.sensor_value)) {
                                text = "$text + \n + $voltage"
                            }
                        }

                        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                    }
                    mSensorManager.registerListener(
                        mAdcSensorEventListener,
                        adcSensor,
                        SensorManager.SENSOR_DELAY_NORMAL
                    )
                }
            }

        } catch (exp:Exception) {
            Log.d("BBBB", "onCreate: error " + exp.message)
            Log.d("BBBB", "onCreate: error " + exp.printStackTrace())
        }
    }

}