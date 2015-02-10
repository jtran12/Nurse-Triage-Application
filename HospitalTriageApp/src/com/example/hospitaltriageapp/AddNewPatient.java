package com.example.hospitaltriageapp;

import backend.Patient;
import backend.RecordManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity that creates a new patient.
 * 
 */
public class AddNewPatient extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_patient);
		
		submitPatient();
	}
	
	/**
	 * This method sends the information that Nurse enters about this new patient to
	 * the RecordManager.
	 * 
	 */
	private void submitPatient() {

		Button submitButton = (Button) findViewById(R.id.buttonAddPatient);
		submitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				RecordManager manager = RecordManager.getInstance(getApplicationContext());
				
				
				EditText HealthCard = (EditText) findViewById(R.id.editTextHealthCard);
				String OHIP = HealthCard.getText().toString();

				EditText FullName = (EditText) findViewById(R.id.editTextName);
				String Name = FullName.getText().toString();

				EditText DateofBirth = (EditText) findViewById(R.id.editTextDOB);
				String DOB = DateofBirth.getText().toString();

				if(manager.checkPatientInfo(OHIP, Name, DOB) && !manager.checkOhipExist(OHIP)){
					manager.recordPatientRecord(OHIP, Name, DOB);
					Intent intent = new Intent(AddNewPatient.this, LoadPatientProfile.class);
					startActivity(intent);
					finish();
				} else if (!manager.checkOhipExist(OHIP) && !manager.checkPatientInfo(OHIP, Name, DOB)) {
					TextView textView = (TextView) findViewById(R.id.errorAddNewPatient);
					textView.setText("Your inputs are invalid");
				} else {
					TextView textView = (TextView) findViewById(R.id.errorAddNewPatient);
					textView.setText("The ohip number already exists!");
				}
				
				
			}
		});
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_patient, menu);
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
