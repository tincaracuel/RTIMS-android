package com.example.rtims_android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ReportActivity extends Activity {

	public final static String EXTRA_OPT = "opt";
	private static String url_roadwork_report = MainActivity.ipadd + "RTIMS/insert_roadwork_report.php";
	private static String url_incident_report = MainActivity.ipadd + "RTIMS/insert_incident_report.php";
	private static String url_other_report = MainActivity.ipadd + "RTIMS/insert_other_report.php";
	private Button mSubmitButton;
	private ProgressDialog pDialog;
	private EditText nameField, emailField, descriptionField, subjectField;
	private Spinner roadworkSpinner, incidentSpinner;
	JSONParser jsonParser = new JSONParser();
	private int opt;

	public ArrayList<Integer> roadworkIndexes = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		opt = getIntent().getIntExtra(EXTRA_OPT, 0);
		
		if(opt == 0){
			finish();
			
		}else if(opt == 1){ //existing roadwork
			setContentView(R.layout.existing_report);
			openReportRoadworkLayout();
			
			mSubmitButton = (Button)findViewById(R.id.ButtonSendReport);
			mSubmitButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					nameField = (EditText) findViewById(R.id.EditTextName);
					String name = nameField.getText().toString();
					
					emailField = (EditText) findViewById(R.id.EditTextEmail);
					String email = emailField.getText().toString();

					roadworkSpinner = (Spinner) findViewById(R.id.SpinnerReportSubject);
					String roadworkID = roadworkSpinner.getSelectedItem().toString();
					
					descriptionField = (EditText) findViewById(R.id.EditTextReportBody);
					String description = descriptionField.getText().toString();
					
					if(name.length() == 0)			nameField.setError("Name is required");
					if(email.length() == 0)			emailField.setError("Email is required");
					if(description.length() == 0) 	descriptionField.setError("Description is required");
					
					if(name.length() != 0 && email.length() != 0 && roadworkID.length() != 0 && description.length() != 0){
						//Log.d("TIN", name + ", " + email + ", " + roadworkID + ", " + description);
						new addReportToDB().execute();
					}
				}
				
			});
			
		}else if(opt == 2){ //existing incident
			setContentView(R.layout.existing_report);
			openReportIncidentLayout();
			
			mSubmitButton = (Button)findViewById(R.id.ButtonSendReport);
			mSubmitButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					nameField = (EditText) findViewById(R.id.EditTextName);
					String name = nameField.getText().toString();

					emailField = (EditText) findViewById(R.id.EditTextEmail);
					String email = emailField.getText().toString();

					incidentSpinner = (Spinner) findViewById(R.id.SpinnerReportSubject);
					String incidentID = incidentSpinner.getSelectedItem().toString();
					
					descriptionField = (EditText) findViewById(R.id.EditTextReportBody);
					String description = descriptionField.getText().toString();
					
					if(name.length() == 0)			nameField.setError("Name is required");
					if(email.length() == 0)			emailField.setError("Email is required");
					if(description.length() == 0) 	descriptionField.setError("Description is required");
					
					if(name.length() != 0 && email.length() != 0 && incidentID.length() != 0 && description.length() != 0){
						//Log.d("TIN", name + ", " + email + ", " + incidentID + ", " + description);
						new addReportToDB().execute();
					}
				}
				
			});
			
		}else if(opt == 3){ //others
			setContentView(R.layout.new_report);
			
			mSubmitButton = (Button)findViewById(R.id.ButtonSendReport);
			mSubmitButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					nameField = (EditText) findViewById(R.id.EditTextName);
					String name = nameField.getText().toString();

					emailField = (EditText) findViewById(R.id.EditTextEmail);
					String email = emailField.getText().toString();

					subjectField = (EditText) findViewById(R.id.EditSubject);
					String subject = subjectField.getText().toString();
					
					descriptionField = (EditText) findViewById(R.id.EditTextReportBody);
					String description = descriptionField.getText().toString();
										
					if(name.length() == 0)			nameField.setError("Name is required");
					if(email.length() == 0)			emailField.setError("Email is required");
					if(subject.length() == 0)		subjectField.setError("Report subject is required");
					if(description.length() == 0) 	descriptionField.setError("Description is required");
										
					if(name.length() != 0 && email.length() != 0 && subject.length() != 0 && description.length() != 0){
						//Log.d("TIN", name + ", " + email + ", " + subject + ", " + description);
						new addReportToDB().execute();
					}
				}
				
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.report, menu);
		return true;
	}
	
	private void openReportRoadworkLayout(){

		Log.d("tin", "asafwfwefw");
		TextView t = (TextView)findViewById(R.id.report_header);
		t.setText("Report on existing roadwork");
		
		//gets the list of roadworks and puts it in the drop down list
		Spinner s = (Spinner)findViewById(R.id.SpinnerReportSubject);			
        ArrayList<String> listOfRoadworks = new ArrayList<String>();
        
        Log.d("tin", "rw");
        roadworkIndexes.clear();	// clear before adding new
        for(int i = 0; i<MarkerList.getInstance().getList().size(); i++){
			if(MarkerList.getInstance().getList().get(i).getType().equals("roadwork")){
				listOfRoadworks.add(MarkerList.getInstance().getList().get(i).getName());
				roadworkIndexes.add(i);
			}
		}
        Log.d("tin", "edone");
        ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,listOfRoadworks);
        s.setAdapter(adp);
        s.setVisibility(View.VISIBLE);
	}
	
	private void openReportIncidentLayout(){
		
		TextView t = (TextView)findViewById(R.id.report_header);
		t.setText("Report on existing incident");
		
		//gets the list of incidents and puts it in the drop down list
		Spinner s = (Spinner)findViewById(R.id.SpinnerReportSubject);			
        ArrayList<String> listOfIncidents = new ArrayList<String>();
        
        for(int i = 0; i<MarkerList.getInstance().getList().size(); i++){
			if(MarkerList.getInstance().getList().get(i).getType().equals("incident")){
				listOfIncidents.add(MarkerList.getInstance().getList().get(i).getId() + ": " + MarkerList.getInstance().getList().get(i).getCategory());
			}
		}
	
        ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,listOfIncidents);
        s.setAdapter(adp);
        s.setVisibility(View.VISIBLE);
	}
	
	
	class addReportToDB extends AsyncTask<String, String, String> {
	   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReportActivity.this);
            pDialog.setMessage("Sending report");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        protected String doInBackground(String... args) {
            
        	
            if(opt == 1){
            	
	            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	            nameField = (EditText) findViewById(R.id.EditTextName);
				emailField = (EditText) findViewById(R.id.EditTextEmail);
				roadworkSpinner = (Spinner) findViewById(R.id.SpinnerReportSubject);
				descriptionField = (EditText) findViewById(R.id.EditTextReportBody);
	            
				int index = roadworkIndexes.get(roadworkSpinner.getSelectedItemPosition());
				
	            params1.add(new BasicNameValuePair("name", nameField.getText().toString()));
	            params1.add(new BasicNameValuePair("email",  emailField.getText().toString()));
	            //params1.add(new BasicNameValuePair("roadworkID",  roadworkSpinner.getSelectedItem().toString()));
	            params1.add(new BasicNameValuePair("roadworkID", MarkerList.getInstance().getList().get(index).getId()));
	            params1.add(new BasicNameValuePair("desc",  descriptionField.getText().toString()));
	
	            JSONObject json = jsonParser.makeHttpRequest(url_roadwork_report,"POST", params1);
	            //Log.d("TIN", "option" + opt + ", " + nameField.getText().toString() +", " + emailField.getText().toString() + ", "+ roadworkSpinner.getSelectedItem().toString() + ", " + descriptionField.getText().toString());
            }
            
            if(opt == 2){
            	
	            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
	            
	            nameField = (EditText) findViewById(R.id.EditTextName);
				emailField = (EditText) findViewById(R.id.EditTextEmail);
				incidentSpinner = (Spinner) findViewById(R.id.SpinnerReportSubject);
				descriptionField = (EditText) findViewById(R.id.EditTextReportBody);
				
	            params2.add(new BasicNameValuePair("name", nameField.getText().toString()));
	            params2.add(new BasicNameValuePair("email",  emailField.getText().toString()));
	            params2.add(new BasicNameValuePair("incidentID",  incidentSpinner.getSelectedItem().toString()));
	            params2.add(new BasicNameValuePair("desc",  descriptionField.getText().toString()));
	
	            JSONObject json = jsonParser.makeHttpRequest(url_incident_report,"POST", params2);
	            //Log.d("TIN", "option" + opt + ", " + nameField.getText().toString() +", " + emailField.getText().toString() + ", "+ incidentSpinner.getSelectedItem().toString() + ", " + descriptionField.getText().toString());
            }
            
            if(opt == 3){
            	
	            List<NameValuePair> params3 = new ArrayList<NameValuePair>();
	            
	            nameField = (EditText) findViewById(R.id.EditTextName);
				emailField = (EditText) findViewById(R.id.EditTextEmail);
				subjectField = (EditText) findViewById(R.id.EditSubject);
				descriptionField = (EditText) findViewById(R.id.EditTextReportBody);
				
	            params3.add(new BasicNameValuePair("name", nameField.getText().toString()));
	            params3.add(new BasicNameValuePair("email",  emailField.getText().toString()));
	            params3.add(new BasicNameValuePair("subject",  subjectField.getText().toString()));
	            params3.add(new BasicNameValuePair("desc",  descriptionField.getText().toString()));
	
	            JSONObject json = jsonParser.makeHttpRequest(url_other_report,"POST", params3);
	            //Log.d("TIN", "option" + opt + ", " + nameField.getText().toString() +", " + emailField.getText().toString() + ", "+ subjectField.getText().toString() + ", " + descriptionField.getText().toString());
            }
            
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
             
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(ReportActivity.this);
            builderSingle.setMessage("Thank you! Your report has been sent.");
            builderSingle.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            
            builderSingle.show();            
        }
 
    }
}
