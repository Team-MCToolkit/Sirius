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

public class Updater extends JFrame{

    public static Updater INSTANCE;
    public UpdaterInfo info;
    private JProgressBar bar;
    private JProgressBar bar2;

    public Updater(UpdaterInfo info) {
        INSTANCE = this;
        this.info = info;

        setResizable(false);
        setSize(new Dimension(330, 87));
        setLocationRelativeTo(null);
        setCursor(Cursor.WAIT_CURSOR);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startDownloading() {
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
        FileUtils.downloadFile(info.getUrl(), new File(info.getDestFile().getPath()));
    }

    public void startUnzipping() {
        setTitle("Unzipping...");
        String dest = info.getDestFile().getPath();
        File zipFile = new File(dest);
        bar2 = new JProgressBar(JProgressBar.HORIZONTAL, 0, (int) (zipFile.length() / (1024 * 1024)));
        bar2.setValue(0);
        bar2.setStringPainted(true);
        bar.setVisible(false);
        add(bar2);
        FileUtils.unzip(dest, new File(dest.replace(info.getNameZip() + ".zip", "")));
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

    public JProgressBar getBar() {
        return bar;
    }

    public JProgressBar getBar2() {
        return bar2;
    }
}
