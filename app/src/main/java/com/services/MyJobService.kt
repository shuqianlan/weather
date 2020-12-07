package com.services

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class MyJobService:JobService() {
	
	//
	companion object {
		val TAG = MyJobService::class.java.simpleName
		
		const val JobID_LOG = 1
		const val JobID_TOAST = 2
		const val JOBID_PRINT = 3
		const val JOBID_NET_ANY = 4
		const val JOBID_MP3_PLAY = 5
		const val JOBID_MP3_STOP = 6

		const val CONST_JOB_ID_NETCALL = 1234
		const val CONST_JOB_ID_SOUND_PLAY = 1235
		const val CONST_JOB_ID_SOUND_STOP = 1236
		const val CONST_JOB_ID_SOUND_RELEASE = 1237
		
		const val CONST_JOB_ID_NET_SOUND_FILE = "SOUND_FILE"
		const val CONST_JOB_ID_NET_SOUND_LOOPS = "SOUND_LOOPS"
		
		const val CONST_TOAST_MESSAGE_KEY = "TOAST_KEY"
		
		@RequiresApi(Build.VERSION_CODES.O)
		@Deprecated(message = "not supported")
		@JvmStatic
		fun getJobInfo(context: Context, jobId: Int, transientExtras: Bundle? = null):JobInfo {
			val componentName = ComponentName(context, MyJobService::class.java)
			return JobInfo.Builder(jobId, componentName).apply {
				if (jobId == JOBID_NET_ANY) {
					setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
				}
				transientExtras?.apply {
					setTransientExtras(transientExtras)
				}
				
			}.build()
		}
	}
	
	/*
	* return:
	*  true: 表示任务正要呗执行，任务执行完毕需调用jobFinished通知系统
	*  false: 表示任务已经执行完毕
	*
	* ：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：：
	*
	* 当系统受到取消请求时，则调用onStopJob取消正在等待执行的job。
	* 如果onStartJob已经返回false，那么9不会调用onStopJob
	* */
	@RequiresApi(Build.VERSION_CODES.O)
	override fun onStartJob(params: JobParameters): Boolean {
		if (params == null) {
			return false
		}
		
		when (params.jobId) {
			CONST_JOB_ID_SOUND_PLAY -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				playBackgroundSound(params)
			}
			CONST_JOB_ID_SOUND_STOP -> stopPlayBackgroundSound()
			CONST_JOB_ID_SOUND_RELEASE -> releasePlayBackgroundSound()
		}
		
		return false
	}
	
	/*
	* return
	*  false: 结束Job
	*  true:  基于重试机制来决定是否重新schedule
	* */
	override fun onStopJob(params: JobParameters): Boolean {
		return false
	}
	
	@RequiresApi(api = Build.VERSION_CODES.O)
	private fun playBackgroundSound(parameters: JobParameters) {
		if (parameters.transientExtras == null) {
			return
		}
		val soundFile = parameters.transientExtras.getString(CONST_JOB_ID_NET_SOUND_FILE, null)
		val numberOfLoops = parameters.transientExtras.getInt(CONST_JOB_ID_NET_SOUND_LOOPS, -1)
		MediaPlayerMgmt.getInstance().playAssetSoundFile(soundFile, numberOfLoops)
	}
	
	fun stopPlayBackgroundSound() {
		MediaPlayerMgmt.getInstance().stopPlayAudio()
	}
	
	fun releasePlayBackgroundSound() {
		MediaPlayerMgmt.getInstance().releaseMedia()
	}
}