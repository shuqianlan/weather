package com.ilifesmart.ui;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fanxq on 17/1/18.
 */

public class EmbeddedViewStackContainer extends EmbeddedViewContainer {
	protected List<EmbeddedView> viewList = Collections.synchronizedList(new ArrayList<EmbeddedView>());
	protected boolean singleViewMode = false;

	public EmbeddedViewStackContainer(Context context) {
		super(context);
	}

	public EmbeddedViewStackContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EmbeddedViewStackContainer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected EmbeddedView[] getAllEmbeddedViews() {
		return viewList.toArray(new EmbeddedView[0]);
	}

	@Override
	public boolean containsView(EmbeddedView view) {
		return viewList.contains(view);
	}

	public EmbeddedView getTopView() {
		synchronized (viewList) {
			if (viewList.isEmpty())
				return null;
			return viewList.get(viewList.size()-1);
		}
	}

	public void pushView(EmbeddedView view) {
		if (view == null)
			return;

		synchronized (viewList) {
			if (containsView(view))
				return;

			EmbeddedView currentTopView = getTopView();

			viewList.add(view);
			this.addView(view.getUIView());

			view.onBound(this);
			view.onActivated(this);
			if (currentTopView != null) {
				currentTopView.onDeactivated(this);
				if (singleViewMode)
					this.removeView(currentTopView.getUIView());
			}

			this.requestLayout();
		}
	}

	public void popView() {
		synchronized (viewList) {
			EmbeddedView topView = getTopView();
			if (topView == null)
				return;
			viewList.remove(topView);
			this.removeView(topView.getUIView());

			topView.onDeactivated(this);
			topView.onUnbound(this);

			EmbeddedView newTopView = getTopView();
			if (newTopView != null) {
				if (singleViewMode)
					this.addView(newTopView.getUIView());
				newTopView.onActivated(this);
			}

			this.requestLayout();
		}
	}

	public void popToView(EmbeddedView targetView, boolean remainTargetView) {
		if (targetView == null)
			return;
		synchronized (viewList) {
			if (containsView(targetView) == false)
				return;
			if (targetView.equals(getTopView()) && remainTargetView)
				return;

			while(viewList.size() > 0) {
				EmbeddedView topView = getTopView();
				if (topView == null)
					break;
				boolean match = topView.equals(targetView);
				if (!match || !remainTargetView) {
					topView.onDeactivated(this);
					topView.onUnbound(this);

					viewList.remove(topView);
					this.removeView(topView.getUIView());
				}
				if (match)
					break;
			}

			EmbeddedView newTopView = getTopView();
			if (newTopView != null) {
				if (singleViewMode)
					this.addView(newTopView.getUIView());
				newTopView.onActivated(this);
			}

			this.requestLayout();
		}
	}


}
