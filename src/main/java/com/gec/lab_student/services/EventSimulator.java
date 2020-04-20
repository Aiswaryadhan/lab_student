package com.gec.lab_student.services;


import com.gec.lab_student.utilities.ImageUtility;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

/**
 * robot.java
 * @author benbac
 */

@Service
public class EventSimulator {

    private Robot rt;

    private Toolkit tk = null;
    private Rectangle screenRect;

    public EventSimulator() {
        tk = Toolkit.getDefaultToolkit();
        screenRect = new Rectangle(tk.getScreenSize());
        try {
            rt = new Robot();
        }
        catch (AWTException awte) {
            awte.getStackTrace();
        }
    }

    public BufferedImage captureScreen() {
        screenRect = new Rectangle(tk.getScreenSize());
        return rt.createScreenCapture(screenRect);
    }

    public byte[] CaptureScreenByteArray() {
        return ImageUtility.toByteArray(captureScreen());
    }

    public Rectangle getScreenRect() {
        return screenRect;
    }

    public void updateData(Object object) throws Exception {

        ArrayList Objects = (ArrayList) object;
        for (int i=0; i<Objects.size(); i++) {
            Object obj = Objects.get(i);
            System.out.println(obj);
            if (obj instanceof MouseEvent)
                applyMouseEvent((MouseEvent)obj);
            else if (obj instanceof Map)
                applyKeyEvent((Map<Integer, Boolean>)obj);
        }
    }

    public void applyMouseEvent(MouseEvent evt) {
        rt.mouseMove(evt.getX(), evt.getY());
        int buttonMask = 0;
        int buttons = evt.getButton();
        if ((buttons == MouseEvent.BUTTON1)) buttonMask = InputEvent.BUTTON1_MASK;
        if ((buttons == MouseEvent.BUTTON2)) buttonMask |= InputEvent.BUTTON2_MASK;
        if ((buttons == MouseEvent.BUTTON3)) buttonMask |= InputEvent.BUTTON3_MASK;
        switch(evt.getID()) {
            case MouseEvent.MOUSE_PRESSED: rt.mousePress(buttonMask); break;
            case MouseEvent.MOUSE_RELEASED: rt.mouseRelease(buttonMask); break;
            case MouseEvent.MOUSE_WHEEL: rt.mouseWheel(
                    ((MouseWheelEvent) evt).getUnitsToScroll()); break;
        }
    }

    public void applyKeyEvent(Map<Integer, Boolean> evt) {
        System.out.println("got a keyevent here");
        for (Map.Entry<Integer, Boolean> entry : evt.entrySet()){
            if(entry.getValue())
                rt.keyPress(entry.getKey());
            else
                rt.keyRelease(entry.getKey());
        }
    }
}

