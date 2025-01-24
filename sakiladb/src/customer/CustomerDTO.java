package customer;

/**
 * @packageName	: customer
 * @fileName	: CustomerDTO.java
 * @author		: TJ
 * @date		: 2025.01.17
* @description	: 한 명 고객의 정보(고객 번호, 고객 명, 주소)를 저장하는 Class
 * ============================================
 * DATE				AUTHOR				NOTE
 * --------------------------------------------
 * 2025.01.17		SW LEE				최초 생성
 */
public class CustomerDTO {
//	컬럼 명과 동일하게 변수 명 설정
	private int customer_id;
	private String name;
	private String address;

	public CustomerDTO(int customer_id, String name, String address) {
		super();
		this.customer_id = customer_id;
		this.name = name;
		this.address = address;
	}

	public int getCustomer_id() { return customer_id; }
	public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }
	@Override
	public String toString() {
		return "[customer_id=" + customer_id + ", name=" + name + ", address=" + address + "]";
	}



}