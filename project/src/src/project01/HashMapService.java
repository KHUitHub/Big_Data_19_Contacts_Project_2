package project01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import dto.ContactDTO;

/**
 * @packageName : project01.lib
 * @fileName : BusinessLogic.java
 * @author : Eddie
 * @date : 2025.01.07
 * @description : 연락처 비즈니스 로직 클래스
 *              ============================================
 *              DATE AUTHOR NOTE
 *              --------------------------------------------
 *              2025.01.07 SW LEE 최초 생성
 */

public class HashMapService {
	private HashMapDAO contactDAO; // 데이터 접근을 위한 DAO(다형성을 활용하기 위함)
	private Scanner scanner = new Scanner(System.in);
	public HashMapService(HashMapDAO contactDAO) {
		this.contactDAO = contactDAO;
	}

	public void insertContact() {
		try {
			System.out.print("이름을 입력하세요: ");
			String name = scanner.nextLine();
			System.out.print("전화번호를 입력하세요: ");
			String phoneNumber = scanner.nextLine();

			while (!phoneNumber.matches("010\\d{8}")) {
				System.out.print("잘못된 형식입니다. 다시 입력해 주세요: ");
				phoneNumber = scanner.nextLine();
			}

			System.out.print("주소를 입력하세요: ");
			String address = scanner.nextLine();
			System.out.print("구분 ('가족', '친구', '기타'): ");
			String tag = scanner.nextLine();

			while (!tag.equals("가족") && !tag.equals("친구") && !tag.equals("기타")) {
				System.out.print("잘못된 값입니다. 다시 입력해 주세요: ");
				tag = scanner.nextLine();
			}

			ContactDTO contact = new ContactDTO(name, address, phoneNumber, tag);
			contactDAO.create (contact); // DAO를 통해 연락처 삽입
			System.out.println("등록되었습니다.");

		} catch (IllegalArgumentException e) {
			System.out.println("입력 오류: " + e.getMessage());
		}
	}

	public void viewAllContacts() {
		try {
			ArrayList<ContactDTO> contacts = contactDAO.readList();
			if (contacts.isEmpty()) {
				System.out.println("저장된 연락처가 없습니다.");
			} else {
				System.out.println("조회된 연락처 목록: ");
				contacts.forEach(System.out::println);
			}
		} catch (IOException e) {
			System.out.println("연락처 조회 중 오류 발생: " + e.getMessage());
		}
	}

	public void updateContact() {
		try {
			System.out.print("수정할 이름: ");
			String name = scanner.nextLine();
			System.out.print("새 전화번호: ");
			String phoneNumber = scanner.nextLine();
			while (!phoneNumber.matches("010\\d{8}")) {
				System.out.print("잘못된 형식입니다. 다시 입력해 주세요: ");
				phoneNumber = scanner.nextLine();
			}

			System.out.print("새 주소: ");
			String address = scanner.nextLine();
			System.out.print("새 구분 ('가족', '친구', '기타'): ");
			String tag = scanner.nextLine();

			while (!tag.equals("가족") && !tag.equals("친구") && !tag.equals("기타")) {
				System.out.print("잘못된 값입니다. 다시 입력해 주세요: ");
				tag = scanner.nextLine();
			}

			ContactDTO updatedContact = new ContactDTO(name, address, phoneNumber, tag);
			contactDAO.update(name, updatedContact);

		} catch (IllegalArgumentException e) {
			System.out.println("입력 오류: " + e.getMessage());
		} catch (IOException e) {

			System.out.println("IO 오류: " + e.getMessage());
		}
	}

	public void deleteContact() {
		try {
			System.out.print("삭제할 이름: ");
			String name = scanner.nextLine();
			if (contactDAO.delete (name)) {
				System.out.println("삭제되었습니다.");
			} else {
				System.out.println("삭제할 연락처가 없습니다.");
			}
		} catch (IOException e) {
			System.out.println("삭제 과정에서 IO 오류 발생: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("삭제 과정에서 오류 발생: " + e.getMessage());
		}
	}
}