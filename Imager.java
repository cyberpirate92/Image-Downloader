import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Imager {
    public static void downloadImage(String image_url, String filename_to_save) throws IOException {
        String extension = image_url.substring(image_url.lastIndexOf("."), image_url.length());
        if(extension != null) {
            filename_to_save += extension;
        }
        InputStream inputStream = new BufferedInputStream(new URL(image_url).openStream());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n = 0;
        while((n = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, n);
        }
        inputStream.close();
        outputStream.close();
        // writing the downloaded image stream to a file
        FileOutputStream fileWriter = new FileOutputStream(filename_to_save);
        fileWriter.write(outputStream.toByteArray());
        fileWriter.close();
    }
    public static void main(String[] args) {
        ArrayList<String> imageLinks = new ArrayList<String>();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter page URL: ");
        String url = input.nextLine();
        try{
            Document doc = Jsoup.connect(url).get();
            Elements media = doc.select("[src]");
            for(Element src : media) {
                if(src.tagName().equals("img")) {
                    imageLinks.add(src.attr("abs:src"));
                }
            }
            int count = 0;
            input = new Scanner(System.in);
            System.out.println("Number of images: " + imageLinks.size());
            for(String s : imageLinks) {
                System.out.println("["+(++count) + "]: " + s);
            }
            if(imageLinks.size() > 0) {
                System.out.print("Download All [Y/N] ?: ");
                String choice = input.nextLine();
                if(choice.equalsIgnoreCase("n")) {
                    System.out.println("Download aborted.");
                }
                else {
                    count = 0;
                    // TODO: open image streams, save em to files...
                    for(String image_url : imageLinks) {
                        Imager.downloadImage(image_url, "img"+count);
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}
