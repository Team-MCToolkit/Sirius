/*
Copyright 2021 MCToolkit

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package net.mctoolkit.sirius;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Updater extends JFrame {

    public static Updater INSTANCE;
    public UpdaterInfo info;
    private JProgressBar bar;
    private JProgressBar bar2;

    /*
    args[0] = The name of the app
    args[1] = The URL of the file to download
    args[2] = The folder to unzip the file
    args[3] = The name to give to the ZIP file
    args[4] = The app to launch when the installation is finished
     */
    public static void main(String[] args) {
        UpdaterInfo info = null;
        try {
            info = new UpdaterInfo(args[0], new URL(args[1]), new File(args[2]), args[3]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Updater updater = new Updater(info);
        //updater.startDownloading();
        //updater.startUnzipping();
        updater.launchApp(args[4]);
    }

    public Updater(UpdaterInfo info) {
        INSTANCE = this;
        this.info = info;

        setResizable(false);
        setSize(new Dimension(330, 87));
        setLocationRelativeTo(null);
        setCursor(Cursor.WAIT_CURSOR);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void startDownloading() {
        try {
            bar = new JProgressBar(JProgressBar.HORIZONTAL, 0, info.getUrl().openConnection().getContentLength());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        bar.setValue(0);
        bar.setStringPainted(true);
        add(bar);
        setVisible(true);
        setTitle("Downloading...");
        FileUtils.downloadFile(info.getUrl(), new File(info.getDestFolder().getPath() + File.separator + info.getNameZip()));
    }

    private void startUnzipping() {
        setTitle("Unzipping...");
        String dest = info.getDestFolder().getPath() + File.separator + info.getNameZip();
        File zipFile = new File(dest);
        bar2 = new JProgressBar(JProgressBar.HORIZONTAL, 0, (int) (zipFile.length() / (1024 * 1024)));
        bar2.setValue(0);
        bar2.setStringPainted(true);
        bar.setVisible(false);
        add(bar2);
        FileUtils.unzip(dest, new File(dest.replace(info.getNameZip() + ".zip", "")), info.getNameZip());
        zipFile.delete();

        //When we have unzipped the file and deleted it, we say to the user to close the program.
        String[] options = {"Close"};
        int option = JOptionPane.showOptionDialog(this,
                "The installation is completed." + "\nPlease restart " + info.getAppName() + " to apply changes.",
                "Installation completed", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                options, options[0]);
        if (option == 0)
            System.exit(0);
    }

    private void launchApp(String app) {
        try {
            ProcessBuilder p = new ProcessBuilder();

            String os = System.getProperty("os.name");
            if(os.contains("Windows"))
                Runtime.getRuntime().exec("cmd /c start \"\" LICENSE.txt");
            else if(os.contains("Mac") || os.contains("OS X"))
                Runtime.getRuntime().exec("cmd /c $ open \"\" " + app);
            else if(os.contains("Linux"))
                Runtime.getRuntime().exec("cmd /c open \"\" " + app);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public JProgressBar getBar() {
        return bar;
    }

    public JProgressBar getBar2() {
        return bar2;
    }
}
