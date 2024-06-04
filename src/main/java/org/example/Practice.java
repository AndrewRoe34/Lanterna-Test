package org.example;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class Practice {

    static String agileBoy = "\n\n\n\n\n\n                          John 15:05" +
            "\n             'I am the vine, you are the branches." +
            "\n              He who abides in me, and I in him, he it" +
            "\n              is that bears much fruit, for apart from" +
            "\n              me you can do nothing.'";

    static String taskTutorial = "To create a task, utilize the following:" +
            "\n\nt1: task(\"HW3\", 4, 2)" +
            "\n\nThis creates a task that has 4 hours and is due in 2 days." +
            "\n\nTasks are the fundamental component to creating a schedule and can" +
            "\nbe broken up over a series of days via subtasks. Tasks can be added" +
            "\nto cards by script via the 'add' method:" +
            "\n\nc1: card(\"Math\", RED)" +
            "\nc1.add(t1) <-- adds task to card" +
            "\n\nAdding a task to a card gives the color of the card to the task" +
            "\n(unless said task already has a color). This provides a more" +
            "\ninsightful interface both locally and with Google Calendar";

    static String cardTutorial = "To create a card, utilize the following:" +
            "\n\nc1: card(\"Math\", BLUE)" +
            "\n\nThis creates a card that has label \"Math\" and color set to blue." +
            "\n\nCards are the frames that hold tasks by type and allow enhanced" +
            "\nvisualization due to the inherent coloring option available. Cards" +
            "\ncan add tasks by script via the 'add' method:" +
            "\n\nc1: card(\"Math\", RED)" +
            "\nc1.add(t1) <-- adds task to card" +
            "\n\nThe card interface was inspired by Trello and attempts to be" +
            "\na more complete version due to how it combines the elements of" +
            "\nboth frame and label.";

    static String includeTutorial = "Include flags tell Simple Script to perform various actions" +
            "\neither at the beginning or end of execution. The following" +
            "\nincludes all available flag types:" +
            "\n\n__CURR_CONFIG__ (Uses current config settings)" +
            "\n__DEF_CONFIG__  (Uses default config settings)" +
            "\n__HTML__        (Generates an HTML page for session)" +
            "\n__LOG__         (Outputs script and system log to console)" +
            "\n\nWhile only one 'config' flag is required, the rest simply provide" +
            "\nhelpful tools to improve your overall scheduling experience.";

    public static void main(String[] args) throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        setupLandingPage(screen);
//        screen.clear();
//        screen.refresh(); // Refresh the screen after clearing

        displayMenuBar(screen);
        screen.stopScreen(); // Properly stop the screen

    }

    public static void setupLandingPage(Screen screen) throws IOException {
        while (true) {
            // Check if the terminal has been resized
            if (screen.doResizeIfNecessary() != null) {
                // The terminal has been resized, clear the screen and redraw your interface
                screen.clear();
                TextGraphics textGraphics = screen.newTextGraphics();
                textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
                textGraphics.fill(' ');
                textGraphics.putString(screen.getTerminalSize().getColumns() / 4, screen.getTerminalSize().getRows() / 2, "AGILE PLANNER v0.5.0 [PRE-RELEASE]", SGR.BOLD);
            }

            // Refresh the screen
            screen.refresh();

            // Check for key press
            KeyStroke keyStroke = screen.pollInput();
            if (keyStroke != null) {
                // A key has been pressed, break the loop
                break;
            }
        }
    }

    public static void displayMenuBar(Screen screen) throws IOException {
        // Create a window and root panel
        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow("System Options");

        // Step 2: Create the menu bar
        com.googlecode.lanterna.gui2.menu.MenuBar menuBar = new com.googlecode.lanterna.gui2.menu.MenuBar();

        // Create menus
        Menu fileMenu = new Menu("Window");
        Menu scriptMenu = new Menu("Scheduling");
        Menu configMenu = new Menu("Config");
        Menu helpMenu = new Menu("Help");

        menuBar.add(fileMenu);
        menuBar.add(scriptMenu);
        menuBar.add(configMenu);
        menuBar.add(helpMenu);

        setupMenuBar(menuBar, textGUI, screen);

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(menuBar);

        window.setComponent(panel);
        textGUI.addWindowAndWait(window);
    }

    public static void setupMenuBar(com.googlecode.lanterna.gui2.menu.MenuBar menuBar, WindowBasedTextGUI textGUI, Screen screen) {
        menuBar.getMenu(0).add(new MenuItem("Exit", () -> System.exit(0)));
        menuBar.getMenu(1).add(new MenuItem("Editor", () -> {
            System.out.println("Entering script editor");
            setupCodeEditor(screen);
        }));
        menuBar.getMenu(1).add(new MenuItem("Tutorial", () -> setupTutorial(screen)));
        menuBar.getMenu(2).add(new MenuItem("Display", () -> setupConfigTable(textGUI)));
        menuBar.getMenu(2).add(new MenuItem("Edit", () -> {
            // todo
            Window window = new BasicWindow("Edit Config");
            // we'll use the action list dialog to allow the user to select one of the items
            textGUI.addWindowAndWait(window);
        }));
        menuBar.getMenu(3).add(new MenuItem("About", () -> {
            Window testWindow = new BasicWindow("Summary");
//                testWindow.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE, SGR.BORDERED));
            Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
            Label text = new Label("Agile Planner is a robust, dynamic\nscheduling platform that aims to" +
                    "\nsolve the perpetual problem of\nintelligent scheduling through the\nsupport of proprietary tooling such" +
                    "\nas schedule serialization and\na built-in scripting.");
            panel.addComponent(text);
            Button closeButton = new Button("Close", testWindow::close);
            panel.addComponent(closeButton);
            testWindow.setComponent(panel);
            textGUI.addWindowAndWait(testWindow);
        }));
        menuBar.getMenu(3).add(new MenuItem("Changelog", () -> {
            Window changeLogTable = new BasicWindow("ChangeLog");
            Panel table = new Panel();
            Table<String> changes = new Table<>("Type", "Description");
            changes.getTableModel().addRow("ADD", "Integrated Event with JBin");
            changes.getTableModel().addRow("FIX", "Simple Script now supports condition chaining");
            changes.getTableModel().addRow("REFACTOR", "Refactored codebase up to standards");
            changes.getTableModel().addRow("DOC", "Updated documentation for system");
            changes.getTableModel().addRow("TEST", "Tested all model classes");
            // todo need to add scrollbar
//                table.addComponent(new ScrollBar(Direction.VERTICAL));
            table.addComponent(changes);
            table.addComponent(new Button("Close", changeLogTable::close));
            changeLogTable.setComponent(table);
            textGUI.addWindowAndWait(changeLogTable);
        }));
        menuBar.getMenu(3).add(new MenuItem("GitHub", () -> {
            try {
                // Check if Desktop is supported on the current platform
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        // Create a URI object with the web page URL
                        URI uri = new URI("https://github.com/AndrewRoe34/agile-planner");
                        // Use the browse method to open the web page
                        desktop.browse(uri);
                    } else {
                        System.out.println("BROWSE action is not supported");
                    }
                } else {
                    System.out.println("Desktop is not supported on this platform");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

    }

    public static File setupFileDialog(WindowBasedTextGUI textGUI, String endType) {
        File file = new FileDialogBuilder().build().showDialog(textGUI);
        if (file != null) {
            if (file.getName().endsWith(endType)) {
                MessageDialog.showMessageDialog(textGUI, "Open", "Selected file:\n" + file, MessageDialogButton.OK);
                return file;
            } else {
                MessageDialog.showMessageDialog(textGUI, "Invalid file", "\nFile must end with " + endType, MessageDialogButton.Retry);
                return null;
            }
        }
        return null;
    }

    public static void setupCodeEditor(Screen screen) {
        // Create window and editorPanel
        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow("Simple Editor");
//        window.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE, SGR.BORDERED));
        Panel pagePanel = new Panel(new GridLayout(1));
        Panel editorPanel = new Panel();
        editorPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Create a multi-line text box
        TextBox textBox = new TextBox(new TerminalSize(70, 18), TextBox.Style.MULTI_LINE);
        editorPanel.addComponent(textBox);

        Panel editorOptions = new Panel(new LinearLayout(Direction.HORIZONTAL));

        Button runButton = new Button("Run", () -> System.out.println("Script is being executed..."));
        editorOptions.addComponent(runButton);
        Button openButton = new Button("Open", () -> {
            File file = setupFileDialog(textGUI, ".smpl");
            if (file != null) {
                textBox.setText("include: __CURR_CONFIG__, __LOG__\n" +
                        "\n" +
                        "# Outputs all binary codes\n" +
                        "func binary(bin, x)\n" +
                        "  if(x.==(0))\n" +
                        "    println(bin)\n" +
                        "    return\n" +
                        "  x.--()\n" +
                        "  binary(bin.add(\"0\"), x)\n" +
                        "  binary(bin.add(\"1\"), x)\n" +
                        "\n" +
                        "print(\"Enter number: \")\n" +
                        "x: input_int()\n" +
                        "binary(\"\", x)\n" +
                        "\n");
            }
        });
        editorOptions.addComponent(openButton);
        Button saveButton = new Button("Save", () -> System.out.println("Script is being saved..."));
        editorOptions.addComponent(saveButton);
        // Add a button to close the window
        Button closeButton = new Button("Close", window::close);
        editorOptions.addComponent(closeButton);
        editorPanel.addComponent(editorOptions);

        pagePanel.addComponent(editorPanel);

        // Add editorPanel to window and start GUI
        window.setComponent(pagePanel);
        textGUI.addWindowAndWait(window);
    }

    public static void setupTutorial(Screen screen) {
        // Create window and editorPanel
        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow("Simple Tutorial 0.5.0");
//        window.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE, SGR.BORDERED));
        Panel pagePanel = new Panel(new GridLayout(1));
        Panel editorPanel = new Panel();
        editorPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Create a multi-line text box
        TextBox textBox = new TextBox(new TerminalSize(70, 18), TextBox.Style.MULTI_LINE);
        textBox.setReadOnly(true);
        textBox.setText(agileBoy);
//        editorPanel.addComponent(textBox);

        Panel editorOptions = new Panel(new LinearLayout(Direction.HORIZONTAL));

        Button docButton = new Button("Docs", () -> {
            System.out.println("Simple Script Documentation");
            Window window1 = new BasicWindow("Topics");
            Panel frame = new Panel(new LinearLayout(Direction.VERTICAL));
            ActionListBox actionListBox = new ActionListBox(new TerminalSize(14, 10));
            actionListBox.addItem("Tasks", () -> textBox.setText(taskTutorial));
            actionListBox.addItem("Cards", () -> textBox.setText(cardTutorial));
            actionListBox.addItem("Include Flags", () -> textBox.setText(includeTutorial));
            Button closeButton = new Button("Close", window1::close);
            frame.addComponent(actionListBox);
            frame.addComponent(closeButton);
            window1.setComponent(frame);
            textGUI.addWindowAndWait(window1);
        });
        editorOptions.addComponent(docButton);
        Button closeButton = new Button("Close", window::close);
        editorOptions.addComponent(closeButton);

        editorPanel.addComponent(editorOptions);
        editorPanel.addComponent(textBox);

        pagePanel.addComponent(editorPanel);

        // Add editorPanel to window and start GUI
        window.setComponent(pagePanel);
        textGUI.addWindowAndWait(window);
    }


    public static void setupConfigTable(WindowBasedTextGUI textGUI) {
        // Create a window and panel for the table
        final Window tableWindow = new BasicWindow("Settings");
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Define the table content
        String[] headers = {"Range", "Hours", "MaxDays", "ArchiveDay", "Overflow", "FitDay", "MinHours", "OptimizeDay", "DefaultAtStart"};
        String[][] data = {
                {"         {9, 20}", "         {8,8,8,8,8,8,8}", "       14", "    5", "      True", "        True", "      0.5", "   True", "True"}
        };

        // Add headers and data to the panel
        for (int i = 0; i < headers.length; i++) {
            Panel rowPanel = new Panel();
            rowPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

            // Add header label
            rowPanel.addComponent(new Label(headers[i] + ": ").addStyle(SGR.BOLD));

            // Add data label
            rowPanel.addComponent(new Label(data[0][i]));

            panel.addComponent(rowPanel);
        }

        // Add a close button
        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
        panel.addComponent(new Button("Close", tableWindow::close));

        // Set panel to window and display
        tableWindow.setComponent(panel);
        textGUI.addWindowAndWait(tableWindow);
    }
}
