package com.spannableText;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpannableActivity extends AppCompatActivity {

	@BindView(R.id.span_example)
	TextView mSpanExample;

	/*
	 * setSpan(obj,start,end,flag);
	 * obj:样式
	 * start: 字符串的起始位置
	 * end: 字符串的结束位置
	 * flag: 四中样式，是否包含收尾下标字符.
	 * */

	private static final String EXAMPLETEXT = "中华人民共和国";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spannable);
		ButterKnife.bind(this);

		mSpanExample.setText(EXAMPLETEXT);
	}

	@OnClick(R.id.foregroundColor)
	public void onMForegroundColorClicked() {
		SpannableString string = new SpannableString(EXAMPLETEXT);
		ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
		string.setSpan(span, 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	@OnClick(R.id.backgroundColor)
	public void onMBackgroundColorClicked() {
		BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.RED);
		SpannableString string = new SpannableString(EXAMPLETEXT);
		string.setSpan(colorSpan, 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	@OnClick(R.id.relativeSizeSpan)
	public void onMRelativeSizeSpanClicked() {
		RelativeSizeSpan sizeSpan_1 = new RelativeSizeSpan(1.0f);
		RelativeSizeSpan sizeSpan_2 = new RelativeSizeSpan(1.1f);
		RelativeSizeSpan sizeSpan_3 = new RelativeSizeSpan(1.2f);
		RelativeSizeSpan sizeSpan_4 = new RelativeSizeSpan(1.4f);
		RelativeSizeSpan sizeSpan_5 = new RelativeSizeSpan(1.2f);
		RelativeSizeSpan sizeSpan_6 = new RelativeSizeSpan(1.1f);
		RelativeSizeSpan sizeSpan_7 = new RelativeSizeSpan(1.0f);
		SpannableString string = new SpannableString(EXAMPLETEXT);
		string.setSpan(sizeSpan_1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		string.setSpan(sizeSpan_2, 1, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		string.setSpan(sizeSpan_3, 2, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		string.setSpan(sizeSpan_4, 3, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		string.setSpan(sizeSpan_5, 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		string.setSpan(sizeSpan_6, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		string.setSpan(sizeSpan_7, 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		mSpanExample.setText(string);
	}

	@OnClick(R.id.strikethroughSpan)
	public void onMStrikethroughSpanClicked() {
		StrikethroughSpan strikeSpan = new StrikethroughSpan();
		SpannableString string = new SpannableString(EXAMPLETEXT);
		string.setSpan(strikeSpan, 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	@OnClick(R.id.under_line)
	public void onMUnderLineClicked() {
		UnderlineSpan underlineSpanSpan = new UnderlineSpan();
		SpannableString string = new SpannableString(EXAMPLETEXT);
		string.setSpan(underlineSpanSpan, 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	@OnClick(R.id.super_script)
	public void onMSuperScriptClicked() {
		SpannableString string = new SpannableString(EXAMPLETEXT);
		SuperscriptSpan span = new SuperscriptSpan();
		string.setSpan(span, 4, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	@OnClick(R.id.sub_script)
	public void onMSubScriptClicked() {
		SpannableString string = new SpannableString(EXAMPLETEXT);
		SubscriptSpan span = new SubscriptSpan();
		string.setSpan(span, 4, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	@OnClick(R.id.style)
	public void onMStyleClicked() {
		SpannableString string = new SpannableString(EXAMPLETEXT);
		StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
		StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
		string.setSpan(boldSpan, 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		string.setSpan(italicSpan, 4, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	@OnClick(R.id.image)
	public void onMImageClicked() {
		Drawable drawable = getDrawable(R.drawable.lingdai);
		drawable.setBounds(0, 0, 40, 40);
		ImageSpan span = new ImageSpan(drawable);
		SpannableString string = new SpannableString(EXAMPLETEXT.concat("123"));
		string.setSpan(span, 1, 10, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	@OnClick(R.id.clickle_span)
	public void onViewClicked() {
		MyClickableSpan span = new MyClickableSpan(EXAMPLETEXT);
		SpannableString string = new SpannableString(EXAMPLETEXT);
		string.setSpan(span, 0, EXAMPLETEXT.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setMovementMethod(LinkMovementMethod.getInstance());
		mSpanExample.setText(string);
		mSpanExample.callOnClick();
	}

}
