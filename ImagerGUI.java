import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class ImagerGUI extends JFrame implements ActionListener {

    JPanel topPanel, centerPanel, buttonPanel;
    JTextField urlField, prefixField;
    JButton fetchButton, downloadButton, resetButton, exitButton;

    public ImagerGUI() {

        urlField = new JTextField();
        prefixField = new JTextField();

        fetchButton = new JButton("Go!");
        downloadButton = new JButton("Download");
        resetButton = new JButton("Reset");
        exitButton = new JButton("Exit");

        topPanel = new JPanel();
        centerPanel = new JPanel();
        buttonPanel = new JPanel();

        initLayout();
        registerEventListeners();

        // initially download button needs to be disabled.
        downloadButton.setEnabled(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400,400);
        this.setVisible(true);
    }
    public void initLayout() {
        this.setLayout(new BorderLayout());

        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JLabel("URL: "));
        topPanel.add(urlField);
        topPanel.add(fetchButton);

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
        }
        else if(src == downloadButton) {
            // TODO: link to the download function
        }
        else if(src == resetButton) {
            urlField.setText("");
        }
        else if(src == exitButton) {
            // TODO: add a confirmation dialog here
        }
    }
}
