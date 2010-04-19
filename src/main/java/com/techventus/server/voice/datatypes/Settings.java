/**
 * 
 */
package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import com.techventus.server.voice.util.ParsingUtil;

/**
 * Holds all settings of the gVoice account and can return it as String, as lists or as json-string
 * Most Lists also have a .toJson() method
 * @deprecated please use the class AllSettings which holds phoneList, phones and settings and for the settings, the class Setting
 */
@Deprecated 
public class Settings {
	private JSONObject jsonObject = null;
	private List<PhoneOld> mPhoneList = null;
	private int[] mPhoneListSimple;
	private List<Greeting> mVoicemailGreetingsList = null;
	private List<Group> mGroupList = null;
	private List<String> mGroupListSimple = null;
	
	private List<WebCallButton> mWebCallButtonList = null;
	
	
	// Settings in order of the json
	private List<Integer> mActiveForwardingList = null;
	private String baseUrl;
	private int credits;
	private int defaultGreetingId;
	private List<String> mDidInfos = null;
    private boolean directConnect;
    private List<DisabledId> mDisabledIdList = null;
    private boolean doNotDisturb;
    private List<EmailAddress> mEmailAddressList = null;
    private boolean emailNotificationActive;
    private String emailNotificationAddress;
	private String language;
	private String primaryDid;
	private int screenBehavior;
	private boolean showTranscripts;
	private String smsNotifications;
	private boolean smsToEmailActive;
	private boolean smsToEmailSubject;
	private int spam;
	private String timezone;
	private boolean useDidAsCallerId;
	private boolean useDidAsSource;

	/**
	 * 
	 * @param json
	 * @param useJSONParser - uses the json.org json parser
	 * @param saveMode - check if each key exists before trying to parse it(in JSON Parser mode)
	 */
	public Settings(String json, boolean useJSONParser, boolean saveMode) {
		super();
		if(useJSONParser) {
			try {
				
				jsonObject = new JSONObject(json); 
				
				// root objects = [phoneList, settings, phones]
				JSONObject settingsJSON = jsonObject.getJSONObject("settings");
				
				if(!saveMode || saveMode && jsonObject.has("phoneList")) {				
					mPhoneListSimple = ParsingUtil.jsonIntArrayToIntArray(jsonObject.getJSONArray("phoneList"));
				}
				
				if(!saveMode || saveMode && jsonObject.has("settings")) {
					if(!saveMode || saveMode && settingsJSON.has("activeForwardingIds")) mActiveForwardingList = jsonIntArrayToIntegerList(settingsJSON,mActiveForwardingList,"activeForwardingIds");
					if(!saveMode || saveMode && settingsJSON.has("baseUrl")) baseUrl = settingsJSON.getString("baseUrl");
					if(!saveMode || saveMode && settingsJSON.has("credits")) credits = settingsJSON.getInt("credits");
					if(!saveMode || saveMode && settingsJSON.has("defaultGreetingId")) defaultGreetingId = settingsJSON.getInt("defaultGreetingId");
					if(!saveMode || saveMode && settingsJSON.has("didInfos")) mDidInfos = jsonStringArrayToStringList(settingsJSON,mDidInfos,"didInfos");
					if(!saveMode || saveMode && settingsJSON.has("directConnect")) directConnect =  settingsJSON.getBoolean("directConnect");
					if(!saveMode || saveMode && settingsJSON.has("disabledIdMap")) mDisabledIdList = DisabledId.createListFromJsonObject(settingsJSON);
					if(!saveMode || saveMode && settingsJSON.has("doNotDisturb")) doNotDisturb =  settingsJSON.getBoolean("doNotDisturb");
					if(!saveMode || saveMode && settingsJSON.has("emailAddresses")) mEmailAddressList = EmailAddress.createListFromJsonObject(settingsJSON);
					if(!saveMode || saveMode && settingsJSON.has("emailNotificationActive")) emailNotificationActive =  settingsJSON.getBoolean("emailNotificationActive");
					if(!saveMode || saveMode && settingsJSON.has("emailNotificationAddress")) emailNotificationAddress = settingsJSON.getString("emailNotificationAddress");
					if(!saveMode || saveMode && settingsJSON.has("greetings")) mVoicemailGreetingsList = Greeting.createListFromJsonObject(settingsJSON);
					if(!saveMode || saveMode && settingsJSON.has("groupList")) mGroupListSimple = jsonStringArrayToStringList(settingsJSON,mGroupListSimple,"groupList");
					//TODO groups
					if(!saveMode || saveMode && settingsJSON.has("language")) language = settingsJSON.getString("language");
					if(!saveMode || saveMode && settingsJSON.has("primaryDid")) primaryDid = settingsJSON.getString("primaryDid");
					if(!saveMode || saveMode && settingsJSON.has("screenBehavior")) screenBehavior = settingsJSON.getInt("screenBehavior");
					if(!saveMode || saveMode && settingsJSON.has("showTranscripts")) showTranscripts = settingsJSON.getBoolean("showTranscripts");
					if(!saveMode || saveMode && settingsJSON.has("smsNotifications")) smsNotifications = settingsJSON.getString("smsNotifications"); //TODO correct?
					if(!saveMode || saveMode && settingsJSON.has("smsToEmailActive")) smsToEmailActive =  settingsJSON.getBoolean("smsToEmailActive");
					if(!saveMode || saveMode && settingsJSON.has("smsToEmailSubject")) smsToEmailSubject = settingsJSON.getBoolean("smsToEmailSubject");
					if(!saveMode || saveMode && settingsJSON.has("spam")) spam = settingsJSON.getInt("spam");
					if(!saveMode || saveMode && settingsJSON.has("timezone")) timezone = settingsJSON.getString("timezone");
					if(!saveMode || saveMode && settingsJSON.has("useDidAsCallerId")) useDidAsCallerId = settingsJSON.getBoolean("useDidAsCallerId");
					if(!saveMode || saveMode && settingsJSON.has("useDidAsSource")) useDidAsSource = settingsJSON.getBoolean("useDidAsSource");
					//TODO webCallButtons
				}
				
				if(!saveMode || saveMode && jsonObject.has("phones")) {
					//TODO implement phones
				}
				

			} catch (JSONException e) {
				System.out.println("Error in json Parsing:"+e.getLocalizedMessage());
			}
		} else {
			mPhoneList = getPhoneListFromJson(json);
			mVoicemailGreetingsList = getGreetingsListFromJson(json);
			mGroupList = getGroupListFromJson(json);
			mEmailAddressList = getEmailAddressListFromJson(json);
			mDisabledIdList = getDisabledIdListFromJson(json);
		}
	}

	/**
	 * 
	 * @param settingsJSON
	 * @param stringList
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	private List<String> jsonStringArrayToStringList(JSONObject settingsJSON, List<String> stringList, String key) throws JSONException {
		stringList = new ArrayList<String>();
		for (int i = 0; i < ((JSONArray) settingsJSON.get(key)).length(); i++) {
			stringList.add(((JSONArray) settingsJSON.get(key)).getString(i));
		}
		return stringList;
	}
	
	private List<Integer> jsonIntArrayToIntegerList(JSONObject settingsJSON, List<Integer> integerList, String key) throws JSONException {
		integerList = new ArrayList<Integer>();
		for (int i = 0; i < ((JSONArray) settingsJSON.get(key)).length(); i++) {
			integerList.add(((JSONArray) settingsJSON.get(key)).getInt(i));
		}
		return integerList;
	}
	
	private JSONArray stringListToJsonArray(List<String> stringList) throws JSONException {	
		String[] lArray = (String[]) stringList.toArray(new String[stringList.size()]);
		return new JSONArray(lArray);
	}

	
	
	/**
	 * Make a prettyprinted JSON text of this JSONObject.
    * <p>
    * Warning: This method assumes that the data structure is acyclical.
    * @param indentFactor The number of spaces to add to each level of
    *  indentation.
    * @return a printable, displayable, portable, transmittable
    *  representation of the object, beginning
    *  with <code>{</code>&nbsp;<small>(left brace)</small> and ending
    *  with <code>}</code>&nbsp;<small>(right brace)</small>.
    *  @throws JSONException if using identFactor > 0 and If the object contains an invalid number.
    */
	public String toJson(int indentFactor) throws JSONException {
		//TODO instead of displaying the original object state we need to update the json with changed properties?
		return jsonObject.toString(indentFactor);
	}
	
	/**
     * Make a JSON text of the Settings. For compactness, no whitespace
     * is added. If this would not result in a syntactically correct JSON text,
     * then null will be returned instead.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     *
     * @return a printable, displayable, portable, transmittable
     *  representation of the object, beginning
     *  with <code>{</code>&nbsp;<small>(left brace)</small> and ending
     *  with <code>}</code>&nbsp;<small>(right brace)</small>.
     */
	public String toJson(){
		JSONObject resultO = new JSONObject();
		try { 
			//phoneList
			resultO.accumulate("phoneList", new JSONArray(mPhoneListSimple));
			//settings
			JSONObject settingsO = new JSONObject();

			settingsO.accumulate("activeForwardingIds", mActiveForwardingList);
			settingsO.accumulate("baseUrl", baseUrl);
			settingsO.accumulate("credits", credits);
			settingsO.accumulate("defaultGreetingId", defaultGreetingId);
			settingsO.accumulate("didInfos", mDidInfos);
			settingsO.accumulate("directConnect", directConnect);
			settingsO.accumulate("disabledIdMap", mDisabledIdList);
			settingsO.accumulate("baseUrl", baseUrl);
			settingsO.accumulate("baseUrl", baseUrl);
			settingsO.accumulate("baseUrl", baseUrl);
			/*
if(!saveMode || saveMode && settingsJSON.has("activeForwardingIds")) mActiveForwardingList = jsonStringArrayToStringList(settingsJSON,mActiveForwardingList,"activeForwardingIds");
if(!saveMode || saveMode && settingsJSON.has("baseUrl")) baseUrl = settingsJSON.getString("baseUrl");
if(!saveMode || saveMode && settingsJSON.has("credits")) credits = settingsJSON.getInt("credits");
if(!saveMode || saveMode && settingsJSON.has("defaultGreetingId")) defaultGreetingId = settingsJSON.getInt("defaultGreetingId");
if(!saveMode || saveMode && settingsJSON.has("didInfos")) mDidInfos = jsonStringArrayToStringList(settingsJSON,mDidInfos,"didInfos");
if(!saveMode || saveMode && settingsJSON.has("directConnect")) directConnect =  settingsJSON.getBoolean("directConnect");
if(!saveMode || saveMode && settingsJSON.has("disabledIdMap")) mDisabledIdList = DisabledId.createListFromJsonObject(settingsJSON);
if(!saveMode || saveMode && settingsJSON.has("doNotDisturb")) doNotDisturb =  settingsJSON.getBoolean("doNotDisturb");
if(!saveMode || saveMode && settingsJSON.has("emailAddresses")) mEmailAddressList = EmailAddress.createListFromJsonObject(settingsJSON);
if(!saveMode || saveMode && settingsJSON.has("emailNotificationActive")) emailNotificationActive =  settingsJSON.getBoolean("emailNotificationActive");
if(!saveMode || saveMode && settingsJSON.has("emailNotificationAddress")) emailNotificationAddress = settingsJSON.getString("emailNotificationAddress");
if(!saveMode || saveMode && settingsJSON.has("greetings")) mVoicemailGreetingsList = Greeting.createListFromJsonObject(settingsJSON);
if(!saveMode || saveMode && settingsJSON.has("groupList")) mGroupListSimple = jsonStringArrayToStringList(settingsJSON,mGroupListSimple,"groupList");
//TODO groups
if(!saveMode || saveMode && settingsJSON.has("language")) language = settingsJSON.getString("language");
if(!saveMode || saveMode && settingsJSON.has("primaryDid")) primaryDid = settingsJSON.getString("primaryDid");
if(!saveMode || saveMode && settingsJSON.has("screenBehavior")) screenBehavior = settingsJSON.getInt("screenBehavior");
if(!saveMode || saveMode && settingsJSON.has("showTranscripts")) showTranscripts = settingsJSON.getBoolean("showTranscripts");
if(!saveMode || saveMode && settingsJSON.has("smsNotifications")) smsNotifications = settingsJSON.getString("smsNotifications"); //TODO correct?
if(!saveMode || saveMode && settingsJSON.has("smsToEmailActive")) smsToEmailActive =  settingsJSON.getBoolean("smsToEmailActive");
if(!saveMode || saveMode && settingsJSON.has("smsToEmailSubject")) smsToEmailSubject = settingsJSON.getBoolean("smsToEmailSubject");
if(!saveMode || saveMode && settingsJSON.has("spam")) spam = settingsJSON.getInt("spam");
if(!saveMode || saveMode && settingsJSON.has("timezone")) timezone = settingsJSON.getString("timezone");
if(!saveMode || saveMode && settingsJSON.has("useDidAsCallerId")) useDidAsCallerId = settingsJSON.getBoolean("useDidAsCallerId");
if(!saveMode || saveMode && settingsJSON.has("useDidAsSource")) useDidAsSource = settingsJSON.getBoolean("useDidAsSource");
//TODO webCallButtons
			 */
			resultO.put("settings", settingsO);
		} catch (JSONException e) {
			return null;
		}
		
		return resultO.toString();
	}
	
	@Override
	public String toString() {
		String ret="";

		if(mPhoneList.size()>0) {
			ret+="\"phones\":{";
			for (Iterator<PhoneOld> iterator = mPhoneList.iterator(); iterator.hasNext();) {
				PhoneOld element = (PhoneOld) iterator.next();
				ret+=element.toString(); //TODO change to toJson when implemented
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+="\n"; 
		}
		
		if(mVoicemailGreetingsList.size()>0) {
			ret+=",\"greetings\":[";
			for (Iterator<Greeting> iterator = mVoicemailGreetingsList.iterator(); iterator.hasNext();) {
				Greeting element = (Greeting) iterator.next();
				ret+=element.toString();
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+="]\n";
		}
		
		if(mGroupList.size()>0) {
			ret+=",\"groups\":{";
			for (Iterator<Group> iterator = mGroupList.iterator(); iterator.hasNext();) {
				Group element = (Group) iterator.next();
				ret+=element.toString();
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+="}\n";
		}
		
		return ret;
	}

	/**
	 * Return the List of Group from json
	 * @param json
	 * @return
	 */
	private List<Group> getGroupListFromJson(String json) {
		List<Group> result = new ArrayList<Group>();
		try {
			result = Group.createGroupSettingsFromJsonResponse(json);
		} catch (Exception e) {
			System.out.println("Error in groupSetting object creation.");
		}
		return result;
	}
	
	/**
	 * @return the mGroupListSimple
	 */
	public List<String> getGroupListSimple() {
		if(mGroupListSimple==null && mGroupList!=null) {
			mGroupListSimple = new ArrayList<String>();
			for (Group groupSetting : mGroupList) {
				mGroupListSimple.add(groupSetting.getId());
			}
		}
		return mGroupListSimple;
	}
	
	/**
	 * @return the mPhoneListSimple
	 */
	public int[] getPhoneListSimple() {
		return mPhoneListSimple;
	}
	
	
	/**
	 * Return the List of Voicemail Greetings from json
	 * 
	 */
	private List<Greeting> getGreetingsListFromJson(String json) {
		List<Greeting> result = new ArrayList<Greeting>();
		try {
			result = Greeting.createGroupSettingsFromJsonResponse(json);
		} catch (Exception e) {
			System.out.println("Error in GreetingSetting object creation.");
		}
		return result;
	}
	
	private List<EmailAddress> getEmailAddressListFromJson(String json) {
		List<EmailAddress> result = new ArrayList<EmailAddress>();
		try {
			result = EmailAddress.createEmailAddressListFromJsonPartResponse(json);
		} catch (Exception e) {
			System.out.println("Error in EmailAddress object creation.");
		}
		return result;
	}
	
	private List<DisabledId> getDisabledIdListFromJson(String json) {
		List<DisabledId> result = new ArrayList<DisabledId>();
		try {
			result = DisabledId.createDisabledIdListFromJsonPartResponse(json);
		} catch (Exception e) {
			System.out.println("Error in DisabledId object creation.");
		}
		return result;
	}
	
	/**
	 * Return the List of Phones from json
	 * TODO implement correctly
	 */
	private List<PhoneOld> getPhoneListFromJson(String phonesInfoJson) {
		List<PhoneOld> phoneList = new ArrayList<PhoneOld>();
		if (phonesInfoJson!=null) {
			/*
			 * TODO fix
			List<Integer> disabledList = new ArrayList<Integer>();
			
			
			String f1 = phonesInfoJson.substring(phonesInfoJson.indexOf("disabledIdMap\":{"));
			f1=f1.substring(16);
			f1 = f1.substring(0,f1.indexOf("},\""));
			String[] rawslice = f1.split(",");
			
			for(int i=0;i<rawslice.length;i++){
				if(rawslice[i].contains("true")){
					//System.out.println(rawslice[i]);
					rawslice[i] = rawslice[i].replace("\":true", "");
					rawslice[i] =rawslice[i].replace("\"", "");
					//System.out.println(rawslice[i]);
					Integer z = Integer.parseInt(rawslice[i]);
					disabledList.add(z);
				}
			}
			*/
			
		    /* TODO fix
			String[] a = phonesInfoJson.split("\\{\"id\"\\:");
			// if(PRINT_TO_CONSOLE) System.out.println(a[0]);
			for (int i = 1; i < a.length; i++) {
				//PhoneOld phone = new PhoneOld();
				String[] b = a[i].split(",\"wd\"\\:\\{", 2)[0].split(",");
				//phone.id = Integer.parseInt(b[0].replaceAll("\"", ""));
				int id = Integer.parseInt(b[0].replaceAll("\"", ""));
				String number = "";
				String type = "";
				String name = "";
				String formattedNumber="";
				String carrier = "";
				Boolean verified = false;
				for (int j = 0; j < b.length; j++) {
					if (b[j].contains("phoneNumber")) {
						//phone.number = b[j].split("\\:")[1]
						//		.replaceAll("\"", "");
						number  = b[j].split("\\:")[1]
						   .replaceAll("\"", "");
					} else if (b[j].contains("type")) {
						//phone.type = b[j].split("\\:")[1].replaceAll("\"", "");
						type = b[j].split("\\:")[1].replaceAll("\"", "");
					} else if (b[j].contains("name")) {
						//phone.name = b[j].split("\\:")[1].replaceAll("\"", "");
						name = b[j].split("\\:")[1].replaceAll("\"", "");
					} else if (b[j].contains("formattedNumber")) {
						//phone.formattedNumber = b[j].split("\\:")[1]
						//		.replaceAll("\"", "");
						formattedNumber = b[j].split("\\:")[1]
						    								.replaceAll("\"", "");
					} else if (b[j].contains("carrier")) {
						//phone.carrier = b[j].split("\\:")[1].replaceAll("\"",
						//		"");
						carrier = b[j].split("\\:")[1].replaceAll("\"",
							"");
					} else if (b[j].contains("\"verified")) {
						//phone.verified = Boolean
						//		.parseBoolean(b[j].split("\\:")[1].replaceAll(
						//				"\"", ""));
						verified = Boolean
						.parseBoolean(b[j].split("\\:")[1].replaceAll(
								"\"", ""));
					}
				}
				if(id!=0 && !number.equals("")&&!formattedNumber.equals("")&&!type.equals("")&&!name.equals("")){
					
					boolean enabled;
					
					if(disabledList.contains(new Integer(id))){
						enabled = false;
					}else{
						enabled = true;
					}
					
					PhoneOld phone = new PhoneOld(id, number, formattedNumber, type, name, carrier, verified,enabled);
											
					phoneList.add(phone);
				}
			} */

		} else{
			System.out.println("Error in phone object creation.");
			phoneList = new ArrayList<PhoneOld>();
		}
		return phoneList;
	}

	/**
	 * @return the mPhoneList
	 */
	public List<PhoneOld> getPhoneList() {
		return mPhoneList;
	}

	/**
	 * @return the mVoicemailGreetingsList
	 */
	public List<Greeting> getVoicemailGreetingsList() {
		return mVoicemailGreetingsList;
	}

	/**
	 * @return the mGroupList
	 */
	public List<Group> getGroupSettingsList() {
		return mGroupList;
	}

	/**
	 * @return the mDisabledIdList
	 */
	public List<DisabledId> getDisabledIdList() {
		return mDisabledIdList;
	}

	/**
	 * @return the mWebCallButtonList
	 */
	public List<WebCallButton> getWebCallButtonList() {
		return mWebCallButtonList;
	}

	/**
	 * @return the mDefaultGreetingId
	 */
	public int getDefaultGreetingId() {
		return defaultGreetingId;
	}

	/**
	 * @return the mEmailAddressList
	 */
	public List<EmailAddress> getEmailAddressList() {
		return mEmailAddressList;
	}

	/**
	 * @return the mBaseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @return the mActiveForwardingList
	 */
	public List<Integer> getActiveForwardingList() {
		return mActiveForwardingList;
	}
	
	
	
	
}
