package pl.poznan.ww.ls.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LsIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LsIOException(String path) {
            super("Error parse files in path: " + path);
	}
}
