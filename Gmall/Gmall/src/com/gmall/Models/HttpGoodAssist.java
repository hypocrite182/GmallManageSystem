package com.gmall.Models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmall.Utils.ServerIPUtil;

public class HttpGoodAssist implements IGoodAssist {
	private String serverIp = ServerIPUtil.ip + "GoodController?";
	private String downloadPhotoIp = ServerIPUtil.ip + "PhotoController?flag=download&";
	private String uploadPhotoIp = ServerIPUtil.ip + "PhotoController?flag=upload&";
	private String uploadPhotoByPost = ServerIPUtil.ip + "PhotoController";
	private ArrayList<Good> datalist = new ArrayList<Good>();
	private Good data = null;
	private int id;
	private String currentVariable;

	public void insert(Good data) {
		this.data = data;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + String.format(
						"flag=insert&name=%s&money=%f"
								+ "&typeName=%s&contact=%s&photoPath=%s&information=%s&userId=%d",
						HttpGoodAssist.this.data.getName(), HttpGoodAssist.this.data.getMoney(),
						HttpGoodAssist.this.data.getTypeName(), HttpGoodAssist.this.data.getContact(),
						HttpGoodAssist.this.data.getPhotoPath(), HttpGoodAssist.this.data.getInformation(),
						HttpGoodAssist.this.data.getUserId()).replaceAll("/", "%2F").replaceAll(" ", "%20");
				try {
					doGet(url, "utf-8");
					if (!HttpGoodAssist.this.data.getPhotoPath().equals("")) {
						upload(HttpGoodAssist.this.uploadPhotoByPost, HttpGoodAssist.this.data.getPhotoPath(),
								HttpGoodAssist.this.uploadPhotoIp);
					}
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

	public void delete(int id) {
		this.id = id;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + String.format("flag=delete&id=%d", HttpGoodAssist.this.id);
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

	public void update(int id, Good data) {
		this.id = id;
		this.data = data;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + String
						.format("flag=update&id=%d&name=%s&money=%f"
								+ "&typeName=%s&contact=%s&photoPath=%s&information=%s", HttpGoodAssist.this.id,
								HttpGoodAssist.this.data.getName(), HttpGoodAssist.this.data.getMoney(),
								HttpGoodAssist.this.data.getTypeName(), HttpGoodAssist.this.data.getContact(),
								HttpGoodAssist.this.data.getPhotoPath(), HttpGoodAssist.this.data.getInformation())
						.replaceAll("/", "%2F").replaceAll(" ", "%20");
				try {
					doGet(url, "utf-8");
					if (!HttpGoodAssist.this.data.getPhotoPath().equals("")) {
						upload(HttpGoodAssist.this.uploadPhotoByPost, HttpGoodAssist.this.data.getPhotoPath(),
								HttpGoodAssist.this.uploadPhotoIp);
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

	public ArrayList<Good> getGoods(String[] conditions) {
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + "flag=getGoods";
				String datas = "";
				try {
					datas = doGet(url, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				initContent(datas);

				for (int i = 0; i < HttpGoodAssist.this.datalist.size(); i++) {
					if (!HttpGoodAssist.this.datalist.get(i).getPhotoPath().equals("")) {
						File f = new File(HttpGoodAssist.this.datalist.get(i).getPhotoPath());
						if (!f.exists()) {
							String[] pathArr = HttpGoodAssist.this.datalist.get(i).getPhotoPath().split("/");
							try {
								download(HttpGoodAssist.this.downloadPhotoIp + "photo=" + pathArr[pathArr.length - 1],
										HttpGoodAssist.this.datalist.get(i).getPhotoPath());
							} catch (Exception e) {
								e.printStackTrace();
							}
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
		return this.datalist;
	}

	public ArrayList<Good> searchGoods(String key) {
		this.currentVariable = key;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + "flag=searchGoods&key=" + HttpGoodAssist.this.currentVariable;
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
		return this.datalist;
	}

	public ArrayList<Good> getTypeOfGoods(String typeName) {
		this.currentVariable = typeName;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + "flag=getTypeOfGoods&typeName=" + HttpGoodAssist.this.currentVariable;
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
		return this.datalist;
	}

	public Good getGoodById(int id) {
		this.id = id;
		Thread t = new Thread() {
			public void run() {
				String url = serverIp + "flag=getGoodById&id=" + HttpGoodAssist.this.id;
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
		return this.datalist.get(0);
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

	public static void download(String url, String path) throws Exception {
		StringBuilder sb = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(new HttpGet(url));

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream is = entity.getContent();
			FileOutputStream fos = new FileOutputStream(path);
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = is.read(b)) != -1) {
				fos.write(b, 0, len);
			}
			fos.close();
		}
	}

	public static void upload(String url, String path, String dogetUrl) throws Exception {
		String[] pathArr = path.split("/");
		doGet(dogetUrl + "photo=" + pathArr[pathArr.length - 1], "utf-8");

		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
		con.connect();
		FileInputStream fis = new FileInputStream(path);
		OutputStream out = con.getOutputStream();
		byte[] b = new byte[1024 * 1024 * 10];
		int len = -1;
		while ((len = fis.read(b)) != -1) {
			out.write(b, 0, len);
		}
		int ok = con.getResponseCode();
		if (ok == HttpURLConnection.HTTP_OK) {
		}
	}

	private void initContent(String json) {
		datalist.clear();
		if (json != null && json.length() > 0) {
			JSONArray jsonArray = null;
			try {
				jsonArray = new JSONArray(json);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String name = jsonObject.getString("name");
					int id = jsonObject.getInt("id");
					double money = jsonObject.getDouble("money");
					String contact = jsonObject.getString("contact");
					String typeName = jsonObject.getString("typeName");
					String photoPath = jsonObject.getString("photoPath");
					int userId = jsonObject.getInt("userId");
					String information = jsonObject.getString("information");

					Good data = new Good(name, money, typeName, contact, photoPath, userId, information);
					data.setId(id);
					this.datalist.add(data);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}
}
