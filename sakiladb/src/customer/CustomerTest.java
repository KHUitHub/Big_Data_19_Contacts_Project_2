package customer;

public class CustomerTest {

	public static void main(String[] args) {
		CustomerDAO customerDao = new CustomerDAO();
//		전체 목록 조회
//		customerDao.select();

//		추가
		customerDao.insert();
	}

}