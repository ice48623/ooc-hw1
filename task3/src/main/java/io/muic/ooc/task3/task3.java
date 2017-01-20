package io.muic.ooc.task3;

import org.apache.commons.cli.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ice on 1/18/17.
 */
public class task3 {
//    private static String URL = "http://www.petreetpetfood.com/img/kitten.png";
//    private static String SAVEPATH = "/home/ice/Desktop/ooc/Assignment/a1/kitten.png";
    private static int BUFFER_SIZE = 4096;

    public static void UseUrlConnection(String inputURL, String outputPath) {
        try {
            URL url = new URL(inputURL);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();

            FileOutputStream outputStream = new FileOutputStream(outputPath);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UseApacheCommon(String inputURL, String outputPath) {
        try {
            File outputFile = new File(outputPath);
            URL url = new URL(inputURL);
            org.apache.commons.io.FileUtils.copyURLToFile(url, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void UseHttpComponent(String inputURL, String outputPath) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(inputURL);
            HttpResponse response = client.execute(get);
            byte[] buffer = new byte[BUFFER_SIZE];
            InputStream input = response.getEntity().getContent();
            OutputStream output = new FileOutputStream(outputPath);
            for (int length; (length = input.read(buffer)) > 0;) {
                output.write(buffer, 0, length);
            }
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption("a", "URLConnection", false, "Download file using URLConnection");
        options.addOption("b", "ApacheCommon", false, "Download file using ApacheCommon");
        options.addOption("c", "HTTPComponent", false, "Download file using HTTPComponent");
        options.addOption(Option.builder()
                .longOpt("e")
                .desc("input file URL")
                .hasArg()
                .argName("url-to-file")
                .build()
        );
        options.addOption(Option.builder("f")
                .desc("output file path/filename")
                .hasArg()
                .argName("save-path")
                .build()
        );

        try {
            CommandLine line = parser.parse(options, args);

            String inputURL = line.getOptionValue("e");
            String outputPath = line.getOptionValue("f");

            if (line.hasOption("URLConnection")) {
                UseUrlConnection(inputURL, outputPath);
            } else if (line.hasOption("ApacheCommon")) {
                UseApacheCommon(inputURL, outputPath);
            } else if (line.hasOption("HTTPComponent")) {
                UseHttpComponent(inputURL, outputPath);
            }

        } catch (ParseException exp) {
            System.out.println("Unexpected exception:" + exp.getMessage());

        }

    }
}
