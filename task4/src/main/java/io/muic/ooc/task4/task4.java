package io.muic.ooc.task4;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by ice on 1/18/17.
 */
public class task4 {

//    private static String ROOT_PATH = "https://cs.muic.mahidol.ac.th/courses/ooc/docs/";
//    private static String DESC_ROOT_PATH = "/home/ice/Desktop/ooc/Assignment/a1/io.muic.ooc.task4/docs/";


    public static void main(String[] args) {
        try {
            Crawler crawler = new Crawler();
            crawler.setToDownloadUrl(new URL("https://cs.muic.mahidol.ac.th/courses/ooc/docs/"));
            crawler.setTargetPath(new File("/home/ice/Desktop/ooc/Assignment/a1/task4/docs/"));
            crawler.start();
        } catch (Exception e) {
            System.out.println("Error in main");
            e.printStackTrace();
        }


    }
}
