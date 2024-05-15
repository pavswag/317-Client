package com.client.graphics.interfaces;

import com.client.Sprite;

import java.util.HashMap;

/**
 * @author ArkCane
 * @social Discord: ArkCane
 * Website: www.arkcane.net
 * @since 22/03/2024
 */
public class InterfaceImage extends RSInterface {
    public int currentSpriteId = 0;

    private Sprite mainSprite;

    private HashMap<Integer, Sprite> extraSprites;

    public Sprite getCurrentSprite() {
        if (this.currentSpriteId > 0 && this.extraSprites != null)
            return this.extraSprites.get(Integer.valueOf(this.currentSpriteId));
        return this.mainSprite;
    }

    public InterfaceImage addExtraSprite(int spriteId, String sprite) {
        return addExtraSprite(spriteId, new Sprite(sprite));
    }

    public InterfaceImage addExtraSprite(int spriteId, Sprite sprite) {
        if (this.extraSprites == null)
            this.extraSprites = new HashMap<>();
        this.extraSprites.put(Integer.valueOf(spriteId), sprite);
        return this;
    }

    private boolean isTransparent = true;

    public boolean isTransparent() {
        return this.isTransparent;
    }

    public InterfaceImage setIsTransparent(boolean isTransparent) {
        this.isTransparent = isTransparent;
        return this;
    }

    public InterfaceImage(int interfaceId, String mainSpriteName) {
        this(interfaceId, new Sprite(mainSpriteName));
    }

    public InterfaceImage(int interfaceId, Sprite mainSprite) {
        this.id = this.parentID = interfaceId;
        this.type = 101;
        this.mainSprite = mainSprite;
        this.width = mainSprite.myWidth;
        this.height = mainSprite.myHeight;
        this.mOverInterToTrigger = -1;
        interfaceCache[this.id] = this;
    }
}
