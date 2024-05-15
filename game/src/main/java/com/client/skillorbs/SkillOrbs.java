package com.client.skillorbs;

import com.client.Client;
import com.client.Configuration;

import static com.client.Client.getUserSettings;

public class SkillOrbs {

    /**
     * The array containing all skill orbs.
     * Each skill orb per available skill.
     */
    public static final SkillOrb[] orbs = new SkillOrb[25];

    /**
     * Initializes orbs and their sprites.
     */
    public static void init() {
        for(int i = 0; i < 25; i++) {
            orbs[i] = new SkillOrb(i, Client.instance.smallXpSprites[i]);
        }
    }

    /**
     * Processes all orbs.
     */
    public static void process() {
        if (!Configuration.skillOrbs) {
            return;
        }
        //Our counter
        int totalOrbs = 0;

        //Count valid orbs..
        for(SkillOrb orb : orbs) {
            if(draw(orb)) {
                totalOrbs++;
            }
        }

        //Is the bounty hunter interface open? Then the orbs may need to be re-positioned.
        final boolean blockingInterfaceOpen = Client.instance.openWalkableWidgetID == 23300;
        boolean hpOverlay = getUserSettings().isShowEntityTarget();

        //Positionining of orbs
        int y = -2;
        int x = (int)(Client.canvasWidth / 3.1) - (totalOrbs * 30);

        if(blockingInterfaceOpen) {
            x -= (totalOrbs * 10);
        } else {
            if(hpOverlay) {
                if(x < 130) {
                    x = 130;
                }
                y = 12;
            }
        }

        if(x < 5) {
            x = 5;
        }

        //Current skillorb hover
        SkillOrb hover = null;

        //Draw orbs and get current hover...
        for(SkillOrb orb : orbs) {
            if(draw(orb)) {

                //Fade orb if needed
                if(orb.getShowTimer().finished()) {
                    orb.decrementAlpha();
                }

                //Draw orb
                orb.draw(x, y);

                //Check if this orb is being hovered
                if(Client.instance.hover(x, y, Client.instance.skillOrbs[0])) {
                    hover = orb;
                }

                //Increase x, space between orbs
                x += 62;
                if(x > (blockingInterfaceOpen ? 300 : 460)) {
                    break;
                }
            }
        }

        //Draw hover tooltip
        if(hover != null) {
            hover.drawTooltip();
        }
    }

    /**
     * Should a skillorb be drawn?
     * @param orb
     * @return
     */
    private static boolean draw(SkillOrb orb) {
//        System.out.println(!orb.getShowTimer().finished() || orb.getAlpha() > 0);
        return !orb.getShowTimer().finished() || orb.getAlpha() > 0;
    }
}
