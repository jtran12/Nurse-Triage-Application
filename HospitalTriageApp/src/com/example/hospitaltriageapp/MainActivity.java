package com.example.hospitaltriageapp;

import java.util.ArrayList;

import backend.Patient;
import backend.RecordManager;
import backend.UserManager;

import com.example.hospitaltriageapp.R;
import com.example.hospitaltriageapp.R.id;
import com.example.hospitaltriageapp.R.layout;
import com.example.hospitaltriageapp.R.menu;
import com.example.hospitaltriageapp.ListofPatients;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * class MainActivity contains intents and onClick methods for the buttons.
 * Activity that initiate the welcome UI. Allow the user to choose Identity (doctor or nurse) 
 * to log in the Application. 
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startNurseLoginActivity();
		startPhysicianLoginActivity();
	}
	
	/**
	 *	Method that initializes the nurse login UI. 
	 *
	 */
	public void startNurseLoginActivity(){
		
		Button buttonNurse = (Button) findViewById(R.id.nurse_button);
		buttonNurse.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, NurseLoginActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 *	Method that initializes the physicians login UI. 
	 *
	 */
	public void startPhysicianLoginActivity(){
		
		Button buttonPhysician = (Button) findViewById(R.id.button_physician);
		buttonPhysician.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, PhysicianLoginActivity.class);
				startActivity(i);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
