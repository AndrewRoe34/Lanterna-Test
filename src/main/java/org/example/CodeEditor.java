package org.example;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class CodeEditor {
    public static void main(String[] args) {
        try {
            // Setup terminal and screen layers
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            // Set the background color to white
            screen.newTextGraphics().setBackgroundColor(TextColor.ANSI.WHITE);
            screen.clear();
            screen.refresh();

            // Create window and panel
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            final Window window = new BasicWindow("Script Editor");
            Panel panel = new Panel();
            panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

            // Create a multi-line text box
            TextBox textBox = new TextBox(new TerminalSize(40, 20), TextBox.Style.MULTI_LINE);
            panel.addComponent(textBox);

            // Add a button to close the window
            Button closeButton = new Button("Close", window::close);
            panel.addComponent(closeButton);

            // Add panel to window and start GUI
            window.setComponent(panel);
            textGUI.addWindowAndWait(window);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
