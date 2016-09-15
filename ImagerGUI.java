import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class ImagerGUI extends JFrame implements ActionListener {

    JPanel topPanel, centerPanel, buttonPanel;
    //JScrollPane centerScrollPane;
    JTextField urlField, prefixField;
    JButton fetchButton, downloadButton, resetButton, exitButton;
    GridLayout imageGrid;

    public ImagerGUI() {

        imageGrid = new GridLayout(1, 1, 1, 1);

        urlField = new JTextField(20);
        prefixField = new JTextField(10);

        fetchButton = new JButton("Go!");
        downloadButton = new JButton("Download");
        resetButton = new JButton("Reset");
        exitButton = new JButton("Exit");

        topPanel = new JPanel();
        centerPanel = new JPanel();
        buttonPanel = new JPanel();

        //centerScrollPane = new JScrollPane();

        initLayout();
        registerEventListeners();

        // initially download button needs to be disabled.
        downloadButton.setEnabled(false);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400,400);
        this.setVisible(true);
    }
    public void initLayout() {
        this.setLayout(new BorderLayout());

        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        //this.getContentPane().add(centerScrollPane, BorderLayout.CENTER);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        topPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        topPanel.add(new JLabel("URL: "));
        topPanel.add(urlField);
        topPanel.add(fetchButton);

        //centerScrollPane.add(centerPanel);
        //centerScrollPane.setPreferredSize(new Dimension(450, 110));
        centerPanel.setLayout(imageGrid);

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(downloadButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);
    }
    public void registerEventListeners() {
        fetchButton.addActionListener(this);
        downloadButton.addActionListener(this);
        resetButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton)e.getSource();
        if(src == fetchButton) {
            // TODO: fetch the image links from the provided base URL
            String baseURL = urlField.getText();
            Imager imager = new Imager(baseURL);
            imager.fetchImageLinks();
            int imageCount = imager.getCount();
            imageGrid = new GridLayout(imageCount/3 + 1, 3, 5, 5); // creating an image grid to display the images
            for(int i=0; i<imager.getCount(); i++) {
                try {
                    centerPanel.add(new JLabel(imager.getImageIcon(i)));
                    this.pack();
                }
                catch(Exception ex) {
                    System.out.println("Exception caught: " + ex);
                }
            }
        }
        else if(src == downloadButton) {
            // TODO: link to the download function
        }
        else if(src == resetButton) {
            urlField.setText("");
            imageGrid = new GridLayout(1, 1, 1, 1);
        }
        else if(src == exitButton) {
            // TODO: add a confirmation dialog here
        }
    }
}
