package com.example.rtims_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {
	
	private Button mLayersButton;
	final Context context = this;
	private String jsonResult, jsonResult2;
	private String url = "http://192.168.1.111/RTIMS/roadwork.php";
	private String url2 = "http://192.168.1.111/RTIMS/incident.php";
	//private String url = "http://sample1206.comeze.com/roadwork.php";
	//private String url2 = "http://sample1206.comeze.com/incident.php";
	private GoogleMap map;
	private LatLng centerMap;
	
	private List<Marker> roadworkMarkers = new ArrayList<Marker>();	
	private List<Marker> rw_cat1 = new ArrayList<Marker>();
	private List<Marker> rw_cat2 = new ArrayList<Marker>();
	private List<Marker> rw_cat3 = new ArrayList<Marker>();
	private List<Marker> rw_cat4 = new ArrayList<Marker>();
	private List<Marker> rw_cat5 = new ArrayList<Marker>();
	private List<Marker> rw_cat6 = new ArrayList<Marker>();
	private List<Marker> rw_cat7 = new ArrayList<Marker>();
	private List<Marker> rw_cat8 = new ArrayList<Marker>();
	private List<Marker> rw_cat9 = new ArrayList<Marker>();
	private List<Marker> rw_cat10 = new ArrayList<Marker>();
	
	private List<Marker> incidentMarkers = new ArrayList<Marker>();
	private List<Marker> inc_cat1 = new ArrayList<Marker>();
	private List<Marker> inc_cat2 = new ArrayList<Marker>();
	private List<Marker> inc_cat3 = new ArrayList<Marker>();
	private List<Marker> inc_cat4 = new ArrayList<Marker>();
	private List<Marker> inc_cat5 = new ArrayList<Marker>();
	private List<Marker> inc_cat6 = new ArrayList<Marker>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(isNetworkAvailable()){
			accessWebService();
		}else{
			Toast.makeText(this, "No Internet Connection. Please check your network settings", Toast.LENGTH_LONG).show();
		}
		
		
		// Get a handle to the Map Fragment
        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        centerMap = new LatLng(14.1876, 121.12508);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(centerMap, 13));

        /*map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(centerMap));*/
        
        
        
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
                    	//loop
                    	for(int i=0; i<roadworkMarkers.size(); i++){
                    		roadworkMarkers.get(i).setVisible(false);
                    	}
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
                        	
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(centerMap, 13));
                        }
                        
                        /*----------------------------------------------------------------------------------*
                         *									ROADWORKS MENU 									*
                         *----------------------------------------------------------------------------------*/
                        else if(strName.equals("Roadworks")){
	                        AlertDialog.Builder builderInner2 = new AlertDialog.Builder(MainActivity.this);
	                        builderInner2.setTitle("Roadworks");
	                        builderInner2.setIcon(R.drawable.construction_menu);
	                        
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
	
	// Async Task to access the web
		 private class JsonReadTask extends AsyncTask<String, Void, String> {
		  @Override
		  protected String doInBackground(String... params) {
			   HttpClient httpclient = new DefaultHttpClient();
			   HttpPost httppostRoadwork = new HttpPost(params[0]);
			   HttpPost httppostIncident = new HttpPost(params[1]);
			   try {
			    HttpResponse response = httpclient.execute(httppostRoadwork);
			    jsonResult = inputStreamToString(
			      response.getEntity().getContent()).toString();
			    
			    HttpResponse response2 = httpclient.execute(httppostIncident);
			    jsonResult2 = inputStreamToString(
			      response2.getEntity().getContent()).toString();
			   }
			 
			   catch (ClientProtocolException e) {
			    e.printStackTrace();
			   } catch (IOException e) {
			    e.printStackTrace();
			   }
			   return null;
			  }
		 
		  private StringBuilder inputStreamToString(InputStream is) {
		   String rLine = "";
		   StringBuilder answer = new StringBuilder();
		   BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		 
		   try {
		    while ((rLine = rd.readLine()) != null) {
		     answer.append(rLine);
		     //System.out.println(rLine);
		    }
		   }
		 
		   catch (IOException e) {
		    // e.printStackTrace();
		    Toast.makeText(getApplicationContext(),
		      "Error..." + e.toString(), Toast.LENGTH_LONG).show();
		   }
		   return answer;
		  }
		 
		  @Override
		  protected void onPostExecute(String result) {
		   ListDrwaer();
		  }
		 }// end async task
		 
		 public void accessWebService() {
		  JsonReadTask task = new JsonReadTask();
		  // passes values for the urls string array
		  task.execute(new String[] { url, url2 });
		 }
		 
		 // build hash set for list view
		 public void ListDrwaer() {
		  try {
			//incidents
			JSONObject jsonResponse = new JSONObject(jsonResult2);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("inc");
			 
			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				createINCMarker(jsonChildNode);
			}
			
			//roadworks
			JSONObject jsonResponse2 = new JSONObject(jsonResult);
			JSONArray jsonMainNode2 = jsonResponse2.optJSONArray("rw");
			 
			for (int i = 0; i < jsonMainNode2.length(); i++) {
				JSONObject jsonChildNode2 = jsonMainNode2.getJSONObject(i);
				createRWMarker(jsonChildNode2);
			}
	
		  } catch (JSONException e) {
			  Toast.makeText(getApplicationContext(), "Error" + e.toString(),
				Toast.LENGTH_SHORT).show();
			  Log.d("JSON", e.toString());
		  }
		 }
		 
		 private void createINCMarker(JSONObject jsonChildNode){
				String inc_id = jsonChildNode.optString("inc_id");
			    String inc_type = jsonChildNode.optString("inc_type");
			    String inc_desc = jsonChildNode.optString("description");
			    String inc_start = jsonChildNode.optString("start_date");
			    String inc_end = jsonChildNode.optString("end_date");
			    String inc_lat = jsonChildNode.optString("latitude");
			    String inc_long = jsonChildNode.optString("longitude");
			    String inc_street = jsonChildNode.optString("street");
			    String inc_barangay = jsonChildNode.optString("barangay");
			    
			    LatLng coords = new LatLng(Double.parseDouble(inc_lat), Double.parseDouble(inc_long));
			    Marker marker = map.addMarker(new MarkerOptions()
	            .title(inc_desc)
	            .snippet(inc_desc)
	            .position(coords));
			    
			    
			    categorizeINCMarker(marker, inc_type);
			 }
		 
		 private void createRWMarker(JSONObject jsonChildNode){
			String rwork_no = jsonChildNode.optString("contract_no");
		    String rwork_name = jsonChildNode.optString("rwork_name");
		    String rwork_type = jsonChildNode.optString("rwork_type");
		    String rwork_desc = jsonChildNode.optString("description");
		    String rwork_start = jsonChildNode.optString("start_date");
		    String rwork_end = jsonChildNode.optString("end_date");
		    String rwork_status = jsonChildNode.optString("status");
		    String rwork_lat = jsonChildNode.optString("latitude");
		    String rwork_long = jsonChildNode.optString("longitude");
		    String rwork_street = jsonChildNode.optString("street");
		    String rwork_barangay = jsonChildNode.optString("barangay");
		    String outPut = rwork_no + " "  + rwork_name + " " + rwork_lat+","+rwork_long;
		    
		    LatLng coords = new LatLng(Double.parseDouble(rwork_lat), Double.parseDouble(rwork_long));
		    Marker marker = map.addMarker(new MarkerOptions()
            .title(rwork_name)
            .snippet(rwork_type)
            .position(coords));
            
		    categorizeRWMarker(marker, rwork_type);
		 }
		 
		 private void categorizeRWMarker(Marker marker, String categ){
			 if(categ.equalsIgnoreCase("Construction")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.construction));
				 rw_cat1.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Rehabilitation")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.rehabilitation));
				 rw_cat2.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Renovation")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.renovation));
				 rw_cat3.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Riprapping")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.riprapping));
				 rw_cat4.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Application")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.application));
				 rw_cat5.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Installation")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.installation));
				 rw_cat6.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Reconstruction")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.reconstruction));
				 rw_cat7.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Concreting")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.concreting));
				 rw_cat8.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Electrification")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.electrification));
				 rw_cat9.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Roadway Lighting")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.roadwaylighting));
				 rw_cat10.add(marker);
			 }
			 
			 roadworkMarkers.add(marker);
		 }
		 
		 private void categorizeINCMarker(Marker marker, String categ){
			 if(categ.equalsIgnoreCase("Accident")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.accident));
				 inc_cat1.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Obstruction")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.obstruction));
				 inc_cat2.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Public Event")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.publicevent));
				 inc_cat3.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Funeral")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.funeral));
				 inc_cat4.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Flood")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.flood));
				 inc_cat5.add(marker);
			 }
			 else if(categ.equalsIgnoreCase("Strike")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.strike));
				 inc_cat6.add(marker);
			 }
			 
			 incidentMarkers.add(marker);
		 }
		 
		 public boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		 }

}
