package io.muic.ooc.task4;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ice on 1/20/17.
 */
public class Crawler {
    private static URL toDownloadUrl;
    private static File targetPath;
    private static Set<URL> toDownloadLinks = new HashSet<>();
    private static Set<URL> downloadedLinks = new HashSet<>();

    public Set<File> DownloadAllLinks(Set<URL> allLinks) {
        Set<File> downloadedLinksPerFile = new HashSet<>();
        try {
            for (URL link : allLinks) {
                File downloadedSingle = DownloadSingleLink(link);
                downloadedLinksPerFile.add(downloadedSingle);


            }
        } catch (Exception e) {
            System.out.println("Unable to call DownloadSingleLink");
            e.printStackTrace();
        }

        return downloadedLinksPerFile;
    }

    public File DownloadSingleLink(URL link) throws Exception{

        File newTarget = new File(targetPath.toString() + link.toString());
        URL newLink = link;
        try {

            if (link.toString().endsWith("/")) {
                newLink = new URL(link.toString() + "index.html");
                newTarget = new File(newTarget + "/index.html");
            }

            System.out.println("Downloading... from: " + newLink.toString() + " To: " + newTarget);

            FileUtils.copyURLToFile(newLink, newTarget);
            downloadedLinks.add(newLink);

        } catch (Exception e) {
            System.out.println("Unable to download " + newLink.toString());
        }

        return newTarget;
    }

    public void reset() {
        toDownloadLinks.clear();
    }

    public Set<URL> SelectToDownloadLinkFromFile(Set<File> downloadedLinksPerFile) {
        Set<URL> selectedToDownload = new HashSet<>();
        try {
            for (File path : downloadedLinksPerFile) {
                File fileFromUrl = new File(path.toString());
                System.out.println("Path: " + path.toString());
                Document docFromFile = Jsoup.parse(fileFromUrl, "UTF-8", toDownloadUrl.toString());

                Elements selectA = docFromFile.select("a");
//                URL selected = new URL(selectA.attr("href"));
                for (Element eachElement : selectA) {
                    String selected = eachElement.attr("href");
                    System.out.println("each selected: " + selected);
                    if (selected != null && !downloadedLinks.contains(toDownloadUrl + selected) && !selected.startsWith("http")) {
                        selectedToDownload.add(new URL(toDownloadUrl + selected));
                    }
                }


            }
        } catch (IOException e) {
            System.out.println("Unable to select download links from file");
            e.printStackTrace();
        }

        return selectedToDownload;

    }

    public void printSetURL(Set<URL> set, String customText) {
        System.out.println(customText);
        for (URL url : set) {
            System.out.println("    " + url);
        }
    }

    public void printSetFile(Set<File> set, String customText) {
        System.out.println(customText);
        for (File url : set) {
            System.out.println("    " + url);
        }
    }

    public void start() throws Exception {
//        toDownloadLinks.add(new URL(toDownloadUrl + "index.html"));
        toDownloadLinks.add(toDownloadUrl);
        while (!toDownloadLinks.isEmpty()) {
            printSetURL(toDownloadLinks, "All toDownloadLinks: ");


            Set<File> downloadedLinksPerFile = DownloadAllLinks(toDownloadLinks);
            printSetFile(downloadedLinksPerFile, "downloadedLinksPerFile ");


            Set<URL> selectedTodownload = SelectToDownloadLinkFromFile(downloadedLinksPerFile);
            printSetURL(selectedTodownload, "After Selected");


            reset();
            toDownloadLinks.addAll(selectedTodownload);
        }
    }

    public static URL getToDownloadUrl() {
        return toDownloadUrl;
    }

    public static void setToDownloadUrl(URL toDownloadUrl) {
        Crawler.toDownloadUrl = toDownloadUrl;
    }

    public static File getTargetPath() {
        return targetPath;
    }

    public static void setTargetPath(File targetPath) {
        Crawler.targetPath = targetPath;
    }
}
