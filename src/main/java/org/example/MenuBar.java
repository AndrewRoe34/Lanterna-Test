package org.example;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MenuBar {
    public static void main(String[] args) {
        try {
            // Step 1: Set up the Lanterna environment
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            TextGraphics textGraphics = screen.newTextGraphics();

            // Create a window and root panel
            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            final Window window = new BasicWindow("System Options");

            // Step 2: Create the menu bar
            com.googlecode.lanterna.gui2.menu.MenuBar menuBar = new com.googlecode.lanterna.gui2.menu.MenuBar();

            // Create menus
            Menu fileMenu = new Menu("File");
            Menu scriptMenu = new Menu("Scripting");
            Menu configMenu = new Menu("Config");
            Menu helpMenu = new Menu("Help");

            // Add items to File menu
            fileMenu.add(new MenuItem("New", new Runnable() {
                @Override
                public void run() {
                    System.out.println("New File");
                    setupPrompt(textGUI);
                }
            }));
            fileMenu.add(new MenuItem("Open", new Runnable() {
                @Override
                public void run() {
                    locateFileBuilder(textGUI, ".jbin");
                }
            }));
            fileMenu.add(new MenuItem("Exit", new Runnable() {
                @Override
                public void run() {
                    System.exit(0);
                }
            }));
            scriptMenu.add(new MenuItem("Load Script", new Runnable() {
                @Override
                public void run() {
                    locateFileBuilder(textGUI, ".smpl");
                }
            }));
            scriptMenu.add(new MenuItem("Editor", new Runnable() {
                @Override
                public void run() {
                    System.out.println("Entering script editor");
                    setupEditor(screen);
                }
            }));

            // Add items to Help menu
            helpMenu.add(new MenuItem("About", new Runnable() {
                @Override
                public void run() {
                    System.out.println("About");
                    // Put the string "Hello World" at position (10, 10)
                    textGraphics.putString(10, 10, "Hello World");
                    try { // todo text is showing up (very briefly), we need it to stay there
                        screen.refresh();
                        screen.readInput();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // Create a new window for the About screen
                    final Window aboutWindow = new BasicWindow("About");

                    // Create a panel and add some components to it
                    Panel aboutPanel = new Panel();
                    aboutPanel.addComponent(new Label("This is the About screen"));

                    // Set the panel as the component of the window
                    aboutWindow.setComponent(aboutPanel);

                    aboutWindow.addWindowListener(new WindowListener() {
                        @Override
                        public void onResized(Window window, TerminalSize terminalSize, TerminalSize terminalSize1) {

                        }

                        @Override
                        public void onMoved(Window window, TerminalPosition terminalPosition, TerminalPosition terminalPosition1) {

                        }

                        @Override
                        public void onInput(Window window, KeyStroke keyStroke, AtomicBoolean atomicBoolean) {
                            aboutWindow.close();
                        }

                        @Override
                        public void onUnhandledInput(Window window, KeyStroke keyStroke, AtomicBoolean atomicBoolean) {

                        }
                    });

                    // Add the window to the text GUI and wait for it to close
                    textGUI.addWindowAndWait(aboutWindow);
                }
            }));

            helpMenu.add(new MenuItem("Changelog", new Runnable() {
                @Override
                public void run() {
                    System.out.println("Display changelog");
                }
            }));

            // Add menus to menu bar
            menuBar.add(fileMenu);
            menuBar.add(scriptMenu);
            menuBar.add(configMenu);
            menuBar.add(helpMenu);

            // Step 3: Handle menu actions
            Panel panel = new Panel();
            panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
            panel.addComponent(menuBar);

            window.setComponent(menuBar);
            textGUI.addWindowAndWait(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void locateFileBuilder(WindowBasedTextGUI textGUI, String endType) {
        while (true) {
            File file = new FileDialogBuilder().build().showDialog(textGUI);
            if (file != null) {
                if (file.getName().endsWith(endType)) {
                    MessageDialog.showMessageDialog(textGUI, "Open", "Selected file:\n" + file, MessageDialogButton.OK);
                    break;
                } else { // todo need the cancel option to result in a break from this loop
                    MessageDialog.showMessageDialog(textGUI, "Invalid file", "\nFile must end with " + endType, MessageDialogButton.Retry);
                }
            }
        }

    }

    public static void setupEditor(Screen screen) {
        // Create window and panel
        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow("Script Editor");
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Create a multi-line text box
        TextBox textBox = new TextBox(new TerminalSize(50, 20), TextBox.Style.MULTI_LINE);
        textBox.setPosition(new TerminalPosition(10, 10)); // todo need to have textbox moved to this coordinate here
        panel.addComponent(textBox);

        // Add a button to close the window
        Button closeButton = new Button("Close", window::close);
        panel.addComponent(closeButton);

        // Add panel to window and start GUI
        window.setComponent(panel);
        textGUI.addWindowAndWait(window);

        // todo need to have another window as well (to the right, with two buttons on the top
    }

    public static void setupPrompt(WindowBasedTextGUI textGUI) {
        // Create panel to hold components
        final Window promptWindow = new BasicWindow("Name of file");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Name"));
        panel.addComponent(new TextBox());

        panel.addComponent(new EmptySpace(new TerminalSize(0,0))); // Empty space underneath labels
        panel.addComponent(new Button("Submit", new Runnable() {
            @Override
            public void run() {
                promptWindow.close();
            }
        })); // todo need a runnable here to close the panel

        promptWindow.setComponent(panel);
        textGUI.addWindowAndWait(promptWindow);
    }

//    public static com.googlecode.lanterna.gui2.menu.MenuBar createMenuBar(WindowBasedTextGUI textGUI) {
//
//    }
}
