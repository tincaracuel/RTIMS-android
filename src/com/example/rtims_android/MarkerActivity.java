package com.example.rtims_android;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MarkerActivity extends Activity {

	public final static String EXTRA_INDEX = "index";
	private RTIMSMarker mRTIMSMarker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		 int index = getIntent().getIntExtra(EXTRA_INDEX, -1);
		 
		 if(index == -1){
			 finish();
		 }
		 
		 mRTIMSMarker = MarkerList.getInstance().getList().get(index);
		 Log.d("Tin", mRTIMSMarker.getCategory());
		 
		 if(mRTIMSMarker.getType().equalsIgnoreCase("roadwork")){
			
			 setContentView(R.layout.roadwork_details);
			 TextView t = (TextView)findViewById(R.id.dtl_rw_name);
			 t.setText(mRTIMSMarker.getName());
			 
			 t = (TextView)findViewById(R.id.dtl_rw_contract_no);
			 t.setText(mRTIMSMarker.getId());
			 
			 t = (TextView)findViewById(R.id.dtl_rw_categ);
			 t.setText(mRTIMSMarker.getCategory());
			 setImageRoadwork(mRTIMSMarker.getCategory());
			
			 
			 t = (TextView)findViewById(R.id.dtl_rw_desc);
			 t.setText(mRTIMSMarker.getDesc());
			 
			 t = (TextView)findViewById(R.id.dtl_rw_duration);
			 if(mRTIMSMarker.getEnd().equalsIgnoreCase("0000-00-00"))
				 t.setText(mRTIMSMarker.getStart() + " onwards");
			 else t.setText(mRTIMSMarker.getStart() + " to " + mRTIMSMarker.getEnd());
			 
			 t = (TextView)findViewById(R.id.dtl_rw_stat);
			 t.setText("Progress/Status: " + mRTIMSMarker.getStatus() + "%");
			 
			 t = (TextView)findViewById(R.id.dtl_rw_coord);
			 t.setText(mRTIMSMarker.getLat() + ", " + mRTIMSMarker.getLong());
			 
			 t = (TextView)findViewById(R.id.dtl_rw_address);
			 if(mRTIMSMarker.getStreet().equals(""))
				 t.setText(mRTIMSMarker.getBarangay() +", Calamba City");
			 else t.setText(mRTIMSMarker.getStreet() + ", " + mRTIMSMarker.getBarangay() +", Calamba City");
			 
		 }else{
			 setContentView(R.layout.incident_details);
			 
			 TextView t = (TextView)findViewById(R.id.dtl_inc_categ);
			 t.setText(mRTIMSMarker.getCategory());
			 
			 setImageIncident(mRTIMSMarker.getCategory());
			 
			 t = (TextView)findViewById(R.id.dtl_inc_no);
			 t.setText(mRTIMSMarker.getId());
			 
			 t = (TextView)findViewById(R.id.dtl_inc_desc);
			 t.setText(mRTIMSMarker.getDesc());
			 
			 t = (TextView)findViewById(R.id.dtl_inc_duration);
			 if(mRTIMSMarker.getEnd().equalsIgnoreCase("0000-00-00"))
				 t.setText(mRTIMSMarker.getStart() + " onwards");
			 else t.setText(mRTIMSMarker.getStart() + " to " + mRTIMSMarker.getEnd());
			 
			 t = (TextView)findViewById(R.id.dtl_inc_coord);
			 t.setText(mRTIMSMarker.getLat() + ", " + mRTIMSMarker.getLong());
			 
			 t = (TextView)findViewById(R.id.dtl_inc_address);
			 if(mRTIMSMarker.getStreet().equals(""))
				 t.setText(mRTIMSMarker.getBarangay() +", Calamba City");
			 else t.setText(mRTIMSMarker.getStreet() + ", " + mRTIMSMarker.getBarangay() +", Calamba City");
		 }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.marker, menu);
		return true;
	}
	
	private void setImageRoadwork(String categ){
		
		ImageView im = (ImageView)findViewById(R.id.dtl_rw_image);
		
		 if(categ.equalsIgnoreCase("Construction")){
			 im.setImageResource(R.drawable.construction);
		 }
		 else if(categ.equalsIgnoreCase("Rehabilitation")){
			 im.setImageResource(R.drawable.rehabilitation);
		 }
		 else if(categ.equalsIgnoreCase("Renovation")){
			 im.setImageResource(R.drawable.renovation);
		 }
		 else if(categ.equalsIgnoreCase("Riprapping")){
			 im.setImageResource(R.drawable.riprapping);
		 }
		 else if(categ.equalsIgnoreCase("Application")){
			 im.setImageResource(R.drawable.application);
		 }
		 else if(categ.equalsIgnoreCase("Installation")){
			 im.setImageResource(R.drawable.installation);
		 }
		 else if(categ.equalsIgnoreCase("Reconstruction")){
			 im.setImageResource(R.drawable.reconstruction);
		 }
		 else if(categ.equalsIgnoreCase("Concreting")){
			 im.setImageResource(R.drawable.concreting);
		 }
		 else if(categ.equalsIgnoreCase("Electrification")){
			 im.setImageResource(R.drawable.electrification);
		 }
		 else if(categ.equalsIgnoreCase("Roadway Lighting")){
			 im.setImageResource(R.drawable.roadwaylighting);
		 }
	}
	
private void setImageIncident(String categ){
		
		ImageView im = (ImageView)findViewById(R.id.dtl_inc_image);
		
		if(categ.equalsIgnoreCase("Accident")){
			 im.setImageResource(R.drawable.accident);
		 }
		 else if(categ.equalsIgnoreCase("Obstruction")){
			 im.setImageResource(R.drawable.obstruction);
		 }
		 else if(categ.equalsIgnoreCase("Public Event")){
			 im.setImageResource(R.drawable.publicevent);
		 }
		 else if(categ.equalsIgnoreCase("Funeral")){
			 im.setImageResource(R.drawable.funeral);
		 }
		 else if(categ.equalsIgnoreCase("Flood")){
			 im.setImageResource(R.drawable.flood);
		 }
		 else if(categ.equalsIgnoreCase("Strike")){
			 im.setImageResource(R.drawable.strike);
		 }
	}

}
