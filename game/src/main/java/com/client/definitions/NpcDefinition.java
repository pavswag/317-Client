package com.client.definitions;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Arrays;
//
//import org.apache.commons.io.FileUtils;

import com.client.*;
import com.client.model.Npcs;
import net.runelite.api.HeadIcon;
import net.runelite.api.IterableHashTable;
import net.runelite.rs.api.RSIterableNodeHashTable;
import net.runelite.rs.api.RSNPCComposition;


public final class NpcDefinition implements RSNPCComposition {

    public static NpcDefinition lookup(int i) {
        for (int j = 0; j < 20; j++)
            if (cache[j].npcId == i)
                return cache[j];

        /*if (i > 11839) {
            i = 11839;
        }*/

        anInt56 = (anInt56 + 1) % 20;
        NpcDefinition entityDef = cache[anInt56] = new NpcDefinition();
        stream.currentPosition = streamIndices[i];
        entityDef.npcId = i;
        entityDef.readValues(stream);
        if (i == Npcs.BOB_BARTER_HERBS) {
            entityDef.actions = new String[] { "Talk-to", "Prices", "Decant", "Clean", null };
        }
        if (i == Npcs.ZAHUR)
            entityDef.actions[0] = "Trade";
        if (i == Npcs.JOSSIK) {
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Talk-to";
            entityDef.actions[2] = "Trade";
        }
        if (i == 9460 || i == 1150 || i == 2912 || i == 2911 || i == 2910 || i == 6481
                || i == 3500 || i == 9459 || i == 9457 || i == 9458) {
            // Setting combat to zero to npcs that can't be attacked
            entityDef.combatLevel = 0;
        }
        if (i == 8679) {
            entityDef.name = "Batman";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 49994 };
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Talk-to", null, "Minigame", null };
            entityDef.rotationSpeed = 64;
        }
        if (i == 8368) {
            entityDef.name = "Flying Pumpkin";
            entityDef.combatLevel = 72;
            entityDef.models = new int[] { 49469 };
            entityDef.standingAnimation = 5021;
            entityDef.walkingAnimation = 5022;
            entityDef.size = 2;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.rotationSpeed = 64;
            entityDef.npcWidth = 50; //WIDTH
            entityDef.npcHeight = 50; // HEIGHT
        }
        if (i == 2577) {
            entityDef.name = "lil' Nyx";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] {60050};
            entityDef.standingAnimation = 5022;
            entityDef.walkingAnimation = 5022;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.npcWidth = 50; //WIDTH
            entityDef.npcHeight = 50; // HEIGH
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 373) {
            entityDef.name = "Pheasant";
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 2268) {
            entityDef.name = "13th Pet Goblin";
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 80; //WIDTH
            entityDef.npcHeight = 80; // HEIGH
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 7633) {
            entityDef.name = "Giant Flying Pumpkin";
            entityDef.combatLevel = 300;
            entityDef.models = new int[] { 60035 };
            entityDef.standingAnimation = 5021;
            entityDef.walkingAnimation = 5022;
            entityDef.size = 4;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.rotationSpeed = 64;
        }
        if (i == 7623) {
            entityDef.name = "Jack-O-Kraken";
            entityDef.combatLevel = 616;
            entityDef.models = new int[] { 60049 };
            entityDef.standingAnimation = 3989;
            entityDef.walkingAnimation = 3989;
            entityDef.size = 4;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.rotationSpeed = 64;
        }
        if (i == 4629) {
            entityDef.name = "Flying Pumpkin pet";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 60037 };
            entityDef.standingAnimation = 5021;
            entityDef.walkingAnimation = 5022;
            entityDef.rotationSpeed = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.npcWidth = 25; //WIDTH
            entityDef.npcHeight = 25; // HEIGH
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 4630) {
            entityDef.name = "Jack-O-Kraken pet";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 49470 };
            entityDef.standingAnimation = 3989;
            entityDef.walkingAnimation = 3989;
            entityDef.actions = new String[5];
            entityDef.rotationSpeed = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.npcWidth = 25; //WIDTH
            entityDef.npcHeight = 25; // HEIGH
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == Npcs.PERDU) {
            entityDef.actions = new String[] { "Talk-to", null, "Reclaim-lost", null, null};
        }
        if (i == 34318) {
            entityDef.actions = new String[] { "Talk-to", null, "Reclaim", null, null};
        }
        if (i == 8184) {
            entityDef.name = "Theatre Of Blood Wizard";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Teleport";
        }
        if (i == 7599) {
            entityDef.name = "Exiled Guide";
        }
        if (i == 4305) {
            entityDef.name = "Drunken cannoneer";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Pickpocket";
        }
        if (i == 3247) {
            entityDef.name = "Wizard";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Teleport";
        }
        if (i == 6517) {
            entityDef.name = "Daily-reward wizard";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Talk-to";
            entityDef.actions[2] = "View rewards";
        }
        if (i == 3428 || i == 3429) {
            entityDef.name = "Elf warrior";
        }
        if (i == 3428 || i == 3429) {
            entityDef.name = "Elf warrior";
        }
        if (i == 5044) { // sanfew (decant)
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Decant-potions";
        }
        if (i == 8026) {
            entityDef.combatLevel = 392;
        }
        if (i == 7913) {
            entityDef.combatLevel = 0;
            entityDef.name = "Ironman shop keeper";
            entityDef.description = "A shop specifically for iron men.";
        }
        if (i == 8906) {
            entityDef.combatLevel = 0;
            entityDef.name = "Santa's little elf";
            entityDef.description = "A helper sent from santa himself.";
            entityDef.actions = new String[] { "Talk-To", null, "Christmas Shop", "Return-Items", null };
        }
        if (i == 954) {
            entityDef.combatLevel = 0;
            entityDef.name = "Crystal Seed Trader";
            entityDef.description = "Use a seed on me to get a Crystal Bow.";

        }
        if (i == 6970) {
            entityDef.combatLevel = 0;
            entityDef.name = "Theif";
            entityDef.actions = new String[] { null, null, "Pickpocket",  null,  null };
        }
        if (i == 8761) {
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Talk-to", null, "Assignment", "Trade", "Rewards" };
        }
        if (i == 1531) {
            entityDef.name = "Fishing Spot";
            entityDef.actions = new String[] { "Bait", null, null, null, null };
            entityDef.description = "A fishing spot in lava ??.";
        }
        if (i == 9400) {
            entityDef.name = "Ted O'bombr";
        }
        if (i == 7677) {
            entityDef.actions = new String[] { "Talk-to", null, "Bank", "Foundry Prices",  null, };
        }
        if (i == 8026 || i == 8027 || i == 8028) {
            entityDef.size = 9;
        }
        if (i == 7954) {
            entityDef.combatLevel = 0;
            entityDef.name = "Achievement Master";
            entityDef.actions = new String[] { "Trade", null, "Open Achievements", null, null, };

        }
        if (i == 5870) {
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Talk-to", null, "Assignment", "Trade", "Rewards" };

        }
        if (i == 3400) {
            entityDef.combatLevel = 0;
            entityDef.name = "Giveaway Manager";
            entityDef.actions = new String[] { "Open-manager", null, null, null, null };

        }
        if (i == 1013) {
            entityDef.combatLevel = 0;
            entityDef.name = "Fortune Freddy";
            entityDef.description = "Have you got Freddy's luck to obtain some amazing!.";
            entityDef.actions = new String[] { "WheelOfFortune", null, "Quick-Spin", null, null };
        }
        if (i == 308) {
            entityDef.combatLevel = 0;
            entityDef.name = "PKP Manager";
        }
        if (i == 13) {
            entityDef.combatLevel = 0;
            entityDef.name = "Referral Tutor";
            entityDef.description = "He manages referrals.";
        }
        if (i == 5293) {
            entityDef.combatLevel = 0;
            entityDef.name = "Elven Keeper";
        }
        if (i == 5978) {
            entityDef.name = "FlowerPoker Guard";
        }
        if(i==3218 || i ==3217){
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==2897){
            entityDef.combatLevel = 0;
            entityDef.name = "Trading Post Manager";
            entityDef.actions = new String[] { "Open", null, "Collect", null, null };
        }
        if(i==1306){
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Make-over", null, null, null, null };
        }
        if(i==3257){
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==1011){
            entityDef.combatLevel = 0;
            entityDef.name = "Item Gambler";
            entityDef.actions = new String[] { "Info", null, "Gamble", null, null };
        }
        if(i==3248){
            entityDef.combatLevel = 0;
            entityDef.name = Configuration.CLIENT_TITLE + " Wizard";
            entityDef.actions = new String[] { "Teleport", null, "Previous Location", null, null };
        }
        if(i==1520){
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Small Net", null, "Harpoon", null, null };
        }
        if(i==8920){

            entityDef.actions = new String[] { null, "Attack", null, null, null };
        }
        if(i==8921){
            entityDef.name = "Crystal Whirlwind";
        }
        if(i==9120){
            entityDef.combatLevel = 0;
            entityDef.name = "Donator Shop";
            entityDef.actions = new String[] { "Trade", null, "Rewards", null, null };
        }
        if(i == 2662){
            entityDef.combatLevel = 0;
            entityDef.name = "Tournament Manager";
            entityDef.actions = new String[] { "Open-Shop", null, null, null, null };
        }
        if(i==603){
            entityDef.combatLevel = 0;
            entityDef.name = "Captain Kraken";
            entityDef.actions = new String[] { "Talk-to", null, null, null, null };
        }
        if(i==7041){
            entityDef.combatLevel = 0;
            entityDef.name = "Ticket Exchange";
            entityDef.actions = new String[] { "Exchange", null, null, null, null };
        }
        if(i==3894){
            entityDef.combatLevel = 0;
            entityDef.name = "Sigmund The Merchant";
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }

        if (i==7413) {
            entityDef.name = "Max Dummy";
            entityDef.actions[0] = null;
        }
        if(i==9011){
            entityDef.combatLevel = 0;
            entityDef.name = "Vote Shop";
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==1933){
            entityDef.combatLevel = 0;
            entityDef.name = "Mills";
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==8819){
            entityDef.combatLevel = 0;
            entityDef.name = "Boss point shop";
            entityDef.actions = new String[] { null, null, "Trade", null, null };
        }
        if(i==8688){
            entityDef.combatLevel = 0;
            entityDef.name = "Fat Tony";
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==7769){
            entityDef.combatLevel = 0;
            entityDef.name = "Shop Keeper";
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==6987){
            entityDef.combatLevel = 0;
            entityDef.name = "Man";
            entityDef.actions = new String[] { "Talk", null, "Pickpocket", null, null };
        }
        if(i==5730){
            entityDef.combatLevel = 0;
            entityDef.name = "Master Farmer";
            entityDef.actions = new String[] { "Pickpocket", null, "Trade", null, null };
        }
        if(i==1501){
            entityDef.combatLevel = 0;
            entityDef.name = "Hunter Store";
            entityDef.actions = new String[] { null, null, null, null, "Trade" };
        }
        if(i==2913){
            entityDef.combatLevel = 0;
            entityDef.name = "Fishing Store";
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==5809){
            entityDef.combatLevel = 0;
            entityDef.name = "Crafting and Tanner";
            entityDef.actions = new String[] { "Tan", null, "Trade", null, null };
        }
        if(i==555){
            entityDef.combatLevel = 0;
            entityDef.name = "Sell Me Store";
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==9168){
            entityDef.combatLevel = 0;
            entityDef.name = "Flex";
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==8208){
            entityDef.combatLevel = 0;
            entityDef.name = "Pet Collector";
            entityDef.actions = new String[] { "Talk-to", null, null, null, null };
        }
        if(i==8202){
            entityDef.actions = new String[] { "Talk-to", "Pick-Up", null, null, null };
        }
        if(i==4921){
            entityDef.combatLevel = 0;
            entityDef.name = "Supplies";
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if (i == 5314) {
            entityDef.combatLevel = 0;
            entityDef.name = "Mystical Wizard";
            entityDef.actions = new String[] { "Teleport", "Previous Location", null, null, null };
            entityDef.description = "This wizard has the power to teleport you to many locations.";
        }
        if (i == 8781) {
            entityDef.name = "@red@Queen Latsyrc";
            entityDef.combatLevel = 982;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.npcWidth = 175; //WIDTH
            entityDef.npcHeight = 175; // HEIGH
        }


        if (i == 655) {
            entityDef.name = "@gre@AFK Goblin";
            entityDef.combatLevel = 4012;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.npcWidth = 500; //WIDTH
            entityDef.npcHeight = 500; // HEIGHT
            entityDef.size = 4;
        }
        if(i==1577){
            entityDef.combatLevel = 0;
            entityDef.name = "Melee Shop";
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==1576){
            entityDef.combatLevel = 0;
            entityDef.name = "Range Shop";
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if(i==1578){
            entityDef.combatLevel = 0;
            entityDef.name = "Mage Shop";
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Trade", null, null, null, null };
        }
        if (i == 315) {
            entityDef.name = "Steve";
        }

        if (i == 8026) {
            entityDef.name = "Vorkath";
            // entityDef.combatLevel = 732;
            entityDef.models = new int[] { 35023 };
            entityDef.standingAnimation = 7946;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Poke", null, null, null, null };
            entityDef.npcWidth = 100;
            entityDef.npcHeight = 100;
        }
        if (i == 7852 || i == 7853 || i == 7884) {//Dawn
            entityDef.standingAnimation = 7775;
            entityDef.walkingAnimation = 7775;
        }
        if (i == 5518) {
            entityDef.standingAnimation = 185;
        }
        if (i == 8019) {
            entityDef.standingAnimation = 185;
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Talk-to";
            entityDef.actions[2] = "Trade";
        }
        if (i == 308) {
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Talk-to";
            entityDef.actions[2] = "Trade";
            entityDef.actions[3] = "Disable Interface";
            entityDef.actions[4] = "Skull";
        }
        if (i == 6088) {
            entityDef.standingAnimation = 185;
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Talk-to";
            entityDef.actions[2] = "Travel";
        }
        if (i == 1434 || i == 876 || i == 1612) {//gnome fix
            entityDef.standingAnimation = 185;
        }
        if (i == 7674 || i == 8009 || i == 388 || i == 8010) {

            entityDef.combatLevel = 0;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", "Metamorphosis", null };
        }
        if (i == 8492 || i == 8493 || i == 8494 || i == 8495) {
            entityDef.combatLevel = 0;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", "Metamorphosis", null };
        }
        if (i == 8737 || i == 8738 || i == 8009 || i == 7674) {
            entityDef.combatLevel = 0;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", "Metamorphosis", null };
        }
        if (i == 326 || i == 327) {
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 85;
            entityDef.npcHeight = 85;
            entityDef.name = "Vote Pet";
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", "Metamorphosis", null };
        }
        if (i >= 7354 && i <=7367) {
            entityDef.combatLevel = 0;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", "Metamorphosis", null };
        }
        if (i >= 3169 && i <= 3183) {
            entityDef.standingAnimation = 6952;
        }
        if (i == 5559 || i == 5560) {
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", "Metamorphosis", null };
        }
        if (i == 2149 || i == 2150 || i == 2151 || i == 2148) {
            entityDef.name = "Trading Clerk";
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Bank", null, "Trading Post", null, null };
        }
        if (i == 6473) { //terror dog
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 50; //WIDTH
            entityDef.npcHeight = 50; // HEIGH
        }
        if (i == 3510) { //outlast shop
            entityDef.name = "Trader";
            entityDef.combatLevel = 0;
            entityDef.onMinimap = true;
            entityDef.npcWidth = 150; //WIDTH
            entityDef.npcHeight = 150; // HEIGH
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Open-Shop", null, null, null, null };
        }
        if (i == 488) { //rain cloud
            entityDef.combatLevel = 0;
            entityDef.size = 1;
            entityDef.onMinimap = true;
            entityDef.npcWidth = 150; //WIDTH
            entityDef.npcHeight = 150; // HEIGH
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 5644) { //swapcreature
            entityDef.npcWidth = 300; //WIDTH
            entityDef.npcHeight = 300; // HEIGH
        }
        if (i == 7668) { //voice of yama
            entityDef.name = "Kratos";
            entityDef.size = 2;
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 90; //WIDTH
            entityDef.npcHeight = 90; // HEIGH
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };

        }
        if (i == 1377) {
            entityDef.size = 3;
            entityDef.npcWidth = 300; //WIDTH
            entityDef.npcHeight = 300; // HEIGH
            entityDef.actions[0] = null;
        }
        if (i == 2105) {
            entityDef.size = 4;
            entityDef.npcWidth = 600; //WIDTH
            entityDef.npcHeight = 600; // HEIGH
        }
        if (i == 2107) {
            entityDef.size = 4;
            entityDef.npcWidth = 600; //WIDTH
            entityDef.npcHeight = 600; // HEIGH
        }
        if (i == 2850) {
            entityDef.name = "GIM Tracker";
            entityDef.actions = new String[] { "Open", null, null, null, null };

        }
        if (i == 6119) { //weird monster
            entityDef.size = 1;
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 30; //WIDTH
            entityDef.npcHeight = 30; // HEIGH
        }
        if (i == 763) { //roc

            entityDef.size = 1;
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 30; //WIDTH
            entityDef.npcHeight = 30; // HEIGH
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", "Metamorphosis", null };


        }
        if (i == 762) { //foe small bird
            entityDef.size = 1;
            entityDef.combatLevel = 0;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", "Metamorphosis", null };
        }
        if (i == 4987 || i == 6292 || i == 6354 ) { //chronzon
            entityDef.size = 1;
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 45; //WIDTH
            entityDef.npcHeight = 45; // HEIGH
        }
        if (i == 8709) {
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.name = "Corrupt Beast";
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 60; //WIDTH
            entityDef.npcHeight = 60; // HEIGH
            entityDef.size = 1;
        }
        if (i == 7025) { //guard dog
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.npcWidth = 85; //WIDTH
            entityDef.npcHeight = 85; // HEIGH
        }

        if (i == 6716) {//prayer
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.npcWidth = 65; //WIDTH
            entityDef.npcHeight = 65; // HEIGH
            entityDef.combatLevel = 0;


        }

        if (i == 6723) {//healer
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.npcWidth = 65; //WIDTH
            entityDef.npcHeight = 65; // HEIGH
            entityDef.combatLevel = 0;

        }
        if (i == 1088) {
            entityDef.name = "Seren";
            entityDef.models = new int[] { 38605 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.npcWidth = 65; //WIDTH
            entityDef.npcHeight = 65; // HEIGH
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 8372;
            entityDef.walkingAnimation = 8372;
            entityDef.models = new int[] { 38605 };

        }
        if (i == 1089) {
            entityDef.name = "Lil mimic";
            entityDef.models = new int[] { 37142 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.npcWidth = 25; //WIDTH
            entityDef.npcHeight = 25; // HEIGH
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 8307;
            entityDef.walkingAnimation = 8306;
            entityDef.models = new int[] { 37142 };

        }
        if (i == 2120) {
            entityDef.name = "Shadow Ranger";
            entityDef.models = new int[] { 29267 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.npcWidth = 85; //WIDTH
            entityDef.npcHeight = 85; // HEIGH
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 8526;
            entityDef.walkingAnimation = 8527;
            entityDef.models = new int[] { 29267 };

        }
        if (i == 2121) {
            entityDef.name = "Shadow Wizard";
            entityDef.models = new int[] { 29268 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.npcWidth = 85; //WIDTH
            entityDef.npcHeight = 85; // HEIGH
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 8526;
            entityDef.walkingAnimation = 8527;
            entityDef.models = new int[] { 29268 };
        }
        if (i == 2122) {
            entityDef.name = "Shadow Warrior";
            entityDef.models = new int[] { 29266 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.npcWidth = 85; //WIDTH
            entityDef.npcHeight = 85; // HEIGH
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 8526;
            entityDef.walkingAnimation = 8527;
            entityDef.models = new int[] { 29266 };
        }

        if (i == 7216 || i == 6473) {//green monkey and green dog
            entityDef.actions = new String[5];
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 6723 || i == 6716 || i == 8709) {
            entityDef.actions = new String[5];
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 3291) {//postie pete
            entityDef.actions = new String[5];
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 5738) {//imp
            entityDef.actions = new String[5];
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };

        }
        if (i == 5240) {//toucan
            entityDef.actions = new String[5];
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };

        }
        if (i == 834) {
            entityDef.name = "King penguin";
            entityDef.actions = new String[5];
            entityDef.combatLevel = 0;
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };

        }
        if (i == 1873) {//klik
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.standingAnimation = 3345;
            entityDef.walkingAnimation = 3346;

        }

        if (i == 4923) {
            entityDef.name = "Groot";
            entityDef.combatLevel = 849;
            entityDef.models = new int[] { 47110 };
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.rotationSpeed = 64;
            entityDef.npcWidth = 250;
            entityDef.npcHeight = 250;
            entityDef.size = 3;
        }

        if (i == 3472) {
            entityDef.name = "Lil' Groot";
            entityDef.models = new int[] { 47110 };
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.rotationSpeed = 64;
            entityDef.npcWidth = 65;
            entityDef.npcHeight = 65;
            entityDef.size = 1;
        }

        if (i == 7316) {
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Trade", null, null };
        }

        if (i == 6298) {
            entityDef.npcWidth = 300; //WIDTH
            entityDef.npcHeight = 300; // HEIGH
        }

        if (i == 5630) { // Giant Snail
            entityDef.headIcon = 6;
        }
        if (i == 6474) {
            entityDef.headIcon = 1;
        }
        if (i == 7804) {
            entityDef.headIcon = 0;
        }
        if (i == 2948) {
            entityDef.headIcon = 0;
        }
        if (i == 1101 || i == 7594) {
            entityDef.headIcon = 8;
        }
        if (i == 7594) {
            entityDef.combatLevel = 90;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
        }
        if (i == 8164) {
            entityDef.name = "Shadow of Araphel";
            entityDef.combatLevel = 666;
            entityDef.models = new int[] { 46831 };
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.rotationSpeed = 64;
            entityDef.npcWidth = 180;
            entityDef.npcHeight = 180;
            entityDef.size = 3;
        }
        if (i == 8172) {
            entityDef.name = "Araphel";
            entityDef.combatLevel = 666;
            entityDef.models = new int[] { 46832 };
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.rotationSpeed = 64;
            entityDef.npcWidth = 180;
            entityDef.npcHeight = 180;
            entityDef.size = 3;
        }

        // NEW PETS
        if (i == 2304) {
            entityDef.name = "mystery box";
            entityDef.models = new int[] { 47004 };//mystery box
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 5021;
            entityDef.walkingAnimation = 5022;
            entityDef.npcWidth = 85; //WIDTH
            entityDef.npcHeight = 85; // HEIGHT
        }



        if (i == 2308) {
            entityDef.name = "Magic Pet";
            entityDef.models = new int[] { 60003 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.npcWidth = 65; //WIDTH
            entityDef.npcHeight = 65; // HEIGH
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 1539;
            entityDef.walkingAnimation = 1539;
        }
        if (i == 5568 || i == 5569 || i == 5570 || i == 5571) {
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }

        if (i == 2311) {
            entityDef.name = "Mask";
            entityDef.models = new int[] { 60004 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 85; //WIDTH
            entityDef.npcHeight = 85; // HEIGHT
            entityDef.size = 1;
            entityDef.standingAnimation = 1539;
            entityDef.walkingAnimation = 1539;
        }

        if (i == 2300) {
            entityDef.models = new int[1];
            entityDef.name = "Skeleton";
            entityDef.models = new int[] { 47008 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
        }

        if (i == 2301) {
            entityDef.name = "Beaver";
            entityDef.models = new int[] { 60001 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.npcWidth = 85; //WIDTH
            entityDef.npcHeight = 85; // HEIGHT
        }

        if (i == 2302) {
            entityDef.name = "Rock";//Mystery box
            entityDef.models = new int[] { 47003 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.npcWidth = 85; //WIDTH
            entityDef.npcHeight = 85; // HEIGHT
        }

        if (i == 2307) {
            entityDef.name = "Fish";
            entityDef.models = new int[] { 60002 };//Santa hat = 50078
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 8526;
            entityDef.walkingAnimation = 8527;
            entityDef.npcWidth = 85; //WIDTH
            entityDef.npcHeight = 85; // HEIGH
        }

        if (i == 2305) {
            entityDef.name = "Jack The Pumpkin King";
            entityDef.models = new int[] { 47011, 47013, 47015, 47017, 47019, 47021 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Teleport", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 13;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
        }


        if (i == 2312) {
            entityDef.name = "@gre@$$@blu@CashMoney@gre@$$";
            entityDef.models = new int[] { 47025 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.standingAnimation = 7939;
            entityDef.walkingAnimation = 7939;
            entityDef.size = 1;
            entityDef.combatLevel = 0;
        }

        if (i == 2313) {
            entityDef.name = "Charlie";
            entityDef.models = new int[] { 46779 };
            entityDef.description = "A Christmas Imp.";
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            NpcDefinition npcDefinition = NpcDefinition.lookup(3134);
            entityDef.standingAnimation = npcDefinition.standingAnimation;
            entityDef.walkingAnimation =  npcDefinition.walkingAnimation;
            entityDef.size = 1;
            entityDef.combatLevel =  npcDefinition.combatLevel;
        }

        if (i == 5371) {
            entityDef.combatLevel = 666;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
        }

        if (i == 539) {
            entityDef.description = "Elfinlocks the keeper of the seasonal shop.";
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Open-Shop", null, null };
        }

        if (i == 2306) {
            entityDef.name = "Snowy";
            entityDef.models = new int[] { 46751 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Teleport", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
        }

        if (i == 5169) {
            entityDef.name = "Durial321";
            entityDef.models = new int[] { 187, 214, 246, 6669, 35817, 6659, 3704, 5409, 9347, 13307, 9638, 9642 };
            entityDef.originalColors = new int[] { 926, 6798 };
            entityDef.newColors = new int[] { 22464, 5186 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.combatLevel = 115;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.npcWidth = 300; //WIDTH
            entityDef.npcHeight = 300; // HEIGHT
        }

        //dark pets
        if (i == 2309) {
            entityDef.name = "Discord";
            entityDef.models = new int[] { 46747 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Sync", "Open-Shop", null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 7939;
            entityDef.walkingAnimation = 7939;
        }



        if (i == 2316) {
            entityDef.name = "Guardian Angel";
            entityDef.models = new int[] { 60017 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] {"Talk-to", null, "Pick-Up", null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 100;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.npcWidth = 75; //WIDTH
            entityDef.npcHeight = 75; // HEIGH
            entityDef.size = 1;
        }

        if (i == 837) {
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, null, null, null };
        }

        if (i == 11226) {
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, null, null, null };
        }
        if (i == 8027) {
            entityDef.name = "Vorkath";
            entityDef.combatLevel = 732;
            entityDef.models = new int[] { 35023 };
            entityDef.standingAnimation = 7950;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, null, null, null, null };
            entityDef.npcWidth = 100;
            entityDef.npcHeight = 100;
        }
        if (i == 8028) {
            entityDef.name = "Vorkath";
            entityDef.combatLevel = 732;
            entityDef.models = new int[] { 35023 };
            entityDef.standingAnimation = 7948;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.npcWidth = 100;
            entityDef.npcHeight = 100;
        }
        if (i == 1257) {
            entityDef.name = "Fluffie";
            entityDef.combatLevel = 1;
            entityDef.standingAnimation = 4472;
            entityDef.walkingAnimation = 4473;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.npcWidth = 400;
            entityDef.npcHeight = 400;
        }
        /*
        * Christmas
        **/
        if (i == 8054) {
            entityDef.name = "Gingerbread Man";
            entityDef.combatLevel = 75;
            entityDef.models = new int[] { 49472 };
            entityDef.standingAnimation = -1;
            entityDef.walkingAnimation = 819;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.size = 2;
        }

        if (i == 4851) {
            entityDef.name = "lil' Gingie";
            entityDef.models = new int[] { 49472 };
            entityDef.standingAnimation = -1;
            entityDef.walkingAnimation = 819;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] {"Talk-to", null, "Pick-Up", null, null };
            entityDef.size = 2;
            entityDef.npcWidth = 75;
            entityDef.npcHeight = 75;
        }

        if (i == 4852) {
            entityDef.name = "lil' Elf";
            entityDef.models = new int[] { 49473, 36100 };
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] {"Talk-to", null, "Pick-Up", null, null };
            entityDef.size = 2;
            entityDef.npcWidth = 75;
            entityDef.npcHeight = 75;
        }

        if (i == 4850) {
            entityDef.name = "lil' Evil Snowman";
            entityDef.models = new int[] { 46750 };
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] {"Talk-to", null, "Pick-Up", null, null };
            entityDef.size = 2;
            entityDef.npcWidth = 50;
            entityDef.npcHeight = 50;
        }

        if (i == 5507) {
            entityDef.name = "Elf";
            entityDef.combatLevel = 75;
            entityDef.models = new int[] { 49473, 36100 };
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.npcWidth = 75;
            entityDef.npcHeight = 75;
            entityDef.size = 2;
        }

        if (i == 5509) {
            entityDef.name = "Evil Nutcracker";
            entityDef.models = new int[] { 46868, 217, 246, 46842, 46848, 46839, 46849, 13307, 9642, 46852 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.combatLevel = 75;
            entityDef.standingAnimation = 1421;
            entityDef.walkingAnimation = 1422;
            entityDef.npcWidth = 75; //WIDTH
            entityDef.npcHeight = 75; // HEIGHT
            entityDef.size = 1;
        }

        if (i == 2310) {
            entityDef.name = "Jack Frost";
            entityDef.models = new int[] { 47023 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, null, null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
        }

        if (i == 2314) {
            entityDef.name = "Evil Snowman Minion";
            entityDef.models = new int[] { 46749 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 126;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.npcWidth = 25; //WIDTH
            entityDef.npcHeight = 25; // HEIGH
            entityDef.size = 1;
        }

        if (i == 2317) {
            entityDef.name = "Evil Snowman";
            entityDef.models = new int[] { 46750 };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.originalColors = null;
            entityDef.newColors = null;
            entityDef.combatLevel = 126;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
            entityDef.npcWidth = 150; //WIDTH
            entityDef.npcHeight = 150; // HEIGH
            entityDef.size = 4;
        }
        if (i == 7118) {
            entityDef.name = "Maniacal Monkey";
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 120; //WIDTH
            entityDef.npcHeight = 120; // HEIGH
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 1467) {
            entityDef.name = "Zombie Monkey";
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 120; //WIDTH
            entityDef.npcHeight = 120; // HEIGH
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 2315) {
            entityDef.name = "Santa";
            entityDef.models = new int[] { 189, 217, 253, 28983, 28981, 28978, 28979, 28989/*, 29016*/ };
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, null, null, null };
            entityDef.originalColors = new int[] { 8741, 25238, 7073, 10297, 9104, 906 };
            entityDef.newColors = new int[] { 5210, 7333, 7467, 5206, 7452, 8596 };
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 808;
            entityDef.walkingAnimation = 819;
        }
        if (i == 8087) {
            entityDef.name = "Baby Red dragon";
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 40; //WIDTH
            entityDef.npcHeight = 40; // HEIGH
            entityDef.size = 2;
            entityDef.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
        }
        if (i == 8081) {
            entityDef.name = "Baby Green dragon";
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 40; //WIDTH
            entityDef.npcHeight = 40; // HEIGH
            entityDef.size = 2;
            entityDef.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
        }
        if (i == 8093) {
            entityDef.name = "Baby Black dragon";
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 40; //WIDTH
            entityDef.npcHeight = 40; // HEIGH
            entityDef.size = 2;
            entityDef.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
        }
        if (i == 8083) {
            entityDef.name = "Baby Blue dragon";
            entityDef.combatLevel = 0;
            entityDef.npcWidth = 40; //WIDTH
            entityDef.npcHeight = 40; // HEIGH
            entityDef.size = 2;
            entityDef.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
        }
        if (i == 2592) {
            entityDef.name = "Realm Nyx";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] {60048};
            entityDef.standingAnimation = 5022;
            entityDef.walkingAnimation = 5022;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.npcWidth = 50; //WIDTH
            entityDef.npcHeight = 50; // HEIGH
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-Up", null, null };
        }
        if (i == 12889) {
            entityDef.name = "Primio";
            entityDef.combatLevel = 0;
            entityDef.standingAnimation = 10947;
            entityDef.npcWidth = 120; //WIDTH
            entityDef.npcHeight = 120; // HEIGH
            entityDef.size = 3;
            entityDef.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
        }
        if (i == 11675) {
            entityDef.name = "SmS";
            entityDef.actions = new String[]{"Talk-to", null, null, null, null};
        }
        if(i==10532) {
            entityDef.combatLevel = 982;
            entityDef.onMinimap = true;
            entityDef.npcWidth = 75;
            entityDef.npcHeight = 75;
        }
        if(i==10531){
            entityDef.combatLevel = 982;
            entityDef.onMinimap = true;
            entityDef.npcWidth = 75;
            entityDef.npcHeight = 75;
        }
        if(i==7144){
            entityDef.headIcon = 0;
        }
        if(i==963){
            entityDef.headIcon = 6;
        }
        if(i==7145){
            entityDef.headIcon = 1;
        }
        if(i==7146){
            entityDef.headIcon = 2;
        }
        if (entityDef.name != null && entityDef.name.toLowerCase().contains("chinchompa") && !entityDef.name.toLowerCase().contains("baby")) {
            entityDef.actions = new String[5];
        }

        switch (i) {
            case 1233:
            case 1234:
            case 1235:
            case 1230:
            case 1231:
            case 1232:
            case 1227:
            case 1228:
            case 1229:
                entityDef.combatLevel = 75;
                break;


            case 9786:
            case 9787:
            case 9788:
            case 9789:
            case 9790:
            case 9791:
            case 9792:
            case 9793:
            case 9794:
            case 9795:
            case 9796:
            case 9797:
            case 9798:
            case 9799:
                entityDef.standingAnimation = 2904;
                entityDef.walkingAnimation = 2943;
                break;
        }
        return entityDef;
    }

    public static int totalAmount;

    public static void dump() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./npc_defs.txt"))) {
            for (int i = 0; i < 70_000; i++) {
                try {
                    NpcDefinition def = NpcDefinition.lookup(i);
                    if (def != null) {
                        writer.write("Npc=" + i);
                        writer.newLine();
                        writer.write("Stand animation=" + def.standingAnimation);
                        writer.newLine();
                        writer.write("Walk animation=" + def.walkingAnimation);
                        writer.newLine();
                        writer.newLine();
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unpackConfig(FileArchive streamLoader) {
        stream = new Buffer(streamLoader.readFile("npc.dat"));
        Buffer stream = new Buffer(streamLoader.readFile("npc.idx"));
        totalAmount = stream.readUShort();
        streamIndices = new int[totalAmount];
        int i = 2;
        for (int j = 0; j < totalAmount; j++) {
            streamIndices[j] = i;
            i += stream.readUShort();
        }

        cache = new NpcDefinition[20];
        for (int k = 0; k < 20; k++)
            cache[k] = new NpcDefinition();
        for (int index = 0; index < totalAmount; index++) {
            NpcDefinition ed = lookup(index);
            if (ed == null)
                continue;
            if (ed.name == null)
                continue;
        }
/*        if (Configuration.dumpDataLists) {
            NpcDefinitionDumper.dump();

            TempWriter writer2 = new TempWriter("npc_fields");
            FieldGenerator generator = new FieldGenerator(writer2::writeLine);
            IntStream.range(0, 100_000).forEach(id -> {
                try {
                    NpcDefinition definition = NpcDefinition.lookup(id);
                    generator.add(definition.name, id);
                } catch (Exception e) {
                }
            });
            writer2.close();
        }*/
    }
    public int category;

    private void readValues(Buffer buffer) {
        while (true) {
            int opcode = buffer.readUnsignedByte();
            if (opcode == 0)
                return;
            if (opcode == 1) {
                int j = buffer.readUnsignedByte();
                models = new int[j];
                for (int j1 = 0; j1 < j; j1++)
                    models[j1] = buffer.readUShort();

            } else if (opcode == 2)
                name = buffer.readString();
            else if (opcode == 12)
                size = buffer.readSignedByte();
            else if (opcode == 13)
                standingAnimation = buffer.readUShort();
            else if (opcode == 14)
                walkingAnimation = buffer.readUShort();
            else if (opcode == 17) {
                walkingAnimation = buffer.readUShort();
                rotate180AnimIndex = buffer.readUShort();
                rotate90CWAnimIndex = buffer.readUShort();
                rotate90CCWAnimIndex = buffer.readUShort();
                if (rotate180AnimIndex == 65535) {
                    rotate180AnimIndex = -1;
                }
                if (rotate90CWAnimIndex == 65535) {
                    rotate90CWAnimIndex = -1;
                }
                if (rotate90CCWAnimIndex == 65535) {
                    rotate90CCWAnimIndex = -1;
                }
            } else if (opcode == 18) {
                category = buffer.readUShort();
            } else if (opcode >= 30 && opcode < 40) {
                if (actions == null)
                    actions = new String[10];
                actions[opcode - 30] = buffer.readString();
                if (actions[opcode - 30].equalsIgnoreCase("hidden"))
                    actions[opcode - 30] = null;
            } else if (opcode == 40) {
                int k = buffer.readUnsignedByte();
                originalColors = new int[k];
                newColors = new int[k];
                for (int k1 = 0; k1 < k; k1++) {
                    originalColors[k1] = buffer.readUShort();
                    newColors[k1] = buffer.readUShort();
                }
            } else if (opcode == 41) {
                int k = buffer.readUnsignedByte();
                originalTextures = new short[k];
                newTextures = new short[k];
                for (int k1 = 0; k1 < k; k1++) {
                    originalTextures[k1] = (short) buffer.readUShort();
                    newTextures[k1] = (short) buffer.readUShort();
                }

            } else if (opcode == 60) {
                int l = buffer.readUnsignedByte();
                chatheadModels = new int[l];
                for (int l1 = 0; l1 < l; l1++)
                    chatheadModels[l1] = buffer.readUShort();

            } else if (opcode == 93)
                onMinimap = false;
            else if (opcode == 95)
                combatLevel = buffer.readUShort();
            else if (opcode == 97)
                npcHeight = buffer.readUShort();
            else if (opcode == 98)
                npcWidth = buffer.readUShort();
            else if (opcode == 99)
                aBoolean93 = true;
            else if (opcode == 100)
                ambient = buffer.readSignedByte();
            else if (opcode == 101)
                contrast = buffer.readSignedByte();
            else if (opcode == 102)
                headIcon = buffer.readUShort();
            else if (opcode == 103)
                rotationSpeed = buffer.readUShort();
            else if (opcode == 106 || opcode == 118) {
                varbitId = buffer.readUShort();
                if (varbitId == 65535)
                    varbitId = -1;
                varpIndex = buffer.readUShort();
                if (varpIndex == 65535)
                    varpIndex = -1;
                int var3 = -1;
                if (opcode == 118)
                    var3 = buffer.readUShort();
                int i1 = buffer.readUnsignedByte();
                configs = new int[i1 + 2];
                for (int i2 = 0; i2 <= i1; i2++) {
                    configs[i2] = buffer.readUShort();
                    if (configs[i2] == 65535)
                        configs[i2] = -1;
                }
                configs[i1 + 1] = var3;
            } else if (opcode == 107)
                clickable = false;
        }
    }

    public Model method160() {
        if (configs != null) {
            NpcDefinition entityDef = method161();
            if (entityDef == null)
                return null;
            else
                return entityDef.method160();
        }
        if (chatheadModels == null) {
            return null;
        }
        boolean flag1 = false;
        for (int i = 0; i < chatheadModels.length; i++)
            if (!Model.isCached(chatheadModels[i]))
                flag1 = true;

        if (flag1)
            return null;
        Model aclass30_sub2_sub4_sub6s[] = new Model[chatheadModels.length];
        for (int j = 0; j < chatheadModels.length; j++)
            aclass30_sub2_sub4_sub6s[j] = Model.getModel(chatheadModels[j]);

        Model model;
        if (aclass30_sub2_sub4_sub6s.length == 1)
            model = aclass30_sub2_sub4_sub6s[0];
        else
            model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);

        if (originalColors != null)
            for (int k = 0; k < originalColors.length; k++)
                model.recolor(originalColors[k], newColors[k]);


        if (originalTextures != null)
            for (int k = 0; k < originalTextures.length; k++)
                model.retexture(originalTextures[k], newTextures[k]);

        return model;
    }

    public NpcDefinition method161() {
        int j = -1;
        if (varbitId != -1 && varbitId <= 2113) {
            VarBit varBit = VarBit.cache[varbitId];
            int k = varBit.anInt648;
            int l = varBit.anInt649;
            int i1 = varBit.anInt650;
            int j1 = Client.anIntArray1232[i1 - l];
            j = clientInstance.variousSettings[k] >> l & j1;
        } else if (varpIndex != -1)
            j = clientInstance.variousSettings[varpIndex];
        int var3;
        if (j >= 0 && j < configs.length)
            var3 = configs[j];
        else
            var3 = configs[configs.length - 1];
        return var3 == -1 ? null : lookup(var3);
    }

    public Model getAnimatedModel(int j, int k, int ai[]) {
        if (configs != null) {
            NpcDefinition entityDef = method161();
            if (entityDef == null)
                return null;
            else
                return entityDef.getAnimatedModel(j, k, ai);
        }
        Model model = (Model) mruNodes.get(npcId);
        if (model == null) {
            boolean flag = false;
            for (int i1 = 0; i1 < models.length; i1++)
                if (!Model.isCached(models[i1]))
                    flag = true;

            if (flag)
                return null;
            Model aclass30_sub2_sub4_sub6s[] = new Model[models.length];
            for (int j1 = 0; j1 < models.length; j1++)
                aclass30_sub2_sub4_sub6s[j1] = Model.getModel(models[j1]);

            if (aclass30_sub2_sub4_sub6s.length == 1)
                model = aclass30_sub2_sub4_sub6s[0];
            else
                model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
            if (originalColors != null) {
                for (int k1 = 0; k1 < originalColors.length; k1++)
                    model.recolor(originalColors[k1], newColors[k1]);

            }
            if (originalTextures != null) {
                for (int i1 = 0; i1 < originalTextures.length; i1++)
                    model.retexture(originalTextures[i1], newTextures[i1]);
            }
            model.generateBones();
            model.light(64 + ambient, 850 + contrast, -30, -50, -30, true);
            // model.method479(84 + anInt85, 1000 + anInt92, -90, -580, -90, true);
            mruNodes.put(model, npcId);
        }
        Model model_1 = Model.emptyModel;
        model_1.buildSharedSequenceModel(model, SeqFrame.noAnimationInProgress(k) & SeqFrame.noAnimationInProgress(j));
        if (k != -1 && j != -1)
            model_1.mix(ai, j, k);
        else if (k != -1)
            model_1.interpolate(k);
        if (npcHeight != 128 || npcWidth != 128)
            model_1.scale(npcHeight, npcHeight, npcWidth);
        model_1.calculateBoundsCylinder();
        model_1.groupedTriangleLabels = null;
        model_1.groupedVertexLabels = null;
        if (size == 1)
            model_1.singleTile = true;
        return model_1;
    }

    public Model getAnimatedModelSkeletal(SeqDefinition primary, SeqDefinition secondary, int primaryTick, int secondaryTick) {
        if (configs != null) {
            NpcDefinition entityDef = method161();
            if (entityDef == null)
                return null;
            else
                return entityDef.getAnimatedModelSkeletal(primary, secondary, primaryTick, secondaryTick);
        }
        Model model = (Model) mruNodes.get(npcId);
        if (model == null) {
            boolean flag = false;
            for (int i1 = 0; i1 < models.length; i1++)
                if (!Model.isCached(models[i1]))
                    flag = true;

            if (flag)
                return null;
            Model aclass30_sub2_sub4_sub6s[] = new Model[models.length];
            for (int j1 = 0; j1 < models.length; j1++)
                aclass30_sub2_sub4_sub6s[j1] = Model.getModel(models[j1]);

            if (aclass30_sub2_sub4_sub6s.length == 1)
                model = aclass30_sub2_sub4_sub6s[0];
            else
                model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
            if (originalColors != null) {
                for (int k1 = 0; k1 < originalColors.length; k1++)
                    model.recolor(originalColors[k1], newColors[k1]);

            }
            if (originalTextures != null) {
                for (int i1 = 0; i1 < originalTextures.length; i1++)
                    model.retexture(originalTextures[i1], newTextures[i1]);
            }
            model.generateBones();
            model.light(64 + ambient, 850 + contrast, -30, -50, -30, true);
            // model.method479(84 + anInt85, 1000 + anInt92, -90, -580, -90, true);
            mruNodes.put(model, npcId);
        }
        Model model_1 = Model.emptyModel;
        model_1.buildSharedSequenceModel(model, false);
        if (primary != null && secondary != null) {
            model_1.playSkeletalDouble(primary, secondary, primaryTick, secondaryTick);
        } else {
            if (primary != null) {
                model_1.playSkeletal(primary, primaryTick);
            } else if (secondary != null) {
                model_1.playSkeletal(secondary, secondaryTick);
            }
        }
        if (npcHeight != 128 || npcWidth != 128)
            model_1.scale(npcHeight, npcHeight, npcWidth);
        model_1.calculateBoundsCylinder();
        model_1.groupedTriangleLabels = null;
        model_1.groupedVertexLabels = null;
        if (size == 1)
            model_1.singleTile = true;
        return model_1;
    }

    private NpcDefinition() {
        rotate90CCWAnimIndex = -1;
        varbitId = walkingAnimation;
        rotate180AnimIndex = walkingAnimation;
        varpIndex = walkingAnimation;
        combatLevel = -1;
        anInt64 = 1834;
        walkingAnimation = -1;
        size = 1;
        headIcon = -1;
        standingAnimation = -1;
        npcId = -1L;
        rotationSpeed = 32;
        rotate90CWAnimIndex = -1;
        clickable = true;
        npcWidth = 128;
        onMinimap = true;
        npcHeight = 128;
        aBoolean93 = false;
    }

    @Override
    public String toString() {
        return "NpcDefinition{" +
                "npcId=" + npcId +
                ", combatLevel=" + combatLevel +
                ", name='" + name + '\'' +
                ", actions=" + Arrays.toString(actions) +
                ", walkAnim=" + walkingAnimation +
                ", size=" + size +
                ", standAnim=" + standingAnimation +
                ", childrenIDs=" + Arrays.toString(configs) +
                ", models=" + Arrays.toString(models) +
                '}';
    }

    public static void nullLoader() {
        mruNodes = null;
        streamIndices = null;
        cache = null;
        stream = null;
    }

    public static void dumpList() {
        try {
            File file = new File("./temp/npc_list.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (int i = 0; i < totalAmount; i++) {
                    NpcDefinition definition = lookup(i);
                    if (definition != null) {
                        writer.write("npc = " + i + "\t" + definition.name + "\t" + definition.combatLevel + "\t"
                                + definition.standingAnimation + "\t" + definition.walkingAnimation + "\t");
                        writer.newLine();
                    }
                }
            }

            System.out.println("Finished dumping npc definitions.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dumpSizes() {
        try {
            File file = new File(System.getProperty("user.home") + "/Desktop/npcSizes 143.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (int i = 0; i < totalAmount; i++) {
                    NpcDefinition definition = lookup(i);
                    if (definition != null) {
                        writer.write(i + "	" + definition.size);
                        writer.newLine();
                    }
                }
            }

            System.out.println("Finished dumping npc definitions.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int rotate90CCWAnimIndex;
    public static int anInt56;
    public int varbitId;
    public int rotate180AnimIndex;
    public int varpIndex;
    public static Buffer stream;
    public int combatLevel;
    public final int anInt64;
    public String name;
    public String actions[];
    public int walkingAnimation;
    public byte size;
    public int[] newColors;
    public static int[] streamIndices;
    public int[] chatheadModels;
    public int headIcon;
    public int[] originalColors;
    public short[] originalTextures, newTextures;
    public int standingAnimation;
    public long npcId;
    public int rotationSpeed;
    public static NpcDefinition[] cache;
    public static Client clientInstance;
    public int rotate90CWAnimIndex;
    public boolean clickable;
    public int ambient;
    public int npcWidth;
    public boolean onMinimap;
    public int configs[];
    public String description;
    public int npcHeight;
    public int contrast;
    public boolean aBoolean93;
    public int[] models;
    public static ReferenceCache mruNodes = new ReferenceCache(70);
    public int[] anIntArray76;

    @Override
    public HeadIcon getOverheadIcon() {
        return HeadIcon.values()[headIcon];
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int[] getModels() {
        return models;
    }

    @Override
    public String[] getActions() {
        return actions;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public boolean isFollower() {
        if (this.actions == null) {
            return false;
        }
        for (String action : this.actions) {
            if (action != null && action.contains("Pick-Up")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInteractible() {
        return clickable;
    }

    @Override
    public boolean isMinimapVisible() {
        return onMinimap;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public int getId() {
        return (int) npcId;
    }

    @Override
    public int getCombatLevel() {
        return combatLevel;
    }

    @Override
    public int[] getConfigs() {
        return new int[0];
    }

    @Override
    public RSNPCComposition transform() {
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getRsOverheadIcon() {
        return headIcon;
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
}