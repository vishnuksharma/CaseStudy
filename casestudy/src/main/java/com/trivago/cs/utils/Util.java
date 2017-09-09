package com.trivago.cs.utils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.commons.validator.routines.UrlValidator;

public class Util {
	
	public static boolean isValidUTF8( String in ) {
		byte[] input = in.getBytes();
	    CharsetDecoder cs = Charset.forName("UTF-8").newDecoder();

	    try {
	        cs.decode(ByteBuffer.wrap(input));
	        return true;
	    }
	    catch(CharacterCodingException e){
	        return false;
	    }       
	}
	
	public static boolean isValidUrl(String s) {
		UrlValidator urlValidator = new UrlValidator();
		
		try {
            return urlValidator.isValid(s);
	        } catch (RuntimeException e) {
	        return false;
	    }
	}

	
	public static void makeDirIfNotExist(String uPLOADED_FOLDER) {
		File f = new File(uPLOADED_FOLDER);
		if (!f.exists()) {
			f.mkdirs();
		}
		
	}

}
