package app.helper;

import app.beans.FileStatus;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Martin
 */
public class FileUpload {
    
    private FileStatus fileStatus;
    private int maxFileSize, maxMemSize; //  = 5000 * 1024
    private String filePath, tempFolder;
    
    public FileUpload(int maxFileSize, int maxMemSize, String filePath, String tempFolder){
        fileStatus = new FileStatus();
        this.maxFileSize = maxFileSize;
        this.maxMemSize = maxMemSize;
        this.filePath = filePath;
        this.tempFolder = tempFolder;
    }
 
    public File uploadFile(HttpServletRequest req) throws SizeLimitExceededException, FileUploadException, Exception{
        
        File file = null;
        DiskFileItemFactory factory = new DiskFileItemFactory();
      
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File(tempFolder));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );
      
        
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(req);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            //System.out.println("fileUPload filepath: " + filePath);
            while ( i.hasNext() ) {
               FileItem fi = (FileItem)i.next();
               if ( !fi.isFormField() ){
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    
                    fileStatus.setFileName(fileName);
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    // Write the file
                    
                    if( fileName.lastIndexOf("\\") >= 0 ){
                        file = new File( filePath + "/" +  fileName.substring(fileName.lastIndexOf("\\")));
                        System.out.println("1");
                    }else{
                        file = new File( filePath + "/" + fileName.substring(fileName.lastIndexOf("\\")+1));
                        System.out.println("2");
                    }
                    fi.write( file );
               }
            }
            
            
            //wenn erfolgreich hochgeladen
            
            //fileStatus.setFileName(file.getAbsolutePath());
            //fileStatus.setFilePath(filePath);  
        
        return file;
    }
}
