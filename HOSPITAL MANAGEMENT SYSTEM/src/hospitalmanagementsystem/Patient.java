package hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	
	private  Connection con;
	private Scanner scanner;
	
	public Patient(Connection con, Scanner scanner)
	{
		this.con = con;
		this.scanner = scanner;
	}
	
	public void addPatient()
	{
		System.out.println("Enter patient name:");
		String name = scanner.next();
		System.out.println("Enter patient age:");
			int age = scanner.nextInt();
			System.out.println("Enter patient gender:");
			String gender = scanner.next();
			
			try
			{
				String q = "Insert into patients(name,age,gender) values(?,?,?)";
				PreparedStatement st = con.prepareStatement(q);
				st.setString(1, name);
				st.setInt(2, age);
				st.setString(3, gender);	
				int affectedrow = st.executeUpdate();
				
				if(affectedrow > 0)
				{
					System.out.println("Patient added successfully");
				}
				else
				{
					System.out.println("Failed to add patient!!");
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}		
	}
	
	public void viewPatient()
	{
		String q = "select * from patients"; 
		try
		{
			PreparedStatement st = con.prepareStatement(q);
			ResultSet set = st.executeQuery();
			System.out.println("Patients: ");
			System.out.println("***************");
			
			while(set.next())
			{
				int id = set.getInt("id");
				String name =set.getString("name");
				int age = set.getInt("age");
				String gender = set.getString("gender");
				
				System.out.println("Id: " + id + " name: " +name+ " age: " + age + " gender: " + gender);
				
				
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id)
	{
		String q = "select * from patients where id = ?";
		try
		{
			PreparedStatement st = con.prepareStatement(q);
			st.setInt(1, id);
			ResultSet set = st.executeQuery();
			
			if(set.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
