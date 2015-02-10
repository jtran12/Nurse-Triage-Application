package backend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.HashMap;

/**
 * Patient class has a single patient including information such as, health card
 * number, patientName, dateOfBirth, and a stack to include the old/new patient
 * information
 */
public class Patient implements Comparator<Patient>, Comparable<Patient>{

	/**
	 *  ohipNumber   the ohip number of the patient as int.
	 *  patientName  the name of the patient as String.
	 *  dateOfBirth  the date of birth of the patient as String.
	 *  urgency      the urgency level of the patient calculated by HospitalPolicy.
	 *  hasSeen      the boolean value checking whether the patient has seen by a doctor.
	 */
	private int ohipNumber;
	private String patientName;
	private String dateOfBirth;
	private int urgency;
	private boolean hasSeen;

	/**
	 * Constructs a Patient object using the string line. It retrieves the ohip number,
	 * patient's name and the date of the birth from the string line and store them as 
	 * attributes.
	 * 
	 * @param line a string in the form of "ohip number,patientName,dateOfBirth"
	 */
	// pass in i.e. "324231,Jie Wu,1992-10-12"
	public Patient(String line) {

		String[] patientString = line.split(",");
		setOhipNumber(Integer.parseInt(patientString[0]));
		setPatientName(patientString[1]);
		setDateOfBirth(patientString[2]);
		urgency = 0;
		hasSeen = false;

	}

	// Getters and setters.
	public int getOhipNumber() {
		return ohipNumber;
	}

	public void setOhipNumber(int ohipNumber) {
		this.ohipNumber = ohipNumber;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setUrgency(int urgency){
		this.urgency = urgency;
	}
	
	public int getUrgency(){
		return urgency;
	}
	
	public void sethasSeen(boolean value){
		this.hasSeen = value;
	}
	
	public boolean gethasSeen(){
		return hasSeen;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	/* Overrides the toString() method.
	 * @return a string representation of the patient object in the form of
	 * 			"Ohip: 1111, Name: Bob, Date of Birth: 11/11/1111, [Urgency Level]: 0"
	 */
	@Override
	public String toString() {

		return "Ohip: " + getOhipNumber() + ", Name: " + getPatientName()
				+ ", Date of Birth: " + getDateOfBirth() + ", [Urgency Level]: " + getUrgency();
	}
	
	/**
	 *  Calculate the age of the patient, the difference between arrival time.
	 * and date of birth, both in yyyy-mm-dd format. 
	 * @param dob         date of birth of the patient that is being passed as a string.
	 * @param arrivaldate arrival date of the patient that is a string type.
	 * @return            the age of the patient at the arrival date.
	 */ 
	public int getPatientAge(String dob, String arrivaldate){

		String[] doblist = dob.split("-");
		String[] adlist = arrivaldate.split("-");
		
		Calendar dobInC = Calendar.getInstance();
		int dyear = Integer.parseInt(doblist[0]);
		int dmonth = Integer.parseInt(doblist[1]);
		int ddate = Integer.parseInt(doblist[2]);
		
		Calendar adInC = Calendar.getInstance();
		int ayear = Integer.parseInt(adlist[0]);
		int amonth = Integer.parseInt(adlist[1]);
		int adate = Integer.parseInt(adlist[2]);
		
		dobInC.set(dyear, dmonth, ddate);
		adInC.set(ayear, amonth, adate);
		
		int age = adInC.get(Calendar.YEAR) - dobInC.get(Calendar.YEAR);
		if (adInC.get(Calendar.DAY_OF_YEAR) < dobInC.get(Calendar.DAY_OF_YEAR)){
		age--;
		}
		return age;
	}
	
	/**
	 * Calculate and return the urgency level of the patient based on the vital signs
	 * of the patient.
	 * 
	 * @param  patientage   the age of the patient that is int type.
	 * @param  ptemp        the temperature of the patient as a double.
	 * @param  sbp          systolic blood pressure as an int.
	 * @param  dbp          diastolic blood pressure as an int.
	 * @param  heartrate    heart rate of the patient as an int.
	 * @return              the urgency level of the patient as an int.
	 */
	public int calculatehospitalPolicy(int patientage, double ptemp, int sbp, int dbp,int heartrate){

		int zurgency = 0; 
		
		if (patientage < 2){
			zurgency++;
		}
		if (ptemp >= 39.0){
			zurgency++;
		}
		if (sbp >= 140 || dbp >= 90){
			zurgency++;
		}
		if (heartrate >= 100 || heartrate <= 50 ){
			zurgency++;
		}
		return zurgency;
	}
	
	/**
	 * 
	 */
	@Override
    public int compareTo(Patient anotherPatient) {
        if (this.urgency == ((Patient) anotherPatient).urgency)
            return 0;
        else if ((this.urgency) > ((Patient) anotherPatient).urgency)
            return 1;
        else
            return -1;
    }

	@Override
	public int compare(Patient d, Patient d1) {
		// TODO Auto-generated method stub
		return d.urgency - d1.urgency;
	}
	
}
