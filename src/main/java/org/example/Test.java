package org.example;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        landingPage(screen);
        displayHelloWorld(screen);

        screen.stopScreen(); // Properly stop the screen
    }

    public static void landingPage(Screen screen) throws IOException {
        while (true) {
            // Check if the terminal has been resized
            if (screen.doResizeIfNecessary() != null) {
                // The terminal has been resized, clear the screen and redraw your interface
                screen.clear();
                TextGraphics textGraphics = screen.newTextGraphics();
                textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
                textGraphics.fill(' ');
                textGraphics.putString(50, 20, " ________  ________  ___  ___       _______           ________  ___       ________  ________   ________   _______   ________     ", SGR.BLINK, SGR.BOLD);
                textGraphics.putString(50, 21, "|\\   __  \\|\\   ____\\|\\  \\|\\  \\     |\\  ___ \\         |\\   __  \\|\\  \\     |\\   __  \\|\\   ___  \\|\\   ___  \\|\\  ___ \\ |\\   __  \\", SGR.BLINK, SGR.BOLD);
                textGraphics.putString(50, 22, "\\ \\  \\|\\  \\ \\  \\___|\\ \\  \\ \\  \\    \\ \\   __/|        \\ \\  \\|\\  \\ \\  \\    \\ \\  \\|\\  \\ \\  \\\\ \\  \\ \\  \\\\ \\  \\ \\   __/|\\ \\  \\|\\  \\   ", SGR.BLINK, SGR.BOLD);
                textGraphics.putString(50, 23, " \\ \\   __  \\ \\  \\  __\\ \\  \\ \\  \\    \\ \\  \\_|/__       \\ \\   ____\\ \\  \\    \\ \\   __  \\ \\  \\\\ \\  \\ \\  \\\\ \\  \\ \\  \\_|/_\\ \\   _  _\\ ", SGR.BLINK, SGR.BOLD);
                textGraphics.putString(50, 24, "  \\ \\  \\ \\  \\ \\  \\|\\  \\ \\  \\ \\  \\____\\ \\  \\_|\\ \\       \\ \\  \\___|\\ \\  \\____\\ \\  \\ \\  \\ \\  \\\\ \\  \\ \\  \\\\ \\  \\ \\  \\_|\\ \\ \\  \\\\  \\| ", SGR.BLINK, SGR.BOLD);
                textGraphics.putString(50, 25, "   \\ \\__\\ \\__\\ \\_______\\ \\__\\ \\_______\\ \\_______\\       \\ \\__\\    \\ \\_______\\ \\__\\ \\__\\ \\__\\\\ \\__\\ \\__\\\\ \\__\\ \\_______\\ \\__\\\\ _\\ ", SGR.BLINK, SGR.BOLD);
                textGraphics.putString(50, 26, "    \\|__|\\|__|\\|_______|\\|__|\\|_______|\\|_______|        \\|__|     \\|_______|\\|__|\\|__|\\|__| \\|__|\\|__| \\|__|\\|_______|\\|__|\\|__|", SGR.BLINK, SGR.BOLD);
                textGraphics.putString(95, 60, "Agile Planner v0.5.1 [pre-release]", SGR.BOLD);
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

    public static void displayHelloWorld(Screen screen) throws IOException {
        // Update screen directly without clearing
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.fill(' ');

        textGraphics.putString(10, 10, "Hello World", SGR.BOLD);
        screen.refresh();

        // Wait for another key press before exiting
        screen.readInput();
    }
}
