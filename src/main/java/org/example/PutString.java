package org.example;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class PutString {

    public static void main(String[] args) throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();

        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        TextGraphics textGraphics = screen.newTextGraphics();

        textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
        textGraphics.fill(' ');
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);


        textGraphics.putString(10, 10, "HELLO WORLD!!", SGR.BOLD);

        screen.refresh();

    }

}
