package com.client.graphics.interfaces.builder.impl;

import com.client.Sprite;
import com.client.graphics.interfaces.Configs;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.builder.InterfaceBuilder;
import com.client.graphics.interfaces.impl.Interfaces;

public class LeaderboardInterface extends InterfaceBuilder {

    private final Sprite bg = new Sprite("interfaces/leaderboard/IMAGE 0");
    private final Sprite typeSelectionButtonLight = new Sprite("interfaces/leaderboard/IMAGE 1");
    private final Sprite typeSelectionButtonDark = new Sprite("interfaces/leaderboard/IMAGE 2");

    private final Sprite periodButtonDark = new Sprite("interfaces/leaderboard/IMAGE 3");
    private final Sprite periodButtonLight = new Sprite("interfaces/leaderboard/IMAGE 4");

    private final Sprite entryBackgroundDark = new Sprite("interfaces/leaderboard/IMAGE 5");
    private final Sprite entryBackgroundLight = new Sprite("interfaces/leaderboard/IMAGE 6");

    public LeaderboardInterface() {
        super(18820);
    }

    @Override
    public void build() {
        int x = 12;
        int y = 17;

        addSprite(nextInterface(), bg);
        child(x, y);

        addText(nextInterface(), 2, DEFAULT_TEXT_COLOR, true, "Grimoire Leaderboard");
        child(x + 265, y + 11);

        child(Interfaces.CLOSE_BUTTON_SMALL, x + 470, y + 11);
        child(Interfaces.CLOSE_BUTTON_SMALL_HOVER, x + 470, y + 11);

        addText(nextInterface(), 2, DEFAULT_TEXT_COLOR, true, "Leaderboards");
        child(x + 67, y + 40);

        addText(nextInterface(), 2, DEFAULT_TEXT_COLOR, true, "Player");
        child(x + 160, y + 40);

        addText(nextInterface(), 2, DEFAULT_TEXT_COLOR, true, "Count");
        child(x + 296, y + 40);

        addText(nextInterface(), 2, DEFAULT_TEXT_COLOR, true, "Reward");
        child(x + 430, y + 40);

        RSInterface boards = addInterface(nextInterface());
        child(8,y + 61);
        boards.width = 112;
        boards.height = 213;
        boards.scrollMax = 19*21;
        setChildren(40, boards);
        int boardchild = 0;

        for (int i = 0; i < 20; i++) {
            RSInterface button = addConfigButton(nextInterface(), typeSelectionButtonDark, typeSelectionButtonLight, "Select", i, Configs.LEADERBOARD_TYPE_CONFIG, 4);
            button.ignoreConfigClicking = true;
            boards.child(boardchild++, lastInterface(),x, (i * 20));

            addText(nextInterface(), 0, DEFAULT_TEXT_COLOR, true, "#" + lastInterface());
            boards.child(boardchild++, lastInterface(),x + 54, 5 + (i * 20));
        }

        RSInterface container = addInterface(nextInterface());
        child(x + 127, y + 63);
        container.width = 364;
        container.height = 230;
        container.scrollMax = 0;
        setChildren(40, container);
        int childId = 0;

        for (int i = 0; i < 10; i++) {
            addSprite(nextInterface(), i % 2 == 0 ? entryBackgroundDark : entryBackgroundLight);
            container.child(childId++, lastInterface(), 0, (i * 21));

            addText(nextInterface(), 1, DEFAULT_TEXT_COLOR, false, "" + lastInterface());
            container.child(childId++, lastInterface(), 12, 3 + (i * 21));

            addText(nextInterface(), 1, DEFAULT_TEXT_COLOR, false, "" + lastInterface());
            container.child(childId++, lastInterface(), 150, 3 + (i * 21));

            RSInterface rewards = addItemContainer(nextInterface(), 1, 1, 0, 0, true,
                    true);
            rewards.forceInvStackSizes = true;
            container.child(childId++, lastInterface(), 295, 2 + (i * 21));
        }
    }
}
