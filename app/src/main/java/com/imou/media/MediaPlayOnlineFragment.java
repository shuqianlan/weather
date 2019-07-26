/*  
 * 项目名: YYS 
 * 文件名: MediaPlayOnlineFragment.java  
 * 版权声明:
 *      本系统的所有内容，包括源码、页面设计，文字、图像以及其他任何信息，
 *      如未经特殊说明，其版权均属大华技术股份有限公司所有。
 *      Copyright (c) 2015 大华技术股份有限公司
 *      版权所有
 */
package com.imou.media;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.ilifesmart.ui.EmbeddedCompassView;
import com.ilifesmart.weather.R;
import com.imou.*;
import com.imou.json.LeChengResponse;
import com.imou.util.MediaPlayHelper;
import com.lechange.opensdk.listener.LCOpenSDK_EventListener;
import com.lechange.opensdk.listener.LCOpenSDK_TalkerListener;
import com.lechange.opensdk.media.LCOpenSDK_Talk;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import java.io.*;

/**
 * 描述：实时视频监控 作者： lc
 */
public class MediaPlayOnlineFragment extends MediaPlayFragment implements
		OnClickListener {

	private static final String TAG = "MediaPlayOnlineFragment";

	protected static final int MediaMain = 0; // 主码流
	protected static final int MediaAssist = 1; // 辅码流
	protected static final int RECORDER_TYPE_DAV = 3; // 录制格式DAV
	protected static final int RECORDER_TYPE_MP4 = 4; // 录制格式MP4

	public enum Cloud {
		up, down, left, right, leftUp, rightUp, leftDown, RightDown, zoomin, zoomout, stop
	}

	// 状态值
	private int bateMode = MediaAssist;

	private enum AudioTalkStatus {
		talk_close, talk_opening, talk_open
	}

	private AudioTalkStatus mOpenTalk = AudioTalkStatus.talk_close; // 语音对讲状态
	private boolean isRecord = false; // 正在录制
	private boolean isOpenSound = false; // 声音打开
	private boolean isPlaying; // 正在播放
	private boolean IsPTZOpen = false; // 云台打开

	private ChannelInfo channelInfo;

	private LinearLayout mLiveMenu;
	private ImageView mLiveMode;
	private ImageView mLivePtz;
	private ImageView mLiveSound;
	private ImageView mLiveScale;

	private LinearLayout mLiveUseLayout;
	private ImageView mLiveScreenshot;
	private ImageView mLiveTalk;
	private ImageView mLiveRecord;
	private EmbeddedCompassView mCompossCtl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		if (b != null) {
			String channelId = b.getString("RESID");
			channelInfo = LeChengMomgr.getInstance().getChannelInfo(channelId);
		}
		if (channelInfo == null) {
			toast("设备不存在");
			getActivity().setResult(-1);
			getActivity().finish();
		}

		Log.d(TAG, "onCreate: channelInfo " + channelInfo);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mView = inflater.inflate(R.layout.fragment_media_live, container,
				false);
		// 必须赋值，父类需要使用到
		mSurfaceParentView = (ViewGroup) mView.findViewById(R.id.live_window);
		// 初始化窗口大小
		ConstraintLayout.LayoutParams mLayoutParams = (ConstraintLayout.LayoutParams) mSurfaceParentView
				.getLayoutParams();
		DisplayMetrics metric = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
		mLayoutParams.width = metric.widthPixels; // 屏幕宽度（像素）
		mLayoutParams.height = metric.widthPixels * 9 / 16;
		mLayoutParams.setMargins(0, 10, 0, 0);
		mSurfaceParentView.setLayoutParams(mLayoutParams);

		mPlayWin.initPlayWindow(this.getActivity(),
				(ViewGroup) mView.findViewById(R.id.live_window_content), 0);

		mProgressDialog = (ProgressBar) mView
				.findViewById(R.id.live_play_load);
		mReplayTip = (TextView) mView.findViewById(R.id.live_play_pressed);

		mLiveMenu = (LinearLayout) mView.findViewById(R.id.live_menu);
		mLiveMode = (ImageView) mView.findViewById(R.id.live_mode);
		mLivePtz = (ImageView) mView.findViewById(R.id.live_ptz);
		mLiveSound = (ImageView) mView.findViewById(R.id.live_sound);
		mLiveUseLayout = (LinearLayout) mView
				.findViewById(R.id.live_use_layout);
		mLiveScale = (ImageView) mView.findViewById(R.id.live_scale);
		mLiveScreenshot = (ImageView) mView.findViewById(R.id.live_screenshot);
		mLiveTalk = (ImageView) mView.findViewById(R.id.live_talk);
		mLiveRecord = (ImageView) mView.findViewById(R.id.live_record);
		mCompossCtl = mView.findViewById(R.id.compass);

		mReplayTip.setOnClickListener(this);
		mLiveMode.setOnClickListener(this);
		mLivePtz.setOnClickListener(this);
		mLiveSound.setOnClickListener(this);
		mLiveUseLayout.setOnClickListener(this);
		mLiveScale.setOnClickListener(this);
		mLiveScreenshot.setOnClickListener(this);
		mLiveTalk.setOnClickListener(this);
		mLiveRecord.setOnClickListener(this);

		mCompossCtl.setListener(new EmbeddedCompassView.OnAngleChangeListener() {
		    private long lastts = 0;
		    private double duration = 0.2; // 支持时间200ms

			@Override
			public void onAngleChanged(double angle, EmbeddedCompassView compass) {
				Log.d(TAG, "OnAngleChangeListener: angle " + angle);

				double h=0,v=0,z=1.0;
				String ORIENTATION = "";
//				if (angle <= 45 && angle >= 315) {
//					ORIENTATION = "RIGHT";
//					h = 5.0;
//				} else if (45 < angle && angle <= 135) {
//					ORIENTATION = "TOP";
//					v = 5.0;
//				} else if (135 < angle && angle <= 225) {
//					ORIENTATION = "LEFT";
//					h = -5;
//				} else if (225 < angle && angle < 315) {
//					v = -5;
//					ORIENTATION = "BOTTOM";
//				}
//
//				Log.d(TAG, "onAngleChanged: [h,v,z] = " + h + "," + v + "," + z + "Orentation: " + ORIENTATION);
//				Log.d(TAG, "onAngleChanged: channelInfo " + channelInfo);
//				Flowable flowable = RemoteRepository.getInstance().controlPTZ(LeChengMomgr.getInstance().getToken(), channelInfo.getDeviceCode(), String.valueOf(channelInfo.getIndex()), "move", h, v, z, "5");
//				RxBus.getInstance().doSubscribe(flowable).subscribe(new Consumer() {
//					@Override
//					public void accept(Object o) throws Exception {
//
//					}
//				}, new Consumer<Throwable>() {
//					@Override
//					public void accept(Throwable throwable) throws Exception {
//
//					}
//				});

				//0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-停止
				if (22.5 < angle && angle <= 67.5) {
					ORIENTATION = "6";
				} else if (67.5 < angle && angle <= 112.5) {
					ORIENTATION = "0";
				} else if (112.5 < angle && angle <= 157.5) {
					ORIENTATION = "4";
				} else if (157.5 < angle && angle <= 202.5) {
					ORIENTATION = "2";
				} else if (202.5 < angle && angle <= 247.5) {
					ORIENTATION = "5";
				} else if (247.5 < angle && angle <= 292.5) {
					ORIENTATION = "1";
				} else if (292.5 < angle && angle <= 337.5) {
					ORIENTATION = "7";
				} else if (337.5 < angle || angle <= 22.5) {
					ORIENTATION = "3";
				}

                if ((lastts + duration) < System.currentTimeMillis()/1000) {
                    Flowable flowable = RemoteRepository.getInstance().controlMovePTZ(LeChengMomgr.getInstance().getToken(), channelInfo.getDeviceCode(), String.valueOf(channelInfo.getIndex()), ORIENTATION);
                    RxBus.getInstance().doSubscribe(flowable).subscribe(new Consumer<LeChengResponse>() {
                        @Override
                        public void accept(LeChengResponse o) throws Exception {
                            lastts = System.currentTimeMillis()/1000;
                            LeChengUtils.isResponseOK(o);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            lastts = System.currentTimeMillis()/1000;
                        }
                    });
                }
			}

			@Override
			public void onTouchBegin() {
                Log.d(TAG, "onTouchBegin: -------要开始咯");
			}

			@Override
			public void onTouchEnd() {
                Log.d(TAG, "onTouchEnd: ---------我靠，GameOver");
//				Flowable flowable = RemoteRepository.getInstance().controlPTZ(LeChengMomgr.getInstance().getToken(), channelInfo.getDeviceCode(), String.valueOf(channelInfo.getIndex()), "move", 0, 0, 1, "5");
//				RxBus.getInstance().doSubscribe(flowable).subscribe(new Consumer() {
//					@Override
//					public void accept(Object o) throws Exception {
//
//					}
//				}, new Consumer<Throwable>() {
//					@Override
//					public void accept(Throwable throwable) throws Exception {
//
//					}
//				});

                Flowable flowable = RemoteRepository.getInstance().controlMovePTZ(LeChengMomgr.getInstance().getToken(), channelInfo.getDeviceCode(), String.valueOf(channelInfo.getIndex()), "10");
                RxBus.getInstance().doSubscribe(flowable).subscribe(new Consumer() {
					@Override
					public void accept(Object o) throws Exception {

					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
			}
		});
		resetViews(getResources().getConfiguration());

		return mView;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		resetViews(newConfig);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listener = new MyBaseWindowListener();
		mPlayWin.setWindowListener(listener);
		mPlayWin.openTouchListener();

		// 开启横竖屏切换
		startListener();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		play(0);
	}

	@Override
	public void onPause() {
		super.onPause();
		// 停止实时视频,无效资源号
		stop(-1);
		// 关闭语音对讲
		if (mOpenTalk == AudioTalkStatus.talk_opening) {
			stopTalk();
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		mPlayWin.uninitPlayWindow();// 销毁底层资源
	}

	@Override
	protected void resetViews(Configuration mConfiguration) {
		if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) { // 横屏
			mLiveUseLayout.setVisibility(View.GONE);
			mLiveScale.setTag("LANDSCAPE");
			mLiveScale.setImageResource(R.drawable.live_btn_smallscreen);

			mCompossCtl.setVisibility(View.GONE);

		} else if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
			mLiveUseLayout.setVisibility(View.VISIBLE);
			mLiveScale.setTag("PORTRAIT");
			mLiveScale.setImageResource(R.drawable.live_btn_fullscreen);

			mCompossCtl.setVisibility(View.VISIBLE);
		}
	}

	class MyBaseWindowListener extends LCOpenSDK_EventListener {
		@Override
		public void onPlayerResult(int index, String code, int type) {
			if (type == LeChengCameraWrapInfo.RESULT_SOURCE_OPENAPI) {
				if (mHander != null) {
					mHander.post(new Runnable() {
						public void run() {
							if (isAdded()) {
								stop(R.string.app_name);
							}
						}
					});
				}
			} else {
				if (code.equals(LeChengCameraWrapInfo.PlayerResultCode.STATE_PACKET_FRAME_ERROR)
						|| code.equals(LeChengCameraWrapInfo.PlayerResultCode.STATE_RTSP_TEARDOWN_ERROR)
						|| code.equals(LeChengCameraWrapInfo.PlayerResultCode.STATE_RTSP_AUTHORIZATION_FAIL)
						|| code.equals(LeChengCameraWrapInfo.PlayerResultCode.STATE_RTSP_KEY_MISMATCH)) {
					if (mHander != null) {
						mHander.post(new Runnable() {
							public void run() {
								if (isAdded()) {
									stop(R.string.app_name);
								}
							}
						});
					}
				}
			}
		}

		@Override
		public void onPlayBegan(int index) {
			// TODO Auto-generated method stub
			// 显示码率
			// if (mHander != null) {
			// mHander.post(MediaPlayOnlineFragment.this);
			// }
			isPlaying = true;
			// 建立码流,自动开启音频
			if (mHander != null) {
				mHander.post(new Runnable() {
					@Override
					public void run() {
						if (isAdded()) {
							// showLoading(R.string.video_monitor_data_cache);
							onClick(mLiveSound);
						}
					}
				});
			}
			// 关闭播放加载控件
			mProgressDialog.post(new Runnable() {
				@Override
				public void run() {
					mProgressDialog.setVisibility(View.GONE);
				}
			});
		}

		// public void onReceiveData(int len) {
		// // 流量统计
		// mTotalFlow += len;
		// }
		@Override
		public void onStreamCallback(int index, byte[] data, int len) {
			// Log.d(TAG, "LCOpenSDK_EventListener::onStreamCallback-size : " +
			// len);
			try {
				String path = Environment.getExternalStorageDirectory()
						.getPath() + "/streamCallback.ts";
				FileOutputStream fout = new FileOutputStream(path, true);
				fout.write(data);
				fout.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void onWindowLongPressBegin(int index, Direction dir, float dx,
                                           float dy) {
			Log.d(TAG, "onWindowLongPressBegin: ----------------------> ");
			sendCloudOrder(direction2Cloud(dir), true);
		}

		@Override
		public void onWindowLongPressEnd(int index) {
			sendCloudOrder(Cloud.stop, false);
		}

		// 电子缩放
		@Override
		public void onZooming(int index, float dScale) {
			if (IsPTZOpen == false) {
				mPlayWin.doScale(dScale);
			}

		}

		// 云台缩放
		@Override
		public void onZoomEnd(int index, ZoomType zoom) {
			Log.d(TAG, "onZoomEnd" + zoom);
			if (IsPTZOpen == false) {
				return;
			}
			// TODO Auto-generated method stub
			sendCloudOrder(zoom == ZoomType.ZOOM_IN ? Cloud.zoomin
					: Cloud.zoomout, false);
		}

		// 滑动开始
		@Override
		public boolean onSlipBegin(int index, Direction dir, float dx, float dy) {
			if (IsPTZOpen == false && mPlayWin.getScale() > 1) {
				Log.d(TAG, "onflingBegin ");
			}
			sendCloudOrder(direction2Cloud(dir), false);
			return true;
		}

		// 滑动中
		@Override
		public void onSlipping(int index, Direction dir, float prex,
                               float prey, float dx, float dy) {
			if (IsPTZOpen == false && mPlayWin.getScale() > 1) {
				Log.d(TAG, "onflingBegin onFlinging");
				mPlayWin.doTranslate(dx, dy);
			}
		}

		// 滑动结束
		@Override
		public void onSlipEnd(int index, Direction dir, float dx, float dy) {
			if (IsPTZOpen == false && mPlayWin.getScale() > 1) {
				Log.d(TAG, "onflingBegin onFlingEnd");
				return;
			}

			// sendCloudOrder(Cloud.stop, false);
		}

		private Cloud direction2Cloud(Direction dir) {
			Cloud cloud = null;
			switch (dir) {
			case Up:
				cloud = Cloud.up;
				break;
			case Down:
				cloud = Cloud.down;
				break;
			case Left:
				cloud = Cloud.left;
				break;
			case Right:
				cloud = Cloud.right;
				break;
			case Left_up:
				cloud = Cloud.leftUp;
				break;
			case Left_down:
				cloud = Cloud.leftDown;
				break;
			case Right_up:
				cloud = Cloud.rightUp;
				break;
			case Right_down:
				cloud = Cloud.RightDown;
				break;
			default:
				break;
			}
			return cloud;
		}

		public void onWindowDBClick(int index, float dx, float dy) {
			// TODO Auto-generated method stub
			switch (mLiveMenu.getVisibility()) {
			case View.GONE:
				mLiveMenu.setVisibility(View.VISIBLE);
				break;
			case View.VISIBLE:
				mLiveMenu.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		}

	}

	private void sendCloudOrder(final Cloud mCloud, final boolean longclick) {
		if (IsPTZOpen && isPlaying) {
			// super.sendCloudOrder(mCloud, longclick);
			Log.d(TAG, "-----is longclick:" + longclick);
			ChannelPTZInfo.Operation operation = ChannelPTZInfo.Operation.Move;
			ChannelPTZInfo.Direction direction = null;
			switch (mCloud) {
			case up:
				direction = ChannelPTZInfo.Direction.Up;
				Log.d(TAG, "-----Up");
				break;
			case down:
				direction = ChannelPTZInfo.Direction.Down;
				Log.d(TAG, "-----Down");
				break;
			case left:// 手势向左， 摄像头向右转
				direction = ChannelPTZInfo.Direction.Left;
				Log.d(TAG, "-----case left");
				break;
			case right:
				direction = ChannelPTZInfo.Direction.Right;
				Log.d(TAG, "-----case right");
				break;
			case zoomin:
				direction = ChannelPTZInfo.Direction.ZoomIn;
				Log.d(TAG, "-----ZoomIn");
				break;
			case zoomout:
				direction = ChannelPTZInfo.Direction.ZoomOut;
				Log.d(TAG, "-----ZoomOut");
				break;
			case stop:
				Log.d(TAG, "-----stop");
				operation = ChannelPTZInfo.Operation.Stop;
				// direction = ChannelPTZInfo.Direction.Down;
				break;
			default:
				break;
			}
			ChannelPTZInfo ptzInfo = new ChannelPTZInfo(operation, direction);
			ptzInfo.setDuration(ChannelPTZInfo.Duration.Short);
			if (longclick) {
				ptzInfo.setDuration(ChannelPTZInfo.Duration.Long);
			}

//			Business.getInstance().AsynControlPtz(channelInfo.getUuid(),
//					ptzInfo, new Handler() {
//						@Override
//						public void handleMessage(Message msg) {
//							if (!isAdded() || getActivity() == null) {
//								Log.d(TAG, "*******page not exits");
//								return;
//							}
//							// Log.d(TAG,
//							// "-----control callback what:"+msg.what);
//							RetObject retObject = (RetObject) msg.obj;
//							if (msg.what == 0) {
//								Log.d(TAG, "-----control result: true");
//							} else
//								toast(retObject.mMsg);
//						}
//					});
		}
	}

	/**
	 * 描述：开始播放
	 */
	public void play(int strRes) {
		// if (isPlaying) {
		stop(-1);
		// }
		if (strRes > 0) {
			showLoading(strRes);
		} else {
			showLoading(R.string.common_loading);
		}
		mPlayWin.playRtspReal(
				LeChengMomgr.getInstance().getToken(),
				channelInfo.getDeviceCode(),
				channelInfo.getEncryptKey() != null ? channelInfo
						.getEncryptKey() : channelInfo.getDeviceCode(),
				channelInfo.getIndex(), bateMode);
	}

	/**
	 * 描述：停止播放
	 */
	public void stop(final int res) {
		// 关闭播放加载控件
		mProgressDialog.post(new Runnable() {
			@Override
			public void run() {
				mProgressDialog.setVisibility(View.GONE);
			}
		});

		if (isRecord) {
			stopRecord();// 关闭录像
		}

		if (isOpenSound) {
			closeAudio();// 关闭音频
			isOpenSound = false;
			mLiveSound.setImageResource(R.drawable.live_btn_sound_off);
		}
		mPlayWin.stopRtspReal();// 关闭视频
		isPlaying = false;

		if (mHander != null) {
			mHander.post(new Runnable() {
				public void run() {
					if (isAdded()) {
						if (res > 0)
							showErrorTip(res);
					}
				}
			});
		}
	}

	/**
	 * 描述：抓拍图像
	 */
	public String capture() {
		String captureFilePath = null;
		// 判断SD卡是否已存在
		// SD卡容量检查
		// FIXME 检查设备是否在线
		// 抓图
		String channelName = null;
		if (channelInfo != null) {
			channelName = channelInfo.getName();
		} else {
			channelName = "";
		}

		// 去除通道中在目录中的非法字符
		channelName = channelName.replace("-", "");

		captureFilePath = MediaPlayHelper.getCaptureAndVideoPath(
				MediaPlayHelper.DHFilesType.DHImage, channelName);
		int ret = mPlayWin.snapShot(captureFilePath);
		if (ret == retOK) {
			// 扫描到相册中
			MediaScannerConnection.scanFile(getActivity(),
					new String[] { captureFilePath }, null, null);
			Toast.makeText(getActivity(),
					R.string.video_monitor_image_capture_success,
					Toast.LENGTH_SHORT).show();
		} else {
			captureFilePath = null;
			Toast.makeText(getActivity(),
					R.string.video_monitor_image_capture_failed,
					Toast.LENGTH_SHORT).show();
		}
		return captureFilePath;
	}

	/**
	 * 描述：开始录制
	 */
	String path = null;

	public boolean startRecord() {
		if (!isPlaying) {
			toast(R.string.video_monitor_video_record_failed);
			return false;
		}
		// 判断SD卡是否已存在
		// SD卡容量检查

		// FIXME 要做 ~~~~检查设备是否在线

		// 录像的路径
		String channelName = null;
		if (channelInfo != null) {
			channelName = channelInfo.getName();
		} else {
			channelName = "";
		}

		// 去除通道中在目录中的非法字符
		channelName = channelName.replace("-", "");

		path = MediaPlayHelper.getCaptureAndVideoPath(MediaPlayHelper.DHFilesType.DHVideo,
				channelName);
		int recordType = 1;
		long spaceRemain = 0x7FFFFFFF; //保留字段
		// 开始录制 1
		int ret = mPlayWin.startRecord(path, recordType, spaceRemain);
		if (ret != retOK) {
			toast(R.string.video_monitor_video_record_failed);
			return false;
		}

		return true;
	}

	/**
	 * 关闭录像
	 */
	public void stopRecord() {
		mPlayWin.stopRecord();
		isRecord = false;
		toast(getString(R.string.video_monitor_video_record_success));
		mLiveRecord.setImageResource(R.drawable.live_btn_record_nor);

		MediaScannerConnection.scanFile(getActivity(), new String[] { path },
				null, null);
	}

	/**
	 * 打开声音
	 */
	public boolean openAudio() {
		return mPlayWin.playAudio() == retOK;
	}

	/**
	 * 关闭声音
	 */
	public boolean closeAudio() {
		return mPlayWin.stopAudio() == retOK;
	}

	/**
	 * 描述：开始对讲
	 */
	public void startTalk() {
		if (!isPlaying) {
			toast(R.string.video_monitor_talk_open_error);
			return;
		}

		toastWithImg(getString(R.string.video_monitor_media_talk),
				R.drawable.live_pic_talkback);

		// 替换图片
		mLiveTalk.setImageResource(R.drawable.live_btn_talk_click);
		mOpenTalk = AudioTalkStatus.talk_opening;
		// 关闭扬声器 默认为关
		if (isOpenSound) {
			closeAudio();
			mLiveSound.setImageResource(R.drawable.live_btn_sound_off);
		}
		mLiveSound.setClickable(false);
		LCOpenSDK_Talk.setListener(new AudioTalkerListener());
		LCOpenSDK_Talk.playTalk(
				LeChengMomgr.getInstance().getToken(),
				channelInfo.getDeviceCode(),
				channelInfo.getEncryptKey() != null ? channelInfo
						.getEncryptKey() : channelInfo.getDeviceCode());
	}

	/**
	 * 描述：停止对讲
	 */
	public void stopTalk() {
		Log.d("playAudio","stopTalk press");
		toast(R.string.video_monitor_media_talk_close);
		// 替换图片
		mLiveTalk.setImageResource(R.drawable.live_btn_talk_nor);
		LCOpenSDK_Talk.stopTalk();
		// 解决gc回收问题
		LCOpenSDK_Talk.setListener(null);
		mOpenTalk = AudioTalkStatus.talk_close;
		// 开启扬声器
		if (isOpenSound && isPlaying) {
			openAudio();
			mLiveSound.setImageResource(R.drawable.live_btn_sound_on);
		}
		mLiveSound.setClickable(true);
	}

	class AudioTalkerListener extends LCOpenSDK_TalkerListener {
		/**
		 * 描述：对讲状态获取
		 */
		@Override
		public void onTalkResult(String error, int type) {
			// TODO Auto-generated method stub
			if (type == LeChengCameraWrapInfo.RESULT_SOURCE_OPENAPI
					|| error.equals(AUDIO_TALK_ERROR)
					|| error.equals(LeChengCameraWrapInfo.PlayerResultCode.STATE_PACKET_FRAME_ERROR)
					|| error.equals(LeChengCameraWrapInfo.PlayerResultCode.STATE_RTSP_TEARDOWN_ERROR)
					|| error.equals(LeChengCameraWrapInfo.PlayerResultCode.STATE_RTSP_AUTHORIZATION_FAIL)) {
				if (mHander != null) {
					mHander.post(new Runnable() {
						public void run() {
							if (isAdded()) {
								// 提示对讲打开失败
								toast(R.string.video_monitor_talk_open_error);
								stopTalk();// 关闭播放
							}
						}
					});
				}
			} else if (error.equals(LeChengCameraWrapInfo.PlayerResultCode.STATE_RTSP_PLAY_READY)) {
				if (mHander != null) {
					mHander.post(new Runnable() {
						public void run() {
							if (isAdded()) {
								// 提示对讲打开成功
								toast(R.string.video_monitor_media_talk_ready);
							}
						}
					});
				}
				mOpenTalk = AudioTalkStatus.talk_open;
			}

		}

		@Override
		public void onTalkPlayReady() {
			// TODO Auto-generated method stub

		}
		
	    @Override
        public void onAudioRecord(byte[] pData, int length, int audioFormat,int sampleRate, int sampleDepth) {
	            super.onAudioRecord(pData, length, audioFormat, sampleRate, sampleDepth);
	            BufferedOutputStream bufferedOutputStream;
	            FileOutputStream fileOutputStream;
	            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudioFile.pcm";
	            File file = new File(filePath);
	            if (file.exists()) {
	                try {
	                    fileOutputStream = new FileOutputStream(file, true);
	                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
	                    try {
	                        bufferedOutputStream.write(pData);
	                        bufferedOutputStream.flush();
	                        bufferedOutputStream.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                } catch (FileNotFoundException e) {
	                    e.printStackTrace();
	                } finally {

	                }
	            }else{
	                try {
	                    file.createNewFile();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	        @Override
	        public void onAudioReceive(byte[] pData, int length, int audioFormat, int sampleRate, int sampleDepth) {
	            super.onAudioReceive(pData, length, audioFormat, sampleRate, sampleDepth);
	        }
	    
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.live_ptz:
			if(!isPlaying){
				return;
			}
			if (channelInfo.isSupportPTZOrPZ()) {
				if (IsPTZOpen) {
					// 测试专用
//					mPlayWin.setStreamCallback(0);
					IsPTZOpen = false;
					mLivePtz.setImageResource(R.drawable.live_btn_ptz_off);
				} else {
					// 测试专用
					mPlayWin.setStreamCallback(1);
					IsPTZOpen = true;
					mLivePtz.setImageResource(R.drawable.live_btn_ptz_on);
				}
			} else {
				toast(R.string.toast_device_ability_no_ptz);
			}
			break;
		case R.id.live_scale:
			if ("LANDSCAPE".equals(mLiveScale.getTag())) {
				mOrientation = ORIENTATION.isPortRait;
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} else {
				mOrientation = ORIENTATION.isLandScape;
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
			break;
		case R.id.live_mode:
			// if(isPlaying) //播放是个异步的,多次点击会使停止播放顺序乱掉
			if (mOpenTalk == AudioTalkStatus.talk_opening) {
				stopTalk();
			}
			// 高清切换到流畅
			if (bateMode == MediaMain) {
				bateMode = MediaAssist;
				mLiveMode.setImageResource(R.drawable.live_btn_fluent);
				play(R.string.video_monitor_change_stream_normal);
			}// 流畅切换到高清
			else if (bateMode == MediaAssist) {
				bateMode = MediaMain;
				mLiveMode.setImageResource(R.drawable.live_btn_hd);
				play(R.string.video_monitor_change_stream_hd);
			}
			break;
		case R.id.live_talk:
			switch (mOpenTalk) {
			case talk_open:
			case talk_opening:
				stopTalk();
				break;
			case talk_close:
				startTalk();
				break;
			default:
				break;
			}
			break;
		case R.id.live_sound:
			if (mOpenTalk != AudioTalkStatus.talk_close || !isPlaying) {
				toast(R.string.video_monitor_load_talk_sound_error);
			} else {
				if (isOpenSound) {
					boolean result = closeAudio();
					if (result) {
						mLiveSound
								.setImageResource(R.drawable.live_btn_sound_off);
						toast(R.string.video_monitor_sound_close);
						isOpenSound = false;
					}
				} else {
					boolean result = openAudio();
					if (result) {
						mLiveSound
								.setImageResource(R.drawable.live_btn_sound_on);
						toast(R.string.video_monitor_sound_open);
						isOpenSound = true;
					}
				}
			}
			break;
		case R.id.live_screenshot:
			mLiveScreenshot
					.setImageResource(R.drawable.live_btn_screenshot_click);
			capture();
			mLiveScreenshot
					.setImageResource(R.drawable.live_btn_screenshot_nor);
			break;
		case R.id.live_record:
			if (!isRecord) {
				boolean result = startRecord();
				if (result) {
					toastWithImg(
							getString(R.string.video_monitor_media_record),
							R.drawable.live_pic_record);
					isRecord = true;
					mLiveRecord
							.setImageResource(R.drawable.live_btn_record_click);
				}
			} else {
				stopRecord();
			}
			break;
		case R.id.live_play_pressed:
			play(-1);
			break;
		default:
			break;
		}
	}
}
