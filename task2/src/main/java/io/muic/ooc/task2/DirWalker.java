package io.muic.ooc.task2;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ice on 1/17/17.
 */
public class DirWalker extends DirectoryWalker {
    private static int fileCount = 0;
    private static int dirCount = 0;
    private static HashMap<String, Integer> eachExtCount = new HashMap<>();


    public DirWalker() {
        super();
    }

    public List start(File startDirectory) throws IOException {
        List results = new ArrayList();
        walk(startDirectory, results);
        return results;
    }


    @Override
    protected void handleFile(File file, int depth, Collection results) throws IOException {
        fileCount += 1;

        String extension = FilenameUtils.getExtension(file.toString());
        if (!extension.equals("")) {

            if (eachExtCount.keySet().contains(extension)) {
                int tempCount = eachExtCount.get(extension);
                tempCount += 1;
                eachExtCount.replace(extension, tempCount);
            } else {
                eachExtCount.put(extension, 1);

            }
        }
    }


    @Override
    protected boolean handleDirectory(File directory, int depth, Collection results) throws IOException {
        dirCount += 1;
        return true;
    }

    public static int getFileCount() {
        return fileCount;
    }

    public static int getDirCount() {
        return dirCount;
    }

    public static HashMap<String, Integer> getEachExtCount() {
        return eachExtCount;
    }
}
