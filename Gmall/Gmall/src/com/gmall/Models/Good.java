package com.gmall.Models;

public class Good {
	private int id;
	
	private String name;
	
	private double money;
	
	private String typeName;
	
	private String contact;
	
	private String photoPath;
	
	private int userId;
	
	private String information;

	
	public Good(String name, double money, String typeName, String contact, String photoPath, int userId,
			String information) {
		super();
		this.name = name;
		this.money = money;
		this.typeName = typeName;
		this.contact = contact;
		this.photoPath = photoPath;
		this.userId = userId;
		this.information = information;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
}
