package com.imou.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.PopupWindow;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;
import com.imou.ChannelInfo;
import com.imou.LeChengMomgr;
import com.imou.RemoteRepository;
import com.imou.json.BindDeviceLiveM3U8Response;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class PopupCategoryWindow extends PopupWindow {

    public static final String TAG = "PopupCategoryWindow";

    private int mWindowWidth;
    private int mWindowHeight;
    private Context mContext;
    private RecyclerView mContView;

    private static List<String> categorys = new ArrayList<>();

    private static final String SET_MOTION_ALARM_STATUS = "modifyDeviceAlarmStatus";
    private static final String UPGRADE_DEVICE = "upgradeDevice";
    private static final String SDCARD_RECOVER = "recoverSDCard";
    private static final String SNAP_DEVICE = "setDeviceSnap";
    private static final String SET_MESSAGE_CB = "setMessageCallback";
    private static final String GET_MESSAGE_CB = "getMessageCallback";
    private static final String BIND_DEVICES_LIVE = "bindDeviceLive";

    static {
        categorys.add(SET_MOTION_ALARM_STATUS);
        categorys.add(UPGRADE_DEVICE);
        categorys.add(SDCARD_RECOVER);
        categorys.add(SNAP_DEVICE);
        categorys.add(SET_MESSAGE_CB);
        categorys.add(GET_MESSAGE_CB);
        categorys.add(BIND_DEVICES_LIVE);
    }

    private static ChannelInfo channel;
    public PopupCategoryWindow(Context context, String uuid) {
        super(context);


        channel = LeChengMomgr.getInstance().getChannelInfo(uuid);
        Log.d(TAG, "PopupCategoryWindow: channel " + channel);

        mContext = context;
        int[] ret = new int[2];
        DensityUtils.getWindowSize(context, ret);

        mWindowWidth = ret[0];
        mWindowHeight = (int) (0.6*ret[1]);

        setWidth(mWindowWidth);
        setHeight(mWindowHeight);
        setOutsideTouchable(false);
        setFocusable(true);
        setAnimationStyle(R.style.popupRegionAnimation);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mContView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.popup_lecheng_category, null, false);
        setContentView(mContView);

        ButterKnife.bind(this, mContView);
        initialize();
    }

    private void initialize() {
        mContView.setLayoutManager(new LinearLayoutManager(mContext));
        mContView.setAdapter(new CategoryAdapter());

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

    }

    public void show() {
        if (mContext instanceof Activity) {
            ViewGroup group = ((Activity) mContext).findViewById(Window.ID_ANDROID_CONTENT);
            darkenBackground(0.6f);
            showAsDropDown(group, 0, -mWindowHeight, Gravity.START);
        }
    }

    private void darkenBackground(float alpha) {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = alpha;
        ((Activity)mContext).getWindow().setAttributes(lp);
    }

//    private void onDismiss(String code) {
//        if (!TextUtils.isEmpty(code)) {
//            if (mSelectedListener != null) {
//                mSelectedListener.onRegionSelected(code, getName());
//            }
//        }
//        dismiss();
//    }

    public static class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private String category;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void onBind(String text) {
            this.category = text;
            ((TextView)itemView).setText(text);
        }

        @Override
        public void onClick(View v) {
            switch (category) {
                case SET_MOTION_ALARM_STATUS:
                    break;
                case UPGRADE_DEVICE:
                    break;
                case SDCARD_RECOVER:
                    break;
                case SNAP_DEVICE:
                    break;
                case SET_MESSAGE_CB:
                    break;
                case GET_MESSAGE_CB:
                    break;
                case BIND_DEVICES_LIVE:
//                    Observable<BindDeviceLiveM3U8Response> flowable = RemoteRepository.getInstance().bindDeviceLive(
//                            LeChengMomgr.getInstance().getToken(), channel.getDeviceCode(), String.valueOf(channel.getIndex()), "proxy", 0
//                    );

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Observable<BindDeviceLiveM3U8Response> flowable2 = RemoteRepository.getInstance().bindDeviceLiveHttps(
                                        LeChengMomgr.getInstance().getToken(), channel.getDeviceCode(), String.valueOf(channel.getIndex()), "proxy", 0
                                );
                            	Thread.sleep(1000);
                                Observable<BindDeviceLiveM3U8Response> flowable3 = RemoteRepository.getInstance().bindUserDeviceLive(
                                        LeChengMomgr.getInstance().getToken(), channel.getDeviceCode(), String.valueOf(channel.getIndex()), "proxy", 0
                                );
                                Thread.sleep(1000);
                                Observable<BindDeviceLiveM3U8Response> flowable4 = RemoteRepository.getInstance().bindUserDeviceLiveHttps(
                                        LeChengMomgr.getInstance().getToken(), channel.getDeviceCode(), String.valueOf(channel.getIndex()), "proxy", 0
                                );
                                Thread.sleep(1000);
                                Observable<BindDeviceLiveM3U8Response> flowable5 = RemoteRepository.getInstance().bindRtspLive(LeChengMomgr.getInstance().getToken());
                                Thread.sleep(1000);
                                Observable.concat(flowable2, flowable3, flowable4, flowable5)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<BindDeviceLiveM3U8Response>() {
                                            @Override
                                            public void accept(BindDeviceLiveM3U8Response bindDeviceLiveM3U8Response) throws Exception {
                                                Log.d(TAG, "accept: response " + bindDeviceLiveM3U8Response);

                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {
                                                Log.d(TAG, "accept: Error " + throwable.getMessage());
                                            }
                                        });
                            } catch(Exception ex) {
                            	ex.printStackTrace();
                            }
                        }
                    }).start();

                    break;

            }

//            PopupCategoryWindow.this.dismiss();
        }
    }

    public static class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            return new CategoryHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int i) {
            categoryHolder.onBind(categorys.get(i));
        }

        @Override
        public int getItemCount() {
            return categorys.size();
        }
    }

}
