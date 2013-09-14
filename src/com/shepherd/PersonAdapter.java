package com.shepherd;

import java.util.ArrayList;
import java.util.List;

import com.shepherd.api.Person;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonAdapter extends ArrayAdapter<Person> {

	public PersonAdapter(Activity context, List<Person> jsonResponse) {
		super(context, R.layout.person_row);
		this.activity = context;
		this.list = jsonResponse;
	}

	private final Activity activity;
	private final List<Person> list;

	@Override
	public void add(Person p){
		super.add(p);
		this.list.add(p);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder view;

		if (rowView == null) {
			// Get a new instance of the row layout view
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.person_row, null);

			// Hold the view objects in an object, that way the don't need to be
			// "re- finded"
			view = new ViewHolder();

			view.pic = (ImageView) rowView
					.findViewById(R.id.thumbnailImageView);
			view.name = (TextView) rowView.findViewById(R.id.nameTextView);
			view.description = (TextView) rowView.findViewById(R.id.personDescription);

			rowView.setTag(view);
		} else {
			view = (ViewHolder) rowView.getTag();
		}

		Log.e("getView",""+position);		
		/** Set data to your Views. */
		Person item = list.get(position);
		Log.e("item",item.firstName);
		
		
		if(view!=null){
		
	//	((MainActivity)this.activity).getImageLoaderInstance().get("http://i.imgur.com/TiT9Baz.jpg", view.pic);
		if(item.firstName!=null && item.lastName!=null)
			view.name.setText(item.firstName +" "+item.lastName);
		if(item.description!=null)
			view.description.setText(item.description);
		}else{
			Log.e("wtf","view is null");
		}
			
		return rowView;
	}

	protected static class ViewHolder {
		protected ImageView pic;
		protected TextView name;
		protected TextView description;
	}
}
