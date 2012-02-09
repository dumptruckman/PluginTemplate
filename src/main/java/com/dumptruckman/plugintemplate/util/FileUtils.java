/******************************************************************************
 * Multiverse 2 Copyright (c) the Multiverse Team 2011.                       *
 * Multiverse 2 is licensed under the BSD License.                            *
 * For more information please check the README.md file included              *
 * with this project.                                                         *
 ******************************************************************************/

package com.dumptruckman.plugintemplate.util;

import java.io.File;

/**
 * File-utilities.
 */
public class FileUtils {

    protected FileUtils() {
        throw new AssertionError();
    }

    /**
     * Used to delete a folder.
     *
     * @param file The folder to delete.
     * @return true if the folder was successfully deleted.
     */
    public static boolean deleteFolder(File file) {
        if (file.exists()) {
            // If the file exists, and it has more than one file in it.
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    if (!FileUtils.deleteFolder(f)) {
                        return false;
                    }
                }
            }
            file.delete();
            return !file.exists();
        } else {
            return false;
        }
    }

}
