package com.happy.para.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;
// DownloadView 클래스가 뷰 페이지로 동작 AbstractView 를 상속 받음
public class DownloadView extends AbstractView{

    public DownloadView(){
        setContentType("application/download; utf-8");    
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {        


         File file = (File)model.get("downloadFile"); //downloadFile의 이름으로 삽입된 파일!
         String original_name = file.getName().substring((file.getName().indexOf("_")+1));
         
         System.out.println("DownloadView --> file.getPath() : " + file.getPath());        
         System.out.println("DownloadView --> file.getName() : " + file.getName());
         System.out.println("DownloadView --> file.sasdasdwwd() : " + original_name);
         
         response.setContentType(getContentType());        
         response.setContentLength((int)file.length());        
         
         String userAgent = request.getHeader("User-Agent");        
         
         boolean ie = userAgent.indexOf("MSIE") > -1;                
         String fileName = null; 
         
             if(ie){                         
                 fileName = URLEncoder.encode(original_name, "utf-8").replace("+","%20");                 
             } else {
                 fileName = new String(original_name.getBytes("utf-8"), "iso-8859-1").replace("+","%20");
             }// end if;

         response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");         
         response.setHeader("Content-Transfer-Encoding", "binary");                 
         OutputStream out = response.getOutputStream();                 
         FileInputStream fis = null;
         
         try {                         
             fis = new FileInputStream(file);                         
             FileCopyUtils.copy(fis, out);                                  
         } catch(Exception e){                         
             e.printStackTrace();                     
         }finally{                         
             if(fis != null){                                 
                 try{                    
                     fis.close();                
                 }catch(Exception e){
                     e.printStackTrace();                     
                 }            
             }                     
         }// try end;                 
         
         out.flush();

    }
}

