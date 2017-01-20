package io.muic.ooc.task2;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ice on 1/17/17.
 */
public class task2 {
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


        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption("a", "total-num-files", false, "The total number of files");
        options.addOption("b", "total-num-dirs", false, "Total number of directory");
        options.addOption("c", "total-unique-exts", false, "Total number od unique file extensions");
        options.addOption("d", "list-exts", false, "List all unique file extensions. Do not list duplicates");
        options.addOption(Option.builder()
                .longOpt("num-ext")
                .desc("List total number of file for specified extension EXT")
                .hasArg()
                .argName("EXT")
                .build()
        );
        options.addOption(Option.builder()
                .longOpt("f")
                .desc("Path to the documentation folder. This is a required argument")
                .hasArg()
                .argName("path-to-folder")
                .build()
        );

        try {
            CommandLine line = parser.parse(options, args);

            File rootPath = new File(line.getOptionValue("f"));
            DirWalker walker = new DirWalker();
            walker.start(rootPath);
            HashMap<String, Integer> allExt = walker.getEachExtCount();



            if (line.hasOption("total-num-files")) {
                System.out.println(walker.getFileCount());
            }
            if (line.hasOption("total-num-dirs")) {
                System.out.println(walker.getDirCount());
            }
            if (line.hasOption("total-unique-exts")) {
                System.out.println(allExt.size());
            }
            if (line.hasOption("list-exts")) {
                printAllExtension(allExt);
            }
            if (line.hasOption("num-ext")) {
                System.out.println(allExt.get(line.getOptionValue("num-ext")));
            }
        } catch (ParseException exp) {
            System.out.println("Unexpected exception:" + exp.getMessage());

        }

    }
}
