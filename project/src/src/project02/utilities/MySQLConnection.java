package project02.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLConnection {
	private Connection con 			= null;
	private PreparedStatement pstmt = null;
	private ResultSet rs			= null;

	public void connect(String sql) {

//		1. Checking Driver(Skip-pable)
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
//		2. Create Connection Instance
		this.con = DriverManager.getConnection(Keys.url(), Keys.id(), Keys.pw());
		this.con.setAutoCommit(false);

//		3. Create PreparedStatement Instance
		this.pstmt = this.con.prepareStatement(sql);

		} catch (ClassNotFoundException e) {
			System.out.println("Can't find the Driver Class.");
		} catch (SQLException e) {
			e.printStackTrace();	// Trace Errors
			System.out.println("Connection Error.");
		}
	}

	public void executeUpdate() {
		try {
			if(this.pstmt.executeUpdate() == 1) {
				System.out.println("Success");
				this.con.commit();
			}else {
				System.out.println("No Rows Affected.");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Execute Update Failed.");
		}
	}

	public ResultSet executeQuery() {
		try {
			this.rs = this.pstmt.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Execute Query Failed.");
		}
		return rs;
	}

	public void disconnect() {
		if(this.rs != null) {
			try {
				this.rs.close();
				this.rs = null;
			}catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ResultSet Disconnect Failed.");
			}
		}
		if(this.pstmt != null) {
			try {
				this.pstmt.close();
				this.pstmt = null;
			}catch (SQLException e) {
				e.printStackTrace();
				System.out.println("PreparedStatement Disconnect Failed.");
			}
		}
		if(this.con != null) {
			try {
				this.con.close();
				this.con = null;
			}catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Connection Instance Disconnect Failed.");
			}
		}
	}
}