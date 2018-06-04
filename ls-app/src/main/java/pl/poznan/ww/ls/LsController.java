package pl.poznan.ww.ls;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.ww.ls.exceptions.LsIOException;
import pl.poznan.ww.ls.exceptions.LsPathNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * A RESTFul controller for accessing list files
 * 
 * @author w.wozniak
 */
@RestController
public class LsController {

    @Autowired
    private LsProperties env;
    
    private LsPath currentPath;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd");

    @PostConstruct
    public void postConstruct() {
        if (env.getRootPath() != null) {
            currentPath = new LsPath(env.getRootPath());
        }
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public HttpServletResponse handleOpt(HttpServletResponse theHttpServletResponse) throws IOException {
        System.out.println("--- handleOpt");
        theHttpServletResponse.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
        theHttpServletResponse.addHeader("Access-Control-Max-Age", "60"); 
        theHttpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        theHttpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        return theHttpServletResponse;
    }
    
    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity responseStatus(HttpStatus httpStatus) {
        System.out.println("--- handle");

        return new ResponseEntity(httpStatus);
    }    
    
    /**
     * Fetch file list
     * 
     * @return file list.
     */
    @RequestMapping("/")
    @CrossOrigin
    public List<LsFile> home() {
        System.out.println("--- home: "+currentPath.getPath());
        currentPath = new LsPath(env.getRootPath());
        return lsByPath(currentPath.getPath());
    }

    /**
     * Set current path
     * 
     * @param path
     * @return HttpStatus.OK
     */
    @RequestMapping("/{path}")
    @CrossOrigin
    public ResponseEntity<?> setPath(@PathVariable String path) {

        System.out.println("--- setPath: "+path);

        path = path.replaceAll("&", "/");
        
        currentPath.setPath(path);
        
        return responseStatus(HttpStatus.OK);
    }
        
    /**
     * Fetch file list
     * 
     * @return file list.
     */
    @RequestMapping("/ls")
    @CrossOrigin
    public List<LsFile> byQPath() {

        System.out.println("--- byQPath: "+currentPath.getPath()+" "+env.getRootPath());
        
        LsFile upNode = null;
        
        if (!currentPath.getPath().equalsIgnoreCase(env.getRootPath())) {
        
            String prevPath = currentPath.getPath();
            prevPath = prevPath.substring(0, prevPath.length()-1);
            int lastibs = prevPath.lastIndexOf("/");
            if (lastibs >= 0) {
                prevPath = prevPath.substring(0, lastibs);
            }

            upNode = new LsFile('D', prevPath, "..", null, null, null);
        }
        
        List<LsFile> rt = lsByPath(currentPath.getPath());
        
        if (upNode != null) {
            rt.add(0, upNode);
        }
        return rt;
    }
        
    /**
     * Fetch current path
     * 
     * @return currentPath
     */
    @RequestMapping("/pwd")
    @CrossOrigin
    public LsPath currentPath() {
        System.out.println("--- currentPath: "+currentPath.getPath());
        return currentPath;
    }
    
    /**
     * Fetch file to view
     * 
     * @param fileName
     * @return file.
     */
    @RequestMapping("/cat/{fileName}")
    @CrossOrigin
    public ResponseEntity<ByteArrayResource> viewFile(@PathVariable String fileName) {
        
        System.out.println("--- viewFile: "+fileName);
        
        fileName = currentPath.getPath() + fileName;
        
        File file = new File(fileName);
        Path path = Paths.get(file.getAbsolutePath());
            
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
            
        try {
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            return ResponseEntity.ok()
                     .headers(headers)
                     .contentLength(file.length())
//                     .contentType(MediaType.parseMediaType("text/plain"))
                     .contentType(MediaType.parseMediaType("application/octet-stream"))
                     .body(resource);
   
        } catch (IOException ex) {
            Logger.getLogger(LsController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(null);
        }
    }
    
    /**
     * Rename file/dir
     * 
     * @param oldName
     * @param newName
     * @return HttpStatus.OK
     */
    @RequestMapping("/{oldName}/{newName}")
    @CrossOrigin
    public ResponseEntity<?> rename(@PathVariable String oldName, @PathVariable String newName) {

        System.out.println("--- rename: "+oldName+" to: "+newName);
        
        oldName = currentPath.getPath() + oldName;
        newName = currentPath.getPath() + newName;

        File fileOld = new File(oldName);
        File fileNew = new File(newName);

        if (fileNew.exists()) {
            return responseStatus(HttpStatus.CONFLICT);
        }
        else {
            if (fileOld.renameTo(fileNew)) {
                return responseStatus(HttpStatus.OK);
            }
            else {
                return responseStatus(HttpStatus.NO_CONTENT);
            }
        }
    }
        
    /**
     * Rename file/dir
     * 
     * @param name
     * @return HttpStatus.OK
     */
    @RequestMapping("/rm/{name}")
    @CrossOrigin
    public ResponseEntity<?> remove(@PathVariable String name) {

        System.out.println("--- remove: "+name);
        
        name = currentPath.getPath() + name;

        File file = new File(name);

        if (file.delete()) {
            return responseStatus(HttpStatus.OK);
        }
        else {
            return responseStatus(HttpStatus.NO_CONTENT);
        }
    }
        
    private List<LsFile> lsByPath(String path) {
        List<LsFile> files = null;

        try {
            
        File dir = new File(path);

        if (dir.isDirectory()) {
            File[] filesInDir = dir.listFiles();
            
            files = new ArrayList<>();
            
            char type;
            String lmDate;
            Long size;
            String attr;
            
            for (File fileInDir: filesInDir) {

                Path file = Paths.get(fileInDir.getCanonicalPath());
                BasicFileAttributes attrB = Files.readAttributes(file, BasicFileAttributes.class);
                DosFileAttributes attrD = Files.readAttributes(file, DosFileAttributes.class);
                
                if (attrB.isDirectory()) {
                    type = 'D';
                    size = null;
                }
                else {
                    type = 'F';
                    size = attrB.size();
                }

                lmDate = dateFormatter.format(new Date(attrB.lastModifiedTime().toMillis()));
                
                attr = "";
                if (attrD.isReadOnly()) {
                    attr += "readOnly";
                }
                if (attrD.isSystem()) {
                    attr += " system";
                }
                if (attrD.isHidden()) {
                    attr += " hidden";
                }
                
                files.add(new LsFile(type, fileInDir.getCanonicalPath(), fileInDir.getName(), lmDate, size , attr.trim()));
            }
        }
        else {
            throw new LsPathNotFoundException(path);
        }
        }
        catch (IOException ioex) {
            throw new LsIOException(path);
        }
        return files;
    }
        
}
