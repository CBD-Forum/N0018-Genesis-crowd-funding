package cn.com.itrus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import com.fbd.core.common.FbdCoreConstants;

public class Config {
	
	private String URL;
	private String AccountHash;
	private String Passwprd;
	
	public Config(){

	    String ra_url = "";
	    String ra_AccountHash = "";
	    String ra_Passwprd = "";
	    if(FbdCoreConstants.RA_URL!=null){
	        ra_url = FbdCoreConstants.RA_URL;
	    }else{
	        ra_url = "http://10.112.33.176:8080/TopCA/services/userAPI?wsdl";
	    }
        if(FbdCoreConstants.RA_AccountHash!=null){
            ra_AccountHash = FbdCoreConstants.RA_AccountHash;
        }else{
            ra_AccountHash = "A44C2C3E7E0D35735BD03BBC2B6D0AAD";
        }
        if(FbdCoreConstants.RA_Password!=null){
            ra_Passwprd = FbdCoreConstants.RA_Password;
        }else{
            ra_Passwprd = "password";
        }
	    this.setURL(ra_url);
		this.setAccountHash(ra_AccountHash);
		this.setPasswprd(ra_Passwprd);
		
	}

	
	public String getURL() {
		return URL;
	}
	
	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getAccountHash() {
		return AccountHash;
	}

	public void setAccountHash(String accountHash) {
		AccountHash = accountHash;
	}

	public String getPasswprd() {
		return Passwprd;
	}

	public void setPasswprd(String passwprd) {
		Passwprd = passwprd;
	}
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new Config().getURL());
	}

}
