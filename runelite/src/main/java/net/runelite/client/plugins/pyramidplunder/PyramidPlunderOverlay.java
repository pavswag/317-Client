/*
 * Copyright (c) 2020 Mitchell <https://github.com/Mitchell-Kovacs>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.pyramidplunder;

import net.runelite.api.Point;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.util.ColorUtil;

import javax.inject.Inject;
import java.awt.*;

import static net.runelite.client.plugins.pyramidplunder.PyramidPlunderPlugin.*;

class PyramidPlunderOverlay extends Overlay {
    private static final int MAX_DISTANCE = 1500;

    private final Client client;
    private final PyramidPlunderPlugin plugin;
    private final PyramidPlunderConfig config;

    @Inject
    private PyramidPlunderOverlay(Client client, PyramidPlunderPlugin plugin, PyramidPlunderConfig config) {
        super(plugin);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        LocalPoint playerLocation = client.getLocalPlayer().getLocalLocation();

        // Highlight convex hulls of urns, chests, and sarcophagus
       /* int currentFloor = client.getVar(Varbits.PYRAMID_PLUNDER_ROOM);
        for (GameObject object : plugin.getObjectsToHighlight()) {
            if (config.highlightUrnsFloor() > currentFloor && URN_IDS.contains(object.getId())
                    || config.highlightChestFloor() > currentFloor && GRAND_GOLD_CHEST_ID == object.getId()
                    || config.highlightSarcophagusFloor() > currentFloor && SARCOPHAGUS_ID == object.getId()
                    || object.getLocalLocation().distanceTo(playerLocation) >= MAX_DISTANCE) {
                continue;
            }

            ObjectComposition imposter = client.getObjectDefinition(object.getId()).getImpostor();
            if (URN_CLOSED_IDS.contains(imposter.getId())
                    || GRAND_GOLD_CHEST_CLOSED_ID == imposter.getId()
                    || SARCOPHAGUS_CLOSED_ID == imposter.getId()) {
                Shape shape = object.getConvexHull();

                if (shape != null) {
                    OverlayUtil.renderPolygon(graphics, shape, config.highlightContainersColor());
                }
            }
        }*/

        Point mousePosition = client.getMouseCanvasPosition();

        // Highlight clickboxes of speartraps and tomb doors
        plugin.getTilesToHighlight().forEach((object, tile) ->
        {
            if (!config.highlightDoors() && TOMB_DOOR_WALL_IDS.contains(object.getId())
                    || !config.highlightSpeartraps() && SPEARTRAP_ID == object.getId()
                    || tile.getPlane() != client.getPlane()
                    || object.getLocalLocation().distanceTo(playerLocation) >= MAX_DISTANCE) {
                return;
            }

            Color highlightColor;
            if (SPEARTRAP_ID == object.getId()) {
                highlightColor = config.highlightSpeartrapsColor();
            } else {
                highlightColor = config.highlightDoorsColor();
            }

            Shape objectClickbox = object.getClickbox();
            if (objectClickbox != null) {
                if (objectClickbox.contains(mousePosition.getX(), mousePosition.getY())) {
                    graphics.setColor(highlightColor.darker());
                } else {
                    graphics.setColor(highlightColor);
                }

                graphics.draw(objectClickbox);
                graphics.setColor(ColorUtil.colorWithAlpha(highlightColor, highlightColor.getAlpha() / 5));
                graphics.fill(objectClickbox);
            }
        });

        return null;
    }
}
