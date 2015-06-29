package com.LocationCity.baseActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

/**
 * 需要百度地图定位继承该类即可 
 * @author 
 */
public abstract class BaseLocationActivity extends Activity {
	
	private LocationClient mLocationClient;
//	private GeofenceClient mGeofenceClient;
	private MyLocationListener mMyLocationListener;
	
	
	private LocationMode tempMode = LocationMode.Hight_Accuracy;//高精度定位
	private String tempcoor="gcj02";//定位结果是百度经纬度
	private int span=1000;//定位请求的间隔时间为1s
	
	protected Vibrator mVibrator;//手机震动控制
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initBaiDuMapClient();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		stopBaiDuMapClient();
		super.onStop();
	}

	/**
	 * 初始化百度地图定位参数
	 */
	private void initBaiDuMapClient() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
//		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);//手机震动控制服务
	}
	
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude(纬度) : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude(经度) : ");
			sb.append(location.getLongitude());
			sb.append("\nradius(半径) : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed(速度) : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite(卫星) : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection(方向) : ");
				sb.append("\naddr(地址) : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr(地址) : ");
//				sb.append(location.getAddrStr());
				sb.append(location.getCity());
				//运营商信息
				sb.append("\noperationers(运营商信息) : ");
				sb.append(location.getOperators());
			}
			sb.append("\n当前定位城市："+location.getCity());
			sb.append("\n当前定位地址："+location.getAddrStr());
			stopBaiDuMapClient();
//			logMsg(sb.toString());
			logMsg(location);
			Log.i("BaiduLocationApiDem", sb.toString());
		}
	}
	
	/**
	 * 显示请求字符串
	 * @param str
	 */
//	protected abstract void logMsg(String msg);
	protected abstract void logMsg(BDLocation location);
	
	/**
	 * 初始化定位客户端
	 */
	protected void startBaiDuMapClient() {
		// TODO Auto-generated method stub
		//开启定位
		InitLocation();
		if(!mLocationClient.isStarted()) {
			mLocationClient.start();
		}
	}
	
	protected void stopBaiDuMapClient() {
		// TODO Auto-generated method stub
		if(mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
	}
	
	/**
	 * 初始化定位参数定位
	 */
	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//设置定位模式
		option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		mLocationClient.setLocOption(option);
	}
}
