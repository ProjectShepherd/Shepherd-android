package com.shepherd;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
		OnLocationObtainedListener, Listener<JSONObject>, ErrorListener {

	// private static final String PERSON_DETAIL_FIRST_NAME = "first_name";
	// private static final String PERSON_DETAIL_MIDDLE_NAME = "middle_name";
	// private static final String PERSON_DETAIL_LAST_NAME = "last_name";
	// private static final String PERSON_DETAIL_AGE = "age";
	// private static final String PERSON_DETAIL_HEIGHT = "height";
	// private static final String PERSON_DETAIL_WEIGHT = "weight";
	// private static final String PERSON_DETAIL_SEX = "sex";
	// private static final String PERSON_DETAIL_HAIR = "hair_color";
	// private static final String PERSON_DETAIL_EYE = "eye_color";
	// private static final String PERSON_DETAIL_RACE = "race";
	// private static final String PERSON_DETAIL_DESCRIPTION = "description";
	// private static final String PERSON_DETAIL_PHOTO = "photo";
	// private static final String PERSON_REPORT_ID = "report_id";

	private JSONObject location;
	private Person personDetail;

//	/**
//	 * Use this factory method to create a new instance of this fragment using
//	 * the provided parameters.
//	 * 
//	 * @param personDetail
//	 *            the detail info to be displayed.
//	 * @return A new instance of fragment MissingPersonDetail.
//	 */
//	public static MissingPersonDetail newInstance(JSONObject personDetail) {
//		MissingPersonDetail fragment = new MissingPersonDetail();
//		Bundle args = new Bundle();
//
//		json2Bundle(args, PERSON_DETAIL_FIRST_NAME, personDetail);
//		json2Bundle(args, PERSON_DETAIL_MIDDLE_NAME, personDetail);
//		json2Bundle(args, PERSON_DETAIL_LAST_NAME, personDetail);
//		json2Bundle(args, PERSON_DETAIL_AGE, personDetail);
//		json2Bundle(args, PERSON_DETAIL_HEIGHT, personDetail);
//		json2Bundle(args, PERSON_DETAIL_WEIGHT, personDetail);
//		json2Bundle(args, PERSON_DETAIL_SEX, personDetail);
//		json2Bundle(args, PERSON_DETAIL_HAIR, personDetail);
//		json2Bundle(args, PERSON_DETAIL_EYE, personDetail);
//		json2Bundle(args, PERSON_DETAIL_RACE, personDetail);
//		json2Bundle(args, PERSON_DETAIL_DESCRIPTION, personDetail);
//		json2Bundle(args, PERSON_REPORT_ID, personDetail);
//		try {
//			if (personDetail.has("pictures")
//					&& !personDetail.getJSONArray("pictures").isNull(0)) {
//				args.putString(PERSON_DETAIL_PHOTO,
//						personDetail.getJSONArray("pictures").getJSONObject(0)
//								.getString("mobile"));
//			} else {
//				args.putString(PERSON_DETAIL_PHOTO, "");
//			}
//		} catch (JSONException e) {
//			args.putString(PERSON_DETAIL_PHOTO, "");
//		}
//		fragment.setArguments(args);
//		return fragment;
//	}
//
//	private static void json2Bundle(Bundle target, String key, JSONObject json) {
//		if (json.has(key)) {
//			try {
//				target.putString(key, json.getString(key));
//			} catch (JSONException e) {
//				// Impossible
//			}
//		} else {
//			target.putString(key, "");
//		}
//	}

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
		if (getArguments() != null) {
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
			((TextView) (view.findViewById(R.id.personEyeColor)))
					.setText("Eye: " + personDetail.eye);
			((TextView) (view.findViewById(R.id.personRace)))
					.setText(personDetail.race);
			((TextView) (view.findViewById(R.id.personDescription)))
					.setText(personDetail.description);

			// ((MainActivity) getActivity()).getImageLoaderInstance().get(
			// "http://i.imgur.com/TiT9Baz.jpg",
			// (ImageView) view.findViewById(R.id.personImage));

			((MainActivity) getActivity()).getImageLoaderInstance().get(
					personDetail.photo,
					(ImageView) view.findViewById(R.id.personImage));
		}
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
		// getActivity().onBackPressed();
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
		JSONObject request = new JSONObject();
		try {
			request.put("found", true);
			request.put("status", "Found by first responers.");
			if (location != null) {
				request.put("found_location", location);
			}
		} catch (JSONException e) {
			// Impossible.
		}
		new JsonObjectRequest(Method.POST, NetUtils.MissingPeopleURL + "/:"
				+ personDetail.id, request, this, this);
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
		((Button) (getView().findViewById(R.id.markFound)))
				.setText("Network Error. Please go back and try again.");
	}

	@Override
	public void onResponse(JSONObject arg0) {
		((Button) (getView().findViewById(R.id.markFound))).setText("Success!");
		getActivity().onBackPressed();
	}

}
