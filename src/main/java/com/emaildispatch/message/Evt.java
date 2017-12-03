package com.emaildispatch.message;

import java.io.Serializable;
import java.util.Date;


public class Evt implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Date timestamp;
	private final Long count;

	public Evt(Date timestamp, Long count) {
		this.timestamp = timestamp;
		this.count = count;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public Long getCount() {
		return count;
	}
}
