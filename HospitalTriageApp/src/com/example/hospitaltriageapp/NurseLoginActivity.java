package com.example.hospitaltriageapp;

import backend.RecordManager;
import backend.UserManager;

import com.example.hospitaltriageapp.R;

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
 * 	Activity for Nurses to login.
 *  
 */
public class NurseLoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_login);
		
		nurseAttemptLogin();
	}

	/**
	 * Login activity for the Nurses
	 * Gets the username and password, checks if it is valid (correct username/password).
	 * if so, the next activity will be launch
	 * 
	 */
	public void nurseAttemptLogin(){
		
		Button buttonLogin = (Button) findViewById(R.id.nurseLoginButton1);
		buttonLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText userEditText = (EditText) findViewById(R.id.nurseEditText1);
				EditText passEditText = (EditText) findViewById(R.id.nurseEditText2);
				
				String username = userEditText.getText().toString();
				String password = passEditText.getText().toString();
				
				UserManager userManager = UserManager.getInstance(getApplicationContext());
				
				if (username.isEmpty() || password.isEmpty()) {
					TextView textViewEmpty = (TextView) findViewById(R.id.nurseErrorHandle);
					textViewEmpty.setText("Please enter your username and password!");
				} else if(userManager.nursesCheck(username, password)){
					Intent intent = new Intent(NurseLoginActivity.this, LoadPatientProfile.class);
					startActivity(intent);
					finish();
				}  else {
					TextView textView = (TextView) findViewById(R.id.nurseErrorHandle);
					textView.setText("The username and/or password you have entered is incorrect.");
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nurse_login, menu);
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
