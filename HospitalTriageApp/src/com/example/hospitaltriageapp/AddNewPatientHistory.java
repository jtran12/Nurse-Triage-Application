package com.example.hospitaltriageapp;

import com.example.hospitaltriageapp.R;

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
 * Activity that creates a new record of the patient.
 * 
 */
public class AddNewPatientHistory extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_patient_history);

		submitPatientRecordInfo();

	}

	/**
	 * This method sends the information that Nurse enters about the patient to
	 * the RecordManager.
	 * 
	 */
	private void submitPatientRecordInfo() {

		Button submitButton = (Button) findViewById(R.id.buttonAddRecord);
		submitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				RecordManager manager = RecordManager.getInstance(getApplicationContext());
				
				
				EditText hour = (EditText) findViewById(R.id.arrivalTimeEditText);
				String arrivalHour = hour.getText().toString();

				EditText date = (EditText) findViewById(R.id.editTextArrivalDate);
				String arrivalDate = date.getText().toString();

				EditText month = (EditText) findViewById(R.id.editTextArrivalMonth);
				String arrivalMonth = month.getText().toString();

				EditText year = (EditText) findViewById(R.id.editTextArrivalYear);
				String arrivalYear = year.getText().toString();

				// Patient vitals.
				EditText temperature = (EditText) findViewById(R.id.editTextupdateTemperature);
				String patientTemperature = temperature.getText().toString();

				EditText bloodPressure = (EditText) findViewById(R.id.editTextupdateBloodPressure);
				String patientBP = bloodPressure.getText().toString();

				EditText heartRate = (EditText) findViewById(R.id.editTextupdateHeartRate);
				String patientHR = heartRate.getText().toString();

				String patientArrivalDate = "  Arrival Date:  [Hour: " + arrivalHour + ' '
						+ "Day: " + arrivalDate + ' ' + "Month: "
						+ arrivalMonth + ' ' + "Year: " + arrivalYear + "] ";

				boolean flag = manager.checkMultipleStrings(arrivalHour, arrivalDate, arrivalMonth, arrivalYear, patientHR);
				boolean flag1 = manager.checkTempBlood(patientTemperature, patientBP);
				String s = flag ? "it worked" : "no it did not work";
				Log.d("checking", s);
				
				if(flag && flag1){
					Intent intent = getIntent();
					int ohip = intent.getIntExtra("the ohip", 0);
					Integer iInteger = Integer.valueOf(ohip);
					
					// Look up the patient using ohip number.
					Patient patient = manager.getPatient(iInteger);
					manager.recordPatientVital(patient, patientArrivalDate,
							"  Patient's condition:  [Temperature: " + patientTemperature + " ", " Blood Pressure: "
									+ patientBP + " ", " Heart Rate: "
									+ patientHR + "] ");
					
					// URGENCY CALCULATING
					String arrivaldate = arrivalYear + '-' + arrivalMonth + '-' + arrivalDate;
					//patient temperature
					double ptemp = Double.parseDouble(patientTemperature);
					//blood pressure 
					String[] bp = patientBP.split("/");
					//systolic bp
					int sbp = Integer.parseInt(bp[0]);
					//diastolic bp
					int dbp = Integer.parseInt(bp[1]);
					//heart rate for calculating urgency
					int heartrateURG = Integer.parseInt(patientHR);
					
					patient.setUrgency(patient.calculatehospitalPolicy(
							patient.getPatientAge(
									patient.getDateOfBirth(), arrivaldate), ptemp, sbp, dbp, heartrateURG));
					
					patient.sethasSeen(false);
					
					finish();
					
				} else {
					TextView textView = (TextView) findViewById(R.id.addNewPatientErrorHandle);
					textView.setText("One or more of your inputs is invalid");
				}
				
			}
		});
	}

	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.add_new_patient_history, menu);
		return true;
	}

	/**
	 * Handle action bar item clicks here. The action bar will automatically
	 * handle clicks on the Home/Up button, so long as you specify a parent
	 * activity in AndroidManifest.xml.
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
