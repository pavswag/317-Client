package com.client.menu;

import com.client.Client;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.runelite.api.events.MenuEntryAdded;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class MenuManager {
    private final Client client;


    @Getter
    @Setter
    private MenuEntry[] menuEntries = null;
    public void start() {
        menuEntries = new MenuEntry[500];

    }
    public void stop() {
        menuEntries = null;
    }

    public MenuEntry addEntry(MenuEntry entry) {
        if(entry.getIdx() >= 500)
            return null;
        menuEntries[entry.getIdx()] = entry;

        return menuEntries[entry.getIdx()];
    }
    public void clearEntries() {
       // menuEntries.clear();
    }

    public MenuEntry getMenuEntry(int idx) {
        MenuEntry entry = (MenuEntry) Client.instance.getMenuEntries()[idx];//menuEntries[idx];
        return entry;
    }

    public void reset() {
        //clearEntries();
        client.menuActionRow = 0;
        Arrays.fill(menuEntries, null);
    }
    public void addCancel() {
        addEntry((MenuEntry) new MenuEntry(0).setOption("Cancel").setType(1107));
        client.getCallbacks().post(new MenuEntryAdded(menuEntries[0]));
        client.menuActionRow = 1;
    }
}
