package com.shepherd.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class LocationProvider implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    private final LocationClient mLocationclient;
    private OnLocationObtainedListener mLocationObtainedListener;

    private static final String TAG = "SHEPHERD";
    private static final int TIMEOUT = 30000;

    public LocationProvider(Context context) {
        mLocationclient = new LocationClient(context, this, this);
    }

    public void getLocation(OnLocationObtainedListener listener) {
        this.mLocationObtainedListener = listener;
        mLocationclient.connect();
        Log.i(TAG, "connecting");
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "connected");
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(TIMEOUT);
        mLocationclient.requestLocationUpdates(locationRequest, this);
    }

    @Override
    public void onDisconnected() {
        Log.i(TAG, "disconnected");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "failed");
        if (mLocationObtainedListener != null) {
            mLocationObtainedListener.onLocationObtained(null);
        }
        mLocationclient.disconnect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "location changed");
        if (location != null) {
            Log.i(TAG, "Location Request :" + location.getLatitude() + "," + location.getLongitude());
        } else {
            Log.i(TAG, "Location Request :null");
        }
        if (mLocationObtainedListener != null) {
            mLocationObtainedListener.onLocationObtained(location);
        }
        mLocationclient.disconnect();
    }

    public interface OnLocationObtainedListener {
        void onLocationObtained(Location loc);
    }

}
