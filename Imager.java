import java.net.*;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Imager {

    private ArrayList<String> imageLinks;
    private String pageURL;
    private boolean fetchComplete;

    public Imager(String pageURL) {
        imageLinks = new ArrayList<String>();
        this.pageURL = pageURL;
        this.fetchComplete = false;
    }
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
        System.out.println(filename_to_save);
    }
    public void fetchImageLinks() {
        try{
            Document doc = Jsoup.connect(this.pageURL).get();
            Elements media = doc.select("[src]");
            for(Element src : media) {
                if(src.tagName().equals("img")) {
                    imageLinks.add(src.attr("abs:src"));
                }
            }
        }
        catch(Exception e) {
            System.out.println("Exception : " + e);
        }
        this.fetchComplete = true;
    }
    public int getCount() {
        if(fetchComplete) return imageLinks.size();
        else return 0;
    }
    public boolean isFetchComplete() {
        return fetchComplete;
    }
    public int initiateDownloads() throws IOException {
        return initiateDownloads("img");
    }
    public int initiateDownloads(String prefix) throws IOException {
        int counter = 0;
        if(fetchComplete && imageLinks.size() > 0) {
            for(String image_url : imageLinks) {
                Imager.downloadImage(image_url, prefix+counter++);
            }
        }
        return counter;
    }
    public String getLink(int n) {
        if(n >= 0 && n < imageLinks.size() )
            return imageLinks.get(n);
        else
            return null;
    }
    public ImageIcon getImageIcon(int n) throws IOException {
        if(n >= 0 && n < imageLinks.size() ) {
            BufferedImage image = ImageIO.read(new URL(imageLinks.get(n)));
            return new ImageIcon(image);
        }
        else
            return null;
    }
}
