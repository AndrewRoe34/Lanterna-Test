package org.example;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class LanternaTableExample {

    public static void main(String[] args) {
        try {
            // Create a terminal and screen
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();

            // Create a window and panel to hold the components
            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            BasicWindow window = new BasicWindow("Linear Algebra");
            Panel panel1 = new Panel(new GridLayout(3));
            BasicWindow window1 = new BasicWindow("Chemistry");
            Panel panel2 = new Panel(new GridLayout(3));

            // Create a table with column titles
            Table<String> table = new Table<>("Name", "Hours", "Due");
            table.getTableModel().addRow("Study Math", "7.5", "01-06-2024");
            table.getTableModel().addRow("Read Ch5", "2.0", "02-06-2024");

            Table<String> table2 = new Table<>("Name", "Hours", "Due");
            table2.getTableModel().addRow("Study Chemistry", "4.5", "01-06-2024");
            table2.getTableModel().addRow("Read Ch8", "1.0", "02-06-2024");

            // Add table to the panel
            panel1.addComponent(table);

            panel2.addComponent(table2);

            // Add panel to the window
            window.setComponent(panel1);

            // Add the window to the text GUI and wait for a user to close it
            textGUI.addWindowAndWait(window);

            window1.setComponent(panel2);
            textGUI.addWindowAndWait(window1);

            // Stop the screen
            screen.stopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
