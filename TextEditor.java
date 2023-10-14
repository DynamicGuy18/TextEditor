import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextEditor extends JFrame {
    private JTextArea textArea;     // This is where you can edit and view text.
    private JFileChooser fileChooser; // It helps to open and save files.

    public TextEditor() {
        setTitle("My Text Editor");  // Setting the title of the application.
        setSize(800, 600);  // Setting the size of the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close the application when the window is closed.

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setBackground(Color.BLACK); // Set the background color of the text area to black.
        textArea.setForeground(Color.GREEN);  // Set the text color to green.
        JScrollPane scrollPane = new JScrollPane(textArea); // Add a scroll pane to the text area for long text.
        getContentPane().add(scrollPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        fileChooser = new JFileChooser();

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();  // This function opens a file and loads its content into the text area.
            }
        });

        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();  // This function saves the content of the text area to a file.
            }
        });

        saveAsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAsFile();  // This function saves the content of the text area with a different name.
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the application when this menu item is selected.
            }
        });

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem toUpperCaseItem = new JMenuItem("To Uppercase");
        JMenuItem toLowerCaseItem = new JMenuItem("To Lowercase");

        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(cutItem);
        editMenu.add(toUpperCaseItem);
        editMenu.add(toLowerCaseItem);
        menuBar.add(editMenu);

        copyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.copy();  // Copy the selected text in the text area.
            }
        });

        pasteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.paste();  // Paste text from the clipboard into the text area.
            }
        });

        cutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.cut();   // Cut the selected text and copy it to the clipboard.
            }
        });

        toUpperCaseItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedText = textArea.getSelectedText();
                if (selectedText != null) {
                    textArea.replaceSelection(selectedText.toUpperCase()); // Convert selected text to uppercase.
                }
            }
        });

        toLowerCaseItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedText = textArea.getSelectedText();
                if (selectedText != null) {
                    textArea.replaceSelection(selectedText.toLowerCase()); // Convert selected text to lowercase.
                }
            }
        });
    }

    private void openFile() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder fileContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
                reader.close();
                textArea.setText(fileContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(textArea.getText());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveAsFile() {
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            saveFile();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditor editor = new TextEditor();
            editor.setVisible(true);  // Make the window visible.
        });
    }
}
