package net.mobiwide.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {


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
}
