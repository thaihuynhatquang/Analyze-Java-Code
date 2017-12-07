/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 * @author phamv
 */
public class ExportImage {
    public static BufferedImage getScreenShot(Component component){
        BufferedImage image = new BufferedImage(component.getWidth(),component.getHeight(),BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        
        return image ;
    }
}
