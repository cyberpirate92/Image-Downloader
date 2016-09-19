import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.IOException;

class ImagerGUI extends JFrame implements ActionListener, KeyListener {

    Imager imager;
    JPanel topPanel, centerPanel, bottomPanel, buttonPanel, prefixPanel, statusPanel;
    JScrollPane centerScrollPane;
    JTextField urlField, prefixField;
    JButton fetchButton, downloadButton, resetButton, exitButton;
    FlowLayout imageGrid;
    JLabel statusLabel;
    boolean downloaded, fetched;

    public ImagerGUI() {

        downloaded = fetched = false;

        imageGrid = new FlowLayout();

        urlField = new JTextField(20);
        prefixField = new JTextField(10);

        fetchButton = new JButton("Go!");
        downloadButton = new JButton("Download");
        resetButton = new JButton("Reset");
        exitButton = new JButton("Exit");

        topPanel = new JPanel();
        centerPanel = new JPanel();
        buttonPanel = new JPanel();
        prefixPanel = new JPanel();
        statusPanel = new JPanel();
        bottomPanel = new JPanel();

        statusLabel = new JLabel();
        statusLabel.setForeground(Color.BLUE);

        //centerScrollPane = new JScrollPane(centerPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        initLayout();
        registerEventListeners();

        // initially download button needs to be disabled.
        downloadButton.setEnabled(false);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500,500);
        this.setVisible(true);
    }
    public void initLayout() {
        this.setLayout(new BorderLayout());

        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(new JLabel("URL: "));
        topPanel.add(urlField);
        topPanel.add(fetchButton);

        centerPanel.setLayout(imageGrid);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //centerScrollPane.add(centerPanel);

        prefixPanel.setLayout(new FlowLayout());
        prefixPanel.add(new JLabel("Filename Prefix: "));
        prefixPanel.add(prefixField);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(downloadButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);

        statusPanel.setLayout(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        bottomPanel.setLayout(new GridLayout(3,1,0,0));
        bottomPanel.add(prefixPanel);
        bottomPanel.add(buttonPanel);
        bottomPanel.add(statusPanel);

        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        //this.getContentPane().add(centerScrollPane, BorderLayout.CENTER);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }
    public void registerEventListeners() {
        fetchButton.addActionListener(this);
        downloadButton.addActionListener(this);
        resetButton.addActionListener(this);
        exitButton.addActionListener(this);
        urlField.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyTyped = (int)e.getKeyCode();
        statusLabel.setText("Key Typed: " + keyTyped);
        if(keyTyped == 10) {
            fetchImages();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton)e.getSource();
        if(src == fetchButton) {
            fetchImages();
        }
        else if(src == downloadButton) {
            try {
                initDownload();
            }
            catch(Exception ex) {
                System.out.println("Exception caught: "+ex);
                statusLabel.setText("Error: Download failed!");
                ex.printStackTrace();
            }
        }
        else if(src == resetButton) {
            urlField.setText("");
            imageGrid = new FlowLayout();
            statusLabel.setText("");
        }
        else if(src == exitButton) {
            if(fetched && !downloaded) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure to exit without downloading ?", "Quit", JOptionPane.YES_NO_OPTION);
                switch(option) {
                    case JOptionPane.NO_OPTION:
                        break;
                    case JOptionPane.YES_OPTION:
                        this.dispose();
                        System.exit(0);
                        break;
                }
            }
            else if(!fetched) {
                this.dispose();
                System.exit(0);
            }
        }
    }

    public void fetchImages() {
        String baseURL = urlField.getText();
        imager = new Imager(baseURL);
        imager.fetchImageLinks();
        int imageCount = imager.getCount();
        statusLabel.setText("Fecthing " + imageCount + " Images");
        for(int i=0; i<imager.getCount(); i++) {
            try {
                JLabel imageLabel = new JLabel(imager.getImageIcon(i));
                imageLabel.setPreferredSize(new Dimension(100,100));
                imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                imageLabel.setBackground(Color.WHITE);
                centerPanel.add(imageLabel);
                centerPanel.validate();
                fetched = true;
            }
            catch(Exception ex) {
                System.out.println("Exception caught: " + ex);
                ex.printStackTrace();
            }
            statusLabel.setText("");
        }
        if(fetched) {
            downloadButton.setEnabled(fetched);
        }
    }

    public void initDownload() throws IOException {
        int downloadCount = 0;
        String filenamePrefix = prefixField.getText().trim();
        statusLabel.setText("Downloading ... Please wait!");
        if(filenamePrefix == null || filenamePrefix.equals("")) {
            int option = JOptionPane.showConfirmDialog(null, "No filename prefix have been set\n Are you sure? ", "Confirm", JOptionPane.YES_NO_OPTION);
            switch(option) {
                case JOptionPane.YES_OPTION:
                    downloadCount = imager.initiateDownloads();
                    break;
                case JOptionPane.NO_OPTION:
                    statusLabel.setText("Download aborted.");
                    break;
            }
        }
        else if(filenamePrefix.length() > 0) {
            downloadCount = imager.initiateDownloads(filenamePrefix);
            if(downloadCount > 0) {
                statusLabel.setText("Download complete, " + downloadCount + " images downloaded.");
                downloaded = true;
            }
        }
    }
}
