package com.vpolishchuk.tanks.map;

import java.io.IOException;

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

