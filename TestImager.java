import java.util.Scanner;

public class TestImager {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("URL: ");
            String pageUrl = input.nextLine();
            Imager imager = new Imager(pageUrl);
            imager.fetchImageLinks();
            imager.initiateDownloads();
            input.close();
        }
        catch (Exception e) {
            System.out.println("Exception raised: "+e);
        }
    }
}
