package com.qozix.mapviewdemo;

import com.qozix.mapview.MapView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Convenience class to use MapView in a Fragment driven app, with memory management overrides provided.
 * Extend and use getMapView to call MapView methods
 * (NOTE: this is just "in theory" and has not been tested)
 */
public class MapViewFragment extends Fragment {

	private MapView mapView;

	public MapView getMapView() {
		return mapView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return new MapView( getActivity() );
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
