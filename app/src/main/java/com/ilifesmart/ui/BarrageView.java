package com.ilifesmart.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.ilifesmart.model.BarrageText;
import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BarrageView extends CustomSurfaceView {

	private List<BarrageText> barrages = Collections.synchronizedList(new ArrayList<>());

	private final static int BARRAGE_SPEED = 6;
	private final static int BARRAGE_MAGIN = 4;
	private final static int MAX_FONT_SIZE = 22;
	private final static int MIN_FONT_SIZE = 16;

	private int frequency = 30;
	private Random mRandom;
	private TextPaint mTextPaint;
	private int mContWidth, mContHeight;
	private boolean isBarrageClosed;

	private static final List<Integer> colors = new ArrayList<>();
	static {
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.BLACK);
		colors.add(Color.DKGRAY);
		colors.add(Color.GRAY);
		colors.add(Color.LTGRAY);
		colors.add(Color.RED);
		colors.add(Color.CYAN);
		colors.add(Color.MAGENTA);
	}

	private static final List<String> barrageTexts = new ArrayList<>();
	static {
		barrageTexts.add("前方高能!!!");
		barrageTexts.add("一本正经地胡说八道");
		barrageTexts.add("少奶奶威武霸气，超神");
		barrageTexts.add("五杀");
		barrageTexts.add("火钳刘明");
		barrageTexts.add("好嗨哟，感觉人生到达了巅峰");
		barrageTexts.add("厉害了我的国");
		barrageTexts.add("坐北朝南，向阳花开");
		barrageTexts.add("天不生夫子，万古如长夜");
		barrageTexts.add("天不生我李淳罡，剑道万古如长夜, 剑来!");
		barrageTexts.add("剑气六千里，敬老黄");
		barrageTexts.add("小二，上酒");
		barrageTexts.add("大秦，洛阳!");
		barrageTexts.add("金钟罩，铁布衫儿~");
		barrageTexts.add("心疼沙溢，心疼沙溢，心疼沙溢!");
		barrageTexts.add("愿天下剑客人人皆可剑开天门");
		barrageTexts.add("房价涨啦，房价涨啦，房价涨啦，房价涨啦，房价涨啦，房价涨啦，完蛋鸟🐦");
		barrageTexts.add("书籍推荐:剑来，将夜，雪中悍刀行，飞升之后，三体，狼群，一枪爆头，颈椎病康复指南");
	}

	public BarrageView(Context context) {
		this(context,null);
	}

	public BarrageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initialize(attrs);
	}

	private void initialize(AttributeSet attrs) {
		mRandom = new Random();
		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		setZOrderOnTop(true);
		mHolder.setFormat(PixelFormat.TRANSPARENT);

		if (attrs != null) {
			TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.BarrageView);
			frequency = a.getInt(R.styleable.BarrageView_frequency, frequency);
			a.recycle();
		}
	}

	@Override
	public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		super.onSurfaceChanged(holder, format, width, height);

		mContWidth = width;
		mContHeight = height - getPaddingTop() - getPaddingBottom();
	}

	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

		if (!isBarrageClosed) {
			for (int i = 0; i < barrages.size(); i++) {
				BarrageText bean = barrages.get(i);
				int textWidth = (int) paint.measureText(bean.getText());
				if ((bean.getX()+textWidth+BARRAGE_SPEED) < 0) {
					barrages.remove(i);
					continue;
				}

				paint.setColor(bean.getColor());
				paint.setTextAlign(Paint.Align.LEFT);
				paint.setTextSize(bean.getTextSize());
				canvas.drawText(bean.getText(), bean.getX(), bean.getY(), paint);

				bean.setX(bean.getX()-bean.getSpeed()); // 刷新位置
			}
		}

		try {
			Thread.sleep(frequency);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setBarrageClosed(boolean closed) {
		this.isBarrageClosed = closed;
	}

	public void sendBarrage() {
		String text = barrageTexts.get(mRandom.nextInt(barrageTexts.size()));
		sendBarrage(text);
	}

	public void sendBarrage(String text) {
		int size = DensityUtils.sp2px(mContext, Math.max(MIN_FONT_SIZE, mRandom.nextInt(MAX_FONT_SIZE)));
		int y = (int) (mRandom.nextDouble()*mContHeight);
		int speed = (int) (mRandom.nextDouble()*BARRAGE_SPEED);
		int color = colors.get(mRandom.nextInt(colors.size()));

		mTextPaint.setTextSize(size);
		Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();

		int halfHeight = (int)(fontMetrics.bottom - fontMetrics.top)/2 + BARRAGE_MAGIN;
		if (y < halfHeight) {
			y = halfHeight;
		} else if (y > (mContHeight-halfHeight)) {
			y = mContHeight-halfHeight;
		}
		barrages.add(new BarrageText().setText(text).setX(mContWidth).setSpeed(speed).setTextColor(color).setY(y).setTextSize(size));
	}

}
