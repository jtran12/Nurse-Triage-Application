package com.example.hospitaltriageapp;

import java.util.ArrayList;
import java.util.Collections;

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
 * Class load the patient profile from the backend.
 * 
 *
 */
public class LoadPatientProfile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_patient_profile);
		
	}
	

	protected void onResume() {
		super.onResume();
		
		setUpLoadPatientProfileButton();
		nurseLogOut();
	}
	
	/**
	 * Methods for nurse the log out of the activity.
	 * 
	 *
	 */
	public void nurseLogOut() {
		// TODO Auto-generated method stub
		Button buttonLogOut = (Button)findViewById(R.id.button_logout);
		buttonLogOut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoadPatientProfile.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
	}

	/**
	 * This will make sure that when the button "Load Patient Profile" is
	 * clicked the health card in the field will be checked among the list of
	 * patients the intent will pass to DisplayPatientProfile activity to be
	 * utilized
	 * 
	 */
	private void setUpLoadPatientProfileButton() {

		Button loadprofile = (Button) findViewById(R.id.lookupbutton);
		loadprofile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// starts new activity
				RecordManager manager = RecordManager.getInstance(getApplicationContext());
				EditText patientuhip = (EditText) findViewById(R.id.enteruhipeditText);
				String uhip = patientuhip.getText().toString();
				if (uhip.isEmpty()){
					TextView emptyError = (TextView) findViewById(R.id.errorHandleTextView);
					emptyError.setText("Please enter an OHIP number");
					
				} else if(manager.checkString(uhip)){
					
					Integer iInteger = Integer.valueOf(uhip);
					int useruhip = Integer.parseInt(uhip);
				
					if(manager.checkOhipExist(String.valueOf(iInteger))){
				
						Patient patient = manager.getPatient(iInteger);
						String dateBirth = patient.getDateOfBirth();
						String name = patient.getPatientName();

						Intent intent = new Intent(LoadPatientProfile.this,
						DisplayPatientProfile.class);
						intent.putExtra("the uhip number", useruhip);
						intent.putExtra("DateOfBirth", dateBirth);
						intent.putExtra("Patient Name", name);

						startActivity(intent);
						
					} else {
						TextView textView = (TextView) findViewById(R.id.errorHandleTextView);
						textView.setText("Your username and/or password are incorrect");
					}
				}else {
					TextView textView = (TextView) findViewById(R.id.errorHandleTextView);
					textView.setText("Your username and/or password are incorrect");
				}
			}
		});
	}

	/**
	 * registerPerson gets the ArrayList of patients from RecordManager. with
	 * that ArrayList, it will be changed from an ArrayList of patients, to an
	 * ArrayList of Strings containing patient info(health card number, birthday,
	 * name) This will then be passed on to ListofPatients activity to be
	 * displayed as a list of patients
	 * 
	 * @param view
	 *            switches the view from this to the other layout
	 */
	public void registerPerson(View view) {
		Intent intentList = new Intent(this, ListofPatients.class);

		// Get the ArrayList of patients
		RecordManager manager = RecordManager
				.getInstance(getApplicationContext());
		ArrayList<Patient> patientList = manager.getPatientRecordList();
		ArrayList<Patient> urgentPatientList = new ArrayList<Patient>();
		
		ArrayList<String> available_patient_list = new ArrayList<String>();
		ArrayList<String> string_patient_list = new ArrayList<String>();
		
		Log.d("Patient string", patientList.get(0).toString());
		// Change ArrayList of patients to ArrayList of Strings
		for (Patient p : patientList) {
			available_patient_list.add(p.toString());
			if (p.gethasSeen() == false){
			urgentPatientList.add(p);
			}
		}
		Collections.sort(urgentPatientList);
		
		for (Patient sortedP : urgentPatientList){
			string_patient_list.add(sortedP.toString());
		}

		Collections.reverse(string_patient_list);
		// Pass the ArrayList from LoadPatientActivity to
		// ListOfPatientInfoActivity
		intentList.putExtra("list of patients", string_patient_list);

		startActivity(intentList);

	}
	
	/**
	 * Methods that MOVES TO ACTIVITY THAT ADDS THE NEW PATIENT.
	 * 
	 */
	public void AddPatient(View view){
		Intent intentAddPatient = new Intent(this, AddNewPatient.class);
		startActivity(intentAddPatient);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load_patient_profile, menu);
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
