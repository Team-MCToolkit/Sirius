package net.mctoolkit.updater;

import net.mcreator.io.OS;
import java.io.File;

public class Updater {

    public Updater(String version) {
        String osName;
        String extension;
        if(OS.getOS() == OS.WINDOWS) {
            osName = "Windows";
            extension = ".zip";
            if(OS.getSystemBits() == OS.BIT32){
                osName = osName + "32";
            } else if(OS.getSystemBits() == OS.BIT64) {
                osName = osName + ".64bit";
            }
        } else if(OS.getOS() == OS.MAC) {
            osName = "mac";
            extension = ".dmg";
        } else {
            osName = "linux";
            extension = ".tar.gz";
            if(OS.getSystemBits() == OS.BIT32){
                osName = osName + "32";
            } else if(OS.getSystemBits() == OS.BIT64) {
                osName = osName + "64";
            }
        }

        String url = "https://github.com/Team-MCToolkit/MCToolkit-Web-API/releases/download/test/MCToolkit." + version + "." + osName + extension;
        String destFile = System.getProperty("user.dir") + File.separator + "mctoolkit" + extension;

        FileUtils.downloadFile(url, destFile);
        FileUtils.unzip(destFile, destFile.replace("mctoolkit" + extension, ""));
        File file= new File(destFile);
        file.delete();
    }
}
