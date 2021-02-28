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
package net.mctoolkit.updater;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Updater {

    public static Updater INSTANCE;
    private JProgressBar bar;
    private JProgressBar bar2;

    public Updater(UpdaterInfo info) {
        INSTANCE = this;

        String dest = info.getDestFile().getPath();

        JFrame frame = createJFrame();
        try {
            bar = new JProgressBar(JProgressBar.HORIZONTAL, 0, info.getUrl().openConnection().getContentLength());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        bar.setValue(0);
        bar.setStringPainted(true);
        frame.add(bar);
        frame.setVisible(true);
        frame.setTitle("Downloading...");
        FileUtils.downloadFile(info.getUrl(), new File(dest));

        File zipFile = new File(dest);
        File destFile = new File(dest.replace(info.getNameZip() + ".zip", ""));
        frame.setTitle("Unzipping...");

        bar2 = new JProgressBar(JProgressBar.HORIZONTAL, 0, (int) (zipFile.length() / (1024 * 1024)));
        bar2.setValue(0);
        bar2.setStringPainted(true);
        bar.setVisible(false);
        frame.add(bar2);
        FileUtils.unzip(dest, destFile);
        zipFile.delete();
        String[] options = {"Close"};
        int option = JOptionPane.showOptionDialog(frame,
                "The installation is completed." + "\nPlease restart " + info.getAppName() + " to apply changes.",
                "Installation completed", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                options, options[0]);
        if (option == 0)
            System.exit(0);
    }

    private static JFrame createJFrame() {
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setSize(new Dimension(330, 87));
        frame.setLocationRelativeTo(null);
        frame.setCursor(Cursor.WAIT_CURSOR);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    public JProgressBar getBar() {
        return bar;
    }

    public JProgressBar getBar2() {
        return bar2;
    }
}
