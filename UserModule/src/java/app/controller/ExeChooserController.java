/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import java.io.File;
import java.io.IOException;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONValue;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;


/**
 *
 * @author Martin
 */
public class ExeChooserController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    /*
    public void listf(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }*/
    
    /*
    public JSONArray listfJSON(String directoryName) {
        JSONArray files = new JSONArray();
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file.getName());
            } 
            
            else if (file.isDirectory()) {
                Map folder = new LinkedHashMap();
                folder.put(file.getName(), listfJSON(file.getAbsolutePath()));
                files.add(folder);
            }
        }
        
        return files;
    }
    */
    
    public JSONArray listfJSON(String directoryName) {
        JSONArray files = new JSONArray();
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                Map f = new LinkedHashMap();
                f.put("type", "file");
                f.put("name", file.getName());
                files.add(f);
            } 
            
            else if (file.isDirectory()) {
                Map folder = new LinkedHashMap();
                folder.put("type", "folder");
                folder.put("name", file.getName());
                folder.put("child", listfJSON(file.getAbsolutePath()));
                //folder.put(file.getName(), listfJSON(file.getAbsolutePath()));
                files.add(folder);
            }
        }
        
        return files;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        System.out.println("JSON: " +  listfJSON("C:\\FileUploadTest\\list"));
        
            JSONArray filesInDir = new JSONArray();
            filesInDir.add("f3");
            filesInDir.add("f4");
        
            Map folder = new LinkedHashMap(); // linked wegen ordered
            folder.put("dir",filesInDir);
        
            
            JSONArray list = new JSONArray();
            list.add("f1");
            list.add("f2");
            list.add(folder);
            System.out.print(list);
            
            

        
            /*
            Map obj=new LinkedHashMap();
            obj.put("name","foo");
            obj.put("num",new Integer(100));
            obj.put("balance",new Double(1000.21));
            obj.put("is_vip",new Boolean(true));
            obj.put("nickname",null);
            StringWriter out = new StringWriter();
            JSONValue.writeJSONString(obj, out);
            String jsonText = out.toString();
            System.out.print(jsonText);
            
            System.out.println("JSON abgeschickt");
            */
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
