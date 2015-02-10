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
 * Activity that creates a new record of diagnostic time 
 * with a doctor for the patient.
 * 
 */
public class DiagnoseTime extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diagnose_time);
		
		submitDiagnosticTime();
	}

	/**
	 * This method sends the information that Nurse enters about the patient to
	 * the RecordManager, in this case, the diagnostic time with a doctor for 
	 * the patient.
	 * 
	 */
	private void submitDiagnosticTime() {
		
		Button submitButton = (Button) findViewById(R.id.buttonrecordandviewtime);
		submitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				RecordManager manager = RecordManager.getInstance(getApplicationContext());
				
				EditText time = (EditText) findViewById(R.id.editTextdiagnosetime);
				String dtime = time.getText().toString();

				EditText date = (EditText) findViewById(R.id.editTextdiagnosedate);
				String ddate = date.getText().toString();
				
				if (dtime.isEmpty() || ddate.isEmpty() || (RecordManager.countOccurrences(ddate, '/') != 2)){
					TextView textView = (TextView) findViewById(R.id.errorTextPhysicianInputCheck);
					textView.setText("Invalid input!");
				} else {
				// Update the doctorCheckPatientMap in RecordManager		
				Intent intent = getIntent();
				int ohip = intent.getIntExtra("the ohip", 0);
				Integer iInteger = Integer.valueOf(ohip);
				
				// Update the doctorCheckPatientMap in RecordManager
				manager.recordSeenDoctorDate(iInteger, "Date seen by a doctor: " + ddate + ", Time seen by a doctor: " + dtime);
				
				// Look up the patient using ohip number.
				Patient patient = manager.getPatient(iInteger);
				
				
				if (patient.gethasSeen() == true){
					patient.sethasSeen(false);
				}
				
				if (dtime != "" && ddate != ""){
					patient.sethasSeen(true);
				} 
				finish();
				}
			}
		});
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.diagnose_time, menu);
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
