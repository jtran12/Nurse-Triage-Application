package com.example.hospitaltriageapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Activity for displaying a list of dates that the patient
 * has been seen by a doctor.
 * 
 */
public class DisplayDatesSeenDoctor extends Activity {
	
	private ArrayList<String> recordDataArrayList;

	private ArrayAdapter<String> itemsAdapter;

	private ListView activity_display_dates_seen_doctor;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_dates_seen_doctor);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_dates_seen_doctor, menu);
		return true;
	}
	
	/**
	 *  Retrieves an ArrayList of patient records as strings representing the
	 *  date the record was created, the ohip number of the patient, temperature,
	 *  blood pressure, heart rate.
	 *  
	 */
	public void onResume() {
		super.onResume();

		recordDataArrayList = getIntent().getStringArrayListExtra(
				"list of doctor seen");

		if (recordDataArrayList == null) {

		} else {
			// Initialize UI
			activity_display_dates_seen_doctor = (ListView) findViewById(R.id.doctor_seen_history);

			// Initialize the Array of strings to be placed in patient_info
			// layout
			itemsAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, recordDataArrayList);
			activity_display_dates_seen_doctor.setAdapter(itemsAdapter);
		}
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
