package com.ilifesmart.cam3drotate;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ilifesmart.ui.RoundCornerImageView;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;


public class CameraRotateActivity extends AppCompatActivity {

	Button RotateOrgx;
	Button RotateX;
	Button RotateOrgy;
	Button RotateY;
	Button RotateXy;
	Button RotateZ;
	LinearLayout mFrameCont;
	ConstraintLayout mCont1;
	ConstraintLayout mCont2;
	ConstraintLayout mCont3;
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

		RotateOrgx = findViewById(R.id.rotate_orgx);
		RotateX = findViewById(R.id.rotate_x);
		RotateY  = findViewById(R.id.rotate_y);
		RotateY = findViewById(R.id.rotate_orgy);
		RotateXy = findViewById(R.id.rotate_xy);
		RotateZ = findViewById(R.id.rotate_z);
		mFrameCont = findViewById(R.id.frame_cont);
		mCont1 = findViewById(R.id.group_1);
		mCont2 = findViewById(R.id.group_2);
		mCont3 = findViewById(R.id.group_3);
		mCont4 = findViewById(R.id.group_4);

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

		RotateOrgx.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRotateOrgxClicked();
			}
		});

		RotateX.setOnClickListener((v) -> {
			onRotateXClicked();
		});

		RotateY.setOnClickListener((v)-> {
			onRotateYClicked();
		});

		RotateXy.setOnClickListener((v)-> {
			onRotateXyClicked();
		});

		RotateZ.setOnClickListener((v)-> {
			onRotateZClicked();
		});

		RotateOrgy.setOnClickListener((v) -> {
			onRotateOrgyClicked();
		});
	}

	public void onRotateOrgxClicked() {
		mCamera.save();
		mCameramatrix.reset();
		mCamera.rotateY(45);
		mCamera.getMatrix(mCameramatrix);
		mCamera.restore();

		mCameramatrix.preTranslate(-mBitmap.getWidth() / 2, 0);
		mCameramatrix.postTranslate(mBitmap.getWidth() / 2, 0);
	}

	public void onRotateXClicked() {
//		mImg.setHoverColorAndDrawable(Color.WHITE, R.drawable.item_viewpager_120switch_1);
	}

	public void onRotateOrgyClicked() {
//		mImg.setHoverColorAndDrawable(Color.WHITE, R.drawable.item_viewpager_120switch_3);
	}

	public void onRotateYClicked() {
//		mImg.setHoverColorAndDrawable(Color.RED, -99);
	}

	public void onRotateXyClicked() {
	}

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
