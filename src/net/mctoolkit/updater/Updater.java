package net.mctoolkit.updater;

import com.sun.javaws.exceptions.ExitException;
import net.mcreator.io.net.api.D8WebAPI;
import net.mcreator.io.net.api.IWebAPI;
import net.mcreator.io.net.api.update.UpdateInfo;

public class Updater {

    public static void main(String[] args) {
        System.out.println("lancement");
        System.exit(1);
        System.out.println("redémarrage");
    }

    public Updater(String folder, String url) {
        IWebAPI webAPI = new D8WebAPI();
        boolean isInternet = webAPI.initAPI();

        if(isInternet) {
            UpdateInfo updateInfo = webAPI.getUpdateInfo();
            //if(updateInfo.getReleases())
        }
    }
}
