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
 * ��Ҫ�ٶȵ�ͼ��λ�̳и��༴�� 
 * @author 
 */
public abstract class BaseLocationActivity extends Activity {
	
	private LocationClient mLocationClient;
//	private GeofenceClient mGeofenceClient;
	private MyLocationListener mMyLocationListener;
	
	
	private LocationMode tempMode = LocationMode.Hight_Accuracy;//�߾��ȶ�λ
	private String tempcoor="gcj02";//��λ����ǰٶȾ�γ��
	private int span=1000;//��λ����ļ��ʱ��Ϊ1s
	
	protected Vibrator mVibrator;//�ֻ��𶯿���
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
	 * ��ʼ���ٶȵ�ͼ��λ����
	 */
	private void initBaiDuMapClient() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
//		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);//�ֻ��𶯿��Ʒ���
	}
	
	/**
	 * ʵ��ʵλ�ص�����
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
			sb.append("\nlatitude(γ��) : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude(����) : ");
			sb.append(location.getLongitude());
			sb.append("\nradius(�뾶) : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed(�ٶ�) : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite(����) : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection(����) : ");
				sb.append("\naddr(��ַ) : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr(��ַ) : ");
//				sb.append(location.getAddrStr());
				sb.append(location.getCity());
				//��Ӫ����Ϣ
				sb.append("\noperationers(��Ӫ����Ϣ) : ");
				sb.append(location.getOperators());
			}
			sb.append("\n��ǰ��λ���У�"+location.getCity());
			sb.append("\n��ǰ��λ��ַ��"+location.getAddrStr());
			stopBaiDuMapClient();
//			logMsg(sb.toString());
			logMsg(location);
			Log.i("BaiduLocationApiDem", sb.toString());
		}
	}
	
	/**
	 * ��ʾ�����ַ���
	 * @param str
	 */
//	protected abstract void logMsg(String msg);
	protected abstract void logMsg(BDLocation location);
	
	/**
	 * ��ʼ����λ�ͻ���
	 */
	protected void startBaiDuMapClient() {
		// TODO Auto-generated method stub
		//������λ
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
	 * ��ʼ����λ������λ
	 */
	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//���ö�λģʽ
		option.setCoorType(tempcoor);//���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		option.setScanSpan(span);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		mLocationClient.setLocOption(option);
	}
}
