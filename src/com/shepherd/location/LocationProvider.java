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
    private final LocationClient locationclient;
    private LocationRequest locationrequest;
    private Location mLastUpdatedLocation;

    private static final String TAG = "SHEPHERD";

    public LocationProvider(Context context) {
        locationclient = new LocationClient(context, this, this);
    }

    public Location getLastLocation() {
        locationclient.connect();
        Location loc = locationclient.getLastLocation();
        Log.i(TAG, "Last Known Location :" + loc.getLatitude() + "," + loc.getLongitude());
        locationclient.disconnect();
        return loc;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
    }

    @Override
    public void onDisconnected() {
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
    }

    public void requestLocationUpdates(int timeout) {
        locationclient.connect();

        locationrequest = LocationRequest.create();
        locationrequest.setInterval(timeout);
        locationclient.requestLocationUpdates(locationrequest, this);

        locationclient.disconnect();
    }

    public Location getCurrentLocation() {
        return mLastUpdatedLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            mLastUpdatedLocation = location;
            Log.i(TAG, "Location Request :" + location.getLatitude() + "," + location.getLongitude());
        }

    }

}
