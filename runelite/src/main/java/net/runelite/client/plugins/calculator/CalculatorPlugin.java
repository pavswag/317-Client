package net.runelite.client.plugins.calculator;

import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.calculator.ui.CalculatorPluginPanel;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PluginDescriptor(name = "Calculator", description = "Adds a calculator panel", tags = {"math"})
public class CalculatorPlugin extends Plugin {
    private static final Logger log = LoggerFactory.getLogger(CalculatorPlugin.class);

    @Inject
    private ClientToolbar clientToolbar;

    private NavigationButton navButton;

    protected void startUp() {
        log.info("calculator started!");
        CalculatorPluginPanel panel = new CalculatorPluginPanel();
        BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "icon.png");
        this

                .navButton = NavigationButton.builder().tooltip("Calculator").priority(7).icon(icon).panel((PluginPanel)panel).build();
        this.clientToolbar.addNavigation(this.navButton);
    }

    protected void shutDown() {
        this.clientToolbar.removeNavigation(this.navButton);
        log.info("calculator stopped!");
    }
}
