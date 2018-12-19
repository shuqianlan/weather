package com.ilifesmart.cam3drotate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraRotateActivity extends AppCompatActivity {

	@BindView(R.id.img)
	ImageView mImg;
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

	private Camera mCamera;
	private Matrix mCameramatrix;
	private Bitmap mBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_rotate);
		ButterKnife.bind(this);

		mCamera = new Camera();
		mCameramatrix = new Matrix();
		mBitmap = ((BitmapDrawable)mImg.getDrawable()).getBitmap();
	}

	@OnClick(R.id.rotate_orgx)
	public void onRotateOrgxClicked() {
		mCamera.save();
		mCameramatrix.reset();
		mCamera.rotateY(45);
		mCamera.getMatrix(mCameramatrix);
		mCamera.restore();

		mCameramatrix.preTranslate(-mBitmap.getWidth()/2, 0);
		mCameramatrix.postTranslate(mBitmap.getWidth()/2, 0);
	}

	@OnClick(R.id.rotate_x)
	public void onRotateXClicked() {
	}

	@OnClick(R.id.rotate_orgy)
	public void onRotateOrgyClicked() {
	}

	@OnClick(R.id.rotate_y)
	public void onRotateYClicked() {
	}

	@OnClick(R.id.rotate_xy)
	public void onRotateXyClicked() {
	}

	@OnClick(R.id.rotate_z)
	public void onRotateZClicked() {
	}
}
