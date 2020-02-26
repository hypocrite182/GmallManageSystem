package com.gmall.Models;

import java.util.ArrayList;

public interface IGoodAssist {
	void insert(Good data);
	
	void delete(int id);
	
	void update(int id,Good data);
	
	ArrayList<Good> getGoods(String[] conditions);
	
	ArrayList<Good> searchGoods(String key);
	
	ArrayList<Good> getTypeOfGoods(String typeName);
	
	Good getGoodById(int id);
}
