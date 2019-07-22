package com.ilifesmart.barrage;

import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.ilifesmart.ui.SurfaceVideoView;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

	public static final String TAG = "VideoActivity";

	@BindView(R.id.send)
	Button mSend;
	@BindView(R.id.frame_cont)
	SurfaceVideoView mVideoCont;
	@BindView(R.id.barrage_switch)
	CheckBox mBarrageSwitch;
	@BindView(R.id.barrage_content)
	EditText mBarrageContent;

	private final static String filePath = "video/Rise_of_Ascended.mp4";

	public static List<String> videos = new ArrayList<>();
	static {
		videos.add(filePath);
		videos.add(filePath);
		videos.add(filePath);
		videos.add(filePath);
		videos.add(filePath);
		videos.add(filePath);
		videos.add(filePath);
		videos.add(filePath);
	}
	@BindView(R.id.videos)
	ListView mVideos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barrage);
		ButterKnife.bind(this);

		mVideoCont.setVideoPath("http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8");

		VideoAdapter adapter = new VideoAdapter();
		adapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onInvalidated() {
				super.onInvalidated();
			}
		});
		mVideos.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	@OnClick({R.id.speed_1, R.id.speed_2, R.id.speed_3, R.id.speed_4, R.id.send})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.speed_1:
				mVideoCont.setVideoSpeed(0.5f);
				break;
			case R.id.speed_2:
				mVideoCont.setVideoSpeed(1.0f);
				break;
			case R.id.speed_3:
				mVideoCont.setVideoSpeed(1.5f);
				break;
			case R.id.speed_4:
				mVideoCont.setVideoSpeed(2.0f);
				break;
			case R.id.send:
				String barrage = mBarrageContent.getText().toString();
				if (TextUtils.isEmpty(barrage)) {
					mVideoCont.sendBarrage();
				} else {
					mVideoCont.sendBarrage(barrage);
				}
				mBarrageContent.setText("");
				break;
		}
	}

	@OnCheckedChanged(R.id.barrage_switch)
	public void OnChecked(CompoundButton button, boolean opened) {
		mVideoCont.setBarrageClosed(!opened);
	}

	public class VideoItem  {
		private SurfaceVideoView view;

		public VideoItem(@NonNull View itemView) {
			view = (SurfaceVideoView) itemView;
		}

		public void onBind(String video) {
			view.setVideoPath(video);
		}
	}

	public class VideoAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return videos.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SurfaceVideoView v;
			if (convertView == null) {
				v = new SurfaceVideoView(parent.getContext());
			} else {
				v= (SurfaceVideoView) convertView;
			}
			v.setVideoPath(videos.get(position));
			Log.d(TAG, "getView: setVideoPath video " + videos.get(position));
			return v;
		}

	}
}
