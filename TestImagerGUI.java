import javax.swing.SwingUtilities;
class TestImagerGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ImagerGUI();
            }
        });
    }
}
