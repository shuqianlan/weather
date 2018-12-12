package com.ilifesmart.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;

/**
 * Created by fanxq on 17/1/18.
 */

public abstract class EmbeddedViewContainer extends FrameLayout {
	public EmbeddedViewContainer(Context context) {
		super(context);
	}

	public EmbeddedViewContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EmbeddedViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	protected abstract EmbeddedView[] getAllEmbeddedViews();

	public int getEmbeddedViewCount() {
		return getAllEmbeddedViews().length;
	}

	public EmbeddedView findView(Class<? extends EmbeddedView> clazz) {
		if (clazz == null)
			return null;
		EmbeddedView[] views = getAllEmbeddedViews();
		for (EmbeddedView view : views) {
			if (clazz.isInstance(view))
				return view;
		}
		return null;
	}

	public EmbeddedView findView(String identifier) {
		if (identifier == null)
			return null;

		EmbeddedView[] views = getAllEmbeddedViews();
		for (EmbeddedView view : views) {
			if (view.getIdentifier() != null && view.getIdentifier().equals(identifier))
				return view;
		}
		return null;
	}

	public <T> T findUIView(Class<T> clazz, String identifier) {
		if (clazz == null)
			return null;
		EmbeddedView[] views = getAllEmbeddedViews();
		for (EmbeddedView view : views) {
			if (clazz.isInstance(view.getUIView()) && (identifier == null || identifier.equals(view.getIdentifier())))
				return (T) view.getUIView();
		}
		return null;
	}


	public boolean containsView(EmbeddedView targetView) {
		EmbeddedView[] views = getAllEmbeddedViews();
		for (EmbeddedView view : views) {
			if (view.equals(targetView))
				return true;
		}
		return false;
	}

	public void destroy() {
		EmbeddedView[] views = getAllEmbeddedViews();
		for(EmbeddedView view : views) {
			view.onDeactivated(this);
			view.onUnbound(this);
			this.removeView(view.getUIView());
		}
	}
}
