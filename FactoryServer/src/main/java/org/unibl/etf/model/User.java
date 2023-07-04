package org.unibl.etf.model;

import java.util.Objects;

public class User {
	private String companyName;
	private String address;
	private String phoneNumber;
	private String email;
	private String username;
	private String password;
	private boolean activated;
	private boolean blocked;
	
	public User() {
		super();
	}
	
	public User(String username) {
		super();
		this.username=username;
	}
	
	public User(String companyName, String address, String phoneNumber, String email, String username,
			String password) 
	{
		super();
		this.companyName = companyName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.username = username;
		this.password = password;
		this.activated=false;
		this.blocked=false;
	}

	public User(String companyName, String address, String phoneNumber, String email, String username,
			String password,boolean isActivated,boolean isBlocked) {
		super();
		this.companyName = companyName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.username = username;
		this.password = password;
		this.activated=isActivated;
		this.blocked=isBlocked;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean isActivated) {
		this.activated = isActivated;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.blocked = isBlocked;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}

	
}
