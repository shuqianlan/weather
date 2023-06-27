package com.spannableText;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
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
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ilifesmart.weather.R;

public class SpannableActivity extends AppCompatActivity {

	TextView mSpanExample;

	/*
	 * setSpan(obj,play,end,flag);
	 * obj:样式
	 * play: 字符串的起始位置
	 * end: 字符串的结束位置
	 * flag: 四中样式，是否包含收尾下标字符.
	 * */

	private static final String EXAMPLETEXT = "中华人民共和国";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spannable);

		mSpanExample = findViewById(R.id.span_example);
		mSpanExample.setText(EXAMPLETEXT);
	}

	public void onMForegroundColorClicked(View c) {
		SpannableString string = new SpannableString(EXAMPLETEXT);
		ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
		string.setSpan(span, 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	public void onMBackgroundColorClicked(View v) {
		BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.RED);
		SpannableString string = new SpannableString(EXAMPLETEXT);
		string.setSpan(colorSpan, 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	public void onMRelativeSizeSpanClicked(View v) {
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

	public void onMStrikethroughSpanClicked() {
		StrikethroughSpan strikeSpan = new StrikethroughSpan();
		SpannableString string = new SpannableString(EXAMPLETEXT);
		string.setSpan(strikeSpan, 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	public void onMUnderLineClicked(View v) {
		UnderlineSpan underlineSpanSpan = new UnderlineSpan();
		SpannableString string = new SpannableString(EXAMPLETEXT);
		string.setSpan(underlineSpanSpan, 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	public void onMSuperScriptClicked(View v) {
		SpannableString string = new SpannableString(EXAMPLETEXT);
		SuperscriptSpan span = new SuperscriptSpan();
		string.setSpan(span, 4, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	public void onMSubScriptClicked(View v) {
		SpannableString string = new SpannableString(EXAMPLETEXT);
		SubscriptSpan span = new SubscriptSpan();
		string.setSpan(span, 4, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	public void onMStyleClicked(View v) {
		SpannableString string = new SpannableString(EXAMPLETEXT);
		StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
		StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
		string.setSpan(boldSpan, 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		string.setSpan(italicSpan, 4, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	public void onMImageClicked(View v) {
		Drawable drawable = getDrawable(R.drawable.stroke_background);
		drawable.setBounds(0, 0, 40, 40);
		ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
		SpannableString string = new SpannableString(EXAMPLETEXT.concat("123"));
		string.setSpan(span, 1, 10, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	public void onViewClicked(View v) {

		MyClickableSpan span = new MyClickableSpan(EXAMPLETEXT);
		SpannableString string = new SpannableString(EXAMPLETEXT);
		string.setSpan(span, 0, EXAMPLETEXT.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

		ForegroundColorSpan span2 = new ForegroundColorSpan(Color.BLUE);
		string.setSpan(span, 0, EXAMPLETEXT.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

		mSpanExample.setMovementMethod(LinkMovementMethod.getInstance());
		mSpanExample.setAutoLinkMask(Linkify.WEB_URLS);
		mSpanExample.setOnClickListener((view) -> {
			Log.d("Home", "onViewClicked: ?????????????????????? ");
		});
		mSpanExample.setText(string);
		mSpanExample.callOnClick();
	}

	public void onRoundRadiusClicked(View v) {
		RoundRadiusTagSpan span = new RoundRadiusTagSpan();
		SpannableString string = new SpannableString(EXAMPLETEXT);
		mSpanExample.setMovementMethod(LinkMovementMethod.getInstance());
		string.setSpan(span, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		mSpanExample.setText(string);
	}

	public void onSetHtml(View v) {

		String html_text = "<i><a href='https://gavinli369.github.io/'>我的博客</a></i>";
		mSpanExample.setMovementMethod(LinkMovementMethod.getInstance());
		mSpanExample.setText(Html.fromHtml(html_text));
	}

	public void onCancelHtml(View v) {
		mSpanExample.setText(EXAMPLETEXT);
		mSpanExample.setMovementMethod(null);
	}

	public void onEnableHtml(View v) {
		Log.d("Home", "onEnableHtml: do nothing.");
	}

}
