package com.LocationCity.control;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.LocationCity.bean.LocationCityItem;
import com.LocationCity.widget.ContactItemInterface;
import com.eegets.peter.enclosure.network.httpreq.HttpRestClient;
import com.eegets.peter.enclosure.network.httpreq.request.RequestParams;
import com.eegets.peter.enclosure.network.httpreq.request.SimpleMultipartEntity;

import android.content.Context;  
import android.os.Handler;
import android.os.Message;

public class LocationControl extends HttpRestClient implements HttpRestClient.BaseControlInterface{

	private Handler mHandler;
	
	public LocationControl(Context context, Handler mHandler) {
		super(context);
		this.mHandler = mHandler;
	}
	@Override
	public void onSucceMessage(int statusCode, String content) {
		System.out.println("statusCode:" + statusCode + "////content:" + content);
		if(statusCode == 200) {
			try {
				JSONArray jsonArray = new JSONArray(content);
				List<ContactItemInterface> list = new ArrayList<ContactItemInterface>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					LocationCityItem cityItem = new LocationCityItem(jsonObject.getString("name"), jsonObject.getString("spell"));
					list.add(cityItem);
				}
				Message msg = Message.obtain();
				msg.what = 0;
				msg.obj = list;
				mHandler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	@Override
	public void onFailureMessage(Throwable error, String content) {
		
	}
	@Override
	public RequestParams setParamsMessage() {
		RequestParams params = new RequestParams();
		return params;
	}
	@Override
	public SimpleMultipartEntity setMultipartEntityMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String url() {
		// TODO Auto-generated method stub
		return "http://125.76.228.10:8081/webapi/SchoolAndClass/GetCityList.ashx";
	}
}
