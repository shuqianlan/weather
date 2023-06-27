package com.ilifesmart.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.List;


public class MobileLocationActivity extends AppCompatActivity {
    public static final String TAG = "MobileLocationActivity";

    LocationManager locManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_location);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            List<String> perms = locManager.getProviders(true);
            Log.d(TAG, "onCreate: perms " + Arrays.toString(perms.toArray()));

        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.last_loc) {
            Log.d(TAG, "onClick: last_loc");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            List<String> perms = locManager.getProviders(true);
            Location location = null;
            if (perms != null && perms.size() != 0) {
                for(String perm : perms) {
                    Location _location = locManager.getLastKnownLocation(perm);

                    if (location == null || location.getAccuracy() > _location.getAccuracy()) {
                        location = _location;
                    }
                }
            }

            if (location != null) {
                // onClick: location Location[gps 30.25****,120.23**** hAcc=2000.0 et=+30d14h27m3s285ms {Bundle[{accuracyType=1}]}]
                Log.d(TAG, "onClick: location " + location);


                try {
                    Geocoder geocoder = new Geocoder(this);
                    List<Address> addrs = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    if (addrs != null && addrs.size() > 0) {
                        Address address = addrs.get(0);
                        Log.d(TAG, "onClick: address " + address);
//                            Log.d(TAG, "onClick: ");
                    }
                } catch (Exception ex) {

                }
            }
        } else if (view.getId() == R.id.loc_update) {
            Log.d(TAG, "onClick: loc_update");
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, (long) 60_000, 200.0F, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    Log.d(TAG, "onLocationChanged: location " + location);
                }
            });
        }
    }
}