package com.services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

public class JobScheduleHelper {

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private static JobScheduler getJobScheduler(Context context) {
		return (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static void scheduleJobInfo(Context context, int jobId) {
		scheduleJobInfo(context, jobId, null);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static void scheduleJobInfo(Context context, int jobId, Bundle extras) {
		JobScheduler scheduler = getJobScheduler(context);
		JobInfo.Builder builder = new JobInfo.Builder(jobId, new ComponentName(context.getApplicationContext(), MyJobService.class));

		if (extras != null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				builder.setTransientExtras(extras);
			}
		}

		if (jobId == MyJobService.CONST_JOB_ID_NETCALL) {
			builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
		}

		// 兼容:低版本默认builder不可为空
		builder.setMinimumLatency(0L); // 延迟时间.

		scheduler.schedule(builder.build());
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static void clearAllJobs(Context context) {
		JobScheduler scheduler = getJobScheduler(context);
		scheduler.cancelAll();
	}
}
