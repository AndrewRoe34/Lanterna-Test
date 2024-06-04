package org.example;

import java.awt.*;
import java.net.URI;

public class URL {

    public static void main(String[] args) {
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
    }
}
