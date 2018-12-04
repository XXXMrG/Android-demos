package com.example.xxxmrg.work;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GpsActivity extends AppCompatActivity {
    private String[] permissions={Manifest.permission.ACCESS_FINE_LOCATION};
    // requestcode use in the callback
    final public static int REQUEST_CODE_ACCESS_FINE_LOCATION = 123;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        TextView textView = findViewById(R.id.textView3);
        this.checkPermission();
        textView.append(this.updateLocation());
    }

    // 动态获取权限，SDK 23 以后新增
    private void checkPermission(){
        //check the permission when sdk >= 6.0
        if (Build.VERSION.SDK_INT >= 23){
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // Granted = accept dinied = no
            if (i != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ACCESS_FINE_LOCATION);
            }else{
                //pass
            }
        }else{
            //pass
        }
    }
    // 获取系统定位管理器
    private LocationManager getLocationManager(){
        return (LocationManager) getSystemService(LOCATION_SERVICE);
    }
    // 新建一个位置监听器
    private final LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
            // TODO Auto-generated method stub

        }

    };
    // 判断用于 LocationManger 的 Provider
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if(prodiverlist.contains(LocationManager.NETWORK_PROVIDER)){
            return LocationManager.NETWORK_PROVIDER;//网络定位
        }else if(prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;//GPS定位
        }else{
            Toast.makeText(this,"没有可用的位置提供器",Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    // 获得位置服务
    private Location beginLocatioon() {
        //获得位置服务
        LocationManager locationManager = this.getLocationManager();
        String provider = this.judgeProvider(locationManager);
        //String provider = LocationManager.GPS_PROVIDER;

        //有位置提供器的情况
        if (provider != null) {
            //为了压制getLastKnownLocation方法的警告
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
                return null;
            }
            return locationManager.getLastKnownLocation(provider);
        }else{
            //不存在位置提供器的情况
            Toast.makeText(this,"不存在位置提供器的情况",Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    // 更新用户界面显示
    public String updateLocation(){
        Location location = this.beginLocatioon();
        String localString;
        if (location != null){
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            localString = "纬度：" + lat + "\n" + "经度：" + lng + "\n";
        }else {
            localString = "获取地理位置失败";
        }

        return localString;
    }

}
