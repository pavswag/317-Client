package com.client.menu;

import com.client.Client;
import com.client.RSFont;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.runelite.api.Actor;
import net.runelite.api.MenuAction;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.widgets.Widget;
import net.runelite.rs.api.RSRuneLiteMenuEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@NoArgsConstructor
public class MenuEntry implements RSRuneLiteMenuEntry {

    public MenuEntry(int idx) {
        setIdx(idx);
    }

    private String option;
    private String target;
    private long identifier;
    private int type;
    private int param0;
    private int param1;
    private boolean forceLeftClick;
    @Setter
    private int itemOp = -1;
    @Setter
    private int itemId = -1;
    private Widget widget;
    @Setter
    private Actor actor;

    @Setter
    private int idx;

    @Setter
    private Consumer<net.runelite.api.MenuEntry> consumer;


    @Override
    public String getOption() {
        return option;
    }

    @Override
    public net.runelite.api.MenuEntry setOption(String option) {
        this.option = RSFont.handleOldSyntax(option);
        return this;
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public net.runelite.api.MenuEntry setTarget(String target) {
        this.target = RSFont.handleOldSyntax(target);
        return this;
    }

    @Override
    public long getIdentifier() {
        return this.identifier;
    }

    @Override
    public net.runelite.api.MenuEntry setIdentifier(long identifier) {
        this.identifier = identifier;
        return this;
    }

    public int getOpcode() {
        return type;
    }

    @Override
    public MenuAction getType() {
        int t = type;
        if (t >= 2000)
            t -= 2000;
        return MenuAction.of(t);
    }

    @Override
    public net.runelite.api.MenuEntry setType(MenuAction type) {
        this.type = type.getId();
        return this;
    }

    public net.runelite.api.MenuEntry setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public int getParam0() {
        return this.param0;
    }

    @Override
    public net.runelite.api.MenuEntry setParam0(int param0) {
        this.param0 = param0;
        return this;
    }

    @Override
    public int getParam1() {
        return this.param1;
    }

    @Override
    public net.runelite.api.MenuEntry setParam1(int param1) {
        this.param1 = param1;
        return this;
    }

    @Override
    public boolean isForceLeftClick() {
        return this.forceLeftClick;
    }

    @Override
    public net.runelite.api.MenuEntry setForceLeftClick(boolean forceLeftClick) {
        this.forceLeftClick = forceLeftClick;
        return this;
    }

    @Override
    public boolean isDeprioritized() {
        return type >= MenuAction.MENU_ACTION_DEPRIORITIZE_OFFSET;
    }

    @Override
    public net.runelite.api.MenuEntry setDeprioritized(boolean deprioritized) {
        if (deprioritized) {
            if (type < MenuAction.MENU_ACTION_DEPRIORITIZE_OFFSET) {
                type += MenuAction.MENU_ACTION_DEPRIORITIZE_OFFSET;
            }
        } else {
            if (type >= MenuAction.MENU_ACTION_DEPRIORITIZE_OFFSET) {
                type -= MenuAction.MENU_ACTION_DEPRIORITIZE_OFFSET;
            }
        }

        return this;
    }

    @Override
    public net.runelite.api.MenuEntry onClick(Consumer<net.runelite.api.MenuEntry> callback) {
        this.consumer = callback;
        return this;
    }

    private MenuEntry parent;

    @Override
    public net.runelite.api.MenuEntry setParent(net.runelite.api.MenuEntry parent) {
        this.parent = (MenuEntry) parent;
        return this;
    }

    @Override
    public net.runelite.api.MenuEntry getParent() {
        return parent;
    }

    @Override
    public boolean isItemOp() {
        return itemOp != -1;
    }

    @Override
    public int getItemOp() {
        return itemOp;
    }

    @Override
    public int getItemId() {
        return itemId;
    }

    @Nullable
    @Override
    public Widget getWidget() {
        return widget;
    }

    @Nullable
    @Override
    public NPC getNpc() {
        return actor instanceof NPC ? (NPC) actor : null;
    }

    @Nullable
    @Override
    public net.runelite.api.Player getPlayer() {
        return actor instanceof net.runelite.api.Player ? (Player) actor : null;
    }

    @Nullable
    @Override
    public Actor getActor() {
        return actor;
    }


    @Override
    public Consumer getConsumer() {
        return consumer;
    }

    @Override
    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public net.runelite.api.MenuEntry setWidget(Widget widget) {
        this.widget = widget;
        return this;
    }

    @Override
    public int getIdx() {
        return idx;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MenuEntry)) return false;
        final MenuEntry other = (MenuEntry) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$option = this.getOption();
        final Object other$option = other.getOption();
        //if (this$option == null ? other$option != null : !this$option.equals(other$option)) return false;
        final Object this$target = this.getTarget();
        final Object other$target = other.getTarget();
        //if (this$target == null ? other$target != null : !this$target.equals(other$target)) return false;
        if (this.getIdentifier() != other.getIdentifier()) return false;
        if (this.getType() != other.getType()) return false;
        if (this.getParam0() != other.getParam0()) return false;
        if (this.getParam1() != other.getParam1()) return false;
        if (this.isForceLeftClick() != other.isForceLeftClick()) return false;
        if (this.getItemOp() != other.getItemOp()) return false;
        if (this.getItemId() != other.getItemId()) return false;
        final Object this$widget = this.getWidget();
        final Object other$widget = other.getWidget();
        if (this$widget == null ? other$widget != null : !this$widget.equals(other$widget)) return false;
        final Object this$actor = this.getActor();
        final Object other$actor = other.getActor();
        if (this$actor == null ? other$actor != null : !this$actor.equals(other$actor)) return false;
        if (this.getIdx() != other.getIdx()) return false;
        final Object this$consumer = this.getConsumer();
        final Object other$consumer = other.getConsumer();
        if (this$consumer == null ? other$consumer != null : !this$consumer.equals(other$consumer)) return false;
        final Object this$parent = this.getParent();
        final Object other$parent = other.getParent();
        if (this$parent == null ? other$parent != null : !this$parent.equals(other$parent)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MenuEntry;
    }
}

