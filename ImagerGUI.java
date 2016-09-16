import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class ImagerGUI extends JFrame implements ActionListener {

    Imager imager;
    JPanel topPanel, centerPanel, buttonPanel;
    JScrollPane centerScrollPane;
    JTextField urlField, prefixField;
    JButton fetchButton, downloadButton, resetButton, exitButton;
    FlowLayout imageGrid;
    JLabel statusLabel;

    public ImagerGUI() {

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
        this.setSize(400,400);
        this.setVisible(true);
    }
    public void initLayout() {
        this.setLayout(new BorderLayout());

        topPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        topPanel.add(new JLabel("URL: "));
        topPanel.add(urlField);
        topPanel.add(fetchButton);

        centerPanel.setLayout(imageGrid);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //centerScrollPane.add(centerPanel);

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(downloadButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(statusLabel);

        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        //this.getContentPane().add(centerScrollPane, BorderLayout.CENTER);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
    public void registerEventListeners() {
        fetchButton.addActionListener(this);
        downloadButton.addActionListener(this);
        resetButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean flag = false;
        JButton src = (JButton)e.getSource();
        if(src == fetchButton) {
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
                    flag = true;
                }
                catch(Exception ex) {
                    System.out.println("Exception caught: " + ex);
                    ex.printStackTrace();
                }
                statusLabel.setText("");
            }
            if(flag) {
                downloadButton.setEnabled(flag);
            }
        }
        else if(src == downloadButton) {
            try {
                statusLabel.setText("Downloading ... Please wait!");
                int downloadCount = imager.initiateDownloads();
                statusLabel.setText("Download complete, " + downloadCount + " images downloaded.");
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
            // TODO: add a confirmation dialog here
            int option = JOptionPane.showConfirmDialog(null, "Are you sure ?", "Quit", JOptionPane.YES_NO_OPTION);
            switch(option) {
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.YES_OPTION:
                    this.dispose();
                    System.exit(0);
                    break;
            }
        }
    }
}
