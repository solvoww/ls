package pl.poznan.ww.ls;

public class LsFile {
    
    private char type;
    private String canonicalPath;
    private String name;
    private String modDate;
    private Long size;
    private String attr;
    
    public LsFile(char type, String canonicalPath, String name, String modDate, Long size, String attr) {
        
        canonicalPath = canonicalPath.replaceAll("/", "&");
        canonicalPath = canonicalPath.replaceAll("\\\\", "&");
        
        this.type = type;
        this.canonicalPath = canonicalPath;
        this.name = name;
        this.modDate = modDate;
        this.size = size;
        this.attr = attr;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getCanonicalPath() {
        return canonicalPath;
    }

    public void setCanonicalPath(String canonicalPath) {
        this.canonicalPath = canonicalPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
    
}
