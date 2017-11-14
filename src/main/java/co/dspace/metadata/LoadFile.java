package co.dspace.metadata;

import java.util.List;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import co.dspace.metadata.commons.CommonConstants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

/**
 * Servlet implementation class LoadFile
 */
@WebServlet("/LoadFile")
public class LoadFile extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadFile() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //response.getWriter().append("Served at: ").append(request.getContextPath());

        String archivo = "", fileName, linea, cadenaCompleta = "";

        if (ServletFileUpload.isMultipartContent(request)) {

            try {

                ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
                List fileItemsList = servletFileUpload.parseRequest(request);
                org.apache.commons.fileupload.FileItem fileItem = null;
                Iterator it = fileItemsList.iterator();
                while (it.hasNext()) {
                    FileItem fileItemTemp = (FileItem) it.next();
                    if (!fileItemTemp.isFormField()) {
                        if (fileItemTemp.getContentType().equals("text/plain")) {
                            archivo = fileItemTemp.getName();
                            fileItem = fileItemTemp;
                            break;
                        }
                    }
                }

                if (fileItem != null) {
                    if (fileItem.getSize() > 0) {
                        String folder = CommonConstants.UPLOAD_DIRECTORY;
                        File folders = new File(folder);
                        if (!folders.exists()) {
                            folders.mkdirs();
                        }
                        fileName = CommonConstants.UPLOAD_DIRECTORY + archivo;
                        File saveTo = new File(fileName);
                        fileItem.write(saveTo);
                        FileInputStream fis = new FileInputStream(saveTo);
                        DataInputStream dis = new DataInputStream(fis);

                        while ((linea = dis.readLine()) != null) {
                            cadenaCompleta += linea + "||";
                        }

                        cadenaCompleta = cadenaCompleta.substring(0, cadenaCompleta.length() - 2);

                        fis.close();
                        dis.close();
                        folders.setReadOnly();

                        File f = new File(CommonConstants.UPLOAD_DIRECTORY + "metadatos.txt");
                        FileOutputStream fos = new FileOutputStream(f);
                        DataOutputStream dos = new DataOutputStream(fos);
                        dos.writeBytes(cadenaCompleta);
                        fos.close();
                        dos.close();
                        
                        request.setAttribute("message", "File Uploaded Successfully");
                        request.getRequestDispatcher("/result.jsp").forward(request, response);

                        response.setContentType("application/txt");
                        response.setContentLength((int) f.length());

                        response.setHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                        response.setHeader("cache-control", "no-cache");
                        byte baz[] = new byte[(int) f.length()];
                        FileInputStream in = new FileInputStream(f);
                        OutputStream os = response.getOutputStream();

                        in.read(baz);
                        os.write(baz);
                        os.flush();

                        in.close();
                        os.close();
                        

                    }
                }

                

            } catch (Exception e) {
                request.setAttribute("message", "File Upload Failed due to " + e);
            }

        } else {
            request.setAttribute("message",
                    "Sorry this Servlet only handles file upload request");
        }

        

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
