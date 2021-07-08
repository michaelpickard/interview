package com.workmarket.exception;

public class ParcelNotFoundException extends Exception {

	public ParcelNotFoundException() {
		super("The parcel could not be found");
	}

}
