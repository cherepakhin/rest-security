package ru.perm.v.restsecurity.controller;

public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException() {
		super("Entity notfound");
	}

	public EntityNotFoundException(String message) {
		super(message);
	}
}
