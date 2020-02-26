package com.gmall.Models;

import java.util.ArrayList;

public class User {
	private int id;
	
	private String name;
	
	private String password;
	
	private String phone;
	
	private String gender;
	
	private String school;
	
	private String headPhotoPath;
	
	private ArrayList<Good> releaseGoods;
	
	public User(String name, String password, String phone, String gender, String school, String headPhotoPath) {
		super();
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.gender = gender;
		this.school = school;
		this.headPhotoPath = headPhotoPath;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getHeadPhotoPath() {
		return headPhotoPath;
	}

	public void setHeadPhotoPath(String headPhotoPath) {
		this.headPhotoPath = headPhotoPath;
	}

	public ArrayList<Good> getReleaseGoods() {
		return releaseGoods;
	}

	public void setReleaseGoods(ArrayList<Good> releaseGoods) {
		this.releaseGoods = releaseGoods;
	}
	
}
