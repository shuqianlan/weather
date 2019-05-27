package com.spannableText;

import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import com.ilifesmart.fold.FoldActivity;

public class MyClickableSpan extends ClickableSpan {

	private String content;

	public MyClickableSpan(String content) {
		this.content = content;
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		ds.setUnderlineText(false);
	}

	@Override
	public void onClick(View widget) {
		Log.d("ClickableSpan", "onClick: >>>>>>>>> ");
		widget.getContext().startActivity(new Intent(widget.getContext(), FoldActivity.class));
	}

}
