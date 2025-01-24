package customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @packageName	: customer
 * @fileName	: CustomerDAO.java
 * @author		: TJ
 * @date		: 2025.01.17
 * @description	: customer Table 고객 정보 처리
 * ============================================
 * DATE				AUTHOR				NOTE
 * --------------------------------------------
 * 2025.01.17		SW LEE				최초 생성
 */
public class CustomerDAO {
//	1. 전체 조회
	public ArrayList<CustomerDTO> select() {
		ArrayList<CustomerDTO> custList = new ArrayList<>();
//		데이터베이스 연동
//		연동 시 URL, ID, PW 저장 -> Connection 생성 시 사용
//		필요한 변수를 선언
		String url = "jdbc:mysql://localhost:3306/sakila";
		String id = "root";
		String pw = "doitmysql";

		Connection con			= null;
		PreparedStatement pstmt	= null;
		ResultSet rs			= null;

		String sql	= "select c.customer_id,									"
					+ "	   	  concat(c.first_name, ' ', c.last_name) as name,	"
					+ "	   	  a.address											"
					+ "  from customer c										"
					+ "  join address a											"
					+ "    on c.address_id = a.address_id						";

//		1. 드라이버 로딩(생략 가능)
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find the Driver Class.");
		}
		try {
//		2. Connection 생성
			con = DriverManager.getConnection(url, id, pw);

//		3. PreparedStatemenet 생성
			pstmt = con.prepareStatement(sql);

//		4. PreparedStatement 실행 -> 반환 : ResultSet
			rs = pstmt.executeQuery();	// `select` 일 경우 executeQuery()

//		5. ResultSet -> ArrayList에 담는다
			while(rs.next()) {	// next(): 다음 행 존재 유무 확인 메소드
				// 한 컬럼씩 꺼낸 후 저장
				int customer_id = rs.getInt("customer_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				// DTO 생성
				CustomerDTO cust =
						new CustomerDTO(
								customer_id,
								name,
								address);
				// DTO를 ArrayList에 추가
				custList.add(cust);
				// 테스트
				System.out.println(cust.toString());

			}

		} catch (SQLException e) {
			e.printStackTrace();	// 에러 추적
			System.out.println("Connection Error.");
		}


//		6. Connection close()

		return custList;
	}

//	2. 추가
	public void insert() {
//		1. 키보드로 고객 정보를 입력: CustomerService(클래스 이름)에서 처리 필요
//		Scanner scanner = new Scanner(System.in);
////		int customer_id;	// DB에서 이 컬럼은 auto_increment: 자동으로 1씩 증가
//		String first_name		= null;
////		String address	= null;
//		System.out.println("Input name please: ");
//		first_name = scanner.next();

//		2. 데이터베이스(customer table)에 insert
//		변수 선언
		String url = "jdbc:mysql://localhost:3306/sakila";
		String id = "root";
		String pw = "doitmysql";

		Connection con			= null;
		PreparedStatement pstmt	= null;

		String sql = "insert into contact (contact_name, contact_phone, contact_address)"
				   + "		 	   values(?,?,?)										";

//		2.1 드라이버 로딩
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		2.2 Connection 생성
		try {
			con = DriverManager.getConnection(url, id, pw);

//		2.3 PreparedStatement 생성
			pstmt = con.prepareStatement(sql);

//		2.4 실행: insert
			pstmt.setString(1, "마크");	// 키보드 입력한 값으로 이름
			pstmt.setString(2, "01033334444");	// 연락처
			pstmt.setString(3, "기흥 동 몰라요");	// 주소
			int flag = pstmt.executeUpdate();	// insert, update, delete

//		3. 정상 추가 시 출력: VIEW에서 처리 필요
			if(flag == 1) {
				System.out.println("Insert complete.");
			}else {
				System.out.println("Insert Error.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		Connection close()
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
