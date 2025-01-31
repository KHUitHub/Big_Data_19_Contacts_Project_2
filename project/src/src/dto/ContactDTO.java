package dto;

import java.io.Serializable;

/*
 * @packageName	: practices.lib
 * @fileName	: ContactVO.java
 * @author		: Eddie
 * @date		: 2025.01.06
 * @description	: 연락처 HashMap에 삽입할 VO 클래스
 * ============================================
 * DATE				AUTHOR				NOTE
 * --------------------------------------------
 * 2025.01.06		SW LEE				최초 생성
 */

public class ContactDTO implements Serializable{
//	클래스 버전 관리를 위한 serialVersionUID 변수
	private static final long serialVersionUID = 1L;

//	DTO 변수 선언
	private String name;
	private String phoneNumber;
	private String address;
	private String tag;

//	DTO 클래스 생성자
	public ContactDTO(String name, String address, String phoneNumber, String tag) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "이름: " + name + ", 전화번호: " + phoneNumber
			 + ", 주소: " + address + ", 구분: " + tag;
	}

//	Getters & Setters
	public String getName() { return this.name; }
	public String getAddress() { return this.address; }
	public String getPhoneNumber() { return this.phoneNumber; }
	public String getTag() { return this.tag; }
	public void setName(String name) { this.name = name; }
	public void setAddress(String address) { this.address = address; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
	public void setTag(String tag) { this.tag = tag; }
}