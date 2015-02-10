package com.example.hospitaltriageapp;

import backend.UserManager;

import com.example.hospitaltriageapp.R;

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
 * Login menu for the Physician,
 * if successful, will move onto PhysicianLookUp where the physician
 * may enter the health card of the patient.
 * Otherwise the login will prompt another login attempt if unsuccessful login.
 *
 */
public class PhysicianLoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_login);
		
		physicianAttemptLogin();
	}
	/**
	 * Login activity for the physician
	 * Gets the username and password, checks if it is valid (correct username/password).
	 * if so, the next activity will be launch
	 */
	
	public void physicianAttemptLogin(){
		
		Button buttonLogin = (Button) findViewById(R.id.physician_login);
		buttonLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText userEditText = (EditText) findViewById(R.id.physicianUserEditText);
				EditText passEditText = (EditText) findViewById(R.id.physicianPassEditText);
				
				String username = userEditText.getText().toString();
				String password = passEditText.getText().toString();
				
				UserManager userManager = UserManager.getInstance(getApplicationContext());
				
				if (username.isEmpty() || password.isEmpty()) {
					TextView textViewEmpty = (TextView) findViewById(R.id.doctorErrorHandle);
					textViewEmpty.setText("Please enter your username and password!");
				} else if(userManager.doctorsCheck(username, password)){
					Intent intent = new Intent(PhysicianLoginActivity.this, PhysicianLookUp.class);
					startActivity(intent);
					finish();
				}  else {
					TextView textView = (TextView) findViewById(R.id.doctorErrorHandle);
					textView.setText("The username and/or password you have entered is incorrect.");
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.physician_login, menu);
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
