package org.example;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DirectoryDialogBuilder;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;

import java.io.File;
import java.io.IOException;

public class DirectoryDialogue {
    public static void main(String[] args) throws InterruptedException, IOException {
        try {
            // Setup terminal and screen layers
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            // Setup WindowBasedTextGUI for dialogs
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

            // Create a panel to hold components
            Panel panel = new Panel();
            panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

            // Add a button that opens the directory dialog
            panel.addComponent(new Button("Locate Folder", new Runnable() {
                @Override
                public void run() {
                    File input = new DirectoryDialogBuilder()
                            .setTitle("Select directory")
                            .setDescription("Choose a directory")
                            .setActionLabel("Select")
                            .build()
                            .showDialog(textGUI);
                    System.out.println(input);
                }
            }));

            // Create window to hold the panel
            BasicWindow window = new BasicWindow();
            window.setComponent(panel);

            // Add window to text GUI and start GUI
            textGUI.addWindowAndWait(window);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
