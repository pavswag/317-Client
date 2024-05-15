package net.runelite.client.game;

import net.runelite.api.*;
import net.runelite.api.clan.ClanTitle;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.util.ImageUtil;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

@Singleton
public class ChatIconManager
{
    private static final Dimension IMAGE_DIMENSION = new Dimension(11, 11);
    private static final Color IMAGE_OUTLINE_COLOR = new Color(33, 33, 33);

    private final Client client;
    private final SpriteManager spriteManager;

    private BufferedImage[] friendsChatRankImages;
    private BufferedImage[] clanRankImages;

    private int friendsChatOffset;
    private int clanOffset;

    @Inject
    private ChatIconManager(Client client, SpriteManager spriteManager, EventBus eventBus)
    {
        this.client = client;
        this.spriteManager = spriteManager;
        eventBus.register(this);
    }

    @Nullable
    public BufferedImage getRankImage(final FriendsChatRank friendsChatRank)
    {
        if (friendsChatRank == FriendsChatRank.UNRANKED)
        {
            return null;
        }

        return friendsChatRankImages[friendsChatRank.ordinal() - 1];
    }

    @Nullable
    public BufferedImage getRankImage(final ClanTitle clanTitle)
    {
        int rank = clanTitle.getId();
        int idx = clanRankToIdx(rank);
        return clanRankImages[idx];
    }

    public int getIconNumber(final FriendsChatRank friendsChatRank)
    {
        return friendsChatOffset + friendsChatRank.ordinal() - 1;
    }

    public int getIconNumber(final ClanTitle clanTitle)
    {
        int rank = clanTitle.getId();
        return clanOffset + clanRankToIdx(rank);
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged)
    {
        if (gameStateChanged.getGameState() == GameState.LOGIN_SCREEN && friendsChatOffset == 0)
        {
//            loadRankIcons();
        }
    }

    private void loadRankIcons()
    {
        final EnumComposition friendsChatIcons = client.getEnum(EnumID.FRIENDS_CHAT_RANK_ICONS);
        final EnumComposition clanIcons = client.getEnum(EnumID.CLAN_RANK_GRAPHIC);

        {
            IndexedSprite[] modIcons = client.getModIcons();
            friendsChatOffset = modIcons.length;
            clanOffset = friendsChatOffset + friendsChatIcons.size();

            IndexedSprite blank = ImageUtil.getImageIndexedSprite(
                    new BufferedImage(modIcons[0].getWidth(), modIcons[0].getHeight(), BufferedImage.TYPE_INT_ARGB),
                    client);

            modIcons = Arrays.copyOf(modIcons, friendsChatOffset + friendsChatIcons.size() + clanIcons.size());
            Arrays.fill(modIcons, friendsChatOffset, modIcons.length, blank);

            client.setModIcons(modIcons);
        }

        friendsChatRankImages = new BufferedImage[friendsChatIcons.size()];
        clanRankImages = new BufferedImage[clanIcons.size()];

        final IndexedSprite[] modIcons = client.getModIcons();

        for (int i = 0; i < friendsChatIcons.size(); i++)
        {
            final int fi = i;

            spriteManager.getSpriteAsync(friendsChatIcons.getIntValue(friendsChatIcons.getKeys()[i]), 0, sprite ->
            {
                friendsChatRankImages[fi] = friendsChatImageFromSprite(sprite);
                modIcons[friendsChatOffset + fi] = ImageUtil.getImageIndexedSprite(friendsChatRankImages[fi], client);
            });
        }

        for (int i = 0; i < clanIcons.size(); i++)
        {
            final int key = clanIcons.getKeys()[i];
            final int idx = clanRankToIdx(key);

            assert idx >= 0 && idx < clanIcons.size();

            spriteManager.getSpriteAsync(clanIcons.getIntValue(key), 0, sprite ->
            {
                final BufferedImage img = ImageUtil.resizeCanvas(sprite, IMAGE_DIMENSION.width, IMAGE_DIMENSION.height);
                clanRankImages[idx] = img;
                modIcons[clanOffset + idx] = ImageUtil.getImageIndexedSprite(img, client);
            });
        }
    }

    private static BufferedImage friendsChatImageFromSprite(final BufferedImage sprite)
    {
        final BufferedImage canvas = ImageUtil.resizeCanvas(sprite, IMAGE_DIMENSION.width, IMAGE_DIMENSION.height);
        return ImageUtil.outlineImage(canvas, IMAGE_OUTLINE_COLOR);
    }

    private static int clanRankToIdx(int key)
    {
        // keys are -6 to 265, with no 0
        return key < 0 ? ~key : (key + 5);
    }
}
