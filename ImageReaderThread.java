import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class ImageReaderThread extends Thread {
    private ImageReaderCallback callback;
    public ImageReaderThread(ReaderCallback x) {
        this.callback = x;
    }
    public void run() {

    }
    public void done() {
        x.finished();
    }
}
