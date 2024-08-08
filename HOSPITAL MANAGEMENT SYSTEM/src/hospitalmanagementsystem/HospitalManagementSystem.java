package hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {

	
	private static String url = "jdbc:mysql://localhost:3306/hospital";
	private static String username = "root";
	private static String password = "System#123";
	
	public static void main(String args[])
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Scanner scanner = new Scanner(System.in);
		try
		{
			Connection con = DriverManager.getConnection(url,username,password);
			Patient patient = new Patient(con, scanner);
			Doctor doctor = new Doctor(con);
			
			while(true)
			{
				System.out.println("*****HOSPITAL MANAGEMENT SYSTEM******");
				System.out.println("1. Add PAtient");
				System.out.println("2. View Patient");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your choice: ");
				int nchoice = scanner.nextInt();
				
				switch(nchoice)
				{
				case 1:
					patient.addPatient();
					break;
				case 2:
					patient.viewPatient();
					break;
				case 3:
					doctor.viewDoctor();
					break;
				case 4:
					bookAppointment(patient, doctor, con, scanner);
					break;
				case 5:
					System.out.println("Thank you for using school management system !!!");
					return;
				default:
					System.out.println("Enter valid choice !!!");
					break;
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void bookAppointment(Patient patient,Doctor doctor,Connection con, Scanner scanner)
	{
		System.out.println("Enter patient id:");
		int pid = scanner.nextInt();
		System.out.println("Enter doctor id: ");
		int did = scanner.nextInt();
		System.out.println("Enter appointment date(YYYY-MM_DD): ");
		String appdate = scanner.next();
		
		if(patient.getPatientById(pid) && doctor.getDoctorById(did))
		{
			if(checkDoctorAvailability(appdate,did, con))
			{
				String q = "insert into appointments (patient_id, doctor_id, appointment_date) values (?,?,?)";
				
				try
				{
					PreparedStatement st = con.prepareStatement(q);
					
					st.setInt(1, pid);
					st.setInt(2, did);
					st.setString(3,appdate);
					
					int row = st.executeUpdate();
					
					if(row > 0)
					{
						System.out.println("Appountment booked");
					}
					else
					{
						System.out.println("Failed to Book");
					}
					
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				 
				
			}
			else
			{
				System.out.println("Doctor not available on this date");
			}
		}
		else
		{
			System.out.println("Either doctor or patient doesn't exist");
		}
		
	}

	private static boolean checkDoctorAvailability(String appdate, int did,Connection con) {
		String q  = "select count(*) from appointments where doctor_id  = ? and appointment_date = ?";
		try
		{
			PreparedStatement st = con.prepareStatement(q);
			st.setInt(1, did);
			st.setString(2, appdate);
			
			ResultSet set = st.executeQuery();
			if(set.next())
			{
				int count = set.getInt(1);
				if(count== 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
		}
}
