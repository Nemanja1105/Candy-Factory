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
import org.unibl.etf.model.FactoryUser;
import org.unibl.etf.model.User;
import org.unibl.etf.util.ConfigReader;
import org.unibl.etf.util.MyLogger;

public class FactoryUserDAO {
	
	public void insertAll(List<FactoryUser> factoryUsers)throws IOException {
		ConfigReader config = ConfigReader.getInstance();
		JSONArray jsonArray = new JSONArray(factoryUsers);
		try (PrintWriter writer = new PrintWriter(new FileWriter(config.getFactoryUserPath()))) {
			writer.println(jsonArray.toString());
		}
	}
	
	public List<FactoryUser> getAll() {
		ArrayList<FactoryUser> result = new ArrayList<>();
		try {

			ConfigReader config = ConfigReader.getInstance();
			String lines = new String(Files.readAllBytes(Path.of(config.getFactoryUserPath())));
			JSONArray jsonArray;
			if (lines.isBlank())
				jsonArray = new JSONArray();
			else
				jsonArray = new JSONArray(lines);
			for (int i = 0; i < jsonArray.length(); i++) {
				var object=jsonArray.getJSONObject(i);
				var user = new FactoryUser(object.getString("name"));
				result.add(user);
			}
		} catch (IOException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
