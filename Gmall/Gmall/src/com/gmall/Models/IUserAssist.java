package com.gmall.Models;

import java.util.ArrayList;

public interface IUserAssist {
	void delete(int id);
	
	void update(int id,User data);
	
	void insert(User data);
	
	ArrayList<User> getUsers(String[] conditions);
	
	User getUserById(int id);
	
	User getUserByName(String userName);
	
	User getUserByNameAndPassword(String name,String password);
}
