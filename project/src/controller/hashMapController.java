package controller;

import java.util.Scanner;

import hashMapLibrary.HashMapDAO;
import hashMapLibrary.HashMapService;

/**
 * @packageName : project01.app
 * @fileName : ContactApplication.java
 * @author : Eddie
 * @date : 2025.01.07
 * @description : 연락어 어플리케이션 클래스
 *              ============================================
 *              DATE AUTHOR NOTE
 *              --------------------------------------------
 *              2025.01.07 SW LEE 최초 생성
 */

public class hashMapController {

	public static void main(String[] args) {

		HashMapDAO contactDAO = new HashMapDAO();
		HashMapService service = new HashMapService(contactDAO);
		Scanner scanner = new Scanner(System.in);
		String input = "";

		do {
			try {
				System.out.println("\n==========================");
				System.out.println("  다음 메뉴 중 하나를 선택하세요.");
				System.out.println("==========================");
				System.out.println("1. 회원 추가");
				System.out.println("2. 회원 목록 보기");
				System.out.println("3. 회원 정보 수정하기");
				System.out.println("4. 회원 삭제");
				System.out.println("5. 종료");
				System.out.println("메뉴 선택: ");

				input = scanner.nextLine();

				switch (input) {
				case "1":
					service.insertContact();
					break;
				case "2":
					service.viewAllContacts();
					break;
				case "3":
					service.updateContact();
					break;
				case "4":
					service.deleteContact();
					break;
				case "5":
					System.out.println("프로그램을 종료합니다.");
					contactDAO.saveContacts();
					System.exit(0);
				default:
					System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
					continue;
				}

			} catch (Exception e) {
				System.out.println("메뉴 선택 중 오류 발생: " + e.getMessage());
				scanner.nextLine(); // 버퍼 클리어
			}

		} while (!input.equals("5"));
		scanner.close();
	}
}