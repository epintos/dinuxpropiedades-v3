package ar.edu.itba.it.paw.domain.publications;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.joda.time.DateTime;

@Embeddable
public class StatusChange implements Serializable {

	@Enumerated(EnumType.STRING)
	private Status previousStatus;
	
	private DateTime changeDate;
	
	@Enumerated(EnumType.STRING)
	private Status newStatus;
	
	protected StatusChange() {
	}
	
	public StatusChange(Status previousStatus, DateTime today, Status newStatus) {
		this.previousStatus = previousStatus;
		this.changeDate = today;
		this.newStatus = newStatus;
	}
	
	public DateTime getChangeDate() {
		return changeDate;
	}
	
	public Status getNewStatus() {
		return newStatus;
	}
	
	public Status getPreviousStatus() {
		return previousStatus;
	}
	
}
