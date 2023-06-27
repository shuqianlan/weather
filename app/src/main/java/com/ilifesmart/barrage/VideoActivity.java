package com.ilifesmart.barrage;

import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.ilifesmart.ui.SurfaceVideoView;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

	public static final String TAG = "VideoActivity";

	Button mSend;
	SurfaceVideoView mVideoCont;
	CheckBox mBarrageSwitch;
	EditText mBarrageContent;

	private final static String filePath = "video/Rise_of_Ascended.mp4";

	public static List<String> videos = new ArrayList<>();
	static {
//		videos.add(filePath);
//		videos.add(filePath);
//		videos.add(filePath);
//		videos.add(filePath);
//		videos.add(filePath);
//		videos.add(filePath);
//		videos.add(filePath);
//		videos.add(filePath);
	}
	ListView mVideos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barrage);
		mSend = findViewById(R.id.send);
		mVideoCont = findViewById(R.id.frame_cont);
		mBarrageSwitch = findViewById(R.id.barrage_switch);
		mBarrageContent = findViewById(R.id.barrage_content);
		mVideos = findViewById(R.id.videos);

		mVideoCont.setVideoPath(filePath);

		VideoAdapter adapter = new VideoAdapter();
		adapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onInvalidated() {
				super.onInvalidated();
			}
		});
		mVideos.setAdapter(adapter);

		mBarrageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				OnChecked(buttonView, isChecked);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	public void onViewClicked(View view) {
		if (view.getId() == R.id.speed_1) {
			mVideoCont.setVideoSpeed(0.5f);
		} else if (view.getId() == R.id.speed_2) {
			mVideoCont.setVideoSpeed(1.0f);
		} else if (view.getId() == R.id.speed_3) {
			mVideoCont.setVideoSpeed(1.5f);
		} else if (view.getId() == R.id.speed_4) {
			mVideoCont.setVideoSpeed(2.0f);
		} else if (view.getId() == R.id.send) {
			String barrage = mBarrageContent.getText().toString();
			if (TextUtils.isEmpty(barrage)) {
				mVideoCont.sendBarrage();
			} else {
				mVideoCont.sendBarrage(barrage);
			}
			mBarrageContent.setText("");
		}
	}

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
