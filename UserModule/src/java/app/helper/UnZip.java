package app.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.IOUtils;


public class UnZip {
    
    public static void unzipit(File file, String outputDir){
 
        try (ZipFile zipFile = new ZipFile(file)) {
            
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            
            while (entries.hasMoreElements()) {
                
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(outputDir,  entry.getName());
                entryDestination.getParentFile().mkdirs();
                
                if (entry.isDirectory())
                    entryDestination.mkdirs();
                else {
                    InputStream in = zipFile.getInputStream(entry);
                    OutputStream out = new FileOutputStream(entryDestination);
                    IOUtils.copy(in, out);
                    IOUtils.closeQuietly(in);
                    IOUtils.closeQuietly(out);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(UnZip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
