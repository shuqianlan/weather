package com.imou;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ilifesmart.utils.PersistentMgr;
import com.ilifesmart.weather.R;
import com.imou.json.DeviceListResponse;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;

public class DevicesListActivity extends AppCompatActivity {

    public static final String TAG = "DevicesListActivity";
    @BindView(R.id.lecheng_devices_recycler)
    RecyclerView mDeviceViews;

    private String token;
    private DevicesAdapter adapter;
    @BindView(R.id.lecheng_user_logout)
    public Button logout;

    private static List<ChannelInfo> channels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);
        ButterKnife.bind(this);

        token = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        Log.d(TAG, "onCreate: token " + token);
        mDeviceViews.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DevicesAdapter();
        mDeviceViews.setAdapter(adapter);

        loadChannels();
    }

    public void loadChannels() {
        Flowable<DeviceListResponse> flowable = RemoteRepository.getInstance()
                .deviceList(token, "1-10");
        RxBus.getInstance().doSubscribe(flowable)
                .subscribe(new Consumer<DeviceListResponse>() {
                    @Override
                    public void accept(DeviceListResponse response) throws Exception {
                        channels.clear();
                        if (LeChengUtils.isResponseOK(response)) {
                            Object data = response.getResult().getData();
                            if (data != null && (data instanceof DeviceListResponse.DeviceListResultData)) {
                                List<DeviceListResponse.DeviceListResultData.DeviceListBean> devices = ((DeviceListResponse.DeviceListResultData) data).getDevices();
                                if (devices == null) {
                                    devices = new ArrayList<>();
                                }

                                for(DeviceListResponse.DeviceListResultData.DeviceListBean device:devices) {
                                    channels.addAll(LeChengUtils.devicesElementToResult(device));
                                }
                                LeChengMomgr.getInstance().addChannels(channels);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: Errmsg " + throwable.getMessage());
                    }
                });
    }

    public class DeviceHolder extends RecyclerView.ViewHolder {
        private ChannelInfo channel;

        @BindView(R.id.list_channel_name)
        TextView mChannelName;

        public DeviceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(ChannelInfo channel) {
            this.channel = channel;
            mChannelName.setText(channel.getName());
        }

        @OnClick({R.id.list_device_livevideo, R.id.list_device_localvideo, R.id.list_device_cloudvideo, R.id.list_device_message, R.id.list_device_setting, R.id.list_device_delete})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.list_device_livevideo:
                    Log.d(TAG, "onClick: channel " + channel);
                    if (channel.getEncryptKey() == null && channel.getEncryptMode() == 1) {
                        Log.d(TAG, "onClick: none support");
                    } else {
                        Intent i = new Intent(DevicesListActivity.this, MediaPlayerActivity.class);
                        i.putExtra(Intent.EXTRA_TEXT, channel.getUuid());
                        startActivity(i);
                    }
                    break;
            }
        }
    }

    public class DevicesAdapter extends RecyclerView.Adapter<DeviceHolder> {
        @NonNull
        @Override
        public DeviceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.holder_lecheng_item, viewGroup, false);
            return new DeviceHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull DeviceHolder deviceHolder, int i) {
            ChannelInfo channel = channels.get(i);
            deviceHolder.onBind(channel);
        }

        @Override
        public int getItemCount() {
            return channels.size();
        }
    }

    @OnClick(R.id.lecheng_user_logout)
    public void onClick() {
        PersistentMgr.putKV(LeChengCameraWrapInfo.EXTRA_USER_ID, null);
        finish();
    }
}
