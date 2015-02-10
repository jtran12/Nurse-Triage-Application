package com.example.hospitaltriageapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import backend.Patient;
import backend.RecordManager;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Handles the activity where the physician 
 * adds a new prescription to the patient.
 * 
 * This prescription becomes part of the patient's profile
 */
public class PhysicianAddNewPrescription extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_add_new_prescription);
		
		submitPatientRecordPrescription();
		
	}
	
	/** This method sends the Prescription information that Physician enters
	 *  about the patient to the RecordManager.
	 */
	private void submitPatientRecordPrescription(){
		
		 
		
		Button submitButton =  (Button)findViewById(R.id.submitbutton12);
		submitButton.setOnClickListener(new View.OnClickListener() {
		
			
			@Override
			public void onClick(View v) {
				RecordManager manager = RecordManager.getInstance(getApplicationContext());
		
				EditText PrescriptionName = (EditText) findViewById(R.id.editTextMedicationName); 
				String prescription = PrescriptionName.getText().toString();
		
				EditText PrescriptionInfo = (EditText) findViewById(R.id.editTextMedicationInfo); 
				String prescriptionInstructions = PrescriptionInfo.getText().toString();
		
				if(!prescription.isEmpty() && !prescriptionInstructions.isEmpty()) {
				String patientPrescription = "PRESCRIPTION: " + prescription + ' ' + "INFORMATION: " + prescriptionInstructions;
		
				Intent intent = getIntent();
					int ohip = intent.getIntExtra("the ohip", 0);
					Integer iInteger = Integer.valueOf(ohip);
					// Look up the patient using ohip number.
					Patient patient = manager.getPatient(iInteger);
					manager.recordPatientPrescription(patient, patientPrescription);
					finish();
				} else {
					TextView textView = (TextView) findViewById(R.id.errorPrescription);
					textView.setText("Invalid input");
				}
			}
		});
	}

	/** Inflate the menu; this adds items to the action bar if 
	 *  it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.physician_add_new_prescription, menu);
		return true;
	}

	

	/** Handle action bar item clicks here. The action bar will
	 * automatically handle clicks on the Home/Up button, so long
	 * as you specify a parent activity in AndroidManifest.xml.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
