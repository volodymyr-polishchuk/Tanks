package map;

import javafx.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Vladimir on 13/02/18.
 **/
public class MapEditor {
    public static void main(String[] args) throws IOException {
        Loader loader = new Loader();
        MFrame frame = new MFrame();
        frame.setVisible(true);
    }
}

