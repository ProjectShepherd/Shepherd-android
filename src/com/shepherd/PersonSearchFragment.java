package com.shepherd;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.shepherd.api.Person;
import com.shepherd.utils.PersonUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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
public class PersonSearchFragment extends ListFragment implements OnClickListener, Listener<JSONObject>{

	protected View mFormView, mStatusView;
	private RequestQueue volleyQueue;
	
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
        
        String dummyURL = "";
		
		volleyQueue = Volley.newRequestQueue(this.getActivity());
		volleyQueue.add(new JsonObjectRequest(dummyURL, null, this, null));
        
        
        String page = getResources().getStringArray(R.array.pages_array)[0];
        
        getActivity().setTitle(page);
        return rootView;
    }
    
    
    @Override
	public void onResponse(JSONObject response) {
    	mStatusView.setVisibility(View.GONE);
		mFormView.setVisibility(View.VISIBLE);
    	
		
		
		PersonAdapter adapter = new PersonAdapter(this.getActivity(), new ArrayList<Person>());
		setListAdapter(adapter);
		
		ArrayList<Person> people = new PersonUtils.getMissingPersons(response.toString(), null);
		
		adapter.add(new Person());
		people.add(new Person());
		people.add(new Person());
    }
    
    @Override
	public void onListItemClick(ListView l, View v, int position, long id) {
    	SampleFragment newFrag = new SampleFragment();

		this.getActivity().getSupportFragmentManager()
				.beginTransaction().replace(R.id.content_frame, newFrag)
				// TODO Add this transaction to the back stack
				// .addToBackStack("placeholder")
				.commit();
    }
    
    @Override
	public void onClick(View v) {
    			MissingPersonDetail newFrag = null;
				try {
					newFrag = MissingPersonDetail.newInstance(new JSONObject("{first_name: 'FIRST',middle_name: 'M',last_name: 'XXXXXX'}"));
				} catch (JSONException e) {
				}

    			this.getActivity().getSupportFragmentManager()
    					.beginTransaction().replace(R.id.content_frame, newFrag)
    					// TODO Add this transaction to the back stack
    					// .addToBackStack("placeholder")
    					.commit();
		
	}
}
