package com.qozix.mapviewdemo;

import android.app.Activity;
import android.os.Bundle;

import com.qozix.mapview.MapView;

/*
 * Convenience class to allow you to easily implement a MapView with memory management built in
 * just extend and use getMapView() to get a reference to the underlying MapView instance
 */

public class MapViewActivity extends Activity {

	private MapView mapView;

	public MapView getMapView() {
		return mapView;
	}

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		mapView = new MapView( this );
		setContentView( mapView );
	}

	@Override
	public void onPause() {
		super.onPause();
		mapView.clear();
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.requestRender();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.destroy();
		mapView = null;
	}

}
