package control;

import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Cyptografy {

	public static byte[] fileToBytes (File f)
	{
		try {
			byte[] bFile = new byte[(int) f.length()];
			FileInputStream fileInputStream = new FileInputStream(f);
		    fileInputStream.read(bFile);
		    fileInputStream.close();
		    return bFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] encrypt (byte[] bMsg, String key)
	{
		try {
			byte[] bKey = hexToBytes(key);
			
			Key ky = new SecretKeySpec(bKey, "AES");
	        Cipher cf = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cf.init(Cipher.ENCRYPT_MODE,ky);
	        byte[] bCryp = cf.doFinal(bMsg);
	        
	        //System.out.println("Key     : "+bytesToHex(bKey));
	        //System.out.println("bFile   : "+bytesToHex(bMsg));
	        //System.out.println("Cipher  : "+bytesToHex(bCryp));
	        return bCryp;
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static byte[] dencrypt (byte[] bMsg, String key)
	{
		try {
			byte[] bKey = hexToBytes(key);
			
			Key ky = new SecretKeySpec(bKey, "AES");
	        Cipher cf = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cf.init(Cipher.DECRYPT_MODE,ky);
	        byte[] bCryp = cf.doFinal(bMsg);
	        
	        //System.out.println("Key     : "+bytesToHex(bKey));
	        //System.out.println("bFile   : "+bytesToHex(bMsg));
	        //System.out.println("Cipher  : "+bytesToHex(bCryp));
	        return bCryp;
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static byte[] hexToBytes(String str) 
	{
	      if (str==null) {
	         return null;
	      } else if (str.length() < 2) {
	         return null;
	      } else {
	         int len = str.length() / 2;
	         byte[] buffer = new byte[len];
	         for (int i=0; i<len; i++) {
	             buffer[i] = (byte) Integer.parseInt(
	                str.substring(i*2,i*2+2),16);
	         }
	         return buffer;
	      }
	 }
	
	public static String bytesToHex(byte[] data) 
	{
	      if (data==null) {
	         return null;
	      } else {
	         int len = data.length;
	         String str = "";
	         for (int i=0; i<len; i++) {
	            if ((data[i]&0xFF)<16) str = str + "0" 
	               + java.lang.Integer.toHexString(data[i]&0xFF);
	            else str = str
	               + java.lang.Integer.toHexString(data[i]&0xFF);
	         }
	         return str.toUpperCase();
	      }
	}
	
	public static String getMD5 (File file) throws Exception
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			byte[] bytes = new byte[2048];
			int nBytes;
			FileInputStream input = new FileInputStream(file);
			
			while ((nBytes = input.read(bytes)) != -1) {
				md.update(bytes, 0, nBytes);
			}
			byte[] digest = md.digest();
			input.close();
			return bytesToHex(digest);			
			
		} catch (Exception e) {
			throw new Exception("Error generating MD5 Checksum");
		}
		
	}
	
}
