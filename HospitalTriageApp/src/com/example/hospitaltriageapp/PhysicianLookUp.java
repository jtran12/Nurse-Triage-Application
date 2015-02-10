package com.example.hospitaltriageapp;

import backend.Patient;
import backend.RecordManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * PhysicianLookUp will Lookup a patient by their health card and
 * will be fetched from the available list of patients.
 * When successful, PhysicianDisplayPatientProfile will be displayed.
 *
 */
public class PhysicianLookUp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_look_up);
		
		setUpLoadPatientProfileButton();
		physicianLogOut();
	}
	/**
	 * Logout for physician
	 * When user clicks the logout, the screen will be moved back to beginning screen (login screen).
	 */
	public void physicianLogOut() {
		// TODO Auto-generated method stub
		Button buttonLogOut = (Button)findViewById(R.id.button_logout2);
		buttonLogOut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PhysicianLookUp.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
	}
	/**
	 * When physician enters the healthcard of the patient,
	 * this sets up the loading of the patient information (health card, date of birth, name)
	 * This will then move to the activity where it displays 
	 * the patient information and the list of possible actions to take.
	 */
	
	private void setUpLoadPatientProfileButton() {
		
		Button loadprofile =  (Button)findViewById(R.id.physician_button);
		loadprofile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		// starts new activity
		RecordManager manager = RecordManager.getInstance(getApplicationContext());
		EditText patientuhip = (EditText) findViewById(R.id.physician_uhip_field); 
		String uhip = patientuhip.getText().toString();

		
		if(manager.checkString(uhip) && manager.checkOhipExist(uhip)) {
		
			int useruhip = Integer.parseInt(uhip);
			Integer iInteger = Integer.valueOf(useruhip);
			Patient patient = manager.getPatient(iInteger);
			String dateBirth = patient.getDateOfBirth();
			String name = patient.getPatientName();
			Intent intent = new Intent(PhysicianLookUp.this, PhysicianDisplayPatientProfile.class);
			intent.putExtra("the uhip number", useruhip);
			intent.putExtra("DateOfBirth", dateBirth);
			intent.putExtra("Patient Name", name);
			startActivity(intent); 
		} else {
			TextView textView = (TextView) findViewById(R.id.doctorEnterOhip);
			textView.setText("Invalid input");
		}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.physician_look_up, menu);
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
