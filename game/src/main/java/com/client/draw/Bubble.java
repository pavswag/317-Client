package com.client.draw;

import com.client.Client;
import com.client.Rasterizer2D;

public class Bubble {
    public Client client;
    private int xPos;
    private int yPos;
    private byte radius;
    private int speed;
    private int xChange;
    private int alpha;
    public static final byte BUBBLES = 0;
    private final int color = 0x454545;

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public byte getRadius() {
        return radius;
    }

    public Bubble() {
        radius = (byte) (Math.random() * 60.0d);
        if (radius < 10) {
            radius += 5;
        }
        xChange = ((Math.random()) == 1 ? -1 : 1) * (int) Math.round(Math.random());
        xPos = radius + (int) (Math.random() * (Client.canvasWidth - radius * 2));
        yPos = Client.canvasHeight - radius + (int) (Math.random() * 50.0d);
        speed = (int) (Math.random() * 3.0d);
        if (speed == 0) {
            speed = 1;
        }
        xSpeed = speed;
        ySpeed = speed;
    }

    private boolean setToRandomY = false;
    private int xSpeed;
    private int ySpeed;

    public void draw(byte state) {
        switch (state) {
            case BUBBLES:
                this.yPos -= speed;
                this.xPos += xChange;
                if (this.yPos < 0 - radius) {
                    radius = (byte) (Math.random() * 60.0d);
                    if (radius < 30) {
                        radius -= 15;
                    }
                    xChange = (Math.round(Math.random()) == 1 ? -1 : 1) * (int) Math.round(Math.random());
                    xPos = radius + (int) (Math.random() * (Client.canvasWidth - radius));
                    yPos = Client.canvasHeight + radius + (int) (Math.random() * (Math.random() * 50.0d));
                    speed = (int) (Math.random() * 3.0d);
                    if (speed == 0) {
                        speed = 1;
                    }
                }
                Rasterizer2D.drawBubble(this.xPos, this.yPos, (int) this.radius, color, this.alpha);
                break;
        }
    }
}