package project02.mvc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import dto.ContactDTO;
import project02.utilities.MySQLConnection;

/*
 * @packageName	: mysqlLibrary
 * @fileName	: MySqlDAO.java
 * @author		: TJ
 * @date		: 2025.01.24
 * @description	: DB DAO(Model) Class
 * ============================================
 * DATE				AUTHOR				NOTE
 * --------------------------------------------
 * 2025.01.24		SW LEE				최초 생성
 */
public class ContactDAO {
	private MySQLConnection mySQLConnection = new MySQLConnection();

	public ArrayList<ContactDTO> select() {
		ResultSet rs = null;

		ArrayList<ContactDTO> contactList = new ArrayList<>();

		String sql	= "select u.user_id,			"
					+ "	   	  u.name,				"
					+ "	      u.phone_number,		"
					+ "	      u.address,			"
					+ "	      t.tag_name			"
					+ "  from user u				"
					+ "  join tag t					"
					+ "    on u.tag_id = t.tag_id	";

		this.mySQLConnection.connect(sql);
		try {
			rs = this.mySQLConnection.executeQuery();

			while(rs.next()) {
				contactList.add(new ContactDTO(rs.getString("user_id"),
											   rs.getString("address"),
											   rs.getString("phone_number"),
											   rs.getString("tag_name")));
			}
		} catch (SQLException e) {
			System.out.println("No Table Found.");
		} finally {
			this.mySQLConnection.disconnect();
		}
		return contactList;
	}
}
