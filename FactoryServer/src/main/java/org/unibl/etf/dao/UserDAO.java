package org.unibl.etf.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;
import org.unibl.etf.model.User;
import org.unibl.etf.util.ConfigReader;
import org.unibl.etf.util.MyLogger;

import com.google.gson.Gson;

public class UserDAO {

	//String companyName, String address, String phoneNumber, String email, String username,
	//String password,boolean isActivated,boolean isBlocked
	public List<User> getAll() {
		ArrayList<User> result = new ArrayList<>();
		try {

			ConfigReader config = ConfigReader.getInstance();
			String lines = new String(Files.readAllBytes(Path.of(config.getUserPath())));
			JSONArray jsonArray;
			if (lines.isBlank())
				jsonArray = new JSONArray();
			else
				jsonArray = new JSONArray(lines);
			for (int i = 0; i < jsonArray.length(); i++) {
				var object=jsonArray.getJSONObject(i);
				var user = new User(object.getString("companyName"),object.getString("address"),object.getString("phoneNumber"),
						object.getString("email"),object.getString("username"),object.getString("password"),
						object.getBoolean("activated"),object.getBoolean("blocked"));
				result.add(user);
			}
		} catch (IOException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public void insertUser(User u) throws IOException {
		ConfigReader config = ConfigReader.getInstance();
		String lines = new String(Files.readAllBytes(Path.of(config.getUserPath())));
		JSONArray jsonArray;
		if (lines.isBlank())
			jsonArray = new JSONArray();
		else
			jsonArray = new JSONArray(lines);
		JSONObject object = new JSONObject(u);
		jsonArray.put(object);
		try (PrintWriter writer = new PrintWriter(new FileWriter(config.getUserPath()))) {
			writer.println(jsonArray.toString());
		}

	}

	public void insertAll(List<User> users) throws IOException {
		ConfigReader config = ConfigReader.getInstance();
		JSONArray jsonArray = new JSONArray(users);
		try (PrintWriter writer = new PrintWriter(new FileWriter(config.getUserPath()))) {
			writer.println(jsonArray.toString());
		}

	}

}
