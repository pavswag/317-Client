package com.client.definitions;

import com.client.*;
import com.client.util.BufferExt;
import com.client.utilities.FileOperations;
import com.google.common.base.Preconditions;
import net.runelite.api.IterableHashTable;
import net.runelite.rs.api.RSItemComposition;
import net.runelite.rs.api.RSIterableNodeHashTable;

import java.util.Arrays;
import java.util.HashMap;

public final class ItemDefinition implements RSItemComposition {

    public static ReferenceCache sprites = new ReferenceCache(100);
    public static ReferenceCache models = new ReferenceCache(50);
    public static boolean isMembers = true;
    public static int totalItems;
    public static ItemDefinition[] cache;
    private static int cacheIndex;
    private static Buffer item_data;
    private static int[] streamIndices;
    public int cost;
    public int[] colorReplace;
    public int id;
    public int[] colorFind;
    public boolean members;
    public int noted_item_id;
    public int femaleModel1;
    public int maleModel0;
    public String[] options;
    public int xOffset2d;
    public String name;
    public int modelId;
    public int maleHeadModel;
    public int weight;
    public int wearPos1;
    public int wearPos2;
    public int wearPos3;
    public boolean stackable;
    public int unnoted_item_id;
    public int zoom2d;
    public int maleModel1;
    public String[] interfaceOptions;
    public int xan2d;
    public int[] countObj;
    public int yOffset2d;//
    public int femaleHeadModel;
    public int yan2d;
    public int femaleModel0;
    public int[] countCo;
    public int team;
    public int zan2d;
    public String[] equipActions;
    public boolean tradeable;
    public HashMap<Integer, Object> params;
    public int glowColor = -1;
    private int[] textureReplace;
    private int[] textureFind;
    private byte femaleOffset;
    private int femaleModel2;
    private int maleHeadModel2;
    private int resizeX;
    private int femaleHeadModel2;
    private int contrast;
    private int maleModel2;
    private int resizeZ;
    private int resizeY;
    private int ambient;
    private byte maleOffset;
    private int shiftClickIndex = -2;
    private int category;
    private int bought_id;
    private int bought_template_id;
    private int placeholder_id;
    private int placeholder_template_id;
    public boolean animateInventory;

    private ItemDefinition() {
        id = -1;
    }

    public static void clear() {
        models = null;
        sprites = null;
        streamIndices = null;
        cache = null;
        item_data = null;
    }

    public static void init(FileArchive archive) {
        item_data = new Buffer(archive.getArchiveData("obj.dat"));
        Buffer stream = new Buffer(archive.getArchiveData("obj.idx"));

        totalItems = stream.readUShort();
        streamIndices = new int[totalItems + 20_000];
        int offset = 2;

        for (int _ctr = 0; _ctr < totalItems; _ctr++) {
            streamIndices[_ctr] = offset;
            offset += stream.readUShort();
        }

        cache = new ItemDefinition[10];

        for (int _ctr = 0; _ctr < 10; _ctr++) {
            cache[_ctr] = new ItemDefinition();
        }

        System.out.println("Loaded: " + totalItems + " items");
    }

    private static ItemDefinition newCustomItems(int itemId) {
        ItemDefinition itemDef = new ItemDefinition();
        itemDef.setDefaults();
        switch (itemId) {
            case 34000:
                return copyColored(itemDef, 34_000, 1128, "Rune platebody (gold)",
                        new int[] { 540, 541, 542 }, new int[] { 8128, 8129, 8130 }, "Wear");
            case 34001:
                return copyTextured(itemDef, 34_001, 1128, "Rune platebody (stone)",
                        new int[] { 2 }, new int[] { 46 }, "Wear");
            case 34_003:
                return copyGhost(itemDef, 34_002, 10348, "Rune platebody (ghost)", "Wear");
            case 30000:
                return copy(itemDef, 30_000, 11738, "Resource box(small)", "Open");
            case 30001:
                return copy(itemDef, 30_001, 11738, "Resource box(medium)", "Open");
            case 30002:
                return copy(itemDef, 30_002, 11738, "Resource box(large)", "Open");
            case 22375:
                return copy(itemDef, 22375, 22374, "Mossy key");


        }

        return null;
    }
    public static ItemDefinition copyColored(ItemDefinition itemDef, int newId, int copyingItemId, String newName,
                                             int[] colorFind, int[] colorReplace, String... actions) {
        copy(itemDef, newId, copyingItemId, newName, actions);
        itemDef.colorFind = colorFind;
        itemDef.colorReplace = colorReplace;
        return itemDef;
    }

    public static ItemDefinition copyTextured(ItemDefinition itemDef, int newId, int copyingItemId, String newName,
                                              int[] textureFind, int[] textureReplace, String... actions) {
        copy(itemDef, newId, copyingItemId, newName, actions);

        if (textureFind != null) {
        //    itemDef.textureFind = new short[textureFind.length];
            for (int i = 0; i < textureFind.length; i++) {
              //  itemDef.textureFind[i] = (short) textureFind[i];
            }
        } else {
            itemDef.textureFind = null;
        }

        if (textureReplace != null) {
          //  itemDef.textureReplace = new short[textureReplace.length];
            for (int i = 0; i < textureReplace.length; i++) {
                itemDef.textureReplace[i] = (short) textureReplace[i];
            }
        } else {
            itemDef.textureReplace = null;
        }

        return itemDef;
    }

    public static ItemDefinition copyGhost(ItemDefinition itemDef, int newId, int copyingItemId, String newName,
                                           String... actions) {
        ItemDefinition base = lookup(copyingItemId);
        copy(itemDef, newId, copyingItemId, newName, actions);
        if (base.colorFind != null) {
            itemDef.colorFind = Arrays.copyOf(base.colorFind, base.colorFind.length);
            itemDef.colorReplace = new int[itemDef.colorFind.length];
            Arrays.fill(itemDef.colorReplace, 0);
        }
        return itemDef;
    }
    public static ItemDefinition copy(ItemDefinition itemDef, int newId, int copyingItemId, String newName, String...actions) {
        ItemDefinition copyItemDef = lookup(copyingItemId);
        itemDef.id = newId;
        itemDef.name = newName;
        itemDef.colorFind = copyItemDef.colorFind;
        itemDef.colorReplace = copyItemDef.colorReplace;
        itemDef.modelId = copyItemDef.modelId;
        itemDef.maleModel0 = copyItemDef.maleModel0;
        itemDef.femaleModel0 = copyItemDef.femaleModel0;
        itemDef.zoom2d = copyItemDef.zoom2d;
        itemDef.xan2d = copyItemDef.xan2d;
        itemDef.yan2d = copyItemDef.yan2d;
        itemDef.xOffset2d = copyItemDef.xOffset2d;
        itemDef.yOffset2d = copyItemDef.yOffset2d;
        itemDef.interfaceOptions = copyItemDef.interfaceOptions;
        itemDef.interfaceOptions = new String[5];
        if (actions != null) {
            for (int index = 0; index < actions.length; index++) {
                itemDef.interfaceOptions[index] = actions[index];
            }
        }
        return itemDef;
    }


    private static void customItems(int itemId) {
        ItemDefinition itemDef = lookup(itemId);

//        if (itemId == 27363) {
//            System.out.println(itemDef.femaleModel0);
//       }

        switch (itemId) {
            case 6798:
                itemDef.name = "Damage Reduction Scroll(10min)";
                itemDef.stackable = true;
                break;
            case 6799:
                itemDef.name = "Damage Reduction Scroll(30min)";
                itemDef.stackable = true;
                break;
            case 6800:
                itemDef.name = "Damage Reduction Scroll(60min)";
                itemDef.stackable = true;
                break;
            case 6801:
                itemDef.name = "Slayer task damage(10min)";
                itemDef.stackable = true;
                break;
            case 6802:
                itemDef.name = "Slayer task damage(30min)";
                itemDef.stackable = true;
                break;
            case 6803:
                itemDef.name = "Slayer task damage(60min)";
                itemDef.stackable = true;
                break;
            case 6804:
                itemDef.name = "Double harvest(10min)";
                itemDef.stackable = true;
                break;
            case 28693:
                itemDef.name = "Turmoil Unlock Scroll";
                itemDef.stackable = true;
                break;
            case 28696:
                itemDef.name = "SoulSplit Unlock Scroll";
                itemDef.stackable = true;
                break;
            case 6806:
                itemDef.name = "Double harvest(30min)";
                itemDef.stackable = true;
                break;
            case 6808:
                itemDef.name = "Double harvest(60min)";
                itemDef.stackable = false;
                break;
            case 26551:
                itemDef.name = "Turmoil's Grimoire";
                itemDef.interfaceOptions = new String[] { null, "Wield", "Read", null, "Drop" };
                break;
            case 8232:
                itemDef.setDefaults();
                itemDef.name = "@red@lil' Nyx";
                //itemDef.description = "The most powerful pet, see ::foepets for full list of perks.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.modelId = 60050;
                itemDef.zoom2d = 4280;
                itemDef.xan2d = 270;
                itemDef.yan2d = 322;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -1;
                itemDef.yOffset2d = 83;
                break;
            case 8230:
                itemDef.setDefaults();
                itemDef.name = "@gre@lil' Flying pumpkin";
                //itemDef.description = "The most powerful pet, see ::foepets for full list of perks.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.modelId = 49469;
                itemDef.zoom2d = 5980;
                itemDef.xan2d = 96;
                itemDef.yan2d = 119;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 6;
                itemDef.yOffset2d = -48;
                break;
            case 8231:
                itemDef.setDefaults();
                itemDef.name = "@gre@lil' Jack-O-Kraken";
                //itemDef.description = "The most powerful pet, see ::foepets for full list of perks.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.modelId = 60049;
                itemDef.zoom2d = 7480;
                itemDef.xan2d = 200;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -322;
                break;
            case 13372:
                itemDef.name = "Turmoil Gloves";
                break;
            case 24364:
                itemDef.name = "Island Scroll (15min)";
                itemDef.stackable = false;
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, null};
                break;
            case 24365:
                itemDef.name = "Island Scroll (30min)";
                itemDef.stackable = false;
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, null};
                break;
            case 24366:
                itemDef.name = "Island Scroll (60min)";
                itemDef.stackable = false;
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, null};
                break;
            case 20754:
                itemDef.stackable = false;
                break;

            case 26545:
                itemDef.name = "Hope (Random Perks)";
                break;
            case 26546:
                itemDef.name = "(Skilling) Fragment";
                break;
            case 26547:
                itemDef.name = "(Combat) Fragment";
                break;
            case 26548:
                itemDef.name = "(Misc) Fragment";
                break;


            case 26575:
                itemDef.name = "Key Of Araphel.";
                break;
            case 21726:
            case 21728:
                itemDef.stackable = true;
                break;
            case 12863:
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, null};
                break;
            case 13092: //this makes crystal halberds wieldable, weird af.
            case 13093:
            case 13094:
            case 13095:
            case 13096:
            case 13097:
            case 13098:
            case 13099:
            case 13100:
            case 13101:
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, null};
                break;
            case 23933:
                itemDef.name = "Vote crystal";
                break;
            case 9698:
                itemDef.name = "Unfired burning rune";
                //itemDef.description = "I should burn this.";
                itemDef.createCustomSprite("Unfired_burning_rune.png");
                break;
            case 9699:
                itemDef.name = "Burning rune";
                //itemDef.description = "Hot to the touch.";
                itemDef.createCustomSprite("Burning_rune.png");
                break;
            case 23778:
                itemDef.name = "Uncut toxic gem";
                //itemDef.description = "I should use a chisel on this.";
                break;
            case 22374:
                itemDef.name = "Hespori key";
                //itemDef.description = "Can be used on the Hespori chest.";
                break;
            case 23783:
                itemDef.name = "Toxic gem";
                //itemDef.description = "I should be careful with this.";
                break;
            case 9017:
                itemDef.name = "Hespori essence";
                //itemDef.description = "Maybe I should burn this.";
                itemDef.interfaceOptions = new String[] {  null, null, null, null, "Drop" };
                break;
            case 19473:
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                break;
            case 10556:
            case 10557:
            case 10558:
            case 10559:
                itemDef.interfaceOptions = new String[] { null, "Wear", "Feature", null, "Drop" };
                break;
            case 21898:
            case 28902:
                itemDef.interfaceOptions = new String[] { null, "Wear", "Teleports", "Features", null };
                break;
            case 27363:
                itemDef.name = "Masori assembler max cape";
                itemDef.maleModel0 = 46501;
                itemDef.femaleModel0 = 46506;
                itemDef.interfaceOptions = new String[] { null, "Wear", "Teleports", "Features", null };
                break;
            case 12873:
            case 12875:
            case 12877:
            case 12879:
            case 12881:
            case 12883:
            case 13024:
            case 13026:
            case 23113:
            case 23116:
            case 23110:
            case 12871:
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                break;
            case 7774:
                itemDef.name = "@yel@10 Credits";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                break;
            case 7775:
                itemDef.name = "@red@50 Credits";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                break;
            case 7776:
                itemDef.name = "@blu@100 Credits";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                break;
            case 23804:
                itemDef.name = "Imbue Dust";
                break;
            case 22517:
                itemDef.name = "Crystal Shard";
                break;
            case 23951:
                itemDef.name = "Crystalline Key";
                break;
            case 33237:
                itemDef.name = "@yel@1 Upgrade Points Certificate";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.modelId = 60039;
                itemDef.zoom2d = ItemDefinition.lookup(691).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(691).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(691).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(691).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(691).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(691).yOffset2d;
                break;
            case 691:
                itemDef.name = "@gre@10,000 Upgrade Points Certificate";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                break;
            case 692:
                itemDef.name = "@red@25,000 Upgrade Points Certificate";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                break;
            case 693:
                itemDef.name = "@cya@50,000 Upgrade Points Certificate";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                break;
            case 696:
                itemDef.name = "<col=f5c964>250,000 Upgrade Points Certificate";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                break;
            case 23877:
                itemDef.name = "Crystal Shard";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = true;
                break;
            case 23943:
                itemDef.interfaceOptions = new String[] { null, "Wear", "Uncharge", "Check", "Drop" };
                break;
            case 2996:
                itemDef.name = "@red@PKP Ticket";
                break;
            case 23776:
                itemDef.name = "@red@Hunllef's Key";
                break;
            case 13148:
                itemDef.name = "@red@Reset Lamp";
                break;
            case 6792:
                itemDef.name = "@red@Seren's Key";
                break;
            case 4185:
                itemDef.name = "@red@Porazdir's Key";
                break;
            case 21880:
                itemDef.name = "Wrath Rune";
                itemDef.cost = 1930;
                break;
            case 12885:
            case 13277:
            case 19701:
            case 13245:
            case 12007:
            case 22106:
            case 12936:
            case 24495:
                itemDef.interfaceOptions = new String[] { null, null, "Open", null, "Drop" };
                break;
            case 21262:
                itemDef.name = "Vote Genie Pet";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Release" };
                break;
            case 21817:
                itemDef.interfaceOptions = new String[] { null, "Wear", "Dismantle", null, null, };
                break;
            case 21347:
                itemDef.interfaceOptions = new String[] { null, null, null, "Chisel-Options", null, };
                break;
            case 21259:
                itemDef.name = "@red@Name Change Scroll";
                itemDef.interfaceOptions = new String[] { null, null, "Read", null, null, };
                break;
            case 22547:
            case 22552:
            case 22542:
                itemDef.interfaceOptions = new String[] { null, null, null, null, null, };
                break;
            case 22555:
            case 22550:
            case 22545:
                itemDef.interfaceOptions = new String[] { null, "Wield", "Check", "Uncharge", null, };
                break;
            case 732:
                itemDef.name = "@blu@Imbuedeifer";
                itemDef.cost = 1930;
                break;
            case 21881:
                itemDef.name = "Wrath Rune";
                itemDef.cost = 1930;
                break;
            case 13226:
                itemDef.name = "Herb Sack";
                //itemDef.description = "Thats a nice looking sack.";
                break;
/*            case 3456:
                itemDef.name = "@whi@Common Raids Key";
                //itemDef.description = "Can be used on the storage unit.";
                break;
            case 3464:
                itemDef.name = "@pur@Rare Raids Key";
                //itemDef.description = "Can be used on the storage unit.";
                break;*/
/*            case 3468:
                itemDef.name = "@pur@Raids@yel@--@pur@ Key";
                //itemDef.description = "Can be used on the storage unit.";
                break;
            case 25432:
                itemDef.name = "@pur@Raids@yel@++@pur@ Key";
                //itemDef.description = "Can be used on the storage unit.";
                break;*/
            case 10943:
                itemDef.name = "@pur@Donor Vault Token";
                itemDef.interfaceOptions = new String[] { "Tear", null, null, null, "Drop" };
                break;

            case 27463:
            case 27465:
            case 27467:
            case 27469:
            case 27471:
                itemDef.interfaceOptions = new String[] { null, "Wield", "Check", null, "Destroy" };
                itemDef.equipActions = new String[]{"Remove", null, "Check", null, null};
                break;
            case 6829:
                itemDef.name = "@yel@P2P Division pass box";
                itemDef.modelId = 60052;
                itemDef.zoom2d = ItemDefinition.lookup(13346).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(13346).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(13346).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(13346).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(13346).yOffset2d;
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                break;
            case 6831:
                itemDef.name = "@cya@F2P Division pass box";
                itemDef.modelId = 49466;
                itemDef.zoom2d = ItemDefinition.lookup(13346).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(13346).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(13346).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(13346).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(13346).yOffset2d;
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };

                break;
            case 6832:
                itemDef.name = "@red@Owner Giveaway Box";
                //itemDef.description = "Spawns items to giveaway for your youtube stream.";
                itemDef.interfaceOptions = new String[] { "Giveaway", null, null, null, "Drop" };

                break;
            case 6833:
                itemDef.name = "@red@YT Stream Giveaway Box (t2)";
                //itemDef.description = "Spawns items to giveaway for your youtube stream.";
                itemDef.interfaceOptions = new String[] { "Giveaway", null, null, null, "Drop" };
                break;
            case 13190:
                itemDef.name = "@yel@100m OSRS GP";
                itemDef.interfaceOptions = new String[] { "Redeem", null, null, null, "Drop" };
                //itemDef.description = "Redeem for 100m OSRS GP!";
                break;
            case 6121:
                itemDef.name = "Break Vials Instruction";
                //itemDef.description = "How does one break a vial, its impossible?";
                break;
            case 2528:
                itemDef.name = "@red@Experience Lamp";
                //itemDef.description = "Should I rub it......";
                break;
            case 5509:
                itemDef.name = "Small Pouch";
                itemDef.createCustomSprite("Small_pouch.png");
                itemDef.interfaceOptions = new String[] { "Fill", "Empty", "Check", null, null };
                break;
            case 5510:
                itemDef.name = "Medium Pouch";
                itemDef.createCustomSprite("Medium_pouch.png");
                itemDef.interfaceOptions = new String[] { "Fill", "Empty", "Check", null, null };
                break;
            case 5512:
                itemDef.name = "Large Pouch";
                itemDef.createCustomSprite("Large_pouch.png");
                itemDef.interfaceOptions = new String[] { "Fill", "Empty", "Check", null, null };
                break;
            case 10724: //full skeleton
            case 10725:
            case 10726:
            case 10727:
            case 10728:
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 5514:
                itemDef.name = "Giant Pouch";
                itemDef.createCustomSprite("Giant_pouch.png");
                break;
            case 22610: //vesta spear
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 22613: //vesta longsword
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 22504: //stat warhammer
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 4224:
            case 4225:
            case 4226:
            case 4227:
            case 4228:
            case 4229:
            case 4230:
            case 4231:
            case 4232:
            case 4233:
            case 4234:
            case 4235://crystal sheild
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 4212:
            case 4214:
            case 4215:
            case 4216:
            case 4217:
            case 4218:
            case 4219:
            case 4220:
            case 4221:
            case 4222:
            case 4223:
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 2841:
                itemDef.name = "@red@Bonus Exp Scroll";
                itemDef.interfaceOptions = new String[] { "@yel@Activate", null, null, null, "Drop" };
                //itemDef.description = "You will get double experience using this scroll.";
                break;
            case 21791:
            case 21793:
            case 21795:
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 19841:
                itemDef.name = "Master Casket";
                break;
            case 21034:
                itemDef.interfaceOptions = new String[] { "Read", null, null, null, "Drop" };
                break;
            case 6830:
                itemDef.name = "@yel@BETA @blu@BOX";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                break;
            case 21079:
                itemDef.interfaceOptions = new String[] { "Read", null, null, null, "Drop" };
                break;
            case 22093:
                itemDef.name = "@gre@Vote Streak Key";
                //itemDef.description = "Thanks for voting!";
                break;
            case 22885:
                itemDef.name = "@gre@Kronos seed";
                //itemDef.description = "Provides whole server with bonus xp for 1 skill for 5 hours!";
                break;
            case 23824:
                itemDef.name = "Slaughter charge";
                //itemDef.description = "Can be used on bracelet of slaughter to charge it.";
                break;
            case 22883:
                itemDef.name = "@gre@Iasor seed";
                //itemDef.description = "Increased drop rate (+10%) for whole server for 5 hours!";
                break;
            case 22881:
                itemDef.name = "@gre@Attas seed";
                //itemDef.description = "Provides the whole server with bonus xp for 5 hours!";
                break;
            case 20906:
                itemDef.name = "@gre@Golpar seed";
                //itemDef.description = "Provides whole server with double c keys, resource boxes, coin bags, and clues!";
                break;
            case 6112:
                itemDef.name = "@gre@Kelda seed";
                //itemDef.description = "Provides whole server with x2 Larren's keys for 1 hour!";
                break;
            case 20903:
                itemDef.name = "@gre@Noxifer seed";
                //itemDef.description = "Provides whole server with x2 Slayer points for 1 hour!";
                break;
            case 20909:
                itemDef.name = "@gre@Buchu seed";
                //itemDef.description = "Provides whole server with x2 Boss points for 1 hour!";
                break;
            case 22869:
                itemDef.name = "@gre@Celastrus seed";
                //itemDef.description = "Provides whole server with x2 Brimstone keys for 1 hour!";
                break;
            case 4205:
                itemDef.name = "@gre@Consecration seed";
                //itemDef.description = "Provides the whole server with +5 PC points for 1 hour.";
                itemDef.stackable = true;
                break;
            case 11864:
            case 11865:
            case 19639:
            case 19641:
            case 19643:
            case 19645:
            case 19647:
            case 19649:
            case 24444:
            case 24370:
            case 23075:
            case 23073:
            case 21888:
            case 21890:
            case 21264:
            case 21266:
                itemDef.equipActions[2] = "Log";
                itemDef.equipActions[1] = "Check";
                break;
            case 13136:
                itemDef.equipActions[2] = "Elidinis";
                itemDef.equipActions[1] = "Kalphite Hive";
                break;
            case 2550:
                itemDef.equipActions[2] = "Check";
                break;
            case 19557:
                itemDef.setDefaults();
//                itemDef = ItemDefinition.lookup(19556);
                itemDef.modelId = ItemDefinition.lookup(19556).modelId;
                itemDef.name = "Monkey";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(19556).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(19556).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(19556).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(19556).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(19556).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(19556).yOffset2d;
                break;
            case 19942:
                itemDef.setDefaults();
//                itemDef = ItemDefinition.lookup(23184);
                itemDef.modelId = ItemDefinition.lookup(23184).modelId;
                itemDef.name = "Lil Mimic";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(23184).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(23184).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(23184).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(23184).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(23184).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(23184).yOffset2d;
                break;
            case 1712:
            case 1710:
            case 1708:
            case 1706:
            case 19707:
                itemDef.equipActions[1] = "Edgeville";
                itemDef.equipActions[2] = "Karamja";
                itemDef.equipActions[3] = "Draynor";
                itemDef.equipActions[4] = "Al-Kharid";
                break;
            case 21816:
                itemDef.interfaceOptions = new String[] { null, "Wear", "Uncharge", null, "Drop" };
                itemDef.equipActions[1] = "Check";
                itemDef.equipActions[2] = "Toggle-absorption";
                break;
            case 2552:
            case 2554:
            case 2556:
            case 2558:
            case 2560:
            case 2562:
            case 2564:
            case 2566: // Ring of duelling
                itemDef.equipActions[2] = "Shantay Pass";
                itemDef.equipActions[1] = "Clan wars";
                break;
            case 6680:
                itemDef.modelId = 60026;
                itemDef.name = "Mini Arbograve Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = 1480;
                itemDef.xan2d = 172;
                itemDef.yan2d = 160;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -14;
                break;
            case 12579:
                itemDef.modelId = 60026;
                itemDef.name = "Arbograve Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = (ItemDefinition.lookup(6199).zoom2d);
                itemDef.xan2d = ItemDefinition.lookup(6199).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(6199).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(6199).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(6199).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(6199).yOffset2d;
                break;
            case 12582:
                itemDef.modelId = 60027;
                itemDef.name = "Chambers Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = (ItemDefinition.lookup(6199).zoom2d);
                itemDef.xan2d = ItemDefinition.lookup(6199).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(6199).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(6199).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(6199).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(6199).yOffset2d;
                break;
            case 12585:
                itemDef.modelId = 60027;
                itemDef.name = "Mini Chambers Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = 1480;
                itemDef.xan2d = 172;
                itemDef.yan2d = 160;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -14;
                break;
            case 12588:
                itemDef.modelId = 60028;
                itemDef.name = "Donation Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = (ItemDefinition.lookup(6199).zoom2d);
                itemDef.xan2d = ItemDefinition.lookup(6199).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(6199).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(6199).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(6199).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(6199).yOffset2d;
                break;
            case 19887:
                itemDef.modelId = 60028;
                itemDef.name = "Mini Donation Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = 1480;
                itemDef.xan2d = 172;
                itemDef.yan2d = 160;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -14;
                break;
            case 19891:
                itemDef.modelId = 60029;
                itemDef.name = "Theatre Of Blood Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = (ItemDefinition.lookup(6199).zoom2d);
                itemDef.xan2d = ItemDefinition.lookup(6199).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(6199).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(6199).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(6199).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(6199).yOffset2d;
                break;
            case 19895:
                itemDef.modelId = 60029;
                itemDef.name = "Mini Theatre Of Blood Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = 1480;
                itemDef.xan2d = 172;
                itemDef.yan2d = 160;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -14;
                break;
            case 19897:
                itemDef.modelId = 60030;
                itemDef.name = " Cosmetic Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = (ItemDefinition.lookup(6199).zoom2d);
                itemDef.xan2d = ItemDefinition.lookup(6199).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(6199).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(6199).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(6199).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(6199).yOffset2d;
                break;
            case 11739:
                itemDef.modelId = 60031;
                itemDef.name = "@gre@Vote Mystery Box";
                //itemDef.description = "Probably contains cosmetics, or maybe not...";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                itemDef.zoom2d = (ItemDefinition.lookup(6199).zoom2d);
                itemDef.xan2d = ItemDefinition.lookup(6199).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(6199).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(6199).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(6199).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(6199).yOffset2d;
                break;
            case 6828:
                itemDef.modelId = 60032;
                itemDef.name = "Super Mystery Box";
                //itemDef.description = "Mystery box that contains goodies.";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = (ItemDefinition.lookup(6199).zoom2d);
                itemDef.xan2d = ItemDefinition.lookup(6199).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(6199).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(6199).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(6199).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(6199).yOffset2d;
                itemDef.stackable = false;
                break;
            case 6677:
                itemDef.modelId = 60032;
                itemDef.name = "Mini Super Mystery Box";
                //itemDef.description = "Mystery box that contains goodies.";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = 1480;
                itemDef.xan2d = 172;
                itemDef.yan2d = 160;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -14;
                itemDef.stackable = false;
                break;
            case 6199:
                itemDef.name = "Mystery Box";
                //itemDef.description = "Mystery box that contains goodies.";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                break;
            case 6679:
                itemDef.modelId = ItemDefinition.lookup(6199).modelId;
                itemDef.colorFind = ItemDefinition.lookup(6199).colorFind;
                itemDef.colorReplace = ItemDefinition.lookup(6199).colorReplace;
                itemDef.name = "Mini Mystery Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = 1480;
                itemDef.xan2d = 172;
                itemDef.yan2d = 160;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -14;
                break;
            case 12789:
                itemDef.name = "@red@Youtube Mystery Box";
                //itemDef.description = "Mystery box that contains goodies.";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                break;
            case 13346:
                itemDef.name = "Ultra Mystery Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                break;
            case 6678:
                itemDef.modelId = ItemDefinition.lookup(13346).modelId;
                itemDef.colorFind = ItemDefinition.lookup(13346).colorFind;
                itemDef.colorReplace = ItemDefinition.lookup(13346).colorReplace;
                itemDef.name = "Mini Ultra Mystery Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.zoom2d = 1480;
                itemDef.xan2d = 172;
                itemDef.yan2d = 160;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -14;
                break;
            case 8167:
                itemDef.name = "@or2@Nomad Mystery Chest @red@(locked)";
                itemDef.interfaceOptions = new String[] { "Unlock", null, null, "Quick-Open", "Drop" };
                break;
            case 13438:
                itemDef.name = "Slayer Mystery Chest";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                break;
            case 30010:
                itemDef.setDefaults();
                itemDef.name = "Postie Pete";
                //itemDef.description = "50% chance to pick up crystal keys that drop.";
                itemDef.createCustomSprite("Postie_Pete.png");
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 30011:
                itemDef.setDefaults();
                itemDef.name = "Imp";
                //itemDef.description = "50% chance to pick up clue scrolls that drop.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Imp.png");
                break;
            case 30012:
                itemDef.setDefaults();
                itemDef.name = "Toucan";
                //itemDef.description = "50% chance to pick up resource packs.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Toucan.png");
                break;
            case 30013:
                itemDef.setDefaults();
                itemDef.name = "Penguin King";
                //itemDef.description = "50% chance to auto-pick up coin bags.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Penguin_King.png");
                break;
            case 30014:
                itemDef.setDefaults();
                itemDef.name = "K'klik";
                //itemDef.description = "An extra 5% in drop rate boost.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("K'klik.png");
                break;
            case 30015:
                itemDef.setDefaults();
                itemDef.name = "Shadow warrior";
                //itemDef.description = "50% chance for an additional +10% strength bonus in pvm.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Shadow_warrior.png");
                break;
            case 30016:
                itemDef.setDefaults();
                itemDef.name = "Shadow archer";
                //itemDef.description = "50% chance for an additional +10% range str bonus in PvM.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Shadow_archer.png");
                break;
            case 30017:
                itemDef.setDefaults();
                itemDef.name = "Shadow wizard";
                //itemDef.description = "50% chance for an additional +10% mage str bonus in PvM.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Shadow_wizard.png");
                break;
            case 30018:
                itemDef.setDefaults();
                itemDef.name = "Healer Death Spawn";
                //itemDef.description = "5% chance hit restores HP.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Healer_Death_Spawn.png");
                break;
            case 30019:
                itemDef.setDefaults();
                itemDef.name = "Holy Death Spawn";
                //itemDef.description = "5% chance 1/2 of your hit is restored into prayer.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Holy_Death_Spawn.png");
                break;
            case 30020:
                itemDef.setDefaults();
                itemDef.name = "Corrupt beast";
                //itemDef.description = "50% chance for an additional +10% strength bonus for melee, mage, and range in pvm.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Corrupt_beast.png");
                break;
            case 30021:
                itemDef.setDefaults();
                itemDef.name = "Roc";
                //itemDef.description = "An extra 10% in drop rate boost.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Roc.png");
                break;
            case 30022:
                itemDef.setDefaults();
                itemDef.name = "@red@Kratos";
                //itemDef.description = "The most powerful pet, see ::foepets for full list of perks.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Yama.png");
                break;
            case 30023:
                itemDef.setDefaults();
                itemDef.name = "Rain cloud";
                //itemDef.description = "Don't worry be happy.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Rain_cloud.png");
                break;
            case 8866:
                itemDef.name = "Storage chest key (UIM)";
                //itemDef.description = "Used to open the UIM storage chest 1 time.";
                itemDef.stackable = true;
                break;
            case 8868:
                itemDef.name = "Perm. storage chest key (UIM)";
                //itemDef.description = "Permanently unlocks UIM storage chest.";
                break;
            case 771:
                itemDef.name = "@cya@Ancient branch";
                //itemDef.description = "Burning items in the FoE with this branch provides a 1 time +10% FoE value increase.";
                break;
            case 2399:
                itemDef.name = "@or2@Nomad Mystery Key";
                //itemDef.description = "Used to unlock the Nomad Mystery Chest.";
                break;
            case 10832:
                itemDef.name = "Small coin bag";
                itemDef.interfaceOptions = new String[] { "Open", null, "Open-All", null, "Drop" };
                //itemDef.description = "I can see some coins inside.";
                break;
            case 10833:
                itemDef.name = "Medium coin bag";
                itemDef.interfaceOptions = new String[] { "Open", null, "Open-All", null, "Drop" };
                //itemDef.description = "I can see some coins inside.";
                break;
            case 10834:
                itemDef.name = "Large coin bag";
                itemDef.interfaceOptions = new String[] { "Open", null, "Open-All", null, "Drop" };
                //itemDef.description = "I can see some coins inside.";
                break;
            case 22316:
                itemDef.name = "Sword of Turmoil";
                //itemDef.description = "The Sword of Turmoil.";
                break;
            case 30110:
                itemDef.setDefaults();
                itemDef.name = "Dark postie pete";
                //itemDef.description = "Picks up all crystal keys and 25% chance to double.";
                itemDef.createCustomSprite("dark_Postie_Pete.png");
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 30111:
                itemDef.setDefaults();
                itemDef.name = "Dark imp";
                //itemDef.description = "Picks up all clue scrolls and 25% chance to double.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_Imp.png");
                break;
            case 30112:
                itemDef.setDefaults();
                itemDef.name = "Dark toucan";
                //itemDef.description = "Picks up all resource boxes and 25% chance to double.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_Toucan.png");
                break;
            case 30113:
                itemDef.setDefaults();
                itemDef.name = "Dark penguin King";
                //itemDef.description = "Picks up all coin bags and 25% chance to double.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_Penguin_King.png");
                break;
            case 30114:
                itemDef.setDefaults();
                itemDef.name = "Dark k'klik";
                //itemDef.description = "An extra 10% in drop rate boost.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_K'klik.png");
                break;
            case 30115:
                itemDef.setDefaults();
                itemDef.name = "Dark shadow warrior";
                //itemDef.description = "Gives constant +10% strength bonus in pvm.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_Shadow_warrior.png");
                break;
            case 30116:
                itemDef.setDefaults();
                itemDef.name = "Dark shadow archer";
                //itemDef.description = "Gives constant +10% range str bonus in PvM.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_Shadow_archer.png");
                break;
            case 30117:
                itemDef.setDefaults();
                itemDef.name = "Dark shadow wizard";
                //itemDef.description = "Gives constant +10% mage str bonus in PvM.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_Shadow_wizard.png");
                break;
            case 30118:
                itemDef.setDefaults();
                itemDef.name = "Dark healer death spawn";
                //itemDef.description = "10% chance hit restores HP.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_Healer_Death_Spawn.png");
                break;
            case 30119:
                itemDef.setDefaults();
                itemDef.name = "Dark holy death spawn";
                //itemDef.description = "10% chance 1/2 of your hit is restored into prayer.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_Holy_Death_Spawn.png");
                break;
            case 30120:
                itemDef.setDefaults();
                itemDef.name = "Dark corrupt beast";
                //itemDef.description = "Extra 10% in drop rate and constant +10% strength bonus for all styles in pvm.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_Corrupt_beast.png");
                break;
            case 30170: //
                itemDef.setDefaults();
                itemDef.id = 30170; //
                itemDef.modelId = 61000; //
                itemDef.maleModel0 = 61000; //
                itemDef.femaleModel0 = 61000; //
                itemDef.name = "MiniGun";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30172: //
                itemDef.setDefaults();
                itemDef.id = 30172; //
                itemDef.modelId = 61002; //
                itemDef.maleModel0 = 61002; //
                itemDef.femaleModel0 = 61002; //
                itemDef.name = "Darkness Platebody (G)";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30174: //
                itemDef.setDefaults();
                itemDef.id = 30174; //
                itemDef.modelId = 61004; //
                itemDef.maleModel0 = 61004; //
                itemDef.femaleModel0 = 61004; //
                itemDef.name = "Darkness Helmet (G)";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30176: //
                itemDef.setDefaults();
                itemDef.id = 30176; //
                itemDef.modelId = 61006; //
                itemDef.maleModel0 = 61006; //
                itemDef.femaleModel0 = 61006; //
                itemDef.name = "Darkness Platelegs (G)";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30180: //
                itemDef.setDefaults();
                itemDef.id = 30180; //
                itemDef.modelId = 61008; //
                itemDef.maleModel0 = 61008; //
                itemDef.femaleModel0 = 61008; //
                itemDef.name = "Cursed Platelegs";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30182: //
                itemDef.setDefaults();
                itemDef.id = 30182; //
                itemDef.modelId = 61010; //
                itemDef.maleModel0 = 61010; //
                itemDef.femaleModel0 = 61010; //
                itemDef.name = "Cursed Platebody";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30184: //
                itemDef.setDefaults();
                itemDef.id = 30184; //
                itemDef.modelId = 61012; //
                itemDef.maleModel0 = 61012; //
                itemDef.femaleModel0 = 61012; //
                itemDef.name = "Cursed Helmet";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30186: //
                itemDef.setDefaults();
                itemDef.id = 30186; //
                itemDef.modelId = 61014; //
                itemDef.maleModel0 = 61014; //
                itemDef.femaleModel0 = 61014; //
                itemDef.name = "Cursed WingedCape";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30188: //
                itemDef.setDefaults();
                itemDef.id = 30188; //
                itemDef.modelId = 27221; //
                itemDef.maleModel0 = 27221; //
                itemDef.femaleModel0 = 27221; //
                itemDef.name = "Shiny Royal Crown";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30190: //
                itemDef.setDefaults();
                itemDef.id = 30190; //
                itemDef.modelId = 53178; //
                itemDef.maleModel0 = 53178; //
                itemDef.femaleModel0 = 53178; //
                itemDef.name = "Crystal Hat";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30192: //
                itemDef.setDefaults();
                itemDef.id = 30192; //
                itemDef.modelId = 53180; //
                itemDef.maleModel0 = 53180; //
                itemDef.femaleModel0 = 53180; //
                itemDef.name = "Crystal Robetop";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30194: //
                itemDef.setDefaults();
                itemDef.id = 30194; //
                itemDef.modelId = 53182; //
                itemDef.maleModel0 = 53182; //
                itemDef.femaleModel0 = 53182; //
                itemDef.name = "Crystal Robebottoms";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30196: //
                itemDef.setDefaults();
                itemDef.id = 30196; //
                itemDef.modelId = 53205; //
                itemDef.maleModel0 = 53205; //
                itemDef.femaleModel0 = 53205; //
                itemDef.name = "Crystal Boots";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30198: //
                itemDef.setDefaults();
                itemDef.id = 30198; //
                itemDef.modelId = 61016; //
                itemDef.maleModel0 = 61016; //
                itemDef.femaleModel0 = 61016; //
                itemDef.name = "The Great Battleaxe";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 33430:
                itemDef.name = "Wraith Sword";
                itemDef.maleModel0 = 60957;
                itemDef.modelId = 33430; //
                itemDef.modelId = 60957; //
                itemDef.femaleModel0 = 60957;
                itemDef.zoom2d = ItemDefinition.lookup(33202).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(33202).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(33202).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(33202).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(33202).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33431:
                itemDef.name = "Wraith Scythe";
                itemDef.maleModel0 = 60964;
                itemDef.femaleModel0 = 60964;
                itemDef.modelId = 60958;
                itemDef.zoom2d = ItemDefinition.lookup(33203).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(33203).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(33203).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(33203).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(33203).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33432:
                itemDef.name = "Wraith Spear";
                itemDef.maleModel0 = 60960;
                itemDef.femaleModel0 = 60960;
                itemDef.modelId = 60959;
                itemDef.zoom2d = ItemDefinition.lookup(33204).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(33204).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(33204).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(33204).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(33204).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33438:
                itemDef.name = "Wraith Mask";
                itemDef.maleModel0 = 60975;
                itemDef.femaleModel0 = 60975;
                itemDef.modelId = 60974;
                itemDef.zoom2d = ItemDefinition.lookup(27235).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(27235).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(27235).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(27235).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(27235).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33439:
                itemDef.name = "Wraith Body";
                itemDef.maleModel0 = 60977;
                itemDef.femaleModel0 = 60977;
                itemDef.modelId = 60976;
                itemDef.zoom2d = ItemDefinition.lookup(27238).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(27238).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(27238).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(27238).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(27238).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33440:
                itemDef.name = "Wraith legs";
                itemDef.maleModel0 = 60979;
                itemDef.femaleModel0 = 60979;
                itemDef.modelId = 60978;
                itemDef.zoom2d = ItemDefinition.lookup(27241).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(27241).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(27241).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(27241).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(27241).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33442:
                itemDef.name = "100% Thrills";
                itemDef.maleModel0 = 60981;
                itemDef.femaleModel0 = 60981;
                itemDef.modelId = 60980;
                itemDef.zoom2d = ItemDefinition.lookup(29796).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(29796).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(29796).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(29796).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(29796).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(29796).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33443:
                itemDef.name = "Wraith Shield";
                itemDef.maleModel0 = 60983;
                itemDef.femaleModel0 = 60983;
                itemDef.modelId = 60982;
                itemDef.zoom2d = ItemDefinition.lookup(12817).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(12817).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(12817).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(12817).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(12817).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(12817).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33444:
                itemDef.name = "Wraith Book";
                itemDef.maleModel0 = 60985;
                itemDef.femaleModel0 = 60985;
                itemDef.modelId = 60984;
                itemDef.zoom2d = ItemDefinition.lookup(26551).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(26551).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(26551).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(26551).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(26551).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(26551).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33445:
                itemDef.name = "Wraith Defender";
                itemDef.maleModel0 = 60987;
                itemDef.femaleModel0 = 60987;
                itemDef.modelId = 60986;
                itemDef.zoom2d = ItemDefinition.lookup(27552).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(27552).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(27552).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(27552).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(27552).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(27552).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33397:
                itemDef.name = "@pur@Pink Ankou Mask";
                itemDef.modelId = 60905;
                itemDef.maleModel0 = 60900;
                itemDef.femaleModel0 = 60900;
                itemDef.zoom2d = ItemDefinition.lookup(20095).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(20095).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(20095).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(20095).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(20095).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33398:
                itemDef.name = "@pur@Pink Ankou Top";
                itemDef.modelId = 60906;
                itemDef.maleModel0 = 60901;
                itemDef.maleModel1 = 60910;
                itemDef.femaleModel0 = 60901;
                itemDef.femaleModel1 = 60910;
                itemDef.zoom2d = ItemDefinition.lookup(20098).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(20098).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(20098).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(20098).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(20098).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33399:
                itemDef.name = "@pur@Pink Ankou Legs";
                itemDef.modelId = 60907;
                itemDef.maleModel0 = 60902;
                itemDef.femaleModel0 = 60902;
                itemDef.zoom2d = ItemDefinition.lookup(20104).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(20104).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(20104).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(20104).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(20104).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;

            case 11505:
                itemDef.name = "Potion of ambition";
                break;
            case 33392:
                itemDef.name = "Freedom Ring";
                itemDef.modelId = 60891;
                itemDef.maleModel0 = 60891;
                itemDef.femaleModel0 = 60891;
                itemDef.zoom2d = ItemDefinition.lookup(28327).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(28327).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(28327).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(28327).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(28327).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 24725:
                itemDef.name = "Hallowed Amulet";
                break;
            case 9068:
                itemDef.name = "Helm of amazement";
                break;
            case 29489:
                itemDef.name = "Prophets Pride";
                break;
            case 28416:
                itemDef.name = "Shadow Crusade Key";
                break;
            case 33394:
                itemDef.name = "Freedom Gloves";
                itemDef.modelId = 60969;
                itemDef.maleModel0 = 60896;
                itemDef.femaleModel0 = 60897;
                itemDef.zoom2d = ItemDefinition.lookup(2912).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(2912).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(2912).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(2912).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(2912).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 28796:
                itemDef.name = "Staff of amazement";
                break;
            case 33393:
                itemDef.name = "Freedom Boots";
                itemDef.modelId = 60970;
                itemDef.maleModel0 = 60893;
                itemDef.femaleModel0 = 60894;
                itemDef.zoom2d = ItemDefinition.lookup(2904).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(2904).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(2904).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(2904).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(2904).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33400:
                itemDef.name = "@pur@Pink Ankou Socks";
                itemDef.modelId = 60908;
                itemDef.maleModel0 = 60903;
                itemDef.femaleModel0 = 60903;
                itemDef.zoom2d = ItemDefinition.lookup(20107).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(20107).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(20107).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(20107).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(20107).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33401:
                itemDef.name = "@pur@Pink Ankou Mask";
                itemDef.modelId = 60909;
                itemDef.maleModel0 = 60904;
                itemDef.femaleModel0 = 60904;
                itemDef.zoom2d = ItemDefinition.lookup(20101).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(20101).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(20101).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(20101).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(20101).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33402:
                itemDef.setDefaults();
                itemDef.name = "Hallowed Gloves";
                itemDef.modelId = 60913;
                itemDef.maleModel0 = 60911;
                itemDef.femaleModel0 = 60911;
                itemDef.zoom2d = ItemDefinition.lookup(22981).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(22981).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(22981).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(22981).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(22981).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33403:
                itemDef.name = "Hallowed Boots";
                itemDef.modelId = 60912;
                itemDef.maleModel0 = 60912;
                itemDef.femaleModel0 = 60912;
                itemDef.zoom2d = ItemDefinition.lookup(13235).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(13235).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(13235).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(13235).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(13235).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33404:
                itemDef.name = "Send Nudes";
                itemDef.modelId = 60915;
                itemDef.maleModel0 = 60914;
                itemDef.femaleModel0 = 60914;
                itemDef.zoom2d = 4180;
                itemDef.xan2d = 235;
                itemDef.yan2d = 43;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 1;
                itemDef.yOffset2d = -71;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33446:
                itemDef.name = "ale of the scotty";
                itemDef.maleModel0 = 60989;
                itemDef.femaleModel0 = 60990;
                itemDef.modelId = 60988;
                itemDef.zoom2d = ItemDefinition.lookup(20056).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(20056).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(20056).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(20056).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(20056).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(20056).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33447:
                itemDef.name = "Xamphur's Lure";
                itemDef.maleModel0 = ItemDefinition.lookup(9627).maleModel0;
                itemDef.femaleModel0 = ItemDefinition.lookup(9627).femaleModel0;
                itemDef.modelId = ItemDefinition.lookup(9627).id;
                itemDef.zoom2d = ItemDefinition.lookup(9627).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9627).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9627).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9627).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9627).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9627).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                break;
            case 26879:
                itemDef.name = "Wraith Essence";
                itemDef.modelId = 60991;
                itemDef.zoom2d = ItemDefinition.lookup(26879).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(26879).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(26879).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(26879).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(26879).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(26879).yOffset2d;
                itemDef.interfaceOptions = new String[] { "Inspect", null, null, null, "Drop" };
                break;
            case 33448:
                itemDef.name = "Skilling Essence";
                itemDef.modelId = 60991;
                itemDef.zoom2d = ItemDefinition.lookup(26879).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(26879).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(26879).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(26879).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(26879).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(26879).yOffset2d;
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, "Drop" };
                itemDef.animateInventory = true;
                break;
            case 33449:
                itemDef.name = "Minotaur Cape";
                itemDef.maleModel0 = 60998;
                itemDef.femaleModel0 = 60998;
                itemDef.modelId = 60997;
                itemDef.zoom2d = ItemDefinition.lookup(24855).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(24855).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(24855).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(24855).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(24855).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(24855).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.animateInventory = true;
                break;
            case 33450:
                itemDef.name = "Minotaur Boots";
                itemDef.maleModel0 = 61000;
                itemDef.femaleModel0 = 61000;
                itemDef.modelId = 60999;
                itemDef.zoom2d = ItemDefinition.lookup(28945).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(28945).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(28945).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(28945).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(28945).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(28945).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.animateInventory = true;
                break;
            case 33451:
                itemDef.name = "Minotaur Gloves";
                itemDef.maleModel0 = 60996;
                itemDef.femaleModel0 = 60996;
                itemDef.modelId = 60995;
                itemDef.zoom2d = ItemDefinition.lookup(7462).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(7462).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(7462).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(7462).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(7462).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(7462).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.animateInventory = true;
                break;
            case 33452:
                itemDef.name = "Minotaur Ring";
                itemDef.maleModel0 = 60994;
                itemDef.femaleModel0 = 60994;
                itemDef.modelId = 60994;
                itemDef.zoom2d = ItemDefinition.lookup(26770).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(26770).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(26770).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(26770).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(26770).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(26770).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.animateInventory = true;
                break;
            case 33453:
                itemDef.name = "Minotaur Amulet";
                itemDef.maleModel0 = 60993;
                itemDef.femaleModel0 = 60993;
                itemDef.modelId = 60992;
                itemDef.zoom2d = ItemDefinition.lookup(13136).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(13136).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(13136).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(13136).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(13136).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(13136).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.animateInventory = true;
                break;
            case 33433:
                itemDef.name = "Wraith Staff";
                itemDef.maleModel0 = 60962;
                itemDef.femaleModel0 = 60962;
                itemDef.modelId = 60961;
                itemDef.zoom2d = ItemDefinition.lookup(33205).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(33205).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(33205).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(33205).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(33205).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33434:
                itemDef.name = "Wraith Bow";
                itemDef.maleModel0 = 60963;
                itemDef.femaleModel0 = 60963;
                itemDef.modelId = 60967;
                itemDef.zoom2d = ItemDefinition.lookup(33207).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(33207).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(33207).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(33207).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(33207).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33435:
                itemDef.name = "Wraith Crossbow";
                itemDef.maleModel0 = 60966;
                itemDef.femaleModel0 = 60966;
                itemDef.modelId = 60965;
                itemDef.zoom2d = ItemDefinition.lookup(26269).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(26269).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(26269).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(26269).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(26269).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30200: //
                itemDef.setDefaults();
                itemDef.id = 30200; //
                itemDef.modelId = 61018; //
                itemDef.maleModel0 = 61018; //
                itemDef.femaleModel0 = 61018; //
                itemDef.name = "Darkness Helmet (Iced)";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30202: //
                itemDef.setDefaults();
                itemDef.id = 30202; //
                itemDef.modelId = 61020; //
                itemDef.maleModel0 = 61020; //
                itemDef.femaleModel0 = 61020; //
                itemDef.name = "Darkness Platebody (Iced)";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30204: //
                itemDef.setDefaults();
                itemDef.id = 30204; //
                itemDef.modelId = 61022; //
                itemDef.maleModel0 = 61022; //
                itemDef.femaleModel0 = 61022; //
                itemDef.name = "Darkness Platelegs (Iced)";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 33406:
                itemDef.name = "Heredit Ring";
                itemDef.modelId = 60917;
                itemDef.maleModel0 = 60917;
                itemDef.femaleModel0 = 60917;
                itemDef.zoom2d = ItemDefinition.lookup(12601).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(12601).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(12601).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(12601).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(12601).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33407:
                itemDef.name = "Heredit Amulet";
                itemDef.modelId = 60919;
                itemDef.maleModel0 = 60920;
                itemDef.femaleModel0 = 60920;
                itemDef.zoom2d = ItemDefinition.lookup(19553).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(19553).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(19553).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(19553).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(19553).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33408:
                itemDef.name = "Heredit Boots";
                itemDef.modelId = 60918;
                itemDef.maleModel0 = 60918;
                itemDef.femaleModel0 = 60918;
                itemDef.zoom2d = ItemDefinition.lookup(22954).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(22954).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(22954).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(22954).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(22954).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33409:
                itemDef.name = "Heredit Gloves";
                itemDef.modelId = 60921;
                itemDef.maleModel0 = 60922;
                itemDef.femaleModel0 = 60922;
                itemDef.zoom2d = ItemDefinition.lookup(22981).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(22981).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(22981).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(22981).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(22981).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33410:
                itemDef.name = "Heredit Cape";
                itemDef.modelId = 60924;
                itemDef.maleModel0 = 60925;
                itemDef.femaleModel0 = 60925;
                itemDef.zoom2d = ItemDefinition.lookup(27447).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(27447).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(27447).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(27447).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(27447).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33411:
                itemDef.name = "Heredit Quiver";
                itemDef.modelId = 60923;
                itemDef.zoom2d = ItemDefinition.lookup(5562).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(5562).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(5562).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(5562).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(5562).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33412:
                itemDef.name = "Orange Party Hat";
                itemDef.modelId = 60926;
                itemDef.maleModel0 = 60927;
                itemDef.femaleModel0 = 60927;
                itemDef.zoom2d = ItemDefinition.lookup(3128).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(3128).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(3128).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(3128).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(3128).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33413:
                itemDef.name = "Element Hood";
                itemDef.modelId = 60928;
                itemDef.maleModel0 = 60929;
                itemDef.femaleModel0 = 60929;
                itemDef.zoom2d = ItemDefinition.lookup(20708).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(20708).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(20708).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(20708).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(20708).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33414:
                itemDef.name = "Element Top";
                itemDef.modelId = 60930;
                itemDef.maleModel0 = 60931;
                itemDef.femaleModel0 = 60931;
                itemDef.zoom2d = ItemDefinition.lookup(20704).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(20704).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(20704).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(20704).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(20704).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33415:
                itemDef.name = "Element Bottoms";
                itemDef.modelId = 60932;
                itemDef.maleModel0 = 60933;
                itemDef.femaleModel0 = 60933;
                itemDef.zoom2d = ItemDefinition.lookup(20706).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(20706).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(20706).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(20706).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(20706).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 33416:
                itemDef.name = "Element Boots";
                itemDef.modelId = 60934;
                itemDef.maleModel0 = 60934;
                itemDef.femaleModel0 = 60934;
                itemDef.zoom2d = ItemDefinition.lookup(20710).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(20710).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(20710).yan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(20710).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(20710).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 30206: //
                itemDef.setDefaults();
                itemDef.id = 30206; //
                itemDef.modelId = 61024; //
                itemDef.maleModel0 = 61024; //
                itemDef.femaleModel0 = 61024; //
                itemDef.name = "Iced MiniGun";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30208: //
                itemDef.setDefaults();
                itemDef.id = 30208; //
                itemDef.modelId = 61026; //
                itemDef.maleModel0 = 61026; //
                itemDef.femaleModel0 = 61026; //
                itemDef.name = "Master Katana";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30210: //
                itemDef.setDefaults();
                itemDef.id = 30210; //
                itemDef.modelId = 61028; //
                itemDef.maleModel0 = 61028; //
                itemDef.femaleModel0 = 61028; //
                itemDef.name = "Demon Helmet";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30212: //
                itemDef.setDefaults();
                itemDef.id = 30212; //
                itemDef.modelId = 61030; //
                itemDef.maleModel0 = 61030; //
                itemDef.femaleModel0 = 61030; //
                itemDef.name = "Demon Platebody";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30214: //
                itemDef.setDefaults();
                itemDef.id = 30214; //
                itemDef.modelId = 61032; //
                itemDef.maleModel0 = 61032; //
                itemDef.femaleModel0 = 61032; //
                itemDef.name = "Demon Platelegs";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30216: //
                itemDef.setDefaults();
                itemDef.id = 30216; //
                itemDef.modelId = 61034; //
                itemDef.maleModel0 = 61034; //
                itemDef.femaleModel0 = 61034; //
                itemDef.name = "Demon Gloves";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30218: //
                itemDef.setDefaults();
                itemDef.id = 30218; //
                itemDef.modelId = 61036; //
                itemDef.maleModel0 = 61036; //
                itemDef.femaleModel0 = 61036; //
                itemDef.name = "Darkness Helmet (Corrupted)";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30220: //
                itemDef.setDefaults();
                itemDef.id = 30220; //
                itemDef.modelId = 61038; //
                itemDef.maleModel0 = 61038; //
                itemDef.femaleModel0 = 61038; //
                itemDef.name = "Darkness Platebody (Corrupted)";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30222: //
                itemDef.setDefaults();
                itemDef.id = 30222; //
                itemDef.modelId = 61040; //
                itemDef.maleModel0 = 61040; //
                itemDef.femaleModel0 = 61040; //
                itemDef.name = "Darkness Platelegs (Corrupted)";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30224: //
                itemDef.setDefaults();
                itemDef.id = 30224; //
                itemDef.modelId = 61042; //
                itemDef.maleModel0 = 61042; //
                itemDef.femaleModel0 = 61042; //
                itemDef.name = "Crystal Katana";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30226: //
                itemDef.setDefaults();
                itemDef.id = 30226; //
                itemDef.modelId = 61044; //
                itemDef.maleModel0 = 61044; //
                itemDef.femaleModel0 = 61044; //
                itemDef.name = "Crystal Dagger";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30228: //
                itemDef.setDefaults();
                itemDef.id = 30228; //
                itemDef.modelId = 61046; //
                itemDef.maleModel0 = 61046; //
                itemDef.femaleModel0 = 61046; //
                itemDef.name = "Crystal Longbow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30230: //
                itemDef.setDefaults();
                itemDef.id = 30230; //
                itemDef.modelId = 61048; //
                itemDef.maleModel0 = 61048; //
                itemDef.femaleModel0 = 61048; //
                itemDef.name = "DragonSlayer Cape";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30232: //
                itemDef.setDefaults();
                itemDef.id = 30232; //
                itemDef.modelId = 61050; //
                itemDef.maleModel0 = 61050; //
                itemDef.femaleModel0 = 61050; //
                itemDef.name = "LightBearer Mask";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30234: //
                itemDef.setDefaults();
                itemDef.id = 30234; //
                itemDef.modelId = 61052; //
                itemDef.maleModel0 = 61052; //
                itemDef.femaleModel0 = 61052; //
                itemDef.name = "LightBearer plated bottoms";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30236: //
                itemDef.setDefaults();
                itemDef.id = 30236; //
                itemDef.modelId = 61054; //
                itemDef.maleModel0 = 61054; //
                itemDef.femaleModel0 = 61054; //
                itemDef.name = "LightBearer Plated Robetop";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30238: //
                itemDef.setDefaults();
                itemDef.id = 30238; //
                itemDef.modelId = 61056; //
                itemDef.maleModel0 = 61056; //
                itemDef.femaleModel0 = 61056; //
                itemDef.name = "The Sword of Light";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30240: //
                itemDef.setDefaults();
                itemDef.id = 30240; //
                itemDef.modelId = 61058; //
                itemDef.maleModel0 = 61058; //
                itemDef.femaleModel0 = 61058; //
                itemDef.name = "Cryo Shadow Chestplate";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30242: //
                itemDef.setDefaults();
                itemDef.id = 30242; //
                itemDef.modelId = 61060; //
                itemDef.maleModel0 = 61060; //
                itemDef.femaleModel0 = 61060; //
                itemDef.name = "Cryo Shadow RangerHelm";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30244: //
                itemDef.setDefaults();
                itemDef.id = 30244; //
                itemDef.modelId = 61062; //
                itemDef.maleModel0 = 61062; //
                itemDef.femaleModel0 = 61062; //
                itemDef.name = "Cryo Shadow Chaps";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30246: //
                itemDef.setDefaults();
                itemDef.id = 30246; //
                itemDef.modelId = 61064; //
                itemDef.maleModel0 = 61064; //
                itemDef.femaleModel0 = 61064; //
                itemDef.name = "Thor's Hammer";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30250: //
                itemDef.setDefaults();
                int model = 15002;
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.name = "Fox's Helm";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30252: //
                itemDef.setDefaults();
                model = 15004;
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.name = "Fox's Chest";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30254: //
                itemDef.setDefaults();
                model = 15005;
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.name = "Fox's Legs";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30256: //
                itemDef.setDefaults();
                model = 15011;
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.name = "Ak47";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30258: //
                itemDef.setDefaults();
                model = 15098;
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.name = "Colossal Blade";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30260: //
                itemDef.setDefaults();
                model = 15351;
                itemDef.name = "Dual Reapers";
                itemDef.modelId = 15352; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30262: //
                itemDef.setDefaults();
                model = 19550;
                itemDef.name = "Giant Shuriken";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model;
                itemDef.femaleModel0 = model;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30264: //
                itemDef.setDefaults();
                model = 19550;
                itemDef.name = "Giant Shuriken";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30266: //
                itemDef.setDefaults();
                model = 36604;
                itemDef.name = "Bear Glaives";
                itemDef.modelId = 36603; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30268: //
                itemDef.setDefaults();
                model = 36601;
                itemDef.name = "Toxic Dragonfire Blowpipe";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", "Check", "Unload", "Uncharge" };
                break;
            case 28688: //
                itemDef.setDefaults();
                model = 36601;
                itemDef.name = "Toxic Dragonfire Blowpipe";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.zoom2d = 1480;
                itemDef.xan2d = 374;
                itemDef.yan2d = 235;
                itemDef.zan2d = 130;
                itemDef.xOffset2d = 28;
                itemDef.yOffset2d = -138;
                itemDef.interfaceOptions = new String[] { null, "Wield", "Check", "Unload", "Uncharge" };
                break;
            case 30272: //
                itemDef.setDefaults();
                model = 48364;
                itemDef.name = "Sword of Mages";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30274: //
                itemDef.setDefaults();
                model = 48395;
                itemDef.name = "Enraged Beast Shield";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30276: //
                itemDef.setDefaults();
                model = 48465;
                itemDef.name = "Handcannon";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30278: //
                itemDef.setDefaults();
                model = 48465;
                itemDef.name = "Handcannon";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30280: //
                itemDef.setDefaults();
                model = 50223;
                itemDef.name = "Demonic Sword";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30282: //
                itemDef.setDefaults();
                model = 5225;
                itemDef.name = "Shark Fists";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30284: //
                itemDef.setDefaults();
                model = 53063;
                itemDef.name = "Dark Vorkath Helm";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30286: //
                itemDef.setDefaults();
                model = 53065;
                itemDef.name = "Dark Vorkath Chest";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30288: //
                itemDef.setDefaults();
                model = 53067;
                itemDef.name = "Dark Vorkath Legs";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30290: //
                itemDef.setDefaults();
                model = 53070;
                itemDef.name = "Dark Vorkath Boots";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30312: //
                itemDef.setDefaults();
                model = 53088;
                itemDef.name = "Stealth Helm";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30292: //
                itemDef.setDefaults();
                model = 53090;
                itemDef.name = "Stealth Body";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 11489:
                itemDef.name = "AIO Overload Potion";
            //itemDef.description = "A Magical Overload Potion.";
            itemDef.interfaceOptions = new String[] { "Consume", null, null, null, null };
            break;
            case 30296: //
                itemDef.setDefaults();
                model = 53092;
                itemDef.name = "Stealth Legs";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 84: //
                itemDef.setDefaults();
                model = 53096;
                itemDef.name = "Staff of Frostmournes";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30300: //
                itemDef.setDefaults();
                model = 53129;
                itemDef.name = "Solak's Hand";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30302: //
                itemDef.setDefaults();
                model = 58002;
                itemDef.name = "Scythe of Vitur (Glowing)";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30304: //
                itemDef.setDefaults();
                model = 59888;
                itemDef.name = "Master Scythe";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30306: //
                itemDef.setDefaults();
                model = 62774;
                itemDef.name = "Heredit Shield";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 28534: //
                itemDef.setDefaults();
                model = 62936;
                itemDef.name = "Mech Claws";
                itemDef.modelId = 62937; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30310: //
                itemDef.setDefaults();
                model = 62774;
                itemDef.name = "Heredit Shield";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30314: //
                itemDef.setDefaults();
                model = 60400;
                itemDef.name = "Bong";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30316: //
                itemDef.setDefaults();
                model = 60401;
                itemDef.name = "Stealth Judgement Legs";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30318: //
                itemDef.setDefaults();
                model = 60402;
                itemDef.name = "Stealth Judgement Mask";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30320: //
                itemDef.setDefaults();
                model = 60403;
                itemDef.name = "Stealth Judgement Chest";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30322: //
                itemDef.setDefaults();
                model = 60404;
                itemDef.name = "Auto-Assasin Helm";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30324: //
                itemDef.setDefaults();
                model = 60405;
                itemDef.name = "Auto-Assasin Chest";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30326: //
                itemDef.setDefaults();
                model = 60406;
                itemDef.name = "Auto-Assasin Legs";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30328: //
                itemDef.setDefaults();
                model = 60407;
                itemDef.name = "Shadow P-hat";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30330: //
                itemDef.setDefaults();
                model = 60408;
                itemDef.name = "Arrav Mask";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30332: //
                itemDef.setDefaults();
                model = 60409;
                itemDef.name = "Arrav Chest";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30334: //
                itemDef.setDefaults();
                model = 60410;
                itemDef.name = "Arrav Legs";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30336: //
                itemDef.setDefaults();
                model = 60411;
                itemDef.name = "Arrav Spear";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30338: //
                itemDef.setDefaults();
                model = 60412;
                itemDef.name = "Arrav's Light";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30340: //
                itemDef.setDefaults();
                model = 60413;
                itemDef.name = "Arrav's Bow";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30342: //
                itemDef.setDefaults();
                model = 60414;
                itemDef.name = "Corrupted Guthix's Mask";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30344: //
                itemDef.setDefaults();
                model = 60415;
                itemDef.name = "Corrupted Guthix's Body";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30346: //
                itemDef.setDefaults();
                model = 60416;
                itemDef.name = "Corrupted Guthix's Legs";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30348: //
                itemDef.setDefaults();
                model = 60417;
                itemDef.name = "Corrupted Guthix's Staff";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30350: // 60418 mod marks sword
                itemDef.setDefaults();
                itemDef.name = "Martial God's Sword";
                itemDef.maleModel0 = 60981;
                itemDef.femaleModel0 = 60981;
                itemDef.modelId = 60980;
                itemDef.zoom2d = ItemDefinition.lookup(29796).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(29796).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(29796).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(29796).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(29796).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(29796).yOffset2d;
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30352: //
                itemDef.setDefaults();
                model = 60419;
                itemDef.name = "Dragon-Tipped Spear";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30354: //
                itemDef.setDefaults();
                model = 60420;
                itemDef.name = "Dark Vorkath Bow";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30356: //cape
                itemDef.setDefaults();
                model = 60421;
                itemDef.name = "Pet Dragon";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30358: //mask
                itemDef.setDefaults();
                model = 10031;
                itemDef.name = "Bag";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30370: //
                itemDef.setDefaults();
                model = 12316;
                itemDef.name = "Bow of Dragon Slaying";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30362: //
                itemDef.setDefaults();
                model = 13544;
                itemDef.name = "Zamorak's Blades";
                itemDef.modelId = model; //
                itemDef.maleModel0 = model; //
                itemDef.femaleModel0 = model; //
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 30121:
                itemDef.setDefaults();
                itemDef.name = "Dark roc";
                //itemDef.description = "An extra 20% in drop rate boost.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_Roc.png");
                break;
            case 30152:
                itemDef.setDefaults();
                itemDef.id = 30152;
                itemDef.modelId = 57273;
                itemDef.maleModel0 = 57273;
                itemDef.femaleModel0 = 57273;
                itemDef.name = "@blu@Twisted Magma Bow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30205:
                itemDef.setDefaults();
                itemDef.id = 30205;
                itemDef.modelId = 57273;
                itemDef.maleModel0 = 57273;
                itemDef.femaleModel0 = 57273;
                itemDef.name = "@red@Infernal Magma Bow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1748;
                itemDef.xan2d = 983;
                itemDef.yan2d = 496;
                itemDef.zan2d = 26;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 12;
                itemDef.textureFind = new int[] { 59,59,59 };
                itemDef.textureReplace  = new int[] {40,40,40};
                break;
            case 30203:
                itemDef.setDefaults();
                itemDef.id = 30203;
                itemDef.modelId = 57273;
                itemDef.maleModel0 = 57273;
                itemDef.femaleModel0 = 57273;
                itemDef.name = "@red@God Magma Bow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1748;
                itemDef.xan2d = 983;
                itemDef.yan2d = 496;
                itemDef.zan2d = 26;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 12;
                itemDef.textureFind = new int[] { 138,138,138 };
                itemDef.textureReplace  = new int[] {40,40,40};
                itemDef.colorFind = new int[] { 32700, 32700, 32700 };
                itemDef.colorReplace = new int[] { 166, 152, 93 };
                break;
            case 30150:
                itemDef.setDefaults();
                itemDef.id = 30150;
                itemDef.modelId = 14516;
                itemDef.maleModel0 = 14516;
                itemDef.femaleModel0 = 14516;
                itemDef.name = "@blu@Chaotic Claws";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30156:
                itemDef.setDefaults();
                itemDef.id = 30156;
                itemDef.modelId = 57270;
                itemDef.maleModel0 = 57270;
                itemDef.femaleModel0 = 57270;
                itemDef.name = "@blu@MVP Sword";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30158:
                itemDef.setDefaults();
                itemDef.id = 30158;
                itemDef.modelId = 56003;
                itemDef.maleModel0 = 56003;
                itemDef.femaleModel0 = 56003;
                itemDef.name = "@blu@Ghostly Torva Full Helm";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30160:
                itemDef.setDefaults();
                itemDef.id = 30160;
                itemDef.modelId = 56002;
                itemDef.maleModel0 = 56002;
                itemDef.femaleModel0 = 56002;
                itemDef.name = "@blu@Ghostly Torva platelegs";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30162:
                itemDef.setDefaults();
                itemDef.id = 30162;
                itemDef.modelId = 46001;
                itemDef.maleModel0 = 46001;
                itemDef.femaleModel0 = 46001;
                itemDef.name = "@blu@Ghostly Torva platebody";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30154:
                itemDef.setDefaults();
                itemDef.id = 30154;
                itemDef.modelId = 58001;
                itemDef.maleModel0 = 58000;
                itemDef.femaleModel0 = 58000;
                itemDef.name = "@blu@Shadowhunter Brutal Whip";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30164:
                itemDef.setDefaults();
                itemDef.id = 30164;
                itemDef.modelId = 58882;
                itemDef.maleModel0 = 58882;
                itemDef.femaleModel0 = 58882;
                itemDef.name = "@blu@Glowing MVP Staff";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30166:
                itemDef.setDefaults();
                copy(itemDef, 30166, 12596, "Infernal Tunic", "Wield");
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.textureFind = new int[] { 15252,16161,17294 };
                itemDef.textureReplace  = new int[] {40,40,40};
                break;
            case 30122:
                itemDef.setDefaults();
                itemDef.id = 30022;
                itemDef.modelId = 47025;
                itemDef.name = "@gre@$$@blu@CashMoney@gre@$$";
                //itemDef.description = "The Spender's pet.";
                itemDef.interfaceOptions = new String[] { "Check", null, "Empty", null, "Drop" };
                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;
                break;
            case 30123:
                itemDef.setDefaults();
                itemDef.name = "Dark seren";
                //itemDef.description = "85% chance for Wildy Event Boss to hit a 0 and 25% chance to double key.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("dark_seren.png");
                break;

            case 23939:
                itemDef.name = "Seren";
                //itemDef.description = "50% chance for wildy event bosses to hit a 0 on you.";
                itemDef.createCustomSprite("seren.png");
                break;
            case 21046:
                itemDef.name = "@cya@Chest rate bonus (+15%)";
                //itemDef.description = "A single use +15% chance from chests, or to receive a rare raids key.";
                itemDef.stackable = true;
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                break;
            case 11666:
                itemDef.name = "Full Elite Void Token";
                //itemDef.description = "Use this token to receive a full elite void set with all combat pieces.";
                itemDef.interfaceOptions = new String[] { "Activate", null, null, null, "Drop" };
                break;
            case 1004:
                itemDef.name = "@gre@20m Coins";
                //itemDef.description = "Lovely coins.";
                itemDef.stackable = false;
                itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
                break;
            case 7629:
                itemDef.name = "@or3@2x Slayer point scroll";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = true;
                break;
            case 24460:
                itemDef.name = "@or3@Faster clues (30 mins)";
                //itemDef.description = "Clue rates are halved for npcs and skilling.";
                itemDef.interfaceOptions = new String[] { "Boost", null, null, null, "Drop" };
                itemDef.stackable = true;
                break;
            case 7968:
                itemDef.name = "@or3@+25% Skilling pet rate (30 mins)";
                itemDef.interfaceOptions = new String[] { "Boost", null, null, null, "Drop" };
                itemDef.stackable = true;
                break;
            case 8899:
                itemDef.name = "@gre@50m Coins";
                //itemDef.description = "Lovely coins.";
                itemDef.stackable = false;
                itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
                break;
            case 4035:
                itemDef.interfaceOptions = new String[] { "Teleport", null, null, null, null };
                break;
            case 10835:
                itemDef.name = "Buldging coin bag";
                itemDef.interfaceOptions = new String[] { "Open", null, "Open-All", null, "Drop" };
                //itemDef.description = "I can see some coins inside.";
                break;
            case 15098:
                itemDef.name = "Dice (up to 100)";
                //itemDef.description = "A 100-sided dice.";
                itemDef.modelId = 31223;
                itemDef.zoom2d = 1104;
                itemDef.yan2d = 215;
                itemDef.xan2d = 94;
                itemDef.yOffset2d = -5;
                itemDef.xOffset2d = -18;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Public-roll";
                itemDef.interfaceOptions[2] = null;
                itemDef.name = "Dice (up to 100)";
                itemDef.ambient = 15;
                itemDef.contrast = 25;
                itemDef.createCustomSprite("Dice_Bag.png");
                break;
            case 11773:
            case 11771:
            case 11770:
            case 11772:
                itemDef.ambient += 45;
                break;
            case 12792:
                itemDef.name = "Graceful Recolor Box";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                break;
            case 6769:
                itemDef.name = "@yel@$5 Scroll";
                //itemDef.description = "Claim this scroll to be rewarded with 5 donator points.";
                itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
                break;
            case 2403:
                itemDef.name = "@yel@$10 Scroll";
                //itemDef.description = "Claim this scroll to be rewarded with 10 donator points.";
                itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
                break;
            case 2396:
                itemDef.name = "@yel@$25 Scroll";
                //itemDef.description = "Claim this scroll to be rewarded with 25 donator points.";
                itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
                break;
            case 786:
                itemDef.name = "@yel@$50 Donator";
                //itemDef.description = "Claim this scroll to be rewarded with 50 donator points.";
                itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
                break;
            case 761:
                itemDef.name = "@yel@$100 Donator";
                //itemDef.description = "Claim this scroll to be rewarded with 100 donator points.";
                itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
                break;
            case 956:
                itemDef.name = "@yel@$1 Donator";
                //itemDef.description = "Claim this scroll to be rewarded with 100 donator points.";
                itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
                break;
            case 607:
                itemDef.name = "@red@$250 Scroll";
                //itemDef.description = "Claim this scroll to be rewarded with 250 donator points.";
                itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
                break;
            case 608:
                itemDef.name = "@gre@$500 Scroll";
                //itemDef.description = "Claim this scroll to be rewarded with 500 donator points.";
                itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
                break;
            case 1464:
                itemDef.name = "Archery ticket";
                //itemDef.description = "Exchange this for a Vote Point.";
                break;

            case 33049:
                itemDef.setDefaults();
                itemDef.name = "Agility master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 677, 801, 43540, 43543, 43546, 43549, 43550, 43552, 43554, 43558,
                        43560, 43575 };
                itemDef.modelId = 50030;
                itemDef.maleModel0 = 50031;
                itemDef.femaleModel0 = 50031;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33033:
                itemDef.setDefaults();
                itemDef.name = "Attack master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 7104, 9151, 911, 914, 917, 920, 921, 923, 925, 929, 931, 946 };
                itemDef.modelId = 50032;
                itemDef.maleModel0 = 50033;
                itemDef.femaleModel0 = 50033;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33055:
                itemDef.setDefaults();
                itemDef.name = "Construction master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 6061, 5945, 6327, 6330, 6333, 6336, 6337, 6339, 6341, 6345, 6347,
                        6362 };
                itemDef.modelId = 50034;
                itemDef.maleModel0 = 50035;
                itemDef.femaleModel0 = 50035;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33040:
                itemDef.setDefaults();
                itemDef.name = "Cooking master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 920, 920, 51856, 51859, 51862, 51865, 51866, 51868, 51870, 51874,
                        51876, 51891 };
                itemDef.modelId = 50036;
                itemDef.maleModel0 = 50037;
                itemDef.femaleModel0 = 50037;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33045:
                itemDef.setDefaults();
                itemDef.name = "Crafting master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 9142, 9152, 4511, 4514, 4517, 4520, 4521, 4523, 4525, 4529, 4531,
                        4546 };
                itemDef.modelId = 50038;
                itemDef.maleModel0 = 50039;
                itemDef.femaleModel0 = 50039;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33034:
                itemDef.setDefaults();
                itemDef.name = "Defence master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 10460, 10473, 41410, 41413, 41416, 41419, 41420, 41422, 41424,
                        41428, 41430, 41445 };
                itemDef.modelId = 50040;
                itemDef.maleModel0 = 50041;
                itemDef.femaleModel0 = 50041;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33052:
                itemDef.setDefaults();
                itemDef.name = "Farming master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 14775, 14792, 22026, 22029, 22032, 22035, 22036, 22038, 22040,
                        22044, 22046, 22061 };
                itemDef.modelId = 50042;
                itemDef.maleModel0 = 50043;
                itemDef.femaleModel0 = 50043;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33044:
                itemDef.setDefaults();
                itemDef.name = "Firemaking master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 8125, 9152, 4015, 4018, 4021, 4024, 4025, 4027, 4029, 4033, 4035,
                        4050 };
                itemDef.modelId = 50044;
                itemDef.maleModel0 = 50045;
                itemDef.femaleModel0 = 50045;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33043:
                itemDef.setDefaults();
                itemDef.name = "Fishing master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 9144, 9152, 38202, 38205, 38208, 38211, 38212, 38214, 38216,
                        38220, 38222, 38237 };
                itemDef.modelId = 50046;
                itemDef.maleModel0 = 50047;
                itemDef.femaleModel0 = 50047;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33042:
                itemDef.setDefaults();
                itemDef.name = "Fletching master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 6067, 9152, 33670, 33673, 33676, 33679, 33680, 33682, 33684,
                        33688, 33690, 33705 };
                itemDef.modelId = 50048;
                itemDef.maleModel0 = 50049;
                itemDef.femaleModel0 = 50049;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33048:
                itemDef.setDefaults();
                itemDef.name = "Herblore master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 9145, 9156, 22414, 22417, 22420, 22423, 22424, 22426, 22428,
                        22432, 22434, 22449 };
                itemDef.modelId = 50050;
                itemDef.maleModel0 = 50051;
                itemDef.femaleModel0 = 50051;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33036:
                itemDef.setDefaults();
                itemDef.name = "Hitpoints master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 818, 951, 8291, 8294, 8297, 8300, 8301, 8303, 8305, 8309, 8311,
                        8319 };
                itemDef.modelId = 50052;
                itemDef.maleModel0 = 50053;
                itemDef.femaleModel0 = 50053;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                //itemDef.femaleOffset = 4;
                break;
            case 33054:
                itemDef.setDefaults();
                itemDef.name = "Hunter master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 5262, 6020, 8472, 8475, 8478, 8481, 8482, 8484, 8486, 8490, 8492,
                        8507 };
                itemDef.modelId = 50054;
                itemDef.maleModel0 = 50055;
                itemDef.femaleModel0 = 50055;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33039:
                itemDef.setDefaults();
                itemDef.name = "Magic master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 43569, 43685, 6336, 6339, 6342, 6345, 6346, 6348, 6350, 6354,
                        6356, 6371 };
                itemDef.modelId = 50056;
                itemDef.maleModel0 = 50057;
                itemDef.femaleModel0 = 50057;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33047:
                itemDef.setDefaults();
                itemDef.name = "Mining master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 36296, 36279, 10386, 10389, 10392, 10395, 10396, 10398, 10400,
                        10404, 10406, 10421 };
                itemDef.modelId = 50058;
                itemDef.maleModel0 = 50059;
                itemDef.femaleModel0 = 50059;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33038:
                itemDef.setDefaults();
                itemDef.name = "Prayer master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 9163, 9168, 117, 120, 123, 126, 127, 127, 127, 127, 127, 127 };
                itemDef.modelId = 50060;
                itemDef.maleModel0 = 50061;
                itemDef.femaleModel0 = 50061;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33037:
                itemDef.setDefaults();
                itemDef.name = "Range master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 3755, 3998, 15122, 15125, 15128, 15131, 15132, 15134, 15136,
                        15140, 15142, 15157 };
                itemDef.modelId = 50062;
                itemDef.maleModel0 = 50063;
                itemDef.femaleModel0 = 50063;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33053:
                itemDef.setDefaults();
                itemDef.name = "Runecrafting master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                // 4 //7 //10 //13 //14//16//18//22 //24//39
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 9152, 8128, 10318, 10321, 10324, 10327, 10328, 10330, 10332,
                        10336, 10338, 10353 };
                itemDef.modelId = 50064;
                itemDef.maleModel0 = 50065;
                itemDef.femaleModel0 = 50065;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33051:
                itemDef.setDefaults();
                itemDef.name = "Slayer master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                itemDef.colorReplace = new int[] { 57022, 48811 };
                itemDef.colorFind = new int[] { 912, 920 };
                itemDef.modelId = 50066;
                itemDef.maleModel0 = 50067;
                itemDef.femaleModel0 = 50067;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33046:
                itemDef.setDefaults();
                itemDef.name = "Smithing master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 8115, 9148, 10386, 10389, 10392, 10395, 10396, 10398, 10400,
                        10404, 10406, 10421 };
                itemDef.modelId = 50068;
                itemDef.maleModel0 = 50069;
                itemDef.femaleModel0 = 50069;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33035:
                itemDef.setDefaults();
                itemDef.name = "Strength master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 935, 931, 27538, 27541, 27544, 27547, 27548, 27550, 27552, 27556,
                        27558, 27573 };
                itemDef.modelId = 50070;
                itemDef.maleModel0 = 50071;
                itemDef.femaleModel0 = 50071;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;
            case 33050:
                itemDef.setDefaults();
                itemDef.name = "Thieving master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 11, 0, 58779, 58782, 58785, 58788, 58789, 57891, 58793, 58797,
                        58799, 58814 };
                itemDef.maleModel0 = 50073;
                itemDef.femaleModel0 = 50073;
                itemDef.modelId = 50072;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = 5;
                break;

            case 33041:
                itemDef.setDefaults();
                itemDef.name = "Woodcutting master cape";
                //itemDef.description = "	A cape worn by those who've overachieved.";
                itemDef.colorReplace = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
                itemDef.colorFind = new int[] { 25109, 24088, 6693, 6696, 6699, 6702, 6703, 6705, 6707, 6711,
                        6713, 6728 };
                itemDef.modelId = 50074;
                itemDef.maleModel0 = 50075;
                itemDef.femaleModel0 = 50075;
                itemDef.zoom2d = 2300;
                itemDef.xan2d = 400;
                itemDef.yan2d = 1020;
                itemDef.xOffset2d = 3;
                itemDef.yOffset2d = 30;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                //itemDef.maleOffset = -2;
                break;
            case 27:
                itemDef.name = "Division Pass";
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, null };
                break;
            case 22999:
                itemDef.name = "Infinite Overload Potion";
                //itemDef.description = "A Magical Overload Potion.";
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, null };
                break;
            case 11429:
                itemDef.name = "Infinite Aggression Potion";
                //itemDef.description = "A Magical Aggression Potion.";
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, null };
                break;
            case 6805:
                itemDef.name = "Fortune Spin";
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, null };
                break;
            case 6807:
                itemDef.name = "1k Prestige Points";
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, null };
                break;
            case 11481:
                itemDef.name = "Infinite Prayer Potion";
                //itemDef.description = "A Magical Prayer Potion.";
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, null };
                break;
            case 11433:
                itemDef.name = "Rage Potion";
                //itemDef.description = "A Magical Prayer Potion.";
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, null };
                break;
            case 26500:
                itemDef.name = "Donor Boss Scroll";
                //itemDef.description = "This scroll will reset your donor boss Kill Allowance.";
                itemDef.interfaceOptions = new String[] { "read", null, null, null, null };
                break;
            case 26515:
                itemDef.name = "Winner Trophy";
                //itemDef.description = "Given to winners during the ArkCane Bingo.";
                break;
            case 26598:
                itemDef.name = "Lava Eel Bait";
                //itemDef.description = "For use with a oily fishing rod.";
                itemDef.interfaceOptions = new String[] { "use", null, null, null, "drop" };
                break;
            case 33056:
                itemDef.setDefaults();
                itemDef.id = 33056;
                itemDef.modelId = 65270;
                itemDef.name = "Completionist cape";
                //itemDef.description = "A cape worn by those who've overachieved.";

                itemDef.zoom2d = 7000;
                itemDef.xan2d = 496;
                itemDef.yan2d = 983;
                itemDef.zan2d = 26;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 12;

                itemDef.maleModel0 = 65310;
                itemDef.femaleModel0 = 65310;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = "Teleports";
                itemDef.interfaceOptions[3] = "Features";
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33058:
                itemDef.setDefaults();
                itemDef.id = 33058;
                itemDef.modelId = 60000;
                itemDef.name = "Seren godbow";
                //itemDef.description = "An intricate bow of pure crystal.";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1748;
                itemDef.xan2d = 983;
                itemDef.yan2d = 496;
                itemDef.zan2d = 26;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 12;
                itemDef.maleModel0 = 60000;
                itemDef.femaleModel0 = 60000;
                break;
            case 7478:
                itemDef.name = "@cya@AFK Token";
                //itemDef.description = "An AFK Token which can be sold to others.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                break;

            case 33059:
                itemDef.setDefaults();
                itemDef.id = 33059;
                itemDef.modelId = 60005;
                itemDef.name = "Halloween Body";
                //itemDef.description = "Halloween Body.";

                itemDef.zoom2d = 2078;
                itemDef.xan2d = 320;
                itemDef.yan2d = 2043;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 34;

                itemDef.maleModel0 = 60006;
                itemDef.femaleModel0 = 60006;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33060:
                itemDef.setDefaults();
                itemDef.id = 33060;
                itemDef.modelId = 60007;
                itemDef.name = "Halloween boots";
                //itemDef.description = "Halloween boots.";

                itemDef.zoom2d = 1017;
                itemDef.xan2d = 357;
                itemDef.yan2d = 2043;
                itemDef.zan2d = 9;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 19;

                itemDef.maleModel0 = 60008;
                itemDef.femaleModel0 = 60008;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33061:
                itemDef.setDefaults();
                itemDef.id = 33061;
                itemDef.modelId = 60009;
                itemDef.name = "Halloween cape";
                //itemDef.description = "Halloween cape.";

                itemDef.zoom2d = 2165;
                itemDef.xan2d = 320;
                itemDef.yan2d = 1191;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 15;
                itemDef.yOffset2d = 54;

                itemDef.maleModel0 = 60010;
                itemDef.femaleModel0 = 60010;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33062:
                itemDef.setDefaults();
                itemDef.id = 33062;
                itemDef.modelId = 60011;
                itemDef.name = "Halloween gloves";
                //itemDef.description = "Halloween gloves.";

                itemDef.zoom2d = 804;
                itemDef.xan2d = 320;
                itemDef.yan2d = 2043;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 13;

                itemDef.maleModel0 = 60012;
                itemDef.femaleModel0 = 60012;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33063:
                itemDef.setDefaults();
                itemDef.id = 33063;
                itemDef.modelId = 60013;
                itemDef.name = "Halloween head";
                //itemDef.description = "Halloween head.";

                itemDef.zoom2d = 843;
                itemDef.xan2d = 9;
                itemDef.yan2d = 1852;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -3;
                itemDef.yOffset2d = -2;

                itemDef.maleModel0 = 60014;
                itemDef.femaleModel0 = 60014;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33064:
                itemDef.setDefaults();
                itemDef.id = 33064;
                itemDef.modelId = 60015;
                itemDef.name = "Halloween legs";
                //itemDef.description = "Halloween legs.";

                itemDef.zoom2d = 1835;
                itemDef.xan2d = 478;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -3;
                itemDef.yOffset2d = -2;

                itemDef.maleModel0 = 60016;
                itemDef.femaleModel0 = 60016;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33065:
                itemDef.setDefaults();
                itemDef.id = 33065;
                itemDef.modelId = 60001;
                itemDef.name = "Beaver";
                //itemDef.description = "Beaver";

                itemDef.zoom2d = 3000;
                itemDef.xan2d = 150;
                itemDef.yan2d = 26;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 5;
                itemDef.yOffset2d = -9;

                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33066:
                itemDef.setDefaults();
                itemDef.id = 33066;
                itemDef.modelId = 47003;
                itemDef.name = "Rock";
                //itemDef.description = "Rock";

                itemDef.zoom2d = 4500;
                itemDef.xan2d = 41;
                itemDef.yan2d = 36;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -4;
                itemDef.yOffset2d = 0;

                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33067:
                itemDef.setDefaults();
                itemDef.id = 33067;
                itemDef.modelId = 47004;
                itemDef.name = "Mystery Box";
                //itemDef.description = "Mystery Box";

                itemDef.zoom2d = 3000;
                itemDef.xan2d = 9;
                itemDef.yan2d = 26;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -3;
                itemDef.yOffset2d = 26;

                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33068:
                itemDef.setDefaults();
                itemDef.id = 33068;
                itemDef.modelId = 60002;
                itemDef.name = "Fish";
                //itemDef.description = "Fish";

                itemDef.zoom2d = 3000;
                itemDef.xan2d = 26;
                itemDef.yan2d = 1748;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -10;
                itemDef.yOffset2d = 13;

                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33069:
                itemDef.setDefaults();
                itemDef.id = 33069;
                itemDef.modelId = 60003;
                itemDef.name = "Magic";
                //itemDef.description = "Magic";

                itemDef.zoom2d = 2687;
                itemDef.xan2d = 26;
                itemDef.yan2d = 1748;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -10;
                itemDef.yOffset2d = 13;

                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33070:
                itemDef.setDefaults();
                itemDef.id = 33070;
                itemDef.modelId = 47008;
                itemDef.name = "Skeleton";
                //itemDef.description = "Skeleton";

                itemDef.zoom2d = 3500;
                itemDef.xan2d = 165;
                itemDef.yan2d = 1800;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -26;
                itemDef.yOffset2d = -43;

                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33071:
                itemDef.setDefaults();
                itemDef.id = 33071;
                itemDef.modelId = 60004;
                itemDef.name = "head";
                //itemDef.description = "head";

                itemDef.zoom2d = 1765;
                itemDef.xan2d = 148;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 78;

                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[4] = "Drop";
                break;

            /* Fragment's */
            case 33072:
                itemDef.setDefaults();
                itemDef.name = "The Fusionist";
                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to save Materials/Platinum when fusing items.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("alchemaniac.png");
                break;
            case 33073:
                itemDef.setDefaults();
                itemDef.name = "Dwarf Overload";
                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to automatically reload your cannon on kill.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Arcane_Conduit.png");
                break;
            case 33074:
                itemDef.setDefaults();
                itemDef.name = "PK Master";
                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Double PK Points.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Armadylean_Decree.png");
                break;
            case 33075:
                itemDef.setDefaults();
                itemDef.name = "Wildy Slayer";
                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Earn double slayer points while skulled in wilderness.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Bandosian_Might.png");
                break;
            case 33076:
                itemDef.setDefaults();
                itemDef.name = "Sneaky Sneaky";
                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double pickpocket loot when stealing from master farmer.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Barbarian_Pest_Wars.png");
                break;
            case 33077:
                itemDef.setDefaults();
                itemDef.name = "Iron Giant";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to smelt an extra bar when smelting bar's.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Bottomless_Quiver.png");
                break;
            case 33078:
                itemDef.setDefaults();
                itemDef.name = "Slayer Override";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Earn 100% more slayer point's upon completing a slayer task.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Catch_Of_The_Day.png");
                break;
            case 33079:
                itemDef.setDefaults();
                itemDef.name = "Runecrafter";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "you will no longer receive rune's lower than or equal to level 20.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Certified_Farmer.png");
                break;
            case 33080:
                itemDef.setDefaults();
                itemDef.name = "Pro Fletcher";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Fletching logs & Stringing bows grant 50% more fletching exp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Chef's_Catch.png");
                break;
            case 33081:
                itemDef.setDefaults();
                itemDef.name = "Chisel Master";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to make 50% more bolt tips per gem, arrow  tips, dart tips.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Chinchonkers.png");
                break;
            case 33082:
                itemDef.setDefaults();
                itemDef.name = "Ava's Accomplice";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to save ammo when using ranged weapon.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Clued_In.png");
                break;
            case 33083:
                itemDef.setDefaults();
                itemDef.name = "Deeper Pockets";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to send drops to your bank.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Deeper_Pockets.png");
                break;
            case 33084:
                itemDef.setDefaults();
                itemDef.name = "Recharger";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "charges have a 60% chance to be saved.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Dine_&_Dash.png");
                break;
            case 33085:
                itemDef.setDefaults();
                itemDef.name = "Magic Paper CHANCED";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "chance to note drops.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Divine_Restoration.png");
                break;
            case 33086:
                itemDef.setDefaults();
                itemDef.name = "Dragon Bait";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "50% increase hunter catch rate.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Dragon_On_A_Bit.png");
                break;
            case 33087:
                itemDef.setDefaults();
                itemDef.name = "Skilled Thief";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double thieving xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Enchanted_Jeweler.png");
                break;
            case 33088:
                itemDef.setDefaults();
                itemDef.name = "Crafting Guru";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double crafting xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Golden_Brick_Road.png");
                break;
            case 33089:
                itemDef.setDefaults();
                itemDef.name = "Hot Hands";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double cooking xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Grave_Robber.png");
                break;
            case 33090:
                itemDef.setDefaults();
                itemDef.name = "Magic Master";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to save rune's when casting spells.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Homewrecker.png");
                break;
            case 33091:
                itemDef.setDefaults();
                itemDef.name = "Yin Yang";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "25% slower prayer drain.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Hot_On_The_Trail.png");
                break;
            case 33092:
                itemDef.setDefaults();
                itemDef.name = "Nomad Master";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double Upgrade Points when burning.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Imcando's_Apprentice.png");
                break;
            case 33093:
                itemDef.setDefaults();
                itemDef.name = "Demon Slayer";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double demon hunter xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Just_Druid!.png");
                break;
            case 33094:
                itemDef.setDefaults();
                itemDef.name = "Slayer Master";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double slayer xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Larger_Recharger.png");
                break;
            case 33095:
                itemDef.setDefaults();
                itemDef.name = "Pyromaniac";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double firemaking xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Livin'_On_A_Prayer.png");
                break;
            case 33096:
                itemDef.setDefaults();
                itemDef.name = "Skilled Hunter";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double hunter xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Message_In_A_Bottle.png");
                break;
            case 33097:
                itemDef.setDefaults();
                itemDef.name = "Molten Miner";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double mining xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Mixologist.png");
                break;
            case 33098:
                itemDef.setDefaults();
                itemDef.name = "Woodchipper";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double woodcutting xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Molten_Miner.png");
                break;
            case 33099:
                itemDef.setDefaults();
                itemDef.name = "Bare Hands";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double fishing xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Mother's_Magic_Fossils.png");
                break;
            case 33100:
                itemDef.setDefaults();
                itemDef.name = "Bare Hands x3";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to triple your fish when fishing.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Plank_Stretcher.png");
                break;
            case 33101:
                itemDef.setDefaults();
                itemDef.name = "Praying Respects";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double prayer xp.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Praying_Respects.png");
                break;
            case 33102:
                itemDef.setDefaults();
                itemDef.name = "Novice Zerk";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Gain 5% Melee Damage Bonus.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Pro_Tips.png");
                break;
            case 33103:
                itemDef.setDefaults();
                itemDef.name = "Novice Magician";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Gain 5% Magic Damage Bonus.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Profletchional.png");
                break;
            case 33104:
                itemDef.setDefaults();
                itemDef.name = "Novice Ranger";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Gain 5% Range Damage Bonus.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Rock_Solid.png");
                break;
            case 33105:
                itemDef.setDefaults();
                itemDef.name = "Pro Zerk";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Gain 10% Melee Damage Bonus.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Rogues'_Chompy_Farm.png");
                break;
            case 33106:
                itemDef.setDefaults();
                itemDef.name = "Pro Magician";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Gain 10% Magic Damage.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Rooty_Tooty_2x_Runeys.png");
                break;
            case 33107:
                itemDef.setDefaults();
                itemDef.name = "Pro Ranger";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Gain 10% Range Damage Bonus.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Rumple-Bow-String.png");
                break;
            case 33108:
                itemDef.setDefaults();
                itemDef.name = "Swedish Swindle";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "10% Bonus Drop Rate.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Rune_Escape.png");
                break;
            case 33109:
                itemDef.setDefaults();
                itemDef.name = "Raiders Luck";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "10% Chance to get an extra key from COX.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Saradominist_Defence.png");
                break;
            case 33110:
                itemDef.setDefaults();
                itemDef.name = "Clepto Maniac";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Collect all drop's.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Seedy_Business.png");
                break;
            case 33111:
                itemDef.setDefaults();
                itemDef.name = "Magic Paper";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Note all drop's.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Slash_&_Burn.png");
                break;
            case 33112:
                itemDef.setDefaults();
                itemDef.name = "Pot Of Gold";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "20% Increased Drop Rate.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Slay_All_Day.png");
                break;
            case 33113:
                itemDef.setDefaults();
                itemDef.name = "Mystery Madness";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double reward's from any mystery box.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Slay_'n'_Pay.png");
                break;
            case 33114:
                itemDef.setDefaults();
                itemDef.name = "Casket Master";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance of an extra clue casket upon openin.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Smithing_Double.png");
                break;
            case 33115:
                itemDef.setDefaults();
                itemDef.name = "Voting King";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "2 extra vote point's per vote.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Smooth_Criminal.png");
                break;
            case 33116:
                itemDef.setDefaults();
                itemDef.name = "Pet Locator";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Extra Chance to find a pet.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Special_Discount.png");
                break;
            case 33117:
                itemDef.setDefaults();
                itemDef.name = "Monk Heals";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to double health when using healer icon.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Superior_Tracking.png");
                break;
            case 33118:
                itemDef.setDefaults();
                itemDef.name = "Dragon Fire";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Permanent Anti-fire potion effect.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Tactical_Duelist.png");
                break;
            case 33119:
                itemDef.setDefaults();
                itemDef.name = "Overload Protection";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Overload's no longer deal damage.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Thrall_Damage.png");
                break;
            case 33120:
                itemDef.setDefaults();
                itemDef.name = "Lucky Coin";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Chance to save 20% when donating to the well.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Unholy_Ranger.png");
                break;
            case 33121:
                itemDef.setDefaults();
                itemDef.name = "Cannon Extender";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "100 extra cannon ball's can be held in your cannon.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Unholy_Warrior.png");
                break;
            case 33122:
                itemDef.setDefaults();
                itemDef.name = "Pure Skills";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "2x Skilling xp to all skills.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Unholy_Wizard.png");
                break;
            case 33123:
                itemDef.setDefaults();
                itemDef.name = "Pc Pro";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "3x Pest control points.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Venomaster.png");
                break;
            case 33124:
                itemDef.setDefaults();
                itemDef.name = "Slayer Guru";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "Double slayer task size.";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Zamorakian_Sight.png");
                break;

            /*skilling crystals*/
            case 33125:
                itemDef.setDefaults();
                itemDef.name = "Skilling Crystal (easy)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from skilling, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("skilling_easy.png");
                break;
            case 33126:
                itemDef.setDefaults();
                itemDef.name = "Skilling Crystal (medium)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from skilling, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("skilling_medium.png");
                break;
            case 33127:
                itemDef.setDefaults();
                itemDef.name = "Skilling Crystal (hard)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from skilling, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("skilling_hard.png");
                break;
            case 33128:
                itemDef.setDefaults();
                itemDef.name = "Skilling Crystal (elite)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from skilling, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("skilling_elite.png");
                break;



            /*PVM crystals*/
            case 33129:
                itemDef.setDefaults();
                itemDef.name = "PVM Crystal (easy)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from PVM, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("pvm_easy.png");
                break;
            case 33130:
                itemDef.setDefaults();
                itemDef.name = "PVM Crystal (medium)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from PVM, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("pvm_medium.png");
                break;
            case 33131:
                itemDef.setDefaults();
                itemDef.name = "PVM Crystal (hard)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from PVM, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("pvm_hard.png");
                break;
            case 33132:
                itemDef.setDefaults();
                itemDef.name = "PVM Crystal (elite)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from PVM, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("pvm_elite.png");
                break;


            /*Misc crystals*/
            case 33133:
                itemDef.setDefaults();
                itemDef.name = "Misc Crystal (easy)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from Various Activities, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("misc_easy.png");
                break;
            case 33134:
                itemDef.setDefaults();
                itemDef.name = "Misc Crystal (medium)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from Various Activities, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("misc_medium.png");
                break;
            case 33135:
                itemDef.setDefaults();
                itemDef.name = "Misc Crystal (hard)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from Various Activities, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("misc_hard.png");
                break;
            case 33136:
                itemDef.setDefaults();
                itemDef.name = "Misc Crystal (elite)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from Various Activities, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("misc_elite.png");
                break;
            /*Foundry crystals*/
            case 33137:
                itemDef.setDefaults();
                itemDef.name = "Foundry Crystal (easy)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from Foundry Points, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("foundry_easy.png");
                break;
            case 33138:
                itemDef.setDefaults();
                itemDef.name = "Foundry Crystal (medium)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from Foundry Points, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("foundry_medium.png");
                break;
            case 33139:
                itemDef.setDefaults();
                itemDef.name = "Foundry Crystal (hard)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from Foundry Points, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("foundry_hard.png");
                break;
            case 33140:
                itemDef.setDefaults();
                itemDef.name = "Foundry Crystal (elite)";

                itemDef.modelId = ItemDefinition.lookup(26546).modelId;
                //itemDef.description = "A Crystal gained from Foundry Points, used with the fusion system.";
                itemDef.interfaceOptions = new String[] { "Use", null, null, null, "Destroy" };
                itemDef.stackable = true;
                itemDef.createCustomSprite("foundry_elite.png");
                break;

            /* Fragment's */

            case 33141:
                itemDef.setDefaults();
                itemDef.id = 33141;
                itemDef.modelId = 46806;
                itemDef.name = "Virtus Mask";
                //itemDef.description = "An ancient mage's mask.";
                itemDef.zoom2d = 880;
                itemDef.xan2d = 2009;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 1;
                itemDef.yOffset2d = 1;
                itemDef.maleModel0 = 46809;
                itemDef.femaleModel0 = 46804;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;
            case 33142:
                itemDef.setDefaults();
                itemDef.id = 33142;
                itemDef.modelId = 46802;
                itemDef.name = "Virtus robe top";
                //itemDef.description = "An ancient mage's robe top.";

                itemDef.zoom2d = 1180;
                itemDef.xan2d = 530;
                itemDef.yan2d = 2026;
                itemDef.zan2d = 9;
                itemDef.xOffset2d = 1;
                itemDef.yOffset2d = 5;

                itemDef.femaleModel0 = 46803;
                itemDef.maleModel0 = 46808;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;

            case 33143:
                itemDef.setDefaults();
                itemDef.id = 33143;
                itemDef.modelId = 46807;
                itemDef.name = "Virtus robe legs";
                //itemDef.description = "An ancient mage's robe legs.";

                itemDef.zoom2d = 1580;
                itemDef.xan2d = 320;
                itemDef.yan2d = 2043;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = -8;

                itemDef.maleModel0 = 46810;
                itemDef.femaleModel0 = 46805;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;

            case 33144:
                itemDef.setDefaults();
                itemDef.id = 33144;
                itemDef.modelId = 46813;
                itemDef.name = "Pernix cowl";
                //itemDef.description = "An ancient ranger's cowl.";

                itemDef.zoom2d = 1070;
                itemDef.xan2d = 304;
                itemDef.yan2d = 43;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -200;

                itemDef.maleModel0 = 46817;
                itemDef.femaleModel0 = 46813;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;

            case 33145:
                itemDef.setDefaults();
                itemDef.id = 33145;
                itemDef.modelId = 46811;
                itemDef.name = "Pernix body";
                //itemDef.description = "An ancient ranger's body armour.";

                itemDef.zoom2d = 1380;
                itemDef.xan2d = 565;
                itemDef.yan2d = 26;
                itemDef.zan2d = 9;
                itemDef.xOffset2d = -3;
                itemDef.yOffset2d = 1;

                itemDef.maleModel0 = 46816;
                itemDef.femaleModel0 = 46812;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;

            case 33146:
                itemDef.setDefaults();
                itemDef.id = 33146;
                itemDef.modelId = 46815;
                itemDef.name = "Pernix chaps";
                //itemDef.description = "An ancient ranger's chaps.";

                itemDef.zoom2d = 1680;
                itemDef.xan2d = 530;
                itemDef.yan2d = 9;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 1;

                itemDef.maleModel0 = 46818;
                itemDef.femaleModel0 = 46814;
                //itemDef.groundActions = new String[5];
                //itemDef.groundActions[2] = "Take";
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[4] = "Drop";
                break;


            case 33147:
                itemDef.setDefaults();
                itemDef.id = 33147;
                itemDef.modelId = 46826;
                itemDef.name = "Korai's sword";
                //itemDef.description = "The sword of a Void Knight.";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1598;
                itemDef.xan2d = 322;
                itemDef.yan2d = 1504;
                itemDef.zan2d = 9;
                itemDef.xOffset2d = 7;
                itemDef.yOffset2d = 7;
                itemDef.femaleModel0 = 46819;
                itemDef.maleModel0 = 46820;
                break;

            case 33148:
                itemDef.setDefaults();
                itemDef.id = 33148;
                itemDef.modelId = 46821;
                itemDef.name = "Noxious scythe";
                //itemDef.description = "A lethal scythe, made from spider parts.";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 2680;
                itemDef.xan2d = 320;
                itemDef.yan2d = 1591;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 12;
                itemDef.yOffset2d = 50;
                itemDef.maleModel0 = 46822;
                itemDef.femaleModel0 = 46822;
                break;

            case 21129:
                itemDef.name = "Rng of wealth(i5)";
                break;

            case 33149:
                itemDef.setDefaults();
                itemDef.id = 33149;
                itemDef.modelId = 46823;
                itemDef.name = "Noxious staff";
                //itemDef.description = "A lethal staff, made from spider parts.";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 2780;
                itemDef.xan2d = 320;
                itemDef.yan2d = 1678;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -4;
                itemDef.yOffset2d = 12;
                itemDef.maleModel0 = 46824;
                itemDef.femaleModel0 = 46824;
                break;

            case 3128:
                itemDef.id = 3128;
                itemDef.modelId = ItemDefinition.lookup(1044).modelId;
                itemDef.name = "Durial's Green Party Hat";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.colorFind = ItemDefinition.lookup(1044).colorFind;
                itemDef.colorReplace = ItemDefinition.lookup(1044).colorReplace;
                itemDef.zoom2d = ItemDefinition.lookup(1044).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(1044).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(1044).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(1044).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(1044).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(1044).yOffset2d;
                itemDef.maleModel0 = ItemDefinition.lookup(1044).maleModel0;
                itemDef.femaleModel0 = ItemDefinition.lookup(1044).femaleModel0;
                break;

            case 33238:
                itemDef.id = 33238;
                itemDef.modelId = (60040);
                itemDef.name = "Sponges Hat";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(1044).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(1044).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(1044).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(1044).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(1044).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(1044).yOffset2d;
                itemDef.maleModel0 = (60041);
                itemDef.femaleModel0 = (60042);
                break;

            case 33239:
                itemDef.id = 33239;
                itemDef.modelId = (60043);
                itemDef.name = "Golden Nut";
                itemDef.interfaceOptions = new String[] { null, "Use", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(4012).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(4012).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(4012).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(4012).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(4012).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(4012).yOffset2d;
                break;

            case 33150:
                itemDef.setDefaults();
                itemDef.id = 33150;
                itemDef.modelId = 46752;
                itemDef.name = "Sirenic hauberk";
                //itemDef.description = "Made from sirenic scales and strengthened by algarum thread.";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1417;
                itemDef.xan2d = 478;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 4;
                itemDef.maleModel0 = 46754;
                itemDef.femaleModel0 = 46753;
                break;
            case 33151:
                itemDef.setDefaults();
                itemDef.id = 33151;
                itemDef.modelId = 46755;
                itemDef.name = "Sirenic mask";
                //itemDef.description = "Made from sirenic scales and strengthened by algarum thread.";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 583;
                itemDef.xan2d = 513;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -3;
                itemDef.maleModel0 = 46757;
                itemDef.femaleModel0 = 46756;
                break;
            case 33152:
                itemDef.setDefaults();
                itemDef.id = 33152;
                itemDef.modelId = 46758;
                itemDef.name = "Sirenic chaps";
                //itemDef.description = "Made from sirenic scales and strengthened by algarum thread.";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1800;
                itemDef.xan2d = 530;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 2;
                itemDef.maleModel0 = 46760;
                itemDef.femaleModel0 = 46759;
                break;

            case 33153:
                itemDef.setDefaults();
                itemDef.id = 33153;
                itemDef.modelId = 46765;
                itemDef.name = "Malevolent helm";
                //itemDef.description = "Armour infused with malevolent energy.";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 809;
                itemDef.xan2d = 0;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -3;
                itemDef.maleModel0 = 46768;
                itemDef.femaleModel0 = 46761;
                break;
            case 33154:
                itemDef.setDefaults();
                itemDef.id = 33154;
                itemDef.modelId = 46762;
                itemDef.name = "Malevolent cuirass";
                //itemDef.description = "Armour infused with malevolent energy.";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1678;
                itemDef.xan2d = 513;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 0;
                itemDef.maleModel0 = 46767;
                itemDef.femaleModel0 = 46763;
                break;
            case 33155:
                itemDef.setDefaults();
                itemDef.id = 33155;
                itemDef.modelId = 46766;
                itemDef.name = "Malevolent greaves";
                //itemDef.description = "Armour infused with malevolent energy.";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1800;
                itemDef.xan2d = 530;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -3;
                itemDef.maleModel0 = 46769;
                itemDef.femaleModel0 = 46764;
                break;

            case 33156:
                itemDef.setDefaults();
                itemDef.id = 33156;
                itemDef.modelId = 46770;
                itemDef.name = "Tectonic mask";
                //itemDef.description = "A powerful mask from the depths.";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 809;
                itemDef.xan2d = 0;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -3;
                itemDef.maleModel0 = 46772;
                itemDef.femaleModel0 = 46771;
                break;
            case 33157:
                itemDef.setDefaults();
                itemDef.id = 33157;
                itemDef.modelId = 46773;
                itemDef.name = "Tectonic robe top";
                //itemDef.description = "A powerful robe from the depths.";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1678;
                itemDef.xan2d = 513;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 6;
                itemDef.maleModel0 = 46775;
                itemDef.femaleModel0 = 46774;
                break;
            case 33158:
                itemDef.setDefaults();
                itemDef.id = 33158;
                itemDef.modelId = 46776;
                itemDef.name = "Tectonic robe bottom";
                //itemDef.description = "A powerful robe from the depths.";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1800;
                itemDef.xan2d = 530;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 3;
                itemDef.maleModel0 = 46778;
                itemDef.femaleModel0 = 46777;
                break;
            case 12161:
                itemDef.name = "Christmas Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                break;
            case 33159:
                itemDef.setDefaults();
                itemDef.id = 33159;
                itemDef.modelId = 46779;
                itemDef.name = "Christmas Imp";
                //itemDef.description = "A Christmas Imp.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.zoom2d = 1452;
                itemDef.xan2d = 0;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -7;
                break;


            case 33160:
                itemDef.setDefaults();
                itemDef.id = 33160;
                itemDef.modelId = 60018;
                itemDef.name = "Candy Cane Twisted Bow";
                //itemDef.description = "A Candy Cane Twisted Bow.";
                itemDef.interfaceOptions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
                itemDef.zoom2d = 1980;
                itemDef.xan2d = 461;
                itemDef.yan2d = 320;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -4;
                itemDef.yOffset2d = 12;
                itemDef.maleModel0 = 60019;
                itemDef.femaleModel0 = 60019;
                break;


            case 33161:
                itemDef.setDefaults();
                itemDef.id = 33161;
                itemDef.modelId = 60020;
                itemDef.name = "Candy Cane Scythe";
                //itemDef.description = "A Candy Cane Scythe.";
                itemDef.interfaceOptions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
                itemDef.zoom2d = 1780;
                itemDef.xan2d = 304;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 2;
                itemDef.yOffset2d = 6;
                itemDef.maleModel0 = 60021;
                itemDef.femaleModel0 = 60021;
                break;


            case 26886:
                itemDef.name = "Upgrader Cell";
                break;
            case 33162:
                itemDef.setDefaults();
                itemDef.id = 33159;
                itemDef.modelId = 60022;
                itemDef.name = "Candy Cane Whip";
                //itemDef.description = "A Candy Cane Whip.";
                itemDef.interfaceOptions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
                itemDef.zoom2d = 980;
                itemDef.xan2d = 320;
                itemDef.yan2d = 436;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 2;
                itemDef.yOffset2d = 12;
                itemDef.maleModel0 = 60023;
                itemDef.femaleModel0 = 60023;
                break;

            case 33163:
                itemDef.setDefaults();
                itemDef.id = 33163;
                itemDef.modelId = 53096;
                itemDef.name = "AOE Tier (1) Bow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 2757;
                itemDef.xan2d = 459;
                itemDef.yan2d = 1000;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 23;
                itemDef.yOffset2d = 17;
                itemDef.maleModel0 = 49980;
                itemDef.femaleModel0 = 49980;
                break;
            case 33164:
                itemDef.setDefaults();
                itemDef.id = 33164;
                itemDef.modelId = 49983;
                itemDef.name = "AOE Tier (2) Bow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 2757;
                itemDef.xan2d = 459;
                itemDef.yan2d = 1000;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 23;
                itemDef.yOffset2d = 17;
                itemDef.maleModel0 = 49982;
                itemDef.femaleModel0 = 49982;
                break;
            case 33165:
                itemDef.setDefaults();
                itemDef.id = 33165;
                itemDef.modelId = 49985;
                itemDef.name = "AOE Tier (3) Bow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 2757;
                itemDef.xan2d = 459;
                itemDef.yan2d = 1000;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 23;
                itemDef.yOffset2d = 17;
                itemDef.maleModel0 = 49984;
                itemDef.femaleModel0 = 49984;
                break;
            case 33166:
                itemDef.setDefaults();
                itemDef.id = 33166;
                itemDef.modelId = 49987;
                itemDef.name = "AOE Tier (4) Bow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 2757;
                itemDef.xan2d = 459;
                itemDef.yan2d = 1000;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 23;
                itemDef.yOffset2d = 17;
                itemDef.maleModel0 = 49986;
                itemDef.femaleModel0 = 49986;
                break;
            case 33167:
                itemDef.setDefaults();
                itemDef.id = 33167;
                itemDef.modelId = 49989;
                itemDef.name = "AOE Tier (5) Bow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 2757;
                itemDef.xan2d = 459;
                itemDef.yan2d = 1000;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 23;
                itemDef.yOffset2d = 17;
                itemDef.maleModel0 = 49988;
                itemDef.femaleModel0 = 49988;
                break;
            case 33168:
                itemDef.setDefaults();
                itemDef.id = 33168;
                itemDef.modelId = 49991;
                itemDef.name = "AOE God Tier Bow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 2757;
                itemDef.xan2d = 459;
                itemDef.yan2d = 1000;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 23;
                itemDef.yOffset2d = 17;
                itemDef.maleModel0 = 49990;
                itemDef.femaleModel0 = 49990;
                break;
            case 33169:
                itemDef.setDefaults();
                itemDef.id = 33169;
                itemDef.modelId = 53096;
                itemDef.name = "AOE Tier (1) Staff";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1887;
                itemDef.xan2d = 357;
                itemDef.yan2d = 1504;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -26;
                itemDef.yOffset2d = -61;
                itemDef.maleModel0 = 53096;
                itemDef.femaleModel0 = 53096;
                break;
            case 33170:
                itemDef.setDefaults();
                itemDef.id = 33170;
                itemDef.modelId = 53096;
                itemDef.name = "AOE Tier (2) Staff";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1887;
                itemDef.xan2d = 357;
                itemDef.yan2d = 1504;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -26;
                itemDef.yOffset2d = -61;
                itemDef.maleModel0 = 53096;
                itemDef.femaleModel0 = 53096;
                break;
            case 33171:
                itemDef.setDefaults();
                itemDef.id = 33171;
                itemDef.modelId = 53096;
                itemDef.name = "AOE Tier (3) Staff";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1887;
                itemDef.xan2d = 357;
                itemDef.yan2d = 1504;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -26;
                itemDef.yOffset2d = -61;
                itemDef.maleModel0 = 53096;
                itemDef.femaleModel0 = 53096;
                break;
            case 33172:
                itemDef.setDefaults();
                itemDef.id = 33172;
                itemDef.modelId = 60417;
                itemDef.name = "AOE Tier (4) Staff";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1887;
                itemDef.xan2d = 357;
                itemDef.yan2d = 1504;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -26;
                itemDef.yOffset2d = -61;
                itemDef.maleModel0 = 60417;
                itemDef.femaleModel0 = 60417;
                break;
            case 33174:
                itemDef.setDefaults();
                itemDef.id = 33174;
                itemDef.modelId = 60417;
                itemDef.name = "AOE Tier (5) Staff";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1887;
                itemDef.xan2d = 357;
                itemDef.yan2d = 1504;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -26;
                itemDef.yOffset2d = -61;
                itemDef.maleModel0 = 60417;
                itemDef.femaleModel0 = 60417;
                break;
            case 33173:
                itemDef.setDefaults();
                itemDef.id = 33173;
                itemDef.modelId = 60886;
                itemDef.name = "AOE God Tier Staff";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1887;
                itemDef.xan2d = 357;
                itemDef.yan2d = 1504;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -26;
                itemDef.yOffset2d = -61;
                itemDef.maleModel0 = 60886;
                itemDef.femaleModel0 = 60886;
                break;
            case 33175:
                itemDef.setDefaults();
                itemDef.id = 33175;
                itemDef.modelId = 60024;
                itemDef.name = "Axe Of Araphel";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1980;
                itemDef.xan2d = 490;
                itemDef.yan2d = 306;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -15;
                itemDef.yOffset2d = -14;
                itemDef.maleModel0 = 60025;
                itemDef.femaleModel0 = 60025;
                break;
            case 33176:
//				itemDef.setDefaults();
                itemDef.id = 33176;
                itemDef.name = "Discord Booster's Cape";
                itemDef.modelId = 46830;
                itemDef.zoom2d = 2600;
                itemDef.xan2d = 0;
                itemDef.yan2d = 0;
                itemDef.zan2d = 3;
                itemDef.xOffset2d = 4;
                itemDef.yOffset2d = 6;
                itemDef.interfaceOptions = new String[5];
                itemDef.interfaceOptions[1] = "Wear";
                itemDef.interfaceOptions[2] = null;
                itemDef.maleModel0 = 46830;
                itemDef.femaleModel0 = 46830;
                break;
            case 25478:
                itemDef.name = "Bounty Protection Scroll";
                break;
            case 20238:
                itemDef.name = "Tasks System Reset";
                break;
            case 25481:
                itemDef.name = "Collection Log Reset Scroll";
                break;
            case 10533:
                itemDef.name = "Guardian Angel Pet";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 33177:
                itemDef.setDefaults();
                itemDef.id = 33177;
                itemDef.modelId = 49914;
                itemDef.name = "Golden blowpipe";
                itemDef.interfaceOptions = new String[] { null, "Wield", "Check", "Unload", "Uncharge" };
                itemDef.zoom2d = 1480;
                itemDef.xan2d = 374;
                itemDef.yan2d = 235;
                itemDef.zan2d = 130;
                itemDef.xOffset2d = 28;
                itemDef.yOffset2d = -138;
                itemDef.maleModel0 = 49914;
                itemDef.femaleModel0 = 49914;
                break;
            case 33183:
                itemDef.setDefaults();
                itemDef.id = 33183;
                itemDef.modelId = 47054;
                itemDef.name = "Cape of malar";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1780;
                itemDef.xan2d = 320;
                itemDef.yan2d = 843;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 10;
                itemDef.maleModel0 = 47055;
                itemDef.femaleModel0 = 47055;
                break;
            case 33184:
                itemDef.setDefaults();
                itemDef.id = 33184;
                itemDef.modelId = 47056;
                itemDef.name = "Blood Scythe";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 3280;
                itemDef.xan2d = 250;
                itemDef.yan2d = 670;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 29;
                itemDef.yOffset2d = -513;
                itemDef.maleModel0 = 47057;
                itemDef.femaleModel0 = 47057;
                break;
            case 33186:
                itemDef.setDefaults();
                itemDef.id = 33186;
                itemDef.modelId = 47060;
                itemDef.name = "Mask Of Malar";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 680;
                itemDef.xan2d = 0;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -5;
                itemDef.maleModel0 = 47061;
                itemDef.femaleModel0 = 47061;
                break;
            case 33187:
                itemDef.setDefaults();
                itemDef.id = 33187;
                itemDef.modelId = 47062;
                itemDef.name = "Chest Of Malar";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1280;
                itemDef.xan2d = 443;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 5;
                itemDef.maleModel0 = 47063;
                itemDef.femaleModel0 = 47063;
                break;
            case 33188:
                itemDef.setDefaults();
                itemDef.id = 33188;
                itemDef.modelId = 47064;
                itemDef.name = "Bottoms Of Malar";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1680;
                itemDef.xan2d = 583;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 3;
                itemDef.maleModel0 = 47065;
                itemDef.femaleModel0 = 47065;
                break;
            case 33189:
                itemDef.setDefaults();
                itemDef.id = 33189;
                itemDef.modelId = 47068;
                itemDef.name = "Dragon Guard Helm";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 980;
                itemDef.xan2d = 217;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 0;
                itemDef.maleModel0 = 47069;
                itemDef.femaleModel0 = 47069;
                break;
            case 33190:
                itemDef.setDefaults();
                itemDef.id = 33190;
                itemDef.modelId = 47070;
                itemDef.name = "Dragon Guard Chest";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1280;
                itemDef.xan2d = 443;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 5;
                itemDef.maleModel0 = 47071;
                itemDef.femaleModel0 = 47071;
                break;
            case 33191:
                itemDef.setDefaults();
                itemDef.id = 33191;
                itemDef.modelId = 47072;
                itemDef.name = "Dragon Guard Bottoms";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1680;
                itemDef.xan2d = 583;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 3;
                itemDef.maleModel0 = 47073;
                itemDef.femaleModel0 = 47073;
                break;
            case 33195:
                itemDef.setDefaults();
                itemDef.id = 33195;
                itemDef.modelId = 47081;
                itemDef.name = "Blood Helm";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 980;
                itemDef.xan2d = 217;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 0;
                itemDef.maleModel0 = 47082;
                itemDef.femaleModel0 = 47082;
                break;
            case 33196:
                itemDef.setDefaults();
                itemDef.id = 33196;
                itemDef.modelId = 47083;
                itemDef.name = "Blood Chest";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1280;
                itemDef.xan2d = 443;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 5;
                itemDef.maleModel0 = 47084;
                itemDef.femaleModel0 = 47084;
                break;
            case 33197:
                itemDef.setDefaults();
                itemDef.id = 33197;
                itemDef.modelId = 47085;
                itemDef.name = "Blood Bottoms";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1680;
                itemDef.xan2d = 583;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -2;
                itemDef.yOffset2d = 3;
                itemDef.maleModel0 = 47086;
                itemDef.femaleModel0 = 47086;
                break;
            case 33198:
                itemDef.setDefaults();
                itemDef.id = 33198;
                itemDef.modelId = 47087;
                itemDef.name = "Reaper Cape";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1780;
                itemDef.xan2d = 548;
                itemDef.yan2d = 791;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 4;
                itemDef.yOffset2d = -2;
                itemDef.maleModel0 = 47088;
                itemDef.femaleModel0 = 47088;
                break;
            case 33199:
                itemDef.setDefaults();
                itemDef.id = 33199;
                itemDef.modelId = 47089;
                itemDef.name = "Reaper Helm";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 680;
                itemDef.xan2d = 0;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = -8;
                itemDef.maleModel0 = 47090;
                itemDef.femaleModel0 = 47090;
                break;
            case 33200:
                itemDef.setDefaults();
                itemDef.id = 33200;
                itemDef.modelId = 47091;
                itemDef.name = "Reaper Chest";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1380;
                itemDef.xan2d = 478;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 0;
                itemDef.maleModel0 = 47092;
                itemDef.femaleModel0 = 47092;
                break;
            case 33201:
                itemDef.setDefaults();
                itemDef.id = 33201;
                itemDef.modelId = 47093;
                itemDef.name = "Reaper Bottoms";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = 1980;
                itemDef.xan2d = 320;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 18;
                itemDef.maleModel0 = 47094;
                itemDef.femaleModel0 = 47094;
                break;
            case 33202:
                itemDef.setDefaults();
                itemDef.id = 33202;
                itemDef.modelId = 47095;
                itemDef.name = "Demon X Sword";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 2080;
                itemDef.xan2d = 496;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 0;
                itemDef.maleModel0 = 47096;
                itemDef.femaleModel0 = 47096;
                break;
            case 33203:
                itemDef.setDefaults();
                itemDef.id = 33203;
                itemDef.modelId = 47097;
                itemDef.name = "Demon X Scythe";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1580;
                itemDef.xan2d = 320;
                itemDef.yan2d = 1104;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -7;
                itemDef.yOffset2d = -5;
                itemDef.maleModel0 = 47221;
                itemDef.femaleModel0 = 47221;
                break;
            case 33204:
                itemDef.setDefaults();
                itemDef.id = 33204;
                itemDef.modelId = 47099;
                itemDef.name = "Demon X Spear";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1580;
                itemDef.xan2d = 320;
                itemDef.yan2d = 1052;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -4;
                itemDef.yOffset2d = 12;
                itemDef.maleModel0 = 47100;
                itemDef.femaleModel0 = 47100;
                break;
            case 33205:
                itemDef.setDefaults();
                itemDef.id = 33205;
                itemDef.modelId = 47101;
                itemDef.name = "Demon X Staff";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1280;
                itemDef.xan2d = 320;
                itemDef.yan2d = 1104;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 0;
                itemDef.maleModel0 = 47102;
                itemDef.femaleModel0 = 47102;
                break;
            case 33206:
                itemDef.setDefaults();
                itemDef.id = 33206;
                itemDef.modelId = 47103;
                itemDef.name = "Ascension Crossbow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 880;
                itemDef.xan2d = 344;
                itemDef.yan2d = 1278;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 1;
                itemDef.yOffset2d = 4;
                itemDef.maleModel0 = 47104;
                itemDef.femaleModel0 = 47104;
                break;
            case 33207:
                itemDef.setDefaults();
                itemDef.id = 33207;
                itemDef.modelId = 47108;
                itemDef.name = "Demon X Bow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1780;
                itemDef.xan2d = 530;
                itemDef.yan2d = 1591;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 15;
                itemDef.yOffset2d = -8;
                itemDef.maleModel0 = 47109;
                itemDef.femaleModel0 = 47109;
                break;
            case 33208:
                itemDef.setDefaults();
                itemDef.name = "@gre@Lil' Groot";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("Groot.png");
                break;
            case 33209:
                itemDef.setDefaults();
                itemDef.name = "@gre@Xmas Box";
                itemDef.interfaceOptions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("xmas_box.png");
                break;
            case 33210:
                itemDef.setDefaults();
                itemDef.name = "lil' Gingie";
                //itemDef.description = "The most powerful pet, see ::foepets for full list of perks.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.modelId = 60034;
                itemDef.zoom2d = 1780;
                itemDef.xan2d = 240;
                itemDef.yan2d = 26;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -1;
                itemDef.yOffset2d = 17;
                break;
            case 33211:
                itemDef.setDefaults();
                itemDef.name = "lil' Elf";
                //itemDef.description = "The most powerful pet, see ::foepets for full list of perks.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.modelId = 60033;
                itemDef.zoom2d = 1780;
                itemDef.xan2d = 287;
                itemDef.yan2d = 217;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 5;
                itemDef.yOffset2d = 43;
                break;
            case 33212:
                itemDef.setDefaults();
                itemDef.name = "lil' Evil Snowman";
                //itemDef.description = "The most powerful pet, see ::foepets for full list of perks.";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.modelId = 46750;
                itemDef.zoom2d = 2980;
                itemDef.xan2d = 165;
                itemDef.yan2d = 0;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 0;
                itemDef.yOffset2d = 149;
                break;
            case 33213:
                itemDef.setDefaults();
                itemDef.name = "Christmas Pudding";
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("pudding.png");
                break;
            case 33214:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5309.png");
                break;
            case 33215:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5310.png");
                break;
            case 33216:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5311.png");
                break;
            case 33217:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5312.png");
                break;
            case 33218:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5313.png");
                break;
            case 33219:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5314.png");
                break;
            case 33220:
                itemDef.setDefaults();
                itemDef.name = "Faster melee";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5315.png");
                break;
            case 33221:
                itemDef.setDefaults();
                itemDef.name = "Faster Range";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5316.png");
                break;
            case 33222:
                itemDef.setDefaults();
                itemDef.name = "Faster Magic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5317.png");
                break;
            case 33223:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5318.png");
                break;
            case 33224:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5319.png");
                break;
            case 33225:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5320.png");
                break;
            case 33226:
                itemDef.setDefaults();
                itemDef.name = "3x Xp Bonus";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5321.png");
                break;
            case 33227:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5322.png");
                break;
            case 33228:
                itemDef.setDefaults();
                itemDef.name = "Simplified Farming";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5323.png");
                break;
            case 33229:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5324.png");
                break;
            case 33230:
                itemDef.setDefaults();
                itemDef.name = "Maximum Damage";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5325.png");
                break;
            case 33231:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5326.png");
                break;
            case 33232:
                itemDef.setDefaults();
                itemDef.name = "Meteor";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5327.png");
                break;
            case 33233:
                itemDef.setDefaults();
                itemDef.name = "AOE Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5328.png");
                break;
            case 33234:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" } ;
                itemDef.stackable = false;
                itemDef.createCustomSprite("5329.png");
                break;
            case 33235:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5330.png");
                break;
            case 33236:
                itemDef.setDefaults();
                itemDef.name = "Relic";
                itemDef.interfaceOptions = new String[] { "Attune", null, null, null, "Destroy" };
                itemDef.stackable = false;
                itemDef.createCustomSprite("5335.png");
                break;
            case 5020:
//                itemDef.setDefaults();
                itemDef.name = "Durial Ticket";
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, "Destroy" };
                break;
            case 11740:
//                itemDef.setDefaults();
                itemDef.name = "Elon Musk Island Scroll";
                itemDef.interfaceOptions = new String[] { "Consume", null, null, null, "Destroy" };
                break;
            case 26269:
                itemDef.setDefaults();
                itemDef.id = 26269;
                itemDef.modelId = 49477;
                itemDef.name = "Demon X Crossbow";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                itemDef.zoom2d = 1280;
                itemDef.xan2d = 322;
                itemDef.yan2d = 200;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = 6;
                itemDef.yOffset2d = 48;
                itemDef.maleModel0 = 60033;//49478
                itemDef.femaleModel0 = 60033;
                break;
            case 23859:
                itemDef.name = "Bloodbark Cape";
                break;
            case 22993:
                itemDef.name = "Seed pack";
                itemDef.interfaceOptions = new String[] { "Open", null, "Open-All", null, "Drop" };
                //itemDef.description = "I can see some seeds in here.";
                break;
            case 11738:
                itemDef.name = "Herb box";
                itemDef.interfaceOptions = new String[] { "Open", null, "Open-All", null, "Drop" };
                itemDef.modelId =  60038;
                //itemDef.description = "I can see some herbs in here.";
                break;
            case 26858:
                itemDef.name = "AFK Robes";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 26860:
                itemDef.name = "AFK Robes";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 26862:
                itemDef.name = "AFK Robes";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 26914:
                itemDef.name = "Collectors Amulet";
                break;
            case 28274:
                itemDef.name = "Smoked gem";
                break;
            case 28669:
                itemDef.name = "Pheasant";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 10998:
                itemDef.name = "13th Pet Goblin";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 24864:
                itemDef.name = "Maniacal Monkey";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 24863:
                itemDef.name = "Zombie Monkey";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                break;

            case 19903:
                //skele set
                itemDef.name = "Skele set";
                break;
            case 19911:
                //clown set
                itemDef.name = "Clown set";
                break;
            case 20280:
                //ornate set
                itemDef.name = "Ornate set";
                break;
            case 20281:
                //ankou set
                itemDef.name = "Ankou set";
                break;
            case 20282:
                //evil chicken set
                itemDef.name = "Evil Chicken set";
                break;
            case 24405:
                itemDef.name = "Twisted hat";
                break;
            case 24407:
                itemDef.name = "Twisted coat";
                break;
            case 24409:
                itemDef.name = "Twisted trousers";
                break;
            case 24411:
                itemDef.name = "Twisted boots";
                break;
            case 11279:
                itemDef.name = "Baby Green Dragon";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 33241:
                itemDef.name = "Baby Blue Dragon";
                itemDef.modelId = 60045;
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(11279).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(11279).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(11279).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(11279).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(11279).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(11279).yOffset2d;
                itemDef.stackable = false;
                break;
            case 33240:
                itemDef.name = "Baby Red Dragon";
                itemDef.modelId = 60044;
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(11279).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(11279).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(11279).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(11279).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(11279).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(11279).yOffset2d;
                itemDef.stackable = false;
                break;
            case 33242:
                itemDef.name = "Baby Black Dragon";
                itemDef.modelId = 60046;
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(11279).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(11279).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(11279).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(11279).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(11279).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(11279).yOffset2d;
                itemDef.stackable = false;
                break;
            case 33243:
                itemDef.setDefaults();
                itemDef.name = "@red@Realm Nyx";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                itemDef.stackable = false;
                itemDef.modelId = 60047;
                itemDef.zoom2d = 4280;
                itemDef.xan2d = 270;
                itemDef.yan2d = 322;
                itemDef.zan2d = 0;
                itemDef.xOffset2d = -1;
                itemDef.yOffset2d = 83;
                break;
            case 25959:
                itemDef.name = "Infernal Crystal";
                itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
                break;
            case 23680:
                itemDef.name = "Crystal Pickaxe";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 23673:
                itemDef.name = "Crystal Axe";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 23762:
                itemDef.name = "Crystal Harpoon";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 6644:
                itemDef.name = "@blu@Crystal Armour";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 6642:
                itemDef.name = "@gre@Crystal Armour";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 6641:
                itemDef.name = "@yel@Crystal Armour";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 6650:
                itemDef.name = "@bla@Crystal Armour";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 6645:
                itemDef.name = "@MAG@Crystal Armour";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 6640:
                itemDef.name = "@red@Crystal Armour";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 6646:
                itemDef.name = "@whi@Crystal Armour";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                itemDef.stackable = false;
                break;
            case 24866:  //Shoulder Monkey
            case 24862:  //Shoulder Monkey
            case 19556:  //Shoulder Monkey
            case 24867:  //Shoulder Monkey
            case 24865:  //Shoulder Monkey
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                break;
            case 20211:
                itemDef.name = "Cape of darkness";
                itemDef.stackable = false;
                break;
            case 10595:
                itemDef.name = "Penguin Suit";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 6722:
                itemDef.name = "Zombie head";
                itemDef.interfaceOptions = new String[] { "Talk-At", null, "Display", null, "Drop" };
                break;
            case 4079:
                itemDef.name = "Yo-Yo";
                itemDef.interfaceOptions = new String[] { "Play", null, "Loop", "Walk", null,};
                break;
            case 28136:
                itemDef.name = "Forestry backpack";
                itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
                break;
            case 33245:
                itemDef.id = 33245;
                itemDef.modelId = (60100);
                itemDef.name = "Centurians party Hat";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(1044).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(1044).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(1044).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(1044).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(1044).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(1044).yOffset2d;
                itemDef.maleModel0 = (60101);
                itemDef.femaleModel0 = (60102);
                break;
            case 33246:
                itemDef.id = 33246;
                itemDef.modelId = (60106);
                itemDef.name = "Sponges Skeleton Mask";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(9925).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9925).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9925).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9925).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9925).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9925).yOffset2d;
                itemDef.maleModel0 = (60111);
                itemDef.femaleModel0 = (60117);
                break;
            case 33247:
                itemDef.id = 33247;
                itemDef.modelId = (60103);
                itemDef.name = "Sponges Skeleton Body";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(9924).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9924).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9924).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9924).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9924).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9924).yOffset2d;
                itemDef.maleModel0 = (60113);
                itemDef.maleModel1 = (60108);
                itemDef.femaleModel0 = (60119);
                itemDef.femaleModel1 = (60114);
                break;
            case 33248:
                itemDef.id = 33248;
                itemDef.modelId = (60107);
                itemDef.name = "Sponges Skeleton Legs";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(9923).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9923).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9923).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9923).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9923).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9923).yOffset2d;
                itemDef.maleModel0 = (60112);
                itemDef.femaleModel0 = (60118);
                break;
            case 33249:
                itemDef.id = 33249;
                itemDef.modelId = (60104);
                itemDef.name = "Sponges Skeleton Boots";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(9921).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9921).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9921).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9921).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9921).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9921).yOffset2d;
                itemDef.maleModel0 = (60109);
                itemDef.femaleModel0 = (60115);
                break;
            case 33250:
                itemDef.id = 33250;
                itemDef.modelId = (60105);
                itemDef.name = "Sponges Skeleton Gloves";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(9922).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9922).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9922).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9922).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9922).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9922).yOffset2d;
                itemDef.maleModel0 = (60110);
                itemDef.femaleModel0 = (60116);
                break;
            case 33261:
                itemDef.id = 33261;
                itemDef.modelId = (60123);
                itemDef.name = "Green Skeleton Mask";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(9925).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9925).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9925).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9925).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9925).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9925).yOffset2d;
                itemDef.maleModel0 = (60128);
                itemDef.femaleModel0 = (60134);
                break;
            case 33262:
                itemDef.id = 33262;
                itemDef.modelId = (60120);
                itemDef.name = "Green Skeleton Body";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(9924).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9924).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9924).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9924).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9924).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9924).yOffset2d;
                itemDef.maleModel0 = (60130);
                itemDef.maleModel1 = (60125);
                itemDef.femaleModel0 = (60136);
                itemDef.femaleModel1 = (60131);
                break;
            case 33263:
                itemDef.id = 33263;
                itemDef.modelId = (60124);
                itemDef.name = "Green Skeleton Legs";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(9923).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9923).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9923).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9923).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9923).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9923).yOffset2d;
                itemDef.maleModel0 = (60129);
                itemDef.femaleModel0 = (60135);
                break;
            case 33264:
                itemDef.id = 33264;
                itemDef.modelId = (60121);
                itemDef.name = "Green Skeleton Boots";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(9921).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9921).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9921).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9921).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9921).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9921).yOffset2d;
                itemDef.maleModel0 = (60126);
                itemDef.femaleModel0 = (60132);
                break;
            case 33265:
                itemDef.id = 33265;
                itemDef.modelId = (60122);
                itemDef.name = "Green Skeleton Gloves";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
                itemDef.zoom2d = ItemDefinition.lookup(9922).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9922).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9922).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9922).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9922).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9922).yOffset2d;
                itemDef.maleModel0 = (60127);
                itemDef.femaleModel0 = (60133);
                break;
            case 33266:
                itemDef.id = 33266;
                itemDef.modelId = (60138);
                itemDef.name = "Red Skeleton Mask";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop"};
                itemDef.zoom2d = ItemDefinition.lookup(9925).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9925).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9925).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9925).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9925).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9925).yOffset2d;
                itemDef.maleModel0 = (60143);
                itemDef.femaleModel0 = (60149);
                break;
            case 33267:
                itemDef.id = 33267;
                itemDef.modelId = (60152);
                itemDef.name = "Red Skeleton Body";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop"};
                itemDef.zoom2d = ItemDefinition.lookup(9924).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9924).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9924).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9924).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9924).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9924).yOffset2d;
                itemDef.maleModel0 = (60145);
                itemDef.maleModel1 = (60140);
                itemDef.femaleModel0 = (60151);
                itemDef.femaleModel1 = (60146);
                break;
            case 33268:
                itemDef.id = 33268;
                itemDef.modelId = (60139);
                itemDef.name = "Red Skeleton Legs";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop"};
                itemDef.zoom2d = ItemDefinition.lookup(9923).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9923).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9923).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9923).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9923).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9923).yOffset2d;
                itemDef.maleModel0 = (60144);
                itemDef.femaleModel0 = (60150);
                break;
            case 33269:
                itemDef.id = 33269;
                itemDef.modelId = (60136);
                itemDef.name = "Red Skeleton Boots";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop"};
                itemDef.zoom2d = ItemDefinition.lookup(9921).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9921).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9921).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9921).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9921).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9921).yOffset2d;
                itemDef.maleModel0 = (60141);
                itemDef.femaleModel0 = (60147);
                break;
            case 33270:
                itemDef.id = 33270;
                itemDef.modelId = (60137);
                itemDef.name = "Red Skeleton gloves";
                itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop"};
                itemDef.zoom2d = ItemDefinition.lookup(9922).zoom2d;
                itemDef.xan2d = ItemDefinition.lookup(9922).xan2d;
                itemDef.yan2d = ItemDefinition.lookup(9922).yan2d;
                itemDef.zan2d = ItemDefinition.lookup(9922).zan2d;
                itemDef.xOffset2d = ItemDefinition.lookup(9922).xOffset2d;
                itemDef.yOffset2d = ItemDefinition.lookup(9922).yOffset2d;
                itemDef.maleModel0 = (60142);
                itemDef.femaleModel0 = (60148);
                break;

            case 28301:
            case 28295:
            case 28304:
            case 28298:
            case 28285:
            case 28279:
            case 28281:
            case 28283:
            case 28293:
            case 28287:
            case 28291:
            case 28289:
                itemDef.interfaceOptions = new String[] { null, null, null, null, null };
                break;
            case 22947:
                itemDef.name = "Scroll Of Levitation";
                itemDef.interfaceOptions = new String[] { null, "Equip", null, null, "Drop" };
                break;
            case 25646:
                itemDef.name = "@gre@$100 Deals chest";
                break;
            case 25649:
                itemDef.name = "@blu@$250 Deals chest";
                break;
            case 25648:
                itemDef.name = "@cya@$750 Deals chest";
                break;
            case 25650:
                itemDef.name = "@red@$999 Deals chest";
                break;
            case 33251:
                itemDef.name = "Donation Coin";
                itemDef.createCustomSprite("dono_coin.png");
                break;
            case 33252:  //Coin Token
            case 33253:  //Coin Token
            case 33254:  //Coin Token
            case 33255:  //Coin Token
            case 33256:  //Coin Token
            case 33257:  //Coin Token
            case 33258:  //Coin Token
            case 33259:  //Coin Token
                itemDef.name = "Coinflip Token";
                String color = "";
                switch (itemId) {
                    case 33252: color = "blue"; break;
                    case 33253: color = "green"; break;
                    case 33254: color = "orange"; break;
                    case 33255: color = "pink"; break;
                    case 33256: color = "purple"; break;
                    case 33257: color = "red"; break;
                    case 33258: color = "teal"; break;
                    case 33259: color = "yellow"; break;
                }
                itemDef.createCustomSprite(color + "_coin.png");
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                break;
            case 33260:
                itemDef.name = "Phone";
                itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
                itemDef.modelId = (60053);
                itemDef.zoom2d = 1991;
                itemDef.xan2d = 96;
                itemDef.yan2d = 165;
                itemDef.zan2d = 9;
                itemDef.xOffset2d = 9;
                itemDef.yOffset2d = 26;
                break;
        }
    }

    public static ItemDefinition lookup(int itemId) {
        for (int count = 0; count < 10; count++)
            if (cache[count].id == itemId)
                return cache[count];

        if (itemId == -1)
            itemId = 0;
        if (newCustomItems(itemId) != null) {
            return newCustomItems(itemId);
        }

        if (itemId >= streamIndices.length)
            itemId = 0;

        cacheIndex = (cacheIndex + 1) % 10;
        ItemDefinition itemDef = cache[cacheIndex];

        item_data.currentPosition = streamIndices[itemId];
        itemDef.id = itemId;
        itemDef.setDefaults();
        itemDef.decode(item_data);

        if (itemDef.noted_item_id != -1)
            itemDef.toNote();

        int id = itemDef.id;

        customItems(itemDef.id);
        itemDef.id = id;
        return itemDef;
    }


    void method2789(ItemDefinition var1, ItemDefinition var2) {
        modelId = var1.modelId * 1;
        zoom2d = var1.zoom2d * 1;
        xan2d = 1 * var1.xan2d;
        yan2d = 1 * var1.yan2d;
        zan2d = 1 * var1.zan2d;
        xOffset2d = 1 * var1.xOffset2d;
        yOffset2d = var1.yOffset2d * 1;
        colorReplace = var2.colorReplace;
        colorFind = var2.colorFind;
         //originalTextureColors = var2.originalTextureColors;
        // modifiedTextureColors = var2.modifiedTextureColors;
        name = var2.name;
        members = var2.members;
        stackable = var2.stackable;
        maleModel0 = 1 * var2.maleModel0;
        maleModel1 = 1 * var2.maleModel1;
        maleModel2 = 1 * var2.maleModel2;
        femaleModel0 = var2.femaleModel0 * 1;
        femaleModel1 = var2.femaleModel1 * 1;
        femaleModel2 = 1 * var2.femaleModel2;
        maleHeadModel = 1 * var2.maleHeadModel;
        maleHeadModel2 = var2.maleHeadModel2 * 1;
        femaleHeadModel = var2.femaleHeadModel * 1;
        femaleHeadModel2 = var2.femaleHeadModel2 * 1;
        team = var2.team * 1;
        options = var2.options;
        interfaceOptions = new String[5];
        equipActions = new String[5];
        if (null != var2.interfaceOptions) {
            for (int var4 = 0; var4 < 4; ++var4) {
                interfaceOptions[var4] = var2.interfaceOptions[var4];
            }
        }

        interfaceOptions[4] = "Discard";
        cost = 0;
    }

    void toPlaceholder(ItemDefinition var1, ItemDefinition var2) {
        modelId = var1.modelId * 1;
        zoom2d = 1 * var1.zoom2d;
        xan2d = var1.xan2d * 1;
        yan2d = var1.yan2d * 1;
        zan2d = var1.zan2d * 1;
        xOffset2d = 1 * var1.xOffset2d;
        yOffset2d = var1.yOffset2d * 1;
        colorReplace = var1.colorReplace;
        colorFind = var1.colorFind;
        textureFind = var1.textureFind;
        textureReplace = var1.textureReplace;
        stackable = var1.stackable;
        name = var2.name;
        cost = 0;
    }

    public static Sprite getSmallSprite(int itemId) {
        return getSmallSprite(itemId, 1);
    }

    public static Sprite getSprite(int itemId, int stackSize, int outlineColor, boolean noted, int border,int shadow) {
        if (outlineColor == 0) {
            Sprite sprite = (Sprite) sprites.get(itemId);

            if (sprite != null && sprite.maxHeight != stackSize && sprite.maxHeight != -1) {
                sprite.remove();
                sprite = null;
            }

            if (sprite != null) {
                return sprite;
            }
        }

        ItemDefinition definition = lookup(itemId);

        if (definition.countObj == null) {
            stackSize = -1;
        }

        if (stackSize > 1) {
            int stack_item_id = -1;

            for (int j1 = 0; j1 < 10; j1++) {
                if (stackSize >= definition.countCo[j1] && definition.countCo[j1] != 0) {
                    stack_item_id = definition.countObj[j1];
                }
            }

            if (stack_item_id != -1) {
                definition = lookup(stack_item_id);
            }
        }

        Model model = definition.getModel(1);

        if (model == null) {
            return null;
        }

        Sprite sprite = null;


        Sprite enabledSprite = new Sprite(32, 32);
        int centerX = Rasterizer3D.originViewX;
        int centerY = Rasterizer3D.originViewY;
        int[] lineOffsets = Rasterizer3D.scanOffsets;
        int[] pixels = Rasterizer2D.pixels;
        int width = Rasterizer2D.width;
        int height = Rasterizer2D.height;
        int vp_left = Rasterizer2D.leftX;
        int vp_right = Rasterizer2D.bottomX;
        int vp_top = Rasterizer2D.topY;
        int vp_bottom = Rasterizer2D.bottomY;
        Rasterizer3D.world = false;
        Rasterizer3D.aBoolean1464 = false;
        Rasterizer2D.initDrawingArea(32, 32, enabledSprite.myPixels);
        Rasterizer2D.drawItemBox(0, 0, 32, 32, 0);
        Rasterizer3D.useViewport();
        int k3 = definition.zoom2d;

        if (outlineColor == -1) {
            k3 = (int) (k3 * 1.5D);
        }

        if (outlineColor > 0) {
            k3 = (int) (k3 * 1.04D);
        }

        int l3 = Rasterizer3D.SINE[definition.xan2d] * k3 >> 16;
        int i4 = Rasterizer3D.COSINE[definition.xan2d] * k3 >> 16;
        Rasterizer3D.renderOnGpu = true;
        model.renderModel(definition.yan2d, definition.zan2d, definition.xan2d, definition.xOffset2d,
                l3 + model.modelBaseY / 2 + definition.yOffset2d, i4 + definition.yOffset2d);
        Rasterizer3D.renderOnGpu = false;

        enabledSprite.highlight(1);

        if (outlineColor == 0) {
            enabledSprite.shadow(3153952);
        }

        Rasterizer2D.initDrawingArea(32, 32, enabledSprite.myPixels);

        if (noted) {
            int old_w = sprite.maxWidth;
            int old_h = sprite.maxHeight;
            sprite.maxWidth = 32;
            sprite.maxHeight = 32;
            sprite.drawSprite(0, 0);
            sprite.maxWidth = old_w;
            sprite.maxHeight = old_h;
        }
        if (outlineColor == 0 && !definition.animateInventory) {
            sprites.put(enabledSprite, itemId);
        }

        Rasterizer2D.initDrawingArea(height, width, pixels);
        Rasterizer2D.setDrawingArea(vp_bottom, vp_left, vp_right, vp_top);
        Rasterizer3D.originViewX = centerX;
        Rasterizer3D.originViewY = centerY;
        Rasterizer3D.scanOffsets = lineOffsets;
        Rasterizer3D.aBoolean1464 = true;
        Rasterizer3D.world = true;
        enabledSprite.maxWidth = definition.stackable ? 33 : 32;
        enabledSprite.maxHeight = stackSize;
        return enabledSprite;
    }


    public static Sprite getSprite(int itemId, int stackSize, int outlineColor) {
        if (outlineColor == 0) {
            Sprite sprite = (Sprite) sprites.get(itemId);
            if (sprite != null && sprite.maxHeight != stackSize && sprite.maxHeight != -1) {

                sprite.remove();
                sprite = null;
            }
            if (sprite != null)
                return sprite;
        }
        ItemDefinition itemDef = lookup(itemId);
        if (itemDef.countObj == null)
            stackSize = -1;
        if (stackSize > 1) {
            int stack_item_id = -1;
            for (int j1 = 0; j1 < 10; j1++)
                if (stackSize >= itemDef.countCo[j1] && itemDef.countCo[j1] != 0)
                    stack_item_id = itemDef.countObj[j1];

            if (stack_item_id != -1)
                itemDef = lookup(stack_item_id);
        }
        Model model = itemDef.getModel(1);
        if (model == null)
            return null;
        Sprite sprite = null;
        if (itemDef.noted_item_id != -1) {
            sprite = getSprite(itemDef.unnoted_item_id, 10, -1);
            if (sprite == null)
                return null;
        }
        if (itemDef.customSpriteLocation != null)
        {
            return new Sprite(itemDef.customSpriteLocation);
        }
        Sprite enabledSprite = new Sprite(32, 32);
        int centerX = Rasterizer3D.originViewX;
        int centerY = Rasterizer3D.originViewY;
        int[] lineOffsets = Rasterizer3D.scanOffsets;
        int[] pixels = Rasterizer2D.pixels;
        int width = Rasterizer2D.width;
        int height = Rasterizer2D.height;
        int vp_left = Rasterizer2D.leftX;
        int vp_right = Rasterizer2D.bottomX;
        int vp_top = Rasterizer2D.topY;
        int vp_bottom = Rasterizer2D.bottomY;
        Rasterizer3D.world = false;
        Rasterizer3D.aBoolean1464 = false;
        Rasterizer2D.initDrawingArea(32, 32, enabledSprite.myPixels);
        Rasterizer2D.drawItemBox(0, 0, 32, 32, 0);
        Rasterizer3D.useViewport();
        int k3 = itemDef.zoom2d;
        if (outlineColor == -1)
            k3 = (int) ((double) k3 * 1.5D);
        if (outlineColor > 0)
            k3 = (int) ((double) k3 * 1.04D);
        int l3 = Rasterizer3D.SINE[itemDef.xan2d] * k3 >> 16;
        int i4 = Rasterizer3D.COSINE[itemDef.xan2d] * k3 >> 16;
        Rasterizer3D.renderOnGpu = true;
        model.renderModel(itemDef.yan2d, itemDef.zan2d, itemDef.xan2d, itemDef.xOffset2d,
                l3 + model.modelBaseY / 2 + itemDef.yOffset2d, i4 + itemDef.yOffset2d);

        enabledSprite.outline(1);
        if (outlineColor > 0) {
            enabledSprite.outline(16777215);
        }
        if (outlineColor == 0) {
            enabledSprite.shadow(3153952);
        }

        Rasterizer2D.initDrawingArea(32, 32, enabledSprite.myPixels);

        if (itemDef.noted_item_id != -1) {
            int old_w = sprite.maxWidth;
            int old_h = sprite.maxHeight;
            sprite.maxWidth = 32;
            sprite.maxHeight = 32;
            sprite.drawSprite(0, 0);
            sprite.maxWidth = old_w;
            sprite.maxHeight = old_h;
        }
        if (outlineColor == 0 && (itemId != 10198 && itemId != 10199 && itemId != 33448) && !itemDef.animateInventory)
            sprites.put(enabledSprite, itemId);
        Rasterizer2D.initDrawingArea(height, width, pixels);
        Rasterizer2D.setDrawingArea(vp_bottom, vp_left, vp_right, vp_top);
        Rasterizer3D.originViewX = centerX;
        Rasterizer3D.originViewY = centerY;
        Rasterizer3D.scanOffsets = lineOffsets;
        Rasterizer3D.aBoolean1464 = true;
        Rasterizer3D.world = true;
        if (itemDef.stackable)
            enabledSprite.maxWidth = 33;
        else
            enabledSprite.maxWidth = 32;
        enabledSprite.maxHeight = stackSize;
        return enabledSprite;
    }

    public static Sprite getSmallSprite(int itemId, int stackSize) {

        ItemDefinition itemDef = lookup(itemId);
        if (itemDef.countObj == null)
            stackSize = -1;
        if (stackSize > 1) {
            int stack_item_id = -1;
            for (int j1 = 0; j1 < 10; j1++)
                if (stackSize >= itemDef.countCo[j1] && itemDef.countCo[j1] != 0)
                    stack_item_id = itemDef.countObj[j1];

            if (stack_item_id != -1)
                itemDef = lookup(stack_item_id);
        }
        Model model = itemDef.getModel(1);
        if (model == null)
            return null;
        Sprite sprite = null;
        if (itemDef.noted_item_id != -1) {
            sprite = getSprite(itemDef.unnoted_item_id, 10, -1);
            if (sprite == null)
                return null;
        }
        Sprite enabledSprite = new Sprite(18, 18);
        int centerX = Rasterizer3D.originViewX;
        int centerY = Rasterizer3D.originViewY;
        int[] lineOffsets = Rasterizer3D.scanOffsets;
        int[] pixels = Rasterizer2D.pixels;
        int width = Rasterizer2D.width;
        int height = Rasterizer2D.height;
        int vp_left = Rasterizer2D.leftX;
        int vp_right = Rasterizer2D.bottomX;
        int vp_top = Rasterizer2D.topY;
        int vp_bottom = Rasterizer2D.bottomY;
        Rasterizer3D.world = false;
        Rasterizer3D.aBoolean1464 = false;
        Rasterizer2D.initDrawingArea(18, 18, enabledSprite.myPixels);
        Rasterizer2D.drawItemBox(0, 0, 18, 18, 0);
        Rasterizer3D.useViewport();
        int k3 = (int) (itemDef.zoom2d * 1.6D);

        int l3 = Rasterizer3D.SINE[itemDef.xan2d] * k3 >> 16;
        int i4 = Rasterizer3D.COSINE[itemDef.xan2d] * k3 >> 16;
        Rasterizer3D.renderOnGpu = true;
        model.renderModel(itemDef.yan2d, itemDef.zan2d, itemDef.xan2d, itemDef.xOffset2d,
                l3 + model.modelBaseY / 2 + itemDef.yOffset2d, i4 + itemDef.yOffset2d);

        enabledSprite.outline(1);

        if (itemDef.noted_item_id != -1) {
            int old_w = sprite.maxWidth;
            int old_h = sprite.maxHeight;
            sprite.maxWidth = 18;
            sprite.maxHeight = 18;
            sprite.drawSprite(0, 0);
            sprite.maxWidth = old_w;
            sprite.maxHeight = old_h;
        }

        sprites.put(enabledSprite, itemId);
        Rasterizer2D.initDrawingArea(height, width, pixels);
        Rasterizer2D.setDrawingArea(vp_bottom, vp_left, vp_right, vp_top);
        Rasterizer3D.originViewX = centerX;
        Rasterizer3D.originViewY = centerY;
        Rasterizer3D.scanOffsets = lineOffsets;
        Rasterizer3D.aBoolean1464 = true;
        Rasterizer3D.world = true;

        enabledSprite.maxWidth = 18;
        enabledSprite.maxHeight = 18;
        return enabledSprite;
    }

    public static Sprite getSprite(int itemId, int stackSize, int zoom, int outlineColor) {
        ItemDefinition itemDef = lookup(itemId);
        if (itemDef.countObj == null)
            stackSize = -1;
        if (stackSize > 1) {
            int stack_item_id = -1;
            for (int j1 = 0; j1 < 10; j1++)
                if (stackSize >= itemDef.countCo[j1] && itemDef.countCo[j1] != 0)
                    stack_item_id = itemDef.countObj[j1];

            if (stack_item_id != -1)
                itemDef = lookup(stack_item_id);
        }
        Model model = itemDef.getModel(1);
        if (model == null)
            return null;
        Sprite sprite = new Sprite(90, 90);
        int centerX = Rasterizer3D.originViewX;
        int centerY = Rasterizer3D.originViewY;
        int[] lineOffsets = Rasterizer3D.scanOffsets;
        int[] pixels = Rasterizer2D.pixels;
        int width = Rasterizer2D.width;
        int height = Rasterizer2D.height;
        int vp_left = Rasterizer2D.leftX;
        int vp_right = Rasterizer2D.bottomX;
        int vp_top = Rasterizer2D.topY;
        int vp_bottom = Rasterizer2D.bottomY;
        Rasterizer3D.world = false;
        Rasterizer3D.aBoolean1464 = false;
        Rasterizer2D.initDrawingArea(90, 90, sprite.myPixels);
        Rasterizer2D.drawItemBox(0, 0, 90, 90, 0);
        Rasterizer3D.useViewport();
        int l3 = Rasterizer3D.SINE[itemDef.xan2d] * zoom >> 15;
        int i4 = Rasterizer3D.COSINE[itemDef.xan2d] * zoom >> 15;
        Rasterizer3D.renderOnGpu = true;

        model.renderModel(itemDef.yan2d, itemDef.zan2d, itemDef.xan2d, itemDef.xOffset2d,
                l3 + model.modelBaseY / 2 + itemDef.yOffset2d, i4 + itemDef.yOffset2d);

        Rasterizer3D.renderOnGpu = false;
        sprite.outline(1);
        if (outlineColor > 0) {
            sprite.outline(16777215);
        }
        if (outlineColor == 0) {
            sprite.shadow(3153952);
        }
        Rasterizer2D.initDrawingArea(90, 90, sprite.myPixels);
        Rasterizer2D.initDrawingArea(height, width, pixels);
        Rasterizer2D.setDrawingArea(vp_bottom, vp_left, vp_right, vp_top);
        Rasterizer3D.originViewX = centerX;
        Rasterizer3D.originViewY = centerY;
        Rasterizer3D.scanOffsets = lineOffsets;
        Rasterizer3D.aBoolean1464 = true;
        Rasterizer3D.world = true;
        if (itemDef.stackable)
            sprite.maxWidth = 33;
        else
            sprite.maxWidth = 32;
        sprite.maxHeight = stackSize;
        return sprite;
    }

    public boolean isDialogueModelCached(int gender) {
        int model_1 = maleHeadModel;
        int model_2 = maleHeadModel2;
        if (gender == 1) {
            model_1 = femaleHeadModel;
            model_2 = femaleHeadModel2;
        }
        if (model_1 == -1)
            return true;
        boolean cached = Model.isCached(model_1);
        if (model_2 != -1 && !Model.isCached(model_2))
            cached = false;
        return cached;
    }

    public Model getChatEquipModel(int gender) {
        int dialogueModel = maleHeadModel;
        int dialogueHatModel = maleHeadModel2;
        if (gender == 1) {
            dialogueModel = femaleHeadModel;
            dialogueHatModel = femaleHeadModel2;
        }
        if (dialogueModel == -1)
            return null;
        Model dialogueModel_ = Model.getModel(dialogueModel);
        if (dialogueHatModel != -1) {
            Model hatModel_ = Model.getModel(dialogueHatModel);
            Model[] models = {dialogueModel_, hatModel_};
            dialogueModel_ = new Model(2, models);
        }
        if (colorReplace != null) {
            for (int i1 = 0; i1 < colorReplace.length; i1++)
                dialogueModel_.recolor(colorReplace[i1], colorFind[i1]);

        }
        if (textureReplace != null) {
            for (int i1 = 0; i1 < textureReplace.length; i1++)
                dialogueModel_.retexture((short) textureReplace[i1], (short) textureFind[i1]);
        }
        return dialogueModel_;
    }

    public boolean isEquippedModelCached(int gender) {
        int primaryModel = maleModel0;
        int secondaryModel = maleModel1;
        int emblem = maleModel2;
        if (gender == 1) {
            primaryModel = femaleModel0;
            secondaryModel = femaleModel1;
            emblem = femaleModel2;
        }
        if (primaryModel == -1)
            return true;
        boolean cached = Model.isCached(primaryModel);
        if (secondaryModel != -1 && !Model.isCached(secondaryModel))
            cached = false;
        if (emblem != -1 && !Model.isCached(emblem))
            cached = false;
        return cached;
    }

    public void createCustomSprite(String img) {
        customSpriteLocation = getCustomSprite(img);
    }

    public void createSmallCustomSprite(String img) {
        customSmallSpriteLocation = getCustomSprite(img);
    }


    private byte[] getCustomSprite(String img) {
        String location = (Sprite.location + Configuration.CUSTOM_ITEM_SPRITES_DIRECTORY + img).toLowerCase();
        byte[] spriteData = FileOperations.readFile(location);
        Preconditions.checkState(spriteData != null, "No sprite: " + location);
        return spriteData;
    }

    public byte[] customSpriteLocation;
    public byte[] customSmallSpriteLocation;


    public Model getEquippedModel(int gender) {
        int primaryModel = maleModel0;
        int secondaryModel = maleModel1;
        int emblem = maleModel2;

        if (gender == 1) {
            primaryModel = femaleModel0;
            secondaryModel = femaleModel1;
            emblem = femaleModel2;
        }

        if (primaryModel == -1)
            return null;
        Model primaryModel_ = Model.getModel(primaryModel);
        if (secondaryModel != -1)
            if (emblem != -1) {
                Model secondaryModel_ = Model.getModel(secondaryModel);
                Model emblemModel = Model.getModel(emblem);
                Model[] models = {primaryModel_, secondaryModel_, emblemModel};
                primaryModel_ = new Model(3, models);
            } else {
                Model model_2 = Model.getModel(secondaryModel);
                Model[] models = {primaryModel_, model_2};
                primaryModel_ = new Model(2, models);
            }
        if (gender == 0 && maleOffset != 0)
            primaryModel_.offsetBy(0, maleOffset, 0);
        if (gender == 1 && femaleOffset != 0)
            primaryModel_.offsetBy(0, femaleOffset, 0);

        if (colorReplace != null) {
            for (int i1 = 0; i1 < colorReplace.length; i1++)
                primaryModel_.recolor(colorReplace[i1], colorFind[i1]);

        }
        if (textureReplace != null) {
            for (int i1 = 0; i1 < textureReplace.length; i1++)
                primaryModel_.retexture((short) textureReplace[i1], (short) textureFind[i1]);
        }
        return primaryModel_;
    }

    private void setDefaults() {
        customSpriteLocation = null;
        customSmallSpriteLocation = null;
        equipActions = new String[]{"Remove", null, "Operate", null, null};
        modelId = 0;
        name = null;
        colorReplace = null;
        colorFind = null;
        textureReplace = null;
        textureFind = null;

        zoom2d = 2000;
        xan2d = 0;
        yan2d = 0;
        zan2d = 0;
        xOffset2d = 0;
        yOffset2d = 0;
        stackable = false;
        cost = 1;
        members = false;
        options = null;
        interfaceOptions = null;
        maleModel0 = -1;
        maleModel1 = -1;
        maleOffset = 0;
        femaleModel0 = -1;
        femaleModel1 = -1;
        femaleOffset = 0;
        maleModel2 = -1;
        femaleModel2 = -1;
        maleHeadModel = -1;
        maleHeadModel2 = -1;
        femaleHeadModel = -1;
        femaleHeadModel2 = -1;
        countObj = null;
        countCo = null;
        unnoted_item_id = -1;
        noted_item_id = -1;
        resizeX = 128;
        resizeY = 128;
        resizeZ = 128;
        ambient = 0;
        contrast = 0;
        team = 0;
        glowColor = -1;
    }

    private void copy(ItemDefinition copy) {
        yan2d = copy.yan2d;
        xan2d = copy.xan2d;
        zan2d = copy.zan2d;
        resizeX = copy.resizeX;
        resizeY = copy.resizeY;
        resizeZ = copy.resizeZ;
        zoom2d = copy.zoom2d;
        xOffset2d = copy.xOffset2d;
        yOffset2d = copy.yOffset2d;
        modelId = copy.modelId;
        stackable = copy.stackable;

    }

    private void toNote() {
        ItemDefinition itemDef = lookup(noted_item_id);
        modelId = itemDef.modelId;
        zoom2d = itemDef.zoom2d;
        xan2d = itemDef.xan2d;
        yan2d = itemDef.yan2d;

        zan2d = itemDef.zan2d;
        xOffset2d = itemDef.xOffset2d;
        yOffset2d = itemDef.yOffset2d;

        ItemDefinition itemDef_1 = lookup(unnoted_item_id);
        name = itemDef_1.name;
        members = itemDef_1.members;
        cost = itemDef_1.cost;
        stackable = true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getNote() {
        return noted_item_id;
    }

    @Override
    public int getLinkedNoteId() {
        return 0;
    }

    @Override
    public int getPlaceholderId() {
        return placeholder_id;
    }

    @Override
    public int getPlaceholderTemplateId() {
        return 0;
    }

    @Override
    public int getPrice() {
        return getPrice();
    }

    @Override
    public boolean isMembers() {
        return members;
    }

    @Override
    public boolean isTradeable() {
        return tradeable;
    }

    @Override
    public void setTradeable(boolean yes) {

    }

    @Override
    public int getIsStackable() {
        return 0;
    }

    @Override
    public int getMaleModel() {
        return 0;
    }

    @Override
    public String[] getInventoryActions() {
        return this.interfaceOptions;
    }

    @Override
    public String[] getGroundActions() {
        return this.options;
    }
    @Override
    public int getShiftClickActionIndex() {
        return shiftClickIndex;
    }

    @Override
    public void setShiftClickActionIndex(int shiftClickActionIndex) {
        this.shiftClickIndex = shiftClickActionIndex;
    }

    public Model getModel(int stack_size) {
        if (countObj != null && stack_size > 1) {
            int stack_item_id = -1;
            for (int k = 0; k < 10; k++)
                if (stack_size >= countCo[k] && countCo[k] != 0)
                    stack_item_id = countObj[k];

            if (stack_item_id != -1)
                return lookup(stack_item_id).getModel(1);
        }
        Model model = (Model) models.get(id);
        if (model != null)
            return model;
        model = Model.getModel(modelId);
        if (model == null)
            return null;
        if (resizeX != 128 || resizeY != 128 || resizeZ != 128)
            model.scale(resizeX, resizeZ, resizeY);
        if (colorReplace != null) {
            for (int l = 0; l < colorReplace.length; l++)
                model.recolor(colorReplace[l], colorFind[l]);

        }
        if (textureReplace != null) {
            for (int i1 = 0; i1 < textureReplace.length; i1++)
                model.retexture((short) textureReplace[i1], (short) textureFind[i1]);
        }
        int lightInt = 64 + ambient;
        int lightMag = 768 + contrast;
        model.light(lightInt, lightMag, -50, -10, -50, true);
        model.singleTile = true;
        models.put(model, id);
        return model;
    }

    @Override
    public int getInventoryModel() {
        return 0;
    }

    @Override
    public short[] getColorToReplaceWith() {
        return new short[0];
    }

    @Override
    public short[] getTextureToReplaceWith() {
        return new short[0];
    }

    @Override
    public RSIterableNodeHashTable getParams() {
        return null;
    }

    @Override
    public void setParams(IterableHashTable params) {

    }

    @Override
    public void setParams(RSIterableNodeHashTable params) {

    }

    public Model getUnshadedModel(int stack_size) {
        if (countObj != null && stack_size > 1) {
            int stack_item_id = -1;
            for (int count = 0; count < 10; count++)
                if (stack_size >= countCo[count] && countCo[count] != 0)
                    stack_item_id = countObj[count];

            if (stack_item_id != -1)
                return lookup(stack_item_id).getUnshadedModel(1);
        }
        Model model = Model.getModel(modelId);
        if (model == null)
            return null;
        if (colorReplace != null) {
            for (int colorPtr = 0; colorPtr < colorReplace.length; colorPtr++)
                model.recolor(colorReplace[colorPtr], colorFind[colorPtr]);

        }
        return model;
    }

    private void decode(Buffer buffer) {
        while (true) {
            int opcode = buffer.readUnsignedByte();
            if (opcode == 0)
                return;
            if (opcode == 1)
                modelId = buffer.readUShort();
            else if (opcode == 2)
                name = buffer.readStrings();
            else if (opcode == 4)
                zoom2d = buffer.readUShort();
            else if (opcode == 5)
                xan2d = buffer.readUShort();
            else if (opcode == 6)
                yan2d = buffer.readUShort();
            else if (opcode == 7) {
                xOffset2d = buffer.readUShort();
                if (xOffset2d > 32767)
                    xOffset2d -= 0x10000;
            } else if (opcode == 8) {
                yOffset2d = buffer.readUShort();
                if (yOffset2d > 32767)
                    yOffset2d -= 0x10000;
            } else if (opcode == 10) {
                buffer.readUShort();
            } else if (opcode == 11)
                stackable = true;
            else if (opcode == 12)
                cost = buffer.readInt();
            else if (opcode == 13)
                wearPos1 = buffer.readUnsignedByte();
            else if (opcode == 14)
                wearPos2 = buffer.readUnsignedByte();
            else if (opcode == 16)
                members = true;
            else if (opcode == 23) {
                maleModel0 = buffer.readUShort();
                maleOffset = buffer.readSignedByte();
            } else if (opcode == 24)
                maleModel1 = buffer.readUShort();
            else if (opcode == 25) {
                femaleModel0 = buffer.readUShort();
                femaleOffset = buffer.readSignedByte();
            } else if (opcode == 26) {
                femaleModel1 = buffer.readUShort();
            } else if (opcode == 27) {
                wearPos3 = buffer.readUnsignedByte();
            } else if (opcode >= 30 && opcode < 35) {
                if (options == null)
                    options = new String[5];
                options[opcode - 30] = buffer.readString();
                if (options[opcode - 30].equalsIgnoreCase("hidden"))
                    options[opcode - 30] = null;
            } else if (opcode >= 35 && opcode < 40) {
                if (interfaceOptions == null)
                    interfaceOptions = new String[5];
                interfaceOptions[opcode - 35] = buffer.readString();
            } else if (opcode == 40) {
                int length = buffer.readUnsignedByte();
                colorReplace = new int[length];
                colorFind = new int[length];
                for (int index = 0; index < length; index++) {
                    colorReplace[index] = buffer.readUShort();
                    colorFind[index] = buffer.readUShort();
                }
            } else if (opcode == 41) {
                int size = buffer.readUnsignedByte();
                textureFind = new int[size];
                textureReplace = new int[size];
                for (int index = 0; index < size; index++) {
                    textureFind[index] = (short) buffer.readUShort();
                    textureReplace[index] = (short) buffer.readUShort();
                }
            } else if (opcode == 42) {
                shiftClickIndex = buffer.readUnsignedByte();
            } else if (opcode == 65) {
                tradeable = true;
            } else if (opcode == 75) {
                weight = buffer.readUShort();
            } else if (opcode == 78)
                maleModel2 = buffer.readUShort();
            else if (opcode == 79)
                femaleModel2 = buffer.readUShort();
            else if (opcode == 90)
                maleHeadModel = buffer.readUShort();
            else if (opcode == 91)
                femaleHeadModel = buffer.readUShort();
            else if (opcode == 92)
                maleHeadModel2 = buffer.readUShort();
            else if (opcode == 93)
                femaleHeadModel2 = buffer.readUShort();
            else if (opcode == 94)
                category = buffer.readUShort();
            else if (opcode == 95)
                zan2d = buffer.readUShort();
            else if (opcode == 97)
                unnoted_item_id = buffer.readUShort();
            else if (opcode == 98)
                noted_item_id = buffer.readUShort();
            else if (opcode >= 100 && opcode < 110) {
                if (countObj == null) {
                    countObj = new int[10];
                    countCo = new int[10];
                }
                countObj[opcode - 100] = buffer.readUShort();
                countCo[opcode - 100] = buffer.readUShort();

            } else if (opcode == 110)
                resizeX = buffer.readUShort();
            else if (opcode == 111)
                resizeY = buffer.readUShort();
            else if (opcode == 112)
                resizeZ = buffer.readUShort();
            else if (opcode == 113)
                ambient = buffer.readSignedByte();
            else if (opcode == 114)
                contrast = buffer.readSignedByte() * 5;
            else if (opcode == 115)
                team = buffer.readUnsignedByte();
            else if (opcode == 139)
                bought_id = buffer.readUShort();
            else if (opcode == 140)
                bought_template_id = buffer.readUShort();
            else if (opcode == 148)
                placeholder_id = buffer.readUShort();
            else if (opcode == 149) {
                placeholder_template_id = buffer.readUShort();
            } else if (opcode == 249) {
                params = BufferExt.readStringIntParameters(buffer);
            }
            if (stackable) {
                weight = 0;
            }
        }
    }

    @Override
    public int getHaPrice() {
        return (int) (cost * 0.6);
    }

    @Override
    public boolean isStackable() {
        return stackable;
    }

    @Override
    public void resetShiftClickActionIndex() {
        this.shiftClickIndex = 0;
    }

    @Override
    public int getIntValue(int paramID) {
        return 0;
    }

    @Override
    public void setValue(int paramID, int value) {

    }

    @Override
    public String getStringValue(int paramID) {
        return null;
    }

    @Override
    public void setValue(int paramID, String value) {

    }
}