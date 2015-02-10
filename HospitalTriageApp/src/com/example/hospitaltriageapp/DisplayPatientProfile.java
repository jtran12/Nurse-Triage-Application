package com.example.hospitaltriageapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
import android.widget.TextView;

/**
 * Activity for displaying the current profile of a patient.
 * 
 */
public class DisplayPatientProfile extends Activity {

	/**
	 * Method that displays the data of the patient whose uhip has been written.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_patient_profile);

		// Get the intent that started.
		Intent intent = getIntent();
		int uhip = intent.getIntExtra("the uhip number", 0);
		String dateBirth = intent.getStringExtra("DateOfBirth");
		String patientName = intent.getStringExtra("Patient Name");

		// Display the values to the screen.
		TextView displayuhip = (TextView) findViewById(R.id.patientuhiptextView);
		displayuhip.setText("" + uhip);

		TextView displayDOB = (TextView) findViewById(R.id.textViewpatientdob);
		displayDOB.setText(dateBirth);

		TextView displayName = (TextView) findViewById(R.id.textViewUserName);
		displayName.setText(patientName);

		setUpCheckPatientHistoryButton();
		setUpListSeenByDoctor();
		setUpNewHistoryButton();
		checkWithDoctorButton();
		
		nurseLogOut();

	}
	
	/**
	 * Methods that displays the data of the patient whose UHIP has been written.
	 * 
	 */
	private void setUpListSeenByDoctor() {
		Button check = (Button) findViewById(R.id.buttonSeenByDoctor);
		check.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DisplayPatientProfile.this,
						DisplayDatesSeenDoctor.class);

				RecordManager manager = RecordManager
						.getInstance(getApplicationContext());
				HashMap<Integer, ArrayList<String>> visitDoctorMap = manager
						.getDoctorCheckPatientMap();
				Intent visitIntent = getIntent();
				int uhip = visitIntent.getIntExtra("the uhip number", 0);
				Integer ohip = Integer.valueOf(uhip);
				ArrayList<String> visitDoctorList = visitDoctorMap.get(ohip);
				if (visitDoctorList != null){
					Collections.reverse(visitDoctorList);
				}
				intent.putExtra("list of doctor seen", visitDoctorList);

				startActivity(intent);
			}

		});

	}

	/**
	 * Method that logs the user out, in this case, the nurse.
	 * 
	 */
	public void nurseLogOut() {
		// TODO Auto-generated method stub
		Button buttonLogOut = (Button)findViewById(R.id.button_logout1);
		buttonLogOut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DisplayPatientProfile.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
	}

	/**
	 * Method that displays the medical history of the patient.
	 * 
	 */
	private void setUpCheckPatientHistoryButton() {
		Button check = (Button) findViewById(R.id.patientmedicalhistorybutton);
		check.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DisplayPatientProfile.this,
						PatientMedicalHistory.class);

				RecordManager manager = RecordManager
						.getInstance(getApplicationContext());
				HashMap<Integer, ArrayList<String>> visitMap = manager
						.getVisitRecordList();
				Intent visitIntent = getIntent();
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

	/**
	 * Method that creates a new record of the patient.
	 * 
	 */
	private void setUpNewHistoryButton() {
		Button addnew = (Button) findViewById(R.id.createhistorybutton);
		addnew.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				int uhip = intent.getIntExtra("the uhip number", 0);
				// Pass the ohip number to addNewPatientHistory
				Intent i = new Intent(DisplayPatientProfile.this,
						AddNewPatientHistory.class);
				i.putExtra("the ohip", uhip);
				startActivity(i);
			}

		});
	}
	
	/**
	 * Method that let patient check with doctors.
	 * 
	 */
	private void checkWithDoctorButton() {
		
		Button doctorcheckbutton = (Button)findViewById(R.id.buttoncheckwithdoc);
		doctorcheckbutton.setOnClickListener(new View.OnClickListener() {
			
			/**
			 * Method that creates a new record of the patient.
			 * 
			 */
			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				int uhip = intent.getIntExtra("the uhip number", 0);
				// Pass the ohip number to addNewPatientHistory
				Intent in = new Intent(DisplayPatientProfile.this, DiagnoseTime.class);
				in.putExtra("the ohip", uhip);
				startActivity(in);
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
