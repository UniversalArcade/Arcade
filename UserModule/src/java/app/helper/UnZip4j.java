/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.helper;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author Martin
 */
public class UnZip4j {
   
    public static void unzip(String source, String destination){
        //String source = "some/compressed/file.zip";
        //String destination = "some/destination/folder";
        String password = "password";

        try {
             ZipFile zipFile = new ZipFile(source);
             zipFile.extractAll(destination);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
    
    
}
