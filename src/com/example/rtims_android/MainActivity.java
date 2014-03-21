package com.example.rtims_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.content.Intent;
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
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {
	
	private Button mLayersButton;
	final Context context = this;
	private String jsonResult, jsonResult2;
	private String url = "http://10.0.246.255/RTIMS/roadwork.php";
	private String url2 = "http://10.0.246.255/RTIMS/incident.php";
	//private String url = "http://sample1206.comeze.com/roadwork.php";
	//private String url2 = "http://sample1206.comeze.com/incident.php";
	private GoogleMap map;
	private LatLng centerMap;
	
	private MarkerList mMarkerList; 
	
	private final int MAX_ROADWORK_ITEM = 10;
	private final int MAX_INCIDENT_ITEM = 7;
	
	boolean[] itemsChecked_rw, itemsChecked_inc;
	
	boolean[] displayedRW = new boolean[MAX_ROADWORK_ITEM];
	boolean[] displayedINC = new boolean[MAX_INCIDENT_ITEM];
	
	final CharSequence[] categ_rw={"construction", "rehabilitation", "renovation", "riprapping", "application", "installation", "reconstruction", "concreting", "electrification", "roadway lighting"};
	
	final CharSequence[] categ_inc={"accident", "obstruction", "public event", "riprapping", "funeral", "flood", "strike"};
	
	final CharSequence[] items_rw={"Construction",
									"Rehabilitation",
									"Renovation",
									"Riprapping",
									"Application",
									"Installation",
									"Reconstruction",
									"Concreting/Asphalting",
									"Electrification",
									"Roadway Lighting"};

	final CharSequence[] items_inc={"Accident",
									"Obstruction",
									"Public Event",
									"Riprapping",
									"Funeral",
									"Flood",
									"Strike"};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(isNetworkAvailable()){
			accessWebService();
		}else{
			Toast.makeText(this, "No Internet Connection. Please check your network settings", Toast.LENGTH_LONG).show();
		}
		
		mMarkerList = MarkerList.getInstance();
		
		// Get a handle to the Map Fragment
        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        centerMap = new LatLng(14.1876, 121.12508);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(centerMap, 13));


        map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
            	findMarkerClicked(marker);
            }
        });
        
        
        
		mLayersButton = (Button)findViewById(R.id.layers_button);
		mLayersButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
	            builderSingle.setTitle("RTIMS");
	            
	            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);
	            arrayAdapter.add("Calamba City");
	            arrayAdapter.add("Roadworks");
	            arrayAdapter.add("Traffic Incidents");
	            arrayAdapter.add("Submit Report");
	            
	            
	            builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }); //end setNegative
	            
	                        
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
                            
                        }else if(strName.equals("Roadworks")){
                        	showRoadworkDialog();
                        	
                        }else if(strName.equals("Traffic Incidents")){
	                        showIncidentDialog();
	                        
                        }else if(strName.equals("Submit Report")){
                        	
                        	//must have an internet connection before you can submit a report
                        	if(isNetworkAvailable()){
                        		AlertDialog.Builder builderInner2 = new AlertDialog.Builder(MainActivity.this);
    							builderInner2.setTitle("Report on...");
    							final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);
    							arrayAdapter2.add("Existing Roadwork");
    							arrayAdapter2.add("Existing Incident");
    							arrayAdapter2.add("Others");
    							
    							builderInner2.setAdapter(arrayAdapter2, new DialogInterface.OnClickListener() {
    							
    							@Override
    							public void onClick(DialogInterface dialog,int which) {
    								String whichReport = arrayAdapter2.getItem(which);
    								
    								Log.d("Tin", whichReport);
    								
    								if(whichReport.equals("Existing Roadwork")){
    									int opt = 1;
    									openReportsActivity(opt);
    								}else if(whichReport.equals("Existing Incident")){
    									int opt = 2;
    									openReportsActivity(opt);
    								}else if(whichReport.equals("Others")){
    									int opt = 3;
    									openReportsActivity(opt);
    								}else{
    									int opt = 0;
    									openReportsActivity(opt);
    								}
    							}
    							});
    							builderInner2.show();

                    		}else{
                    			
                    			Toast.makeText(MainActivity.this, "No Internet Connection. Please check your network settings", Toast.LENGTH_LONG).show();
                    		}
							
                        }
                    }
                });
	            
	            builderSingle.show();
	           
			}
			
		}); //end setOnClickListener
		
		
	}

	// find marker clicked from marker list
	private void findMarkerClicked(Marker marker) {
		Log.d("Tin", "findMarkerClicked");
		for(int i = 0; i < mMarkerList.getList().size(); i++) {
			Log.d("Tin", mMarkerList.getList().get(i).getMarker().getId() + " " + marker.getId());
			if(mMarkerList.getList().get(i).getMarker().getId().equals(marker.getId())) {
				Log.d("Tin", "marker found by id");
				openDetailsActivity(i);
				break;
			}
		}
	}
	
	private void openDetailsActivity(int index) {
		Intent i = new Intent(MainActivity.this,MarkerActivity.class);
		i.putExtra(MarkerActivity.EXTRA_INDEX, index);
		startActivity(i);
	}
	
	private void openReportsActivity(int opt) {
		Intent i = new Intent(MainActivity.this,ReportActivity.class);
		i.putExtra(ReportActivity.EXTRA_OPT, opt);
		startActivity(i);
	}
	
	/*----------------------------------------------------------------------------------*
     *									ROADWORKS MENU 									*
     *----------------------------------------------------------------------------------*/
	public void showRoadworkDialog(){
						
		itemsChecked_rw = new boolean[MAX_ROADWORK_ITEM];
		
    	for(int i=0; i <MAX_ROADWORK_ITEM; i++){
    		itemsChecked_rw[i] = displayedRW[i];
    	}
    	
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	builder.setTitle("Roadworks");
    	builder.setIcon(R.drawable.construction_menu);
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            	
                for (int i = 0; i < MAX_ROADWORK_ITEM; i++) {
                	List<RTIMSMarker> temp = mMarkerList.getListInCategory(categ_rw[i].toString());
                	
	                if (itemsChecked_rw[i]) {
	                    Log.d("Tin","item selected:"+categ_rw[i].toString());          		
	                	for(int j=0; j<temp.size(); j++){
	                		temp.get(j).getMarker().setVisible(true);
                    	}
	                                	
	                	
	                }else if (!itemsChecked_rw[i]) {
	                	Log.d("Tin","item not selected: "+categ_rw[i].toString());
	                	for(int j=0; j<temp.size(); j++){
	                		temp.get(j).getMarker().setVisible(false);
                    	}
	                }	
                }
                
            }
        });
    	
    	builder.setMultiChoiceItems(items_rw, displayedRW, new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					itemsChecked_rw[which]=isChecked;	
			}
		});
    	builder.show();
    }
	
	/*----------------------------------------------------------------------------------*
     *									INCIDENTS MENU 									*
     *----------------------------------------------------------------------------------*/
    
	public void showIncidentDialog(){
			
		itemsChecked_inc = new boolean[MAX_INCIDENT_ITEM];

    	for(int i=0; i <MAX_INCIDENT_ITEM; i++){
    		itemsChecked_inc[i] = displayedINC[i];
    	}
    	
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	builder.setTitle("Traffic Incidents");
    	builder.setIcon(R.drawable.stop);
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            	for (int i = 0; i < MAX_INCIDENT_ITEM; i++) {
                	List<RTIMSMarker> temp = mMarkerList.getListInCategory(categ_inc[i].toString());
                	
	                if (itemsChecked_inc[i]) {
	                    Log.d("Tin","item selected:"+categ_inc[i].toString());          		
	                	for(int j=0; j<temp.size(); j++){
	                		temp.get(j).getMarker().setVisible(true);
                    	}
	                                	
	                	
	                }else if (!itemsChecked_inc[i]) {
	                	Log.d("Tin","item not selected: "+categ_inc[i].toString());
	                	for(int j=0; j<temp.size(); j++){
	                		temp.get(j).getMarker().setVisible(false);
                    	}
	                }	
                }
            }
        });
    	
    	builder.setMultiChoiceItems(items_inc, displayedINC, new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					itemsChecked_inc[which]=isChecked;	
			}
		});
    	builder.show();
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
			 RTIMSMarker iMarker; 
			 
			 String inc_id = jsonChildNode.optString("inc_id");
			 String inc_type = jsonChildNode.optString("inc_type");
			 String inc_desc = jsonChildNode.optString("description");
			 String inc_start = jsonChildNode.optString("start_date");
			 String inc_end = jsonChildNode.optString("end_date");
			 String inc_street = jsonChildNode.optString("street");
			 String inc_barangay = jsonChildNode.optString("barangay");
			 String inc_lat = jsonChildNode.optString("latitude");
			 String inc_long = jsonChildNode.optString("longitude");

		    LatLng coords = new LatLng(Double.parseDouble(inc_lat), Double.parseDouble(inc_long));
		    Marker marker = map.addMarker(new MarkerOptions()
            .title(inc_id + ": " + inc_type)
            .snippet(inc_desc)
            .position(coords));
		    
		    categorizeINCMarker(marker, inc_type);
		    
		    iMarker = new RTIMSMarker(marker, inc_type, "incident", inc_id, inc_desc,
		    						inc_start, inc_end, inc_street, inc_barangay, inc_lat, inc_long);
			mMarkerList.add(iMarker);
		 }
		 
		 private void createRWMarker(JSONObject jsonChildNode){
			RTIMSMarker rMarker;
			 
			String rwork_no = jsonChildNode.optString("contract_no");
		    String rwork_name = jsonChildNode.optString("rwork_name");
		    String rwork_type = jsonChildNode.optString("rwork_type");
		    String rwork_desc = jsonChildNode.optString("description");
		    String rwork_start = jsonChildNode.optString("start_date");
		    String rwork_end = jsonChildNode.optString("end_date");
		    String rwork_status = jsonChildNode.optString("status"); 
		    String rwork_street = jsonChildNode.optString("street");
		    String rwork_barangay = jsonChildNode.optString("barangay");
		    String rwork_lat = jsonChildNode.optString("latitude");
		    String rwork_long = jsonChildNode.optString("longitude");
		    
		    LatLng coords = new LatLng(Double.parseDouble(rwork_lat), Double.parseDouble(rwork_long));
		    Marker marker = map.addMarker(new MarkerOptions()
            .title(rwork_name)
            .snippet(rwork_type)
            .position(coords));
            
		    categorizeRWMarker(marker, rwork_type);
		    
		    rMarker = new RTIMSMarker(marker, rwork_type, "roadwork", rwork_no, rwork_name, rwork_desc,
		    						rwork_start, rwork_end, rwork_status, rwork_street, rwork_barangay,
		    						rwork_lat, rwork_long);
			 mMarkerList.add(rMarker);
		 }
		 
		 private void categorizeRWMarker(Marker marker, String categ){
					 
			 if(categ.equalsIgnoreCase("Construction")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.construction));
				 displayedRW[0] = true;
			 }
			 else if(categ.equalsIgnoreCase("Rehabilitation")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.rehabilitation));
				 displayedRW[1] = true;
			 }
			 else if(categ.equalsIgnoreCase("Renovation")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.renovation));
				 displayedRW[2] = true;
			 }
			 else if(categ.equalsIgnoreCase("Riprapping")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.riprapping));
				 displayedRW[3] = true;
			 }
			 else if(categ.equalsIgnoreCase("Application")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.application));
				 displayedRW[4] = true;
			 }
			 else if(categ.equalsIgnoreCase("Installation")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.installation));
				 displayedRW[5] = true;
			 }
			 else if(categ.equalsIgnoreCase("Reconstruction")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.reconstruction));
				 displayedRW[6] = true;
			 }
			 else if(categ.equalsIgnoreCase("Concreting")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.concreting));
				 displayedRW[7] = true;
			 }
			 else if(categ.equalsIgnoreCase("Electrification")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.electrification));
				 displayedRW[8] = true;
			 }
			 else if(categ.equalsIgnoreCase("Roadway Lighting")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.roadwaylighting));
				 displayedRW[9] = true;
			 }
			 
		 }
		 
		 private void categorizeINCMarker(Marker marker, String categ){
			 
			 
			 if(categ.equalsIgnoreCase("Accident")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.accident));
				 displayedINC[0] = true;
			 }
			 else if(categ.equalsIgnoreCase("Obstruction")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.obstruction));
				 displayedINC[1] = true;
			 }
			 else if(categ.equalsIgnoreCase("Public Event")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.publicevent));
				 displayedINC[2] = true;
			 }
			 else if(categ.equalsIgnoreCase("Funeral")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.funeral));
				 displayedINC[3] = true;
			 }
			 else if(categ.equalsIgnoreCase("Flood")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.flood));
				 displayedINC[4] = true;
			 }
			 else if(categ.equalsIgnoreCase("Strike")){
				 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.strike));
				 displayedINC[5] = true;
			 }
			 
			
		 }
		 
		 public boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		 }

}
