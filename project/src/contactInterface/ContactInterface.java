package contactInterface;

import java.io.IOException;
import java.util.ArrayList;

import dto.ContactDTO;

/**
 * @packageName	: project01.lib
 * @fileName	: ContactDAO.java
 * @author		: Eddie
 * @date		: 2025.01.06
 * @description	: DB 연동을 위한 연락처 DataAccessObject 인터페이스
 * ============================================
 * DATE				AUTHOR				NOTE
 * --------------------------------------------
 * 2025.01.06		SW LEE				최초 생성
 */

public interface ContactInterface {
	ArrayList<ContactDTO> readList () throws IOException;
	ContactDTO read (String name) throws IOException;
	void create (ContactDTO user) throws IllegalArgumentException;
	void update (String name, ContactDTO user) throws IllegalArgumentException, IOException;
	boolean delete (String name) throws IOException;
	boolean search (String name) throws IOException;
	void saveContacts () throws IOException;	// 연락처 저장 메소드
}

//select -> read
//insert -> create
//update -> update
//delete -> delete
//search