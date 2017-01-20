package io.muic.ooc.task1;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ice on 1/17/17.
 */
public class main {
    public static void printAllExtension(HashMap<String, Integer> allExt) {
        for (String key : allExt.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();
    }

    public static void printEachExtCount(HashMap<String, Integer> eachExtCount) {
        for (String key : eachExtCount.keySet()) {
            System.out.print(key + " : " + eachExtCount.get(key) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        File rootPath = new File("/home/ice/Desktop/ooc/Assignment/a1/docs/");

        DirWalker walker = new DirWalker();
        walker.start(rootPath);
        System.out.println(walker.getFileCount());
        System.out.println(walker.getDirCount());
        HashMap<String, Integer> allExt = walker.getEachExtCount();
        System.out.println(allExt.size());
        printAllExtension(allExt);
        printEachExtCount(allExt);

    }
}
