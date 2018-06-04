package pl.poznan.ww.ls.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LsPathNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LsPathNotFoundException(String path) {
            super("No such path: " + path);
	}
}
