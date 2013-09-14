package com.shepherd;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class PersonSearchFragment extends ListFragment implements OnClickListener{

	protected View mFormView, mStatusView;
	
	public PersonSearchFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person_search, container, false);
        
        mFormView = rootView.findViewById(R.id.form);
		mStatusView = rootView.findViewById(R.id.status);

		mStatusView.setVisibility(View.VISIBLE);
		mFormView.setVisibility(View.GONE);
        
        //TODO remove and replace with networking
		onResult();
        
        
        String page = getResources().getStringArray(R.array.pages_array)[0];

        Button b = (Button) rootView.findViewById(R.id.button1);
        b.setOnClickListener(this);
        
        getActivity().setTitle(page);
        return rootView;
    }
    
    
    public void onResult(){
    	//TODO plug into volley
    	mStatusView.setVisibility(View.GONE);
		mFormView.setVisibility(View.VISIBLE);
    	
		
		
    }
    
    @Override
	public void onListItemClick(ListView l, View v, int position, long id) {
    	SampleFragment newFrag = new SampleFragment();

		this.getActivity().getFragmentManager()
				.beginTransaction().replace(R.id.content_frame, newFrag)
				// TODO Add this transaction to the back stack
				// .addToBackStack("placeholder")
				.commit();
    }
    
    @Override
	public void onClick(View v) {
    			SampleFragment newFrag = new SampleFragment();

    			this.getActivity().getFragmentManager()
    					.beginTransaction().replace(R.id.content_frame, newFrag)
    					// TODO Add this transaction to the back stack
    					// .addToBackStack("placeholder")
    					.commit();
		
	}
}
