package com.ilifesmart.ui;

import android.view.View;

public interface EmbeddedView {
	public void onBound(EmbeddedViewContainer container);
	public void onUnbound(EmbeddedViewContainer container);

	public void onActivated(EmbeddedViewContainer container);
	public void onDeactivated(EmbeddedViewContainer container);

	public View getUIView();
	public String getIdentifier();

	public Object getUserProperty(String key);
	public void setUserProperty(String key, Object value);

}
