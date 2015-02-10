package com.example.hospitaltriageapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import backend.RecordManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Class displays the patient profile (name/date of birth/health card including button handling).
 * 
 *
 */
public class PhysicianDisplayPatientProfile extends Activity {
	
	/** Display the data of the patient whose uhip has been written.
	 * Displays the patient profile (OHIP number, date of birth, patient name).
	 * Displays the set of actions a physician can do (display patient vitalsigns/prescriptions or record prescription).
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_display_patient_profile);
		
		// Get the intent that started.
		Intent intent =  getIntent();
		int uhip = intent.getIntExtra("the uhip number", 0);
		String dateBirth = intent.getStringExtra("DateOfBirth");
		String patientName = intent.getStringExtra("Patient Name");
		
		
		// Display the values to the screen.
		TextView displayuhip = (TextView) findViewById(R.id.patientuhiptextView);
		displayuhip.setText("" + uhip);
		
		TextView displayDOB = (TextView) findViewById(R.id.textViewpatientdob);
		displayDOB.setText(dateBirth); 
		
		TextView displayName= (TextView) findViewById(R.id.textViewUserName);
		displayName.setText(patientName);
		
		setupcheckpatienthistorybutton();
		setupnewhistorybutton();
	
	}
	/** Display the medical history of the patient.
	 * Button leads to the medical history page of the patient.
	 * This page includes the prescriptions given and vital signs.
	 *
	 */
	private void setupcheckpatienthistorybutton() {
		Button check =  (Button)findViewById(R.id.patientprofilebutton);
		check.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PhysicianDisplayPatientProfile.this, PatientMedicalHistory.class);
				
				RecordManager manager = RecordManager.getInstance(getApplicationContext());
				HashMap<Integer,ArrayList<String>> visitMap =  manager.getVisitRecordList();
				Intent visitIntent =  getIntent();
				int uhip = visitIntent.getIntExtra("the uhip number", 0);
				Integer ohip = Integer.valueOf(uhip);
				ArrayList<String> visitList = visitMap.get(ohip);
				if (visitList != null){
					Collections.reverse(visitList);
				}
				
				intent.putExtra("list of patient records", visitList);
				
				startActivity(intent); 	
			}
			
		});
		
	}

	/** Create a new prescription given to patient.
	 * Button which leads to the activity to record the patient's prescription info.
	 */
	private void setupnewhistorybutton() {
		Button addnew =  (Button)findViewById(R.id.prescriptionbutton);
		addnew.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				int uhip = intent.getIntExtra("the uhip number", 0);
				// Pass the ohip number to addNewPatientHistory
				Intent i = new Intent(PhysicianDisplayPatientProfile.this, PhysicianAddNewPrescription.class);
				i.putExtra("the ohip", uhip);
				startActivity(i); 	
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_patient_profile, menu);
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
