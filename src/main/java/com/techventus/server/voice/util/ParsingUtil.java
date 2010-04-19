package com.techventus.server.voice.util;

import java.util.ArrayList;
import java.util.List;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

/**
 * Collection of useful html parsing methods
 * 
 * @author Tobias Eisentraeger
 *
 */
public abstract class ParsingUtil {

	/**
	 * Strips the text from the uninteresting parts before and after the interesting part.
	 * The return includes borders if includeBorders == true - Returns null when Exception occures<br/><br/>
	 * Example:<br/>
	 * removeUninterestingParts("Hello Toby  , How are you today? Fine.", "How are", "?" , <b>true</b>)<br/>
	 * Returns: "How are you today?"<br/>
	 * <br/>
	 * removeUninterestingParts("Hello Joseph, How are you today? Fine.", "How are", "?" , <b>false</b>)<br/>
	 * Returns: " you today"
	 * 
	 * @param text
	 * @param startBorder
	 * @param endBorder
	 * @param includeBorders
	 * @return
	 */
	public static final String removeUninterestingParts(String text, String startBorder, String endBorder, boolean includeBorders) {
		String ret="";
		try {
			if(text!=null&&startBorder!=null&&endBorder!=null&&(text.indexOf(startBorder)!=-1)&&(text.indexOf(endBorder)!=-1) ) {
				
				if(includeBorders) {
					text = text.substring(text.indexOf(startBorder));
					if(text!=null) {
						ret = text.substring(0,text.indexOf(endBorder)+endBorder.length());
					} else {
						ret = null;
					}
				} else {
					text = text.substring(text.indexOf(startBorder)+startBorder.length());
					if(text!=null) {
						ret = text.substring(0,text.indexOf(endBorder));
					} else {
						ret = null;
					}
				}
			
			} else {
				ret = null;
			}
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage());
			System.out.println("Begin:"+startBorder);
			System.out.println("End:"+endBorder);
			System.out.println("Text:"+text);
			e.printStackTrace();
			ret = null;
		}
		return ret;
	}
	
	/**
	 * Replaces some speciel htmlEntities with a corresponding String
	 * //TODO use Apache commons StringEscapeUtils.unescapeHTML() ?
	 * @param s
	 * @return
	 */
	public static String htmlEntitiesDecode(String s) {
		s=s.replaceAll("&#39;", "'"); 
		return s;
	}
	
	public static final int[] jsonIntArrayToIntArray(JSONArray array) {
		int[] result = new int[array.length()];
		for (int i = 0; i < array.length(); i++) {
			try {
				result[i] = array.getInt(i);
			} catch (JSONException e) {
				return null;
			}
		}
		return result;
	}
	
	public static final String[] jsonStringArrayToStringArray(JSONArray array) {
		String[] result = new String[array.length()];
		for (int i = 0; i < array.length(); i++) {
			try {
				result[i] = array.getString(i);
			} catch (JSONException e) {
				return null;
			}
		}
		return result;
	}
	
	public static final List<String> jsonStringArrayToStringList(JSONObject settingsJSON, List<String> stringList, String key) throws JSONException {
		stringList = new ArrayList<String>();
		for (int i = 0; i < ((JSONArray) settingsJSON.get(key)).length(); i++) {
			stringList.add(((JSONArray) settingsJSON.get(key)).getString(i));
		}
		return stringList;
	}
	
	public static final List<Integer> jsonIntArrayToIntegerList(JSONObject settingsJSON, List<Integer> integerList, String key) throws JSONException {
		integerList = new ArrayList<Integer>();
		for (int i = 0; i < ((JSONArray) settingsJSON.get(key)).length(); i++) {
			integerList.add(((JSONArray) settingsJSON.get(key)).getInt(i));
		}
		return integerList;
	}
	
	public static final JSONArray stringListToJsonArray(List<String> stringList) throws JSONException {	
		String[] lArray = (String[]) stringList.toArray(new String[stringList.size()]);
		return new JSONArray(lArray);
	}
	
	
}