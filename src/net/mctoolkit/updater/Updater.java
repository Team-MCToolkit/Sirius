package net.mctoolkit.updater;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Updater {

    public static Updater INSTANCE;
    private JProgressBar bar;
    private JProgressBar bar2;

    public Updater(String version) {
        INSTANCE = this;

        String url = "https://github.com/Team-MCToolkit/MCToolkit-Web-API/releases/download/MCToolkit/MCToolkit" + version + ".zip";
        String dest = System.getProperty("user.dir") + File.separator + "mctoolkit.zip";

        JFrame frame = createJFrame();
        try {
            bar = new JProgressBar(JProgressBar.HORIZONTAL, 0, new URL(url).openConnection().getContentLength());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        bar.setValue(0);
        bar.setStringPainted(true);
        frame.add(bar);
        frame.setVisible(true);
        frame.setTitle("Downloading...");
        FileUtils.downloadFile(url, dest);

        File zipFile = new File(dest);
        File destFile = new File(dest.replace("mctoolkit.zip", ""));
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
                "The installation is completed." + "\nPlease restart MCToolkit to apply changes.",
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
