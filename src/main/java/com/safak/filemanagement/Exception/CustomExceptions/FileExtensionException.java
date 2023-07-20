package com.safak.filemanagement.Exception.CustomExceptions;

public class FileExtensionException extends RuntimeException{

	public FileExtensionException(String message) {
		super(message);
	}
	
	public FileExtensionException(String message, Throwable ex) {
		super(message,ex);
	}
	
}
