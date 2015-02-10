package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * Usage: RecordManager manager =
 * RecordManager.getInstance(getApplicationContext()); ArrayList<Patient>
 * list = manager.getPatientRecords(); RecordManager manager =
 * RecordManager.getinstance();
 * Class features:
 * It is implemented using the singleton design pattern and it manages
 * data about the patients.
 */
public class RecordManager {
	
	/**
	 * instance           		it is an instance of the the class RecordManager. Used for singleton.
	 *  context            	 	a Context variable that keeps track of the state of the android app.
	 *  patientRecordList   	ArrayList of Patient objects.
	 *  patientOhipList    		ArrayList of ohip numbers of patients that are in the record of the hospital.
	 *  visitRecordList    	 	A HashMap where the keys are ohip numbers of patients and values are the ArrayLists
	 * 							of Strings where each string contains information on the date of patient arrival,
	 * 							patient's ohip number and his/her vital signs.
	 *  patientMap         		A HashMap where the keys are ohip numbers and the values are the Patient objects
	 * 								  	that associate with the ohip numbers.
	 *  doctorCheckPatientMap  	A HashMap where the keys are ohip numbers and the values are ArrayLists 
	 * 									 of strings where each string represents date and time when the doctor has
	 * 									 seen the patient at the hospital.
	 */

	private static RecordManager instance;
	private static Context context;
	private static ArrayList<Patient> patientRecordList;
	private static ArrayList<Integer> patientOhipList; 
	private static HashMap<Integer, ArrayList<String>> visitRecordList;
	private static HashMap<Integer, Patient> patientMap; // feature 2
	private static HashMap<Integer, ArrayList<String>> doctorCheckPatientMap;
	
	/**
	 * A private constructor that can only be called locally which constructs an 
	 * instance of RecordManager. It call several important methods and 
	 * instantiates patientOhipList and patientMap objects.
	 */
	private RecordManager() {
		patientOhipList = new ArrayList<Integer>();
		patientRecordList = initializePatientRecords();
		visitRecordList = intitializeVisitRecordList();
		patientMap = new HashMap<Integer, Patient>();
		loadPatientMap();
		doctorCheckPatientMap = new HashMap<Integer, ArrayList<String>>();
	}
	
	/**
	 * Method used to instantiate the RecordManger class.
	 * @param ctx   Context variable that is used to retrieve the current state of the application
	 * 				This attribute context will be updated by ctx.
	 * @return      instance of RecordManager class.
	 */
	public static RecordManager getInstance(Context ctx) {
		if (instance == null) {
			context = ctx;
			instance = new RecordManager();
		}
		return instance;
	}

	/**
	 * Loads the patientMap with patient objects from patientRecordList.
	 */
	public void loadPatientMap() {
		for (Patient p : patientRecordList) {
			if (!patientMap.containsValue(p)) {
				patientMap.put(p.getOhipNumber(), p);
			} else {
				continue;
			}

		}
	}
	
	/**
	 * @param   ohip an int that represents the ohip number of the patient.
	 * @return  A Patient object of the patient that has this ohip number. 
	 */
	public Patient getPatient(int ohip) {
		return patientMap.get(ohip);
	}
	
	/**
	 * Load data from existing patient_records.txt and read it line by line.
	 * Store each patient's info into Patient objects. Construct an
	 * ArrayList of Patient objects with info loaded.
	 * @return ArrayList of Patient objects from data in the file patient_records.txt. 
	 */
	private ArrayList<Patient> initializePatientRecords() {

		ArrayList<Patient> list = new ArrayList<Patient>();
		String line;
		FileInputStream fileByte = null;
		BufferedReader reader = null;
		try {
			String fileName = "patient_records.txt";
			File patientFile = context.getFileStreamPath(fileName);
			if (patientFile.exists()) {
				
				try {
					fileByte = new FileInputStream(patientFile);
					reader = new BufferedReader(new InputStreamReader(fileByte));
					line = reader.readLine();
					while (line != null) {
						// Process the patient_record.txt line by line
						// pass in i.e. "324231,Jie Wu,1992-10-12"
						Patient patient = new Patient(line);
						list.add(patient);
						String[] lineList = line.split(",");
						Integer ohip = Integer.valueOf(lineList[0]);
						patientOhipList.add(ohip);
						line = reader.readLine();
				
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					if (fileByte != null) {
						fileByte.close();
					}
					reader.close();
				}
			} else {
				// Create it for the first time TODO: In next phase
				AssetManager manager = context.getAssets();
				InputStream in = null;
				OutputStream out = null;
				in = manager.open(fileName);
				patientFile = new File(context.getFilesDir(), fileName);
				out = new FileOutputStream(patientFile);

				byte[] buffer = new byte[1024];
				int read;
				while ((read = in.read(buffer)) != -1) {
					out.write(buffer, 0, read);
				}

				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;
				// Rerun method to return list properly now. Thanks based
				// recursion
				return initializePatientRecords();
			}
		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
		return list;
	}
	
	/**
	 * Add a new patient to the record by writing data in the patient_records.txt.
	 * @param ohip OHIP number of the patient as String.
	 * @param name Name of the patient as String.
	 * @param birthdate birthday of the patient as String. 
	 */
	public void recordPatientRecord(String ohip, String name, String birthdate) {
		String fileName = "patient_records.txt";
		try {
			FileOutputStream fileWrite = context.openFileOutput(fileName,
					Context.MODE_APPEND);
			OutputStreamWriter osw = new OutputStreamWriter(fileWrite);
			String line = "\n" + ohip + "," + name + ","
					+ birthdate;
			osw.write(line);
			osw.flush();
			osw.close();
			// Keeps the HashMap updated.
			this.setPatientRecordList(initializePatientRecords());
			this.loadPatientMap();
			

			// initializePatientRecords(); // you load the patientRecords
			// hashmap once you finish writing.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Read file methods for visit records.
	   Used to initialize visitRecords.
	   EITHER get visit_records.txt if it exists or create it for the first time
	   if it doesn't
	 * @return HashMap of patient's visit information where the key is the ohip number
	 * 		   of the patient and the value is an ArrayList of Strings where each String
	 * 		   has date of patient's arrival, his/her ohip number and vital signs.
	 */
	public HashMap<Integer, ArrayList<String>> intitializeVisitRecordList() {
		HashMap<Integer, ArrayList<String>> visitMap = new HashMap<Integer, ArrayList<String>>();

		// Get files directory (NOT ASSETS)
		String fileName = "visit_records.txt";
		File file = context.getFileStreamPath(fileName);
		String line;
		FileInputStream fileByte = null;
		BufferedReader reader = null;

		try {
			if (file.exists()) {
				// If file exists then return a hashmap of current content
				fileByte = new FileInputStream(file);

				reader = new BufferedReader(new InputStreamReader(fileByte));
				line = reader.readLine();

				while (line != null) {
					// Do something with line
					String[] visitDetails = line.split(","); // [0]=OHIP,
																// [1]=DATE,
																// [2]=TEMP,
																// [3]=BLOOD,
																// [4]=HEART
					int ohip = Integer.parseInt(visitDetails[0]);

					// Get patients current visit record
					ArrayList<String> visitRecord = visitMap.get(ohip);
					if (visitRecord == null) {
						visitRecord = new ArrayList<String>();
						visitRecord.add(line);
						visitMap.put(ohip, visitRecord);
					} else {
						visitRecord.add(line);
					}

					// Go to next line to deal with next visit
					line = reader.readLine();
				}
			} else {
				// If file does not exist, make a new file and initialize
				// contents
				File visitFile = new File(context.getFilesDir(), fileName);
				if (visitFile.createNewFile()) {
					return intitializeVisitRecordList(); // Creates the file and
															// reruns the method
															// to the
															// file.exists
															// conidtion.
				} else {
					return null;
				}
			}
			fileByte.close();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Finally assign map and return it
		visitRecordList = visitMap;
		return visitMap;

	}

	/**
	 * Write data in the visit_records.txt
	 * @param patient    		A Patient object that associates with patient that you are calling
	 * 					 		method for.
	 * @param dateOfVisit       String variable that represents the date and time of the patient arriving at the hospital.
	 * @param temperature       String variable that represents the body temperature of the patient.
	 * @param blood             String variable that represents the blood pressure measured for the patient.
	 * @param heartrate         String variable that represents the heart rate measured for the patient.
	 */
	public void recordPatientVital(Patient patient, String dateOfVisit,
			String temperature, String blood, String heartrate) {
		String fileName = "visit_records.txt";
		try {
			FileOutputStream fileWrite = context.openFileOutput(fileName,
					Context.MODE_APPEND);
			OutputStreamWriter osw = new OutputStreamWriter(fileWrite);
			String line = patient.getOhipNumber() + "," + dateOfVisit + ","
					+ temperature + "," + blood + "," + heartrate + "\n";
			osw.write(line);
			osw.flush();
			osw.close();
			// Keeps the HashMap updated.
			this.setVisitRecordList(intitializeVisitRecordList());

			// initializePatientRecords(); // you load the patientRecords
			// hashmap once you finish writing.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * The method writes data to the visit_records.txt
	 * @param patient               A Patient object of the target patient.
	 * @param patientPrescription   String object that represents the prescription the
	 * 							    doctor give to the patient.
	 */
	public void recordPatientPrescription(Patient patient, String patientPrescription) {
		String fileName = "visit_records.txt";
		try {
			FileOutputStream fileWrite = context.openFileOutput(fileName,
					Context.MODE_APPEND);
			OutputStreamWriter osw = new OutputStreamWriter(fileWrite);
			String line = patient.getOhipNumber() + "," + patientPrescription + "\n";
			osw.write(line);
			osw.flush();
			osw.close();
			// Keeps the HashMap updated.
			this.setVisitRecordList(intitializeVisitRecordList());

			// initializePatientRecords(); // you load the patientRecords
			// hashmap once you finish writing.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Method that retrieves the patient's visit information including previous visits.
	 * @param ohip    Ohip number of the target patient as an int.
	 * @return        ArrayList of strings where each string represents the visit information
	 * 				  of the target patient.
	 */
	public ArrayList<String> getVisitRecordsForPatient(int ohip) {
		ArrayList<String> visitRecord = visitRecordList.get(ohip);
		return visitRecord;
	}
	
	/**
	 * Method that check if the String satisfies the condition where only digits exist
	 * in the string, not letters or other symbols in the string
	 * @param text    Any String object. 
	 * @return        boolean value of whether the string can convert to Integer or not.
	 */
	public boolean checkString(String text){
		if(text.isEmpty()){
			return false;
		} else {
		for(int i = 0; i < text.length(); i++){
			if (!Character.isDigit(text.charAt(i))){
				return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Invoke the checkString method on each of the parameters.
	 * @param a Any String object.
	 * @param b Any String object.
	 * @param c Any String object.
	 * @param d Any String object.
	 * @param e Any String object.
	 * @return  true if every parameter satisfies the condition in checkString method.
	 * 		    false otherwise.
	 */
	// Check if multiple strings contain a character that is not a number.
	public boolean checkMultipleStrings(String a, String b, String c, String d, String e){
		boolean flag = (checkString(a) && checkString(b) && checkString(c) && checkString(d) && checkString(e)) ? true : false;
		return flag;
	}
	
	/**
	 * Check if the string inputs satisfy the condition for temperature and blood pressure format.
	 * @param temperature Any String object.
	 * @param BP Any String object.
	 * @return true if temperature can be converted to a double and blood pressure is written
	 *         in the format "number/number" with at least a slash in the input.
	 */
	public boolean checkTempBlood(String temperature, String BP){
		if (temperature.isEmpty() || BP.isEmpty()){
			return false;
		} else {
			try
			{
			  Double.parseDouble(temperature);
			}
			catch(NumberFormatException e)
			{
			  //not a double
				return false;
			}
			if(!(countOccurrences(BP, '/') == 1)){
				return false;
			} else {
				int idx = BP.indexOf("/");
				int stringSize = BP.length();
				if(idx == (stringSize - 1)){
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * Check if the ohip number exists in the record.
	 * @param ohip  String object of ohip number.
	 * @return	true if the ohip number exists in the record. false otherwise.
	 */
	public boolean checkOhipExist(String ohip) {
		Integer uhip = Integer.valueOf(ohip);
		if (patientOhipList.contains(uhip)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Record the date the patient has seen by a doctor.
	 * @param ohip Integer object that represents the ohip number of the patient.
	 * @param date String object that represents the date the patient has seen by a doctor.
	 */
	public void recordSeenDoctorDate(Integer ohip, String date){
		if(RecordManager.doctorCheckPatientMap.containsKey(ohip)){
			ArrayList<String>arrayList = doctorCheckPatientMap.get(ohip);
			arrayList.add(date);
			doctorCheckPatientMap.put(ohip, arrayList);
		} else {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(date);
			doctorCheckPatientMap.put(ohip, arrayList);
		}
		
	}
	
	/**
	 * Check if the the parameters are valid
	 * @param ohip  ohip number of the patient.
	 * @param name  name of the patient.
	 * @param DOB   date of birth of the patient.
	 * @return   true if all strings are not empty, if ohip number's size is exactly 6
	 * 			and date of birth contains exactly "-" twice in the example format of "dd-mm-yyyy"
	 */
	public boolean checkPatientInfo(String ohip, String name, String DOB){
		if (ohip.isEmpty() || name.isEmpty() || DOB.isEmpty()) {
			return false;
		} else if (ohip.length() != 6) {
			return false;
		} else if (countOccurrences(DOB, '-') != 2){
			return false;
		} else {
			return true;
		}
		
	}
	
	/**
	 * Count the number of occurrences of a character target in the String DOB.
	 * @param DOB Any String object.
	 * @param target the character that you are interested in finding out how many times it occurred.
	 * @return the number of occurrences of a character target in the String DOB.
	 */
	public static int countOccurrences(String DOB, char target){
	    int occurrence = 0;
	    for (int i=0; i < DOB.length(); i++)
	    {
	        if (DOB.charAt(i) == target)
	        {
	        	occurrence = occurrence + 1;
	        }
	    }
	    return occurrence;
	}

	// Getters and setters
    /**
     * set the VisitRecordList with the given input
     * @param visitRecordList new ArrayList of the visitRecordList according to its definition.
     */
	private void setVisitRecordList(
			HashMap<Integer, ArrayList<String>> visitRecordList) {
		RecordManager.visitRecordList = visitRecordList;
	}
	
	/**
	 * set the PatientRecordList with the given input
	 * @param patientRecordList new ArrayList of patient objects.
	 */
	private void setPatientRecordList(ArrayList<Patient> patientRecordList){
		RecordManager.patientRecordList = patientRecordList;
	}
	
	/**
	 * 
	 * @return the arraylist of patientRecordList.
	 */
	public ArrayList<Patient> getPatientRecordList() {
		return patientRecordList;
	}
	
	/**
	 * 
	 * @return the HashMap attribute visitRecordList.
	 */
	public HashMap<Integer, ArrayList<String>> getVisitRecordList() {
		return visitRecordList;
	}
	
	/**
	 * 
	 * @return the HashMap attribute of doctorCheckPatientMap.
	 */
	public HashMap<Integer, ArrayList<String>> getDoctorCheckPatientMap(){
		return doctorCheckPatientMap;
	}

}
