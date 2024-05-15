package net.runelite.client.plugins.playeroutline;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

public class PlayerOutlineOverlay extends Overlay
{

    private final Client client;
    private final PlayerOutlineConfig config;
    private final ModelOutlineRenderer modelOutlineRenderer;

    @Inject
    public PlayerOutlineOverlay(Client client, PlayerOutlineConfig config, ModelOutlineRenderer modelOutlineRenderer)
    {
        this.client = client;
        this.config = config;
        this.modelOutlineRenderer = modelOutlineRenderer;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.HIGH);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        modelOutlineRenderer.drawOutline(client.getLocalPlayer(), config.borderWidth(), config.playerOutlineColor(), config.outlineFeather());
        return null;
    }

}