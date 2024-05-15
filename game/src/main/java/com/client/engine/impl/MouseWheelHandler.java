package com.client.engine.impl;

import com.client.Client;
import net.runelite.rs.api.RSMouseWheelHandler;

import java.awt.*;
import java.awt.event.*;

import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.SettingsTabWidget;

import static com.client.engine.impl.MouseHandler.mouseX;
import static com.client.engine.impl.MouseHandler.mouseY;

public class MouseWheelHandler implements MouseWheelListener, RSMouseWheelHandler {

    static int rotation;
    public static boolean mouseWheelDown;
    public static int mouseWheelX;
    public static int mouseWheelY;

    public static synchronized int useRotation() {
        int rotation = MouseWheelHandler.rotation;
        MouseWheelHandler.rotation = 0;
        return rotation; 
    }

    public void addTo(Component component) {
        component.addMouseWheelListener(this);
    }

    public void removeFrom(Component component) {
        component.removeMouseWheelListener(this);
    }

    public boolean canZoom = true;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        e = Client.instance.getCallbacks().mouseWheelMoved(e);
        if(e.isConsumed())
            return;
        int rotation = e.getWheelRotation();
        MouseWheelHandler.rotation = rotation;
        Client.rotation = rotation;
        if(Client.instance.isMenuOpen()) {
            Client client = Client.instance;
            int var2 = client.getMouseX();
            int var3 = client.getMouseY();
            //this checks if the mouse is contained in the menu area
            boolean regmenuContainsMouse = (var2 >= client.getMenuX() - 10 && var2 <= client.getMenuX() + client.getMenuWidth() + 10 && var3 >= client.getMenuY() - 10 && var3 <= client.getMenuY() + client.getMenuHeight() + 10);
            boolean submenuContainsMouse = (var2 >= client.getSubmenuX() - 10 && var2 <= client.getSubmenuX() + client.getSubmenuWidth() + 10 && var3 >= client.getSubmenuY() - 10 && var3 <= client.getSubmenuY() + client.getSubmenuHeight() + 10);
            if(regmenuContainsMouse || submenuContainsMouse)
                return;
        }
        if (!handleInterfaceScrolling(e)) {
            if (mouseX > 0 && mouseX < 512 && MouseHandler.mouseY > Client.canvasHeight - 165
                    && MouseHandler.mouseY < Client.canvasHeight - 25) {
                int scrollPos = Client.anInt1089;
                scrollPos -= rotation * 30;
                if (scrollPos < 0)
                    scrollPos = 0;

                if (Client.anInt1089 != scrollPos) {
                    Client.anInt1089 = scrollPos;
                }
            } else if (Client.loggedIn) {
                    /** ZOOMING **/
                    boolean zoom = !Client.instance.isResized() ? (mouseX < 512) : (mouseX < Client.canvasWidth - 200);
                    if(zoom && Client.openInterfaceID == -1) {
                        int zoom_in = !Client.instance.isResized() ? 195 : 240;

                        int zoom_out = !Client.instance.isResized() ? 1105 : 1220;

                        if (rotation != -1) {
                            if (Client.cameraZoom > zoom_in) {
                                Client.cameraZoom -= 45;
                            }
                        } else {
                            if (Client.cameraZoom < zoom_out) {
                                Client.cameraZoom += 45;
                            }
                        }

                    }
                    Client.inputTaken = true;


                    if (Client.openInterfaceID == -1 && zoom) {
                        RSInterface.interfaceCache[SettingsTabWidget.ZOOMTOGGLE].active = true;
                        RSInterface.interfaceCache[SettingsTabWidget.ZOOM_SLIDER].slider.setValue(Client.cameraZoom);
                    }
            }
        }
    }

    public Boolean inMap(int mouseX, int mouseY, boolean resized, int width)  {
        int i = mouseX - 25 - 547;
        int j = mouseY - 5 - 3;
        if (resized) {
            i = mouseX - (width - 182 + 24);
            j = mouseY - 8;
        }

        return inCircle(0, 0, i, j, 76);
    }

    public boolean inCircle(int circleX, int circleY, int clickX, int clickY, int radius) {
        return Math.pow((circleX + radius - clickX), 2)
                + Math.pow((circleY + radius - clickY), 2) < Math
                .pow(radius, 2);
    }

    public boolean handleInterfaceScrolling(MouseWheelEvent event) {
        int rotation = event.getWheelRotation();
        int offsetX;
        int offsetY;

        /* Tab interface scrolling */
        int tabInterfaceID = Client.tabInterfaceIDs[Client.tabID];
        if (tabInterfaceID != -1) {
            offsetX = !Client.instance.isResized() ? 765 - 218 : Client.canvasWidth - 197;
            offsetY = !Client.instance.isResized() ? 503 - 298
                    : Client.canvasHeight - (Client.canvasWidth >= 960 ? 37 : 74) - 267;
            if (RSInterface.get(tabInterfaceID) == null) {
                return false;
            }
            handleTabInterfaceScrolling(RSInterface.get(tabInterfaceID), rotation, offsetX, offsetY);
        }

        /* Main interface scrolling */
        if (Client.openInterfaceID != -1) {
            offsetX = !Client.instance.isResized() ? 4
                    : (Client.canvasWidth / 2) - 356;
            offsetY = !Client.instance.isResized()? 4
                    : (Client.canvasHeight / 2) - 230;
            return handleMainInterfaceScrolling(Client.openInterfaceID, offsetX, offsetY, rotation);
        }

        return false;
    }

    private void handleTabInterfaceScrolling(RSInterface tab, int rotation, int offsetX, int offsetY) {
        int positionX = 0;
        int positionY = 0;
        int width = 0;
        int height = 0;
        int childID = 0;


        if (tab.children != null) {
            for (int index = 0; index < tab.children.length; index++) {
                RSInterface child = RSInterface.interfaceCache[tab.children[index]];
                handleTabInterfaceScrolling(child, rotation, offsetX + tab.childX[index], offsetY + tab.childY[index]);
                if (child.scrollMax > 0) {
                    childID = index;
                    positionX = tab.childX[index];
                    positionY = tab.childY[index];
                    width = child.width + 16;
                    height = child.height;
                    break;
                }
            }
        }
        if (mouseX > offsetX + positionX && MouseHandler.mouseY > offsetY + positionY && mouseX < offsetX + positionX + width
                && MouseHandler.mouseY < offsetY + positionY + height) {
            RSInterface rsInterface = RSInterface.interfaceCache[tab.children[childID]];
            int newScrollPosition = rsInterface.scrollPosition + (rotation * 30);
            if (newScrollPosition < 0) {
                rsInterface.scrollPosition = 0;
            } else if (newScrollPosition > rsInterface.scrollMax - rsInterface.height) {
                rsInterface.scrollPosition = rsInterface.scrollMax - rsInterface.height;
            } else {
                rsInterface.scrollPosition += rotation * 30;
            }
            Client.tabAreaAltered = true;
            Client.needDrawTabArea = true;
            return;
        }
    }

    private boolean handleMainInterfaceScrolling(int interfaceId, int offsetX, int offsetY, int rotation) {
        RSInterface rsi = RSInterface.interfaceCache[interfaceId];
        if (rsi.children != null) {
            for (int index = 0; index < rsi.children.length; index++) {
                handleMainInterfaceScrolling(rsi.children[index], offsetX + rsi.childX[index], offsetY + rsi.childY[index], rotation);
                if (RSInterface.interfaceCache[rsi.children[index]].scrollMax <= 0) {
                    continue;
                }
                if (mouseX > offsetX + rsi.childX[index] && MouseHandler.mouseY > offsetY + rsi.childY[index]
                        && mouseX < offsetX + rsi.childX[index] + RSInterface.interfaceCache[rsi.children[index]].width + 16
                        && MouseHandler.mouseY < offsetY + rsi.childY[index]
                        + RSInterface.interfaceCache[rsi.children[index]].height) {

                    RSInterface rsInterface = RSInterface.interfaceCache[rsi.children[index]];
                    int newScrollPosition = rsInterface.scrollPosition + (rotation * 30);
                    rsInterface.scrollPosition += rotation * 30;
                    if (newScrollPosition < 0) {
                        rsInterface.scrollPosition = 0;
                    } else if (newScrollPosition > rsInterface.scrollMax - rsInterface.height) {
                        rsInterface.scrollPosition = rsInterface.scrollMax - rsInterface.height;
                    } else {
                        rsInterface.scrollPosition += rotation * 30;
                    }
                    return true;
                }
            }
        }

        return false;
    }

}
