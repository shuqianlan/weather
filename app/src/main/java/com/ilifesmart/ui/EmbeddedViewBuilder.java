package com.ilifesmart.ui;

import android.view.View;

import java.util.HashMap;

/**
 * Created by fanxq on 17/1/18.
 */

public class EmbeddedViewBuilder {
    public interface Call {
        void onCall(EmbeddedViewContainer container, EmbeddedView view);
    }

    protected View uiView;
    protected String identifier;
    protected Call onBoundCall;
    protected Call onUnboundCall;
    protected Call onActivatedCall;
    protected Call onDeactivatedCall;
    protected HashMap<String, Object> properties = new HashMap<>();

    public EmbeddedViewBuilder setUIView(View uiView) {
        this.uiView = uiView;
        return this;
    }

    public EmbeddedViewBuilder setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public EmbeddedViewBuilder setOnBound(Call call) {
        this.onBoundCall = call;
        return this;
    }

    public EmbeddedViewBuilder setOnUnbound(Call call) {
        this.onUnboundCall = call;
        return this;
    }

    public EmbeddedViewBuilder setOnActivated(Call call) {
        this.onActivatedCall = call;
        return this;
    }

    public EmbeddedViewBuilder setOnDeactivated(Call call) {
        this.onDeactivatedCall = call;
        return this;
    }

    public EmbeddedView build() {
        final EmbeddedView impl = new EmbeddedView() {
            @Override
            public void onBound(EmbeddedViewContainer container) {
                if (onBoundCall != null)
                    onBoundCall.onCall(container, this);
            }

            @Override
            public void onUnbound(EmbeddedViewContainer container) {
                if (onUnboundCall != null)
                    onUnboundCall.onCall(container, this);
            }

            @Override
            public void onActivated(EmbeddedViewContainer container) {
                if (onActivatedCall != null)
                    onActivatedCall.onCall(container, this);
            }

            @Override
            public void onDeactivated(EmbeddedViewContainer container) {
                if (onDeactivatedCall != null)
                    onDeactivatedCall.onCall(container, this);
            }

            @Override
            public View getUIView() {
                return uiView;
            }

            @Override
            public String getIdentifier() {
                return identifier;
            }

            @Override
            public Object getUserProperty(String key) {
                return properties.get(key);
            }

            @Override
            public void setUserProperty(String key, Object value) {
                properties.put(key, value);
            }
        };
        return impl;
    }

}
