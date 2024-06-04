package org.example;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.FileDialog;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.File;
import java.io.IOException;

public class CustomFileDialogue {

    public static void main(String[] args) throws IOException {
//        Terminal terminal = new DefaultTerminalFactory().createTerminal();
//        Screen screen = new TerminalScreen(terminal);
//        screen.startScreen();
//        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        Window window = new BasicWindow("File Dialog");

        Panel panel = new Panel(new GridLayout(2));

        Label label = new Label("Enter directory:");
        TextBox textBox = new TextBox(new TerminalSize(15, 1));
        textBox.setText("\\files\\");

        Button button = new Button("Test", () -> {
            String directory = textBox.getText();

            FileDialog fileDialog = new FileDialog("Select File", "Choose a file", "Open",
                    new TerminalSize(60, 20), false, new File(directory));
            String selectedFile = fileDialog.showDialog(textGUI).getName();
            System.out.println(selectedFile);
        });

        com.googlecode.lanterna.gui2.menu.MenuBar menuBar = new com.googlecode.lanterna.gui2.menu.MenuBar();
        menuBar.add(new Menu("Step 1"));

        panel.addComponent(label);
        panel.addComponent(textBox);
        panel.addComponent(button);
        panel.addComponent(menuBar);

        window.setComponent(panel);
        textGUI.addWindowAndWait(window);
    }

}
