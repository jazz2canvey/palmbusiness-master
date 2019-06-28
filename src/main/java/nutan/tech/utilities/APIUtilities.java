package nutan.tech.utilities;

import java.io.*;
import java.security.SecureRandom;

public class APIUtilities {

	public static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private final static String vars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static SecureRandom secureRandom = new SecureRandom();
	
	public static String generateRandom(int len) {
		
		   StringBuilder sb = new StringBuilder(len);
		   for(int i = 0; i < len; i++ ) 
		      sb.append(vars.charAt(secureRandom.nextInt(vars.length()) ) );

		   return sb.toString();
	}

	public static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

		try {
			int read;
			byte[] bytes = new byte[1024];

			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}			
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
