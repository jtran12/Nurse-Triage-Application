package backend;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * This is a singleton class that deals with reading data from
 * usernames_passwords.txt and stores the login data.
 * 
 */
public class UserManager {
	
	/**
	 * instance  a UserManager object that is used in singleton pattern.
	 * context   a Context attribute that gives the most recent state of the app.
	 * nurseMap  a HashMap that has the nurse's username as key and the nurse's password as value.
	 * doctorsMap a HahsMap that has the doctor's username as key and the nurse's password as value.
	 */
	private static UserManager instance;
	private static Context context;
	private HashMap<String, String> nursesMap;
	private HashMap<String, String> doctorsMap;
	
	/**
	 * A private constructor that can only be called locally.
	 * Instantiates attributes nurseMap and doctorsMap.
	 */
	private UserManager() {
		nursesMap = new HashMap<String, String>();
		doctorsMap = new HashMap<String, String>();
		try {
			loadLoginInfo();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Get the instance of the UserManager. As a way to instantiate a class 
	 * implemented using the singleton pattern.
	 * @param ctx the current context of the application.
	 * @return instance of the UserManager class.
	 */
	public static UserManager getInstance(Context ctx) {
		if (instance == null) {
			context = ctx;
			instance = new UserManager();
		}
		return instance;
	}

	/**
	 *  Reads the data from usernames_passwords.txt
	 * @throws FileNotFoundException
	 * 
	 */
	private void loadLoginInfo() throws FileNotFoundException {

		String line;
		AssetManager assetManager = context.getAssets();
		InputStream fileByte = null;
		BufferedReader reader = null;

		try {
			fileByte = assetManager.open("usernames_passwords.txt");
			reader = new BufferedReader(new InputStreamReader(fileByte));
			line = reader.readLine();
			while (line != null) {
				String[] loginInfo = line.split(",");
				String username = loginInfo[0];
				String password = loginInfo[1];
				String occupation = loginInfo[2];
				if (occupation.equals("nurse")) {
					this.nursesMap.put(username, password);
				}
				if (occupation.equals("physician")) {
					this.doctorsMap.put(username, password);
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Check if the username and password are valid, i.e exist in the username and password
	 * record for nurses.
	 * @param username String object of the username of the nurse.
	 * @param password String object of the password of the nurse.
	 * @return true if the username and password are valid. false otherwise.
	 */
	public boolean nursesCheck(String username, String password) {
		Set<String> keys = nursesMap.keySet();
		Collection<String> values = nursesMap.values();
		boolean user_check = (keys.contains(username)) ? true : false; // conditional
																		// ternary
																		// operator
		boolean pass_check = (values.contains(password)) ? true : false;
		return (user_check && pass_check);
	}

	/**
	 * Check if the username and password are valid, i.e exist in the username and password
	 * record for doctors.
	 * @param username String object of the username of physician.
	 * @param password String object of the password of physician.
	 * @return true if the username and password are valid in the physician username and password
	 *         record. false otherwise.
	 */
	public boolean doctorsCheck(String username, String password) {
		Set<String> keys = doctorsMap.keySet();
		Collection<String> values = doctorsMap.values();
		boolean user_check = (keys.contains(username)) ? true : false; // conditional
																		// ternary
																		// operator
		boolean pass_check = (values.contains(password)) ? true : false;
		return (user_check && pass_check);
	}
	
	/**
	 * 
	 * @return HashMap nursesMap.
	 */
	public HashMap<String, String> getNurses_list() {
		return nursesMap;
	}
	
	/**
	 * 
	 * @return doctorsMap.
	 */

	public HashMap<String, String> getDoctors_list() {
		return doctorsMap;
	}

}
