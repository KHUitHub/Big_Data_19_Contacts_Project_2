package hashMapLibrary;

import java.io.*;
import java.util.HashMap;

import dto.ContactDTO;

/**
 * @packageName	: project01.lib
 * @fileName	: ContactsFileReaderWriter.java
 * @author		: Eddie
 * @date		: 2025.01.06
 * @description	: 파일 입출력 클래스
 * ============================================
 * DATE				AUTHOR				NOTE
 * --------------------------------------------
 * 2025.01.06		SW LEE				최초 생성
 */

public class ContactsFileReaderWriter {
//	디렉토리 경로 및 파일 이름 설정(FILE_PATH = DIRECTORY_PATH + FILE_NAME)
	private static final String FILE_PATH = "C:\\big_data19\\java_dev\\workspace\\sample\\Contacts\\conatacts.bin";

	public void saveContacts(HashMap<String, ContactDTO> contacts) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(contacts);
        }
    }

	@SuppressWarnings("unchecked")
    public HashMap<String, ContactDTO> loadContacts() throws IOException, ClassNotFoundException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (HashMap<String, ContactDTO>) ois.readObject();
        }
    }
}