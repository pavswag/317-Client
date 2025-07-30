package com.client;

import com.client.definitions.ItemDefinition;
import com.client.sign.Signlink;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * @author ArkCane
 * @social Discord: ArkCane
 * Website: www.arkcane.net
 * @since 29/03/2024
 */
public class ItemStats {

    public static final int STAB = 0;
    public static final int SLASH = 1;
    public static final int CRUSH = 2;
    public static final int MAGIC = 3;
    public static final int RANGED = 4;

    public static ItemStats[] itemstats = new ItemStats[
            ItemDefinition.totalItems > 0 ? ItemDefinition.totalItems + 20_000 : 35_000];
    public int itemId;
    public int[] attackBonus;
    public int[] defenceBonus;
    public int prayerBonus;
    public int rangeBonus;
    public int strengthBonus;
    public int magicBonus;
    public int healAmount;
    public int type;
    public int[][] rewards;
    public String information;

    public ItemStats(int id, int typeOfStat) {
        this.itemId = id;
        this.attackBonus = new int[]{0, 0, 0, 0, 0};
        this.defenceBonus = new int[]{0, 0, 0, 0, 0};
        this.prayerBonus = 0;
        this.strengthBonus = 0;
        this.rangeBonus = 0;
        this.magicBonus = 0;
        this.healAmount = 0;
        this.type = typeOfStat;
    }
    /**
     * No-argument constructor for JSON deserialization.
     */
    public ItemStats() {
    }
    private static int readType = 0;

    public static void readDefinitions() {
        try {
            File file = new File(Signlink.getCacheDirectory() + "item_stats.json");
            if (!file.exists()) {
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(file);

            readType = 1;

            if (root.isArray()) {
                List<ItemStats> list = mapper.convertValue(root,
                        new TypeReference<List<ItemStats>>() {});
                for (ItemStats stats : list) {
                    int id = stats.itemId;
                    if (id < 0 || id >= itemstats.length) {
                        continue;
                    }
                    itemstats[id] = stats;
                }
            } else if (root.isObject()) {
                for (Iterator<Map.Entry<String, JsonNode>> it = root.fields(); it.hasNext();) {
                    Map.Entry<String, JsonNode> entry = it.next();
                    int id;
                    try {
                        id = Integer.parseInt(entry.getKey());
                    } catch (NumberFormatException ex) {
                        continue;
                    }
                    if (id < 0 || id >= itemstats.length) {
                        continue;
                    }
                    JsonNode eq = entry.getValue().get("equipment");
                    if (eq == null) {
                        continue;
                    }
                    ItemStats stats = new ItemStats();
                    stats.itemId = id;
                    stats.attackBonus = new int[] {
                            eq.path("astab").asInt(),
                            eq.path("aslash").asInt(),
                            eq.path("acrush").asInt(),
                            eq.path("amagic").asInt(),
                            eq.path("arange").asInt() };
                    stats.defenceBonus = new int[] {
                            eq.path("dstab").asInt(),
                            eq.path("dslash").asInt(),
                            eq.path("dcrush").asInt(),
                            eq.path("dmagic").asInt(),
                            eq.path("drange").asInt() };
                    stats.strengthBonus = eq.path("str").asInt();
                    stats.rangeBonus = eq.path("rstr").asInt();
                    stats.magicBonus = eq.path("mdmg").asInt();
                    stats.prayerBonus = eq.path("prayer").asInt();
                    itemstats[id] = stats;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}