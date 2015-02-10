package com.example.hospitaltriageapp;

import java.util.ArrayList;

import com.example.hospitaltriageapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * ListofPatients is an activity which will be given the array of strings from
 * the patient list. onResume() will make sure that the layout view will update
 * every time to recent patient list. When the activity is opened in case there
 * is any changes to the patient list
 * 
 */

public class ListofPatients extends Activity {

	private ArrayList<String> patientDataArrayList;

	private ArrayAdapter<String> itemsAdapter;

	private ListView list_of_patient_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_patients);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listof_patients, menu);
		return true;
	}

	/**
	 * onResume() will make sure the list of patients is completely updated. If
	 * user changes activities and adds some patient to the list, the list will
	 * be updated when the user comes back to the list of patients
	 * 
	 */
	protected void onResume() {
		super.onResume();

		// retrieve patient array list from LoadPatient activity
		patientDataArrayList = getIntent().getStringArrayListExtra(
				"list of patients");

		// Initialize UI
		list_of_patient_info = (ListView) findViewById(R.id.list_of_patients);

		// Initialize the Array of strings to be placed in patient_info layout
		itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, patientDataArrayList);
		list_of_patient_info.setAdapter(itemsAdapter);

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
