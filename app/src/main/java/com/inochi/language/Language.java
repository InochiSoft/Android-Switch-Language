package com.inochi.language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;

public class Language {
    private String content;
    private Context context;
    private String file;

	public Language(Context context, String file){
		this.context = context;
		this.file = file;
		content = readText();
	}
	
	public String getTranslate(int id) {
		String translate = "";
		if (file.length() == 0){
			translate = context.getResources().getString(id);
		} else {
			if (content.length() == 0){
				translate = context.getResources().getString(id);
			} else {
				try {
					String key = context.getResources().getResourceEntryName(id);
					translate = getTranslations(key);
					
					if (translate.length() == 0){
						translate = context.getResources().getString(id);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return translate;
	}

	public String[] getTranslateArray(int id) {
		String[] translate = null;
		if (file.length() == 0){
			translate = context.getResources().getStringArray(id);
		} else {
			if (content.length() == 0){
				translate = context.getResources().getStringArray(id);
			} else {
				try {
					String key = context.getResources().getResourceEntryName(id);
					translate = getArrayTranslations(key);
					
					if (translate == null){
						translate = context.getResources().getStringArray(id);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return translate;
	}

	public String getTranslate(String key) {
		String translate = "";
		if (content.length() > 0){
			try {
				translate = getTranslations(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return translate;
	}

	public String[] getTranslateArray(String key) {
		String[] translate = null;
		if (content.length() > 0){
			try {
				translate = getArrayTranslations(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return translate;
	}

	private String getTranslations(String key){
		String value = "";
		
		if (content.length() > 0){
			JSONObject jsonObject;
			
			try {
				jsonObject = new JSONObject(content);
				value = jsonObject.getString(key);
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	private String[] getArrayTranslations(String key){
		String[] value = null;
		
		if (content.length() > 0){
			JSONObject jsonObject;
			
			try {
				jsonObject = new JSONObject(content);
				JSONArray jsonArray = jsonObject.getJSONArray(key);
			    int length = jsonArray.length();
			    
			    if (length > 0){
			    	List<String> list = new ArrayList<>();
					for (int i = 0; i < length; i++) {
						list.add( jsonArray.getString(i) );
					}
					value = list.toArray(new String[list.size()]);
			    }
			   
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	private String readText(){
    	AssetManager am = context.getAssets();
    	InputStream inputStream;
        StringBuilder result = new StringBuilder();
        
        if (file.length() > 0){
	        try {
				inputStream = am.open("lang/" + file + ".json");
				BufferedReader reader;
			    reader = new BufferedReader(new InputStreamReader(inputStream));
			    String line = reader.readLine();
			    if (line != null)
			    	if (line.length() > 0) result.append(line);
			    
			    while(line != null){
			        line = reader.readLine();
			        if (line != null)
			        if (line.length() > 0) result.append(line);
			    }
			    
			    inputStream.close();  
			} catch (IOException e) {
				e.printStackTrace();
			}	
        }
        
		return result.toString();
    }
}
