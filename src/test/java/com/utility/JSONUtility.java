package com.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.constants.Env;
import com.google.gson.Gson;
import com.ui.pojo.Config;
import com.ui.pojo.Environment;

public class JSONUtility {
	public static Environment readJSON(Env env) {
		Gson gson = new Gson();
		File file = new File(System.getProperty("user.dir") + "/config/config.json");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Config config = gson.fromJson(fileReader, Config.class);
		return config.getEnvironments().get(env.toString());
	}
}
