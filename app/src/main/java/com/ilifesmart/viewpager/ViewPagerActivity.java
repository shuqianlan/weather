package com.ilifesmart.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ilifesmart.ui.QualCircle;
import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerActivity extends AppCompatActivity {

	public static final String TAG = "ViewPagerActivity";
	@BindView(R.id.viewpager)
	ViewPager mViewpager;
	@BindView(R.id.linear_cont)
	LinearLayout mLinearCont;
	private final List<View> mViews = new ArrayList<>();
	private final List<View> mPointDots = new ArrayList<>();
	@BindView(R.id.cont)
	ConstraintLayout mCont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		ButterKnife.bind(this);

		initialize();
		initPageAdapter();
		initPositionPointer();
	}

	private void initialize() {
		mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i1) {
				Log.d(TAG, "onPageScrolled: i " + i);
				Log.d(TAG, "onPageScrolled: v " + v);
				Log.d(TAG, "onPageScrolled: i1 " + i1);
			}

			@Override
			public void onPageSelected(int i) {
				Log.d(TAG, "onPageSelected: index " + i);
				Log.d(TAG, "onPageSelected: view " + mPointDots.get(i).getTag());
			}

			@Override
			public void onPageScrollStateChanged(int i) {
				Log.d(TAG, "onPageScrollStateChanged: i " + i);
			}
		});
	}

	private void initPageAdapter() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.viewpager_view_item, null);
		((ImageView) v.findViewById(R.id.image)).setImageDrawable(getDrawable(R.drawable.item_viewpager_120switch_1));
		mViews.add(v);
		v = inflater.inflate(R.layout.viewpager_view_item, null);
		((ImageView) v.findViewById(R.id.image)).setImageDrawable(getDrawable(R.drawable.item_viewpager_120switch_2));
		mViews.add(v);
		v = inflater.inflate(R.layout.viewpager_view_item, null);
		((ImageView) v.findViewById(R.id.image)).setImageDrawable(getDrawable(R.drawable.item_viewpager_120switch_3));
		mViews.add(v);

		PagerAdapter adapter = new PagerAdapter() {
			@Override
			public int getCount() {
				return mViews.size();
			}

			@Override
			public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
				return view == o;
			}

			@Override
			public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
				container.removeView(mViews.get(position));
			}

			@NonNull
			@Override
			public Object instantiateItem(@NonNull ViewGroup container, int position) {
				View v = mViews.get(position);
				container.addView(v);
				return v;
			}
		};

		mViewpager.setAdapter(adapter);
	}

	private void initPositionPointer() {
		Log.d(TAG, "initPositionPointer: mView.sizes " + mViews.size());
		int[] colors = new int[]{Color.RED, Color.GREEN, Color.DKGRAY};
		for (int i = 0; i < mViews.size(); i++) {
			View v = new View(this);
			v.setBackground(getDrawable(R.drawable.shape_circle_background));
			v.setTag(i);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtils.dp2px(this, 20), DensityUtils.dp2px(this, 20));
			v.setPadding(20, 0, (i + 1) == mViews.size() ? 20 : 0, 0);
			v.setElevation(DensityUtils.dp2px(this, 6));
			v.setLayoutParams(lp);
			mPointDots.add(v);
			mLinearCont.addView(v);
		}

		mPointDots.get(0).post(new Runnable() {
			@Override
			public void run() {
				QualCircle qualCircle = new QualCircle(ViewPagerActivity.this);
				ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(ViewPagerActivity.this, 20));
				qualCircle.setLayoutParams(lp);
				qualCircle.setTranslationX(mPointDots.get(0).getLeft()-mPointDots.get(0).getWidth()/2);
				qualCircle.setTranslationY(mLinearCont.getTop() + mLinearCont.getHeight() / 2 - mPointDots.get(0).getHeight()/2);
				qualCircle.setDistanceOffsexX(mPointDots.get(0).getWidth());
				qualCircle.setElevation(DensityUtils.dp2px(ViewPagerActivity.this, 10));
				mCont.addView(qualCircle);
			}
		});

	}
}
