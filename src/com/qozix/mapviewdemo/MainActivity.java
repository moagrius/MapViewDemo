package com.qozix.mapviewdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.qozix.mapview.MapView;

public class MainActivity extends Activity {

	private MapView mapView;
	
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		
		// required...
		super.onCreate( savedInstanceState );
		
		// there's just the single constructor
		mapView = new MapView( this );
		
		// i'll set it as the content view but this isn't necessary - it's just a View like any other
		setContentView( mapView );
		
		// add some zoom levels - these all share the same downsample but that's not required
		// i've added them in order of smallest to largest, but the order of insertion is irrelevant
		// zoom levels are managed by a tree set, so are naturally ordered by area (width * height)
		mapView.addZoomLevel( 1159, 951, "tiles/125_%col%_%row%.png", "downsamples/map.png" );
		mapView.addZoomLevel( 2318, 1902, "tiles/250_%col%_%row%.png", "downsamples/map.png" );
		mapView.addZoomLevel( 4635, 3803, "tiles/500_%col%_%row%.png", "downsamples/map.png" );
		mapView.addZoomLevel( 9269, 7606, "tiles/1000_%col%_%row%.png", "downsamples/map.png" );
		
		// let's intercept touch events so that markers and callout don't interrupt our gestures
		mapView.setShouldIntercept( true );
		
		// let's disallow caching explicitly - this isn't terribly helpful unless dealing with especially large
		// or difficult to decode (e.g., remote) bitmaps
		mapView.setCacheEnabled( false );
		
		// provide the corner coordinates for relative positioning
		mapView.registerGeolocator( 42.379676, -71.094919, 42.346550, -71.040280);
		
		// draw some of the points
		mapView.drawPath( points.subList( 0, 12 ) );
		
		// add markers for all the points
		// i'll pass the coordinate via setTag, so the marker can open a callout and center on itself when clicked
		// but really this could be done by grabbing LayoutParams just as well
		for( double[] point : points ) {
			// any view will do...
			ImageView marker = new ImageView( this );
			// save the coordinate for centering and callout positioning
			marker.setTag( point );
			// give it a standard marker icon - this indicator points down and is centered, so we'll use appropriate anchors
			marker.setImageResource( R.drawable.maps_marker_blue );
			// on tap show further information about the area indicated
			marker.setOnClickListener( markerClickListener );
			// add it to the view tree - the anchors -0.5f, -1.0f indicate that the marker should be offset
			// by negative values equal to half its width (horizontally) and its entire height (vertically)
			mapView.addMarker( marker, point[0], point[1], -0.5f, -1.0f );
		}
		
		// set the scale after adding markers - could be set before as well
		mapView.setScale( 0.5 );
		
		// use pixel coordinates to roughly center it
		// pass a boolean true as the 3rd parameter to indicate pixels since we've registered a geolocator
		// when using absolute pixels, they are calculated against the "full" size of the mapView 
		// i.e., the largest zoom level as it would be rendered at a scale of 1.0f
		mapView.moveToAndCenter( 4500, 3500, true );
		
		// add another marker using pixels, again passing a boolean true as the last parameter to indicate absolute pixel values
		ImageView marker = new ImageView( this );
		marker.setImageResource( R.drawable.maps_marker_blue );
		mapView.addMarker( marker, 4500,  3500, true );
		
	}
	
	// when a marker is clicked, show a callout
	// the callout can be any view, and can have it's own event listeners
	private View.OnClickListener markerClickListener = new View.OnClickListener() {
		@Override
		public void onClick( View v ) {
			// we saved the coordinate in the marker's tag
			double[] point = (double[]) v.getTag();
			// lets center the screen to that coordinate
			mapView.slideToAndCenter( point[0], point[1] );
			// create a simple callout
			SampleCallout callout = new SampleCallout( v.getContext() );
			// add it to the view tree at the same position and offset as the marker that invoked it
			mapView.addCallout( callout, point[0], point[1], -0.5f, -1.0f );
			// a little sugar
			callout.transitionIn();
		}
	};

	// a list of points roughly along the freedom trail in Boston
	private ArrayList<double[]> points = new ArrayList<double[]>();
	{
		points.add( new double[] { 42.355503, -71.063883 } );
		points.add( new double[] { 42.358480, -71.063736 } );
		points.add( new double[] { 42.357125, -71.062320 } );
		points.add( new double[] { 42.357420, -71.062025 } );
		points.add( new double[] { 42.358099, -71.060195 } );
		points.add( new double[] { 42.358274, -71.059914 } );
		points.add( new double[] { 42.357797, -71.059639 } );
		points.add( new double[] { 42.357643, -71.058387 } );
		points.add( new double[] { 42.357198, -71.058204 } );
		points.add( new double[] { 42.358769, -71.057806 } );
		points.add( new double[] { 42.358798, -71.057052 } );
		points.add( new double[] { 42.360174, -71.056435 } );
		points.add( new double[] { 42.364047, -71.053808 } );
		points.add( new double[] { 42.366495, -71.054320 } );
		points.add( new double[] { 42.367330, -71.056245 } );
		points.add( new double[] { 42.375664, -71.061504 } );
		points.add( new double[] { 42.376300, -71.060799 } );
		points.add( new double[] { 42.374183, -71.055315 } );
		points.add( new double[] { 42.372443, -71.056196 } );
		points.add( new double[] { 42.377216, -71.053755 } );
		points.add( new double[] { 42.376969, -71.051732 } );
		points.add( new double[] { 42.374512, -71.057248 } );
		points.add( new double[] { 42.373694, -71.056508 } );
		points.add( new double[] { 42.373551, -71.055871 } );
		points.add( new double[] { 42.372381, -71.057642 } );
		points.add( new double[] { 42.357571, -71.063247 } );
		points.add( new double[] { 42.358914, -71.065826 } );
		points.add( new double[] { 42.358959, -71.067922 } );
		points.add( new double[] { 42.358601, -71.069754 } );
		points.add( new double[] { 42.357840, -71.070747 } );
		points.add( new double[] { 42.360107, -71.069324 } );
		points.add( new double[] { 42.360217, -71.066614 } );
		points.add( new double[] { 42.360186, -71.065391 } );
		points.add( new double[] { 42.359999, -71.065127 } );
		points.add( new double[] { 42.360025, -71.065663 } );
		points.add( new double[] { 42.349781, -71.046353 } );
	}
	
	// on pause let's clear the tiles bitmap data
	@Override
	public void onPause(){
		super.onPause();
		mapView.clear();
	}
	
	// on resume, get a new render
	@Override
	public void onResume(){
		super.onResume();
		mapView.requestRender();
	}

	// on destroy, clear and recycle bitmap data, and set all references to null
	@Override
	public void onDestroy(){
		super.onDestroy();
		mapView.destroy();
		mapView = null;
	}
}
