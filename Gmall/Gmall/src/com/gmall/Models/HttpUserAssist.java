package com.gmall.Models;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmall.Utils.ServerIPUtil;

import android.content.ContentValues;
import android.database.Cursor;

public class HttpUserAssist implements IUserAssist {
	private String serverIp = ServerIPUtil.ip+"UserController?";
	private String downloadPhotoIp = ServerIPUtil.ip+"PhotoController?flag=download&";
	private String uploadPhotoIp = ServerIPUtil.ip + "PhotoController?flag=upload&";
	private String uploadPhotoByPost = ServerIPUtil.ip + "PhotoController";
	private User data = null;
	private int id;
	private String currentVariable;

	public void delete(int id) {
		this.id = id;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + String.format("flag=delete&id=%d", HttpUserAssist.this.id);
				try {
					doGet(url, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void update(int id, User data) {
		this.id = id;
		this.data = data;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + String
						.format("flag=update&id=%d&password=%s&phone=%s" + "&gender=%s&school=%s&headPhotoPath=%s",
								HttpUserAssist.this.id, HttpUserAssist.this.data.getPassword(),
								HttpUserAssist.this.data.getPhone(), HttpUserAssist.this.data.getGender(),
								HttpUserAssist.this.data.getSchool(), HttpUserAssist.this.data.getHeadPhotoPath())
						.replaceAll("/", "%2F").replaceAll(" ", "%20");
				try {
					doGet(url, "utf-8");
					if (!HttpUserAssist.this.data.getHeadPhotoPath().equals("")) {
						HttpGoodAssist.upload(HttpUserAssist.this.uploadPhotoByPost, HttpUserAssist.this.data.getHeadPhotoPath(),
								HttpUserAssist.this.uploadPhotoIp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void insert(User data) {
		this.data = data;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + String
						.format("flag=insert&name=%s&password=%s&phone=%s" + "&gender=%s&school=%s&headPhotoPath=%s",
								HttpUserAssist.this.data.getName(), HttpUserAssist.this.data.getPassword(),
								HttpUserAssist.this.data.getPhone(), HttpUserAssist.this.data.getGender(),
								HttpUserAssist.this.data.getSchool(), HttpUserAssist.this.data.getHeadPhotoPath())
						.replaceAll("/", "%2F").replaceAll(" ", "%20");
				try {
					doGet(url, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public User getUserById(int id) {
		this.id = id;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + "flag=getUserById&id=" + HttpUserAssist.this.id;
				String datas = "";
				try {
					datas = doGet(url, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				initContent(datas);

				if (!HttpUserAssist.this.data.getHeadPhotoPath().equals("")) {
					File f = new File(HttpUserAssist.this.data.getHeadPhotoPath());
					if (!f.exists()) {
						String[] pathArr = HttpUserAssist.this.data.getHeadPhotoPath().split("/");
						try {
							HttpGoodAssist.download(
									HttpUserAssist.this.downloadPhotoIp + "photo=" + pathArr[pathArr.length - 1],
									HttpUserAssist.this.data.getHeadPhotoPath());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			};
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.data;
	}

	public User getUserByName(String userName) {
		this.currentVariable = userName;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + "flag=getUserByName&name=" + HttpUserAssist.this.currentVariable;
				String datas = "";
				try {
					datas = doGet(url, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				initContent(datas);
			};
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.data;
	}

	public User getUserByNameAndPassword(String name, String password) {
		this.currentVariable = this.serverIp
				+ String.format("flag=getUserByNameAndPassword&name=%s&password=%s", name, password);
		Thread t = new Thread() {
			public void run() {
				String datas = "";
				try {
					datas = doGet(HttpUserAssist.this.currentVariable, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				initContent(datas);
			};
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.data;
	}

	public static String doGet(String url, String encode) throws Exception {
		StringBuilder sb = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(new HttpGet(url));
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), encode), 8192);

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
		}
		return sb.toString();
	}

	private void initContent(String json) {
		this.data = null;
		if (json != null && json.length() > 0) {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(json);
				int id = jsonObject.getInt("id");
				String name = jsonObject.getString("name");
				String password = jsonObject.getString("password");
				String phone = jsonObject.getString("phone");
				String gender = jsonObject.getString("gender");
				String school = jsonObject.getString("school");
				String headPhotoPath = jsonObject.getString("headPhotoPath");
				User user = new User(name, password, phone, gender, school, headPhotoPath);
				user.setId(id);
				JSONArray jsonArray = jsonObject.getJSONArray("releaseGoods");
				ArrayList<Good> datas = new ArrayList<Good>();

				for (int i = 0; i < jsonArray.length(); i++) {
					jsonObject = jsonArray.getJSONObject(i);
					name = jsonObject.getString("name");
					id = jsonObject.getInt("id");
					double money = jsonObject.getDouble("money");
					String contact = jsonObject.getString("contact");
					String typeName = jsonObject.getString("typeName");
					String photoPath = jsonObject.getString("photoPath");
					int userId = jsonObject.getInt("userId");
					String information = jsonObject.getString("information");
					Good data = new Good(name, money, typeName, contact, photoPath, userId, information);
					data.setId(id);
					datas.add(data);
				}
				user.setReleaseGoods(datas);
				this.data = user;
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public ArrayList<User> getUsers(String[] conditions) {
		return null;
	}
}
