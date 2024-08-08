package hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {

	private  Connection con;
	private Scanner scanner;
	
	public Doctor(Connection con)
	{
		this.con = con;
	}
	
	public void viewDoctor()
	{
		String q = "select * from doctors"; 
		try
		{
			PreparedStatement st = con.prepareStatement(q);
			ResultSet set = st.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("***************");
			
			while(set.next())
			{
				int id = set.getInt("id");
				String name =set.getString("name");
				String specialization = set.getString("specialization");
				
				System.out.println("Id: " + id + " name: " +name+  " specialization: " + specialization);
				
				
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorById(int id)
	{
		String q = "select * from doctors where id = ?";
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
