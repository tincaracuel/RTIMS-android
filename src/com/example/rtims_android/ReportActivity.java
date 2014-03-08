package com.example.rtims_android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ReportActivity extends Activity {

	public final static String EXTRA_INDEX = "opt";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int opt = getIntent().getIntExtra(EXTRA_INDEX, -1);
		 
		setContentView(R.layout.roadwork_report);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report, menu);
		return true;
	}

}
