package com.client.graphics.interfaces.impl;

import com.client.*;
import com.client.connection.Connection;
import com.client.graphics.interfaces.Configs;
import com.client.graphics.interfaces.MenuItem;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.builder.impl.*;
import com.client.graphics.interfaces.builder.impl.tasks.TaskInterface;
import com.client.graphics.interfaces.builder.impl.tob.TobFoodChest;
import com.client.graphics.interfaces.builder.impl.tob.TobPartyOverlay;
import com.client.graphics.interfaces.builder.impl.tob.TobRewardsInterface;
import com.client.graphics.interfaces.daily.DailyRewards;
import com.client.graphics.interfaces.dropdown.KeybindingMenu;
import com.client.graphics.interfaces.eventcalendar.EventCalendar;
import com.client.graphics.interfaces.settings.SettingsInterface;
import com.client.sign.Signlink;

import java.util.Random;

import static com.client.graphics.interfaces.impl.DropParty.dropPartyInterface;

public final class Interfaces extends RSInterface {

	public static final int CLOSE_BUTTON_SMALL = 37302;
	public static final int CLOSE_BUTTON_SMALL_HOVER = 37303;
	public static final int SHOP_CONTAINER = 64016;

	public static TaskInterface taskInterface;

	private Interfaces() {
	}

	public static void reloadInterfaces() {
		StarterNew(defaultTextDrawingAreas);
	}

	public static void loadInterfaces() {
		dropPartyInterface(defaultTextDrawingAreas);
		fireofExchange(defaultTextDrawingAreas);
		wrathRune();
		new SettingsInterface().load(defaultTextDrawingAreas);
		mysteryBox(defaultTextDrawingAreas);
		Teleporting(defaultTextDrawingAreas);
		shopWidget(defaultTextDrawingAreas);
		barrowsReward(defaultTextDrawingAreas);
		keybinding(defaultTextDrawingAreas);
		ancients(defaultTextDrawingAreas);
		helpDatabaseComponent(defaultTextDrawingAreas);
		helpComponent(defaultTextDrawingAreas);
		new Bank().bank(defaultTextDrawingAreas);
		bankPin(defaultTextDrawingAreas);
		clanChatTab(defaultTextDrawingAreas);
		clanChatSetup(defaultTextDrawingAreas);
//		teleportInterface(defaultTextDrawingAreas);
		SettingsTabWidget.widget(defaultTextDrawingAreas);
		emoteTab();
		bountyHunterWidget(defaultTextDrawingAreas);
		godWars(defaultTextDrawingAreas);
		prayerBook(defaultTextDrawingAreas);
		equipmentScreen(defaultTextDrawingAreas);
		presetInterface(defaultTextDrawingAreas);
		equipmentTab(defaultTextDrawingAreas);
		itemsOnDeath(defaultTextDrawingAreas);
		Pestpanel(defaultTextDrawingAreas);
		Pestpanel2(defaultTextDrawingAreas);
		configureLunar(defaultTextDrawingAreas);
//		achievements(defaultTextDrawingAreas);
		updateShopWidget(defaultTextDrawingAreas);
		initializeTitleWidget(defaultTextDrawingAreas);
		initializeCommandHelp();
		rangeTargetBoard(defaultTextDrawingAreas);
		addPestControlRewardWidget(defaultTextDrawingAreas);
		addAntibotWidget(defaultTextDrawingAreas);
		addGodwarsWidget(defaultTextDrawingAreas);
		barrowsKillcount(defaultTextDrawingAreas);
		lootingBag(defaultTextDrawingAreas);
		lootingBagAdd(defaultTextDrawingAreas);
		runePouch(defaultTextDrawingAreas);
//		runePouchUpgrade(defaultTextDrawingAreas);
		quickPrayers(defaultTextDrawingAreas);
		listings(defaultTextDrawingAreas);
		tradingpost(defaultTextDrawingAreas);
		tradingSelect(defaultTextDrawingAreas);
		offer(defaultTextDrawingAreas);
		tradingSelected(defaultTextDrawingAreas);
		skotizo(defaultTextDrawingAreas);
		slayerOverlay(defaultTextDrawingAreas);
		bloodOverlay(defaultTextDrawingAreas);
		prestigeInterface(defaultTextDrawingAreas);
		expLock(defaultTextDrawingAreas);
		skillTabWithHovers(defaultTextDrawingAreas);
		normals(defaultTextDrawingAreas);
		tournamentInterface(defaultTextDrawingAreas);
		tourneyJoinInterface(defaultTextDrawingAreas);
		collectionLog(defaultTextDrawingAreas);
		votePanel(defaultTextDrawingAreas);
		pollInterface(defaultTextDrawingAreas);
		pollResults(defaultTextDrawingAreas);
		SlayerRewards.initializeInterfaces(defaultTextDrawingAreas);
		new LootViewer().load(defaultTextDrawingAreas);

		quest2(defaultTextDrawingAreas);
		OfflineRewards(defaultTextDrawingAreas);
		fixDefensiveAutocast();
		EventCalendar.getCalendar().load(defaultTextDrawingAreas);
		Nightmare.instance.load(defaultTextDrawingAreas);
		staffSpecialBar();
		Autocast.getSingleton().load();
		questInterface(defaultTextDrawingAreas);
		DailyRewards.get().load();
		SpawnContainer.get().load();
		KillLog.get().load();
		WogwInterface.get().load();
		DonatorRewards.getInstance().load();
//		NotificationTab.instance.build();
		cataTele(defaultTextDrawingAreas);
		groupInformation(defaultTextDrawingAreas);
		groupLeaderboard(defaultTextDrawingAreas);
		new MonsterDropViewer().OsDropViewer(defaultTextDrawingAreas);
		dropTable(defaultTextDrawingAreas);
		new TobPartyOverlay().build();
		new TobRewardsInterface().build();
		new TobFoodChest().build();
		new GroupIronmanBank().build();
		new PlayerPartyInterface().build();
		new WildWarning().build();
		new PerduLostPropertyShop().build();
		new LeaderboardInterface().build();
		new QuestInterface().build();
		new BlackJack().mainInterface(defaultTextDrawingAreas);

		PetPerk.mainInterface(defaultTextDrawingAreas);

		quest2(defaultTextDrawingAreas);
		upgradeInterface(defaultTextDrawingAreas);
		Wintertodt(defaultTextDrawingAreas);
		equipv2(defaultTextDrawingAreas);
		taskMaster(defaultTextDrawingAreas);
		discordSystem(defaultTextDrawingAreas);
		instanceManager(defaultTextDrawingAreas);
		slayerMenuv2(defaultTextDrawingAreas);
		newAchievements(defaultTextDrawingAreas);
		CharInfo(defaultTextDrawingAreas);
		progressionInterface(defaultTextDrawingAreas);
		WeaponGame(defaultTextDrawingAreas);
		StarterNew(defaultTextDrawingAreas);
		perkInformation(defaultTextDrawingAreas);
		wheelOfFortune(defaultTextDrawingAreas);

		seasonalPass(defaultTextDrawingAreas);
		questing(defaultTextDrawingAreas);
		IngameStore(defaultTextDrawingAreas);
		coxRewards(defaultTextDrawingAreas);
		cosmeticOverride(defaultTextDrawingAreas);
		coinFlipInterface(defaultTextDrawingAreas);
		coinFlipItemsInterface(defaultTextDrawingAreas);
		boostsInterface(defaultTextDrawingAreas);
		itemsInterface(defaultTextDrawingAreas);
		cosmeticDealsInterface(defaultTextDrawingAreas);
		offerDealsInterface(defaultTextDrawingAreas);
		ruinousPowerBook(defaultTextDrawingAreas);
		youtubeInterface(defaultTextDrawingAreas);
		smsManager(defaultTextDrawingAreas);
		referralSystem(defaultTextDrawingAreas);
	}

	private static void referralSystem(TextDrawingArea[] tda) {
		int main_interface = 61_000;
		int ref_codes_interface = 61_010;
		RSInterface main = addInterface(main_interface);
		String dir = "Interfaces/referral/SPRITE";

		int index = 1;
		int baseX = 8, baseY = 12;
		addSprite(main_interface+index++, 0, dir);
		configHoverButton(main_interface+index, "Close Interface", dir, 3,4,3,3,false, main_interface+index++);
		addText(main_interface+index++, "Referral Codes", tda, 2, 0xfe3200);
		RSInterface refcodes = RSInterface.addInterface(ref_codes_interface); index++;

		main.totalChildren(index-1);

		index = 0;
		main.child(index++, main_interface + index, baseX, baseY); //background
		main.child(index++, main_interface + index, baseX + 280, baseY + 10); //close
		main.child(index++, main_interface + index, baseX + 100, baseY + 10);//title
		main.child(index++, ref_codes_interface, baseX+2, 36 + baseY); //video list

		int ref_codes = 50;

		refcodes.width = 284;
		refcodes.height = 152;
		refcodes.scrollMax = 19*ref_codes;

		refcodes.totalChildren(ref_codes * 2);

		ref_codes_interface++;

		index = 0;

		int columns = 2; // Number of columns
		int rows = 25; // Number of rows
		int boxWidth = 136; // Width of each referral code box
		int boxHeight = 36; // Height of each referral code box
		int horizontalGap = 2; // Gap between boxes horizontally
		int verticalGap = 2; // Gap between boxes vertically

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				int x = baseX + col * (boxWidth + horizontalGap);
				int y = 1 + row * (boxHeight + verticalGap);
				configHoverButton(ref_codes_interface, "Select", dir, 1,2,1,1, false, ref_codes_interface);
				refcodes.child(index++, ref_codes_interface++, x, y);
				addText(ref_codes_interface, "::refer_code", tda, 0, 0xfe3200, true, true);
				refcodes.child(index++, ref_codes_interface++, x+70, y+12);
			}
		}
	}
	private static void smsManager(TextDrawingArea[] tda) {
		int main_interface = 24960;
		RSInterface main = addInterface(main_interface);
		String dir = "Interfaces/sms/SPRITE";

		main.totalChildren(3);
		int index = 1;

		addSprite(main_interface+index++, 0, dir);
		addInputField(main_interface+index++, 200, 0xff981f, "+44123456789", 136, 37, false, false, "");
		configHoverButton(main_interface + index, "Close interface", dir, 3, 4, 3, 3, false, main_interface + index++);//close button

		index = 0;

		main.child(index++,main_interface+index, 10,10);
		main.child(index++,main_interface+index, 18,124);

		main.child(index++, main_interface + index, 138, 20); //close
	}

	private static void youtubeInterface(TextDrawingArea[] tda) {
		int main_interface = 24710;
		int video_interface = 24810;
		RSInterface main = addInterface(main_interface);
		String dir = "Interfaces/youtube/SPRITE";

		int index = 1;
		int baseX = 8, baseY = 12;
		addSprite(main_interface + index++, 0, dir);//background
		addText(main_interface+index++, "Latest Youtube Videos", tda, 2, 0xff981f, true);//text
		configHoverButton(main_interface + index, "Close interface", dir, 3, 4, 3, 3, false, main_interface + index++);//close button
		addText(main_interface+index++, "Comment your in-game name on these videos below", tda, 1, 0xff981f, true);//text
		addText(main_interface+index++, "and then click 'collect' to receive a voting goodie bag.", tda, 1, 0xff981f, true);//text

		RSInterface videoList = RSInterface.addInterface(video_interface); index++;

		main.totalChildren(index-1);

		index = 0;
		main.child(index++, main_interface + index, baseX, baseY); //background
		main.child(index++, main_interface + index, baseX + (495 / 2), baseY + 10); //title

		main.child(index++, main_interface + index, baseX + 466, baseY + 10); //close

		main.child(index++, main_interface + index, baseX + (495 / 2), baseY + 40); //desc
		main.child(index++, main_interface + index, baseX + (495 / 2), baseY + 55); //desc

		main.child(index++, video_interface, 9 + baseX, 76 + baseY); //video list

		int totalVideos = 20;

		videoList.width = 460;
		videoList.height = 223;
		videoList.scrollMax = 85*totalVideos;

		videoList.totalChildren(totalVideos * 7);

		video_interface++;

		index = 0;

		int increaseY = 0;

		for(int videoIndex = 0; videoIndex < totalVideos; videoIndex++) {
			RSInterface.addSprite(video_interface, 5, dir); //main sprite
			RSInterface.interfaceCache[video_interface].invisible = true;
			videoList.child(index++, video_interface++, 10, 8 + increaseY);

			RSInterface.addText(video_interface, "Exiled RSPS | #1 Semi-Custom", tda, 1, 0xffffff, false);
			RSInterface.interfaceCache[video_interface].invisible = true;
			videoList.child(index++, video_interface++, 135, 12 + increaseY);

			RSInterface.addText(video_interface, "This be a description?...", tda, 0, 0xC0C0C0, false);
			RSInterface.interfaceCache[video_interface].invisible = true;
			videoList.child(index++, video_interface++, 135, 30 + increaseY);

			RSInterface.addText(video_interface, "Exiled", tda, 0, 0xfe3200, false);
			RSInterface.interfaceCache[video_interface].invisible = true;
			videoList.child(index++, video_interface++, 135, 60 + increaseY);

			RSInterface.hoverButton(video_interface, "Watch", 1,2, "Watch", tda, 2, 0xFF981F, 0xffffff, true, dir);
			RSInterface.interfaceCache[video_interface].invisible = true;
			videoList.child(index++, video_interface++, 250, 55 + increaseY);

			RSInterface.hoverButton(video_interface, "Collect", 1,2, "Collect", tda, 2, 0xFF981F, 0xffffff, true, dir);
			RSInterface.interfaceCache[video_interface].invisible = true;
			videoList.child(index++, video_interface++, 350, 55 + increaseY);

			RSInterface.addRectangle(video_interface, 425, 1, 0x301F0D, 180, false);
			RSInterface.interfaceCache[video_interface].invisible = true;
			videoList.child(index++, video_interface++, 18, 80 + increaseY);

			increaseY += 85;
		}
	}

	private static void itemsInterface(TextDrawingArea[] tda) {
		int MAIN_INTERFACE = 24535;
		RSInterface main = addInterface(MAIN_INTERFACE);
		String dir = "Interfaces/deals/SPRITE";

		int index = 1;
		int baseX = 45, baseY = 8;

		main.totalChildren(26);

		addSprite(MAIN_INTERFACE + index++, 0, dir);//background
		addText(MAIN_INTERFACE + index++, "Exiled Deals Hub", 2, 0xFF981F, true, true); //title
		configHoverButton(MAIN_INTERFACE + index, "Close interface", dir, 21, 22, 21, 21, false, MAIN_INTERFACE + index++);//close button
		configHoverButton(MAIN_INTERFACE + index, "Account Boosts", dir, 9, 10, 9, 9, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Bonus Items", dir, 16, 16, 16, 16, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Cosmetics", dir, 11, 12, 11, 11, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Timed Offers", dir, 13, 14, 13, 13, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Deals", dir, 7, 8, 7, 7, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Store", dir, 5, 6, 5, 5, false, MAIN_INTERFACE + index++);
		addText(MAIN_INTERFACE + index++, "Boosts", 1, 0xffffff, true, true); //Boosts
		addText(MAIN_INTERFACE + index++, "Items", 1, 0xffffff, true, true); //Items
		addText(MAIN_INTERFACE + index++, "Cosmetics", 1, 0xffffff, true, true); //Cosmetics
		addText(MAIN_INTERFACE + index++, "Offers", 1, 0xffffff, true, true); //Offers
		addSprites(MAIN_INTERFACE + index++, dir, 2, 2, 2);//

		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);
		addText(MAIN_INTERFACE + index++, "TEST\\nTEST\\nTEST\\nTEST", 2, 0xFF981F, true, true);
		addText(MAIN_INTERFACE + index++, "TEST\\nTEST\\nTEST\\nTEST", 2, 0xFF981F, true, true);
		addText(MAIN_INTERFACE + index++, "TEST\\nTEST\\nTEST\\nTEST", 2, 0xFF981F, true, true);
		addText(MAIN_INTERFACE + index++, "TEST\\nTEST\\nTEST\\nTEST", 2, 0xFF981F, true, true);

		index = 0;

		main.child(index++, MAIN_INTERFACE + index, baseX, baseY); //background
		main.child(index++, MAIN_INTERFACE + index, baseX + 220, baseY + 10); //title
		main.child(index++, MAIN_INTERFACE + index, 405 + baseX, 10 + baseY); //close button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 75 + baseY); //Boosts button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 136 + baseY); //bonus button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 197 + baseY); //cosmetic button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 257 + baseY); //timed button
		main.child(index++, MAIN_INTERFACE + index, 120 + baseX, 45 + baseY); //deals button
		main.child(index++, MAIN_INTERFACE + index, 259 + baseX, 45 + baseY); //store button
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 107); //Boosts
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 167); //Items
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 228); //Cosmetics
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 287); //Offers
		main.child(index++, MAIN_INTERFACE + index, baseX + 89, baseY + 75); //Offers

		main.child(index++, MAIN_INTERFACE + index, 120 + baseX, 94 + baseY);  //items
		main.child(index++, MAIN_INTERFACE + index, 205 + baseX, 94 + baseY);  //items
		main.child(index++, MAIN_INTERFACE + index, 282 + baseX, 94 + baseY);  //items
		main.child(index++, MAIN_INTERFACE + index, 365 + baseX, 94 + baseY);  //items

		main.child(index++, MAIN_INTERFACE + index, 120 + baseX, 206 + baseY);  //items
		main.child(index++, MAIN_INTERFACE + index, 205 + baseX, 206 + baseY);  //items
		main.child(index++, MAIN_INTERFACE + index, 282 + baseX, 206 + baseY);  //items
		main.child(index++, MAIN_INTERFACE + index, 365 + baseX, 206 + baseY);  //items

		main.child(index++, MAIN_INTERFACE + index, 175 + baseX, 137 + baseY);  //text
		main.child(index++, MAIN_INTERFACE + index, 335 + baseX, 137 + baseY);  //text
		main.child(index++, MAIN_INTERFACE + index, 175 + baseX, 248 + baseY);  //text
		main.child(index++, MAIN_INTERFACE + index, 335 + baseX, 248 + baseY);  //text
	}

	private static void coinFlipItemsInterface(TextDrawingArea[] tda) {
		int interfaceId = 24500;
		int childId = 0;
		RSInterface inter = addInterface(interfaceId++);
		inter.totalChildren(4);

		addSprite(interfaceId, new Sprite("spawner/bg"));
		inter.child(childId++, interfaceId++, 12, 18);

		// Container
		RSInterface scrollable = addInterface(interfaceId++);
		scrollable.scrollMax = 6000;
		scrollable.width = 470;
		scrollable.height = 260;
		scrollable.invAutoScrollHeightOffset = 0;
		scrollable.totalChildren(1);
		addItemContainerAutoScrollable(interfaceId, 14, 400, 1, 0, false, scrollable.id, "Select");
		get(interfaceId).hideInvStackSizes = true;
		scrollable.child(0, interfaceId++, 4, 4);

		inter.child(childId++, scrollable.id, 20, 58);

		inter.child(childId++, Interfaces.CLOSE_BUTTON_SMALL, 484, 28);
		inter.child(childId++, Interfaces.CLOSE_BUTTON_SMALL_HOVER, 484, 28);
	}

	private static void coinFlipInterface(TextDrawingArea[] tda) {
		int MAIN_INTERFACE = 24485;
		RSInterface main = addInterface(MAIN_INTERFACE);
		String dir = "Interfaces/coinflip/SPRITE";

		int index = 1;
		int baseX = 100, baseY = 8;

		main.totalChildren(15);

		addSprite(MAIN_INTERFACE + index++, 0, dir);
		configHoverButton(MAIN_INTERFACE + index, "Close interface", dir,
				16, 17, 16, 16, false, MAIN_INTERFACE + index++);
		addConfigButton(MAIN_INTERFACE + index++, MAIN_INTERFACE, 12, 10, dir, 43, 43, "Select", 0, 1, 2000);
		addConfigButton(MAIN_INTERFACE + index++, MAIN_INTERFACE, 15, 13, dir, 43, 43, "Select", 0, 1, 2001);
		addAnimatedSprite(MAIN_INTERFACE + index++, Signlink.getCacheDirectory() + "sprites/gifs/Blue-Coin-STILL.gif");
		hoverButton(MAIN_INTERFACE + index++, "Flip!", 4, 5, "Flip!", tda, 2, 0xFF981F, 0xffffff, false, dir);
		addText(MAIN_INTERFACE + index++, "Select A Side", tda, 2, 0xFF981F, true);
		addText(MAIN_INTERFACE + index++, "Select Your Prize", tda, 2, 0xFF981F, true);
		addText(MAIN_INTERFACE + index++, "Coin Flip", tda, 2, 0xFF981F, true);
		addSprite(MAIN_INTERFACE + index++, 6, dir);
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true, new String[]{""});
		addSprite(MAIN_INTERFACE + index++, 1, dir);
		addText(MAIN_INTERFACE + index++, "ITEM NAME", tda, 1, 0xFF981F, true);
		hoverButton(MAIN_INTERFACE + index++, "Settings", 2, 3, "   Settings", tda, 2, 0xFF981F, 0xffffff, false, dir);

		index = 0;

		main.child(index++, MAIN_INTERFACE + index, baseX, baseY); //background
		main.child(index++, MAIN_INTERFACE + index, 330 + baseX, 8 + baseY); //close button
		main.child(index++, MAIN_INTERFACE + index, 80 + baseX, 48 + baseY); //Blue Coin
		main.child(index++, MAIN_INTERFACE + index, 20 + baseX, 48 + baseY); //Red Coin
		main.child(index++, MAIN_INTERFACE + index, 145 + baseX, 37 + baseY); //Main Coin
		main.child(index++, MAIN_INTERFACE + index, 200 + baseX, 215 + baseY); //Flip Coin Button
		main.child(index++, MAIN_INTERFACE + index, 70 + baseX, 36 + baseY); //Select A Side Text
		main.child(index++, MAIN_INTERFACE + index, 70 + baseX, 102 + baseY); //Select Your Prize Text
		main.child(index++, MAIN_INTERFACE + index, 175 + baseX, 8 + baseY); //Coin Flip Text
		main.child(index++, MAIN_INTERFACE + index, 52 + baseX, 125 + baseY); //Item Box
		main.child(index++, MAIN_INTERFACE + index, 55 + baseX, 128 + baseY); //Item On Interface Box
		main.child(index++, MAIN_INTERFACE + index, 12 + baseX, 175 + baseY); //Item Name Box
		main.child(index++, MAIN_INTERFACE + index, 72 + baseX, 178 + baseY); //Item Name Text
		main.child(index++, MAIN_INTERFACE + index, 25 + baseX, 215 + baseY); //Settings Button
	}

	private static void offerDealsInterface(TextDrawingArea[] tda) {
		int MAIN_INTERFACE = 24605;
		RSInterface main = addInterface(MAIN_INTERFACE);
		String dir = "Interfaces/deals/SPRITE";

		int index = 1;
		int baseX = 45, baseY = 8;

		main.totalChildren(26);


		addSprite(MAIN_INTERFACE + index++, 0, dir);//background
		addText(MAIN_INTERFACE + index++, "Exiled Deals Hub", 2, 0xFF981F, true, true); //title
		configHoverButton(MAIN_INTERFACE + index, "Close interface", dir, 21, 22, 21, 21, false, MAIN_INTERFACE + index++);//close button
		configHoverButton(MAIN_INTERFACE + index, "Account Boosts", dir, 9, 10, 9, 9, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Bonus Items", dir, 15, 16, 15, 15, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Cosmetics", dir, 11, 12, 11, 11, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Timed Offers", dir, 14, 14, 14, 14, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Deals", dir, 7, 8, 7, 7, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Store", dir, 5, 6, 5, 5, false, MAIN_INTERFACE + index++);
		addText(MAIN_INTERFACE + index++, "Boosts", 1, 0xffffff, true, true); //Boosts
		addText(MAIN_INTERFACE + index++, "Items", 1, 0xffffff, true, true); //Items
		addText(MAIN_INTERFACE + index++, "Cosmetics", 1, 0xffffff, true, true); //Cosmetics
		addText(MAIN_INTERFACE + index++, "Offers", 1, 0xffffff, true, true); //Offers
		addSprites(MAIN_INTERFACE + index++, dir, 4, 4, 4);//

		//15
		addItemContainer(MAIN_INTERFACE + index++, 3, 1, -4, 0, true);//Item Containers
		addItemContainer(MAIN_INTERFACE + index++, 3, 1, -4, 0, true);//Item Containers
		addItemContainer(MAIN_INTERFACE + index++, 3, 1, -4, 0, true);//Item Containers
		addText(MAIN_INTERFACE + index++, "6d 23h 59m", 1, 0xFFFFFF, true, true);//Text container
		addText(MAIN_INTERFACE + index++, "6d 23h 59m", 1, 0xFFFFFF, true, true);//Text container
		addText(MAIN_INTERFACE + index++, "6d 23h 59m", 1, 0xFFFFFF, true, true);//Text container
		hoverButton(MAIN_INTERFACE + index++, "Claim Offer", 19, 20, "CLAIM", tda, 3, 0xFF981F, 0xffffff, true, dir);//buy button
		hoverButton(MAIN_INTERFACE + index++, "Claim Offer", 19, 20, "CLAIM", tda, 3, 0xFF981F, 0xffffff, true, dir);//buy button
		hoverButton(MAIN_INTERFACE + index++, "Claim Offer", 19, 20, "CLAIM", tda, 3, 0xFF981F, 0xffffff, true, dir);//buy button
		addText(MAIN_INTERFACE + index++, "DESCRIPTIONNNNNNNNN\\nDESCRIPTIONNNNNNNNN\\nDESCRIPTIONNNNNNNNN", 1, 0xFFFFFF, true, true);//Text container
		addText(MAIN_INTERFACE + index++, "DESCRIPTIONNNNNNNNN\\nDESCRIPTIONNNNNNNNN\\nDESCRIPTIONNNNNNNNN", 1, 0xFFFFFF, true, true);//Text container
		addText(MAIN_INTERFACE + index++, "DESCRIPTIONNNNNNNNN\\nDESCRIPTIONNNNNNNNN\\nDESCRIPTIONNNNNNNNN", 1, 0xFFFFFF, true, true);//Text container

		index = 0;

		main.child(index++, MAIN_INTERFACE + index, baseX, baseY); //background
		main.child(index++, MAIN_INTERFACE + index, baseX + 220, baseY + 10); //title
		main.child(index++, MAIN_INTERFACE + index, 405 + baseX, 10 + baseY); //close button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 75 + baseY); //Boosts button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 136 + baseY); //bonus button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 197 + baseY); //cosmetic button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 257 + baseY); //timed button
		main.child(index++, MAIN_INTERFACE + index, 120 + baseX, 45 + baseY); //deals button
		main.child(index++, MAIN_INTERFACE + index, 259 + baseX, 45 + baseY); //store button
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 107); //Boosts
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 167); //Items
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 228); //Cosmetics
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 287); //Offers
		main.child(index++, MAIN_INTERFACE + index, baseX + 89, baseY + 75); //Offers

		main.child(index++, MAIN_INTERFACE + index, baseX + 94, baseY + 103); //Item Containers
		main.child(index++, MAIN_INTERFACE + index, baseX + 94, baseY + 172); //Item Containers
		main.child(index++, MAIN_INTERFACE + index, baseX + 94, baseY + 241); //Item Containers
		main.child(index++, MAIN_INTERFACE + index, baseX + 145, baseY + 142); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 145, baseY + 211); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 145, baseY + 280); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 332, baseY + 115); //buy button
		main.child(index++, MAIN_INTERFACE + index, baseX + 332, baseY + 185); //buy button
		main.child(index++, MAIN_INTERFACE + index, baseX + 332, baseY + 255); //buy button
		main.child(index++, MAIN_INTERFACE + index, baseX + 250, baseY + 110); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 250, baseY + 180); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 250, baseY + 250); //Text container
	}

	private static void cosmeticDealsInterface(TextDrawingArea[] tda) {
		int MAIN_INTERFACE = 24565;
		RSInterface main = addInterface(MAIN_INTERFACE);
		String dir = "Interfaces/deals/SPRITE";

		int index = 1;
		int baseX = 45, baseY = 8;

		main.totalChildren(34);

		addSprite(MAIN_INTERFACE + index++, 0, dir);//background
		addText(MAIN_INTERFACE + index++, "Exiled Deals Hub", 2, 0xFF981F, true, true); //title
		configHoverButton(MAIN_INTERFACE + index, "Close interface", dir, 21, 22, 21, 21, false, MAIN_INTERFACE + index++);//close button
		configHoverButton(MAIN_INTERFACE + index, "Account Boosts", dir, 9, 10, 9, 9, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Bonus Items", dir, 15, 16, 15, 15, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Cosmetics", dir, 12, 12, 12, 12, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Timed Offers", dir, 13, 14, 13, 13, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Deals", dir, 7, 8, 7, 7, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Store", dir, 5, 6, 5, 5, false, MAIN_INTERFACE + index++);
		addText(MAIN_INTERFACE + index++, "Boosts", 1, 0xffffff, true, true); //Boosts
		addText(MAIN_INTERFACE + index++, "Items", 1, 0xffffff, true, true); //Items
		addText(MAIN_INTERFACE + index++, "Cosmetics", 1, 0xffffff, true, true); //Cosmetics
		addText(MAIN_INTERFACE + index++, "Offers", 1, 0xffffff, true, true); //Offers
		addSprites(MAIN_INTERFACE + index++, dir, 3, 3, 3);//


		addText(MAIN_INTERFACE + index++, "Every $1 Spent = 1 Credit", 2, 0xFF981F, true, true);
		addText(MAIN_INTERFACE + index++, "Credits: 1000", 2, 0xFFFFFF, true, true);
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);//Item Containers
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);//Item Containers
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);//Item Containers
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);//Item Containers
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);//Item Containers
		addItemContainer(MAIN_INTERFACE + index++, 1, 1, 0, 0, true);//Item Containers
		addText(MAIN_INTERFACE + index++, "30 credits", 2, 0xFFFFFF, true, true);//Text container
		addText(MAIN_INTERFACE + index++, "30 credits", 2, 0xFFFFFF, true, true);//Text container
		addText(MAIN_INTERFACE + index++, "30 credits", 2, 0xFFFFFF, true, true);//Text container
		addText(MAIN_INTERFACE + index++, "30 credits", 2, 0xFFFFFF, true, true);//Text container
		addText(MAIN_INTERFACE + index++, "30 credits", 2, 0xFFFFFF, true, true);//Text container
		addText(MAIN_INTERFACE + index++, "30 credits", 2, 0xFFFFFF, true, true);//Text container
		hoverButton(MAIN_INTERFACE + index++, "Buy cosmetic", 17, 18, "Buy", tda, 2, 0xFF981F, 0xffffff, true, dir);
		hoverButton(MAIN_INTERFACE + index++, "Buy cosmetic", 17, 18, "Buy", tda, 2, 0xFF981F, 0xffffff, true, dir);
		hoverButton(MAIN_INTERFACE + index++, "Buy cosmetic", 17, 18, "Buy", tda, 2, 0xFF981F, 0xffffff, true, dir);
		hoverButton(MAIN_INTERFACE + index++, "Buy cosmetic", 17, 18, "Buy", tda, 2, 0xFF981F, 0xffffff, true, dir);
		hoverButton(MAIN_INTERFACE + index++, "Buy cosmetic", 17, 18, "Buy", tda, 2, 0xFF981F, 0xffffff, true, dir);
		hoverButton(MAIN_INTERFACE + index++, "Buy cosmetic", 17, 18, "Buy", tda, 2, 0xFF981F, 0xffffff, true, dir);


		index = 0;

		main.child(index++, MAIN_INTERFACE + index, baseX, baseY); //background
		main.child(index++, MAIN_INTERFACE + index, baseX + 220, baseY + 10); //title
		main.child(index++, MAIN_INTERFACE + index, 405 + baseX, 10 + baseY); //close button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 75 + baseY); //Boosts button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 136 + baseY); //bonus button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 197 + baseY); //cosmetic button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 257 + baseY); //timed button
		main.child(index++, MAIN_INTERFACE + index, 120 + baseX, 45 + baseY); //deals button
		main.child(index++, MAIN_INTERFACE + index, 259 + baseX, 45 + baseY); //store button
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 107); //Boosts
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 167); //Items
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 228); //Cosmetics
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 287); //Offers
		main.child(index++, MAIN_INTERFACE + index, baseX + 89, baseY + 75); //Offers
		main.child(index++, MAIN_INTERFACE + index, baseX + 185, baseY + 86); //timer
		main.child(index++, MAIN_INTERFACE + index, baseX + 327, baseY + 86); //timer

		main.child(index++, MAIN_INTERFACE + index, baseX + 125, baseY + 115); //Item Containers
		main.child(index++, MAIN_INTERFACE + index, baseX + 239, baseY + 115); //Item Containers
		main.child(index++, MAIN_INTERFACE + index, baseX + 352, baseY + 115); //Item Containers
		main.child(index++, MAIN_INTERFACE + index, baseX + 125, baseY + 215); //Item Containers
		main.child(index++, MAIN_INTERFACE + index, baseX + 239, baseY + 215); //Item Containers
		main.child(index++, MAIN_INTERFACE + index, baseX + 352, baseY + 215); //Item Containers
		main.child(index++, MAIN_INTERFACE + index, baseX + 143, baseY + 153); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 257, baseY + 153); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 370, baseY + 153); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 142, baseY + 253); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 257, baseY + 253); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 370, baseY + 253); //Text container
		main.child(index++, MAIN_INTERFACE + index, baseX + 105, baseY + 171); //buy button
		main.child(index++, MAIN_INTERFACE + index, baseX + 219, baseY + 171); //buy button
		main.child(index++, MAIN_INTERFACE + index, baseX + 332, baseY + 171); //buy button
		main.child(index++, MAIN_INTERFACE + index, baseX + 105, baseY + 271); //buy button
		main.child(index++, MAIN_INTERFACE + index, baseX + 219, baseY + 271); //buy button
		main.child(index++, MAIN_INTERFACE + index, baseX + 332, baseY + 271); //buy button


	}

	private static void boostsInterface(TextDrawingArea[] tda) {
		int MAIN_INTERFACE = 24505;
		RSInterface main = addInterface(MAIN_INTERFACE);
		String dir = "Interfaces/deals/SPRITE";

		int index = 1;
		int baseX = 45, baseY = 8;

		main.totalChildren(25);

		addSprite(MAIN_INTERFACE + index++, 0, dir);//background
		addText(MAIN_INTERFACE + index++, "Exiled Deals Hub", 2, 0xFF981F, true, true); //title
		configHoverButton(MAIN_INTERFACE + index, "Close interface", dir, 21, 22, 21, 21, false, MAIN_INTERFACE + index++);//close button
		configHoverButton(MAIN_INTERFACE + index, "Account Boosts", dir, 10, 10, 10, 10, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Bonus Items", dir, 15, 16, 15, 15, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Cosmetics", dir, 11, 12, 11, 11, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Timed Offers", dir, 13, 14, 13, 13, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Deals", dir, 7, 8, 7, 7, false, MAIN_INTERFACE + index++);
		configHoverButton(MAIN_INTERFACE + index, "Store", dir, 5, 6, 5, 5, false, MAIN_INTERFACE + index++);
		addText(MAIN_INTERFACE + index++, "Boosts", 1, 0xffffff, true, true); //Boosts
		addText(MAIN_INTERFACE + index++, "Items", 1, 0xffffff, true, true); //Items
		addText(MAIN_INTERFACE + index++, "Cosmetics", 1, 0xffffff, true, true); //Cosmetics
		addText(MAIN_INTERFACE + index++, "Offers", 1, 0xffffff, true, true); //Offers
		addSprites(MAIN_INTERFACE + index++, dir, 1, 1, 1);//

		/** Boost's text Information (Autoclaimed when spending X Amount) **/
		new ProgressBar(MAIN_INTERFACE + index++, 120, 20, new int[]{0x009a1a}, true, false, "", new int[]{0x9a0000});
		new ProgressBar(MAIN_INTERFACE + index++, 120, 20, new int[]{0x009a1a}, true, false, "", new int[]{0x9a0000});
		new ProgressBar(MAIN_INTERFACE + index++, 120, 20, new int[]{0x009a1a}, true, false, "", new int[]{0x9a0000});
		new ProgressBar(MAIN_INTERFACE + index++, 120, 20, new int[]{0x009a1a}, true, false, "", new int[]{0x9a0000});
		new ProgressBar(MAIN_INTERFACE + index++, 120, 20, new int[]{0x009a1a}, true, false, "", new int[]{0x9a0000});
		addText(MAIN_INTERFACE + index++, "$45 / $50", 2, 0xFF981F, true, true);
		addText(MAIN_INTERFACE + index++, "$45 / $100", 2, 0xFF981F, true, true);
		addText(MAIN_INTERFACE + index++, "$45 / $250", 2, 0xFF981F, true, true);
		addText(MAIN_INTERFACE + index++, "$45 / $500", 2, 0xFF981F, true, true);
		addText(MAIN_INTERFACE + index++, "$45 / $1000", 2, 0xFF981F, true, true);
		addText(MAIN_INTERFACE + index++, "6d 23h", 1, 0xffffff, true, true);
		/** Boost's text Information (Autoclaimed when spending X Amount) **/

		/**  **/

		index = 0;

		main.child(index++, MAIN_INTERFACE + index, baseX, baseY); //background
		main.child(index++, MAIN_INTERFACE + index, baseX + 220, baseY + 10); //title
		main.child(index++, MAIN_INTERFACE + index, 405 + baseX, 10 + baseY); //close button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 75 + baseY); //Boosts button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 136 + baseY); //bonus button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 197 + baseY); //cosmetic button
		main.child(index++, MAIN_INTERFACE + index, 14 + baseX, 257 + baseY); //timed button
		main.child(index++, MAIN_INTERFACE + index, 120 + baseX, 45 + baseY); //deals button
		main.child(index++, MAIN_INTERFACE + index, 259 + baseX, 45 + baseY); //store button
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 107); //Boosts
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 167); //Items
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 228); //Cosmetics
		main.child(index++, MAIN_INTERFACE + index, baseX + 48, baseY + 287); //Offers
		main.child(index++, MAIN_INTERFACE + index, baseX + 89, baseY + 75); //Offers

		/** Boost's text Information (Autoclaimed when spending X Amount) **/
		main.child(index++, MAIN_INTERFACE + index, baseX + 191, baseY + 100); //Progress bar
		main.child(index++, MAIN_INTERFACE + index, baseX + 191, baseY + 141); //Progress bar
		main.child(index++, MAIN_INTERFACE + index, baseX + 191, baseY + 182); //Progress bar
		main.child(index++, MAIN_INTERFACE + index, baseX + 191, baseY + 223); //Progress bar
		main.child(index++, MAIN_INTERFACE + index, baseX + 191, baseY + 264); //Progress bar
		main.child(index++, MAIN_INTERFACE + index, baseX + 249, baseY + 121); //price / price
		main.child(index++, MAIN_INTERFACE + index, baseX + 249, baseY + 162); //price / price
		main.child(index++, MAIN_INTERFACE + index, baseX + 249, baseY + 203); //price / price
		main.child(index++, MAIN_INTERFACE + index, baseX + 249, baseY + 244); //price / price
		main.child(index++, MAIN_INTERFACE + index, baseX + 249, baseY + 285); //price / price
		main.child(index++, MAIN_INTERFACE + index, baseX + 287, baseY + 80); //timer
		/** Boost's text Information (Autoclaimed when spending X Amount) **/

	}

	private static void cosmeticOverride(TextDrawingArea[] tda) {
		int MAIN_INTERFACE = 42_669;
		RSInterface main = addInterface(MAIN_INTERFACE);
		String dir = "Interfaces/cosmetic/SPRITE";

		int index = 1;
		int c = 0;
		int basex = 5, basey = 50;

		main.totalChildren(17);
		addSprite(MAIN_INTERFACE + index, 0, dir);
		main.child(c++, MAIN_INTERFACE + index++, basex, basey);

		addHoverButton(MAIN_INTERFACE + index, dir, 1, 16, 16, "Close Window", 0, MAIN_INTERFACE + 1, 3);
		main.child(c++, MAIN_INTERFACE + index++, basex + 140, basey + 10);
		addHoveredButton(MAIN_INTERFACE + index, dir, 2, 16, 16, 1);
		main.child(c++, MAIN_INTERFACE + index++, basex + 140, basey + 10);

		addText(MAIN_INTERFACE + index, 1, RSInterface.DEFAULT_TEXT_COLOR, false, "Cosmetic Overrides");
		main.child(c++, MAIN_INTERFACE + index++, basex + 27, basey + 10);

		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 70, basey + 45);//HELM
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 110, basey + 45);//AURA
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 30, basey + 85);//CAPE
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 70, basey + 85);//NECK
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 110, basey + 85);//ARROW
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 25, basey + 125);//WEAPON
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 70, basey + 125);//CHEST
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 115, basey + 125);//SHIELD
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 70, basey + 165);//LEGS
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 25, basey + 205);//GLOVES
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 70, basey + 205);//BOOTS
		addItemContainer(MAIN_INTERFACE + index, 1, 1, 0, 0, false, "Remove", "toggle");
		main.child(c++, MAIN_INTERFACE + index++, basex + 115, basey + 205);//RING

	}

	private static void coxRewards(TextDrawingArea[] tda) {
		int MAIN_INTERFACE = 22725;
		RSInterface main = addInterface(MAIN_INTERFACE);
		String dir = "Interfaces/cox/SPRITE";


		int index = 1;
		int c = 0;
		int basex = 100, basey = 50;

		main.totalChildren(5);
		addSprite(MAIN_INTERFACE + index, 3, dir);
		main.child(c++, MAIN_INTERFACE + index++, basex, basey);

		addHoverButton(MAIN_INTERFACE + index, dir, 0, 16, 16, "Close Window", 0, MAIN_INTERFACE + 1, 3);
		main.child(c++, MAIN_INTERFACE + index++, basex + 250, basey + 9);
		addHoveredButton(MAIN_INTERFACE + index, dir, 1, 16, 16, 1);
		main.child(c++, MAIN_INTERFACE + index++, basex + 250, basey + 9);

		addItemContainer(MAIN_INTERFACE + index, 3, 4, 5, 5, false);
		main.child(c++, MAIN_INTERFACE + index++, basex + 150, basey + 35);

		addText(MAIN_INTERFACE + index, "Rewards", tda, 2, 0xFF981F);
		main.child(c++, MAIN_INTERFACE + index++, basex + 125, basey + 9);


	}

	private static void IngameStore(TextDrawingArea[] tda) {
		int MAIN_INTERFACE = 60400;
		int SUB_INTERFACE = 60500;
		int SUB_INTERFACE2 = 60700;
		RSInterface main = addInterface(MAIN_INTERFACE);
		String dir = "Interfaces/store/SPRITE";

		int index = 1;
		int c = 0;
		int basex = 50, basey = 10;

		main.totalChildren(11);
		addSprite(MAIN_INTERFACE + index, 0, dir);
		main.child(c++, MAIN_INTERFACE + index++, basex, basey);

		addHoverButton(MAIN_INTERFACE + index, dir, 8, 16, 16, "Close Window", 0, MAIN_INTERFACE + 1, 3);
		main.child(c++, MAIN_INTERFACE + index++, basex + 390, basey + 10);
		addHoveredButton(MAIN_INTERFACE + index, dir, 9, 16, 16, 1);
		main.child(c++, MAIN_INTERFACE + index++, basex + 390, basey + 10);

		addText(MAIN_INTERFACE + index, "Exiled's InGame Store", tda, 2, 0xff9933);
		main.child(c++, MAIN_INTERFACE + index++, basex + 100, basey + 10);

		addText(MAIN_INTERFACE + index, "Subtotal:", tda, 0, 0xff9933);
		main.child(c++, MAIN_INTERFACE + index++, basex + 235, basey + 267);

		addText(MAIN_INTERFACE + index, 0, 0xffe373, false, "$0.00");
		main.child(c++, MAIN_INTERFACE + index++, basex + 280, basey + 267);

		configHoverButton(MAIN_INTERFACE + index, "Checkout", dir, 5, 4, 5, 4, false);
		main.child(c++, MAIN_INTERFACE + index++, basex + 235, basey + 280);

		addText(MAIN_INTERFACE + index, "Pay With Paypal", tda, 1, 0xffFFFF);
		main.child(c++, MAIN_INTERFACE + index++, basex + 275, basey + 280);

		main.child(c++, SUB_INTERFACE, basex + 231, basey + 45);//CART
		main.child(c++, SUB_INTERFACE2, basex + 10, basey + 42);//STORE

		int boxes1 = 30;

		RSInterface teleScroll = addTabInterface(SUB_INTERFACE);
		teleScroll.scrollMax = 37 * boxes1;
		teleScroll.width = 155;
		teleScroll.height = 215;

		teleScroll.totalChildren(boxes1 * 5);

		int id = SUB_INTERFACE + 1, frame = 0;
		basex = 1;
		basey = 0;
		for (int i = 1; i < boxes1 + 1; i++) {
			addSprite(id, (i % 2 == 0 ? 2 : 3), dir);
			teleScroll.child(frame++, id++, basex, basey);
			addItemContainer(id, 1, 1, 0, 0, false, "Remove", "Clear Cart");
			teleScroll.child(frame++, id++, basex + 2, basey + 2);
			addText(id, 0, 0xff9933, false, "");
			teleScroll.child(frame++, id++, basex + 32, basey + 2);
			addText(id, 0, 0xffe373, true, "");
			teleScroll.child(frame++, id++, basex + 52, basey + 20);
			addText(id, 0, 0xb6ffa5, true, "");
			teleScroll.child(frame++, id++, basex + 123, basey + 20);

			basey += 37;
		}

		int boxes2 = 20;

		RSInterface teleScroll2 = addTabInterface(SUB_INTERFACE2);
		teleScroll2.scrollMax = 52 * boxes2;
		teleScroll2.width = 200;
		teleScroll2.height = 262;

		teleScroll2.totalChildren(boxes2 * 12);

		int id2 = SUB_INTERFACE2 + 1, frame2 = 0;
		basex = 1;
		basey = 0;
		for (int i = 1; i < boxes2 + 1; i++) {
			for (int j = 0; j < 4; j++) {
				addSprite(id2, 1, dir);
				teleScroll2.child(frame2++, id2++, basex + 2, basey + 2);

				addItemContainer(id2, 1, 1, 0, 0, false, "Add to cart");
				teleScroll2.child(frame2++, id2++, basex + 8, basey + 4);//10292

				addText(id2, 0, 0xffe373, true, "");
				teleScroll2.child(frame2++, id2++, basex + 22, basey + 41);//10292

				basex += 49;
			}
			basey += 52;
			basex = 1;
		}
	}

	private static void questing(TextDrawingArea[] tda) {
		int MAIN_INTERFACE = 55325;
		int SUB_INTERFACE = 55425;
		RSInterface main = addInterface(MAIN_INTERFACE);
		String dir = "Interfaces/questing/SPRITE";
		int index = 1;
		int c = 0;
		int basex = 10, basey = 10;

		main.totalChildren(21);
		addSprite(MAIN_INTERFACE + index, 0, dir);
		main.child(c++, MAIN_INTERFACE + index++, basex, basey);

		addHoverButton(MAIN_INTERFACE + index, dir, 1, 16, 16, "Close Window", 0, MAIN_INTERFACE + 1, 3);
		main.child(c++, MAIN_INTERFACE + index++, basex + 472, basey + 10);
		addHoveredButton(MAIN_INTERFACE + index, dir, 2, 16, 16, 1);
		main.child(c++, MAIN_INTERFACE + index++, basex + 472, basey + 10);

		addText(MAIN_INTERFACE + index, "", tda, 2, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 41);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 61);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 81);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 101);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 121);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 141);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 161);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 181);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 201);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 221);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 241);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 261);
		addText(MAIN_INTERFACE + index, "", tda, 1, 0xff9933, true, true);
		main.child(c++, MAIN_INTERFACE + index++, basex + 335, basey + 281);

		addText(MAIN_INTERFACE + index, "Exiled's Quest List", tda, 2, 0xff9933);
		main.child(c++, MAIN_INTERFACE + index++, basex + 200, basey + 10);

		main.child(c++, SUB_INTERFACE, basex + 6, basey + 41);//TOP BAR

		int boxes1 = 120;

		RSInterface teleScroll = addTabInterface(SUB_INTERFACE);
		teleScroll.scrollMax = 20 * boxes1;
		teleScroll.width = 160;
		teleScroll.height = 264;

		teleScroll.totalChildren(boxes1);

		int id = SUB_INTERFACE + 1, frame = 0;
		basex = 1;
		basey = 0;
		for (int i = 1; i < boxes1 + 1; i++) {
			addClickableText(id, "", "Select", tda, 0, 0xff9933, false, true, 160);
			teleScroll.child(frame, id, basex + 6, basey + 2);//10292
			id++;
			frame++;
			basey += 20;
		}
	}

	public static void addWheel(int id, int width, int height, int strokeWidth, int color,
								int alpha, int closure, boolean fill, int segments, int spriteId, String dir) {
		RSInterface widget = addInterface(id);
		widget.wheel = new WheelOfFortune(width, height, strokeWidth, color, alpha, closure, fill, segments, imageLoader(spriteId, dir));
		widget.type = 150;
	}

	public WheelOfFortune wheel;

	private static void wheelOfFortune(TextDrawingArea[] fonts) {
		final int STARTING_POINT = 23354;
		RSInterface main = addInterface(STARTING_POINT);

		String dir = "Interfaces/spinwheel/SPRITE";

		addSprite(STARTING_POINT + 1, 1192, dir); // 1184 -> 1192
		addWheel(STARTING_POINT + 2, 210, 210, 2, 0x3f372c, 255, 2, true, 10, 1193, dir);
		main.totalChildren(13);
		main.child(0, STARTING_POINT + 1, 50, 10);
		main.child(1, STARTING_POINT + 2, 80, 65);
		configHoverButton1(STARTING_POINT + 3, "Spin Wheel", dir, 1195, 1196, 1196, 1196, false, new int[]{STARTING_POINT + 3});
		main.child(2, STARTING_POINT + 3, 146, 283);
		addText(STARTING_POINT + 4, "Spin Wheel!", 1, 0xff8624, false, true);
		main.child(3, STARTING_POINT + 4, 160, 290);

		addPet(STARTING_POINT + 5, 1013);
		main.child(4, STARTING_POINT + 5, 335, 186);

		addSprite(STARTING_POINT + 6, 1194, dir);
		main.child(5, STARTING_POINT + 6, 301, 168);
		addSprite(STARTING_POINT + 7, 1198, dir);
		main.child(6, STARTING_POINT + 7, 282, 62);
		addText(STARTING_POINT + 8, "Spin the wheel", 1, 0xff8624, false, true);
		main.child(7, STARTING_POINT + 8, 287, 72);
		addText(STARTING_POINT + 9, "for a reward!", 1, 0xff8624, false, true);
		main.child(8, STARTING_POINT + 9, 292, 84);
		configHoverButton1(STARTING_POINT + 10, "close", dir, 737, 738, 738, 738, false, new int[]{STARTING_POINT + 10});
		main.child(9, STARTING_POINT + 10, 441, 21);

		addText(STARTING_POINT + 11, "Wheel Of Fortune", 2, 0xff8624, true, true);
		main.child(10, STARTING_POINT + 11, 264, 19);
		addTransparentLayer(STARTING_POINT + 12, 0, 120);
		main.child(11, STARTING_POINT + 12, 50, 10);
		main.child(12, STARTING_POINT + 20, 173, 48);

		RSInterface rewardInterface = addInterface(STARTING_POINT + 20);
		rewardInterface.interfaceHidden = true;
		rewardInterface.totalChildren(7);
		addSprite(STARTING_POINT + 21, 1197, dir);
		rewardInterface.child(0, STARTING_POINT + 21, 0, 0);
		configHoverButton1(STARTING_POINT + 22, "close", dir, 737, 738, 738, 738, false, new int[]{STARTING_POINT + 22});
		rewardInterface.child(1, STARTING_POINT + 22, 164, 9);
		addText(STARTING_POINT + 23, "Reward", 2, 0xff8624, true, true);
		rewardInterface.child(2, STARTING_POINT + 23, 94, 10);
		addText(STARTING_POINT + 24, "You've won:", 2, 0xff8624, false, true);
		rewardInterface.child(3, STARTING_POINT + 24, 57, 43);
		configHoverButton1(STARTING_POINT + 25, "Spin Again!", dir, 1195, 1196, 1196, 1196, false, new int[]{STARTING_POINT + 25});
		rewardInterface.child(4, STARTING_POINT + 25, 51, 198);
		addText(STARTING_POINT + 26, "Spin Again!", 0, 0xff8624, false, true);
		rewardInterface.child(5, STARTING_POINT + 26, 69, 205);

		addModel(STARTING_POINT + 27, 75, 50, 4447, 800, 3500);
		rewardInterface.child(6, STARTING_POINT + 27, 63, 105);
	}

	public static void perkInformation(TextDrawingArea[] tda) {
		int baseId = 18910;
		int teleScrollId = 18920;
		int basex = 10, basey = 6;
		RSInterface inter = addInterface(baseId);
		String dir = "Interfaces/prestigerelics/SPRITE";
		int index = 1;
		inter.totalChildren(60);
		addSprite(baseId + index++, 0, dir);
		addText(baseId + index++, "Prestige Points: ", 2, 0xFF0000, true, true);
		addText(baseId + index++, "Current Tier: ", 2, 0x00FF00, true, true);
		addText(baseId + index++, "Prestige Relics", 2, 0xff8624, true, true);
		configHoverButton1(baseId + index, "Close", dir, 40, 41, 41, 41, false, new int[]{baseId + index++});

		addConfigSprite(baseId + index++, 1, dir, 37, dir, 0, 2400);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Nov. XP", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 2, dir, 20, dir, 0, 2401);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "BloodyBattle", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 3, dir, 21, dir, 0, 2402);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Super XP", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 4, dir, 22, dir, 0, 2403);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Pro Perks", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 5, dir, 23, dir, 0, 2404);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Godly XP", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 6, dir, 24, dir, 0, 2405);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Ultra Boxes", tda, 1, 0xFFFFFF, true, true);

		addConfigSprite(baseId + index++, 7, dir, 25, dir, 0, 2406);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Nov. Damage", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 8, dir, 26, dir, 0, 2407);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Cannon God", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 9, dir, 27, dir, 0, 2408);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Sup. Damage", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 10, dir, 28, dir, 0, 2409);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Nomad's Eye", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 11, dir, 29, dir, 0, 2410);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Godly Dmg", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 12, dir, 31, dir, 0, 2411);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Ark's Dmg", tda, 1, 0xFFFFFF, true, true);

		addConfigSprite(baseId + index++, 13, dir, 32, dir, 0, 2412);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "2x Chests", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 14, dir, 33, dir, 0, 2413);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Triple Keys", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 15, dir, 34, dir, 0, 2414);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Quick Prayer", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 16, dir, 35, dir, 0, 2415);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Quick HP", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 17, dir, 36, dir, 0, 2416);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "Nomad's Demise", tda, 1, 0xFFFFFF, true, true);
		addConfigSprite(baseId + index++, 18, dir, 19, dir, 0, 2417);//
		addClickableText(baseId + index++, "", "Unlock", tda, 1, 0x00ff00, true, true, 30, 30);
		addText(baseId + index++, "RAGE", tda, 1, 0xFFFFFF, true, true);


		index = 0;
		inter.child(index++, baseId + index, basex + 4, basey);
		inter.child(index++, baseId + index, basex + 320, basey + 45);
		inter.child(index++, baseId + index, basex + 65, basey + 45);
		inter.child(index++, baseId + index, basex + 250, basey + 10);
		inter.child(index++, baseId + index, basex + 455, basey + 10);

		inter.child(index++, baseId + index, basex + 34, basey + 93);
		inter.child(index++, baseId + index, basex + 34, basey + 93);
		inter.child(index++, baseId + index, basex + 34 + 15, basey + 93 + 27);
		inter.child(index++, baseId + index, basex + 112, basey + 93);
		inter.child(index++, baseId + index, basex + 112, basey + 93);
		inter.child(index++, baseId + index, basex + 112 + 15, basey + 93 + 27);
		inter.child(index++, baseId + index, basex + 190, basey + 93);
		inter.child(index++, baseId + index, basex + 190, basey + 93);
		inter.child(index++, baseId + index, basex + 190 + 15, basey + 93 + 27);
		inter.child(index++, baseId + index, basex + 268, basey + 93);
		inter.child(index++, baseId + index, basex + 268, basey + 93);
		inter.child(index++, baseId + index, basex + 268 + 15, basey + 93 + 27);
		inter.child(index++, baseId + index, basex + 346, basey + 93);
		inter.child(index++, baseId + index, basex + 346, basey + 93);
		inter.child(index++, baseId + index, basex + 346 + 15, basey + 93 + 27);
		inter.child(index++, baseId + index, basex + 424, basey + 93);
		inter.child(index++, baseId + index, basex + 424, basey + 93);
		inter.child(index++, baseId + index, basex + 424 + 15, basey + 93 + 27);

		inter.child(index++, baseId + index, basex + 34, basey + 166);
		inter.child(index++, baseId + index, basex + 34, basey + 166);
		inter.child(index++, baseId + index, basex + 34 + 15, basey + 166 + 27);
		inter.child(index++, baseId + index, basex + 112, basey + 166);
		inter.child(index++, baseId + index, basex + 112, basey + 166);
		inter.child(index++, baseId + index, basex + 112 + 15, basey + 166 + 27);
		inter.child(index++, baseId + index, basex + 190, basey + 166);
		inter.child(index++, baseId + index, basex + 190, basey + 166);
		inter.child(index++, baseId + index, basex + 190 + 15, basey + 166 + 27);
		inter.child(index++, baseId + index, basex + 268, basey + 166);
		inter.child(index++, baseId + index, basex + 268, basey + 166);
		inter.child(index++, baseId + index, basex + 268 + 15, basey + 166 + 27);
		inter.child(index++, baseId + index, basex + 346, basey + 166);
		inter.child(index++, baseId + index, basex + 346, basey + 166);
		inter.child(index++, baseId + index, basex + 346 + 15, basey + 166 + 27);
		inter.child(index++, baseId + index, basex + 424, basey + 166);
		inter.child(index++, baseId + index, basex + 424, basey + 166);
		inter.child(index++, baseId + index, basex + 424 + 15, basey + 166 + 27);

		inter.child(index++, baseId + index, basex + 34, basey + 239);
		inter.child(index++, baseId + index, basex + 34, basey + 239);
		inter.child(index++, baseId + index, basex + 34 + 15, basey + 239 + 27);
		inter.child(index++, baseId + index, basex + 112, basey + 239);
		inter.child(index++, baseId + index, basex + 112, basey + 239);
		inter.child(index++, baseId + index, basex + 112 + 15, basey + 239 + 27);
		inter.child(index++, baseId + index, basex + 190, basey + 239);
		inter.child(index++, baseId + index, basex + 190, basey + 239);
		inter.child(index++, baseId + index, basex + 190 + 15, basey + 239 + 27);
		inter.child(index++, baseId + index, basex + 268, basey + 239);
		inter.child(index++, baseId + index, basex + 268, basey + 239);
		inter.child(index++, baseId + index, basex + 268 + 15, basey + 239 + 27);
		inter.child(index++, baseId + index, basex + 346, basey + 239);
		inter.child(index++, baseId + index, basex + 346, basey + 239);
		inter.child(index++, baseId + index, basex + 346 + 15, basey + 239 + 27);
		inter.child(index++, baseId + index, basex + 424, basey + 239);
		inter.child(index++, baseId + index, basex + 424, basey + 239);
		inter.child(index++, baseId + index, basex + 424 + 15, basey + 239 + 27);
	}

	private static void StarterNew(TextDrawingArea[] tda) {
		RSInterface Interface = addInterface(24303);
		addSprite(24304, 0, "Interfaces/starterInt/SPRITE");
		addText(24305, "Exiled Account Setup", 0xff9933, true, true, 52, tda, 2);
		addText(24306, "Mode Description:", 0xff9933, true, true, 52, tda, 2);
		addText(24307, "Description line here (24307)", 0xff9933, true, true, 52, tda, 0);
		addText(24308, "Description line here (24308)", 0xff9933, true, true, 52, tda, 0);
		addText(24309, "Description line here (24309)", 0xff9933, true, true, 52, tda, 0);
		addText(24310, "Description line here (243010)", 0xff9933, true, true, 52, tda, 0);
		addConfigButton(24311, 45000, 3, 4, "Interfaces/starterInt/SPRITE", 15, 15, "Select Normal account settings", 0, 5, 1085);
		addConfigButton(24312, 45000, 3, 4, "Interfaces/starterInt/SPRITE", 15, 15, "Select Ironman Ironman settings", 1, 5, 1085);
		addConfigButton(24313, 45000, 3, 4, "Interfaces/starterInt/SPRITE", 15, 15, "Select Ultimate Ironman account settings", 2, 5, 1085);
		addConfigButton(24314, 45000, 3, 4, "Interfaces/starterInt/SPRITE", 15, 15, "Select Hardcore Ironman account settings", 3, 5, 1085);
		addConfigButton(24315, 45000, 3, 4, "Interfaces/starterInt/SPRITE", 15, 15, "Select Group Ironman account settings", 4, 5, 1085);
		addConfigButton(24316, 45000, 3, 4, "Interfaces/starterInt/SPRITE", 15, 15, "Select Rogue Ironman account settings", 5, 5, 1085);
		addConfigButton(24317, 45000, 3, 4, "Interfaces/starterInt/SPRITE", 15, 15, "Select Rogue Hardcore account settings", 6, 5, 1085);
		addConfigButton(24318, 45000, 3, 4, "Interfaces/starterInt/SPRITE", 15, 15, "Select OSRS Ironman account settings", 7, 5, 1085);
		addConfigButton(24319, 45000, 3, 4, "Interfaces/starterInt/SPRITE", 15, 15, "Select Wildyman account settings", 8, 5, 1085);
		addConfigButton(24320, 45000, 3, 4, "Interfaces/starterInt/SPRITE", 15, 15, "Select Hardcore Wildyman account settings", 9, 5, 1085);
		addHoverText(24321, "Normal", "Select Normal account settings", tda, 0, 0xF7AA25, false, true, 65);
		addHoverText(24322, "<img=12> Ironman", "Select Ironman account settings", tda, 0, 0xF7AA25, false, true, 65);
		addHoverText(24323, "<img=13> Ultimate Ironman", "Select Ultimate Ironman account settings", tda, 0, 0xF7AA25, false, true, 65);
		addHoverText(24324, "<img=9> Hardcore Ironman", "Select Hardcore Ironman account settings", tda, 0, 0xF7AA25, false, true, 65);
		addHoverText(24325, "<img=27> Group Ironman", "Select Group Ironman account settings", tda, 0, 0xF7AA25, false, true, 65);
		String rogue_ironman = "<img=25> Rogue Ironman";
		String rogue_hardcore = "<img=26> Rogue Hardcore";
		String osrs_ironman = "<img=30> OSRS Ironman";
		String wildyman = "<img=92> Wildyman";
		String hc_wildyman = "<img=93> HC Wildyman";
		addHoverText(24326, (Configuration.CONNECTION.equals(Connection.WORLD_1) ? rogue_ironman : wildyman),
				"Select Rogue Ironman account settings", tda, 0, 0xF7AA25, false, true, 65);

		addHoverText(24327, (Configuration.CONNECTION.equals(Connection.WORLD_1) ? rogue_hardcore : hc_wildyman),
				"Select Hardcore Rogue account settings", tda, 0, 0xF7AA25, false, true, 65);

		addHoverText(24328, (Configuration.CONNECTION.equals(Connection.WORLD_1) ? osrs_ironman : ""), (Configuration.CONNECTION.equals(Connection.WORLD_1) ? "Select OSRS account settings" : ""), tda, 0, 0xF7AA25, false, true, 65);

		addHoverText(24329, (Configuration.CONNECTION.equals(Connection.WORLD_1) ? wildyman : ""), (Configuration.CONNECTION.equals(Connection.WORLD_1) ? "Select Wildyman settings" : ""), tda, 0, 0xF7AA25, false, true, 65);

		addHoverText(24330, (Configuration.CONNECTION.equals(Connection.WORLD_1) ? hc_wildyman : ""), (Configuration.CONNECTION.equals(Connection.WORLD_1) ? "Select Hardcore Wildyman account settings" : ""), tda, 0, 0xF7AA25, false, true, 65);

		configHoverButton(24331, "Confirm", "Interfaces/starterInt/SPRITE", 2, 1, 2, 1, false);
		addChar(24332, 825);
		addToItemGroup(24333, 6, 18, 5, 4, false, null, null, null, null, null);

		Interface.totalChildren(30);
		Interface.child(0, 24304, 11, 13);
		Interface.child(1, 24305, 255, 22);
		Interface.child(2, 24306, 330, 213);
		Interface.child(3, 24307, 330, 240);
		Interface.child(4, 24308, 330, 255);
		Interface.child(5, 24309, 330, 270);
		Interface.child(6, 24310, 330, 285);
		Interface.child(7, 24311, 30, 59);
		Interface.child(8, 24312, 30, 84);
		Interface.child(9, 24313, 30, 109);
		Interface.child(10, 24314, 30, 134);
		Interface.child(11, 24315, 30, 159);
		Interface.child(12, 24316, 30, 184);
		Interface.child(13, 24317, 30, 209);
		Interface.child(14, 24318, 30, 234);
		Interface.child(15, 24319, 30, 259);
		Interface.child(16, 24320, 30, 284);


		Interface.child(17, 24321, 51, 62);
		Interface.child(18, 24322, 51, 87);
		Interface.child(19, 24323, 51, 112);
		Interface.child(20, 24324, 51, 137);
		Interface.child(21, 24325, 51, 162);
		Interface.child(22, 24326, 51, 187);
		Interface.child(23, 24327, 51, 212);
		Interface.child(24, 24328, 51, 237);
		Interface.child(25, 24329, 51, 262);
		Interface.child(26, 24330, 51, 287);

		Interface.child(27, 24331, 465, 19);
		Interface.child(28, 24332, 145, 105);
		Interface.child(29, 24333, 265, 58);
	}

	private static void WeaponGame(TextDrawingArea[] textDrawingAreas) {
		int interfaceId = 25985;
		RSInterface interfaces = addInterface(interfaceId);
		interfaceId++;
		RSInterface.setChildren(9, interfaces);
		int child = 0;
		int y = 7;

		RSInterface.addText(interfaceId, " ", textDrawingAreas, 0, Configuration.GOLD, true);
		RSInterface.setBounds(interfaceId, 195 + 25, y + 29, child, interfaces);
		interfaceId++;
		child++;
		RSInterface.addText(interfaceId, " ", textDrawingAreas, 0, Configuration.SILVER, true);
		RSInterface.setBounds(interfaceId, 195 + 80, y + 29, child, interfaces);
		interfaceId++;
		child++;
		RSInterface.addText(interfaceId, " ", textDrawingAreas, 0, Configuration.BRONZE, true);
		RSInterface.setBounds(interfaceId, 195 + 135, y + 29, child, interfaces);
		interfaceId++;
		child++;

		addTransparentSprite3(interfaceId, 0, "Interfaces/weapongames/SPRITE", 180);
		RSInterface.setBounds(interfaceId, 195, y + 9, child, interfaces);
		interfaceId++;
		child++;
		addTransparentSprite3(interfaceId, 0, "Interfaces/weapongames/SPRITE", 180);
		RSInterface.setBounds(interfaceId, 195 + 55, y + 9, child, interfaces);
		interfaceId++;
		child++;
		addTransparentSprite3(interfaceId, 0, "Interfaces/weapongames/SPRITE", 180);
		RSInterface.setBounds(interfaceId, 195 + 110, y + 9, child, interfaces);
		interfaceId++;
		child++;

		RSInterface.addText(interfaceId, " ", textDrawingAreas, 1, Configuration.YELLOW, true);
		RSInterface.setBounds(interfaceId, 195 + 25, y + 12, child, interfaces);
		interfaceId++;
		child++;
		RSInterface.addText(interfaceId, " ", textDrawingAreas, 1, Configuration.YELLOW, true);
		RSInterface.setBounds(interfaceId, 195 + 80, y + 12, child, interfaces);
		interfaceId++;
		child++;
		RSInterface.addText(interfaceId, " ", textDrawingAreas, 1, Configuration.YELLOW, true);
		RSInterface.setBounds(interfaceId, 195 + 135, y + 12, child, interfaces);
		interfaceId++;
		child++;

	}

	public static void progressionInterface(TextDrawingArea[] tda) {
		int interID = 59951;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 115;
		int y = 270;
		tab.totalChildren(8);

		addTransparentSprite3(id, 0, "Interfaces/progressbar/SPRITE", 150);
		tab.child(c++, id++, 0 + x, 0 + y);

		addText(id, "NPC Name", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 85 + x, 6 + y);

		addText(id, "Next Rewards", tda, 1, 0xFF981F, true, true);
		tab.child(c++, id++, 222 + x, 4 + y);

		new ProgressBar(id, 160, 23, new int[]{0x009a1a}, true, false, "", new int[]{0x9a0000});
		tab.child(c++, id++, 5 + x, 26 + y);

		addText(id, "50% (10/20)", tda, 0, 0xFFFFFF, true, true);
		tab.child(c++, id++, 85 + x, 32 + y);

		dropGroup(id, 1, 1, 1, 1);
		tab.child(c++, id++, 169 + x, 21 + y);
		dropGroup(id, 1, 1, 1, 1);
		tab.child(c++, id++, 206 + x, 21 + y);
		dropGroup(id, 1, 1, 1, 1);
		tab.child(c++, id++, 243 + x, 21 + y);

	}

	public static void quest2(TextDrawingArea[] tda) {
		int interfaceId = 10280;
		int teleScrollId = 10300;
		RSInterface inter = addInterface(interfaceId);
		String dir = "Interfaces/q2/SPRITE";
		int index = 1;
		int basex = 4, basey = 2;

		inter.totalChildren(14);
		addSprite(interfaceId + index++, 0, dir);
		configHoverButton1(interfaceId + index, "Collection Log", dir, 1, 2, 2, 2, false, interfaceId + index++);
		configHoverButton1(interfaceId + index, "Drop Tables", dir, 1, 2, 2, 2, false, interfaceId + index++);
		configHoverButton1(interfaceId + index, "Loot Tables", dir, 1, 2, 2, 2, false, interfaceId + index++);
		configHoverButton1(interfaceId + index, "Kill Log", dir, 1, 2, 2, 2, false, interfaceId + index++);
		configHoverButton1(interfaceId + index, "Character Information", dir, 1, 2, 2, 2, false, interfaceId + index++);
		configHoverButton1(interfaceId + index, "Achievements", dir, 1, 2, 2, 2, false, interfaceId + index++);

		addText(interfaceId + index++, "Collection Log", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "Drop Tables", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "Loot Tables", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "Kill Log", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "Char Info", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "Achievements", tda, 0, 0xff9933, false, true);

		index = 0;
		inter.child(index++, interfaceId + index, basex + 4, basey);
		inter.child(index++, interfaceId + index, 10, 170);
		inter.child(index++, interfaceId + index, 100, 170);
		inter.child(index++, interfaceId + index, 10, 200);
		inter.child(index++, interfaceId + index, 100, 200);
		inter.child(index++, interfaceId + index, 10, 230);
		inter.child(index++, interfaceId + index, 100, 230);

		inter.child(index++, interfaceId + index, 18, 177);
		inter.child(index++, interfaceId + index, 115, 177);
		inter.child(index++, interfaceId + index, 25, 207);
		inter.child(index++, interfaceId + index, 125, 207);
		inter.child(index++, interfaceId + index, 28, 237);
		inter.child(index++, interfaceId + index, 108, 237);

		inter.child(index++, teleScrollId, basex, basey + 2);//TOP BAR

		int boxes1 = 60;

		RSInterface teleScroll = addTabInterface(teleScrollId);
		teleScroll.scrollMax = 20 * boxes1;
		teleScroll.width = 160;
		teleScroll.height = 159;

		teleScroll.totalChildren(boxes1);

		int id = teleScrollId + 1, frame = 0;
		basex = 1;
		basey = 0;
		for (int i = 1; i < boxes1 + 1; i++) {
			addText(id, "tetet " + id, tda, 0, 0xff9933, false, true);
			teleScroll.child(frame, id, basex + 6, basey + 2);//10292
			id++;
			frame++;
			basey += 20;
		}
	}


	public static void instanceManager(TextDrawingArea[] tda) {
		int interID = 54000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 75;
		int y = 10;
		tab.totalChildren(14);

		addSprite(id, 0, "Interfaces/instance/image"); // 1582
		tab.child(c++, id++, 0 + x, 0 + y);

		addText(id, "Instance Manager", tda, 2, 0xff8624, true, true);
		tab.child(c++, id++, 182 + x, 4 + y);

		addHoverButton(id, "Interfaces/instance/image", 4, 16, 16, "Close Window", 0, id + 1, 3); // 1016
		tab.child(c++, id++, 340 + x, 3 + y);
		addHoveredButton(id, "Interfaces/instance/image", 5, 16, 16, 1); // 1017
		tab.child(c++, id++, 340 + x, 3 + y);
		id++;

		addText(id, "Monsters", tda, 1, 0xff8624, true, true);
		tab.child(c++, id++, 92 + x, 29 + y);

		addText(id, "Preview", tda, 1, 0xff8624, true, true);
		tab.child(c++, id++, 264 + x, 29 + y);

		dropGroup(id, 1, 1, 1, 1);
		tab.child(c++, id++, 185 + x, 179 + y);

		addText(id, "Instance token", tda, 1, 0xff8624, false, true);
		tab.child(c++, id++, 223 + x, 178 + y);

		addText(id, "Spawns: 45", tda, 1, 0xff8624, false, true);
		tab.child(c++, id++, 223 + x, 194 + y);

		addText(id, "Cost: 5k Satan tokens", tda, 1, 0xff8624, true, true);
		tab.child(c++, id++, 263 + x, 217 + y);


		addButton(id, 3, "Interfaces/instance/image", "Start Instance");
		tab.child(c++, id++, 206 + x, 272 + y);

		addText(id, "Start Instance", tda, 1, 0xff8624, true, true);
		tab.child(c++, id++, 263 + x, 277 + y);

		addPet(id, 252);
		tab.child(c++, id, 195 + x, 60 + y);

		tab.child(c++, 54070, 12 + x, 47 + y);


		interID = 54070;
		RSInterface list = addInterface(interID);
		list.width = 160 - 16;
		list.height = 256;
		list.scrollMax = 1493;
		id = interID + 1;
		c = 0;
		x = 0;
		y = 0;
		list.totalChildren(200);

		id = 54171;
		for (int i = 0; i < 100; i++) {
			addSprite(id, 2, "Interfaces/instance/image"); // 1582
			list.child(c++, id++, 0 + x, 0 + y);
			y += 36;
		}

		y = 0;
		id = 54071;
		for (int i = 0; i < 100; i++) {
			addHoverText(id, "", "Select", tda, 1, 0xFF9900, false, true, 169, 17);
			list.child(c++, id++, 2 + x, 2 + y);
			y += 18;
		}

	}

	public static void upgradeInterface(TextDrawingArea[] tda) {//this one
		int interID = 35000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 45;
		int y = 45;
		tab.totalChildren(21);

		addSprite(id, 0, "Interfaces/upgrade/background"); // 1582
		tab.child(c++, id++, x, y);

		addHoverButton(id, "Interfaces/upgrade/close", 2, 16, 16, "Close Window", 0, id + 1, 3); // 1016
		tab.child(c++, id++, 394 + x, 9 + y);
		addHoveredButton(id, "Interfaces/upgrade/close", 3, 16, 16, 1); // 1017
		tab.child(c++, id++, 394 + x, 9 + y);
		id++;

		addButton(id, 0, "Interfaces/upgrade/overlay", "Weaponry"); //  1555, 1554
		tab.child(c++, id++, 10 + x, 39 + y);
		addButton(id, 0, "Interfaces/upgrade/overlay", "Armoury"); // overlay 0 & overlay 1
		tab.child(c++, id++, 110 + x, 39 + y);
		addButton(id, 0, "Interfaces/upgrade/overlay", "Accessories");
		tab.child(c++, id++, 210 + x, 39 + y);
		addButton(id, 0, "Interfaces/upgrade/overlay", "Miscellaneous");
		tab.child(c++, id++, 310 + x, 39 + y);

		addSprite(id, 0, "Interfaces/upgrade/icon"); // icon 0
		tab.child(c++, id++, 14 + x, 42 + y);
		addSprite(id, 1, "Interfaces/upgrade/icon");// icon 1
		tab.child(c++, id++, 114 + x, 41 + y);
		addSprite(id, 2, "Interfaces/upgrade/icon");// icon 2
		tab.child(c++, id++, 216 + x, 42 + y);
		addSprite(id, 3, "Interfaces/upgrade/icon");// icon 3
		tab.child(c++, id++, 314 + x, 41 + y);

		addText(id, "Weaponry", tda, 0, 0xFF981F, false, true);
		tab.child(c++, id++, 32 + x, 44 + y);
		addText(id, "Armoury", tda, 0, 0xFF981F, false, true);
		tab.child(c++, id++, 132 + x, 44 + y);
		addText(id, "Accessories", tda, 0, 0xFF981F, false, true);
		tab.child(c++, id++, 232 + x, 44 + y);
		addText(id, "Miscellaneous", tda, 0, 0xFF981F, false, true);
		tab.child(c++, id++, 332 + x, 44 + y);

		dropGroup(id, 1, 1, 1, 1);
		tab.child(c++, id++, 335 + x, 101 + y);

		addText(id, "Tokens required: ", tda, 0, 0xFF981F, false, true);
		tab.child(c++, id++, 295 + x, 158 + y);
		addText(id, "Success rate: ", tda, 0, 0xFF981F, false, true);
		tab.child(c++, id++, 295 + x, 181 + y);


		addButton(id, 0, "Interfaces/upgrade/button", "Upgrade");
		tab.child(c++, id++, 312 + x, 208 + y);

		addText(id, "Upgrade", tda, 1, 0xFF981F);
		tab.child(c++, id++, 325 + x, 212 + y);


		tab.child(c++, 35100, 12 + x, 98 + y);

		RSInterface scroll = addInterface(35100);
		scroll.totalChildren(1);
		scroll.width = 273 - 16;
		scroll.height = 138;
		scroll.scrollMax = 500;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;

		addToItemGroup(35150, 6, 1750, 10, 10, true, "Select", null, null);
		setChildren(1, scroll);
		setBounds(35150, 6 + x, 4 + y, 0, scroll);
	}

	public static void questInterface(TextDrawingArea[] TDA) {
		RSInterface Interface = addInterface(8134);
		Interface.centerText = true;
		addSprite(8135, 0, "quest/questbg");
		addSprite(8136, 1, "quest/questbg");
		addText(8144, "Quest Name", 0x000000, true, false, 52, TDA, 3);//249 18
		addHover(8137, 3, 0, 8138, 0, "quest/close", 26, 23, "Close");
		addHovered(8138, 1, "quest/close", 26, 23, 8139);
		setChildren(6, Interface);
		setBounds(8136, 18, 4, 0, Interface);
		setBounds(8135, 18, 62, 1, Interface);
		setBounds(8144, 260, 15, 2, Interface);
		setBounds(8140, 50, 86, 3, Interface);
		setBounds(8137, 452, 63, 4, Interface);
		setBounds(8138, 452, 63, 5, Interface);
		Interface = addInterface(8140);
		Interface.height = 217;
		Interface.width = 404;
		Interface.scrollMax = 218;
		setChildren(52 + 100, Interface);
		int Ypos = 18;
		int frameID = 0;
		for (int iD = 8145; iD <= 8195; iD++) {
			addText(iD, "j", 0x000080, true, false, 52, TDA, 1);
			setBounds(iD, 202, Ypos, frameID, Interface);
			frameID++;
			Ypos += 19;
			Ypos++;
		}
		for (int iD = 21614; iD <= 21614 + 100; iD++) {
			addText(iD, "j", 0x000080, true, false, 52, TDA, 1);
			setBounds(iD, 202, Ypos, frameID, Interface);
			frameID++;
			Ypos += 19;
			Ypos++;
		}
	}

	public static void staffSpecialBar() {
		RSInterface inter = get(328);
		RSInterface.expandChildren(1, inter);
		inter.child(inter.children.length - 1, 12323, 17, 201);
	}

	public static void fireofExchange(TextDrawingArea[] tda) {
		RSInterface inter = addInterface(33400);
		addSprite(33401, 0, "Interfaces/FireOfExchange/BACKGROUND");
		addText(33402, "Nomad Item Dissolver", tda, 2, 0xff9933, true, true);
/*		addHoverButton(33932, "Interfaces/FireOfExchange/SPRITE", 3, 16, 16, "Close", -1, 33933, 3);
		addHoveredButton(33933, "Interfaces/FireOfExchange/SPRITE", 4, 16, 16, 33933);*/
		configHoverButton1(33932, "Close", "Interfaces/FireOfExchange/SPRITE", 3, 4, 4, 4, false, new int[]{33932});
		addSprite(33406, 1, "Interfaces/FireOfExchange/SPRITE");
		addText(33407, "Nomad Value:", tda, 2, 0xff9933, true, true);
		addText(33409, "0", tda, 2, 0xff0000, false, true);
//		addButton(33411, 1, "Interfaces/FireOfExchange/BUTTON", "Dissolve Item");
		configHoverButton1(33411, "Dissolve Item", "Interfaces/FireOfExchange/BUTTON", 1, 2, 2, 2, false, new int[]{33411});
		addText(33412, "Dissolve", tda, 2, 0xffffff, true, true);
		addText(33413, "Recently Dissolved Item's", tda, 2, 0xffffff, true, true);
		addText(33414, "Total Dissolved: ", tda, 2, 0xffffff, true, true);
		addText(33415, "", tda, 2, 0x7BDD74, false, true);

		addSprite(33416, 0, "Interfaces/FireOfExchange/SPRITE");
		addSprite(33417, 0, "Interfaces/FireOfExchange/SPRITE");
		addSprite(33418, 0, "Interfaces/FireOfExchange/SPRITE");
		addSprite(33419, 0, "Interfaces/FireOfExchange/SPRITE");
		addSprite(33420, 0, "Interfaces/FireOfExchange/SPRITE");
		addSprite(33421, 0, "Interfaces/FireOfExchange/SPRITE");

		addText(33428, "", tda, 1, 0x7BDD74, true, true);
		addText(33429, "", tda, 1, 0x7BDD74, true, true);
		addText(33430, "", tda, 1, 0x7BDD74, true, true);
		addText(33431, "", tda, 1, 0x7BDD74, true, true);
		addText(33432, "", tda, 1, 0x7BDD74, true, true);
		addText(33433, "", tda, 1, 0x7BDD74, true, true);


		inter.totalChildren(32);
		int frame = 0;

		setBounds(33401, 88, 77, frame++, inter);
		setBounds(33402, 260, 88, frame++, inter);
		setBounds(33932, 402, 88, frame++, inter);
		setBounds(33406, 245, 138, frame++, inter);
		setBounds(33407, 250, 115, frame++, inter);
		setBounds(33409, 295, 116, frame++, inter);

		setBounds(33411, 220, 185, frame++, inter);
		setBounds(33412, 263, 190, frame++, inter);

		setBounds(33413, 262, 212, frame++, inter);

		setBounds(33414, 245, 285, frame++, inter);
		setBounds(33415, 300, 286, frame++, inter);

		setBounds(33416, 104, 230, frame++, inter);
		setBounds(33417, 158, 230, frame++, inter);
		setBounds(33418, 212, 230, frame++, inter);
		setBounds(33419, 266, 230, frame++, inter);
		setBounds(33420, 320, 230, frame++, inter);
		setBounds(33421, 374, 230, frame++, inter);

		setBounds(33428, 110 + 15, 266, frame++, inter);
		setBounds(33429, 164 + 15, 266, frame++, inter);
		setBounds(33430, 218 + 15, 266, frame++, inter);
		setBounds(33431, 272 + 15, 266, frame++, inter);
		setBounds(33432, 326 + 15, 266, frame++, inter);
		setBounds(33433, 380 + 15, 266, frame++, inter);

		addItemContainer(33422, 1, 1, 0, 0, false);
		setBounds(33422, 110, 235, frame++, inter);
		addItemContainer(33423, 1, 1, 0, 0, false);
		setBounds(33423, 164, 235, frame++, inter);
		addItemContainer(33424, 1, 1, 0, 0, false);
		setBounds(33424, 218, 235, frame++, inter);

		addItemContainer(33425, 1, 1, 0, 0, false);
		setBounds(33425, 272, 235, frame++, inter);
		addItemContainer(33426, 1, 1, 0, 0, false);
		setBounds(33426, 326, 235, frame++, inter);
		addItemContainer(33427, 1, 1, 0, 0, false);
		setBounds(33427, 380, 235, frame++, inter);


		addItemContainer(33403, 1, 1, 0, 0, false);
		setBounds(33403, 250, 138, frame++, inter);


		inter = addInterface(33404);
		inter.totalChildren(1);
		addItemContainer(33405, 4, 7, 10, 4, true,
				"Offer");
		setBounds(33405, 16, 8, 0, inter);


	}

	public static void pollInterface(TextDrawingArea tda[]) {
		RSInterface rsi = addInterface(21406);
		RSInterface scroll = addInterface(21407);

		addText(21408, "Poll:\\nPoll Name Here", tda, 2, 0xFF9300, true, true);

		int childId = 21409;
		scroll.width = 165;
		scroll.height = 258;

		addText(childId++, "Time Left to Vote: 13 Hours", tda, 0, 0xFF9300, true, true);

		for (int i = 0; i < 5; i++) {
			addText(childId++, "" + (i + 1) + ")", tda, 0, 0xFF9300, true, true);
			addConfigButton(childId++, 618, 2, 4, "Interfaces/Ironman/IMAGE", 17, 17, "Select", 1, 1, 30 + i);
			addText(childId++, "Give bronze dagger\\na spec", tda, 0, 0xFF9300, false, true);
		}

		addHoverButton(childId++, "Interfaces/PollTab/BUTTON", 0, 150, 35, "Vote", -1, childId, 1);
		addHoveredButton(childId++, "Interfaces/PollTab/BUTTON", 1, 150, 35, childId++);
		addText(childId++, "Vote", tda, 2, 0xFF9300, true, true);

		scroll.totalChildren(childId - 21409 - 1);
		childId = 21409;
		int frame = 0;

		scroll.child(frame++, childId++, 80, 25);

		int startX = 15;
		int startY = 50;
		for (int i = 0; i < 5; i++) {
			scroll.child(frame++, childId++, startX, startY);
			scroll.child(frame++, childId++, startX + 10, startY - 2);
			scroll.child(frame++, childId++, startX + 35, startY);
			startY += 22;
		}

		scroll.child(frame++, childId++, 5, 165);
		scroll.child(frame++, childId++, 5, 165);
		childId++;
		scroll.child(frame++, childId++, 80, 174);

		rsi.totalChildren(2);
		rsi.child(0, 21407, 16, 0);
		rsi.child(1, 21408, 96, 10);
	}

	public static void pollResults(TextDrawingArea tda[]) {
		RSInterface rsi = addInterface(21429);

		int childId = 21430;
		addText(childId++, "Current Votes:", tda, 2, 0xFF9300, true, true);

		for (int i = 0; i < 5; i++) {
			addText(childId++, (i + 1) + ": 10 Votes", tda, 0, 0xFF9300, true, true);
		}

		for (int i = 0; i < 5; i++) {
			addText(childId++, "Answers", tda, 0, 0xFF9300, true, true);
		}

		rsi.totalChildren(childId - 21430);
		childId = 21430;
		int frame = 0;

		rsi.child(frame++, childId++, 96, 10);

		int startY = 35;
		for (int i = 0; i < 5; i++) {
			rsi.child(frame++, childId++, 96, startY);
			startY += 20;
		}

		startY = 150;
		for (int i = 0; i < 5; i++) {
			rsi.child(frame++, childId++, 96, startY);
			startY += 20;
		}
	}

	public static void fixDefensiveAutocast() {
		RSInterface rsi = interfaceCache[24111];
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray212[0] = 1;
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.scripts = new int[1][3];
		rsi.scripts[0][0] = 5;
		rsi.scripts[0][1] = Configs.AUTOCAST_DEFENCE_CONFIG;
		rsi.scripts[0][2] = 0;
		rsi.ignoreConfigClicking = true;
	}

	public static void mysteryBox(TextDrawingArea[] tda) {
		RSInterface iface = addInterface(47000);
		/* Base interface */
		//addSpriteLoader(47001, 1073);
		addSprite(47001, 1073, "Interfaces/MysteryBox/SPRITE");
		//addSprite(65001, 0, "Interfaces/Teleporting/Background");
		addText(47002, "Mystery Box", tda, 2, 0xFFA500, true, true);
		addButton(47003, 527, "Interfaces/MysteryBox/SPRITE", "Close");
		addButton(47004, 810, "Interfaces/MysteryBox/SPRITE", "Spin!");
		addText(47005, "@gre@Spin!", tda, 2, 0xFFA500, true, true);
		addSprite(47006, 530, "Interfaces/MysteryBox/SPRITE");
		addSprite(47007, 531, "Interfaces/MysteryBox/SPRITE");
		addText(47008, "Feeling lucky?", tda, 2, 0xFFA500, true, true);
		addText(47009, "Sacrifice your box for a chance at something rare!", tda, 1, 0xFFA500, true, true);
		addSprite(47010, 528, "Interfaces/MysteryBox/SPRITE");
		addSprite(47011, 533, "Interfaces/MysteryBox/SPRITE");


		setChildren(13, iface);
		setBounds(47001, 10, 10, 0, iface);
		setBounds(47002, 253, 13, 1, iface);
		setBounds(47003, 473, 14, 2, iface);
		setBounds(47004, 218, 256, 3, iface);
		setBounds(47005, 253, 263, 4, iface);
		setBounds(47006, 17, 185, 5, iface);
		setBounds(47007, 33, 65, 6, iface);
		setBounds(47008, 253, 78, 7, iface);
		setBounds(47009, 253, 108, 8, iface);
		// Boxes
		setBounds(47200, 10, 187, 9, iface);
		// Items
		setBounds(47100, 17, 192, 10, iface);
		// Item selector
		setBounds(47010, 252, 187, 11, iface);
		setBounds(47011, 10, 185, 12, iface);

		/* Boxes */
		RSInterface box = addInterface(47200);
		box.width = 480;
		setChildren(Client.BOXES64, box);
		// 64 boxes in each sprite
		int x = 0;
		for (int i = 0; i < Client.BOXES64; i++) {
			//addSpriteLoader(47201, 1076);
			addSprite(47201, 532, "Interfaces/MysteryBox/SPRITE");
			//addSprite(47201, 0, "");
			setBounds(47201, 0 + x, 0, i, box);
			x += 2880;
		}

		/* Items */
		RSInterface scroll = addInterface(47100);
		scroll.width = 474;
		addToItemGroup(47101, 1750, 1, 13, 10, false, null, null, null);
		setChildren(1, scroll);
		setBounds(47101, 0, 0, 0, scroll);
	}

	public static void wrathRune() {
		RSInterface rune = addTabInterface(28226);
		rune.totalChildren(1);
		addSprite(28228, 0, "Magic/wrath");
		setBounds(28228, 0, 0, 0, rune);
	}

	public static void normals(TextDrawingArea[] tda) {
		RSInterface p = addTabInterface(938);
		RSInterface rsinterface = interfaceCache[1151];
		RSInterface rsinterface2 = interfaceCache[12424];
		rsinterface2.height = 250;

		// earth wave
		rsinterface2.childX[36] = 96;
		rsinterface2.childY[36] = 168;

		// enfeeble
		rsinterface2.childX[46] = 120;
		rsinterface2.childY[46] = 168;

		// teleother lumbridge
		rsinterface2.childX[53] = 144;
		rsinterface2.childY[53] = 168;

		// fire wave
		rsinterface2.childX[37] = 1;
		rsinterface2.childY[37] = 192;

		// entangle
		rsinterface2.childX[50] = 23;
		rsinterface2.childY[50] = 192;

		// stun
		rsinterface2.childX[47] = 47;
		rsinterface2.childY[47] = 193;

		// charge
		rsinterface2.childX[41] = 71;
		rsinterface2.childY[41] = 192;

		// teleother falador
		rsinterface2.childX[54] = 120;
		rsinterface2.childY[54] = 192;

		// teleblock
		rsinterface2.childX[55] = 0;
		rsinterface2.childY[55] = 218;

		// lvl-6 enchant
		rsinterface2.childX[57] = 47;
		rsinterface2.childY[57] = 218;

		// teleother camelot
		rsinterface2.childX[56] = 71;
		rsinterface2.childY[56] = 218;

		rsinterface.childY[1] = 12;
		rsinterface.childX[1] = 14;
		addSpellSmall2_3(31674 + 975, 563, 566, 555, 554, 2, 2, 4, 5, 30012, 30015, 30004, 30003, 68, "Teleport to Kourend",
				"Teleports you to Kourend", tda, 10, 7, 5);
		addSpellLarge2(13674 + 975, 563, 560, 562, 1, 1, 1, 30012, 30009, 30011, 84, "Teleport to Bounty\\nTarget",
				"Teleports you near your Bounty\\nHunter Target", tda, 8, 7, 5);

		addSpellSmall2(22674 + 975, 565, 566, 564, 20, 20, 1, 30014, 30015, 30013, 92, "Lvl-7 Enchant",
				"For use on zenyte jewellery", tda, 12, 16, 2); // 16 spell useable on is for magic on inventory item

		addSpellSmaller(22644 + 975, 556, 21880, 7, 1, 30005, 28226, 80, "Wind Surge",
				"A very high level Air missile", tda, 0, 10, 2);

		addSpellSmall(22658 + 975, 555, 556, 21880, 10, 7, 1, 30004, 30005, 28226, 84, "Water Surge",
				"A very high level Water missile", tda, 2, 10, 2);

		addSpellSmall(22628 + 975, 557, 556, 21880, 10, 7, 1, 30006, 30005, 28226, 89, "Earth Surge",
				"A very high level Earth missile", tda, 4, 10, 2);

		addSpellSmall(22608 + 975, 554, 556, 21880, 10, 7, 1, 30003, 30005, 28226, 94, "Fire Surge",
				"A very high level Fire missile", tda, 6, 10, 2);

		setChildren(15, p);
		setBounds(31674 + 975, 84, 178, 0, p);
		setBounds(13674 + 975, 35, 228, 1, p);
		setBounds(22674 + 975, 132, 227, 2, p);
		setBounds(22644 + 975, 108, 202, 3, p);
		setBounds(22658 + 975, 156, 202, 4, p);
		setBounds(22628 + 975, 108, 227, 5, p);
		setBounds(22608 + 975, 156, 227, 6, p);
		setBounds(1151, 0, 0, 7, p);
		setBounds(22609 + 975, 5, 5, 8, p);
		setBounds(22629 + 975, 5, 5, 9, p);
		setBounds(22659 + 975, 5, 5, 10, p);
		setBounds(22645 + 975, 5, 5, 11, p);
		setBounds(31675 + 975, 5, 5, 12, p);
		setBounds(13675 + 975, 5, 5, 13, p);
		setBounds(22675 + 975, 5, 5, 14, p);
	}

	public static void closeButton(int id, int sprite2, int sprite1) {
		RSInterface tab = addInterface(id);
		tab.atActionType = 0;
		tab.type = 0;
		tab.sprite2 = Client.cacheSprite3[sprite2];
		tab.sprite1 = Client.cacheSprite3[sprite1];
		tab.width = tab.sprite1.myWidth;
		tab.height = tab.sprite2.myHeight;
		// tab.toggled = false;
		// tab.spriteOpacity = 255;d
	}

	public static void createSkillHover(int id, int x) {
		RSInterface hover = addInterface(id);
		hover.inventoryhover = true;
		hover.type = 8;
		hover.message = "TESTING!" + x;
		hover.contentType = x;
		hover.width = 60;
		hover.height = 32;
	}

	private static void skillTabWithHovers(TextDrawingArea[] tda) {
		int[] firstRow = {-1,

				/** First row (enx. index 9) **/
				14918, 14919, 14920, 14921, 14922, 14923, 14924, 14933

		};
		int[] secondRow = {

				/** Second row (enx. index 8) **/
				14926, 14927, 14928, 14929, 14930, 14931, 14932, 14925

		};
		int[] thirdRow = {

				/** Third row (enx. index 8) **/
				14934, 14935, 14936, 14937, 14938, 14939, 14940, 14941

		};

		RSInterface tab = addInterface(13917);
		tab.totalChildren(firstRow.length + secondRow.length + thirdRow.length);
		setBounds(3917, 0, 0, 0, tab);
		for (int i = 1; i < firstRow.length; i++) {
			createSkillHover(firstRow[i], 205 + i);
			setBounds(firstRow[i], 0, 2 + (31 * i) - 32, i, tab);
		}
		for (int i = 0; i < secondRow.length; i++) {
			createSkillHover(secondRow[i], 214 + i);
			setBounds(secondRow[i], 60, 2 + (31 * i), 9 + i, tab);
		}
		for (int i = 0; i < thirdRow.length; i++) {
			createSkillHover(thirdRow[i], 223 + i);
			setBounds(thirdRow[i], 120, 2 + (31 * i), 17 + i, tab);
		}
	}

	/**
	 * @author Grant_ | www.rune-server.ee/members/grant | 3/22/2020
	 */
	public static void votePanel(TextDrawingArea[] tda) {
		RSInterface widget = addInterface(24127);
		int childId = 24128;

		addSprite(childId++, 0, "Interfaces/VotePanel/BACKGROUND");
		addHoverButton(childId++, "Interfaces/CollectionLog/CLOSE", 0, 16, 16, "Close", -1, childId, 1);
		addHoveredButton(childId++, "Interfaces/CollectionLog/CLOSE", 1, 16, 16, childId++);

		addText(childId++, "Day Streak Points:", tda, 0, 0xFF9300, false, true);
		addText(childId++, "6", tda, 0, 0xFFFFFF, true, true);
		addText(childId++, "1", tda, 0, 0xFFFFFF, true, true);

		addText(childId++, "Vote Panel", tda, 2, 0xFF9300, true, true);

		addText(childId++, "", tda, 0, 0xFF9300, true, true);

		addText(childId++, "Current Day Streak: @whi@4", tda, 2, 0xFF9300, true, true);

		addSprites(childId++, "Interfaces/VotePanel/PIPE_UP", 0, 1, 2);
		addSprites(childId++, "Interfaces/VotePanel/PIPE_DOWN", 0, 1, 2);
		addSprites(childId++, "Interfaces/VotePanel/PIPE_UP", 0, 1, 2);
		addSprites(childId++, "Interfaces/VotePanel/PIPE_DOWN", 0, 1, 2);

		addSprites(childId++, "Interfaces/VotePanel/PENTA", 0, 1);
		addSprites(childId++, "Interfaces/VotePanel/PENTA", 0, 1);
		addSprites(childId++, "Interfaces/VotePanel/PENTA", 0, 1);
		addSprites(childId++, "Interfaces/VotePanel/PENTA", 0, 1);
		addSprites(childId++, "Interfaces/VotePanel/PENTA", 0, 1);

		//Top pentagon labels
		int[] days = {4, 8};
		String[] circleTexts = {"+3", "+5"};
		for (int i = 0; i < 2; i++) {
			addSprite(childId++, 0, "Interfaces/VotePanel/CIRCLE");
			addText(childId++, "@gre@Day " + days[i], tda, 0, 0xFF9300, true, true);
			addText(childId++, circleTexts[i], tda, 0, 0xFFFFFF, true, true);
		}

		//Bottom pentagon labels
		days = new int[]{2, 6};
		circleTexts = new String[]{"+2", "+4"};
		for (int i = 0; i < 2; i++) {
			addSprite(childId++, 0, "Interfaces/VotePanel/CIRCLE");
			addText(childId++, "@gre@Day " + days[i], tda, 0, 0xFF9300, true, true);
			addText(childId++, circleTexts[i], tda, 0, 0xFFFFFF, true, true);
		}

		//Custom last pentagon
		addText(childId++, "@gre@Day 10", tda, 0, 0xFFFFFF, true, true);
		addSprite(childId++, 1, "Interfaces/VotePanel/CIRCLE");
		addText(childId++, "+1", tda, 0, 0xFFFFFF, true, true);
		addSprite(childId++, 0, "Interfaces/VotePanel/BOX");
		addSprite(childId++, 1, "Interfaces/VotePanel/BOX");

		String[] buttonText = {"30 Min. Bonus XP", "10% DR Boost 1HR", "Vote Crystal", "Ultra M. Box"};
		int[] amounts = {2, 2, 1, 5};
		for (int i = 0; i < 4; i++) {
			addHoverButton(childId++, "Interfaces/VotePanel/BUTTON", 0, 139, 28, buttonText[i], -1, childId, 1);
			addHoveredButton(childId++, "Interfaces/VotePanel/BUTTON", 1, 139, 28, childId++);
			//System.out.println("ID: " + childId);
			addText(childId++, buttonText[i], tda, 0, 0xFF9933, true, true);
			addSprite(childId++, i == 3 ? 1 : 0, "Interfaces/VotePanel/CIRCLE");
			addText(childId++, amounts[i] + "", tda, 0, 0xFFFFFF, true, true);
		}

		addSprite(childId++, 2, "Interfaces/VotePanel/BOX");
		addText(childId++, "Point Store", tda, 0, 0xFF9933, true, true);

		addText(childId++, "Top 3 Weekly Voters:", tda, 2, 0xFF9300, true, true);

		String[] names = {"1. Billy [12]", "2. Grant [7]", "3. Noah [3]"};
		for (int i = 0; i < 3; i++) {
			addText(childId++, names[i], tda, 0, 0xFF9300, false, true);
			addText(childId++, "Prize:", tda, 0, 0xFF9300, false, true);
		}

		addHoverButton(childId++, "Interfaces/VotePanel/BUTTON", 2, 103, 26, "Claim Prize", -1, childId, 1);
		addHoveredButton(childId++, "Interfaces/VotePanel/BUTTON", 3, 103, 26, childId++);

		addText(childId++, "Claim Prize", tda, 2, 0xFF9300, true, true);
		addText(childId++, "Top 3 Voters reset in:\\n4 days, 3 hrs, 2 min", tda, 0, 0xFFFFFF, true, true);

		addToItemGroup(childId++, 1, 3, 0, 0, false);

		widget.totalChildren(childId - 24128 - 6);
		childId = 24128;
		int frame = 0;

		widget.child(frame++, childId++, 15, 30); //Background

		widget.child(frame++, childId++, 476, 39); //Close button
		widget.child(frame++, childId++, 476, 39);
		childId++;

		widget.child(frame++, childId++, 30, 42); //Day streak title
		widget.child(frame++, childId++, 139, 42); //Blue count
		widget.child(frame++, childId++, 160, 42); //Red count

		widget.child(frame++, childId++, 267, 40); //Title

		widget.child(frame++, childId++, 410, 42); //Vote Key

		widget.child(frame++, childId++, 163, 70); //Current Day streak

		int startX = 55;
		int startY = 125;
		for (int i = 0; i < 4; i++) {
			widget.child(frame++, childId++, startX, startY); //Pipe
			startX += 60;
			if (i == 1) {
				startX = 148;
			}
		}

		widget.child(frame++, childId++, 47, 164); //Pentagon
		widget.child(frame++, childId++, 95, 102); //Pentagon
		widget.child(frame++, childId++, 140, 164); //Pentagon
		widget.child(frame++, childId++, 185, 102); //Pentagon
		widget.child(frame++, childId++, 234, 164); //Pentagon

		startX = 115;
		startY = 89;
		//Top labels
		for (int i = 0; i < 2; i++) {
			widget.child(frame++, childId++, startX - 7, startY + 25);
			widget.child(frame++, childId++, startX, startY);
			widget.child(frame++, childId++, startX, startY + 28);

			startX += 90;
		}

		startX = 68;
		startY = 209;
		//Bottom labels
		for (int i = 0; i < 2; i++) {
			widget.child(frame++, childId++, startX - 8, startY - 32);
			widget.child(frame++, childId++, startX, startY);
			widget.child(frame++, childId++, startX, startY - 28);

			startX += 92;
		}

		//Custom last label
		widget.child(frame++, childId++, 254, 209);
		widget.child(frame++, childId++, 240, 173);
		widget.child(frame++, childId++, 247, 176);
		widget.child(frame++, childId++, 249, 184);
		widget.child(frame++, childId++, 255, 168);

		startX = 22;
		startY = 240;
		for (int i = 0; i < 4; i++) {
			widget.child(frame++, childId++, startX, startY);
			widget.child(frame++, childId++, startX, startY);
			childId++;

			widget.child(frame++, childId++, startX + 68, startY + 8);
			widget.child(frame++, childId++, startX + 117, startY + 4);
			widget.child(frame++, childId++, startX + 126, startY + 8);

			startX += 139;
			if (i == 1) {
				startX = 22;
				startY += 28;
			}
		}

		widget.child(frame++, childId++, 169, 272); //M box icon
		widget.child(frame++, childId++, 163, 227); //Point store text

		widget.child(frame++, childId++, 400, 70);
		startX = 314;
		startY = 103;
		for (int i = 0; i < 3; i++) {
			widget.child(frame++, childId++, startX, startY);
			widget.child(frame++, childId++, startX + 100, startY);
			startY += 30;
		}

		widget.child(frame++, childId++, 347, 209);
		widget.child(frame++, childId++, 347, 209);
		childId++;

		widget.child(frame++, childId++, 400, 215);
		widget.child(frame++, childId++, 400, 252);

		widget.child(frame++, childId++, 449, 93); //Items
	}

	/**
	 * @param tda
	 * @author Grant_ | www.rune-server.ee/members/grant_ | 10/7/19
	 */
	public static void collectionLog(TextDrawingArea[] tda) {
		//Config = 519
		RSInterface widget = addInterface(23110);
		int childId = 23111;

		addSprite(childId++, 0, "Interfaces/CollectionLog/BACKGROUND");

		addText(childId++, "Collection Log", tda, 2, 0xFF9300, true, true);

		addHoverButton(childId++, "Interfaces/CollectionLog/CLOSE", 0, 16, 16, "Close", -1, childId, 1);
		addHoveredButton(childId++, "Interfaces/CollectionLog/CLOSE", 1, 16, 16, childId++);

		addConfigButton(childId++, 618, 0, 1, "Interfaces/CollectionLog/TAB", 96, 20, "Select Bosses", 1, 1, 519);
		addText(childId++, "Bosses", tda, 1, 0xFF9300, true, true);

		addText(childId++, "Boss Name Here", tda, 2, 0xFF9300, false, true);
		addText(childId++, "Obtained: @red@10/11", tda, 0, 0xFF9300, false, true);
		addText(childId++, "Boss Name count: @whi@125", tda, 0, 0xFF9300, true, true);

		RSInterface tableView = addInterface(childId++);
		int scrollChildId = childId;
		int scrollFrame = 0;
		tableView.width = 187;
		tableView.height = 246;
		tableView.scrollMax = 750;
		tableView.totalChildren(50 * 2);

		int scrollX = 0;
		int scrollY = 0;
		for (int i = 0; i < 50; i++) {
			if (i % 2 == 0) {
				addConfigButton(scrollChildId, 23122, 0, 1, "Interfaces/CollectionLog/CELL", 187, 15, "Select Boss", 1, 1, 520 + i);
			} else {
				addConfigButton(scrollChildId, 23122, 2, 1, "Interfaces/CollectionLog/CELL", 187, 15, "Select Boss", 1, 1, 520 + i);
			}
			tableView.child(scrollFrame++, scrollChildId++, scrollX, scrollY);

			addText(scrollChildId, "Boss Name Here", tda, 1, 0xFF9300, false, true);
			tableView.child(scrollFrame++, scrollChildId++, scrollX + 4, scrollY);

			scrollY += 15;
		}

		widget.totalChildren(childId - 23111 - 1 + (8) + 1 + 1);
		childId = 23111;
		int frame = 0;

		widget.child(frame++, childId++, 9, 11);//Background
		widget.child(frame++, childId++, 257, 20);//Title

		widget.child(frame++, childId++, 482, 20);
		widget.child(frame++, childId++, 482, 20);
		childId++;

		widget.child(frame++, childId++, 19, 47);
		widget.child(frame++, childId++, 65, 50);

		widget.child(frame++, childId++, 230, 70);
		widget.child(frame++, childId++, 230, 94);
		widget.child(frame++, childId++, 433, 94);

		widget.child(frame++, childId++, 20, 68);

		//Had to add these on
		int nextChildId = scrollChildId;
		addConfigButton(nextChildId, 23111, 0, 1, "Interfaces/CollectionLog/TAB", 96, 20, "Select Wilderness", 1, 1, 571);
		widget.child(frame++, nextChildId++, 19 + 96, 47);
		addText(nextChildId, "Wilderness", tda, 1, 0xFF9300, true, true);
		widget.child(frame++, nextChildId++, 65 + 96, 50);

		addConfigButton(nextChildId, 23111, 0, 1, "Interfaces/CollectionLog/TAB", 96, 20, "Select Raids", 1, 1, 572);
		widget.child(frame++, nextChildId++, 19 + 96 + 96, 47);
		addText(nextChildId, "Raids", tda, 1, 0xFF9300, true, true);
		widget.child(frame++, nextChildId++, 65 + 96 + 96, 50);

		addConfigButton(nextChildId, 23111, 0, 1, "Interfaces/CollectionLog/TAB", 96, 20, "Select Minigames", 1, 1, 573);
		widget.child(frame++, nextChildId++, 19 + 96 + 96 + 96, 47);
		addText(nextChildId, "Minigames", tda, 1, 0xFF9300, true, true);
		widget.child(frame++, nextChildId++, 65 + 96 + 96 + 96, 50);

		addConfigButton(nextChildId, 23111, 0, 1, "Interfaces/CollectionLog/TAB", 96, 20, "Select Clue Scroll", 1, 1, 574);
		widget.child(frame++, nextChildId++, 19 + 96 + 96 + 96 + 96, 47);
		addText(nextChildId, "Other", tda, 1, 0xFF9300, true, true);
		widget.child(frame++, nextChildId++, 65 + 96 + 96 + 96 + 96, 50);

		int itemScrollId = nextChildId;
		RSInterface scroll = addInterface(itemScrollId);
		widget.child(frame++, itemScrollId++, 232, 114);
		int itemScrollFrame = 0;
		scroll.width = 250;
		scroll.height = 150;
		scroll.scrollMax = 750;
		scroll.totalChildren(1);

		addToItemGroup(itemScrollId, 6, 33, 9, 6, false, null, null, null);
		scroll.child(itemScrollFrame++, itemScrollId++, 0, 0);

		int claimID = itemScrollId;
		RSInterface claim = addInterface(claimID);
//		System.out.println(claimID);
		widget.child(frame++, claimID++, 223, 255);
		int itemClaimFrame = 0;
		claim.totalChildren(5);
		addSprite(claimID + 1, 0, "Interfaces/CollectionLog/box");
		addToItemGroup(claimID + 2, 6, 20, 5, 6, false, "", "", "");
		configHoverButton(claimID + 3, "Claim", "Interfaces/CollectionLog/claim", 0, 1, 0, 1, false);
		interfaceCache[claimID + 3].onClick = new Object[] {68, claimID + 3, interfaceCache[claimID + 3] + "1"};
		addText(claimID + 4, "@cr49@ Rewards for completing log:", tda, 0, 16750623, false, true);
		addText(claimID + 5, "Claim", tda, 2, 16750623, true, true);

		claim.child(itemClaimFrame++, claimID + 1, 2, 12);
		claim.child(itemClaimFrame++, claimID + 2, 7, 21);
		claim.child(itemClaimFrame++, claimID + 3, 200, 16);
		claim.child(itemClaimFrame++, claimID + 4, 6, 1);
		claim.child(itemClaimFrame++, claimID + 5, 236, 36);
	}

	public static void teleportInterface1(TextDrawingArea[] tda) {
		final int STARTING_POINT = 60000;

		RSInterface main = addInterface(STARTING_POINT);

		String dir = "Interfaces/telev2/SPRITE";

		addSprite(STARTING_POINT + 1, 0, dir); // Background

		String[] teleports = {"Monsters", "Bosses", "Minigames", "Dungeons", "Wildy", "Cities"};
		main.totalChildren(36);
		main.child(0, STARTING_POINT + 1, 10, 10);
		int childStart = 1;
		int xPos = 18;
		int spot = 0;
		for (int i = 0; i < teleports.length * 5; i += 5) {
			addHoverButton(STARTING_POINT + 2 + i, dir, 3, 105, 21, teleports[spot], -1, STARTING_POINT + 3 + i, 1);//Tabs
			addHoveredButton(STARTING_POINT + 3 + i, dir, 4, 105, 21, STARTING_POINT + 4 + i); //Tabs
			main.child(childStart++, STARTING_POINT + 2 + i, xPos, 44);
			main.child(childStart++, STARTING_POINT + 3 + i, xPos, 44);

			xPos += 93;
			spot++;
		}

		int textStart = 11;
		int textX = 55;
		for (int i = 0; i < teleports.length; i++) {
			addText(STARTING_POINT + 40 + i, teleports[i], tda, 1, 0xFF9300, true, true);
			main.child(textStart, STARTING_POINT + 40 + i, textX, 48);
			textStart++;
			textX += 92;
		}

		addText(STARTING_POINT + 46, "Bosses", tda, 2, 0xb9a888, false, true);
		addText(STARTING_POINT + 47, "Npc Name", tda, 2, 0xb9a888, false, true);
		addText(STARTING_POINT + 48, "Drops", tda, 2, 0xb9a888, false, true);
		addText(STARTING_POINT + 49, "Description", tda, 2, 0xb9a888, false, true);
		addText(STARTING_POINT + 50, "Tier: ", tda, 3, 0xb9a888, false, true);
		main.child(16, STARTING_POINT + 100, 0, 93);

		main.child(17, STARTING_POINT + 46, 56, 73);
		main.child(18, STARTING_POINT + 47, 214, 73);
		main.child(19, STARTING_POINT + 48, 395, 73);
		main.child(20, STARTING_POINT + 49, 221, 246);
		main.child(21, STARTING_POINT + 50, 386, 247);

		main.child(22, STARTING_POINT + 51, 180, 267);
		main.child(23, STARTING_POINT + 52, 180, 282);
		main.child(24, STARTING_POINT + 53, 180, 297);
		main.child(25, STARTING_POINT + 54, 415, 267);
		main.child(26, STARTING_POINT + 55, 415, 282);
		main.child(27, STARTING_POINT + 56, 415, 297);

		main.child(28, STARTING_POINT + 57, 268, 20);

		main.child(29, STARTING_POINT + 58, 360, 210);
		main.child(30, STARTING_POINT + 59, 360, 210);
		main.child(31, STARTING_POINT + 61, 425, 217);
		main.child(32, STARTING_POINT + 62, 197, 122);
		main.child(33, STARTING_POINT + 200, 361, 95);

		main.child(34, STARTING_POINT + 64, 480, 19);
		main.child(35, STARTING_POINT + 65, 480, 19);

		addHoverButton(STARTING_POINT + 64, "Interfaces/CollectionLog/CLOSE", 0, 16, 16, "Close", -1, STARTING_POINT + 64, 1);
		addHoveredButton(STARTING_POINT + 65, "Interfaces/CollectionLog/CLOSE", 1, 16, 16, STARTING_POINT + 66);

		addText(STARTING_POINT + 51, "Attacks with: Melee", tda, 1, 0xFF9300, false, true);
		addText(STARTING_POINT + 52, "Weakness: Range", tda, 1, 0xFF9300, false, true);
		addText(STARTING_POINT + 53, "Health: 50000", tda, 1, 0xFF9300, false, true);
		addText(STARTING_POINT + 54, "Max hit: 450", tda, 1, 0xFF9300, true, true);
		addText(STARTING_POINT + 55, "Players here: 0", tda, 1, 0xFF9300, true, true);
		addText(STARTING_POINT + 56, "", tda, 1, 0xFF9300, true, true);

		addText(STARTING_POINT + 57, "Teleports", tda, 2, 0xFFDB58, true, true);

		addHoverButton(STARTING_POINT + 58, dir, 1, 132, 29, "Teleport", -1, STARTING_POINT + 59, 1);//Tabs
		addHoveredButton(STARTING_POINT + 59, dir, 2, 132, 29, STARTING_POINT + 60); //Tabs

		addText(STARTING_POINT + 61, "Teleport", tda, 2, 0xFF9300, true, true);

		addPet(STARTING_POINT + 62, 7025);

		RSInterface scroll = addInterface(STARTING_POINT + 100);
		scroll.totalChildren(81);
		int yPos = 1;
		for (int i = 0; i < 60; i++) {
			addSprite(STARTING_POINT + 101 + i, i % 2 == 0 ? 6 : 7, dir); // Background
			scroll.child(i, STARTING_POINT + 101 + i, 21, yPos);
			yPos += 21;
		}
		int textY = 7;
		for (int i = 0; i < 21; i++) {
			addHoverText(STARTING_POINT + 162 + i, "Name : " + i, "Select", tda, 0, 0xFF9300, false, false, 100);
			scroll.child(50 + i, STARTING_POINT + 162 + i, 25, textY);
			textY += 21;
		}

		scroll.width = 155;
		scroll.height = 221;
		scroll.scrollMax = 420;

		RSInterface scroll1 = addInterface(STARTING_POINT + 200);
		scroll1.totalChildren(1);
		addToItemGroup(STARTING_POINT + 63, 3, 50, 8, 5, true, new String[]{null, null, null, null, null});
		scroll1.child(0, STARTING_POINT + 63, 1, 5);

		scroll1.width = 130 - 16;
		scroll1.height = 113;
		scroll1.scrollMax = 1000;
	}

	public static void tourneyJoinInterface(TextDrawingArea[] tda) {
		RSInterface widget = addInterface(270);
		int childId = 271;

		addSprite(childId++, 0, "Interfaces/Tourney/BACKGROUND");
		addText(childId++,
				"\\n" +
						"\\n" +
						"\\n" +
						"safe PvP tournament.\\n" +
						"\\n" +
						"Earn weapon and armour upgrades by killing targets. \\n" +
						"Once the 3 minute timer runs out, the fog damage will begin.\\n" +
						"Good luck!", tda, 2, 0xFF9300, true, true);

		addHoverButton(childId++, "Interfaces/Tourney/BUTTON", 0, 136, 32, "Fight", -1, childId, 1);
		addHoveredButton(childId++, "Interfaces/Tourney/BUTTON", 0, 135, 32, childId++);

		addHoverButton(childId++, "Interfaces/Tourney/BUTTON", 0, 136, 32, "Exit", -1, childId, 1);
		addHoveredButton(childId++, "Interfaces/Tourney/BUTTON", 0, 135, 32, childId++);

		addText(childId++, "Fight", tda, 2, 0x00FF00, true, true);
		addText(childId++, "Exit", tda, 2, 0xFF0000, true, true);

		widget.totalChildren(childId - 271 - 2);
		childId = 271;
		int frame = 0;

		widget.child(frame++, childId++, 11, 11);
		widget.child(frame++, childId++, 257, 100);

		widget.child(frame++, childId++, 100, 255);
		widget.child(frame++, childId++, 100, 255);
		childId++;

		widget.child(frame++, childId++, 285, 255);
		widget.child(frame++, childId++, 285, 255);
		childId++;

		widget.child(frame++, childId++, 167, 263);
		widget.child(frame++, childId++, 352, 263);
	}

	/**
	 * @param tda
	 * @author Grant_ | www.rune-server.ee/members/grant_ | 10/4/19
	 */
	public static void tournamentInterface(TextDrawingArea[] tda) {
		RSInterface widget = addInterface(264);
		int childId = 265;

		addText(childId++, "Tournament", tda, 2, 0xFFFFFF, true, true);
		addText(childId++, "Time Left: 5 min", tda, 1, 0xFFFFFF, true, true);
		addText(childId++, "Current Players", tda, 1, 0xFFFFFF, true, true);

		widget.totalChildren(childId - 265);
		childId = 265;
		int frame = 0;
		widget.child(frame++, childId++, 251, 5);
		widget.child(frame++, childId++, 251, 17);
		widget.child(frame++, childId++, 251, 29);
	}

	/**
	 * @param tda
	 * @author Grant_ | www.rune-server.ee/members/grant_ | 9/29/19
	 */
	public static void presetInterface(TextDrawingArea[] tda) {
		RSInterface widget = addInterface(21553);
		int childId = 21554;

		addSprite(childId++, 0, "Presets/BACKGROUND");

		for (int i = 0; i < 4; i++) {
			addConfigButton(childId++, 21553, 0, 1, "Presets/BUTTON", 118, 22, "Select Preset", 1, 1, 899 + i);
			addText(childId++, "Default " + i, tda, 1, 0xFF9300, false, true);
		}

		addText(childId++, "Pre-made Presets", tda, 1, 0xFFFFFF, true, true);
		addText(childId++, "Customs", tda, 1, 0xFF9300, true, true);
		addText(childId++, "Current Set", tda, 1, 0xFFFFFF, true, true);

		addHoverButton(childId++, "Presets/BUTTON", 2, 81, 28, "Load", -1, childId, 1);
		addHoveredButton(childId++, "Presets/BUTTON", 2, 81, 28, childId++);

		addHoverButton(childId++, "Presets/BUTTON", 2, 81, 28, "Save", -1, childId, 1);
		addHoveredButton(childId++, "Presets/BUTTON", 2, 81, 28, childId++);

		addText(childId++, "Load", tda, 2, 0xFF9300, true, true);
		addText(childId++, "Save", tda, 2, 0xFF9300, true, true);

		addText(childId++, Configuration.CLIENT_TITLE + " Presets", tda, 2, 0xFF9300, true, true);

		addHoverButton(childId++, "/Interfaces/Exp Lock/SPRITE", 1, 16, 16, "Close", -1, childId, 1);
		addHoveredButton(childId++, "/Interfaces/Exp Lock/SPRITE", 2, 16, 16, childId++);

		addToItemGroup(childId++, 4, 7, 6, 6, true, "Remove", null, null);

		/*
		 * Note: Using a modified childId to avoid overridden child members
		 */
		int overlayModificationChildId = 569;
		int overlayModificationConfig = 61;
		for (int i = 0; i < 11; i++) {
			addConfigButton(overlayModificationChildId++, 21553, 1, 0, "Presets/OVERLAY", 36, 36, new String[]{}, 1, overlayModificationConfig + i);
		}

		for (int i = 0; i < 11; i++) {
			addToItemGroup(childId++, 1, 1, 0, 0, true, "Remove", null, null);
		}

		RSInterface scroll = addInterface(childId++);
		int scrollChildId = childId;
		int scrollFrame = 0;
		scroll.width = 100;
		scroll.height = 140;
		scroll.scrollMax = 220;
		scroll.totalChildren(30);
		int scrollX = 0;
		int scrollY = 0;

		for (int i = 0; i < 10; i++) {
			addConfigButton(scrollChildId, 21553, 0, 1, "Presets/BUTTON", 118, 22, new String[]{"Delete Preset", "Select Preset"}, 1, 903 + i);
			scroll.child(scrollFrame++, scrollChildId++, scrollX, scrollY);

			addText(scrollChildId, "Preset Name " + i, tda, 1, 0xFF9300, false, true);
			scroll.child(scrollFrame++, scrollChildId++, scrollX + 1, scrollY + 3);

			scrollY += 22;
		}

		childId = scrollChildId;
		addHoverButton(childId++, "Presets/EDIT", 0, 14, 12, "Edit Preset Name", -1, childId, 1);
		addHoveredButton(childId++, "Presets/EDIT", 0, 14, 12, childId++);

		int additionChildId = 251;
		addHoverButton(additionChildId++, "Presets/BUTTON", 3, 35, 35, "Switch Spellbook", -1, additionChildId, 1);
		addHoveredButton(additionChildId++, "Presets/BUTTON", 3, 35, 35, additionChildId++);
		addSprites(additionChildId++, "Presets/BOOK", new int[]{0, 1, 2});

		widget.totalChildren(childId - 21554 + 4 + (11) + (3));

		overlayModificationChildId = 569;
		childId = 21554;
		int frame = 0;

		widget.child(frame++, childId++, 11, 11);

		int startX = 19;
		int startY = 61;
		for (int i = 0; i < 4; i++) {
			widget.child(frame++, childId++, startX, startY);

			widget.child(frame++, childId++, startX + 1, startY + 3);

			startY += 22;
		}

		widget.child(frame++, childId++, 75, 45);
		widget.child(frame++, childId++, 75, 151);
		widget.child(frame++, childId++, 228, 45);

		widget.child(frame++, childId++, 143, 278);
		widget.child(frame++, childId++, 143, 278);
		childId++;

		widget.child(frame++, childId++, 230, 278);
		widget.child(frame++, childId++, 230, 278);
		childId++;

		widget.child(frame++, childId++, 182, 284);
		widget.child(frame++, childId++, 270, 284);
		widget.child(frame++, childId++, 247, 21);

		widget.child(frame++, childId++, 474, 20);
		widget.child(frame++, childId++, 474, 20);
		childId++;

		widget.child(frame++, childId++, 333, 48);

		//Equipment Overlays
		widget.child(frame++, overlayModificationChildId++, 211, 68);
		widget.child(frame++, overlayModificationChildId++, 170, 107);
		widget.child(frame++, overlayModificationChildId++, 211, 107);
		widget.child(frame++, overlayModificationChildId++, 252, 107);
		widget.child(frame++, overlayModificationChildId++, 211, 146);
		widget.child(frame++, overlayModificationChildId++, 155, 146);
		widget.child(frame++, overlayModificationChildId++, 267, 146);
		widget.child(frame++, overlayModificationChildId++, 211, 186);
		widget.child(frame++, overlayModificationChildId++, 211, 226);
		widget.child(frame++, overlayModificationChildId++, 155, 226);
		widget.child(frame++, overlayModificationChildId++, 267, 226);

		//Equipment
		widget.child(frame++, childId++, 214, 70);
		widget.child(frame++, childId++, 172, 109);
		widget.child(frame++, childId++, 214, 109);
		widget.child(frame++, childId++, 255, 109);
		widget.child(frame++, childId++, 214, 148);
		widget.child(frame++, childId++, 158, 148);
		widget.child(frame++, childId++, 270, 148);
		widget.child(frame++, childId++, 214, 188);
		widget.child(frame++, childId++, 214, 228);
		widget.child(frame++, childId++, 158, 228);
		widget.child(frame++, childId++, 270, 228);

		widget.child(frame++, childId++, 19, 168);

		childId = scrollChildId;

		widget.child(frame++, childId++, 301, 46);
		widget.child(frame++, childId++, 301, 46);

		additionChildId = 251;
		//button
		widget.child(frame++, additionChildId++, 276, 64);
		widget.child(frame++, additionChildId++, 276, 64);
		additionChildId++;
		//icon
		widget.child(frame++, additionChildId++, 281, 70);
	}

	public static void equipmentScreen(TextDrawingArea[] wid) {
		RSInterface Interface = RSInterface.interfaceCache[1644];
		addButton(19144, 140, "Show Equipment Stats");
		removeSomething(19145);
		removeSomething(19146);
		removeSomething(19147);
		setBounds(19145, 40, 210, 24, Interface);
		setBounds(19146, 40, 210, 25, Interface);
		setBounds(19147, 40, 210, 26, Interface);
		RSInterface tab = addTabInterface(15106);
		addSprite3(15107, 116);

		addHoverButton_sprite_loader(15210, 142, 21, 21, "Close", 250, 15211, 3);
		addHoveredButton_sprite_loader(15211, 143, 21, 21, 15212);

		addText(15111, "Equip Your Character...", wid, 2, 0xFF9300, false, true);
		addText(15112, "Attack bonus", wid, 2, 0xFF9300, false, true);
		addText(15113, "Defence bonus", wid, 2, 0xFF9300, false, true);
		addText(15114, "Other bonuses", wid, 2, 0xFF9300, false, true);

		addText(21451, "Ranged Strength:", wid, 1, 0xFF9300, false, true);
		addText(21452, "Magic Damage:", wid, 1, 0xFF9300, false, true);
		addText(1686, "Melee Strength:", wid, 1, 0xFF9300, false, true);

		addText(16117, "0kg", wid, 1, 0xFF9300, false, true);

		for (int i = 1675; i <= 1684; i++) {
			textSize(i, wid, 1);
		}
		textSize(1686, wid, 1);
		textSize(1687, wid, 1);
		addChar(15125);
		tab.totalChildren(47);
		tab.child2(0, 15107, 5, 5);
		tab.child2(1, 15210, 477, 12);
		tab.child2(2, 15211, 477, 12);
		tab.child2(3, 15111, 15, 15);
		int Child = 4;
		int Y = 69;
		int xOff = 332;
		int xOff2 = 323;
		int yOff = 7;
		int yOff2 = 8;
		for (int i = 1675; i <= 1679; i++) {
			tab.child2(Child, i, 20 + xOff, Y - yOff2);
			Child++;
			Y += 14;
		}

		tab.child2(9, 1680, 20 + xOff, 161 - yOff2);
		tab.child2(10, 1681, 20 + xOff, 177 - yOff2);
		tab.child2(11, 1682, 20 + xOff, 192 - yOff2);
		tab.child2(12, 1683, 20 + xOff, 207 - yOff2);
		tab.child2(13, 1684, 20 + xOff, 221 - yOff2);
		tab.child2(14, 1686, 20 + xOff, 262 - yOff2);
		tab.child2(15, 15125, 192, 210);
		tab.child2(16, 15112, 16 + xOff, 55 - yOff2);
		tab.child2(18, 15113, 16 + xOff, 147 - yOff2);
		tab.child2(19, 15114, 16 + xOff, 248 - yOff2);
		tab.child2(20, 1645, 104 + 295 - xOff2, 149 - 52 + yOff);
		tab.child2(21, 1646, 399 - xOff2, 163 + yOff);
		tab.child2(22, 1647, 399 - xOff2, 163 + yOff);
		tab.child2(23, 1648, 399 - xOff2, 58 + 146 + yOff);
		tab.child2(24, 1649, 26 + 22 + 297 - xOff2 - 2, 110 - 44 + 118 - 13 + 5 + yOff);
		tab.child2(25, 1650, 321 - xOff2 + 22, 58 + 154 + yOff);
		tab.child2(26, 1651, 321 - xOff2 + 134, 58 + 118 + yOff);
		tab.child2(27, 1652, 321 - xOff2 + 134, 58 + 154 + yOff);
		tab.child2(28, 1653, 321 - xOff2 + 48, 58 + 81 + yOff);
		tab.child2(29, 1654, 321 - xOff2 + 107, 58 + 81 + yOff);
		tab.child2(30, 1655, 321 - xOff2 + 58, 58 + 42 + yOff);
		tab.child2(31, 1656, 321 - xOff2 + 112, 58 + 41 + yOff);
		tab.child2(32, 1657, 321 - xOff2 + 78, 58 + 4 + yOff);
		tab.child2(33, 1658, 321 - xOff2 + 37, 58 + 43 + yOff);
		tab.child2(34, 1659, 321 - xOff2 + 78, 58 + 43 + yOff);
		tab.child2(35, 1660, 321 - xOff2 + 119, 58 + 43 + yOff);
		tab.child2(36, 1661, 321 - xOff2 + 22, 58 + 82 + yOff);
		tab.child2(37, 1662, 321 - xOff2 + 78, 58 + 82 + yOff);
		tab.child2(38, 1663, 321 - xOff2 + 134, 58 + 82 + yOff);
		tab.child2(39, 1664, 321 - xOff2 + 78, 58 + 122 + yOff);
		tab.child2(40, 1665, 321 - xOff2 + 78, 58 + 162 + yOff);
		tab.child2(41, 1666, 321 - xOff2 + 22, 58 + 162 + yOff);
		tab.child2(42, 1667, 321 - xOff2 + 134, 58 + 162 + yOff);
		tab.child2(43, 1688, 50 + 297 - xOff2 - 2, 110 - 13 + 5 + yOff);

		// Maxhits

		tab.child2(44, 16117, 87, 283);

		for (int i = 1675; i <= 1684; i++) {
			RSInterface rsi = interfaceCache[i];
			rsi.textColor = 0xFF9300;
			rsi.centerText = false;
		}
		for (int i = 1686; i <= 1687; i++) {
			RSInterface rsi = interfaceCache[i];
			rsi.textColor = 0xFF9300;
			rsi.centerText = false;
		}

		tab.child2(44, 21451, 352, 270);
		tab.child2(45, 21452, 352, 286);
		tab.child2(17, 1687, 352, 302);
	}

	public static void equipv2(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(65000);
		String dir = "/Interfaces/Exp Lock/SPRITE";
		addSprite(65001, 0, "/Interfaces/equipv2/BACKGROUND");
		addHoverButton(65002, "Interfaces/CollectionLog/CLOSE", 0, 16, 16, "Close", -1, 65003, 1);
		addHoveredButton(65003, "Interfaces/CollectionLog/CLOSE", 1, 16, 16, 65004);

		addText(65005, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65006, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65007, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65008, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65009, "500.0", tda, 0, 0xFFFFFF, true, true);

		addText(65010, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65011, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65012, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65013, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65014, "500.0", tda, 0, 0xFFFFFF, true, true);

		addText(65015, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65016, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65017, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65018, "500.0", tda, 0, 0xFFFFFF, true, true);

		addText(65019, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65020, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65034, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65035, "500.0", tda, 0, 0xFFFFFF, true, true);
		addText(65036, "PLAYERNAME", tda, 1, 0xFFFFFF, true, true);
		addText(65037, "TOTAL LEVEL: 2376", tda, 0, 0xFFFFFF, true, true);

		addChar(65021);

		addToItemGroup(65022, 9, 20, 1, 0, true, "Remove", "Operate", null);

		addToItemGroup(65023, 1, 100, 10, 10, true, "Remove", "Operate", null);
		addToItemGroup(65024, 1, 100, 10, 10, true, "Remove", "Operate", null);
		addToItemGroup(65025, 1, 100, 10, 10, true, "Remove", "Operate", null);
		addToItemGroup(65026, 1, 100, 10, 10, true, "Remove", "Operate", null);
		addToItemGroup(65027, 1, 100, 10, 10, true, "Remove", "Operate", null);
		addToItemGroup(65028, 1, 100, 10, 10, true, "Remove", "Operate", null);
		addToItemGroup(65029, 1, 100, 10, 10, true, "Remove", "Operate", null);
		addToItemGroup(65030, 1, 100, 10, 10, true, "Remove", "Operate", null);
		addToItemGroup(65031, 1, 100, 10, 10, true, "Remove", "Operate", null);
		addToItemGroup(65032, 1, 100, 10, 10, true, "Remove", "Operate", null);
		addToItemGroup(65033, 1, 100, 10, 10, true, "Remove", "Operate", null);

		tab.totalChildren(37);
		tab.child(0, 65001, 5, 5);//background
		tab.child2(1, 65002, 480, 10);//close
		tab.child2(2, 65003, 480, 10);//close
		//Attack Stats
		tab.child2(3, 65005, 350, 69);//stats
		tab.child2(4, 65006, 350, 86);//stats
		tab.child2(5, 65007, 350, 103);//stats
		tab.child2(6, 65008, 350, 120);//stats
		tab.child2(7, 65009, 350, 137);//stats
		//Defense Stats
		tab.child2(8, 65010, 470, 69);//stats
		tab.child2(9, 65011, 470, 86);//stats
		tab.child2(10, 65012, 470, 103);//stats
		tab.child2(11, 65013, 470, 120);//stats
		tab.child2(12, 65014, 470, 137);//stats
		//other bonuses
		tab.child2(13, 65015, 351, 179);//stats
		tab.child2(14, 65016, 351, 196);//stats
		tab.child2(15, 65017, 351, 213);//stats
		tab.child2(16, 65018, 351, 230);//stats
		//target-specific bonuses
		tab.child2(17, 65019, 470, 179);//stats
		tab.child2(18, 65020, 470, 196);//stats
		tab.child2(32, 65034, 470, 213);//stats
		tab.child2(33, 65035, 470, 230);//stats
		tab.child2(34, 65036, 120, 35);//NAME
		tab.child2(35, 65037, 120, 50);//TOTAL LEVEL

		tab.child2(19, 65021, 60, 208);//char
		tab.child2(20, 65022, 200, 290);//Perk Grouping

		tab.child2(21, 65023, 15, 75);//Helm
		tab.child2(22, 65024, 15, 118);//Neck
		tab.child2(23, 65025, 15, 158);//Cape
		tab.child2(24, 65026, 15, 200);//Body
		tab.child2(25, 65027, 15, 240);//Legs
		tab.child2(26, 65028, 15, 280);//Boots
		tab.child2(27, 65029, 200, 76);//Weapon
		tab.child2(28, 65030, 200, 118);//Shield
		tab.child2(29, 65031, 200, 158);//Arrows
		tab.child2(30, 65032, 200, 200);//Ring
		tab.child2(31, 65033, 200, 240);//Gloves

	}

	public static void expLock(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(37600);
		String dir = "/Interfaces/Exp Lock/SPRITE";
		String dir2 = "/Interfaces/Prestige/PlayerStats/skills/IMG";
		addSprite(37601, 0, dir);
		addHoverButton(37602, dir, 1, 16, 16, "Close", -1, 37603, 1);
		addHoveredButton(37603, dir, 2, 16, 16, 37604);
		addText(37605, "Exp Lock Manager", tda, 2, 0xFFA500, true, true);
		int x = 150, y = 10;
		tab.totalChildren(5);
		tab.child(0, 37601, x, y);
		tab.child(1, 37602, 180 + x, 4 + y);
		tab.child(2, 37603, 180 + x, 4 + y);
		tab.child(3, 37605, 100 + x, 5 + y);
		tab.child(4, 37610, 10 + x, 30 + y);

		final String[] skillNames = {"Attack", "Defence", "Strength", "Hitpoints", "Ranged", "Prayer", "Magic"};

		RSInterface over = addInterface(37610);
		addSprite(37627, 5, dir);
		int xx = 0, yy = 0;
		over.totalChildren(42);
		for (int i = 0; i < 7; i++) {
			addButton(37611 + i, 3, dir, "Toggle @lre@" + skillNames[i]);
			addSprite(37619 + i, i, dir2);
			addText(37628 + i, Client.capitalize(skillNames[i]) + ":", tda, 0, 16748608, false, true);
			addText(37636 + i, "@gre@Unlocked", tda, 0, 16777215, false, true);
			addText(37644 + i, "@gre@99", tda, 0, 16777215, false, true);
			int[] g = centerSkillSprite(interfaceCache[37619 + i].sprite1);
			over.child(i, 37611 + i, xx, yy);
			over.child(i + 7, 37619 + i, xx + g[0], yy + g[1]);
			over.child(i + 14, 37627, xx + 32, yy + 7);
			over.child(i + 21, 37628 + i, xx + 35, yy + 10);
			over.child(i + 28, 37636 + i, xx + 125, yy + 10);
			over.child(i + 35, 37644 + i, xx + 90, yy + 10);
			xx += 117;
			if (xx == 117) {
				xx = 0;
				yy += 39;
			}
		}
	}

	public static void prestigeInterface(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(37300);
		String dir = "/Interfaces/Prestige/SPRITE";
		String dir2 = "/Interfaces/Prestige/PlayerStats/skills/IMG";
		addSprite(37301, 0, dir);
		addHoverButton(37302, dir, 1, 16, 16, "Close", -1, 37303, 1);
		addHoveredButton(37303, dir, 2, 16, 16, 37304);
		get(37302).atActionType = 3;
		get(37303).atActionType = 3;

		addText(37305, "Prestige Manager", tda, 2, 0xFFA500, true, true);
		addSprite(37306, 6, dir);
		addText(37307, "Attack:", tda, 2, 0xFFA500, true, true);
		addText(37308, "Current Prestige: @whi@0", tda, 0, 16748608, false, true);
		addText(37309, "Reward: @whi@1000 Points", tda, 0, 16748608, false, true);
		addText(37390, "Can Prestige: @whi@...", tda, 0, 16748608, false, true);
		addHoverButton(37391, dir, 7, 80, 31, "Prestige selected skill", 0, 37392, 1);
		addHoveredButton(37392, dir, 8, 80, 31, 37393);
		addText(37394, "Prestige", tda, 2, 16777215, true, true);
		int x = 10, y = 10;
		tab.totalChildren(13);
		tab.child(0, 37301, x, y);
		tab.child(1, 37302, 463 + x, 6 + y);
		tab.child(2, 37303, 463 + x, 6 + y);
		tab.child(3, 37305, 243 + x, 6 + y);
		tab.child(4, 37310, 10 + x, 30 + y);
		tab.child(5, 37306, 258 + x, 222 + y);
		tab.child(6, 37307, 366 + x, 226 + y);
		tab.child(7, 37308, 263 + x, 242 + y);
		tab.child(8, 37309, 263 + x, 257 + y);
		tab.child(9, 37390, 263 + x, 273 + y);
		tab.child(10, 37391, 389 + x, 255 + y);
		tab.child(11, 37392, 389 + x, 255 + y);
		tab.child(12, 37394, 429 + x, 262 + y);

		final String[] skillNames = {"attack", "defence", "strength", "hitpoints", "ranged", "prayer", "magic",
				"cooking", "woodcutting", "fletching", "fishing", "firemaking", "crafting", "smithing", "mining",
				"herblore", "agility", "thieving", "slayer", "farming", "runecraft", "hunter", "-unused-", "-unused-",
				"-unused-"};

		RSInterface over = addInterface(37310);
		addSprite(37359, 5, dir);
		int xx = 0, yy = 0, line = 0;
		over.totalChildren(110);
		for (int i = 0; i < 22; i++) {
			addButton(37311 + i, 3, dir, "Select @lre@" + skillNames[i]);
			addSprite(37335 + i, i, dir2);
			addText(37360 + i, Client.capitalize(skillNames[i]), tda, 0, 16748608, false, true);
			addText(37400 + i, "0", tda, 0, 16777215, false, true);
			int[] g = centerSkillSprite(interfaceCache[37335 + i].sprite1);
			int bonusY = 0;
			if (xx == 0 || xx == 117)
				bonusY = 5 * line;
			over.child(i, 37311 + i, xx, yy + bonusY);
			over.child(i + 22, 37335 + i, xx + g[0], yy + g[1] + bonusY);
			over.child(i + 44, 37359, xx + 32, yy + 7 + bonusY);
			over.child(i + 66, 37360 + i, xx + 35, yy + 10 + bonusY);
			over.child(i + 88, 37400 + i, xx + 102, yy + 10 + bonusY);
			xx += 117;
			if (xx == 468) {
				xx = 0;
				yy += 39;
				line++;
			}
		}
	}

	public static int[] centerSkillSprite(Sprite s) {
		int x = 15, y = 15;
		x -= (s.myWidth / 2);
		y -= (s.myHeight / 2);
		return new int[]{x, y};
	}

	public static void slayerOverlay(TextDrawingArea[] tda) {
		RSInterface rsInterface = addInterface(35424);
		addText(35425, "Abyssal Demon: 5", tda, 1, 0xF7FE2E, true, true);
		setChildren(1, rsInterface);
		rsInterface.child(0, 35425,
				!Client.instance.isResized() ? 250 : (Client.canvasWidth - 300), 10); // Assignment
	}

	public static void bloodOverlay(TextDrawingArea[] tda) {
		RSInterface rsInterface = addInterface(35427);
		addText(35428, "Blood Wave: 0", tda, 1, 0xF7FE2E, true, true);
		addText(35429, "Next Wave: ", tda, 1, 0xF7FE2E, true, true);
		setChildren(2, rsInterface);
		rsInterface.child(0, 35428,
				!Client.instance.isResized() ? 250 : (Client.canvasWidth - 300), 10);
		rsInterface.child(1, 35429,
				!Client.instance.isResized() ? 250 : (Client.canvasWidth - 300), 30); // Assignment
	}

	public static void fusionSystem(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(37200);
		tab.totalChildren(25);
		int x = 58;
		addSprite(37201, 0, "Interfaces/fusion/END");
		tab.child(0, 37201, 10 + x, 5);
		addButton(37202, 1, "Interfaces/fusion/END", "Fuse");
		tab.child(1, 37202, 64 + x, 293);
		addText(37203, "126", tda, 0, 0xffb000);
		tab.child(2, 37203, 263 + x, 305);
		addText(37204, "2 Bil (GP)", tda, 0, 0xffb000);
		tab.child(3, 37204, 332 + x, 305);

		addToItemGroup(37213, 1, 100, 10, 10, false, null, null, null);
		tab.child(4, 37213, 34 + x, 78);
		addToItemGroup(37214, 1, 100, 10, 10, false, null, null, null);
		tab.child(5, 37214, 72 + x, 78);
		addToItemGroup(37215, 1, 100, 10, 10, false, null, null, null);
		tab.child(6, 37215, 110 + x, 78);
		addToItemGroup(37216, 1, 100, 10, 10, false, null, null, null);
		tab.child(7, 37216, 147 + x, 78);
		addToItemGroup(37217, 1, 100, 10, 10, false, null, null, null);
		tab.child(8, 37217, 92 + x, 116);

		addToItemGroup(37218, 1, 100, 10, 10, false, null, null, null);
		tab.child(9, 37218, 36 + x, 185);
		addToItemGroup(37219, 1, 100, 10, 10, false, null, null, null);
		tab.child(10, 37219, 74 + x, 185);
		addToItemGroup(37220, 1, 100, 10, 10, false, null, null, null);
		tab.child(11, 37220, 112 + x, 185);
		addToItemGroup(37221, 1, 100, 10, 10, false, null, null, null);
		tab.child(12, 37221, 151 + x, 185);
		addToItemGroup(37222, 1, 100, 10, 10, false, null, null, null);
		tab.child(13, 37222, 94 + x, 223);

		addConfigButton(37226, 37200, 0, 1, "Interfaces/fusion/BUTTON", 94, 20, "Weaponry", 1, 1, 50);
		tab.child(14, 37226, 15 + x, 38);
		addConfigButton(37227, 37200, 0, 1, "Interfaces/fusion/BUTTON", 94, 20, "Armory", 1, 1, 51);
		tab.child(15, 37227, 109 + x, 38);
		addConfigButton(37228, 37200, 0, 1, "Interfaces/fusion/BUTTON", 94, 20, "Accessories", 1, 1, 52);
		tab.child(16, 37228, 202 + x, 38);
		addConfigButton(37229, 37200, 0, 1, "Interfaces/fusion/BUTTON", 94, 20, "Miscellaneous", 1, 1, 53);
		tab.child(17, 37229, 295 + x, 38);

		addText(37230, "Weaponary", tda, 0, 0xffb000);
		tab.child(18, 37230, 37 + x, 40);
		addText(37231, "Armory", tda, 0, 0xffb000);
		tab.child(19, 37231, 126 + x, 40);
		addText(37232, "Accessories", tda, 0, 0xffb000);
		tab.child(20, 37232, 219 + x, 40);
		addText(37233, "Miscellaneous", tda, 0, 0xffb000);
		tab.child(21, 37233, 315 + x, 40);

		addHoverButton(37234, "/Interfaces/Prestige/SPRITE", 1, 16, 16, "Close", -1, 37235, 1);
		addHoveredButton(37235, "/Interfaces/Prestige/SPRITE", 2, 16, 16, 39023);
		tab.child(22, 37234, 428, 12);
		tab.child(23, 37235, 428, 12);

		tab.child(24, 37236, 197 + x, 86);

		RSInterface scroll = addInterface(37236);
		scroll.totalChildren(1);
		scroll.width = 175;
		scroll.height = 207;
		scroll.scrollMax = 1500;

		addToItemGroup(37237, 4, 1750, 10, 8, true, "Select", null, null);
		setChildren(1, scroll);
		setBounds(37237, 8, 8, 0, scroll);
	}

	public static void discordSystem(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(37500);
		tab.centerText = true;
		tab.totalChildren(20);
		int x = 114;
		int y = 2;
		addSprite(37501, 0, "Interfaces/discord/Interface");
		tab.child(0, 37501, 10 + x, y + 2);
		//exit
		addHoverButton(37502, "Interfaces/Prestige/SPRITE", 1, 16, 16, "Close", -1, 37503, 1);
		addHoveredButton(37503, "Interfaces/Prestige/SPRITE", 2, 16, 16, 39023);
		tab.child(1, 37502, 257 + x, y + 12);
		tab.child(2, 37503, 257 + x, y + 12);
		//Disconnect
		addButton(37504, 0, "Interfaces/discord/SPRITE", "Disconnect");
		tab.child(3, 37504, 180 + x, y + 295);
		//sync
		addButton(37505, 2, "Interfaces/discord/SPRITE", "Sync");
		tab.child(4, 37505, 25 + x, y + 295);
		//Toggle
		addButton(37506, 4, "Interfaces/discord/SPRITE", "Toggle");
		tab.child(5, 37506, 103 + x, y + 295);

		addText(37507, 0, 0xff0000, true, "Inactive");
		tab.child(6, 37507, 147 + x, y + 62);
		addText(37508, 0, 0xff0000, true, "Inactive");
		tab.child(7, 37508, 147 + x, y + 112);
		addText(37509, 0, 0xff0000, true, "Inactive");
		tab.child(8, 37509, 147 + x, y + 162);
		addText(37510, 0, 0xffb000, true, "");
		tab.child(9, 37510, 147 + x, y + 213);
		addText(37511, 0, 0x00ff00, true, "999999999");
		tab.child(10, 37511, 147 + x, y + 262);

		addText(37512, "Discord Status", tda, 0, 0xffb000);
		tab.child(11, 37512, 110 + x, y + 47);
		addText(37513, "Discord Notifications", tda, 0, 0xffb000);
		tab.child(12, 37513, 95 + x, y + 97);
		addText(37514, "Boosting Status", tda, 0, 0xffb000);
		tab.child(13, 37514, 107 + x, y + 147);
		addText(37515, "Discord Boosting Perk", tda, 0, 0xffb000);
		tab.child(14, 37515, 92 + x, y + 197);
		addText(37516, "Discord Points", tda, 0, 0xffb000);
		tab.child(15, 37516, 111 + x, y + 247);

		addText(37517, "Sync", tda, 0, 0xffb000);
		tab.child(16, 37517, 50 + x, y + 302);
		addText(37518, "Toggle", tda, 0, 0xffb000);
		tab.child(17, 37518, 130 + x, y + 302);
		addText(37519, "Disconnect", tda, 0, 0xffb000);
		tab.child(18, 37519, 207 + x, y + 302);

		addText(37520, "Discord Manager", tda, 2, 0xffb000);
		tab.child(19, 37520, 95 + x, y + 12);
	}

	/*	public static void buildPlayerMenu(ArrayList<Account> accountList) {
            RSInterface rsi = addTabInterface(37900);
            setChildren(accountList.size(), rsi);
            int interId = 37901;
            int frame = 0;
            int x = 157, y = 306;
            for (Account account : accountList) {
                addAPlayerHead(interId, account);
                setBounds(interId, x-20, y-10, frame, rsi);
                frame++;
                interId++;
                x += 50;
            }
        }*/
	private final static int[] headAnims = new int[]{9846, 9742, 9827, 9841,
			9851, 9745, 9785, 9805, 9810, 9815, 9820, 9860, 9835, 9845, 9850,
			9855, 9864, 9851};

	public static int getRandom(int number, boolean greaterThan0) {
		Random random = new Random();
		int randomNr = random.nextInt(number) + (greaterThan0 ? 1 : 0);
		return randomNr;
	}

	/*	public static void addAPlayerHead(int interfaceID, Account a) {
            RSInterface rsi = addTabInterface(interfaceID);
            rsi.type = 6;
            rsi.contentType = (a.getIDKHead() <= 0 ? 11 : 10);
            int anim = headAnims[getRandom(headAnims.length - 1, false)];
            rsi.enabledAnimationId = rsi.disabledAnimationId = anim;
            rsi.mediaID = (a.getIDKHead() <= 0 ? a.getHelmet() : a.getIDKHead());
            rsi.plrJaw = a.getJaw();
            if (a.getGender() == 1)
                rsi.plrJaw = -1;
            rsi.gender = a.getGender();
            rsi.modelZoom = 2000;
            rsi.modelRotation1 = 40;
            rsi.modelRotation2 = 1800;
            rsi.height = 150;
            rsi.width = 150;
        }*/
	public static void taskMaster(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(38000);
		tab.totalChildren(15);
		addSprite(38001, 0, "Interfaces/taskmaster/BACKGROUND");
		tab.child(0, 38001, 50, 15);

		addText(38002, "Kill @whi@Nex @or1@55 times.", tda, 0, 0xffb000);
		tab.child(1, 38002, 110, 82);

		addText(38003, "@yel@24H - 21M - 58S", tda, 0, 0xffff00);
		tab.child(2, 38003, 295, 82);

		addText(38004, "@gre@Complete", tda, 0, 0xFFFFFF);
		tab.child(3, 38004, 380, 82);

		addText(38005, "Kill @whi@Nex @or1@55 times.", tda, 0, 0xffb000);
		tab.child(4, 38005, 110, 127);

		addText(38006, "@yel@24H - 21M - 58S", tda, 0, 0xffff00);
		tab.child(5, 38006, 295, 127);

		addText(38007, "@red@Incomplete", tda, 0, 0xFFFFFF);
		tab.child(6, 38007, 380, 127);

		addText(38008, "Complete @whi@Trials @or1@55 times.", tda, 0, 0xffb000);
		tab.child(7, 38008, 110, 195);

		addText(38009, "4D-24H-21M-58S", tda, 0, 0xffff00);
		tab.child(8, 38009, 295, 195);

		addText(38010, "@gre@Complete", tda, 0, 0xFFFFFF);
		tab.child(9, 38010, 380, 195);

		addConfigButton(38011, 38000, 1, 2, "Interfaces/taskmaster/SPRITE", 13, 11, "Claim Reward", 1, 1, 5000);
		addConfigButton(38012, 38000, 1, 2, "Interfaces/taskmaster/SPRITE", 13, 11, "Claim Reward", 1, 1, 5001);
		addConfigButton(38013, 38000, 1, 2, "Interfaces/taskmaster/SPRITE", 13, 11, "Claim Reward", 1, 1, 5002);
		tab.child(10, 38011, 440, 73);
		tab.child(11, 38012, 440, 116);
		tab.child(12, 38013, 440, 186);

		addHoverButton(38014, "/Interfaces/Prestige/SPRITE", 1, 16, 16, "Close", -1, 38015, 1);
		addHoveredButton(38015, "/Interfaces/Prestige/SPRITE", 2, 16, 16, 37304);
		tab.child(13, 37302, 447, 22);
		tab.child(14, 37303, 447, 22);
	}

	private static void skotizo(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(29230);
		tab.totalChildren(5);
		addSprite(29231, 0, "Interfaces/Skotizo/SKOTIZOM");
		tab.child(0, 29231, 36, 141);
		addSprites(29232, "Interfaces/Skotizo/SKOTIZO", 3, 0);
		tab.child(1, 29232, 56, 128);
		addSprites(29233, "Interfaces/Skotizo/SKOTIZO", 3, 0);
		tab.child(2, 29233, 56, 193);
		addSprites(29234, "Interfaces/Skotizo/SKOTIZO", 3, 0);
		tab.child(3, 29234, 23, 160);
		addSprites(29235, "Interfaces/Skotizo/SKOTIZO", 3, 0);
		tab.child(4, 29235, 88, 160);
	}

	public static void tradingSelect(TextDrawingArea[] tda) {
		RSInterface Interface = addTabInterface(48599);
		RSInterface history = addTabInterface(48635);
		RSInterface offers = addTabInterface(48950);

		addSprite(48601, 7, "Trading/SPRITE");
		addHover(48602, 3, 0, 48603, 1, "Bank/BANK", 17, 17, "Close Window");
		addHovered(48603, 2, "Bank/BANK", 17, 17, 48604);

		addText(48605, "Trading Post", 0xff9933, true, true, -1, tda, 2);

		addText(48606, "Coffer", 0xff9933, true, true, -1, tda, 2);
		addHoverButton(48607, "Trading/SPRITE", 8, 143, 35, "Claim money", 0, 48608, 1);
		addHoveredButton(48608, "Trading/SPRITE", 9, 143, 35, 48609);
		addText(48610, "", 0xff9933, true, true, -1, tda, 0);

		addText(48611, "Search for...", 0xff9933, true, true, -1, tda, 2);
		addHoverButton(48612, "Trading/SPRITE", 10, 72, 32, "Search for a item", 0, 48613, 1);
		addHoveredButton(48613, "Trading/SPRITE", 11, 72, 32, 48614);

		addHoverButton(48615, "Trading/SPRITE", 10, 72, 32, "Search for a player", 0, 48616, 1);
		addHoveredButton(48616, "Trading/SPRITE", 11, 72, 32, 48617);

		addHoverButton(48618, "Trading/SPRITE", 10, 72, 32, "Recent offers", 0, 48619, 1);
		addHoveredButton(48619, "Trading/SPRITE", 11, 72, 32, 48620);

		addHoverButton(48621, "Trading/SPRITE", 12, 150, 35, "Click", 0, 48622, 1);
		addHoveredButton(48622, "Trading/SPRITE", 13, 150, 35, 48623);

		addText(48624, "History", 0xff9933, true, true, -1, tda, 2);
		addText(48625, "My Offers...", 0xff9933, true, true, -1, tda, 2);

		addText(48626, "Item", 0xff9933, true, true, -1, tda, 2);
		addText(48627, "Player", 0xff9933, true, true, -1, tda, 2);
		addText(48628, "Recent", 0xff9933, true, true, -1, tda, 2);
		addText(48629, "Cancel Listing", 0xff9933, true, true, -1, tda, 2);

		setChildren(25, Interface);

		setBounds(48601, 8, 10, 0, Interface); // Base
		setBounds(48602, 471, 18, 1, Interface); // Close
		setBounds(48603, 471, 18, 2, Interface);

		setBounds(48605, 262, 20, 3, Interface); // Title

		setBounds(48606, 40, 50, 4, Interface); // Coffer text
		setBounds(48607, 19, 68, 5, Interface); // Coffer Button
		setBounds(48608, 19, 68, 6, Interface);
		setBounds(48610, 75, 80, 7, Interface); // Amount in coffer

		setBounds(48611, 59, 250, 8, Interface); // Text search for...
		setBounds(48612, 19, 270, 9, Interface); // Item Button
		setBounds(48613, 19, 270, 10, Interface);

		setBounds(48615, 96, 270, 11, Interface); // Player Button
		setBounds(48616, 96, 270, 12, Interface);

		setBounds(48618, 173, 270, 13, Interface); // Recent Button
		setBounds(48619, 173, 270, 14, Interface);

		setBounds(48621, 293, 270, 15, Interface); // Long button
		setBounds(48622, 293, 270, 16, Interface);

		setBounds(48624, 42, 104, 17, Interface); // Text History
		setBounds(48625, 289, 50, 18, Interface); // TextMy offers

		setBounds(48626, 55, 279, 19, Interface); // Text Item
		setBounds(48627, 132, 279, 20, Interface); // Text Player
		setBounds(48628, 208, 279, 21, Interface); // Text Recent
		setBounds(48629, 367, 279, 22, Interface); // Text List

		setBounds(48635, 30, 128, 23, Interface); // Interface history
		setBounds(48950, 260, 75, 24, Interface); // Interface my offers

		setChildren(20, history);
		for (int i = 48636, y = 5, id = 0; i < 48656; i++) {
			addText(i, "", 0xff9933, true, true, -1, tda, 0);
			setBounds(i, 94, y, id, history);
			if (id == 1 || id == 3 || id == 5 || id == 7 || id == 9 || id == 11 || id == 13 || id == 15 || id == 17)
				y += 20;
			else
				y += 10;
			id++;
		}

		history.scrollMax = 300;
		history.width = 193;
		history.height = 116;

		addText(48951, "Select an item", 0xff9933, true, true, -1, tda, 2);
		addText(48952, "from your", 0xff9933, true, true, -1, tda, 2);
		addText(48953, "inventory.", 0xff9933, true, true, -1, tda, 2);

		setChildren(3, offers);

		setBounds(48951, 108, 66, 0, offers); // Name
		setBounds(48952, 108, 80, 1, offers); // Price
		setBounds(48953, 108, 94, 2, offers); // sold

		// offers.scrollMax = 300;
		offers.width = 203;
		offers.height = 181;
	}

	public static void tradingpost(TextDrawingArea[] tda) {
		RSInterface Interface = addTabInterface(48600);
		RSInterface history = addTabInterface(48635);
		RSInterface offers = addTabInterface(48786);

		addSprite(48601, 7, "Trading/SPRITE");
		addHover(48602, 3, 0, 48603, 1, "Bank/BANK", 17, 17, "Close Window");
		addHovered(48603, 2, "Bank/BANK", 17, 17, 48604);

		addText(48605, "Trading Post", 0xff9933, true, true, -1, tda, 2);

		addText(48606, "Coffer", 0xff9933, true, true, -1, tda, 2);
//		configHoverButton(48607, "Claim money", "Trading/SPRITE", 8,9,8,8,false, 48607);
		addHoverButton(48607, "Trading/SPRITE", 8, 143, 35, "Claim money", 0, 48608, 1);
		addHoveredButton(48608, "Trading/SPRITE", 9, 143, 35, 48609);
		addText(48610, "Empty", 0xff9933, true, true, -1, tda, 0);

		addText(48611, "Search for...", 0xff9933, true, true, -1, tda, 2);
//		configHoverButton(48612, "Search for a item", "Trading/SPRITE", 10,11,10,10, false, 48612);
		addHoverButton(48612, "Trading/SPRITE", 10, 72, 32, "Search for a item", 0, 48613, 1);
		addHoveredButton(48613, "Trading/SPRITE", 11, 72, 32, 48614);

//		configHoverButton(48615, "Search for a player", "Trading/SPRITE", 10,11,10,10, false, 48615);
		addHoverButton(48615, "Trading/SPRITE", 10, 72, 32, "Search for a player", 0, 48616, 1);
		addHoveredButton(48616, "Trading/SPRITE", 11, 72, 32, 48617);

//		configHoverButton(48618, "Recent offers", "Trading/SPRITE", 10,11,10,10, false, 48618);
		addHoverButton(48618, "Trading/SPRITE", 10, 72, 32, "Recent offers", 0, 48619, 1);
		addHoveredButton(48619, "Trading/SPRITE", 11, 72, 32, 48620);

/*		addHoverButton(48621, "Trading/SPRITE", 12, 150, 35, "Click", 0, 48622, 1);
		addHoveredButton(48622, "Trading/SPRITE", 13, 150, 35, 48623);*/

		addText(48624, "History", 0xff9933, true, true, -1, tda, 2);
		addText(48625, "My Offers...", 0xff9933, true, true, -1, tda, 2);

		addText(48626, "Item", 0xff9933, true, true, -1, tda, 2);
		addText(48627, "Player", 0xff9933, true, true, -1, tda, 2);
		addText(48648, "Recent", 0xff9933, true, true, -1, tda, 2);
//		addText(48630, "List item for sale", 0xff9933, true, true, -1, tda, 2);

		setChildren(25, Interface);

		setBounds(48601, 8, 10, 0, Interface); // Base
		setBounds(48602, 471, 18, 1, Interface); // Close
		setBounds(48603, 471, 18, 2, Interface);

		setBounds(48605, 262, 20, 3, Interface); // Title

		setBounds(48606, 40, 50, 4, Interface); // Coffer text
		setBounds(48607, 19, 68, 5, Interface); // Coffer Button
		setBounds(48608, 19, 68, 6, Interface);
		setBounds(48610, 75, 80, 7, Interface); // Amount in coffer

		setBounds(48611, 59, 250, 8, Interface); // Text search for...
		setBounds(48612, 19, 270, 9, Interface); // Item Button
		setBounds(48613, 19, 270, 10, Interface);

		setBounds(48615, 96, 270, 11, Interface); // Player Button
		setBounds(48616, 96, 270, 12, Interface);

		setBounds(48618, 173, 270, 13, Interface); // Recent Button
		setBounds(48619, 173, 270, 14, Interface);

/*		setBounds(48621, 293, 270, 15, Interface); // Long button
		setBounds(48622, 293, 270, 16, Interface);*/

		setBounds(48624, 42, 104, 17, Interface); // Text History
		setBounds(48625, 289, 50, 18, Interface); // TextMy offers

		setBounds(48626, 55, 279, 19, Interface); // Text Item
		setBounds(48627, 132, 279, 20, Interface); // Text Player
		setBounds(48628, 208, 279, 21, Interface); // Text Recent
//		setBounds(48630, 367, 279, 22, Interface); // Text List

		setBounds(48635, 30, 128, 23, Interface); // Interface history
		setBounds(48786, 260, 75, 24, Interface); // Interface my offers

		setChildren(40, history);
		for (int i = 48636, y = 5, id = 0; i < 48676; i++) {
			addText(i, "", 0xff9933, true, true, -1, tda, 0);
			setBounds(i, 94, y, id, history);
			if (id == 1 || id == 3 || id == 5 || id == 7 || id == 9 || id == 11 || id == 13 || id == 15 || id == 17 || id == 19 || id == 21 || id == 23 || id == 25 || id == 27 || id == 29 || id == 31 || id == 33 || id == 35 || id == 37 || id == 39)
				y += 20;
			else
				y += 10;
			id++;
		}

		history.scrollMax = 5000;
		history.width = 193;
		history.height = 116;

		addListing(48847, false);
		addText(48788, "", 0xff9933, true, true, -1, tda, 0);
		addText(48789, "", 0xff9933, true, true, -1, tda, 0);
		addText(48790, "", 0xff9933, true, true, -1, tda, 0);

		addText(48792, "", 0xff9933, true, true, -1, tda, 0);
		addText(48793, "", 0xff9933, true, true, -1, tda, 0);
		addText(48794, "", 0xff9933, true, true, -1, tda, 0);

		addText(48796, "", 0xff9933, true, true, -1, tda, 0);
		addText(48797, "", 0xff9933, true, true, -1, tda, 0);
		addText(48798, "", 0xff9933, true, true, -1, tda, 0);

		addText(48800, "", 0xff9933, true, true, -1, tda, 0);
		addText(48801, "", 0xff9933, true, true, -1, tda, 0);
		addText(48802, "", 0xff9933, true, true, -1, tda, 0);

		addText(48804, "", 0xff9933, true, true, -1, tda, 0);
		addText(48805, "", 0xff9933, true, true, -1, tda, 0);
		addText(48806, "", 0xff9933, true, true, -1, tda, 0);

		addText(48808, "", 0xff9933, true, true, -1, tda, 0);
		addText(48809, "", 0xff9933, true, true, -1, tda, 0);
		addText(48810, "", 0xff9933, true, true, -1, tda, 0);

		addText(48812, "", 0xff9933, true, true, -1, tda, 0);
		addText(48813, "", 0xff9933, true, true, -1, tda, 0);
		addText(48814, "", 0xff9933, true, true, -1, tda, 0);

		addText(48816, "", 0xff9933, true, true, -1, tda, 0);
		addText(48817, "", 0xff9933, true, true, -1, tda, 0);
		addText(48818, "", 0xff9933, true, true, -1, tda, 0);

		addText(48820, "", 0xff9933, true, true, -1, tda, 0);
		addText(48821, "", 0xff9933, true, true, -1, tda, 0);
		addText(48822, "", 0xff9933, true, true, -1, tda, 0);

		addText(48824, "", 0xff9933, true, true, -1, tda, 0);
		addText(48825, "", 0xff9933, true, true, -1, tda, 0);
		addText(48826, "", 0xff9933, true, true, -1, tda, 0);

		addText(48828, "", 0xff9933, true, true, -1, tda, 0);
		addText(48829, "", 0xff9933, true, true, -1, tda, 0);
		addText(48830, "", 0xff9933, true, true, -1, tda, 0);

		addText(48832, "", 0xff9933, true, true, -1, tda, 0);
		addText(48833, "", 0xff9933, true, true, -1, tda, 0);
		addText(48834, "", 0xff9933, true, true, -1, tda, 0);

		addText(48836, "", 0xff9933, true, true, -1, tda, 0);
		addText(48837, "", 0xff9933, true, true, -1, tda, 0);
		addText(48838, "", 0xff9933, true, true, -1, tda, 0);

		addText(48840, "", 0xff9933, true, true, -1, tda, 0);
		addText(48841, "", 0xff9933, true, true, -1, tda, 0);
		addText(48842, "", 0xff9933, true, true, -1, tda, 0);

		addText(48844, "", 0xff9933, true, true, -1, tda, 0);
		addText(48845, "", 0xff9933, true, true, -1, tda, 0);
		addText(48846, "", 0xff9933, true, true, -1, tda, 0);

		setChildren(46, offers);

		setBounds(48788, 118, 10, 0, offers); // Name
		setBounds(48789, 51, 33, 1, offers); // Price
		setBounds(48790, 118, 23, 2, offers); // sold

		setBounds(48792, 118, 57, 3, offers); // Name y + 49
		setBounds(48793, 51, 80, 4, offers); // Price y + 48
		setBounds(48794, 118, 70, 5, offers); // sold y + 49

		setBounds(48796, 118, 104, 6, offers); // Name y + 49
		setBounds(48797, 51, 127, 7, offers); // Price y + 28
		setBounds(48798, 118, 117, 8, offers); // sold y + 49

		setBounds(48800, 118, 151, 9, offers); // Name y + 49
		setBounds(48801, 51, 174, 10, offers); // Price y + 28
		setBounds(48802, 118, 164, 11, offers); // sold y + 49

		setBounds(48804, 118, 198, 12, offers); // Name y + 49
		setBounds(48805, 51, 221, 13, offers); // Price y + 28
		setBounds(48806, 118, 211, 14, offers); // sold y + 49

		setBounds(48808, 118, 245, 15, offers); // Name y + 49
		setBounds(48809, 51, 268, 16, offers); // Price y + 48
		setBounds(48810, 118, 258, 17, offers); // sold y + 49

		setBounds(48812, 118, 292, 18, offers); // Name y + 49
		setBounds(48813, 51, 315, 19, offers); // Price y + 48
		setBounds(48814, 118, 305, 20, offers); // sold y + 49

		setBounds(48816, 118, 339, 21, offers); // Name y + 49
		setBounds(48817, 51, 362, 22, offers); // Price y + 48
		setBounds(48818, 118, 352, 23, offers); // sold y + 49

		setBounds(48820, 118, 386, 24, offers); // Name y + 49
		setBounds(48821, 51, 409, 25, offers); // Price y + 48
		setBounds(48822, 118, 399, 26, offers); // sold y + 49

		setBounds(48824, 118, 433, 27, offers); // Name y + 47
		setBounds(48825, 51, 456, 28, offers); // Price y + 47 fout
		setBounds(48826, 118, 446, 29, offers); // sold y + 47

		setBounds(48828, 118, 480, 30, offers); // Name y + 49
		setBounds(48829, 51, 503, 31, offers); // Price y + 48
		setBounds(48830, 118, 493, 32, offers); // sold y + 49

		setBounds(48832, 118, 527, 33, offers); // Name y + 49
		setBounds(48833, 51, 550, 34, offers); // Price y + 48
		setBounds(48834, 118, 540, 35, offers); // sold y + 49

		setBounds(48836, 118, 574, 36, offers); // Name y + 49
		setBounds(48837, 51, 597, 37, offers); // Price y + 48
		setBounds(48838, 118, 587, 38, offers); // sold y + 49

		setBounds(48840, 118, 621, 39, offers); // Name y + 49
		setBounds(48841, 51, 644, 40, offers); // Price y + 48
		setBounds(48842, 118, 634, 41, offers); // sold y + 49

		setBounds(48844, 118, 668, 42, offers); // Name y + 49
		setBounds(48845, 51, 691, 43, offers); // Price y + 48
		setBounds(48846, 118, 681, 44, offers); // sold y + 49

		setBounds(48847, 35, 2, 45, offers);

		offers.scrollMax = 710;
		offers.width = 203;
		offers.height = 181;
	}

	public static void offer(TextDrawingArea[] tda) {

		RSInterface rsi = addTabInterface(48500);
		addOffer(48501);
		rsi.totalChildren(1);
		rsi.child(0, 48501, 16, 8);
	}

	public static void addOffer(int index) {
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[10];
		rsi.spritesX = new int[20];
		rsi.inventoryAmounts = new int[30];
		rsi.inventoryItemId = new int[30];
		rsi.spritesY = new int[20];

		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];

		rsi.actions[0] = "Offer 1";
		rsi.actions[1] = "Offer 5";
		rsi.actions[2] = "Offer 10";
		rsi.actions[3] = "Offer All";
		rsi.actions[4] = "Offer X";
		rsi.centerText = true;
		rsi.filled = false;
		rsi.aBoolean235 = false;
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.aBoolean259 = true;
		rsi.textShadow = false;
		rsi.invSpritePadX = 10;
		rsi.invSpritePadY = 4;
		rsi.height = 7;
		rsi.width = 4;
		rsi.parentID = 48501;
		rsi.id = 48500;
		rsi.type = 2;
	}

	public static void tradingSelected(TextDrawingArea[] tda) {
		RSInterface Interface = addTabInterface(48598);
		RSInterface history = addTabInterface(48635);
		RSInterface offers = addTabInterface(48960);

		addSprite(48601, 7, "Trading/SPRITE");
		addHover(48602, 3, 0, 48603, 1, "Bank/BANK", 17, 17, "Close Window");
		addHovered(48603, 2, "Bank/BANK", 17, 17, 48604);

		addText(48605, "Trading Post", 0xff9933, true, true, -1, tda, 2);

		addText(48606, "Coffer", 0xff9933, true, true, -1, tda, 2);
		addHoverButton(48607, "Trading/SPRITE", 8, 143, 35, "Claim money", 0, 48608, 1);
		addHoveredButton(48608, "Trading/SPRITE", 9, 143, 35, 48609);
		addText(48610, "Empty", 0xff9933, true, true, -1, tda, 0);

		addText(48611, "Search for...", 0xff9933, true, true, -1, tda, 2);
		addHoverButton(48612, "Trading/SPRITE", 10, 72, 32, "Search for a item", 0, 48613, 1);
		addHoveredButton(48613, "Trading/SPRITE", 11, 72, 32, 48614);

		addHoverButton(48615, "Trading/SPRITE", 10, 72, 32, "Search for a player", 0, 48616, 1);
		addHoveredButton(48616, "Trading/SPRITE", 11, 72, 32, 48617);

		addHoverButton(48618, "Trading/SPRITE", 10, 72, 32, "Recent offers", 0, 48619, 1);
		addHoveredButton(48619, "Trading/SPRITE", 11, 72, 32, 48620);

		addHoverButton(48621, "Trading/SPRITE", 12, 150, 35, "Click", 0, 48622, 1);
		addHoveredButton(48622, "Trading/SPRITE", 13, 150, 35, 48623);

		addText(48624, "History", 0xff9933, true, true, -1, tda, 2);
		addText(48625, "My Offers...", 0xff9933, true, true, -1, tda, 2);

		addText(48626, "Item", 0xff9933, true, true, -1, tda, 2);
		addText(48627, "Player", 0xff9933, true, true, -1, tda, 2);
		addText(48628, "Recent", 0xff9933, true, true, -1, tda, 2);
		addText(48629, "Cancel Listing", 0xff9933, true, true, -1, tda, 2);

		setChildren(25, Interface);

		setBounds(48601, 8, 10, 0, Interface); // Base
		setBounds(48602, 471, 18, 1, Interface); // Close
		setBounds(48603, 471, 18, 2, Interface);

		setBounds(48605, 262, 20, 3, Interface); // Title

		setBounds(48606, 40, 50, 4, Interface); // Coffer text
		setBounds(48607, 19, 68, 5, Interface); // Coffer Button
		setBounds(48608, 19, 68, 6, Interface);
		setBounds(48610, 75, 80, 7, Interface); // Amount in coffer

		setBounds(48611, 59, 250, 8, Interface); // Text search for...
		setBounds(48612, 19, 270, 9, Interface); // Item Button
		setBounds(48613, 19, 270, 10, Interface);

		setBounds(48615, 96, 270, 11, Interface); // Player Button
		setBounds(48616, 96, 270, 12, Interface);

		setBounds(48618, 173, 270, 13, Interface); // Recent Button
		setBounds(48619, 173, 270, 14, Interface);

		setBounds(48621, 293, 270, 15, Interface); // Long button
		setBounds(48622, 293, 270, 16, Interface);

		setBounds(48624, 42, 104, 17, Interface); // Text History
		setBounds(48625, 289, 50, 18, Interface); // TextMy offers

		setBounds(48626, 55, 279, 19, Interface); // Text Item
		setBounds(48627, 132, 279, 20, Interface); // Text Player
		setBounds(48628, 208, 279, 21, Interface); // Text Recent
		setBounds(48629, 367, 279, 22, Interface); // Text List

		setBounds(48635, 30, 128, 23, Interface); // Interface history
		setBounds(48960, 260, 75, 24, Interface); // Interface my offers

		setChildren(30, history);
		for (int i = 48636, y = 5, id = 0; i < 48666; i++) {
			addText(i, "", 0xff9933, true, true, -1, tda, 0);
			setBounds(i, 94, y, id, history);
			if (id == 1 || id == 3 || id == 5 || id == 7 || id == 9 || id == 11 || id == 13 || id == 15 || id == 17 ||
					id == 19 || id == 21 || id == 23 || id == 25 || id == 27)
				y += 20;
			else
				y += 10;
			id++;
		}

		history.scrollMax = 500;
		history.width = 193;
		history.height = 116;

		addSprite(48961, 14, "Trading/SPRITE");
		addItemOnInterface(48962);
		addText(48963, "", 0xff9933, true, true, -1, tda, 2);
		addText(48964, "", 0xff9933, true, true, -1, tda, 1);
		addText(48965, "", 0xff9933, true, true, -1, tda, 1);
		addText(48966, "", 0xff9933, true, true, -1, tda, 0); // Guide: 50M
		addText(48967, "", 0xff9933, true, true, -1, tda, 0); // Listed: 0

		addHoverButton(48968, "Trading/SPRITE", 10, 72, 32, "Set price", 0, 48969, 1);
		addHoveredButton(48969, "Trading/SPRITE", 11, 72, 32, 48970);

		// addHoverButton(48971, "Trading/SPRITE", 10, 72, 32, "Set quantity", 0, 48972,
		// 1);
		// addHoveredButton(48972, "Trading/SPRITE", 11, 72, 32, 48973);

		addHoverButton(48974, "Trading/SPRITE", 10, 72, 32, "Confirm offer", 0, 48975, 1);
		addHoveredButton(48975, "Trading/SPRITE", 11, 72, 32, 48976);

		addText(48977, "Set", 0xff9933, true, true, -1, tda, 2);
		addText(48978, "price", 0xff9933, true, true, -1, tda, 2);
		// addText(48979, "Set", 0xff9933, true, true, -1, tda, 2);
		// addText(48980, "quantity", 0xff9933, true, true, -1, tda, 2);
		addText(48981, "Confirm", 0xff9933, true, true, -1, tda, 2);

		setChildren(14, offers);

		setBounds(48961, 0, 0, 0, offers); // background
		setBounds(48962, 91, 11, 1, offers); // item
		setBounds(48963, 108, 50, 2, offers); // name
		setBounds(48964, 108, 70, 3, offers); // price ea
		setBounds(48965, 108, 85, 4, offers); // quantity
		setBounds(48966, 175, 5, 5, offers); // guide
		setBounds(48967, 175, 20, 6, offers); // listed

		setBounds(48968, 72, 105, 7, offers); // Set price button
		setBounds(48969, 72, 105, 8, offers);

		// setBounds(48971, 114, 105, 9, offers); //Set quantity button
		// setBounds(48972, 114, 105, 10, offers);

		setBounds(48974, 72, 142, 9, offers); // confirm button
		setBounds(48975, 72, 142, 10, offers);

		setBounds(48977, 107, 108, 11, offers); // set
		setBounds(48978, 107, 118, 12, offers); // price
		// setBounds(48979, 149, 108, 13, offers); //set
		// setBounds(48980, 149, 118, 14, offers); //quantity

		setBounds(48981, 107, 151, 13, offers); // confirm

		// offers.scrollMax = 300;
		offers.width = 219;
		offers.height = 181;
	}

	public static void addItemOnInterface(int i) {
		RSInterface rsinterface = interfaceCache[i] = new RSInterface();
		rsinterface.actions = new String[5];
		rsinterface.spritesX = new int[20];
		rsinterface.inventoryAmounts = new int[30];
		rsinterface.inventoryItemId = new int[30];
		rsinterface.spritesY = new int[20];
		rsinterface.children = new int[0];
		rsinterface.childX = new int[0];
		rsinterface.childY = new int[0];
		rsinterface.spritesY[0] = 0;
		// rsinterface.invStackSizes[0] = 0;
		for (int i2 = 0; i2 < 30; i2++)
			rsinterface.inventoryAmounts[i2] = 0;
		rsinterface.inventoryItemId[0] = 0;
		rsinterface.spritesX[0] = 0;
		rsinterface.actions[0] = "Cancel";
		rsinterface.centerText = false;
		rsinterface.filled = false;
		rsinterface.aBoolean235 = false;
		rsinterface.usableItemInterface = false;
		rsinterface.isInventoryInterface = false;
		rsinterface.aBoolean259 = false;
		rsinterface.textShadow = false;
		rsinterface.width = 6;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.invSpritePadX = 24;
		rsinterface.parentID = i;
		rsinterface.invSpritePadY = 24;
		rsinterface.type = 2;
		rsinterface.height = 5;
	}

	public static void dropGroup(int id, int w, int h, int x, int y) {
		RSInterface rsi = addInterface(id);
		rsi.width = w;
		rsi.height = h;
		rsi.inventoryItemId = new int[w * h];
		rsi.inventoryAmounts = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.type = 2;

		rsi.inventoryItemId[0] = 6200;
		rsi.inventoryAmounts[0] = 2;
	}


	public static void listings(TextDrawingArea[] tda) {

		RSInterface Interface = addTabInterface(48000);
		RSInterface main = addTabInterface(48020);

		setChildren(18, Interface);
		addSprite(48001, 0, "Trading/SPRITE");

		addHover(48002, 3, 0, 48003, 1, "Bank/BANK", 17, 17, "Close Window");
		addHovered(48003, 2, "Bank/BANK", 17, 17, 48004);

		configHoverButton(48005, "Go back", "Trading/SPRITE", 1,2,1,1,false, 48005);
/*		addHoverButton(48005, "Trading/SPRITE", 1, 88, 30, "Go back", 0, 48006, 1);
		addHoveredButton(48006, "Trading/SPRITE", 2, 88, 30, 48007);*/
//		addHoverButton(48008, "Trading/SPRITE", 10, 72, 35, "Prev Page", 0, 48009, 1);
//		addHoveredButton(48009, "Trading/SPRITE", 11, 72, 35, 48010);
//		addHoverButton(48011, "Trading/SPRITE", 10, 72, 35, "Next Page", 0, 48012, 1);
//		addHoveredButton(48012, "Trading/SPRITE", 11, 72, 35, 48013); // 48045
//
//		addText(48046, "Prev Page", 0xff9933, true, true, -1, tda, 1);
//		addText(48047, "Next Page", 0xff9933, true, true, -1, tda, 1);

		addText(48014, "Quantity", tda, 0, 0xff981f, false, false);
		addText(48015, "Name", tda, 0, 0xff981f, false, false);
		addText(48016, "Price", tda, 0, 0xff981f, false, false);
		addText(48017, "Seller", tda, 0, 0xff981f, false, false);
		addText(48018, "Total Sold", tda, 0, 0xff981f, false, false);
		// addText(48019, "Title", tda, 2, 0xff981f, true, true);
		addText(48019, "Trading post", 0xff9933, true, true, -1, tda, 2); // 262, 20

		setBounds(48001, 9, 2, 0, Interface);
		setBounds(48002, 471, 12, 1, Interface); // Close
		setBounds(48003, 471, 12, 2, Interface);
		setBounds(48005, 19, 281, 3, Interface); // Go back
//		setBounds(48006, 19, 281, 4, Interface);
//		setBounds(48008, 331, 281, 5, Interface); // Modify
//		setBounds(48009, 331, 281, 6, Interface);
//		setBounds(48011, 413, 281, 7, Interface); // Refresh
//		setBounds(48012, 413, 281, 8, Interface);

		setBounds(48014, 23, 42, 9, Interface); // Quantity
		setBounds(48015, 117, 42, 10, Interface); // Name
		setBounds(48016, 217, 42, 11, Interface); // Price
		setBounds(48017, 317, 42, 12, Interface); // Seller
		setBounds(48018, 414, 42, 13, Interface); // Total sold
		setBounds(48019, 262, 12, 14, Interface); // Titel
		setBounds(48020, 21, 50, 15, Interface); // Scroll

//		setBounds(48046, 366, 288, 16, Interface); // Prev page
//		setBounds(48047, 449, 288, 17, Interface); // Next Page

		setChildren(1001, main);

		int childId = 26022;
		int frame = 0;

		addToItemGroup(childId, 1, 250, 0, 7, true, "Buy 1", "Buy 5", "Buy 10", "Buy All", "Buy X", "View History");
		setBounds(childId++, 5, 10, frame++, main); // Item

		for (int i = 0; i < 250; i++) {
			addText(childId, "", tda, 0, 0xff981f, true, false);
			setBounds(childId++, 109, 20 + (i * 39), frame++, main); // Name

			addText(childId, "", tda, 0, 0xff981f, true, false);
			setBounds(childId++, 210, 20 + (i * 39), frame++, main); // Amount

			addText(childId, "", tda, 0, 0xff981f, true, false);
			setBounds(childId++, 310, 20 + (i * 39), frame++, main); // Seller

			addText(childId, "", tda, 0, 0xff981f, true, false);
			setBounds(childId++, 418, 20 + (i * 39), frame++, main); // Buy
		}


		main.width = 445;
		main.height = 227;
		main.scrollMax = 39 * 250;
	}

	public static void addListing(int index, boolean search) {
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[10];
		rsi.spritesX = new int[20];
		rsi.inventoryAmounts = new int[30];
		rsi.inventoryItemId = new int[30];
		rsi.spritesY = new int[20];

		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];
		if (!search)
			rsi.actions[0] = "Cancel";
		else {
			rsi.actions[0] = "Buy 1";
			rsi.actions[1] = "Buy 5";
			rsi.actions[2] = "Buy 10";
			rsi.actions[3] = "Buy All";
			rsi.actions[4] = "Buy X";
		}
		rsi.centerText = true;
		rsi.filled = false;
		rsi.aBoolean235 = false;
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.aBoolean259 = true;
		rsi.textShadow = false; // 42, 82
		if (!search) {
			rsi.invSpritePadX = 0;
			rsi.invSpritePadY = 15;
		} else {
			rsi.invSpritePadX = 0;
			rsi.invSpritePadY = 5;
		}
		if (!search)
			rsi.height = 15;
		else
			rsi.height = 8;
		rsi.width = 1;
		rsi.parentID = index + 1;
		rsi.id = index;
		rsi.type = 2;
	}

//	public static void groundItemCustomizing(TextDrawingArea[] tda) {
//		RSInterface r = addInterface(37700);
//		addSprite(37701, 2, "Interfaces/GroundItems/IMAGE");// they got saved as jpgs, need 2 convert lol il plz do
//		addText(37702, "Ground items customizer", tda, 2, 0xFE9624, true, true);
//		addHoverButton(37703, "Interfaces/GroundItems/IMAGE", 0, 16, 16, "Close", -1, 37704, 3);
//		addHoveredButton(37704, "Interfaces/GroundItems/IMAGE", 1, 16, 16, 37705);
//		addButton(37706, 4, "Interfaces/GroundItems/IMAGE", "Choose color");
//		addText(37707, "Choose a color below!", tda, 2, 0xffffff, true, true);
//		addButton(37708, 3, "Interfaces/GroundItems/IMAGE", "Enter Item Name");
//		addText(37709, "Set Color To Item", tda, 0, 0xFE9624, true, true);
//		addButton(37710, 3, "Interfaces/GroundItems/IMAGE", "Enter Minimum Item Value");
//		addText(37711, "Enter Min. Value", tda, 0, 0xFE9624, true, true);
//		addButton(37712, 3, "Interfaces/GroundItems/IMAGE", "Reset All Item Colors");
//		addText(37713, "Reset All Colors", tda, 0, 0xFE9624, true, true);
//		r.totalChildren(12);
//		r.child(0, 37701, 130, 75);// opn skype need 2 send u some imgs
//		r.child(1, 37702, 245, 85);
//		r.child(2, 37703, 334, 85);
//		r.child(3, 37704, 334, 85);
//		r.child(4, 37706, 137, 215);
//		r.child(5, 37707, 240, 195);
//		r.child(6, 37708, 150, 120);
//		r.child(7, 37709, 192, 125);
//		r.child(8, 37710, 250, 120);
//		r.child(9, 37711, 292, 125);
//		r.child(10, 37712, 150, 150);
//		r.child(11, 37713, 192, 155);
//	}

	public static void helpDatabaseComponent(TextDrawingArea[] tda) {
		RSInterface widget = addInterface(59550);
		addSprite(59551, 8, "Interfaces/HelpInterface/IMAGE");
		addHoverButton(59552, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, 59553, 3);
		addHoveredButton(59553, "Interfaces/HelpInterface/IMAGE", 3, 16, 16, 59554);
		addText(59555, "Bug Database", tda, 2, 0xFF981F, true, true);
		addText(59556, "Username/Date", tda, 1, 0xFF981F, false, true);
		addText(59557, "Line2", tda, 1, 0xFF981F, true, true);
		addText(59558, "Line3", tda, 1, 0xFF981F, true, true);
		addText(59559, "Line4", tda, 1, 0xFF981F, true, true);
		addText(59560, "Line5", tda, 1, 0xFF981F, true, true);

		setChildren(10, widget);
		setBounds(59551, 0, 2, 0, widget);
		setBounds(59552, 375, 8, 1, widget);
		setBounds(59553, 375, 8, 2, widget);
		setBounds(59570, 120, 30, 3, widget);
		setBounds(59555, 256, 8, 4, widget);
		setBounds(59556, 20, 225, 5, widget);
		setBounds(59557, 256, 245, 6, widget);
		setBounds(59558, 256, 265, 7, widget);
		setBounds(59559, 256, 285, 8, widget);
		setBounds(59560, 256, 305, 9, widget);

		RSInterface scroll = addInterface(59570);
		scroll.scrollMax = 400;
		scroll.width = 255;
		scroll.height = 170;
		setChildren(60, scroll);

		int yPosition = 0;
		int index = 0;
		for (int i = 0; i < 80; i += 4) {
			addText(59573 + i, "", tda, 1, 0xFF981F, false, true);
			addButton(59574 + i, 10, "Interfaces/HelpInterface/IMAGE", 13, 10, "View Request", 1);
			addButton(59575 + i, 9, "Interfaces/HelpInterface/IMAGE", 16, 15, "Remove Request", 1);
			setBounds(59573 + i, 4, yPosition + 3, index++, scroll);
			setBounds(59574 + i, 225, yPosition + 5, index++, scroll);
			setBounds(59575 + i, 240, yPosition + 3, index++, scroll);
			yPosition += 20;
		}
	}

	private static void barrowsKillcount(TextDrawingArea[] tda) {
		RSInterface barrow = addInterface(27500);
		addText(27501, "Brothers", tda, 2, 0xFD851A, true, true);
		addText(27502, "Ahrim", tda, 0, 0xFD851A, true, true);
		addText(27503, "Dharok", tda, 0, 0xFD851A, true, true);
		addText(27504, "Guthan", tda, 0, 0xFD851A, true, true);
		addText(27505, "Karil", tda, 0, 0xFD851A, true, true);
		addText(27506, "Torag", tda, 0, 0xFD851A, true, true);
		addText(27507, "Verac", tda, 0, 0xFD851A, true, true);
		addText(27508, "Killcount", tda, 2, 0xFD851A, true, true);
		addText(27509, "0", tda, 0, 0xFD851A, true, true);
		setChildren(9, barrow);
		setBounds(27501, 470, 42, 0, barrow);
		for (int index = 1; index < 7; index++)
			setBounds(27501 + index, 470, 45 + index * 14, index, barrow);
		setBounds(27508, 470, 15, 7, barrow);
		setBounds(27509, 470, 30, 8, barrow);
	}

	public static void achievements(TextDrawingArea[] tda) {
		RSInterface rsi = addInterface(49000);
		addSprite(49001, 1, "Interfaces/Achievements/IMAGE");
		addHoverButton(49002, "Interfaces/Achievements/IMAGE", 15, 16, 16, "Close Window", -1, 49003, 3);
		addHoveredButton(49003, "Interfaces/Achievements/IMAGE", 16, 16, 16, 49004);
		addConfigButton(49005, 49000, 12, 20, "Interfaces/Achievements/IMAGE", 71, 29, "Tier Tier I", 1, 1, 800);
		addConfigButton(49006, 49000, 13, 20, "Interfaces/Achievements/IMAGE", 71, 29, "Tier Tier II", 1, 1, 801);
		addConfigButton(49007, 49000, 14, 20, "Interfaces/Achievements/IMAGE", 71, 29, "View Tier III", 1, 1, 802);
		addSprite(49014, 11, "Interfaces/Achievements/IMAGE");
		addText(49016, "1000", tda, 0, 0xff981f, true, true);
		addText(49017, "Tier I", tda, 0, 0xff981f, false, true);
		addText(49018, "Tier II", tda, 0, 0xff981f, false, true);
		addText(49019, "Tier III", tda, 0, 0xff981f, false, true);
		addText(49020, "100", tda, 0, 0xff981f, false, true);

		setChildren(14, rsi);
		setBounds(49001, 0, 0, 0, rsi);
		setBounds(49002, 490, 6, 1, rsi);
		setBounds(49003, 490, 6, 2, rsi);

		setBounds(49005, 15, 10, 3, rsi);
		setBounds(49006, 90, 10, 4, rsi);
		setBounds(49007, 165, 10, 5, rsi);

		setBounds(49014, 415, 14, 6, rsi);
		setBounds(49016, 443, 19, 7, rsi);
		setBounds(49017, 37, 19, 8, rsi);
		setBounds(49018, 111, 19, 9, rsi);
		setBounds(49019, 184, 19, 10, rsi);

		setBounds(49100, 3, 48, 11, rsi);
		setBounds(51100, 3, 48, 12, rsi);
		setBounds(53100, 3, 48, 13, rsi);

		RSInterface scroll = addInterface(49100);
		setChildren(800, scroll);
		scroll.scrollMax = 6502;
		scroll.height = 281;
		scroll.width = 486;
		int y = 0;
		for (int i = 0; i < 100; i++) {
			addSprite(49101 + i, 10, "Interfaces/Achievements/IMAGE");
			addSprite(49201 + i, 2, "Interfaces/Achievements/IMAGE");
			addSprite(49301 + i, 5, "Interfaces/Achievements/IMAGE");
			addText(49401 + i, "", tda, 2, 0xFFFFFF, true, true);
			addText(49501 + i, "", tda, 2, 0xFFFFFF, false, true);
			addText(49601 + i, "", tda, 2, 0x425619, false, true);
			addSprite(49701 + i, 6, "Interfaces/Achievements/IMAGE");
			addText(49801 + i, "0/1", tda, 1, 0xFFFFFF, true, true);
			setBounds(49101 + i, 1, y, i, scroll);
			setBounds(49201 + i, 8, y + 5, 100 + i, scroll);
			setBounds(49301 + i, 430, y + 12, 200 + i, scroll);
			setBounds(49401 + i, 448, y + 24, 300 + i, scroll);
			setBounds(49501 + i, 65, y + 9, 400 + i, scroll);
			setBounds(49601 + i, 65, y + 24, 500 + i, scroll);
			setBounds(49701 + i, 65, y + 41, 600 + i, scroll);
			setBounds(49801 + i, 160, y + 43, 700 + i, scroll);
			y += 65;
		}
		RSInterface tier2 = addInterface(51100);
		setChildren(800, tier2);
		tier2.scrollMax = 6502;
		tier2.height = 281;
		tier2.width = 486;
		y = 0;
		for (int i = 0; i < 100; i++) {
			addSprite(51101 + i, 10, "Interfaces/Achievements/IMAGE");
			addSprite(51201 + i, 3, "Interfaces/Achievements/IMAGE");
			addSprite(51301 + i, 5, "Interfaces/Achievements/IMAGE");
			addText(51401 + i, "", tda, 2, 0xFFFFFF, true, true);
			addText(51501 + i, "", tda, 2, 0xFFFFFF, false, true);
			addText(51601 + i, "", tda, 2, 0x425619, false, true);
			addSprite(51701 + i, 6, "Interfaces/Achievements/IMAGE");
			addText(51801 + i, "0/1", tda, 1, 0xFFFFFF, true, true);
			setBounds(51101 + i, 1, y, i, tier2);
			setBounds(51201 + i, 8, y + 5, 100 + i, tier2);
			setBounds(51301 + i, 430, y + 12, 200 + i, tier2);
			setBounds(51401 + i, 448, y + 24, 300 + i, tier2);
			setBounds(51501 + i, 65, y + 9, 400 + i, tier2);
			setBounds(51601 + i, 65, y + 24, 500 + i, tier2);
			setBounds(51701 + i, 65, y + 41, 600 + i, tier2);
			setBounds(51801 + i, 160, y + 43, 700 + i, tier2);
			y += 65;
		}
		RSInterface tier3 = addInterface(53100);
		setChildren(800, tier3);
		tier3.scrollMax = 6502;
		tier3.height = 281;
		tier3.width = 486;
		y = 0;
		for (int i = 0; i < 100; i++) {
			addSprite(53101 + i, 10, "Interfaces/Achievements/IMAGE");
			addSprite(53201 + i, 4, "Interfaces/Achievements/IMAGE");
			addSprite(53301 + i, 5, "Interfaces/Achievements/IMAGE");
			addText(53401 + i, "", tda, 2, 0xFFFFFF, true, true);
			addText(53501 + i, "", tda, 2, 0xFFFFFF, false, true);
			addText(53601 + i, "", tda, 2, 0x425619, false, true);
			addSprite(53701 + i, 6, "Interfaces/Achievements/IMAGE");
			addText(53801 + i, "0/1", tda, 1, 0xFFFFFF, true, true);
			setBounds(53101 + i, 1, y, i, tier3);
			setBounds(53201 + i, 8, y + 5, 100 + i, tier3);
			setBounds(53301 + i, 430, y + 12, 200 + i, tier3);
			setBounds(53401 + i, 448, y + 24, 300 + i, tier3);
			setBounds(53501 + i, 65, y + 9, 400 + i, tier3);
			setBounds(53601 + i, 65, y + 24, 500 + i, tier3);
			setBounds(53701 + i, 65, y + 41, 600 + i, tier3);
			setBounds(53801 + i, 160, y + 43, 700 + i, tier3);
			y += 65;
		}
	}

	private static final void addGodwarsWidget(TextDrawingArea[] tda) {
		RSInterface godwars = addInterface(16210);
		setChildren(11, godwars);
		addText(16211, "NPC Killcount", tda, 0, 0xFD851A, false, true);
		addText(16212, "Armadyl", tda, 0, 0xFD851A, false, true);
		addText(16213, "Bandos", tda, 0, 0xFD851A, false, true);
		addText(16214, "Saradomin", tda, 0, 0xFD851A, false, true);
		addText(16215, "Zamorak", tda, 0, 0xFD851A, false, true);
		addText(16216, "Zaros", tda, 0, 0xFD851A, false, true);
		addText(16218, "0", tda, 0, 0x66FFFF, false, true);
		addText(16219, "0", tda, 0, 0x66FFFF, false, true);
		addText(16220, "0", tda, 0, 0x66FFFF, false, true);
		addText(16221, "0", tda, 0, 0x66FFFF, false, true);
		addText(16222, "0", tda, 0, 0x66FFFF, false, true);
		setBounds(16211, 400, 26, 0, godwars);
		for (int index = 1; index <= 5; index++) {
			setBounds(16211 + index, 400, 26 + index * 15, index, godwars);
		}
		for (int index = 1; index <= 5; index++) {
			setBounds(16217 + index, 480, 26 + index * 15, index + 5, godwars);
		}
	}

	private static void teleportInterface(TextDrawingArea[] textDrawingAreas) {

		int interfaceId = 39700;//checkinterface
		int child = 0;
		RSInterface interfaces = RSInterface.addInterface(interfaceId);
		interfaceId++;
		RSInterface.setChildren(33, interfaces);
		int xOffset = 80;
		int yOffset = 40;

		RSInterface.addSprite(interfaceId, 609, "Interfaces/Presets/SPRITE"); // Background.
		RSInterface.setBounds(interfaceId, 9 + xOffset, 21 + yOffset, child, interfaces);
		interfaceId++;
		child++;

		RSInterface.addText(interfaceId, "Teleports", textDrawingAreas, 2, 0xff981f, false);
		RSInterface.setBounds(interfaceId, 150 + xOffset, 30 + yOffset, child, interfaces);
		interfaceId++;
		child++;

		Object[][] teleportSelections = {
				{"Monsters", 1616},
				{"Bosses", 1617},
				{"Wilderness", 1613},
				{"Skilling", 1614},
				{"Minigames", 1618},
				{"Cities", 1615},
				{"Donator", 1619},
		};

		int yExtra = -1, count = 0;
		for (int index = 0; index < 7; index++) {
			String name = (String) teleportSelections[index][0];
			int buttonId = (int) teleportSelections[index][1];
			RSInterface.addHoverButtonLatest("Interfaces/Presets/SPRITE", interfaceId, interfaceId + 1, interfaceId + 2, count % 2 == 0 ? 614 : 614,
					610, 89, 26, "Choose");
			count++;
			RSInterface.addSprite(interfaceId, 612, "Interfaces/Presets/SPRITE");
			RSInterface.setBounds(interfaceId, 16 + xOffset, 57 + yOffset + yExtra, child, interfaces);
			child++;
			RSInterface.setBounds(interfaceId + 1, 16 + xOffset, 56 + yOffset + yExtra, child, interfaces);
			child++;
			interfaceId += 3;
			RSInterface.addClickableText(interfaceId, name, "Choose", textDrawingAreas, 1, 0xff981f, false, false, 60, 16);
			RSInterface.setBounds(interfaceId, 40 + xOffset, 61 + yOffset + yExtra, child, interfaces);
			interfaceId++;
			child++;
			Sprite selectionIcon = new Sprite("Interfaces/teleport_interface/SPRITE " + buttonId);
			RSInterface.addSprite(interfaceId, selectionIcon);
			RSInterface.setBounds(interfaceId, 18 + xOffset - (selectionIcon.myWidth / 2) + 10,
					59 + yOffset + yExtra - (selectionIcon.myHeight / 2) + 9, child, interfaces);
			interfaceId++;
			child++;
			yExtra += 25;
		}
		yExtra = 0;
		interfaceId += 5000;
		//RSInterface.addHoverButtonLatest("Interfaces/Presets/SPRITE",interfaceId, interfaceId + 1, interfaceId + 2, 148, 149, 15, 15,
		//		"Close Window");
		RSInterface.setBounds(CLOSE_BUTTON_SMALL, 317 + xOffset, 30 + yOffset, child, interfaces);
		RSInterface.setBounds(CLOSE_BUTTON_SMALL_HOVER, 317 + xOffset, 30 + yOffset, child + 1, interfaces);
		child += 2;
		interfaceId += 3;

		RSInterface scrollTab1 = RSInterface.addInterface(interfaceId);
		RSInterface.setBounds(interfaceId, 111 + xOffset, 56 + yOffset, child, interfaces); // scrollTab1
		interfaceId++;
		child++;

		int teleportAmount = 40;
		scrollTab1.width = 209;
		scrollTab1.height = 174;
		scrollTab1.scrollMax = 625;
		scrollTab1.totalChildren(teleportAmount * 3);
		int increaseY = 0, childNew = 0;
		for (int i = 0; i < teleportAmount; i++) {

			RSInterface.addHoverButtonLatest("Interfaces/Presets/SPRITE", interfaceId, interfaceId + 1, interfaceId + 2, count % 2 == 0 ? 613 : 967,
					611, 224, 26, "Teleport");
			count++;
			scrollTab1.child(childNew, interfaceId, 0, increaseY);
			childNew++;
			scrollTab1.child(childNew, interfaceId + 1, 0, increaseY);
			childNew++;
			interfaceId += 3;

			RSInterface.addText(interfaceId, "" + interfaceId, textDrawingAreas, 1, 0xff981f, true);
			scrollTab1.child(childNew, interfaceId, 104, increaseY + 4);
			increaseY += 25;
			interfaceId++;
			childNew++;
		}

		interfaces.setNewButtonClicking();
		scrollTab1.setNewButtonClicking();
	}

	public static void keybindingDropdown(int id, int width, int defaultOption, String[] options, MenuItem menuItem,
										  boolean inverted) {
		RSInterface widget = addInterface(id);
		widget.type = TYPE_KEYBINDS_DROPDOWN;
		widget.dropdown = new DropdownMenu(width, true, defaultOption, options, menuItem);
		widget.atActionType = AT_ACTION_TYPE_OPTION_DROPDOWN;
		widget.inverted = inverted;
	}

	public static void keybinding(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(53000);

		addSettingsSprite(53001, 63);
		addText(53002, "Keybinding", tda, 2, 0xff8a1f, false, true);
		closeButton(53003, 83, 84);

		hoverButton(Keybinding.RESTORE_DEFAULT, "Restore Defaults", 80, 81, "Restore Defaults", rsFont, 0xff8a1f,
				0xff8a1f, true);

		addText(53005, "Esc closes current interface", tda, 1, 0xff8a1f, false, true);
		configButton(Keybinding.ESCAPE_CONFIG, "Select", 62, 61);

		tab.totalChildren(48);
		int childNum = 0;

		setBounds(53001, 5, 17, childNum++, tab);
		setBounds(53002, 221, 27, childNum++, tab);
		setBounds(39021, 479, 24, childNum++, tab);
		setBounds(Keybinding.RESTORE_DEFAULT, 343, 275, childNum++, tab);
		setBounds(53005, 59, 284, childNum++, tab);
		setBounds(Keybinding.ESCAPE_CONFIG, 35, 285, childNum++, tab);

		/* Tabs and dropdowns */

		int x = 31;
		int y = 63;
		childNum = 47;

		for (int i = 0; i < 14; i++, y += 43) {

			addSettingsSprite(53007 + 3 * i, 64 + i);
			configButton(53008 + 3 * i, "", 79, 78);

			boolean inverted = i == 3 || i == 4 || i == 8 || i == 9 || i == 13;
			keybindingDropdown(53009 + 3 * i, 86, 0, Keybinding.OPTIONS, new KeybindingMenu(), inverted);

			setBounds(Keybinding.MIN_FRAME - 2 + 3 * i, x + stoneOffset(64 + i, true), y + stoneOffset(64 + i, false),
					childNum--, tab);
			setBounds(Keybinding.MIN_FRAME - 1 + 3 * i, x, y, childNum--, tab);
			setBounds(Keybinding.MIN_FRAME + 3 * i, x + 39, y + 4, childNum--, tab);

			if (i == 4 || i == 9) {
				x += 160;
				y = 20;
			}
		}
	}

	public static int stoneOffset(int spriteId, boolean xOffset) {
		Sprite stone = Client.cacheSprite3[79];
		Sprite icon = Client.cacheSprite3[spriteId];

		if (xOffset) {
			return (stone.myWidth / 2) - icon.myWidth / 2;
		}
		return (stone.myHeight / 2) - icon.myHeight / 2;
	}

	public static void clanChatSetup(TextDrawingArea[] tda) {
		RSInterface rsi = addInterface(18300);
		rsi.totalChildren(12 + 15);
		int count = 0;
		/* Background */
		addSprite(18301, 1, "/Interfaces/Clan Chat/sprite");
		rsi.child(count++, 18301, 14, 18);
		/* Close button */
		addButton(18302, 0, "/Interfaces/Clan Chat/close", "Close");
		interfaceCache[18302].atActionType = 3;
		rsi.child(count++, 18302, 475, 26);
		/* Clan Setup title */
		addText(18303, "Clan Setup", tda, 2, 0xFF981F, true, true);
		rsi.child(count++, 18303, 256, 26);
		/* Setup buttons */
		String[] titles = {"Clan name:", "Who can enter chat?", "Who can talk on chat?", "Who can kick on chat?",
				"Who can ban on chat?"};
		String[] defaults = {"Chat Disabled", "Anyone", "Anyone", "Anyone", "Anyone"};
		String[] whoCan = {"Anyone", "Recruit", "Corporal", "Sergeant", "Lieutenant", "Captain", "General",
				"Only Me"};
		for (int index = 0, id = 18304, y = 50; index < titles.length; index++, id += 3, y += 40) {
			addButton(id, 2, "/Interfaces/Clan Chat/sprite", "");
			interfaceCache[id].atActionType = 0;
			if (index > 0) {
				interfaceCache[id].actions = whoCan;
			} else {
				interfaceCache[id].actions = new String[]{"Change title", "Delete clan"};
				;
			}
			addText(id + 1, titles[index], tda, 0, 0xFF981F, true, true);
			addText(id + 2, defaults[index], tda, 1, 0xFFFFFF, true, true);
			rsi.child(count++, id, 25, y);
			rsi.child(count++, id + 1, 100, y + 4);
			rsi.child(count++, id + 2, 100, y + 17);
		}
		/* Table */
		addSprite(18319, 5, "/Interfaces/Clan Chat/sprite");
		rsi.child(count++, 18319, 197, 70);
		/* Labels */
		int id = 18320;
		int y = 74;
		addText(id, "Ranked Members", tda, 2, 0xFF981F, false, true);
		rsi.child(count++, id++, 202, y);
		addText(id, "Banned Members", tda, 2, 0xFF981F, false, true);
		rsi.child(count++, id++, 339, y);
		/* Ranked members list */
		RSInterface list = addInterface(id++);
		int lines = 100;
		list.totalChildren(lines);
		String[] ranks = {"Demote", "Recruit", "Corporal", "Sergeant", "Lieutenant", "Captain", "General", "Owner"};
		list.childY[0] = 2;
		// System.out.println(id);
		for (int index = id; index < id + lines; index++) {
			addText(index, "", tda, 1, 0xffffff, false, true);
			interfaceCache[index].actions = ranks;
			list.children[index - id] = index;
			list.childX[index - id] = 2;
			list.childY[index - id] = (index - id > 0 ? list.childY[index - id - 1] + 14 : 0);
		}
		id += lines;
		list.width = 119;
		list.height = 210;
		list.scrollMax = 2000;
		rsi.child(count++, list.id, 199, 92);
		/* Banned members list */
		list = addInterface(id++);
		list.totalChildren(lines);
		list.childY[0] = 2;
		// System.out.println(id);
		for (int index = id; index < id + lines; index++) {
			if (index == 18470) {
				index++;
				id++;
			}
			addText(index, "", tda, 1, 0xffffff, false, true);
			interfaceCache[index].actions = new String[]{"Unban"};
			list.children[index - id] = index;
			list.childX[index - id] = 0;
			list.childY[index - id] = (index - id > 0 ? list.childY[index - id - 1] + 14 : 0);
		}
		id += lines;
		list.width = 119;
		list.height = 210;
		list.scrollMax = 2000;
		rsi.child(count++, list.id, 339, 92);
		/* Table info text */
		y = 47;
		addText(id, "You can manage both ranked and banned members here.", tda, 0, 0xFF981F, true, true);
		rsi.child(count++, id++, 337, y);
		addText(id, "Right click on a name to edit the member.", tda, 0, 0xFF981F, true, true);
		rsi.child(count++, id++, 337, y + 11);
		/* Add ranked member button */
		y = 75;
		addButton(id, 0, "/Interfaces/Clan Chat/plus", "Add ranked member");
		interfaceCache[id].atActionType = 5;
		rsi.child(count++, id++, 319, y);
		/* Add banned member button */
		addButton(id, 0, "/Interfaces/Clan Chat/plus", "Add banned member");
		interfaceCache[id].atActionType = 5;
		rsi.child(count++, id++, 459, y);

		/* Hovers */
		int[] clanSetup = {18302, 18304, 18307, 18310, 18313, 18316, 18526, 18527};
		String[] names = {"close", "sprite", "sprite", "sprite", "sprite", "sprite", "plus", "plus"};
		int[] ids = {1, 3, 3, 3, 3, 3, 1, 1};
		for (int index = 0; index < clanSetup.length; index++) {
			rsi = interfaceCache[clanSetup[index]];
			rsi.disabledHover = imageLoader(ids[index], "/Interfaces/Clan Chat/" + names[index]);
		}
	}

	public static void Pestpanel(TextDrawingArea[] tda) {
		RSInterface RSinterface = addTab(21119);

		addText(21120, "Next Departure:", 0xCCCBCB, false, true, 52, tda, 1);
		addText(21121, "Players Ready:", 0x5BD230, false, true, 52, tda, 1);
		addText(21122, "(Need 5 to 25 players)", 0xDED36A, false, true, 52, tda, 1);
		addText(21123, "Pest Points:", 0x99FFFF, false, true, 52, tda, 1);
		int last = 4;
		RSinterface.children = new int[last];
		RSinterface.childX = new int[last];
		RSinterface.childY = new int[last];

		setBounds(21120, 5, 5, 0, RSinterface);
		setBounds(21121, 5, 20, 1, RSinterface);
		setBounds(21122, 5, 35, 2, RSinterface);
		setBounds(21123, 5, 50, 3, RSinterface);

		RSInterface rsi = interfaceCache[6114] = new RSInterface();
		rsi.type = 4;
		rsi.width = 390;
		rsi.centerText = true;
	}

	public static void Wintertodt(TextDrawingArea[] tda) {
		RSInterface RSinterface = addTab(63000);
		setChildren(1, RSinterface);

		addText(63001, "Health Remaining : 100%", 0xfc1c1c, false, true, 52, tda, 2);

		setBounds(63001, 5, 300, 0, RSinterface);

	}

	public static void Pestpanel2(TextDrawingArea[] tda) {
		RSInterface RSinterface = addInterface(21100);
		addSprite(21101, 0, "Interfaces/Pest Control/PEST1");
		addSprite(21102, 1, "Interfaces/Pest Control/PEST1");
		addSprite(21103, 2, "Interfaces/Pest Control/PEST1");
		addSprite(21104, 3, "Interfaces/Pest Control/PEST1");
		addSprite(21105, 4, "Interfaces/Pest Control/PEST1");
		addSprite(21106, 5, "Interfaces/Pest Control/PEST1");
		addText(21107, "", 0xCC00CC, false, true, 52, tda, 1);
		addText(21108, "", 0x0000FF, false, true, 52, tda, 1);
		addText(21109, "", 0xFFFF44, false, true, 52, tda, 1);
		addText(21110, "", 0xCC0000, false, true, 52, tda, 1);
		addText(21111, "", 0x99FF33, false, true, 52, tda, 1);// w purp
		addText(21112, "", 0x99FF33, false, true, 52, tda, 1);// e blue
		addText(21113, "", 0x99FF33, false, true, 52, tda, 1);// se yel
		addText(21114, "", 0x99FF33, false, true, 52, tda, 1);// sw red
		addText(21115, "200", 0x99FF33, false, true, 52, tda, 1);// attacks
		addText(21116, "", 0x99FF33, false, true, 52, tda, 1);// knights hp
		addText(21117, "Time Remaining:", 0xFFFFFF, false, true, 52, tda, 0);
		addText(21118, "", 0xFFFFFF, false, true, 52, tda, 0);
		int last = 18;
		RSinterface.children = new int[last];
		RSinterface.childX = new int[last];
		RSinterface.childY = new int[last];
		setBounds(21101, 361, 27, 0, RSinterface);
		setBounds(21102, 396, 27, 1, RSinterface);
		setBounds(21103, 436, 27, 2, RSinterface);
		setBounds(21104, 474, 27, 3, RSinterface);
		setBounds(21105, 3, 21, 4, RSinterface);
		setBounds(21106, 3, 50, 5, RSinterface);
		setBounds(21107, 371, 60, 6, RSinterface);
		setBounds(21108, 409, 60, 7, RSinterface);
		setBounds(21109, 443, 60, 8, RSinterface);
		setBounds(21110, 479, 60, 9, RSinterface);
		setBounds(21111, 362, 14, 10, RSinterface);
		setBounds(21112, 398, 14, 11, RSinterface);
		setBounds(21113, 436, 14, 12, RSinterface);
		setBounds(21114, 475, 14, 13, RSinterface);
		setBounds(21115, 32, 32, 14, RSinterface);
		setBounds(21116, 32, 62, 15, RSinterface);
		setBounds(21117, 8, 88, 16, RSinterface);
		setBounds(21118, 87, 88, 17, RSinterface);
	}

	public static void godWars(TextDrawingArea[] tda) {
		RSInterface rsinterface = addTabInterface(16210);
		addText(16211, "NPC killcount", tda, 0, 0xff9040, true, true);
		addText(16212, "Armadyl kills", tda, 0, 0xff9040, true, true);
		addText(16213, "Bandos kills", tda, 0, 0xff9040, true, true);
		addText(16214, "Saradomin kills", tda, 0, 0xff9040, true, true);
		addText(16215, "Zamorak kills", tda, 0, 0xff9040, true, true);
		addText(16216, "0", tda, 0, 0x66FFFF, true, true);// armadyl
		addText(16217, "0", tda, 0, 0x66FFFF, true, true);// bandos
		addText(16218, "0", tda, 0, 0x66FFFF, true, true);// saradomin
		addText(16219, "0", tda, 0, 0x66FFFF, true, true);// zamorak
		rsinterface.scrollMax = 0;
		rsinterface.children = new int[9];
		rsinterface.childX = new int[9];
		rsinterface.childY = new int[9];
		rsinterface.children[0] = 16211;
		rsinterface.childX[0] = -52 + 375 + 30;
		rsinterface.childY[0] = 7;
		rsinterface.children[1] = 16212;
		rsinterface.childX[1] = -52 + 375 + 30;
		rsinterface.childY[1] = 30;
		rsinterface.children[2] = 16213;
		rsinterface.childX[2] = -52 + 375 + 30;
		rsinterface.childY[2] = 44;
		rsinterface.children[3] = 16214;
		rsinterface.childX[3] = -52 + 375 + 30;
		rsinterface.childY[3] = 58;
		rsinterface.children[4] = 16215;
		rsinterface.childX[4] = -52 + 375 + 30;
		rsinterface.childY[4] = 73;
		rsinterface.children[5] = 16216;
		rsinterface.childX[5] = -52 + 460 + 60;
		rsinterface.childY[5] = 31;
		rsinterface.children[6] = 16217;
		rsinterface.childX[6] = -52 + 460 + 60;
		rsinterface.childY[6] = 45;
		rsinterface.children[7] = 16218;
		rsinterface.childX[7] = -52 + 460 + 60;
		rsinterface.childY[7] = 59;
		rsinterface.children[8] = 16219;
		rsinterface.childX[8] = -52 + 460 + 60;
		rsinterface.childY[8] = 74;
	}

	public static final int BEGIN_READING_PRAYER_INTERFACE = 6;// Amount of total custom prayers we've added
	public static final int CUSTOM_PRAYER_HOVERS = 3; // Amount of custom prayer hovers we've added

	public static final int TYPE_SPRITE = 5;

	public static void addPrayerHover(TextDrawingArea[] tda, int idx, int ID, String hover, int xOffset, int yOffset) {
		// Adding hover box
		RSInterface p = addTabInterface(ID);
		p.inventoryhover = true;
		p.parentID = 5608;
		p.type = 8;
		p.width = 40;
		p.height = 32;
		p.hoverText = p.message = hover;
		p.textDrawingAreas = tda[idx];
		p.hoverXOffset = xOffset;
		p.hoverYOffset = yOffset;
		p.regularHoverBox = true;
	}

	public static void prayerBook(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(15608);
		tab.totalChildren(10);
		RSInterface prayerBook = interfaceCache[5608];

		// Switches the Chivalry & Piety positions.
		/** Chivalry **/
		int xMinus = 1;
		int minusX = 37;
		prayerBook.childX[50] = 41 - minusX;
		prayerBook.childY[50] = 195 - xMinus;
		prayerBook.childX[51] = 48 - minusX;
		prayerBook.childY[51] = 197 - xMinus;
		prayerBook.childX[63] = 41 - minusX;
		prayerBook.childY[63] = 200 - xMinus;

		/** Piety **/
		prayerBook.childX[52] = 41;
		prayerBook.childY[52] = 195 - xMinus;
		prayerBook.childX[53] = 43;
		prayerBook.childY[53] = 206 - xMinus;
		prayerBook.childX[64] = 48;
		prayerBook.childY[64] = 197 - xMinus;

		prayerBook.childX[32] = 80;
		prayerBook.childY[32] = 124;
		interfaceCache[prayerBook.children[32]].width = 30;
		interfaceCache[prayerBook.children[32]].height = 30;

		addPrayer(39401, 0, 708, 55, 26, "Preserve");
		addPrayer(39404, 0, 710, 74, 27, "Rigour");
		addPrayer(39407, 0, 712, 77, 28, "Augury");
		addPrayerHover(tda, 1, 39409,
				"Level 77\\nAugury\\nIncreases your Magic attack\\nby 25% and your defence by\\n25%", -110, -100);
		addPrayerHover(tda, 1, 39403, "Level 55\\nPreserve\\nBoosted stats last 20%\nlonger.", -135, -60);
		addPrayerHover(tda, 1, 39406,
				"Level 74\\nRigour\\nIncreases your Ranged attack\\nby 20% and damage by 23%,\\nand your defence by 25%",
				-70, -100);
		setBounds(39401, 152, 158 - xMinus, 0, tab);
		setBounds(39404, 78, 195 - xMinus, 1, tab);
		setBounds(39407, 115, 195 - xMinus, 2, tab);
		setBounds(39402, 154, 158 - xMinus, 3, tab);
		setBounds(39405, 81, 198 - xMinus, 4, tab);
		setBounds(39408, 118, 198 - xMinus, 5, tab);
		setBounds(5608, 0, 0 - xMinus, 6, tab);
		setBounds(39403, 154, 158 - xMinus, 7, tab);
		setBounds(39406, 84, 198 - xMinus, 8, tab);
		setBounds(39409, 120, 198 - xMinus, 9, tab);
	}

	public static void ancients(TextDrawingArea[] tda) {
		RSInterface p = addTabInterface(838);
		RSInterface rsinterface = interfaceCache[12855];
		// rsinterface.childX[6] = 10;
		rsinterface.childY[22] = 153;
		rsinterface.childX[22] = 18;
		rsinterface.childY[30] = 153;
		rsinterface.childX[30] = 65;
		rsinterface.childY[44] = 153; // ANNAKARL
		rsinterface.childX[44] = 112;
		rsinterface.childY[46] = 180; // GHORROCK
		rsinterface.childX[46] = 65;
		rsinterface.childY[7] = 181; // ICE BARRAGE
		rsinterface.childX[7] = 18;
		rsinterface.childY[15] = 153; // BLOOD BARRAGE
		rsinterface.childX[15] = 152;

		addSpellBig2(34674, 563, 560, 562, 10, 10, 10, 30009, 30009, 30011, 85, "Teleport to Target",
				"Teleports you near your Bounty\\nHunter Target", tda, 9, 7, 5);
		setChildren(3, p);
		setBounds(12855, 0, 0, 0, p);
		setBounds(34674, 150, 123, 1, p);
		setBounds(34675, 5, -5, 2, p);
	}

	public static void ruinousPowerBook(TextDrawingArea[] tda) {
		final int INTERFACE_ID = 24633;
		int index = 1;

		RSInterface Interface = addTabInterface(INTERFACE_ID);
		addText(INTERFACE_ID + index++, "99/99", 0xFF981F, false, false, -1, tda, 1);
		addSprite(INTERFACE_ID + index++, 2, "interfaces/curses/DATA");

		String[] prayerNames = {
				"Rejuvenation", "Ancient Strength", "Ancient Sight", "Ancient Will",
				"Protect Item", "Ruinous Grace", "Dampen Magic", "Dampen Ranged",
				"Dampen Melee", "Trinitas", "Berserker", "Purge", "Metabolise",
				"Rebuke", "Vindication", "Decimate", "Annihilate", "Vaporise",
				"Fumus' Vow", "Umbra's Vow", "Cruor's Vow", "Glacies' Vow",
				"Wrath", "Intensify"
		};

		int[] prayerIds = {
				710, 711, 712, 713, 730, 714, 715, 716, 717, 718, 719, 720,
				721, 722, 723, 724, 725, 726, 727, 728, 729, 731, 732, 733
		};

		for (int i = 0; i < prayerNames.length; i++) {
			addPrayer(INTERFACE_ID + index, 0, prayerIds[i], 60 + i, 49 + i, prayerNames[i]);
			index += 2; // Increment by 2 for the ID gap
			addPrayerHover(tda, 1, INTERFACE_ID + index++, "Level " + (50 + i) + "\\n" + prayerNames[i], -135, -60);
		}

		setChildren(75, Interface);

		// TODO Alter the XY & The Overlapping issue for Prayer Hover.
// Row 1
		setBounds(INTERFACE_ID + 3, -2, -2, 0, Interface);
		setBounds(INTERFACE_ID + 4, 0, 0, 1, Interface);
		setBounds(INTERFACE_ID + 5, 0, 0, 2, Interface);
		setBounds(INTERFACE_ID + 6, 38 - 2, -2, 3, Interface);
		setBounds(INTERFACE_ID + 7, 38, 0, 4, Interface);
		setBounds(INTERFACE_ID + 8, 38, 0, 5, Interface);
		setBounds(INTERFACE_ID + 9, 76 - 2, -2, 6, Interface);
		setBounds(INTERFACE_ID + 10, 76, 0, 7, Interface);
		setBounds(INTERFACE_ID + 11, 76, 0, 8, Interface);
		setBounds(INTERFACE_ID + 12, 114 - 2, -2, 9, Interface);
		setBounds(INTERFACE_ID + 13, 114, 0, 10, Interface);
		setBounds(INTERFACE_ID + 14, 114, 0, 11, Interface);
		setBounds(INTERFACE_ID + 15, 152 - 2, -2, 12, Interface);
		setBounds(INTERFACE_ID + 16, 152, 0, 13, Interface);
		setBounds(INTERFACE_ID + 17, 152, 0, 14, Interface);

// Row 2
		setBounds(INTERFACE_ID + 18, -2, 40 - 2, 15, Interface);
		setBounds(INTERFACE_ID + 19, 0, 40, 16, Interface);
		setBounds(INTERFACE_ID + 20, 0, 40, 17, Interface);
		setBounds(INTERFACE_ID + 21, 38 - 2, 40 - 2, 18, Interface);
		setBounds(INTERFACE_ID + 22, 38, 40, 19, Interface);
		setBounds(INTERFACE_ID + 23, 38, 40, 20, Interface);
		setBounds(INTERFACE_ID + 24, 76 - 2, 40 - 2, 21, Interface);
		setBounds(INTERFACE_ID + 25, 76, 40, 22, Interface);
		setBounds(INTERFACE_ID + 26, 76, 40, 23, Interface);
		setBounds(INTERFACE_ID + 27, 114 - 2, 40 - 2, 24, Interface);
		setBounds(INTERFACE_ID + 28, 114, 40, 25, Interface);
		setBounds(INTERFACE_ID + 29, 114, 40, 26, Interface);
		setBounds(INTERFACE_ID + 30, 152 - 2, 40 - 2, 27, Interface);
		setBounds(INTERFACE_ID + 31, 152, 40, 28, Interface);
		setBounds(INTERFACE_ID + 32, 152, 40, 29, Interface);

// Row 3
		setBounds(INTERFACE_ID + 33, -2, 80 - 2, 30, Interface);
		setBounds(INTERFACE_ID + 34, 0, 80, 31, Interface);
		setBounds(INTERFACE_ID + 35, 0, 80, 32, Interface);
		setBounds(INTERFACE_ID + 36, 38 - 2, 80 - 2, 33, Interface);
		setBounds(INTERFACE_ID + 37, 38, 80, 34, Interface);
		setBounds(INTERFACE_ID + 38, 38, 80, 35, Interface);
		setBounds(INTERFACE_ID + 39, 76 - 2, 80 - 2, 36, Interface);
		setBounds(INTERFACE_ID + 40, 76, 80, 37, Interface);
		setBounds(INTERFACE_ID + 41, 76, 80, 38, Interface);
		setBounds(INTERFACE_ID + 42, 114 - 2, 80 - 2, 39, Interface);
		setBounds(INTERFACE_ID + 43, 114, 80, 40, Interface);
		setBounds(INTERFACE_ID + 44, 114, 80, 41, Interface);
		setBounds(INTERFACE_ID + 45, 152 - 2, 80 - 2, 42, Interface);
		setBounds(INTERFACE_ID + 46, 152, 80, 43, Interface);
		setBounds(INTERFACE_ID + 47, 152, 80, 44, Interface);

// Row 4
		setBounds(INTERFACE_ID + 48, -2, 120 - 2, 45, Interface);
		setBounds(INTERFACE_ID + 49, 0, 120, 46, Interface);
		setBounds(INTERFACE_ID + 50, 0, 120, 47, Interface);
		setBounds(INTERFACE_ID + 51, 38 - 2, 120 - 2, 48, Interface);
		setBounds(INTERFACE_ID + 52, 38, 120, 49, Interface);
		setBounds(INTERFACE_ID + 53, 38, 120, 50, Interface);
		setBounds(INTERFACE_ID + 54, 76 - 2, 120 - 2, 51, Interface);
		setBounds(INTERFACE_ID + 55, 76, 120, 52, Interface);
		setBounds(INTERFACE_ID + 56, 76, 120, 53, Interface);
		setBounds(INTERFACE_ID + 57, 114 - 2, 120 - 2, 54, Interface);
		setBounds(INTERFACE_ID + 58, 114, 120, 55, Interface);
		setBounds(INTERFACE_ID + 59, 114, 120, 56, Interface);
		setBounds(INTERFACE_ID + 60, 152 - 2, 120 - 2, 57, Interface);
		setBounds(INTERFACE_ID + 61, 152, 120, 58, Interface);
		setBounds(INTERFACE_ID + 62, 152, 120, 59, Interface);

// Row 5
		setBounds(INTERFACE_ID + 63, -2, 160 - 2, 60, Interface);
		setBounds(INTERFACE_ID + 64, 0, 160, 61, Interface);
		setBounds(INTERFACE_ID + 65, 0, 160, 62, Interface);
		setBounds(INTERFACE_ID + 66, 38 - 2, 160 - 2, 63, Interface);
		setBounds(INTERFACE_ID + 67, 38, 160, 64, Interface);
		setBounds(INTERFACE_ID + 68, 38, 160, 65, Interface);
		setBounds(INTERFACE_ID + 69, 76 - 2, 160 - 2, 66, Interface);
		setBounds(INTERFACE_ID + 70, 76, 160, 67, Interface);
		setBounds(INTERFACE_ID + 71, 76, 160, 68, Interface);
		setBounds(INTERFACE_ID + 72, 114 - 2, 160 - 2, 69, Interface);
		setBounds(INTERFACE_ID + 73, 114, 160, 70, Interface);
		setBounds(INTERFACE_ID + 74, 114, 160, 71, Interface);

		setBounds(INTERFACE_ID + 1, 85, 241, 72, Interface);
		setBounds(INTERFACE_ID + 2, 65, 241, 73, Interface);

	}

	public static void configureLunar(TextDrawingArea[] TDA) {
		constructLunar();
		homeTeleport();
		drawRune(30003, 1, "Fire");
		drawRune(30004, 2, "Water");
		drawRune(30005, 3, "Air");
		drawRune(30006, 4, "Earth");
		drawRune(30007, 5, "Mind");
		drawRune(30008, 6, "Body");
		drawRune(30009, 7, "Death");
		drawRune(30010, 8, "Nature");
		drawRune(30011, 9, "Chaos");
		drawRune(30012, 10, "Law");
		drawRune(30013, 11, "Cosmic");
		drawRune(30014, 12, "Blood");
		drawRune(30015, 13, "Soul");
		drawRune(30016, 14, "Astral");
		addLunar3RunesSmallBox(30017, 9075, 554, 555, 0, 4, 3, 30003, 30004, 64, "Bake Pie",
				"Bake pies without a stove", TDA, 0, 16, 2);
		addLunar2RunesSmallBox(30025, 9075, 557, 0, 7, 30006, 65, "Cure Plant", "Cure disease on farming patch", TDA, 1,
				4, 2);
		addLunar3RunesBigBox(30032, 9075, 564, 558, 0, 0, 0, 30013, 30007, 65, "Monster Examine",
				"Detect the combat statistics of a\\nmonster", TDA, 2, 2, 2);
		addLunar3RunesSmallBox(30040, 9075, 564, 556, 0, 0, 1, 30013, 30005, 66, "NPC Contact",
				"Speak with varied NPCs", TDA, 3, 0, 2);
		addLunar3RunesSmallBox(30048, 9075, 563, 557, 0, 0, 9, 30012, 30006, 67, "Cure Other", "Cure poisoned players",
				TDA, 4, 8, 2);
		addLunar3RunesSmallBox(30056, 9075, 555, 554, 0, 2, 0, 30004, 30003, 67, "Humidify",
				"fills certain vessels with water", TDA, 5, 0, 5);
		addLunar3RunesSmallBox(30064, 9075, 563, 557, 1, 0, 1, 30012, 30006, 68, "Moonclan Teleport",
				"Teleports you to moonclan island", TDA, 6, 0, 5);
		addLunar3RunesBigBox(30075, 9075, 563, 557, 1, 0, 3, 30012, 30006, 69, "Tele Groun Moonclan",
				"Teleports players to Moonclan\\nisland", TDA, 7, 0, 5);
		addLunar3RunesSmallBox(30083, 9075, 563, 557, 1, 0, 5, 30012, 30006, 70, "Ourania Teleport",
				"Teleports you to ourania rune altar", TDA, 8, 0, 5);
		addLunar3RunesSmallBox(30091, 9075, 564, 563, 1, 1, 0, 30013, 30012, 70, "Cure Me", "Cures Poison", TDA, 9, 0,
				5);
		addLunar2RunesSmallBox(30099, 9075, 557, 1, 1, 30006, 70, "Hunter Kit", "Get a kit of hunting gear", TDA, 10, 0,
				5);
		addLunar3RunesSmallBox(30106, 9075, 563, 555, 1, 0, 0, 30012, 30004, 71, "Waterbirth Teleport",
				"Teleports you to Waterbirth island", TDA, 11, 0, 5);
		addLunar3RunesBigBox(30114, 9075, 563, 555, 1, 0, 4, 30012, 30004, 72, "Tele Group Waterbirth",
				"Teleports players to Waterbirth\\nisland", TDA, 12, 0, 5);
		addLunar3RunesSmallBox(30122, 9075, 564, 563, 1, 1, 1, 30013, 30012, 73, "Cure Group",
				"Cures Poison on players", TDA, 13, 0, 5);
		addLunar3RunesBigBox(30130, 9075, 564, 559, 1, 1, 4, 30013, 30008, 74, "Stat Spy",
				"Cast on another player to see their\\nskill levels", TDA, 14, 8, 2);
		addLunar3RunesBigBox(30138, 9075, 563, 554, 1, 1, 2, 30012, 30003, 74, "Barbarian Teleport",
				"Teleports you to the Barbarian\\noutpost", TDA, 15, 0, 5);
		addLunar3RunesBigBox(30146, 9075, 563, 554, 1, 1, 5, 30012, 30003, 75, "Tele Group Barbarian",
				"Teleports players to the Barbarian\\noutpost", TDA, 16, 0, 5);
		addLunar3RunesSmallBox(30154, 9075, 554, 556, 1, 5, 9, 30003, 30005, 76, "Superglass Make",
				"Make glass without a furnace", TDA, 17, 16, 2);
		addLunar3RunesSmallBox(30162, 9075, 563, 555, 1, 1, 3, 30012, 30004, 77, "Khazard Teleport",
				"Teleports you to Port khazard", TDA, 18, 0, 5);
		addLunar3RunesSmallBox(30170, 9075, 563, 555, 1, 1, 7, 30012, 30004, 78, "Tele Group Khazard",
				"Teleports players to Port khazard", TDA, 19, 0, 5);
		addLunar3RunesBigBox(30178, 9075, 564, 559, 1, 0, 4, 30013, 30008, 78, "Dream",
				"Take a rest and restore hitpoints 3\\n times faster", TDA, 20, 0, 5);
		addLunar3RunesSmallBox(30186, 9075, 557, 555, 1, 9, 4, 30006, 30004, 79, "String Jewellery",
				"String amulets without wool", TDA, 21, 0, 5);
		addLunar3RunesLargeBox(30194, 9075, 557, 555, 1, 9, 9, 30006, 30004, 80, "Stat Restore Pot\\nShare",
				"Share a potion with up to 4 nearby\\nplayers", TDA, 22, 0, 5);
		addLunar3RunesSmallBox(30202, 9075, 554, 555, 1, 6, 6, 30003, 30004, 81, "Magic Imbue",
				"Combine runes without a talisman", TDA, 23, 0, 5);
		addLunar3RunesBigBox(30210, 9075, 561, 557, 2, 1, 14, 30010, 30006, 82, "Fertile Soil",
				"Fertilise a farming patch with super\\ncompost", TDA, 24, 4, 2);
		addLunar3RunesBigBox(30218, 9075, 557, 555, 2, 11, 9, 30006, 30004, 83, "Boost Potion Share",
				"Shares a potion with up to 4 nearby\\nplayers", TDA, 25, 0, 5);
		addLunar3RunesSmallBox(30226, 9075, 563, 555, 2, 2, 9, 30012, 30004, 84, "Fishing Guild Teleport",
				"Teleports you to the fishing guild", TDA, 26, 0, 5);
		addSpellBig2(30234, 563, 560, 562, 1, 1, 1, 30009, 30012, 30004, 84, "Teleport to Target",
				"Teleports you near your Bounty\\nHunter Target", TDA, 9, 7, 5);
		addLunar3RunesSmallBox(30242, 9075, 557, 561, 2, 14, 0, 30006, 30010, 85, "Plank Make", "Turn Logs into planks",
				TDA, 28, 16, 5);
		/******** Cut Off Limit **********/
		addLunar3RunesSmallBox(30250, 9075, 563, 555, 2, 2, 9, 30012, 30004, 86, "Catherby Teleport",
				"Teleports you to Catherby", TDA, 29, 0, 5);
		addLunar3RunesSmallBox(30258, 9075, 563, 555, 2, 2, 14, 30012, 30004, 87, "Tele Group Catherby",
				"Teleports players to Catherby", TDA, 30, 0, 5);
		addLunar3RunesSmallBox(30266, 9075, 563, 555, 2, 2, 7, 30012, 30004, 88, "Ice Plateau Teleport",
				"Teleports you to Ice Plateau", TDA, 31, 0, 5);
		addLunar3RunesBigBox(30274, 9075, 563, 555, 2, 2, 15, 30012, 30004, 89, "Tele Group Ice\\n Plateau",
				"\\nTeleports players to Ice Plateau", TDA, 32, 0, 5);
		addLunar3RunesBigBox(30282, 9075, 563, 561, 2, 1, 0, 30012, 30010, 90, "Energy Transfer",
				"Spend hitpoints and Energy to\\n give another player \\nhitpoints and run energy", TDA, 33, 8, 2);
		addLunar3RunesBigBox(30290, 9075, 563, 565, 2, 2, 0, 30012, 30014, 91, "Heal Other",
				"Transfer up to 75% of hitpoints\\n to another player", TDA, 34, 8, 2);
		addLunar3RunesBigBox(30298, 9075, 560, 557, 2, 1, 9, 30009, 30006, 92, "Vengeance Other",
				"Allows another player to rebound\\ndamage to an opponent", TDA, 35, 8, 2);
		addLunar3RunesSmallBox(30306, 9075, 560, 557, 3, 1, 9, 30009, 30006, 93, "Vengeance",
				"Rebound damage to an opponent", TDA, 36, 0, 5);
		addLunar3RunesBigBox(30314, 9075, 565, 563, 3, 2, 5, 30014, 30012, 94, "Heal Group",
				"Transfer up to 75% of hitpoints to a group", TDA, 37, 0, 5);
		addLunar3RunesBigBox(30322, 9075, 564, 563, 2, 1, 0, 30013, 30012, 95, "Spellbook Swap",
				"Change to another spellbook for 1\\nspell cast", TDA, 38, 0, 5);
	}

	public static void constructLunar() {
		RSInterface Interface = addInterface(29999);
		setChildren(80, Interface); // 71
		int[] Cid = {30000, 30017, 30025, 30032, 30040, 30048, 30056, 30064, 30075, 30083, 30091, 30099, 30106, 30114,
				30122, 30130, 30138, 30146, 30154, 30162, 30170, 30178, 30186, 30194, 30202, 30210, 30218, 30226, 30234,
				30242, 30250, 30258, 30266, 30274, 30282, 30290, 30298, 30306, 30314, 30322, 30001, 30018, 30026, 30033,
				30041, 30049, 30057, 30065, 30076, 30084, 30092, 30100, 30107, 30115, 30123, 30131, 30139, 30147, 30155,
				30163, 30171, 30179, 30187, 30195, 30203, 30211, 30219, 30227, 30235, 30243, 30251, 30259, 30267, 30275,
				30283, 30291, 30299, 30307, 30315, 30323};

		int[] xCord = {11, 40, 71, 103, 135, 165, 8, 39, 71, 103, 135, 165, 12, 42, 71, 103, 135, 165, 14, 42, 71, 101,
				135, 168, 11, 42, 74, 103, 135, 164, 10, 42, 71, 103, 136, 165, 13, 42, 71, 104, 6, 5, 5, 5, 5, 5, 5, 5,
				5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};

		int[] yCord = {9, 9, 12, 10, 12, 10, 39, 39, 39, 39, 39, 37, 68, 68, 68, 68, 68, 68, 97, 97, 97, 97, 98, 98,
				125, 124, 125, 125, 125, 126, 155, 155, 155, 155, 155, 155, 185, 185, 184, 184, 184, 176, 176, 163, 176,
				176, 176, 176, 163, 176, 176, 176, 176, 163, 176, 163, 163, 163, 176, 176, 176, 163, 176, 149, 176, 163,
				163, 176, 149, 176, 176, 176, 176, 176, 9, 9, 9, 9, 9, 9};

		for (int i = 0; i < Cid.length; i++) {
			setBounds(Cid[i], xCord[i], yCord[i], i, Interface);
		}
	}

	public static void emoteTab() {
		RSInterface tab = addTabInterface(147);
		RSInterface scroll = addTabInterface(148);
		tab.totalChildren(1);
		tab.child(0, 148, -9, 1);
		addButton(168, 0, "Emotes/EMOTE", "Yes");
		addButton(169, 1, "Emotes/EMOTE", "No");
		addButton(164, 2, "Emotes/EMOTE", "Bow");
		addButton(165, 3, "Emotes/EMOTE", "Angry");
		addButton(162, 4, "Emotes/EMOTE", "Think");
		addButton(163, 5, "Emotes/EMOTE", "Wave");
		addButton(13370, 6, "Emotes/EMOTE", "Shrug");
		addButton(171, 7, "Emotes/EMOTE", "Cheer");
		addButton(167, 8, "Emotes/EMOTE", "Beckon");
		addButton(170, 9, "Emotes/EMOTE", "Laugh");
		addButton(13366, 10, "Emotes/EMOTE", "Jump for Joy");
		addButton(13368, 11, "Emotes/EMOTE", "Yawn");
		addButton(166, 12, "Emotes/EMOTE", "Dance");
		addButton(13363, 13, "Emotes/EMOTE", "Jig");
		addButton(13364, 14, "Emotes/EMOTE", "Spin");
		addButton(13365, 15, "Emotes/EMOTE", "Headbang");
		addButton(161, 16, "Emotes/EMOTE", "Cry");
		addButton(11100, 17, "Emotes/EMOTE", "Blow kiss");
		addButton(13362, 18, "Emotes/EMOTE", "Panic");
		addButton(13367, 19, "Emotes/EMOTE", "Raspberry");
		addButton(172, 20, "Emotes/EMOTE", "Clap");
		addButton(13369, 21, "Emotes/EMOTE", "Salute");
		addButton(13383, 22, "Emotes/EMOTE", "Goblin Bow");
		addButton(13384, 23, "Emotes/EMOTE", "Goblin Salute");
		addButton(667, 24, "Emotes/EMOTE", "Glass Box");
		addButton(6503, 25, "Emotes/EMOTE", "Climb Rope");
		addButton(6506, 26, "Emotes/EMOTE", "Lean On Air");
		addButton(666, 27, "Emotes/EMOTE", "Glass Wall");
		addButton(18464, 28, "Emotes/EMOTE", "Zombie Walk");
		addButton(18465, 29, "Emotes/EMOTE", "Zombie Dance");
		addButton(15166, 30, "Emotes/EMOTE", "Scared");
		addButton(18686, 31, "Emotes/EMOTE", "Rabbit Hop");
		addConfigButton(154, 147, 32, 33, "Emotes/EMOTE", 41, 47, "Skillcape Emote", 0, 1, 700);
		scroll.totalChildren(33);
		scroll.child(0, 168, 10, 7);
		scroll.child(1, 169, 54, 7);
		scroll.child(2, 164, 98, 7);
		scroll.child(3, 165, 137, 7);
		scroll.child(4, 162, 9, 56);
		scroll.child(5, 163, 48, 56);
		scroll.child(6, 13370, 95, 56);
		scroll.child(7, 171, 137, 56);
		scroll.child(8, 167, 7, 105);
		scroll.child(9, 170, 51, 105);
		scroll.child(10, 13366, 95, 104);
		scroll.child(11, 13368, 139, 105);
		scroll.child(12, 166, 6, 154);
		scroll.child(13, 13363, 50, 154);
		scroll.child(14, 13364, 90, 154);
		scroll.child(15, 13365, 135, 154);
		scroll.child(16, 161, 8, 204);
		scroll.child(17, 11100, 51, 203);
		scroll.child(18, 13362, 99, 204);
		scroll.child(19, 13367, 137, 203);
		scroll.child(20, 172, 10, 253);
		scroll.child(21, 13369, 53, 253);
		scroll.child(22, 13383, 88, 258);
		scroll.child(23, 13384, 138, 252);
		scroll.child(24, 667, 2, 303);
		scroll.child(25, 6503, 49, 302);
		scroll.child(26, 6506, 93, 302);
		scroll.child(27, 666, 137, 302);
		scroll.child(28, 18464, 9, 352);
		scroll.child(29, 18465, 50, 352);
		scroll.child(30, 15166, 94, 356);
		scroll.child(31, 18686, 141, 353);
		scroll.child(32, 154, 5, 401);
		scroll.width = 180;
		scroll.height = 258;
		scroll.scrollMax = 450;
	}

	public static void createInterface(int id) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
	}

	public static void addHoverButton_sprite_loader(int i, int spriteId, int width, int height, String text,
													int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.sprite1 = Client.cacheSprite1[spriteId];
		tab.sprite2 = Client.cacheSprite1[spriteId];
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredButton_sprite_loader(int i, int spriteId, int w, int h, int IMAGEID) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.isMouseoverTriggered = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		addHoverImage_sprite_loader(IMAGEID, spriteId);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addSprite3(int childId, int spriteId) {
		RSInterface rsi = interfaceCache[childId] = new RSInterface();
		rsi.id = childId;
		rsi.parentID = childId;
		rsi.type = 5;
		rsi.atActionType = 0;
		rsi.contentType = 0;
		rsi.sprite1 = Client.cacheSprite3[spriteId];
		rsi.sprite2 = Client.cacheSprite3[spriteId];
		rsi.width = rsi.sprite1.myWidth;
		rsi.height = rsi.sprite2.myHeight - 2;
	}

	public static void addButton(int id, int sid, String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = Client.cacheSprite1[sid];// imageLoader(sid, spriteName);
		tab.sprite2 = Client.cacheSprite1[sid];// imageLoader(sid, spriteName);
		tab.width = tab.sprite1.myWidth;
		tab.height = tab.sprite1.myHeight;
		tab.tooltip = tooltip;
	}

	public static void addHoverImage_sprite_loader(int i, int spriteId) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.sprite1 = Client.cacheSprite1[spriteId];
		tab.sprite2 = Client.cacheSprite1[spriteId];
	}

	public static void addHoverImage_sprite_loader3(int i, int spriteId) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.sprite1 = Client.cacheSprite3[spriteId];
		tab.sprite2 = Client.cacheSprite3[spriteId];
	}

	public static void addHoveredButton_sprite_loader3(int i, int spriteId, int w, int h, int IMAGEID) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.isMouseoverTriggered = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		addHoverImage_sprite_loader3(IMAGEID, spriteId);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoverButton_sprite_loader3(int i, int spriteId, int width, int height, String text,
													 int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.sprite1 = Client.cacheSprite3[spriteId];
		tab.sprite2 = Client.cacheSprite3[spriteId];
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void equipmentTab(TextDrawingArea[] wid) {
		RSInterface Interface = interfaceCache[1644];
		addSprite(15101, 0, "Interfaces/Equipment/bl");// cheap hax
		addSprite(15102, 1, "Interfaces/Equipment/bl");// cheap hax
		addSprite(15109, 2, "Interfaces/Equipment/bl");// cheap hax
		createInterface(21338);
		createInterface(21344);
		createInterface(21342);
		createInterface(21341);
		createInterface(21340);
		createInterface(15103);
		createInterface(15104);
		Interface.children[24] = 15102;
		Interface.childX[24] = 110;
		Interface.childY[24] = 205;
		Interface.children[25] = 15109;
		Interface.childX[25] = 39;
		Interface.childY[25] = 240;
		Interface.children[26] = 27650;
		Interface.childX[26] = 0;
		Interface.childY[26] = 0;
		Interface = addInterface(27650);

		addHoverButton(27651, "/Equipment/SPRITE", 6, 40, 40, "Show Cosmetics", 550, 27652, 5);
		addHoveredButton(27652, "/Equipment/SPRITE", 7, 40, 40, 27658);

		addHoverButton(27653, "/Equipment/SPRITE", 8, 40, 40, "Show Equipment Stats", 550, 27655, 5);
		addHoveredButton(27655, "/Equipment/SPRITE", 9, 40, 40, 27665);

		addHoverButton(27654, "/Equipment/SPRITE", 10, 40, 40, "Show items kept on death", -1, 27657, 1);
		addHoveredButton(27657, "/Equipment/SPRITE", 11, 40, 40, 27666);

		addHoverButton(27660, "/Equipment/SPRITE", 12, 40, 40, "Call follower", -1, 27661, 1);
		addHoveredButton(27661, "/Equipment/SPRITE", 13, 40, 40, 27662);

		setChildren(8, Interface);
		setBounds(27651, 53, 205, 0, Interface);
		setBounds(27652, 53, 205, 1, Interface);
		setBounds(27653, 8, 205, 2, Interface);
		setBounds(27654, 98, 205, 3, Interface);
		setBounds(27655, 8, 205, 4, Interface);
		setBounds(27657, 98, 205, 5, Interface);

		setBounds(27660, 98 + 45, 205, 6, Interface);
		setBounds(27661, 98 + 45, 205, 7, Interface);
	}

	public static void itemsOnDeath(TextDrawingArea[] wid) {
		RSInterface rsinterface = addInterface(17100);
		addSprite(17101, new Sprite("Equipment/SPRITE 2"));

		addHover(17102, 3, 0, 10601, 1, "Interfaces/Equipment/SPRITE", 17, 17, "Close Window");
		addHovered(10601, 3, "Interfaces/Equipment/SPRITE", 17, 17, 10602);
		addText(17103, "Items Kept On Death", wid, 2, 0xff981f);
		addText(17104, "Items you will keep:", wid, 1, 0xff981f);
		addText(17105, "Items you will lose:", wid, 1, 0xff981f);
		addText(17106, "On unsafe death:", wid, 1, 0xff981f);
		addText(17107, "Max items kept on death:", wid, 1, 0xffcc33);
		addText(17108, "", wid, 1, 0xffcc33);
		rsinterface.scrollMax = 0;
		rsinterface.isMouseoverTriggered = false;
		rsinterface.children = new int[12];
		rsinterface.childX = new int[12];
		rsinterface.childY = new int[12];

		rsinterface.children[0] = 17101;
		rsinterface.childX[0] = 7;
		rsinterface.childY[0] = 8;
		rsinterface.children[1] = 17102;
		rsinterface.childX[1] = 480;
		rsinterface.childY[1] = 17;
		rsinterface.children[2] = 17103;
		rsinterface.childX[2] = 185;
		rsinterface.childY[2] = 18;
		rsinterface.children[3] = 17104;
		rsinterface.childX[3] = 22;
		rsinterface.childY[3] = 50;
		rsinterface.children[4] = 17105;
		rsinterface.childX[4] = 22;
		rsinterface.childY[4] = 110;
		rsinterface.children[5] = 17106;
		rsinterface.childX[5] = 347;
		rsinterface.childY[5] = 47;
		rsinterface.children[6] = 17107;
		rsinterface.childX[6] = 349;
		rsinterface.childY[6] = 270;
		rsinterface.children[7] = 17108;
		rsinterface.childX[7] = 398;
		rsinterface.childY[7] = 298;
		rsinterface.children[8] = 17115;
		rsinterface.childX[8] = 348;
		rsinterface.childY[8] = 64;
		rsinterface.children[9] = 10494;
		rsinterface.childX[9] = 26;
		rsinterface.childY[9] = 74;
		rsinterface.children[10] = 10600;
		rsinterface.childX[10] = 26;
		rsinterface.childY[10] = 133;
		rsinterface.children[11] = 10601;
		rsinterface.childX[11] = 480;
		rsinterface.childY[11] = 17;
		interfaceCache[10494].invSpritePadX = 8;
		interfaceCache[10600].invSpritePadX = 6;
		interfaceCache[10600].invSpritePadY = 6;
		itemsOnDeathDATA(wid);
	}

	public static void itemsOnDeathDATA(TextDrawingArea[] wid) {
		RSInterface rsinterface = addInterface(17115);

		rsinterface.parentID = 17115;
		rsinterface.id = 17115;
		rsinterface.type = 0;
		rsinterface.atActionType = 0;
		rsinterface.contentType = 0;
		rsinterface.width = 130;
		rsinterface.height = 197;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.scrollMax = 199;
		rsinterface.totalChildren(1);
		addStringContainer(17109, 17115, Client.instance.newSmallFont,
				false, RSInterface.DEFAULT_TEXT_COLOR, true, 12, "testingy");
		rsinterface.child(0, 17109, 0, 16);

	}

	public static void clanChatTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(18128);
		addHoverButton(18129, "/Clan Chat/SPRITE", 6, 72, 32, "Join Clan", 550, 18130, 5);
		addHoveredButton(18130, "/Clan Chat/SPRITE", 7, 72, 32, 18131);
		addHoverButton(18132, "/Clan Chat/SPRITE", 6, 72, 32, "Clan Setup", -1, 18133, 5);
		addHoveredButton(18133, "/Clan Chat/SPRITE", 7, 72, 32, 18134);
		addText(18135, "Join Clan", tda, 0, 0xff9b00, true, true);
		addText(18136, "Clan Setup", tda, 0, 0xff9b00, true, true);
		addSprite(18137, 37, "/Clan Chat/SPRITE");
		addText(18138, "Clan Chat", tda, 1, 0xff9b00, true, true);
		addText(18139, "Talking in: Not in clan", tda, 0, 0xff9b00, false, true);
		addText(18140, "Owner: None", tda, 0, 0xff9b00, false, true);
		addSprite(16126, 4, "/Clan Chat/SPRITE");
		tab.totalChildren(13);
		tab.child(0, 16126, 0, 221);
		tab.child(1, 16126, 0, 59);
		tab.child(2, 18137, 0, 62);
		tab.child(3, 18143, 0, 62);
		tab.child(4, 18129, 15, 226);
		tab.child(5, 18130, 15, 226);
		tab.child(6, 18132, 103, 226);
		tab.child(7, 18133, 103, 226);
		tab.child(8, 18135, 51, 237);
		tab.child(9, 18136, 139, 237);
		tab.child(10, 18138, 95, 1);
		tab.child(11, 18139, 10, 23);
		tab.child(12, 18140, 25, 38);
		/* Text area */
		RSInterface list = addTabInterface(18143);
		list.totalChildren(100);
		for (int i = 18144; i <= 18244; i++) {
			addText(i, "", tda, 0, 0xffffff, false, true);
		}
		for (int id = 18144, i = 0; id <= 18243 && i <= 99; id++, i++) {
			interfaceCache[id].actions = new String[]{"Edit Rank", "Kick", "Ban"};
			list.children[i] = id;
			list.childX[i] = 5;
			for (int id2 = 18144, i2 = 1; id2 <= 18243 && i2 <= 99; id2++, i2++) {
				list.childY[0] = 2;
				list.childY[i2] = list.childY[i2 - 1] + 14;
			}
		}
		list.height = 158;
		list.width = 174;
		list.scrollMax = 1405;
	}

	public static void cataTele(TextDrawingArea[] tda) {
		RSInterface inter = addInterface(33900);
		addSprite(33901, 0, "Interfaces/Prestige/SPRITE");
		addText(33902, "Catacombs Teleports", tda, 2, 0xff9933, true, true);
		addClickableText(33903, "1. Abyssal Demon", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33904, "2. Ankou", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33905, "3. Black Demons", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33906, "4. Bronze Dragon", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33907, "5. Brutal Black Dragon", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33908, "6. Brutal Blue Dragon", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33909, "7. Brutal Red Dragon", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33910, "8. Cyclops", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33911, "9. Dagannoth", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33912, "10. Dust Devil", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33913, "11. Deviant Spectre", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33914, "12. Fire Giant", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33915, "13. Ghost", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33916, "14. Greater Demon", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33917, "15. Greater Nechryael", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33918, "16. Hellhound", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33919, "17. Hill Giant", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33920, "18. Iron Dragon", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33921, "19. King Sand Crab", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33922, "20. Magic Axe", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33923, "21. Moss Giant", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33924, "22. Mutated Bloodveld", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33925, "23. Possessed Pickaxe", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33926, "24. Steel Dragon", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33927, "25. Shade", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33928, "26. Skeleton", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33929, "27. Twisted Banshee", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33930, "28. Warped Jelly", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addClickableText(33931, "29. Dark Beast", "Teleport", tda, 1, 0xff9933, false, true, 130);
		addHoverButton(33932, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, 33932, 3);
		addHoveredButton(33933, "Interfaces/HelpInterface/IMAGE", 3, 16, 16, 33933);

		inter.totalChildren(34);
		setBounds(33901, 15, 15, 0, inter);
		setBounds(33902, 250, 19, 1, inter);
		setBounds(33903, 50, 50, 2, inter);
		setBounds(33904, 50, 70, 3, inter);
		setBounds(33905, 50, 90, 4, inter);
		setBounds(33906, 50, 110, 5, inter);
		setBounds(33907, 50, 130, 6, inter);
		setBounds(33908, 50, 150, 7, inter);
		setBounds(33909, 50, 170, 8, inter);
		setBounds(33910, 50, 190, 9, inter);
		setBounds(33911, 50, 210, 10, inter);
		setBounds(33912, 50, 230, 11, inter);
		setBounds(33913, 200, 50, 12, inter);
		setBounds(33914, 200, 70, 13, inter);
		setBounds(33915, 200, 90, 14, inter);
		setBounds(33916, 200, 110, 15, inter);
		setBounds(33917, 200, 130, 16, inter);
		setBounds(33918, 200, 150, 17, inter);
		setBounds(33919, 200, 170, 18, inter);
		setBounds(33920, 200, 190, 19, inter);
		setBounds(33921, 200, 210, 20, inter);
		setBounds(33922, 200, 230, 21, inter);
		setBounds(33923, 350, 50, 22, inter);
		setBounds(33924, 350, 70, 23, inter);
		setBounds(33925, 350, 90, 24, inter);
		setBounds(33926, 350, 110, 25, inter);
		setBounds(33927, 350, 130, 26, inter);
		setBounds(33928, 350, 150, 27, inter);
		setBounds(33929, 350, 170, 28, inter);
		setBounds(33930, 350, 190, 29, inter);
		setBounds(33931, 350, 210, 30, inter);
		setBounds(33932, 479, 18, 31, inter);
		setBounds(33933, 479, 18, 32, inter);
	}


	public static void slayerMenuv2(TextDrawingArea[] tda) {
		int interfaceId = 54280;
		int selectScrollId = 54310;
		int otherScrollId = 54710;
		String dir = "Interfaces/slayerinterface/SPRITE";
		int index = 1;
		int basex = 12, basey = 4;
		RSInterface inter = addInterface(interfaceId);
		inter.totalChildren(22);
		addSprite(interfaceId + index++, 0, "Interfaces/slayerinterface/SPRITE");

		configHoverButton1(interfaceId + index, "Extend Current Task", dir, 1, 3, 3, 3, false, new int[]{interfaceId + index++});
		configHoverButton1(interfaceId + index, "Teleport to task", dir, 4, 5, 5, 5, false, new int[]{interfaceId + index++});
		configHoverButton1(interfaceId + index, "Block current task", dir, 6, 7, 7, 7, false, new int[]{interfaceId + index++});
		configHoverButton1(interfaceId + index, "Cancel current task", dir, 8, 9, 9, 9, false, new int[]{interfaceId + index++});
		configHoverButton1(interfaceId + index, "Obtain a new task", dir, 10, 11, 11, 11, false, new int[]{interfaceId + index++});

		addText(interfaceId + index++, "Current Task & Data", tda, 0, 0xff9933);
		addText(interfaceId + index++, "Unlock & Extend", tda, 0, 0xff9933);

		addText(interfaceId + index++, "Extend current task", tda, 0, 0xff9933);
		addText(interfaceId + index++, "Teleport to task", tda, 0, 0xff9933);
		addText(interfaceId + index++, "Block current task", tda, 0, 0xff9933);
		addText(interfaceId + index++, "Cancel current task", tda, 0, 0xff9933);
		addText(interfaceId + index++, "Obtain a new task", tda, 0, 0xff9933);

		addText(interfaceId + index++, "Master:" + (interfaceId + index), tda, 0, 0xa6a6a6);
		addText(interfaceId + index++, "Task :", tda, 0, 0xa6a6a6);
		addText(interfaceId + index++, "Task Amount :", tda, 0, 0xa6a6a6);
		addText(interfaceId + index++, "Task Streak :", tda, 0, 0xa6a6a6);
		addText(interfaceId + index++, "Slayer Points :", tda, 0, 0xa6a6a6);

		addText(interfaceId + index++, "Slayer Master's Hub!", tda, 1, 0xff9933);

		configHoverButton1(interfaceId + index, "Close", dir, 40, 41, 41, 41, false, new int[]{interfaceId + index++});

		index = 0;

		inter.child(index++, interfaceId + index, basex, basey);

		inter.child(index++, interfaceId + index, basex + 355, basey + 150);
		inter.child(index++, interfaceId + index, basex + 355, basey + 180);
		inter.child(index++, interfaceId + index, basex + 355, basey + 210);
		inter.child(index++, interfaceId + index, basex + 355, basey + 240);
		inter.child(index++, interfaceId + index, basex + 355, basey + 270);

		inter.child(index++, interfaceId + index, basex + 355, basey + 40);
		inter.child(index++, interfaceId + index, basex + 25, basey + 158);

		inter.child(index++, interfaceId + index, basex + 385, basey + 157);
		inter.child(index++, interfaceId + index, basex + 385, basey + 187);
		inter.child(index++, interfaceId + index, basex + 385, basey + 217);
		inter.child(index++, interfaceId + index, basex + 385, basey + 247);
		inter.child(index++, interfaceId + index, basex + 385, basey + 277);

		inter.child(index++, interfaceId + index, basex + 355, basey + 56);
		inter.child(index++, interfaceId + index, basex + 355, basey + 74);
		inter.child(index++, interfaceId + index, basex + 355, basey + 92);
		inter.child(index++, interfaceId + index, basex + 355, basey + 110);
		inter.child(index++, interfaceId + index, basex + 355, basey + 128);

		inter.child(index++, interfaceId + index, basex + 200, basey + 10);

		inter.child(index++, interfaceId + index, basex + 476, basey + 10);

		inter.child(index++, selectScrollId, basex + 14, basey + 41);//TOP BAR
		inter.child(index++, otherScrollId, basex + 14, basey + 170);//TOP BAR

		int boxes = 99;
		int id = selectScrollId + 1, frame = 0;

		RSInterface teleScroll = addTabInterface(selectScrollId);
		teleScroll.scrollMax = 18 * boxes;
		teleScroll.width = 311;
		teleScroll.height = 114;

		teleScroll.totalChildren(boxes * 4);

		id = selectScrollId + 1;
		frame = 0;
		basex = 1;
		basey = 0;
		for (int i = 1; i < boxes + 1; i++) {
//			configHoverButton1(id, "Unblock",dir, ( i % 2 ==0 ) ? 12 : 13, ( i % 2 ==0 ) ? 13 : 12, 344, 27, false, id);
			addSprite(id, (i % 2 == 0) ? 12 : 13, dir);
			teleScroll.child(frame, id, basex, basey);
			id++;
			frame++;
			addText(id, "Slot " + i + ":", tda, 0, 0xa6a6a6, false, true);
			teleScroll.child(frame, id, basex + 5, basey + 3);
			id++;
			frame++;
			addText(id, "", tda, 0, 0x757474, true, true);
			teleScroll.child(frame, id, basex + 150, basey + 3);
			id++;
			frame++;
			addClickableText(id, "Unblock", "select", tda, 0, 0x2bb81c, false, true, 133);
//			addText(id, "UnBlock", tda, 0, 0x2bb81c, false, true);
			teleScroll.child(frame, id, basex + 265, basey + 3);
			id++;
			frame++;
			basey += 18;
		}

		int boxes1 = 12;
		int id1 = otherScrollId + 1, frame1 = 0;

		RSInterface teleScroll2 = addTabInterface(otherScrollId);
		teleScroll2.scrollMax = 38 * boxes1;
		teleScroll2.width = 307;
		teleScroll2.height = 125;

		teleScroll2.totalChildren(boxes1 * 4);

		id1 = otherScrollId + 1;
		frame1 = 0;
		basex = 1;
		basey = 0;
		for (int i = 1; i < boxes1 + 1; i++) {
			configHoverButton1(id1, "Unlock", dir, 14, 14, 344, 27, false, id1);
			teleScroll2.child(frame1, id1, basex, basey);
			id1++;
			frame1++;
			addText(id1, "", tda, 0, 0x757474, true, true);
			teleScroll2.child(frame1, id1, basex + 160, basey + 5);
			id1++;
			frame1++;
			addText(id1, "", tda, 0, 0x757474, true, true);
			teleScroll2.child(frame1, id1, basex + 160, basey + 22);
			id1++;
			frame1++;
			addClickableSprites(id1, dir, 15, 16, 15);
			teleScroll2.child(frame1, id1, basex + 280, basey + 10);
			id1++;
			frame1++;
			basey += 38;
		}

	}

	public static void groupLeaderboard(TextDrawingArea[] tda) {
		RSInterface inter = addInterface(39900);
		addSprite(39901, 0, "Interfaces/groupleaderboard/background");
		addText(39902, "$3000 GIM Contest Leaderboard", tda, 2, 0xff9933, true, true);
		addHoverButton(39903, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, 50639, 3);
		addHoveredButton(39904, "Interfaces/HelpInterface/IMAGE", 3, 16, 16, 50640);
		//1st Text Group, top left
		addText(39905, "Collection Log Entries", tda, 1, 0xff9933, true, true);
		addText(39906, "1. Team 1", tda, 0, 0xffffff, true, true);
		addText(39907, "2. Team 2", tda, 0, 0xffffff, true, true);
		addText(39908, "3. Team 3", tda, 0, 0xffffff, true, true);
		//2nd Text Group, top right
		addText(39909, "Pets in Bank", tda, 1, 0xff9933, true, true);
		addText(39910, "1. Team 1", tda, 0, 0xffffff, true, true);
		addText(39911, "2. Team 2", tda, 0, 0xffffff, true, true);
		addText(39912, "3. Team 3", tda, 0, 0xffffff, true, true);
		//3rd Text Group, bottom left
		addText(39913, "Earned Exchange Points", tda, 1, 0xff9933, true, true);
		addText(39914, "1. Team 1", tda, 0, 0xffffff, true, true);
		addText(39915, "2. Team 2", tda, 0, 0xffffff, true, true);
		addText(39916, "3. Team 3", tda, 0, 0xffffff, true, true);
		//4th Text Group, bottom right
		addText(39917, "ToB Completions", tda, 1, 0xff9933, true, true);
		addText(39918, "1. Team 1", tda, 0, 0xffffff, true, true);
		addText(39919, "2. Team 2", tda, 0, 0xffffff, true, true);
		addText(39920, "3. Team 3", tda, 0, 0xffffff, true, true);

		inter.totalChildren(20);
		//window and title
		setBounds(39901, 71, 46, 0, inter);
		setBounds(39902, 253, 55, 1, inter);
		//close
		setBounds(39903, 404, 55, 2, inter);
		setBounds(39904, 404, 55, 3, inter);
		//1st text group, top left
		setBounds(39905, 164, 100, 4, inter);
		setBounds(39906, 164, 120, 5, inter);
		setBounds(39907, 164, 140, 6, inter);
		setBounds(39908, 164, 160, 7, inter);
		//2nd Text Group, top right
		setBounds(39909, 340, 100, 8, inter);
		setBounds(39910, 340, 120, 9, inter);
		setBounds(39911, 340, 140, 10, inter);
		setBounds(39912, 340, 160, 11, inter);
		//3rd Text Group, bottom left
		setBounds(39913, 164, 190, 12, inter);
		setBounds(39914, 164, 210, 13, inter);
		setBounds(39915, 164, 230, 14, inter);
		setBounds(39916, 164, 250, 15, inter);
		//4th Text Group, bottom right
		setBounds(39917, 340, 190, 16, inter);
		setBounds(39918, 340, 210, 17, inter);
		setBounds(39919, 340, 230, 18, inter);
		setBounds(39920, 340, 250, 19, inter);
	}

	public static void groupInformation(TextDrawingArea[] tda) {
		RSInterface inter = addInterface(50635);
		addSprite(50636, 0, "Interfaces/groupinfo/background");
		addText(50637, "Group Information", tda, 2, 0xff9933, true, true);
		addHoverButton(50638, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, 50639, 3);
		addHoveredButton(50639, "Interfaces/HelpInterface/IMAGE", 3, 16, 16, 50640);
		inter.totalChildren(5);
		//window and title
		setBounds(50636, 140, 50, 0, inter);
		setBounds(50637, 263, 60, 1, inter);
		//close
		setBounds(50638, 359, 60, 2, inter);
		setBounds(50639, 359, 60, 3, inter);

		//scroll window
		setBounds(50641, 147, 85, 4, inter);

		RSInterface scroll = addInterface(50641);
		scroll.width = 214;
		scroll.height = 190;
		scroll.scrollMax = 230;
		setChildren(300, scroll);
		int rows = 8;
		for (int i = 0, n = 0; i < rows * 2; i += 2, n += 1) {
			//ROWS
			if (n % 2 == 0) {
				addSprite(50642 + i, 0, "Interfaces/groupinfo/rows");
			} else {
				addSprite(50642 + i, 1, "Interfaces/groupinfo/rows");
			}
			setBounds(50642 + i, 0, i / 2 * 24, i, scroll);
			//Type Text
			String[] Types = {"Total Level", "Total Xp", "GP Value", "Exchange Points", "Boss Points", "Slayer Points", "PK Points", "Vote Points"};
			String type = "Type";
			if (n < Types.length) {
				type = Types[n];
			}
			addText(50642 + i + 1, type, tda, 0, 0xFF981F, false, true);
			setBounds(50642 + i + 1, 10, 6 + (i / 2 * 24), i + 1, scroll);
			//Value Text
			addLeftText(50665 + i + 1, 0, 0xFFFFFF, "Value" + (1 + i / 2));
			setBounds(50665 + i + 1, 200, 6 + (i / 2 * 24), i + (rows * 2), scroll);
		}

	}

	public static void initializeTitleWidget(TextDrawingArea[] tda) {
		RSInterface widget = addInterface(53501);
		addSprite(53502, 0, "Interfaces/Titles/IMAGE");
		addSprite(53503, 6, "Interfaces/Titles/IMAGE");
		drawRoundedRectangle(53505, 200, 130, 0x000000, (byte) 30, true, true);
		addButton(53506, 1, "Interfaces/Titles/IMAGE", "Close", 3, 52);
		addButton(53508, 7, "Interfaces/Titles/IMAGE", "Ok");
		addText(53511, "Lorem ipsum dolor sit amet,\\n"
						+ "consectetur adipiscing elit,\\n"
						+ "sed do eiusmod tempor incididunt\\n"
						+ "ut labore et dolore magna aliqua.\\n"
						+ "fUt enim ad minim veniam, quis\\n"
						+ "nostrud exercitation ullamco \\n"
						+ "laboris nisi ut aliquip ex ea\\n" + "commodo consequat.",
				tda, 1, 0xFF981F, false, true);
		addText(53512, "Purchase", tda, 1, 0xFF981F, true, true);
		drawRoundedRectangle(53513, 140, 22, 0x000000, (byte) 30, true, true);
		addText(53514, "$", tda, 2, 0xFF981F, false, true);
		addText(53515, "45,000GP", tda, 1, 0xFF981F, false, true);
		addButton(53516, 9, "Interfaces/Titles/IMAGE", "Information");
		addSprites(53517, "Interfaces/Titles/IMAGE", 11, 12, 13);
		setChildren(15, widget);
		setBounds(53502, 56, 45, 0, widget);
		setBounds(53503, 250, 80, 1, widget);
		setBounds(53505, 223, 90, 2, widget);
		setBounds(53506, 435, 51, 3, widget);
		setBounds(53508, 277, 250, 4, widget);
		setBounds(53511, 230, 100, 5, widget);
		setBounds(53512, 320, 255, 6, widget);
		setBounds(53513, 250, 224, 7, widget);
		setBounds(53514, 255, 228, 8, widget);
		setBounds(53515, 268, 228, 9, widget);
		setBounds(53516, 418, 51, 10, widget);
		setBounds(53517, 370, 221, 11, widget);
		setBounds(53549, 61, 51, 12, widget);
		setBounds(53530, 0, 0, 13, widget);
		setBounds(53535, 0, 0, 14, widget);

		RSInterface scroll = addInterface(53549);
		scroll.width = 114;
		scroll.height = 230;
		scroll.scrollMax = 750;
		setChildren(70, scroll);

		for (int i = 0; i < 70; i += 2) {
			addClickableSprites(53550 + i, "View", "Interfaces/Titles/IMAGE",
					3, 4, 5, 14);
			addText(53550 + i + 1, "Entry " + (1 + i / 2), tda, 1, 0xFF981F,
					false, true);
			setBounds(53550 + i, 0, i / 2 * 22, i, scroll);
			setBounds(53550 + i + 1, 4, 3 + (i / 2 * 22), i + 1, scroll);
		}

		widget = addInterface(53530);
		setChildren(1, widget);
		addText(53531, "Selected Title", tda, 2, 0xFF981F, true, true);
		setBounds(53531, 320, 66, 0, widget);

		widget = addInterface(53535);
		setChildren(1, widget);
		addInputField(53536, 16, 0xFF981F, "Custom title", 120, 22, false,
				true, "[A-Za-z0-9 ]");
		setBounds(53536, 262, 58, 0, widget);
	}

	public static final void initializeCommandHelp() {
		for (int childId : interfaceCache[8143].children) {
			interfaceCache[childId].message = "";
		}
	}

	public static void rangeTargetBoard(TextDrawingArea[] tda) {
		int start = 22999;
		RSInterface widget = addInterface(start);
		String dir = "Interfaces/rangingguild/SPRITE";
		widget.totalChildren(16);
		int index = 1;
		int basex = 20, basey = 30;

		addSprite(start + index++, 0, dir);
		configHoverButton1(start + index, "Close", dir, 40, 41, 41, 41, false, new int[]{start + index++});
		addText(start + index++, "100", tda, 1, 0x00a966, false, true);//score
		addText(start + index++, "Bulls-Eye!", tda, 2, 0xf3f300, false, true);//where you hit
		addToItemGroup(start + index++, 2, 5, 10, 10, false, new String[]{null, null, null, null, null});//Shots left
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2299);//BULLSEYE  -> DONE
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2300);//MISSED -> DONE
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2301);//MISSED -> DONE
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2302);//10 -> DONE
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2303);//10 -> DONE
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2304);//20 -> DONE
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2305);//20 -> DONE
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2306);//30 -> DONE
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2307);//30 -> DONE
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2308);//50 -> DONE
		addConfigSprite(start + index++, 1, dir, 2, dir, 0, 2309);//50 -> Done

		index = 0;
		widget.child(index++, start + index, basex, basey);//background
		widget.child(index++, start + index, basex + 465, basey + 8);
		widget.child(index++, start + index, basex + 410, basey + 150);//score
		widget.child(index++, start + index, basex + 215, basey + 90);//bullseye
		widget.child(index++, start + index, basex + 40, basey + 80);//itemgroup
		widget.child(index++, start + index, basex + 235, basey + 140);//BULLSEYE SPRITE -> DONE
		widget.child(index++, start + index, basex + 75, basey + 250);//MISSED SPRITE -> DONE
		widget.child(index++, start + index, basex + 350, basey + 75);//MISSED SPRITE -> DONE
		widget.child(index++, start + index, basex + 200, basey + 50);//10 SPRITE -> DONE
		widget.child(index++, start + index, basex + 215, basey + 240);//10 SPRITE -> DONE
		widget.child(index++, start + index, basex + 200, basey + 200);//20 SPRITE -> DONE
		widget.child(index++, start + index, basex + 215, basey + 75);//20 SPRITE -> DONE
		widget.child(index++, start + index, basex + 215, basey + 185);//30 SPRITE -> DONE
		widget.child(index++, start + index, basex + 215, basey + 110);//30 SPRITE -> DONE
		widget.child(index++, start + index, basex + 215, basey + 150);//50 SPRITE -> DONE
		widget.child(index++, start + index, basex + 200, basey + 125);//50 SPRITE -> DONE
	}

	public static void updateShopWidget(TextDrawingArea[] tda) {
		RSInterface widget = interfaceCache[3824];
		int[] childrenId = new int[widget.children.length + 1];
		int[] childrenX = new int[widget.children.length + 1];
		int[] childrenY = new int[widget.children.length + 1];
		for (int i = 0; i < widget.children.length; i++) {
			childrenId[i] = widget.children[i];
			childrenX[i] = widget.childX[i];
			childrenY[i] = widget.childY[i];
		}
		setChildren(93, widget);

		for (int i = 0; i < widget.children.length; i++) {
			setBounds(childrenId[i], childrenX[i], childrenY[i], i, widget);
		}
		setBounds(28050, 0, 0, 92, widget);

		RSInterface subWidget = addInterface(28050);
		setChildren(2, subWidget);
		addSprite(28051, 2, "Interfaces/BountyHunter/IMAGE");
		addText(28052, "PKP:", tda, 1, 0xFFFF00, false, true);
		setBounds(28051, 20, 30, 0, subWidget);
		setBounds(28052, 48, 30, 1, subWidget);
	}

	public static void newAchievements(TextDrawingArea[] tda) {
		int interfaceId = 54760;
		int selectScrollId = 54785;
		int teleScrollId = 54795;
		RSInterface tab = addInterface(interfaceId);
		String dir = "Interfaces/achievement3/SPRITE";
		int index = 1;
		int basex = 40, basey = 30;

		tab.totalChildren(16);

		addSprite(interfaceId + index++, 0, dir);
		configHoverButton1(interfaceId + index, "Close", dir, 40, 41, 41, 41, false, new int[]{interfaceId + index++});
		addText(interfaceId + index++, "Achievement Diary", tda, 1, 0xff9933, false, true);
		addText(interfaceId + index++, "Achievements completed: @whi@54/59", tda, 0, 0xff9933, false, true);
		configHoverButton1(interfaceId + index, "Claim all", dir, 14, 15, 15, 15, false, new int[]{interfaceId + index++});
		addText(interfaceId + index++, "Claim all", tda, 2, 0x298a00, true, true);

		index = 0;
		tab.child(index++, interfaceId + index, basex, basey);
		tab.child(index++, interfaceId + index, basex + 415, basey + 9);
		tab.child(index++, interfaceId + index, basex + 175, basey + 10);
		tab.child(index++, interfaceId + index, basex + 250, basey + 50);
		tab.child(index++, interfaceId + index, basex + 136, basey + 38);
		tab.child(index++, interfaceId + index, basex + 178, basey + 43);

		tab.child(index++, selectScrollId, basex + 12, basey + 36);//TOP BAR
		tab.child(index++, teleScrollId, basex + 10, basey + 65);//TOP BAR

		int boxes = 4;

		RSInterface selectScroll = addTabInterface(selectScrollId);

		selectScroll.totalChildren(8);
		int id = selectScrollId + 1, frame = 0;
		basex = 2;
		basey = 2;

		configHoverButton1(id, "Select", dir, 8, 7, 7, 7, false, new int[]{54787, 54788, 54789});
		selectScroll.child(frame++, id++, basex, basey);
		configHoverButton1(id, "Select", dir, 8, 7, 7, 7, false, new int[]{54786, 54788, 54789});
		selectScroll.child(frame++, id++, basex + 30, basey);
		configHoverButton1(id, "Select", dir, 8, 7, 7, 7, false, new int[]{54787, 54786, 54789});
		selectScroll.child(frame++, id++, basex + 60, basey);
		configHoverButton1(id, "Select", dir, 8, 7, 7, 7, false, new int[]{54787, 54788, 54786});
		selectScroll.child(frame++, id++, basex + 90, basey);

		for (int i = 1; i < boxes + 1; i++) {
			addSprite(id, 8 + i, dir);
			selectScroll.child(frame, id, basex + 8, basey + 7);
			id++;
			frame++;
			basex += 30;
		}

		int boxes1 = 60;

		RSInterface teleScroll = addTabInterface(teleScrollId);
		teleScroll.scrollMax = 36 * boxes1;
		teleScroll.width = 400;
		teleScroll.height = 200;

		teleScroll.totalChildren(boxes1 * 6);

		id = teleScrollId + 1;
		frame = 0;
		basex = 1;
		basey = 0;
		for (int i = 1; i < boxes1 + 1; i++) {
			addSprite(id, (i % 2 == 0) ? 5 : 6, dir);
			teleScroll.child(frame, id, basex + 5, basey + 2);
			id++;
			frame++;
			addSprite(id, 13, dir);
			teleScroll.child(frame, id, basex + 7, basey + 7);
			id++;
			frame++;
			addText(id, "Would a wood chuck" + id, tda, 0, 0xff9933, false, true);
			teleScroll.child(frame, id, basex + 35, basey + 4);
			id++;
			frame++;/*
			addProgressBar(id, 150, 4, new int[] { GREEN_COLOR, RED_COLOR }, false);
			teleScroll.child(frame, id, basex + 40, basey + 25);
			id++;
			frame++;*/
			addText(id, "Current Progress: 45/100" + id, tda, 0, 0xffffff, false, true);
			teleScroll.child(frame, id, basex + 40, basey + 25);
			id++;
			frame++;
			addToItemGroup(id, 50, 10, 3, 0, false, new String[]{null, null, null, null, null});
			teleScroll.child(frame, id, basex + 210, basey + 4);
			id++;
			frame++;
			addClickableSprites(id, "Claim Rewards", dir, 1, 2, 1);
			teleScroll.child(frame, id, basex + 360, basey + 7);
			id++;
			frame++;
			basey += 36;
		}
	}

	public static void CharInfo(TextDrawingArea[] tda) {
		int interfaceId = 55160;
		int teleScrollId = 55200;
		RSInterface tab = addInterface(interfaceId);
		String dir = "Interfaces/charinfo/SPRITE";
		int index = 1;
		int basex = 12, basey = 4;

		tab.totalChildren(35);

		addSprite(interfaceId + index++, 0, dir);
		configHoverButton1(interfaceId + index, "Close", dir, 40, 41, 41, 41, false, new int[]{interfaceId + index++});
		addText(interfaceId + index++, "Character Information Panel", tda, 1, 0xff9933, false, true);
//55164
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "99", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "Total Level: ", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "Donator: ", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "D. Credits: ", tda, 0, 0xff9933, false, true);
		addText(interfaceId + index++, "Donated: ", tda, 0, 0xff9933, false, true);

		addChar(interfaceId + index++);

		index = 0;
		tab.child(index++, interfaceId + index, basex, basey);
		tab.child(index++, interfaceId + index, basex + 475, basey + 10);
		tab.child(index++, interfaceId + index, basex + 175, basey + 10);

		tab.child(index++, interfaceId + index, basex + 37, basey + 45);
		tab.child(index++, interfaceId + index, basex + 37, basey + 68);
		tab.child(index++, interfaceId + index, basex + 37, basey + 90);
		tab.child(index++, interfaceId + index, basex + 37, basey + 113);
		tab.child(index++, interfaceId + index, basex + 37, basey + 135);
		tab.child(index++, interfaceId + index, basex + 37, basey + 157);
		tab.child(index++, interfaceId + index, basex + 37, basey + 179);
		tab.child(index++, interfaceId + index, basex + 37, basey + 201);
		tab.child(index++, interfaceId + index, basex + 83, basey + 45);
		tab.child(index++, interfaceId + index, basex + 83, basey + 68);
		tab.child(index++, interfaceId + index, basex + 83, basey + 90);
		tab.child(index++, interfaceId + index, basex + 83, basey + 113);
		tab.child(index++, interfaceId + index, basex + 83, basey + 135);
		tab.child(index++, interfaceId + index, basex + 83, basey + 157);
		tab.child(index++, interfaceId + index, basex + 83, basey + 179);
		tab.child(index++, interfaceId + index, basex + 83, basey + 201);
		tab.child(index++, interfaceId + index, basex + 128, basey + 45);
		tab.child(index++, interfaceId + index, basex + 128, basey + 68);
		tab.child(index++, interfaceId + index, basex + 128, basey + 90);
		tab.child(index++, interfaceId + index, basex + 128, basey + 113);
		tab.child(index++, interfaceId + index, basex + 128, basey + 135);
		tab.child(index++, interfaceId + index, basex + 128, basey + 157);
		tab.child(index++, interfaceId + index, basex + 128, basey + 179);
		tab.child(index++, interfaceId + index, basex + 128, basey + 201);
		tab.child(index++, interfaceId + index, basex + 30, basey + 222);
		tab.child(index++, interfaceId + index, basex + 14, basey + 257);
		tab.child(index++, interfaceId + index, basex + 14, basey + 275);
		tab.child(index++, interfaceId + index, basex + 14, basey + 292);

		tab.child2(index++, interfaceId + index, 180, 195);//char

		tab.child(index++, teleScrollId, basex + 325, basey + 53);//TOP BAR

		int boxes1 = 60;

		RSInterface teleScroll = addTabInterface(teleScrollId);
		teleScroll.scrollMax = 20 * boxes1;
		teleScroll.width = 145;
		teleScroll.height = 254;

		teleScroll.totalChildren(boxes1 * 2);

		int id = teleScrollId + 1, frame = 0;
		basex = 1;
		basey = 0;
		for (int i = 1; i < boxes1 + 1; i++) {
			addSprite(id, (i % 2 == 0) ? 1 : 2, dir);
			teleScroll.child(frame, id, basex + 5, basey + 2);
			id++;
			frame++;
			addText(id, "", tda, 0, 0xff9933, false, true);
			teleScroll.child(frame, id, basex + 6, basey + 6);
			id++;
			frame++;
			basey += 20;
		}
	}

	public static void Teleporting(TextDrawingArea[] ryan) {
		int interfaceId = 31000;
		int selectScrollId = 31035;
		int teleScrollId = 31065;
		String dir = "Interfaces/Telev2/SPRITE";
		int index = 1;
		RSInterface main = addInterface(interfaceId);
		main.totalChildren(25);

		addSprite(interfaceId + index++, 0, dir);
		configHoverButton1(interfaceId + index, "Close", dir, 40, 41, 41, 41, false, new int[]{interfaceId + index++});
		addText(interfaceId + index++, "Exiled Teleports", ryan, 2, 0xff9933, true, true);

		addText(interfaceId + index++, "Favourites", ryan, 2, 0xd9bc50, false, true);
//		addButton(interfaceId + index++, 36, dir,"Previous Teleport");
		configHoverButton1(interfaceId + index, "Previous Teleport", dir, 36, 37, 37, 37, false, new int[]{interfaceId + index++});
		addText(interfaceId + index++, "Locations", ryan, 2, 0xd9bc50, false, true);

		for (int i = 0; i < 9; i++) {
			addClickableText(interfaceId + index++, "Favourites", "Select", ryan, 0, 0xff9040, true, true, 133);
		}

//		addText(interfaceId + index++, "Previous Teleport", 1, 0xff9933, false, true);
		configHoverButton1(interfaceId + index, "teleport", dir, 1, 2, 2, 2, false, new int[]{interfaceId + index++});

		RSInterface scroll1 = addInterface(interfaceId + index++);
		scroll1.totalChildren(1);
		addToItemGroup(interfaceId + index, 4, 50, 8, 5, true, new String[]{null, null, null, null, null});
		scroll1.child(0, interfaceId + index++, 1, 5);

		scroll1.width = 157;
		scroll1.height = 177;
		scroll1.scrollMax = 1000;

		int basex = 11, basey = 4;
		index = 0;

		main.child(index++, interfaceId + index, basex, basey);
		main.child(index++, interfaceId + index, basex + 475, basey + 10);
		main.child(index++, interfaceId + index, basex + 245, basey + 10);

		main.child(index++, interfaceId + index, basex + 367, basey + 64);
		main.child(index++, interfaceId + index, basex + 274, basey + 265);
		main.child(index++, interfaceId + index, basex + 14, basey + 64);

		main.child(index++, interfaceId + index, basex + 360, basey + 86);
		main.child(index++, interfaceId + index, basex + 360, basey + 84 + (25 * 1));
		main.child(index++, interfaceId + index, basex + 360, basey + 82 + (25 * 2));
		main.child(index++, interfaceId + index, basex + 360, basey + 80 + (25 * 3));
		main.child(index++, interfaceId + index, basex + 360, basey + 78 + (25 * 4));
		main.child(index++, interfaceId + index, basex + 360, basey + 76 + (25 * 5));
		main.child(index++, interfaceId + index, basex + 360, basey + 74 + (25 * 6));
		main.child(index++, interfaceId + index, basex + 360, basey + 72 + (25 * 7));
		main.child(index++, interfaceId + index, basex + 360, basey + 70 + (25 * 8));

		main.child(index++, interfaceId + index, basex + 186, basey + 265);

		main.child(index++, interfaceId + index, basex + 185, basey + 79);


		main.child(index++, selectScrollId, basex + 8, basey + 32);//TOP BAR
		main.child(index++, teleScrollId, basex + 14, basey + 79);//TELE LIST

		int boxes = 6;

		RSInterface selectScroll = addTabInterface(selectScrollId);

		selectScroll.totalChildren(boxes * 3);
		String[] names = {"Monsters", "Bosses", "Minigames", "Dungeons", "Wildy", "Cities"};
		int id = selectScrollId + 1, frame = 0;
		basex = 2;
		basey = 7;

		configHoverButton1(id, "Select", dir, 3, 4, 4, 4, false, new int[]{31037, 31038, 31039, 31040, 31041});
		selectScroll.child(frame++, id++, basex, basey);
		configHoverButton1(id, "Select", dir, 3, 4, 4, 4, false, new int[]{31036, 31038, 31039, 31040, 31041});
		selectScroll.child(frame++, id++, basex + 80, basey);
		configHoverButton1(id, "Select", dir, 3, 4, 4, 4, false, new int[]{31036, 31037, 31039, 31040, 31041});
		selectScroll.child(frame++, id++, basex + 160, basey);
		configHoverButton1(id, "Select", dir, 3, 4, 4, 4, false, new int[]{31036, 31037, 31038, 31040, 31041});
		selectScroll.child(frame++, id++, basex + 240, basey);
		configHoverButton1(id, "Select", dir, 3, 4, 4, 4, false, new int[]{31036, 31037, 31039, 31038, 31041});
		selectScroll.child(frame++, id++, basex + 320, basey);
		configHoverButton1(id, "Select", dir, 3, 4, 4, 4, false, new int[]{31036, 31037, 31039, 31040, 31038});
		selectScroll.child(frame++, id++, basex + 400, basey);

		for (int i = 1; i < boxes + 1; i++) {
			addText(id, "" + names[i - 1], ryan, 0, 0xff9933, true, true);
			selectScroll.child(frame, id, basex + 40, basey + 3);
			id++;
			frame++;
			addSprite(id, 29 + i, dir);
			selectScroll.child(frame, id, basex + 3, basey + 2);
			id++;
			frame++;
			basex += 80;
		}

		int boxes1 = 30;

		RSInterface teleScroll = addTabInterface(teleScrollId);
		teleScroll.scrollMax = 21 * boxes1;
		teleScroll.width = 145;
		teleScroll.height = 210;

		teleScroll.totalChildren(boxes1 * 5);

		id = teleScrollId + 1;
		frame = 0;
		basex = 1;
		basey = 0;
		for (int i = 1; i < boxes1 + 1; i++) {
			configHoverButton1(id, "Teleport", dir, 8, 9, 344, 27, false, id);
			teleScroll.child(frame, id, basex, basey);
			id++;
			frame++;
			addText(id, "", ryan, 1, 0xff9933, false, true);
			teleScroll.child(frame, id, basex + 5, basey + 2);
			id++;
			frame++;
			addClickableSprites(id, "Favourites", dir, 6, 7, 6);
			teleScroll.child(frame, id, basex + 125, basey + 2);
			id++;
			frame++;
			basey += 21;
		}
	}

	public static void StarterKit(TextDrawingArea[] tda) {
		int INTERFACEID = 49520;
		RSInterface StarterPickerWidget = addInterface(INTERFACEID);

		int InterfaceLayout = 49521;
		addSprite(InterfaceLayout, 1, "Interfaces/StarterKit/PerkSelection");

		int MeleeKit = 49522;
		addButton(MeleeKit, 1, "Interfaces/StarterKit/MeleeKit", "Melee set");

		int RangeKit = 49523;
		addButton(RangeKit, 1, "Interfaces/StarterKit/RangeKit", "Range set");

		int MagicKit = 49524;
		addButton(MagicKit, 1, "Interfaces/StarterKit/MagicKit", "Magic set");

		int CloseButton = 49525;
		addButton(CloseButton, 1, "Interfaces/StarterKit/Close", "Close interface");

		StarterPickerWidget.totalChildren(5);
		setBounds(InterfaceLayout, 20, 15, 0, StarterPickerWidget);
		setBounds(MeleeKit, 125, 41, 1, StarterPickerWidget);
		setBounds(RangeKit, 219, 41, 2, StarterPickerWidget);
		setBounds(MagicKit, 313, 41, 3, StarterPickerWidget);
		setBounds(CloseButton, 480, 18, 4, StarterPickerWidget);

	}

	public static void shopWidget(TextDrawingArea[] tda) {
		// Interface background
		RSInterface widget = addInterface(64000);
		setChildren(5, widget);
		addSprite(64001, 1, "Interfaces/Shop/IMAGE");
		addHoverButton(64002, "Interfaces/Shop/IMAGE", 2, 21, 21, "Close Window", 201, 64002, 5);
		addText(64003, "Tony's store", tda, 2, 0xFFA500, false, true);
		setBounds(64001, 10, 10, 0, widget);
		setBounds(64015, 20, 55, 1, widget);
		setBounds(64002, 485, 20, 2, widget);
		setBounds(64003, 200, 20, 3, widget);
		setBounds(64017, 0, 0, 4, widget);

		// Scroll
		RSInterface scroll = addInterface(64015);
		setChildren(1, scroll);
		scroll.height = 252;
		scroll.width = 465;
		scroll.scrollMax = 800;

		// Item container
		addToItemGroup(SHOP_CONTAINER, 10, 80, 14, 14, true, "Value", "Buy 1", "Buy 5", "Buy 10", "Buy X", null);
		setBounds(SHOP_CONTAINER, 8, 8, 0, scroll);
		interfaceCache[SHOP_CONTAINER].invAlwaysInfinity = false;

		// Bounty hunter widget
		RSInterface subWidget = addInterface(64017);
		setChildren(2, subWidget);
		addSprite(64018, 2, "Interfaces/BountyHunter/IMAGE");
		addText(64019, "PKP", tda, 1, 0xFFFF00, false, true);
		setBounds(64018, 20, 20, 0, subWidget);
		setBounds(64019, 48, 20, 1, subWidget);
	}

	static void OfflineRewards(TextDrawingArea[] tda) {
		int interID = 24472;
		RSInterface tab = addInterface(interID);
		setChildren(10, tab);
		String dir = "Interfaces/offlinerewards/SPRITE";

		addSprite(24473, 0, dir);
		addToItemGroup(24474, 10, 80, 14, 14, false);
		addText(24475, "Stored Items : 0", tda, 0, 0xFFFFFFF);

		addHoverButton(24476, dir, 3, 21, 21, "Close Window", 201, 24477, 5);
		addHoveredButton(24477, dir, 4, 16, 16, 24478);

		addHoverButton(24479, dir, 1, 86, 26, "Claim Rewards", 201, 24480, 5);
		addHoveredButton(24480, dir, 2, 86, 26, 24481);

		addText(24482, "Claim All", tda, 1, 0x00ff00);
		addText(24483, "Offline Rewards Chest", tda, 2, 0x15ada8);


		setBounds(24473, 10, 10, 0, tab);
		setBounds(24474, 27, 57, 1, tab);//items
		setBounds(24475, 42, 23, 2, tab);

		setBounds(24476, 485, 20, 3, tab);
		setBounds(24477, 485, 20, 4, tab);

		setBounds(24479, 217, 290, 5, tab);
		setBounds(24480, 217, 290, 6, tab);

		setBounds(24482, 235, 295, 7, tab);
		setBounds(24483, 190, 20, 8, tab);

	}

	static void seasonalPass(TextDrawingArea[] tda) {
		int interID = 59961;
		RSInterface tab = addInterface(interID);
		String dir = "Interfaces/battlepass/SPRITE";
		int id = interID + 1;
		int c = 0;
		int x = 25;
		int y = 40;
		tab.totalChildren(12);

		addSprite(id, 1115, dir);
		tab.child(c++, id++, 0 + x, 0 + y);

		addHoverButton(id, dir, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 440 + x, 8 + y);
		addHoveredButton(id, dir, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 440 + x, 8 + y);
		id++;

		addText(id, "", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 230 + x, 7 + y);

		addText(id, "Division", tda, 1, 0xd4d4d4, true, true);
		tab.child(c++, id++, 45 + x, 15 + y);

		addText(id, "XP: 0/10", tda, 0, 0xFFFFFF, false, true);
		tab.child(c++, id++, 90 + x, 33 + y);

		addText(id, "10", tda, 2, 0xFFFFFF, true, true);
		tab.child(c++, id++, 45 + x, 35 + y);

		addText(id, "Division 1", tda, 2, 0xFFFFFF, true, true);
		tab.child(c++, id++, 285 + x, 26 + y);
		addText(id, "Division Ends: 10 days", tda, 0, 0xd4d4d4, true, true);
		tab.child(c++, id++, 285 + x, 45 + y);

//		addText(id, "Information", "Select", fonts, 0, 0xFF9900, true, true, 65, 17);
		addText(id, "", tda, 0, 0xd4d4d4, true, true);
		tab.child(c++, id++, 15 + x, 225 + y);

		new ProgressBar(id, 61, 8, new int[]{0x009a1a}, true, false, "", new int[]{0x9a0000});
		tab.child(c++, id++, 89 + x, 47 + y);

		tab.child(c++, 59990, 10 + x, 61 + y);

		interID = 59990;

		RSInterface scroll = addInterface(interID);

		scroll.totalChildren(400);
		scroll.width = 445;
		scroll.height = 170;
		scroll.scrollMax = 2600;
		scroll.horizontalScroll = true;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;
		for (int i = 0; i < 50; i++) {
			addSprite(id, 1106, dir);
			scroll.child(c++, id++, 0 + x, 0 + y);
			addText(id, "" + (i + 1), tda, 0, 0xFFFFFF, true, true);
			scroll.child(c++, id++, 24 + x, 4 + y);


			addRectangle(id, 49, 66, 0x8BD0F1, 200, true);
			scroll.child(c++, id++, 0 + x, 21 + y);
			addRectangle(id, 49, 66, 0xFED161, 200, true);
			scroll.child(c++, id++, 0 + x, 90 + y);

			addToItemGroup(id, 1, 1, 1, 1, false);
			scroll.child(c++, id++, 8 + x, 50 + y);
			addToItemGroup(id, 1, 1, 1, 1, false);
			scroll.child(c++, id++, 8 + x, 119 + y);

			addConfigButton(id, interID, 1113, 1114, dir, 20, 20, ".", 0, 0, 1614 + i);
			scroll.child(c++, id++, 15 + x, 25 + y);
			addConfigButton(id, interID, 1113, 1114, dir, 20, 20, ".", 0, 0, 1814 + i);
			scroll.child(c++, id++, 15 + x, 96 + y);

			x += 52;
		}

	}

	public static void bountyHunterWidget(TextDrawingArea[] tda) {
		RSInterface widget = addInterface(28000);
		addTransparentSprite3(28001, 1, "Interfaces/BountyHunter/IMAGE", 20);
		addText(28003, "Target:", tda, 0, 0xFFFF00, false, true);
		addText(28004, "Abnant", tda, 1, 0xFFFFFF, true, true);
		addText(28005, "Lvl 1-4, Cmb 70", tda, 0, 0xCC0000, true, true);
		addText(28006, "Wealth: V. Low", tda, 0, 0xFFFF00, true, true);
		setChildren(15, widget);
		setBounds(28001, 320, 15, 0, widget);
		setBounds(28003, 440, 18, 1, widget);
		setBounds(28004, 458, 31, 2, widget);
		setBounds(28005, 458, 47, 3, widget);
		setBounds(28006, 359, 47, 4, widget);

		/** TODO WIldy Skull **/
		setBounds(196, Client.instance.isResized() ? 420 : 420,
				Client.instance.isResized() ? 286 : 286, 5, widget);

		addText(250, "", tda, 1, 0xFFFF00, true, true);
		RSInterface skullWidget = RSInterface.interfaceCache[196];
		int[] backupX = skullWidget.childX;
		int[] backupY = skullWidget.childY;

		skullWidget.children = new int[3];
		skullWidget.childX = new int[3];
		skullWidget.childY = new int[3];

		skullWidget.totalChildren(3);
		skullWidget.child(0, 194, backupX[0], backupY[0]);
		skullWidget.child(1, 195, backupX[1], backupY[1]);
		skullWidget.child(2, 250, 29, 24);

		skullWidget.width *= 1.5;

		setBounds(28030, 345, 25, 6, widget);
		setBounds(28032, 345, 25, 7, widget);
		setBounds(28034, 345, 25, 8, widget);
		setBounds(28036, 345, 25, 9, widget);
		setBounds(28038, 345, 25, 10, widget);
		setBounds(28040, 345, 25, 11, widget);
		setBounds(28020, 0, 5, 12, widget);
		setBounds(28070, 0, 5, 13, widget);

		RSInterface sprite;
		int imageId = 2;
		for (int i = 0; i < 12; i += 2) {
			sprite = addInterface(28030 + i);
			addSprite(28031 + i, imageId++, "Interfaces/BountyHunter/IMAGE");
			setChildren(1, sprite);
			setBounds(28031 + i, 0, 0, 0, sprite);
		}

		RSInterface statistics = addInterface(28020);
		setChildren(9, statistics);
		addTransparentSprite3(28021, 0, "Interfaces/BountyHunter/IMAGE", 20);
		addText(28022, "Current  Record", tda, 0, 0xFFFF00, false, true);
		addText(28023, "Rogue:", tda, 0, 0xFFFF00, false, true);
		addText(28024, "Hunter:", tda, 0, 0xFFFF00, false, true);
		addText(28025, "1", tda, 0, 0xFFFF00, true, true);
		addText(28026, "2", tda, 0, 0xFFFF00, true, true);
		addText(28027, "3", tda, 0, 0xFFFF00, true, true);
		addText(28028, "4", tda, 0, 0xFFFF00, true, true);
		addSprite(28029, 8, "Interfaces/BountyHunter/IMAGE");

		setBounds(28021, 340, 58, 0, statistics);
		setBounds(28022, 420, 60, 1, statistics);
		setBounds(28023, 375, 73, 2, statistics);
		setBounds(28024, 375, 87, 3, statistics);
		setBounds(28025, 440, 73, 4, statistics);
		setBounds(28026, 440, 87, 5, statistics);
		setBounds(28027, 481, 73, 6, statistics);
		setBounds(28028, 481, 87, 7, statistics);
		setBounds(28029, 347, 74, 8, statistics);

		RSInterface timerWidget = addInterface(28070);
		addTransparentSprite3(28071, 10, "Interfaces/BountyHunter/IMAGE", 20);
		addText(28072, "0:59", tda, 0, 0xff9040, true, true);
		setChildren(2, timerWidget);
		setBounds(28071, 293, 10, 0, timerWidget);
		setBounds(28072, 307, 27, 1, timerWidget);
	}

	public static void helpComponent(TextDrawingArea[] tda) {
		RSInterface widget = addInterface(59525);
		addSprite(59526, 1, "Interfaces/HelpInterface/IMAGE");
		addInputField(59527, 200, 0xFF981F, "Describe the bug you've experienced. (200 characters max)", 430, 28, false,
				false, "[A-Za-z0-9 .,]");
		addText(59528, "Help Request", tda, 2, 0xFF981F, true, true);
		addText(59529,
				"You are only allowed to request help when you need it. Situations such as being stuck,\\n"
						+ "threatened by another player, problems with a donation, or experiencing a bug are all\\n"
						+ "viable uses of the help system. Improper use of this system may lead to punishment.",
				tda, 0, 0xFF981F, false, true);
		addHoverButton(59530, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, 59531, 3);
		addHoveredButton(59531, "Interfaces/HelpInterface/IMAGE", 3, 16, 16, 59532);
		setChildren(6, widget);
		setBounds(59526, 33, 106, 0, widget);
		setBounds(59527, 40, 192, 1, widget);
		setBounds(59528, 256, 113, 2, widget);
		setBounds(59529, 40, 135, 3, widget);
		setBounds(59530, 456, 112, 4, widget);
		setBounds(59531, 456, 112, 5, widget);
	}

	public static void barrowsReward(TextDrawingArea[] wid) {
		RSInterface tab = addInterface(64500);
		addSprite(64501, 0, "Interfaces/Bankpin/IMAGE");
		addHoverButton(64502, "Interfaces/Shop/IMAGE", 2, 21, 21, "Close Window", 201, 64502, 5);
		setChildren(3, tab);
		setBounds(64501, 256 - 140, 120, 0, tab);
		setBounds(64502, 374, 127, 1, tab);

		// Item container
		addToItemGroup(64503, 6, 2, 5, 5, false, "", "", "");
		setBounds(64503, 148, 155, 2, tab);
	}

	public static void bankPin(TextDrawingArea[] wid) {
		RSInterface tab = addInterface(59500);
		addSprite(59501, 0, "Interfaces/Bankpin/IMAGE");
		addText(59502, "Account Pin", wid, 2, 0xFF981F, true, true);
		addText(59503, "Enter your 4 digit code", wid, 1, 0xFF981F, true, true);
		addText(59504, "Enter your 4 digit code", wid, 1, 0xFF981F, true, true);
		addText(59505, "Enter your 4 digit code", wid, 1, 0xFF981F, true, true);
		addText(59506, "Press enter to submit", wid, 2, 0xFF981F, true, true);
		addInputField(59507, 8, 0xFF981F, "", 100, 24, true);
		addHoverButton(59508, "Interfaces/Bankpin/IMAGE", 1, 16, 16, "Close", 375, 59509, 3);
		addHoveredButton(59509, "Interfaces/Bankpin/IMAGE", 2, 16, 16, 59510);
		setChildren(9, tab);
		setBounds(59501, 256 - 140, 120, 0, tab);
		setBounds(59502, 256, 132, 1, tab);
		setBounds(59503, 256, 150, 2, tab);
		setBounds(59504, 256, 165, 3, tab);
		setBounds(59505, 256, 180, 4, tab);
		setBounds(59506, 256, 200, 5, tab);
		setBounds(59507, 256 - 50, 220, 6, tab);
		setBounds(59508, 374, 127, 7, tab);
		setBounds(59509, 374, 127, 8, tab);
	}
}