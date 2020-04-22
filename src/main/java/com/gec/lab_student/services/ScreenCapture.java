package com.gec.lab_student.services;

import com.gec.lab_student.utilities.ImageUtility;
import com.gec.lab_student.utilities.ZipUtility;
import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ScreenCapture {
    @Autowired
    ActivemqProducerService activemqProducerService=new ActivemqProducerService();

    ArrayList<Object> imageArray = new ArrayList<Object>();

    int i = 0;

    private static Robot r;

    public BufferedImage captureScreen() {
        BufferedImage Image = null;
        try {
            Thread.sleep(120);
            Robot r = new Robot();

            // It saves screenshot to desired path
            String path = "/home/aiswarya/Downloads/screenshot.jpg";

            // Used to get ScreenSize and capture image
            Rectangle capture =
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            Image = r.createScreenCapture(capture);
            ImageIO.write(Image, "jpg", new File(path));
            System.out.println("Screenshot saved");
            this.imageArray.add(Image);

        } catch (AWTException | IOException | InterruptedException ex) {
            System.out.println(ex);
        }
        return Image;
    }

public void screen() throws IOException {
    System.out.println(imageArray.size());
        try {
            ArrayList<Object> SendObjects = new ArrayList<Object>();
            SendObjects.add(CaptureScreenByteArray());
            SendObjects.add(getScreenRect());

            synchronized (imageArray) {
                    SendObjects = imageArray;
                    imageArray = new ArrayList<Object>();
            }
            i++;
            System.out.println(i);
            System.out.println(SendObjects);
            activemqProducerService.send(ZipUtility.objecToByteArray(SendObjects));
            imageArray.clear();
            System.out.println("sent " + i + "messages");
        } catch (Exception e) {
            e.printStackTrace();
        }

}

    public byte[] CaptureScreenByteArray() {
        return ImageUtility.toByteArray(captureScreen());
    }

    public Rectangle getScreenRect() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Rectangle screenRect = new Rectangle(tk.getScreenSize());
        return screenRect;
    }



}