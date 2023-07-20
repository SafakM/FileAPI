package com.safak.filemanagement.Exception.CustomExceptions;


public class FileLocationException extends RuntimeException{

	public FileLocationException(String message) {
		super(message);
	}
	
	public FileLocationException(String message, Throwable ex) {
		super(message, ex);
	}
}
