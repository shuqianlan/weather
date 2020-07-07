package com.amap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.android.exoplayer2.util.Log;
import com.ilifesmart.weather.R;

public class MapLocationActivity extends AppCompatActivity implements AMapLocationListener,
        AMap.OnMapClickListener, GeocodeSearch.OnGeocodeSearchListener {

    public static final String TAG = "MapLocationActivity";
    MapView mMapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    GeocodeSearch geocoderSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);

        mMapView = new MapView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        ((LinearLayout)findViewById(R.id.container)).addView(mMapView, 0);

        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        aMap.setOnMapClickListener(this);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    private void onStartLocation() {
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(MapLocationActivity.this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(MapLocationActivity.this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        }

        mlocationClient.stopLocation();
        mlocationClient.startLocation();//启动定位
    }

    private void onStopLocation() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

        if (mlocationClient != null) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
//        mMapView.onSaveInstanceState(outState);
    }


    private double latitude = 0;
    private double lngitude = 0;
    private String address;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        android.util.Log.d(TAG, "onLocationChanged: amapLocation " + amapLocation);
        if (amapLocation != null
                && amapLocation.getErrorCode() == 0) {
            address = amapLocation.getAddress();
            latitude = amapLocation.getLatitude();
            lngitude = amapLocation.getLongitude();

            updatePosition();
        } else {
            String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
            Log.e("AmapErr",errText);
        }
    }

    private void updatePosition() {
        runOnUiThread(() -> {
            LatLng latLng = new LatLng(latitude, lngitude);
            MarkerOptions otMarkerOptions = new MarkerOptions();
            otMarkerOptions.position(latLng);
            aMap.addMarker(otMarkerOptions);
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
            LatLonPoint latLonPoint = new LatLonPoint(latitude, lngitude);

            // 根据经纬度获取城市信息.
            RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);

            geocoderSearch.getFromLocationAsyn(query);
        });

    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.start_location:
                onStartLocation();
                break;
            case R.id.stop_location:
                onStopLocation();
                break;
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        onStopLocation();
        aMap.clear();

        latitude = latLng.latitude;
        lngitude = latLng.longitude;

        android.util.Log.d(TAG, "onMapClick: click-start");
        updatePosition();

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            android.util.Log.d(TAG, "onMapClick: click-end");
            RegeocodeAddress reAddress = regeocodeResult.getRegeocodeAddress();
            address = reAddress.getFormatAddress();

            runOnUiThread(() -> {
                ((TextView)findViewById(R.id.address)).setText(address);
            });
        }

    }
}