package com.LocationCity;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.LocationCity.adapter.LocationAdapter;
import com.LocationCity.baseActivity.BaseLocationActivity;
import com.LocationCity.control.LocationControl;
import com.LocationCity.widget.ContactItemInterface;
import com.baidu.location.BDLocation;

public class LocationActivity extends BaseLocationActivity {
	private ListView listview;
	private List<ContactItemInterface> contactList;
	private TextView cityName = null;
	
	@SuppressLint("HandlerLeak")
	@SuppressWarnings("unchecked")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			contactList = (List<ContactItemInterface>) msg.obj;
			LocationAdapter adapter = new LocationAdapter(getApplicationContext(), R.layout.location_city_item, contactList);
			listview.setAdapter(adapter);
		};
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_citylist);
		LocationControl cemo = new LocationControl(this, mHandler);
		cemo.setBaseControlInterface(cemo);
		cemo.postRequestParams();
		
		cityName = (TextView) this.findViewById(R.id.cityName);
		cityName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startBaiDuMapClient();
				cityName.setText("正在获取定位城市...");
			}
		});
		listview = (ListView) this.findViewById(R.id.listview);
		listview.setVerticalScrollBarEnabled(true);
		listview.setFastScrollEnabled(false);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				cityName.setText(contactList.get(position).getDisplayInfo());
			}
		});
		startBaiDuMapClient();
	}

	@Override
	protected void logMsg(BDLocation location) {
		// TODO Auto-generated method stub
		cityName.setText(location.getCity());
	}
}
