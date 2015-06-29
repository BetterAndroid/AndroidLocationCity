package com.LocationCity.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.LocationCity.R;
import com.LocationCity.adapter.base.BaseLocationAdapter;
import com.LocationCity.widget.ContactItemInterface;

@SuppressLint("ViewConstructor")
public class LocationAdapter extends BaseLocationAdapter {

	public LocationAdapter(Context context, int resource,
			List<ContactItemInterface> items) {
		super(context, resource, items);
	}

	public void populateDataForRow(View parentView, ContactItemInterface item,
			int position) {
		View infoView = parentView.findViewById(R.id.infoRowContainer);
		TextView nicknameView = (TextView) infoView.findViewById(R.id.cityName);
		if(position == items.size()) {
			infoView.setBackgroundResource(R.drawable.apply_business__edit_bg);
		}
		nicknameView.setText(item.getDisplayInfo());
	}

}
