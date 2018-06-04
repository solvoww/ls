package pl.poznan.ww.ls;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.poznan.ww.ls.exceptions.LsPathNotFoundException;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = LsServer.class)

public class LsControllerTest {
    
    @Autowired
    private LsController lsController;
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    public LsControllerTest() {
    }
    
    /**
     * Test of postConstruct method, of class LsController.
     */
    @Test
    public void testPostConstruct() {
        System.out.println("--- test postConstruct");
        assertNotEquals(lsController.currentPath(), null);
    }

    /**
     * Test of responseStatus method, of class LsController.
     */
    @Test
    public void testResponseStatus() {
        System.out.println("--- test responseStatus");
        ResponseEntity re = lsController.responseStatus(HttpStatus.MULTI_STATUS);
        assertNotEquals(re, null);
        assertEquals(re.getStatusCode(), HttpStatus.MULTI_STATUS);
    }

    /**
     * Test of home method, of class LsController.
     */
    @Test
    public void testHome() {
        System.out.println("--- test home");
        lsController.postConstruct();
        List<LsFile> result = lsController.home();
        assertNotEquals(result, null);
    }

    /**
     * Test of setPath method, of class LsController.
     */
    @Test
    public void testSetPath() {
        System.out.println("--- test setPath");
        lsController.setPath("/pathOK/");
        assertNotEquals(lsController.currentPath(), null);
        assertEquals(lsController.currentPath().getPath(), "/pathOK/");
    }

    /**
     * Test of byQPath method, of class LsController.
     */
    @Test
    public void testByQPath() {
        System.out.println("--- test byQPath");
        lsController.postConstruct();
        List<LsFile> result = lsController.byQPath();
//        assertFalse(Arrays.equals(result, null));
        assertNotEquals(result, null);
    }
    @Test
    public void testByQPathErr() {
        System.out.println("--- test byQPath on error");
        lsController.currentPath().setPath("errorPath");
        exception.expect(LsPathNotFoundException.class);
        lsController.byQPath();
    }

    /**
     * Test of currentPath method, of class LsController.
     */
    @Test
    public void testCurrentPath() {
        System.out.println("--- test currentPath");
        LsPath result = lsController.currentPath();
        assertNotEquals(result, null);
    }
    
    /**
     * Test of viewFile method, of class LsController.
     */
    @Test
    public void testViewFile() {
        System.out.println("--- test viewFile");
        lsController.postConstruct();
        FileWriter fileWrite;
        try {
            fileWrite = new FileWriter(lsController.currentPath().getPath() + "alamakota12.txt", true);
            fileWrite.write("Ala ma kota 12");
            fileWrite.close();        
        } catch (IOException ex) {
            Logger.getLogger(LsControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ResponseEntity<ByteArrayResource> re = lsController.viewFile("alamakota12.txt");
        assertNotEquals(re, null);
        assertNotEquals(re.getBody(), null);
        
        File file = new File(lsController.currentPath().getPath() + "alamakota12.txt");
        file.delete();
    }

    /**
     * Test of rename method, of class LsController.
     */
    @Test
    public void testRename() {
        System.out.println("--- test rename");
        lsController.postConstruct();
        FileWriter fileWrite;
        try {
            fileWrite = new FileWriter(lsController.currentPath().getPath() + "alamakota12.txt", true);
            fileWrite.write("Ala ma kota 12");
            fileWrite.close();        
        } catch (IOException ex) {
            Logger.getLogger(LsControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ResponseEntity re = lsController.rename("alamakota12.txt", "alamakota21.txt");
        assertNotEquals(re, null);
        
        File file = new File(lsController.currentPath().getPath() + "alamakota21.txt");
        
        boolean isE = file.exists();

        assertEquals(isE, true);

        file.delete();
        
        file = new File("alamakota21.txt");
        file.delete();
    }

    /**
     * Test of remove method, of class LsController.
     */
    @Test
    public void testRemove() {
        System.out.println("--- test remove");
        lsController.postConstruct();
        FileWriter fileWrite;
        try {
            fileWrite = new FileWriter(lsController.currentPath().getPath() + "alamakota12.txt", true);
            fileWrite.write("Ala ma kota 12");
            fileWrite.close();        
        } catch (IOException ex) {
            Logger.getLogger(LsControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ResponseEntity re = lsController.remove("alamakota12.txt");
        assertNotEquals(re, null);
        assertEquals(re.getStatusCode(), HttpStatus.OK);
        
        re = lsController.remove("alamakota12.txt");
        assertNotEquals(re, null);
        assertEquals(re.getStatusCode(), HttpStatus.NO_CONTENT);
        
    }

}
