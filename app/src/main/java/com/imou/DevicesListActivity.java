package com.imou;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.ilifesmart.utils.PersistentMgr;
import com.ilifesmart.weather.R;
import com.imou.json.AuthedDeviceListResponse;
import com.imou.json.DeviceListResponse;
import com.imou.json.LeChengResponse;
import com.imou.ui.PopupCategoryWindow;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DevicesListActivity extends AppCompatActivity {

    public static final String TAG = "DevicesListActivity";
    RecyclerView mDeviceViews;

    private String token;
    private DevicesAdapter adapter;
    public Button logout;

    private static List<ChannelInfo> channels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);
        mDeviceViews = findViewById(R.id.lecheng_devices_recycler);
        logout = findViewById(R.id.lecheng_user_logout);

        token = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        Log.d(TAG, "onCreate: token " + token);
        mDeviceViews.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DevicesAdapter();
        mDeviceViews.setAdapter(adapter);

        loadChannels();
    }

    public void loadChannels() {
        Observable deviceList = RemoteRepository.getInstance()
                .deviceList(token, "1-10");
        Observable shareDeviceList = RemoteRepository.getInstance()
                .shareDeviceList(token, "1-10");
        Observable beAuthDeviceList = RemoteRepository.getInstance()
                .beAuthDeviceList(token, "1-10");

        channels.clear();

        AtomicInteger atomic = new AtomicInteger(0);
        Observable.concat(deviceList, shareDeviceList, beAuthDeviceList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LeChengResponse>() {
                    @Override
                    public void accept(LeChengResponse response) throws Exception {
                        if (LeChengUtils.isResponseOK(response)) {
                            atomic.getAndIncrement();
                            Log.d(TAG, "accept: response " + response);
                            Object data = response.getResult().getData();
                            if (data != null && (data instanceof DeviceListResponse.DeviceListResultData)) {
                                List<DeviceListResponse.DeviceListResultData.DeviceListBean> devices = ((DeviceListResponse.DeviceListResultData) data).getDevices();
                                if (devices == null) {
                                    devices = new ArrayList<>();
                                }

                                for(DeviceListResponse.DeviceListResultData.DeviceListBean device:devices) {
                                    channels.addAll(LeChengUtils.devicesElementToResult(device));
                                }
                            } else if (data != null && data instanceof AuthedDeviceListResponse.Data) {
                                channels.addAll(LeChengUtils.devicesElementToResult(response));
                            }

                            if (atomic.get() == 2) {
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

        beAuthDeviceList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AuthedDeviceListResponse>() {
                    @Override
                    public void accept(AuthedDeviceListResponse response) throws Exception {
                        Log.d(TAG, "accept: beAuthDeviceList " + response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });


        shareDeviceList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeviceListResponse>() {
                    @Override
                    public void accept(DeviceListResponse response) throws Exception {
                        Log.d(TAG, "accept: shareDeviceList " + response);
                        if (LeChengUtils.isResponseOK(response)) {
//                            atomic.getAndIncrement();
                            Log.d(TAG, "accept: response " + response);
                            Object data = response.getResult().getData();
                            if (data != null && (data instanceof DeviceListResponse.DeviceListResultData)) {
                                List<DeviceListResponse.DeviceListResultData.DeviceListBean> devices = ((DeviceListResponse.DeviceListResultData) data).getDevices();
                                if (devices == null) {
                                    devices = new ArrayList<>();
                                }

                                for(DeviceListResponse.DeviceListResultData.DeviceListBean device:devices) {
                                    channels.addAll(LeChengUtils.devicesElementToResult(device));
                                }
                            } else if (data != null && data instanceof AuthedDeviceListResponse.Data) {
                                channels.addAll(LeChengUtils.devicesElementToResult(response));
                            }

//                            if (atomic.get() == 2) {
                                LeChengMomgr.getInstance().addChannels(channels);
                                adapter.notifyDataSetChanged();
//                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public class DeviceHolder extends RecyclerView.ViewHolder {
        private ChannelInfo channel;

        TextView mChannelName;

        public DeviceHolder(@NonNull View itemView) {
            super(itemView);

            mChannelName = itemView.findViewById(R.id.list_channel_name);
        }

        public void onBind(ChannelInfo channel) {
            this.channel = channel;
            mChannelName.setText(channel.getName());
        }

        public void onClick(View v) {
            if (v.getId() == R.id.list_device_livevideo) {
                Log.d(TAG, "onClick: channel " + channel);
                if (channel.getEncryptKey() == null && channel.getEncryptMode() == 1) {
                    Log.d(TAG, "onClick: none support");
                } else {
                    Intent i = new Intent(DevicesListActivity.this, MediaPlayerActivity.class);
                    i.putExtra(Intent.EXTRA_TEXT, channel.getUuid());
                    startActivity(i);
                }
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

    public void onClick(View v) {
        if (v.getId() == R.id.lecheng_user_logout) {
            PersistentMgr.putKV(LeChengCameraWrapInfo.EXTRA_USER_ID, null);
            finish();
        } else if (v.getId() == R.id.lecheng_category) {
            if (channels.size() > 0) {
                ChannelInfo channel = channels.get(0);
                new PopupCategoryWindow(this, channel.getUuid()).show();
            }
        }
    }
}
