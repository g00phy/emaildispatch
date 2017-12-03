package com.emaildispatch.message;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sapient
 */
public class Cmd implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Date timestamp;
	private final Long count;

	public Cmd(Date timestamp, Long count) {
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
