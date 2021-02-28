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

    public static void downloadFile(URL url, File destFile) {
        BufferedInputStream in;
        try {
            in = new BufferedInputStream(url.openStream());
            FileOutputStream output = new FileOutputStream(destFile);
            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                Updater.INSTANCE.getBar().setValue((int) output.getChannel().size());
            }
            output.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void unzip(String strZipFile, File dst) {
        Path extractFolder = dst.toPath();
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(new File(strZipFile).toPath()))) {
            ZipEntry entry;
            int current = 0;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path toPath = extractFolder.resolve(entry.getName());
                current += entry.getCompressedSize();
                Updater.INSTANCE.getBar2().setValue(current / (1024 * 1024));
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
