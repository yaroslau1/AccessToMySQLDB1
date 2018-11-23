package com.work.exeption;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOException extends Exception {
	
	public DAOException(String description, SQLException e) {
		super(description);
		addSuppressed(e);
	}
	
	public DAOException(String description, InstantiationException e) {
		super(description);
		initCause(e);
	}
	
	public DAOException(String description, IllegalAccessException e) {
		super(description);
		initCause(e);
	}
	
	public DAOException(String description, ClassNotFoundException e) {
		super(description);
		initCause(e);
	}
	
	public DAOException(String description, IOException e) {
		super(description);
		initCause(e);
	}
	
	public DAOException(String description, Exception e) {
		super(description);
		addSuppressed(e);
	}
	
	public DAOException(String description) {
		super(description);
	}

}
