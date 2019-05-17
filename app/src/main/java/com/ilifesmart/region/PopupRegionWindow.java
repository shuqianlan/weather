package com.ilifesmart.region;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupRegionWindow extends PopupWindow {

	/*
	* 知识点:
	* 1. window默认无焦点，setFocusable(true);
	* 2. 背景变暗/恢复正常，设置底Activity的alpha
	* 3. 当前contentView的位置 showAsDropDown
	* 4. Window的动画样式 setAnimationStyle(int style)
	* 5. 仿京东快递(VX小程序)
	* */

	public static final String TAG = "PopupRegionWindow";
	private Context mContext;

	@BindView(R.id.tablayout)
	TabLayout mTablayout;
	@BindView(R.id.recy_cont_1)
	RecyclerView mRecyCont1;
	@BindView(R.id.head_cont)
	ConstraintLayout mHeadCont;

	private List<RegionItem> regions = new ArrayList<>();

	private RegionAdapter mRegionAdapter;
	private String regionCode;

	private OnRegionSelectedListener mSelectedListener;
	private static final int MAX_TABS = 3;
	private int mWindowWidth;
	private int mWindowHeight;

	public PopupRegionWindow(Context context) {
		super(context);

		this.mContext = context;
		int[] ret = new int[2];
		DensityUtils.getWindowSize(context, ret);

		mWindowWidth = ret[0];
		mWindowHeight = (int) (0.6*ret[1]);

		setWidth(mWindowWidth);
		setHeight(mWindowHeight);
		setOutsideTouchable(false);
		setFocusable(true);
		setAnimationStyle(R.style.popupRegionAnimation);
		setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		View v = LayoutInflater.from(context).inflate(R.layout.popup_region_window, null, false);
		setContentView(v);

		ButterKnife.bind(this, v);
		initialize(context);
	}

	private void initialize(Context context) {
		mRegionAdapter = new RegionAdapter();
		mRecyCont1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
		mRecyCont1.setAdapter(mRegionAdapter);

		mTablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				ItemBean bean = (ItemBean) tab.getTag();
				notiDataChanged(tab.getPosition(), bean.category, bean.selected);
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				darkenBackground(1f);
			}
		});
	}

	private void darkenBackground(float alpha) {
		WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
		lp.alpha = alpha;
		((Activity)mContext).getWindow().setAttributes(lp);
	}

	public PopupRegionWindow setRegionCode() {
		setRegionCode(RegionMgr.TOPKEY);
		return this;
	}

	public PopupRegionWindow setRegionCode(String code) {
		if (TextUtils.isEmpty(code)) {
			code = RegionMgr.TOPKEY;
		}

		RegionItem bean = (code.equals(RegionMgr.TOPKEY)) ? null : RegionMgr.getInstance().getRegionItem(code);
		if (bean != null) {
			String cityCode = bean.getParent();
			RegionItem city_bean = RegionMgr.getInstance().getRegionItem(cityCode);
			RegionItem province_bean = RegionMgr.getInstance().getRegionItem(city_bean.getParent());

			newTab(province_bean.getName(), province_bean.getParent(), province_bean.getCode());
			newTab(city_bean.getName(), city_bean.getParent(), city_bean.getCode());
			newTab(bean.getName(), bean.getParent(), bean.getCode()).select();
		} else {
			newTab(R.string.pls_select, RegionMgr.TOPKEY).select();
		}

		return this;
	}

	public PopupRegionWindow setOnRegionSelectedListener(OnRegionSelectedListener listener) {
		this.mSelectedListener = listener;
		return this;
	}

	public void show() {
		if (mContext instanceof Activity) {
			ViewGroup group = ((Activity) mContext).findViewById(Window.ID_ANDROID_CONTENT);
			darkenBackground(0.6f);
			showAsDropDown(group, 0, -mWindowHeight, Gravity.START);
		}
	}

	private TabLayout.Tab newTab(String text, String tag) {
		return newTab(text, tag, null);
	}

	private TabLayout.Tab newTab(String text, String tag, String code) {
		TabLayout.Tab tab = mTablayout.newTab().setText(text).setTag(new ItemBean(tag, code));
		mTablayout.addTab(tab);
		return tab;
	}

	private TabLayout.Tab newTab(int text, String tag) {
		return newTab(mContext.getString(text), tag);
	}

	private String getTabBeanCode(TabLayout.Tab tab) {
		ItemBean bean = (ItemBean) tab.getTag();
		return bean.selected;
	}

	private String getTabBeanDispName(int index) {
		return getTabBeanDispName(mTablayout.getTabAt(index));
	}

	private String getTabBeanDispName(TabLayout.Tab tab) {
		if (tab == null) {
			return "";
		}
		RegionItem bean = RegionMgr.getInstance().getRegionItem(getTabBeanCode(tab));
		return (bean != null) ? bean.getDispName() : "";
	}

	private String getName() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < MAX_TABS; i++) {
			builder.append(getTabBeanDispName(i));
		}

		return builder.toString();
	}

	private void notiItemChanged(int index, String code, String name) {
		if (mTablayout.getTabAt(index-1) != null) {
			mTablayout.getTabAt(index-1).setText(name);
			((ItemBean)mTablayout.getTabAt(index-1).getTag()).selected = code;
		}

		for (int i = mTablayout.getTabCount()-1; i >= index ; i--) {
			mTablayout.removeTabAt(i);
		}

		TabLayout.Tab tab = null;
		if (index < MAX_TABS) {
			tab = newTab(R.string.pls_select, code);
		}

		if (tab == null) {
			return;
		}
		tab.select();
	}

	private void notiDataChanged(int index, String code, String currCode) {
		regionCode = currCode;
		regions = RegionMgr.getInstance().getRegions(index, code);
		mRegionAdapter.notifyDataSetChanged();
	}

	public final class RegionHolder extends RecyclerView.ViewHolder {
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
			mSelected.setVisibility((!TextUtils.isEmpty(regionCode) && regionCode.equals(getCode())) ? View.VISIBLE : View.INVISIBLE);
		}

		@OnClick(R.id.region_item)
		public void OnClickItem(View v) {
			mSelected.setVisibility(View.VISIBLE);
			notiItemChanged(getIndex(), getCode(), getName());
			if (bean.getIndex() == 2) {
				onDismiss(getCode());
			}
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

	private final class RegionAdapter extends RecyclerView.Adapter<RegionHolder> {

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

	private final class ItemBean {
		String category;
		String selected;

		public ItemBean(String category, String selected) {
			this.category = category;
			this.selected = selected;
		}
	}

	public interface OnRegionSelectedListener {
		void onRegionSelected(String code, String fullName);
	}

	@OnClick(R.id.head_cont)
	public void onClickCancel() {
		onDismiss(null);
	}

	private void onDismiss(String code) {
		if (!TextUtils.isEmpty(code)) {
			if (mSelectedListener != null) {
				mSelectedListener.onRegionSelected(code, getName());
			}
		}
		dismiss();
	}

}
