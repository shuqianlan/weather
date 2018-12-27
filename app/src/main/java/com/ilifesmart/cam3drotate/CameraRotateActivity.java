package com.ilifesmart.cam3drotate;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ilifesmart.ui.RoundCornerImageView;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraRotateActivity extends AppCompatActivity {

	@BindView(R.id.rotate_orgx)
	Button RotateOrgx;
	@BindView(R.id.rotate_x)
	Button RotateX;
	@BindView(R.id.rotate_orgy)
	Button RotateOrgy;
	@BindView(R.id.rotate_y)
	Button RotateY;
	@BindView(R.id.rotate_xy)
	Button RotateXy;
	@BindView(R.id.rotate_z)
	Button RotateZ;
	@BindView(R.id.frame_cont)
	LinearLayout mFrameCont;
	@BindView(R.id.group_1)
	ConstraintLayout mCont1;
	@BindView(R.id.group_2)
	ConstraintLayout mCont2;
	@BindView(R.id.group_3)
	ConstraintLayout mCont3;
	@BindView(R.id.group_4)
	ConstraintLayout mCont4;

	private final List<RoundCornerImageView> mImgList = new ArrayList<RoundCornerImageView>();
	private final List<ConstraintLayout> mContList = new ArrayList<ConstraintLayout>();

	private Camera mCamera;
	private Matrix mCameramatrix;
	private Bitmap mBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_rotate);
		ButterKnife.bind(this);

		mContList.add(mCont1);
		mContList.add(mCont2);
		mContList.add(mCont3);
		mContList.add(mCont4);

		for(ConstraintLayout cont:mContList) {
			mImgList.add((RoundCornerImageView) cont.findViewById(R.id.img));
		}

		mCamera = new Camera();
		mCameramatrix = new Matrix();
//		mBitmap = ((BitmapDrawable)mImg.getDrawable()).getBitmap();
	}

	@OnClick(R.id.rotate_orgx)
	public void onRotateOrgxClicked() {
		mCamera.save();
		mCameramatrix.reset();
		mCamera.rotateY(45);
		mCamera.getMatrix(mCameramatrix);
		mCamera.restore();

		mCameramatrix.preTranslate(-mBitmap.getWidth() / 2, 0);
		mCameramatrix.postTranslate(mBitmap.getWidth() / 2, 0);
	}

	@OnClick(R.id.rotate_x)
	public void onRotateXClicked() {
//		mImg.setHoverColorAndDrawable(Color.WHITE, R.drawable.item_viewpager_120switch_1);
	}

	@OnClick(R.id.rotate_orgy)
	public void onRotateOrgyClicked() {
//		mImg.setHoverColorAndDrawable(Color.WHITE, R.drawable.item_viewpager_120switch_3);
	}

	@OnClick(R.id.rotate_y)
	public void onRotateYClicked() {
//		mImg.setHoverColorAndDrawable(Color.RED, -99);
	}

	@OnClick(R.id.rotate_xy)
	public void onRotateXyClicked() {
	}

	@OnClick(R.id.rotate_z)
	public void onRotateZClicked() {
	}

	@Override
	protected void onResume() {
		super.onResume();

		mFrameCont.setVisibility(View.VISIBLE);

		for (int i = 0; i < mImgList.size(); i++) {
			mImgList.get(i).setImageDrawable(getDrawable(R.drawable.item_viewpager_120switch_1));
		}
//		mImg.setHoverColorAndDrawable(Color.WHITE, R.drawable.colorlight3);
	}
}
