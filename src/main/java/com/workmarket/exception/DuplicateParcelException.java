package com.workmarket.exception;

public class DuplicateParcelException extends Exception {

	public DuplicateParcelException() {
		super("A parcel with this uuid already exists");
	}

}
