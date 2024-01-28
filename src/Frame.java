import javax.swing.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;

public class Frame extends JFrame implements ActionListener {

    private JTextArea textArea;

    public Frame() {
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the frame on screen

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem newMenuItem = new JMenuItem("New", new ImageIcon("icons/new.png")); // Add icons
        newMenuItem.addActionListener(this);
        fileMenu.add(newMenuItem);

        JMenuItem openMenuItem = new JMenuItem("Open", new ImageIcon("icons/open.png"));
        openMenuItem.addActionListener(this);
        fileMenu.add(openMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save", new ImageIcon("icons/save.png"));
        saveMenuItem.addActionListener(this);
        fileMenu.add(saveMenuItem);

        JMenuItem printMenuItem = new JMenuItem("Print", new ImageIcon("icons/print.png"));
        printMenuItem.addActionListener(this);
        fileMenu.add(printMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit", new ImageIcon("icons/exit.png"));
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        JMenuItem cutMenuItem = new JMenuItem("Cut", new ImageIcon("icons/cut.png"));
        cutMenuItem.addActionListener(this);
        editMenu.add(cutMenuItem);

        JMenuItem copyMenuItem = new JMenuItem("Copy", new ImageIcon("icons/copy.png"));
        copyMenuItem.addActionListener(this);
        editMenu.add(copyMenuItem);

        JMenuItem pasteMenuItem = new JMenuItem("Paste", new ImageIcon("icons/paste.png"));
        pasteMenuItem.addActionListener(this);
        editMenu.add(pasteMenuItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                textArea.setText("");
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Print":
                printFile();
                break;
            case "Exit":
                exitApplication();
                break;
            case "Cut":
                textArea.cut();
                break;
            case "Copy":
                textArea.copy();
                break;
            case "Paste":
                textArea.paste();
                break;
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder text = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    text.append(line).append("\n");
                }
                textArea.setText(text.toString());
            } catch (IOException ex) {
                showErrorDialog("Error opening file: " + ex.getMessage());
            }
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
            } catch (IOException ex) {
                showErrorDialog("Error saving file: " + ex.getMessage());
            }
        }
    }

    private void printFile() {
        try {
            textArea.print();
        } catch (PrinterException ex) {
            showErrorDialog("Error printing: " + ex.getMessage());
        }
    }

    private void exitApplication() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Frame frame = new Frame();
            frame.setVisible(true);
        });
    }
}