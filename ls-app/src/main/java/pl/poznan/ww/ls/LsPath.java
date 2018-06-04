package pl.poznan.ww.ls;

public class LsPath {
    
    private String path;
    
    public LsPath(String path) {
        setPathI(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        setPathI(path);
    }
    
    private void setPathI(String path) {
        if (!path.endsWith("/")) {
            path += "/";
        }
        this.path = path;
    }
    
}
