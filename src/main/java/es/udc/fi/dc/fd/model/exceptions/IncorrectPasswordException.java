package es.udc.fi.dc.fd.model.exceptions;

@SuppressWarnings("serial")
public class IncorrectPasswordException extends Exception {

	public IncorrectPasswordException(String message) {
		super(message);
	}

}
