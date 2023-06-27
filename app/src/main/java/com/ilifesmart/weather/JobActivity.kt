package com.ilifesmart.weather

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import com.services.MyJobService
import java.util.concurrent.atomic.AtomicInteger

class JobActivity : AppCompatActivity() {
	
	private lateinit var scheduler:JobScheduler
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_job)
		
		/*
		* 1. 构建JobInfo
		* 2. 通过schedule将JobInfos传送到JobScheduler
		* 3. 当条件满足时，系统将会执行应用内的JobService上的Job
		*
		* schedule失败原因
		* 1. 无效的参数
		* 2. 太多jobs
		* 3. 短时间schedule太多jobs
		*
		* 如果schedule一个同id的job，如果当前id的正在执行，那么其将停止
		* */
		scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
		
		findViewById<Button>(R.id.log).setOnClickListener {
			scheduleJobInfo(this, MyJobService.JobID_LOG)
		}
		
		findViewById<Button>(R.id.toast).setOnClickListener {
			scheduleJobInfo(this, MyJobService.JobID_TOAST, Bundle().apply {
				putString(MyJobService.CONST_TOAST_MESSAGE_KEY, "Toast here ..")
			})
		}
		
		findViewById<Button>(R.id.print).setOnClickListener {
			scheduleJobInfo(this, MyJobService.JOBID_PRINT)
		}
	}
	
	fun scheduleJobInfo(context:Context, jobId:Int, bundle:Bundle?=null) {
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			MyJobService.getJobInfo(context, jobId, bundle)?.apply {
				scheduler.schedule(this)
			}
		}
	}
	
	fun scheduleJobInfo(jobID:Int) {

		with(JobInfo.Builder(jobID, componentName)) {
//			setMinimumLatency(5000)
			setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//			setOverrideDeadline(10_000)
			setBackoffCriteria(1000L, JobInfo.BACKOFF_POLICY_LINEAR)
		}.build().apply {
		
		}
	}
}