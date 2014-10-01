/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.model;

import app.beans.FileStatus;
import app.helper.FileUpload;
import app.helper.UnZip;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Martin
 */
public class GameUploadModel {
 
    
    public void uploadGame(HttpServletRequest req){
        //int maxFileSize, int maxMemSize, String saveFolder, String tempFolder
        FileUpload upload = new FileUpload(5000 * 1024, 5000 * 1024, "c:\\FileUploadTest\\", "c:\\temp");
        FileStatus file = upload.uploadFile(req);
        UnZip unZip = new UnZip();
        unZip.unZipIt(file.getFullPath(), file.getFilePath());
    }
    
   
    
}
