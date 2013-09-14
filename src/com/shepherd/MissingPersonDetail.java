package com.shepherd;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shepherd.api.Person;
import com.shepherd.location.LocationProvider;
import com.shepherd.location.LocationProvider.OnLocationObtainedListener;
import com.shepherd.utils.NetUtils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link MissingPersonDetail.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link MissingPersonDetail#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class MissingPersonDetail extends Fragment implements OnClickListener,
		OnLocationObtainedListener, Listener<String>, ErrorListener {

	private JSONObject location;
	private Person personDetail;

	public MissingPersonDetail() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void setPersonDetail(Person personDetail) {
		this.personDetail = personDetail;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_missing_person_detail,
				container, false);
		Log.e("asdf", personDetail.firstName);
		((TextView) (view.findViewById(R.id.personName))).setText(String
				.format("%s %s %s", personDetail.firstName,
						personDetail.middleName, personDetail.lastName));
		((TextView) (view.findViewById(R.id.personAge))).setText("Age: "
				+ personDetail.age);
		((TextView) (view.findViewById(R.id.personHeight)))
				.setText(personDetail.height + " inches");
		((TextView) (view.findViewById(R.id.personWeight)))
				.setText(personDetail.weight + " pounds");
		((TextView) (view.findViewById(R.id.personSex)))
				.setText(personDetail.sex.equals("F") ? "Female" : "Male");
		((TextView) (view.findViewById(R.id.personHairColor)))
				.setText(personDetail.hair + " hair");
		((TextView) (view.findViewById(R.id.personEyeColor))).setText("Eye: "
				+ personDetail.eye);
		((TextView) (view.findViewById(R.id.personRace)))
				.setText(personDetail.race);
		((TextView) (view.findViewById(R.id.personDescription)))
				.setText(personDetail.description);

		((MainActivity) getActivity()).getImageLoaderInstance().get(
				personDetail.photo,
				(ImageView) view.findViewById(R.id.personImage));
		view.findViewById(R.id.markFound).setOnClickListener(this);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	// Mark Found.
	@Override
	public void onClick(View v) {
		((Button) (getView().findViewById(R.id.markFound))).setClickable(false);
		location = null;
		if (((CheckBox) (getView().findViewById(R.id.includeLocation)))
				.isChecked()) {
			(new LocationProvider(getActivity())).getLocation(this);
		} else {
			setMarkFoundRequeset();
		}

	}

	private void setMarkFoundRequeset() {
		final JSONObject request = new JSONObject();
		try {
			request.put("found", true);
			request.put("status", "Found by first responders.");
			if (location != null) {
				request.put("found_location", location);
			}
		} catch (JSONException e) {
			// Impossible.
		}
		StringRequest req = new StringRequest(Method.PUT,
				NetUtils.MissingPeopleURL + "/" + personDetail.id, this, this) {

			protected Map<String, String> getParams()
					throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("data", request.toString());
				return params;
			};
		};
		((MainActivity) getActivity()).getRequestQueue().add(req);
		Log.e("asdf", request.toString());
	}

	@Override
	public void onLocationObtained(Location loc) {
		if (loc != null) {
			location = new JSONObject();
			try {
				location.put("lat", loc.getLatitude());
				location.put("long", loc.getLongitude());
			} catch (JSONException e) {
				// Impossible.
			}
		} else {
			((Button) (getView().findViewById(R.id.markFound)))
					.setText("Could not send location.");
		}
		setMarkFoundRequeset();
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		Log.e("asdf", "Error!!!" + arg0.toString());
		((Button) (getView().findViewById(R.id.markFound)))
				.setText("Network Error. Please go back and try again.");
	}

	@Override
	public void onResponse(String arg0) {
		Log.e("asdf", "Response!!!" + arg0.toString());
		((Button) (getView().findViewById(R.id.markFound))).setText("Success!");
		getActivity().onBackPressed();
	}

}
