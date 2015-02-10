package com.example.hospitaltriageapp;

import java.util.ArrayList;

import com.example.hospitaltriageapp.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * An activity displaying all of the patient's recorded medical history.
 * 
 */
public class PatientMedicalHistory extends Activity {

	private ArrayList<String> recordDataArrayList;

	private ArrayAdapter<String> itemsAdapter;

	private ListView activity_patient_medical_history;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_medical_history);

	}
	
	/** 
	 * Retrieves an ArrayList of patient records as strings representing the date 
	 *  the record was created, the ohip number of the patient, temperature,
	 *  blood pressure, heart rate.
	 *  
	 */
	public void onResume() {
		super.onResume();

		// Retrieves an ArrayList of patient records as strings representing the
		// date
		// the record was created, the ohip number of the patient, temperature,
		// blood pressure, heart rate.
		recordDataArrayList = getIntent().getStringArrayListExtra(
				"list of patient records");

		if (recordDataArrayList == null) {

		} else {
			// Initialize UI
			activity_patient_medical_history = (ListView) findViewById(R.id.medical_history);

			// Initialize the Array of strings to be placed in patient_info
			// layout
			itemsAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, recordDataArrayList);
			activity_patient_medical_history.setAdapter(itemsAdapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_medical_history, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}