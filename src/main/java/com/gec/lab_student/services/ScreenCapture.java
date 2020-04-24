package com.gec.lab_student.services;

import com.gec.lab_student.utilities.ImageUtility;
import com.gec.lab_student.utilities.ZipUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ScreenCapture {

    @Autowired
    ActivemqProducerService activemqProducerService;

    public BufferedImage captureScreen() {
        BufferedImage image = null;
        try {
            Robot r = new Robot();

            // Used to get ScreenSize and capture image
            Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            image = r.createScreenCapture(capture);

        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        return image;
    }

public void publishScreen() throws IOException {
        try {
            ArrayList<Object> sendObjects = new ArrayList<Object>();
            sendObjects.add(captureScreenByteArray());
            sendObjects.add(getScreenRect());

            activemqProducerService.send(ZipUtility.objecToByteArray(sendObjects));
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    public byte[] captureScreenByteArray() {
        return ImageUtility.toByteArray(captureScreen(), (float) 0.1);
    }

    public Rectangle getScreenRect() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Rectangle screenRect = new Rectangle(tk.getScreenSize());
        return screenRect;
    }
}