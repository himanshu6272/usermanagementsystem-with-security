package com.springboot.UserManagementSystem.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Component
public class EncryptPwd {

	public String encryption(String password)
	{
		
	        String encryptedpassword = null;  
	        try {
	        MessageDigest m = MessageDigest.getInstance("MD5");  
	              
	            /* Add plain-text password bytes to digest using MD5 update() method. */  
	           m.update(password.getBytes("UTF-8"));  
	              
	            /* Convert the hash value into bytes */   
	            byte[] bytes = m.digest();  
	              
	            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */  
	            StringBuilder s = new StringBuilder();  
	            for(int i=0; i< bytes.length ;i++)  
	            {  
	                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
	            }  
	              
	            encryptedpassword = s.toString(); 
	        }
	        catch(Exception e)
	        {
	        	System.out.println(e);
	        }
	            return encryptedpassword;
	}
}
