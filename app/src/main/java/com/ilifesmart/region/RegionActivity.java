package com.ilifesmart.region;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegionActivity extends AppCompatActivity {

	@BindView(R.id.tablayout)
	TabLayout mTablayout;
	@BindView(R.id.recy_cont_1)
	RecyclerView mRecyCont1;

	private List<RegionItem> regions = new ArrayList<>();

	private RegionAdapter mRegionAdapter;
	private RegionHolder lastHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_region);
		ButterKnife.bind(this);

		mTablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				Log.d("holder", "onTabSelected: ---> ");
				notiDataChanged(tab.getPosition(), tab.getTag().toString());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		initialize();
	}

	private void initialize() {
		mRegionAdapter = new RegionAdapter();
		mRecyCont1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		regions = RegionMgr.getInstance(this).getRegions(0);
		mRecyCont1.setAdapter(mRegionAdapter);

		String regionCode = getIntent().getStringExtra(Intent.EXTRA_TEXT);
		if (TextUtils.isEmpty(regionCode)) {
			notiItemChanged();
		}
	}

	private TabLayout.Tab newTab(String text, String tag) {
		TabLayout.Tab tab = mTablayout.newTab().setText(text).setTag(tag);
		tab.select();
		mTablayout.addTab(tab);
		return tab;
	}

	private TabLayout.Tab newTab(int text, String tag) {
		return newTab(getString(text), tag);
	}

	private void notiItemChanged() {
		notiItemChanged(1, RegionMgr.TOPKEY, getString(R.string.pls_select));
	}

	private void notiItemChanged(int index, String code, String name) {
		if (mTablayout.getTabAt(index-1) != null) {
			mTablayout.getTabAt(index-1).setText(name);
		}

		for (int i = mTablayout.getTabCount(); i > index ; i--) {
			mTablayout.removeTabAt(i);
		}

		TabLayout.Tab tab = null;
		if (index < 3) {
			tab = newTab(R.string.pls_select, code);
		}

		if (tab == null) {
			return;
		}
		tab.select();
	}

	private void notiDataChanged(int index, String code) {
		regions = RegionMgr.getInstance(this).getRegions(index, code);
		mRegionAdapter.notifyDataSetChanged();
	}

	public class RegionHolder extends RecyclerView.ViewHolder {
		private RegionItem bean;

		@BindView(R.id.text)
		TextView mTextView;

		@BindView(R.id.selected)
		ImageView mSelected;

		public RegionHolder(@NonNull View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public void onBind(RegionItem bean) {
			this.bean = bean;
			mTextView.setText(bean.getName());
			mSelected.setVisibility(View.INVISIBLE);
		}

		@OnClick(R.id.region_item)
		public void OnClickItem(View v) {
			mSelected.setVisibility(View.VISIBLE);
			notiItemChanged(getIndex(), getCode(), getName());
		}

		public String getCode() {
			return bean.getCode();
		}

		public String getName() {
			return bean.getName();
		}

		public int getIndex() {
			return bean.getIndex()+1;
		}

	}

	private class RegionAdapter extends RecyclerView.Adapter<RegionHolder> {

		@NonNull
		@Override
		public RegionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
			View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_region_item, viewGroup, false);
			return new RegionHolder(v);
		}

		@Override
		public void onBindViewHolder(@NonNull RegionHolder regionHolder, int i) {
			regionHolder.onBind(regions.get(i));
		}

		@Override
		public int getItemCount() {
			return regions.size();
		}
	}
}
