package net.mctoolkit.updater;

import net.mcreator.Launcher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {

    public static void downloadFile(String url, String destFile) {
        BufferedInputStream in;
        try {
            in = new BufferedInputStream(new URL(url).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            fileOutputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void unzip(String strZipFile, String dst) {
        Path extractFolder = new File(dst).toPath();
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(new File(strZipFile).toPath()))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path toPath = extractFolder.resolve(entry.getName());
                if (entry.isDirectory()) {
                    toPath.toFile().mkdirs();
                } else {
                    toPath.toFile().getParentFile().mkdirs();
                    Files.copy(zipInputStream, toPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
