package org.unibl.etf.server;

import javax.xml.transform.OutputKeys;

public enum Protocol {
	MESSAGE_SEPARATOR("#"),
	INVALID_REQUEST("INVALID REQUEST"),
	LOGIN("LOGIN"),
	LOGIN_OK("OK"),
	LOGIN_NOT_OK("NOT_OK"),
	FINISH_ORDER("FINISH_ORDER"),
	END("END"),
	OK("OK"),
	SERVER_ERROR("SERVER_ERROR");
	private String value;
	private Protocol(String value) {
        this.value = value;
    }

    public String getMessage() {
        return value;
    }
}
