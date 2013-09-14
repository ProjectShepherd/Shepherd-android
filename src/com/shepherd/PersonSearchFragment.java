package com.shepherd;

import com.shepherd.location.LocationProvider;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class PersonSearchFragment extends Fragment {
    public static final String ARG_PAGE_NUMBER = "page_number";

    public PersonSearchFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample, container, false);
        int i = getArguments().getInt(ARG_PAGE_NUMBER);
        String page = getResources().getStringArray(R.array.pages_array)[i];

        
        getActivity().setTitle(page);
        return rootView;
    }
}
