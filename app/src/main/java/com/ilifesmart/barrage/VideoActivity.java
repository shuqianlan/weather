package com.ilifesmart.barrage;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import com.ilifesmart.ui.SurfaceVideoView;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class VideoActivity extends AppCompatActivity {

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

		mVideoCont.setVideoPath(filePath);
		mVideos.setAdapter(new VideoAdapter());
	}

	@Override
	protected void onDestroy() {
		mVideoCont.onDestroyed();
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
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SurfaceVideoView v = new SurfaceVideoView(parent.getContext());
			v.setVideoPath(videos.get(position));
			return v;
		}
	}
}
