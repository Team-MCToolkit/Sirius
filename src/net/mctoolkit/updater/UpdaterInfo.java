package net.mctoolkit.updater;

import java.io.File;
import java.net.URL;

public class UpdaterInfo {

    private final String appName;
    private final URL url;
    private final File destFile;
    private final String nameZip;

    public UpdaterInfo(String appName, URL url, File destFile, String nameZip) {
        this.appName = appName;
        this.url = url;
        this.destFile = destFile;
        this.nameZip = nameZip;
    }

    public String getAppName() {
        return appName;
    }

    public URL getUrl() {
        return url;
    }

    public File getDestFile() {
        return destFile;
    }

    public String getNameZip() {
        return nameZip;
    }
}
