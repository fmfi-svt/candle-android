package com.svt.candle;

/**
 * Výnimka používaná triedou Lesson, ak je zlý formát vstupu.
 */
public class WrongFormatException extends Exception {

	private static final long serialVersionUID = 1L;
	private String part;

    public WrongFormatException(String part) {
        this.part = part;
    }

    @Override
    public String getMessage() {
        return "Zly format suboru na pozicii" + part;
    }
}