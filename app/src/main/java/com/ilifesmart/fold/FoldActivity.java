package com.ilifesmart.fold;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ilifesmart.model.FoldData;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoldActivity extends AppCompatActivity {

	private static final List<FoldData> mFoldData = new ArrayList<>();

	static {
		mFoldData.clear();

		List<FoldData> emptyData = new ArrayList<>();
		List<FoldData<String>> OneData = new ArrayList<>();
		OneData.add(new FoldData<>());

		List<FoldData<String>> TwoData = new ArrayList<>();
		TwoData.add(new FoldData<>());
		TwoData.add(new FoldData<>());

		List<FoldData<String>> ThreeData = new ArrayList<>();
		ThreeData.add(new FoldData<>());
		ThreeData.add(new FoldData<>());
		ThreeData.add(new FoldData<>());

		mFoldData.add(new FoldData().setTitle("测试1").setSelected(true).setMods(emptyData));
		mFoldData.add(new FoldData().setTitle("测试2").setSelected(false).setMods(emptyData));

		mFoldData.add(new FoldData().setTitle("测试3").setSelected(true).setMods(OneData));
		mFoldData.add(new FoldData().setTitle("测试4").setSelected(false).setMods(OneData));

		mFoldData.add(new FoldData().setTitle("测试5").setSelected(true).setMods(TwoData));
		mFoldData.add(new FoldData().setTitle("测试6").setSelected(false).setMods(TwoData));

		mFoldData.add(new FoldData().setTitle("测试7").setSelected(true).setMods(ThreeData));
		mFoldData.add(new FoldData().setTitle("测试8").setSelected(false).setMods(ThreeData));
	}

	@BindView(R.id.foldContainer)
	RecyclerView mFoldContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fold);
		ButterKnife.bind(this);

		FoldAdapter adapter = new FoldAdapter();
		mFoldContainer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mFoldContainer.setAdapter(adapter);
	}

	private class FoldAdapter extends RecyclerView.Adapter<FoldItem> {

		@NonNull
		@Override
		public FoldItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
			View v = LayoutInflater.from(FoldActivity.this).inflate(R.layout.layout_fold_item_3, viewGroup, false);
			return new FoldItem(v);
		}

		@Override
		public void onBindViewHolder(@NonNull FoldItem foldItem, int i) {
			foldItem.onBind(mFoldData.get(i));
		}

		@Override
		public int getItemCount() {
			return mFoldData.size();
		}
	}

	public class FoldItem extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

		@BindView(R.id.layout_item_0)
		LinearLayout mCont0;

		@BindView(R.id.layout_item_1)
		LinearLayout mCont1;

		@BindView(R.id.layout_item_2)
		LinearLayout mCont2;

		@BindView(R.id.layout_item_3)
		LinearLayout mCont3;
		private boolean isExpand;

		private FoldData mFoldData;
		public FoldItem(@NonNull View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			((CheckBox)mCont0.findViewById(R.id.fold_item_check_box)).setOnCheckedChangeListener(this);
			((CheckBox)mCont1.findViewById(R.id.fold_item_check_box)).setOnCheckedChangeListener(this);
			((CheckBox)mCont2.findViewById(R.id.fold_item_check_box)).setOnCheckedChangeListener(this);
			((CheckBox)mCont3.findViewById(R.id.fold_item_check_box)).setOnCheckedChangeListener(this);
		}

		public void onBind(FoldData<String> data) {
			mFoldData = data;
			updateUI(data, mCont0);
			switch(data.getMods().size()) {
				case 3:
					updateUI(data.getMods().get(2), mCont3);
					mCont3.setVisibility(View.VISIBLE);
					mCont3.setTag(data.getMods().get(2));
				case 2:
					updateUI(data.getMods().get(1), mCont2);
					mCont2.setTag(data.getMods().get(1));
					mCont2.setVisibility(View.VISIBLE);
				case 1:
					updateUI(data.getMods().get(0), mCont1);
					mCont1.setTag(data.getMods().get(0));
					mCont1.setVisibility(View.VISIBLE);

			}
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			buttonView.setChecked(isChecked);
		}

		private void updateUI(FoldData data, LinearLayout v) {
			((TextView)v.findViewById(R.id.fold_item_fold_title)).setText(data.getTitle());
			((TextView)v.findViewById(R.id.fold_item_fold_disp)).setText(data.getTitle());
			((CheckBox)v.findViewById(R.id.fold_item_check_box)).setChecked(data.isSelected());
		}

		@OnClick({R.id.layout_item_0,R.id.layout_item_1,R.id.layout_item_2,R.id.layout_item_3})
		public void onClick(View v) {
			CheckBox checkBox;
			int selected = 0;
			switch (v.getId()) {
				case R.id.layout_item_0:
					isExpand = !isExpand;
					mCont0.setSelected(isExpand);
					switch(mFoldData.getMods().size()) {
						case 3:
							mCont3.setVisibility(isExpand ? View.VISIBLE : View.GONE);
						case 2:
							mCont2.setVisibility(isExpand ? View.VISIBLE : View.GONE);
						case 1:
							mCont1.setVisibility(isExpand ? View.VISIBLE : View.GONE);
					}
					break;
				case R.id.layout_item_1:
					checkBox = v.findViewById(R.id.fold_item_check_box);
					setChecked(checkBox, !checkBox.isChecked());
					selected = 0;
					break;
				case R.id.layout_item_2:
					checkBox = v.findViewById(R.id.fold_item_check_box);
					setChecked(checkBox, !checkBox.isChecked());
					selected = 1;
					break;
				case R.id.layout_item_3:
					checkBox = v.findViewById(R.id.fold_item_check_box);
					setChecked(checkBox, !checkBox.isChecked());
					selected = 2;
					break;
			}

		}

		private void setChecked(CheckBox check, boolean checked) {
			check.setChecked(checked);
		}

	}

	public interface OnItemClickCallback {
		public void onItemClick(FoldData data);
	}
}
