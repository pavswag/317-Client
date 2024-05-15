package net.runelite.client.plugins.feed;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.SpritePixels;
import net.runelite.api.events.FeedRecieved;
import net.runelite.client.Notifier;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SpriteOverride;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FatalErrorDialog;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;
import net.runelite.http.api.feed.FeedItem;
import net.runelite.http.api.feed.FeedItemType;
import net.runelite.http.api.feed.FeedResult;
import okhttp3.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Singleton
class FeedPanel extends PluginPanel {
    private static final ImageIcon RUNELITE_ICON;
    private static final ImageIcon OSRS_ICON;
    private final ItemManager itemManager;
    private static final Color TWEET_BACKGROUND = new Color(15, 15, 15);
    private static final Color OSRS_NEWS_BACKGROUND = new Color(36, 30, 19);
    private static final Color BLOG_POST_BACKGROUND = new Color(41, 11, 11);

    private static final int MAX_CONTENT_LINES = 3;
    private static final int CONTENT_WIDTH = 148;
    private static final int TIME_WIDTH = 20;

    private static List<FeedItem> currentFeedItems = new ArrayList<>();

    @Inject
    private Notifier notifier;

    private static final Comparator<FeedItem> FEED_ITEM_COMPARATOR = (o1, o2) -> -Long.compare(o1.getTimestamp(), o2.getTimestamp());

    static {
        RUNELITE_ICON = new ImageIcon(ImageUtil.loadImageResource(FeedPanel.class, "runelite.png"));
        OSRS_ICON = new ImageIcon(ImageUtil.loadImageResource(FeedPanel.class, "osrs.png"));
        currentFeedItems.add(new FeedItem(FeedItemType.GROOT, null, "Groot Spawned", ";;groot to kill them", "https://discord.gg/BRcRaVVX", System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(30)));
        currentFeedItems.add(new FeedItem(FeedItemType.DONOR_BOSS, null, "Donor Boss Spawend", ";;db to kill them", "https://discord.gg/BRcRaVVX", System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(30)));
        currentFeedItems.add(new FeedItem(FeedItemType.SHOOTING_STAR, null, "Shooting Star Spawend", ";;star to get to the star", "https://discord.gg/BRcRaVVX", System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(30)));
        currentFeedItems.add(new FeedItem(FeedItemType.VOTE_BOSS, null, "Vote Boss Spawend", ";;vb to kill them", "https://discord.gg/BRcRaVVX", System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(30)));
        currentFeedItems.add(new FeedItem(FeedItemType.SEASONAL, null, "Seasonal event has spawned", ";;seasonal to get there", "https://discord.gg/BRcRaVVX", System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(30)));
        currentFeedItems.add(new FeedItem(FeedItemType.WILDY_BOSS, null, "Wildy event is active", ";;wildyevent to get there", "https://discord.gg/BRcRaVVX", System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(30)));
        currentFeedItems.add(new FeedItem(FeedItemType.HESPORI_BOSS, null, "Hespori is active", ";;worldevent to get there.", "https://discord.gg/BRcRaVVX", System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(30)));
    }

    private final FeedConfig config;
    public Supplier<FeedResult> feedSupplier;

    @Inject
    FeedPanel(ItemManager itemManager, FeedConfig config, Supplier<FeedResult> feedSupplier) {
        this.itemManager = itemManager;
        this.config = config;
        this.feedSupplier = feedSupplier;

        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new GridLayout(0, 1, 0, 4));
    }

    void rebuildFeed() {
        if (!currentFeedItems.isEmpty()) {
            currentFeedItems.removeIf(currentFeedItem -> currentFeedItem.getTimestamp()+TimeUnit.MINUTES.toMillis(2) < System.currentTimeMillis());
        }
        if (currentFeedItems.isEmpty()) {
            reDrawFeed();
            return;
        }
                SwingUtilities.invokeLater(() ->
                {
                    reDrawFeed();
                    currentFeedItems.stream()
                            .filter(f -> f.getType() != FeedItemType.GROOT || config.includeGrootSpawns())
                            .filter(f -> f.getType() != FeedItemType.DONOR_BOSS || config.includeDonorSpawns())
                            .filter(f -> f.getType() != FeedItemType.VOTE_BOSS || config.includeVoteSpawns())
                            .filter(f -> f.getType() != FeedItemType.WILDY_BOSS || config.includeWildySpawns())
                            .filter(f -> f.getType() != FeedItemType.HESPORI_BOSS || config.includeHesporiSpawns())
                            .filter(f -> f.getType() != FeedItemType.SHOOTING_STAR || config.includeStarSpawns())
                            .filter(f -> f.getType() != FeedItemType.SEASONAL || config.includeSeasonalSpawns())
                            .sorted(FEED_ITEM_COMPARATOR)
                            .forEach(this::addItemToPanel);
                });
    }

    private void addItemToPanel(FeedItem item) {
        JPanel avatarAndRight = new JPanel(new BorderLayout());
        avatarAndRight.setPreferredSize(new Dimension(0, 56));
        JLabel avatar = new JLabel();
        // width = 48+4 to compensate for the border
        avatar.setPreferredSize(new Dimension(52, 48));
        avatar.setBorder(new EmptyBorder(0, 4, 0, 0));

        switch (item.getType()) {
            case GROOT:
                avatar.setIcon(new ImageIcon(itemManager.getImage(1044, 1, false).getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
                break;
            case DONOR_BOSS:
                avatar.setIcon(new ImageIcon(itemManager.getImage(1042, 1, false).getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
                break;
            case VOTE_BOSS:
                avatar.setIcon(new ImageIcon(itemManager.getImage(1046, 1, false).getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
                break;
            case WILDY_BOSS:
                avatar.setIcon(new ImageIcon(itemManager.getImage(1038, 1, false).getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
                break;
            case HESPORI_BOSS:
                avatar.setIcon(new ImageIcon(itemManager.getImage(20661, 1, false).getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
                break;
            case SHOOTING_STAR:
                avatar.setIcon(new ImageIcon(itemManager.getImage(25527, 100000000, true).getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
                break;
            case SEASONAL:
                avatar.setIcon(new ImageIcon(itemManager.getImage(23108, 1, false).getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
                break;
        }

        avatarAndRight.setBackground(BLOG_POST_BACKGROUND);

        JPanel upAndContent = new JPanel();
        upAndContent.setLayout(new BoxLayout(upAndContent, BoxLayout.Y_AXIS));
        upAndContent.setBorder(new EmptyBorder(4, 8, 4, 4));
        upAndContent.setBackground(null);

        JPanel titleAndTime = new JPanel();
        titleAndTime.setLayout(new BorderLayout());
        titleAndTime.setBackground(null);

        Color darkerForeground = UIManager.getColor("Label.foreground").darker();

        JLabel titleLabel = new JLabel(item.getTitle());
        titleLabel.setFont(FontManager.getRunescapeSmallFont());
        titleLabel.setBackground(null);
        titleLabel.setForeground(darkerForeground);
        titleLabel.setPreferredSize(new Dimension(CONTENT_WIDTH - TIME_WIDTH, 0));

        Duration duration = Duration.between(Instant.ofEpochMilli(item.getTimestamp()), Instant.now());
        JLabel timeLabel = new JLabel(durationToString(duration));
        timeLabel.setFont(FontManager.getRunescapeSmallFont());
        timeLabel.setForeground(darkerForeground);

        titleAndTime.add(titleLabel, BorderLayout.WEST);
        titleAndTime.add(timeLabel, BorderLayout.EAST);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(null);

        JLabel contentLabel = new JLabel(lineBreakText(item.getContent(), FontManager.getRunescapeSmallFont()));
        contentLabel.setBorder(new EmptyBorder(2, 0, 0, 0));
        contentLabel.setFont(FontManager.getRunescapeSmallFont());
        contentLabel.setForeground(darkerForeground);

        content.add(contentLabel, BorderLayout.CENTER);

        upAndContent.add(titleAndTime);
        upAndContent.add(content);
        upAndContent.add(new Box.Filler(new Dimension(0, 0),
                new Dimension(0, Short.MAX_VALUE),
                new Dimension(0, Short.MAX_VALUE)));

        avatarAndRight.add(avatar, BorderLayout.WEST);
        avatarAndRight.add(upAndContent, BorderLayout.CENTER);

        Color backgroundColor = avatarAndRight.getBackground();
        Color hoverColor = backgroundColor.brighter().brighter();
        Color pressedColor = hoverColor.brighter();

        avatarAndRight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                avatarAndRight.setBackground(hoverColor);
                avatarAndRight.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                avatarAndRight.setBackground(backgroundColor);
                avatarAndRight.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Container parent = avatarAndRight.getParent();
                    if (parent != null) {
                        parent.remove(avatarAndRight); // Remove the panel from its parent container
                        parent.revalidate();
                        parent.repaint();
                    }
                } else {
                    if (item.getUrl().contains("https:")) {
                        LinkBrowser.browse(item.getUrl());
                    }
                }
                avatarAndRight.setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                avatarAndRight.setBackground(hoverColor);
            }
        });

        add(avatarAndRight);
        revalidate();
        repaint();
    }

    private String durationToString(Duration duration) {
        if (duration.getSeconds() >= 60 * 60 * 24) {
            return (int) (duration.getSeconds() / (60 * 60 * 24)) + "d";
        } else if (duration.getSeconds() >= 60 * 60) {
            return (int) (duration.getSeconds() / (60 * 60)) + "h";
        }
        return (int) (duration.getSeconds() / 60) + "m";
    }

    private String lineBreakText(String text, Font font) {
        StringBuilder newText = new StringBuilder("<html>");

        FontRenderContext fontRenderContext = new FontRenderContext(font.getTransform(),
                true, true);

        int lines = 0;
        int pos = 0;
        String[] words = text.split(" ");
        String line = "";

        while (lines < MAX_CONTENT_LINES && pos < words.length) {
            String newLine = pos > 0 ? line + " " + words[pos] : words[pos];
            double width = font.getStringBounds(newLine, fontRenderContext).getWidth();

            if (width >= CONTENT_WIDTH) {
                newText.append(line);
                newText.append("<br>");
                line = "";
                lines++;
            } else {
                line = newLine;
                pos++;
            }
        }

        newText.append(line);
        newText.append("</html>");

        return newText.toString();
    }


    public void onFeedRecieved(FeedRecieved feedRecieved) {
        FeedResult initialFeedResult = new FeedResult(feedRecieved.getItems()); // Initialize with an initial FeedResult

        currentFeedItems.addAll(initialFeedResult.getItems());
        feedRecieved.getItems().forEach(feedItem -> {
            if (feedItem.getType() == FeedItemType.GROOT && config.includeGrootSpawns()) {
                if (config.notificationSounds()) {
                    Toolkit.getDefaultToolkit().beep();
                }
                if (config.trayNotification()) {
                    notifier.notify(feedItem.content, TrayIcon.MessageType.INFO);
                }
            }
            if (feedItem.getType() == FeedItemType.DONOR_BOSS && config.includeDonorSpawns()) {
                if (config.notificationSounds()) {
                    Toolkit.getDefaultToolkit().beep();
                }
                if (config.trayNotification()) {
                    notifier.notify(feedItem.content, TrayIcon.MessageType.INFO);
                }
            }
            if (feedItem.getType() == FeedItemType.VOTE_BOSS && config.includeVoteSpawns()) {
                if (config.notificationSounds()) {
                    Toolkit.getDefaultToolkit().beep();
                }
                if (config.trayNotification()) {
                    notifier.notify(feedItem.content, TrayIcon.MessageType.INFO);
                }
            }
            if (feedItem.getType() == FeedItemType.WILDY_BOSS && config.includeWildySpawns()) {
                if (config.notificationSounds()) {
                    Toolkit.getDefaultToolkit().beep();
                }
                if (config.trayNotification()) {
                    notifier.notify(feedItem.content, TrayIcon.MessageType.INFO);
                }
            }
            if (feedItem.getType() == FeedItemType.HESPORI_BOSS && config.includeHesporiSpawns()) {
                if (config.notificationSounds()) {
                    Toolkit.getDefaultToolkit().beep();
                }
                if (config.trayNotification()) {
                    notifier.notify(feedItem.content, TrayIcon.MessageType.INFO);
                }
            }
            if (feedItem.getType() == FeedItemType.SHOOTING_STAR && config.includeStarSpawns()) {
                if (config.notificationSounds()) {
                    Toolkit.getDefaultToolkit().beep();
                }
                if (config.trayNotification()) {
                    notifier.notify(feedItem.content, TrayIcon.MessageType.INFO);
                }
            }
            if (feedItem.getType() == FeedItemType.SEASONAL && config.includeSeasonalSpawns()) {
                if (config.notificationSounds()) {
                    Toolkit.getDefaultToolkit().beep();
                }
                if (config.trayNotification()) {
                    notifier.notify(feedItem.content, TrayIcon.MessageType.INFO);
                }
            }
        });

        rebuildFeed();
    }

    private void reDrawFeed() {
        for (Component component : this.getComponents()) {
            Container parent = component.getParent();
            if (parent != null) {
                parent.remove(component);
                parent.revalidate();
                parent.repaint();
            }
        }
    }
}
