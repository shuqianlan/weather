package com.ilifesmart.weather

import android.app.Dialog
import android.app.job.JobScheduler
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.ilifesmart.ToolsApplication
import com.services.JobScheduleHelper
import com.services.MyJobService
import kotlinx.android.synthetic.main.activity_m_p3.*
import kotlin.collections.mutableListOf as array


class MP3Activity : AppCompatActivity() {
	private lateinit var scheduler:JobScheduler

	private lateinit var handler:Handler
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_m_p3)
		
		handler = Handler(Looper.myLooper()!!) {
			true
		}
		start.setOnClickListener {
			
			JobScheduleHelper.scheduleJobInfo(ToolsApplication.getContext(), MyJobService.CONST_JOB_ID_SOUND_PLAY, Bundle().apply {
				AlertDialog.Builder(this@MP3Activity).apply {
					val allItems = (1..6).asSequence().map { "sound/sound$it.wav" }.toList().toTypedArray()
					setSingleChoiceItems(allItems, 0) { dialog, index ->
						dialog.dismiss()
						
						JobScheduleHelper.scheduleJobInfo(ToolsApplication.getContext(), MyJobService.CONST_JOB_ID_SOUND_PLAY, Bundle().apply {
							putString(MyJobService.CONST_JOB_ID_NET_SOUND_FILE, allItems[index])
							putInt(MyJobService.CONST_JOB_ID_NET_SOUND_LOOPS, -1)
						})
					}
				}.show()
			})
		}
		
		stop.setOnClickListener {
			JobScheduleHelper.scheduleJobInfo(ToolsApplication.getContext(), MyJobService.CONST_JOB_ID_SOUND_STOP)
		}
		
		release.setOnClickListener {
			JobScheduleHelper.scheduleJobInfo(ToolsApplication.getContext(), MyJobService.CONST_JOB_ID_SOUND_RELEASE)
		}

		scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
		
	}
	
	override fun onBackPressed() {
		JobScheduleHelper.scheduleJobInfo(ToolsApplication.getContext(), MyJobService.CONST_JOB_ID_SOUND_RELEASE)
		super.onBackPressed()
	}
}