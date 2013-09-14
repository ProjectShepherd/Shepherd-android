package com.shepherd;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shepherd.location.LocationProvider;
import com.shepherd.location.LocationProvider.OnLocationObtainedListener;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class SampleFragment extends Fragment implements OnLocationObtainedListener {
    public static final String ARG_PAGE_NUMBER = "page_number";

    public SampleFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample, container, false);
        String page = getResources().getStringArray(R.array.pages_array)[1];

        LocationProvider lp = new LocationProvider(getActivity());
        lp.getLocation(this);

        getActivity().setTitle(page);
        return rootView;
    }

    @Override
    public void onLocationObtained(Location loc) {
    }
}
