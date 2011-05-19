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
		
		PrintWriter print = new PrintWriter(htmlFile);
		
		print.print(content);
		
		Log.i(TAG, "Writing finished");
		
		// Affichage pour verifier le contenu :
		
//		InputStreamReader flog = null;
//		LineNumberReader llog = null;
//		String myLine = null;
//		try{ 
//			flog = new InputStreamReader(new FileInputStream("pathToFile") );
//			llog = new LineNumberReader(flog);
//			while ((myLine = llog.readLine()) != null) { 
//	                      // --- Affichage de la ligne lu depuis le fichier
//	                      System.out.println("Ligne : "+myLine);
//	                }
//	        }catch (Exception e){
//	               // --- Gestion erreur lecture du fichier (fichier non existant, illisible, etc.)
//	               System.err.println("Error : "+e.getMessage());
//	        }

	}
	
	
}
