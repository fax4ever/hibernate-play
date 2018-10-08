package it.redhat.demo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "User_", uniqueConstraints = @UniqueConstraint(columnNames = { "client_id", "name" }))
public class User implements Serializable {

	@Id
	private Integer id;

	@Column(nullable = false, length = 255)
	private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@Column(name = "credentials_id", nullable = false, unique = true)
	private long credentialsId;

	@Column(name = "functionrights_id", nullable = false, unique = true)
	private long functionRightsId;

	public User() {
	}

	public User(Integer id, String name, long credentialsId, long functionRightsId) {
		this.id = id;
		this.name = name;
		this.credentialsId = credentialsId;
		this.functionRightsId = functionRightsId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public long getCredentialsId() {
		return credentialsId;
	}

	public void setCredentialsId(long credentialsId) {
		this.credentialsId = credentialsId;
	}

	public long getFunctionRightsId() {
		return functionRightsId;
	}

	public void setFunctionRightsId(long functionRightsId) {
		this.functionRightsId = functionRightsId;
	}
}
