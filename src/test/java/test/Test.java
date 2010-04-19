package test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import com.techventus.server.voice.Voice;
import com.techventus.server.voice.datatypes.AllSettings;
import com.techventus.server.voice.datatypes.DisabledForwardingId;
import com.techventus.server.voice.datatypes.Group;
import com.techventus.server.voice.datatypes.Phone;
import com.techventus.server.voice.datatypes.PhoneOld;
import com.techventus.server.voice.datatypes.Greeting;
import com.techventus.server.voice.util.ParsingUtil;

public class Test {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static String userName = null;
	static String pass = null;
	static boolean connectOnStartup = false;
	static Properties testProps = null;
	private static Voice voice;
	private static String jsonData;
	
	public static void main(String[] args){
		
		try {
			testProps = load("test/privateTestData.properties");
			userName = testProps.getProperty("username");
			pass = testProps.getProperty("password");
			connectOnStartup = Boolean.parseBoolean(testProps.getProperty("connectOnStartup"));
			jsonData = testProps.getProperty("jsonData");
		} catch (Exception e) {
			System.out.println("Could not read the testProps, falling back to input. ("+e.getMessage()+")");
			System.out.println("Enter Your Google Voice Username, eg user@gmail.com:");
				//Added this line, otherwise fails all tests.
				connectOnStartup = true;
			try {
				userName = br.readLine();
			} catch (IOException ioe) {
				System.out.println("IO error trying to read your name!");
				System.exit(1);
			}

			System.out.println("Enter Your Password:");


			try {
				pass = br.readLine();
			} catch (IOException ioe) {
				System.out.println("IO error trying to read your name!");
				System.exit(1);
			}
		}
		
		try {
			if(connectOnStartup) voice = new Voice(userName, pass);
		} catch (IOException e) {
			System.out.println("IO error creating voice!"+e.getLocalizedMessage());
		}

		listTests();

		/*
	      System.out.println("Enter A \"Source\" for the Log:");
	      String source = null;
	      try {
	    	  source = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your name!");
	         System.exit(1);
	      }

	      System.out.println("Log into Google Voice and find the _rnr_se variable in the page Source. ");
	      System.out.println("Enter rnr_se_ value:");
	      String rnrSee = null;
	      try {
	    	  rnrSee = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your name!");
	         System.exit(1);
	      }
		 */


	}

	/**
	 * Lets the Tester choose the Test to run
	 */
	private static void listTests() {
		System.out.println("Availible Tests for "+userName);
		System.out.println("0: Exit");  
		System.out.println("1: Multi phone enable / disable");
		System.out.println("2: Inbox paging");
		System.out.println("3: Call Announcement Settings (Presentation)");
		System.out.println("4: Set Default Voicemail Greeting");
		System.out.println("5: Change do not disturb setting.");
		System.out.println("6: Change Group settings.");
		System.out.println("7: Read all settings and print them (cached)");
		System.out.println("8: Read all settings and print them (uncached)");
		System.out.println("9: Read all settings - pure json driven - flat data");
		System.out.println("10: Read all settings - pure json driven - actual account data");
		System.out.println("11: Update Group Settings");
		System.out.println("12: Group settings isPhoneEnabled tests");
		System.out.println("13: List Default Phones and Enabled/Disabled Setting");
		
		
		int testNr = 0;
		try {
			testNr = Integer.parseInt(br.readLine());
		} catch (Exception e) {
			System.out.println("Error trying to read the testNr!"+e.getMessage());
			System.exit(1);
		}
		  
		runTest(userName, pass, testNr);
	}

	/**
	 * @param userName
	 * @param pass
	 * @param testNr
	 */
	private static void runTest(String userName, String pass, int testNr) {
	    if(testNr==0) {// 0: Exit							
			System.out.println("Exiting.");
			System.exit(1);
	    }
		try {
	    	  
			
			//Voice voice = new Voice();
//			try {
			if(connectOnStartup) System.out.println(voice.isLoggedIn());
				//Thread.sleep(2000);

					switch (testNr) {
						case 1: // 1: Multi phone enable / disable 
							System.out.println("******** Starting Test "+testNr+" ********");
							// create int Array from all phone ids
							int[] phonesToChangeStatus = new int[voice.getPhoneList(true).size()];
							int i=0;
							
							for (Iterator<PhoneOld> iterator = voice.getPhoneList(false).iterator(); iterator.hasNext();) {
								PhoneOld type = (PhoneOld) iterator.next();
								phonesToChangeStatus[i] = type.id;
								i++;
							}
							
							System.out.println("Current phone status:");
							for (Iterator<PhoneOld> iterator = voice.getPhoneList(true).iterator(); iterator.hasNext();) {
								PhoneOld type = (PhoneOld) iterator.next();
								System.out.println(type.toString());
							}
							
							//Disable all phones with one call
							voice.phonesDisable(phonesToChangeStatus);
							
							// Output
							System.out.println("After deactivate multi:");
							for (Iterator<PhoneOld> iterator = voice.getPhoneList(true).iterator(); iterator.hasNext();) {
								PhoneOld type = (PhoneOld) iterator.next();
								System.out.println(type.toString());
							}
							
							//Enable all phones with one call
							voice.phonesEnable(phonesToChangeStatus);
							
							// Output
							System.out.println("After activate multi:");
							for (Iterator<PhoneOld> iterator = voice.getPhoneList(false).iterator(); iterator.hasNext();) {
								PhoneOld type = (PhoneOld) iterator.next();
								System.out.println(type.toString());
							}
							
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 2: // 2: Inbox paging
							System.out.println("******** Starting Test "+testNr+" ********");
//							Thread.sleep(2000);
							System.out.println(voice.getInboxPage(1000));
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 3: // 3: Call Announcement settings (Presentation)
							System.out.println("******** Starting Test "+testNr+" ********");
							System.out.println("Type 'true' for enable, 'false' for disable");
							boolean choice = false;
							try {
								choice = Boolean.parseBoolean(br.readLine());
							} catch (Exception e) {
								System.out.println("Error trying to read the choice!"+e.getMessage());
								System.exit(1);
							}
//							Thread.sleep(2000);
							System.out.println(voice.setCallPresentation(choice));
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 4: // 4: Caller ID in
							System.out.println("******** Starting Test "+testNr+" ********");
							for (Iterator<Greeting> iterator = voice.getVoicemailList(true).iterator(); iterator.hasNext();) {
								Greeting type = (Greeting) iterator.next();
								System.out.println(type.toString());
							}
							System.out.println("Choose the id of the voicemail greeting to use. ie '0' system standard or '1','2'");
							String voicemailNr = "0";
							try {
								voicemailNr = br.readLine();
							} catch (Exception e) {
								System.out.println("Error trying to read the choice!"+e.getMessage());
								System.exit(1);
							}
//							Thread.sleep(2000);
							voice.setVoicemailGreetingId(voicemailNr);
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 5: // 5: Do not disturb
							System.out.println("******** Starting Test "+testNr+" ********");
							System.out.println("Type 'true' for enable, 'false' for disable");
							boolean dndChoice = false;
							try {
								dndChoice = Boolean.parseBoolean(br.readLine());
							} catch (Exception e) {
								System.out.println("Error trying to read the choice!"+e.getMessage());
								System.exit(1);
							}
							voice.setDoNotDisturb(dndChoice);
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 6: // 6: Group settings
							System.out.println("******** Starting Test "+testNr+" ********");
							System.out.println("All to json:"+voice.getSettings(false).getSettings().getGroups());
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 7: // 7: Read all settings - cached
							System.out.println("******** Starting Test "+testNr+" ********");
							System.out.println(voice.getSettings(false).getSettings().toJson());
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 8: // 8: Read all settings - uncached
							System.out.println("******** Starting Test "+testNr+" ********");
							System.out.println(voice.getSettings(true).getSettings().toJson());
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 9: // 9: Read all settings - pure json driven
							System.out.println("******** Starting Test "+testNr+" ********");
							try {
								System.out.println("******** Original JSON Data ********");
								JSONObject origSettings = new JSONObject(jsonData);
								System.out.println(origSettings.toString(4));
								
								System.out.println("******* Parsed back and forth ******");
								AllSettings settings2 = new AllSettings(jsonData);
								System.out.println(settings2.toJsonObject().toString(4));
								
								System.out.println("******* Creating new AllSettings from old JSON ******");
								AllSettings settings4 = new AllSettings(settings2.toJsonObject().toString());
								System.out.println(settings4.toJsonObject().toString(4));
								System.out.println("1:"+settings4.isPhoneDisabled(1));
								System.out.println("2:"+settings4.isPhoneDisabled(2));
								System.out.println("3:"+settings4.isPhoneDisabled(4));
							} catch (JSONException e) {
								System.out.println("Error displaying json:"+e.getLocalizedMessage());
								e.printStackTrace();
							}
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 10: // 10: Read all settings - pure json driven - account data
							System.out.println("******** Starting Test "+testNr+" ********");
							try {
								System.out.println("******** Original JSON Data ********");
								String lJson = ParsingUtil.removeUninterestingParts(voice.getONLYFORTEST("https://www.google.com/voice/settings/tab/groups"), "<json><![CDATA[", "]]></json>", false);
								JSONObject origSettings = new JSONObject(lJson);
								System.out.println(origSettings.toString(4));
								
								System.out.println("******* JsonObject from AllSettings ******");
								AllSettings settings2 = new AllSettings(lJson);
								JSONObject objFromAllSettings = settings2.toJsonObject();
								System.out.println(objFromAllSettings.toString(4));
								
								System.out.println("******* Creating new AllSettings from old JSON ******");
								AllSettings settings3 = new AllSettings(objFromAllSettings.toString());
								System.out.println(settings3.toJsonObject().toString(4));
								
							} catch (JSONException e) {
								System.out.println("Error displaying json:"+e.getLocalizedMessage());
								e.printStackTrace();
							}
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 11: // 11: Update Group settings
							System.out.println("******** Starting Test "+testNr+" ********");
							try {
								System.out.println("******** Before ********");
								
								AllSettings settings2 = voice.getSettings(false);
								JSONObject objFromAllSettings = settings2.toJsonObject();
								System.out.println(objFromAllSettings.toString(4));
								
								System.out.println("Choose group to change settings (15)");
								String groupId = "0";
								try {
									groupId = br.readLine();
								} catch (Exception e) {
									System.out.println("Error trying to read the choice!"+e.getMessage());
									System.exit(1);
								}
								
								Group[] groups = voice.getSettings(false).getSettings().getGroups();
								for (int j = 0; j < groups.length; j++) {
								/* New Settings
								"directConnect": true,
				                "disabledForwardingIds": {"1": true},
				                "greetingId": 2,
				                "id": "15",
				                "isCustomDirectConnect": true,
				                "isCustomForwarding": true,
				                "isCustomGreeting": true,	
								 */
									if(groups[j].getId().equals(groupId)) {
										System.out.println("Changing settings for Group: "+groups[j].getName());
										groups[j].setCustomDirectConnect(true);
										groups[j].setDirectConnect(true);
										groups[j].setCustomForwarding(true);
										List<DisabledForwardingId> disList = new ArrayList<DisabledForwardingId>();
										disList.add(new DisabledForwardingId("1", true));
										groups[j].setDisabledForwardingIds(disList);
										groups[j].setCustomGreeting(true);
										groups[j].setGreetingId(2);
										voice.setNewGroupSettings(groups[j]);
									}
								}
								
								System.out.println("******** After  ********");
								AllSettings settings3 = voice.getSettings(true);
								System.out.println(settings3.toJsonObject().toString(4));
								
							} catch (JSONException e) {
								System.out.println("Error displaying json:"+e.getLocalizedMessage());
								e.printStackTrace();
							}
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 12: // 12: Group isPhoneEnabled Tests
							System.out.println("******** Starting Test "+testNr+" ********");
							try {
								AllSettings settings3 = voice.getSettings(true);
								Group[] groups = settings3.getSettings().getGroups();
								int[] phoneIds = settings3.getPhoneList();
								for (int j = 0; j < groups.length; j++) {
									System.out.println("+++ Disabled Phones in "+groups[j].getName()+" +++");
									for (int j2 = 0; j2 < phoneIds.length; j2++) {
										System.out.println(phoneIds[j2] + ":" + groups[j].isPhoneDisabled(phoneIds[j2]));
									}

								}
							} catch (JSONException e) {
								System.out.println("Error displaying json:"+e.getLocalizedMessage());
								e.printStackTrace();
							}
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 13: // 13: List Default Phones and Enabled/Disabled Setting
							System.out.println("*********Starting Test "+testNr+" *******");
							
							AllSettings settings3 = voice.getSettings(true);
							List<Integer> phoneList =settings3.getPhoneListAsList();
							Phone[] actualPhoneArray = settings3.getPhones();
							for(Integer phoneint:phoneList){
								inner: for(int ig=0;ig<actualPhoneArray.length;ig++){
											if(actualPhoneArray[ig].getId()==phoneint){
												System.out.println(actualPhoneArray[ig].getId()+ " "+actualPhoneArray[ig].getName()+" enabled:"+!settings3.isPhoneDisabled(actualPhoneArray[ig].getId()));
												break inner;
											}
								}
							}
							
							
//							Group[] groupAr = settings3.getSettings().getGroups()
//							for(String group:groupAr){
//								settings3.getSettings()
//							}
//							
//							
//							settings3.getSettings().getGroups()[0].
//							}
							break;
	
						default: 						
							System.out.println("Test "+testNr+" not found, exiting.");
							System.exit(1);
							break;
					}
					
					
//				Thread.sleep(2000);
				
				//System.out.println(voice.getSMS());
				//System.out.println(voice.getInbox());
//				System.out.println(voice.getInboxPage(1000));
				//System.out.println(voice.getInboxPage(100));
				/*
				System.out.println(voice.getInbox());
				Thread.sleep(2000);
				System.out.println(voice.phoneDisable(6));
				
				
				System.out.println(voice.getInbox());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getMissed());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getPlaced());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getReceived());
				Thread.sleep(2000);
				System.out.println("**********************************");
				
				System.out.println("**********************************");
				System.out.println(voice.getRecorded());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getSMS());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getSpam());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getStarred());
				Thread.sleep(2000);
				System.out.println("**********************************");
				*/
		
//			} catch (InterruptedException e) {			
//				e.printStackTrace();
//			}		
		} catch (IOException e) {	
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		listTests(); // List the Tests again
	}
	
	/**
     * Load a Properties File
     * @param propsFile
     * @return Properties
     * @throws IOException
     */
    private static Properties load(String propsFile) throws IOException {
//        Properties props = new Properties();
//
//        ResourceBundle.getBundle(propsFile);
//        final ResourceBundle rb = ResourceBundle.getBundle(propsFile, Locale.getDefault (), ClassLoader.getSystemClassLoader());
//        for (Enumeration keys = rb.getKeys (); keys.hasMoreElements ();)
//        {
//            final String key = (String) keys.nextElement ();
//            final String value = rb.getString (key);
//            
//            props.put (key, value);
//        } 
//        return props;
    	
    	Properties result = null;
        InputStream in = null;
         
         if (! propsFile.endsWith (".properties"))
        	 propsFile = propsFile.concat (".properties");
                         
         // Returns null on lookup failures:
         in = ClassLoader.getSystemClassLoader().getResourceAsStream (propsFile);
         if (in != null)
         {
             result = new Properties ();
             result.load (in); // Can throw IOException
         }
         testProps = result;
         return result;
    }
	
}
