import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Imager {
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
            }
            else {
                // TODO: open image streams, save em to files...
            }
        }
        catch(Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}
