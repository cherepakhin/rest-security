package ru.perm.v.restsecurity.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bookmark implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String uri = "";
	private String description = "";

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	public Bookmark() {
		// for jpa
	}

	public Bookmark(Account account, String uri, String description) {
		this.account = account;
		this.uri = uri;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Bookmark)) {
			return false;
		}

		Bookmark bookmark = (Bookmark) o;

		return id != null ? id.equals(bookmark.id) : bookmark.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Bookmark{" +
				"id=" + id +
				", uri='" + uri + '\'' +
				", description='" + description + '\'' +
				", account=" + account +
				'}';
	}
}
