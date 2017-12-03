package com.emaildispatch.model;

import com.emaildispatch.message.Evt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sapient
 */
public class EmailState implements Serializable {
	private static final long serialVersionUID = 1L;
	private final List<Date> timestamps;
	private Long countOfEmailsSent;

	public EmailState() {
		this(new ArrayList<>(), 0L);
	}

	public EmailState(List<Date> timestamps, Long countOfEmailsSent) {
		this.timestamps = timestamps;
		this.countOfEmailsSent = countOfEmailsSent;
	}

	public EmailState copy() {
		return new EmailState(new ArrayList<>(timestamps), countOfEmailsSent);
	}

	public void update(Evt evt) {
		timestamps.add(evt.getTimestamp());
		countOfEmailsSent += evt.getCount();
	}

	public int size() {
		return timestamps.size();
	}

	@Override
	public String toString() {
		return "statistics " + "\n" +
				"emails sent :"+ countOfEmailsSent + "\n"
				+"Timestamps of emails sent :" + timestamps;
	}
}
