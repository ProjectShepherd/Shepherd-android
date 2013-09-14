package com.shepherd;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.shepherd.utils.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link MissingPersonDetail.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link MissingPersonDetail#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class MissingPersonDetail extends Fragment implements OnClickListener {

	private static final String PERSON_DETAIL_FIRST_NAME = "first_name";
	private static final String PERSON_DETAIL_MIDDLE_NAME = "middle_name";
	private static final String PERSON_DETAIL_LAST_NAME = "last_name";
	private static final String PERSON_DETAIL_AGE = "age";
	private static final String PERSON_DETAIL_HEIGHT = "height";
	private static final String PERSON_DETAIL_WEIGHT = "weight";
	private static final String PERSON_DETAIL_SEX = "sex";
	private static final String PERSON_DETAIL_HAIR = "hair_color";
	private static final String PERSON_DETAIL_EYE = "eye_color";
	private static final String PERSON_DETAIL_RACE = "race";
	private static final String PERSON_DETAIL_DESCRIPTION = "description";
	private static final String PERSON_DETAIL_PHOTO = "photo";

	private RequestQueue _requestQueue;
	private ImageLoader _imageLoader;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param personDetail
	 *            the detail info to be displayed.
	 * @return A new instance of fragment MissingPersonDetail.
	 */
	public static MissingPersonDetail newInstance(JSONObject personDetail) {
		MissingPersonDetail fragment = new MissingPersonDetail();
		Bundle args = new Bundle();
		try {
			args.putString(PERSON_DETAIL_FIRST_NAME,
					personDetail.getString(PERSON_DETAIL_FIRST_NAME));
			args.putString(PERSON_DETAIL_MIDDLE_NAME,
					personDetail.getString(PERSON_DETAIL_MIDDLE_NAME));
			args.putString(PERSON_DETAIL_LAST_NAME,
					personDetail.getString(PERSON_DETAIL_LAST_NAME));
			args.putString(PERSON_DETAIL_AGE,
					personDetail.getString(PERSON_DETAIL_AGE));
			args.putString(PERSON_DETAIL_HEIGHT,
					personDetail.getString(PERSON_DETAIL_HEIGHT));
			args.putString(PERSON_DETAIL_WEIGHT,
					personDetail.getString(PERSON_DETAIL_WEIGHT));
			args.putString(PERSON_DETAIL_SEX,
					personDetail.getString(PERSON_DETAIL_SEX));
			args.putString(PERSON_DETAIL_HAIR,
					personDetail.getString(PERSON_DETAIL_HAIR));
			args.putString(PERSON_DETAIL_EYE,
					personDetail.getString(PERSON_DETAIL_EYE));
			args.putString(PERSON_DETAIL_RACE,
					personDetail.getString(PERSON_DETAIL_RACE));
			args.putString(PERSON_DETAIL_DESCRIPTION,
					personDetail.getString(PERSON_DETAIL_DESCRIPTION));
			if (!personDetail.getJSONArray("pictures").isNull(0))
				args.putString(PERSON_DETAIL_PHOTO,
						personDetail.getJSONArray("pictures").getJSONObject(0)
								.getString("mobile"));
		} catch (JSONException e) {
			throw new IllegalArgumentException(e);
		}
		fragment.setArguments(args);
		return fragment;
	}

	public MissingPersonDetail() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_requestQueue = Volley.newRequestQueue(getActivity());
		_imageLoader=new ImageLoader(getActivity());
		
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_missing_person_detail,
				container, false);
		if (getArguments() != null) {
			((TextView) (view.findViewById(R.id.personName)))
					.setText(String
							.format("%s %s %s",
									getArguments().getString(
											PERSON_DETAIL_FIRST_NAME),
									getArguments().getString(
											PERSON_DETAIL_MIDDLE_NAME),
									getArguments().getString(
											PERSON_DETAIL_LAST_NAME)));
			((TextView) (view.findViewById(R.id.personAge)))
					.setText(getArguments().getString(PERSON_DETAIL_AGE));
			((TextView) (view.findViewById(R.id.personHeight)))
					.setText(getArguments().getString(PERSON_DETAIL_HEIGHT));
			((TextView) (view.findViewById(R.id.personWeight)))
					.setText(getArguments().getString(PERSON_DETAIL_WEIGHT));
			((TextView) (view.findViewById(R.id.personSex)))
					.setText(getArguments().getString(PERSON_DETAIL_SEX)
							.equals("F") ? "Female" : "Male");
			((TextView) (view.findViewById(R.id.personHairColor)))
					.setText(getArguments().getString(PERSON_DETAIL_HAIR));
			((TextView) (view.findViewById(R.id.personEyeColor)))
					.setText(getArguments().getString(PERSON_DETAIL_EYE));
			((TextView) (view.findViewById(R.id.personRace)))
					.setText(getArguments().getString(PERSON_DETAIL_RACE));
			((TextView) (view.findViewById(R.id.personDescription)))
					.setText(getArguments()
							.getString(PERSON_DETAIL_DESCRIPTION));

			((NetworkImageView)(view.findViewById(R.id.personImage))).setImageUrl(getArguments()
							.getString(PERSON_DETAIL_PHOTO), imageLoader)
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
		getActivity().onBackPressed();
	}

}
