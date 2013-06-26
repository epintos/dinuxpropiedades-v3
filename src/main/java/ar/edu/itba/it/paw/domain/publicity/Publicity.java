package ar.edu.itba.it.paw.domain.publicity;

import javax.persistence.Column;
import javax.persistence.Entity;

import ar.edu.itba.it.paw.domain.PersistentEntity;

@Entity
public class Publicity extends PersistentEntity {
	@Column(nullable = false)
	private String client;
	
	@Column(nullable = false)
	private String url;
	
	@Column(nullable = false)
	private Integer frequence;

	protected Publicity() {
	}

	public Publicity(String client, String url, Integer frequence) {
		this.client = client;
		this.url = url;
		this.frequence = frequence;
	}

	public Integer getFrequence() {
		return frequence;
	}

	public String getUrl() {
		return url;
	}
	
	public String getClient() {
		return client;
	}
}
