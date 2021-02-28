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
