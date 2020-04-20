package com.gec.lab_student.models;

import java.io.Serializable;

public class KeyEvent implements Serializable {
    private int keyCode;
    private boolean isKeyPressed;

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public boolean isKeyPressed() {
        return isKeyPressed;
    }

    public void setKeyPressed(boolean keyPressed) {
        isKeyPressed = keyPressed;
    }

    public KeyEvent(int keyCode, boolean isKeyPressed) {
        this.keyCode = keyCode;
        this.isKeyPressed = isKeyPressed;
    }
}
