package pl.poznan.ww.ls;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ls-properties")
public class LsProperties {

    private String rootPath;

    public LsProperties() {
    }

    public String getRootPath() {
        if (!rootPath.endsWith("/")) {
            rootPath += "/";
        }
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

}
