package com.example.rtims_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {
	
	private Button mLayersButton;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		// Get a handle to the Map Fragment
        GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        LatLng centerMap = new LatLng(14.1876, 121.12508);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(centerMap, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(centerMap));
        
        
        
		mLayersButton = (Button)findViewById(R.id.layers_button);
		mLayersButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
	            builderSingle.setTitle("Layers");
	            
	            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);
	            arrayAdapter.add("Calamba City");
	            arrayAdapter.add("Roadworks");
	            arrayAdapter.add("Traffic Incidents");
	            
	            
	            builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }); //end setNegative
	            
	            
	            builderSingle.setNeutralButton("Clear Map", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }); //end setNeutral

	            
	            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                
                        /*----------------------------------------------------------------------------------*
                         *									CALAMBA CITY									*
                         *----------------------------------------------------------------------------------*/
                        
                        if(strName.equals("Calamba City")){
	                        //centers the map
                        }
                        
                        /*----------------------------------------------------------------------------------*
                         *									ROADWORKS MENU 									*
                         *----------------------------------------------------------------------------------*/
                        else if(strName.equals("Roadworks")){
	                        AlertDialog.Builder builderInner2 = new AlertDialog.Builder(MainActivity.this);
	                        builderInner2.setTitle("Roadworks");
	                        builderInner2.setIcon(R.drawable.construction);
	                        
	                        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);
	        	            arrayAdapter2.add("All Roadworks");
	        	            arrayAdapter2.add("Construction");
	        	            arrayAdapter2.add("Rehabilitation");
	        	            arrayAdapter2.add("Renovation");
	        	            arrayAdapter2.add("Riprapping");
	        	            arrayAdapter2.add("Application");
	        	            arrayAdapter2.add("Installation");
	        	            arrayAdapter2.add("Reconstruction");
	        	            arrayAdapter2.add("Concreting/Asphalting");
	        	            arrayAdapter2.add("Electrification");
	        	            arrayAdapter2.add("Roadway Lighting");
	        	            
	        	            builderInner2.setAdapter(arrayAdapter2, null);
	        	            
	        	            builderInner2.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
	
	                                    @Override
	                                    public void onClick(DialogInterface dialog,int which) {
	                                        dialog.dismiss();
	                                    }
	                                });
	                        builderInner2.show();
                        }
                        
                        /*----------------------------------------------------------------------------------*
                         *									INCIDENTS MENU 									*
                         *----------------------------------------------------------------------------------*/
                        
                        else if(strName.equals("Traffic Incidents")){
	                        AlertDialog.Builder builderInner2 = new AlertDialog.Builder(MainActivity.this);
	                        builderInner2.setTitle("Traffic Incidents");
	                        builderInner2.setIcon(R.drawable.stop);
	                        
	                        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);
	        	            arrayAdapter2.add("All Incidents");
	        	            arrayAdapter2.add("Accident");
	        	            arrayAdapter2.add("Obstruction");
	        	            arrayAdapter2.add("Public Event");
	        	            arrayAdapter2.add("Riprapping");
	        	            arrayAdapter2.add("Funeral");
	        	            arrayAdapter2.add("Flood");
	        	            arrayAdapter2.add("Strike");
	        	            
	        	            builderInner2.setAdapter(arrayAdapter2, null);
	        	            
	                        builderInner2.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
	
	                                    @Override
	                                    public void onClick(DialogInterface dialog,int which) {
	                                        dialog.dismiss();
	                                    }
	                                });
	                        builderInner2.show();
                        }
                    }
                });
	            
	            builderSingle.show();
	           
			}
			
		}); //end setOnClickListener
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
