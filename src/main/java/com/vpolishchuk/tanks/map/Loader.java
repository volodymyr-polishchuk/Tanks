package com.vpolishchuk.tanks.map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Vladimir on 13/02/18.
 **/
public class Loader {
    public static BufferedImage FON_ASPHALT;
    public static BufferedImage FON_GRASS;
    public static BufferedImage FON_GROUND;
    public static BufferedImage FON_ICE;
    public static BufferedImage FON_LIGHT_GRASS;

    public Loader() throws IOException {
        FON_ASPHALT = ImageIO.read(ClassLoader.class.getResourceAsStream("/map/resource/fon_asphalt.png"));
        FON_GRASS = ImageIO.read(ClassLoader.class.getResourceAsStream("/map/resource/fon_grass.png"));
        FON_GROUND = ImageIO.read(ClassLoader.class.getResourceAsStream("/map/resource/fon_ground.png"));
        FON_ICE = ImageIO.read(ClassLoader.class.getResourceAsStream("/map/resource/fon_ice.png"));
        FON_LIGHT_GRASS = ImageIO.read(ClassLoader.class.getResourceAsStream("/map/resource/fon_light_grass.png"));
    }
}
