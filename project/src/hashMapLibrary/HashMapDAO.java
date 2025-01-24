package hashMapLibrary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import contactInterface.ContactInterface;
import dto.ContactDTO;

/**
 * @packageName	: project01.lib
 * @fileName	: ContactContainer.java
 * @author		: Eddie
 * @date		: 2025.01.06
 * @description	: DAO 인터페이스 실체 클래스
 * ============================================
 * DATE				AUTHOR				NOTE
 * --------------------------------------------
 * 2025.01.06		SW LEE				최초 생성
 */

public class HashMapDAO implements ContactInterface{
	private HashMap<String, ContactDTO> contactMap = new HashMap<>();	// 연락처 저장 해시맵 오브젝트 생성
	private ContactsFileReaderWriter fileReaderWriter = new ContactsFileReaderWriter();	// 연락처 입출력 오브젝트 생성
	private Scanner scanner = new Scanner(System.in);

	public HashMapDAO() {
		fileReaderWriter = new ContactsFileReaderWriter();
		try {
            contactMap = fileReaderWriter.loadContacts();	// 파일로부터 불러온 연락처 초기화
            System.out.println("불러온 연락처 수: " + contactMap.size());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("초기화 중 오류 발생: " + e.getMessage());
            contactMap = new HashMap<>();
        }
	}

	@Override
	public void create(ContactDTO user) throws IllegalArgumentException {	// Create
		if (contactMap.containsKey(user.getPhoneNumber())) {
            throw new IllegalArgumentException("중복된 전화번호가 있습니다.");
        }
		contactMap.put(user.getPhoneNumber(), user);	// 해시맵에 연락처 추가
	}

	@Override
	public ContactDTO read(String name) throws IOException {	// Read
		ArrayList<ContactDTO> matchedContacts = new ArrayList<>();
        for (ContactDTO contact : contactMap.values()) {
            if (contact.getName().equals(name)) {
                matchedContacts.add(contact);
            }
        }

        if (matchedContacts.isEmpty()) {
            throw new IOException("해당 이름의 연락처가 없습니다.");
        } else if (matchedContacts.size() == 1) {
            return matchedContacts.get(0);
        } else {
            System.out.println("중복된 이름이 발견되었습니다. 선택할 연락처를 입력하세요.");
            for (int i = 0; i < matchedContacts.size(); i++) {
                System.out.println((i + 1) + ". " + matchedContacts.get(i));
            }
            System.out.print("번호 선택: ");

            int choice = scanner.nextInt();

            while (choice < 1 || choice > matchedContacts.size()) {
                System.out.print("잘못된 선택입니다. 다시 입력해 주세요: ");
                choice = scanner.nextInt();
            }
            return matchedContacts.get(choice - 1);
        }
    }

	@Override
	public ArrayList<ContactDTO> readList () throws IOException {	// Read
		return new ArrayList<>(contactMap.values());	// 모든 연락처 배열리스트로 리턴
	}

	@Override
	 public void update (String name, ContactDTO user) throws IllegalArgumentException, IOException {
        ArrayList<ContactDTO> matchedContacts = new ArrayList<>();

        for (ContactDTO temp : contactMap.values()) {
            if (temp.getName().equals(name)) {
                matchedContacts.add(temp);
            }
        }

        if (matchedContacts.isEmpty()) {
            throw new IOException("이름에 해당하는 연락처가 없습니다.");
        } else if (matchedContacts.size() == 1) {
            contactMap.put(matchedContacts.get(0).getPhoneNumber(), user);
        } else {
            System.out.println("중복된 이름이 발견되었습니다. 업데이트할 연락처를 선택하세요.");

            for (int i = 0; i < matchedContacts.size(); i++) {
                System.out.println((i + 1) + ". " + matchedContacts.get(i));
            }
            System.out.print("번호 선택: ");
            int choice = scanner.nextInt();
            while (choice < 1 || choice > matchedContacts.size()) {
                System.out.print("잘못된 선택입니다. 다시 입력해 주세요: ");
                choice = scanner.nextInt();
            }
            contactMap.put(matchedContacts.get(choice - 1).getPhoneNumber(), user);
        }
        System.out.println("업데이트가 완료되었습니다.");
    }

	@Override
	public boolean delete (String name) throws IOException {
        ArrayList<ContactDTO> matchedContacts = new ArrayList<>();

        for (ContactDTO contact : contactMap.values()) {
            if (contact.getName().equals(name)) {
                matchedContacts.add(contact);
            }
        }

        if (matchedContacts.isEmpty()) {
            return false;
        } else if (matchedContacts.size() == 1) {
            contactMap.remove(matchedContacts.get(0).getPhoneNumber());
            return true;
        } else {
            System.out.println("중복된 이름이 발견되었습니다. 삭제할 연락처를 선택하세요.");

            for (int i = 0; i < matchedContacts.size(); i++) {
                System.out.println((i + 1) + ". " + matchedContacts.get(i));
            }

            System.out.print("번호 선택: ");

            int choice = scanner.nextInt();

            while (choice < 1 || choice > matchedContacts.size()) {
                System.out.print("잘못된 선택입니다. 다시 입력해 주세요: ");
                choice = scanner.nextInt();
            }

            contactMap.remove(matchedContacts.get(choice - 1).getPhoneNumber());

            return true;
        }
    }

	@Override
	 public boolean search(String name) throws IOException {
        return contactMap.values().stream().anyMatch(c -> c.getName().equals(name));
    }

	@Override
	public void saveContacts() throws IOException {
		fileReaderWriter.saveContacts(contactMap);	// 연락처 파일에 저장
	}
}