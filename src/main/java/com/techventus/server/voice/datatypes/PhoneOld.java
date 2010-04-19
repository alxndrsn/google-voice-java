package com.techventus.server.voice.datatypes;

import gvjava.org.json.JSONObject;


/**
 * @deprecated please move everything over to Phone.java
 */
@Deprecated
public class PhoneOld {
	
	
	/*Use Getters and Setters - Need to make these variables private*/
	@Deprecated
	public int id;
	@Deprecated
	public String number;
	@Deprecated
	public String formattedNumber;
	@Deprecated
	public String type;
	@Deprecated
	public String name;
	@Deprecated
	public String carrier;
	@Deprecated 
	public boolean verified;
	@Deprecated
	public boolean enabled;
	
	/**
	 * Instantiates a new empty PhoneOld object.  This method is deprecated.
	 * Consider using PhoneOld(int id,String number,String formattedNumber,String type,String name,String carrier, Boolean verified)
	 */
	@Deprecated
	public PhoneOld(){
		
	}
	
	public PhoneOld(int id,String number,String formattedNumber,String type,String name,String carrier, boolean verified, boolean enabled){
		this.id = id;
		this.number = number;
		this.formattedNumber = formattedNumber;
		this.type = type;
		this.name = name;
		this.carrier = carrier;
		this.verified = verified;
		this.enabled = enabled;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getNumber(){
		return this.number;
	}
	
	public void setNumber(String number){
		this.number = number;
	}
	public String getFormattedNumber(){
		return this.formattedNumber;
	}
	public void setFormattedNumber(String formattedNumber){
		this.formattedNumber = formattedNumber;
	}
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getCarrier(){
		return this.carrier;
	}
	public void setCarrier(String carrier){
		this.carrier = carrier;
	}
	public boolean getVerified(){
		return this.verified;
	}
	public void setVerified(boolean verified){
		this.verified = verified;
	}
	public boolean getEnabled(){
		return this.enabled;
	}
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public String toString(){
		String ret = "";
		ret+="id="+id+";";
		if(number!=null){
			ret+="number="+number+";";
		}
		if(name!=null){
			ret+="name="+name+";";
		}
		if(carrier!=null){
			ret+="carrier="+carrier+";";
		}
		if(type!=null){
			ret+="type="+type+";";
		}
		//if(verified!=null){
			ret+="verified="+verified+";";
		//}
		//if(enabled!=null){
			ret+="enabled="+enabled+";";
		//}
		if(formattedNumber!=null){
			ret+="formattedNumber="+formattedNumber+";";
		}
		
		return ret;
		
	}

	public static PhoneOld[] jsonObjectToPhone(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
