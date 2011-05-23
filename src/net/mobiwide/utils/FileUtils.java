package net.mobiwide.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import android.util.Log;

public class FileUtils {

	private static final String TAG = "FileUtils";

	public static void inputStreamToFile ( InputStream in, String pathToFile ) throws IOException {

		File ligneXML = new File(pathToFile);

		OutputStream out = new FileOutputStream(ligneXML);
		byte buf[] = new byte[1024];
		int len;

		while ( (len=in.read(buf)) > 0 ) {
			out.write(buf, 0, len);
		}

		out.close();
		in.close();

	}

	
	public static void writeStringToFile(String content, String pathToFile) throws IOException {
	
		File htmlFile = new File(pathToFile);
		OutputStream out = new FileOutputStream(htmlFile);
		byte [] buff =  content.getBytes();
		out.write(buff);
		out.close();
		
		Log.i(TAG, "Writing finished");

	}
	
	
}
