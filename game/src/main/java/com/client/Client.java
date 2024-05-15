package com.client;

import java.awt.*;
import java.awt.Point;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import com.client.connection.Connection;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import ch.qos.logback.classic.Level;
import com.client.accounts.Account;
import com.client.definitions.anims.datastructure.IterableNodeHashTable;
import com.client.engine.impl.MouseWheelHandler;
import com.client.graphics.interfaces.*;
import com.client.graphics.interfaces.impl.health_hud.HealthHud;
import com.client.graphics.interfaces.impl.notification.NotificationInterface;
import com.client.graphics.loaders.*;
import com.client.instruction.InstructionArgs;
import com.client.instruction.InstructionId;
import com.client.instruction.InstructionProcessor;
import com.client.menu.MenuEntry;
import com.client.accounts.AccountManager;
import com.client.broadcast.Broadcast;
import com.client.broadcast.BroadcastManager;
import com.client.definitions.*;
import com.client.definitions.server.ItemDef;
import com.client.engine.impl.KeyHandler;
import com.client.engine.impl.MouseHandler;
import com.client.draw.Bubble;
import com.client.features.settings.Preferences;
import com.client.graphics.interfaces.daily.DailyRewards;
import com.client.graphics.interfaces.dropdown.StretchedModeMenu;
import com.client.graphics.interfaces.eventcalendar.EventCalendar;
import com.client.graphics.interfaces.impl.*;

import static com.client.SceneGraph.pitchRelaxEnabled;
import static com.client.StringUtils.longForName;
import static com.client.engine.impl.MouseHandler.*;

import com.client.features.EntityTarget;
import com.client.features.ExperienceDrop;
import com.client.features.gametimers.GameTimer;
import com.client.features.gametimers.GameTimerHandler;
import com.client.features.settings.InformationFile;
import com.client.graphics.interfaces.settings.Setting;
import com.client.graphics.interfaces.settings.SettingsInterface;
import com.client.graphics.textures.TextureProvider;
import com.client.menu.MenuManager;
import com.client.model.Items;
import com.client.music.Class56;
import com.client.music.JavaMidiPlayer;
import com.client.osrs.OSRSCacheLoader;
import com.client.script.ClientScripts;
import com.client.sign.Signlink;
import com.client.skillorbs.SkillOrbs;
import com.client.sound.Music;
import com.client.sound.Sound;
import com.client.sound.SoundType;
import com.client.soundeffects.SoundConstants;
import com.client.soundeffects.SoundEffects;
import com.client.soundeffects.SoundPlayer;
import com.client.ui.DevConsole;
import com.client.utilities.*;
import com.client.utilities.settings.Settings;
import com.client.utilities.settings.SettingsManager;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.client.util.FileUtility;
import dorkbox.notify.Notify;
import dorkbox.notify.Pos;
import dorkbox.util.ActionHandler;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.api.Tile;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.http.api.feed.FeedItem;
import net.runelite.http.api.feed.FeedItemType;
import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.runelite.api.*;
import net.runelite.api.clan.ClanRank;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.hooks.Callbacks;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.api.vars.AccountType;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.rs.api.*;
import com.client.engine.GameEngine;

@Slf4j
public class Client extends GameEngine implements RSClient {

    public boolean sliderShowAlpha;
	public Account lastAccount;
	public int DamageDealer;
	public HashMap<Integer, Integer> cosmetic_cost = new HashMap<>();

	public final void init() {
		Signlink.createCacheDirectory();
		try {
			enableExceptionLogging(); // Don't remove this!
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		nodeID = 1;
		portOff = 0;
		setHighMem();
		isMembers = true;
		Sprite.start();
		Signlink.storeid = 32;
		try {
			Signlink.startpriv(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}

		instance = this;
		newSmallFont = new RSFont(false, "p11_full");
		newBoldFont = new RSFont(false, "b12_full");

		startThread(765, 503, 206, 1);
		setMaxCanvasSize(765, 503);
	}

	@Override
	protected void vmethod1099() {

	}

	private int[] aintIntTest = new int[5];
	protected final void resizeGame() {
		Client.instance.setBounds();
	}

	private static final Logger logger = LoggerFactory.getLogger("Client");
	public String lastViewedDropTable;

	public boolean isServerUpdating() {
		return anInt1104 > 0;
	}

	public static int[] regions;
	public static int region;

	public DevConsole devConsole = new DevConsole();
	public int scrollbar_position;
	int scrollMax = 0;
	public void draw_scrollbar(int x, int y, int width, int height, int offset, int max_lines, int height_seperator, int y_pad)
	{
		scrollMax = (offset * max_lines + (height_seperator - 1));
		if(scrollMax < height - 3)
			scrollMax = height - 3;

		chatboxInterface.scrollPosition = scrollMax - scrollbar_position - (height - 4);
		if(scrollMax > height)
			handleScroller(x, height, MouseHandler.mouseX - 0, MouseHandler.mouseY - y, chatboxInterface, y_pad, true, scrollMax);

		int pos = scrollMax - (height - 4) - chatboxInterface.scrollPosition;
		if(pos < 0)
			pos = 0;

		if(pos > scrollMax - (height - 4))
			pos = scrollMax - (height - 4);

		if(scrollbar_position != pos)
			scrollbar_position = pos;

		drawScrollbar(height, scrollMax - scrollbar_position - (height - 1), x, y, scrollMax);
	}

	private void drawGrid() {
		for (int index = 0; index < 516; index += 10) {
			if (index < 334) {
				Rasterizer2D.drawHorizontalLine2(0, index, 516, 0xff0000);
			}
			Rasterizer2D.drawVerticalLine2(index, 0, 334, 0xff0000);
		}

		int xPos = MouseHandler.mouseX - 4 - ((MouseHandler.mouseX - 4) % 10);
		int yPos = MouseHandler.mouseY - 4 - ((MouseHandler.mouseY - 4) % 10);

		Rasterizer2D.drawTransparentBox(xPos, yPos, 10, 10, 0xffffff, 255);
		newSmallFont.drawCenteredString("(" + (xPos + 4) + ", " + (yPos + 4) + ")", xPos + 4, yPos - 1, 0xffff00, 0);
	}

	private boolean screenFlashDrawing;
	private boolean screenFlashOpacityDownward;
	private double screenFlashOpacity = 0d;
	private int screenFlashColor = 0;
	private int screenFlashMaxIntensity = 30;
	private boolean screenFlashAutoFadeOut;

	public boolean fogEnabled;
	public int fogOpacity;

	private final Object modelLoaderLock = new Object();
	@SneakyThrows
	public Model loadModelData(int id) {
		return Model.getModel(id);
		/*assert isClientThread() : "Model Data must be loaded on client thread";
		Model modelData = (Model) this.tmpModelDataCache.get(id);
		synchronized (modelLoaderLock) {
			if (modelData == null) {
				modelData = Model.getModel(id);
				while (!Model.isCached(id)) {
					processOnDemandQueue();
					modelLoaderLock.wait(2);
				}

				if (modelData == null) {
					return null;
				}
				System.out.println("Found model data for model " + id);
				this.tmpModelDataCache.put(modelData, id);
			}
		}
		return modelData;*/
	}

	@Override
	public String[] playerlist() {
		return friendsList;
	}

	public void drawScreenBox() {
		if (screenFlashDrawing) {
			double rate = 1_000d / fps / 20d;
			if (screenFlashOpacityDownward) {
				screenFlashOpacity -= rate;
				if (screenFlashOpacity <= 10) {
					screenFlashOpacityDownward = false;
					screenFlashDrawing = false;
				}
			} else {
				screenFlashOpacity += rate;
				if (screenFlashOpacity >= screenFlashMaxIntensity) {
					screenFlashOpacityDownward = true;
				}
			}
			Rasterizer2D.drawAlphaGradient(0, 0, canvasWidth, canvasHeight, screenFlashColor, screenFlashColor, (int) screenFlashOpacity);
		}

		if (fogEnabled) {
			Rasterizer2D.drawAlphaBox(0, 0, canvasWidth, canvasHeight, 0xE67D2D, fogOpacity);
		}
	}

	public int getDragSetting(int interfaceId) {
		return dragDelay;//interfaceId == 3214 ? Preferences.getPreferences().dragTime : 5;
	}

	public static int IDLE_TIME = 30000; // 1 minute = 3000
	public boolean hintMenu;
	public String hintName;
	public int hintId;

	private boolean pollActive;

	private Pattern pattern;

	private Matcher matcher;

	private boolean placeHolders = true;

	public static boolean debugModels = false;

	private EntityTarget entityTarget;

	private InformationFile informationFile = new InformationFile();

	public static boolean snowVisible = Configuration.CHRISTMAS ? true : false;

	public static int[][] runePouch = new int[][] { { -1, -1 }, { -1, -1 }, { -1, -1 } };

	public static int itemX = 0, itemY = 0;

	private static boolean shiftDrop = true;

	public static boolean shiftDown;

	private static boolean removeShiftDropOnMenuOpen;

	public static Settings getUserSettings() {
		return userSettings;
	}

	public static void setUserSettings(Settings settings) {
		Client.userSettings = settings;
	}

	private static Settings userSettings;


	public static boolean hidelocalnpcs, hideentities, hideothers, hideothers2d, hidefriends, hidefriendclan,
			hideignores, hidelocalplayers, hidelocal2dplayers, hide2dnpc, hidepets, hideprojectiles;

	/*
	 * @Override public void keyPressed(KeyEvent event) { MouseHandler.keyPressed(event);
	 * if(loggedIn) { stream.createFrame(186); stream.writeWord(event.getKeyCode());
	 * } }
	 */
	public static void mouseMoved() {
		if (loggedIn)
			// if(idleTime >= (Client.IDLE_TIME - 500))
			stream.createFrame(187);
	}

	/**
	 * Handles rune pouch input
	 * @return
	 * @author Sky
	 */
	private void loadPlayerData() throws IOException {
		File file = new File(Signlink.getCacheDirectory() + "/fkeys.dat");
		if (!file.exists()) {
			return;
		}

		DataInputStream stream = new DataInputStream(new FileInputStream(file));

		try {


			for(int i = 0; i < Keybinding.KEYBINDINGS.length; i++) {
				Keybinding.KEYBINDINGS[i] = stream.readByte();
			}

		} catch (IOException e) {
			System.out.println("Unable to load player data.");
			file.delete();
		} finally {
			stream.close();
		}
	}
	public void savePlayerData() {
		try {
			File file = new File(Signlink.getCacheDirectory() + "/fkeys.dat");
			if (!file.exists()) {
				file.createNewFile();
			}
			DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));

			try {
				for(int i = 0; i < 14; i++) {
					stream.writeByte(Keybinding.KEYBINDINGS[i]);
				}
			} finally {
				stream.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleScrollPosition(String text, int frame) {
		if (text.startsWith(":scp:")) {
			int scrollPosition = Integer.parseInt(text.split(" ")[1]);
			RSInterface widget = RSInterface.interfaceCache[frame];
			widget.scrollPosition = scrollPosition;
		} else if (text.startsWith(":scpfind:")) {
			RSInterface widget = RSInterface.interfaceCache[frame];
			sendString(7, widget.scrollPosition + "");
		}

		return;
	}
	public static boolean handleRunePouch(String text, int frame) {
		if (!(text.startsWith("#") && text.endsWith("$") && frame == 49999)) {
			return false;
		}
		try {
			// System.out.println("got here");
			text = text.replace("#", "");
			text = text.replace("$", "");
			String[] runes = text.split("-");
			for (int index = 0; index < runes.length; index++) {
				String[] args = runes[index].split(":");
				int id = Integer.parseInt(args[0]);
				int amt = Integer.parseInt(args[1]);
				runePouch[index][0] = id;
				runePouch[index][1] = amt;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	private boolean updateGameMode = false;

	public void frameMode(boolean resizable) {
		if(isResized() == resizable) {
			return;
		}

		setResized(resizable);

		Bounds bounds = getFrameContentBounds();
		canvasWidth = !isResized() ? 765 : bounds.highX;
		canvasHeight = !isResized() ? 503 : bounds.highY;
		cameraZoom = !isResized() ? 600 : 850;
		changeTabArea = !isResized() ? false : true;
		changeChatArea = !isResized() ? false : true;
		setMaxCanvasSize(canvasWidth, canvasHeight);
		ResizeableChanged event = new ResizeableChanged();
		event.setResized(resizable);
		callbacks.post(event);

		setBounds();

		showChatComponents = !isResized() || showChatComponents;
		showTabComponents = !isResized() || showTabComponents;
	}

	private void setBounds() {
		Preferences.getPreferences().fixed = !isResized();
		Preferences.getPreferences().screenWidth = canvasWidth;
		Preferences.getPreferences().screenHeight = canvasHeight;

		Rasterizer3D.reposition(canvasWidth, canvasHeight);
		fullScreenTextureArray = Rasterizer3D.scanOffsets;
		anIntArray1180 = Rasterizer3D.scanOffsets;
		anIntArray1181 = Rasterizer3D.scanOffsets;
		Rasterizer3D.scanOffsets = new int[canvasHeight];
		for (int x = 0; x < canvasHeight; x++) {
			Rasterizer3D.scanOffsets[x] = canvasWidth * x;
		}
		anIntArray1182 = Rasterizer3D.scanOffsets;
		Rasterizer3D.originViewX = instance.getViewportWidth() / 2;
		Rasterizer3D.originViewY = instance.getViewportHeight() / 2;

		Rasterizer3D.fieldOfView = instance.getViewportWidth() * instance.getViewportHeight() / 85504 << 1;

		int[] ai = new int[9];
		for (int i8 = 0; i8 < 9; i8++) {
			int k8 = 128 + i8 * 32 + 15;
			int l8 = 600 + k8 * 3;
			int i9 = Rasterizer3D.SINE[k8];
			ai[i8] = (l8 * i9 >> 16);
		}
		SceneGraph.buildVisibilityMap(500, 800, instance.getViewportWidth(), instance.getViewportHeight(), ai);
	}


	private long experienceCounter;
	private Sprite mapBack;
	public Sprite[] smallXpSprites = new Sprite[25];
	public Sprite[] skillOrbs = new Sprite[2];
	private static final long serialVersionUID = 1L;
	private Sprite[] inputSprites = new Sprite[7];

	private int modifiableXValue = 0; // u dont care if it starts at 1? Can't see a real problem with it :P kk
	private int achievementCutoff = 100;
	private Sprite[] minimapIcons = new Sprite[2];
	private String macAddress;

	public static void dumpModels() {
		int id = 6610;
		NpcDefinition npc = NpcDefinition.lookup(id);
		System.out.println(npc.name);
		if (npc.models != null) {
			for (int modelid = 0; modelid < npc.models.length; modelid++) {
				System.out.println(npc.models[modelid]);
			}
		}
	}

	private static String intToKOrMilLongName(int i) {
		String s = String.valueOf(i);
		for (int k = s.length() - 3; k > 0; k -= 3)
			s = s.substring(0, k) + "," + s.substring(k);
		if (s.length() > 8)
			s = "@gre@" + s.substring(0, s.length() - 8) + " million @whi@(" + s + ")";
		else if (s.length() > 4)
			s = "@cya@" + s.substring(0, s.length() - 4) + "K @whi@(" + s + ")";
		return " " + s;
	}

	private static String intToKOrMil(int j) {
		if (j < 0x186a0)
			return String.valueOf(j);
		if (j < 0x989680)
			return j / 1000 + "K";
		else
			return j / 0xf4240 + "M";
	}

	public void stopMidi() {
		Signlink.midifade = 0;
		Signlink.midi = "stop";
	}

	public static int spellID = 0;

	public static void setTab(int id) {
		needDrawTabArea = true;
		tabID = id;
		tabAreaAltered = true;
	}

	public void setSidebarInterface(int sidebarID, int interfaceID) {
		tabInterfaceIDs[sidebarID] = interfaceID;
		tabID = sidebarID;
		needDrawTabArea = true;
		tabAreaAltered = true;
	}

	private boolean menuHasAddFriend(int j) {
		if (j < 0)
			return false;
		int k = menuManager.getMenuEntries()[j].getOpcode();//menuActionOpcode[j];
		if (k >= 2000)
			k -= 2000;
		return k == 337;
	}

	private Sprite channelButtons;

	private void drawChannelButtons() {

		String text[] = { "On", "Friends", "Off", "Hide" };
		int textColor[] = { 65280, 0xffff00, 0xff0000, 65535 };
		int yOffset = (!isResized() ? 338 : canvasHeight - 165);

		if (hideChatArea) {
			channelButtons.drawSprite(0, canvasHeight - 23);
		} else {
			getOldSchoolSprite(1018).drawSprite(0, 142 + yOffset);
		}
		getOldSchoolSprite(3051).drawSprite(5, 142 + yOffset);
		getOldSchoolSprite(3051).drawSprite(71, 142 + yOffset);
		getOldSchoolSprite(3051).drawSprite(137, 142 + yOffset);
		getOldSchoolSprite(3051).drawSprite(203, 142 + yOffset);
		getOldSchoolSprite(3051).drawSprite(269, 142 + yOffset);
		getOldSchoolSprite(3051).drawSprite(335, 142 + yOffset);
		getOldSchoolSprite(1024).drawSprite(404, 142 + yOffset);

		switch (channelButtonClickPosition) {
			case 0:
				getOldSchoolSprite(3053).drawSprite(5, 142 + yOffset);
				break;
			case 1:
				getOldSchoolSprite(3053).drawSprite(71, 142 + yOffset);
				break;
			case 2:
				getOldSchoolSprite(3053).drawSprite(137, 142 + yOffset);
				break;
			case 3:
				getOldSchoolSprite(3053).drawSprite(203, 142 + yOffset);
				break;
			case 4:
				getOldSchoolSprite(3053).drawSprite(269, 142 + yOffset);
				break;
			case 5:
				getOldSchoolSprite(3053).drawSprite(335, 142 + yOffset);
				break;
		}
		if (channelButtonHoverPosition == channelButtonClickPosition) {
			switch (channelButtonHoverPosition) {
				case 0:
					getOldSchoolSprite(3054).drawSprite(5, 142 + yOffset);
					break;
				case 1:
					getOldSchoolSprite(3054).drawSprite(71, 142 + yOffset);
					break;
				case 2:
					getOldSchoolSprite(3054).drawSprite(137, 142 + yOffset);
					break;
				case 3:
					getOldSchoolSprite(3054).drawSprite(203, 142 + yOffset);
					break;
				case 4:
					getOldSchoolSprite(3054).drawSprite(269, 142 + yOffset);
					break;
				case 5:
					getOldSchoolSprite(3054).drawSprite(335, 142 + yOffset);
					break;
				case 6:
					getOldSchoolSprite(1025).drawSprite(404, 142 + yOffset);
					break;
			}
		} else {
			switch (channelButtonHoverPosition) {
				case 0:
					getOldSchoolSprite(3052).drawSprite(5, 142 + yOffset);
					break;
				case 1:
					getOldSchoolSprite(3052).drawSprite(71, 142 + yOffset);
					break;
				case 2:
					getOldSchoolSprite(3052).drawSprite(137, 142 + yOffset);
					break;
				case 3:
					getOldSchoolSprite(3052).drawSprite(203, 142 + yOffset);
					break;
				case 4:
					getOldSchoolSprite(3052).drawSprite(269, 142 + yOffset);
					break;
				case 5:
					getOldSchoolSprite(3052).drawSprite(335, 142 + yOffset);
					break;
				case 6:
					getOldSchoolSprite(1025).drawSprite(404, 142 + yOffset);
					break;
			}
		}
		/**
		 * Simple format.
		 */
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
		/**
		 * Sets timezone to USA
		 */
		dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		/**
		 * Updates button per tick.
		 */
		newSmallFont.drawCenteredString(""+dateFormat.format(new Date().getTime()), 459, 157 + yOffset, 0xffffff, 0);


		smallText.method389(true, 26, 0xffffff, "All", 157 + yOffset);
		smallText.method389(true, 86, 0xffffff, "Game", 152 + yOffset);
		smallText.method389(true, 150, 0xffffff, "Public", 152 + yOffset);
		smallText.method389(true, 212, 0xffffff, "Private", 152 + yOffset);
		smallText.method389(true, 286, 0xffffff, "Clan", 152 + yOffset);
		smallText.method389(true, 349, 0xffffff, "Trade", 152 + yOffset);
		smallText.method382(textColor[gameMode], 98, text[gameMode], 163 + yOffset, true);
		smallText.method382(textColor[publicChatMode], 164, text[publicChatMode], 163 + yOffset, true);
		smallText.method382(textColor[privateChatMode], 230, text[privateChatMode], 163 + yOffset, true);
		smallText.method382(textColor[clanChatMode], 296, text[clanChatMode], 163 + yOffset, true);
		smallText.method382(textColor[tradeMode], 362, text[tradeMode], 163 + yOffset, true);
	}

	ItemSearch grandExchangeItemSearch = null;

	public static int grandExchangeSearchScrollPostion;

	private static void handleGEItemSearchClick(int itemId) {
		// TODO Send Item Packet
		System.out.println("Clicking Item: " + itemId);
	}

	public static void updateInterface(int[] children, int parent) {
		if(children == null) {
			handleWidgetEvents(RSInterface.interfaceCache[parent], 0, 0);
			return;
		}
		for(int i = 0; i < children.length; i++) {
			handleWidgetEvents(RSInterface.interfaceCache[children[i]], RSInterface.interfaceCache[parent].childX[i], RSInterface.interfaceCache[parent].childY[i]);
		}

	}

	private static void handleWidgetEvents(RSInterface widget, int x, int y) {
		if(widget != null) {
			if(widget.onClickRepeat != null && widget.clicked) {
				widget.clicked = false;
				ScriptEvent scriptEvent = new ScriptEvent();
				scriptEvent.widget = widget;
				scriptEvent.args = widget.onClickRepeat;
				scriptEvents.addFirst(scriptEvent);
			}
			if(widget.onClick != null) {
				if(widget.onClick != null && widget.clicked) {
					widget.clicked = false;
					ScriptEvent scriptEvent = new ScriptEvent();
					scriptEvent.widget = widget;
					scriptEvent.args = widget.onClick;
					scriptEvents.addFirst(scriptEvent);
				}
			}
		}
	}

	private void drawChatArea() {
		if (!isResized())
			hideChatArea = false;

		int yOffset = (!isResized() ? 338 : canvasHeight - 165);
		final int yOffset2 = !isResized() ? 338 : 0;

		Rasterizer3D.scanOffsets = anIntArray1180;

		if (hideChatArea)
			drawChannelButtons();

		if (!hideChatArea) {
			getOldSchoolSprite(1017).drawSprite(0, yOffset);
			drawChannelButtons();

			TextDrawingArea textDrawingArea = aTextDrawingArea_1271;
			if (MouseHandler.saveClickX >= 0 && MouseHandler.saveClickX <= 518) {
				if (MouseHandler.saveClickY >= (!isResized() ? 335 : canvasHeight - 164)
						&& MouseHandler.saveClickY <= (!isResized() ? 484 : canvasHeight - 30)) {
					if (this.isFieldInFocus()) {
						Client.inputString = "";
						this.resetInputFieldFocus();
					}
				}
			}
			if (messagePromptRaised) {
				newBoldFont.drawCenteredString(aString1121, 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(promptInput + "*", 259, 80 + yOffset, 128, -1);
			} else if (inputDialogState == 1) {
				newBoldFont.drawCenteredString(enterInputInChatString, 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(amountOrNameInput + "*", 259, 80 + yOffset, 128, -1);
			} else if (inputDialogState == 2) {
				newBoldFont.drawCenteredString(enterInputInChatString, 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(amountOrNameInput + "*", 259, 80 + yOffset, 128, -1);
			} else if (inputDialogState == 7) {
				newBoldFont.drawCenteredString("Enter the price for the item:", 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(amountOrNameInput + "*", 259, 80 + yOffset, 128, -1);
			} else if (inputDialogState == 8) {
				newBoldFont.drawCenteredString("Amount you want to sell:", 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(amountOrNameInput + "*", 259, 80 + yOffset, 128, -1);
			} else if (Bank.isSearchingBank()) {
				newBoldFont.drawCenteredString("Enter an item to search for:", 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(Bank.searchingBankString + "*", 259, 80 + yOffset, 128, -1);
			} else if (inputDialogState == 3) {
				Rasterizer2D.fillPixels(8, 505, 108, 0x463214, 28 + yOffset);
				Rasterizer2D.drawAlphaBox(8, 28 + yOffset, 505, 108, 0x746346, 75);

				newBoldFont.drawCenteredString("@bla@What would you like to buy? @blu@" + amountOrNameInput + "*", 259,
						20 + yOffset, 128, -1);
				if (!Objects.equals(amountOrNameInput, "")) {
					grandExchangeItemSearch = new ItemSearch(amountOrNameInput, 100, true);

					final int xPosition = 15;
					final int yPosition = 32 + yOffset - grandExchangeSearchScrollPostion;
					int rowCountX = 0;
					int rowCountY = 0;

					int itemAmount = grandExchangeItemSearch.getItemSearchResultAmount();

					if (amountOrNameInput.length() == 0) {
						newRegularFont.drawCenteredString("Start typing the name of an item to search for it.", 259,
								70 + yOffset, 0, -1);
					} else if (itemAmount == 0) {
						newRegularFont.drawCenteredString("No matching items found!", 259, 70 + yOffset, 0, -1);
					} else {
						Rasterizer2D.setDrawingArea(134 + yOffset, 8, 497, 29 + yOffset);
						for (int itemId = 0; itemId < itemAmount; itemId++) {
							int itemResults[] = grandExchangeItemSearch.getItemSearchResults();

							if (itemResults[itemId] != -1) {
								final int startX = xPosition + rowCountX * 160;
								final int startY = yPosition + rowCountY * 35;
								Sprite itemSprite = ItemDefinition.getSprite(itemResults[itemId], 1, 0);
								if (itemSprite != null)
									itemSprite.drawSprite(startX, startY);

								ItemDefinition itemDef = ItemDefinition.lookup(itemResults[itemId]);
								newRegularFont.drawBasicString(itemDef.name, startX + 40, startY + 14, 0, -1);

								if (MouseHandler.mouseX >= startX && MouseHandler.mouseX <= startX + 160) {
									if (MouseHandler.mouseY >= (startY + yOffset2)
											&& MouseHandler.mouseY <= (startY + yOffset2) + 35) {
										Rasterizer2D.drawAlphaBox(startX, startY, 160, 35, 0xFFFFFF, 120);

										if (clickMode3 == 1)
											handleGEItemSearchClick(itemDef.id);
									}
								}
							}

							rowCountX++;

							if (rowCountX > 2) {
								rowCountY++;
								rowCountX = 0;
							}
						}
						Rasterizer2D.defaultDrawingAreaSize();
					}

					int maxScrollPosition = itemAmount / 3 * 35;
					if (itemAmount > 9)
						drawScrollbar(106, grandExchangeSearchScrollPostion > maxScrollPosition ? 0
								: grandExchangeSearchScrollPostion, 29 + yOffset, 496, itemAmount / 3 * 35);

				}
			} else if (clickToContinueString != null) {
				newBoldFont.drawCenteredString(clickToContinueString, 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString("Click to continue", 259, 80 + yOffset, 128, -1);
			} else if (backDialogID != -1) {
				drawInterface(0, 20, RSInterface.interfaceCache[backDialogID], 20 + yOffset);
			} else if (dialogID != -1) {
				drawInterface(0, 20, RSInterface.interfaceCache[dialogID], 20 + yOffset);
			} else {
				int messageCount = 0;
				Rasterizer2D.setDrawingArea(122 + yOffset, 8, 497, 7 + yOffset);
				for (int k = 0; k < 500; k++)
					if (chatMessages[k] != null && chatMessages[k].getMessage() != null) {
						int chatType = chatMessages[k].type;
						int yPos = (111 - messageCount * 14) + anInt1089 + 6;
						String s1 = chatMessages[k].getName();

						List<Integer> crowns = new ArrayList<>();

						while (s1.startsWith("@cr")) {
							String s2 = s1.substring(3, s1.length());
							int index = s2.indexOf("@");
							if (index != -1) {
								s2 = s2.substring(0, index);
								crowns.add(Integer.parseInt(s2));
								s1 = s1.substring(4 + s2.length());
							}
						}
						if (chatType == 0 || chatType == 99) {
							if (chatTypeView == 5 || chatTypeView == 0) {
								int lineCount = 1;
								if (yPos > 0 && yPos < 210) {//484
									String chatMessage = chatMessages[k].getMessage();
									if(chatMessage.startsWith("<url>")) {
										chatMessage = chatMessage.replaceFirst("<url>", "");
										chatMessage = "<osrsimg=2168>" + chatMessage.substring(chatMessage.indexOf(">") + 1, chatMessage.length());
										if (newRegularFont.getTextWidth(chatMessage) > 480) {
											int lines = newRegularFont.paragraphHeigth(chatMessage, 480);
											lineCount = newRegularFont.drawInterfaceText(chatMessage, 10, yPos - 1 + yOffset - (lines == 3 ? 18 : 12),
													480, 0, chatMessages[k].hovered ? 0xe69943 : 0, -1, 255, 0, 1, 14);
										} else {
											newRegularFont.drawBasicString(chatMessage, 10, yPos - 1 + yOffset, chatMessages[k].hovered ? 0xe69943 : 0, -1);
										}
									} else {
										if (newRegularFont.getTextWidth(chatMessage) > 480) {
											int lines = newRegularFont.paragraphHeigth(chatMessage, 480);
											lineCount = newRegularFont.drawInterfaceText(chatMessage, 10, yPos - 1 + yOffset - (lines == 3 ? 18 : 12),
													480, 0, 0, -1, 255, 0, 1, 14);
										} else {
											newRegularFont.drawBasicString(chatMessage, 10, yPos - 1 + yOffset, 0, -1);
										}
									}
								}
								messageCount += lineCount;
							}
						}
						if ((chatType == 1 || chatType == 2) && (chatType == 1 || publicChatMode == 0
								|| publicChatMode == 1 && isFriendOrSelf(s1))) {
							if (chatTypeView == 1 || chatTypeView == 0) {
								int lineCount = 1;
								if (yPos > 0 && yPos < 210) {
									int xPos = 11;
									if (!crowns.isEmpty()) {
										for (int crown : crowns) {
											Sprite modcon = handleIcon(crown, xPos, yPos, 1, yOffset+2);
											if (modcon != null) {
												xPos += modcon.myWidth;
											}
											xPos += 2;
										}
									}

									newRegularFont.drawBasicString(s1 + ":", xPos - 1, yPos - 1 + yOffset, 0, -1);
									xPos += newRegularFont.getTextWidth(s1) + 5;
										String chatMessage = chatMessages[k].getMessage();
											newRegularFont.drawBasicString(chatMessage, xPos - 1, yPos - 1 + yOffset, 255,-1, false);
								}
								messageCount += lineCount;
							}
						}


						if ((chatType == 3 || chatType == 7) && (!splitPrivateChat || chatTypeView == 2)
								&& (chatType == 7 || privateChatMode == 0
								|| privateChatMode == 1 && isFriendOrSelf(s1))) {
							if (chatTypeView == 2 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210) {
									int k1 = 11;
									newRegularFont.drawBasicString("From", k1, yPos - 1 + yOffset, 0, -1);
									k1 += textDrawingArea.getTextWidth("From ");
									if (!crowns.isEmpty()) {
										for (int crown : crowns) {
											Sprite modcon = handleIcon(crown, k1, yPos, 1, yOffset+2);
											if (modcon != null) {
												k1 += modcon.myWidth;
											}
											k1 += 2;
										}
									}
									newRegularFont.drawBasicString(s1 + ":", k1, yPos - 1 + yOffset, 0, -1);
									k1 += newRegularFont.getTextWidth(s1) + 8;
									newRegularFont.drawBasicString(chatMessages[k].getMessage(), k1, yPos - 1 + yOffset, 0x800000,-1, false);
								}
								messageCount++;
							}
						}


						if (chatType == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
							if (chatTypeView == 3 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210)
									newRegularFont.drawBasicString(s1 + " " + chatMessages[k].getMessage(), 11, yPos - 1 + yOffset,0x800080, -1, false);
								messageCount++;
							}
						}
						if (chatType == 31 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
							int crownId = 29;
							if (chatTypeView == 3 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210) {
									int xPos = 11;
									modIcons[crownId].drawAdvancedSprite(xPos - 1, yPos + yOffset - modIcons[crownId].myHeight);
									xPos += modIcons[crownId].myWidth;
									newRegularFont.drawBasicString(s1 + " " + chatMessages[k].getMessage(), xPos - 1, yPos - 1 + yOffset, 0x1950ce, -1, false);
								}
								messageCount++;
							}
						}
						if (chatType == 5 && !splitPrivateChat && privateChatMode < 2) {
							if (chatTypeView == 2 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210)
									newRegularFont.drawBasicString(chatMessages[k].getMessage(), 10, yPos - 1 + yOffset, 0x800000,-1, false);
								messageCount++;
							}
						}
						if (chatType == 6 && (!splitPrivateChat || chatTypeView == 2) && privateChatMode < 2) {
							if (chatTypeView == 2 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210) {
									int k1 = 15;
									k1 += textDrawingArea.getTextWidth("To " + s1);
									newRegularFont.drawBasicString("To " + s1 + ":", 10, yPos - 1 + yOffset, 0, -1);
									// newRegularFont.drawBasicString(chatMessages[k], 15 +
									// newRegularFont.getTextWidth("To :" + s1), yPos, 0x800000, -1);
									newRegularFont.drawBasicString(chatMessages[k].getMessage(), k1, yPos - 1 + yOffset, 0x800000,-1, false);
								}
								messageCount++;
							}
						}

						// Clan chat
						if (chatType == 11 && (clanChatMode == 0)) {
							if (chatTypeView == 11 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210) {
									try {
										String lastChatNameEnd = ":";
										String clanChatMessage = RSFont.handleOldSyntax(chatMessages[k].getMessage());
										String chatMessage = clanChatMessage.substring(clanChatMessage.indexOf(lastChatNameEnd) + lastChatNameEnd.length());
										String usernameAndFormatting = clanChatMessage.substring(0, clanChatMessage.indexOf(lastChatNameEnd) + lastChatNameEnd.length());
										newRegularFont.drawBasicString(usernameAndFormatting, 8, yPos - 1 + yOffset, 0x7e3200, -1, true);
										newRegularFont.drawBasicString(chatMessage, newRegularFont.getTextWidth(usernameAndFormatting) + 8, yPos - 1 + yOffset, 0x7e3200, -1, true);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								messageCount++;
							}
						}
						if (chatType == 13 && chatTypeView == 12) {
							if (yPos > 0 && yPos < 210)
								newRegularFont.drawBasicString(s1 + " " + chatMessages[k].getMessage(), 8, yPos - 1 + yOffset,0x7e3200, -1, false);
							messageCount++;
						}
						if (chatType == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
							if (chatTypeView == 3 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210)
									newRegularFont.drawBasicString(s1 + " " + chatMessages[k].getMessage(), 10, yPos - 1 + yOffset,0x7e3200, -1, false);
								messageCount++;
							}
						}
						if (chatType == 16) {
							int j2 = 40 + 11;
							String clanname = clanList[k];
							int clanNameWidth = textDrawingArea.getTextWidth(clanname);
							if (chatTypeView == 11 || chatTypeView == 0) {
								newRegularFont.drawBasicString("[", 19, yPos - 1 + yOffset, 0, -1);
								newRegularFont.drawBasicString("]", clanNameWidth + 16 + 10, yPos - 1 + yOffset, 0, -1);
								newRegularFont.drawBasicString("" + capitalize(clanname) + "", 25, yPos - 1 + yOffset,
										255, -1);
								newRegularFont.drawBasicString(chatMessages[k].getName() + ":", j2 - 17, yPos);
								j2 += newRegularFont.getTextWidth(chatMessages[k].getName()) + 7;
								newRegularFont.drawBasicString(capitalize(chatMessages[k].getMessage()), j2 - 16, yPos - 1 + yOffset,0x800000, -1);
								messageCount++;
							}
						}
					}
				rasterProvider.setRaster();
				chatAreaScrollLength = messageCount * 14;
				if (chatAreaScrollLength < 111)
					chatAreaScrollLength = 111;
				drawScrollbar(114, (chatAreaScrollLength - anInt1089 - 113) < 0 ? 0 : chatAreaScrollLength - anInt1089 - 113, 7 + yOffset, 496, chatAreaScrollLength);
				String fixedString;
				if (localPlayer != null && localPlayer.displayName != null) {
					if (localPlayer.title.length() > 0) {
						fixedString = "<col=" + localPlayer.titleColor + ">" + localPlayer.title + "</col>" + " "
								+ localPlayer.displayName;
					} else {
						fixedString = localPlayer.displayName;
					}
				} else {
					fixedString = StringUtils.fixName(capitalize(myUsername));
				}

				int xOffset = 0;
				if (localPlayer.hasRightsOtherThan(PlayerRights.PLAYER)) {
					for (PlayerRights right : localPlayer.getDisplayedRights()) {
						if (right.hasCrown()) {
							Sprite modcon = handleIcon3(right.crownId(), 9, 137, xOffset, yOffset);
							if (modcon != null) {
								xOffset += modcon.myWidth;
							}
							xOffset += 2;
						}
					}

					newRegularFont.drawBasicString(fixedString, 10 + xOffset, 133 + yOffset, 0, -1);
				} else {
					newRegularFont.drawBasicString(fixedString, 10 + xOffset, 133 + yOffset, 0, -1);
				}

				xOffset += newRegularFont.getTextWidth(fixedString) + 10;
				newRegularFont.drawBasicString(": ", xOffset, 133 + yOffset, 0, -1);
				xOffset += textDrawingArea.getTextWidth(": ");

				if (!isFieldInFocus() && !isChatboxLocked()) {
					newRegularFont.drawBasicString(inputString + "*",xOffset, 133 + yOffset, 255, -1, false);
				} else {
					newRegularFont.drawBasicString("[Click chat box to enable]",xOffset, 133 + yOffset, 255, -1, false);
				}

				Rasterizer2D.method339(120 + yOffset, 0x807660, 506, 7);
			}

		}
		if (menuOpen) {
			drawMenu(menuOffsetX, menuOffsetY);
		}
		rasterProvider.setRaster();
		Rasterizer3D.scanOffsets = anIntArray1182;
	}

	private String clanUsername;
	private String clanMessage;
	public String clanTitle;
	private EnumSet channelRights;
	/**Handle Chatbox Icon's**/
	public Sprite handleIcon(int crown, int xPos, int yPos, int xOffset, int yOffset) {
		int icon = -1; // Default icon
		switch (crown) {
			case 11: // HELPER
				icon = 38;
				break;
			case 1: // MOD
				icon = 39;
				break;
			case 2: // OWNER/ADMIN
				icon = 37;
				break;
			case 16: // OWNER/ADMIN
				icon = 15;
				break;
			case 3: // OWNER/ADMIN
			case 4: // OWNER/ADMIN
				icon = 36;
				break;
			case 5: // $20
				icon = 48;
				break;
			case 7: // $50
				icon = 47;
				break;
			case 8: // $1000
				icon = 42;
				break;
			case 9: // $100
				icon = 46;
				break;
			case 17: // $250
				icon = 45;
				break;
			case 18: // $500
				icon = 44;
				break;
			case 30: // Event Manager
				icon = 28;
				break;
			case 32: // $750
				icon = 43;
				break;
			case 33: // $1500
				icon = 41;
				break;
			case 34: // $2000
				icon = 40;
				break;
			case 35:
				icon = 49;
				break;
		}
		if(icon != -1) {
			Sprite modcon = modIcons[icon];
			if (icon >= 40) {
				modcon = SpriteLoader.fetchAnimatedSprite(Signlink.getCacheDirectory() + "/sprites/gifs/" + icon + ".gif").getInstance(modcon.myWidth, modcon.myHeight);
			}
			if (modcon != null)
				modcon.drawAdvancedSprite(xPos - 1, yPos + yOffset - modcon.myHeight);
			return modcon;
		}
		return null;
	}

	/**Handle Chatbox Icon's for friends list**/
	public Sprite handleIcon2(int crown, int xPos, int yPos, int xOffset, int yOffset) {
		int icon = -1; // Default icon
		switch (crown) {
			case 11: // HELPER
				icon = 38;
				break;
			case 1: // MOD
				icon = 39;
				break;
			case 2: // OWNER/ADMIN
				icon = 37;
				break;
			case 16:
				icon = 15;
				break;
			case 3: // OWNER/ADMIN
			case 4: // OWNER/ADMIN
				icon = 36;
				break;
			case 5: // $20
				icon = 48;
				break;
			case 7: // $50
				icon = 47;
				break;
			case 8: // $1000
				icon = 42;
				break;
			case 9: // $100
				icon = 46;
				break;
			case 17: // $250
				icon = 45;
				break;
			case 18: // $500
				icon = 44;
				break;
			case 30: // Event Manager
				icon = 28;
				break;
			case 32: // $750
				icon = 43;
				break;
			case 33: // $1500
				icon = 41;
				break;
			case 34: // $2000
				icon = 40;
				break;
			case 35:
				icon = 49;
				break;
		}
		if (icon != -1 && modIcons[icon] != null) {
			Sprite modcon = modIcons[icon];
			if (icon >= 40) {
				modcon = SpriteLoader.fetchAnimatedSprite(Signlink.getCacheDirectory()+"/sprites/gifs/" +icon+".gif").getInstance(modcon.myWidth, modcon.myHeight);
				yOffset -=2;
			}
			modcon.drawAdvancedSprite(xPos, yPos + yOffset - modcon.myHeight);
			return modcon;
		}
		return null;
	}
	/**Handle Chatbox Icon Next to name where text is typed**/
	public Sprite handleIcon3(int crown, int xPos, int yPos, int xOffset, int yOffset) {
		int icon = -1; // Default icon
		switch (crown) {
			case 11: // HELPER
				icon = 38;
				break;
			case 1: // MOD
				icon = 39;
				break;
			case 2: // OWNER/ADMIN
				icon = 37;
				break;
			case 16:
				icon = 15;
				break;
			case 3: // OWNER/ADMIN
			case 4: // OWNER/ADMIN
				icon = 36;
				break;
			case 5: // $20
				icon = 48;
				break;
			case 7: // $50
				icon = 47;
				break;
			case 8: // $1000
				icon = 42;
				break;
			case 9: // $100
				icon = 46;
				break;
			case 17: // $250
				icon = 45;
				break;
			case 18: // $500
				icon = 44;
				break;
			case 30: // Event Manager
				icon = 28;
				break;
			case 32: // $750
				icon = 43;
				break;
			case 33: // $1500
				icon = 41;
				break;
			case 34: // $2000
				icon = 40;
				break;
			case 35:
				icon = 49;
				break;
		}
		if (icon != -1 && modIcons[icon] != null) {
			Sprite modcon = modIcons[icon];
			if (icon >= 40) {
				modcon = SpriteLoader.fetchAnimatedSprite(Signlink.getCacheDirectory()+"/sprites/gifs/" +icon+".gif").getInstance(modcon.myWidth, modcon.myHeight);
				yOffset -=2;
			}
			modcon.drawSprite(xPos + xOffset, yPos + yOffset - modcon.myHeight);
			return modcon;
		}
		return null;
	}



	public Socket openSocket(int port) throws IOException {
		return new Socket(InetAddress.getByName(server), port);
	}

	public boolean processMenuClick() {
		if (activeInterfaceType != 0)
			return false;
		int j = clickMode3;
		if (spellSelected == 1 && MouseHandler.saveClickX >= 516 && MouseHandler.saveClickY >= 160 && MouseHandler.saveClickX <= 765
				&& MouseHandler.saveClickY <= 205)
			j = 0;
		if (menuOpen) {
			menu();
			return true;
		} else {
			if (j == 1 && menuActionRow > 0) {
				int i1 = menuManager.getMenuEntry(menuActionRow - 1).getOpcode();;
				if (i1 == 632 || i1 == 78 || i1 == 867 || i1 == 431 || i1 == 53 || i1 == 74 || i1 == 454 || i1 == 539
						|| i1 == 493 || i1 == 847 || i1 == 447 || i1 == 1125) {
					int l1 = menuManager.getMenuEntry(menuActionRow - 1).getParam0();
					int j2 = menuManager.getMenuEntry(menuActionRow - 1).getParam1();
					RSInterface class9 = RSInterface.interfaceCache[j2];
					if (class9.aBoolean259 || class9.aBoolean235) {
						aBoolean1242 = false;
						anInt989 = 0;
						draggingItemInterfaceId = j2;
						itemDraggingSlot = l1;
						activeInterfaceType = 2;
						anInt1087 = MouseHandler.saveClickX;
						anInt1088 = MouseHandler.saveClickY;
						if (RSInterface.interfaceCache[j2].parentID == openInterfaceID)
							activeInterfaceType = 1;
						if (RSInterface.interfaceCache[j2].parentID == backDialogID)
							activeInterfaceType = 3;
						return true;
					}
				}
			}
			if (j == 1 && (anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2)
				j = 2;
			if (j == 1 && menuActionRow > 0)
				doAction(menuActionRow - 1);
			if (j == 2 && menuActionRow > 0)
				determineMenuSize();
			minimapHovers();

//			System.out.println("X: " +MouseHandler.mouseX + " / Y: " + MouseHandler.mouseY);
			return false;
		}
	}
	final void menu()
	{
		Client client = instance;
		boolean var1 = false;
		int var2;
		int var5;
		while (!var1)
		{
			var1 = true;
			for (var2 = 0; var2 < client.getMenuOptionCount() - 1; ++var2)
			{
				if (client.getMenuOpcodes()[var2] < 1000 && client.getMenuOpcodes()[var2 + 1] > 1000)
				{
					sortMenuEntries(var2, var2 + 1);
					var1 = false;
				}
			}
			if (var1 && !client.isMenuOpen())
			{
				client.getCallbacks().post(new PostMenuSort());
			}
		}

		if (client.getDraggedWidget() == null)
		{
			int var19 = client.getMouseLastButton();
			int var4;
			int var7;
			int var8;
			int var20;
			if (client.isMenuOpen())
			{
				int var3;
				if (var19 != 1 && (client.isMouseCam() || var19 != 4))
				{
					var2 = client.getMouseX();
					var3 = client.getMouseY();
					//this checks if the mouse is contained in the menu area
					boolean regmenuContainsMouse = (var2 >= client.getMenuX() - 10 && var2 <= client.getMenuX() + client.getMenuWidth() + 10 && var3 >= client.getMenuY() - 10 && var3 <= client.getMenuY() + client.getMenuHeight() + 10);
					boolean submenuContainsMouse = (var2 >= client.getSubmenuX() - 10 && var2 <= client.getSubmenuX() + client.getSubmenuWidth() + 10 && var3 >= client.getSubmenuY() - 10 && var3 <= client.getSubmenuY() + client.getSubmenuHeight() + 10);
					if (!regmenuContainsMouse && !submenuContainsMouse)
					{
						client.setMenuOpen(false);
						client.setSubmenuIdx(-1);
					}
					else if (!submenuContainsMouse)
					{
						client.setSubmenuIdx(-1);
					}

					if (client.isMenuOpen() && client.getMouseWheelRotation() != 0)
					{
						if (regmenuContainsMouse && menuScrollMax > 0)
						{
							client.setMenuScroll(menuScroll + client.getMouseWheelRotation());
							if (menuScroll < 0)
							{
								menuScroll = 0;
							}
							else if (menuScroll > menuScrollMax)
							{
								menuScroll = menuScrollMax;
							}
						}

						if (submenuContainsMouse && submenuScrollMax > 0)
						{
							client.setSubmenuScroll(submenuScroll + client.getMouseWheelRotation());
							if (submenuScroll < 0)
							{
								submenuScroll = 0;
							}
							else if (submenuScroll > submenuScrollMax)
							{
								submenuScroll = submenuScrollMax;
							}
						}
					}
				}
				if (var19 == 1 || !client.isMouseCam() && var19 == 4)
				{
					var7 = -1;

					net.runelite.api.MenuEntry[] entries = client.getMenuEntries();
					int rootMenuTotalCount = 0;
					int submenuTotalCount = 0;
					for (var8 = 0; var8 < entries.length; ++var8)
					{
						net.runelite.api.MenuEntry e = entries[var8];
						if (e.getParent() == null)
						{
							rootMenuTotalCount++;
						}
						else if (e.getParent().getIdx() == client.getSubmenuIdx())
						{
							submenuTotalCount++;
						}
					}
					int rootMenuCount = 0;
					int submenuCount = 0;
					for (var8 = 0; var8 < entries.length; ++var8)
					{
						net.runelite.api.MenuEntry e = entries[var8];
						if (e.getParent() == null)
						{
							int rowY = (rootMenuTotalCount - 1 - rootMenuCount - client.getMenuScroll()) * 15 + client.getMenuY() + 31;
							if (client.getMouseLastPressedX() > client.getMenuX() && client.getMouseLastPressedX() < client.getMenuWidth() + client.getMenuX() && client.getMouseLastPressedY() > rowY - 13 && client.getMouseLastPressedY() < rowY + 3)
							{
								var7 = var8;
							}
							rootMenuCount++;
						}
						else if (e.getParent().getIdx() == client.getSubmenuIdx())
						{
							int rowY = (submenuTotalCount - 1 - submenuCount - client.getSubmenuScroll()) * 15 + client.getSubmenuY() + 31;
							if (client.getMouseLastPressedX() > client.getSubmenuX() && client.getMouseLastPressedX() < client.getSubmenuWidth() + client.getSubmenuX() && client.getMouseLastPressedY() > rowY - 13 && client.getMouseLastPressedY() < rowY + 3)
							{
								var7 = var8;
							}
							submenuCount++;
						}
						if(e.getParent() != null)
							System.out.println("Clicked submenu=" + e.getParent().getIdx());
					}
					System.out.println("Selected submenu=" + client.getSubmenuIdx());

					clickedIdx = var7;
					if (clickedIdx >= 0)
					{
						doAction(clickedIdx);
					}

					client.setMenuOpen(false);
					client.setSubmenuIdx(-1);
				}
			}

			else
			{
				var2 = client.getMenuOptionCount() - 1;
				if ((var19 == 1 || !client.isMouseCam() && var19 == 4) && copy$shouldLeftClickOpenMenu())
				{
					var19 = 2;
				}

				if ((var19 == 1 || !client.isMouseCam() && var19 == 4) && client.getMenuOptionCount() > 0 && var2 >= 0)
				{
					doAction(var2);
				}

				if (var19 == 2 && client.getMenuOptionCount() > 0)
				{
					determineMenuSize();
				}
			}
		}

		if(rotation != 0)
			rotation = 0;
	}

	boolean copy$shouldLeftClickOpenMenu()
	{
		if (copy$shouldLeftClickOpenMenu())
		{
			return true;
		}

		MenuShouldLeftClick menuShouldLeftClick = new MenuShouldLeftClick();
		getCallbacks().post(menuShouldLeftClick);

		if (menuShouldLeftClick.isForceRightClick())
		{
			return true;
		}

		int len = getMenuOptionCount();
		if (len > 0)
		{
			int type = getMenuOpcodes()[len - 1];
			return type == MenuAction.RUNELITE_OVERLAY.getId() || type == MenuAction.RUNELITE_OVERLAY_CONFIG.getId() || type == MenuAction.RUNELITE_INFOBOX.getId();
		}

		return false;
	}

	private int getMouseWheelRotation() {
		final int rot = rotation;
		return rot;
	}

	public int getMouseLastPressedX() {
		return saveClickX;
	}
	public int getMouseLastPressedY() {
		return saveClickY;
	}

	private int getMouseLastButton() {
		return clickMode3;
	}

	private final void minimapHovers() {
		final boolean fixed = !isResized();
		prayHover = fixed
				? prayHover = MouseHandler.mouseX >= 517 && MouseHandler.mouseX <= 573
				&& MouseHandler.mouseY >= (Configuration.osbuddyGameframe ? 81 : 76)
				&& MouseHandler.mouseY < (Configuration.osbuddyGameframe ? 109 : 107)
				: MouseHandler.mouseX >= canvasWidth - 210 && MouseHandler.mouseX <= canvasWidth - 157
				&& MouseHandler.mouseY >= (Configuration.osbuddyGameframe ? 90 : 55)
				&& MouseHandler.mouseY < (Configuration.osbuddyGameframe ? 119 : 110);

		runHover = fixed
				? runHover = MouseHandler.mouseX >= (Configuration.osbuddyGameframe ? 530 : 522)
				&& MouseHandler.mouseX <= (Configuration.osbuddyGameframe ? 582 : 594)
				&& MouseHandler.mouseY >= (Configuration.osbuddyGameframe ? 114 : 109)
				&& MouseHandler.mouseY < (Configuration.osbuddyGameframe ? 142 : 142)
				: MouseHandler.mouseX >= canvasWidth - (Configuration.osbuddyGameframe ? 196 : 205)
				&& MouseHandler.mouseX <= canvasWidth - (Configuration.osbuddyGameframe ? 142 : 148)
				&& MouseHandler.mouseY >= (Configuration.osbuddyGameframe ? 123 : 112)
				&& MouseHandler.mouseY < (Configuration.osbuddyGameframe ? 150 : 145);

		counterHover = fixed ? MouseHandler.mouseX >= 522 && MouseHandler.mouseX <= 544 && MouseHandler.mouseY >= 20 && MouseHandler.mouseY <= 47
				: MouseHandler.mouseX >= canvasWidth - 205 && MouseHandler.mouseX <= canvasWidth - 184 && MouseHandler.mouseY >= 27
				&& MouseHandler.mouseY <= 44;


		specialHover = fixed
				? MouseHandler.mouseX >= (552)
				&& MouseHandler.mouseX <= (607)
				&& MouseHandler.mouseY >= (135)
				&& MouseHandler.mouseY < (163)

				: MouseHandler.mouseX >= canvasWidth - (184)
				&& MouseHandler.mouseX <= canvasWidth - (119)
				&& MouseHandler.mouseY >= (147)
				&& MouseHandler.mouseY < (170);

		donatorHover = fixed ? MouseHandler.mouseX >= 730 && MouseHandler.mouseX <= 755 && MouseHandler.mouseY >= 100 && MouseHandler.mouseY <= 126
				: MouseHandler.mouseX >= canvasWidth - 34 && MouseHandler.mouseX <= canvasWidth - 5 && MouseHandler.mouseY >= 143 && MouseHandler.mouseY <= 172;


		worldHover = fixed ? MouseHandler.mouseX >= 715 && MouseHandler.mouseX <= 740 && MouseHandler.mouseY >= 132 && MouseHandler.mouseY <= 160
				: MouseHandler.mouseX >= canvasWidth - 65 && MouseHandler.mouseX <= canvasWidth - 36 && MouseHandler.mouseY >= 158 && MouseHandler.mouseY <= 187;

		taskHover = fixed ? MouseHandler.mouseX >= 737 && MouseHandler.mouseX <= 761 && MouseHandler.mouseY >= 71 && MouseHandler.mouseY <= 95
				: MouseHandler.mouseX >= canvasWidth - 95 && MouseHandler.mouseX <= canvasWidth - 65 && MouseHandler.mouseY >= 168 && MouseHandler.mouseY <= 197;


		questHover = fixed ? MouseHandler.mouseX >= 734 && MouseHandler.mouseX <= 754 && MouseHandler.mouseY >= 38 && MouseHandler.mouseY <= 68
				: MouseHandler.mouseX >= canvasWidth - 125 && MouseHandler.mouseX <= canvasWidth - 100 && MouseHandler.mouseY >= 166 && MouseHandler.mouseY <= 191;
	}

	private boolean prayHover, prayClicked;

	public static boolean counterOn;

	private boolean counterHover;

	private boolean worldHover;

	private boolean donatorHover;
	private boolean taskHover;
	private boolean questHover;

	private boolean showClanOptions;

	public static int totalRead = 0;

	public static String getFileNameWithoutExtension(String fileName) {
		File tmpFile = new File(fileName);
		tmpFile.getName();
		int whereDot = tmpFile.getName().lastIndexOf('.');
		if (0 < whereDot && whereDot <= tmpFile.getName().length() - 2) {
			return tmpFile.getName().substring(0, whereDot);
		}
		return "";
	}

	public String indexLocation(int cacheIndex, int index) {
		return Signlink.getCacheDirectory() + "index" + cacheIndex + "/" + (index != -1 ? index + ".gz" : "");
	}

	public void repackCacheAll() {
		packCustomMaps();
		packCustomModels();
		packCustomAnimations();
	}

	public byte[] fileToByteArray(File file) {
		try {
			byte[] fileData = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(fileData);
			fis.close();
			return fileData;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void repackCacheIndex(int cacheIndex) {
		if (!new File(indexLocation(cacheIndex, -1)).exists()) {
			System.out.println("No index data found for: " + cacheIndex);
			return;
		}

		System.out.println("Started repacking index " + cacheIndex + " . " + new File(indexLocation(cacheIndex, -1)).getPath());
		int indexLength = new File(indexLocation(cacheIndex, -1)).listFiles().length;
		File[] file = new File(indexLocation(cacheIndex, -1)).listFiles();
		int packed = 0;
		try {
			for (int index = 0; index < indexLength; index++) {
				if (file[index] != null) {
					if (pack(file[index], cacheIndex)) {
						packed++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error packing index " + cacheIndex + ".");
		}

	}

	public boolean pack(File file, int cacheIndex) {
		int fileIndex = Integer.parseInt(getFileNameWithoutExtension(file.toString()));
		byte[] data = fileToByteArray(file);
		if (data != null && data.length > 0) {
			decompressors[cacheIndex].write(data.length, data, fileIndex);
			System.out.println("Packed " + file.toString() + " to index " + cacheIndex);
			return true;
		} else {
			System.out.println("No file to pack: " + file);
			return false;
		}
	}

	public void packCustomMaps() {
		if (new File(Configuration.CUSTOM_MAP_DIRECTORY).exists()) {
			System.out.println("Repacking custom maps..");
			packToIndex(4, new File(Configuration.CUSTOM_MAP_DIRECTORY));
		}
	}

	public void packCustomModels() {
		if (new File(Configuration.CUSTOM_MODEL_DIRECTORY).exists()) {
			System.out.println("Repacking custom models..");
			packToIndex(1, new File(Configuration.CUSTOM_MODEL_DIRECTORY));
		}
	}

	public void packCustomAnimations() {
		if (new File(Configuration.CUSTOM_ANIMATION_DIRECTORY).exists()) {
			System.out.println("Repacking custom animations..");
			packToIndex(2, new File(Configuration.CUSTOM_ANIMATION_DIRECTORY));
		}
	}

	private void packToIndex(int index, File file) {
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			if (list != null) {
				Arrays.stream(list).forEach(f -> packToIndex(index, f));
			}
		} else {
			pack(file, index);
			System.out.println("Pack " + file.getName() + " to index " + index);
		}
	}

	public void preloadModels() {
		File models = new File(Signlink.getCacheDirectory() + "/Raw/");
		File[] modelList = models.listFiles();
		for (int model = 0; model < modelList.length; model++) {
			String modelID = modelList[model].getName();
			byte[] modelData = ReadFile(Signlink.getCacheDirectory() + "/Raw/" + modelID);
			Model.loadModel(modelData, Integer.parseInt(getFileNameWithoutExtension(modelID)));
			System.out.println("Loaded " + models.length() + " preloaded models.");
		}
	}

	public static final byte[] ReadFile(String s) {
		try {
			byte abyte0[];
			File file = new File(s);
			int i = (int) file.length();
			abyte0 = new byte[i];
			DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(s)));
			datainputstream.readFully(abyte0, 0, i);
			datainputstream.close();
			totalRead++;
			return abyte0;
		} catch (Exception e) {
			System.out.println((new StringBuilder()).append("Read Error: ").append(s).toString());
			return null;
		}
	}

	public void addModels() {
		for (int ModelIndex = 0; ModelIndex < 50000; ModelIndex++) {
			byte[] abyte0 = getModel(ModelIndex);
			if (abyte0 != null && abyte0.length > 0) {
				decompressors[1].write(abyte0.length, abyte0, ModelIndex);
			}
		}
	}

	public byte[] getModel(int Index) {
		try {
			File Model = new File(Signlink.getCacheDirectory() + "./pModels/" + Index + ".gz");
			byte[] aByte = new byte[(int) Model.length()];
			FileInputStream fis = new FileInputStream(Model);
			fis.read(aByte);
			System.out.println("" + Index + " aByte = [" + aByte + "]!");
			fis.close();
			return aByte;
		} catch (Exception e) {
			return null;
		}
	}

	public void addMaps() {
		for (int MapIndex = 0; MapIndex < 3536; MapIndex++) {
			byte[] abyte0 = getMaps(MapIndex);
			if (abyte0 != null && abyte0.length > 0) {
				decompressors[4].write(abyte0.length, abyte0, MapIndex);
				System.out.println("Maps Added");
			}
		}
	}

	public byte[] getMaps(int Index) {
		try {
			File Map = new File(Signlink.getCacheDirectory() + "./maps/" + Index + ".gz");
			byte[] aByte = new byte[(int) Map.length()];
			FileInputStream fis = new FileInputStream(Map);
			fis.read(aByte);
			pushMessage("aByte = [" + aByte + "]!", 0, "");
			fis.close();
			return aByte;
		} catch (Exception e) {
			return null;
		}
	}

	public void saveMidi(boolean flag, byte abyte0[]) {
		Signlink.midifade = flag ? 1 : 0;
		Signlink.midisave(abyte0, abyte0.length);
	}
	public MapRegion currentMapRegion;
	public final void loadRegion() {
		try {
			setGameState(GameState.LOADING);
			anInt985 = -1;
			incompleteAnimables.removeAll();
			projectiles.removeAll();

			unlinkMRUNodes();
			scene.initToNull();
			System.gc();
			load_objects();

			for (int i = 0; i < 4; i++)
				collisionMaps[i].setDefault();

			for (int l = 0; l < 4; l++) {
				for (int k1 = 0; k1 < 104; k1++) {
					for (int j2 = 0; j2 < 104; j2++)
						tileFlags[l][k1][j2] = 0;
				}
			}
			currentMapRegion = new MapRegion(tileFlags, tileHeights);

			int k2 = terrainData.length;

			/*
			 * int k18 = 62; for (int A = 0; A < k2; A++) for (int B = 0; B < 2000; B++) if
			 * (anIntArray1234[A] == positions[B]) { anIntArray1235[A] = landScapes[B];
			 * anIntArray1236[A] = objects[B]; }
			 */

			stream.createFrame(0);

			if (!isDynamicRegion) {
				for (int i3 = 0; i3 < k2; i3++) {
					int i4 = (mapCoordinates[i3] >> 8) * 64 - baseX;
					int k5 = (mapCoordinates[i3] & 0xff) * 64 - baseY;

					byte abyte0[] = terrainData[i3];

					if (abyte0 != null)
						currentMapRegion.method180(abyte0, k5, i4, (currentRegionX - 6) * 8, (currentRegionY - 6) * 8,
								collisionMaps);
				}

				for (int j4 = 0; j4 < k2; j4++) {
					int l5 = (mapCoordinates[j4] >> 8) * 64 - baseX;
					int k7 = (mapCoordinates[j4] & 0xff) * 64 - baseY;
					byte abyte2[] = terrainData[j4];
					if (abyte2 == null && currentRegionY < 800)
						currentMapRegion.initiateVertexHeights(k7, 64, 64, l5);
				}

				anInt1097++;
				if (anInt1097 > 160) {
					anInt1097 = 0;
					stream.createFrame(238);
					stream.writeUnsignedByte(96);

				}
				stream.createFrame(0);

				for (int i6 = 0; i6 < k2; i6++) {
					byte abyte1[] = objectData[i6];
					if (abyte1 != null) {
						int l8 = (mapCoordinates[i6] >> 8) * 64 - baseX;
						int k9 = (mapCoordinates[i6] & 0xff) * 64 - baseY;
						currentMapRegion.loadObjectsInScene(l8, collisionMaps, k9, scene, abyte1);
					}
				}

			}
			if (isDynamicRegion) {
				for (int j3 = 0; j3 < 4; j3++) {
					for (int k4 = 0; k4 < 13; k4++) {
						for (int j6 = 0; j6 < 13; j6++) {
							int l7 = constructRegionData[j3][k4][j6];
							if (l7 != -1) {
								int i9 = l7 >> 24 & 3;
								int l9 = l7 >> 1 & 3;
								int j10 = l7 >> 14 & 0x3ff;
								int l10 = l7 >> 3 & 0x7ff;
								int j11 = (j10 / 8 << 8) + l10 / 8;
								for (int l11 = 0; l11 < mapCoordinates.length; l11++) {
									if (mapCoordinates[l11] != j11 || terrainData[l11] == null)
										continue;
									currentMapRegion.loadMapChunk(i9, l9, collisionMaps, k4 * 8, (j10 & 7) * 8,
											terrainData[l11], (l10 & 7) * 8, j3, j6 * 8);
									break;
								}

							}
						}

					}

				}

				for (int l4 = 0; l4 < 13; l4++) {
					for (int k6 = 0; k6 < 13; k6++) {
						int i8 = constructRegionData[0][l4][k6];
						if (i8 == -1)
							currentMapRegion.initiateVertexHeights(k6 * 8, 8, 8, l4 * 8);
					}

				}

				stream.createFrame(0);

				for (int l6 = 0; l6 < 4; l6++) {
					for (int j8 = 0; j8 < 13; j8++) {
						for (int j9 = 0; j9 < 13; j9++) {
							int chunkBits = constructRegionData[l6][j8][j9];
							if (chunkBits != -1) {
								int z = chunkBits >> 24 & 3;
								int rotation = chunkBits >> 1 & 3;
								int xCoord = chunkBits >> 14 & 0x3ff;
								int yCoord = chunkBits >> 3 & 0x7ff;
								int mapRegion = (xCoord / 8 << 8) + yCoord / 8;
								for (int k12 = 0; k12 < mapCoordinates.length; k12++) {
									if (mapCoordinates[k12] != mapRegion || objectData[k12] == null)
										continue;
									currentMapRegion.loadMapChunk(z, rotation, collisionMaps, x * 8, (xCoord & 7) * 8,
											terrainData[k12], (yCoord & 7) * 8, plane, y * 8);

									break;
								}

							}
						}

					}

				}

			}
			stream.createFrame(0);
			currentMapRegion.createRegionScene(collisionMaps, scene);

			stream.createFrame(0);

			int k3 = MapRegion.maximumPlane;
			if (k3 > plane)
				k3 = plane;

			if (k3 < plane - 1)
				k3 = plane - 1;
			if (lowMem)

				scene.method275(MapRegion.maximumPlane);
			else
				scene.method275(0);
			for (int i5 = 0; i5 < 104; i5++) {
				for (int i7 = 0; i7 < 104; i7++) {
					updateGroundItems(i5, i7);
				}
			}

			anInt1051++;
			if (anInt1051 > 98) {
				anInt1051 = 0;
				stream.createFrame(150);
			}
			method63();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectDefinition.baseModels.unlinkAll();
		if (lowMem && Signlink.cache_dat != null) {
			int j = resourceProvider.getVersionCount(0);
			for (int i1 = 0; i1 < j; i1++) {
				int l1 = resourceProvider.getModelIndex(i1);
				if ((l1 & 0x79) == 0)
					Model.resetModel(i1);
			}

		}
		stream.createFrame(210);
		stream.writeDWord(0x3f008edd);
		System.gc();

		resourceProvider.method566();

		int k = (currentRegionX - 6) / 8 - 1;
		int j1 = (currentRegionX + 6) / 8 + 1;
		int i2 = (currentRegionY - 6) / 8 - 1;
		int l2 = (currentRegionY + 6) / 8 + 1;
		if (inTutorialIsland) {
			k = 49;
			j1 = 50;
			i2 = 49;
			l2 = 50;
		}
		for (int l3 = k; l3 <= j1; l3++) {
			for (int j5 = i2; j5 <= l2; j5++)
				if (l3 == k || l3 == j1 || j5 == i2 || j5 == l2) {
					int j7 = resourceProvider.getMapFiles(0, j5, l3);
					if (j7 != -1)
						resourceProvider.method560(j7, 3);
					int k8 = resourceProvider.getMapFiles(1, j5, l3);
					if (k8 != -1)
						resourceProvider.method560(k8, 3);
				}

		}
		setGameState(GameState.LOGGED_IN);
		if (drawCallbacks != null) {
			drawCallbacks.loadScene(scene);
			drawCallbacks.swapScene(scene);
		}
	}

	public void unlinkMRUNodes() {
		ObjectDefinition.baseModels.unlinkAll();
		ObjectDefinition.models.unlinkAll();
		NpcDefinition.mruNodes.unlinkAll();
		ItemDefinition.models.unlinkAll();
		ItemDefinition.sprites.unlinkAll();
		Player.mruNodes.unlinkAll();
		GraphicsDefinition.aMRUNodes_415.unlinkAll();
	}

	private int lastSong = 0;
	public void handleRegionChangeMusic() {
		final int regionX = Client.localPlayer.getAbsoluteX() >> 3;
		final int regionY = Client.localPlayer.getAbsoluteY() >> 3;
		int regionID = ((regionX / 8) << 8) + (regionY / 8);

		int song = Music.getMusicIdFromBoundary(Client.localPlayer.getAbsoluteX(), Client.localPlayer.getAbsoluteY());
		if (song != lastSong) {
			requestMusic(song);
			lastSong = song;
		}
	}

	public void renderMapScene(int i) {
		int ai[] = minimapImage.myPixels;
		int j = ai.length;

		for (int k = 0; k < j; k++)
			ai[k] = 0;

		for (int l = 1; l < 103; l++) {
			int i1 = 24628 + (103 - l) * 512 * 4;
			for (int k1 = 1; k1 < 103; k1++) {
				if ((tileFlags[i][k1][l] & 0x18) == 0)
					scene.drawTileMinimap(ai, i1, i, k1, l);
				if (i < 3 && (tileFlags[i + 1][k1][l] & 8) != 0)
					scene.drawTileMinimap(ai, i1, i + 1, k1, l);
				i1 += 4;
			}

		}

		int j1 = ((238 + (int) (Math.random() * 20D)) - 10 << 16) + ((238 + (int) (Math.random() * 20D)) - 10 << 8)
				+ ((238 + (int) (Math.random() * 20D)) - 10);
		int l1 = (238 + (int) (Math.random() * 20D)) - 10 << 16;
		minimapImage.init();
		for (int i2 = 1; i2 < 103; i2++) {
			for (int j2 = 1; j2 < 103; j2++) {
				if ((tileFlags[i][j2][i2] & 0x18) == 0)
					drawMapScenes(i2, j1, j2, l1, i);
				if (i < 3 && (tileFlags[i + 1][j2][i2] & 8) != 0)
					drawMapScenes(i2, j1, j2, l1, i + 1);
			}

		}

		rasterProvider.setRaster();
		mapIconAmount = 0;
		for (int k2 = 0; k2 < 104; k2++) {
			for (int l2 = 0; l2 < 104; l2++) {
				long i3 = scene.getGroundDecorationUid(plane, k2, l2);
				if (i3 != 0) {

					int objId = ObjectKeyUtil.getObjectId(i3);
					int j3 = ObjectDefinition.lookup(objId).minimapFunction;
					if (objId == 42781 || objId == 42742) {
						j3 = 6;
					}
					if (objId == 46201) {
						j3 = -1;
					}
					if (j3 >= 0) {
						int sprite = AreaDefinition.lookup(j3).spriteId;
						if (sprite != -1) {
							int k3 = k2;
							int l3 = l2;
							try {
								if (objId == 7389) {
									j3 = 57;
								}
								if (objId == 17030) {
									j3 = 70;
								}
								if (objId == 26301) {
									j3 = 65;
								}
								if (objId == 16455) {
									j3 = 77;
								}
/*								if (j3 == 58 || j3 == 71 || j3 == 66 || j3 == 79 || j3 == 91) {
									System.out.println(objId + " object and the icon " + j3);
								}*/
								mapIconSprite[mapIconAmount] = mapFunctions[j3];
								anIntArray1072[mapIconAmount] = k3;
								anIntArray1073[mapIconAmount] = l3;
								mapIconAmount++;
							} catch(Exception e) {

							}

						}
					}
				}
			}

		}

	}
	public boolean hover(int x1, int y1, Sprite drawnSprite) {
		return MouseHandler.mouseX >= x1 && MouseHandler.mouseX <= x1 + drawnSprite.myWidth && MouseHandler.mouseY >= y1 && MouseHandler.mouseY <= y1 + drawnSprite.myHeight;
	}
	private void updateGroundItems(int i, int j) {
		Deque class19 = groundItems[plane][i][j];

		if (class19 == null) {
			scene.removeGroundItemTile(plane, i, j);
			return;
		}

		int k = 0xfa0a1f01;
		Object obj = null;
		for (Item item = (Item) class19.reverseGetFirst(); item != null; item =
				(Item) class19.reverseGetNext()) {
			ItemDefinition itemDef = ItemDefinition.lookup(item.ID);
			int l = itemDef.cost;
			if (itemDef.stackable)
				l *= item.itemCount + 1;

/*			if (l > k) {
				k = l;*/
				obj = item;
//			}

		}

		class19.insertTail(((Linkable) (obj)));
		Object obj1 = null;
		Object obj2 = null;
		for (Item item = (Item) class19
				.reverseGetFirst(); item != null; item =
					 (Item) class19.reverseGetNext()) {
			if (item.ID != ((Item) (obj)).ID && obj1 == null) {
				obj1 = item;
			}
			if (item.ID != ((Item) (obj)).ID
					&& item.ID != ((Item) (obj1)).ID && obj2 == null) {
				obj2 = item;
			}
		}


		TileItem ti = (TileItem) obj;

		if (obj1 != null) {
			ti = (TileItem) obj1;
		} else if (obj2 != null) {
			ti = (TileItem) obj2;
		}

		int i1 = i + (j << 7) + 0x60000000;
		scene.addGroundItemTile(obj, i, i1, ((Renderable) (obj1)),
				getCenterHeight(plane, j * 128 + 64, i * 128 + 64), ((Renderable) (obj2)),
				((Renderable) (obj)), plane, j);


	}


	public void method26(boolean flag) {
		if (hidelocalnpcs) {
			return;
		}

		for (int j = 0; j < npcCount; j++) {
			Npc npc = npcs[npcIndices[j]];
			long k = 0x20000000L | (long) npcIndices[j] << 32;
			if (npc == null || !npc.isVisible() || npc.desc.aBoolean93 != flag)
				continue;
			int l = npc.x >> 7;
			int i1 = npc.y >> 7;
			if (l < 0 || l >= 104 || i1 < 0 || i1 >= 104)
				continue;
			if (npc.anInt1540 == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
				if (anIntArrayArray929[l][i1] == anInt1265)
					continue;
				anIntArrayArray929[l][i1] = anInt1265;
			}
			if (!npc.desc.clickable)
				k |= ~0x7fffffffffffffffL;
			scene.addAnimableA(plane, npc.orientation, getCenterHeight(plane, npc.y, npc.x), k, npc.y,
					(npc.anInt1540 - 1) * 64 + 60, npc.x, npc, npc.dynamic);
		}
	}

	public void loadError() {
		String s = "ondemand";// was a constant parameter
		System.out.println(s);
		try {
			getAppletContext().showDocument(new URL(getCodeBase(), "loaderror_" + s + ".html"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		do
			try {
				Thread.sleep(1000L);
			} catch (Exception _ex) {
			}
		while (true);
	}

	public int drawTabInterfaceHoverParent;
	public int drawTabInterfaceHover;
	public int drawTabInterfaceHoverLast;
	public long drawTabInterfaceHoverTimer;

	public RSInterface lastHovered;

	public void resetTabInterfaceHover() {
		drawTabInterfaceHover = 0;
		if (lastHovered != null) {
			lastHovered.isHovered = false;
			lastHovered.hoverRunnable.run();
			lastHovered = null;
		}
	}

	// interfaceClick
	private void buildInterfaceMenu(int xPosition, RSInterface class9, int mouseX, int yPosition, int mouseY, int j1) {
		if (class9 == null || RSInterface.interfaceCache[class9.parentID].interfaceHidden) {
			return;
		}
		if (class9.type != 0 || class9.children == null || class9.isMouseoverTriggered || class9.actionsDisabled) {
//			hoverShopTile = -1;
			if(class9.id == PetPerk.widgetId) {
				if(!RSInterface.get(48848).interfaceHidden) {
					buildInterfaceMenu(xPosition, RSInterface.get(48848), mouseX, yPosition, mouseY, j1);
				}
				if(!RSInterface.get(39350).interfaceHidden) {
					buildInterfaceMenu(xPosition, RSInterface.get(39350), mouseX, yPosition, mouseY, j1);
				}
			}
			return;
		}
		if (mouseX < xPosition || mouseY < yPosition || mouseX > xPosition + class9.width || mouseY > yPosition + class9.height) {
			hoverShopTile = -1;
			return;
		}
		int childCount = class9.children.length;
		for (int childIndex = 0; childIndex < childCount; childIndex++) {
			try {
				int drawX = class9.childX[childIndex] + xPosition - (class9.horizontalScroll ? j1 : 0);
				int drawY = (class9.childY[childIndex] + yPosition) - (class9.horizontalScroll ? 0 : j1);
				RSInterface class9_1 = RSInterface.interfaceCache[class9.children[childIndex]];
				if (class9_1 == null) {
					break;
				}
				if (class9_1.invisible) {
					continue;
				}
				drawX += class9_1.anInt263;
				drawY += class9_1.anInt265;

				if (mouseX >= drawX && mouseY >= drawY && mouseX <= drawX + class9_1.width && mouseY <= drawY + class9_1.height) {
					if (class9_1.hoverInterfaceId != 0) {
						drawTabInterfaceHoverParent = class9_1.id;
						drawTabInterfaceHover = class9_1.hoverInterfaceId;
					}

					if (class9_1.hoverRunnable != null && lastHovered == null) {
						class9_1.isHovered = true;
						class9_1.hoverRunnable.run();
						lastHovered = class9_1;
					}
				}

				if ((class9_1.mOverInterToTrigger >= 0 || class9_1.anInt216 != 0) && mouseX >= drawX && mouseY >= drawY
						&& mouseX < drawX + class9_1.width && mouseY < drawY + class9_1.height) {
					if (class9_1.mOverInterToTrigger >= 0) {
						anInt886 = class9_1.mOverInterToTrigger;
					} else {
						anInt886 = class9_1.id;
					}
				}
				if (class9_1.atActionType == RSInterface.AT_ACTION_TYPE_OPTION_DROPDOWN) {

					boolean flag = false;
					class9_1.hovered = false;
					class9_1.dropdownHover = -1;

					if (class9_1.dropdown.isOpen()) {

						// Inverted keybinds dropdown
						if (class9_1.type == RSInterface.TYPE_KEYBINDS_DROPDOWN && class9_1.inverted && mouseX >= drawX
								&& mouseX < drawX + (class9_1.dropdown.getWidth() - 16)
								&& mouseY >= drawY - class9_1.dropdown.getHeight() - 10 && mouseY < drawY) {
							resetTabInterfaceHover();
							int yy = mouseY - (drawY - class9_1.dropdown.getHeight());

							if (mouseX > drawX + (class9_1.dropdown.getWidth() / 2)) {
								class9_1.dropdownHover = ((yy / 15) * 2) + 1;
							} else {
								class9_1.dropdownHover = (yy / 15) * 2;
							}
							flag = true;
						} else if (!class9_1.inverted && mouseX >= drawX && mouseX < drawX + (class9_1.dropdown.getWidth() - 16)
								&& mouseY >= drawY + 19 && mouseY < drawY + 19 + class9_1.dropdown.getHeight()) {
							resetTabInterfaceHover();
							int yy = mouseY - (drawY + 19);

							if (class9_1.type == RSInterface.TYPE_KEYBINDS_DROPDOWN && class9_1.dropdown.doesSplit()) {
								if (mouseX > drawX + (class9_1.dropdown.getWidth() / 2)) {
									class9_1.dropdownHover = ((yy / 15) * 2) + 1;
								} else {
									class9_1.dropdownHover = (yy / 15) * 2;
								}
							} else {
								class9_1.dropdownHover = yy / 14; // Regular dropdown hover
							}
							flag = true;
						}
						// just to show difference, we're basically turning these old ones into MenuEntry objects
						if (flag) {
							if (menuActionRow != 1) {
								menuActionRow = 1;
							}
							MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
									.setOption("Select")
									.setType(770)
									.setWidget(class9)
									.setParam0(class9_1.id)
									.setParam1(class9.id)
									.setIdentifier(class9_1.dropdownHover);
							menuManager.addEntry(menuEntry);
							setMenuOptionCount(getMenuOptionCount() + 1);
							callbacks.post(new MenuEntryAdded(menuEntry));
						}
					}
					if (mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.dropdown.getWidth() && mouseY < drawY + 24
							&& menuActionRow == 1) {
						class9_1.hovered = true;
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(class9_1.dropdown.isOpen() ? "Hide" : "Show")
								.setWidget(class9)
								.setType(769)
								.setParam0(class9_1.id)
								.setParam1(class9.id);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
				}

				if (mouseX >= drawX && mouseY >= drawY && mouseX < drawX + (class9_1.type == 4 ? 100 : class9_1.width)
						&& mouseY < drawY + class9_1.height) {
					if (class9_1.actions != null && !class9_1.invisible && !class9_1.drawingDisabled) {

						if (!(class9_1.contentType == 206 && interfaceIsSelected(class9_1))) {
							if (class9_1.type == 5) {

								boolean drawOptions = true;

								// HARDCODE CLICKABLE TEXT HERE
								if (class9_1.parentID == 37128) { // Clan chat interface, dont show options for guests
									drawOptions = showClanOptions;
								}

								if (drawOptions) {
									for (int action = class9_1.actions.length - 1; action >= 0; action--) {
										if (class9_1.actions[action] != null) {
											String s = class9_1.actions[action]
													+ (class9_1.type == 4 ? " @or1@" + class9_1.message : "");

											if (s.contains("img")) {
												int prefix = s.indexOf("<img=");
												int suffix = s.indexOf(">");
												s = s.replaceAll(s.substring(prefix + 5, suffix), "");
												s = s.replaceAll("</img>", "");
												s = s.replaceAll("<img=>", "");
											}
											if (s.contains("ico")) {
												int prefix = s.indexOf("<ico=");
												int suffix = s.indexOf(">");
												s = s.replaceAll(s.substring(prefix + 5, suffix), "");
												s = s.replaceAll("</ico>", "");
												s = s.replaceAll("<ico=>", "");
											}
											MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
													.setOption(s)
													.setType(647)
													.setWidget(class9)
													.setIdentifier(action)
													.setParam0(class9_1.id);
											menuManager.addEntry(menuEntry);
											setMenuOptionCount(getMenuOptionCount() + 1);
											callbacks.post(new MenuEntryAdded(menuEntry));
										}
									}
								}
							}
						}
					}
				}
				if (class9_1.type == 22) {
					RSInterface class9_2;
					int slot = class9_1.grandExchangeSlot;
					if (grandExchangeInformation[slot][0] == -1)
						class9_2 = RSInterface.interfaceCache[GrandExchange.grandExchangeBuyAndSellBoxIds[slot]];
					else
						class9_2 = RSInterface.interfaceCache[GrandExchange.grandExchangeItemBoxIds[slot]];
					buildInterfaceMenu(drawX, class9_2, mouseX, drawY, mouseY, j1);
				}
				if (class9_1.type == 9 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width && mouseY < drawY + class9_1.height) {
					anInt1315 = class9_1.id;
				}
				if (class9_1.type == 5 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width && mouseY < drawY + class9_1.height) {
					hoverId = class9_1.id;
				}

				if (class9_1.type == 0) {
					buildInterfaceMenu(drawX, class9_1, mouseX, drawY, mouseY, class9_1.scrollPosition);
					if (class9_1.horizontalScroll) {
						if (class9_1.scrollMax > class9_1.width) {
							if (scrollbarVisible(class9_1)) {
								scrollInterfaceBottom(drawX, class9_1.width, mouseX, mouseY, class9_1, drawY + class9_1.height, true, class9_1.scrollMax);
							}
						}
					} else {
						if (class9_1.scrollMax > class9_1.height) {
							if (scrollbarVisible(class9_1)) {
								handleScroller(drawX + class9_1.width, class9_1.height, mouseX, mouseY, class9_1, drawY, true, class9_1.scrollMax);
							}
						}
					}
				} else {
					if (class9_1.atActionType == RSInterface.OPTION_OK && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						boolean flag = false;
						if (class9_1.contentType != 0)
							flag = buildFriendsListMenu(class9_1);
						if (!flag) {
							MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
									.setOption(class9_1.tooltip)
									.setType(315)
									.setWidget(class9)
									.setParam1(class9_1.id);
							menuManager.addEntry(menuEntry);
							setMenuOptionCount(getMenuOptionCount() + 1);
							callbacks.post(new MenuEntryAdded(menuEntry));
						}
						if (class9_1.type == RSInterface.TYPE_HOVER || class9_1.type == RSInterface.TYPE_CONFIG_HOVER
								|| class9_1.type == RSInterface.TYPE_ADJUSTABLE_CONFIG
								|| class9_1.type == RSInterface.TYPE_BOX) {
							class9_1.toggled = true;
						}

					}
					if (class9_1.atActionType == 2 && spellSelected == 0 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						String s = class9_1.selectedActionName;
						if (s.indexOf(" ") != -1)
							s = s.substring(0, s.indexOf(" "));
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(s)
								.setWidget(class9)
								.setTarget("@gre@" + class9_1.spellName)
								.setType(626)
								.setParam1(class9_1.id);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					// close interface button
					if (class9_1.atActionType == 3 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption("Close")
								.setType(200)
								.setWidget(class9)
								.setParam1(class9_1.id);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					if (class9_1.atActionType == 4 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(class9_1.tooltip)
								.setType(169)
								.setWidget(class9)
								.setParam1(class9_1.id);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					//System.out.println(class9_1.id + "/" + class9_1.atActionType + "/" + class9_1.type + "/" + class9_1.tooltip);
					if (class9_1.atActionType == 5 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(class9_1.tooltip)
								.setType(646)
								.setWidget(class9)
								.setParam1(class9_1.id);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					if (class9_1.atActionType == 6 && !aBoolean1149 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(class9_1.tooltip)
								.setType(679)
								.setWidget(class9)
								.setParam1(class9_1.id);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}

					if (spellSelected == 0 && class9_1.atActionType == RSInterface.AT_ACTION_TYPE_AUTOCAST && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(class9_1.tooltip)
								.setType(Autocast.AUTOCAST_MENU_ACTION_ID)
								.setWidget(class9)
								.setParam1(class9_1.id);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}

					if (class9_1.type == 8 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width && mouseY < drawY + class9_1.height) {
						anInt1315 = class9_1.id;
					}
					if (class9_1.atActionType == 8 && !aBoolean1149 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						for (int s1 = 0; s1 < class9_1.tooltips.length; s1++) {
							MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
									.setOption(class9_1.tooltips[s1])
									.setType(1700 + s1)
									.setWidget(class9)
									.setParam1(class9_1.id);
							menuManager.addEntry(menuEntry);
							setMenuOptionCount(getMenuOptionCount() + 1);
							callbacks.post(new MenuEntryAdded(menuEntry));
						}
					}
					if (class9_1.atActionType == 9 && !aBoolean1149 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(class9_1.tooltip)
								.setType(1100)
								.setWidget(class9)
								.setParam1(class9_1.id);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					if (class9_1.atActionType == 10 && !aBoolean1149 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(class9_1.getMenuItem().getText())
								.setType(1200)
								.setWidget(class9)
								.setParam1(class9_1.id);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					if (class9_1.atActionType == 11 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + class9_1.width
							&& mouseY < drawY + class9_1.height) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(class9_1.tooltip)
								.setType(201)
								.setWidget(class9)
								.setParam1(class9_1.id);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					if (mouseX >= drawX && mouseY >= drawY && mouseX < drawX + (class9_1.type == 4 ? 100 : class9_1.width)
							&& mouseY < drawY + class9_1.height) {

						if (class9_1.actions != null) {
							if ((class9_1.type == 4 && class9_1.message.length() > 0) || class9_1.type == 5) {
								for (int action = class9_1.actions.length - 1; action >= 0; action--) {
									if (class9_1.actions[action] != null) {
										MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
												.setOption(class9_1.actions[action]
														+ (class9_1.type == 4 ? " " + class9_1.message : ""))
												.setType(647)
												.setWidget(class9)
												.setParam0(action)
												.setParam1(class9_1.id);
										menuManager.addEntry(menuEntry);
										setMenuOptionCount(getMenuOptionCount() + 1);
										callbacks.post(new MenuEntryAdded(menuEntry));
									}
								}
							}
						}
					}
					if (class9_1.type == 2) {
						int k2 = 0;
						for (int l2 = 0; l2 < class9_1.height; l2++) {
							for (int i3 = 0; i3 < class9_1.width; i3++) {
								boolean smallSprite = openInterfaceID == 26000
										&& GrandExchange.isSmallItemSprite(class9_1.id) || class9_1.smallInvSprites;
								int size = smallSprite ? 18 : 32;
								int j3 = drawX + i3 * (size + class9_1.invSpritePadX);
								int k3 = drawY + l2 * (size + class9_1.invSpritePadY);
								if (k2 < 20) {
									j3 += class9_1.spritesX[k2];
									k3 += class9_1.spritesY[k2];
								}
								if (mouseX >= j3 && mouseY >= k3 && mouseX < j3 + size && mouseY < k3 + size) {
									mouseInvInterfaceIndex = k2;
									lastActiveInvInterface = class9_1.id;
									int itemID = class9_1.inventoryItemId[k2] - 1;
									class9_1.setItemId(itemID);
/*									if (class9_1.id == 23231) {
										itemID = (class9_1.inventoryItemId[k2] & 0x7FFF) - 1;
									}*/
									if (class9_1.inventoryItemId[k2] > 0) {
										ItemDefinition itemDef = ItemDefinition.lookup(itemID);
										boolean hasDestroyOption = false;
										if (itemSelected == 1 && class9_1.isInventoryInterface) {
											if (class9_1.id != anInt1284 || k2 != anInt1283) {
												MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
														.setOption("Use " + selectedItemName)
														.setTarget("@cya@" + itemDef.name)
														.setType(870)
														.setWidget(class9)
														.setIdentifier(k2)
														.setParam0(k2)
														.setParam1(class9_1.id);
												menuManager.addEntry(menuEntry);
												setMenuOptionCount(getMenuOptionCount() + 1);
												callbacks.post(new MenuEntryAdded(menuEntry));
											}
										} else if (spellSelected == 1 && class9_1.isInventoryInterface) {
											if ((spellUsableOn & 0x10) == 16) {
												MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
														.setOption(spellTooltip)
														.setTarget("@lre@" + itemDef.name)
														.setIdentifier(itemDef.id)
														.setType(543)
														.setWidget(class9)
														.setParam0(k2)
														.setParam1(class9_1.id);
												menuManager.addEntry(menuEntry);
												setMenuOptionCount(getMenuOptionCount() + 1);
												callbacks.post(new MenuEntryAdded(menuEntry));
											}
										} else {
											if (class9_1.isInventoryInterface) {
												for (int l3 = 4; l3 >= 3; l3--)
													if (itemDef.interfaceOptions != null
															&& itemDef.interfaceOptions[l3] != null) {
														MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
																.setOption(itemDef.interfaceOptions[l3])
																.setTarget("@lre@" + itemDef.name)
																.setIdentifier(itemID)
																.setParam0(k2)
																.setWidget(class9_1)
																.setParam1(class9_1.id);
														if (itemDef.interfaceOptions[l3].contains("Wield") || itemDef.interfaceOptions[l3].contains("Wear")) {
															hintMenu = true;
															hintName = itemDef.name;
															hintId = itemDef.id;
														} else {
															hintMenu = false;
														}
														if (l3 == 3)
															menuEntry.setType(493);

														if (l3 == 4)
															menuEntry.setType(847);
														hasDestroyOption = itemDef.interfaceOptions[l3].contains("Destroy");
														menuManager.addEntry(menuEntry);
														setMenuOptionCount(getMenuOptionCount() + 1);
														callbacks.post(new MenuEntryAdded(menuEntry));
													} else if (l3 == 4) {
														if (KeyHandler.ctrlDown) {//oh im a dickhead
															MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
																	.setOption("@lre@" + itemDef.name)
																	.setIdentifier(10)
																	.setParam0(k2)
																	.setWidget(class9_1)
																	.setParam1(class9_1.id)
																	.setType(847);
															menuManager.addEntry(menuEntry);
															setMenuOptionCount(getMenuOptionCount() + 1);
															callbacks.post(new MenuEntryAdded(menuEntry));
														} else {
															MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
																	.setOption("Drop")
																	.setTarget("@lre@" + itemDef.name)
																	.setIdentifier(itemID)
																	.setType(847)
																	.setWidget(class9_1)
																	.setParam0(k2)
																	.setParam1(class9_1.id);
															menuManager.addEntry(menuEntry);
															setMenuOptionCount(getMenuOptionCount() + 1);
															callbacks.post(new MenuEntryAdded(menuEntry));
														}
													}
											}

											String option = "";
											if (class9_1.parentID == 5292)
												if (class9_1.id == 5382 && this.placeHolders && class9_1.inventoryAmounts[k2] <= 0) {
													class9_1.actions = new String[8];
													class9_1.actions[1] = "Release";
												} else {
													if (this.variousSettings[222] == 1) {
														option = "Withdraw-5";
													} else if (this.variousSettings[222] == 2) {
														option = "Withdraw-10";
													} else if (this.variousSettings[222] == 3 && this.modifiableXValue > 0) {
														option = "Withdraw-" + this.modifiableXValue;
													} else if (this.variousSettings[222] == 4) {
														option = "Withdraw-All";
													}
													if (this.modifiableXValue > 0)
														if (class9_1.inventoryAmounts[k2] <= 0) {
															class9_1.actions = new String[8];
															class9_1.actions[1] = "Release";
														} else {
															(new String[8])[0] = "Withdraw-1";
															(new String[8])[1] = "Withdraw-5";
															(new String[8])[2] = "Withdraw-10";
															(new String[8])[3] = "Withdraw-All";
															(new String[8])[4] = "Withdraw-X";
															(new String[8])[5] = "Withdraw-" + this.modifiableXValue;
															(new String[8])[6] = "Withdraw-All-but-1";
															(new String[8])[7] = "Placeholder";
															(new String[9])[0] = option;
															(new String[9])[1] = "Withdraw-1";
															(new String[9])[2] = "Withdraw-5";
															(new String[9])[3] = "Withdraw-10";
															(new String[9])[4] = "Withdraw-All";
															(new String[9])[5] = "Withdraw-X";
															(new String[9])[6] = "Withdraw-" + this.modifiableXValue;
															(new String[9])[7] = "Withdraw-All-but-1";
															(new String[9])[8] = "Placeholder";
															class9_1.actions = option.isEmpty() ? new String[8] : new String[9];
														}
												}
											if (class9_1.id == 5064) {
												if (modifiableXValue > 0) {// so issue is when x = 0
													if (class9_1.actions.length < 9) {
														if (variousSettings[222] == 1) {
															class9_1.actions = new String[] {
																	"Store-5",
																	"Store-5",
																	"Store-10",
																	"Store-All",
																	"Store-" + modifiableXValue,
																	"Store-All-but-1"
															};
														} else if (variousSettings[222] == 2) {
															class9_1.actions = new String[] {
																	"Store-10",
																	"Store-5",
																	"Store-10",
																	"Store-All",
																	"Store-" + modifiableXValue,
																	"Store-All-but-1"
															};
														} else if (variousSettings[222] == 3) {
															class9_1.actions = new String[] {
																	"Store-" + modifiableXValue,
																	"Store-5",
																	"Store-10",
																	"Store-All",
																	"Store-" + modifiableXValue,
																	"Store-All-but-1"
															};
														} else if (variousSettings[222] == 4) {
															class9_1.actions = new String[] {
																	"Store-All",
																	"Store-5",
																	"Store-10",
																	"Store-All",
																	"Store-" + modifiableXValue,
																	"Store-All-but-1"
															};
														} else {
															class9_1.actions = new String[] {
																	"Store-1",
																	"Store-5",
																	"Store-10",
																	"Store-All",
																	"Store-" + modifiableXValue,
																	"Store-All-but-1"
															};
														}
													} else {
														class9_1.actions[5] = "Store-" + modifiableXValue;
													}
												}
											}
											if (class9_1.usableItemInterface) {
												hintMenu = false;
												hintMenu = false;
												MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
														.setOption("Use ")
														.setTarget("@lre@" + itemDef.name)
														.setIdentifier(itemDef.id)
														.setType(447)
														.setParam0(k2)
														.setWidget(class9_1)
														.setParam1(class9_1.id);
												menuManager.addEntry(menuEntry);
												setMenuOptionCount(getMenuOptionCount() + 1);
												callbacks.post(new MenuEntryAdded(menuEntry));

												if (!hasDestroyOption && !menuOpen && shiftDrop && shiftDown) {
													menuEntry = (MenuEntry) new MenuEntry(1)
															.setOption("Drop @lre@")
															.setTarget(itemDef.name)
															.setIdentifier(itemID)
															.setParam0(k2)
															.setParam1(class9_1.id)
															.setType(847);
													menuManager.addEntry(menuEntry);
													callbacks.post(new MenuEntryAdded(menuEntry));
													menuActionRow = 2;
													removeShiftDropOnMenuOpen = true;
												}
//												if (!menuOpen && KeyHandler.altIsDown) {
//													Preferences preferences = Preferences.getPreferences();
//													if (preferences.groundItemTextHide.contains(itemDef.name) || preferences.groundItemTextHide.contains(itemDef.name.toLowerCase())) {
//														menuActionsRow("Show @lre@" + itemDef.name, 1, 616, 2);
//													} else {
//														menuActionsRow("Hide @lre@" + itemDef.name, 1, 616, 2);
//													}
//												}
											}
											if (class9_1.isInventoryInterface && itemDef.interfaceOptions != null) {
												for (int i4 = 2; i4 >= 0; i4--)
													if (itemDef.interfaceOptions[i4] != null) {

														if (itemDef.interfaceOptions[i4].contains("Wield") || itemDef.interfaceOptions[i4].contains("Wear")) {
															hintMenu = true;
															hintName = itemDef.name;
															hintId = itemDef.id;
														} else {
															hintMenu = false;
														}

														MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
																.setOption(itemDef.interfaceOptions[i4])
																.setTarget("@lre@" + itemDef.name)
																.setIdentifier(itemID)
																.setParam0(k2)
																.setWidget(class9_1)
																.setParam1(class9_1.id);
														if (i4 == 0)
															menuEntry.setType(74);
														if (i4 == 1)
															menuEntry.setType(454);
														if (i4 == 2)
															menuEntry.setType(539);
														menuManager.addEntry(menuEntry);
														setMenuOptionCount(getMenuOptionCount() + 1);
														callbacks.post(new MenuEntryAdded(menuEntry));
														if (!hasDestroyOption && !menuOpen && shiftDrop && shiftDown) {
															menuEntry = (MenuEntry) new MenuEntry(1)
																	.setOption("Drop @lre@")
																	.setTarget(itemDef.name)
																	.setIdentifier(itemID)
																	.setWidget(class9_1)
																	.setParam0(k2)
																	.setParam1(class9_1.id)
																	.setType(847);
															menuManager.addEntry(menuEntry);
															callbacks.post(new MenuEntryAdded(menuEntry));
															menuActionRow = 2;
															removeShiftDropOnMenuOpen = true;
														}
													}

											}

											if (class9_1.actions != null) {
												if (openInterfaceID == 21553 && class9_1.parentID == 3213) {
													// Equipment
													String[] options = new String[]{"Place in inventory", "Use as equipment"};
													for (int j4 = 8; j4 >= 0; j4--) {
														if (j4 > options.length - 1)
															continue;
														if (options[j4] != null) {
															MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
																	.setOption(options[j4])
																	.setTarget("@lre@" + itemDef.name)
																	.setIdentifier(10)
																	.setParam0(k2)
																	.setWidget(class9_1)
																	.setParam1(class9_1.id);
															if (j4 == 0)
																menuEntry.setType(632);
															if (j4 == 1)
																menuEntry.setType(78);
															if (j4 == 2)
																menuEntry.setType(867);
															if (j4 == 3)
																menuEntry.setType(431);
															if (j4 == 4)
																menuEntry.setType(53);
															if (j4 == 7)
																menuEntry.setType(1337);
															menuManager.addEntry(menuEntry);
															setMenuOptionCount(getMenuOptionCount() + 1);
															callbacks.post(new MenuEntryAdded(menuEntry));
														}
													}

												} else {
													// Standard containers
													if (Bank.isBankContainer(class9_1)) {
														if (class9_1.id == 5382 && placeHolders && class9_1.inventoryAmounts[k2] <= 0) {
															class9_1.actions = new String[8];
															class9_1.actions[1] = "Release";
														} else {
															if (modifiableXValue > 0) {// so issue is when x = 0
																if (class9_1.actions.length < 9) {
																	if (variousSettings[222] == 1) {
																		class9_1.actions = new String[] {
																				"Withdraw-5",
																				"Withdraw-5",
																				"Withdraw-10",
																				"Withdraw-All",
																				"Withdraw-" + modifiableXValue,
																				"Withdraw-All-but-1"
																		};
																	} else if (variousSettings[222] == 2) {
																		class9_1.actions = new String[] {
																				"Withdraw-10",
																				"Withdraw-5",
																				"Withdraw-10",
																				"Withdraw-All",
																				"Withdraw-" + modifiableXValue,
																				"Withdraw-All-but-1"
																		};
																	} else if (variousSettings[222] == 3) {
																		class9_1.actions = new String[] {
																				"Withdraw-" + modifiableXValue,
																				"Withdraw-5",
																				"Withdraw-10",
																				"Withdraw-All",
																				"Withdraw-" + modifiableXValue,
																				"Withdraw-All-but-1"
																		};
																	} else if (variousSettings[222] == 4) {
																		class9_1.actions = new String[] {
																				"Withdraw-All",
																				"Withdraw-5",
																				"Withdraw-10",
																				"Withdraw-All",
																				"Withdraw-" + modifiableXValue,
																				"Withdraw-All-but-1"
																		};
																	} else {
																		class9_1.actions = new String[] {
																				"Withdraw-1",
																				"Withdraw-5",
																				"Withdraw-10",
																				"Withdraw-All",
																				"Withdraw-" + modifiableXValue,
																				"Withdraw-All-but-1"
																		};
																	}
																} else {
																	class9_1.actions[5] = "Withdraw-" + modifiableXValue;
																}
															}
														}
													}

													for (int j4 = 8; j4 >= 0; j4--) {
														if (j4 > class9_1.actions.length - 1)
															continue;
														if (class9_1.actions[j4] != null) {
															MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
																	.setOption(class9_1.actions[j4])
																	.setTarget("@lre@" + itemDef.name)
																	.setIdentifier(itemID)
																	.setParam0(k2)
																	.setWidget(class9_1)
																	.setParam1(class9_1.id);
															if (class9_1.id != 1688) {
																if (j4 == 0)
																	menuEntry.setType(632);
																if (j4 == 1)
																	menuEntry.setType(78);
																if (j4 == 2)
																	menuEntry.setType(867);
																if (j4 == 3)
																	menuEntry.setType(431);
																if (j4 == 4)
																	menuEntry.setType(53);
																if (j4 == 5)
																	menuEntry.setType(300);
																if (j4 == 6)
																	menuEntry.setType(291);

															} else {
																if (itemDef.equipActions[j4] == null) {
																	menuEntry = (MenuEntry) new MenuEntry(3)
																			.setOption("Operate ")
																			.setWidget(class9)
																			.setTarget("@lre@" + itemDef.name)
																			.setIdentifier(itemDef.id);
																	menuManager.addEntry(menuEntry);
																	callbacks.post(new MenuEntryAdded(menuEntry));
																	menuEntry = (MenuEntry) new MenuEntry(4)
																			.setOption("Remove ")
																			.setWidget(class9_1)
																			.setTarget("@lre@" + itemDef.name)
																			.setIdentifier(10);
																} else {
																	class9_1.actions = itemDef.equipActions;
																	menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
																			.setOption(itemDef.equipActions[j4])
																			.setTarget("@lre@" + itemDef.name)
																			.setIdentifier(itemDef.id)
																			.setType(847)
																			.setWidget(class9_1)
																			.setParam0(k2)
																			.setParam1(class9_1.id);
																}
																if (j4 == 0)
																	menuEntry.setType(632);
																if (j4 == 1)
																	menuEntry.setType(661);
																if (j4 == 2)
																	menuEntry.setType(662);
																if (j4 == 3)
																	menuEntry.setType(663);
																if (j4 == 4)
																	menuEntry.setType(664);
															}
															menuManager.addEntry(menuEntry);
															setMenuOptionCount(getMenuOptionCount() + 1);
															callbacks.post(new MenuEntryAdded(menuEntry));
														}
													}
												}
											}
										}

										if (class9_1.parentID >= 58040 && class9_1.parentID <= 58048
												|| class9_1.parentID >= 32100 && class9_1.parentID <= 32156
												|| class9_1.parentID >= 32200 && class9_1.parentID <= 32222) {
											return;
										}

										if (class9_1.isItemSearchComponent) {
											MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
													.setOption("Select ")
													.setTarget("@lre@" + itemDef.name)
													.setIdentifier(10)
													.setType(1130)
													.setParam0(k2)
													.setWidget(class9_1)
													.setParam1(class9_1.id);
											menuManager.addEntry(menuEntry);
											setMenuOptionCount(getMenuOptionCount() + 1);
											callbacks.post(new MenuEntryAdded(menuEntry));
										} else {
											if (debugModels) {
												if (System.currentTimeMillis() - debugDelay > 1000) {
													debugDelay = System.currentTimeMillis();
													pushMessage("<col=255>" + itemDef.name + ":</col> Male: <col=255>"
															+ itemDef.maleModel0 + "</col> Female: <col=255>"
															+ itemDef.femaleModel0 + "</col> Model id: <col=255>"
															+ itemDef.modelId, 0, "");
													pushMessage("Zoom: <col=255>" + itemDef.zoom2d
															+ "</col> Rotation: <col=255>x" + itemDef.xan2d + " y"
															+ itemDef.yan2d + "", 0, "");
													pushMessage("Offset: <col=255>x" + itemDef.xOffset2d + " y"
															+ itemDef.yOffset2d, 0, "");
													//menuActions[menuActionRow] = "#2 (@whi@"
													//		+ Arrays.toString(itemDef.colorReplace) + ")@gre@)(@whi@"
													//		+ Arrays.toString(itemDef.colorFind) + ")@gre@)";
												}
											}
											String target = "@lre@" + itemDef.name;

											if (localPlayer.isAdminRights()) {
												target = "@lre@" + itemDef.name + " @whi@(" + itemID + " / " + itemDef.modelId + ")";
											}

											if (cosmetic_cost.containsKey(itemID)) {
												target = target + "@gre@$" + cosmetic_cost.get(itemID);
											}

											MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
													.setOption("Examine")
													.setTarget(target)
													.setIdentifier(itemID)
													.setType(1126)
													.setWidget(class9_1)
													.setParam0(k2)
													.setParam1(class9_1.id);
											menuManager.addEntry(menuEntry);
											setMenuOptionCount(getMenuOptionCount() + 1);
											callbacks.post(new MenuEntryAdded(menuEntry));
											if (class9_1.parentID == 64016) {
												hoverShopTile = k2;
											} else {
												hoverShopTile = -1;
											}
										}
									}
								}
								k2++;
							}

						}

					}
				}

			} catch (Exception | StackOverflowError e) {
				System.err.println(String.format("Error building interface menu: rootId=%d, childIndex=%d, childId=%d", class9.id, childIndex, class9.children[childIndex]));
				e.printStackTrace();
			}
		}
	}

	private void menuActionsRow(String action, int index, int actionID, int row) {
		if (menuOpen)
			return;
		MenuEntry menuEntry = (MenuEntry) new MenuEntry(index)
				.setOption(action)
				.setType(actionID);
		menuManager.addEntry(menuEntry);
		callbacks.post(new MenuEntryAdded(menuEntry));
		menuActionRow = row;
	}

	public void drawScrollbar(int height, int scrollPosition, int yPosition, int xPosition, int scrollMax) {
		getOldSchoolSprite(773).drawSprite(xPosition, yPosition);
		getOldSchoolSprite(788).drawSprite(xPosition, (yPosition + height) - 16);
		Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x000001, 16);
		Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x3d3426, 15);
		Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x342d21, 13);
		Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x2e281d, 11);
		Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x29241b, 10);
		Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x252019, 9);
		Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x000001, 1);
		int k1 = ((height - 32) * height) / scrollMax;
		if (k1 < 8)
			k1 = 8;
		int l1 = ((height - 32 - k1) * scrollPosition) / (scrollMax - height);
		Rasterizer2D.drawPixels(k1, yPosition + 16 + l1, xPosition, barFillColor, 16);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x000001, k1, xPosition);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x817051, k1, xPosition + 1);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x73654a, k1, xPosition + 2);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x6a5c43, k1, xPosition + 3);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x6a5c43, k1, xPosition + 4);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x655841, k1, xPosition + 5);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x655841, k1, xPosition + 6);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x61553e, k1, xPosition + 7);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x61553e, k1, xPosition + 8);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x5d513c, k1, xPosition + 9);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x5d513c, k1, xPosition + 10);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x594e3a, k1, xPosition + 11);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x594e3a, k1, xPosition + 12);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x514635, k1, xPosition + 13);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x4b4131, k1, xPosition + 14);
		Rasterizer2D.method339(yPosition + 16 + l1, 0x000001, 15, xPosition);
		Rasterizer2D.method339(yPosition + 17 + l1, 0x000001, 15, xPosition);
		Rasterizer2D.method339(yPosition + 17 + l1, 0x655841, 14, xPosition);
		Rasterizer2D.method339(yPosition + 17 + l1, 0x6a5c43, 13, xPosition);
		Rasterizer2D.method339(yPosition + 17 + l1, 0x6d5f48, 11, xPosition);
		Rasterizer2D.method339(yPosition + 17 + l1, 0x73654a, 10, xPosition);
		Rasterizer2D.method339(yPosition + 17 + l1, 0x76684b, 7, xPosition);
		Rasterizer2D.method339(yPosition + 17 + l1, 0x7b6a4d, 5, xPosition);
		Rasterizer2D.method339(yPosition + 17 + l1, 0x7e6e50, 4, xPosition);
		Rasterizer2D.method339(yPosition + 17 + l1, 0x817051, 3, xPosition);
		Rasterizer2D.method339(yPosition + 17 + l1, 0x000001, 2, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x000001, 16, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x564b38, 15, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x5d513c, 14, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x625640, 11, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x655841, 10, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x6a5c43, 7, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x6e6046, 5, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x716247, 4, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x7b6a4d, 3, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x817051, 2, xPosition);
		Rasterizer2D.method339(yPosition + 18 + l1, 0x000001, 1, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x000001, 16, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x514635, 15, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x564b38, 14, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x5d513c, 11, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x61553e, 9, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x655841, 7, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x6a5c43, 5, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x6e6046, 4, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x73654a, 3, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x817051, 2, xPosition);
		Rasterizer2D.method339(yPosition + 19 + l1, 0x000001, 1, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x000001, 16, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x4b4131, 15, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x544936, 14, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x594e3a, 13, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x5d513c, 10, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x61553e, 8, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x655841, 6, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x6a5c43, 4, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x73654a, 3, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x817051, 2, xPosition);
		Rasterizer2D.method339(yPosition + 20 + l1, 0x000001, 1, xPosition);
		Rasterizer2D.method341(yPosition + 16 + l1, 0x000001, k1, xPosition + 15);
	}

	/**
	 * NPC Updating
	 * <p>
	 * There is a crash at random npcs so i did some sort of cheap fix to stop it
	 * from throwing runtime exception which will ultimately log a player out, even
	 * regardless of where and when they are in the game, causes to many issues.
	 *
	 * @param stream
	 * @param i
	 */
	public void updateNPCs(Buffer stream, int i) {
		anInt839 = 0;
		anInt893 = 0;
		method139(stream);
		method46(i, stream);
		updateNpcState(stream);
		for (int k = 0; k < anInt839; k++) {
			int l = anIntArray840[k];
			if (npcs[l].anInt1537 != loopCycle) {
				callbacks.post(new NpcDespawned(npcs[l]));
				callbacks.post(new NpcChanged(npcs[l], npcs[l].getComposition()));
				npcs[l].desc = null;
				npcs[l] = null;
			}
		}

		// Cheaphax fix to it doesnt throw exception and boot player
		if (stream.currentPosition <= -1 || stream.currentPosition != i) {
			System.out.println("[NPC] Size mismatch : returning");
			return;
		}

		if (stream.currentPosition != i) {
			Signlink.reporterror(
					myUsername + " size mismatch in getnpcpos - pos:" + stream.currentPosition + " psize:" + i);
			throw new RuntimeException("eek");
		}

		for (int i1 = 0; i1 < npcCount; i1++)
			if (npcs[npcIndices[i1]] == null) {
				Signlink.reporterror(myUsername + " null entry in npc list - pos:" + i1 + " size:" + npcCount);
				throw new RuntimeException("eek");
			}
		callbacks.updateNpcs();
	}

	private int channelButtonHoverPosition;
	private int channelButtonClickPosition;

	public void processChatModeClick() {
		if (MouseHandler.mouseY >= canvasHeight - 22 && MouseHandler.mouseY <= canvasHeight) {
			if (MouseHandler.mouseX >= 5 && MouseHandler.mouseX <= 61) {
				channelButtonHoverPosition = 0;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 71 && MouseHandler.mouseX <= 127) {
				channelButtonHoverPosition = 1;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 137 && MouseHandler.mouseX <= 193) {
				channelButtonHoverPosition = 2;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 203 && MouseHandler.mouseX <= 259) {
				channelButtonHoverPosition = 3;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 269 && MouseHandler.mouseX <= 325) {
				channelButtonHoverPosition = 4;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 335 && MouseHandler.mouseX <= 391) {
				channelButtonHoverPosition = 5;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 404 && MouseHandler.mouseX <= 515) {
				channelButtonHoverPosition = 6;
				inputTaken = true;
			}
		} else {
			channelButtonHoverPosition = -1;
			inputTaken = true;
		}
		if (clickMode3 == 1) {
			if (MouseHandler.saveClickY >= canvasHeight - 22 && MouseHandler.saveClickY <= canvasHeight) {
				if (MouseHandler.saveClickX >= 5 && MouseHandler.saveClickX <= 61) {
					if (channelButtonClickPosition == 0)
						toggleHideChatArea();
					channelButtonClickPosition = 0;
					chatTypeView = 0;
					inputTaken = true;
				} else if (MouseHandler.saveClickX >= 71 && MouseHandler.saveClickX <= 127) {
					if (channelButtonClickPosition == 1)
						toggleHideChatArea();
					channelButtonClickPosition = 1;
					chatTypeView = 5;
					inputTaken = true;
				} else if (MouseHandler.saveClickX >= 137 && MouseHandler.saveClickX <= 193) {
					if (channelButtonClickPosition == 2)
						toggleHideChatArea();
					channelButtonClickPosition = 2;
					chatTypeView = 1;
					inputTaken = true;
				} else if (MouseHandler.saveClickX >= 203 && MouseHandler.saveClickX <= 259) {
					if (channelButtonClickPosition == 3)
						toggleHideChatArea();
					channelButtonClickPosition = 3;
					chatTypeView = 2;
					inputTaken = true;
				} else if (MouseHandler.saveClickX >= 269 && MouseHandler.saveClickX <= 325) {
					if (channelButtonClickPosition == 4)
						toggleHideChatArea();
					channelButtonClickPosition = 4;
					chatTypeView = 11;
					inputTaken = true;
				} else if (MouseHandler.saveClickX >= 335 && MouseHandler.saveClickX <= 391) {
					if (channelButtonClickPosition == 5)
						toggleHideChatArea();
					channelButtonClickPosition = 5;
					chatTypeView = 3;
					inputTaken = true;
				}
			}
		}
	}

	private static boolean hideChatArea = false;

	private static void toggleHideChatArea() {
		if (!instance.isResized())
			return;
		hideChatArea = !hideChatArea;
	}

	private static boolean hideInventoryArea = false;

	private static void toggleInventoryArea() {
		if (!instance.isResized())
			return;
		hideInventoryArea = !hideInventoryArea;
	}

	public void method33(int i) {
		if (i > Varp.cacheSize)
			return;

		if (Varp.cache[i] == null)
			return;

		int j = Varp.cache[i].anInt709;
		if (j == 0)
			return;
		int k = variousSettings[i];
		if (j == 1) {
//			if (k == 1)
//				Rasterizer.setBrightness(0.90000000000000002D);
//			if (k == 2)
//				Rasterizer.setBrightness(0.80000000000000004D);
//			if (k == 3)
//				Rasterizer.setBrightness(0.69999999999999996D);
//			if (k == 4)
//				Rasterizer.setBrightness(0.59999999999999998D);
			ItemDefinition.sprites.unlinkAll();
			welcomeScreenRaised = true;
		}
		if (j == 3) {
			int volume = 0;

			if (k == 0) {
				volume = 255;
			} else if (k == 1) {
				volume = 192;
			} else if (k == 2) {
				volume = 128;
			} else if (k == 3) {
				volume = 64;
			} else if (k == 4) {
				volume = 0;
			}

			if (volume != musicVolume) {
				if (musicVolume != 0 || currentSong == -1) {
					if (volume != 0) {
						// setVolume(volume);
					} else {
						// method55(false);
						prevSong = 0;
					}
				} else {
					// method56(volume, false, currentSong);
					prevSong = 0;
				}

				musicVolume = volume;
			}
		}
		if (j == 4) {
			if (k == 0) {
				soundEffectVolume = 127;
			} else if (k == 1) {
				soundEffectVolume = 96;
			} else if (k == 2) {
				soundEffectVolume = 64;
			} else if (k == 3) {
				soundEffectVolume = 32;
			} else if (k == 4) {
				soundEffectVolume = 0;
			}
		}
		if (j == 5)
			anInt1253 = k;
		if (j == 6)
			anInt1249 = k;
		if (j == 9)
			anInt913 = k;
	}

	public void updateEntities() {
		try {
			int anInt974 = 0;
			for (int j = -1; j < playerCount + npcCount; j++) {
				Object obj;
				if (j == -1) {
					obj = localPlayer;
				} else if (j < playerCount) {
					obj = players[playerIndices[j]];
				} else {
					obj = npcs[npcIndices[j - playerCount]];
				}
				if (obj == null || !((Entity) (obj)).isVisible())
					continue;
				if (hidelocalplayers && obj == localPlayer)
					continue;
				if (hideothers && obj instanceof Player && obj != localPlayer)
					continue;
				if (obj instanceof Npc) {
					NpcDefinition entityDef = ((Npc) obj).desc;
					if (entityDef.configs != null)
						entityDef = entityDef.method161();
					if (entityDef == null)
						continue;
				}
				/**
				 * Players
				 */
				if (j < playerCount) {
					int l = 30;
					Player player = (Player) obj;
					if (player.headIcon >= 0) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1) {
							if (player.skullIcon < 2) {
								skullIcons[player.skullIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
								l += 25;
							}
							if (player.headIcon < 18) {
								headIcons[player.headIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
								l += 18;
							}
						}
					}
					if (player.centurion > -1) {
						Sprite iconSprite = SpriteLoader.fetchAnimatedSprite(Signlink.getCacheDirectory() + "sprites/gifs/" + player.centurion + ".gif").getInstance(13, 13);
						iconSprite.drawAdvancedSprite(spriteDrawX - (13 / 2), spriteDrawY - l);
						l += 19;
					}
					if (j >= 0 && hintType == 10 && anInt933 == playerIndices[j]) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);

						if (spriteDrawX > -1)
							headIconsHint[player.hintIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
					}
					if(Configuration.playerNames) {
						if (player.hasRightsOtherThan(PlayerRights.PLAYER) && !player.hideDonor) {
							latoBold.drawCenteredString(PlayerRights.buildCrownStringminus(player.getRights())+" "+player.displayName, spriteDrawX, spriteDrawY + 7, 0x6495ed, 1);
						} else {
							latoBold.drawCenteredString(player.displayName, spriteDrawX, spriteDrawY + 7, 0x6495ed, 1);
						}
					}//

				} else {
					NpcDefinition entityDef_1 = ((Npc) obj).desc;
					if (entityDef_1.headIcon >= 0 && entityDef_1.headIcon < headIcons.length) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1)
							headIcons[entityDef_1.headIcon].drawSprite(spriteDrawX - 12, spriteDrawY - 30);
					}
					if (hintType == 1 && anInt1222 == npcIndices[j - playerCount] && loopCycle % 20 < 10) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1)
							headIconsHint[0].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
					}
				}
				if (((Entity) (obj)).textSpoken != null && (j >= playerCount || publicChatMode == 0
						|| publicChatMode == 3 || publicChatMode == 1 && isFriendOrSelf(((Player) obj).displayName))) {
					npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height);
					if (spriteDrawX > -1 && anInt974 < anInt975) {
						anIntArray979[anInt974] = chatTextDrawingArea.method384(((Entity) (obj)).textSpoken) / 2;
						anIntArray978[anInt974] = chatTextDrawingArea.anInt1497;
						anIntArray976[anInt974] = spriteDrawX;
						anIntArray977[anInt974] = spriteDrawY;
						anIntArray980[anInt974] = ((Entity) (obj)).anInt1513;
						anIntArray981[anInt974] = ((Entity) (obj)).anInt1531;
						anIntArray982[anInt974] = ((Entity) (obj)).textCycle;
						aStringArray983[anInt974++] = ((Entity) (obj)).textSpoken;
						if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 >= 1 && ((Entity) (obj)).anInt1531 <= 3) {
							anIntArray978[anInt974] += 10;
							anIntArray977[anInt974] += 5;
						}
						if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 == 4)
							anIntArray979[anInt974] = 60;
						if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 == 5)
							anIntArray978[anInt974] += 5;
					}
				}
				if (((Entity) (obj)).loopCycleStatus > loopCycle) {
					try {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1) {
							int i1 = (((Entity) (obj)).currentHealth * 30) / ((Entity) (obj)).maxHealth;
							int i2 = (((Entity) (obj)).currentHealth * 30) / ((Entity) (obj)).maxHealth;
							if (i1 > 30)
								i1 = 30;
							Rasterizer2D.drawPixels(5, spriteDrawY - 3, spriteDrawX - 15, 65280, i1);
							Rasterizer2D.drawPixels(5, spriteDrawY - 3, (spriteDrawX - 15) + i1, 0xff0000, 30 - i1);
						}
					} catch (Exception e) {
					}
				}
				for (int j1 = 0; j1 < 4; j1++)
					if (((Entity) (obj)).hitsLoopCycle[j1] > loopCycle) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height / 2);

						if (spriteDrawX > -1) {
							if (j1 == 1)
								spriteDrawY -= 20;
							if (j1 == 2) {
								spriteDrawX -= 15;
								spriteDrawY -= 10;
							}
							if (j1 == 3) {
								spriteDrawX += 15;
								spriteDrawY -= 10;
							}
							if (((Entity) (obj)).hitMarkTypes[j1] == 16) { // Heal hitsplat
								spriteDrawX += 1;
								spriteDrawY -= 1;
							}

							if (entityTarget != null && ((entityTarget.target == obj && localPlayer.playerId == ((Entity) (obj)).hitSource[j1]) || obj == localPlayer)) {
								hitMarks[((Entity) (obj)).hitMarkTypes[j1]].drawSprite(spriteDrawX - 12, spriteDrawY - 12);
							} else {
								int hitType = ((Entity) (obj)).hitMarkTypes[j1];
								if (hitType == 16) {
									hitType = 8;
								}
								hitMarks[hitType+8].drawSprite(spriteDrawX - 12, spriteDrawY - 12);
							}

							smallText.drawText(0, String.valueOf(((Entity) (obj)).hitArray[j1]), spriteDrawY + 4,
									spriteDrawX);

							smallText.drawText(0xffffff, String.valueOf(((Entity) (obj)).hitArray[j1]), spriteDrawY + 3,
									spriteDrawX - 1);

						}
					}
			}
			for (int k = 0; k < anInt974; k++) {
				int k1 = anIntArray976[k];
				int l1 = anIntArray977[k];
				int j2 = anIntArray979[k];
				int k2 = anIntArray978[k];
				boolean flag = true;
				while (flag) {
					flag = false;
					for (int l2 = 0; l2 < k; l2++)
						if (l1 + 2 > anIntArray977[l2] - anIntArray978[l2] && l1 - k2 < anIntArray977[l2] + 2
								&& k1 - j2 < anIntArray976[l2] + anIntArray979[l2]
								&& k1 + j2 > anIntArray976[l2] - anIntArray979[l2]
								&& anIntArray977[l2] - anIntArray978[l2] < l1) {
							l1 = anIntArray977[l2] - anIntArray978[l2];
							flag = true;
						}

				}
				spriteDrawX = anIntArray976[k];
				spriteDrawY = anIntArray977[k] = l1;

				String s = aStringArray983[k];
				if (anInt1249 == 0) {
					int i3 = 0xffff00;
					if (anIntArray980[k] < 6)
						i3 = anIntArray965[anIntArray980[k]];
					if (anIntArray980[k] == 6)
						i3 = anInt1265 % 20 >= 10 ? 0xffff00 : 0xff0000;
					if (anIntArray980[k] == 7)
						i3 = anInt1265 % 20 >= 10 ? 65535 : 255;
					if (anIntArray980[k] == 8)
						i3 = anInt1265 % 20 >= 10 ? 0x80ff80 : 45056;
					if (anIntArray980[k] == 9) {
						int j3 = 150 - anIntArray982[k];
						if (j3 < 50)
							i3 = 0xff0000 + 1280 * j3;
						else if (j3 < 100)
							i3 = 0xffff00 - 0x50000 * (j3 - 50);
						else if (j3 < 150)
							i3 = 65280 + 5 * (j3 - 100);
					}
					if (anIntArray980[k] == 10) {
						int k3 = 150 - anIntArray982[k];
						if (k3 < 50)
							i3 = 0xff0000 + 5 * k3;
						else if (k3 < 100)
							i3 = 0xff00ff - 0x50000 * (k3 - 50);
						else if (k3 < 150)
							i3 = (255 + 0x50000 * (k3 - 100)) - 5 * (k3 - 100);
					}
					if (anIntArray980[k] == 11) {
						int l3 = 150 - anIntArray982[k];
						if (l3 < 50)
							i3 = 0xffffff - 0x50005 * l3;
						else if (l3 < 100)
							i3 = 65280 + 0x50005 * (l3 - 50);
						else if (l3 < 150)
							i3 = 0xffffff - 0x50000 * (l3 - 100);
					}
					/**
					 * Entity chat
					 */
					if (anIntArray981[k] == 0) {
						chatTextDrawingArea.drawText(0, s, spriteDrawY + 1, spriteDrawX + 1);
						chatTextDrawingArea.drawText(i3, s, spriteDrawY, spriteDrawX);
					}
					if (anIntArray981[k] == 1) {
						chatTextDrawingArea.method386(0, s, spriteDrawX + 1, anInt1265, spriteDrawY + 1);
						chatTextDrawingArea.method386(i3, s, spriteDrawX, anInt1265, spriteDrawY);
					}
					if (anIntArray981[k] == 2) {
						chatTextDrawingArea.method387(spriteDrawX + 1, s, anInt1265, spriteDrawY + 1, 0);
						chatTextDrawingArea.method387(spriteDrawX, s, anInt1265, spriteDrawY, i3);
					}
					if (anIntArray981[k] == 3) {
						chatTextDrawingArea.method388(150 - anIntArray982[k], s, anInt1265, spriteDrawY + 1,
								spriteDrawX + 1, 0);
						chatTextDrawingArea.method388(150 - anIntArray982[k], s, anInt1265, spriteDrawY, spriteDrawX,
								i3);
					}
					if (anIntArray981[k] == 4) {
						int i4 = chatTextDrawingArea.method384(s);
						int k4 = ((150 - anIntArray982[k]) * (i4 + 100)) / 150;
						Rasterizer2D.setDrawingArea(334, spriteDrawX - 50, spriteDrawX + 50, 0);
						chatTextDrawingArea.method385(0, s, spriteDrawY + 1, (spriteDrawX + 50) - k4);
						chatTextDrawingArea.method385(i3, s, spriteDrawY, (spriteDrawX + 50) - k4);
						Rasterizer2D.defaultDrawingAreaSize();
					}
					if (anIntArray981[k] == 5) {
						int j4 = 150 - anIntArray982[k];
						int l4 = 0;
						if (j4 < 25)
							l4 = j4 - 25;
						else if (j4 > 125)
							l4 = j4 - 125;
						Rasterizer2D.setDrawingArea(spriteDrawY + 5, 0, 516,
								spriteDrawY - chatTextDrawingArea.anInt1497 - 1);
						chatTextDrawingArea.drawText(0, s, spriteDrawY + 1 + l4, spriteDrawX);
						chatTextDrawingArea.drawText(i3, s, spriteDrawY + l4, spriteDrawX);
						Rasterizer2D.defaultDrawingAreaSize();
					}
				} else {
					chatTextDrawingArea.drawText(0, s, spriteDrawY + 1, spriteDrawX);
					chatTextDrawingArea.drawText(0xffff00, s, spriteDrawY, spriteDrawX);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delFriend(long l) {
		try {
			if (l == 0L)
				return;
			for (int i = 0; i < friendsCount; i++) {
				if (friendsListAsLongs[i] != l)
					continue;
				friendsCount--;
				needDrawTabArea = true;
				for (int j = i; j < friendsCount; j++) {
					friendsList[j] = friendsList[j + 1];
					friendsNodeIDs[j] = friendsNodeIDs[j + 1];
					friendsListAsLongs[j] = friendsListAsLongs[j + 1];
				}

				stream.createFrame(215);
				stream.writeQWord(l);
				break;
			}
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("18622, " + false + ", " + l + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	Sprite tabAreaFixed;
	Sprite[] tabAreaResizable = new Sprite[3];
	private boolean drawingTabArea = false;

	public static void insertPixels(int[][] pixels, Sprite image, int x, int y, boolean ignoreTransparency) {
		int[][] imagePixels = Utility.d1Tod2(image.myPixels, image.myWidth);

		for(int j = y; j < y + image.myHeight; j++) {
			for(int i = x; i < x + image.myWidth; i++) {
				if (ignoreTransparency && imagePixels[j-y][i-x] == 0)
					continue;
				pixels[j][i] = imagePixels[j-y][i-x];
			}
		}
	}

	public static void fillPixels(int[][] pixels, Sprite image, int startX, int startY, int endX, int endY) {
		int[][] imagePixels = Utility.d1Tod2(image.myPixels, image.myWidth);

		for(int j = startY; j < endY; j++) {
			for(int i = startX; i <endX; i++) {
				pixels[j][i] = imagePixels[(j - startY)%image.myHeight][(i - startX)%image.myWidth];
			}
		}
	}
	public Sprite buildCombatButton(int width, int height, boolean active) {
		int[][] pixels = new int[height][width];

		// Background
		fillPixels(pixels, getOldSchoolSprite(1145 + (active ? 9 : 0)), 6, 6, width - 6, height - 6);

		// Top border
		fillPixels(pixels, getOldSchoolSprite(1142 + (active ? 9 : 0)), 6, 0, width - 6, 6);

		// Left border
		fillPixels(pixels, getOldSchoolSprite(1144 + (active ? 9 : 0)), 0, 6, 6, height - 6);

		// Right border
		fillPixels(pixels, getOldSchoolSprite(1146 + (active ? 9 : 0)), width - 6, 6, width, height - 6);

		// Bottom border
		fillPixels(pixels, getOldSchoolSprite(1148 + (active ? 9 : 0)), 6, height - 6, width - 6, height);

		// Top left corner
		insertPixels(pixels, getOldSchoolSprite(1141 + (active ? 9 : 0)), 0, 0, true);

		// Top right corner
		insertPixels(pixels, getOldSchoolSprite(1143 + (active ? 9 : 0)), width - 6, 0, true);

		// Bottom left corner
		insertPixels(pixels, getOldSchoolSprite(1147 + (active ? 9 : 0)), 0, height - 6, true);

		// Bottom right corner
		insertPixels(pixels, getOldSchoolSprite(1149 + (active ? 9 : 0)), width - 6, height - 6, true);

		if(width == 150 && height == 44) {
			// Auto Retaliate Button
			insertPixels(pixels, getOldSchoolSprite(1748 + (active ? 1 : 0)), 8, 3, true);
		}
		return new Sprite(width, height, 0, 0, Utility.d2Tod1(pixels));
	}
	public Sprite buildBackground(int width, int height, Sprite sprite, boolean borders) {
		int[][] pixels = new int[height][width];

		// Background
		fillPixels(pixels, sprite, 0, 0, width, height);
		if(borders) {
			// Top border
			fillPixels(pixels, getOldSchoolSprite(2835), 8, 0, width - 8, 6);

			// Left border
			fillPixels(pixels, getOldSchoolSprite(2837), 0, 4, 6, height - 4);

			// Right border
			fillPixels(pixels, getOldSchoolSprite(2838), width - 4, 4, width, height - 4);

			// Bottom border
			fillPixels(pixels, getOldSchoolSprite(2836), 8, height - 8, width - 8, height);

			// Top left corner
			insertPixels(pixels, getOldSchoolSprite(3382), 0, 0, true);

			// Top right corner
			insertPixels(pixels, getOldSchoolSprite(3383), width - 8, 0, true);

			// Bottom left corner
			insertPixels(pixels, getOldSchoolSprite(3384), 0, height - 8, true);

			// Bottom right corner
			insertPixels(pixels, getOldSchoolSprite(3385), width - 8, height - 8, true);
		}
		return new Sprite(width, height, 0, 0, Utility.d2Tod1(pixels));
	}

	public void drawTabArea() {
		drawingTabArea = true;
		boolean fixedMode = !isResized();
		final int xOffset = !isResized() ? 516 : 0;
		final int yOffset = !isResized() ? 168 : 0;//240

		rasterProvider.setRaster();
		Rasterizer3D.scanOffsets = anIntArray1181;
		if (fixedMode) {
			getOldSchoolSprite(1031).drawSprite(547, 205);

			getOldSchoolSprite(1033).drawSprite(516, 205);
			getOldSchoolSprite(1034).drawSprite(519, 338);

			getOldSchoolSprite(1035).drawSprite(737, 205);
			getOldSchoolSprite(1036).drawSprite(516, 167);
			getOldSchoolSprite(1032).drawSprite(519, 466);//463
			//getOldSchoolSprite(1611).drawSprite(516, 499);
			//tabAreaFixed.drawSprite(xOffset,yOffset);
			if (invOverlayInterfaceID == 0)
				drawTabs(xOffset,yOffset);

		} else {
			int x = Client.canvasWidth - (stackTabs() ? 231 : 462);
			int y = Client.canvasHeight - (stackTabs() ? 73 : 37);
			if(stackTabs()) {

			} else {
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
				x += 33;
				getOldSchoolSprite(1180).drawSprite(x, y);
			}
			//(stackTabs() ? tabAreaResizable[1] : tabAreaResizable[2]).drawSprite(x, y);

			if (showTabComponents) {
				buildBackground(stackTabs() ? 209 : 207, stackTabs() ? 288 : 274, getOldSchoolSprite(897), !stackTabs())
						.drawSprite((Client.canvasWidth - 197 - (stackTabs() ? 33 : 8)),
								Client.canvasHeight - 280 + 47 - (stackTabs() ? 82 : 77));
				if(stackTabs()) {
					getOldSchoolSprite(1173).drawAdvancedSprite(
							(Client.canvasWidth - 241),
							Client.canvasHeight - 310 - 37 + 15
					);
					getOldSchoolSprite(1174).drawAdvancedSprite(
							(Client.canvasWidth - 241),
							Client.canvasHeight - 37
					);
					getOldSchoolSprite(1175).drawAdvancedSprite(
							(Client.canvasWidth - 54 - 197 + 15),
							Client.canvasHeight - 280 + 47 - 78 + 15
					);
					getOldSchoolSprite(1176).drawAdvancedSprite(
							(Client.canvasWidth - 26),
							Client.canvasHeight - 280 + 47 - 78 + 15
					);
				}
				//getOldSchoolSprite(897).drawSprite((Client.canvasWidth - 204), Client.canvasHeight - 275 - (stackTabs() ? 73 : 37));
				//tabAreaResizable[0].drawSprite((Client.canvasWidth - 204), Client.canvasHeight - 275 - (stackTabs() ? 73 : 37));
			}

			if (invOverlayInterfaceID == 0)
				drawTabs(xOffset,yOffset);
		}
		int y = stackTabs() ? 73 : 37;
		//drawHpBar(xOffset,yOffset);
		//drawPrayerBar(xOffset,yOffset);


		// Set open tab to one that has an interface
		if (tabInterfaceIDs[tabID] <= 0) {
			for (int i = tabID; i >= 0; i--) {
				if (tabInterfaceIDs[i] >= 1) {
					tabID = i;
					break;
				}
			}

			if (tabInterfaceIDs[tabID] <= 0)
				tabID = 3;
		}

		if (invOverlayInterfaceID != 0 && showTabComponents) {
			drawInterface(0,
					(fixedMode ? 31 + xOffset :
							canvasWidth - 197 - (stackTabs() ? 16 : 1)),
					RSInterface.interfaceCache[invOverlayInterfaceID],
					(fixedMode ? 37 + yOffset :
							canvasHeight - 275 + (stackTabs() ? 42 : -1) - y + 10));
		} else if (Client.tabInterfaceIDs[Client.tabID] != -1  && showTabComponents) {
			try {
				drawInterface(0,
						(fixedMode ? 31 + xOffset :
								canvasWidth - 197 - (stackTabs() ? 16 : 1)),
						RSInterface.interfaceCache[Client.tabInterfaceIDs[Client.tabID]],
						(fixedMode ? 37 + yOffset :
								canvasHeight - 275 + (stackTabs() ? 42 : -1) - y + 10));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (drawTabInterfaceHover != 0 && showTabComponents) {
			RSInterface parent = RSInterface.interfaceCache[drawTabInterfaceHoverParent];
			if (drawTabInterfaceHoverTimer == 0 || drawTabInterfaceHoverLast != drawTabInterfaceHover)
				drawTabInterfaceHoverTimer = System.currentTimeMillis();
			drawTabInterfaceHoverLast = drawTabInterfaceHover;
			if (System.currentTimeMillis() - drawTabInterfaceHoverTimer >= parent.hoverInterfaceDelay) {
				RSInterface hover = RSInterface.interfaceCache[drawTabInterfaceHover];
				int xOffset1 = (fixedMode ? 516 + xOffset : 0);
				int yOffset1 = (fixedMode ? 168 + yOffset : 0);
				int drawingAreaWidth = canvasWidth - xOffset1;
				int drawingAreaHeight = canvasHeight - yOffset1;
				int mouseX = MouseHandler.mouseX - xOffset1;
				int mouseY = MouseHandler.mouseY - yOffset1;
				int interfaceWidth = hover.width + 12; // Was drawing offscreen a bit, don't have time to proper fix

				if (mouseX + interfaceWidth > drawingAreaWidth) {
					mouseX = drawingAreaWidth - interfaceWidth;
				}

				if (mouseY + hover.height > drawingAreaHeight) {
					mouseY = drawingAreaHeight - hover.height;
				}

				drawInterface(0, mouseX, hover, mouseY, true);
			}
		} else {
			drawTabInterfaceHoverTimer = 0;
		}



		if (menuOpen && showTabComponents) {
			drawMenu(menuOffsetX, menuOffsetY);
		}

		Rasterizer3D.scanOffsets = anIntArray1182;

		drawingTabArea = false;
	}

	Sprite[] sideIcons = new Sprite[15];
	Sprite[] redStones = new Sprite[6];

	Sprite hpBarSprite = new Sprite("bars/hp");
	Sprite prayerBarSprite = new Sprite("bars/prayer");

	private void drawHpBar(int xOffset,int yOffset) {
		try {
			int offsetX = xOffset;
			int offsetY = yOffset;
			int offsetX2 = 0;
			int offsetWidth = 0;
			if (Client.instance.isResized()) {
				offsetX = canvasWidth - 246;
				offsetY = canvasHeight - 337;
				if (!showTabComponents) {
					offsetX = canvasWidth - 46;
					offsetY = canvasHeight - 337;
				}
				// if (changeTabArea) {
				offsetX += 2;
				offsetY -= 30;
				offsetX2 -= 3;
				offsetWidth -= 6;
				if (canvasWidth >= 1000)
					offsetY += 36;
				// }
			}
			int level = currentLevels[3];
			int max = maximumLevels[3];
			if (max == 0)
				max = 1;
			if (level > max)
				level = max;
			int percent = level * 100 / max;
			int toPixels = -250 * percent / 100;
			TextDrawingArea.drawRectangle(offsetX + 12, offsetY + 43, offsetWidth + 18, 250, 0x771111);
			TextDrawingArea.drawTransparentBox(offsetX + 12, offsetY + 43, offsetWidth + 18, 250, 0x000000, 125);
			TextDrawingArea.drawTransparentBox(offsetX + 12, offsetY + 293 + toPixels, offsetWidth + 18,
					250 * percent / 100, 0xaa0000, 100);
			newSmallFont.drawCenteredString(Integer.toString(currentLevels[3]), offsetX2 + offsetX + 21, offsetY + 74,
					0xffffff, 1);
			hpBarSprite.drawSprite(offsetX2 + offsetX + 14, offsetY + 45);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void drawPrayerBar(int xOffset,int yOffset) {
		try {
			int offsetX = xOffset;
			int offsetY = yOffset;
			int offsetX2 = 0;
			int offsetWidth = 0;
			if (Client.instance.isResized()) {
				offsetX = canvasWidth - 247;
				offsetY = canvasHeight - 337;
				if (!showTabComponents) {
					offsetX = canvasWidth - 47;
					offsetY = canvasHeight - 337;
				}
				// if (changeTabArea) {
				offsetX -= 193;
				offsetY -= 30;
				offsetX2 -= 3;
				offsetWidth -= 6;
				if (canvasWidth >= 1000)
					offsetY += 36;
				// }
			}


			int level = currentLevels[5];
			int max = maximumLevels[5];
			if (max == 0)
				max = 1;
			int percent = level * 100 / max;
			int toPixels = -250 * percent / 100;
			TextDrawingArea.drawRectangle(offsetX + 222, offsetY + 43, offsetWidth + 18, 250, 0x118963);
			TextDrawingArea.drawTransparentBox(offsetX + 222, offsetY + 43, offsetWidth + 18, 250, 0x000000, 125);
			TextDrawingArea.drawTransparentBox(offsetX + 222, offsetY + 293 + toPixels, offsetWidth + 18,
					250 * percent / 100, 0x00d9b3, 100);
			newSmallFont.drawCenteredString(Integer.toString(level), offsetX2 + offsetX + 231, offsetY + 74, 0xffffff,
					1);
			prayerBarSprite.drawSprite(offsetX2 + offsetX + 224, offsetY + 45);
			sendFrame126(currentLevels[5]+"/"+ maximumLevels[5], 22499);
			// System.out.println(gameScreenWidth);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void drawTabs(int xOffset,int yOffset) {
		if (!isResized()) {
			final int[][] sideIconCoordinates = new int[][] {
					{ 10, 10 }, { 45, 10 }, { 77, 10 }, { 109, 10 }, { 143, 10 }, { 176, 10 }, { 209, 10 },
					{ 10, 306 }, { 45, 306 }, { 78, 306 }, { 109, 306 }, { 143, 306 }, { 175, 306 }, { 215, 310 }, { 212, 310 }
			};
			final int[][] sideIconCoordinates1 = new int[][] { { 24, 8 }, { 49, 5}, { 79, 5 }, { 108, 3}, { 147, 5 },
					{ 176, 5 }, { 205, 8 }, { 22, 300 }, { 49, 304 }, { 77, 304 }, { 111, 303 }, { 147, 301 },
					{ 180, 303 }, { 204, 303 }, { 207, 305 } };
			if (Client.tabInterfaceIDs[Client.tabID] != -1) {
				int stoneY = yOffset - 306;
				if(!getUserSettings().isOldGameframe()) {
					if (Client.tabID == 0)
						getOldSchoolSprite(1026 + 0).drawSprite(5 + xOffset, yOffset);
					if (Client.tabID == 1)
						getOldSchoolSprite(1026 + 4).drawSprite(43 + xOffset, yOffset);
					if (Client.tabID == 2)
						getOldSchoolSprite(1026 + 4).drawSprite(76 + xOffset, yOffset);
					if (Client.tabID == 3)
						getOldSchoolSprite(1026 + 4).drawSprite(109 + xOffset,yOffset);
					if (Client.tabID == 4)
						getOldSchoolSprite(1026 + 4).drawSprite(142 + xOffset, yOffset);
					if (Client.tabID == 5)
						getOldSchoolSprite(1026 + 4).drawSprite(175 + xOffset,yOffset);
					if (Client.tabID == 6)
						getOldSchoolSprite(1026 + 1).drawSprite(208 + xOffset, yOffset);
					if (Client.tabID == 7)
						getOldSchoolSprite(1026 + 2).drawSprite(5 + xOffset, 298 + yOffset);
					if (Client.tabID == 8)
						getOldSchoolSprite(1026 + 4).drawSprite(43 + xOffset, 298 + yOffset);
					if (Client.tabID == 9)
						getOldSchoolSprite(1026 + 4).drawSprite(76 + xOffset, 298 + yOffset);
					if (Client.tabID == 10)
						getOldSchoolSprite(1026 + 4).drawSprite(109 + xOffset, 298 + yOffset);
					if (Client.tabID == 11)
						getOldSchoolSprite(1026 + 4).drawSprite(142 + xOffset, 298 + yOffset);
					if (Client.tabID == 12)
						getOldSchoolSprite(1026 + 4).drawSprite(175 + xOffset, 298 + yOffset);
					if (Client.tabID == 13)
						getOldSchoolSprite(1026 + 3).drawSprite(208 + xOffset, 298 + yOffset);
				}else {
					if (Client.tabID == 0)
						getOldSchoolSprite(1026 + 1).drawSprite(14 + xOffset, stackTabs() ? stoneY : yOffset);
					if (Client.tabID == 1)
						getOldSchoolSprite(1026 + 2).drawSprite(47 + xOffset, stackTabs() ? stoneY : yOffset);
					if (Client.tabID == 2)
						getOldSchoolSprite(1026 + 2).drawSprite(74 + xOffset, stackTabs() ? stoneY : yOffset);
					if (Client.tabID == 3)
						getOldSchoolSprite(1026 + 3).drawSprite(102 + xOffset, stackTabs() ? stoneY : yOffset);
					if (Client.tabID == 4)
						getOldSchoolSprite(1026 + 2).drawSprite(144 + xOffset, stackTabs() ? stoneY : yOffset);
					if (Client.tabID == 5)
						getOldSchoolSprite(1026 + 2).drawSprite(172 + xOffset, stackTabs() ? stoneY : yOffset);
					if (Client.tabID == 6)
						getOldSchoolSprite(1026 + 0).drawSprite(201 + xOffset, stackTabs() ? stoneY : yOffset);
					if (Client.tabID == 7)
						getOldSchoolSprite(1026 + 4).drawSprite(13 + xOffset, 296 + yOffset);
					if (Client.tabID == 8)
						getOldSchoolSprite(1026 + 2).drawSprite(46 + xOffset, 297 + yOffset);
					if (Client.tabID == 9)
						getOldSchoolSprite(1026 + 2).drawSprite(74 + xOffset, 298 + yOffset);
					if (Client.tabID == 10)
						getOldSchoolSprite(1026 + 3).drawSprite(102 + xOffset, 297 + yOffset);
					if (Client.tabID == 11)
						getOldSchoolSprite(1026 + 2).drawSprite(144 + xOffset, 296 + yOffset);
					if (Client.tabID == 12)
						getOldSchoolSprite(1026 + 2).drawSprite(171 + xOffset, 296 + yOffset);
					if (Client.tabID == 13)
						getOldSchoolSprite(1026 + 3).drawSprite(201 + xOffset, 298 + yOffset);
				}

			}
			for (int index = 0; index <= 14; index++) {
				if (Client.tabInterfaceIDs[index] > 0) {
					Sprite sprite = getOldSchoolSprite(774 + index);
					if (!getUserSettings().isOldGameframe()) {
						if (index != 13 || (index == 13 && pollActive)) {
							sprite.drawSprite(sideIconCoordinates[index][0] + xOffset, sideIconCoordinates[index][1] + yOffset - 8);
						} else {
							sideIcons[index + 1].drawSprite(sideIconCoordinates[index][0] + xOffset, sideIconCoordinates[index][1] + yOffset - 8);
						}
					} else {
						sprite.drawSprite(sideIconCoordinates1[index][0] + xOffset, sideIconCoordinates1[index][1] + yOffset);
					}
				}
			}
		} else {
			final int[][] sideIconOffsets = stackTabs() ?
					new int[][] {
							{ -6, 1 }, { -4, 1 }, { -5, 1 }, { -6, 1 }, { -5, 1 }, { -5, 1 }, { -4, 2 },
							{ -6, 0 }, { -4, 0 }, { -5, 0 }, { -6, 0 }, { -5, 0 }, { -5, 0 }, { -4, 0 }
					}
			:
			new int[][] { { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 },
					{ 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 } };
			int x = Client.canvasWidth - (stackTabs() ? 231 : 462) + xOffset;
			int y = Client.canvasHeight - (stackTabs() ? 73 : 37) + yOffset;
			for (int tabIndex = 0; tabIndex < 14; tabIndex++) {
				if (Client.tabID == tabIndex && showTabComponents) {
					if(stackTabs()) {
						if (Client.tabID == 0)
							getOldSchoolSprite(1026).drawSprite(x - 9, stackTabs() ? y - 260 : y);
						if (Client.tabID == 1)
							getOldSchoolSprite(1026 + 4).drawSprite(x - 6, stackTabs() ? y - 260 : y);
						if (Client.tabID == 2)
							getOldSchoolSprite(1026 + 4).drawSprite(x - 4, stackTabs() ? y - 260 : y);
						if (Client.tabID == 3)
							getOldSchoolSprite(1026 + 4).drawSprite(x - 6, stackTabs() ? y - 260 : y);
						if (Client.tabID == 4)
							getOldSchoolSprite(1026 + 4).drawSprite(x - 5, stackTabs() ? y - 260 : y);
						if (Client.tabID == 5)
							getOldSchoolSprite(1026 + 4).drawSprite(x - 4, stackTabs() ? y - 260 : y);
						if (Client.tabID == 6)
							getOldSchoolSprite(1026 + 1).drawSprite(x - 6, stackTabs() ? y - 260 : y);

						if (Client.tabID == 7)
							getOldSchoolSprite(1026 + 2).drawSprite(x - 9,  y);
						if (Client.tabID == 8)
							getOldSchoolSprite(1026 + 4).drawSprite(x - 6,  y);
						if (Client.tabID == 9)
							getOldSchoolSprite(1026 + 4).drawSprite(x - 4,  y);
						if (Client.tabID == 10)
							getOldSchoolSprite(1026 + 4).drawSprite(x - 6,  y);
						if (Client.tabID == 11)
							getOldSchoolSprite(1026 + 4).drawSprite(x - 5, y);
						if (Client.tabID == 12)
							getOldSchoolSprite(1026 + 4).drawSprite(x - 4, y);
						if (Client.tabID == 13)
							getOldSchoolSprite(1026 + 3).drawSprite(x - 6, y);
					} else {
						getOldSchoolSprite(1026 + 4).drawSprite(x, stackTabs() && tabIndex <= 6 ? y - 273 : y);
					}
				}
				if (stackTabs()) {
					if (tabIndex != 6) {
						x += 33;
					} else if (tabIndex == 6) {
						y += 36;
						x = Client.canvasWidth - 231;
					}
				} else {
					x += 33;
				}
			}
			x = Client.canvasWidth - (stackTabs() ? 231 : 462) + xOffset;
			y = Client.canvasHeight - (stackTabs() ? 73 : 37) + yOffset;
			for (int index = 0; index < 14; index++) {
				if (Client.tabInterfaceIDs[index] != -1) {
					getOldSchoolSprite(774 + index).drawSprite(
							x + sideIconOffsets[index][0],
							y + sideIconOffsets[index][1] - (stackTabs() && index <= 6 ? 261 : 0)
					);
				}
				if (stackTabs()) {
					if (index != 6) {
						x += 33;
					} else if (index == 6) {
						y += 36;
						x = Client.canvasWidth - 231;
					}
				} else {
					x += 33;
				}
			}
		}

		if (openInterfaceID == 25650) {
			if (!isResized()) {
				grandExchangeSprite4.drawSprite1(29 + xOffset, 37 + yOffset, 162 + (int) (50 * Math.sin(loopCycle / 15.0)));
			} else {
				grandExchangeSprite4.drawSprite1((canvasWidth - 197) + xOffset,
						(stackTabs() ? canvasHeight - 341 : canvasHeight - 305) + yOffset,
						162 + (int) (50 * Math.sin(loopCycle / 15.0)));
			}
		}
	}


	public void processMobChatText() {
		for (int i = -1; i < playerCount; i++) {
			int j;
			if (i == -1)
				j = maxPlayerCount;
			else
				j = playerIndices[i];
			Player player = players[j];
			if (player != null && player.textCycle > 0) {
				player.textCycle--;
				if (player.textCycle == 0)
					player.textSpoken = null;
			}
		}
		for (int k = 0; k < npcCount; k++) {
			int l = npcIndices[k];
			Npc npc = npcs[l];
			if (npc != null && npc.textCycle > 0) {
				npc.textCycle--;
				if (npc.textCycle == 0)
					npc.textSpoken = null;
			}
		}
	}

	public void calcCameraPos() {
		int i = x * 128 + 64;
		int j = y * 128 + 64;
		int k = getCenterHeight(plane, j, i) - height;
		if (xCameraPos < i) {
			xCameraPos += anInt1101 + ((i - xCameraPos) * angle) / 1000;
			if (xCameraPos > i)
				xCameraPos = i;
		}
		if (xCameraPos > i) {
			xCameraPos -= anInt1101 + ((xCameraPos - i) * angle) / 1000;
			if (xCameraPos < i)
				xCameraPos = i;
		}
		if (zCameraPos < k) {
			zCameraPos += anInt1101 + ((k - zCameraPos) * angle) / 1000;
			if (zCameraPos > k)
				zCameraPos = k;
		}
		if (zCameraPos > k) {
			zCameraPos -= anInt1101 + ((zCameraPos - k) * angle) / 1000;
			if (zCameraPos < k)
				zCameraPos = k;
		}
		if (yCameraPos < j) {
			yCameraPos += anInt1101 + ((j - yCameraPos) * angle) / 1000;
			if (yCameraPos > j)
				yCameraPos = j;
		}
		if (yCameraPos > j) {
			yCameraPos -= anInt1101 + ((yCameraPos - j) * angle) / 1000;
			if (yCameraPos < j)
				yCameraPos = j;
		}
		i = cinematicCamXViewpointLoc * 128 + 64;
		j = cinematicCamYViewpointLoc * 128 + 64;
		k = getCenterHeight(plane, j, i) - cinematicCamZViewpointLoc;
		int l = i - xCameraPos;
		int i1 = k - zCameraPos;
		int j1 = j - yCameraPos;
		int k1 = (int) Math.sqrt(l * l + j1 * j1);
		int l1 = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
		int i2 = (int) (Math.atan2(l, j1) * -325.94900000000001D) & 0x7ff;
		if (l1 < 128)
			l1 = 128;
		if (l1 > 383)
			l1 = 383;
		if (yCameraCurve < l1) {
			yCameraCurve += anInt998 + ((l1 - yCameraCurve) * anInt999) / 1000;
			if (yCameraCurve > l1)
				yCameraCurve = l1;
		}
		if (yCameraCurve > l1) {
			yCameraCurve -= anInt998 + ((yCameraCurve - l1) * anInt999) / 1000;
			if (yCameraCurve < l1)
				yCameraCurve = l1;
		}
		int j2 = i2 - xCameraCurve;
		if (j2 > 1024)
			j2 -= 2048;
		if (j2 < -1024)
			j2 += 2048;
		if (j2 > 0) {
			xCameraCurve += anInt998 + (j2 * anInt999) / 1000;
			xCameraCurve &= 0x7ff;
		}
		if (j2 < 0) {
			xCameraCurve -= anInt998 + (-j2 * anInt999) / 1000;
			xCameraCurve &= 0x7ff;
		}
		int k2 = i2 - xCameraCurve;
		if (k2 > 1024)
			k2 -= 2048;
		if (k2 < -1024)
			k2 += 2048;
		if (k2 < 0 && j2 > 0 || k2 > 0 && j2 < 0)
			xCameraCurve = i2;
	}
	public void shiftMenuEntriesLeft() {
		MenuEntry temp = getMenuManager().getMenuEntries()[menuActionRow];

		for (int i = (menuActionRow - 1); i >= 0; i--) {
			getMenuManager().getMenuEntries()[i+1] = getMenuManager().getMenuEntries()[i];
		}

		getMenuManager().getMenuEntries()[0] = temp;
	}
	private void drawMenu(int xOffSet, int yOffSet) {
		BeforeMenuRender event = new BeforeMenuRender();
		instance.getCallbacks().post(event);
		if (!event.isConsumed())
		{
			instance.drawOriginalMenu(255);
		}
	}

	private void addFriend(long l) {
		try {
			if (l == 0L)
				return;

			String username = StringUtils.fixName(StringUtils.nameForLong(l));

			if (username.equalsIgnoreCase(localPlayer.displayName)) {
				System.err.println("blocked from adding self as friend..");
				return;
			}
			if (friendsCount >= 100 && anInt1046 != 1) {
				pushMessage("Your friendlist is full.", 0, "");
				return;
			}
			if (friendsCount >= 200) {
				pushMessage("Your friendlist is full.", 0, "");
				return;
			}

			for (int i = 0; i < friendsCount; i++)
				if (friendsListAsLongs[i] == l) {

					return;
				}
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					pushMessage("Please remove " + username + " from your ignore list first", 0, "");
					return;
				}

			if (username.equals(localPlayer.displayName)) {
				return;
			} else {
				//friendsList[friendsCount] = s;
				//friendsListAsLongs[friendsCount] = l;
				//friendsNodeIDs[friendsCount] = 0;
				//friendsCount++;
				//needDrawTabArea = true;
				stream.createFrame(188);
				stream.writeQWord(l);
				return;
			}
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("15283, " + (byte) 68 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	private int getCenterHeight(int i, int j, int k) {
		int l = k >> 7;
		int i1 = j >> 7;
		if (l < 0 || i1 < 0 || l > 103 || i1 > 103)
			return 0;
		int j1 = i;
		if (j1 < 3 && (tileFlags[1][l][i1] & 2) == 2)
			j1++;
		int k1 = k & 0x7f;
		int l1 = j & 0x7f;
		int i2 = tileHeights[j1][l][i1] * (128 - k1) + tileHeights[j1][l + 1][i1] * k1 >> 7;
		int j2 = tileHeights[j1][l][i1 + 1] * (128 - k1) + tileHeights[j1][l + 1][i1 + 1] * k1 >> 7;
		return i2 * (128 - l1) + j2 * l1 >> 7;
	}

	public void resetLogout() {
		setGameState(GameState.LOGIN_SCREEN);
		logger.debug("being logged out.. from: "+new Throwable().getStackTrace()[1].toString());
		logger.debug("Logging out..");
		loggedIn = false;
		prayClicked = false;
		loginScreenState = LoginScreenState.LOGIN;

		captcha = null;
		captchaInput = "";
		firstLoginMessage = "";
		try {
			if (socketStream != null)
				socketStream.close();
		} catch (Exception _ex) {
		}
		socketStream = null;
		PacketLog.clear();
		if (entityTarget != null) {
			entityTarget.stop();
		}
		// myUsername = "";
		// myPassword = "";
		unlinkMRUNodes();
		scene.initToNull();
		for (int i = 0; i < 4; i++)
			collisionMaps[i].setDefault();
		System.gc();
		BroadcastManager.broadcasts = new Broadcast[1000];
		currentSong = -1;
		nextSong = -1;
		prevSong = 0;
		experienceCounter = 0;
		GameTimerHandler.getSingleton().stopAll();
		Preferences.save();
		frameMode(false);
		requestMusic(SoundConstants.SCAPE_RUNE);
		if (informationFile.isUsernameRemembered()) {
			if (localPlayer.getDisplayedRights().size() > 0) {
				AccountManager.add(myUsername, myPassword, localPlayer.getDisplayedRights().get(0).crownId());
			} else {
				AccountManager.add(myUsername, myPassword, 0);
			}
		}
		if (Configuration.SAVE_ACCOUNTS) {
			AccountManager.saveAccount();
		}
	}

	public void method45() {
		aBoolean1031 = true;
		for (int j = 0; j < 7; j++) {
			myAppearance[j] = -1;
			for (int k = 0; k < IDK.length; k++) {
				if (IDK.cache[k].nonSelectable || IDK.cache[k].bodyPartId != j + (aBoolean1047 ? 0 : 7))
					continue;
				myAppearance[j] = k;
				break;
			}
		}
	}

	private void method46(int i, Buffer stream) {
		//if (var1.bitsRemaining(Client.packetWriter.serverPacketLength) >= var2 + 12) {
		while (stream.bitsRemaining(i) >= 28) {
			int k = stream.readBits(16);
			if (k == 65535) {
				break;
			}
			if (npcs[k] == null) {
				npcs[k] = new Npc(); // Teleports works fine if we create a new npc?
			}
			Npc npc = npcs[k];
			npcIndices[npcCount++] = k;
			npc.anInt1537 = loopCycle;
			int l = stream.readBits(5);
			if (l > 15)
				l -= 32;
			int i1 = stream.readBits(5);
			if (i1 > 15)
				i1 -= 32;
			int j1 = stream.readBits(1);
			int npcId = stream.readBits(14);
			npc.npcPetType = stream.readBits(2);
			npc.desc = NpcDefinition.lookup(npcId);
			int k1 = stream.readBits(1);
			if (k1 == 1)
				anIntArray894[anInt893++] = k;
			npc.anInt1540 = npc.desc.size;
			npc.anInt1504 = npc.desc.rotationSpeed;
			npc.walkAnimIndex = npc.desc.walkingAnimation;
			npc.turn180AnimIndex = npc.desc.rotate180AnimIndex;
			npc.turn90CWAnimIndex = npc.desc.rotate90CWAnimIndex;
			npc.turn90CCWAnimIndex = npc.desc.rotate90CCWAnimIndex;
			npc.seqStandID = npc.desc.standingAnimation;
			npc.anInt1538 = 0;
			npc.anInt1539 = 0;
			npc.setPos(localPlayer.pathX[0] + i1, localPlayer.pathY[0] + l, j1 == 1);
			npc.setIndex(k);
			callbacks.post(new NpcSpawned(npc));
		}
		stream.finishBitAccess();
	}

	@Override
	public void processGameLoop() {
		if(loggedIn) {
			getCallbacks().tick();
			getCallbacks().post(ClientTick.INSTANCE);
		}


		if (rsAlreadyLoaded || loadingError || genericLoadingError)
			return;
		loopCycle++;
		if (!loggedIn)
			processLoginScreenInput();
		else
			mainGameProcessor();

		processOnDemandQueue();
		processMusic();
		// method49();
		// handleSounds();
	}

	public void method47(boolean flag) {
		if (localPlayer.x >> 7 == destX && localPlayer.y >> 7 == destY)
			destX = 0;
		if (hideothers && !flag) {
			return;
		}
		int j = playerCount;
		if (flag)
			j = 1;
		for (int l = 0; l < j; l++) {
			Player player;
			long i1;
			if (flag) {
				player = localPlayer;
				i1 = (long) maxPlayerCount << 32;
			} else {
				player = players[playerIndices[l]];
				i1 = (long) playerIndices[l] << 32;
			}
			if (player == null || !player.isVisible() || (hidelocalplayers && player == localPlayer))
				continue;
			player.aBoolean1699 = (lowMem && playerCount > 50 || playerCount > 200) && !flag
					&& player.secondarySeqID == player.seqStandID;
			int j1 = player.x >> 7;
			int k1 = player.y >> 7;
			if (j1 < 0 || j1 >= 104 || k1 < 0 || k1 >= 104)
				continue;
			if (player.aModel_1714 != null && loopCycle >= player.anInt1707 && loopCycle < player.anInt1708) {
				player.aBoolean1699 = false;
				player.anInt1709 = getCenterHeight(plane, player.y, player.x);
				scene.addToScenePlayerAsObject(plane, player.y, player, player.orientation, player.anInt1722, player.x,
						player.anInt1709, player.anInt1719, player.anInt1721, i1, player.anInt1720);
				continue;
			}
			if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
				if (anIntArrayArray929[j1][k1] == anInt1265)
					continue;
				anIntArrayArray929[j1][k1] = anInt1265;
			}
			player.anInt1709 = getCenterHeight(plane, player.y, player.x);
			scene.addAnimableA(plane, player.orientation, player.anInt1709, i1, player.y, 60, player.x, player,
					player.dynamic);
		}
	}

	private boolean promptUserForInput(RSInterface class9) {
		int j = class9.contentType;
		if (anInt900 == 2) {
			if (j == 201) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 1;
				aString1121 = "Enter name of friend to add to list";
			}
			if (j == 202) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 2;
				aString1121 = "Enter name of friend to delete from list";
			}
		}
		if (j == 205) {
			anInt1011 = 250;
			return true;
		}
		if (j == 501) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 4;
			aString1121 = "Enter name of player to add to list";
		}
		if (j == 502) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 5;
			aString1121 = "Enter name of player to delete from list";
		}
		if (j == 550) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 6;
			aString1121 = "Enter the name of the chat you wish to join";
		}
		if (j >= 300 && j <= 313) {
			int k = (j - 300) / 2;
			int j1 = j & 1;
			int i2 = myAppearance[k];
			if (i2 != -1) {
				do {
					if (j1 == 0 && --i2 < 0)
						i2 = IDK.length - 1;
					if (j1 == 1 && ++i2 >= IDK.length)
						i2 = 0;
				} while (IDK.cache[i2].nonSelectable || IDK.cache[i2].bodyPartId != k + (aBoolean1047 ? 0 : 7));
				myAppearance[k] = i2;
				aBoolean1031 = true;
			}
		}
		if (j >= 314 && j <= 323) {
			int l = (j - 314) / 2;
			int k1 = j & 1;
			int j2 = anIntArray990[l];
			if (k1 == 0 && --j2 < 0)
				j2 = anIntArrayArray1003[l].length - 1;
			if (k1 == 1 && ++j2 >= anIntArrayArray1003[l].length)
				j2 = 0;
			anIntArray990[l] = j2;
			aBoolean1031 = true;
		}
		if (j == 324 && !aBoolean1047) {
			aBoolean1047 = true;
			method45();
		}
		if (j == 325 && aBoolean1047) {
			aBoolean1047 = false;
			method45();
		}
		if (j == 326) {
			stream.createFrame(101);
			stream.writeUnsignedByte(aBoolean1047 ? 0 : 1);
			for (int i1 = 0; i1 < 7; i1++)
				stream.writeUnsignedByte(myAppearance[i1]);

			for (int l1 = 0; l1 < 5; l1++)
				stream.writeUnsignedByte(anIntArray990[l1]);

			return true;
		}
		if (j == 613)
			canMute = !canMute;
		if (j >= 601 && j <= 612) {
			clearTopInterfaces();
			if (reportAbuseInput.length() > 0) {
				stream.createFrame(218);
				stream.writeQWord(longForName(reportAbuseInput));
				stream.writeUnsignedByte(j - 601);
				stream.writeUnsignedByte(canMute ? 1 : 0);
			}
		}
		return false;
	}

	public void method49(Buffer stream) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			Player player = players[k];
			int l = stream.readUnsignedByte();
			if ((l & 0x40) != 0)
				l += stream.readUnsignedByte() << 8;
			method107(l, k, stream, player);
		}
	}

	public void drawMapScenes(int i, int k, int l, int i1, int j1) {
		long id = scene.getWallObjectUid(j1, l, i);
		if (id != 0) {
			int k2 = ObjectKeyUtil.getObjectOrientation(id);
			int i3 = ObjectKeyUtil.getObjectType(id);
			int k3 = k;
			if (id > 0) {
				k3 = i1;
			}
			int ai[] = minimapImage.myPixels;
			int k4 = 24624 + l * 4 + (103 - i) * 512 * 4;
			int i5 = ObjectKeyUtil.getObjectId(id);
			ObjectDefinition class46_2 = ObjectDefinition.lookup(i5);
			if (class46_2.mapscene != -1) {
				IndexedImage background_2 = mapScenes[class46_2.mapscene];
				if (background_2 != null) {
					int i6 = (class46_2.sizeX * 4 - background_2.subWidth) / 2;
					int j6 = (class46_2.sizeY * 4 - background_2.subHeight) / 2;
					background_2.draw(48 + l * 4 + i6, 48 + (104 - i - class46_2.sizeY) * 4 + j6);
				}
			} else {
				if (i3 == 0 || i3 == 2)
					if (k2 == 0) {
						ai[k4] = k3;
						ai[k4 + 512] = k3;
						ai[k4 + 1024] = k3;
						ai[k4 + 1536] = k3;
					} else if (k2 == 1) {
						ai[k4] = k3;
						ai[k4 + 1] = k3;
						ai[k4 + 2] = k3;
						ai[k4 + 3] = k3;
					} else if (k2 == 2) {
						ai[k4 + 3] = k3;
						ai[k4 + 3 + 512] = k3;
						ai[k4 + 3 + 1024] = k3;
						ai[k4 + 3 + 1536] = k3;
					} else if (k2 == 3) {
						ai[k4 + 1536] = k3;
						ai[k4 + 1536 + 1] = k3;
						ai[k4 + 1536 + 2] = k3;
						ai[k4 + 1536 + 3] = k3;
					}
				if (i3 == 3)
					if (k2 == 0)
						ai[k4] = k3;
					else if (k2 == 1)
						ai[k4 + 3] = k3;
					else if (k2 == 2)
						ai[k4 + 3 + 1536] = k3;
					else if (k2 == 3)
						ai[k4 + 1536] = k3;
				if (i3 == 2)
					if (k2 == 3) {
						ai[k4] = k3;
						ai[k4 + 512] = k3;
						ai[k4 + 1024] = k3;
						ai[k4 + 1536] = k3;
					} else if (k2 == 0) {
						ai[k4] = k3;
						ai[k4 + 1] = k3;
						ai[k4 + 2] = k3;
						ai[k4 + 3] = k3;
					} else if (k2 == 1) {
						ai[k4 + 3] = k3;
						ai[k4 + 3 + 512] = k3;
						ai[k4 + 3 + 1024] = k3;
						ai[k4 + 3 + 1536] = k3;
					} else if (k2 == 2) {
						ai[k4 + 1536] = k3;
						ai[k4 + 1536 + 1] = k3;
						ai[k4 + 1536 + 2] = k3;
						ai[k4 + 1536 + 3] = k3;
					}
			}
		}
		id = scene.getGameObjectUid(j1, l, i);
		if (id != 0) {
			int l2 = ObjectKeyUtil.getObjectOrientation(id);
			int j3 = ObjectKeyUtil.getObjectType(id);

			int l3 = ObjectKeyUtil.getObjectId(id);
			ObjectDefinition class46_1 = ObjectDefinition.lookup(l3);
			if (class46_1.mapscene != -1) {
				if (class46_1.mapscene < mapScenes.length) {
					IndexedImage background_1 = mapScenes[class46_1.mapscene];
					if (background_1 != null) {
						int j5 = (class46_1.sizeX * 4 - background_1.subWidth) / 2;
						int k5 = (class46_1.sizeY * 4 - background_1.subHeight) / 2;
						background_1.draw(48 + l * 4 + j5, 48 + (104 - i - class46_1.sizeY) * 4 + k5);
					}
				} else if (Configuration.developerMode) {
					System.err.println("Missing map scene: " + class46_1.mapscene);
				}
			} else if (j3 == 9) {
				int l4 = 0xeeeeee;
				if (id > 0)
					l4 = 0xee0000;
				int ai1[] = minimapImage.myPixels;
				int l5 = 24624 + l * 4 + (103 - i) * 512 * 4;
				if (l2 == 0 || l2 == 2) {
					ai1[l5 + 1536] = l4;
					ai1[l5 + 1024 + 1] = l4;
					ai1[l5 + 512 + 2] = l4;
					ai1[l5 + 3] = l4;
				} else {
					ai1[l5] = l4;
					ai1[l5 + 512 + 1] = l4;
					ai1[l5 + 1024 + 2] = l4;
					ai1[l5 + 1536 + 3] = l4;
				}
			}
		}
		id = scene.getGroundDecorationUid(j1, l, i);
		if (id != 0) {
			int j2 = ObjectKeyUtil.getObjectId(id);
			ObjectDefinition class46 = ObjectDefinition.lookup(j2);
			if (class46.mapscene > 100) {
				class46.mapscene = 1;
			}
			if (class46.mapscene != -1) {
				IndexedImage background = mapScenes[class46.mapscene];
				if (background != null) {
					int i4 = (class46.sizeX * 4 - background.subWidth) / 2;
					int j4 = (class46.sizeY * 4 - background.subHeight) / 2;
					background.draw(48 + l * 4 + i4, 48 + (104 - i - class46.sizeY) * 4 + j4);
				}
			}
		}
	}

	private Sprite aSprite_1201;
	private Sprite aSprite_1202;

	private void loadTitleScreen() {
		if (titleButton == null) {
			titleButton = new IndexedImage(titleStreamLoader, "titlebutton", 0);
		}
	}

	private static void setHighMem() {
		SceneGraph.lowMem = false;
		// Rasterizer.lowMem = false;
		lowMem = false;
		MapRegion.lowMem = false;
		ObjectDefinition.lowMem = false;
	}

	public static final String[] SKILL_NAME = { "Attack", "Defence", "Strength", "Hitpoints", "Ranged", "Prayer",
			"Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining",
			"Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting", "Hunter", "Construction",
			"Summoning" };

	public static int getXPForLevel(int level) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level) {
				return output;
			}
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	/**
	 * An array containing all the player's experience.
	 */
	public static double[] experience;
	public static int totalExperience;

	public static int getStaticLevelByExperience(int slot) {
		double exp = experience[slot];

		int points = 0;
		int output = 0;
		for (byte lvl = 1; lvl < 100; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if ((output - 1) >= exp) {
				return lvl;
			}
		}
		return 99;
	}

	private static String[] args = null;
	public static Client instance;
	public static boolean runelite;

	private static int getVersion() {
		String version = System.getProperty("java.version");
		if(version.startsWith("1.")) {
			version = version.substring(2, 3);
		} else {
			int dot = version.indexOf(".");
			if(dot != -1) { version = version.substring(0, dot); }
		} return Integer.parseInt(version);
	}


// bro its still buildding
	public static JFrame appFrame = null;
	public static Container gameContainer = null;

	private static String getErrorLogDirectory() {
		return Signlink.getCacheDirectory() + Configuration.ERROR_LOG_DIRECTORY;
	}

	private static void enableExceptionLogging() throws IOException {
		if (!new File(getErrorLogDirectory()).exists()) {
			Preconditions.checkState(new File(getErrorLogDirectory()).mkdirs());
		}

		TeeOutputStream outputStream = new TeeOutputStream(System.err, new FileOutputStream(getErrorLogDirectory() + Configuration.ERROR_LOG_FILE, true));
		System.setErr(new PrintStream(outputStream));
	}

	private static void setLoggingLevel(Level level) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(level);
	}

	public static Client getClient(boolean runelite, String...args) {
		try {
//			RPC.init();

			System.out.println("Running Java version " + getVersion());
			Client.runelite = runelite;
			Client.args = args;
			StringBuilder windowTitleBuilder = new StringBuilder();
			windowTitleBuilder.append(Configuration.CLIENT_TITLE);
			server = Configuration.DEDICATED_SERVER_ADDRESS;
			setLoggingLevel(Level.INFO);

			if (args.length > 0) {
				StringBuilder configurationBuilder = new StringBuilder();
				configurationBuilder.append("[");

				for (int index = 0; index < args.length; index++) {
					configurationBuilder.append(args[index].replace("--", ""));
					if (index != args.length - 1)
						configurationBuilder.append(" ");

					if (args[index].startsWith("server=")) {
						server = args[index].replace("server=", "");
						System.out.println("Custom connection address set through run arguments: " + server);
					} else {
						switch (args[index]) {
							case "--debug":
							case "-db":
								setLoggingLevel(Level.DEBUG);
								System.out.println("Setting log level to debug.");
								break;

							case "--developer":
							case "-d":
								Configuration.developerMode = true;
								Configuration.cacheName = Configuration.CACHE_NAME_DEV;
								System.out.println("Developer mode enabled.");
								break;
							case "--local":
							case "-l":
							case "localhost":
							case "local":
								if (server.equals(Configuration.DEDICATED_SERVER_ADDRESS)) {
									server = "127.0.0.1";
									System.out.println("Localhost client enabled.");
								} else {
									throw new IllegalArgumentException("Cannot have custom ip and local enabled.");
								}
								break;
							case "test_server":
								port = Configuration.TEST_PORT;
								System.out.println("Test server enabled.");
								break;

							case "pack_data":
								Configuration.packIndexData = true;
								break;
							case "dump_maps":
								Configuration.dumpMaps = true;
								break;
							case "dump_animation_data":
								Configuration.dumpAnimationData = true;
								break;
							case "dump_data_lists":
								Configuration.dumpDataLists = true;
								break;
							default:
								throw new IllegalArgumentException("Run argument not recognized: " + args[index]);
						}
					}
				}

				// Build the client title with configuration arguments
				configurationBuilder.append("]");
				windowTitleBuilder.append(" ");
				windowTitleBuilder.append(configurationBuilder.toString().trim());
			}

			Signlink.createCacheDirectory();
			enableExceptionLogging(); // Don't remove this!
			Configuration.clientTitle = windowTitleBuilder.toString();
			nodeID = 1;
			portOff = 0;
			setHighMem();
			isMembers = true;
			Signlink.storeid = 32;
			Signlink.startpriv(InetAddress.getLocalHost());

			instance = new Client();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return Client.instance;
	}

	public static Client getInstance() {
		return instance;
	}

	public void loadingStages() {
		if (lowMem && loadingStage == 2 && MapRegion.anInt131 != plane) {

			//setGameState(GameState.LOADING);

			loadingStage = 1;
			longStartTime = System.currentTimeMillis();
		}
		if (loadingStage == 1) {
			int j = getMapLoadingState();
			if (j != 0 && System.currentTimeMillis() - longStartTime > 0x57e40L) {
				Signlink.reporterror(
						myUsername + " glcfb " + aLong1215 + "," + j + "," + lowMem + "," + decompressors[0] + ","
								+ resourceProvider.getNodeCount() + "," + plane + "," + currentRegionX + "," + currentRegionY);
				longStartTime = System.currentTimeMillis();
			}
		}
		if (loadingStage == 2 && plane != anInt985) {
			anInt985 = plane;
			renderMapScene(plane);
			stream.createFrame(121);
		}
	}

	private int getMapLoadingState() {
		for (int i = 0; i < terrainData.length; i++) {
			if (terrainData[i] == null && terrainIndices[i] != -1)
				return -1;
			if (objectData[i] == null && objectIndices[i] != -1)
				return -2;
		}
		boolean flag = true;
		for (int j = 0; j < terrainData.length; j++) {
			byte abyte0[] = objectData[j];
			if (abyte0 != null) {
				try {
					int k = (mapCoordinates[j] >> 8) * 64 - baseX;
					int l = (mapCoordinates[j] & 0xff) * 64 - baseY;
					if (isDynamicRegion) {
						k = 10;
						l = 10;
					}
					flag &= MapRegion.method189(k, abyte0, l);
				} catch (Exception e) {
					if (objectIndices[j] != -1)
						System.err.println("Error on map file: " + objectIndices[j]);
					e.printStackTrace();
				}
			}
		}
		if (!flag)
			return -3;// couldn't parse all landscapes
		if (loadingMap) {
			return -4;
		} else {
			loadingStage = 2;
			MapRegion.anInt131 = plane;
			loadRegion();
			stream.createFrame(121);
//			logger.debug("Map region loaded.");
			return 0;
		}
	}

	public void method55() {
		for (Projectile projectile = (Projectile) projectiles
				.reverseGetFirst(); projectile != null; projectile = (Projectile) projectiles
				.reverseGetNext())
			if (projectile.projectileZ != plane || loopCycle > projectile.stopCycle)
				projectile.remove();
			else if (loopCycle >= projectile.startCycle) {
				if (projectile.target > 0) {
					Npc npc = npcs[projectile.target - 1];
					if (npc != null && npc.x >= 0 && npc.x < 13312 && npc.y >= 0 && npc.y < 13312)
						projectile.calculateIncrements(loopCycle, npc.y,
								getCenterHeight(projectile.projectileZ, npc.y, npc.x)
										- projectile.endHeight,
								npc.x);
				}
				if (projectile.target < 0) {
					int j = -projectile.target - 1;
					Player player;
					if (j == localPlayerIndex)
						player = localPlayer;
					else
						player = players[j];
					if (player != null && player.x >= 0 && player.x < 13312 && player.y >= 0 && player.y < 13312)
						projectile.calculateIncrements(loopCycle, player.y,
								getCenterHeight(projectile.projectileZ, player.y, player.x)
										- projectile.endHeight,
								player.x);
				}
				projectile.progressCycles(tickDelta);
				scene.addAnimableA(plane, projectile.turnValue,
						(int) projectile.cnterHeight, -1, (int) projectile.yPos, 60,
						(int) projectile.xPos, projectile, false);
			}

	}

	public static String capitalize(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
			}
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				if (i + 1 < s.length()) {
					s = String.format("%s%s%s", s.subSequence(0, i + 1), Character.toUpperCase(s.charAt(i + 1)),
							s.substring(i + 2));
				}
			}
		}
		return s;
	}

	private void processOnDemandQueue() {
		do {
			OnDemandData onDemandData;
			do {
				onDemandData = resourceProvider.getNextNode();
				if (onDemandData == null)
					return;
				if (onDemandData.dataType == 0) {
					try {
						Model.loadModel(onDemandData.buffer, onDemandData.ID);
						needDrawTabArea = true;
						if (backDialogID != -1)
							inputTaken = true;
					} catch (Exception e) {
						System.out.println("Error loading model " + onDemandData.ID);
					}
				}
				if (onDemandData.dataType == 1 && onDemandData.buffer != null)
					Frame.load(onDemandData.ID, onDemandData.buffer);
				if (onDemandData.dataType == 2 && onDemandData.ID == nextSong && onDemandData.buffer != null) {
//					System.out.println("got music to play.");
					music_payload = new byte[onDemandData.buffer.length];
					System.arraycopy(onDemandData.buffer, 0, music_payload, 0, music_payload.length);
					fetchMusic = true;
				}
				if (onDemandData.dataType == 3 && loadingStage == 1) {
					for (int i = 0; i < terrainData.length; i++) {
						if (terrainIndices[i] == onDemandData.ID) {
							if (terrainData[i] == null)
								terrainData[i] = onDemandData.buffer;
							if (onDemandData.buffer == null)
								terrainIndices[i] = -1;
							break;
						}
						if (objectIndices[i] != onDemandData.ID)
							continue;
						if (objectData[i] == null)
							objectData[i] = onDemandData.buffer;
						if (onDemandData.buffer == null)
							objectIndices[i] = -1;
						break;
					}

				}
			} while (onDemandData.dataType != 93 || !resourceProvider.method564(onDemandData.ID));
			//MapRegion.passiveRequestGameObjectModels(new Buffer(onDemandData.buffer), resourceProvider);
		} while (true);
	}

	public void calcFlamesPosition() {
		char c = '\u0100';
		for (int j = 10; j < 117; j++) {
			int k = (int) (Math.random() * 100D);
			if (k < 50)
				anIntArray828[j + (c - 2 << 7)] = 255;
		}
		for (int l = 0; l < 100; l++) {
			int i1 = (int) (Math.random() * 124D) + 2;
			int k1 = (int) (Math.random() * 128D) + 128;
			int k2 = i1 + (k1 << 7);
			anIntArray828[k2] = 192;
		}

		for (int j1 = 1; j1 < c - 1; j1++) {
			for (int l1 = 1; l1 < 127; l1++) {
				int l2 = l1 + (j1 << 7);
				anIntArray829[l2] = (anIntArray828[l2 - 1] + anIntArray828[l2 + 1] + anIntArray828[l2 - 128]
						+ anIntArray828[l2 + 128]) / 4;
			}

		}

		anInt1275 += 128;
		if (anInt1275 > anIntArray1190.length) {
			anInt1275 -= anIntArray1190.length;
			int i2 = (int) (Math.random() * 12D);
			randomizeBackground(aBackgroundArray1152s[i2]);
		}
		for (int j2 = 1; j2 < c - 1; j2++) {
			for (int i3 = 1; i3 < 127; i3++) {
				int k3 = i3 + (j2 << 7);
				int i4 = anIntArray829[k3 + 128] - anIntArray1190[k3 + anInt1275 & anIntArray1190.length - 1] / 5;
				if (i4 < 0)
					i4 = 0;
				anIntArray828[k3] = i4;
			}

		}

		System.arraycopy(anIntArray969, 1, anIntArray969, 0, c - 1);

		anIntArray969[c - 1] = (int) (Math.sin(loopCycle / 14D) * 16D + Math.sin(loopCycle / 15D) * 14D
				+ Math.sin(loopCycle / 16D) * 12D);
		if (anInt1040 > 0)
			anInt1040 -= 4;
		if (anInt1041 > 0)
			anInt1041 -= 4;
		if (anInt1040 == 0 && anInt1041 == 0) {
			int l3 = (int) (Math.random() * 2000D);
			if (l3 == 0)
				anInt1040 = 1024;
			if (l3 == 1)
				anInt1041 = 1024;
		}
	}

	public void resetAnimation(int i) {
		RSInterface class9 = RSInterface.interfaceCache[i];
		for (int j = 0; j < class9.children.length; j++) {
			if (class9.children[j] == -1)
				break;
			RSInterface class9_1 = RSInterface.interfaceCache[class9.children[j]];
			if (class9_1 == null)
				System.err.println("Null child of index " + j + " inside interface " + i);
			if (class9_1.type == 1)
				resetAnimation(class9_1.id);
			class9_1.anInt246 = 0;
			class9_1.anInt208 = 0;
		}
	}

	public void drawHeadIcon() {
		if (hintType != 2)
			return;
		calcEntityScreenPos((hintIconXpos - baseX << 7) + anInt937, hintIconFloorPos * 2, (hintIconYpos - baseY << 7) + anInt938);
		if (spriteDrawX > -1 && loopCycle % 20 < 10)
			headIconsHint[0].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
	}

	private GameTimer gameTimer;

	private void mainGameProcessor() {
		callbacks.tick();
		callbacks.post(ClientTick.INSTANCE);
		callbacks.post(new GameTick());

		timeOutHasLoggedMessages();

		if (gameTimer != null) {
			if (gameTimer.isCompleted()) {
				gameTimer.stop();
			}
		}
		if (tabID >= 0 && tabInterfaceIDs[tabID] == 60000) {
			if (!PetSystem.isPetAnimationRunning) {
				PetSystem.updateAnimations();
			}
			needDrawTabArea = true;
		}

		spin();

		if (anInt1104 > 1)
			anInt1104--;
		if (anInt1011 > 0)
			anInt1011--;

		int packetsHandled = 0;
		while (parsePacket()) {
			if (packetsHandled++ >= 100) {
				break;
			}
		}

		if (!loggedIn)
			return;

		if (anInt1016 > 0)
			anInt1016--;
		if (KeyHandler.keyArray[1] == 1 || KeyHandler.keyArray[2] == 1 || KeyHandler.keyArray[3] == 1 || KeyHandler.keyArray[4] == 1)
			aBoolean1017 = true;
		if (aBoolean1017 && anInt1016 <= 0) {
			anInt1016 = 20;
			aBoolean1017 = false;
			stream.createFrame(86);
			stream.writeWord(camAngleYY);
			stream.method432(viewRotation);
		}
		if (super.canvas.hasFocus() && !aBoolean954) {
			aBoolean954 = true;
			stream.createFrame(3);
			stream.writeUnsignedByte(1);
		}
		if (!super.canvas.hasFocus() && aBoolean954) {
			aBoolean954 = false;
			stream.createFrame(3);
			stream.writeUnsignedByte(0);
		}
		loadingStages();
		method115();
		// method90();
		anInt1009++;
		if (anInt1009 > 750) {
			logger.debug("Dropping client due to packets not being received.");
			dropClient();
		}
		processPlayerMovement();
		processNpcMovement();
		processSoundEffects();
		processMobChatText();


			ScriptEvent var45;
			while((var45 = (ScriptEvent)scriptEvents2.removeLast()) != null) {
				InstructionProcessor.runScriptEvent(var45);
			}
		while((var45 = (ScriptEvent)scriptEvents3.removeLast()) != null) {
			InstructionProcessor.runScriptEvent(var45);
		}
		while((var45 = (ScriptEvent)scriptEvents.removeLast()) != null) {
			InstructionProcessor.runScriptEvent(var45);
		}

		NotificationInterface.processNotifications();
		InterfaceAnimations.processAnimations();
		tickDelta++;
		if (crossType != 0) {
			crossIndex += 20;
			if (crossIndex >= 400)
				crossType = 0;
		}
		if (atInventoryInterfaceType != 0) {
			atInventoryLoopCycle++;
			if (atInventoryLoopCycle >= 15) {
				if (atInventoryInterfaceType == 2)
					needDrawTabArea = true;
				if (atInventoryInterfaceType == 3)
					inputTaken = true;
				atInventoryInterfaceType = 0;
			}
		}
		if (activeInterfaceType != 0) {
			anInt989++;
			if (MouseHandler.mouseX > anInt1087 + 5 || MouseHandler.mouseX < anInt1087 - 5 || MouseHandler.mouseY > anInt1088 + 5
					|| MouseHandler.mouseY < anInt1088 - 5)
				aBoolean1242 = true;
			if (MouseHandler.instance.clickMode2 == 0) {
				if (activeInterfaceType == 2)
					needDrawTabArea = true;
				if (activeInterfaceType == 3)
					inputTaken = true;
				activeInterfaceType = 0;
				if (aBoolean1242 && anInt989 >= getDragSetting(draggingItemInterfaceId)) {
					lastActiveInvInterface = -1;
					processRightClick();

					// Bank interface adding items to another tab via dragging on the tab icons at the top
					if (Bank.isBankContainer(RSInterface.interfaceCache[draggingItemInterfaceId]) || draggingItemInterfaceId == Bank.SEARCH_CONTAINER) {
						//System.out.println("BANK TAB SLOT " + mouseX + ", " + mouseY);
						Point southWest, northEast;



						int xOffset = !isResized() ? 0 : (canvasWidth / 2) - 356;
						int yOffset = !isResized() ? 0: (canvasHeight / 2) - 230;

						if (MouseHandler.mouseX >= 25+xOffset && MouseHandler.mouseX <= 48+xOffset && MouseHandler.mouseY >= 262+yOffset && MouseHandler.mouseY <= 282+yOffset) {
							RSInterface class9 = RSInterface.interfaceCache[draggingItemInterfaceId];
							int l2 = itemDraggingSlot;
							int itemID = class9.inventoryItemId[l2];
							int itemAmount = class9.inventoryAmounts[l2];
//							System.out.println("Sending item to incinerator! " + 73311337 + " / " + itemID + " / " + itemAmount);

							stream.createFrame(145);
							stream.method432(7331);
							stream.method432(itemID);
							stream.method432(itemAmount);
							return;
						}

						southWest = new Point(68 + xOffset, 75 + yOffset);
						northEast = new Point(457 + xOffset, 41 + yOffset);
						int[] slots = new int[9];
						for (int i = 0; i < slots.length; i++)
							slots[i] = (41 * i) + (int) southWest.getX();
						for (int i = 0; i < slots.length; i++) {
							if (MouseHandler.mouseX >= slots[i] && MouseHandler.mouseX <= slots[i] + 42
									&& MouseHandler.mouseY >= northEast.getY() && MouseHandler.mouseY <= southWest.getY()) {

								if (draggingItemInterfaceId != Bank.SEARCH_CONTAINER) {
									RSInterface rsi = RSInterface.interfaceCache[58050 + i];
									if (rsi.isMouseoverTriggered) {
										continue;
									}
								}

								// Update client side to hide latency
								if (Bank.getCurrentBankTab() == 0) {
									OptionalInt fromTabOptional = Arrays.stream(Bank.ITEM_CONTAINERS).filter(id -> draggingItemInterfaceId == id).findFirst();
									if (fromTabOptional.isPresent()) {
										RSInterface fromTab = RSInterface.interfaceCache[fromTabOptional.getAsInt()];
										RSInterface toTab = RSInterface.interfaceCache[Bank.ITEM_CONTAINERS[i]];
										if (toTab.getInventoryContainerFreeSlots() > 0 && fromTab.id != toTab.id) {
											RSInterface.insertInventoryItem(fromTab, itemDraggingSlot, toTab);
										}
									}
								}

								if (draggingItemInterfaceId != Bank.SEARCH_CONTAINER) {
									stream.createFrame(214);
									stream.method433(draggingItemInterfaceId);
									stream.method424(0);
									stream.method433(itemDraggingSlot);
									stream.method431(1000 + i);
								} else {
									stream.createFrame(243);
									stream.writeWord(i);
									stream.writeWord(RSInterface.get(draggingItemInterfaceId).inventoryItemId[itemDraggingSlot]);
								}
								return;
							}
						}
					}

					try {
						RSInterface class9 = RSInterface.interfaceCache[draggingItemInterfaceId];
						if (class9 != null) {
							if (mouseInvInterfaceIndex != itemDraggingSlot && lastActiveInvInterface == draggingItemInterfaceId) {
								// Dragging item inside the same container

								int insertMode = 0;
								if (anInt913 == 1 && class9.contentType == 206)
									insertMode = 1;
								if (class9.inventoryItemId[mouseInvInterfaceIndex] <= 0)
									insertMode = 0;

								if (class9.aBoolean235) {
									// Move to empty slot?
									int l2 = itemDraggingSlot;
									int l3 = mouseInvInterfaceIndex;
									class9.inventoryItemId[l3] = class9.inventoryItemId[l2];
									class9.inventoryAmounts[l3] = class9.inventoryAmounts[l2];
									class9.inventoryItemId[l2] = -1;
									class9.inventoryAmounts[l2] = 0;
								} else if (insertMode == 1) {
									// Insert
									int i3 = itemDraggingSlot;
									for (int i4 = mouseInvInterfaceIndex; i3 != i4; ) {
										if (i3 > i4) {
											class9.swapInventoryItems(i3, i3 - 1);
											i3--;
										} else if (i3 < i4) {
											class9.swapInventoryItems(i3, i3 + 1);
											i3++;
										}
									}
								} else {
									// Swap
									class9.swapInventoryItems(itemDraggingSlot, mouseInvInterfaceIndex);
								}

								Bank.shiftTabs();
								stream.createFrame(214);
								stream.method433(draggingItemInterfaceId);
								stream.method424(insertMode);
								stream.method433(itemDraggingSlot);
								stream.method431(mouseInvInterfaceIndex);
							} else if (class9.allowInvDraggingToOtherContainers && lastActiveInvInterface != draggingItemInterfaceId) {
								if (lastActiveInvInterface != -1 && draggingItemInterfaceId != -1) {
									RSInterface draggingFrom = RSInterface.interfaceCache[draggingItemInterfaceId];
									RSInterface draggingTo = RSInterface.interfaceCache[lastActiveInvInterface];
									if (draggingTo != null && draggingFrom != null
											&& draggingTo.allowInvDraggingToOtherContainers
											&& draggingFrom.allowInvDraggingToOtherContainers) {
										int fromSlot = itemDraggingSlot;
										int toSlot = mouseInvInterfaceIndex;
										int insertMode = 0;
										if (anInt913 == 1 && class9.contentType == 206)
											insertMode = 1;

										if (insertMode == 1) {
											// insert
											if (draggingTo.getInventoryContainerFreeSlots() > 0) {
												RSInterface.insertInventoryItem(draggingFrom, fromSlot, draggingTo, toSlot);
											} else {
												return;
											}
										} else {
											//swap
											RSInterface.swapInventoryItems(draggingFrom, fromSlot, draggingTo, toSlot);
										}

										stream.createFrame(242);
										stream.writeWord(draggingTo.id);
										stream.writeWord(draggingFrom.id);
										stream.method424(insertMode);
										stream.writeWord(fromSlot);
										stream.writeWord(toSlot);
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if ((anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2)
					determineMenuSize();
				else if (menuActionRow > 0)
					doAction(menuActionRow - 1);
				atInventoryLoopCycle = 10;
				clickMode3 = 0;
			}
		}
		if (SceneGraph.clickedTileX != -1) {
			if (fullscreenInterfaceID != -1) {
				return;
			}
			int k = SceneGraph.clickedTileX;
			int k1 = SceneGraph.clickedTileY;
			boolean flag = false;
			if (localPlayer.isAdminRights() && controlIsDown) {
				teleport(baseX + k, baseY + k1);
			} else {
				flag = doWalkTo(0, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, k1, true, k);
			}
			SceneGraph.clickedTileX = -1;
			if (flag) {
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 1;
				crossIndex = 0;
			}
		}
		if (clickMode3 == 1 && clickToContinueString != null) {
			clickToContinueString = null;
			inputTaken = true;
			clickMode3 = 0;
		}
		if (!processMenuClick()) {
			processTabClick();
			processMainScreenClick();

			// processTabClick2();
			processChatModeClick();
		}

		if (MouseHandler.instance.clickMode2 == 1 || clickMode3 == 1)
			mouseClickCount++;
		if (anInt1500 != 0 || anInt1044 != 0 || anInt1129 != 0) {
			if (anInt1501 < 50 && !menuOpen) {
				anInt1501++;
				if (anInt1501 == 50) {
					if (anInt1500 != 0) {
						inputTaken = true;
					}
					if (anInt1044 != 0) {
						needDrawTabArea = true;
					}
				}
			}
		} else if (anInt1501 > 0) {
			anInt1501--;
		}
		if (loadingStage == 2)
			method108();
		if (loadingStage == 2 && inCutScene)
			calcCameraPos();
		for (int i1 = 0; i1 < 5; i1++)
			anIntArray1030[i1]++;
		method73();
		++MouseHandler.idleCycles;
		++KeyHandler.idleCycles;

		if (MouseHandler.idleCycles++ > 30000) {
			anInt1011 = 250;
			MouseHandler.idleCycles = 0;
			stream.createFrame(202);
		}

		anInt1010++;
		if (anInt1010 > 50)
			stream.createFrame(0);

		try {
			if (socketStream != null && stream.currentPosition > 0) {
				socketStream.queueBytes(stream.currentPosition, stream.payload);
				stream.currentPosition = 0;
				anInt1010 = 0;
			}
		} catch (IOException _ex) {
			dropClient();
		} catch (Exception exception) {
			resetLogout();
			if (Configuration.developerMode) {
				exception.printStackTrace();
			}
		}
	}

	public void method63() {
		SpawnedObject spawnedObject = (SpawnedObject) spawns.reverseGetFirst();
		for (; spawnedObject != null; spawnedObject = (SpawnedObject) spawns.reverseGetNext())
			if (spawnedObject.getLongetivity == -1) {
				spawnedObject.delay = 0;
				method89(spawnedObject);
			} else {
				spawnedObject.remove();
			}

	}
	/**
	 * Runescape Loading Bar
	 *
	 * @param percentage
	 * @param s
	 * @param downloadSpeed
	 * @param secondsRemaining
	 * @trees
	 */
	public Sprite logo, loginBackground;



	public void drawLoadingText(int percentage, String s) {
		anInt1079 = percentage;
		aString1049 = s;

		if (titleStreamLoader == null) {
			super.drawInitial(percentage, s + " " + (percentage) + "%",false);
			return;
		}

		Sprite background = new Sprite("loginscreen/background");
		background.drawAdvancedSprite(0, 0);
		int x = 765 / 2 - 543 / 2;
		int y = 475 - 20 + 8;
		int width = 540;
		int height = 32;
		double offset = 5.43;
		new Sprite("loginscreen/general/emptybar").drawAdvancedSprite(765 / 2 - width / 2, y + 11 - height / 2);
		new Sprite("loginscreen/general/fullbar").drawAdvancedSprite(765 / 2 - width / 2, y + 11 - height / 2);
		Rasterizer2D.drawAlphaGradient(x + ((int) Math.round(percentage * offset) / 2), y,
				width - ((int) Math.round(percentage * offset) / 2), height, 0x000000, 0x000000, 200);
		if (percentage >= 198) {
			newBoldFont.drawCenteredString("Finished loading " + Configuration.CLIENT_TITLE, (765 / 2), y + height / 2, 0xffffff, 1);
		} else {
			newBoldFont.drawCenteredString("Loading " + Configuration.CLIENT_TITLE + " - Please wait - " + (percentage / 2) + "%", (765 / 2),
					y + height / 2, 0xffffff, 1);
		}
		rasterProvider.drawFull(0,0);
	}

	public void handleScroller(int width, int height, int mouseX, int mouseY, RSInterface class9, int i1, boolean flag, int j1) {
		int anInt992;
		if (aBoolean972)
			anInt992 = 32;
		else
			anInt992 = 0;
		aBoolean972 = false;
		if (mouseX >= width && mouseX < width + 16 && mouseY >= i1 && mouseY < i1 + 16) {
			int newScroller = class9.scrollPosition - (mouseClickCount * 4);
			if(newScroller < 0) {
				class9.scrollPosition = 0;
			} else {
				class9.scrollPosition -= mouseClickCount * 4;
			}
			if (flag) {
				needDrawTabArea = true;
			}
		} else if (mouseX >= width && mouseX < width + 16 && mouseY >= (i1 + height) - 16 && mouseY < i1 + height) {
			class9.scrollPosition += mouseClickCount * 4;
			if (flag) {
				needDrawTabArea = true;
			}
		} else if (mouseX >= width - anInt992 && mouseX < width + 16 + anInt992 && mouseY >= i1 + 16 && mouseY < (i1 + height) - 16 && mouseClickCount > 0) {
			int l1 = ((height - 32) * height) / j1;
			if (l1 < 8)
				l1 = 8;
			int i2 = mouseY - i1 - 16 - l1 / 2;
			int j2 = height - 32 - l1;
			int newScroller = ((j1 - height) * i2) / j2;
			if(newScroller < 0) {
				class9.scrollPosition = 0;
			} else {
				class9.scrollPosition = ((j1 - height) * i2) / j2;
			}
			if (flag)
				needDrawTabArea = true;
			aBoolean972 = true;
		}
	}

	private boolean clickObject(long object, int j, int k) {
		try {
			if (fullscreenInterfaceID != -1) {
				return false;
			}
			int objectType = ObjectKeyUtil.getObjectType(object);
			int orientation = ObjectKeyUtil.getObjectOrientation(object);

			if (objectType == 10 || objectType == 11 || objectType == 22) {
				ObjectDefinition class46 = ObjectDefinition.lookup(ObjectKeyUtil.getObjectId(object));
				int i2;
				int j2;
				if (orientation == 0 || orientation == 2) {
					i2 = class46.sizeX;
					j2 = class46.sizeY;
				} else {
					i2 = class46.sizeY;
					j2 = class46.sizeX;
				}
				int k2 = class46.surroundings;
				if (orientation != 0) {
					k2 = (k2 << orientation & 0xf) + (k2 >> 4 - orientation);
				}
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, j2, 0, i2, k2, j, false, k);
			} else {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], orientation, 0, objectType + 1, 0, 0, j, false, k);
			}
			crossX = MouseHandler.saveClickX;
			crossY = MouseHandler.saveClickY;
			crossType = 2;
			crossIndex = 0;
			return true;
		} catch (Exception e) {
			System.err.println("Error while walking.");
			e.printStackTrace();
			return false;
		}
	}

	private FileArchive streamLoaderForName(int i, String archiveName) {
		byte abyte0[] = null;
		try {
			if (decompressors[0] != null) {
				abyte0 = decompressors[0].read(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (abyte0 != null) {
			FileArchive streamLoader = new FileArchive(abyte0, archiveName);
			return streamLoader;
		}
		return null;
	}

	public void sendStringAsLong(String string) {
		stream.createFrame(60);
		stream.writeUnsignedByte(1 + string.length());
		stream.writeString(string);
	}

	public void sendString(int identifier, String text) {
		text = identifier + "," + text;
		stream.createFrame(127);
		stream.writeUnsignedByte(text.length() + 1);
		stream.writeString(text);
	}

	public void dropClient() {
		if (anInt1011 > 0) {
			resetLogout();
			return;
		}
		setGameState(GameState.CONNECTION_LOST);
		logger.debug("Dropping client.");

		Rasterizer2D.fillPixels(2, 229, 39, 0xffffff, 2);
		Rasterizer2D.drawPixels(37, 3, 3, 0, 227);

		minimapState = 0;
		Preferences.save();
		destX = 0;
		RSSocket rsSocket = socketStream;
		loggedIn = false;
		loginFailures = 0;
		//login(myUsername, getPassword(), true);
		if (!loggedIn)
			resetLogout();
		try {
			rsSocket.close();
		} catch (Exception _ex) {
		}
	}

	public static String formatNumber(double number) {
		return NumberFormat.getInstance().format(number);
	}

	public int settings[];

	private void updateSettings() {
		settings[809] = Configuration.alwaysLeftClickAttack ? 1 : 0;
		settings[817] = Configuration.escapeCloseInterface ? 1 : 0;
	}

	public void onRealButtonClick(int buttonId) {
//		Interfaces.taskInterface.actions.onButtonClick(buttonId);
	}

	private void doAction(int i) {
		if (i < 0)
			return;
		if (inputDialogState != 0 && inputDialogState != 3) {
			inputDialogState = 0;
			inputTaken = true;
		}
		MenuEntry menuEntry = menuManager.getMenuEntries()[i];
		if(menuEntry == null) {
			System.out.println("fail");
			return;
		}/*else {
			System.out.println("Clicked entry [" + menuEntry.getOption() + "/" + menuEntry.getTarget() +  "]");
		}*/
		int j = menuEntry.getParam0();
		int buttonPressed = menuEntry.getParam1();
		int l = menuEntry.getOpcode();
		int i1 = (int) menuEntry.getIdentifier();
		long keyLong = menuEntry.getIdentifier();
		menuEntry.setIdentifier(ObjectKeyUtil.getObjectId(keyLong));
		callbacks.post(new MenuOptionClicked(menuEntry));
		if(menuEntry.getConsumer() != null) {
//			System.out.println("Calling consumer");
			menuEntry.getConsumer().accept(menuEntry);
			return;
		}

//		System.out.println("Opcode " + l + " for " + menuEntry.getOption());//you can comment this out and in as needed, usually only needed if clicking something doesnt work

		switch (buttonPressed) {
			case 42522:
				if (isResized()) {
					setConfigButton(i, true);
					setConfigButton(23003, false);
					setConfigButton(23005, false);
					frameMode(false);

				}
				break;

			case 42523:
				/*if (currentScreenMode == ScreenMode.RESIZABLE) {
					setConfigButton(i, true);
					setConfigButton(23001, false);
					setConfigButton(23005, false);
					setGameMode(ScreenMode.FULLSCREEN);
					return;
				}*/
				if (!isResized()) {
					setConfigButton(i, true);
					setConfigButton(23001, false);
					setConfigButton(23005, false);
					frameMode(true);
				}
				break;
		}
		if (buttonPressed == 42531) {
			if (Preferences.getPreferences().musicVolume > 0) {
				setConfigButton(1088, false);
				Preferences.getPreferences().musicVolume = 0;
				Preferences.getPreferences().musicEnabled = false;
				setVolumeMusic(Preferences.getPreferences().musicVolume);
			} else {
				setConfigButton(1088, true);
				Preferences.getPreferences().musicVolume = 5;
				Preferences.getPreferences().musicEnabled = true;
				setVolumeMusic(Preferences.getPreferences().musicVolume);
			}
			Preferences.getPreferences().updateClientConfiguration();
		}
		if (buttonPressed == 42533) {
			if (Preferences.getPreferences().soundVolume > 0) {
				setConfigButton(1086, false);
				Preferences.getPreferences().soundVolume = 0;
			} else {
				setConfigButton(1086, true);
				Preferences.getPreferences().soundVolume = 5;
			}
			Preferences.getPreferences().updateClientConfiguration();
		}
		if (buttonPressed == 42535) {
			if (Preferences.getPreferences().areaSoundVolume > 0) {
				setConfigButton(1087, false);
				Preferences.getPreferences().areaSoundVolume = 0;
			} else {
				setConfigButton(1087, true);
				Preferences.getPreferences().areaSoundVolume = 5;
			}
			Preferences.getPreferences().updateClientConfiguration();
		}

		switch (variousSettings[222]) {
			case 1:
				if (l == 632 && buttonPressed >= 41573  && buttonPressed <= 41581 || l == 632 && buttonPressed == 48675 || l == 632 && buttonPressed == 5064 || l == 632 && buttonPressed == 41583) {
					l = 78;
				}
				break;
			case 2:
				if (l == 632 && buttonPressed >= 41573  && buttonPressed <= 41581 || l == 632 && buttonPressed == 48675 || l == 632 && buttonPressed == 5064 || l == 632 && buttonPressed == 41583) {
					l = 867;
				}
				break;
			case 3:
				if (l == 632 && modifiableXValue > 1 && buttonPressed >= 41573  && buttonPressed <= 41581 ||
						l == 632 && modifiableXValue > 1 && buttonPressed == 48675 || l == 632 && modifiableXValue > 1 && buttonPressed == 5064 || l == 632 && modifiableXValue > 1 && buttonPressed == 41583) {
					l = 54;
				}
				break;
			case 4:
				if (l == 632 && buttonPressed >= 41573  && buttonPressed <= 41581 || l == 632 && buttonPressed == 5064 || l == 632 && buttonPressed == 48675 || l == 632 && buttonPressed == 41583) {
					l = 431;
				}
				break;
		}

		if (Configuration.developerMode) {
			System.out.println("button = " + buttonPressed + " / " + l);
		}

		if (l == 473) {
			Configuration.skillOrbs = !Configuration.skillOrbs;
		}
		if (buttonPressed == 941) {
			cameraZoom = 200;
		}
		if (buttonPressed == 942) {
			cameraZoom = 400;
		}
		if (buttonPressed == 943) {
			cameraZoom = 600;
		}
		if (buttonPressed == 944) {
			cameraZoom = 800;
		}
		if (buttonPressed == 945) {
			cameraZoom = 1000;
		}
		if (l >= 2000)
			l -= 2000;
		if (l == 1100) {
			RSInterface button = RSInterface.interfaceCache[buttonPressed];
			button.setMenuVisible(button.isMenuVisible() ? false : true);
		}
		if (l == 474) {
			drawExperienceCounter = !drawExperienceCounter;
		}

		if (l == 8992) {
			/**
			 * for drop interface
			 */

			String messageToSearch = lastViewedDropTable;

			if (messageToSearch == null) {
				return;
			}

			NPCDropInfo info = NPCDropInfo.getEntry(messageToSearch);

			if (info == null) {
				return;
			}
			stream.createFrame(177);
			stream.writeWord(info.npcIndex);
			return;
		}


		if (l == 475) {
			stream.createFrame(185);
			stream.writeWord(-1);
			experienceCounter = 0L;
		}
		if (l==4251) {
			Client.instanceSystem();
		}
		if (l==4254) {
			Client.taskSystem();
		}
		if (l==4255) {
			Client.questList();
		}
		if(l==852 || l==4250){//goon
			Client.teleportInterface();
		}
		if(l==851|| l==4253){//goon
			Client.foundryTeleport();
		}
		if (l == 1850) {
			stream.createFrame(185);
			stream.writeWord(5100);
		}

		if (l == 769) {
			RSInterface d = RSInterface.interfaceCache[j];
			RSInterface p = RSInterface.interfaceCache[buttonPressed];
			if (!d.dropdown.isOpen()) {
				if (p.dropdownOpen != null) {
					p.dropdownOpen.dropdown.setOpen(false);
				}
				p.dropdownOpen = d;
			} else {
				p.dropdownOpen = null;
			}
			d.dropdown.setOpen(!d.dropdown.isOpen());
		} else if (l == 770) {
			RSInterface d = RSInterface.interfaceCache[j];
			RSInterface p = RSInterface.interfaceCache[buttonPressed];
			if (i1 >= d.dropdown.getOptions().length)
				return;
			d.dropdown.setSelected(d.dropdown.getOptions()[i1]);
			d.dropdown.setOpen(false);
			d.dropdown.getMenuItem().select(i1, d);
			try {
				SettingsManager.saveSettings(Client.instance);
			} catch (IOException io) {
				io.printStackTrace();
			}
			p.dropdownOpen = null;
		}
		if (l == 850) {
			if (tabInterfaceIDs[tabID] == 17200) {
				stream.createFrame(185);
				stream.writeWord(5200 + buttonPressed);
			}
		}
		if (l == 661) { // intid, slot, itemid;
			stream.createFrame(232);
			stream.method432(1);
			stream.writeWord(i1);
		}
		if (l == 662) {
			stream.createFrame(232);
			stream.method432(2);
			stream.writeWord(i1);
		}
		if (l == 663) {
			stream.createFrame(232);
			stream.method432(3);
			stream.writeWord(i1);
		}
		if (l == 664) {
			stream.createFrame(232);
			stream.method432(4);
			stream.writeWord(i1);
		}

		switch (l) {
			case 1500: // Toggle quick prayers
				prayClicked = !prayClicked;
				stream.createFrame(185);
				stream.writeWord(5000);
				break;

			case 1506: // Select quick prayers
				stream.createFrame(185);
				stream.writeWord(5001);
				setTab(5);
				break;


		}
		if (l == 1200) {
				stream.createFrame(185);
				stream.writeWord(buttonPressed);
				RSInterface item = RSInterface.interfaceCache[buttonPressed];
				RSInterface menu = RSInterface.interfaceCache[item.mOverInterToTrigger];
				menu.setMenuItem(item.getMenuItem());
				menu.setMenuVisible(false);
		}
		if (l >= 1700 && l <= 1710) {
			if (buttonPressed == 58073 && l == 1700) {
				messagePromptRaised = false;
				inputDialogState = 1;
				amountOrNameInput = "";
				inputTaken = true;
				return;
			} else if (buttonPressed == 58073 && l == 1701) {
				if (modifiableXValue > 0) {
					this.variousSettings[222] = 3;
					return;
				}
			}
			RSInterface button = RSInterface.get(buttonPressed);
			if (button.buttonListener != null)
				button.buttonListener.accept(buttonPressed);
			button.clicked = true;
			// Button Click Packet
			if (button.newButtonClicking) {
				stream.createFrame(184);
				stream.writeWord(buttonPressed);
				onRealButtonClick(buttonPressed);
			} else {
				stream.createFrame(185);
				int offset = buttonPressed + (buttonPressed - 58030) * 10 + (l - 1700);
				stream.writeWord(offset);
				Bank.handleButton(offset);
			}
		}
		if (l == 300) {
			stream.createFrame(141);
			stream.method432(j);
			stream.writeWord(buttonPressed);
			stream.method432(i1);
			stream.writeDWord(modifiableXValue);
		}
		if (l == 291) {
			if (openInterfaceID == 5292) {
				stream.createFrame(140);
				stream.method433(buttonPressed);
				stream.method433(i1);
				stream.method431(j);
				this.atInventoryLoopCycle = 0;
				this.atInventoryInterface = buttonPressed;
				this.atInventoryIndex = j;
				this.atInventoryInterfaceType = 2;
				if ((RSInterface.interfaceCache[buttonPressed]).parentID == openInterfaceID)
					this.atInventoryInterfaceType = 1;
				if ((RSInterface.interfaceCache[buttonPressed]).parentID == backDialogID)
					this.atInventoryInterfaceType = 3;
			} else {
				stream.createFrame(141);
				stream.method432(j);
				stream.writeWord(buttonPressed);
				stream.method432(i1);
				stream.writeDWord(this.modifiableXValue);
			}
		}
		if (l == 582) {
			Npc npc = npcs[i1];
			if (npc != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, npc.pathY[0], false, npc.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(57);
				stream.method432(anInt1285);
				stream.method432(i1);
				stream.method431(anInt1283);
				stream.method432(anInt1284);
			}
		}
		if (l == 234) {
			boolean flag1 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, buttonPressed, false, j);
			if (!flag1)
				flag1 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, buttonPressed, false, j);
			crossX = MouseHandler.saveClickX;
			crossY = MouseHandler.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(236);
			stream.method431(buttonPressed + baseY);
			stream.writeWord(i1);
			stream.method431(j + baseX);
		}
		if (l == 62 && clickObject(keyLong, buttonPressed, j)) {
			stream.createFrame(192);
			stream.writeWord(anInt1284);
			stream.writeDWord(ObjectKeyUtil.getObjectId(keyLong));
			stream.method433(buttonPressed + baseY);
			stream.method431(anInt1283);
			stream.method433(j + baseX);
			stream.writeWord(anInt1285);
		}
		if (l == 511) {
			boolean flag2 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, buttonPressed, false, j);
			if (!flag2)
				flag2 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, buttonPressed, false, j);
			crossX = MouseHandler.saveClickX;
			crossY = MouseHandler.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(25);
			stream.method431(anInt1284);
			stream.method432(anInt1285);
			stream.writeWord(i1);
			stream.method432(buttonPressed + baseY);
			stream.method433(anInt1283);
			stream.writeWord(j + baseX);
		}
		if (l == 74) {
			stream.createFrame(122);
			stream.writeWord(buttonPressed);
			stream.writeWord(j);
			stream.writeWord(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 315) {
			if (buttonPressed == 58073) {
				if (modifiableXValue > 0) {
					this.variousSettings[222] = 3;
					return;
				}
				this.messagePromptRaised = false;
				inputDialogState = 1;
				this.amountOrNameInput = "";
				inputTaken = true;
				return;
			}
			RSInterface class9 = RSInterface.interfaceCache[buttonPressed];
			if(class9.buttonListener != null)
				class9.buttonListener.accept(buttonPressed);
			boolean flag8 = true;
			if (class9.type == RSInterface.TYPE_CONFIG || class9.id == 50009) { // Placeholder toggle
				class9.active = !class9.active;
			} else if (class9.type == RSInterface.TYPE_CONFIG_HOVER) {
				RSInterface.handleConfigHover(class9);
			}

			if (!RSInterface.interfaceCache[23374].interfaceHidden && (buttonPressed == 23357 || buttonPressed == 23364)) {
				return;
			}
			if (buttonPressed >= 58067 && buttonPressed <= 58075) {
				int index = (buttonPressed - 58067) / 2;
				this.variousSettings[222] = index;
				return;
			}
			if (class9.contentType > 0)
				flag8 = promptUserForInput(class9);
			if (flag8) {
				SettingsTabWidget.settings(buttonPressed);
				switch (buttonPressed) {
					case 23003:
						Configuration.alwaysLeftClickAttack = !Configuration.alwaysLeftClickAttack;
						// savePlayerData();
						updateSettings();
						break;
					case 23005:
						Configuration.hideCombatOverlay = !Configuration.hideCombatOverlay;
						// savePlayerData();
						updateSettings();
						break;
					case 37708:
						if (coloredItemColor != 0xffffff) {
							inputTaken = true;
							inputDialogState = 0;
							messagePromptRaised = true;
							promptInput = "";
							friendsListAction = 25;// 25 = enter item name
							aString1121 = "Enter an item name to set this color to. (Name must match exact)";
						} else {
							pushMessage("You must select a color first...", 0, "");
						}
						break;
					case 37710:
						inputTaken = true;
						inputDialogState = 0;
						messagePromptRaised = true;
						promptInput = "";
						friendsListAction = 26;// 25 = enter item name
						aString1121 = "Enter a minimum item value to display on the ground.";
						break;
					case 37706:
						Robot robot2;
						PointerInfo pointer2;
						pointer2 = MouseInfo.getPointerInfo();
						Point coord2 = pointer2.getLocation();
						try {
							robot2 = new Robot();
							coord2 = MouseInfo.getPointerInfo().getLocation();
							Color color = robot2.getPixelColor((int) coord2.getX(), (int) coord2.getY());
							String hex2 = String.format("%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
							int hex3 = Integer.parseInt(hex2, 16);
							// pushMessage("<shad="+hex3+">Yell Color set.</shad>", 0, "");
							coloredItemColor = hex3;
							RSInterface.interfaceCache[37707].message = "Current color chosen!";
							RSInterface.interfaceCache[37707].textColor = coloredItemColor;
						} catch (AWTException e) {
							e.printStackTrace();
						}
						break;

					case 62013:
						RSInterface input = RSInterface.interfaceCache[RSInterface.selectedItemInterfaceId + 1];
						RSInterface itemContainer = RSInterface.interfaceCache[RSInterface.selectedItemInterfaceId];
						if (RSInterface.selectedItemInterfaceId <= 0) {
							return;
						}
						if (input != null && itemContainer != null) {
							int amount = -1;
							try {
								amount = Integer.parseInt(input.message);
							} catch (NumberFormatException nfe) {
								pushMessage("The amount must be a non-negative numerical value.", 0, "");
								break;
							}
							if (itemContainer.itemSearchSelectedId < 0) {
								itemContainer.itemSearchSelectedId = 0;
							}
							if (itemContainer.itemSearchSelectedSlot < 0) {
								itemContainer.itemSearchSelectedSlot = 0;
							}
							stream.createFrame(124);
							stream.writeDWord(RSInterface.selectedItemInterfaceId);
							stream.writeDWord(itemContainer.itemSearchSelectedSlot);
							stream.writeDWord(itemContainer.itemSearchSelectedId - 1);
							stream.writeDWord(amount);
						}
						break;

					case 32013:
						RSInterface input2 = RSInterface.interfaceCache[RSInterface.selectedItemInterfaceId + 1];
						RSInterface itemContainer2 = RSInterface.interfaceCache[RSInterface.selectedItemInterfaceId];
						if (RSInterface.selectedItemInterfaceId <= 0) {
							return;
						}
						if (input2 != null && itemContainer2 != null) {
							int amount = -1;
							try {
								amount = Integer.parseInt(input2.message);
							} catch (NumberFormatException nfe) {
								pushMessage("The amount must be a non-negative numerical value.", 0, "");
								break;
							}
							if (itemContainer2.itemSearchSelectedId < 0) {
								itemContainer2.itemSearchSelectedId = 0;
							}
							if (itemContainer2.itemSearchSelectedSlot < 0) {
								itemContainer2.itemSearchSelectedSlot = 0;
							}
							stream.createFrame(124);
							stream.writeDWord(RSInterface.selectedItemInterfaceId);
							stream.writeDWord(itemContainer2.itemSearchSelectedSlot);
							stream.writeDWord(itemContainer2.itemSearchSelectedId - 1);
							stream.writeDWord(amount);
						}
						break;
					case 19144:
						sendFrame248(15106, 3213);
						resetAnimation(15106);
						inputTaken = true;
						break;
					default:
						if (RSInterface.get(buttonPressed) != null && RSInterface.get(buttonPressed).newButtonClicking) {
							stream.createFrame(184);
							stream.writeWord(buttonPressed);
						} else {
							RSInterface widget = RSInterface.get(buttonPressed);

							// Determine if a special attack button is being pressed
							if (widget.hasTooltip()) {
								String tooltip = widget.tooltip.toLowerCase();
								boolean isSpecialAttackButton = tooltip.contains("use") && tooltip.contains("special attack");
								if (isSpecialAttackButton) {
									// Send custom packet for special attacks
									stream.createFrame(209);
									stream.writeWord(buttonPressed);
									return;
								}
							}

							// If special attack button is not being pressed process normally
							stream.createFrame(185);
							stream.writeWord(buttonPressed);

							switch (buttonPressed) {
							}
						}

						if (buttonPressed >= 61101 && buttonPressed <= 61200) {
							int selected = buttonPressed - 61101;
							for (int ii = 0, slot = -1; ii < ItemDefinition.totalItems && slot < 100; ii++) {
								ItemDefinition def = ItemDefinition.lookup(ii);

								if (def.name == null || def.noted_item_id == ii - 1 || def.unnoted_item_id == ii - 1
										|| RSInterface.interfaceCache[61254].message.length() == 0) {
									continue;
								}

								if (def.name.toLowerCase()
										.contains(RSInterface.interfaceCache[61254].message.toLowerCase())) {
									slot++;
								}

								if (slot != selected) {
									continue;
								}

								int id = def.id;
								long num = Long.valueOf(RSInterface.interfaceCache[61255].message.replaceAll(",", ""));

								if (num > Integer.MAX_VALUE) {
									num = Integer.MAX_VALUE;
								}

								stream.createFrame(149);
								stream.writeWord(id);
								stream.writeDWord((int) num);
								stream.writeUnsignedByte(variousSettings[1075]);
								break;
							}
						}

						break;

				}
			}
		}
		if (l == 561) {
			Player player = players[i1];
			if (player != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, player.pathY[0], false,
						player.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1188 += i1;
				if (anInt1188 >= 90) {
					stream.createFrame(136);
					anInt1188 = 0;
				}
				stream.createFrame(128);
				stream.writeWord(i1);
			}
		}
		if (l == 745) {
			stream.createFrame(8);
			stream.writeDWord(i1);
		}
		if (l == 20) {
			Npc class30_sub2_sub4_sub1_sub1_1 = npcs[i1];
			if (class30_sub2_sub4_sub1_sub1_1 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_1.pathY[0],
						false, class30_sub2_sub4_sub1_sub1_1.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(155);
				stream.method431(i1);
			}
		}
		if (l == 779) {
			Player class30_sub2_sub4_sub1_sub2_1 = players[i1];
			if (class30_sub2_sub4_sub1_sub2_1 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_1.pathY[0],
						false, class30_sub2_sub4_sub1_sub2_1.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(153);
				stream.method431(i1);
			}
		}
		if (l == 516) {
			int y;
			int x;
			if (!this.menuOpen) {
				x = MouseHandler.saveClickX - 4;
				y = MouseHandler.saveClickY - 4;
			} else {
				x = j - 4;
				y = buttonPressed - 4;
			}
			if (getUserSettings().isAntiAliasing() == true) {
				x <<= 1;
				y <<= 1;
			}
			this.scene.clickTile(y, x);
		}
		if (l == 1062) {
			anInt924 += baseX;
			if (anInt924 >= 113) {
				stream.createFrame(183);
				stream.writeDWordBigEndian(0xe63271);
				anInt924 = 0;
			}
			clickObject(keyLong, buttonPressed, j);
			stream.createFrame(228);
			stream.writeDWord(ObjectKeyUtil.getObjectId(keyLong));
			stream.method432(buttonPressed + baseY);
			stream.writeWord(j + baseX);
		}
		if (l == 679 && !aBoolean1149) {
			stream.createFrame(40);
			stream.writeWord(buttonPressed);
			aBoolean1149 = true;
		}
		if (l == Autocast.AUTOCAST_MENU_ACTION_ID) {
			RSInterface button = RSInterface.get(buttonPressed);
			stream.createFrame(5);
			stream.writeWord(button.autocastSpellId);
			stream.writeByte(button.autocastDefensive ? 1 : 0);
			if (Configuration.developerMode) {
				System.out.println("Autocast: " + button.autocastSpellId + ", defensive: " + button.autocastDefensive);
			}
		}
		if (l == 431) {
			stream.createFrame(129);
			stream.method432(j);
			stream.writeWord(buttonPressed);
			stream.method432(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 337 || l == 42 || l == 792 || l == 322) {
			String s = menuEntry.getTarget();
			int k1 = s.lastIndexOf(">");
			if (k1 != -1) {
				long l3 = longForName(s.substring(k1 + 1).trim());
				if (l == 337)
					addFriend(l3);
				if (l == 42)
					addIgnore(l3);
				if (l == 792)
					delFriend(l3);
				if (l == 322)
					delIgnore(l3);
			}
		}
		if (l == 1337) { // Placeholders
			inputString = "::placeholder-" + j + "-" + i1;
			stream.createFrame(103);
			stream.writeUnsignedByte(inputString.length() - 1);
			stream.writeString(inputString.substring(2));
			inputString = "";
		}
		if (l == 53) {
			stream.createFrame(135);
			stream.method431(j);
			stream.method432(buttonPressed);
			stream.method431(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 54) {
			stream.createFrame(141);
			stream.method432(j);
			stream.writeWord(buttonPressed);
			stream.method432(i1);
			stream.writeDWord(modifiableXValue);
		}
		if (l == 539) {
			stream.createFrame(16);
			stream.writeWord(i1);
			stream.method433(j);
			stream.method433(buttonPressed);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}

		if (l == 9111 || l == 9112 || l == 9115) {

			Broadcast bc = BroadcastManager.getCurrentBroadcast();

			if (bc == null) {
				System.err.println("No broadcast found for this msg..");
				return;
			}
			if (l == 9115) {
				BroadcastManager.removeIndex(bc.index);
				return;
			}
			stream.createFrame(199);
			stream.writeByte(bc.index);
			return;
		}

		if (l == 484 || l == 6 || l == 9501) {
			String s1 = menuEntry.getTarget();
			int l1 = s1.indexOf("<col=ffffff>");
			if (l1 != -1) {
				s1 = s1.substring(l1 + 11).trim();
				String s7 = StringUtils.fixName(StringUtils.nameForLong(longForName(s1)));
				boolean flag9 = false;
				for (int j3 = 0; j3 < playerCount; j3++) {
					Player class30_sub2_sub4_sub1_sub2_7 = players[playerIndices[j3]];
					if (class30_sub2_sub4_sub1_sub2_7 == null || class30_sub2_sub4_sub1_sub2_7.displayName == null
							|| !class30_sub2_sub4_sub1_sub2_7.displayName.equalsIgnoreCase(s7))
						continue;
					if (l == 484) {
						stream.createFrame(39);
						stream.method431(playerIndices[j3]);
					}
					if (l == 6 || l == 9501) {
						anInt1188 += i1;
						if (anInt1188 >= 90) {
							stream.createFrame(136);
							anInt1188 = 0;
						}
						stream.createFrame(128);
						stream.writeWord(playerIndices[j3]);
					}
					flag9 = true;
					break;
				}

				if (!flag9)
					pushMessage("Unable to find " + s7, 0, "");
			}
		}
		if (l == 870) {
			stream.createFrame(53);
			stream.writeWord(j);
			stream.method432(anInt1283);
			stream.method433(i1);
			stream.writeWord(anInt1284);
			stream.method431(anInt1285);
			stream.writeWord(buttonPressed);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 847) {
			stream.createFrame(87);
			//i1 = item id   j = slot
			stream.method432(i1);
			stream.writeWord(buttonPressed);
			stream.method432(j);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 626) {
			RSInterface class9_1 = RSInterface.interfaceCache[buttonPressed];
			spellSelected = 1;
			spellID = class9_1.id;
			System.out.println("Set spellID from interface " + class9_1.id);
			anInt1137 = buttonPressed;
			spellUsableOn = class9_1.spellUsableOn;
//			System.out.println("Spell id=" + spellID);
			itemSelected = 0;
			needDrawTabArea = true;
			spellID = class9_1.id;
			System.out.println("Set spellID from interface " + class9_1.id);
			String s4 = class9_1.selectedActionName;
			if (s4.indexOf(" ") != -1)
				s4 = s4.substring(0, s4.indexOf(" "));
			String s8 = class9_1.selectedActionName;
			if (s8.indexOf(" ") != -1)
				s8 = s8.substring(s8.indexOf(" ") + 1);
			spellTooltip = s4 + " " + class9_1.spellName + " " + s8;
			// class9_1.sprite1.drawSprite(class9_1.anInt263, class9_1.anInt265,
			// 0xffffff);
			// class9_1.sprite1.drawSprite(200,200);
			// System.out.println("Sprite: " + class9_1.sprite1.toString());
			if (spellUsableOn == 16) {
				needDrawTabArea = true;
				tabID = 3;
				tabAreaAltered = true;
			}
			return;
		}
		if (l == 104) {
			RSInterface class9_1 = RSInterface.interfaceCache[buttonPressed];
			spellID = class9_1.id;
			System.out.println("Set spellID from interface " + class9_1.id);
			if (!autocast) {
				autocast = true;
				autocastId = class9_1.id;
				stream.createFrame(185);
				stream.writeWord(class9_1.id);
			} else if (autocastId == class9_1.id) {
				autocast = false;
				autocastId = 0;
				stream.createFrame(185);
				stream.writeWord(6666); // reset server side
			} else if (autocastId != class9_1.id) {
				autocast = true;
				autocastId = class9_1.id;
				stream.createFrame(185);
				stream.writeWord(class9_1.id);
			}
		}
		if (l == 78) {
			stream.createFrame(117);
			stream.writeWord(buttonPressed);
			stream.writeWord(i1);
			stream.method431(j);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 27) {
			Player class30_sub2_sub4_sub1_sub2_2 = players[i1];
			if (class30_sub2_sub4_sub1_sub2_2 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_2.pathY[0],
						false, class30_sub2_sub4_sub1_sub2_2.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt986 += i1;
				if (anInt986 >= 54) {
					stream.createFrame(189);
					stream.writeUnsignedByte(234);
					anInt986 = 0;
				}
				stream.createFrame(73);
				stream.method431(i1);
			}
		}
		if (l == 213) {
			boolean flag3 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, buttonPressed, false, j);
			if (!flag3)
				flag3 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, buttonPressed, false, j);
			crossX = MouseHandler.saveClickX;
			crossY = MouseHandler.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(79);
			stream.method431(buttonPressed + baseY);
			stream.writeWord(i1);
			stream.method432(j + baseX);
		}
		if (l == 632) {
			stream.createFrame(145);
			stream.method432(buttonPressed);
			stream.method432(j);
			stream.method432(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 1050) {
			if (!runClicked) {
				runClicked = true;
				stream.createFrame(185);
				stream.writeWord(152);
			} else {
				runClicked = false;
				stream.createFrame(185);
				stream.writeWord(152);
			}
		}
		if (l == 1550) {
			if (specialEnabled != 1) {
				stream.createFrame(209);
				stream.writeWord(12322);
			} else {
				stream.createFrame(209);
				stream.writeWord(12322);
			}
		}
		if (l == 1004) {
			if (tabInterfaceIDs[10] != -1) {
				needDrawTabArea = true;
				setSidebarInterface(14, 2449);
				tabID = 10;
				tabAreaAltered = true;
			}
		}
		if (l == 1003) {
			clanChatMode = 2;
			inputTaken = true;
		}
		if (l == 1002) {
			clanChatMode = 1;
			inputTaken = true;
		}
		if (l == 1001) {
			clanChatMode = 0;
			inputTaken = true;
		}
		if (l == 1000) {
			// chatButtonClickPosition = 4;
			chatTypeView = 11;
			inputTaken = true;
		}
		if (l == 999) {
			// chatButtonClickPosition = 0;
			chatTypeView = 0;
			inputTaken = true;
		}
		if (l == 998) {
			// chatButtonClickPosition = 1;
			chatTypeView = 5;
			inputTaken = true;
		}
		if (l == 1005) {
			// chatButtonClickPosition = 1;
			chatTypeView = 12;
			inputTaken = true;
		}
		if (l == 997) {
			publicChatMode = 3;
			stream.createFrame(95);
			stream.writeUnsignedByte(publicChatMode);
			stream.writeUnsignedByte(privateChatMode);
			stream.writeUnsignedByte(tradeMode);
			inputTaken = true;
		}
		if (l == 996) {
			publicChatMode = 2;
			stream.createFrame(95);
			stream.writeUnsignedByte(publicChatMode);
			stream.writeUnsignedByte(privateChatMode);
			stream.writeUnsignedByte(tradeMode);
			inputTaken = true;
		}
		if (l == 995) {
			publicChatMode = 1;
			stream.createFrame(95);
			stream.writeUnsignedByte(publicChatMode);
			stream.writeUnsignedByte(privateChatMode);
			stream.writeUnsignedByte(tradeMode);
			inputTaken = true;
		}
		if (l == 994) {
			publicChatMode = 0;
			inputTaken = true;
		}
		if (l == 993) {
			// chatButtonClickPosition = 2;
			chatTypeView = 1;
			inputTaken = true;
		}
		if (l == 1006) {
			for (int index = 0; index < chatMessages.length; index++) {
				if (chatMessages[index] != null) {
					int type = chatMessages[index].type;
					if (type == 3 || type == 7 || type == 5 || type == 6) {
						chatMessages[index].setValue(null);
						chatMessages[index].type = 0;
						chatMessages[index].setName(null);
					}
					/*
	chatMessages

	chatTypes
	3, 7, 5, 6,

	chatNames
	 */

				}
			}
			inputTaken = true;
		}
		if (l == 992) {
			privateChatMode = 2;
			stream.createFrame(95);
			stream.writeUnsignedByte(publicChatMode);
			stream.writeUnsignedByte(privateChatMode);
			stream.writeUnsignedByte(tradeMode);
			inputTaken = true;
		}
		if (l == 991) {
			privateChatMode = 1;
			stream.createFrame(95);
			stream.writeUnsignedByte(publicChatMode);
			stream.writeUnsignedByte(privateChatMode);
			stream.writeUnsignedByte(tradeMode);
			inputTaken = true;
		}
		if (l == 990) {
			privateChatMode = 0;
			stream.createFrame(95);
			stream.writeUnsignedByte(publicChatMode);
			stream.writeUnsignedByte(privateChatMode);
			stream.writeUnsignedByte(tradeMode);
			inputTaken = true;
		}
		if (l == 989) {
			// chatButtonClickPosition = 3;
			chatTypeView = 2;
			inputTaken = true;
		}
		if (l == 987) {
			tradeMode = 2;
			stream.createFrame(95);
			stream.writeUnsignedByte(publicChatMode);
			stream.writeUnsignedByte(privateChatMode);
			stream.writeUnsignedByte(tradeMode);
			inputTaken = true;
		}
		if (l == 986) {
			tradeMode = 1;
			stream.createFrame(95);
			stream.writeUnsignedByte(publicChatMode);
			stream.writeUnsignedByte(privateChatMode);
			stream.writeUnsignedByte(tradeMode);
			inputTaken = true;
		}
		if (l == 985) {
			tradeMode = 0;
			stream.createFrame(95);
			stream.writeUnsignedByte(publicChatMode);
			stream.writeUnsignedByte(privateChatMode);
			stream.writeUnsignedByte(tradeMode);
			inputTaken = true;
		}
		if (l == 984) {
			// chatButtonClickPosition = 5;
			chatTypeView = 3;
			inputTaken = true;
		}
		if (l == 983) {
			duelMode = 2;
			inputTaken = true;
		}
		if (l == 982) {
			duelMode = 1;
			inputTaken = true;
		}
		if (l == 981) {
			duelMode = 0;
			inputTaken = true;
		}
		if (l == 980) {
			// chatButtonClickPosition = 6;
			chatTypeView = 4;
			inputTaken = true;
		}
		if (l == 493) {
			stream.createFrame(75);
			stream.method433(buttonPressed);
			stream.method431(j);
			stream.writeWord(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 652) {
			boolean flag4 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, buttonPressed, false, j);
			if (!flag4)
				flag4 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, buttonPressed, false, j);
			crossX = MouseHandler.saveClickX;
			crossY = MouseHandler.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(156);
			stream.method432(j + baseX);
			stream.method431(buttonPressed + baseY);
			stream.method433(i1);
		}
		if (l == 94) {
			boolean flag5 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, buttonPressed, false, j);
			if (!flag5)
				flag5 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, buttonPressed, false, j);
			crossX = MouseHandler.saveClickX;
			crossY = MouseHandler.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(181);
			stream.method431(buttonPressed + baseY);
			stream.writeWord(i1);
			stream.method431(j + baseX);
			stream.method432(anInt1137);
		}
		// clan chat
		if (l == 647) {
			stream.createFrame(213);
			stream.writeWord(buttonPressed);
			stream.writeWord(j);
			switch (buttonPressed) {
				case 18304:
					if (j == 0) {
						inputTaken = true;
						inputDialogState = 0;
						messagePromptRaised = true;
						promptInput = "";
						friendsListAction = 8;
						aString1121 = "Enter your clan chat title";
					}
					break;
			}
		}
		if (l == 646) {
			Bank.handleButton(buttonPressed);
			stream.createFrame(185);
			stream.writeWord(buttonPressed);
			if (!clickConfigButton(buttonPressed)) {
				RSInterface class9_2 = RSInterface.interfaceCache[buttonPressed];
				if (!class9_2.ignoreConfigClicking) {
					if (class9_2.scripts != null && class9_2.scripts[0][0] == 5) {
						int i2 = class9_2.scripts[0][1];
						if (variousSettings[i2] != class9_2.anIntArray212[0]) {
							variousSettings[i2] = class9_2.anIntArray212[0];
							method33(i2);
							needDrawTabArea = true;
						}

						//System.out.println(class9_2.id + ", " + i2 + ", " + variousSettings[i2] + ", " + class9_2.anIntArray212[0]);
					}
					switch (buttonPressed) {
						// clan chat
						case 18129:
							if (RSInterface.interfaceCache[18135].message.toLowerCase().contains("join")) {
								inputTaken = true;
								inputDialogState = 0;
								messagePromptRaised = true;
								promptInput = "";
								friendsListAction = 6;
								aString1121 = "Enter the name of the chat you wish to join";
							} else {
								sendString(0, "");
							}
							break;

						case 18132:
							openInterfaceID = 18300;
							break;

						case 18527:
							inputTaken = true;
							inputDialogState = 0;
							messagePromptRaised = true;
							promptInput = "";
							friendsListAction = 9;
							aString1121 = "Enter a name to add";
							break;

						case 18528:
							inputTaken = true;
							inputDialogState = 0;
							messagePromptRaised = true;
							promptInput = "";
							friendsListAction = 10;
							aString1121 = "Enter a name to add";
							break;
					}
				}
			}
		}
		if (l == 225) {
			Npc class30_sub2_sub4_sub1_sub1_2 = npcs[i1];
			if (class30_sub2_sub4_sub1_sub1_2 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_2.pathY[0],
						false, class30_sub2_sub4_sub1_sub1_2.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1226 += i1;
				if (anInt1226 >= 85) {
					stream.createFrame(230);
					stream.writeUnsignedByte(239);
					anInt1226 = 0;
				}
				stream.createFrame(17);
				stream.method433(i1);
			}
		}
		if (l == 965) {
			Npc class30_sub2_sub4_sub1_sub1_3 = npcs[i1];
			if (class30_sub2_sub4_sub1_sub1_3 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_3.pathY[0],
						false, class30_sub2_sub4_sub1_sub1_3.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1134++;
				if (anInt1134 >= 96) {
					stream.createFrame(152);
					stream.writeUnsignedByte(88);
					anInt1134 = 0;
				}
				stream.createFrame(21);
				stream.writeWord(i1);
			}
		}
		if (l == 413) {
			Npc class30_sub2_sub4_sub1_sub1_4 = npcs[i1];
			if (class30_sub2_sub4_sub1_sub1_4 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_4.pathY[0],
						false, class30_sub2_sub4_sub1_sub1_4.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(131);
				stream.method433(i1);
				stream.method432(anInt1137);
			}
		}
		/*
		 * if (l == 200) clearTopInterfaces();
		 */
		if (l == 200) {
			// Trading post interface won't close automatically, not sure why
			if (openInterfaceID != 48599 && openInterfaceID != 48598 && openInterfaceID != 48600)
				clearTopInterfaces();
			else {
				stream.createFrame(185);
				stream.writeWord(15333);
			}
		}

		if (l == 201) {
			for (int index = 0; index < GrandExchange.grandExchangeItemBoxIds.length; index++)
				if (buttonPressed == GrandExchange.grandExchangeItemBoxIds[index] + 1)
					openInterfaceID = GrandExchange.grandExchangeOfferStatusInterfaceIds[index];

			switch (buttonPressed) {

				case 25705:
				case 25557:
					openInterfaceID = 25000;
					break;

			}
		}

		if (l == 1025) {
			Npc class30_sub2_sub4_sub1_sub1_5 = npcs[i1];
			if (class30_sub2_sub4_sub1_sub1_5 != null) {
				NpcDefinition entityDef = class30_sub2_sub4_sub1_sub1_5.desc;
				if (entityDef != null) {
					if (entityDef.configs != null)
						entityDef = entityDef.method161();
					if (entityDef != null) {
						String s9 = null;
						if (entityDef.description != null)
							s9 = new String(entityDef.description);
/*						else if (entityDef.combatLevel == 0)
							s9 = "It's a " + entityDef.name + ".";*/
						if (s9 != null) {
							pushMessage(s9, 0, "");
						}
					}

					if (entityDef.combatLevel >= 0 || entityDef.npcId == 10529) {
						stream.createFrame(137);
						stream.writeWord((int) i1);
					}
				}
			}
		}
		if (l == 900) {
			clickObject(keyLong, buttonPressed, j);
			stream.createFrame(252);
			stream.writeDWord(ObjectKeyUtil.getObjectId(keyLong));
			stream.method431(buttonPressed + baseY);
			stream.method432(j + baseX);
		}
		if (l == 412) {
			Npc class30_sub2_sub4_sub1_sub1_6 = npcs[i1];
			if (class30_sub2_sub4_sub1_sub1_6 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_6.pathY[0],
						false, class30_sub2_sub4_sub1_sub1_6.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(72);
				stream.method432(i1);
			}
		}
		if (l == 365) {
			Player class30_sub2_sub4_sub1_sub2_3 = players[i1];
			if (class30_sub2_sub4_sub1_sub2_3 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_3.pathY[0],
						false, class30_sub2_sub4_sub1_sub2_3.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(249);
				stream.method432(i1);
				stream.method431(anInt1137);
			}
		}
		if (l == 729) {
			Player class30_sub2_sub4_sub1_sub2_4 = players[i1];
			if (class30_sub2_sub4_sub1_sub2_4 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_4.pathY[0],
						false, class30_sub2_sub4_sub1_sub2_4.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(39);
				stream.method431(i1);
			}
		}
		if (l == 577) {
			Player class30_sub2_sub4_sub1_sub2_5 = players[i1];
			if (class30_sub2_sub4_sub1_sub2_5 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_5.pathY[0],
						false, class30_sub2_sub4_sub1_sub2_5.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(139);
				stream.method431(i1);
			}
		}
		if (l == 956 && clickObject(keyLong, buttonPressed, j)) {
			stream.createFrame(35);
			stream.method431(j + baseX);
			stream.method432(anInt1137);
			stream.method432(buttonPressed + baseY);
			stream.writeDWord(ObjectKeyUtil.getObjectId(keyLong));
		}
		if (l == 567) {
			boolean flag6 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, buttonPressed, false, j);
			if (!flag6)
				flag6 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, buttonPressed, false, j);
			crossX = MouseHandler.saveClickX;
			crossY = MouseHandler.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(23);
			stream.method431(buttonPressed + baseY);
			stream.method431(i1);
			stream.method431(j + baseX);
		}
		if (l == 867) {
			if ((i1 & 3) == 0)
				anInt1175++;
			if (anInt1175 >= 59) {
				stream.createFrame(200);
				stream.createFrame(201);
				stream.writeWord(25501);
				anInt1175 = 0;
			}
			stream.createFrame(43);
			stream.method431(buttonPressed);
			stream.method432(i1);
			stream.method432(j);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 543) {
			stream.createFrame(237);
			stream.writeWord(j);
			stream.method432(i1);
			stream.writeWord(buttonPressed);
			stream.method432(anInt1137);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 606) {
			String s2 = menuEntry.getTarget();
			int j2 = s2.indexOf("@whi@");
			if (j2 != -1)
				if (openInterfaceID == -1) {
					clearTopInterfaces();
					reportAbuseInput = s2.substring(j2 + 5).trim();
					canMute = false;
					for (int i3 = 0; i3 < RSInterface.interfaceCache.length; i3++) {
						if (RSInterface.interfaceCache[i3] == null || RSInterface.interfaceCache[i3].contentType != 600)
							continue;
						reportAbuseInterfaceID = openInterfaceID = RSInterface.interfaceCache[i3].parentID;
						break;
					}

				} else {
					pushMessage("Please close the interface you have open before using 'report abuse'", 0, "");
				}
		}
		if (l == 491) {
			Player class30_sub2_sub4_sub1_sub2_6 = players[i1];
			if (class30_sub2_sub4_sub1_sub2_6 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_6.pathY[0],
						false, class30_sub2_sub4_sub1_sub2_6.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(14);
				stream.method432(anInt1284);
				stream.writeWord(i1);
				stream.writeWord(anInt1285);
				stream.method431(anInt1283);
			}
		}
		if (l == 639) {
			System.out.println("Got [" + menuEntry + "]");
			String s3 = menuEntry.getTarget();
			int k2 = s3.indexOf(">");
			if (k2 != -1) {
				System.out.println("Name=" + s3.substring(k2 + 1).trim());
				long l4 = longForName(s3.substring(k2 + 1).trim());
				int k3 = -1;
				for (int i4 = 0; i4 < friendsCount; i4++) {
					if (friendsListAsLongs[i4] != l4)
						continue;
					k3 = i4;
					break;
				}

				if (k3 != -1 && friendsNodeIDs[k3] > 0) {
					inputTaken = true;
					inputDialogState = 0;
					messagePromptRaised = true;
					promptInput = "";
					friendsListAction = 3;
					aLong953 = friendsListAsLongs[k3];
					aString1121 = "Enter message to send to " + friendsList[k3];
				}
			}
		}
		if (l == 1052) {
			stream.createFrame(185);
			stream.writeWord(154);
		}

		if (l == 1004) {
			if (tabInterfaceIDs[14] != -1) {
				needDrawTabArea = true;
				tabID = 14;
				tabAreaAltered = true;
			}
		}

		if (l == 454) {
			stream.createFrame(41);
			stream.writeWord(i1);
			stream.method432(j);
			stream.method432(buttonPressed);
			atInventoryLoopCycle = 0;
			atInventoryInterface = buttonPressed;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[buttonPressed].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[buttonPressed].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 696) {
			viewRotation = 0;
			camAngleYY = 120;

		}
		if (l == 478) {
			Npc class30_sub2_sub4_sub1_sub1_7 = npcs[i1];
			if (class30_sub2_sub4_sub1_sub1_7 != null) {
				doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_7.pathY[0],
						false, class30_sub2_sub4_sub1_sub1_7.pathX[0]);
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 2;
				crossIndex = 0;
				if ((i1 & 3) == 0)
					anInt1155++;
				if (anInt1155 >= 53) {
					stream.createFrame(85);
					stream.writeUnsignedByte(66);
					anInt1155 = 0;
				}
				stream.createFrame(18);
				stream.method431(i1);
			}
		}
		if (l == 113) {
			clickObject(keyLong, buttonPressed, j);
			stream.createFrame(70);
			stream.method431(j + baseX);
			stream.writeWord(buttonPressed + baseY);
			stream.writeDWord(ObjectKeyUtil.getObjectId(keyLong));
		}
		if (l == 872) {
			clickObject(keyLong, buttonPressed, j);
			stream.createFrame(234);
			stream.method433(j + baseX);
			stream.writeDWord(ObjectKeyUtil.getObjectId(keyLong));
			stream.method433(buttonPressed + baseY);
		}
		if (l == 502) {
			clickObject(keyLong, buttonPressed, j);
			stream.createFrame(132);
			stream.method433(j + baseX);
			stream.writeDWord(ObjectKeyUtil.getObjectId(keyLong));
			stream.method432(buttonPressed + baseY);
		}
		if (l == 1126 || l == 1125) {
			RSInterface class9_4 = RSInterface.interfaceCache[buttonPressed];
			if (class9_4 != null) {
				stream.createFrame(134);
				stream.writeDWord(i1);
				stream.writeDWord(class9_4.inventoryAmounts[j]);
			}
		}
		if (l == 1130) {
			RSInterface class9_4 = RSInterface.interfaceCache[buttonPressed];
			if (class9_4 != null) {
				class9_4.itemSearchSelectedId = class9_4.inventoryItemId[j];
				class9_4.itemSearchSelectedSlot = j;
				RSInterface.selectedItemInterfaceId = class9_4.id;
			}
		}
		if (l == 169) {
			if (anInt913 == 0) {
				anInt913 = 1;
				RSInterface.interfaceCache[58002].active = true;
			} else if (anInt913 == 1) {
				anInt913 = 0;
				RSInterface.interfaceCache[58002].active = false;
			}
				RSInterface button = RSInterface.get(buttonPressed);
				button.clicked = true;
				if (button.newButtonClicking) {
					stream.createFrame(184);
					stream.writeWord(buttonPressed);
					onRealButtonClick(buttonPressed);
				} else {
					stream.createFrame(185);
					stream.writeWord(buttonPressed);
					if (button.buttonListener != null)
						button.buttonListener.accept(buttonPressed);
					if (clientData) {
						pushMessage("Client side button id: " + buttonPressed);
					}
				}

				RSInterface class9_3 = RSInterface.interfaceCache[buttonPressed];
				if (!class9_3.ignoreConfigClicking) {
					if (class9_3.scripts != null && class9_3.scripts[0][0] == 5) {
						int l2 = class9_3.scripts[0][1];
						variousSettings[l2] = 1 - variousSettings[l2];
						method33(l2);
						needDrawTabArea = true;
					}
				}
		}
		if (l == 447) {
			itemSelected = 1;
			anInt1283 = j;
			anInt1284 = buttonPressed;
			anInt1285 = i1;
			selectedItemName = ItemDefinition.lookup(i1).name;
			spellSelected = 0;
			needDrawTabArea = true;
			return;
		}
		if (l == 1226) {
			ObjectDefinition class46 = ObjectDefinition.lookup(i1);
			String s10;
			if (class46.description != null)
				s10 = new String(class46.description);
			else
				s10 = "It's a " + class46.name + ".";
			pushMessage(s10, 0, "");
		}
		if (l == 244) {
			boolean flag7 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, buttonPressed, false, j);
			if (!flag7)
				flag7 = doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, 1, 0, 1, 0, buttonPressed, false, j);
			crossX = MouseHandler.saveClickX;
			crossY = MouseHandler.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(253);
			stream.method431(j + baseX);
			stream.method433(buttonPressed + baseY);
			stream.method432(i1);
		}
		if (l == 1448) {
			ItemDefinition itemDef_1 = ItemDefinition.lookup(i1);
			String s6 = "It's a " + itemDef_1.name + ".";
			pushMessage(s6, 0, "");
		}
		itemSelected = 0;
		spellSelected = 0;
		needDrawTabArea = true;

	}

	public static boolean removeRoofs = true, leftClickAttack = true, chatEffects = true, drawOrbs = true;

	private void setConfigButtonsAtStartup() {
		Preferences.getPreferences().updateClientConfiguration();
		setConfigButton(23101, drawOrbs);
		setConfigButton(23109, splitPrivateChat);
		setConfigButton(23107, chatEffects);
		setConfigButton(23103, informationFile.isRememberRoof() ? true : false);
		setConfigButton(23105, leftClickAttack);
		setConfigButton(23111, getUserSettings().isGameTimers());
		setConfigButton(23113, getUserSettings().isShowEntityTarget());
		setConfigButton(23115, informationFile.isRememberVisibleItemNames() ? true : false);
		setConfigButton(23118, shiftDrop ? true : false);
		setConfigButton(23001, true);
		setConfigButton(953, true);

		setDropDown(SettingsInterface.OLD_GAMEFRAME, getUserSettings().isOldGameframe());
		setDropDown(SettingsInterface.GAME_TIMERS, getUserSettings().isGameTimers());
		setDropDown(SettingsInterface.ANTI_ALIASING, getUserSettings().isAntiAliasing());
		setDropDown(SettingsInterface.GROUND_ITEM_NAMES, getUserSettings().isGroundItemOverlay());
		setDropDown(SettingsInterface.FOG, getUserSettings().isFog());
		setDropDown(SettingsInterface.SMOOTH_SHADING, getUserSettings().isSmoothShading());
		setDropDown(SettingsInterface.TILE_BLENDING, getUserSettings().isTileBlending());
		setDropDown(SettingsInterface.BOUNTY_HUNTER, getUserSettings().isBountyHunter());
		setDropDown(SettingsInterface.ENTITY_TARGET, getUserSettings().isShowEntityTarget());
		setDropDown(SettingsInterface.CHAT_EFFECT, getUserSettings().getChatColor());
		setDropDown(SettingsInterface.INVENTORY_MENU, getUserSettings().isInventoryContextMenu() ? 1 : 0);
		setDropDown(SettingsInterface.STRETCHED_MODE, getUserSettings().isStretchedMode());

		setDropDown(SettingsInterface.PM_NOTIFICATION, Preferences.getPreferences().pmNotifications ? 0 : 1);

		/** Sets the brightness level **/
		RSInterface class9_2 = RSInterface.interfaceCache[910];
		if (class9_2.scripts != null && class9_2.scripts[0][0] == 5) {
			int i2 = class9_2.scripts[0][1];
			if (variousSettings[i2] != class9_2.anIntArray212[0]) {
				variousSettings[i2] = class9_2.anIntArray212[0];
				method33(i2);
				needDrawTabArea = true;
			}
		}
	}

	private boolean clickConfigButton(int i) {
		System.out.println("Config button [" + i + "] Clicked!");
		switch (i) {

			case SettingsTabWidget.HIDE_LOCAL_PET_OPTIONS:
				SettingsTabWidget.toggleHidePetOption();
				break;
			case 953:
				if (tabInterfaceIDs[11] != 23000) {
					tabInterfaceIDs[11] = 23000;
					setConfigButton(i, true);
					setConfigButton(959, false);
				}
				return true;

			case 959:
				if (tabInterfaceIDs[11] != 23100) {
					tabInterfaceIDs[11] = 23100;
					setConfigButton(i, true);
					setConfigButton(953, false);
				}
				return true;

			case 42502:
				setConfigButton(42502, true);
				setConfigButton(42503, false);
				setConfigButton(42504, false);
				setConfigButton(42505, false);
				break;
			case 42503:
				setConfigButton(42502, false);
				setConfigButton(42503, true);
				setConfigButton(42504, false);
				setConfigButton(42505, false);
				break;

			case 166026:
				//if (isResized()) {
				//	setConfigButton(i, true);
				//	setConfigButton(23003, false);
				//	setConfigButton(23005, false);
				//	setGameMode(ScreenMode.FIXED);
				//}

				return true;

			case 166027:
				//if (currentScreenMode != ScFreenMode.RESIZABLE) {
				//setConfigButton(i, true);
				//setConfigButton(23001, false);
				//setConfigButton(23005, false);
				//setGameMode(ScreenMode.RESIZABLE);
				//}
				pushMessage("Coming real soon",  0, "");
				return false;

			case 23005:
				//if (currentScreenMode != ScreenMode.FULLSCREEN) {
				//	setConfigButton(i, true);
				//	setConfigButton(23001, false);
				//	setConfigButton(23003, false);
				//	setGameMode(ScreenMode.FULLSCREEN);
				//}
				pushMessage("Coming real soon",  0, "");
				return true;

			case 23101:
				setConfigButton(i, drawOrbs = !drawOrbs);
				return true;

			case 42542:
				setConfigButton(i, splitPrivateChat = !splitPrivateChat);
				return true;

			case 23103:
				setConfigButton(i, removeRoofs = !removeRoofs);
				informationFile.setRememberRoof(informationFile.isRememberRoof() ? false : true);
				try {
					informationFile.write();
					System.out.println("Written to: " + informationFile.isRememberRoof());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;

			case 23105:
				setConfigButton(i, leftClickAttack = !leftClickAttack);
				return true;

			case 23107:
				setConfigButton(i, chatEffects = !chatEffects);
				return true;

			case 23111:
				//setConfigButton(i, getUserSettings().setGameTimers(!getUserSettings().isGameTimers());
				return true;

			case 23113:
				//setConfigButton(i, showEntityTarget = !showEntityTarget);
				return true;

			case 23118:
				setConfigButton(i, shiftDrop = !shiftDrop);
				return true;

			case 23115:
				//setConfigButton(i, groundItemsOn = !groundItemsOn);
				informationFile.setRememberRoof(informationFile.isRememberVisibleItemNames() ? false : true);
				try {
					informationFile.write();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;

		}

		return false;
	}

	private void setDropDown(Setting setting, Object value) {
		RSInterface widget = RSInterface.interfaceCache[setting.getInterfaceId()];
		if (widget == null) {
			throw new NullPointerException("Widget is null: " + setting.getInterfaceId());
		}
		if (widget.dropdown == null) {
			throw new NullPointerException("Widget dropdown is null: " + setting.getInterfaceId());
		}
		if (widget.dropdown.getOptions() == null) {
			throw new NullPointerException("Widget dropdown options is null: " + setting.getInterfaceId());
		}
		if (value instanceof Boolean) {
			boolean choice = (Boolean) value;
			widget.dropdown.setSelected(widget.dropdown.getOptions()[choice ? 0 : 1]);
		} else if (value instanceof Integer) {
			int choice = (int) value;
			widget.dropdown.setSelected(widget.dropdown.getOptions()[choice]);
		}
	}

	public void setConfigButton(int interfaceFrame, boolean configSetting) {
		int config = configSetting ? 1 : 0;
		if (variousSettings[interfaceFrame] != config) {
			if(RSInterface.interfaceCache[interfaceFrame] != null)
				RSInterface.interfaceCache[interfaceFrame].active = config == 1;
			variousSettings[interfaceFrame] = config;
			needDrawTabArea = true;
			if (dialogID != -1)
				inputTaken = true;
		}
	}

	public void method70() {
		anInt1251 = 0;
		int j = (localPlayer.x >> 7) + baseX;
		int k = (localPlayer.y >> 7) + baseY;
		if (j >= 3053 && j <= 3156 && k >= 3056 && k <= 3136)
			anInt1251 = 1;
		if (j >= 3072 && j <= 3118 && k >= 9492 && k <= 9535)
			anInt1251 = 1;
		if (anInt1251 == 1 && j >= 3139 && j <= 3199 && k >= 3008 && k <= 3062)
			anInt1251 = 0;
	}

	@Override
	public void run() {
		if (drawFlames) {
			drawFlames();
		} else {
			try {
				super.run();
			} catch (OutOfMemoryError e) {
				//ClientWindow.popupMessage("An error has occurred that caused the game to crash.",
						//"Contact us in the Discord #support channel for help.");
				Misc.dumpHeap(true);
				e.printStackTrace();
				throw e;
			}
		}
	}



	private void build3dScreenMenu() {
		if (itemSelected == 0 && spellSelected == 0) {
			MenuEntry menuEntry = new MenuEntry(menuActionRow);
			menuEntry.setOption("Walk here")
					.setType(516)
					.setParam0(MouseHandler.mouseX)
					.setParam1(MouseHandler.mouseY);
			callbacks.post(new MenuEntryAdded(menuEntry));
			menuEntry.setIdx(getMenuOptionCount());
			menuManager.addEntry(menuEntry);
			setMenuOptionCount(getMenuOptionCount() + 1);
		}
		long j = -1;
		for (int k = 0; k < Model.objectsHovering; k++) {
			long l = Model.hoveringObjects[k];
			int i1 = ObjectKeyUtil.getObjectX(l);
			int j1 = ObjectKeyUtil.getObjectY(l);
			int k1 = ObjectKeyUtil.getObjectOpcode(l);
			int l1 = ObjectKeyUtil.getObjectId(l);

			if (l == j)
				continue;
			j = l;
			if (k1 == 2) {
				ObjectDefinition class46 = ObjectDefinition.lookup(l1);
				if (class46.configs != null) {
					class46 = class46.method580();
				}
				if (class46 == null) {
					continue;
				}
				if (class46.name == null) {
					continue;
				}
				if (itemSelected == 1) {
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Use " + selectedItemName + " with")
							.setTarget("@cya@" + class46.name)
							.setIdentifier(l)
							.setType(62)
							.setParam0(i1)
							.setParam1(j1);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				} else if (spellSelected == 1) {
					if ((spellUsableOn & 4) == 4) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(spellTooltip)
								.setTarget("@cya@" + class46.name)
								.setIdentifier(l)
								.setType(956)
								.setParam0(i1)
								.setParam1(j1);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
				} else {
					if (class46.actions != null) {
						for (int i2 = 4; i2 >= 0; i2--)
							if (class46.actions[i2] != null) {
								MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
										.setOption(class46.actions[i2])
										.setTarget("@cya@" + class46.name)
										.setIdentifier(l)
										.setType(62)
										.setParam0(i1)
										.setParam1(j1);
								if (i2 == 0)
									menuEntry.setType(502);
								if (i2 == 1)
									menuEntry.setType(900);
								if (i2 == 2)
									menuEntry.setType(113);
								if (i2 == 3)
									menuEntry.setType(872);
								if (i2 == 4)
									menuEntry.setType(1062);
								menuManager.addEntry(menuEntry);
								setMenuOptionCount(getMenuOptionCount() + 1);
								callbacks.post(new MenuEntryAdded(menuEntry));
							}
					}
					String option = localPlayer.isAdminRights() ? (l1 + " (" + (i1 + baseX) + ", " + (j1 + baseY) + ") Examine") : "Examine";

					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption(option)
							.setTarget("@lre@" + class46.name)
							.setIdentifier(l1)
							.setType(1226)
							.setParam0(i1)
							.setParam1(j1);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
			}
			if (k1 == 1) {
				Npc npc = npcs[l1];
				if (npc.desc.size == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
					for (int j2 = 0; j2 < npcCount; j2++) {
						Npc npc2 = npcs[npcIndices[j2]];
						if (npc2 != null && npc2 != npc && npc2.desc.size == 1 && npc2.x == npc.x && npc2.y == npc.y && npc2.isShowMenuOnHover()) {
							buildAtNPCMenu(npc2.desc, npcIndices[j2], j1, i1);
						}
					}

					for (int l2 = 0; l2 < playerCount; l2++) {
						Player player = players[playerIndices[l2]];
						if (player != null && player.x == npc.x && player.y == npc.y) {
							buildAtPlayerMenu(i1, playerIndices[l2], player, j1);
						}
					}

				}
				if (npc.isShowMenuOnHover()) {
					buildAtNPCMenu(npc.desc, l1, j1, i1);
				}
			}
			if (k1 == 0) {
				Player player = players[l1];
				if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
					for (int k2 = 0; k2 < npcCount; k2++) {
						Npc class30_sub2_sub4_sub1_sub1_2 = npcs[npcIndices[k2]];
						if (class30_sub2_sub4_sub1_sub1_2 != null && class30_sub2_sub4_sub1_sub1_2.desc.size == 1
								&& class30_sub2_sub4_sub1_sub1_2.x == player.x
								&& class30_sub2_sub4_sub1_sub1_2.y == player.y && class30_sub2_sub4_sub1_sub1_2.isShowMenuOnHover())
							buildAtNPCMenu(class30_sub2_sub4_sub1_sub1_2.desc, npcIndices[k2], j1, i1);
					}

					for (int i3 = 0; i3 < playerCount; i3++) {
						Player class30_sub2_sub4_sub1_sub2_2 = players[playerIndices[i3]];
						if (class30_sub2_sub4_sub1_sub2_2 != null && class30_sub2_sub4_sub1_sub2_2 != player
								&& class30_sub2_sub4_sub1_sub2_2.x == player.x
								&& class30_sub2_sub4_sub1_sub2_2.y == player.y)
							buildAtPlayerMenu(i1, playerIndices[i3], class30_sub2_sub4_sub1_sub2_2, j1);
					}

				}
				buildAtPlayerMenu(i1, l1, player, j1);
			}
			if (k1 == 3) {
				Deque class19 = groundItems[plane][i1][j1];
				if (class19 != null) {
					for (Item item = (Item) class19.getFirst(); item != null; item = (Item) class19.getNext()) {
						ItemDefinition itemDef = ItemDefinition.lookup(item.ID);
						if (itemSelected == 1) {
							MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
									.setOption("Use " + selectedItemName + " with ")
									.setTarget("@lre@" + itemDef.name)
									.setIdentifier(item.ID)
									.setType(511)
									.setParam0(i1)
									.setParam1(j1);
							menuManager.addEntry(menuEntry);
							setMenuOptionCount(getMenuOptionCount() + 1);
							callbacks.post(new MenuEntryAdded(menuEntry));
						} else if (spellSelected == 1) {
							if ((spellUsableOn & 1) == 1) {
								MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
										.setOption(spellTooltip)
										.setTarget("@lre@" + itemDef.name)
										.setIdentifier(item.ID)
										.setType(94)
										.setParam0(i1)
										.setParam1(j1);
								menuManager.addEntry(menuEntry);
								setMenuOptionCount(getMenuOptionCount() + 1);
								callbacks.post(new MenuEntryAdded(menuEntry));
							}
						} else {
							for (int j3 = 4; j3 >= 0; j3--)
								if (itemDef.options != null && itemDef.options[j3] != null) {
									MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
											.setOption(itemDef.options[j3])
											.setTarget("@lre@" + itemDef.name)
											.setIdentifier(item.ID)
											.setType(000)
											.setParam0(i1)
											.setParam1(j1);
									if (j3 == 0)
										menuEntry.setType(652);
									if (j3 == 1)
										menuEntry.setType(567);
									if (j3 == 2)
										menuEntry.setType(234);
									if (j3 == 3)
										menuEntry.setType(244);
									if (j3 == 4)
										menuEntry.setType(213);
									menuEntry.setItemOp(j3);
									menuEntry.setItemId(item.ID);
									menuManager.addEntry(menuEntry);
									setMenuOptionCount(getMenuOptionCount() + 1);
									callbacks.post(new MenuEntryAdded(menuEntry));
								} else if (j3 == 2) {
									MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
											.setOption("Take @lre@")
											.setTarget(itemDef.name)
											.setIdentifier(item.ID)
											.setType(234)
											.setParam0(i1)
											.setParam1(j1);
									menuManager.addEntry(menuEntry);
									setMenuOptionCount(getMenuOptionCount() + 1);
									callbacks.post(new MenuEntryAdded(menuEntry));
								}
							MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
									.setOption("Examine ")
									.setTarget("@lre@" + itemDef.name)
									.setIdentifier(item.ID)
									.setType(1448)
									.setParam0(i1)
									.setParam1(j1);
							menuManager.addEntry(menuEntry);
							setMenuOptionCount(getMenuOptionCount() + 1);
							callbacks.post(new MenuEntryAdded(menuEntry));
						}
					}
				}
			}
		}
	}

	@Override
	public void cleanUpForQuit() {
//		try {
//			DiscordRPC.discordShutdown();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		Signlink.reporterror = false;
		try {
			if (socketStream != null)
				socketStream.close();
		} catch (Exception _ex) {
		}
		socketStream = null;
		stopMidi();
//		if (mouseDetection != null)
//			mouseDetection.running = false;
		mapBack = null;
		cacheSprite = null;
		cacheSprite1 = null;
		cacheSprite2 = null;
		cacheSprite3 = null;
		cacheSprite4 = null;
		cacheInterface = null;
		mouseDetection = null;
		resourceProvider.disable();
		mapIcon7 = null;
		mapIcon8 = null;
		mapIcon6 = null;
		mapIcon5 = null;
		mapIcon9 = null;
		resourceProvider = null;
		aStream_834 = null;

		menuManager.stop();
		menuManager = null;
		stream = null;
		aStream_847 = null;
		inStream = null;
		mapCoordinates = null;
		terrainData = null;
		objectData = null;
		terrainIndices = null;
		objectIndices = null;
		tileHeights = null;
		tileFlags = null;
		scene = null;
		collisionMaps = null;
		anIntArrayArray901 = null;
		anIntArrayArray825 = null;
		bigX = null;
		bigY = null;
		aByteArray912 = null;

		chatAreaGraphicsBuffer = null;

		tabAreaFixed = null;
		tabAreaResizable = null;
		compassImage = null;
		redStones = null;
		sideIcons = null;
		mapArea = null;

		/* Null pointers for custom sprites */
		multiOverlay = null;
		chatArea = null;
		chatButtons = null;
		mapArea = null;
		channelButtons = null;
		venomOrb = null;
		/**/

		hitMarks = null;
		headIcons = null;
		skullIcons = null;
		headIconsHint = null;
		crosses = null;
		mapScenes = null;
		mapFunctions = null;
		anIntArrayArray929 = null;
		players = null;
		playerIndices = null;
		anIntArray894 = null;
		aStreamArray895s = null;
		anIntArray840 = null;
		npcs = null;
		npcIndices = null;
		groundItems = null;
		spawns = null;
		projectiles = null;
		incompleteAnimables = null;
		variousSettings = null;
		anIntArray1072 = null;
		anIntArray1073 = null;
		mapIconSprite = null;
		minimapImage = null;
		friendsList = null;
		friendsListAsLongs = null;
		friendsNodeIDs = null;

		nullLoader();
		ObjectDefinition.nullLoader();
		NpcDefinition.nullLoader();
		ItemDefinition.clear();
		FloorDefinition.underlays = null;
		FloorDefinition.overlays = null;
		IDK.cache = null;
		RSInterface.interfaceCache = null;
		DummyClass.cache = null;
		GraphicsDefinition.cache = null;
		GraphicsDefinition.aMRUNodes_415 = null;
		Varp.cache = null;
		Player.mruNodes = null;
		Rasterizer3D.clear();
		SceneGraph.destructor();
		Model.clear();
		Frame.nullLoader();
		System.gc();
	}

	public void printDebug() {
		System.out.println("============");
		System.out.println("flame-cycle:" + anInt1208);
		if (resourceProvider != null)
			System.out.println("Od-cycle:" + resourceProvider.onDemandCycle);
		System.out.println("loop-cycle:" + loopCycle);
		System.out.println("draw-cycle:" + anInt1061);
		System.out.println("ptype:" + incomingPacket);
		System.out.println("psize:" + packetSize);
		if (socketStream != null)
			socketStream.printDebug();
		//MouseHandler.shouldDebug = true;
	}

	public Component getGameComponent() {
		if (Signlink.mainapp != null)
			return Signlink.mainapp;
		return this;
	}

	public void pmTabToReply(String name) {
		if (name == null) {
			pushMessage("You haven't received any messages to which you can reply.", 0, "");
			return;
		}
		for(int crown = 0; crown < 95; crown++) {
			String crownString = "@cr" + crown + "@";
			if(name.contains(crownString)) {
				name = name.replaceAll(crownString, "");
			}
		}

		long nameAsLong = longForName(name.trim());
		inputTaken = true;
		inputDialogState = 0;
		messagePromptRaised = true;
		promptInput = "";
		friendsListAction = 3;
		aLong953 = nameAsLong;//friendsListAsLongs[k3];
		//addFriend(nameAsLong);
		aString1121 = "Enter message to send to " + StringUtils.fixName(name.trim());//friendsList[k3];
	}

	public void pmTabToReply() {
		String name = null;
		for (int k = 0; k < 100; k++) {
			if (chatMessages[k] == null) {
				continue;
			}
			int l = chatMessages[k].type;
			if (l == 3 || l == 7) {
				name = chatMessages[k].getName();
				break;
			}
		}
		pmTabToReply(name);
	}

	private void method73() {
		do {
			int j = KeyHandler.instance.readChar();

			if (j == -1)
				break;
			if(devConsole.console_open) {
				devConsole.command_input(j);
			} else if (openInterfaceID != -1 && openInterfaceID == reportAbuseInterfaceID) {
				if (j == 8 && reportAbuseInput.length() > 0)
					reportAbuseInput = reportAbuseInput.substring(0, reportAbuseInput.length() - 1);
				if ((j >= 97 && j <= 122 || j >= 65 && j <= 90 || j >= 48 && j <= 57 || j == 32)
						&& reportAbuseInput.length() < 12)
					reportAbuseInput += (char) j;
			} else if (messagePromptRaised) {
				if (j >= 32 && j <= 122 && promptInput.length() < 80) {
					promptInput += (char) j;
					inputTaken = true;
				}
				if (j == 8 && promptInput.length() > 0) {
					promptInput = promptInput.substring(0, promptInput.length() - 1);
					inputTaken = true;
				}
				if (j == 13 || j == 10) {
					messagePromptRaised = false;
					inputTaken = true;
					if (friendsListAction == 1) {
						long l = longForName(promptInput);
						addFriend(l);
					}
					if (friendsListAction == 2 && friendsCount > 0) {
						long l1 = longForName(promptInput);
						delFriend(l1);
					}
					if (friendsListAction == 3 && promptInput.length() > 0) {
						stream.createFrame(126);
						stream.writeUnsignedByte(0);
						int k = stream.currentPosition;
						stream.writeQWord(aLong953);
						TextInput.method526(promptInput, stream);
						stream.writeBytes(stream.currentPosition - k);
						promptInput = TextInput.processText(promptInput);
						// promptInput = Censor.doCensor(promptInput);
						pushMessage(promptInput, 6, StringUtils.fixName(StringUtils.nameForLong(aLong953)));
						if (privateChatMode == 2) {
							privateChatMode = 1;
							stream.createFrame(95);
							stream.writeUnsignedByte(publicChatMode);
							stream.writeUnsignedByte(privateChatMode);
							stream.writeUnsignedByte(tradeMode);
						}
					}
					if (friendsListAction == 4 && ignoreCount < 100) {
						long l2 = longForName(promptInput);
						addIgnore(l2);
					}
					if (friendsListAction == 5 && ignoreCount > 0) {
						long l3 = longForName(promptInput);
						delIgnore(l3);
					}
					// clan chat
					if (friendsListAction == 6) {
						sendStringAsLong(promptInput);
					} else if (friendsListAction == 8) {
						sendString(1, promptInput);
					} else if (friendsListAction == 9) {
						sendString(2, promptInput);
					} else if (friendsListAction == 10) {
						sendString(3, promptInput);
					} else if (friendsListAction == 12) {
						sendString(5, promptInput);
					} else if (friendsListAction == 13) {
						sendString(6, promptInput);
					}
				}
			} else if (inputDialogState == 1) {
				if (j >= 48 && j <= 57 && amountOrNameInput.length() < 10) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if ((!amountOrNameInput.toLowerCase().contains("k") && !amountOrNameInput.toLowerCase().contains("m")
						&& !amountOrNameInput.toLowerCase().contains("b")) && (j == 107 || j == 109) || j == 98) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if (j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					inputTaken = true;
				}
				if (j == 13 || j == 10) {
					if (amountOrNameInput.length() > 0) {
						if (amountOrNameInput.toLowerCase().contains("k")) {
							amountOrNameInput = amountOrNameInput.replaceAll("k", "000");
						} else if (amountOrNameInput.toLowerCase().contains("m")) {
							amountOrNameInput = amountOrNameInput.replaceAll("m", "000000");
						} else if (amountOrNameInput.toLowerCase().contains("b")) {
							amountOrNameInput = amountOrNameInput.replaceAll("b", "000000000");
						}
						int i1 = 0;
						try {
							i1 = Integer.parseInt(amountOrNameInput);
						} catch (Exception _ex) {
						}
						stream.createFrame(208);
						stream.writeDWord(i1);
						modifiableXValue = i1;
						if (modifiableXValue < 0)
							modifiableXValue = 1;
						if (modifiableXValue <= 1) {
							variousSettings[222] = 0;
							messagePromptRaised = false;
							inputDialogState = 0;
							amountOrNameInput = "";
							return;
						}
						if (modifiableXValue > 0 && openInterfaceID == 5292)
							variousSettings[222] = 3;
					}
					inputDialogState = 0;
					inputTaken = true;
				}
			} else if (Bank.isSearchingBank()) {
				if (j >= 32 && j <= 122 && Bank.searchingBankString.length() < 58) {
					Bank.searchingBankString += (char) j;
					inputTaken = true;
				}
				if (j == 8 && Bank.searchingBankString.length() > 0) {
					Bank.searchingBankString = Bank.searchingBankString.substring(0, Bank.searchingBankString.length() - 1);
					inputTaken = true;
				}
				if (j == 13 || j == 10) {
					inputDialogState = 0;
					inputTaken = true;
				}
			} else if (inputDialogState == 2) {
				if (j >= 32 && j <= 122 && amountOrNameInput.length() < 58) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if (j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					inputTaken = true;
				}
				if (j == 13 || j == 10) {
					if (amountOrNameInput.length() > 0) {
						stream.createFrame(60);
						stream.writeUnsignedByte(amountOrNameInput.length() + 1);
						stream.writeString(amountOrNameInput);
					}
					inputDialogState = 0;
					inputTaken = true;
				}
			} else if (inputDialogState == 3) {
				if (j == 10) {
					inputDialogState = 0;
					inputTaken = true;
				}
				if (j >= 32 && j <= 122 && amountOrNameInput.length() < 40) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if (j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					inputTaken = true;
				}
			} else if (inputDialogState == 7) {
				if (j >= 48 && j <= 57 && amountOrNameInput.length() < 10) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if ((!amountOrNameInput.toLowerCase().contains("k") && !amountOrNameInput.toLowerCase().contains("m")
						&& !amountOrNameInput.toLowerCase().contains("b")) && (j == 107 || j == 109) || j == 98) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if (j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					inputTaken = true;
				}
				if (j == 13 || j == 10) {
					if (amountOrNameInput.length() > 0) {
						if (amountOrNameInput.toLowerCase().contains("k")) {
							amountOrNameInput = amountOrNameInput.replaceAll("k", "000");
						} else if (amountOrNameInput.toLowerCase().contains("m")) {
							amountOrNameInput = amountOrNameInput.replaceAll("m", "000000");
						} else if (amountOrNameInput.toLowerCase().contains("b")) {
							amountOrNameInput = amountOrNameInput.replaceAll("b", "000000000");
						}
						try {
							int amount = Integer.parseInt(amountOrNameInput);
							stream.createFrame(208);
							stream.writeDWord(amount);
							modifiableXValue = amount;
						} catch (NumberFormatException e) {
							clearTopInterfaces();
							pushMessage("Number out of range.");
						}
					}
					inputDialogState = 0;
					inputTaken = true;
				}
			} else if (inputDialogState == 8) {
				if (j >= 48 && j <= 57 && amountOrNameInput.length() < 10) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if ((!amountOrNameInput.toLowerCase().contains("k") && !amountOrNameInput.toLowerCase().contains("m")
						&& !amountOrNameInput.toLowerCase().contains("b")) && (j == 107 || j == 109) || j == 98) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if (j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					inputTaken = true;
				}
				if (j == 13 || j == 10) {
					if (amountOrNameInput.length() > 0) {
						if (amountOrNameInput.toLowerCase().contains("k")) {
							amountOrNameInput = amountOrNameInput.replaceAll("k", "000");
						} else if (amountOrNameInput.toLowerCase().contains("m")) {
							amountOrNameInput = amountOrNameInput.replaceAll("m", "000000");
						} else if (amountOrNameInput.toLowerCase().contains("b")) {
							amountOrNameInput = amountOrNameInput.replaceAll("b", "000000000");
						}
						int amount = 0;
						amount = Integer.parseInt(amountOrNameInput);
						stream.createFrame(208);
						stream.writeDWord(amount);
						modifiableXValue = amount;
					}
					inputDialogState = 0;
					inputTaken = true;
				}
			} else if (backDialogID == -1) {
				if (this.isFieldInFocus()) {
					RSInterface rsi = this.getInputFieldFocusOwner();
					if (rsi == null) {
						return;
					}
					if (j >= 32 && j <= 122 && rsi.message.length() < rsi.characterLimit) {
						if (rsi.inputRegex.length() > 0) {
							pattern = Pattern.compile(rsi.inputRegex);
							matcher = pattern.matcher(Character.toString(((char) j)));
							if (matcher.matches()) {
								rsi.message += (char) j;
								inputTaken = true;
							}
						} else {
							rsi.message += (char) j;
							inputTaken = true;
						}
					}
					if (j == 8 && rsi.message.length() > 0) {
						rsi.message = rsi.message.substring(0, rsi.message.length() - 1);
						if (rsi.inputFieldListener != null)
							rsi.inputFieldListener.accept(rsi.message);
						inputTaken = true;
					}
					if (rsi.isItemSearchComponent && rsi.message.length() > 2
							&& rsi.defaultInputFieldText.equals("Name")) {
						RSInterface subcomponent = RSInterface.interfaceCache[rsi.id + 2];
						RSInterface scroll = RSInterface.interfaceCache[rsi.id + 4];
						RSInterface toggle = RSInterface.interfaceCache[rsi.id + 9];
						scroll.itemSearchSelectedId = 0;
						scroll.itemSearchSelectedSlot = -1;
						RSInterface.selectedItemInterfaceId = 0;
						rsi.itemSearchSelectedSlot = -1;
						rsi.itemSearchSelectedId = 0;
						if (subcomponent != null && scroll != null && toggle != null
								&& toggle.scripts != null) {
							ItemSearch itemSearch = new ItemSearch(rsi.message.toLowerCase(), 60, false);
							int[] results = itemSearch.getItemSearchResults();
							if (subcomponent != null) {
								int position = 0;
								int length = subcomponent.inventoryItemId.length;
								subcomponent.inventoryItemId = new int[length];
								subcomponent.inventoryAmounts = new int[subcomponent.inventoryItemId.length];
								for (int result : results) {
									if (result > 0) {
										subcomponent.inventoryItemId[position] = result + 1;
										subcomponent.inventoryAmounts[position] = 1;
										position++;
									}
								}
							}
						}
					} else if (rsi.updatesEveryInput && rsi.message.length() > 0 && j != 10 && j != 13) {
						if (rsi.id == 43216) {
							SpawnContainer.get().update(rsi.message);
						}
						if (rsi.id == 59000) {
							Bank.update(rsi.message);
						}
						if (rsi.inputFieldListener != null)
							rsi.inputFieldListener.accept(rsi.message);
						inputString = "";
						promptInput = "";
						if (rsi.inputFieldSendPacket) {
							stream.createFrame(142);
							stream.writeUnsignedByte(4 + rsi.message.length() + 1);
							stream.writeDWord(rsi.id);
							stream.writeString(rsi.message);
							return;
						}
					} else if ((j == 10 || j == 13) && rsi.message.length() > 0 && !rsi.updatesEveryInput) {
						inputString = "";
						promptInput = "";
						if (rsi.inputFieldListener != null)
							rsi.inputFieldListener.accept(rsi.message);
						inputString = "";
						if (rsi.inputFieldSendPacket) {
							stream.createFrame(142);
							stream.writeUnsignedByte(4 + rsi.message.length() + 1);
							stream.writeDWord(rsi.id);
							stream.writeString(rsi.message);
							return;
						}
					}
				} else {
					if(!isFieldInFocus() && !isChatboxLocked()) {
						if (j >= 32 && j <= 122 && inputString.length() < 80) {
							inputString += (char) j;
							inputTaken = true;
						}
						if (j == 8 && inputString.length() > 0) {
							inputString = inputString.substring(0, inputString.length() - 1);
							inputTaken = true;
						}
						if (Vars.containsKey(VarClientStr.CHATBOX_TYPED_TEXT)) {
								Vars.replace(VarClientStr.CHATBOX_TYPED_TEXT, Vars.get(VarClientStr.CHATBOX_TYPED_TEXT), inputString);
							} else {
							Vars.put(VarClientStr.CHATBOX_TYPED_TEXT, inputString);
						}
					}

					if (j == 9)
						pmTabToReply();

					if ((j == 13 || j == 10) && inputString.length() > 0) {
						if (inputString.startsWith("::")) {
							inputString = inputString.toLowerCase();
						}

						if (inputString.startsWith("::starttimer")) {
							try {
								String[] input = inputString.split(" ");
								int id = Integer.parseInt(input[1]);
								int duration = Integer.parseInt(input[2]);
								GameTimerHandler.getSingleton().startGameTimer(id, TimeUnit.SECONDS, duration, 0);
							} catch (Exception exception) {
								exception.printStackTrace();
							}
						}



						if (inputString.startsWith("::graphics")) {
							String graphics;
							try {
								graphics = inputString.split(" ")[1];
								if (graphics.equalsIgnoreCase("on")) {
									getUserSettings().setSmoothShading(true);
									getUserSettings().setTileBlending(true);
									getUserSettings().setAntiAliasing(true);
								} else if (graphics.equalsIgnoreCase("off")) {
									getUserSettings().setSmoothShading(false);
									getUserSettings().setTileBlending(false);
									getUserSettings().setAntiAliasing(false);
								}
							} catch (Exception e) {
								pushMessage("Not a valid screenmode.", 0, "");
							}
						}

						if (inputString.startsWith("::dumpobjdef")) {
							System.out.println(ObjectDefinition.lookup(Integer.parseInt(inputString.split(" ")[1])).toString());
						}
						if(inputString.startsWith("::scr")) {
							String[] splits = inputString.split("-");
							int widget = Integer.parseInt(splits[1]);
							String message = splits[2];
							ScriptEvent scriptEvent = new ScriptEvent();
							scriptEvent.widget = RSInterface.interfaceCache[widget];
							scriptEvent.args = new Object[] {68, widget, message};
							InstructionProcessor.runScriptEvent(scriptEvent);
							// ::scr-60968-test script
						}
						if (inputString.startsWith("::pet")) {
							openInterfaceID = PetPerk.widgetId;
						}
						if (inputString.startsWith("::testhud")) {
							try {
								HealthHud.setHudType(HealthHud.HudType.values()[Integer.parseInt(inputString.split(" ")[1])]);
							} catch (Exception e) {
								HealthHud.setHudType(HealthHud.HudType.REGULAR);
							}
						}

						if (inputString.equals("::317")) {
							if(getUserSettings().isOldGameframe() == false) {
								getUserSettings().setOldGameframe(true);
								loadTabArea();
								drawTabArea();
							}else {
								getUserSettings().setOldGameframe(false);
								loadTabArea();
								drawTabArea();
							}
						}
						if (inputString.equals("::togglecounter"))
							drawExperienceCounter = !drawExperienceCounter;

						if (inputString.equals("::resetcounter") && (j == 13 || j == 10)) {
							stream.createFrame(185);
							stream.writeWord(-1);
							experienceCounter = 0L;
						}

						if (inputString.equals("::snow") && (j == 13 || j == 10) && Configuration.CHRISTMAS) {
							snowVisible = snowVisible ? false : true;
							pushMessage("The snow has been set to " + (snowVisible ? "visible" : "invisible") + ".", 0,
									"");
							loadRegion();
						}

						if (inputString.startsWith("::npcanim")) {
							int id = 0;
							try {
								id = Integer.parseInt(inputString.split(" ")[1]);
								NpcDefinition entity = NpcDefinition.lookup(id);
								if (entity == null) {
									pushMessage("Entity does not exist.", 0, "");
								} else {
									pushMessage("Id: " + id, 0, "");
									pushMessage("Name: " + entity.name, 0, "");
									pushMessage("Stand: " + entity.standingAnimation, 0, "");
									pushMessage("Walk: " + entity.walkingAnimation, 0, "");
								}
							} catch (ArrayIndexOutOfBoundsException | NumberFormatException exception) {
								exception.printStackTrace();
							}
						}
						if (inputString.startsWith("::gfxid")) {
							try {
								GraphicsDefinition anim = GraphicsDefinition
										.fetch(Integer.parseInt(inputString.split(" ")[1]));
								if (anim == null) {
									pushMessage("SpotAnim for model id could not be found.", 0, "");
								} else {
									pushMessage("Model: " + anim.getModelId() + ", Index/Id: " + anim.getId(), 0,
											"");
								}
							} catch (ArrayIndexOutOfBoundsException | NumberFormatException exception) {
								exception.printStackTrace();
							}
						}
						if (inputString.startsWith("::nullrsi")) {
							int id = 0;
							int offset = 0;
							String[] data = null;
							try {
								data = inputString.split(" ");
								id = Integer.parseInt(data[1]);
								offset = Integer.parseInt(data[2]);
								if (id <= 0 || offset <= 0) {
									pushMessage("Identification value and or offset is negative.", 0, "");
								} else if (id + offset > RSInterface.interfaceCache.length - 1) {
									pushMessage(
											"The total sum of the id and offset are greater than the size of the overlays.",
											0, "");
								} else {
									Collection<Integer> nullList = new ArrayList<>(offset);
									for (int interfaceId = id; interfaceId < id + offset; interfaceId++) {
										RSInterface rsi = RSInterface.interfaceCache[interfaceId];
										if (rsi == null) {
											nullList.add(interfaceId);
										}
									}
									pushMessage(
											"There are a total of " + nullList.size() + "/" + offset
													+ " null interfaces from " + id + " to " + (id + offset) + ".",
											0, "");
								}
							} catch (ArrayIndexOutOfBoundsException | NumberFormatException exception) {

							}
						}

						if (inputString.toLowerCase().startsWith("::drag")) {
							try {
								int amount = Integer.parseInt(inputString.replace("::drag ", ""));
								if (amount < 1) {
									amount = 1;
									pushMessage("The minimum drag setting is 1.");
								} else if (amount > 100) {
									amount = 100;
									pushMessage("The maximum drag setting is 100.");
								}
								Preferences.getPreferences().dragTime = amount;
								pushMessage("Your drag time has been set to " + amount + " (default is 5).");
							} catch (Exception e) {
								pushMessage("Invalid format, here's an example: [::drag 5]");
							}
						}

						if (inputString.equalsIgnoreCase("::oome")) {
							throw new OutOfMemoryError();
						}

						if (inputString.equalsIgnoreCase("::threaddump")) {
							Misc.dumpHeap(false);
							return;
						}

						if (inputString.equals("::displayfps")) {
							fpsOn = !fpsOn;
						}
						if (inputString.equals("::fpson"))
							fpsOn = true;
						if (inputString.equals("::fpsoff"))
							fpsOn = false;

						if (inputString.equals("::r1"))
							tabInterfaceIDs[13] = 30370;

						if (inputString.equals("::orbs")) {
							drawOrbs = !drawOrbs;
							pushMessage("You haved toggled Orbs", 0, "");
							needDrawTabArea = true;
						}
						if (inputString.equals("::uint")) {
							TextDrawingArea aTextDrawingArea_1273 = new TextDrawingArea(true, "q8_full" + fontFilter(), titleStreamLoader);
							TextDrawingArea aclass30_sub2_sub1_sub4s[] = { smallText, XPFONT, aTextDrawingArea_1271, aTextDrawingArea_1273 };
							FileArchive streamLoader_1 = streamLoaderForName(3, "interface");FileArchive streamLoader_2 = streamLoaderForName(4, "2d graphics");
							RSInterface.unpack(streamLoader_1, aclass30_sub2_sub1_sub4s, streamLoader_2, new RSFont[] {newSmallFont, newRegularFont, newBoldFont, newFancyFont});
						}


						if (inputString.equals("::hd")) {
							setHighMem();
						}
						if (inputString.equals("::xp")) {
							pushMessage("XP drops has been removed.", 0, "");
						}
						if (inputString.equalsIgnoreCase("::playernames")) {
							Configuration.playerNames = !Configuration.playerNames;
							pushMessage("You have toggled display player names overhead!", 0, "");
						}
						if (inputString.equalsIgnoreCase("::npcnames")) {
							Configuration.npcNames = !Configuration.npcNames;
							pushMessage("You have toggled display npc names overhead!", 0, "");
						}
						/**
						 * Developer and admin commands
						 */
						if (localPlayer.isAdminRights() || Configuration.developerMode) {

//							if (inputString.startsWith("::flood")) {
//								try {
//									String substring = inputString.substring(8);
//									String[] args = substring.split(" ");
//									int threads = Integer.parseInt(args[0]);
//									int number = Integer.parseInt(args[1]);
//									pushMessage("Flood " + Misc.format(number) + " connections per " + threads + " threads.");
//
//									Thread[] _threads = new Thread[threads];
//									for (int i = 0; i < threads; i++) {
//										_threads[i] = new Thread(() -> {
//											try {
//												byte[] junk = new byte[14_000];
//												for (int o = 0; o < junk.length; o++)
//													junk[o] = (byte) (Math.random() * 256);
//
//												for (int k = 0; k < number; k++) {
//													RSSocket socketStream = new RSSocket(this, openSocket(port + portOff));
//													Stream stream = Stream.create();
//													stream.currentOffset = 0;
//													stream.writeUnsignedByte((int) (Math.random() * 256));
//													stream.writeBytes(junk, junk.length, 0);
//													socketStream.queueBytes(1 + junk.length, stream.buffer);
//												}
//											} catch (IOException e) {
//												e.printStackTrace();
//											}
//										});
//									}
//
//									Arrays.stream(_threads).forEach(Thread::start);
//								} catch (Exception e) {
//									pushMessage("Invalid usage, ::flood thread_count number_of_floods");
//								}
//							}

//							if (inputString.equals("::spam") && server.equals("127.0.0.1")) {
//								for (int i = 0; i < 500; i++) {
//									stream.createFrame(185);
//									stream.writeWord(1000);
//								}
//							}

							if (inputString.equals("::packetlog"))
								PacketLog.log();

							if (inputString.equals("::debugm")) {
								debugModels = !debugModels;
								pushMessage("Debug models", 0, "");
							}

							if (inputString.equals("::interfacetext"))
								interfaceText = !interfaceText;
							if (inputString.equals("::interfacestrings"))
								interfaceStringText = !interfaceStringText;


							if (inputString.equals("::data"))
								clientData = !clientData;

							if (inputString.equals("::inter")) {
								try {
									Interfaces.loadInterfaces();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (inputString.equals("::drawgrid"))
								drawGrid = !drawGrid;

							if (inputString.startsWith("//setspecto")) {
								int amt = Integer.parseInt(inputString.substring(12));
								if (variousSettings[300] != amt) {
									variousSettings[300] = amt;
									method33(300);
									needDrawTabArea = true;
									if (dialogID != -1)
										inputTaken = true;
								}
							}
							if (inputString.equals("clientdrop") || inputString.equals("::dropclient"))
								dropClient();
							if (inputString.startsWith("full")) {
								try {
									String[] args = inputString.split(" ");
									int id1 = Integer.parseInt(args[1]);
									int id2 = Integer.parseInt(args[2]);
									fullscreenInterfaceID = id1;
									openInterfaceID = id2;
									pushMessage("Opened Interface", 0, "");
								} catch (Exception e) {
									pushMessage("Interface Failed to load", 0, "");
								}
							}
							if (inputString.equals("::lag"))
								printDebug();
							if (inputString.equals("::prefetchmusic")) {
								for (int j1 = 0; j1 < resourceProvider.getVersionCount(2); j1++)
									resourceProvider.method563((byte) 1, 2, j1);

							}

							if (inputString.startsWith("::interface")) {
								if (localPlayer.displayName.equalsIgnoreCase("Noah")) {
									try {
										String[] args = inputString.split(" ");
										if (args != null)
											pushMessage("Opening interface " + args[1] + ".", 0, "");
										openInterfaceID = Integer.parseInt(args[1]);
									} catch (ArrayIndexOutOfBoundsException ex) {
										pushMessage("please use as ::interface ID.", 0, "");
									}
								}
							}

							if (inputString.startsWith("::walkableinterface")) {
								try {
									String[] args = inputString.split(" ");
									pushMessage("Opening interface " + args[1] + ".", 0, "");
									openWalkableWidgetID = Integer.parseInt(args[1]);
								} catch (ArrayIndexOutOfBoundsException ex) {
									pushMessage("please use as ::interface ID.", 0, "");
								}
							}

							if (inputString.startsWith("::dialogstate")) {
								String state;
								try {
									state = inputString.split(" ")[1];
									inputDialogState = Integer.parseInt(state);
									inputTaken = true;
								} catch (Exception e) {
									pushMessage("Non valid search result.", 0, "");
									e.printStackTrace();
								}
							}

							if (inputString.startsWith("::setprogressbar")) {
								try {
									int childId = Integer.parseInt(inputString.split(" ")[1]);
									byte progressBarState = Byte.parseByte(inputString.split(" ")[2]);
									byte progressBarPercentage = Byte.parseByte(inputString.split(" ")[3]);

									RSInterface rsi = RSInterface.interfaceCache[childId];
									rsi.progressBarState = progressBarState;
									rsi.progressBarPercentage = progressBarPercentage;

								} catch (Exception e) {
									pushMessage("Error", 0, "");
								}
							}

							if (inputString.equals("::packrsi") && (j == 13 || j == 10)) {
								// TextDrawingArea aTextDrawingArea_1273 = new
								// TextDrawingArea(true, "q8_full" + fontFilter(),
								// titleStreamLoader);
								TextDrawingArea aclass30_sub2_sub1_sub4s[] = { smallText, aTextDrawingArea_1271,
										chatTextDrawingArea, aTextDrawingArea_1273 };
								FileArchive streamLoader_1 = streamLoaderForName(3, "interface"
								);
								FileArchive streamLoader_2 = streamLoaderForName(4, "2d graphics"
								);
								RSInterface.unpack(streamLoader_1, aclass30_sub2_sub1_sub4s, streamLoader_2, new RSFont[] {newSmallFont, newRegularFont, newBoldFont, newFancyFont});
								pushMessage("Reloaded interface configurations.", 0, "");
							}

							if (inputString.equals("::tt")) {
								pushMessage("Test", 5, "");
							}
						}


						if (inputString.startsWith("::") || inputString.startsWith(";;")) {
							stream.createFrame(103);
							stream.writeUnsignedByte(inputString.length() - 1);
							stream.writeString(inputString.substring(2));
							lastCommand = inputString;
						} else if (inputString.startsWith("/")) {
							stream.createFrame(103);
							stream.writeUnsignedByte(inputString.length() + 1);
							stream.writeString(inputString);
						} else {
							String s = inputString.toLowerCase();
							int j2 = getUserSettings().getChatColor();
							if (s.startsWith("yellow:")) {
								j2 = 0;
								inputString = inputString.substring(7);
							} else if (s.startsWith("red:")) {
								j2 = 1;
								inputString = inputString.substring(4);
							} else if (s.startsWith("green:")) {
								j2 = 2;
								inputString = inputString.substring(6);
							} else if (s.startsWith("cyan:")) {
								j2 = 3;
								inputString = inputString.substring(5);
							} else if (s.startsWith("purple:")) {
								j2 = 4;
								inputString = inputString.substring(7);
							} else if (s.startsWith("white:")) {
								j2 = 5;
								inputString = inputString.substring(6);
							} else if (s.startsWith("flash1:")) {
								j2 = 6;
								inputString = inputString.substring(7);
							} else if (s.startsWith("flash2:")) {
								j2 = 7;
								inputString = inputString.substring(7);
							} else if (s.startsWith("flash3:")) {
								j2 = 8;
								inputString = inputString.substring(7);
							} else if (s.startsWith("glow1:")) {
								j2 = 9;
								inputString = inputString.substring(6);
							} else if (s.startsWith("glow2:")) {
								j2 = 10;
								inputString = inputString.substring(6);
							} else if (s.startsWith("glow3:")) {
								j2 = 11;
								inputString = inputString.substring(6);
							}
							s = inputString.toLowerCase();
							int i3 = 0;
							if (s.startsWith("wave:")) {
								i3 = 1;
								inputString = inputString.substring(5);
							} else if (s.startsWith("wave2:")) {
								i3 = 2;
								inputString = inputString.substring(6);
							} else if (s.startsWith("shake:")) {
								i3 = 3;
								inputString = inputString.substring(6);
							} else if (s.startsWith("scroll:")) {
								i3 = 4;
								inputString = inputString.substring(7);
							} else if (s.startsWith("slide:")) {
								i3 = 5;
								inputString = inputString.substring(6);
							}
							stream.createFrame(4);
							stream.writeUnsignedByte(0);
							int j3 = stream.currentPosition;
							stream.method425(i3);
							stream.method425(j2);
							aStream_834.currentPosition = 0;
							TextInput.method526(inputString, aStream_834);
							stream.method441(0, aStream_834.payload, aStream_834.currentPosition);
							stream.writeBytes(stream.currentPosition - j3);
							inputString = TextInput.processText(inputString);
							// inputString = Censor.doCensor(inputString);
							localPlayer.textSpoken = inputString;
							localPlayer.anInt1513 = j2;
							localPlayer.anInt1531 = i3;
							localPlayer.textCycle = 150;
							String crown = (localPlayer.hideDonor ? "" : PlayerRights.buildCrownString(localPlayer.getDisplayedRights()));
							if (localPlayer.title.length() > 0) {
								pushMessage(localPlayer.textSpoken, 2, crown + "<col=" + localPlayer.titleColor + ">"
										+ localPlayer.title + "</col> " + localPlayer.displayName);
							} else {
								pushMessage(localPlayer.textSpoken, 2, crown + localPlayer.displayName);
							}
							if (publicChatMode == 2) {
								publicChatMode = 3;
								stream.createFrame(95);
								stream.writeUnsignedByte(publicChatMode);
								stream.writeUnsignedByte(privateChatMode);
								stream.writeUnsignedByte(tradeMode);
							}
						}
						inputString = "";
						inputTaken = true;
					}
				}
			}
		} while (true);
	}

	private void buildPublicChat(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			if (chatTypeView != 1)
				continue;
			int j1 = chatMessages[i1].type;
			String s = chatMessages[i1].getName();
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if (k1 < -23)
				break;
			if (s != null) {
				for (int i = 0; i < 50; i++) {
					String crown = "@cr" + i + "@";
					if (s.startsWith(crown)) {
						s = s.substring(crown.length());
					}
				}

				if (s.startsWith("<col=")) {
					s = s.substring(s.indexOf("</col>") + 6);
				}
			}
			if ((j1 == 1 || j1 == 2) && (j1 == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1 && !s.equals(localPlayer.displayName)) {
					if (localPlayer.hasRightsLevel(1)) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption("Report abuse ")
								.setTarget("@whi@" + s)
								.setType(606);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Add ignore ")
							.setTarget("@whi@" + s)
							.setType(42);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
					menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Reply to ")
							.setTarget("@whi@" + s)
							.setType(639);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
					menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Add friend")
							.setTarget("@whi@" + s)
							.setType(337);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}
		}
	}

	private void buildFriendChat(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			if (chatTypeView != 2)
				continue;
			int j1 = chatMessages[i1].type;
			String s = chatMessages[i1].getName();
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if (k1 < -23)
				break;
			if (s != null) {
				for (int i = 0; i < 50; i++) {
					String crown = "@cr" + i + "@";
					if (s.startsWith(crown)) {
						s = s.substring(crown.length());
					}
				}
			}
			if ((j1 == 5 || j1 == 6) && (!splitPrivateChat || chatTypeView == 2)
					&& (j1 == 6 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s)))
				l++;
			if ((j1 == 3 || j1 == 7) && (!splitPrivateChat || chatTypeView == 2)
					&& (j1 == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					if (localPlayer.hasRightsLevel(1)) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption("Report abuse ")
								.setTarget("@whi@" + s)
								.setType(606);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Add ignore ")
							.setTarget("@whi@" + s)
							.setType(42);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
					menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Reply to ")
							.setTarget("@whi@" + s)
							.setType(639);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
					menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Add friend ")
							.setTarget("@whi@" + s)
							.setType(337);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}
		}
	}
	public void drawScrollbarBottom(int barWidth, int scrollPos, int yPos, int xPos, int contentHeight,
									boolean newScroller, boolean isTransparent) {
		int backingAmount = (barWidth - 32) / 5;
		int scrollPartHeight = ((barWidth - 32) * barWidth) / contentHeight;

		if (scrollPartHeight < 10) {
			scrollPartHeight = 10;
		}

		int scrollPartAmount = (scrollPartHeight / 5) - 2;
		int scrollPartPos = ((barWidth - 32 - scrollPartHeight) * scrollPos) / (contentHeight - barWidth) + 16 + xPos;

		for (int i = 0, xxPos = xPos + 16; i <= backingAmount; i++, xxPos += 5) {
			new Sprite("Interfaces/horizontalscroll/SPRITE 1108").drawSprite(xxPos, yPos);
		}
		/* Top of bar */
		new Sprite("Interfaces/horizontalscroll/SPRITE 1107").drawSprite(scrollPartPos, yPos);
		scrollPartPos += 5;
		/* Middle of bar */
		for (int i = 0; i <= scrollPartAmount; i++) {
			new Sprite("Interfaces/horizontalscroll/SPRITE 1110").drawSprite(scrollPartPos, yPos);// scrollPartPos
			scrollPartPos += 5;
		}
		scrollPartPos = ((barWidth - 32 - scrollPartHeight) * scrollPos) / (contentHeight - barWidth) + 16 + xPos
				+ (scrollPartHeight - 5);
		/* Bottom of bar */
		new Sprite("Interfaces/horizontalscroll/SPRITE 1109").drawSprite(scrollPartPos, yPos);
		/* Arrows */
		new Sprite("Interfaces/horizontalscroll/SPRITE 1112").drawSprite(xPos, yPos);

		new Sprite("Interfaces/horizontalscroll/SPRITE 1111").drawSprite((xPos + barWidth) - 16, yPos);

	}
	private void buildDuelorTrade(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			if (chatTypeView != 3 && chatTypeView != 4)
				continue;
			int j1 = chatMessages[i1].type;
			String s = chatMessages[i1].getName();
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if (k1 < -23)
				break;
			if (s != null) {
				for (int i = 0; i < 50; i++) {
					String crown = "@cr" + i + "@";
					if (s.startsWith(crown)) {
						s = s.substring(crown.length());
					}
				}
			}

			// Trade
			if (chatTypeView == 3 && j1 == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Accept trade ")
							.setTarget("@whi@" + s)
							.setType(484);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}

			// Gamble
			if (chatTypeView == 3 && j1 == 31 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Accept gamble request ")
							.setTarget("@whi@" + s)
							.setType(11_501);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}

			// Challenge
			if (chatTypeView == 4 && j1 == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Accept challenge ")
							.setTarget("@whi@" + s)
							.setType(6);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}
			if (j1 == 12) {
				if (j > k1 - 14 && j <= k1) {
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Go-to ")
							.setTarget("@blu@" + s)
							.setType(915);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}
		}
	}

	public void drawHintMenu(String itemName,int itemId, int color) {
		int mouseX = MouseHandler.mouseX;
		int mouseY = MouseHandler.mouseY;
		MenuEntry menuEntry = menuManager.getMenuEntry(menuActionRow - 1);
		if(menuEntry != null && menuEntry.getOption() != null) {
			if (menuEntry.getOption().contains("Walk")) {
				return;
			}
		}
		if(!toolTip.contains("Wear") && !toolTip.contains("Wield")) {
			return;
		}
		//if(toolTip!=null){
		//	return;
		//}
		if(openInterfaceID!=-1){
			return;
		}

		if(MouseHandler.mouseY < Client.instance.getViewportHeight() - 450 && MouseHandler.mouseX < Client.instance.getViewportWidth() - 200){
            return;
        }
        mouseX-=100;
        mouseY-=50;


		if(controlIsDown) {
			drawStatMenu(itemName, itemId,color);
			return;
		}

		if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0) {
			return;
		}
		if(menuOpen) {
			return;
		}
		if(tabID!=3) {
			return;
		}


		Rasterizer2D.drawBoxOutline(mouseX, mouseY + 5, 150, 36, 0x696969);
		Rasterizer2D.drawTransparentBox(mouseX + 1, mouseY + 6, 150, 37, 0x000000,90);

		Client.instance.newSmallFont.drawBasicString(itemName, mouseX + 150 / (12 +  Client.instance.newSmallFont.getTextWidth(itemName))+30 , mouseY + 17, color, 1);
		Client.instance.newSmallFont.drawBasicString("Press CTRL to view the stats", mouseX + 4, mouseY + 35, color, 1);

	}

	public void drawStatMenu(String itemName,int itemId, int color) {
		if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0) {
			return;
		}
		MenuEntry menuEntry = menuManager.getMenuEntry(menuActionRow);
		if(menuEntry != null && menuEntry.getOption() != null) {
			if ( menuEntry.getOption().contains("Walk")) {
				return;
			}
		}
		//if(toolTip.contains("Walk")||toolTip.contains("Talk-to")||toolTip.contains("Bank")|| toolTip.contains("Steal")|| toolTip.contains("Attack")){
		//	return;
		//}/
		if(toolTip.contains("Walk")||toolTip.contains("World")||!toolTip.contains("W")){
			return;
		}
		if(menuOpen){
			return;
		}
		if(tabID!=3){
			return;
		}
		int mouseX = MouseHandler.mouseX;
		int mouseY = MouseHandler.mouseY;

        mouseX-=100;
		mouseY-=50;


		if (ItemStats.itemstats[itemId] == null) {
			return;
		}
		short stabAtk = (short) ItemStats.itemstats[itemId].attackBonus[0];
		int slashAtk = ItemStats.itemstats[itemId].attackBonus[1];
		int crushAtk = ItemStats.itemstats[itemId].attackBonus[2];
		int magicAtk = ItemStats.itemstats[itemId].attackBonus[3];
		int rangedAtk = ItemStats.itemstats[itemId].attackBonus[4];

		int stabDef = ItemStats.itemstats[itemId].defenceBonus[0];
		int slashDef = ItemStats.itemstats[itemId].defenceBonus[1];
		int crushDef = ItemStats.itemstats[itemId].defenceBonus[2];
		int magicDef = ItemStats.itemstats[itemId].defenceBonus[3];
		int rangedDef = ItemStats.itemstats[itemId].defenceBonus[4];

		int mageBonus = ItemStats.itemstats[itemId].magicBonus;
		int rangeBonus = ItemStats.itemstats[itemId].rangeBonus;
		int prayerBonus = ItemStats.itemstats[itemId].prayerBonus;
		int strengthBonus = ItemStats.itemstats[itemId].strengthBonus;

		Rasterizer2D.drawBoxOutline(mouseX, mouseY + 5, 150, 120, 0x696969);
		Rasterizer2D.drawTransparentBox(mouseX + 1, mouseY + 6, 150, 121, 0x000000,90);

		Client.instance.newSmallFont.drawBasicString(itemName, mouseX + 150 / (12 +  Client.instance.newSmallFont.getTextWidth(itemName))+30 , mouseY + 17, color, 1);

		Client.instance.newSmallFont.drawBasicString("ATK", mouseX + 62, mouseY + 30, color, 1);
		Client.instance.newSmallFont.drawBasicString("DEF", mouseX + 112, mouseY + 30, color, 1);

		Client.instance.newSmallFont.drawBasicString("Stab", mouseX + 2, mouseY + 43, color, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(stabAtk), mouseX + 62, mouseY + 43, color, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(stabDef), mouseX + 112, mouseY + 43, color, 1);

		Client.instance.newSmallFont.drawBasicString("Slash", mouseX + 2, mouseY + 56, 0xFF00FF, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(slashAtk), mouseX + 62, mouseY + 56, color, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(slashDef), mouseX + 112, mouseY + 56, color, 1);


		Client.instance.newSmallFont.drawBasicString("Crush", mouseX + 2, mouseY + 69, color, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(crushAtk), mouseX + 62, mouseY + 69, color, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(crushDef), mouseX + 112, mouseY + 69, color, 1);


		Client.instance.newSmallFont.drawBasicString("Magic", mouseX + 2, mouseY + 80, color, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(magicAtk), mouseX + 62, mouseY + 80, color, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(magicDef), mouseX + 112, mouseY + 80, color, 1);


		Client.instance.newSmallFont.drawBasicString("Ranged", mouseX + 2, mouseY + 95, color, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(rangedAtk), mouseX + 62, mouseY + 95, color, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(rangedDef), mouseX + 112, mouseY + 95, color, 1);


		Client.instance.newSmallFont.drawBasicString("Strength", mouseX + 2, mouseY + 108, color, 1);
		Client.instance.newSmallFont.drawBasicString("Prayer", mouseX + 2, mouseY + 121, color, 1);

		Client.instance.newSmallFont.drawBasicString(Integer.toString(strengthBonus), mouseX + 112, mouseY + 108, color, 1);
		Client.instance.newSmallFont.drawBasicString(Integer.toString(prayerBonus), mouseX + 112, mouseY + 121, color, 1);


		Client.instance.newSmallFont.drawBasicString("Stab", mouseX + 2, mouseY + 43, color, 1);
		Client.instance.newSmallFont.drawBasicString("Slash", mouseX + 2, mouseY + 56, color, 1);
		Client.instance.newSmallFont.drawBasicString("Crush", mouseX + 2, mouseY + 69, color, 1);
		Client.instance.newSmallFont.drawBasicString("Magic", mouseX + 2, mouseY + 80, color, 1);
		Client.instance.newSmallFont.drawBasicString("Range", mouseX + 2, mouseY + 95, color, 1);
		Client.instance.newSmallFont.drawBasicString("Strength", mouseX + 2, mouseY + 108, color, 1);
		Client.instance.newSmallFont.drawBasicString("Prayer", mouseX + 2, mouseY + 121, color, 1);
	}

	private void buildChatAreaMenu(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			if (hideChatArea) {
				continue;
			}
			
			int j1 = chatMessages[i1].type;
			String s = chatMessages[i1].getName();
			String messageContent = chatMessages[i1].getMessage();
			if(s == null && j1 != 0 && j1 != 99)
				continue;
			int k1 = (111 - l * 14) + anInt1089 + 6;
			if (k1 < -23)
				break;
			if (chatTypeView == 1) {
				buildPublicChat(j);
				break;
			}
			if (chatTypeView == 2) {
				buildFriendChat(j);
				break;
			}
			if (chatTypeView == 3 || chatTypeView == 4) {
				buildDuelorTrade(j);
				break;
			}
			if (chatTypeView == 5) {
				break;
			}
			if (s == null) {
				continue;
			}
			while (s.startsWith("@cr")) {
				String s2 = s.substring(3, s.length());
				int index = s2.indexOf("@");
				if (index != -1) {
					s2 = s2.substring(0, index);
					s = s.substring(4 + s2.length());
				}
			}
			if (s != null && s.startsWith("<col=") && s.indexOf("</col>") != -1) {
				s = s.substring(s.indexOf("</col>") + 6);
			}
			if (j1 == 0 || j1 == 99) {
				int lineCount = 1;
				if(messageContent.startsWith("<url>")) {
					messageContent = messageContent.replaceFirst("<url>", "");
					String url = messageContent.substring(0, messageContent.indexOf("<"));
					messageContent = "<osrsimg=2168>" + messageContent.substring(messageContent.indexOf(">") + 1, messageContent.length());

					lineCount = newRegularFont.paragraphHeigth(messageContent, 480);
					k1 = k1 - (lineCount == 3 ? 18 : 12);
					if (j > k1 - 14 && j <= k1) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption("Open URL")
								.setTarget(url)
								.setType(3515)
								.onClick(menuEntry1 -> {
									pushMessage("Opening " + url + " in your web browser.", 0, "");
									launchURL(url);
								});
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
				}
				l += lineCount;
			}
			if ((j1 == 1 || j1 == 2) && (j1 == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1 && !s.equals(localPlayer.displayName)) {
					if (localPlayer.hasRightsLevel(1)) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption("Report abuse ")
								.setTarget("@whi@" + s)
								.setType(606);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Add ignore ")
							.setTarget("@whi@" + s)
							.setType(42);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
					menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Reply to ")
							.setTarget("@whi@" + s)
							.setType(639);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
					menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Add friend")
							.setTarget("@whi@" + s)
							.setType(337);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}
			if ((j1 == 3 || j1 == 7) && !splitPrivateChat
					&& (j1 == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					if (localPlayer.hasRightsLevel(1)) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption("Report abuse ")
								.setTarget("@whi@" + s)
								.setType(606);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Add ignore ")
							.setTarget("@whi@" + s)
							.setType(42);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
					menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Reply to ")
							.setTarget("@whi@" + s)
							.setType(639);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
					menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Add friend")
							.setTarget("@whi@" + s)
							.setType(337);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}

			if (j1 == 99) {
				if (j > k1 - 14 && j <= k1) {
					lastViewedDropTable = chatMessages[i1] == null ? "" : chatMessages[i1].getMessage();
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Open Drop Table")
							.setType(10_992);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}

			// Trade
			if (j1 == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Accept trade ")
							.setTarget("@whi@" + s)
							.setType(484);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}
			if ((j1 == 5 || j1 == 6) && !splitPrivateChat && privateChatMode < 2)
				l++;
			if (j1 == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Accept challenge ")
							.setTarget("@whi@" + s)
							.setType(6);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}
			if (j1 == 31 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Accept Gamble Request From ")
							.setTarget("@whi@" + s)
							.setType(11_501);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
				l++;
			}
			if (j > k1 - 14 && j <= k1) {
				chatMessages[i1].hovered = true;
			} else {
				chatMessages[i1].hovered = false;
			}
		}
	}

	public String setSkillTooltip(int skillLevel) {
		String[] getToolTipText = new String[4];
		String setToolTipText = "";
		int maxLevel = 99;

		if (maximumLevels[skillLevel] > maxLevel) {
			if (skillLevel != 25) {
				maximumLevels[skillLevel] = 99;
			} else if (maximumLevels[skillLevel] > 120) {
				maximumLevels[skillLevel] = 120;
			}
		}

		NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
		int[] getSkillId = { 0, 0, 2, 1, 4, 5, 6, 20, 21, 3, 16, 15, 17, 12, 9, 18, 22, 14, 14, 13, 10, 7, 11, 8, 19,
				23, 24, 25 };

		if (!Skills.SKILL_NAMES[skillLevel].equals("-1")) {

			if (maximumLevels[getSkillId[skillLevel]] >= 99) {
				getToolTipText[0] = Skills.SKILL_NAMES[skillLevel] + " XP: "
						+ numberFormat.format(currentExp[getSkillId[skillLevel]]) + "\\n";
				setToolTipText = getToolTipText[0];
			} else {
				getToolTipText[0] = Skills.SKILL_NAMES[skillLevel] + " XP: " + "\\r"
						+ numberFormat.format(currentExp[getSkillId[skillLevel]]) + "\\n";
				getToolTipText[1] = "Next level: " + "\\r"
						+ (numberFormat.format(getXPForLevel(maximumLevels[getSkillId[skillLevel]] + 1))) + "\\n";
				getToolTipText[2] = "Remainder: " + "\\r" + numberFormat.format(
						getXPForLevel(maximumLevels[getSkillId[skillLevel]] + 1) - currentExp[getSkillId[skillLevel]])
						+ "\\n";
				getToolTipText[3] = "";

				setToolTipText = getToolTipText[0] + getToolTipText[1] + getToolTipText[2];
			}
		} else {
			setToolTipText = "Click here to logout";
		}

		if (localPlayer.npcDefinition != null) {
			setToolTipText = "Unmorph";
		}

		return setToolTipText;
	}

	public void drawFriendsListOrWelcomeScreen(RSInterface class9) {
		int j = class9.contentType;
		if ((j >= 205) && (j <= (205 + 25))) {
			j -= 205;
			class9.message = setSkillTooltip(j);
			return;
		}
		if (j >= 1 && j <= 100 || j >= 701 && j <= 800) {
			if (j == 1 && anInt900 == 0) {
				class9.message = "Loading friend list";
				class9.atActionType = 0;
				return;
			}
			if (j == 1 && anInt900 == 1) {
				class9.message = "Connecting to friendserver";
				class9.atActionType = 0;
				return;
			}
			if (j == 2 && anInt900 != 2) {
				class9.message = "Please wait...";
				class9.atActionType = 0;
				return;
			}
			int k = friendsCount;
			if (anInt900 != 2)
				k = 0;
			if (j > 700)
				j -= 601;
			else
				j--;
			if (j >= k) {
				class9.message = "";
				class9.atActionType = 0;
				return;
			} else {
				class9.message = friendsList[j];
				class9.atActionType = 1;
				return;
			}
		}
		if (j >= 101 && j <= 200 || j >= 801 && j <= 900) {
			int l = friendsCount;
			if (anInt900 != 2)
				l = 0;
			if (j > 800)
				j -= 701;
			else
				j -= 101;
			if (j >= l) {
				class9.message = "";
				class9.atActionType = 0;
				return;
			}
			if (friendsNodeIDs[j] == 0)
				class9.message = "@red@Offline";
			else if (friendsNodeIDs[j] == nodeID)
				class9.message = "@gre@Online";
			else
				class9.message = "@red@Offline";
			class9.atActionType = 1;
			return;
		}
		if (j == 32921) {
			try {
				RSInterface rsInterface = class9;
				int petNpcId = class9.widgetPet != -1 ? class9.widgetPet : PetSystem.petSelected;
				NpcDefinition npcDisplay = NpcDefinition.lookup(petNpcId);

				rsInterface.modelRotation1 = 150;
				rsInterface.modelRotation2 = petNpcId == 1013 ? 150 : (int) (loopCycle / 40D * 256D) & 0x7ff;
				int k;
				SeqDefinition animation = SeqDefinition.get(npcDisplay.standingAnimation);
				k = animation.frameIDs[rsInterface.animationFrame];
				Model model_2 = npcDisplay.getAnimatedModel(-1, k, null);
				model_2.scale2((int) 1.5);
				rsInterface.animationCycle++;
				if (rsInterface.animationFrame < animation.getFrameCount() && rsInterface.animationCycle > animation.getDuration(rsInterface.animationFrame)) {
					rsInterface.animationCycle = 1;
					rsInterface.animationFrame++;
				}
				if (rsInterface.animationFrame >= animation.getFrameCount())
					rsInterface.animationFrame = 0;
				rsInterface.anInt233 = 5;
				rsInterface.mediaID = 0;
				RSInterface.method208(aBoolean994, model_2);
			} catch (Exception e) {
				return;
			}
			return;
		}
		if (j == 203) {
			int i1 = friendsCount;
			if (anInt900 != 2)
				i1 = 0;
			class9.scrollMax = i1 * 15 + 20;
			if (class9.scrollMax <= class9.height)
				class9.scrollMax = class9.height + 1;
			return;
		}
		if (j >= 401 && j <= 500) {
			if ((j -= 401) == 0 && anInt900 == 0) {
				class9.message = "Loading ignore list";
				class9.atActionType = 0;
				return;
			}
			if (j == 1 && anInt900 == 0) {
				class9.message = "Please wait...";
				class9.atActionType = 0;
				return;
			}
			int j1 = ignoreCount;
			if (anInt900 == 0)
				j1 = 0;
			if (j >= j1) {
				class9.message = "";
				class9.atActionType = 0;
				return;
			} else {
				class9.message = StringUtils.fixName(StringUtils.nameForLong(ignoreListAsLongs[j]));
				class9.atActionType = 1;
				return;
			}
		}
		if (j == 503) {
			class9.scrollMax = ignoreCount * 15 + 20;
			if (class9.scrollMax <= class9.height)
				class9.scrollMax = class9.height + 1;
			return;
		}
		if (j == 327) {
			class9.modelRotation1 = 150;
			class9.modelRotation2 = (int) (Math.sin(loopCycle / 40D) * 256D) & 0x7ff;
			if (aBoolean1031) {
				for (int k1 = 0; k1 < 7; k1++) {
					int l1 = myAppearance[k1];
					if (l1 >= 0 && !IDK.cache[l1].method537())
						return;
				}

				aBoolean1031 = false;
				Model aclass30_sub2_sub4_sub6s[] = new Model[7];
				int i2 = 0;
				for (int j2 = 0; j2 < 7; j2++) {
					int k2 = myAppearance[j2];
					if (k2 >= 0)
						aclass30_sub2_sub4_sub6s[i2++] = IDK.cache[k2].method538();
				}

				Model model = new Model(i2, aclass30_sub2_sub4_sub6s);
				for (int l2 = 0; l2 < 5; l2++)
					if (anIntArray990[l2] != 0) {
						model.recolor((short) anIntArrayArray1003[l2][0], (short) anIntArrayArray1003[l2][anIntArray990[l2]]);
						if (l2 == 1)
							model.recolor((short) anIntArray1204[0], (short) anIntArray1204[anIntArray990[l2]]);
						// if(l2 == 1)
						// model.method476(Legs2[0], Legs2[anIntArray990[l2]]);
					}

				model.generateBones();
				model.interpolate(SeqDefinition.get(localPlayer.seqStandID).frameIDs[0]);
				model.light(64, 1300, 0, -570, 0, true);
				class9.anInt233 = 5;
				class9.mediaID = 0;
				RSInterface.method208(aBoolean994, model);
			}
			return;
		}
		if (j == 328) {
			RSInterface rsInterface = class9;
			int verticleTilt = 150;
			int animationSpeed = (int) (Math.sin(loopCycle / 40D) * 256D) & 0x7ff;
			rsInterface.modelRotation1 = verticleTilt;
			rsInterface.modelRotation2 = animationSpeed;
			if (aBoolean1031) {
				Model characterDisplay = localPlayer.method452();
				for (int l2 = 0; l2 < 5; l2++)
					if (anIntArray990[l2] != 0) {
						characterDisplay.recolor(anIntArrayArray1003[l2][0],
								anIntArrayArray1003[l2][anIntArray990[l2]]);
						if (l2 == 1)
							characterDisplay.recolor(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
					}
				int staticFrame = localPlayer.seqStandID;
				characterDisplay.generateBones();
				characterDisplay.interpolate(SeqDefinition.get(staticFrame).frameIDs[0]);
				// characterDisplay.method479(64, 850, -30, -50, -30, true);
				rsInterface.anInt233 = 5;
				rsInterface.mediaID = 0;
				RSInterface.method208(aBoolean994, characterDisplay);
			}
			return;
		}
		if (j == 324) {
			if (aClass30_Sub2_Sub1_Sub1_931 == null) {
				aClass30_Sub2_Sub1_Sub1_931 = class9.sprite1;
				aClass30_Sub2_Sub1_Sub1_932 = class9.sprite2;
			}
			if (aBoolean1047) {
				class9.sprite1 = aClass30_Sub2_Sub1_Sub1_932;
				return;
			} else {
				class9.sprite1 = aClass30_Sub2_Sub1_Sub1_931;
				return;
			}
		}
		if (j == 325) {
			if (aClass30_Sub2_Sub1_Sub1_931 == null) {
				aClass30_Sub2_Sub1_Sub1_931 = class9.sprite1;
				aClass30_Sub2_Sub1_Sub1_932 = class9.sprite2;
			}
			if (aBoolean1047) {
				class9.sprite1 = aClass30_Sub2_Sub1_Sub1_931;
				return;
			} else {
				class9.sprite1 = aClass30_Sub2_Sub1_Sub1_932;
				return;
			}
		}
		if (j == 600) {
			class9.message = reportAbuseInput;
			if (loopCycle % 20 < 10) {
				class9.message += "|";
				return;
			} else {
				class9.message += " ";
				return;
			}
		}
		if (j == 613)
			if (localPlayer.hasRightsLevel(1)) {
				if (canMute) {
					class9.textColor = 0xff0000;
					// class9.message =
					// "Moderator option: Mute player for 48 hours: <ON>";
				} else {
					class9.textColor = 0xffffff;
					// class9.message =
					// "Moderator option: Mute player for 48 hours: <OFF>";
				}
			} else {
				class9.message = "";
			}
		if (j == 650 || j == 655)
			if (anInt1193 != 0) {
				String s;
				if (daysSinceLastLogin == 0)
					s = "earlier today";
				else if (daysSinceLastLogin == 1)
					s = "yesterday";
				else
					s = daysSinceLastLogin + " days ago";
				class9.message = "You last logged in " + s + " from: " + Signlink.dns;
			} else {
				class9.message = "";
			}
		if (j == 651) {
			if (unreadMessages == 0) {
				class9.message = "0 unread messages";
				class9.textColor = 0xffff00;
			}
			if (unreadMessages == 1) {
				class9.message = "1 unread message";
				class9.textColor = 65280;
			}
			if (unreadMessages > 1) {
				class9.message = unreadMessages + " unread messages";
				class9.textColor = 65280;
			}
		}
		if (j == 652)
			if (daysSinceRecovChange == 201) {
				if (membersInt == 1)
					class9.message = "@yel@This is a non-members world: @whi@Since you are a member we";
				else
					class9.message = "";
			} else if (daysSinceRecovChange == 200) {
				class9.message = "You have not yet set any password recovery questions.";
			} else {
				String s1;
				if (daysSinceRecovChange == 0)
					s1 = "Earlier today";
				else if (daysSinceRecovChange == 1)
					s1 = "Yesterday";
				else
					s1 = daysSinceRecovChange + " days ago";
				class9.message = s1 + " you changed your recovery questions";
			}
		if (j == 653)
			if (daysSinceRecovChange == 201) {
				if (membersInt == 1)
					class9.message = "@whi@recommend you use a members world instead. You may use";
				else
					class9.message = "";
			} else if (daysSinceRecovChange == 200)
				class9.message = "We strongly recommend you do so now to secure your account.";
			else
				class9.message = "If you do not remember making this change then cancel it immediately";
		if (j == 654) {
			if (daysSinceRecovChange == 201)
				if (membersInt == 1) {
					class9.message = "@whi@this world but member benefits are unavailable whilst here.";
					return;
				} else {
					class9.message = "";
					return;
				}
			if (daysSinceRecovChange == 200) {
				class9.message = "Do this from the 'account management' area on our front webpage";
				return;
			}
			class9.message = "Do this from the 'account management' area on our front webpage";
		}
	}

	private void sortFriendsList() {
		for (boolean flag6 = false; !flag6;) {
			flag6 = true;
			for (int k29 = 0; k29 < friendsCount - 1; k29++)
				if (friendsNodeIDs[k29] != nodeID && friendsNodeIDs[k29 + 1] == nodeID
						|| friendsNodeIDs[k29] == 0 && friendsNodeIDs[k29 + 1] != 0) {
					int j31 = friendsNodeIDs[k29];
					friendsNodeIDs[k29] = friendsNodeIDs[k29 + 1];
					friendsNodeIDs[k29 + 1] = j31;
					String s10 = friendsList[k29];
					friendsList[k29] = friendsList[k29 + 1];
					friendsList[k29 + 1] = s10;
					long l32 = friendsListAsLongs[k29];
					friendsListAsLongs[k29] = friendsListAsLongs[k29 + 1];
					friendsListAsLongs[k29 + 1] = l32;
					needDrawTabArea = true;
					flag6 = false;
				}
		}
	}

	private void drawSplitPrivateChat() {
		if (!splitPrivateChat)
			return;
		RSFont font = newRegularFont;

		int listPosition = 0;
		boolean update = anInt1104 != 0;
		boolean broadcast = BroadcastManager.isDisplayed();
		listPosition = update && broadcast ? 2 : broadcast || update ? 1 : 0;
		int xPosition = 0;
		int yPosition = 0;
		for (int j = 0; j < 100; j++) {
			if (chatMessages[j] != null) {
				int k = chatMessages[j].type;
				String s = chatMessages[j].getName();
				if(s == null)
					continue;
				List<Integer> crowns = new ArrayList<>();
				while (s.startsWith("@cr")) {
					String s2 = s.substring(3, s.length());
					int index = s2.indexOf("@");
					if (index != -1) {
						s2 = s2.substring(0, index);
						crowns.add(Integer.parseInt(s2));
						s = s.substring(4 + s2.length());
					}
				}
				if ((k == 3 || k == 7) && (k == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
					yPosition = (!isResized() ? 330 : canvasHeight - 173) - listPosition * 13;
					xPosition = !isResized() ? 5 : 0;
					font.drawBasicString("From", xPosition, yPosition, 65535, 0);
					xPosition += font.getTextWidth("From ");
					if (!crowns.isEmpty()) {
						for (int crown : crowns) {
							for (int right = 0; right < modIcons.length; right++) {
								if (right == (crown - 1) && modIcons[right] != null) {
									System.out.println("Split PM crown = " + crown);
									Sprite modcon = handleIcon2(crown, xPosition, yPosition, 0, +2);
									if (modcon != null) {
										xPosition += modcon.myWidth;
									}
									xPosition += 2;
									//yPosition -= 2;
									break;
								}
							}
						}
					}
					font.drawBasicString(s + ": " + chatMessages[j].getMessage(), xPosition, yPosition, 65535, 0);
					if (++listPosition >= 5)
						return;
				}
				if (k == 5 && privateChatMode < 2) {
					yPosition = (!isResized() ? 330 : canvasHeight - 173) - listPosition * 13;
					xPosition = !isResized() ? 5 : 0;
					font.drawBasicString(chatMessages[j].getMessage(), xPosition, yPosition, 65535, 0);
					if (++listPosition >= 5)
						return;
				}
				if (k == 6 && privateChatMode < 2) {
					yPosition = (!isResized() ? 330 : canvasHeight - 173) - listPosition * 13;
					xPosition = !isResized() ? 5 : 0;
					font.drawBasicString("To " + s + ": " + chatMessages[j].getMessage(), xPosition, yPosition, 65535, 0);
					if (++listPosition >= 5)
						return;
				}
			}
		}
	}

	private void buildSplitPrivateChatMenu() {
		if (!splitPrivateChat)
			return;
		int i = 0;
		if (anInt1104 != 0)
			i = 1;
		for (int j = 0; j < 100; j++) {
			if (chatMessages[j] != null) {
				int k = chatMessages[j].type;
				String s = chatMessages[j].getName();
				if(s == null)
					continue;
				while (s.startsWith("@cr")) {
					String s2 = s.substring(3, s.length());
					int index = s2.indexOf("@");
					if (index != -1) {
						s2 = s2.substring(0, index);
						s = s.substring(4 + s2.length());
					}
				}
				if ((k == 3 || k == 7)
						&& (k == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
					int yPosition = (!isResized() ? 330 : canvasHeight - 173) - i * 13;
					int messageLength = Math.min(475, newRegularFont.getTextWidth("From: " + s + chatMessages[j]) + 2);
					if (MouseHandler.mouseX >= 0 && MouseHandler.mouseX <= messageLength) {
						if (MouseHandler.mouseY >= yPosition - 10 && getMouseY() <= yPosition + 3) {
							if (messageLength > 450)
								messageLength = 450;
							if (localPlayer.hasRightsLevel(1)) {
								MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
										.setOption("Report abuse ")
										.setTarget("@whi@" + s)
										.setType(2606);
								menuManager.addEntry(menuEntry);
								setMenuOptionCount(getMenuOptionCount() + 1);
								callbacks.post(new MenuEntryAdded(menuEntry));
							}
							MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
									.setOption("Add ignore ")
									.setTarget("@whi@" + s)
									.setType(2042);
							menuManager.addEntry(menuEntry);
							setMenuOptionCount(getMenuOptionCount() + 1);
							callbacks.post(new MenuEntryAdded(menuEntry));
							menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
									.setOption("Reply to ")
									.setTarget("@whi@" + s)
									.setType(2639);
							menuManager.addEntry(menuEntry);
							setMenuOptionCount(getMenuOptionCount() + 1);
							callbacks.post(new MenuEntryAdded(menuEntry));
							menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
									.setOption("Add friend ")
									.setTarget("@whi@" + s)
									.setType(2337);
							menuManager.addEntry(menuEntry);
							setMenuOptionCount(getMenuOptionCount() + 1);
							callbacks.post(new MenuEntryAdded(menuEntry));
						}
					}
					if (++i >= 5)
						return;
				}
				if ((k == 5 || k == 6) && privateChatMode < 2 && ++i >= 5)
					return;
			}
		}
	}

	private void buildBroadcasts() {

		int i = 0;

		if (BroadcastManager.isDisplayed()) {

			Broadcast bc = BroadcastManager.getCurrentBroadcast();

			if (bc == null)
				return;

			boolean update = isServerUpdating();

			int yPosition = (!isResized() ? 330 : canvasHeight - 173) - i * 13;
			yPosition -= update ? 13 : 0;
			int messageLength = newRegularFont.getTextWidth(bc.getDecoratedMessage());
			if (openInterfaceID <= 0 && MouseHandler.mouseX >= 0 && MouseHandler.mouseX <= messageLength) {
				if (MouseHandler.mouseY >= yPosition - 10 && getMouseY() <= yPosition) {
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption("Dismiss")
							.setType(11_115);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
					if (bc.type != 0) {
						menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(bc.type == 1 ? "Open URL" : bc.type == 2 ? "Teleport To Location" : null)
								.setType(bc.type == 1 ? 11_111 : 11_112);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
				}
			}
			if (++i >= 1)
				return;
		}
	}

	public void timeOutHasLoggedMessages() {
		for (int i = 0; i < chatMessages.length; i++) {
			if (chatMessages[i] != null && chatMessages[i].getMessage() != null && chatMessages[i].getMessage().contains("has logged") && System.currentTimeMillis() - chatMessages[i].chatTime >= 15_000) {
				chatMessages[i].setValue("");
				shiftMessages();
			}
		}
	}

	public void pushMessage(String s) {
		pushMessage(s, 0, "");
	}

	public static long getTime() {
		long time = ZonedDateTime.now(ZoneId.systemDefault()).toEpochSecond();
		return time;
	}
	public ChatMessage pushMessage(ChatMessage chatMessage) {
		return pushMessage(chatMessage.getMessage(), chatMessage.type, chatMessage.getName());
	}
	public ChatMessage pushMessage(String s, int i, String s1) {
		if (i == 0 && dialogID != -1) {
			clickToContinueString = s;
			clickMode3 = 0;
		}
		if (backDialogID == -1)
			inputTaken = true;
		for (int j = 499; j > 0; j--) {
				chatMessages[j] = chatMessages[j - 1];
		}
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.type = i;
		chatMessage.setName(s1);
		chatMessage.setValue(s);
		chatMessage.chatTime = System.currentTimeMillis();
		chatMessages[0] = chatMessage;


		/*Interpreter_intStackSize = 2;
		intStack.add(0, 0);//1 for split PM
		intStack.add(1, chatMessage.uid);
		stringStackSize = 4;
		stringStack.add(0, "");//channel
		stringStack.add(1, chatMessage.getName() == null ? "" : chatMessage.getName());
		stringStack.add(2, chatMessage.getValue());
		stringStack.add(3, "");
		ScriptCallbackEvent scriptCallbackEvent = new ScriptCallbackEvent();
		scriptCallbackEvent.setEventName("chatMessageBuilding");*/
		//callbacks.post(scriptCallbackEvent);
		//chatMessage.setValue(stringStack.get(stringStackSize - 2));
		net.runelite.api.events.ChatMessage chatMessageEvent = new net.runelite.api.events.ChatMessage();
		chatMessageEvent.setType(ChatMessageType.of(i));
		chatMessageEvent.setMessage(s);
		callbacks.post(chatMessageEvent);

		return chatMessages[0];
	}

	public void shiftMessages() {
		int index = 0;

		ChatMessage[] chatMessages = new ChatMessage[500];

		for (int i = 0; i < chatMessages.length; i++) {
			if (this.chatMessages[i] != null && this.chatMessages[i].getMessage() != null && !this.chatMessages[i].getMessage().isEmpty()) {
				chatMessages[index] = this.chatMessages[i];
				index++;
			}
		}
		this.chatMessages = chatMessages;
	}

	public void setNorth() {
		cameraOffsetX = 0;
		cameraOffsetY = 0;
		viewRotationOffset = 0;
		viewRotation = 0;
		minimapRotation = 0;
		minimapZoom = 0;
	}

	public void processTabClick() {
		if (clickMode3 == 1) {
			if (!isResized()) {
				int x = 516;
				int y = 168;
				int[] points = new int[] { 3, 41, 74, 107, 140, 173, 206, 244 };
				for (int index = 0; index < points.length - 1; index++) {
					if (MouseHandler.saveClickX >= x + points[index] && MouseHandler.saveClickX <= x + points[index + 1]) {
						if (MouseHandler.saveClickY >= y && MouseHandler.saveClickY <= y + 36) {
							if (Client.tabInterfaceIDs[index] != -1) {
								Client.tabID = index;
								Client.needDrawTabArea = true;
								Client.tabAreaAltered = true;
							}
						} else if (MouseHandler.saveClickY >= y + 298 && MouseHandler.saveClickY <= y + 36 + 298) {
							if (Client.tabInterfaceIDs[index + 7] != -1) {
								if (index + 7 == 13) {
									stream.createFrame(185);
									stream.writeWord(21406);
								}
								Client.tabID = index + 7;
								Client.needDrawTabArea = true;
								Client.tabAreaAltered = true;
							}
						}
						Client.needDrawTabArea = true;
						Client.tabAreaAltered = true;
					}
				}
			} else {
				int x = Client.canvasWidth - (stackTabs() ? 241 : 462);
				int y = Client.canvasHeight - (stackTabs() ? 332 : 36);
				for (int index = 0; index < 14; index++) {
					if (MouseHandler.saveClickX >= x && MouseHandler.saveClickX <= x + (stackTabs() ? 36 : 33)) {
						if (MouseHandler.saveClickY >= y && MouseHandler.saveClickY <= y + 38) {
							if (Client.tabInterfaceIDs[index] != -1) {
								if (Client.tabID == index) {
									showTabComponents = !showTabComponents;
								} else if (!showTabComponents) {
									showTabComponents = !showTabComponents;
								}
								Client.tabID = index;
								Client.needDrawTabArea = true;
								Client.tabAreaAltered = true;
							}
						} else if (stackTabs() && MouseHandler.saveClickY >= y + 294 && MouseHandler.saveClickY <= y + 294 + 38) {
							if (Client.tabInterfaceIDs[index + 7] != -1) {
								Client.tabID = index + 7;
								Client.needDrawTabArea = true;
								Client.tabAreaAltered = true;
							}
						}
						if(stackTabs())
							showTabComponents = true;
						variousSettings[83566] = showTabComponents ? 1 : 0;
					}
					x += (stackTabs() ? 36 : 33);
				}
			}
		}
	}



	public String getDocumentBaseHost() {
		if (Signlink.mainapp != null) {
			return Signlink.mainapp.getDocumentBase().getHost().toLowerCase();
		}
		return "";
	}

	private void refreshMinimap(Sprite sprite, int j, int k) {
		int l = k * k + j * j;
		if (l > 4225 && l < 0x15f90) {
			int i1 = viewRotation + minimapRotation & 0x7ff;
			int j1 = Model.SINE[i1];
			int k1 = Model.COSINE[i1];
			j1 = (j1 * 256) / (minimapZoom + 256);
			k1 = (k1 * 256) / (minimapZoom + 256);
			int l1 = j * j1 + k * k1 >> 16;
			int i2 = j * k1 - k * j1 >> 16;
			double d = Math.atan2(l1, i2);
		} else {
			markMinimap(sprite, k, j);
		}
	}

	private void rightClickChatButtons() {
		if (fullscreenInterfaceID != -1) {
			return;
		}
		if (MouseHandler.mouseY >= (!isResized() ? 482 : canvasHeight - 22)
				&& MouseHandler.mouseY <= (!isResized() ? 503 : canvasHeight)) {

			if (MouseHandler.mouseX >= 5 && MouseHandler.mouseX <= 61) {
				MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
						.setOption("View All")
						.setType(99);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));
				menuActionRow = 2;
			} else if (MouseHandler.mouseX >= 71 && MouseHandler.mouseX <= 127) {
				MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
						.setOption("Switch tab")
						.setType(998);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(2)
						.setOption("Configure filter")
						.setType(4005);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(3)
						.setOption("@yel@Game: @whi@Filter")
						.setType(1005);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));
				menuActionRow = 4;
			} else if (MouseHandler.mouseX >= 137 && MouseHandler.mouseX <= 193) {
				MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
						.setOption("@bl3@Setup your autochat")
						.setType(496);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(2)
						.setOption("@gre@Filter public chat")
						.setType(495);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(3)
						.setOption("@yel@Public: @whi@Clear history")
						.setType(497);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(4)
						.setOption("@yel@Public: @whi@Hide")
						.setType(997);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(5)
						.setOption("@yel@Public: @whi@Off")
						.setType(996);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(6)
						.setOption("@yel@Public: @whi@Show friends")
						.setType(995);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(7)
						.setOption("@yel@Public: @whi@Show standard")
						.setType(994);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(8)
						.setOption("@yel@Public: @whi@Show autochat")
						.setType(494);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(9)
						.setOption("Switch tab")
						.setType(993);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuActionRow = 10;
			} else if (MouseHandler.mouseX >= 203 && MouseHandler.mouseX <= 259) {
				MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
						.setOption("@yel@Private: @whi@Clear history")
						.setType(1006);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(2)
						.setOption("@yel@Private: @whi@Off")
						.setType(992);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(3)
						.setOption("@yel@Private: @whi@Show friends")
						.setType(991);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(4)
						.setOption("@yel@Private: @whi@Show all")
						.setType(990);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(5)
						.setOption("Switch tab")
						.setType(989);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuActionRow = 6;
			} else if (MouseHandler.mouseX >= 269 && MouseHandler.mouseX <= 325) {
				MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
						.setOption("@yel@Clan: @whi@Off")
						.setType(1003);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(2)
						.setOption("@yel@Clan: @whi@Show friends")
						.setType(1002);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(3)
						.setOption("@yel@Clan: @whi@Show all")
						.setType(1001);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(4)
						.setOption("Switch tab")
						.setType(1000);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));
				menuActionRow = 5;
			} else if (MouseHandler.mouseX >= 335 && MouseHandler.mouseX <= 391) {
				MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
						.setOption("@yel@Trade: @whi@Off")
						.setType(987);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(2)
						.setOption("@yel@Trade: @whi@Show friends")
						.setType(986);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(3)
						.setOption("@yel@Trade: @whi@Show all")
						.setType(985);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));

				menuEntry = (MenuEntry) new MenuEntry(4)
						.setOption("Switch tab")
						.setType(984);
				menuManager.addEntry(menuEntry);
				callbacks.post(new MenuEntryAdded(menuEntry));
				menuActionRow = 5;
			}
		}
	}

	private boolean checkMainScreenBounds() {
		if (checkBounds(0, canvasWidth - (stackTabs() ? 244 : 462), canvasHeight - (stackTabs() ? 332 : 37),
				canvasWidth, canvasHeight)) {
			return false;
		}
		if (checkBounds(0, canvasWidth - 225, 0, canvasWidth, 170)) {
			return false;
		}
		if (checkBounds(0, canvasWidth - 204, canvasHeight - (stackTabs() ? 73 : 37) - 275, canvasWidth,
				canvasHeight)) {
			return false;
		}
		if (checkBounds(0, canvasWidth - 204, canvasHeight - (stackTabs() ? 73 : 37) - 275, canvasWidth,
				canvasHeight) && !hideChatArea) {
			return false;
		}
		if (checkBounds(0, 0, canvasHeight - 168, 516, canvasHeight) && !hideChatArea) {
			return false;
		}
		return true;
	}

	public static boolean showChatComponents = true;
	public static boolean showTabComponents = true;
	public static boolean changeTabArea = false;
	public static boolean changeChatArea = false;

	public boolean getMousePositions() {
		if (mouseInRegion(canvasWidth - (canvasWidth <= 1000 ? 240 : 420),
				canvasHeight - (canvasWidth <= 1000 ? 90 : 37), canvasWidth, canvasHeight)) {
			return false;
		}
		if (showChatComponents) {
			if (changeChatArea) {
				if (MouseHandler.mouseX > 0 && MouseHandler.mouseX < 494
						&& MouseHandler.mouseY > canvasHeight - 175
						&& MouseHandler.mouseY < canvasHeight) {
					return true;
				} else {
					if (MouseHandler.mouseX > 494 && MouseHandler.mouseX < 515
							&& MouseHandler.mouseY > canvasHeight - 175
							&& MouseHandler.mouseY < canvasHeight) {
						return false;
					}
				}
			} else if (!changeChatArea) {
				if (MouseHandler.mouseX > 0 && MouseHandler.mouseX < 519
						&& MouseHandler.mouseY > canvasHeight - 175
						&& MouseHandler.mouseY < canvasHeight) {
					return false;
				}
			}
		}
		if (mouseInRegion(canvasWidth - 216, 0, canvasWidth, 172)) {
			return false;
		}
		if (!changeTabArea) {
			if (MouseHandler.mouseX > 0 && MouseHandler.mouseY > 0 && MouseHandler.mouseY < canvasWidth
					&& MouseHandler.mouseY < canvasHeight) {
				if (MouseHandler.mouseX >= canvasWidth - 244 && MouseHandler.mouseY >= canvasHeight - 335) {
					return false;
				}
				return true;
			}
			return false;
		}
		if (showTabComponents) {
			if (canvasWidth > 1000) {
				if (MouseHandler.mouseX >= canvasWidth - 420 && MouseHandler.mouseX <= canvasWidth
						&& MouseHandler.mouseY >= canvasHeight - 37
						&& MouseHandler.mouseY <= canvasHeight
						|| MouseHandler.mouseX > canvasWidth - 225 && MouseHandler.mouseX < canvasWidth
						&& MouseHandler.mouseY > canvasHeight - 37 - 274
						&& MouseHandler.mouseY < canvasHeight) {
					return false;
				}
			} else {
				if (MouseHandler.mouseX >= canvasWidth - 210 && MouseHandler.mouseX <= canvasWidth
						&& MouseHandler.mouseY >= canvasHeight - 74
						&& MouseHandler.mouseY <= canvasHeight
						|| MouseHandler.mouseX > canvasWidth - 225 && MouseHandler.mouseX < canvasWidth
						&& MouseHandler.mouseY > canvasHeight - 74 - 274
						&& MouseHandler.mouseY < canvasHeight) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkBounds(int type, int x1, int y1, int x2, int y2) {
		if (type == 0)
			return getMouseX() >= x1 && getMouseX() <= x2 && getMouseY() >= y1 && getMouseY() <= y2;
		else
			return Math.pow((x1 + type - x2), 2) + Math.pow((y1 + type - y2), 2) < Math.pow(type, 2);
	}

	public boolean isFullscreenInterface(int id) {
		if(id == -1) {
			return false;
		}
		return false;
	}

	public Point getOnScreenWidgetOffsets() {
		boolean newTab = true;
		int w = 512, h = 334;
		int x = (canvasWidth / 2) - (newTab ? 286 : 256);
		int y = (canvasHeight / 2) - 169;

		if (x + 65656 > (canvasWidth - 225)) {
			x = x - 90;
			if (x < 0) {
				x = 0;
			}
		}
		if (y + 565656 > (canvasHeight - 192)) {
			y = y - 83;
			if (y < 0) {
				y = 0;
			}
		}
		return new Point(x, y);
	}

	public boolean stackTabs() {
		return !(canvasWidth >= 1000);
	}

	private void processRightClick() {
		if (loggedIn) {
			if (activeInterfaceType != 0)
				return;
			menuManager.addCancel();
			if (isResized()) {
				if (fullscreenInterfaceID != -1) {
					anInt886 = 0;
					anInt1315 = 0;
					buildInterfaceMenu((canvasWidth / 2) - 765 / 2,
							RSInterface.interfaceCache[fullscreenInterfaceID], MouseHandler.mouseX,
							(canvasHeight / 2) - 503 / 2, MouseHandler.mouseY, 0);
					if (anInt886 != anInt1026) {
						anInt1026 = anInt886;
					}
					if (anInt1315 != anInt1129) {
						anInt1129 = anInt1315;
					}
					return;
				}
			}
			buildSplitPrivateChatMenu();
			buildBroadcasts();
			anInt886 = 0;
			anInt1315 = 0;

			if (!isResized()) {
				if (isFullscreenInterface(openInterfaceID)) {

					if(!isResized()) {
						buildInterfaceMenu(0, RSInterface.interfaceCache[openInterfaceID], MouseHandler.mouseX , 0, MouseHandler.mouseY - 4,0);
					} else {
						buildInterfaceMenu((int) getOnScreenWidgetOffsets().getX(), RSInterface.interfaceCache[openInterfaceID], MouseHandler.mouseX, (int) getOnScreenWidgetOffsets().getY(), MouseHandler.mouseY,0);
					}
				} else if (getMouseX() > 0 && getMouseY() > 0 && getMouseX() < 516 && getMouseY() < 343) {
					if (openInterfaceID != -1) {
						buildInterfaceMenu(0, RSInterface.interfaceCache[openInterfaceID], getMouseX(), 0, getMouseY(), 0);
					} else {
						build3dScreenMenu();
					}
				}
			} else {
				if (checkMainScreenBounds()) {
					int interfaceX = (canvasWidth / 2) - 256 - 99;
					int interfaceY = (canvasHeight / 2) - 167 - 63;
					if (openInterfaceID != -1 && openInterfaceID != 44900) {
						buildInterfaceMenu(interfaceX, RSInterface.interfaceCache[openInterfaceID],
								getMouseX(), interfaceY, getMouseY(), 0);
					}
					if (openInterfaceID == -1 || getMouseX() < interfaceX || getMouseY() < interfaceY || getMouseX() > interfaceX + 516 || getMouseY() > interfaceY + 338) {
						build3dScreenMenu();
					}
				}
			}

			if (anInt886 != anInt1026) {
				anInt1026 = anInt886;
			}
			if (anInt1315 != anInt1129) {
				anInt1129 = anInt1315;
			}

			anInt886 = 0;
			anInt1315 = 0;
			resetTabInterfaceHover();
			if (!isResized()) {
				if (getMouseX() > 516 && getMouseY() > 205 && getMouseX() < 765 && getMouseY() < 466) {
					if (invOverlayInterfaceID != 0) {
						buildInterfaceMenu(547, RSInterface.interfaceCache[invOverlayInterfaceID], getMouseX(), 205, getMouseY(),
								0);
					} else if (tabInterfaceIDs[tabID] != -1) {
						buildInterfaceMenu(547, RSInterface.interfaceCache[tabInterfaceIDs[tabID]], getMouseX(), 205, getMouseY(),
								0);
					}
				}
			} else {
				int y = stackTabs() ? 73 : 37;
				if (getMouseX() >canvasWidth - 197 - (stackTabs() ? 16 : 1) && getMouseY() > canvasHeight - 275 + (stackTabs() ? 42 : -1) - y + 10
						&& getMouseX() < canvasWidth - 7 && getMouseY() < canvasHeight - 37 - 5 && showTabComponents) {
					if (invOverlayInterfaceID != 0 && !isFullscreenInterface(openInterfaceID)) {
						buildInterfaceMenu(canvasWidth - 197 - (stackTabs() ? 16 : 1), RSInterface.interfaceCache[invOverlayInterfaceID],
								getMouseX(), canvasHeight - 275 + (stackTabs() ? 42 : -1) - y + 10, getMouseY(), 0);
					} else if (tabInterfaceIDs[tabID] != -1 && !isFullscreenInterface(openInterfaceID)) {
						buildInterfaceMenu(
								canvasWidth - 197 - (stackTabs() ? 16 : 1),
								RSInterface.interfaceCache[tabInterfaceIDs[tabID]],
								getMouseX(),
								canvasHeight - 275 + (stackTabs() ? 42 : -1) - y + 10,
								getMouseY(),
								0
						);
					}
				}
			}
			if (anInt886 != anInt1048) {
				needDrawTabArea = true;
				tabAreaAltered = true;
				anInt1048 = anInt886;
			}
			if (anInt1315 != anInt1044) {
				needDrawTabArea = true;
				tabAreaAltered = true;
				anInt1044 = anInt1315;
			}
			anInt886 = 0;
			anInt1315 = 0;
			/* Chat area clicking */
			if (!isResized()) {
				if (getMouseX() > 0 && getMouseY() > 338 && getMouseX() < 490 && getMouseY() < 463) {
					if (backDialogID != -1)
						buildInterfaceMenu(20, RSInterface.interfaceCache[backDialogID], getMouseX(), 358, getMouseY(), 0);
					else if (getMouseY() < 463 && getMouseX() < 490)
						buildChatAreaMenu(getMouseY() - 338);
				}
			} else {
				if (getMouseX() > 0 && getMouseY() > canvasHeight - 165 && getMouseX() < 490 && getMouseY() < canvasHeight - 40) {
					if (backDialogID != -1)
						buildInterfaceMenu(20, RSInterface.interfaceCache[backDialogID], getMouseX(),
								canvasHeight - 145, getMouseY(), 0);
					else if (getMouseY() < canvasHeight - 40 && getMouseX() < 490)
						buildChatAreaMenu(getMouseY() - (canvasHeight - 160));
				}
			}
			if (backDialogID != -1 && anInt886 != anInt1039) {
				inputTaken = true;
				anInt1039 = anInt886;
			}
			if (backDialogID != -1 && anInt1315 != anInt1500) {
				inputTaken = true;
				anInt1500 = anInt1315;
			}
			/* Enable custom right click areas */
			if (anInt886 != anInt1026)
				anInt1026 = anInt886;
			anInt886 = 0;

			rightClickChatButtons();
			processMinimapActions();
			// Reverses menu actions!
			boolean var1 = false;
			int var2;
			int var5;
			while (!var1)
			{
				var1 = true;
				for (var2 = 0; var2 < instance.getMenuOptionCount() - 1; ++var2)
				{
					if (instance.getMenuOpcodes()[var2] < 1000 && instance.getMenuOpcodes()[var2 + 1] > 1000)
					{
						sortMenuEntries(var2, var2 + 1);
						var1 = false;
					}
				}
				if (var1 && !instance.isMenuOpen())
				{
					instance.getCallbacks().post(new PostMenuSort());
				}
			}
			//if(menuManager.getMenuEntries()[0] == null || menuManager.getMenuEntries()[0].getOption() == null)
			//	shiftMenuEntriesLeft();
		}

	}

	private void processMinimapActions() {
		final boolean fixed = !isResized();
		if (fixed ? MouseHandler.mouseX >= 542 && MouseHandler.mouseX <= 579 && MouseHandler.mouseY >= 2 && MouseHandler.mouseY <= 38
				: MouseHandler.mouseX >= Client.canvasWidth - 180 && MouseHandler.mouseX <= Client.canvasWidth - 139
				&& MouseHandler.mouseY >= 0 && MouseHandler.mouseY <= 40) {

			MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
					.setOption("Look North")
					.setType(696);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));
			menuActionRow = 2;
		}
		if (counterHover && drawOrbs) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
					.setOption("Reset @or1@XP total")
					.setType(475);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));
			menuEntry = (MenuEntry) new MenuEntry(2)
					.setOption(drawExperienceCounter ? "Hide @or1@XP drops" : "Show @or1@XP drops")
					.setType(474);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));

			menuActionRow = 3;
		}
		if (worldHover) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
					.setOption("Teleports")
					.setType(852);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));
			menuActionRow = 2;
		}
		if (donatorHover) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
					.setOption("Division Pass")
					.setType(6251);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));
			menuActionRow = 2;
		}
		if (taskHover) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
					.setOption("Task Master")
					.setType(6254);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));
			menuActionRow = 2;
		}
		if (questHover) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
					.setOption("Quests")
					.setType(6255);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));
			menuActionRow = 2;
		}
		int mouseX1 = !isResized() ? 572 : canvasWidth - 175;
		int mouseX2 = !isResized() ? 600 : canvasWidth - 150;
		if (runHover) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
					.setOption("Toggle Run")
					.setType(1050);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));
			menuActionRow = 2;
		}
		if(specialHover) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
					.setOption("Use Special Attack")
					.setType(1550);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));
			menuActionRow = 2;
		}
		if (prayHover && drawOrbs) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(1)
					.setOption("Select Quick Prayers")
					.setType(1506);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));

			 menuEntry = (MenuEntry) new MenuEntry(2)
					.setOption(prayClicked ? "Turn Quick Prayers Off" : "Turn Quick Prayers On")
					.setType(1500);
			menuManager.addEntry(menuEntry);
			callbacks.post(new MenuEntryAdded(menuEntry));
			menuActionRow = 3;
		}
	}

	private int method83(int i, int j, int k) {
		int l = 256 - k;
		return ((i & 0xff00ff) * l + (j & 0xff00ff) * k & 0xff00ff00)
				+ ((i & 0xff00) * l + (j & 0xff00) * k & 0xff0000) >> 8;
	}

	public boolean missingUsername() {
		if (myUsername == null || myUsername.isEmpty()) {
			loginScreenCursorPos = 0;
			return true;
		}
		return false;
	}

	public boolean missingPassword() {
		if (getPassword() == null || getPassword().isEmpty()) {
			System.out.println("Empty password detected!");
			loginScreenCursorPos = 0;
			firstLoginMessage = "Please enter your password.";
			OpenErrorScreen();
			return true;
		}
		return false;
	}

	public boolean missingCaptchaInput() {
		if (loginScreenState == LoginScreenState.CAPTCHA && captchaInput.length() == 0 && captcha != null) {
			firstLoginMessage = "You must enter the captcha (case sensitive).";
			OpenErrorScreen();
			return true;
		}
		return false;
	}

	public boolean nameWhitespace() {
		if (myUsername != null && myUsername.startsWith(" ") || myUsername.endsWith(" ") || myUsername.contains("  ")) {
			firstLoginMessage = "Invalid username whitespace usage. Please try again.";
			OpenErrorScreen();
			return true;
		}
		return false;
	}

	public void login(String s, String s1, boolean flag) {
		if (loggedIn)
			return;
		if (Configuration.developerMode) {
			System.out.println("Attempting connection to " + server + ":" + port);
		}
		if (missingPassword()) {
			loginScreenState = LoginScreenState.LOGIN;
			return;
		}
		if (missingCaptchaInput())
			return;

		captcha = null;
		fogEnabled = false;
		fogOpacity = 0;
		firstLoginMessage = "Connecting to server..";
		Signlink.errorname = s;
		resetTabInterfaceHover();

		try {
			if(myUsername.length() < 3) {
				firstLoginMessage = "Your username must be at least 3 characters long";
				OpenErrorScreen();
				loginScreenState = LoginScreenState.LOGIN;
				return;
			}
			if (!flag) {
				drawLoginScreen(true);
			}
			setConfigButton(23103, informationFile.isRememberRoof());
			socketStream = new RSSocket(openSocket(port + portOff));
			long l = longForName(s);
			int i = (int) (l >> 16 & 31L);
			stream.currentPosition = 0;
			stream.writeUnsignedByte(14);
			stream.writeUnsignedByte(i);
			socketStream.queueBytes(2, stream.payload);
			for (int j = 0; j < 8; j++)
				socketStream.read();

			int responseCode = socketStream.read();
			int i1 = responseCode;
			if (responseCode == 0) {
				new Identity().createIdentity();
				try {
					new Identity().loadIdentity();
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				}
				socketStream.flushInputStream(inStream.payload, 8);
				inStream.currentPosition = 0;
				aLong1215 = inStream.readQWord();

				// Pow request
				stream.currentPosition = 0;
				stream.writeUnsignedByte(19);
				socketStream.queueBytes(1, stream.payload);
				int powRequestReturnCode = socketStream.read();
				if (powRequestReturnCode == 60) {
					int size = ((socketStream.read()) << 8) + (socketStream.read() & 0xff);
					socketStream.flushInputStream(inStream.payload, size);
					inStream.currentPosition = 0;
					int randomValue = inStream.readUShort();
					int difficulty = inStream.readUShort();
					String seed = inStream.readNewString();

					// Pow process
					long start = System.currentTimeMillis();
					long answer = Hash.run(randomValue, difficulty, seed);
					long time = System.currentTimeMillis() - start;
					logger.debug("Took {}ms to finish POW.", time);

					// Pow check
					Buffer powStream = Buffer.create();
					powStream.writeByte(20); // Pow check opcode
					powStream.writeQWord(answer);
					this.socketStream.queueBytes(powStream.currentPosition, powStream.payload);

					int response = socketStream.read();
					if (response == 0) {
						socketStream.flushInputStream(inStream.payload, 8);
						inStream.currentPosition = 0;
						inStream.readQWord();
					}

					int ai[] = new int[4];
					ai[0] = (int) (Math.random() * 99999999D);
					ai[1] = (int) (Math.random() * 99999999D);
					ai[2] = (int) (aLong1215 >> 32);
					ai[3] = (int) aLong1215;
					stream.currentPosition = 0;
					stream.writeUnsignedByte(10);
					stream.writeDWord(ai[0]);
					stream.writeDWord(ai[1]);
					stream.writeDWord(ai[2]);
					stream.writeDWord(ai[3]);
					stream.writeDWord(Signlink.uid);
					stream.writeString(s);
					stream.writeString(s1);
					stream.writeString(captchaInput);
					stream.writeString(macAddress);
					stream.writeString(FingerPrint.getFingerprint());
					stream.doKeys();
					aStream_847.currentPosition = 0;
					if (flag)
						aStream_847.writeUnsignedByte(18);
					else
						aStream_847.writeUnsignedByte(16);
					aStream_847.writeUnsignedByte(stream.currentPosition + 36 + 1 + 1 + 2);
					aStream_847.writeUnsignedByte(255);
					aStream_847.writeWord(Configuration.CLIENT_VERSION);
					aStream_847.writeUnsignedByte(lowMem ? 1 : 0);
					for (int l1 = 0; l1 < 9; l1++)
						aStream_847.writeDWord(expectedCRCs[l1]);

					aStream_847.writeBytes(stream.payload, stream.currentPosition, 0);
					stream.encryption = new ISAACRandomGen(ai);
					for (int j2 = 0; j2 < 4; j2++)
						ai[j2] += 50;

					encryption = new ISAACRandomGen(ai);
					socketStream.queueBytes(aStream_847.currentPosition, aStream_847.payload);
					responseCode = socketStream.read();
				}
			}
			if (responseCode == 1) {
				try {
					Thread.sleep(2000L);
				} catch (Exception _ex) {
				}
				login(s, s1, flag);
				return;
			}
			if (responseCode == 2) {
				Arrays.stream(RSInterface.interfaceCache).filter(Objects::nonNull).forEach(rsi -> rsi.scrollPosition = 0);

				Preferences.getPreferences().updateClientConfiguration();
				Preferences.getPreferences().updateClientConfiguration();
				@SuppressWarnings("unused")
				int rights = socketStream.read();
				flagged = socketStream.read() == 1;
				logger.debug("Login accepted, rights={}, flagged={}", rights, flagged);
				aLong1220 = 0L;
				anInt1022 = 0;
				//mouseDetection.coordsIndex = 0;

				if (!Preferences.getPreferences().musicEnabled && Preferences.getPreferences().musicVolume > 0) {
					setVolumeMusic(Preferences.getPreferences().musicVolume);
				}

				aBoolean954 = true;
				loggedIn = true;
				setGameState(GameState.LOGGING_IN);
				stream.currentPosition = 0;
				inStream.currentPosition = 0;
				incomingPacket = -1;
				dealtWithPacket = -1;
				previousPacket1 = -1;
				dealtWithPacketSize = -1;
				previousPacket2 = -1;
				previousPacketSize1 = -1;
				previousPacketSize2 = -1;
				packetSize = 0;
				anInt1009 = 0;
				anInt1104 = 0;
				anInt1011 = 0;
				hintType = 0;
				menuActionRow = 0;
				menuOpen = false;

				for (int j1 = 0; j1 < 500; j1++)
					chatMessages[j1] = null;

				itemSelected = 0;
				spellSelected = 0;
				loadingStage = 0;
				setNorth();
				setBounds();
				minimapState = 0;
				anInt985 = -1;
				destX = 0;
				destY = 0;
				playerCount = 0;
				npcCount = 0;
				for (int i2 = 0; i2 < maxPlayers; i2++) {
					players[i2] = null;
					aStreamArray895s[i2] = null;
				}

				for (int k2 = 0; k2 < 65536; k2++)
					npcs[k2] = null;

				localPlayer = players[maxPlayerCount] = new Player();
				projectiles.removeAll();
				incompleteAnimables.removeAll();
				for (int l2 = 0; l2 < 4; l2++) {
					for (int i3 = 0; i3 < 104; i3++) {
						for (int k3 = 0; k3 < 104; k3++)
							groundItems[l2][i3][k3] = null;

					}

				}

				spawns = new Deque();
				fullscreenInterfaceID = -1;
				anInt900 = 0;
				friendsCount = 0;
				dialogID = -1;
				backDialogID = -1;
				openInterfaceID = -1;
				invOverlayInterfaceID = 0;
				openWalkableWidgetID = -1;
				aBoolean1149 = false;
				tabID = 3;
				inputDialogState = 0;
				menuOpen = false;
				messagePromptRaised = false;
				clickToContinueString = null;
				anInt1055 = 0;
				aBoolean1047 = true;
				method45();
				for (int j3 = 0; j3 < 5; j3++)
					anIntArray990[j3] = 0;

				for (int l3 = 0; l3 < 6; l3++) {
					atPlayerActions[l3] = null;
					atPlayerArray[l3] = false;
				}

				anInt1175 = 0;
				anInt1134 = 0;
				anInt986 = 0;
				anInt1288 = 0;
				anInt924 = 0;
				anInt1188 = 0;
				anInt1155 = 0;
				anInt1226 = 0;
				setBounds();

				if (informationFile.isUsernameRemembered()) {
					informationFile.setStoredUsername(myUsername);
				}

				if (informationFile.isPasswordRemembered()) {
					informationFile.setStoredPassword(getPassword());
				}

				informationFile.write();
				if (!Client.instance.isResized() && getUserSettings().isStretchedMode()) {
					StretchedModeMenu.updateStretchedMode(true);
				}
				return;
			}
			if (responseCode == 3) {
				loginScreenState = LoginScreenState.LOGIN;
				if (missingPassword()) {
					return;
				}
				if (nameWhitespace()) {
					return;
				}
				firstLoginMessage = "Invalid username or password.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 4) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Your account has been disabled.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 5) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Your account is already logged in.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 6) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = Configuration.CLIENT_TITLE + " has been updated - restart the launcher!";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 7) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "This world is full.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 8) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Unable to connect.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 9) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Login limit exceeded.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 10) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Unable to connect. Bad session id.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 11) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Your username is too long.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 12) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Unknown error 12.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 13) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Too many connection attempts, please wait before trying again.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 14) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = Configuration.CLIENT_TITLE + " is currently being updated.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 15) {
				loginScreenState = LoginScreenState.LOGIN;
				loggedIn = true;
				stream.currentPosition = 0;
				inStream.currentPosition = 0;
				incomingPacket = -1;
				dealtWithPacket = -1;
				previousPacket1 = -1;
				previousPacket2 = -1;
				packetSize = 0;
				anInt1009 = 0;
				anInt1104 = 0;
				menuActionRow = 0;
				menuOpen = false;
				longStartTime = System.currentTimeMillis();
				return;
			}
			if (responseCode == 16) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Unknown error 16.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 17) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Unknown error 17.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 20) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Unknown error 20.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 21) {
				loginScreenState = LoginScreenState.LOGIN;
				for (int k1 = socketStream.read(); k1 >= 0; k1--) {

					drawLoginScreen(true);
					try {
						Thread.sleep(1000L);
					} catch (Exception _ex) {
					}
				}

				login(s, s1, flag);
				return;
			}
			if (responseCode == 22) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Unknown error 22.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 23) {
				loginScreenState = LoginScreenState.LOGIN;
				for (int k1 = socketStream.read(); k1 >= 0; k1--) {

					drawLoginScreen(true);
					try {
						Thread.sleep(1000L);
					} catch (Exception _ex) {
					}
				}

				login(s, s1, flag);
				return;
			}
			if (responseCode == 24) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Unknown error 24.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 25) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "Unknown error 25.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 26) {
				loginScreenState = LoginScreenState.LOGIN;
				firstLoginMessage = "An error occurred while loading your file, contact support.";
				OpenErrorScreen();
				return;
			}
			if (responseCode == 27 || responseCode == 28) {
				try {
					int length = ((socketStream.read() & 0xFF) << 8) + socketStream.read();
					byte[] captchaData = new byte[length];
					for (int i12 = 0; i12 < length; i12++)
						captchaData[i12] = (byte) socketStream.read();
					captcha = new Sprite(BufferedImages.toBufferedImage(captchaData));
					captcha.setTransparency(45, 45, 45);
					firstLoginMessage = responseCode == 27 ? "Enter the captcha (case sensitive)." : "Incorrect, enter the captcha (case sensitive).";
					synchronized (CAPTCHA_LOCK) {
						captchaInput = "";
					}
					loginScreenState = LoginScreenState.CAPTCHA;
				} catch (IOException e) {
					firstLoginMessage = "Captcha error occurred, contact staff.";
					OpenErrorScreen();
					e.printStackTrace();
				}

				return;
			}
			if (responseCode == -1) {
				if (i1 == 0) {
					if (loginFailures < 2) {
						try {
							Thread.sleep(2000L);
						} catch (Exception _ex) {
						}
						loginFailures++;
						login(s, s1, flag);
						return;
					}
				} else {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ignored) { }
					firstLoginMessage = "Error connecting to server, please try again.";
					OpenErrorScreen();
					return;
				}
			} else {
				return;
			}
		}
		catch (IOException _ex) {
			if (Configuration.developerMode)
				_ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (missingPassword()) {
			loginScreenState = LoginScreenState.LOGIN;
			return;
		}
		loginScreenState = LoginScreenState.LOGIN;
		logger.debug("Went to end of login method.");
		firstLoginMessage = "Updates are incoming... Check our discord!";
		OpenErrorScreen();
	}


	// Original signature int clickType, int j, int k, int i1, int localY, int k1, int l1, int i2, int localX, boolean flag, int k2
	private boolean doWalkTo(int clickType, int localX, int localY, int j, int k, int i1, int k1, int l1, int i2, boolean flag, int k2) {
		byte byte0 = 104;
		byte byte1 = 104;
		for (int l2 = 0; l2 < byte0; l2++) {
			for (int i3 = 0; i3 < byte1; i3++) {
				anIntArrayArray901[l2][i3] = 0;
				anIntArrayArray825[l2][i3] = 0x5f5e0ff;
			}
		}
		int j3 = localX;
		int k3 = localY;
		anIntArrayArray901[localX][localY] = 99;
		anIntArrayArray825[localX][localY] = 0;
		int l3 = 0;
		int i4 = 0;
		bigX[l3] = localX;
		bigY[l3++] = localY;
		boolean flag1 = false;
		int j4 = bigX.length;
		int ai[][] = collisionMaps[plane].clipData;
		while (i4 != l3) {
			j3 = bigX[i4];
			k3 = bigY[i4];
			i4 = (i4 + 1) % j4;
			if (j3 == k2 && k3 == i2) {
				flag1 = true;
				break;
			}
			if (i1 != 0) {
				if ((i1 < 5 || i1 == 10) && collisionMaps[plane].method219(k2, j3, k3, j, i1 - 1, i2)) {
					flag1 = true;
					break;
				}
				if (i1 < 10 && collisionMaps[plane].method220(k2, i2, k3, i1 - 1, j, j3)) {
					flag1 = true;
					break;
				}
			}
			if (k1 != 0 && k != 0 && collisionMaps[plane].atObject(i2, k2, j3, k, l1, k1, k3)) {
				flag1 = true;
				break;
			}
			int l4 = anIntArrayArray825[j3][k3] + 1;
			if (j3 > 0 && anIntArrayArray901[j3 - 1][k3] == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3] = 2;
				anIntArrayArray825[j3 - 1][k3] = l4;
			}
			if (j3 < byte0 - 1 && anIntArrayArray901[j3 + 1][k3] == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3] = 8;
				anIntArrayArray825[j3 + 1][k3] = l4;
			}
			if (k3 > 0 && anIntArrayArray901[j3][k3 - 1] == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 - 1] = 1;
				anIntArrayArray825[j3][k3 - 1] = l4;
			}
			if (k3 < byte1 - 1 && anIntArrayArray901[j3][k3 + 1] == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 + 1] = 4;
				anIntArrayArray825[j3][k3 + 1] = l4;
			}
			if (j3 > 0 && k3 > 0 && anIntArrayArray901[j3 - 1][k3 - 1] == 0 && (ai[j3 - 1][k3 - 1] & 0x128010e) == 0
					&& (ai[j3 - 1][k3] & 0x1280108) == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 - 1] = 3;
				anIntArrayArray825[j3 - 1][k3 - 1] = l4;
			}
			if (j3 < byte0 - 1 && k3 > 0 && anIntArrayArray901[j3 + 1][k3 - 1] == 0
					&& (ai[j3 + 1][k3 - 1] & 0x1280183) == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0
					&& (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 - 1] = 9;
				anIntArrayArray825[j3 + 1][k3 - 1] = l4;
			}
			if (j3 > 0 && k3 < byte1 - 1 && anIntArrayArray901[j3 - 1][k3 + 1] == 0
					&& (ai[j3 - 1][k3 + 1] & 0x1280138) == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0
					&& (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 + 1] = 6;
				anIntArrayArray825[j3 - 1][k3 + 1] = l4;
			}
			if (j3 < byte0 - 1 && k3 < byte1 - 1 && anIntArrayArray901[j3 + 1][k3 + 1] == 0
					&& (ai[j3 + 1][k3 + 1] & 0x12801e0) == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0
					&& (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 + 1] = 12;
				anIntArrayArray825[j3 + 1][k3 + 1] = l4;
			}
		}
		anInt1264 = 0;
		if (!flag1) {
			if (flag) {
				int i5 = 100;
				for (int k5 = 1; k5 < 2; k5++) {
					for (int i6 = k2 - k5; i6 <= k2 + k5; i6++) {
						for (int l6 = i2 - k5; l6 <= i2 + k5; l6++)
							if (i6 >= 0 && l6 >= 0 && i6 < 104 && l6 < 104 && anIntArrayArray825[i6][l6] < i5) {
								i5 = anIntArrayArray825[i6][l6];
								j3 = i6;
								k3 = l6;
								anInt1264 = 1;
								flag1 = true;
							}

					}

					if (flag1)
						break;
				}

			}
			if (!flag1)
				return false;
		}
		i4 = 0;
		bigX[i4] = j3;
		bigY[i4++] = k3;
		int l5;
		for (int j5 = l5 = anIntArrayArray901[j3][k3]; j3 != localX || k3 != localY; j5 = anIntArrayArray901[j3][k3]) {
			if (j5 != l5) {
				l5 = j5;
				bigX[i4] = j3;
				bigY[i4++] = k3;
			}
			if ((j5 & 2) != 0)
				j3++;
			else if ((j5 & 8) != 0)
				j3--;
			if ((j5 & 1) != 0)
				k3++;
			else if ((j5 & 4) != 0)
				k3--;
		}
		// if(cancelWalk) { return i4 > 0; }

		if (i4 > 0) {
			int k4 = i4;
			if (k4 > 25)
				k4 = 25;
			i4--;
			int k6 = bigX[i4];
			int i7 = bigY[i4];
			anInt1288 += k4;
			if (anInt1288 >= 92) {
				stream.createFrame(36);
				stream.writeDWord(0);
				anInt1288 = 0;
			}
			if (clickType == 0) {
				stream.createFrame(164);
				stream.writeUnsignedByte(k4 + k4 + 3);
			}
			if (clickType == 1) {
				stream.createFrame(248);
				stream.writeUnsignedByte(k4 + k4 + 3 + 14);
			}
			if (clickType == 2) {
				stream.createFrame(98);
				stream.writeUnsignedByte(k4 + k4 + 3);
			}
			stream.method433(k6 + baseX);
			destX = bigX[0];
			destY = bigY[0];
			for (int j7 = 1; j7 < k4; j7++) {
				i4--;
				stream.writeUnsignedByte(bigX[i4] - k6);
				stream.writeUnsignedByte(bigY[i4] - i7);
			}

			stream.method431(i7 + baseY);
			stream.method424(KeyHandler.keyArray[5] != 1 ? 0 : 1);
			return true;
		}
		return clickType != 1;
	}


	public void updateNpcState(Buffer stream) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			Npc npc = npcs[k];
			int l = stream.readUShort();
			if((l & 0x100) != 0)
			{
				npc.anInt1543 = stream.readUnsignedByte();
				npc.anInt1545 = stream.readUnsignedByte();
				npc.anInt1544 = stream.readUnsignedByte();
				npc.anInt1546 = stream.readUnsignedByte();
				npc.initiate_movement = stream.readUShort() + loopCycle;
				npc.cease_movement = stream.readUShort() + loopCycle;
				npc.forceMovementDirection = stream.readUnsignedByte();
				npc.method446();
			}
			if ((l & 0x10) != 0) {
				int i1 = stream.method434();
				if (i1 == 65535)
					i1 = -1;
				int i2 = stream.readUnsignedByte();
				if (i1 == npc.primarySeqID && i1 != -1) {
					int l2 = SeqDefinition.get(i1).replyMode;
					if (l2 == 1) {
						npc.primarySeqFrame = 0;
						npc.primarySeqCycle = 0;
						npc.primarySeqDelay = i2;
						npc.animationLoops = 0;
					}
					if (l2 == 2)
						npc.animationLoops = 0;
				} else if (i1 == -1 || npc.primarySeqID == -1
						|| SeqDefinition.get(i1).forcedPriority >= SeqDefinition.get(npc.primarySeqID).forcedPriority) {
					npc.primarySeqID = i1;
					npc.primarySeqFrame = 0;
					npc.primarySeqCycle = 0;
					npc.primarySeqDelay = i2;
					npc.animationLoops = 0;
					npc.remaining_steps = npc.smallXYIndex;
				}
				AnimationChanged animationChanged = new AnimationChanged();
				animationChanged.setActor(npc);
				callbacks.post(animationChanged);
			}
			if ((l & 8) != 0) {
				int damage = stream.readInt();
				int j2 = stream.readByte();
				int source = stream.readInt();
				npc.updateHitData(j2, damage, loopCycle, source);
				Hitsplat hitSplat = new Hitsplat(Hitsplat.HitsplatType.fromInteger(j2), damage, loopCycle + 70);
				Actor actor = null;
				HitsplatApplied hitsplatApplied = new HitsplatApplied();
				if(source > 32768) {
					if(source - 32768 == localPlayer.playerId)
						actor = localPlayer;
					else
						actor = players[source - 32768];
				} else if(source == localPlayer.playerId) {
					actor = localPlayer;
				}
				hitsplatApplied.setDamager(actor);
				hitsplatApplied.setHitsplat(hitSplat);
				hitsplatApplied.setActor(npc);
				Client.instance.getCallbacks().post(hitsplatApplied);
				npc.loopCycleStatus = loopCycle + 300;
				npc.currentHealth = stream.readInt();
				npc.maxHealth = stream.readInt();
				if (npc.currentHealth <= 0) {
					npc.setDead(true);
					callbacks.postDeferred(new ActorDeath(npc));
				} else {
					npc.setDead(false);
				}
			}
			if ((l & 0x80) != 0) {
				int graphicsReceived = stream.readUShort();
				for(int i = 0; i < graphicsReceived; i++) {
					npc.graphicId = stream.readUShort();
					int k1 = stream.readDWord();
					npc.graphicHeight = k1 >> 16;
					npc.graphic_cycle = loopCycle + (k1 & 0xffff);
					npc.graphicFrame = 0;
					npc.currentAnimationTimeRemaining = 0;
					if (npc.graphic_cycle > loopCycle)
						npc.graphicFrame = -1;
					if (npc.graphicId == 65535)
						npc.graphicId = -1;
					npc.createSpotAnim(npc.graphicId, npc.graphicId, npc.graphicHeight, (k1 & 0xffff));
				}
			}
			if ((l & 0x20) != 0) {
				npc.interactingEntity = stream.readUShort();
				if (npc.interactingEntity == 65535)
					npc.interactingEntity = -1;
				callbacks.post(new InteractingChanged(npc, npc.getInteracting()));
			}
			if ((l & 1) != 0) {
				String s = stream.readString();
				int j2 = -1;
				if (s.startsWith("yellow:")) {
					j2 = 0;
					s = s.substring(7);
				} else if (s.startsWith("red:")) {
					j2 = 1;
					s = s.substring(4);
				} else if (s.startsWith("green:")) {
					j2 = 2;
					s = s.substring(6);
				} else if (s.startsWith("cyan:")) {
					j2 = 3;
					s = s.substring(5);
				} else if (s.startsWith("purple:")) {
					j2 = 4;
					s = s.substring(7);
				} else if (s.startsWith("white:")) {
					j2 = 5;
					s = s.substring(6);
				} else if (s.startsWith("flash1:")) {
					j2 = 6;
					s = s.substring(7);
				} else if (s.startsWith("flash2:")) {
					j2 = 7;
					s = s.substring(7);
				} else if (s.startsWith("flash3:")) {
					j2 = 8;
					s = s.substring(7);
				} else if (s.startsWith("glow1:")) {
					j2 = 9;
					s = s.substring(6);
				} else if (s.startsWith("glow2:")) {
					j2 = 10;
					s = s.substring(6);
				} else if (s.startsWith("glow3:")) {
					j2 = 11;
					s = s.substring(6);
				}
				if (j2 >= 0) {
					npc.anInt1513 = j2;
				}
				npc.textSpoken = s;
				npc.textCycle = 150;
			}
			if ((l & 0x40) != 0) {
				int damage = stream.readInt();
				int k2 = stream.readByte();
				int source = stream.readInt();
				npc.updateHitData(k2, damage, loopCycle, source);
				Hitsplat hitSplat = new Hitsplat(Hitsplat.HitsplatType.fromInteger(k2), damage, loopCycle + 70);
				Actor actor = null;
				HitsplatApplied hitsplatApplied = new HitsplatApplied();
				if(source > 32768) {
					if(source - 32768 == localPlayer.playerId)
						actor = localPlayer;
					else
						actor = players[source - 32768];
				} else if(source == localPlayer.playerId) {
					actor = localPlayer;
				}
				hitsplatApplied.setDamager(actor);
				hitsplatApplied.setHitsplat(hitSplat);
				hitsplatApplied.setActor(npc);
				Client.instance.getCallbacks().post(hitsplatApplied);
				npc.loopCycleStatus = loopCycle + 300;
				npc.currentHealth = stream.readInt();
				npc.maxHealth = stream.readInt();
				if (npc.currentHealth <= 0) {
					npc.setDead(true);
					callbacks.postDeferred(new ActorDeath(npc));
				} else {
					npc.setDead(false);
				}
			}
			if ((l & 2) != 0) {
				npc.desc = NpcDefinition.lookup(stream.method436());
				npc.anInt1540 = npc.desc.size;
				npc.anInt1504 = npc.desc.rotationSpeed;
				npc.walkAnimIndex = npc.desc.walkingAnimation;
				npc.turn180AnimIndex = npc.desc.rotate180AnimIndex;
				npc.turn90CWAnimIndex = npc.desc.rotate90CWAnimIndex;
				npc.turn90CCWAnimIndex = npc.desc.rotate90CCWAnimIndex;
				npc.seqStandID = npc.desc.standingAnimation;
			}
			if ((l & 4) != 0) {
				npc.anInt1538 = stream.method434();
				npc.anInt1539 = stream.method434();
			}
		}
	}

	private long debugDelay;

	public void buildAtNPCMenu(NpcDefinition entityDef, int i, int j, int k) {
		if (menuActionRow >= 400)
			return;
		if (entityDef.configs != null)
			entityDef = entityDef.method161();
		if (entityDef == null)
			return;
		if (!entityDef.clickable)
			return;
		String s = entityDef.name;
		if (entityDef.combatLevel != 0)
			s = s + combatDiffColor(localPlayer.combatLevel, entityDef.combatLevel) + " (level-" + entityDef.combatLevel
					+ ")";
		if (itemSelected == 1) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
					.setOption("Use " + selectedItemName + " with@yel@")
					.setTarget("" + s)
					.setIdentifier(i)
					.setType(582)
					.setParam0(k)
					.setParam1(j);
			menuEntry.setActor(npcs[i]);
			menuManager.addEntry(menuEntry);
			setMenuOptionCount(getMenuOptionCount() + 1);
			callbacks.post(new MenuEntryAdded(menuEntry));
			return;
		}
		if (spellSelected == 1) {
			if ((spellUsableOn & 2) == 2) {
				MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
						.setOption(spellTooltip + "@yel@")
						.setTarget(s)
						.setIdentifier(i)
						.setType(413)
						.setParam0(k)
						.setParam1(j);
				menuEntry.setActor(npcs[i]);
				menuManager.addEntry(menuEntry);
				setMenuOptionCount(getMenuOptionCount() + 1);
				callbacks.post(new MenuEntryAdded(menuEntry));
			}
		} else {
			if (entityDef.actions != null) {
				for (int l = 4; l >= 0; l--)
					if (entityDef.actions[l] != null && !entityDef.actions[l].equalsIgnoreCase("attack")) {
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(entityDef.actions[l] + "@yel@")
								.setTarget(s)
								.setIdentifier(i)
								.setType(582)
								.setParam0(k)
								.setParam1(j);
						if (l == 0)
							menuEntry.setType(20);
						if (l == 1)
							menuEntry.setType(412);
						if (l == 2)
							menuEntry.setType(225);
						if (l == 3)
							menuEntry.setType(965);
						if (l == 4)
							menuEntry.setType(478);
						menuEntry.setActor(npcs[i]);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}

			}
			if (entityDef.actions != null) {
				for (int i1 = 4; i1 >= 0; i1--) {
					if (entityDef.actions[i1] != null && entityDef.actions[i1].equalsIgnoreCase("attack")) {
						char c = '\0';
						if (Configuration.npcAttackOptionPriority == 3)
							continue;
						if (Configuration.npcAttackOptionPriority == 0 && entityDef.combatLevel > localPlayer.combatLevel
								|| Configuration.npcAttackOptionPriority == 1)
							c = '\u07D0';
						MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
								.setOption(entityDef.actions[i1] + "@yel@")
								.setTarget(s)
								.setIdentifier(i)
								.setType(582)
								.setParam0(k)
								.setParam1(j);
						if (i1 == 0)
							menuEntry.setType(20 + c);
						if (i1 == 1)
							menuEntry.setType(412 + c);
						if (i1 == 2)
							menuEntry.setType(225 + c);
						if (i1 == 3)
							menuEntry.setType(965 + c);
						if (i1 == 4)
							menuEntry.setType(478 + c);
						menuEntry.setActor(npcs[i]);
						menuManager.addEntry(menuEntry);
						setMenuOptionCount(getMenuOptionCount() + 1);
						callbacks.post(new MenuEntryAdded(menuEntry));
					}
				}

			}
			String target = s;

			if (localPlayer.isAdminRights()) {
				target = s + " @whi@(" + npcs[i].getId() + ")";
			}
			MenuEntry menuEntry = new MenuEntry(menuActionRow);
			menuEntry.setOption("Examine @yel@")
					.setTarget(target)
					.setType(1025)
					.setIdentifier(i)
					.setParam0(k)
					.setParam1(j);
			menuEntry.setActor(npcs[i]);
			menuManager.addEntry(menuEntry);
			setMenuOptionCount(getMenuOptionCount() + 1);
			callbacks.post(new MenuEntryAdded(menuEntry));

			if (debugModels) {
				if (System.currentTimeMillis() - debugDelay > 1000 && entityDef.models != null) {
					String modelIds = Arrays.toString(entityDef.models);
					String regColors = Arrays.toString(entityDef.originalColors);
					String newColors = Arrays.toString(entityDef.newColors);
					int standAnim = entityDef.standingAnimation;
					int walkAnim = entityDef.walkingAnimation;
					String name = entityDef.name;
					System.out.println(name + modelIds);
					pushMessage(name + ": " + modelIds, 0, "");
					pushMessage("Reg: " + regColors, 0, "");
					pushMessage("New: " + newColors, 0, "");
					pushMessage("stand: " + standAnim, 0, "");
					pushMessage("walk: " + walkAnim, 0, "");
					debugDelay = System.currentTimeMillis();
					// menuActionName[menuActionRow] = "Examine
					// @gre@O(@whi@"+Arrays.toString(entityDef.models)+")@gre@)";
					// menuActionName[menuActionRow] = "#2
					// (@whi@"+Arrays.toString(entityDef.originalColors)+")@gre@)(@whi@"+Arrays.toString(entityDef.newColors)+")@gre@)";
				}
			}

		}
	}

	public void buildAtPlayerMenu(int i, int j, Player player, int k) {
		if (player == localPlayer)
			return;
		if (!player.visible)
			return;
		if (menuActionRow >= 400)
			return;
		String s;
		if (player.title.length() < 0)
			s = player.displayName + combatDiffColor(localPlayer.combatLevel, player.combatLevel) + " (level: "
					+ player.combatLevel + ")";
		else if (player.title.length() != 0)
			s = "@or1@" + player.title + "@whi@ " + player.displayName
					+ combatDiffColor(localPlayer.combatLevel, player.combatLevel) + " (level: " + player.combatLevel
					+ ")";
		else
			s = player.displayName + combatDiffColor(localPlayer.combatLevel, player.combatLevel) + " (level: "
					+ player.combatLevel + ")";
		if (itemSelected == 1) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
					.setOption("Use " + selectedItemName + " with ")
					.setTarget("@whi@" + s)
					.setIdentifier(j)
					.setType(491)
					.setParam0(i)
					.setParam1(k);
			menuEntry.setActor(player);
			menuManager.addEntry(menuEntry);
			setMenuOptionCount(getMenuOptionCount() + 1);
			callbacks.post(new MenuEntryAdded(menuEntry));
		} else if (spellSelected == 1) {
			if ((spellUsableOn & 8) == 8) {
				MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
						.setOption(spellTooltip)
						.setTarget("@whi@" + s)
						.setIdentifier(j)
						.setType(365)
						.setParam0(i)
						.setParam1(k);
				menuEntry.setActor(player);
				menuManager.addEntry(menuEntry);
				setMenuOptionCount(getMenuOptionCount() + 1);
				callbacks.post(new MenuEntryAdded(menuEntry));
			}
		} else {
			for (int l = 7; l >= 0; l--)
				if (atPlayerActions[l] != null) {
					MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
							.setOption(atPlayerActions[l])
							.setTarget("@whi@" + s);
					char c = '\0';
					if (atPlayerActions[l].equalsIgnoreCase("attack")) {
						if (Configuration.playerAttackOptionPriority == 3)
							continue;
						if (Configuration.playerAttackOptionPriority == 0 && player.combatLevel > localPlayer.combatLevel
								|| Configuration.playerAttackOptionPriority == 1)
							c = '\u07D0';
						if (localPlayer.team != 0 && player.team != 0)
							if (localPlayer.team == player.team)
								c = '\u07D0';
							else
								c = '\0';
					} else if (atPlayerArray[l])
						c = '\u07D0';
					if (l == 0)
						menuEntry.setType(561 + c);
					if (l == 1)
						menuEntry.setType(779 + c);
					if (l == 2)
						menuEntry.setType(27 + c);
					if (l == 3)
						menuEntry.setType(577 + c);
					if (l == 4)
						menuEntry.setType(729 + c);
					if (l == 5 && atPlayerOptions[l] <= 0) {
						if (localPlayer.hasRightsBetween(0, 4)) {
							menuEntry.setType(745 + c);
						} else {
							continue;
						}
					}
					if (atPlayerOptions[l] > 0) {
						menuEntry.setType(atPlayerOptions[l]);
					}
					menuEntry.setIdentifier(j)
							.setParam0(i)
							.setParam1(k);
					menuEntry.setActor(player);
					menuManager.addEntry(menuEntry);
					setMenuOptionCount(getMenuOptionCount() + 1);
					callbacks.post(new MenuEntryAdded(menuEntry));
				}
		}
		for (int i1 = 0; i1 < menuActionRow; i1++)
			if (menuManager.getMenuEntry(i1).getOpcode() == 516) {
				menuManager.getMenuEntry(i1).setOption("Walk here");
				menuManager.getMenuEntry(i1).setTarget("@whi@" + s);
				callbacks.post(new MenuEntryAdded(menuManager.getMenuEntry(i1)));
				return;
			}

	}

	private void method89(SpawnedObject obj) {
		long id = 0L;
		int key = -1;
		int type = 0;
		int orientation = 0;
		if (obj.group == 0) {
			id = scene.getWallObjectUid(obj.plane, obj.x, obj.y);
		}
		if (obj.group == 1) {
			id = scene.getWallDecorationUid(obj.plane, obj.x, obj.y);
		}
		if (obj.group == 2) {
			id = scene.getGameObjectUid(obj.plane, obj.x, obj.y);
		}
		if (obj.group == 3) {
			id = scene.getGroundDecorationUid(obj.plane, obj.x, obj.y);
		}
		if (id != 0) {
			key = ObjectKeyUtil.getObjectId(id);
			type = ObjectKeyUtil.getObjectType(id);
			orientation = ObjectKeyUtil.getObjectOrientation(id);
		}
		obj.getPreviousId = key;
		obj.previousType = type;
		obj.previousOrientation = orientation;
	}

	public void objectFill(final int objectId, final int x1, final int y1, final int x2, final int y2, final int type,
						   final int face, final int height) {
		// if(height == height) // dunno your height variable but refactor
		// yourself
		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				addObject(objectId, x, y, face, type, height);
			}
		}
	}

	public void addObject(int objectId, int x, int y, int face, int type, int height) {
		int mX = currentRegionX - 6;
		int mY = currentRegionY - 6;
		int x2 = x - (mX * 8);
		int y2 = y - (mY * 8);
		int i15 = 40 >> 2;
		int l17 = objectTypes[i15];
		if (y2 > 0 && y2 < 103 && x2 > 0 && x2 < 103) {
			method130(-1, objectId, face, l17, y2, type, height, x2, 0);
		}
	}


	public void load_objects() {
		/**
		 * @link addObject objectId, x, y, face, type, height
		 */

		// Carts
		addObject(7029, 1656, 3542, 0, 10, 0); // Hos
		addObject(7029, 1591, 3621, 0, 10, 0); // Shay
		addObject(7029, 1760, 3710, 0, 10, 0); // Pisc
		addObject(7029, 1254, 3548, 0, 10, 0); // Qu
		addObject(7029, 1670, 3832, 0, 10, 0); // Arc
		addObject(7029, 1518, 3732, 0, 10, 0); // love
		// Resource area

		/**
		 * @link objectFill objectId, SWX, SWY, NEX, NEY, type, face, height
		 */

	}

	public static String fontFilter() {
		if (Configuration.newFonts) {
			return "_2";
		}
		return "";
	}

	public FileArchive mediaStreamLoader;

	@Override
	protected void startUp() {

		setGameState(GameState.STARTING);
		SettingsManager.loadSettings();
		ClientScripts.load();

		drawLoadingText(5, "Starting up...");

		getDocumentBaseHost();
		variousSettings[304] = 1;
		if (Signlink.cache_dat != null) {
			for (int i = 0; i < 5; i++)
				decompressors[i] = new Decompressor(Signlink.cache_dat, Signlink.cache_idx[i], i + 1);
		}

		new CacheDownloader(this).downloadCache();
//		CacheDownloader cacheDownloader = new CacheDownloader(
//				Signlink.getCacheDirectory(),
//				"https://arkcanestorage.b-cdn.net/cache/",
//				true,
//				false,
//				new Progress() {
//			@Override
//			public void update(int progress, @NotNull String message) {
//				drawLoadingText(progress, message);
//			}
//		});
//
//		cacheDownloader.awaitCompletion();

		OSRSCacheLoader.init();

		SpriteLoader1.loadSprites();
		cacheSprite1 = SpriteLoader1.sprites;
		SpriteLoader1.sprites = null;

		SpriteLoader2.loadSprites();
		cacheSprite2 = SpriteLoader2.sprites;
		SpriteLoader2.sprites = null;

		SpriteLoader3.loadSprites();
		cacheSprite3 = SpriteLoader3.sprites;
		SpriteLoader3.sprites = null;

		SpriteLoader4.loadSprites();
		cacheSprite4 = SpriteLoader4.sprites;
		SpriteLoader4.sprites = null;
		drawLoadingText(10, "Loading Packed Sprites...");
		try {
			ItemDef.load();
			ItemStats.readDefinitions();
			titleStreamLoader = streamLoaderForName(1, "title screen");
			drawLoadingText(12, "Loading Fonts...");
			smallText = new TextDrawingArea(false, "p11_full" + fontFilter(), titleStreamLoader);
			XPFONT = new TextDrawingArea(true, "q8_full" + fontFilter(), titleStreamLoader);
			aTextDrawingArea_1271 = new TextDrawingArea(false, "p12_full" + fontFilter(), titleStreamLoader);
			chatTextDrawingArea = new TextDrawingArea(false, "b12_full", titleStreamLoader);
			drawLoadingText(15, "Loading Fonts...");
			aTextDrawingArea_1273 = new TextDrawingArea(true, "q8_full" + fontFilter(), titleStreamLoader);
			newSmallFont = new RSFont(false, "p11_full" + fontFilter(), titleStreamLoader);
			newRegularFont = new RSFont(false, "p12_full" + fontFilter(), titleStreamLoader);
			newBoldFont = new RSFont(false, "b12_full" + fontFilter(), titleStreamLoader);
			newFancyFont = new RSFont(true, "q8_full" + fontFilter(), titleStreamLoader);

			for (int index = 0; index < 25; index++) {
				bubbles.add(new Bubble());
			}

			/**
			 * New fonts
			 */
			lato = new RSFont(true, "lato_full", titleStreamLoader);
			latoBold = new RSFont(true, "lato_bold_full", titleStreamLoader);
			kingthingsPetrock = new RSFont(true, "kingthings_petrock_full", titleStreamLoader);
			kingthingsPetrockLight = new RSFont(true, "kingthings_petrock_light_full", titleStreamLoader);
			drawLoadingText(20, "Loading Archives...");

			loadTitleScreen();
			createScreenImages();

			anInt720 = 20;
			/** Required for Music **/
			midi_player = new JavaMidiPlayer();
			FileArchive streamLoader = streamLoaderForName(2, "config");
			FileArchive streamLoader_1 = streamLoaderForName(3, "interface");
			FileArchive streamLoader_2 = streamLoaderForName(4, "2d graphics");
			mediaStreamLoader = streamLoader_2;
			drawLoadingText(25, "Loading Archives...");
			FileArchive streamLoader_3 = streamLoaderForName(6, "textures");
			FileArchive streamLoader_4 = streamLoaderForName(7, "chat system");
			@SuppressWarnings("unused")
			FileArchive streamLoader_5 = streamLoaderForName(8, "sound effects");
			byte[] bytes = streamLoader_5.readFile("sounds.dat");
			Buffer buffer = new Buffer(bytes);

			SoundEffects.unpack(buffer);
			drawLoadingText(25, "Loading Scene...");
			tileFlags = new byte[4][104][104];
			tileHeights = new int[4][105][105];
			scene = new SceneGraph(tileHeights);
			for (int j = 0; j < 4; j++)
				collisionMaps[j] = new CollisionMap();

			FileArchive streamLoader_6 = streamLoaderForName(5, "update list");
			drawLoadingText(30, "Connecting to update server");

			resourceProvider = new OnDemandFetcher();
			resourceProvider.start(streamLoader_6, this);

			if (Configuration.dumpMaps) {
				resourceProvider.dumpMaps();
			}

			if (Configuration.packIndexData) {
				repackCacheAll();
			}
			Frame.method528();

			Model.init();

			drawLoadingText(35, "Unpacking media");
			 donatorOrb = new Sprite("orbs/promo_orb");
			 donatorOrbHover = new Sprite("orbs/promo_orb_hover");

			 utilityOrb = new Sprite("orbs/utility_orb");
			 utilityOrbHover = new Sprite("orbs/utility_orb_hover");

			 questOrb = new Sprite("orbs/quest_orb");
			 questOrbHover = new Sprite("orbs/quest_hover_orb");

			 knowOrb = new Sprite("orbs/event_orb");
			 knowOrbHover = new Sprite("orbs/event_orb_hover");
			mapIcon7 = new Sprite(streamLoader_2, "mapfunction", 1);
			mapIcon8 = new Sprite(streamLoader_2, "mapfunction", 51);
			mapIcon6 = new Sprite(streamLoader_2, "mapfunction", 74);
			mapIcon5 = new Sprite(streamLoader_2, "mapfunction", 5);
			mapIcon9 = new Sprite(streamLoader_2, "mapfunction", 56);
			multiOverlay = new Sprite(streamLoader_2, "overlay_multiway", 0);

			eventIcon = new Sprite(streamLoader_2, "mapfunction", 72);
			bankDivider = new Sprite("bank_divider");
			minimapImage = new Sprite(512,512);
			// Login
			drawLoadingText(40, "Unpacking media");
			// Login
			loginAsset0 = new Sprite("loginscreen2/677");//username hover
			loginAsset1 = new Sprite("loginscreen2/677");//password hover
			loginAsset2 = new Sprite("loginscreen2/676");//Account Name Background

			loginAsset4 = new Sprite("loginscreen2/60");//World switcher hover.
			loginAsset5 = new Sprite("loginscreen2/61");//World switcher hover.

			loginAsset6 = new Sprite("loginscreen2/58");//Login Button
			loginAsset7 = new Sprite("loginscreen2/59");//Login Button

			loginAsset8 = new Sprite("loginscreen2/674");//Account Delete
			loginAsset9 = new Sprite("loginscreen2/675");//Account Delete
			loginAsset10 = new Sprite("loginscreen2/676");//Account Hover
			loginAsset11 = new Sprite("loginscreen2/186");//Discord Icon
			loginAsset12 = new Sprite("loginscreen2/187");//Discord Icon
			loginAsset13 = new Sprite("loginscreen2/178");//Remember me check
			loginAsset14 = new Sprite("loginscreen2/180");//Remember me check
			loginAsset15 = new Sprite("loginscreen2/188");//Music Control
			loginAsset16 = new Sprite("loginscreen2/189");//Music Control
			loginScreenBackground = new Sprite("/loginscreen2/57");
			loginScreenBackgroundCaptcha = new Sprite("/loginscreen/captcha_background");

			drawLoadingText(45, "Unpacking media");
			File[] file = new File(Signlink.getCacheDirectory() + "/sprites/sprites/").listFiles();
			int size = file.length;
			cacheSprite = new Sprite[size];
			for (int i = 0; i < size; i++) {
				cacheSprite[i] = new Sprite("Sprites/" + i);
			}
			drawLoadingText(47, "Unpacking media");
			xpSprite = new Sprite("medal");
			for (int i = 0; i < inputSprites.length; i++)
				inputSprites[i] = new Sprite("Interfaces/Inputfield/SPRITE " + (i + 1));

			/*
			 * for (int index = 0; index < minimapSprites.length; index++) {
			 * minimapSprites[index] = new Sprite(streamLoader_2, "gameframe", index); Image
			 * image = minimapSprites[index].getImage(); if (image == null) {
			 * System.out.println("Image is null for: " + index); } }
			 */

			for (int i = 0; i < tabAreaResizable.length; i++)
				tabAreaResizable[i] = new Sprite("Gameframe/resizable/tabArea " + i);
			drawLoadingText(49, "Unpacking media");
			loadTabArea();

			SkillOrbs.init();

			infinity = new Sprite("infinity");
			chatArea = new Sprite("Gameframe/chatarea");
			channelButtons = new Sprite("Gameframe/channelbuttons");
			venomOrb = new Sprite("orbs/venom");
			shopSprite = new Sprite("orbs/shop");
			shopSpriteHover = new Sprite("orbs/shophover");
			drawLoadingText(50, "Unpacking media");
			for (int index = 0; index < smallXpSprites.length; index++) {
				smallXpSprites[index] = new Sprite("expdrop/" + index);

			}

			for (int c1 = 0; c1 <= 3; c1++)
				chatButtons[c1] = new Sprite(streamLoader_2, "chatbuttons", c1);
			chatButtons[3] = new Sprite("1025_0");
			Sprite[] clanIcons = new Sprite[9];
			for (int index = 0; index < clanIcons.length; index++) {
				clanIcons[index] = new Sprite("Clan Chat/Icons/" + index);
			}
			drawLoadingText(51, "Unpacking media");
			String iconPackDir = Signlink.getCacheDirectory() + "sprites" + Signlink.separator + "icon_pack";
			Sprite[] iconPack = new Sprite[FileUtility.getFileCount(iconPackDir)];
			for (int index = 0; index < iconPack.length; index++) {
				iconPack[index] = new Sprite("icon_pack/" + index);
			}

			RSFont.unpackImages(modIcons, clanIcons, iconPack);
			drawLoadingText(53, "Unpacking media");
			mapEdge = new Sprite(streamLoader_2, "mapedge", 0);
			mapEdge.method345();

			try {
				for (int k3 = 0; k3 < 188; k3++)
					mapScenes[k3] = new IndexedImage(streamLoader_2, "mapscene", k3);
			} catch (Exception _ex) {
			}
			try {
				for (int l3 = 0; l3 < 125; l3++)
					mapFunctions[l3] = new Sprite("mapfunctions/" + l3);
			} catch (Exception _ex) {
			}

			try {
				for (int i4 = 0; i4 < 17; i4++) {
					hitMarks[i4] = new Sprite("hitmark/HIT " + i4);
				}
			} catch (Exception _ex) {
			}

			drawLoadingText(55, "Unpacking media");
			try {
				for (int h1 = 0; h1 < 6; h1++)
					headIconsHint[h1] = new Sprite(streamLoader_2, "headicons_hint", h1);
			} catch (Exception _ex) {
			}
			try {
				for (int j4 = 0; j4 < 18; j4++)
					headIcons[j4] = new Sprite(streamLoader_2, "headicons_prayer", j4);
				for (int j45 = 0; j45 < 3; j45++)
					skullIcons[j45] = new Sprite(streamLoader_2, "headicons_pk", j45);
			} catch (Exception _ex) {
			}
			for (int i = 0; i < minimapIcons.length; i++) {
				minimapIcons[i] = new Sprite("Mapicons/ICON " + i);
			}

			mapFlag = new Sprite(streamLoader_2, "mapmarker", 0);
			mapMarker = new Sprite(streamLoader_2, "mapmarker", 1);
			for (int k4 = 0; k4 < 8; k4++)
				crosses[k4] = new Sprite(streamLoader_2, "cross", k4);
			drawLoadingText(56, "Unpacking media");
 			mapDots = new Sprite[6];
			mapDots[0] = new Sprite(streamLoader_2, "mapdots", 0);
			mapDots[1] = new Sprite(streamLoader_2, "mapdots", 1);
			mapDots[2] = new Sprite(streamLoader_2, "mapdots", 2);
			mapDots[3] = new Sprite(streamLoader_2, "mapdots", 3);
			mapDots[4] = new Sprite(streamLoader_2, "mapdots", 4);
			mapDots[5] = new Sprite(streamLoader_2, "mapdots", 5);
			drawLoadingText(57, "Unpacking media");
			scrollBar1 = new Sprite(streamLoader_2, "scrollbar", 0);
			scrollBar2 = new Sprite(streamLoader_2, "scrollbar", 1);
			for (int i = 0; i < modIcons.length; i++) {
				modIcons[i] = new Sprite("Player/MODICONS " + i + "");
			}

			for (int index = 0; index < GameTimerHandler.TIMER_IMAGES.length; index++) {
				GameTimerHandler.TIMER_IMAGES[index] = new Sprite("GameTimer/TIMER " + index);
			}
			drawLoadingText(60, "Unpacking media");
			int i5 = (int) (Math.random() * 21D) - 10;
			int j5 = (int) (Math.random() * 21D) - 10;
			int k5 = (int) (Math.random() * 21D) - 10;
			int l5 = (int) (Math.random() * 41D) - 20;

			for (int i6 = 0; i6 < 188; i6++) {
				if (mapScenes[i6] != null)
					mapScenes[i6].offsetColor(i5 + l5, j5 + l5, k5 + l5);
			}

			drawLoadingText(65, "Unpacking Textures");
			TextureProvider textureProvider = new TextureProvider(streamLoader_3,streamLoader,20,Rasterizer3D.lowMem ? 64 : 128);
			textureProvider.setBrightness(0.80000000000000004D);
			Rasterizer3D.setTextureLoader(textureProvider);
			drawLoadingText(66, "Unpacking Animations");

			drawLoadingText(67, "Unpacking Objects");
			ObjectDefinition.init(streamLoader);
			drawLoadingText(68, "Unpacking Floors");
			FloorDefinition.init(streamLoader);
			drawLoadingText(70, "Unpacking Items");
			ItemDefinition.init(streamLoader);
			drawLoadingText(73, "Unpacking Npc's");
			NpcDefinition.unpackConfig(streamLoader);
			drawLoadingText(74, "Unpacking Player Kits");
			IDK.unpackConfig(streamLoader);
			drawLoadingText(75, "Unpacking Graphics");
			GraphicsDefinition.unpackConfig(streamLoader);
			drawLoadingText(80, "Unpacking textures");
			AreaDefinition.init(streamLoader);
			drawLoadingText(84, "Unpacking Varps");
			Varp.unpackConfig(streamLoader);
			VarBit.unpackConfig(streamLoader);
			drawLoadingText(88, "Unpacking textures");
			loadPlayerData();

			if (Configuration.dumpDataLists) {
				NpcDefinition.dumpList();
				ObjectDefinition.dumpList();
				resourceProvider.dumpModels();
			}

			drawLoadingText(90, "Unpacking interfaces");

			TextDrawingArea allFonts[] = { smallText, aTextDrawingArea_1271, chatTextDrawingArea,
					aTextDrawingArea_1273 };
			RSInterface.unpack(streamLoader_1, allFonts, streamLoader_2, new RSFont[] {newSmallFont, newRegularFont, newBoldFont, newFancyFont});
			drawLoadingText(92, "Preparing game engine");

			if(getUserSettings().isOldGameframe() == false) {
				mapBack = new Sprite("Gameframe/fixed/mapBack");
			} else {
				mapBack = new Sprite("Gameframe317/fixed/mapBack");
			}
			drawLoadingText(94, "Preparing game engine");
			for (int pixelY = 0; pixelY < 33; pixelY++) {
				int k6 = 999;
				int i7 = 0;
				for (int pixelX = 0; pixelX < 34; pixelX++) {
					if (mapBack.myPixels[pixelX + pixelY * mapBack.myWidth] == 0) {
						if (k6 == 999)
							k6 = pixelX;
						continue;
					}
					if (k6 == 999)
						continue;
					i7 = pixelX;
					break;
				}
				anIntArray968[pixelY] = k6;
				anIntArray1057[pixelY] = i7 - k6;
			}
			drawLoadingText(95, "Preparing game engine");
			for (int pixelY = 1; pixelY < 153; pixelY++) {
				int j7 = 999;
				int l7 = 0;
				for (int pixelX = 24; pixelX < 177; pixelX++) {
					if (mapBack.myPixels[pixelX + pixelY * mapBack.myWidth] == 0 && (pixelX > 34 || pixelY > 34)) {
						if (j7 == 999) {
							j7 = pixelX;
						}
						continue;
					}
					if (j7 == 999) {
						continue;
					}
					l7 = pixelX;
					break;
				}
				anIntArray1052[pixelY - 1] = j7 - 24;
				anIntArray1229[pixelY - 1] = l7 - j7;
			}
			drawLoadingText(97, "Preparing game engine");
			try {
				macAddress = GetNetworkAddress.GetAddress("mac");
				if (macAddress == null)
					macAddress = "";
				if (Configuration.developerMode) {
					System.out.println(macAddress);
				}
			} catch (Exception e) {
				e.printStackTrace();
				macAddress = "";
			}
			drawLoadingText(98, "Preparing game engine");
			//Censor.loadConfig(streamLoader_4);
			setConfigButtonsAtStartup();
			//mouseDetection = new MouseDetection(this);

			try {
				informationFile.read();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (informationFile.isUsernameRemembered()) {
				myUsername = informationFile.getStoredUsername();
			}
			if (informationFile.isPasswordRemembered()) {
				setPassword(informationFile.getStoredPassword());
			}
			if (informationFile.isRememberRoof()) {
				removeRoofs = true;
			}
			drawLoadingText(99, "Preparing game engine");
			//startRunnable(mouseDetection, 10);
			SceneObject.clientInstance = this;
			ObjectDefinition.clientInstance = this;
			NpcDefinition.clientInstance = this;
			Frame.clientInstance = this;

			if (Configuration.PRINT_EMPTY_INTERFACE_SECTIONS) {
				RSInterface.printEmptyInterfaceSections();
			}

			if (anInt913 == 0) {
				RSInterface.interfaceCache[58002].active = true;
			} else if (anInt913 == 1) {
				RSInterface.interfaceCache[58002].active = false;
			}
			RSInterface.interfaceCache[58010].active = true;
			Preferences.load();
			AccountManager.loadAccount();
			drawLoadingText(100, "Preparing game engine");

			requestMusic(SoundConstants.SCAPE_RUNE);
			setGameState(GameState.LOGIN_SCREEN);
			clientLoaded = true;
			return;
		} catch (Exception exception) {
			exception.printStackTrace();
			Signlink.reporterror("loaderror " + aString1049 + " " + anInt1079);
		}
		loadingError = true;
	}

	private void select(int avatar) {
		Account account = lastAccount;

		if (account == null) {
			return;
		}

		account.avatar = avatar;
		AccountManager.setAvatar(account.username, avatar);
		AccountManager.saveAccount();
		firstLoginMessage = "";
		AvatarScreen = false;
		AccountScreen = true;
	}

	public boolean AccountScreen = false;
	public boolean AvatarScreen = false;
	public boolean ErrorScreen = false;
	private void OpenAvatarScreen() {
//		resetImageProducers();
//		loginScreenGraphicsBuffer.initDrawingArea();
//		loginScreenGraphicsBuffer = new RSImageProducer(canvasWidth, canvasHeight, getGameComponent());
		AccountScreen = false;
		ErrorScreen = false;
		AvatarScreen = true;
		int centerX = canvasWidth/2;
		int centerY = canvasHeight/2;
		int offset = 29;
		int frameColor = 0x3C99A3;
		int frameHoverColor = 0x3C767D;
		int avatarHoverAlpha = 70;


		/* Background */
		Sprite accmng = new Sprite("loginscreen2/678");
		Sprite picture;

		accmng.drawTransparentSprite((centerX) - (loginScreenBackground.myWidth / 2), (centerY) - (loginScreenBackground.myHeight / 2), 126);

		//Darth Vader
		if (mouseInRegion(centerX - 215, centerY - 136, centerX - 115, centerY - 62)) {
			picture = new Sprite("loginscreen2/avatar/534");
			picture.drawAdvancedSprite(165 + offset, 115, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(165 + offset, 115, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/534");
			picture.drawAdvancedSprite(165 + offset, 115);
			Rasterizer2D.drawRectangle(165 + offset, 115, picture.myWidth, picture.myHeight, frameHoverColor);
		}
		//Skeleton
		if (mouseInRegion(centerX - 91, centerY - 136, centerX - 14, centerY - 62)) {
			picture = new Sprite("loginscreen2/avatar/535");
			picture.drawAdvancedSprite(265 + offset, 115, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(265 + offset, 115, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/535");
			picture.drawAdvancedSprite(265 + offset, 115);
			Rasterizer2D.drawRectangle(265 + offset, 115, picture.myWidth, picture.myHeight, frameColor);
		}
		//Cool dude
		if (mouseInRegion(centerX + 9, centerY - 136, centerX + 85, centerY - 62)) {
			picture = new Sprite("loginscreen2/avatar/536");
			picture.drawAdvancedSprite(365 + offset, 115, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(365 + offset, 115, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/536");
			picture.drawAdvancedSprite(365 + offset, 115);
			Rasterizer2D.drawRectangle(365 + offset, 115, picture.myWidth, picture.myHeight, frameColor);
		}
		//Gas mask
		if (mouseInRegion(centerX + 112, centerY - 136, centerX + 185, centerY - 62)) {
			picture = new Sprite("loginscreen2/avatar/537");
			picture.drawAdvancedSprite(465 + offset, 115, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(465 + offset, 115, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/537");
			picture.drawAdvancedSprite(465 + offset, 115);
			Rasterizer2D.drawRectangle(465 + offset, 115, picture.myWidth, picture.myHeight, frameColor);
		}
		//KFC
		if (mouseInRegion(centerX - 188, centerY - 46, centerX - 114, centerY + 30)) {
			picture = new Sprite("loginscreen2/avatar/538");
			picture.drawAdvancedSprite(165 + offset, 205, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(165 + offset, 205, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/538");
			picture.drawAdvancedSprite(165 + offset, 205);
			Rasterizer2D.drawRectangle(165 + offset, 205, picture.myWidth, picture.myHeight, frameColor);
		}
		//Dog
		if (mouseInRegion(centerX - 91, centerY - 46, centerX - 14, centerY + 30)) {
			picture = new Sprite("loginscreen2/avatar/539");
			picture.drawAdvancedSprite(265 + offset, 205, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(265 + offset, 205, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/539");
			picture.drawAdvancedSprite(265 + offset, 205);
			Rasterizer2D.drawRectangle(265 + offset, 205, picture.myWidth, picture.myHeight, frameColor);
		}
		//Monkey
		if (mouseInRegion(centerX + 9, centerY - 46, centerX + 85, centerY + 30)) {
			picture = new Sprite("loginscreen2/avatar/540");
			picture.drawAdvancedSprite(365 + offset, 205, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(365 + offset, 205, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/540");
			picture.drawAdvancedSprite(365 + offset, 205);
			Rasterizer2D.drawRectangle(365 + offset, 205, picture.myWidth, picture.myHeight, frameColor);
		}
		//Wolf
		if (mouseInRegion(centerX + 112, centerY - 46, centerX + 185, centerY + 30)) {
			picture = new Sprite("loginscreen2/avatar/541");
			picture.drawAdvancedSprite(465 + offset, 205, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(465 + offset, 205, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/541");
			picture.drawAdvancedSprite(465 + offset, 205);
			Rasterizer2D.drawRectangle(465 + offset, 205, picture.myWidth, picture.myHeight, frameColor);
		}
		//Joker
		if (mouseInRegion(centerX - 189, centerY + 45, centerX - 114, centerY + 122)) {
			picture = new Sprite("loginscreen2/avatar/680");
			picture.drawAdvancedSprite(165 + offset, 295, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(165 + offset, 295, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/680");
			picture.drawAdvancedSprite(165 + offset, 295);
			Rasterizer2D.drawRectangle(165 + offset, 295, picture.myWidth, picture.myHeight, frameColor);
		}
		//Demon
		if (mouseInRegion(centerX - 91, centerY + 45, centerX - 14, centerY + 122)) {
			picture = new Sprite("loginscreen2/avatar/681");
			picture.drawAdvancedSprite(265 + offset, 295, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(265 + offset, 295, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/681");
			picture.drawAdvancedSprite(265 + offset, 295);
			Rasterizer2D.drawRectangle(265 + offset, 295, picture.myWidth, picture.myHeight, frameColor);
		}
		//Hot girl 1
		if (mouseInRegion(centerX + 9, centerY + 45, centerX + 85, centerY + 122)) {
			picture = new Sprite("loginscreen2/avatar/682");
			picture.drawAdvancedSprite(365 + offset, 295, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(365 + offset, 295, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/682");
			picture.drawAdvancedSprite(365 + offset, 295);
			Rasterizer2D.drawRectangle(365 + offset, 295, picture.myWidth, picture.myHeight, frameColor);
		}
		//Hot girl 2
		if (mouseInRegion(centerX + 112, centerY + 45, centerX + 185, centerY + 122)) {
			picture = new Sprite("loginscreen2/avatar/683");
			picture.drawAdvancedSprite(465 + offset, 295, avatarHoverAlpha);
			Rasterizer2D.drawRectangle(465 + offset, 295, picture.myWidth, picture.myHeight, frameHoverColor);
		} else {
			picture = new Sprite("loginscreen2/avatar/683");
			picture.drawAdvancedSprite(465 + offset, 295);
			Rasterizer2D.drawRectangle(465 + offset, 295, picture.myWidth, picture.myHeight, frameColor);
		}

		newBoldFont.drawCenteredString("[ Click on the avatar you wish to select ]",  centerX + 5,  centerY + 150, 0xFFFFFF, 1);

		/* Drawing */
//		loginScreenGraphicsBuffer.drawGraphics(0, 0, super.graphics);
	}

	private void OpenAccountScreen() {
//		resetImageProducers();
//		loginScreenGraphicsBuffer.initDrawingArea();
//		loginScreenGraphicsBuffer = new RSImageProducer(canvasWidth, canvasHeight, getGameComponent());
		AccountScreen = true;
		ErrorScreen = false;
		AvatarScreen = false;
		int centerX = canvasWidth/2;
		int centerY = canvasHeight/2;
		Account account = lastAccount;
		int moveY = 20;

		Sprite avatar = new Sprite ("loginscreen2/avatar/"+account.avatar);

		/* Background */
		Sprite accmng = new Sprite("loginscreen2/678");

		accmng.drawTransparentSprite((centerX) - (loginScreenBackground.myWidth / 2), (centerY) - (loginScreenBackground.myHeight / 2), 126);

		/* Box */
		Rasterizer2D.fillRectangle(175, 175 + moveY, 425, 150, 0x26464A, 150);
		Rasterizer2D.drawRectangle(175, 175 + moveY, 425, 150, 0x27A2B0);

		/* Avatar */
		if (mouseInRegion(centerX - 181, centerY - 32, centerX - 84, centerY + 66)) {
			Rasterizer2D.fillRectangle(200, 200 + moveY, 100, 100, 0x26464A, 85);
			avatar.drawTransparentSprite(212, 233, 85);
		} else {
			Rasterizer2D.fillRectangle(200, 200 + moveY, 100, 100, 0x26464A, 150);
			avatar.drawTransparentSprite(212, 233, 255);
		}
		Rasterizer2D.drawRectangle(200, 200 + moveY, 100, 100, 0x27A2B0);

		/* Messages */
		newBoldFont.drawCenteredString("Exiled",  centerX + 5, centerY - 105, 0x27A2B0, 1);
		newRegularFont.drawCenteredString("Account Manager", centerX + 5,  centerY - 85, 0xD4A190, 1);
		newBoldFont.drawCenteredString(account.username == null ? "" : Utility.formatName(account.username), 385, 210 + moveY, 0x27A2B0, 0);
		newBoldFont.drawBasicString("Created:", 320, 235 + moveY, 0xD4A190, 0);
		newBoldFont.drawBasicString(account.created, 385, 235 + moveY, 0xBDD490, 0);
		newBoldFont.drawBasicString("Rank:", 320, 265 + moveY, 0xD4A190, 0);
		int rank = (account.rank - 1);
		newBoldFont.drawBasicString("@cr" + rank + "@ " + Utility.getRank(rank), 385, 265 + moveY, 0xBDD490, 0);
		newBoldFont.drawBasicString("Uses:", 320, 295 + moveY, 0xD4A190, 0);
		newBoldFont.drawBasicString(account.uses + "", 385, 295 + moveY, 0xBDD490, 0);
		newSmallFont.drawCenteredString("Click on your avatar to open the avatar menu", centerX + 5, centerY + 88, 0xB5EBEB, 1);
		newBoldFont.drawCenteredString("[ Click anywhere to return to the main screen ]", centerX + 5,  centerY + 150, 0xFFFFFF,1);

//		loginScreenGraphicsBuffer.drawGraphics(0, 0, super.graphics);
	}

	private void OpenErrorScreen() {
//		resetImageProducers();
//		loginScreenGraphicsBuffer.initDrawingArea();
//		loginScreenGraphicsBuffer = new RSImageProducer(canvasWidth, canvasHeight, getGameComponent());
		AccountScreen = false;
		ErrorScreen = true;
		AvatarScreen = false;
		int centerX = canvasWidth/2;
		int centerY = canvasHeight/2;
		Account account = lastAccount;
		int moveY = 20;

		/* Background */
		Sprite accmng = new Sprite("loginscreen2/678");

		accmng.drawTransparentSprite((centerX) - (loginScreenBackground.myWidth / 2), (centerY) - (loginScreenBackground.myHeight / 2), 126);

		/* Box */
		Rasterizer2D.fillRectangle(175, 175 + moveY, 425, 150, 0x26464A, 150);
		Rasterizer2D.drawRectangle(175, 175 + moveY, 425, 150, 0x27A2B0);

		/* Messages */
		newBoldFont.drawCenteredString("Exiled",  centerX + 5, centerY - 115, 0x27A2B0, 1);
		newRegularFont.drawCenteredString("Error Message", centerX + 5,  centerY - 95, 0xD4A190, 1);
		newBoldFont.drawCenteredString(firstLoginMessage, centerX + 5,  centerY + 25, 0xD4A190, 1);
		newBoldFont.drawCenteredString("[ Click anywhere to return to the main screen ]", centerX + 5, centerY + 150, 0xFFFFFF, 1);

//		loginScreenGraphicsBuffer.drawGraphics(0, 0, super.graphics);
	}

	public void method91(Buffer stream, int i) {
		while (stream.bitPosition + 10 < i * 8) {
			int j = stream.readBits(11);
			if (j == 2047)
				break;
			if (players[j] == null) {
				players[j] = new Player();
				if (aStreamArray895s[j] != null)
					players[j].updatePlayer(aStreamArray895s[j]);
			}
			playerIndices[playerCount++] = j;
			Player player = players[j];
			player.anInt1537 = loopCycle;
			int k = stream.readBits(1);
			if (k == 1)
				anIntArray894[anInt893++] = j;
			int l = stream.readBits(1);
			int i1 = stream.readBits(5);
			if (i1 > 15)
				i1 -= 32;
			int j1 = stream.readBits(5);
			if (j1 > 15)
				j1 -= 32;
			player.setPos(localPlayer.pathX[0] + j1, localPlayer.pathY[0] + i1, l == 1);
			callbacks.post(new PlayerSpawned(player));
		}
		stream.finishBitAccess();
	}

	public boolean inCircle(int circleX, int circleY, int clickX, int clickY, int radius) {
		return Math.pow((circleX + radius - clickX), 2)
				+ Math.pow((circleY + radius - clickY), 2) < Math.pow(radius, 2);
	}

	public boolean mouseMapPosition() {
		if (MouseHandler.mouseX >= canvasWidth - 21 && MouseHandler.mouseX <= canvasWidth && MouseHandler.mouseY >= 0
				&& MouseHandler.mouseY <= 21) {
			return false;
		}
		return true;
	}

	private void processMainScreenClick() {
		if (minimapState != 0)
			return;
		if (clickMode3 == 1) {
			int i = MouseHandler.saveClickX - 25 - 547;
			int j = MouseHandler.saveClickY - 5 - 3;
			if (isResized()) {
				i = MouseHandler.saveClickX - (canvasWidth - 182 + 24);
				j = MouseHandler.saveClickY - 8;
			}
			if (inCircle(0, 0, i, j, 76) && mouseMapPosition() && !runHover) {
				i -= 73;
				j -= 75;
				int k = viewRotation + minimapRotation & 0x7ff;
				int i1 = Rasterizer3D.SINE[k];
				int j1 = Rasterizer3D.COSINE[k];
				i1 = i1 * (minimapZoom + 256) >> 8;
				j1 = j1 * (minimapZoom + 256) >> 8;
				int k1 = j * i1 + i * j1 >> 11;
				int l1 = j * j1 - i * i1 >> 11;
				int i2 = localPlayer.x + k1 >> 7;
				int j2 = localPlayer.y - l1 >> 7;
				if (localPlayer.isAdminRights() && controlIsDown) {
					teleport(baseX + i2, baseY + j2);
				} else {
					boolean flag1 = doWalkTo(1, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, j2, true, i2);
					if (flag1) {
						stream.writeUnsignedByte(i);
						stream.writeUnsignedByte(j);
						stream.writeWord(viewRotation);
						stream.writeUnsignedByte(57);
						stream.writeUnsignedByte(minimapRotation);
						stream.writeUnsignedByte(minimapZoom);
						stream.writeUnsignedByte(89);
						stream.writeWord(localPlayer.x);
						stream.writeWord(localPlayer.y);
						stream.writeUnsignedByte(anInt1264);
						stream.writeUnsignedByte(63);
					}
				}
			}
			anInt1117++;
			if (anInt1117 > 1151) {
				anInt1117 = 0;
				stream.createFrame(246);
				stream.writeUnsignedByte(0);
				int l = stream.currentPosition;
				if ((int) (Math.random() * 2D) == 0)
					stream.writeUnsignedByte(101);
				stream.writeUnsignedByte(197);
				stream.writeWord((int) (Math.random() * 65536D));
				stream.writeUnsignedByte((int) (Math.random() * 256D));
				stream.writeUnsignedByte(67);
				stream.writeWord(14214);
				if ((int) (Math.random() * 2D) == 0)
					stream.writeWord(29487);
				stream.writeWord((int) (Math.random() * 65536D));
				if ((int) (Math.random() * 2D) == 0)
					stream.writeUnsignedByte(220);
				stream.writeUnsignedByte(180);
				stream.writeBytes(stream.currentPosition - l);
			}
		}
	}
	/**
	 * Get the String residing on the clipboard.
	 *
	 * @return any text found on the Clipboard; if none found, return an empty
	 *         String.
	 */
	public static String getClipboardContents() {
		if (System.currentTimeMillis() - timer < 2000) {
			return "";
		}
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
				timer = System.currentTimeMillis();
			} catch (UnsupportedFlavorException | IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	static long timer = 0;

	private void teleport(int x, int y) {
		String text = "::tele " + x + " " + y;
		stream.createFrame(103);
		stream.writeUnsignedByte(text.length() - 1);
		stream.writeString(text.substring(2));
	}

	public static void teleportInterface() {
		if (System.currentTimeMillis() - timer < 400) {
			return;
		}
		timer = System.currentTimeMillis();
		String text = "::teleport";
		stream.createFrame(103);
		stream.writeUnsignedByte(text.length() - 1);
		stream.writeString(text.substring(2));
	}


    public static void foundryTeleport() {
        if (System.currentTimeMillis() - timer < 400) {
            return;
        }
        timer = System.currentTimeMillis();
        String text = "::foundry";
        stream.createFrame(103);
        stream.writeUnsignedByte(text.length() - 1);
        stream.writeString(text.substring(2));
    }

    public static void instanceSystem() {
        if (System.currentTimeMillis() - timer < 400) {
            return;
        }
        timer = System.currentTimeMillis();
        String text = "::bp";
        stream.createFrame(103);
        stream.writeUnsignedByte(text.length() - 1);
        stream.writeString(text.substring(2));
    }

    public static void taskSystem() {
        if (System.currentTimeMillis() - timer < 400) {
            return;
        }
        timer = System.currentTimeMillis();
        String text = "::taskmanager";
        stream.createFrame(103);
        stream.writeUnsignedByte(text.length() - 1);
        stream.writeString(text.substring(2));
    }

	public static void questList() {
		if (System.currentTimeMillis() - timer < 400) {
			return;
		}
		timer = System.currentTimeMillis();
		String text = "::quest";
		stream.createFrame(103);
		stream.writeUnsignedByte(text.length() - 1);
		stream.writeString(text.substring(2));
	}

    public static void bankInterface() {
        if (System.currentTimeMillis() - timer < 400) {
            return;
        }
        timer = System.currentTimeMillis();
        String text = "::bank";
        stream.createFrame(103);
        stream.writeUnsignedByte(text.length() - 1);
        stream.writeString(text.substring(2));
    }

    public static void home() {
        if (System.currentTimeMillis() - timer < 400) {
            return;
        }
        timer = System.currentTimeMillis();
        String text = "::home";
        stream.createFrame(103);
        stream.writeUnsignedByte(text.length() - 1);
        stream.writeString(text.substring(2));
    }

    public static void lastTeleport() {
        if (System.currentTimeMillis() - timer < 400) {
            return;
        }
        timer = System.currentTimeMillis();
        String text = "::lastTele";
        stream.createFrame(103);
        stream.writeUnsignedByte(text.length() - 1);
        stream.writeString(text.substring(2));
    }

    public static void dz() {
        if (System.currentTimeMillis() - timer < 400) {
            return;
        }
        timer = System.currentTimeMillis();
        String text = "::dz";
        stream.createFrame(103);
        stream.writeUnsignedByte(text.length() - 1);
        stream.writeString(text.substring(2));
    }

    public static void closeInterface() {
		String text = "::close";
		stream.createFrame(103);
		stream.writeUnsignedByte(text.length() - 1);
		stream.writeString(text.substring(2));
	}

	public static void continueDialogue() {
		if (System.currentTimeMillis() - timer < 400) {
			return;
		}
		timer = System.currentTimeMillis();
		String text = "::dialoguecontinuation continue";
		stream.createFrame(103);
		stream.writeUnsignedByte(text.length() - 1);
		stream.writeString(text.substring(2));
	}

	public static void dialogueOptions(String option) {
		if (System.currentTimeMillis() - timer < 400) {
			return;
		}
		timer = System.currentTimeMillis();
		String text = option == "one" ? "::dialoguecontinuation option_one"
				: option == "two" ? "::dialoguecontinuation option_two"
				: option == "three" ? "::dialoguecontinuation option_three"
				: option == "four" ? "::dialoguecontinuation option_four"
				: "::dialoguecontinuation option_five";
		stream.createFrame(103);
		stream.writeUnsignedByte(text.length() - 1);
		stream.writeString(text.substring(2));
	}

	private String interfaceIntToString(int j) {
		if (j < 0x3b9ac9ff)
			return String.valueOf(j);
		else
			return "*";
	}

	public void showErrorScreen() {
		Graphics g = getGameComponent().getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 765, 503);
		//method4(1);
		if (loadingError) {
			aBoolean831 = false;
			g.setFont(new Font("Helvetica", 1, 16));
			g.setColor(Color.yellow);
			int k = 35;
			g.drawString("Sorry, an error has occured whilst loading " + Configuration.CLIENT_TITLE, 30, k);
			k += 50;
			g.setColor(Color.white);
			g.drawString("To fix this try the following (in order):", 30, k);
			k += 50;
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", 1, 12));
			g.drawString("1: Try restarting your client.", 30, k);
			k += 30;
			g.drawString("2: Try deleting the cache folder: " + Signlink.getCacheDirectory(), 30, k);
			k += 30;
			g.drawString("3: Try rebooting your computer", 30, k);
			k += 30;
			g.drawString("4: Try installing a different version of Java", 30, k);
			k += 30;
			g.drawString("5: Ask on discord", 30, k);
		}
		if (genericLoadingError) {
			aBoolean831 = false;
			g.setFont(new Font("Helvetica", 1, 20));
			g.setColor(Color.white);
			g.drawString("Error - unable to load game!", 50, 50);
			g.drawString("To play RuneScape make sure you play from", 50, 100);
			g.drawString("http://www.RuneScape.com", 50, 150);
		}
		if (rsAlreadyLoaded) {
			aBoolean831 = false;
			g.setColor(Color.yellow);
			int l = 35;
			g.drawString("Error a copy of RuneScape already appears to be loaded", 30, l);
			l += 50;
			g.setColor(Color.white);
			g.drawString("To fix this try the following (in order):", 30, l);
			l += 50;
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", 1, 12));
			g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, l);
			l += 30;
			g.drawString("2: Try rebooting your computer, and reloading", 30, l);
			l += 30;
		}
	}

	@Override
	public URL getCodeBase() {
		try {
			return new URL(server + ":" + (80 + portOff));
		} catch (Exception _ex) {
		}
		return null;
	}

	public void processNpcMovement() {
		for (int j = 0; j < npcCount; j++) {
			int k = npcIndices[j];
			Npc npc = npcs[k];
			if (npc != null)
				method96(npc);
		}
	}

	public void method96(Entity entity) {
		if (entity.x < 128 || entity.y < 128 || entity.x >= 13184 || entity.y >= 13184) {
			entity.primarySeqID = -1;
			entity.graphicId = -1;
			entity.initiate_movement = 0;
			entity.cease_movement = 0;
			entity.x = entity.pathX[0] * 128 + entity.anInt1540 * 64;
			entity.y = entity.pathY[0] * 128 + entity.anInt1540 * 64;
			entity.method446();
		}
		if (entity == localPlayer && (entity.x < 1536 || entity.y < 1536 || entity.x >= 11776 || entity.y >= 11776)) {
			entity.primarySeqID = -1;
			entity.graphicId = -1;
			entity.initiate_movement = 0;
			entity.cease_movement = 0;
			entity.x = entity.pathX[0] * 128 + entity.anInt1540 * 64;
			entity.y = entity.pathY[0] * 128 + entity.anInt1540 * 64;
			entity.method446();
		}
		if (entity.initiate_movement > loopCycle)
			method97(entity);
		else if (entity.cease_movement >= loopCycle)
			method98(entity);
		else
			method99(entity);
		method100(entity);
		updateAnimation(entity);
	}

	public void method97(Entity entity) {
		int i = entity.initiate_movement - loopCycle;
		int j = entity.anInt1543 * 128 + entity.anInt1540 * 64;
		int k = entity.anInt1545 * 128 + entity.anInt1540 * 64;
		entity.x += (j - entity.x) / i;
		entity.y += (k - entity.y) / i;
		entity.anInt1503 = 0;
		if (entity.forceMovementDirection == 0)
			entity.setTurnDirection(1024);
		if (entity.forceMovementDirection == 1)
			entity.setTurnDirection(1536);
		if (entity.forceMovementDirection == 2)
			entity.setTurnDirection(0);
		if (entity.forceMovementDirection == 3)
			entity.setTurnDirection(512);
	}

	public void method98(Entity entity) {
		if (entity.cease_movement == loopCycle || entity.primarySeqID == -1 || entity.primarySeqDelay != 0
				|| entity.primarySeqCycle + 1 > SeqDefinition.get(entity.primarySeqID).getDuration(entity.primarySeqFrame)) {
			int i = entity.cease_movement - entity.initiate_movement;
			int j = loopCycle - entity.initiate_movement;
			int k = entity.anInt1543 * 128 + entity.anInt1540 * 64;
			int l = entity.anInt1545 * 128 + entity.anInt1540 * 64;
			int i1 = entity.anInt1544 * 128 + entity.anInt1540 * 64;
			int j1 = entity.anInt1546 * 128 + entity.anInt1540 * 64;
			entity.x = (k * (i - j) + i1 * j) / i;
			entity.y = (l * (i - j) + j1 * j) / i;
		}
		entity.anInt1503 = 0;
		if (entity.forceMovementDirection == 0)
			entity.setTurnDirection(1024);
		if (entity.forceMovementDirection == 1)
			entity.setTurnDirection(1536);
		if (entity.forceMovementDirection == 2)
			entity.setTurnDirection(0);
		if (entity.forceMovementDirection == 3)
			entity.setTurnDirection(512);
		entity.orientation = entity.getTurnDirection();
	}

	public void method99(Entity entity) {
		entity.secondarySeqID = entity.seqStandID;
		if (entity.smallXYIndex == 0) {
			entity.anInt1503 = 0;
			return;
		}
		if (entity.primarySeqID != -1 && entity.primarySeqDelay == 0) {
			SeqDefinition animation = SeqDefinition.get(entity.primarySeqID);
			if (entity.remaining_steps > 0 && animation.moveStyle == 0) {
				entity.anInt1503++;
				return;
			}
			if (entity.remaining_steps <= 0 && animation.idleStyle == 0) {
				entity.anInt1503++;
				return;
			}
		}
		int i = entity.x;
		int j = entity.y;
		int k = entity.pathX[entity.smallXYIndex - 1] * 128 + entity.anInt1540 * 64;
		int l = entity.pathY[entity.smallXYIndex - 1] * 128 + entity.anInt1540 * 64;
		if (k - i > 256 || k - i < -256 || l - j > 256 || l - j < -256) {
			entity.x = k;
			entity.y = l;
			return;
		}
		if (i < k) {
			if (j < l)
				entity.setTurnDirection(1280);
			else if (j > l)
				entity.setTurnDirection(1792);
			else
				entity.setTurnDirection(1536);
		} else if (i > k) {
			if (j < l)
				entity.setTurnDirection(768);
			else if (j > l)
				entity.setTurnDirection(256);
			else
				entity.setTurnDirection(512);
		} else if (j < l)
			entity.setTurnDirection(1024);
		else
			entity.setTurnDirection(0);
		int i1 = entity.getTurnDirection() - entity.orientation & 0x7ff;
		if (i1 > 1024)
			i1 -= 2048;
		int j1 = entity.turn180AnimIndex;
		if (i1 >= -256 && i1 <= 256)
			j1 = entity.walkAnimIndex;
		else if (i1 >= 256 && i1 < 768)
			j1 = entity.turn90CCWAnimIndex;
		else if (i1 >= -768 && i1 <= -256)
			j1 = entity.turn90CWAnimIndex;
		if (j1 == -1)
			j1 = entity.walkAnimIndex;
		entity.secondarySeqID = j1;
		int k1 = 4;
		if (entity.orientation != entity.getTurnDirection() && entity.interactingEntity == -1 && entity.anInt1504 != 0)
			k1 = 2;
		if (entity.smallXYIndex > 2)
			k1 = 6;
		if (entity.smallXYIndex > 3)
			k1 = 8;
		if (entity.anInt1503 > 0 && entity.smallXYIndex > 1) {
			k1 = 8;
			entity.anInt1503--;
		}
		if (entity.aBooleanArray1553[entity.smallXYIndex - 1])
			k1 <<= 1;
		if (k1 >= 8 && entity.secondarySeqID == entity.walkAnimIndex && entity.anInt1505 != -1)
			entity.secondarySeqID = entity.anInt1505;
		if (i < k) {
			entity.x += k1;
			if (entity.x > k)
				entity.x = k;
		} else if (i > k) {
			entity.x -= k1;
			if (entity.x < k)
				entity.x = k;
		}
		if (j < l) {
			entity.y += k1;
			if (entity.y > l)
				entity.y = l;
		} else if (j > l) {
			entity.y -= k1;
			if (entity.y < l)
				entity.y = l;
		}
		if (entity.x == k && entity.y == l) {
			entity.smallXYIndex--;
			if (entity.remaining_steps > 0)
				entity.remaining_steps--;
		}
	}

	public void method100(Entity entity) {
		if (entity.anInt1504 == 0)
			return;
		if (entity.interactingEntity != -1 && entity.interactingEntity < 32768) {
			Npc npc = npcs[entity.interactingEntity];
			if (npc != null) {
				int i1 = entity.x - npc.x;
				int k1 = entity.y - npc.y;
				if (i1 != 0 || k1 != 0)
					entity.setTurnDirection((int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff);
			}
		}
		if (entity.interactingEntity >= 32768) {
			int j = entity.interactingEntity - 32768;
			if (j == localPlayerIndex)
				j = maxPlayerCount;
			Player player = players[j];
			if (player != null) {
				int l1 = entity.x - player.x;
				int i2 = entity.y - player.y;
				if (l1 != 0 || i2 != 0)
					entity.setTurnDirection((int) (Math.atan2(l1, i2) * 325.94900000000001D) & 0x7ff);
			}
		}
		if ((entity.anInt1538 != 0 || entity.anInt1539 != 0) && (entity.smallXYIndex == 0 || entity.anInt1503 > 0)) {
			int k = entity.x - (entity.anInt1538 - baseX - baseX) * 64;
			int j1 = entity.y - (entity.anInt1539 - baseY - baseY) * 64;
			if (k != 0 || j1 != 0) {
				entity.setTurnDirection((int) (Math.atan2(k, j1) * 325.94900000000001D) & 0x7ff);
			}
			entity.anInt1538 = 0;
			entity.anInt1539 = 0;
		}
		int l = entity.getTurnDirection() - entity.orientation & 0x7ff;
		if (l != 0) {
			if (l < entity.anInt1504 || l > 2048 - entity.anInt1504)
				entity.orientation = entity.getTurnDirection();
			else if (l > 1024)
				entity.orientation -= entity.anInt1504;
			else
				entity.orientation += entity.anInt1504;
			entity.orientation &= 0x7ff;
			if (entity.secondarySeqID == entity.seqStandID && entity.orientation != entity.getTurnDirection()) {
				if (entity.standTurnAnimIndex != -1) {
					entity.secondarySeqID = entity.standTurnAnimIndex;
					return;
				}
				entity.secondarySeqID = entity.walkAnimIndex;
			}
		}
	}

	public void updateAnimation(Entity entity) {
		try {
			if (entity.graphicId > SeqDefinition.length())
				entity.graphicId = -1;
			entity.dynamic = false;

			if (entity.secondarySeqID > SeqDefinition.length())
				entity.secondarySeqID = -1;
			if (entity.secondarySeqID != -1) {
				if (entity.secondarySeqID > SeqDefinition.length())
					entity.secondarySeqID = 0;

				SeqDefinition secondarySeq = SeqDefinition.get(entity.secondarySeqID);
				if (secondarySeq.isSkeletalAnimation()) {
					++entity.secondarySeqFrame;
					int skeletalLength = secondarySeq.getSkeletalLength();
					if (entity.secondarySeqFrame < skeletalLength) {

					} else {
						entity.secondarySeqFrame -= secondarySeq.getFrameStep();
						++entity.animationLoops;
						if (entity.animationLoops >= secondarySeq.getLoopCount()) {
							entity.secondarySeqID = entity.seqStandID;
						} else if (entity.secondarySeqFrame >= 0 && entity.secondarySeqFrame < skeletalLength) {

						} else {
							entity.secondarySeqID = entity.seqStandID;
						}

						if (entity.secondarySeqFrame >= secondarySeq.getFrameCount()) {
							entity.secondarySeqFrame = 0;
						}
					}
				} else {
					entity.secondarySeqCycle++;
					if (entity.secondarySeqFrame < secondarySeq.getFrameCount() && entity.secondarySeqCycle > secondarySeq.getDuration(entity.secondarySeqFrame)) {
						entity.secondarySeqCycle = 1;
						entity.secondarySeqFrame++;
						entity.nextIdleFrame++;
					}

					entity.nextIdleFrame = entity.secondarySeqFrame + 1;
					if (entity.nextIdleFrame >= secondarySeq.getFrameCount()) {
						if (entity.nextIdleFrame >= secondarySeq.getFrameCount())
							entity.nextIdleFrame = 0;
					}

					if (entity.secondarySeqFrame >= secondarySeq.getFrameCount()) {
						entity.secondarySeqCycle = 1;
						entity.secondarySeqFrame = 0;
					}
				}
			}

			Iterator ir = entity.spotAnims.iterator();
			while(ir.hasNext()) {
				EntitySpotAnim graphicObject = (EntitySpotAnim) ir.next();
				if(graphicObject.getId() != -1 && loopCycle >= graphicObject.getCycle()) {
					if(graphicObject.getFrame() < 0)
						graphicObject.setFrame(0);
					SeqDefinition gfxSeq = GraphicsDefinition.cache[graphicObject.getId()].animationSequence;
					if(gfxSeq == null) {
						graphicObject.remove();
						continue;
					}
					if (gfxSeq.isSkeletalAnimation()) {
						int length = gfxSeq.getSkeletalLength();
						graphicObject.setFrame(graphicObject.getFrame() + 1);
						if(graphicObject.getFrame() >= length) {
							graphicObject.setId(-1);
							graphicObject.remove();
						}
					} else {
						for (graphicObject.currentAnimationTimeRemaining++; graphicObject.getFrame() < gfxSeq.getFrameCount() && graphicObject.currentAnimationTimeRemaining > gfxSeq.getDuration(entity.graphicFrame);) {
							graphicObject.currentAnimationTimeRemaining -= gfxSeq.getDuration(graphicObject.getFrame());
							graphicObject.setFrame(graphicObject.getFrame() + 1);
						}

						if (graphicObject.getFrame() >= gfxSeq.getFrameCount() && (graphicObject.getFrame() < 0 || graphicObject.getFrame() >= gfxSeq.getFrameCount())) {
							graphicObject.setId(-1);
							graphicObject.remove();
						}

						if (graphicObject.nextGraphicFrame < 0 || graphicObject.nextGraphicFrame >= gfxSeq.getFrameCount())
							graphicObject.nextGraphicFrame = graphicObject.getFrame() + 1;
						if (graphicObject.nextGraphicFrame >= gfxSeq.getFrameCount()) {
							graphicObject.setId(-1);
							graphicObject.remove();
						}
					}
				}
			}
			/*if (entity.graphicId != -1 && loopCycle >= entity.graphic_cycle) {
				if (entity.graphicFrame < 0)
					entity.graphicFrame = 0;

				SeqDefinition gfxSeq = GraphicsDefinition.cache[entity.graphicId].animationSequence;

				if (gfxSeq.isSkeletalAnimation()) {
					int length = gfxSeq.getSkeletalLength();
					entity.graphicFrame++;
					if(entity.graphicFrame >= length) {
						entity.graphicId = -1;
					}
				} else {
					for (entity.currentAnimationTimeRemaining++; entity.graphicFrame < gfxSeq.getFrameCount() && entity.currentAnimationTimeRemaining > gfxSeq.getDuration(entity.graphicFrame); entity.graphicFrame++)
						entity.currentAnimationTimeRemaining -= gfxSeq.getDuration(entity.graphicFrame);

					if (entity.graphicFrame >= gfxSeq.getFrameCount() && (entity.graphicFrame < 0 || entity.graphicFrame >= gfxSeq.getFrameCount()))
						entity.graphicId = -1;

					if (entity.nextGraphicFrame < 0 || entity.nextGraphicFrame >= gfxSeq.getFrameCount())
						entity.nextGraphicFrame = entity.graphicFrame + 1;
					if (entity.nextGraphicFrame >= gfxSeq.getFrameCount()) {

						entity.graphicId = -1;
					}
				}
			}*/
			if (entity.primarySeqID != -1 && entity.primarySeqDelay <= 1) {
				if (entity.primarySeqID >= SeqDefinition.length()) {
					entity.primarySeqID = -1;
				}
				SeqDefinition primarySeq = SeqDefinition.get(entity.primarySeqID);
				if (primarySeq.getMoveStyle() == 1 && entity.remaining_steps > 0 && entity.initiate_movement <= loopCycle && entity.cease_movement < loopCycle) {
					entity.primarySeqDelay = 1;
					return;
				}
			}
			if (entity.primarySeqID != -1 && entity.primarySeqDelay == 0) {
				SeqDefinition primarySeq = SeqDefinition.get(entity.primarySeqID);
				if (primarySeq.isSkeletalAnimation()) {
					++entity.primarySeqFrame;
					int skeletalLength = primarySeq.getSkeletalLength();
					if (entity.primarySeqFrame < skeletalLength) {
					} else {
						entity.primarySeqFrame -= primarySeq.getFrameStep();
						++entity.animationLoops;
						if (entity.animationLoops >= primarySeq.getLoopCount()) {
							entity.primarySeqID = -1;
						} else if (entity.primarySeqFrame >= 0 && entity.primarySeqFrame < skeletalLength) {

						} else {
							entity.primarySeqID = -1;
						}
					}
				} else {
					for (entity.primarySeqCycle++; entity.primarySeqFrame < primarySeq.getFrameCount() && entity.primarySeqCycle > primarySeq.getDuration(entity.primarySeqFrame); entity.primarySeqFrame++)
						entity.primarySeqCycle -= primarySeq.getDuration(entity.primarySeqFrame);

					if (entity.primarySeqFrame >= primarySeq.getFrameCount()) {
						entity.primarySeqFrame -= primarySeq.getFrameStep();
						entity.animationLoops++;
						if (entity.animationLoops >= primarySeq.getLoopCount())
							entity.primarySeqID = -1;
						if (entity.primarySeqFrame < 0 || entity.primarySeqFrame >= primarySeq.getFrameCount())
							entity.primarySeqID = -1;
					}
					entity.nextAnimationFrame = entity.primarySeqFrame + 1;
					if (entity.nextAnimationFrame >= primarySeq.getFrameCount()) {
						if (entity.animationLoops >= primarySeq.getLoopCount())
							entity.nextAnimationFrame = entity.primarySeqFrame + 1;
						if (entity.nextAnimationFrame < 0 || entity.nextAnimationFrame >= primarySeq.getFrameCount())
							entity.nextAnimationFrame = entity.primarySeqFrame;
					}
				}
				entity.dynamic = primarySeq.isStretches();
			}
			if (entity.primarySeqDelay > 0)
				entity.primarySeqDelay--;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void drawGameScreen() {
		if (fullscreenInterfaceID != -1 && loadingStage == 2) {
			if (loadingStage == 2) {
				try {
					processWidgetAnimations(tickDelta, fullscreenInterfaceID);
					if (openInterfaceID != -1) {
						processWidgetAnimations(tickDelta, openInterfaceID);
					}
				} catch(Exception ex) {

				}
				tickDelta = 0;
				Rasterizer3D.scanOffsets = fullScreenTextureArray;
				Rasterizer2D.clear();
				welcomeScreenRaised = true;
				if (openInterfaceID != -1) {
					RSInterface rsInterface_1 = RSInterface.interfaceCache[openInterfaceID];
					if (rsInterface_1.width == 512 && rsInterface_1.height == 334
							&& rsInterface_1.type == 0) {
						rsInterface_1.width = 765;
						rsInterface_1.height = 503;
					}
					try {
						drawInterface(0, 0, rsInterface_1, 8);
					} catch(Exception ex) {

					}
				}
				RSInterface rsInterface = RSInterface.interfaceCache[fullscreenInterfaceID];
				if (rsInterface.width == 512 && rsInterface.height == 334
						&& rsInterface.type == 0) {
					rsInterface.width = 765;
					rsInterface.height = 503;
				}
				try {
					drawInterface(0, 0, rsInterface, 8);
				} catch (Exception ex) {

				}

				if (!menuOpen) {
					processRightClick();
					drawTopLeftTooltip();
				} else {
					drawMenu(menuOffsetX, menuOffsetY);
				}
			}
			drawCount++;
			return;
		} else {
			if (drawCount != 0) {
				Rasterizer2D.clear();
			}
		}

		if (welcomeScreenRaised) {
			welcomeScreenRaised = false;
			inputTaken = true;
			tabAreaAltered = true;
		}
		if (backDialogID != -1) {
			try {
				processWidgetAnimations(tickDelta, backDialogID);
			} catch (Exception ex) {

			}
		}

		if (backDialogID == -1) {
			int scroller = chatAreaScrollLength - anInt1089 - 110;
			if(scroller < 0) {
				chatboxInterface.scrollPosition = 0;
			} else {
				chatboxInterface.scrollPosition = chatAreaScrollLength - anInt1089 - 110;
			}
			if(chatboxInterface.scrollPosition < 0)
				chatboxInterface.scrollPosition = 0;
			if (MouseHandler.mouseX >= 496 && MouseHandler.mouseX <= 511
					&& MouseHandler.mouseY > (!isResized() ? 345
					: canvasHeight - 158))
				handleScroller(
						494,
						110,
						MouseHandler.mouseX,

						MouseHandler.mouseY - (canvasHeight - 155),
						chatboxInterface,
						0,
						false,
						chatAreaScrollLength);
			int i = chatAreaScrollLength - 110 - chatboxInterface.scrollPosition;
			if (i < 0) {
				i = 0;
			}
			if (i > chatAreaScrollLength - 110) {
				i = chatAreaScrollLength - 110;
			}
			if (anInt1089 != i) {
				anInt1089 = i;
				inputTaken = true;
			}
		}
		if (backDialogID != -1) {
			boolean flag2 = false;

			try {
				flag2 = processWidgetAnimations(tickDelta, backDialogID);
			} catch(Exception ex) {
				ex.printStackTrace();
			}

			if (flag2) {
				inputTaken = true;
			}
		}
		if (atInventoryInterfaceType == 3)
			inputTaken = true;
		if (activeInterfaceType == 3)
			inputTaken = true;
		if (clickToContinueString != null)
			inputTaken = true;
		if (inputTaken) {
			inputTaken = false;
		}
		if (loadingStage == 2) {
			moveCameraWithPlayer();

			if (menuOpen) {
				drawMenu(menuOffsetX, menuOffsetY);
			}
			if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0) {
				tickDelta = 0;
				return;
			}
			String s;
			if (itemSelected == 1 && menuActionRow < 2)
				s = "Use " + selectedItemName + " with...";
			else if (spellSelected == 1 && menuActionRow < 2)
				s = spellTooltip + "...";
			else
				s =( menuManager.getMenuEntry(menuActionRow - 1).getTarget() == null ||  menuManager.getMenuEntry(menuActionRow - 1).getTarget().equalsIgnoreCase("null")) ? menuManager.getMenuEntry(menuActionRow - 1).getOption() : menuManager.getMenuEntry(menuActionRow - 1).getOption() + " " + menuManager.getMenuEntry(menuActionRow - 1).getTarget();

			//if (s != null && !s.contains("Walk here") && !menuOpen)
			//	drawHoverBox(MouseHandler.mouseX+10, MouseHandler.mouseY-10, s);


			if(getUserSettings().isInventoryContextMenu() && hintMenu && showTabComponents) {
				drawHintMenu(hintName,hintId, getUserSettings().getStartMenuColor());
			}
		}


		tickDelta = 0;
	}

	private boolean buildFriendsListMenu(RSInterface class9) {
		int i = class9.contentType;
		if (i >= 1 && i <= 200 || i >= 701 && i <= 900) {
			if (i >= 801)
				i -= 701;
			else if (i >= 701)
				i -= 601;
			else if (i >= 101)
				i -= 101;
			else
				i--;
			if (tabInterfaceIDs[tabID] != 42500) {
				MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
						.setOption("Remove ")
						.setTarget("@whi@" + friendsList[i])
						.setType(792);
				menuManager.addEntry(menuEntry);
				setMenuOptionCount(getMenuOptionCount() + 1);
				callbacks.post(new MenuEntryAdded(menuEntry));
				menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
						.setOption("Message")
						.setTarget("@whi@" + friendsList[i])
						.setType(639);
				menuManager.addEntry(menuEntry);
				setMenuOptionCount(getMenuOptionCount() + 1);
				callbacks.post(new MenuEntryAdded(menuEntry));
			}

			return true;
		}
		if (i >= 401 && i <= 500) {
			MenuEntry menuEntry = (MenuEntry) new MenuEntry(menuActionRow)
					.setOption("Remove ")
					.setTarget("@whi@" + class9.message)
					.setType(322);
			menuManager.addEntry(menuEntry);
			setMenuOptionCount(getMenuOptionCount() + 1);
			callbacks.post(new MenuEntryAdded(menuEntry));
			return true;
		} else {
			return false;
		}
	}

	private int hoverId;

	public void updateGraphicObjects() {
		GraphicObject gfxObj = (GraphicObject) incompleteAnimables.reverseGetFirst();
		for (; gfxObj != null; gfxObj = (GraphicObject) incompleteAnimables.reverseGetNext()) {
			if (gfxObj.gfxHeightLevel != plane || gfxObj.isFinished) {
				gfxObj.remove();
			} else {
					if (loopCycle >= gfxObj.gfxDisplayCycle) {
						gfxObj.update(tickDelta);
						if (gfxObj.isFinished) {
							gfxObj.remove();
						} else {
							scene.addAnimableA(gfxObj.gfxHeightLevel, 0, gfxObj.z, -1,
									gfxObj.y, 60, gfxObj.x,
									gfxObj, false);
						}
					}
			}
		}
	}

	public int dropdownInversionFlag;

	public Sprite grandExchangeSprite0 = new Sprite("Grand Exchange/0"),
			grandExchangeSprite1 = new Sprite("Grand Exchange/1"),
			grandExchangeSprite2 = new Sprite("Grand Exchange/2"),
			grandExchangeSprite3 = new Sprite("Grand Exchange/3"),
			grandExchangeSprite4 = new Sprite("Grand Exchange/4");

	private int interfaceDrawY;
	public boolean interfaceText = false;
	public boolean interfaceStringText = false;

	public static final int[] runeChildren = { 1202, 1203, 1209, 1210, 1211, 1218, 1219, 1220, 1227, 1228, 1234, 1235, 1236, 1243,
			1244, 1245, 1252, 1253, 1254, 1261, 1262, 1263, 1270, 1271, 1277, 1278, 1279, 1286, 1287, 1293,
			1294, 1295, 1302, 1303, 1304, 1311, 1312, 1318, 1319, 1320, 1327, 1328, 1329, 1336, 1337, 1343,
			1344, 1345, 1352, 1353, 1354, 1361, 1362, 1363, 1370, 1371, 1377, 1378, 1384, 1385, 1391, 1392,
			1393, 1400, 1401, 1407, 1408, 1410, 1417, 1418, 1424, 1425, 1426, 1433, 1434, 1440, 1441, 1442,
			1449, 1450, 1456, 1457, 1463, 1464, 1465, 1472, 1473, 1474, 1481, 1482, 1488, 1489, 1490, 1497,
			1498, 1499, 1506, 1507, 1508, 1515, 1516, 1517, 1524, 1525, 1526, 1533, 1534, 1535, 1547, 1548,
			1549, 1556, 1557, 1558, 1566, 1567, 1568, 1576, 1577, 1578, 1586, 1587, 1588, 1596, 1597, 1598,
			1605, 1606, 1607, 1616, 1617, 1618, 1627, 1628, 1629, 1638, 1639, 1640, 6007, 6008, 6011, 8673,
			8674, 12041, 12042, 12429, 12430, 12431, 12439, 12440, 12441, 12449, 12450, 12451, 12459, 12460,
			15881, 15882, 15885, 18474, 18475, 18478 };

	public static final int[] SOME_IDS = { 1196, 1199, 1206, 1215, 1224, 1231, 1240, 1249, 1258, 1267, 1274, 1283, 1573, 1290, 1299,
			1308, 1315, 1324, 1333, 1340, 1349, 1358, 1367, 1374, 1381, 1388, 1397, 1404, 1583, 12038, 1414,
			1421, 1430, 1437, 1446, 1453, 1460, 1469, 15878, 1602, 1613, 1624, 7456, 1478, 1485, 1494, 1503,
			1512, 1521, 1530, 1544, 1553, 1563, 1593, 1635, 12426, 12436, 12446, 12456, 6004, 18471,
			/* Ancients */
			12940, 12988, 13036, 12902, 12862, 13046, 12964, 13012, 13054, 12920, 12882, 13062, 12952, 13000,
			13070, 12912, 12872, 13080, 12976, 13024, 13088, 12930, 12892, 13096 };


	private static final int[] BOUNTY_INTERFACE_IDS = {
			21001, 28030, 28006, 28021, 28003, 28004, 28005, 28029,
			28023, 28024, 28025, 28026, 28027, 28028, 28001, 28022
	};

	public void drawInterface(int scrollPosition, int xPosition, RSInterface rsInterface, int yPosition) {
		drawInterface(scrollPosition, xPosition, rsInterface, yPosition, false);
	}

	public void drawInterface(int scrollPosition, int xPosition, RSInterface rsInterface, int yPosition, boolean inheritDrawingArea) {
		try {

			if (rsInterface.type != 0 || rsInterface.children == null) {
				handleWidgetEvents(rsInterface, 0, 0);
				return;
			}
			if (rsInterface.isMouseoverTriggered && anInt1026 != rsInterface.id && anInt1048 != rsInterface.id
					&& anInt1039 != rsInterface.id)
				return;

			if(rsInterface.id == HealthHud.WIDGET_ID) {
				if(RSInterface.interfaceCache[HealthHud.PROGRESS_WIDGET_ID] == null
				|| RSInterface.interfaceCache[HealthHud.PROGRESS_WIDGET_ID].message == null)
					return;
				if(!RSInterface.interfaceCache[HealthHud.PROGRESS_WIDGET_ID].message.contains("/"))
					return;
			}

			int clipLeft = Rasterizer2D.leftX;
			int clipTop = Rasterizer2D.topY;
			int clipRight = Rasterizer2D.bottomX;
			int clipBottom = Rasterizer2D.bottomY;

			// Sets the drawing area so things don't draw outside the boundary (scrollbars are a good example)
			// If you have a type 0 (container) inside another type 0 the latest one of the tree will set the drawing area
			// and possibly breaking scrolling (making things draw above or below the scrolling container)
			//if (rsInterface.id <= 24507)
			if (!inheritDrawingArea) {
				Rasterizer2D.setDrawingArea(yPosition + rsInterface.height, xPosition, xPosition + rsInterface.width, yPosition);
			}
			int childCount = rsInterface.children.length;
			for (int childId = 0; childId < childCount; childId++) {
				try {
					int _x = rsInterface.childX[childId] + xPosition  - (rsInterface.horizontalScroll ? scrollPosition : 0);
					int _y = (rsInterface.childY[childId] + yPosition)- (rsInterface.horizontalScroll ? 0 : scrollPosition);
					RSInterface class9_1 = RSInterface.interfaceCache[rsInterface.children[childId]];

					_x += class9_1.anInt263;
					_y += class9_1.anInt265;

					handleWidgetEvents(class9_1, _x, _y);
					if (class9_1.interfaceHidden)
						continue;
					if (class9_1.invisible) {
						continue;
					}

					if (!getUserSettings().isBountyHunter() && Arrays.stream(BOUNTY_INTERFACE_IDS).anyMatch(id -> id == class9_1.id)) {
						continue;
					}
					if (class9_1.contentType > 0)
						drawFriendsListOrWelcomeScreen(class9_1);

					if (class9_1.id != 28060 && class9_1.id != 28061) {
						for (int m5 = 0; m5 < SOME_IDS.length; m5++) {
							if (class9_1.id == SOME_IDS[m5] + 1) {
								if (m5 > 61) {
									drawBlackBox(_x + 1, _y);
								} else {
									drawBlackBox(_x, _y + 1);
								}
							}
						}
					}
					if (class9_1.id == 58073)
						if (this.modifiableXValue > 0) {
							class9_1.atActionType = 8;
							class9_1.tooltips = new String[] { "Set custom quantity", "Default quantity: " + this.modifiableXValue };
						} else {
							this.modifiableXValue = 1;
							class9_1.atActionType = 1;
							class9_1.tooltip = "Set custom quantity";
						}
					for (int r = 0; r < runeChildren.length; r++)
						if (class9_1.id == runeChildren[r])
							class9_1.modelZoom = 775;

					if (class9_1.type == 150) {
						class9_1.wheel.render(_x, _y);
					}
					if (class9_1.type == 554) {

						ProgressBar inter = (ProgressBar) class9_1;
						int displayColor = inter.colorTypes[inter.progressBarState];
						int defaultBarColor = inter.defaultColorTypes[inter.defaultBarState];

						if (inter.drawHorizontal) {
							int drawingWidth = inter.progressBarPercentage * inter.width / 100;
							Rasterizer2D.drawPixels(inter.height, _y, _x, defaultBarColor, inter.width);
							Rasterizer2D.drawPixels(inter.height, _y, _x, displayColor, drawingWidth);
							Rasterizer2D.fillPixels(_x, inter.width, inter.height, 0, _y);

							// DrawingArea.fillRect(childX, childY, inter.width, inter.height, defaultBarColor);
							//  DrawingArea.fillRect(childX, childY, drawingWidth, inter.height, displayColor);
							//  DrawingArea.drawRect(childX, childY, inter.width, inter.height, 0);
						} else {
							int drawingHeight = inter.progressBarPercentage * inter.height / 100;

							Rasterizer2D.fillRectangle(_x, _y, inter.width, inter.height, 0x00000);
							Rasterizer2D.fillRectangle(_x, _y, inter.width, drawingHeight, displayColor);
							Rasterizer2D.drawRectangle(_x, _y, inter.width, inter.height, 0);

							Rasterizer2D.drawPixels(inter.height, _y, _x, defaultBarColor, inter.width);
							Rasterizer2D.drawPixels(drawingHeight,  _y+ inter.height - drawingHeight, _x, displayColor, inter.width);
							Rasterizer2D.fillPixels(_x, inter.width, inter.height, 0, _y);
						}
					}
					if (class9_1.type == 0) {
						if (class9_1.horizontalScroll) {
							if (class9_1.scrollPosition > class9_1.scrollMax - class9_1.width)
								class9_1.scrollPosition = class9_1.scrollMax - class9_1.width;
						} else {
							if (class9_1.scrollPosition > class9_1.scrollMax - class9_1.height)
								class9_1.scrollPosition = class9_1.scrollMax - class9_1.height;
						}
						if (class9_1.scrollPosition < 0)
							class9_1.scrollPosition = 0;
						drawInterface(class9_1.scrollPosition, _x, class9_1, _y, inheritDrawingArea || rsInterface.scrollMax > 0);
						if (class9_1.horizontalScroll) {
							if (class9_1.scrollMax > class9_1.width) {
								if (scrollbarVisible(class9_1)) {
									drawScrollbarBottom(class9_1.width, class9_1.scrollPosition, _y + class9_1.height, _x,
											class9_1.scrollMax, false, false);
								}
							}

						} else {
						// Hardcodes
						if (class9_1.scrollMax > class9_1.height) {
							// clan chat
							if (class9_1.id == 18143) {
								int clanMates = 0;
								for (int i = 18155; i < 18244; i++) {
									RSInterface line = RSInterface.interfaceCache[i];
									if (line.message.length() > 0) {
										clanMates++;
									}
								}
								class9_1.scrollMax = (clanMates * 14) + class9_1.height + 1;
							}
							if (class9_1.id == 18322 || class9_1.id == 18423) {
								int members = 0;
								for (int i = class9_1.id + 1; i < class9_1.id + 1 + 100; i++) {
									RSInterface line = RSInterface.interfaceCache[i];
									if (line != null && line.message != null) {
										if (line.message.length() > 0) {
											members++;
										}
									}
								}
								class9_1.scrollMax = (members * 14) + 1;
							}
							if (rsInterface.parentID == 49000 || rsInterface.parentID == 49100 || rsInterface.parentID == 51100
									|| rsInterface.parentID == 53100) {
								int scrollMax = class9_1.scrollMax;

								if (achievementCutoff > 1) {
									scrollMax = achievementCutoff * 65;
								} else {
									achievementCutoff = 282;
								}
								class9_1.scrollMax = scrollMax;
							}
							if (scrollbarVisible(class9_1)) {
								drawScrollbar(class9_1.height, class9_1.scrollPosition, _y, _x + class9_1.width,
										class9_1.scrollMax);
							}
						}
					}
					} else if (class9_1.type != 1)
						if (class9_1.type == 2) {
							if (interfaceText) {
								newSmallFont.drawBasicString("Container: " + class9_1.id, _x, _y);
							}
							try {
								Bank.setupMainTab(class9_1, _x, _y);
							} catch (Exception e) {
								e.printStackTrace();
							}

							// Item container
							if (class9_1.invAutoScrollHeight) {
								int lastRow = -1;
								int rowCount = 0;
								int i3 = 0;

								for (int l3 = 0; l3 < class9_1.height; l3++) {
									for (int l4 = 0; l4 < class9_1.width; l4++) {
										if (class9_1.inventoryItemId[i3] > 1) {
											if (lastRow != l3) {
												lastRow = l3;
												rowCount++;
											}
										}

										i3++;
									}
								}

								RSInterface scrollable = RSInterface.interfaceCache[class9_1.invAutoScrollInterfaceId];
								scrollable.scrollMax = scrollable.height + 1;
								int heightPerRow = class9_1.invSpritePadY + 32;
								if (heightPerRow * rowCount > scrollable.scrollMax) {
									scrollable.scrollMax += (heightPerRow * rowCount) - scrollable.scrollMax + scrollable.invAutoScrollHeightOffset;
								}
							}

							int i3 = 0;
							for (int l3 = 0; l3 < class9_1.height; l3++) {
								for (int l4 = 0; l4 < class9_1.width; l4++) {
									// System.out.println("id? " + class9_1.inv[i3]);
									int k5 = _x + l4 * (32 + class9_1.invSpritePadX);
									int j6 = _y + l3 * (32 + class9_1.invSpritePadY);
									if (i3 < 20) {
										k5 += class9_1.spritesX[i3];
										j6 += class9_1.spritesY[i3];
									}
									if (class9_1.inventoryItemId[i3] > 0) {
										if (class9_1.id == Interfaces.SHOP_CONTAINER) {
											if (hoverShopTile == i3) {
												shopSpriteHover.drawSprite(k5 - 7, j6 - 4);
											} else {
												shopSprite.drawSprite(k5 - 7, j6 - 4);
											}
										}
										int k6 = 0;
										int j7 = 0;
										int j9 = class9_1.inventoryItemId[i3] - 1;
/*										if (class9_1.id == 23231) {
											j9 = (class9_1.inventoryItemId[i3] & 0x7FFF) - 1;
										}*/
										if (k5 > Rasterizer2D.leftX - 32 && k5 < Rasterizer2D.bottomX && j6 > Rasterizer2D.topY - 32
												&& j6 < Rasterizer2D.bottomY || activeInterfaceType != 0 && itemDraggingSlot == i3) {
											int l9 = 0;
											if (itemSelected == 1 && anInt1283 == i3 && anInt1284 == class9_1.id)
												l9 = 0xffffff;

											int itemSpriteOpacity = 256;
											/**
											 * Placeholder opacity editing
											 */
											if (class9_1.inventoryAmounts[i3] <= 0)
												itemSpriteOpacity = 100;

											Sprite itemSprite = null;

											if (openInterfaceID == 29875) {
												if (class9_1.inventoryItemId[i3] < 555
														|| class9_1.inventoryItemId[i3] > 567 && class9_1.inventoryItemId[i3] != 9076) {
													itemSpriteOpacity = 100;
												}
											}

											if (class9_1.id == 23121) {
												final boolean isLastBitEnabled = ((class9_1.inventoryItemId[i3] >> 15) & 0x1) == 1;
												if (isLastBitEnabled) {
													itemSpriteOpacity = 90;
												}
											}

											boolean smallSprite = openInterfaceID == 26000
													&& GrandExchange.isSmallItemSprite(class9_1.id) || class9_1.smallInvSprites;

											ItemDefinition itemDef = ItemDefinition.lookup(j9);
											if (smallSprite) {
												itemSprite = ItemDefinition.getSmallSprite(j9, class9_1.inventoryAmounts[i3]);
												if (itemDef.customSmallSpriteLocation != null)
												{
													itemSprite = new Sprite(itemDef.customSmallSpriteLocation);
												} else if (itemDef.customSpriteLocation != null)
												{
													itemSprite = new Sprite(itemDef.customSpriteLocation);
												}
											} else {
												itemSprite = ItemDefinition.getSprite(j9, class9_1.inventoryAmounts[i3], l9);
												if (itemDef.customSpriteLocation != null)
												{
													itemSprite = new Sprite(itemDef.customSpriteLocation);
												}
											}

											if (class9_1.id >= 32212 && class9_1.id <= 32212 + 11) {
												if (class9_1.inventoryItemId[i3] > 0) {
													if (class9_1.getSprite2() != null) {
														class9_1.getSprite2().drawSprite(k5 + k6 - 2, j6 + j7 - 2);
													}
												}
											}
											if (itemSprite != null) {
												if (activeInterfaceType != 0 && itemDraggingSlot == i3 && draggingItemInterfaceId == class9_1.id) {
													k6 = MouseHandler.mouseX - anInt1087;
													j7 = MouseHandler.mouseY - anInt1088;
													if (k6 < 5 && k6 > -5)
														k6 = 0;
													if (j7 < 5 && j7 > -5)
														j7 = 0;
													if (anInt989 < getDragSetting(class9_1.id)) {
														k6 = 0;
														j7 = 0;
													}
													itemSprite.drawTransparentSprite(k5 + k6, j6 + j7, 128);
													if (j6 + j7 < Rasterizer2D.topY && rsInterface.scrollPosition > 0) {
														int i10 = (tickDelta * (Rasterizer2D.topY - j6 - j7)) / 3;
														if (i10 > tickDelta * 10)
															i10 = tickDelta * 10;
														if (i10 > rsInterface.scrollPosition)
															i10 = rsInterface.scrollPosition;
														rsInterface.scrollPosition -= i10;
														anInt1088 += i10;
													}
													if (j6 + j7 + 32 > Rasterizer2D.bottomY
															&& rsInterface.scrollPosition < rsInterface.scrollMax
															- rsInterface.height) {
														int j10 = (tickDelta * ((j6 + j7 + 32) - Rasterizer2D.bottomY)) / 3;
														if (j10 > tickDelta * 10)
															j10 = tickDelta * 10;
														if (j10 > rsInterface.scrollMax - rsInterface.height
																- rsInterface.scrollPosition)
															j10 = rsInterface.scrollMax - rsInterface.height
																	- rsInterface.scrollPosition;
														rsInterface.scrollPosition += j10;
														anInt1088 -= j10;
													}
												} else if (atInventoryInterfaceType != 0 && atInventoryIndex == i3
														&& atInventoryInterface == class9_1.id)
													itemSprite.drawTransparentSprite(k5, j6, 128);
												else
												// itemSprite.drawSprite(k5, j6);
												/**
												 * Draws item sprite
												 */
													itemSprite.drawTransparentSprite(k5, j6, itemSpriteOpacity);
												if (class9_1.id == RSInterface.selectedItemInterfaceId
														&& class9_1.itemSearchSelectedSlot > -1
														&& class9_1.itemSearchSelectedSlot == i3) {
													for (int i = 32; i > 0; i--) {
														Rasterizer2D.method338(j6 + j7, i, 256 - Byte.MAX_VALUE, 0x395D84, i,
																k5 + k6);
													}
													Rasterizer2D.method338(j6 + j7, 32, 256, 0x395D84, 32, k5 + k6);
												}

												if (class9_1.forceInvStackSizes || !smallSprite && !class9_1.hideInvStackSizes) {
													if (class9_1.parentID < 58040 || class9_1.parentID > 58048) {
														if (itemSprite.maxWidth == 33 || class9_1.inventoryAmounts[i3] != 1) {
															if (class9_1.id != 23121 || class9_1.id == 23121 && ((class9_1.inventoryItemId[i3] >> 15) & 0x1) == 0) {
																if (class9_1.invAlwaysInfinity || class9_1.id == Interfaces.SHOP_CONTAINER && class9_1.inventoryAmounts[i3] == 1_000_000_000) {
																	infinity.drawSprite(k5 + k6, j6 + j7);
																} else {
																	int k10 = class9_1.inventoryAmounts[i3];
																	int xOffset = class9_1.forceInvStackSizes ? 10 : 0;
																	if (k10 >= 1)
																		smallText.method385(0xFFFF00, intToKOrMil(k10), j6 + 9 + j7,
																				xOffset+ k5 + k6);
																	smallText.method385(0, intToKOrMil(k10), j6 + 10 + j7, xOffset+ k5 + 1 + k6);
																	if (k10 > 99999 && k10 < 10000000) {
																		smallText.method385(0xFFFFFF, intToKOrMil(k10), j6 + 9 + j7,
																				xOffset+ k5 + k6);
																	} else if (k10 > 9999999) {
																		smallText.method385(0x00ff80, intToKOrMil(k10), j6 + 9 + j7,
																				xOffset+ k5 + k6);
																	} else {
																		smallText.method385(0xFFFF00, intToKOrMil(k10), j6 + 9 + j7,
																				xOffset+ k5 + k6);
																	}
																}
															}
														}
													}
												}
											}
										}
									} else if (class9_1.getSprite3(i3) != null && i3 < 20) {
										Sprite itemContainerSprite = class9_1.getSprite3(i3);
										if (itemContainerSprite != null)
											itemContainerSprite.drawSprite(k5, j6);
									}
									i3++;
								}
							}
						} else if (class9_1.type == 3) {
							boolean flag = false;
							if (anInt1039 == class9_1.id || anInt1048 == class9_1.id || anInt1026 == class9_1.id)
								flag = true;
							int colour;
							if (interfaceIsSelected(class9_1)) {
								colour = class9_1.secondaryColor;
								if (flag && class9_1.anInt239 != 0)
									colour = class9_1.anInt239;
							} else {
								colour = class9_1.textColor;
								if (flag && class9_1.anInt216 != 0)
									colour = class9_1.anInt216;
							}

							if (MouseHandler.mouseX > _x
									&& MouseHandler.mouseX < _x + class9_1.width
									&& MouseHandler.mouseY > _y && MouseHandler.mouseY < _y + class9_1.height) {
								colour = class9_1.secondaryColor;
							}
							if (class9_1.opacity == 0) {
								if (class9_1.filled)
									Rasterizer2D.drawBox(_x, _y, class9_1.width, class9_1.height, colour
									);
								else
									Rasterizer2D.drawBoxOutline(_x, _y, class9_1.width,
											class9_1.height, colour);
							} else if (class9_1.filled)
								Rasterizer2D.drawTransparentBox(_x, _y, class9_1.width, class9_1.height, colour,
										256 - (class9_1.opacity & 0xff));
							else
								Rasterizer2D.drawTransparentBoxOutline(_x, _y, class9_1.width, class9_1.height,
										colour, 256 - (class9_1.opacity & 0xff)
								);
						} else if (class9_1.type == 4 || class9_1.type == RSInterface.TYPE_TEXT_DRAW_FROM_LEFT) {
							TextDrawingArea textDrawingArea = class9_1.textDrawingAreas;
							String s = class9_1.message;
							if (interfaceStringText) {
								s = class9_1.id + "";
							}
							boolean flag1 = false;
							if (anInt1039 == class9_1.id || anInt1048 == class9_1.id || anInt1026 == class9_1.id)
								flag1 = true;
							int i4 = 0;
							if (interfaceIsSelected(class9_1)) {
								i4 = class9_1.secondaryColor;
								if (flag1 && class9_1.anInt239 != 0)
									i4 = class9_1.anInt239;
								if (class9_1.aString228.length() > 0)
									s = class9_1.aString228;
							} else {
								i4 = class9_1.textColor;
								if (flag1 && class9_1.anInt216 != 0) {
									i4 = class9_1.anInt216;
									if (i4 == 49152)
										i4 = 16777215;
								}
							}
							if (class9_1.atActionType == 6 && aBoolean1149) {
								s = "Please wait...";
								i4 = 255;
							}
							if ((class9_1.parentID == 1151) || (class9_1.parentID == 12855)) {
								switch (i4) {
									case 16773120:
										i4 = 0xFE981F;
										break;
									case 7040819:
										i4 = 0xAF6A1A;
										break;
								}
							}
							if (class9_1.hoverText != null && !class9_1.hoverText.isEmpty()) {
								if (MouseHandler.mouseX > _x
										&& MouseHandler.mouseX < _x + class9_1.textDrawingAreas.getTextWidth(class9_1.message)
										&& MouseHandler.mouseY > _y && MouseHandler.mouseY < _y + 15) {
									s = class9_1.hoverText;
									i4 = class9_1.hoverTextColor;
								}
							}
							if((backDialogID != -1 || dialogID != -1 || class9_1.message.contains("Click here to continue")) &&
									(rsInterface.id == backDialogID || rsInterface.id == dialogID )){
								if(i4 == 0xffff00)
									i4 = 255;
								if(i4 == 49152)
									i4 = 0xffffff;
							}
							for (int l6 = _y + textDrawingArea.anInt1497; s.length() > 0; l6 += textDrawingArea.anInt1497) {
								if (s.indexOf("%") != -1) {
									do {
										int k7 = s.indexOf("%1");
										if (k7 == -1)
											break;
										if (class9_1.id < 4000 || class9_1.id > 5000 && class9_1.id != 13921
												&& class9_1.id != 13922 && class9_1.id != 12171 && class9_1.id != 12172)
											s = s.substring(0, k7) + methodR(extractInterfaceValues(class9_1, 0))
													+ s.substring(k7 + 2);
										else
											s = s.substring(0, k7) + interfaceIntToString(extractInterfaceValues(class9_1, 0))
													+ s.substring(k7 + 2);
									} while (true);
									do {
										int l7 = s.indexOf("%2");
										if (l7 == -1)
											break;
										s = s.substring(0, l7) + interfaceIntToString(extractInterfaceValues(class9_1, 1))
												+ s.substring(l7 + 2);
									} while (true);
									do {
										int i8 = s.indexOf("%3");
										if (i8 == -1)
											break;
										s = s.substring(0, i8) + interfaceIntToString(extractInterfaceValues(class9_1, 2))
												+ s.substring(i8 + 2);
									} while (true);
									do {
										int j8 = s.indexOf("%4");
										if (j8 == -1)
											break;
										s = s.substring(0, j8) + interfaceIntToString(extractInterfaceValues(class9_1, 3))
												+ s.substring(j8 + 2);
									} while (true);
									do {
										int k8 = s.indexOf("%5");
										if (k8 == -1)
											break;
										s = s.substring(0, k8) + interfaceIntToString(extractInterfaceValues(class9_1, 4))
												+ s.substring(k8 + 2);
									} while (true);
								}
								int l8 = s.indexOf("\\n");
								String s1;
								if (l8 != -1) {
									s1 = s.substring(0, l8);
									s = s.substring(l8 + 2);
								} else {
									s1 = s;
									s = "";
								}
								RSFont font = null;
								if (textDrawingArea == smallText) {
									font = newSmallFont;
								} else if (textDrawingArea == aTextDrawingArea_1271) {
									font = newRegularFont;
								} else if (textDrawingArea == chatTextDrawingArea) {
									font = newBoldFont;
								} else if (textDrawingArea == aTextDrawingArea_1273) {
									font = newFancyFont;
								}
								if (rsInterface.parentID == 49100 || rsInterface.parentID == 51100 || rsInterface.parentID == 53100) {
									int parent = rsInterface.parentID == 49100 ? 49100 : rsInterface.parentID == 51100 ? 51100 : 53100;
									int subId = (class9_1.id - parent) % 100;
									if (subId > achievementCutoff) {
										continue;
									}
								}
								if (interfaceStringText) {
									s1 = "" + class9_1.id;
								}
								if(class9_1.wrapText) {
									font.drawInterfaceText(s1, _x, l6,
											class9_1.width, 0,i4, class9_1.textShadow ? 0 : -1, 255, 1, 0, 0);
								} else {
									if (class9_1.type == RSInterface.TYPE_TEXT_DRAW_FROM_LEFT) {
										int width = font.getTextWidth(s1);
										font.drawBasicString(s1, _x - width, l6, i4, class9_1.textShadow ? 0 : -1);
									} else if (class9_1.centerText) {
										font.drawCenteredString(s1, _x + class9_1.width / 2, l6, i4, class9_1.textShadow ? 0 : -1);
									} else {
										font.drawBasicString(s1, _x, l6, i4, class9_1.textShadow ? 0 : -1);
									}
								}
							}
						} else if (class9_1.type == 5) {
							Sprite sprite;
							if(interfaceIsSelected(class9_1) || class9_1.active) {
								sprite = class9_1.getSprite2();
							} else {
								sprite = class9_1.getSprite1();
							}



							if(spellSelected == 1 && class9_1.id == spellID && spellID != 0 && sprite != null) {
								sprite.drawSprite(_x, _y, 0xffffff);
							}

							if(sprite != null) {
								if (class9_1.drawsTransparent) {
									sprite.drawTransparentSprite(_x, _y, class9_1.transparency);
								} else {
									if(sprite.advancedSprite) {
										sprite.drawAdvancedSprite(_x, _y);
									} else {
										sprite.drawSprite(_x, _y);
									}
								}
							}
						} else if (class9_1.type == 6) {
							Rasterizer3D.renderOnGpu = true;
							int k3 = Rasterizer3D.originViewX;
							int j4 = Rasterizer3D.originViewY;
							Rasterizer3D.originViewX = _x + class9_1.width / 2;
							Rasterizer3D.originViewY = _y + class9_1.height / 2;
							int i5 = Rasterizer3D.SINE[class9_1.modelRotation1] * class9_1.modelZoom >> 16;
							int l5 = Rasterizer3D.COSINE[class9_1.modelRotation1] * class9_1.modelZoom >> 16;
							boolean flag2 = interfaceIsSelected(class9_1);
							int i7;
							if (flag2)
								i7 = class9_1.enabledAnimationId;
							else
								i7 = class9_1.disabledAnimationId;
							Model model;
							if (i7 == -1) {
								model = class9_1.method209(-1, -1, flag2);
							} else {
								SeqDefinition animation = SeqDefinition.get(i7);
								if (class9_1.anInt246 >= animation.secondaryFrameIds.length || class9_1.anInt246 >= animation.frameIDs.length) {
									class9_1.anInt246 = 0; // Fixes array index out of bounds on npc dialogues
								}

								model = class9_1.method209(animation.secondaryFrameIds[class9_1.anInt246],
									animation.frameIDs[class9_1.anInt246], flag2);
							}
							if (model != null) {
								Rasterizer3D.world = false;
								model.renderModel(class9_1.modelRotation2, 0, class9_1.modelRotation1, 0, i5, l5);
								Rasterizer3D.world = true;
							}

							Rasterizer3D.originViewX = k3;
							Rasterizer3D.originViewY = j4;
							Rasterizer3D.renderOnGpu = false;
						} else if (class9_1.type == 7) {
							TextDrawingArea textDrawingArea_1 = class9_1.textDrawingAreas;
							int k4 = 0;
							for (int j5 = 0; j5 < class9_1.height; j5++) {
								for (int i6 = 0; i6 < class9_1.width; i6++) {
									if (class9_1.inventoryItemId[k4] > 0) {
										ItemDefinition itemDef = ItemDefinition.lookup(class9_1.inventoryItemId[k4] - 1);
										String s2 = itemDef.name;
										if (itemDef.stackable || class9_1.inventoryAmounts[k4] != 1)
											s2 = s2 + " x" + intToKOrMilLongName(class9_1.inventoryAmounts[k4]);
										int i9 = _x + i6 * (115 + class9_1.invSpritePadX);
										int k9 = _y + j5 * (12 + class9_1.invSpritePadY);
										if (class9_1.centerText)
											textDrawingArea_1.method382(class9_1.textColor, i9 + class9_1.width / 2, s2, k9,
													class9_1.textShadow);
										else
											textDrawingArea_1.method389(class9_1.textShadow, i9, class9_1.textColor, s2, k9);
									}
									k4++;
								}
							}
						} else if (class9_1.type == 8
								&& (anInt1500 == class9_1.id || anInt1044 == class9_1.id || anInt1129 == class9_1.id)
								&& anInt1501 == 50 && !menuOpen) {
							if (class9_1.parentID == 3917) {
								return;
							}
							int boxWidth = 0;
							int boxHeight = 0;

							/**
							 * Skill tab hovers Remove "next level at" and "remaining" for xp if we're level
							 * 99.
							 */

							TextDrawingArea textDrawingArea_2 = aTextDrawingArea_1271;
							for (String s1 = class9_1.message; s1.length() > 0;) {
								if (s1.indexOf("%") != -1) {
									do {
										int k7 = s1.indexOf("%1");
										if (k7 == -1)
											break;
										s1 = s1.substring(0, k7) + interfaceIntToString(extractInterfaceValues(class9_1, 0))
												+ s1.substring(k7 + 2);
									} while (true);
									do {
										int l7 = s1.indexOf("%2");
										if (l7 == -1)
											break;
										s1 = s1.substring(0, l7) + interfaceIntToString(extractInterfaceValues(class9_1, 1))
												+ s1.substring(l7 + 2);
									} while (true);
									do {
										int i8 = s1.indexOf("%3");
										if (i8 == -1)
											break;
										s1 = s1.substring(0, i8) + interfaceIntToString(extractInterfaceValues(class9_1, 2))
												+ s1.substring(i8 + 2);
									} while (true);
									do {
										int j8 = s1.indexOf("%4");
										if (j8 == -1)
											break;
										s1 = s1.substring(0, j8) + interfaceIntToString(extractInterfaceValues(class9_1, 3))
												+ s1.substring(j8 + 2);
									} while (true);
									do {
										int k8 = s1.indexOf("%5");
										if (k8 == -1)
											break;
										s1 = s1.substring(0, k8) + interfaceIntToString(extractInterfaceValues(class9_1, 4))
												+ s1.substring(k8 + 2);
									} while (true);
								}
								int l7 = s1.indexOf("\\n");

								String s4;
								if (l7 != -1) {
									s4 = s1.substring(0, l7);
									s1 = s1.substring(l7 + 2);
								} else {
									s4 = s1;
									s1 = "";
								}
								int j10 = textDrawingArea_2.getTextWidth(s4);
								if (j10 > boxWidth) {
									boxWidth = j10;
								}
								boxHeight += textDrawingArea_2.anInt1497 + 1;
							}

							if (tabInterfaceIDs[tabID] == 17200) {
								return;
							}
							boxWidth += 6;
							boxHeight += 7;
							int xPos = (_x + class9_1.width) - 5 - boxWidth;
							int yPos = _y + class9_1.height + 5;
							if (xPos < _x + 5)
								xPos = _x + 5;
							if (xPos + boxWidth > xPosition + rsInterface.width)
								xPos = (xPosition + rsInterface.width) - boxWidth;
							if (yPos + boxHeight > yPosition + rsInterface.height)
								yPos = (_y - boxHeight);
							switch (class9_1.id) {
								case 9217:
								case 9220:
								case 9223:
								case 9226:
								case 9229:
								case 9232:
								case 9235:
								case 9238:
									xPos -= 80;
									break;
								case 9239:
									yPos -= 100;
									break;
							}
							if (class9_1.inventoryhover) {

								//System.out.println(canvasWidth - 648);
								if (xPos + boxWidth > canvasWidth - 8 - boxWidth + 100) {
									xPos = canvasWidth - 8 - boxWidth;
								}
								if (yPos + boxHeight > canvasHeight - 118 - boxHeight + 100
										&& yPos + boxHeight < canvasHeight - 118 - boxHeight + 120) {
									yPos = canvasHeight - 148 - boxHeight;
								} else if (yPos + boxHeight > canvasHeight - 118 - boxHeight + 100) {
									yPos = canvasHeight - 118 - boxHeight;
								}
							}
							Rasterizer2D.drawPixels(boxHeight, yPos, xPos, 0xFFFFA0, boxWidth);
							Rasterizer2D.fillPixels(xPos, boxWidth, boxHeight, 0, yPos);
							String s2 = class9_1.message;
							for (int j11 = yPos + textDrawingArea_2.anInt1497 + 2; s2
									.length() > 0; j11 += textDrawingArea_2.anInt1497 + 1) {// anInt1497
								if (s2.indexOf("%") != -1) {
									do {
										int k7 = s2.indexOf("%1");
										if (k7 == -1)
											break;
										s2 = s2.substring(0, k7) + interfaceIntToString(extractInterfaceValues(class9_1, 0))
												+ s2.substring(k7 + 2);
									} while (true);
									do {
										int l7 = s2.indexOf("%2");
										if (l7 == -1)
											break;
										s2 = s2.substring(0, l7) + interfaceIntToString(extractInterfaceValues(class9_1, 1))
												+ s2.substring(l7 + 2);
									} while (true);
									do {
										int i8 = s2.indexOf("%3");
										if (i8 == -1)
											break;
										s2 = s2.substring(0, i8) + interfaceIntToString(extractInterfaceValues(class9_1, 2))
												+ s2.substring(i8 + 2);
									} while (true);
									do {
										int j8 = s2.indexOf("%4");
										if (j8 == -1)
											break;
										s2 = s2.substring(0, j8) + interfaceIntToString(extractInterfaceValues(class9_1, 3))
												+ s2.substring(j8 + 2);
									} while (true);
									do {
										int k8 = s2.indexOf("%5");
										if (k8 == -1)
											break;
										s2 = s2.substring(0, k8) + interfaceIntToString(extractInterfaceValues(class9_1, 4))
												+ s2.substring(k8 + 2);
									} while (true);
								}
								int l11 = s2.indexOf("\\n");
								String s5;
								if (l11 != -1) {
									s5 = s2.substring(0, l11);
									s2 = s2.substring(l11 + 2);
								} else {
									s5 = s2;
									s2 = "";
								}
								if (class9_1.centerText) {
									textDrawingArea_2.method382(yPos, xPos + class9_1.width / 2, s5, j11, false);
								} else {
									if (s5.contains("\\r")) {
										String text = s5.substring(0, s5.indexOf("\\r"));
										String text2 = s5.substring(s5.indexOf("\\r") + 2);
										textDrawingArea_2.method389(false, xPos + 3, 0, text, j11);
										int rightX = boxWidth + xPos - textDrawingArea_2.getTextWidth(text2) - 2;
										textDrawingArea_2.method389(false, rightX, 0, text2, j11);
									} else
										textDrawingArea_2.method389(false, xPos + 3, 0, s5, j11);
								}
							}

						} else if (class9_1.type == RSInterface.TYPE_HOVER || class9_1.type == RSInterface.TYPE_CONFIG_HOVER) {
							// Draw sprite
							boolean flag = false;

							if (class9_1.toggled) {
								class9_1.getSprite1().drawAdvancedSprite(_x, _y, class9_1.spriteOpacity);
								flag = true;
								class9_1.toggled = false;
							} else {
								class9_1.getSprite2().drawAdvancedSprite(_x, _y, class9_1.spriteOpacity);
							}

							// Draw text
							if (class9_1.message == null) {
								continue;
							}
							if (class9_1.centerText) {
								newRegularFont.drawCenteredString(class9_1.message, _x + class9_1.msgX, _y + class9_1.msgY,
										flag ? class9_1.anInt216 : class9_1.textColor, 0x000000);

								// class9_1.rsFont.drawCenteredString(class9_1.message, _x + class9_1.msgX, _y +
								// class9_1.msgY,
								// flag ? class9_1.anInt216 : class9_1.textColor, 0);
							} else {
								newRegularFont.drawCenteredString(class9_1.message, _x + class9_1.msgX, _y + class9_1.msgY,
										flag ? class9_1.anInt216 : class9_1.textColor, 0x000000);
								// class9_1.rsFont.drawBasicString(class9_1.message, _x + 5, _y + class9_1.msgY,
								// flag ? class9_1.anInt216 : class9_1.textColor, 0);
							}
						} else if (class9_1.type == RSInterface.TYPE_CONFIG) {
							Sprite sprite = class9_1.active ? class9_1.getSprite2() : class9_1.getSprite1();
							sprite.drawSprite(_x, _y);
						} else if (class9_1.type == RSInterface.TYPE_SLIDER) {
							Slider slider = class9_1.slider;
							if (slider != null) {
								slider.draw(_x, _y, 255);
							}
						} else if (class9_1.type == RSInterface.TYPE_DROPDOWN) {

							DropdownMenu d = class9_1.dropdown;

							int bgColour = class9_1.dropdownColours[2];
							int fontColour = 0xfe971e;
							int downArrow = 30;

							if (class9_1.hovered || d.isOpen()) {
								downArrow = 31;
								fontColour = 0xffb83f;
								bgColour = class9_1.dropdownColours[3];
							}

							Rasterizer2D.drawPixels(20, _y, _x, class9_1.dropdownColours[0], d.getWidth());
							Rasterizer2D.drawPixels(18, _y + 1, _x + 1, class9_1.dropdownColours[1], d.getWidth() - 2);
							Rasterizer2D.drawPixels(16, _y + 2, _x + 2, bgColour, d.getWidth() - 4);

							int xOffset = class9_1.centerText ? 3 : 16;
							if (rsInterface.id == 41900) {
								newRegularFont.drawCenteredString(d.getSelected(), _x + (d.getWidth() - xOffset) / 2, _y + 14,
										fontColour, 0);
							} else {
								newSmallFont.drawCenteredString(d.getSelected(), _x + (d.getWidth() - xOffset) / 2, _y + 14,
										fontColour, 0);
							}

							if (d.isOpen()) {
								// Up arrow
								cacheSprite3[29].drawSprite(_x + d.getWidth() - 18, _y + 2);

								Rasterizer2D.drawPixels(d.getHeight(), _y + 19, _x, class9_1.dropdownColours[0], d.getWidth());
								Rasterizer2D.drawPixels(d.getHeight() - 2, _y + 20, _x + 1, class9_1.dropdownColours[1],
										d.getWidth() - 2);
								Rasterizer2D.drawPixels(d.getHeight() - 4, _y + 21, _x + 2, class9_1.dropdownColours[3],
										d.getWidth() - 4);

								int yy = 2;
								for (int i = 0; i < d.getOptions().length; i++) {
									if (class9_1.dropdownHover == i) {
										if (class9_1.id == 28102) {
											Rasterizer2D.drawAlphaBox(_x + 2, _y + 19 + yy, d.getWidth() - 4, 13, 0xd0914d, 80);
										} else {
											Rasterizer2D.drawPixels(13, _y + 19 + yy, _x + 2, class9_1.dropdownColours[4],
													d.getWidth() - 4);
										}
										if (rsInterface.id == 41900) {
											newRegularFont.drawCenteredString(d.getOptions()[i],
													_x + (d.getWidth() - xOffset) / 2, _y + 29 + yy, 0xffb83f, 0);
										} else {
											newSmallFont.drawCenteredString(d.getOptions()[i],
													_x + (d.getWidth() - xOffset) / 2, _y + 29 + yy, 0xffb83f, 0);
										}

									} else {
										Rasterizer2D.drawPixels(13, _y + 19 + yy, _x + 2, class9_1.dropdownColours[3],
												d.getWidth() - 4);
										if (rsInterface.id == 41900) {
											newRegularFont.drawCenteredString(d.getOptions()[i],
													_x + (d.getWidth() - xOffset) / 2, _y + 29 + yy, 0xfe971e, 0);
										} else {
											newSmallFont.drawCenteredString(d.getOptions()[i],
													_x + (d.getWidth() - xOffset) / 2, _y + 29 + yy, 0xfe971e, 0);
										}

									}
									yy += 14;
								}
								drawScrollbar(d.getHeight() - 4, class9_1.scrollPosition, _y + 21, _x + d.getWidth() - 18,
										d.getHeight() - 5);

							} else {
								cacheSprite3[downArrow].drawSprite(_x + d.getWidth() - 18, _y + 2);
							}
						} else if (class9_1.type == RSInterface.TYPE_KEYBINDS_DROPDOWN) {

							DropdownMenu d = class9_1.dropdown;

							// If dropdown inverted, don't draw following 2 menus
							if (dropdownInversionFlag > 0) {
								dropdownInversionFlag--;
								continue;
							}

							Rasterizer2D.drawPixels(18, _y + 1, _x + 1, 0x544834, d.getWidth() - 2);
							Rasterizer2D.drawPixels(16, _y + 2, _x + 2, 0x2e281d, d.getWidth() - 4);
							newRegularFont.drawBasicString(d.getSelected(), _x + 7, _y + 15, 0xff8a1f, 0);
							cacheSprite3[82].drawSprite(_x + d.getWidth() - 18, _y + 2); // Arrow TODO

							if (d.isOpen()) {

								RSInterface.interfaceCache[class9_1.id - 1].active = true; // Alter stone colour

								int yPos = _y + 18;

								// Dropdown inversion for lower stones
								if (class9_1.inverted) {
									yPos = _y - d.getHeight() - 10;
									dropdownInversionFlag = 2;
								}

								Rasterizer2D.drawPixels(d.getHeight() + 12, yPos, _x + 1, 0x544834, d.getWidth() - 2);
								Rasterizer2D.drawPixels(d.getHeight() + 10, yPos + 1, _x + 2, 0x2e281d, d.getWidth() - 4);

								int yy = 2;
								int xx = 0;
								int bb = d.getWidth() / 2;

								for (int i = 0; i < d.getOptions().length; i++) {

									int fontColour = 0xff981f;
									if (class9_1.dropdownHover == i) {
										fontColour = 0xffffff;
									}

									if (xx == 0) {
										newRegularFont.drawBasicString(d.getOptions()[i], _x + 5, yPos + 14 + yy, fontColour,
												0x2e281d);
										xx = 1;

									} else {
										newRegularFont.drawBasicString(d.getOptions()[i], _x + 5 + bb, yPos + 14 + yy,
												fontColour, 0x2e281d);
										xx = 0;
										yy += 15;
									}
								}
							} else {
								RSInterface.interfaceCache[class9_1.id - 1].active = false;
							}
						} else if (class9_1.type == RSInterface.TYPE_ADJUSTABLE_CONFIG) {
							if (class9_1.id != 37010) {
								Rasterizer2D.setDrawingArea(canvasHeight, 0, canvasHeight, 0);
							}
							if(class9_1.id == 37100) {
								if (_y < 41 || _y > 230) {
									return;
								}
							}
							Rasterizer2D.drawAlphaBox(_x, _y, class9_1.width, class9_1.height, class9_1.fillColor, class9_1.opacity);
							Rasterizer2D.setDrawingArea(yPosition + class9_1.height, xPosition, xPosition + class9_1.width, yPosition);
							/**
							 int totalWidth = class9_1.width;
							 int spriteWidth = class9_1.sprite2.myWidth;
							 int totalHeight = class9_1.height;
							 int spriteHeight = class9_1.sprite2.myHeight;
							 Sprite behindSprite = class9_1.active ? class9_1.enabledAltSprite : class9_1.disabledAltSprite;

							 if (class9_1.toggled) {
							 behindSprite.drawSprite(_x, _y);
							 class9_1.sprite2.drawSprite(_x + (totalWidth / 2) - spriteWidth / 2,
							 _y + (totalHeight / 2) - spriteHeight / 2, class9_1.spriteOpacity);
							 class9_1.toggled = false;
							 } else {
							 behindSprite.drawSprite(_x, _y);
							 class9_1.sprite2.drawSprite(_x + (totalWidth / 2) - spriteWidth / 2,
							 _y + (totalHeight / 2) - spriteHeight / 2);
							 }
							 **/
						} else if (class9_1.type == 16) {
							drawInputField(class9_1, _x, _y, class9_1.width, class9_1.height);
						}else if (class9_1.type == RSInterface.TYPE_BOX) {
							// Draw outline
							Rasterizer2D.drawBox(_x - 2, _y - 2, class9_1.width + 4, class9_1.height + 4, 0x0e0e0c);
							Rasterizer2D.drawBox(_x - 1, _y - 1, class9_1.width + 2, class9_1.height + 2, 0x474745);
							// Draw base box
							if (class9_1.toggled) {
								Rasterizer2D.drawBox(_x, _y, class9_1.width, class9_1.height, class9_1.anInt239);
								class9_1.toggled = false;
							} else {
								Rasterizer2D.drawBox(_x, _y, class9_1.width, class9_1.height, class9_1.hoverTextColor);
							}
						} else if (class9_1.type == 19) {
							if (class9_1.backgroundSprites.length > 1) {
								if (class9_1.getSprite1() != null) {
									class9_1.getSprite1().drawAdvancedSprite(_x, _y);
								}
							}
						} else if (class9_1.type == RSInterface.TYPE_STRING_CONTAINER) {
							int x = _x;
							int y = _y;

							// Set the scroll max based on the strings
							if (class9_1.scrollableContainerInterfaceId != 0) {
								RSInterface container = RSInterface.get(class9_1.scrollableContainerInterfaceId);
								int scrollMax = class9_1.invAutoScrollHeightOffset;
								for (String string : class9_1.stringContainer)
									scrollMax += class9_1.invSpritePadY;
								if (scrollMax > container.height + 1) {
									container.scrollMax = scrollMax;
								} else {
									container.scrollMax = container.height + 1;
								}

								container.scrollMax += container.stringContainerContainerExtraScroll;
							}

							// Draw the container
							for (String string : class9_1.stringContainer) {
								if (class9_1.centerText) {
									class9_1.font.drawCenteredString(string, x, y, class9_1.textColor, class9_1.textShadow ? 0 : -1);
								} else {
									class9_1.font.drawBasicString(string, x, y, class9_1.textColor, class9_1.textShadow ? 0 : -1);
								}
								y += class9_1.invSpritePadY;
							}
						} else if (class9_1.type == RSInterface.TYPE_HORIZONTAL_STRING_CONTAINER) {
							int x = _x;
							int y = _y;

							for (int i = 0; i < class9_1.stringContainer.size(); i++) {
								String string = class9_1.stringContainer.get(i);
								if (class9_1.centerText) {
									class9_1.font.drawCenteredString(string, x, y, class9_1.textColor, class9_1.textShadow ? 0 : -1);
								} else {
									class9_1.font.drawBasicString(string, x, y, class9_1.textColor, class9_1.textShadow ? 0 : -1);
								}
								x += 32 + class9_1.invSpritePadX;
								if ((i + 1) % class9_1.width == 0) {
									y += 32 + class9_1.invSpritePadY;
									x = _x;
								}
							}
						} else if (class9_1.type == RSInterface.TYPE_PROGRESS_BAR) {
							Rasterizer2D.drawPixels(class9_1.height, _y, _x, class9_1.fillColor, class9_1.width);
						} else if (class9_1.type == 67) {
							Rasterizer2D.drawBox(_x, _y, class9_1.width, class9_1.height, class9_1.borderWidth, class9_1.borderColor, class9_1.secondaryColor, class9_1.transparency);

							if (class9_1.filled) {
								Rasterizer2D.fill_Rectangle(class9_1.fillColor, _y + class9_1.borderWidth, class9_1.width - class9_1.borderWidth * 2 + 1, class9_1.height - class9_1.borderWidth * 2 - 1, 0, _x + class9_1.borderWidth);
							}
						} else if (class9_1.type == 66) {
							try {
								String progress = class9_1.message;

								if (!progress.contains("/")) {
									continue;
								}

								int current = Integer.parseInt(progress.substring(0, progress.indexOf("/")));

								int maximum = Integer.parseInt(progress.substring(progress.indexOf("/") + 1));
								if (current > maximum) {
									current = maximum;
								}

								int width = class9_1.width;
								int height = class9_1.height;
								int color = class9_1.fillColor;

								Rasterizer2D.drawRectangle(_x - 1, _y - 1, class9_1.width - 5, class9_1.height + 2, 0, 250);
								Rasterizer2D.draw_filled_rect(_x, _y, width - 7, height, class9_1.progressBackColor, class9_1.progressBackAlpha);
								Rasterizer2D.draw_filled_rect(_x, _y, (int) ((double) current / maximum * width - 7), height, color, 200);

									RSFont font = class9_1.font == null ? newSmallFont : class9_1.font;
									font.drawCenteredString(current + " / " + maximum, _x + (width - 8) / 2, _y + height / 2 + 5, 0xFFFFFF, 0);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (class9_1.type == RSInterface.TYPE_PROGRESS_BAR_2021) {
							double percentage = class9_1.progressBar2021Percentage;
							int color = RSInterface.getRgbProgressColor(percentage);

							int progressBarWidth = (int) ((double) class9_1.width * percentage);
							Rasterizer2D.drawPixels(class9_1.height, _y, _x, color, progressBarWidth);
							Rasterizer2D.drawBorder(_x, _y, class9_1.width, class9_1.height, class9_1.fillColor);
						} else if (class9_1.type == RSInterface.TYPE_DRAW_BOX) {
							//DrawingArea.drawRoundedRectangle(_x, _y, class9_1.width, class9_1.height, class9_1.fillColor, class9_1.transparency, true, true);

							Rasterizer2D.drawTransparentBox(_x, _y, class9_1.width, class9_1.height, class9_1.fillColor, class9_1.transparency);
							Rasterizer2D.drawBorder(_x, _y, class9_1.width, class9_1.height, class9_1.borderColor);
						} else if (class9_1.type == 91) {

							AnimatedSprite gif = SpriteLoader.fetchAnimatedSprite(class9_1.gifLocation);
							if (gif != null) {
								gif.getInstance(class9_1.width, class9_1.height).drawAdvancedSprite(_x, _y,255);
							}

						}

					if (interfaceText) {
						newSmallFont.drawString(class9_1.id + "", _x - 12, _y, 0xFFFFFFFF, 0, 256);
					}

					callbacks.drawLayer(rsInterface, class9_1.getWidgetItems());
				} catch (Exception | StackOverflowError e) {
					System.err.println(String.format("Error on interface child, parentId=%s, childIndex=%s, childId=%s", rsInterface.id, childId, rsInterface.children[childId]));
					e.printStackTrace();
				}
			}

			callbacks.drawInterface(rsInterface.id, rsInterface.getWidgetItems());
//			Bank.drawOnBank(rsInterface, 0, 0);

			if (!inheritDrawingArea) {
				Rasterizer2D.setDrawingArea(clipBottom, clipLeft, clipRight, clipTop);
			}
			if (rsInterface.id == 42000) {
				cacheSprite2[76].flashSprite(24, 280, 200 + (int) (50 * Math.sin(loopCycle / 15.0)));
			}
			if (rsInterface.id == 16244) {
				if (MouseHandler.mouseX > 165 && MouseHandler.mouseX < 610 && MouseHandler.mouseY > 428 && MouseHandler.mouseY < 470) {
					Rasterizer2D.drawAlphaBox(165, 428, 444, 42, 0xffffff, 40);
				}
				// cacheSprite2[76].drawSprite1(24, 280,
				// 200 + (int) (50 * Math.sin(tick / 15.0)));
				// DrawingArea.drawBox(30, 400, 400, 400, 0xffffff);
			}
		} catch (Exception | StackOverflowError e) {
			System.err.println(String.format("Error on interface j=%d, xPosition=%d, id=%d, yPosition=%d", scrollPosition, xPosition, rsInterface.id, yPosition));
			e.printStackTrace();
		}
	}

	/**
	 * int state -1 for buy and sell buttons, 0 for buying, 1 for selling, 2 for
	 * canceled
	 * <p>
	 * int itemId The item that the player wants to buy
	 * <p>
	 * int currentlyBought/Sold How many items the player has bought/sold so far.
	 * <p>
	 * int totalAmount How many items the player wants to buy/sell in total.
	 */
	private int[][] grandExchangeInformation = new int[][] { { -1, -1, -1, -1 }, { -1, -1, -1, -1 }, { -1, -1, -1, -1 },
			{ -1, -1, -1, -1 }, { -1, -1, -1, -1 }, { -1, -1, -1, -1 }, { -1, -1, -1, -1 }, { -1, -1, -1, -1 },
			{ -1, -1, -1, -1 },
			// { 0, 11802, 0, 10 },
			// { 0, 11804, 4, 14 },
			// { 2, 11826, 2, 2 },
			// { 1, 555, 47500, 52500 },
			// { 0, 560, 35000, 50000 },
			// { 1, 11847, 0, 1 },
			// { -1, -1, -1, -1 }
	};

	public final String methodR(int j) {
		if (j >= 0 && j < 10000)
			return String.valueOf(j);
		if (j >= 10000 && j < 10000000)
			return j / 1000 + "K";
		if (j >= 10000000 && j < 999999999)
			return j / 1000000 + "M";
		if (j >= 999999999)
			return "*";
		else
			return "?";
	}

	public void updateProgressBar(int childId, int interfaceState, int percentage, int interfaceState2) {
		RSInterface rsi = RSInterface.interfaceCache[childId];
		if (rsi == null) {
			return;
		}

		ProgressBar inter = (ProgressBar) rsi;
		inter.progressBarState = interfaceState;
		inter.progressBarPercentage = percentage;
		inter.defaultBarState = interfaceState2;
	}

	public void drawHoverBox(int xPos, int yPos, String text) {
		String[] results = text.split("\n");
		int height = (results.length * 16) + 3;
		int width;
		width = newRegularFont.getTextWidth(results[0]) + 6;
		for (int i = 1; i < results.length; i++)
			if (width <= newRegularFont.getTextWidth(results[i]) + 6)
				width = newRegularFont.getTextWidth(results[i]) + 6;
		if(xPos + width > getCanvasWidth()) {
			xPos = getCanvasWidth() - width - 5;
		}
		if(yPos < 5)
			yPos = 5;
		if(yPos + height > getCanvasHeight())
			yPos = getCanvasHeight() - height - 5;
		Rasterizer2D.drawPixels(height, yPos, xPos, 0x544433, width);
		Rasterizer2D.fillPixels(xPos, width, height, 0, yPos);
		yPos += 14;
		for (int i = 0; i < results.length; i++) {
			newRegularFont.drawBasicString(results[i], xPos + 3, yPos, 0, 0);
			yPos += 16;
		}
	}

	public void drawBlackBox(int xPos, int yPos) {
		Rasterizer2D.drawBox(xPos - 2, yPos - 1, 1, 71, 0x726451);
		Rasterizer2D.drawBox(xPos + 174, yPos, 1, 69, 0x726451);
		Rasterizer2D.drawBox(xPos - 2, yPos - 2, 178, 1, 0x726451);
		Rasterizer2D.drawBox(xPos, yPos + 68, 174, 1, 0x726451);
		Rasterizer2D.drawBox(xPos - 1, yPos - 1, 1, 71, 0x2E2B23);
		Rasterizer2D.drawBox(xPos + 175, yPos - 1, 1, 71, 0x2E2B23);
		Rasterizer2D.drawBox(xPos, yPos - 1, 175, 1, 0x2E2B23);
		Rasterizer2D.drawBox(xPos, yPos + 69, 175, 1, 0x2E2B23);
		Rasterizer2D.drawTransparentBox(xPos, yPos, 174, 68, 0, 220);
	}

	public void loadTabArea() {
		if(getUserSettings().isOldGameframe() == false) {
			for (int i = 0; i < redStones.length; i++)
				redStones[i] = new Sprite("Gameframe/redstones/redstone" + i);

			for (int i = 0; i < sideIcons.length; i++)
				sideIcons[i] = new Sprite("Gameframe/sideicons/sideicon" + i);

			mapArea[0] = new Sprite("Gameframe/fixed/mapArea");
			mapArea[1] = new Sprite("Gameframe/fixed/mapBorder");
			mapArea[2] = new Sprite("Gameframe/resizable/mapArea");
			mapArea[3] = new Sprite("Gameframe/fixed/blackMapArea");
			mapArea[4] = new Sprite("Gameframe/resizable/mapBlack");
			mapArea[5] = new Sprite("Gameframe/fixed/topframe");
			mapArea[6] = new Sprite("Gameframe/fixed/chatborder");
			mapArea[7] = new Sprite("Gameframe/fixed/frame");

			tabAreaFixed = new Sprite("Gameframe/fixed/tabArea");
			compassImage = new Sprite("Gameframe/compassImage");
		}else {
			for (int i = 0; i < redStones.length; i++)
				redStones[i] = new Sprite("Gameframe317/redstones/redstone" + i);

			for (int i = 0; i < sideIcons.length; i++)
				sideIcons[i] = new Sprite("Gameframe317/sideicons/sideicon" + i);

			mapArea[0] = new Sprite("Gameframe/fixed/mapArea");
			mapArea[1] = new Sprite("Gameframe317/fixed/mapBorder");
			mapArea[2] = new Sprite("Gameframe317/resizable/mapArea");
			mapArea[3] = new Sprite("Gameframe317/fixed/blackMapArea");
			mapArea[4] = new Sprite("Gameframe317/resizable/mapBlack");
			mapArea[5] = new Sprite("Gameframe/fixed/topframe");
			mapArea[6] = new Sprite("Gameframe/fixed/chatborder");
			mapArea[7] = new Sprite("Gameframe/fixed/frame");

			tabAreaFixed = new Sprite("Gameframe317/fixed/tabArea");
			compassImage = new Sprite("Gameframe317/compassImage");
		}
	}
	public void randomizeBackground(IndexedImage background) {
		int j = 256;
		for (int k = 0; k < anIntArray1190.length; k++)
			anIntArray1190[k] = 0;

		for (int l = 0; l < 5000; l++) {
			int i1 = (int) (Math.random() * 128D * j);
			anIntArray1190[i1] = (int) (Math.random() * 256D);
		}
		for (int j1 = 0; j1 < 20; j1++) {
			for (int k1 = 1; k1 < j - 1; k1++) {
				for (int i2 = 1; i2 < 127; i2++) {
					int k2 = i2 + (k1 << 7);
					anIntArray1191[k2] = (anIntArray1190[k2 - 1] + anIntArray1190[k2 + 1] + anIntArray1190[k2 - 128]
							+ anIntArray1190[k2 + 128]) / 4;
				}

			}
			int ai[] = anIntArray1190;
			anIntArray1190 = anIntArray1191;
			anIntArray1191 = ai;
		}
		if (background != null) {
			int l1 = 0;
			for (int j2 = 0; j2 < background.subHeight; j2++) {
				for (int l2 = 0; l2 < background.subWidth; l2++)
					if (background.palettePixels[l1++] != 0) {
						int i3 = l2 + 16 + background.xOffset;
						int j3 = j2 + 16 + background.yOffset;
						int k3 = i3 + (j3 << 7);
						anIntArray1190[k3] = 0;
					}
			}
		}
	}

	private void method107(int i, int j, Buffer stream, Player player) {
		if ((i & 0x400) != 0) {
			player.anInt1543 = stream.method428();
			player.anInt1545 = stream.method428();
			player.anInt1544 = stream.method428();
			player.anInt1546 = stream.method428();
			player.initiate_movement = stream.method436() + loopCycle;
			player.cease_movement = stream.readUShortA() + loopCycle;
			player.forceMovementDirection = stream.method428();
			player.method446();
		}
		if ((i & 0x100) != 0) {
			int graphicsReceived = stream.method434();
			for(int li = 0; li < graphicsReceived; li++) {
				player.graphicId = stream.method434();
				int k = stream.readDWord();
				player.graphicHeight = k >> 16;
				player.graphic_cycle = loopCycle + (k & 0xffff);
				player.graphicFrame = 0;
				player.currentAnimationTimeRemaining = 0;
				if (player.graphic_cycle > loopCycle)
					player.graphicFrame = -1;
				if (player.graphicId == 65535)
					player.graphicId = -1;
				player.createSpotAnim(player.graphicId, player.graphicId, player.graphicHeight, (k & 0xffff));
			}
		}
		if ((i & 8) != 0) {
			int l = stream.method434();
			if (l == 65535)
				l = -1;
			int i2 = stream.method427();
			if (l == player.primarySeqID && l != -1) {
				int i3 = SeqDefinition.get(l).replyMode;
				if (i3 == 1) {
					player.primarySeqFrame = 0;
					player.primarySeqCycle = 0;
					player.primarySeqDelay = i2;
					player.animationLoops = 0;
				}
				if (i3 == 2)
					player.animationLoops = 0;
			} else if (l == -1 || player.primarySeqID == -1
					|| SeqDefinition.get(l).forcedPriority >= SeqDefinition.get(player.primarySeqID).forcedPriority) {
				player.primarySeqID = l;
				player.primarySeqFrame = 0;
				player.primarySeqCycle = 0;
				player.primarySeqDelay = i2;
				player.animationLoops = 0;
				player.remaining_steps = player.smallXYIndex;
			}
			AnimationChanged animationChanged = new AnimationChanged();
			animationChanged.setActor(player);
			callbacks.post(animationChanged);
		}
		if ((i & 4) != 0) {
			String textSpoken = stream.readString();

			// Only show flower poker chat to people within the area
			if (!player.inFlowerPokerArea() || localPlayer.inFlowerPokerChatProximity()) {
				player.textSpoken = textSpoken;
				if (player.textSpoken.charAt(0) == '~') {
					player.textSpoken = player.textSpoken.substring(1);
					if (player.lastForceChat == null || !player.lastForceChat.equals(textSpoken))
						pushMessage(player.textSpoken, 2, player.displayName);
				} else if (player == localPlayer) {
					if (player.lastForceChat == null || !player.lastForceChat.equals(textSpoken))
						pushMessage(player.textSpoken, 2, player.displayName);
				}
				player.anInt1513 = 0;
				player.anInt1531 = 0;
				player.textCycle = 150;
				player.lastForceChat = textSpoken;
			}
		}
		if ((i & 0x80) != 0) {
			int i1 = stream.method434();
			int rightsPrimaryValue = stream.readUnsignedByte();
			int j3 = stream.method427();
			int k3 = stream.currentPosition;
			PlayerRights rights = PlayerRights.forRightsValue(rightsPrimaryValue);
			if (player.displayName != null && player.visible) {
				long l3 = longForName(player.displayName);
				boolean flag = false;
				if (!rights.isStaffPosition()) {
					for (int i4 = 0; i4 < ignoreCount; i4++) {
						if (ignoreListAsLongs[i4] != l3)
							continue;
						flag = true;
						break;
					}
				}
				if (!flag && anInt1251 == 0)
					try {
						aStream_834.currentPosition = 0;
						stream.method442(j3, 0, aStream_834.payload);
						aStream_834.currentPosition = 0;
						String s = TextInput.method525(j3, aStream_834);

						// Only show flower poker chat to people within the area
						if (!player.inFlowerPokerArea() || localPlayer.inFlowerPokerChatProximity()) {
							player.textSpoken = s;
							player.anInt1513 = i1 >> 8;
							player.privelage = rightsPrimaryValue; // TODO remove privilege sending in send text player update flag
							player.anInt1531 = i1 & 0xff;
							player.textCycle = 150;
							String crown = (player.hideDonor ? "" : PlayerRights.buildCrownString(player.getDisplayedRights()));
							String title = player.title != null && !player.title.isEmpty()
									? "<col=" + player.titleColor + ">" + player.title + "</col> "
									: "";
							pushMessage(s, 2, crown + title + player.displayName);
						}
					} catch (Exception exception) {
						exception.printStackTrace();
						Signlink.reporterror("cde2");
					}
			}
			stream.currentPosition = k3 + j3;
		}
		if ((i & 1) != 0) {
			player.interactingEntity = stream.method434();
			if (player.interactingEntity == 65535)
				player.interactingEntity = -1;
			callbacks.post(new InteractingChanged(player, player.getInteracting()));
		}
		if ((i & 0x10) != 0) {
			int j1 = stream.method427();
			byte abyte0[] = new byte[j1];
			Buffer stream_1 = new Buffer(abyte0);
			stream.readBytes(j1, 0, abyte0);
			aStreamArray895s[j] = stream_1;
			player.updatePlayer(stream_1);
		}
		if ((i & 2) != 0) {
			player.anInt1538 = stream.method436();
			player.anInt1539 = stream.method434();
		}
		if ((i & 0x20) != 0) {
			int k1 = stream.readUnsignedByte();
			int k2 = stream.method426();
			int source = stream.readInt();
			player.updateHitData(k2, k1, loopCycle, source);
			player.loopCycleStatus = loopCycle + 300;
			player.currentHealth = stream.method427();
			player.maxHealth = stream.readUnsignedByte();
			if (player.currentHealth <= 0) {
				player.setDead(true);
				callbacks.postDeferred(new ActorDeath(player));
			} else {
				player.setDead(false);
			}
		}
		if ((i & 0x200) != 0) {
			int l1 = stream.readUnsignedByte();
			int l2 = stream.method428();
			int source = stream.readInt();
			player.updateHitData(l2, l1, loopCycle, source);
			player.loopCycleStatus = loopCycle + 300;
			player.currentHealth = stream.readUnsignedByte();
			player.maxHealth = stream.method427();
			if (player.currentHealth <= 0) {
				player.setDead(true);
				callbacks.postDeferred(new ActorDeath(player));
			} else {
				player.setDead(false);
			}
		}
	}

	public void method108() {
		try {
			int j = localPlayer.x + cameraOffsetX;
			int k = localPlayer.y + cameraOffsetY;
			if (anInt1014 - j < -500 || anInt1014 - j > 500 || anInt1015 - k < -500 || anInt1015 - k > 500) {
				anInt1014 = j;
				anInt1015 = k;
			}
			if (anInt1014 != j)
				anInt1014 += (j - anInt1014) / 16;
			if (anInt1015 != k)
				anInt1015 += (k - anInt1015) / 16;
			if (KeyHandler.keyArray[1] == 1)
				anInt1186 += (-24 - anInt1186) / 2;
			else if (KeyHandler.keyArray[2] == 1)
				anInt1186 += (24 - anInt1186) / 2;
			else
				anInt1186 /= 2;
			if (KeyHandler.keyArray[3] == 1)
				anInt1187 += (12 - anInt1187) / 2;
			else if (KeyHandler.keyArray[4] == 1)
				anInt1187 += (-12 - anInt1187) / 2;
			else
				anInt1187 /= 2;
			viewRotation = viewRotation + anInt1186 / 2 & 0x7ff;
            camAngleYY += anInt1187 / 2;
			if (camAngleYY < 128)
				camAngleYY = 128;
			if (camAngleYY > 383)
				camAngleYY = 383;
			onCameraPitchTargetChanged(camAngleYY);
			int l = anInt1014 >> 7;
			int i1 = anInt1015 >> 7;
			int j1 = getCenterHeight(plane, anInt1015, anInt1014);
			int k1 = 0;
			if (l > 3 && i1 > 3 && l < 100 && i1 < 100) {
				for (int l1 = l - 4; l1 <= l + 4; l1++) {
					for (int k2 = i1 - 4; k2 <= i1 + 4; k2++) {
						int l2 = plane;
						if (l2 < 3 && (tileFlags[1][l1][k2] & 2) == 2)
							l2++;
						int i3 = j1 - tileHeights[l2][l1][k2];
						if (i3 > k1)
							k1 = i3;
					}

				}

			}
			anInt1005++;
			if (anInt1005 > 1512) {
				anInt1005 = 0;
				stream.createFrame(77);
				stream.writeUnsignedByte(0);
				int i2 = stream.currentPosition;
				stream.writeUnsignedByte((int) (Math.random() * 256D));
				stream.writeUnsignedByte(101);
				stream.writeUnsignedByte(233);
				stream.writeWord(45092);
				if ((int) (Math.random() * 2D) == 0)
					stream.writeWord(35784);
				stream.writeUnsignedByte((int) (Math.random() * 256D));
				stream.writeUnsignedByte(64);
				stream.writeUnsignedByte(38);
				stream.writeWord((int) (Math.random() * 65536D));
				stream.writeWord((int) (Math.random() * 65536D));
				stream.writeBytes(stream.currentPosition - i2);
			}
			int j2 = k1 * 192;
			if (j2 > 0x17f00)
				j2 = 0x17f00;
			if (j2 < 32768)
				j2 = 32768;
			if (j2 > field556) {
				field556 += (j2 - field556) / 24;
				return;
			}
			if (j2 < field556) {
				field556 += (j2 - field556) / 80;
			}
		} catch (Exception _ex) {
			Signlink.reporterror("glfc_ex " + localPlayer.x + "," + localPlayer.y + "," + anInt1014 + "," + anInt1015 + ","
					+ currentRegionX + "," + currentRegionY + "," + baseX + "," + baseY);
			throw new RuntimeException("eek");
		}
	}

	public int loadingPercent;
	public String loadingText;

	private void drawLoadingMessage(String messages) {
		int width = 0;
		for (String message : messages.split("<br>")) {
			int size = newRegularFont.getTextWidth(message);
			if(width <= newRegularFont.getTextWidth(message)) {
				width = size;
			}
		}

		int offset = isResized() ? 3 : 6;

		int height =  (12 * messages.split("<br>").length) + 4;

		Rasterizer2D.drawBox(offset,offset, width + 16, height + 6,0x000000);
		Rasterizer2D.drawBoxOutline(offset,offset, width + 16, height + 6,0xFFFFFF);

		int offsetY = 0;
		for (String message : messages.split("<br>")) {
			newRegularFont.drawCenteredString(message, offset + (width + 16) / 2, offset + 15 + offsetY,0xffffff,1);
			offsetY += 12;
		}

	}


	@Override
	public void draw(boolean redraw) {


		callbacks.frame();
		updateCamera();

		if (gameState == GameState.STARTING.getState()) {
			rasterProvider.drawFull(0, 0);
		} else if (gameState == GameState.LOGIN_SCREEN.getState()) {
			drawLoginScreen(false);
		} else if (gameState == GameState.CONNECTION_LOST.getState()) {
			drawLoadingMessage("Connection lost" + "<br>" + "Please wait - attempting to reestablish");
		} else if (gameState == GameState.LOADING.getState()) {
			drawLoadingMessage("Loading - please wait.");
		} else if (gameState == GameState.LOGGED_IN.getState()) {
			drawGameScreen();
		}
		rasterProvider.drawFull(0, 0);
		//anInt1061++;

		mouseClickCount = 0;
	}

	private boolean isFriendOrSelf(String s) {
		if (s == null)
			return false;
		for (int i = 0; i < friendsCount; i++)
			if (s.equalsIgnoreCase(friendsList[i]))
				return true;
		return s.equalsIgnoreCase(localPlayer.displayName);
	}

	private static String combatDiffColor(int i, int j) {
		int k = i - j;
		if (k < -9)
			return "@red@";
		if (k < -6)
			return "@or3@";
		if (k < -3)
			return "@or2@";
		if (k < 0)
			return "@or1@";
		if (k > 9)
			return "@gre@";
		if (k > 6)
			return "@gr3@";
		if (k > 3)
			return "@gr2@";
		if (k > 0)
			return "@gr1@";
		else
			return "@yel@";
	}

	public void setWaveVolume(int i) {
		Signlink.wavevol = i;
	}

	public static boolean centerInterface() {
		int minimumScreenWidth = 765, minimumScreenHeight = 610;
		if (canvasWidth >= minimumScreenWidth && canvasHeight >= minimumScreenHeight)
			return true;
		return false;
	}


	public static boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		return ((objectX - playerX <= distance && objectX - playerX >= -distance)
				&& (objectY - playerY <= distance && objectY - playerY >= -distance));
	}

	public static int distanceToPoint(int x1, int y1, int x2, int y2) {
		int x = (int) Math.pow(x1 - x2, 2.0D);
		int y = (int) Math.pow(y1 - y2, 2.0D);
		return (int) Math.floor(Math.sqrt(x + y));
	}

	private void displayGroundItems() {
		/**
		 * Loop thru all tiles in region
		 */

		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				Deque class19 = groundItems[plane][x][y];
				int count = 0;
				if (class19 != null) {
					Map<Integer, Long> amounts = new HashMap<>();
					HashSet<Integer> drawn = new HashSet<>();

					for (Item item = (Item) class19.getFirst(); item != null; item = (Item) class19.getNext()) {
						amounts.put(item.ID, item.itemCount + amounts.getOrDefault(item.ID, 0L));
					}

					for (Item item = (Item) class19.getFirst(); item != null; item = (Item) class19.getNext()) {
						if (drawn.contains(item.ID)) {
							continue;
						}



						ItemDef itemDef = ItemDef.forId(item.ID);
						long amount = amounts.get(item.ID);
						long value = (long) itemDef.getShopValue() * amount;
						drawn.add(item.ID);

						String valueText = Preferences.getPreferences().groundItemTextShowMoreThan;
						int showValue = 0;
						try {
							showValue = valueText.length() == 0 ? 0 : Integer.parseInt(valueText);
						} catch (NumberFormatException e) {
							Preferences.getPreferences().groundItemTextShowMoreThan = "";
							if (Configuration.developerMode) {
								e.printStackTrace();
							}
						}

						boolean rareDropColor = value > 100_000;

						if (itemDef.isTradable() || !Preferences.getPreferences().groundItemAlwaysShowUntradables) {
							String[] hide = Preferences.getPreferences().groundItemTextHide.split(",");
							String[] show = Preferences.getPreferences().groundItemTextShow.split(",");
							String itemNameFormatted = itemDef.getName().toLowerCase();

							boolean showItem = Arrays.stream(show).anyMatch(showTxt -> showTxt.length() > 0 && itemNameFormatted.contains(showTxt.trim()));
							boolean hideItem = Arrays.stream(hide).anyMatch(hideTxt -> hideTxt.length() > 0 && itemNameFormatted.contains(hideTxt.trim()));

							if (!showItem && (hideItem || value < showValue)) {
								continue;
							}
						} else {
							rareDropColor = true;
						}

						calcEntityScreenPos((x * 128 + 64), 25, (y * 128 + 64));

						int itemX = baseX + x;
						int itemY = baseY + y;
						int playerX = baseX + (localPlayer.x - 6 >> 7);
						int playerY = baseY + (localPlayer.y - 6 >> 7);

						/**
						 * Fading
						 */
						int transparencyDistance = distanceToPoint(playerX, playerY, itemX, itemY);
						int transparency = 256 - (transparencyDistance * 16);

						if (goodDistance(itemX, itemY, playerX, playerY, 16)) {
							int yMod = (count * 12);

							StringBuilder builder = new StringBuilder();
							builder.append(itemDef.getName());
							if (amount > 1) {
								builder.append(" (" + (int) amount + ")");
							}
							if (value > 1 && itemDef.isTradable()) {
								builder.append(" ("+ StringUtils.insertCommas(value) + " gp)");
							}

							latoBold.drawCenteredString(builder.toString(), spriteDrawX, spriteDrawY - yMod, rareDropColor ? 0xDA6EA2 : 0xffffff, 0x00000, transparency);
							count++;
						}
					}
				}
			}
		}


	}

	private void draw3dScreen() {
		if (!isResized()) {
			mapArea[1].drawSprite(516, 0);
			getOldSchoolSprite(1039).drawSprite(0, 0);
			getOldSchoolSprite(4).drawSprite(0, 4);
			getOldSchoolSprite(1441).drawSprite(717, 0);
		}
		if (snowVisible && Configuration.CHRISTMAS && !Client.instance.isResized()) {
			processWidgetAnimations(tickDelta, 11877);
			drawInterface(0, 0, RSInterface.interfaceCache[11877], 0);
		}

		if (getUserSettings().isShowEntityTarget()) {
			if (entityTarget != null) {
				entityTarget.draw();
			}
		}

		if (getUserSettings().isGameTimers()) {
			try {
				int startX = 516;
				int startY = !Client.instance.isResized() ? 294 : Client.canvasHeight - 209;
				GameTimerHandler.getSingleton().drawGameTimers(this, startX, startY);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		drawSplitPrivateChat();

		if (crossType == 1) {
			getOldSchoolSprite(515 + (crossIndex / 100)).drawSprite(crossX - 8, crossY - 8);
			anInt1142++;
			if (anInt1142 > 67) {
				anInt1142 = 0;
				stream.createFrame(78);
			}
		}
		if (crossType == 2) {
			getOldSchoolSprite(519 + (crossIndex / 100)).drawSprite(crossX - 8, crossY - 8);
		}

		drawParallelWidgets();
		if (openWalkableWidgetID != -1) {
			processWidgetAnimations(tickDelta, openWalkableWidgetID);
			if (!Nightmare.instance.drawNightmareInterfaces(openWalkableWidgetID)) {
				RSInterface rsinterface = RSInterface.interfaceCache[openWalkableWidgetID];
				if (!isResized()) {
					drawInterface(0, 0, rsinterface, 0);
				} else {
					if (openWalkableWidgetID == 28000 || openWalkableWidgetID == 28020 || openWalkableWidgetID == 16210
							|| openWalkableWidgetID == 27500 || openWalkableWidgetID == 196) {
						/**
						 * Interfaces to draw at the top right corner nex to the minimap (Ex. Wildy::inter
						 * Target)
						 **/
						drawInterface(0, canvasWidth - 730, rsinterface, 20);
					} else if (openWalkableWidgetID == 197) {
						drawInterface(0, canvasWidth - 530, rsinterface, -70);
					} else if (openWalkableWidgetID == 21100 || openWalkableWidgetID == 21119
							|| openWalkableWidgetID == 29230) {
						/**
						 * Interfaces to draw at top left corner (Ex. Pest Control)
						 **/
						drawInterface(0, 0, rsinterface, 0);
					} else if (openWalkableWidgetID == 201) {
						/** Duel arena interface **/
						drawInterface(0, canvasWidth - 510, rsinterface, -110);
					} else if (openWalkableWidgetID == 35424 || openWalkableWidgetID == 65000) {
						drawInterface(0, (canvasWidth / 2) - 340, rsinterface,0);
					}  else if (openWalkableWidgetID == 63000) {
						drawInterface(0, (canvasWidth / 2) - 340, rsinterface,-295);
					} else if (centerInterface() || openInterfaceID == 29230) {
						drawInterface(0, (canvasWidth / 2) - 356, rsinterface,
								!isResized() ? 0 : (canvasHeight / 2) - 230);
					} else {
						if (canvasWidth >= 900 && canvasHeight >= 650) {
							drawInterface(0, (canvasWidth / 2) - 356, RSInterface.interfaceCache[openWalkableWidgetID], !isResized() ? 0 : (canvasHeight / 2) - 230);
						} else {
							drawInterface(0, 0, RSInterface.interfaceCache[openWalkableWidgetID], 0);
						}
					}
				}
			}
		}

		BroadcastManager.display(this);

		if (anInt1055 == 1) {
			int x = !isResized() ? 476 : (canvasWidth - 30);
			int y = !isResized() ? 300 : 175;
			if (!isResized() && GameTimerHandler.getSingleton().hasActiveGameTimers()) {
				y -= 32;
			}
			multiOverlay.drawSprite(x, y);
		}

		if (fpsOn) {
			// char c = '\u01FB';
			int yPosition = 30;
			int xPosition = !isResized() ? 515 : canvasWidth - 222;
			int textColor = 0xffff00;
			if (super.fps < 15)
				textColor = 0xff0000;
			aTextDrawingArea_1271.method380("Fps: " + super.fps, xPosition, textColor, yPosition);
			yPosition += 15;
			Runtime runtime = Runtime.getRuntime();
			int j1 = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
			textColor = 0xffff00;
			if (j1 > 0x2000000 && lowMem)
				textColor = 0xff0000;
			aTextDrawingArea_1271.method380("Mem: " + j1 / 1000 + " mb", xPosition, textColor, yPosition);
			yPosition += 15;
		}

		int x = baseX + (localPlayer.x - 6 >> 7);
		int y = baseY + (localPlayer.y - 6 >> 7);
		int mapx = currentRegionX; // map region x
		int mapy = currentRegionY; // map region y
		if (clientData) {
			Runtime runtime = Runtime.getRuntime();
			int j1 = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
			aTextDrawingArea_1271.method385(0x00FF00, "Players Nearby: " + playerCount, 27, 5);
			aTextDrawingArea_1271.method385(0x00FF00, "Npcs Nearby: " + npcCount, 41, 5);

			if (mapx > 1000 || mapy > 1000) {
				aTextDrawingArea_1271.method385(0xffff00, "Current Region: " + mapx + ", " + mapy, 55, 5);
			} else {
				aTextDrawingArea_1271.method385(0xffff00, "Current Region: 0" + mapx + ", 0" + mapy, 55, 5);
			}
			for (int num = 0; num < terrainIndices.length; num++) {
				int[] flo = terrainIndices;
				aTextDrawingArea_1271.method385(0xffff00, "Floor map: " + Arrays.toString(flo), 69, 5);
			}
			for (int num = 0; num < objectIndices.length; num++) {
				int[] obj = objectIndices;
				aTextDrawingArea_1271.method385(0xffff00, "Object map: " + Arrays.toString(obj), 83, 5);
				// output: "Object map: "[1, 3, 5, 7, 9]"
			}

			aTextDrawingArea_1271.method385(0xffff00, "Map Data: " + terrainIndices[0] + ".dat", 97, 5);
			aTextDrawingArea_1271.method385(0xffff00, "Fps: " + super.fps, 111, 5);
			aTextDrawingArea_1271.method385(0xffff00, "Memory Used: " + j1/1024 + "MB", 125, 5);
			aTextDrawingArea_1271.method385(0xffff00,
					"Mouse: [" + MouseHandler.mouseX + ", " + MouseHandler.mouseY + "] ", 139, 5);
			aTextDrawingArea_1271.method385(0xffff00, "Coordinates: X: " + x + ", Y: " + y, 153, 5);

			aTextDrawingArea_1271.method385(0xffff00,
					"Camera Position: X: " + xCameraPos + ", Y: " + yCameraPos + ", Z: " + zCameraPos, 167, 5);
			aTextDrawingArea_1271.method385(0xffff00, "Camera Curve: X: " + xCameraCurve + ", Y: " + yCameraCurve, 181,
					5);
			y = 181;
			y += 15;

		}

		if (openInterfaceID != -1) {
			processWidgetAnimations(tickDelta, openInterfaceID);
			RSInterface rsinterface = RSInterface.interfaceCache[openInterfaceID];
			if (!isResized())
				drawInterface(0, 0, rsinterface, 0);
			else
				drawInterface(0, (canvasWidth / 2) - 356, rsinterface, !isResized() ? 0 : (canvasHeight / 2) - 230);
		}
		method70();

		if (!menuOpen) {
			processRightClick();
			drawTopLeftTooltip();
		} else if (menuScreenArea == 0) {
			drawMenu(menuOffsetX, menuOffsetY);
		}

		if (drawGrid) {
			drawGrid();
		}


		if (anInt1104 != 0) {
			int j = anInt1104 / 50;
			int l = j / 60;
			j %= 60;
			int yPosition = !isResized() ? 329 : canvasHeight - 165;
			if (j < 10)
				aTextDrawingArea_1271.method385(0xffff00, "System update in: " + l + ":0" + j, yPosition, 5);
			else
				aTextDrawingArea_1271.method385(0xffff00, "System update in: " + l + ":" + j, yPosition, 5);

			anInt849++;
			if (anInt849 > 75) {
				anInt849 = 0;
				stream.createFrame(148);
			}
		}

		drawScreenBox();
	}

	private void addIgnore(long l) {
		try {
			if (l == 0L)
				return;

			String username = StringUtils.fixName(StringUtils.nameForLong(l));

			if (username.equalsIgnoreCase(localPlayer.displayName))
				return;

			if (ignoreCount >= 100) {
				pushMessage("Your ignore list is full. Max of 100 hit", 0, "");
				return;
			}
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					pushMessage(username + " is already on your ignore list", 0, "");
					return;
				}
			for (int k = 0; k < friendsCount; k++)
				if (friendsListAsLongs[k] == l) {
					pushMessage("Please remove " + username + " from your friend list first", 0, "");
					return;
				}

			//ignoreListAsLongs[ignoreCount++] = l;
			//needDrawTabArea = true;
			stream.createFrame(133);
			stream.writeQWord(l);
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("45688, " + l + ", " + 4 + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public void processPlayerMovement() {
		for (int i = -1; i < playerCount; i++) {
			int j;
			if (i == -1)
				j = maxPlayerCount;
			else
				j = playerIndices[i];
			Player player = players[j];
			if (player != null)
				method96(player);
		}

	}

	private void method115() {
		if (loadingStage == 2) {
			boolean passedRequest = false;
			for (SpawnedObject spawnedObject = (SpawnedObject) spawns
					.reverseGetFirst(); spawnedObject != null; spawnedObject = (SpawnedObject) spawns
					.reverseGetNext()) {
				if (spawnedObject.getLongetivity > 0)
					spawnedObject.getLongetivity--;
				if (spawnedObject.getLongetivity == 0) {
					if (spawnedObject.getPreviousId < 0
							|| MapRegion.modelReady(spawnedObject.getPreviousId, spawnedObject.previousType)) {
						removeObject(spawnedObject.y, spawnedObject.plane, spawnedObject.previousOrientation,
								spawnedObject.previousType, spawnedObject.x, spawnedObject.group,
								spawnedObject.getPreviousId);
						spawnedObject.remove();
					}
				} else {
					if (spawnedObject.delay > 0)
						spawnedObject.delay--;
					if (spawnedObject.delay == 0 && spawnedObject.x >= 1 && spawnedObject.y >= 1
							&& spawnedObject.x <= 102 && spawnedObject.y <= 102
							&& (spawnedObject.objectId < 0
							|| MapRegion.modelReady(spawnedObject.objectId, spawnedObject.type))) {
						removeObject(spawnedObject.y, spawnedObject.plane, spawnedObject.orientation,
								spawnedObject.type, spawnedObject.x, spawnedObject.group,
								spawnedObject.objectId);
						spawnedObject.delay = -1;
						if (spawnedObject.objectId == spawnedObject.getPreviousId && spawnedObject.getPreviousId == -1)
							spawnedObject.remove();
						else if (spawnedObject.objectId == spawnedObject.getPreviousId
								&& spawnedObject.orientation == spawnedObject.previousOrientation
								&& spawnedObject.type == spawnedObject.previousType)
							spawnedObject.remove();
						passedRequest = true;
					}
				}
			}
			if (passedRequest)
				// anInt985 = plane;
				renderMapScene(plane);

		}
	}

	private void determineMenuSize() {
		final MenuOpened event = new MenuOpened();
		event.setMenuEntries(getMenuEntries());
		callbacks.post(event);

		if (event.isModified())
		{
			setMenuEntries(event.getMenuEntries());
		}

		Client client = instance;

		int tempWidth = client.getFontBold12().getTextWidth("Choose Option");

		int var4;
		int var5;
		int realCount = 0;
		for (var4 = 0; var4 < client.getMenuOptionCount(); ++var4)
		{
			net.runelite.api.MenuEntry tempMenuEntry = client.getMenuEntries()[var4];
			if (tempMenuEntry.getParent() != null)
			{
				continue;
			}
			realCount++;
			String s = tempMenuEntry.getOption();
			if (tempMenuEntry.getTarget() != null && !tempMenuEntry.getTarget().isEmpty())
			{
				s = s + " " + tempMenuEntry.getTarget();
			}

			if (tempMenuEntry.getType() == MenuAction.RUNELITE_SUBMENU)
			{
				s = s + " <col=ffffff><gt>";
			}
			tempWidth = Math.max(client.getFontBold12().getTextWidth(s), tempWidth);
		}

		tempWidth += 8;
		var4 = realCount * 15 + 22;
		var5 = saveClickX - tempWidth / 2;
		if (var5 + tempWidth > client.getCanvasWidth())
		{
			var5 = client.getCanvasWidth() - tempWidth;
		}

		if (var5 < 0)
		{
			var5 = 0;
		}

		int var6 = saveClickY;
		if (var4 + saveClickY > client.getCanvasHeight())
		{
			var6 = client.getCanvasHeight() - var4;
		}

		if (var6 < 0)
		{
			var6 = 0;
		}

		client.setMenuX(var5);
		client.setMenuY(var6);
		client.setMenuWidth(tempWidth);
		client.setMenuHeight(realCount * 15 + 22);
		client.getScene().menuOpen(client.getPlane(), x - client.getViewportXOffset(), y - client.getViewportYOffset(), false);
		client.setMenuOpen(true);

		client.setMenuScroll(0);
		menuScrollMax = 0;
		if (var4 > this.getCanvasHeight())
		{
			menuScrollMax = (var4 - this.getCanvasHeight() + 14) / 15;
		}
	}

	public void method117(Buffer stream) {
		stream.initBitAccess();
		int j = stream.readBits(1);
		if (j == 0)
			return;
		int k = stream.readBits(2);
		if (k == 0) {
			anIntArray894[anInt893++] = maxPlayerCount;
			return;
		}
		if (k == 1) {
			int l = stream.readBits(3);
			localPlayer.moveInDir(false, l);
			int k1 = stream.readBits(1);
			if (k1 == 1)
				anIntArray894[anInt893++] = maxPlayerCount;
			return;
		}
		if (k == 2) {
			int i1 = stream.readBits(3);
			localPlayer.moveInDir(true, i1);
			int l1 = stream.readBits(3);
			localPlayer.moveInDir(true, l1);
			int j2 = stream.readBits(1);
			if (j2 == 1)
				anIntArray894[anInt893++] = maxPlayerCount;
			return;
		}
		if (k == 3) {
			plane = stream.readBits(2);
			int j1 = stream.readBits(1);
			int i2 = stream.readBits(1);
			if (i2 == 1)
				anIntArray894[anInt893++] = maxPlayerCount;
			int k2 = stream.readBits(7);
			int l2 = stream.readBits(7);
			localPlayer.setPos(l2, k2, j1 == 1);
		}
	}

	public void nullLoader() {
		aBoolean831 = false;
		while (drawingFlames) {
			aBoolean831 = false;
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}
		aBackgroundArray1152s = null;
		anIntArray850 = null;
		anIntArray851 = null;
		anIntArray852 = null;
		anIntArray853 = null;
		anIntArray1190 = null;
		anIntArray1191 = null;
		anIntArray828 = null;
		anIntArray829 = null;
		aClass30_Sub2_Sub1_Sub1_1201 = null;
		aClass30_Sub2_Sub1_Sub1_1202 = null;
	}

	private boolean processWidgetAnimations(int tick, int interfaceId) {
		boolean flag1 = false;
		RSInterface class9 = RSInterface.interfaceCache[interfaceId];
		for (int k = 0; k < class9.children.length; k++) {
			if (class9.children[k] == -1)
				break;
			RSInterface class9_1 = RSInterface.interfaceCache[class9.children[k]];
			if (class9_1.type == 1)
				flag1 |= processWidgetAnimations(tick, class9_1.id);
			if (class9_1.type == 6 && (class9_1.disabledAnimationId != -1 || class9_1.enabledAnimationId != -1)) {
				boolean flag2 = interfaceIsSelected(class9_1);
				int l;
				if (flag2)
					l = class9_1.enabledAnimationId;
				else
					l = class9_1.disabledAnimationId;
				if (l != -1) {
					SeqDefinition animation = SeqDefinition.get(l);
					for (class9_1.anInt208 += tick; class9_1.anInt208 > animation.getDuration(class9_1.anInt246);) {
						class9_1.anInt208 -= animation.getDuration(class9_1.anInt246) + 1;
						class9_1.anInt246++;
						if (class9_1.anInt246 >= animation.frameCount) {
							class9_1.anInt246 -= animation.frameStep;
							if (class9_1.anInt246 < 0 || class9_1.anInt246 >= animation.frameCount)
								class9_1.anInt246 = 0;
						}
						flag1 = true;
					}

				}
			}
		}

		return flag1;
	}

	public int getXCameraPosShift() {
		int i = xCameraPos >> 7;
		return i < 0 ? 0 : i >= 104 ? 103 : i;
	}

	public int getYCameraPosShift() {
		int i = yCameraPos >> 7;
		return i < 0 ? 0 : i >= 104 ? 103 : i;
	}

	private int method120() {
		int j = 3;
		if (yCameraCurve < 310 || removeRoofs) {
			int k = getXCameraPosShift();
			int l = getYCameraPosShift();
			int i1 = localPlayer.x >> 7;
			int j1 = localPlayer.y >> 7;
			if ((tileFlags[plane][k][l] & 4) != 0)
				j = plane;
			int k1;
			if (i1 > k)
				k1 = i1 - k;
			else
				k1 = k - i1;
			int l1;
			if (j1 > l)
				l1 = j1 - l;
			else
				l1 = l - j1;
			if (k1 > l1) {
				int i2 = (l1 * 0x10000) / k1;
				int k2 = 32768;
				while (k != i1) {
					if (k < i1)
						k++;
					else if (k > i1)
						k--;
					if (removeRoofs || (tileFlags[plane][k][l] & 4) != 0)
						j = plane;
					k2 += i2;
					if (k2 >= 0x10000) {
						k2 -= 0x10000;
						if (l < j1)
							l++;
						else if (l > j1)
							l--;
						if (removeRoofs || (tileFlags[plane][k][l] & 4) != 0)
							j = plane;
					}
				}
			} else {
				if (l1 == 0) // /zero crash fix
					l1 = 1;
				int j2 = (k1 * 0x10000) / l1;
				int l2 = 32768;
				while (l != j1) {
					if (l < j1)
						l++;
					else if (l > j1)
						l--;
					if (removeRoofs || (tileFlags[plane][k][l] & 4) != 0)
						j = plane;
					l2 += j2;
					if (l2 >= 0x10000) {
						l2 -= 0x10000;
						if (k < i1)
							k++;
						else if (k > i1)
							k--;
						if (removeRoofs || (tileFlags[plane][k][l] & 4) != 0)
							j = plane;
					}
				}
			}
		}
		if ((tileFlags[plane][localPlayer.x >> 7][localPlayer.y >> 7] & 4) != 0)
			j = plane;
		return j;
	}

	private int method121() {
		int j = getCenterHeight(plane, yCameraPos, xCameraPos);
		int x = getXCameraPosShift();
		int y = getYCameraPosShift();
		if (j - zCameraPos < 800 && (tileFlags[plane][x][y] & 4) != 0)
			return plane;
		else
			return 3;
	}

	private void delIgnore(long l) {
		try {
			if (l == 0L)
				return;
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					ignoreCount--;
					needDrawTabArea = true;
					System.arraycopy(ignoreListAsLongs, j + 1, ignoreListAsLongs, j, ignoreCount - j);

					stream.createFrame(74);
					stream.writeQWord(l);
					return;
				}

			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("47229, " + 3 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	@Override
	public String getParameter(String s) {
		if (Signlink.mainapp != null)
			return Signlink.mainapp.getParameter(s);
		else
			return super.getParameter(s);
	}

	public void adjustVolume(boolean flag, int i) {
		Signlink.midivol = i;
		if (flag)
			Signlink.midi = "voladjust";
	}

	private int extractInterfaceValues(RSInterface widget, int id) {
		if (widget.scripts == null || id >= widget.scripts.length)
			return -2;
		try {
			int script[] = widget.scripts[id];
			int accumulator = 0;
			int counter = 0;
			int operator = 0;
			do {
				int instruction = script[counter++];
				int value = 0;
				byte next = 0;
				if (instruction == 0)
					return accumulator;
				if (instruction == 1)
					value = currentLevels[script[counter++]];
				if (instruction == 2)
					value = maximumLevels[script[counter++]];
				if (instruction == 3)
					value = currentExp[script[counter++]];
				if (instruction == 4) { // Check for item inside interface, interfaceId = ai[l++]
					RSInterface inventoryContainer = RSInterface.interfaceCache[script[counter++]];
					int item = script[counter++];
					if (item >= 0 && item < ItemDefinition.totalItems
							&& (!ItemDefinition.lookup(item).members || isMembers)) {
						int itemId = item + 1;
						int actualItemId = item;
						for (int slot = 0; slot < inventoryContainer.inventoryItemId.length; slot++) {
							if (inventoryContainer.inventoryItemId[slot] == itemId)
								value += inventoryContainer.inventoryAmounts[slot];
						}

						if (inventoryContainer.id == 3214) {
							RSInterface equipment = RSInterface.interfaceCache[1688];

							if (actualItemId == Items.DEATH_RUNE) {

							}

							if (actualItemId == Items.NATURE_RUNE) {
								if (equipment.hasItem(Items.BRYOPHYTAS_STAFF)) {
									return Integer.MAX_VALUE;
								}
							}

							if (actualItemId == Items.FIRE_RUNE) {
								if (equipment.hasItem(Items.TOME_OF_FIRE) || equipment.hasItem(12000) || equipment.hasItem(21198)) {
									return Integer.MAX_VALUE;
								}
							}

							if (actualItemId == Items.AIR_RUNE) {
								if (equipment.hasItem(1405) || equipment.hasItem(12000)) {
									return Integer.MAX_VALUE;
								}
							}

							if (actualItemId == Items.EARTH_RUNE) {
								if (equipment.hasItem(21198)) {
									return Integer.MAX_VALUE;
								}
							}

							if (actualItemId == Items.WATER_RUNE) {
								if (equipment.hasItem(Items.KODAI_WAND)) {
									return Integer.MAX_VALUE;
								}
							}

							if (actualItemId == Items.GUTHIX_STAFF) {
								if (equipment.hasItem(Items.STAFF_OF_BALANCE)) {
									return 1;
								}
							}
						}

						if ((runePouch[0][0] + 1) == itemId) {
							value += runePouch[0][1];
						}
						if ((runePouch[1][0] + 1) == itemId) {
							value += runePouch[1][1];
						}
						if ((runePouch[2][0] + 1) == itemId) {
							value += runePouch[2][1];
						}
					}
				}
				if (instruction == 5) {
					value = variousSettings[script[counter++]];
				}
				if (instruction == 6)
					value = SKILL_EXPERIENCE[maximumLevels[script[counter++]] - 1];
				if (instruction == 7)
					value = (variousSettings[script[counter++]] * 100) / 46875;
				if (instruction == 8) {
					value = localPlayer.combatLevel;
					System.out.println("Widget [" + widget.id + "] requesting localPlayer#combatLevel");
				}
				if (instruction == 9) {
					for (int l1 = 0; l1 < Skills.SKILLS_COUNT; l1++)
						if (Skills.SKILLS_ENABLED[l1])
							value += maximumLevels[l1];
				}
				if (instruction == 10) {
					RSInterface equipmentContainer = RSInterface.interfaceCache[script[counter++]];
					int item = script[counter++] + 1;
					if (item >= 0 && item < ItemDefinition.totalItems
							&& (!ItemDefinition.lookup(item).members || isMembers)) {
						for (int stored = 0; stored < equipmentContainer.inventoryItemId.length; stored++) {
							if (equipmentContainer.inventoryItemId[stored] != item)
								continue;
							value = 0x3b9ac9ff;
							break;
						}

					}
				}
				if (instruction == 11)
					value = energy;
				if (instruction == 12)
					value = weight;
				if (instruction == 13) {
					int bool = variousSettings[script[counter++]];
					int shift = script[counter++];
					value = (bool & 1 << shift) == 0 ? 0 : 1;
				}
				if (instruction == 14) {
					int index = script[counter++];
					VarBit bits = VarBit.cache[index];
					int setting = bits.anInt648;
					int low = bits.anInt649;
					int high = bits.anInt650;
					int mask = anIntArray1232[high - low];
					value = variousSettings[setting] >> low & mask;
				}
				if (instruction == 15)
					next = 1;
				if (instruction == 16)
					next = 2;
				if (instruction == 17)
					next = 3;
				if (instruction == 18)
					value = (localPlayer.x >> 7) + baseX;
				if (instruction == 19)
					value = (localPlayer.y >> 7) + baseY;
				if (instruction == 20)
					value = script[counter++];
				if (next == 0) {
					if (operator == 0)
						accumulator += value;
					if (operator == 1)
						accumulator -= value;
					if (operator == 2 && value != 0)
						accumulator /= value;
					if (operator == 3)
						accumulator *= value;
					operator = 0;
				} else {
					operator = next;
				}
			} while (true);
		} catch (Exception _ex) {
			return -1;
		}
	}

	public void drawTopLeftTooltip() {
		if (devConsole.console_open)
			return;

		if (fullscreenInterfaceID != -1) {
			return;
		}
		if(getMenuEntries().length <= 1)
			return;
		if (tabInterfaceIDs[tabID] == 17200 && menuManager.getMenuEntry(1).getOption().contains("ctivate")) {
			menuManager.getMenuEntry(1).setOption("Select");
			menuManager.getMenuEntry(1).setType(850);
			menuActionRow = 2;
		}
		if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0)
			return;
		String s;
		if (itemSelected == 1 && menuActionRow < 2)
			s = "Use " + selectedItemName + " with...";
		else if (spellSelected == 1 && menuActionRow < 2)
			s = spellTooltip + "...";
		else
			s = (menuManager.getMenuEntry(menuActionRow - 1).getTarget() == null || menuManager.getMenuEntry(menuActionRow - 1).getTarget().equalsIgnoreCase("null")) ? menuManager.getMenuEntry(menuActionRow - 1).getOption() : menuManager.getMenuEntry(menuActionRow - 1).getOption() + " " + menuManager.getMenuEntry(menuActionRow - 1).getTarget();

		//if (s != null && !s.contains("Walk here"))
		//	drawHoverBox(MouseHandler.mouseX+10, MouseHandler.mouseY-10, s);

		if (menuActionRow > 2)
			s = s + "@whi@ / " + (menuActionRow - 2) + " more options";

		if (s == null) {
			s = "";
		}

		toolTip=s;
		newBoldFont.drawString(s, 8, 19, 0xffffff, 0, 255);
	}
	public String toolTip;

	private Sprite compassImage;
	private Sprite[] mapArea = new Sprite[8];

	private Sprite eventIcon;
	public Sprite bankDivider;

	private int[][] getCustomMapIcons() {
		return new int[][] {
				//map icons minimap icons map sprites
				/*
				0 gen store
				1 sword
				2 mage
				3 b axe
				4 med helm
				5 bank
				6 quest
				7 jewerlly
				8 mining
				9 furnace
				10 smithing
				11 dummy
				12 dungeon
				13 leave dung
				14 shortcut
				15 bstaff
				16 gray top
				17 gray legs
				18 small sword
				19 arrow
				20 shield
				21 altar
				22 herb
				23 ring
				24 red gem
				25 crafting chisel
				26 ccandle
				27 fishing rod
				28 fish spot
				29 green top
				30 potions
				31 silk
				32 kebab
				33 beer
				34 chisel?
				35 brown top
				36 woodcutting
				37 wheel
				38 bread
				39 fork and knife
				40 minigame
				41 water
				42 pot
				43 pink skirt
				44 bowl
				45 grain
				46 golden axe
				47 chain body
				48 gray rock, spices?
				49 red man
				50 bowl with red stuff
				51 agility
				52 apple
				53 slayer
				54 haircut
				55 shovel
				56 appearence mask editor
				57 travel arrow
				58 purple portal
				59 pot
				60 fence
				61 barrel
				62 butter
				63 water
				64 hunter
				65 vote
				66 leather
				67 house
				68 saw
				69 hammer and rock
				70 shortcut
				71 box
				72 gift box
				73 sand pit
				74 green achievement star
				75 pets
				76 bounty icon
				77 sword and shirt icon
				78 logs
				79 plants
				80 runecrafting
				81 rc rock
				82 grand exchange
				83 purple star
				84 brown star
				85 question mark tutorial icons
				86 bank
				87 lock
				88 wc
				89 mining
				90 fishing
				91 hunter
				92 smithing
				93 crafting
				94 cooking
				95 melee
				96 prayer
				97 dungeon
				98 ironman
				99 bounty hunter
				100 bond
				101 holiday gift
				102 bottle
				103 partyhat
				104 newspaper
				105 cup of tea
				106 bell
				107 red bottle
				108 whichs pot
				109 rope
				110 hand
				111 band aid
				112 ancient altar
				113 bowl
				 */
				// sprite id, x, y
				{53, 3094, 3491}, // slayer area

				{40, 3109, 3478}, // outlast portal
				{40, 3067, 3499}, // hespori entrance
				{98, 3091, 3501}, // ironman store
				{77, 3089, 3491}, //supplies area
				{24, 3108, 3495}, //thieving red gem

				{51, 3099, 3493}, //agility

				{5, 3101, 3508}, //bank

				{21, 3121, 3500}, // altar
				{89, 3083, 3473}, // Mining
				{88, 3082, 3482}, // Tree
				{80, 3108, 3502}, // Runecrafting
				{93, 3100, 3481}, // Crafting
				{57, 3082, 3499}, // travel arrow
				{28, 3118, 3465} //fishing
		};
	}

	Sprite donatorOrb = null;
	Sprite donatorOrbHover = null;

	Sprite utilityOrb = null;
	Sprite utilityOrbHover = null;

	Sprite questOrb = null;
	Sprite questOrbHover = null;

	Sprite knowOrb = null;
	Sprite knowOrbHover = null;

	private void drawMinimap() {

		int xOffset = !isResized() ? 516 : 0;

		/** Black minimap **/
		if (minimapState == 2) {
			if (!isResized()) {
				mapArea[3].drawSprite(xOffset, 4);
			} else {
				mapArea[2].drawSprite(canvasWidth - 183, 0);
				mapArea[4].drawSprite(canvasWidth - 160, 8);
			}
			if(getUserSettings().isOldGameframe() && !isResized() ){
				compassImage.rotate(33, viewRotation, anIntArray1057, 256, anIntArray968,
						(!isResized() ? 28 + xOffset : 25), 4,
						(!isResized() ? 27 + xOffset : canvasWidth - 178), 33, 25);
			} else {
				compassImage.rotate(33, viewRotation, anIntArray1057, 256, anIntArray968,
						(!isResized() ? 25 : 25) , 4,
						(!isResized() ? 29 + xOffset : canvasWidth - 178) , 33, 25);
			}

			return;
		}

		// If not loaded in don't draw mapback
		if (loadingStage == 2) {

			int i = viewRotation + minimapRotation & 0x7ff;
			int j = 48 + localPlayer.x / 32;
			int l2 = 464 - localPlayer.y / 32;

			int positionX = (!isResized() ? 9 : 7);
			int positionY = (!isResized() ? 51 + xOffset : canvasWidth - 160);

			minimapImage.rotate(151, i, anIntArray1229, 256 + minimapZoom, anIntArray1052, l2, positionX , positionY, 151, j);

			for (int[] icon : getCustomMapIcons()) {
				markMinimap(mapFunctions[icon[0]], ((icon[1] - baseX) * 4 + 2) - Client.localPlayer.x / 32, ((icon[2] - baseY) * 4 + 2) - Client.localPlayer.y / 32);
			}

			for (int j5 = 0; j5 < mapIconAmount; j5++) {
				int k = (anIntArray1072[j5] * 4 + 2) - localPlayer.x / 32;
				int i3 = (anIntArray1073[j5] * 4 + 2) - localPlayer.y / 32;
				markMinimap(mapIconSprite[j5], k, i3);
			}


			if (Configuration.HALLOWEEN) {
				switch (plane) {
					case 0:
						markMinimap(eventIcon, ((3088 - baseX) * 4 + 2) - localPlayer.x / 32,
								((3494 - baseY) * 4 + 2) - localPlayer.y / 32);
						markMinimap(eventIcon, ((2607 - baseX) * 4 + 2) - localPlayer.x / 32,
								((4773 - baseY) * 4 + 2) - localPlayer.y / 32);
						markMinimap(eventIcon, ((3103 - baseX) * 4 + 2) - localPlayer.x / 32,
								((3482 - baseY) * 4 + 2) - localPlayer.y / 32);
						break;
				}
			}
			if (Configuration.CHRISTMAS && Configuration.CHRISTMAS_EVENT) {
				switch (plane) {
					case 0:
						markMinimap(minimapIcons[0], ((3086 - baseX) * 4 + 2) - localPlayer.x / 32,
								((3498 - baseY) * 4 + 2) - localPlayer.y / 32);
						markMinimap(minimapIcons[0], ((2832 - baseX) * 4 + 2) - localPlayer.x / 32,
								((3798 - baseY) * 4 + 2) - localPlayer.y / 32);
						markMinimap(minimapIcons[0], ((2982 - baseX) * 4 + 2) - localPlayer.x / 32,
								((3643 - baseY) * 4 + 2) - localPlayer.y / 32);
						break;

					case 2:
						markMinimap(minimapIcons[0], ((2827 - baseX) * 4 + 2) - localPlayer.x / 32,
								((3810 - baseY) * 4 + 2) - localPlayer.y / 32);
						break;
				}
			}

			for (int k5 = 0; k5 < 104; k5++) {
				for (int l5 = 0; l5 < 104; l5++) {
					Deque class19 = groundItems[plane][k5][l5];
					if (class19 != null) {
						int l = (k5 * 4 + 2) - localPlayer.x / 32;
						int j3 = (l5 * 4 + 2) - localPlayer.y / 32;
						markMinimap(mapDots[0], l, j3);
					}
				}
			}

			for (int i6 = 0; i6 < npcCount; i6++) {
				Npc npc = npcs[npcIndices[i6]];
				if (npc != null && npc.isVisible()) {
					NpcDefinition entityDef = npc.desc;
					if (entityDef.configs != null)
						entityDef = entityDef.method161();
					if (entityDef != null && entityDef.onMinimap && entityDef.clickable) {
						int i1 = npc.x / 32 - localPlayer.x / 32;
						int k3 = npc.y / 32 - localPlayer.y / 32;
						markMinimap(mapDots[1], i1, k3);
					}
				}
			}

			for (int j6 = 0; j6 < playerCount; j6++) {
				Player player = players[playerIndices[j6]];
				if (player != null && player.isVisible()) {
					int j1 = player.x / 32 - localPlayer.x / 32;
					int l3 = player.y / 32 - localPlayer.y / 32;
					boolean flag1 = false;
					boolean flag3 = false;
					String clanname;
					for (int j3 = 0; j3 < clanList.length; j3++) {
						if (clanList[j3] == null)
							continue;
						clanname = clanList[j3];
						if (clanname.startsWith("<clan"))
							clanname = clanname.substring(clanname.indexOf(">") + 1, clanname.length());
						if (!clanname.equalsIgnoreCase(player.displayName))
							continue;
						flag3 = true;
						break;
					}
					long l6 = longForName(player.displayName);
					for (int k6 = 0; k6 < friendsCount; k6++) {
						if (l6 != friendsListAsLongs[k6] || friendsNodeIDs[k6] == 0)
							continue;
						flag1 = true;
						break;
					}
					boolean flag2 = false;
					if (localPlayer.team != 0 && player.team != 0 && localPlayer.team == player.team)
						flag2 = true;
					if (flag1)
						markMinimap(mapDots[3], j1, l3);
					else if (flag3)
						markMinimap(mapDots[5], j1, l3);
					else if (flag2)
						markMinimap(mapDots[4], j1, l3);
					else
						markMinimap(mapDots[2], j1, l3);
				}
			}

			if (hintType != 0 && loopCycle % 20 < 10) {
				if (hintType == 1 && anInt1222 >= 0 && anInt1222 < npcs.length) {
					Npc npc = npcs[anInt1222];
					if (npc != null) {
						int k1 = npc.x / 32 - localPlayer.x / 32;
						int i4 = npc.y / 32 - localPlayer.y / 32;
						refreshMinimap(mapMarker, i4, k1);
					}
				}
				if (hintType == 2) {
					int l1 = ((hintIconXpos - baseX) * 4 + 2) - localPlayer.x / 32;
					int j4 = ((hintIconYpos - baseY) * 4 + 2) - localPlayer.y / 32;
					refreshMinimap(mapMarker, j4, l1);
				}
				if (hintType == 10 && anInt933 >= 0 && anInt933 < players.length) {
					Player player = players[anInt933];
					if (player != null) {
						int i2 = player.x / 32 - localPlayer.x / 32;
						int k4 = player.y / 32 - localPlayer.y / 32;
						refreshMinimap(mapMarker, k4, i2);
					}
				}
			}

			if (destX != 0) {
				int j2 = (destX * 4 + 2) - localPlayer.x / 32;
				int l4 = (destY * 4 + 2) - localPlayer.y / 32;
				markMinimap(mapFlag, j2, l4); //764 1068
			}
			Rasterizer2D.drawBox((!isResized() ? xOffset + 127 : canvasWidth - 88), (!isResized() ? 83 : 80), 3, 3,
					0xffffff);

		}

		if (!isResized()) {
			//getOldSchoolSprite(1039).drawSprite(516, 0);
			getOldSchoolSprite(1037).drawSprite(xOffset, 4);
			getOldSchoolSprite(1182).drawSprite(545, 4);
			getOldSchoolSprite(1038).drawSprite(717, 4);
			getOldSchoolSprite(1611).drawSprite(xOffset, 160);
		} else
			mapArea[2].drawSprite(canvasWidth - 183, 0);

		compassImage.rotate(33, viewRotation, anIntArray1057, 256, anIntArray968,
				(!isResized() ? 25 : 25), 4,
				(!isResized() ? 29 + xOffset : canvasWidth - 178), 33, 25);
		if (drawOrbs)
			loadAllOrbs(!isResized() ? 0 + xOffset : canvasWidth - 217);

		if (isResized()) {
			(donatorHover ? donatorOrbHover :  donatorOrb).drawSprite(canvasWidth - 31, 145);
			(worldHover ? knowOrbHover :  knowOrb).drawSprite(canvasWidth - 62 + xOffset, 160);
			(taskHover ? utilityOrbHover :  utilityOrb).drawSprite(canvasWidth - 93, 170);
			(questHover ? questOrbHover :  questOrb).drawSprite(canvasWidth - 124, 165);
		} else {
			(donatorHover ? donatorOrbHover :  donatorOrb).drawSprite(215 - 2 + xOffset, 10 + 90);
			(taskHover ? utilityOrbHover :  utilityOrb).drawSprite(222 - 2 + xOffset, 10 + 60);
			(worldHover ? knowOrbHover :  knowOrb).drawSprite(202 - 2 + xOffset, 20 + 108);
			(questHover ? questOrbHover :  questOrb).drawSprite(217 - 2 + xOffset, 10 + 30);
		}
		if (menuOpen) {
			drawMenu(menuOffsetX, menuOffsetY);
		}

	}

	public static AbstractRasterProvider rasterProvider;

	private boolean hpHover = false;


	private void scrollInterfaceBottom(int maxWidth, int width, int mouseX, int mouseY, RSInterface class9,
									   int maxHeight, boolean flag, int scrollMax) {
		int anInt992;
		if (aBoolean972) {
			anInt992 = 32;
		} else {
			anInt992 = 0;
		}
		aBoolean972 = false;

		if (mouseX >= maxWidth && mouseX < maxWidth + 16 && mouseY >= maxHeight && mouseY < maxHeight + 16) {
			class9.scrollPosition -= mouseClickCount * 4;
			if (flag) {
				//  needDrawTabArea = true;
			}
		} else if (mouseY >= maxHeight && mouseY < maxHeight + 16 && mouseX >= (maxWidth + width) - 16
				&& mouseX < maxWidth + width) {
			class9.scrollPosition += mouseClickCount * 4;
			if (flag) {
				// needDrawTabArea = true;
			}
		} else if (mouseY >= maxHeight - anInt992 && mouseY < maxHeight + 16 + anInt992 && mouseX >= maxWidth + 16
				&& mouseX < (maxWidth + width) - 16 && mouseClickCount > 0) {
			int l1 = ((width - 32) * width) / scrollMax;
			if (l1 < 8) {
				l1 = 8;
			}
			int i2 = mouseX - maxWidth - 16 - l1 / 2;
			int j2 = width - 32 - l1;
			class9.scrollPosition = ((scrollMax - width) * i2) / j2;
			if (flag) {
				//  needDrawTabArea = true;
			}
			aBoolean972 = true;
		}
	}

	private void loadHpOrb(int xOffset) {
		int yOff = !isResized() ? 0 : -5;
		int xOff = !isResized() ? 0 : -6;
		String cHP = RSInterface.interfaceCache[4016].message;
		String mHP = RSInterface.interfaceCache[4017].message;
		int currentHP = Integer.parseInt(cHP);
		int maxHP = Integer.parseInt(mHP);
		int health = (int) (((double) currentHP / (double) maxHP) * 100D);
		int poisonType = variousSettings[Configs.POISON_CONFIG];
		Sprite bg = getOldSchoolSprite(hpHover ? 1072 : 1071);
		Sprite fg = null;
		if (poisonType == 0 || poisonType == 3) {
			fg = cacheSprite3[161];
		}
		if (poisonType == 1) {
			fg = cacheSprite3[162];
		}
		if (poisonType == 2) {
			fg = venomOrb;
		}
		bg.drawSprite(0 + xOffset - xOff, 41 - yOff);
		fg.drawSprite(27 + xOffset - xOff, 45 - yOff);
		if (getOrbFill(health) <= 26) {
			cacheSprite3[160].myHeight = getOrbFill(health);
		} else {
			cacheSprite3[160].myHeight = 26;
		}
		cacheSprite3[160].drawSprite(27 + xOffset - xOff, 45 - yOff);
		cacheSprite3[168].drawSprite(27 + xOffset - xOff, 45 - yOff);
		if (health > 1_000_000_000) {
			infinity.drawSprite(10 + xOffset - xOff, 59 - yOff);
		} else {
			smallText.method382(getOrbTextColor(health), 15 + xOffset - xOff, "" + cHP, 67 - yOff, true);
		}
	}

	public double fillHP;

	public Sprite worldMap1, worldMap2, worldMap3;

	private boolean specialHover;
	private int specialEnabled;
	public int specialAttack=100;

	private void drawSpecialOrb(int xOffset) {
		Sprite image = getOldSchoolSprite(specialHover ? 1072 : 1071);
		Sprite fill = cacheSprite[specialEnabled == 0 ? 9 : 6];
		Sprite sword = cacheSprite[12];
		double percent = specialAttack  / (double) 100;
		//int percent = 100;
		boolean isFixed = !isResized();
		image.drawSprite((isFixed ? 37 : 37) + xOffset, isFixed ? 134 : 139);
		if(specialEnabled==1) {
			//fill.drawSprite((isFixed ? 60 : 133) + xOffset, isFixed ? 134 : 151);
			fill.drawSprite((isFixed ? 63 : 63) + xOffset, isFixed ? 137 : 141);
		}else{
			fill.drawSprite((isFixed ? 64 : 64) + xOffset, isFixed ? 138 : 143);
			//fill.drawSprite((isFixed ? 65 : 133) + xOffset, isFixed ? 139 : 151);
		}
		sword.drawSprite((isFixed ? 64 : 64) + xOffset, isFixed ? 138 : 143);

		smallText.method382(getOrbTextColor((int) (percent * 100)),
				(isFixed ? 53 : 53) + xOffset, specialAttack + "", isFixed ? 159 : 164,
				true);
	}

	private void loadPrayerOrb(int xOffset) {
		int yOff = Configuration.osbuddyGameframe ? !isResized() ? 10 : 2
				: !isResized() ? 0 : -5;
		int xOff = !isResized() ? -1 : -7;
		Sprite bg = getOldSchoolSprite(prayHover ? 1072 : 1071);
		Sprite fg = prayClicked ? new Sprite("Gameframe/newprayclicked") : cacheSprite1[1];
		bg.drawSprite(0 + xOffset - xOff, 75 - yOff);
		fg.drawSprite(27 + xOffset - xOff, 79 - yOff);
		int level = Integer.parseInt(RSInterface.interfaceCache[4012].message.replaceAll("%", ""));
		int max = maximumLevels[5];
		double percent = level / (double) max;
		cacheSprite1[14].myHeight = (int) (26 * (1 - percent));
		cacheSprite1[14].drawSprite(27 + xOffset - xOff, 79 - yOff);
		if (percent <= .25) {
			cacheSprite1[10].drawSprite(30 + xOffset - xOff, 82 - yOff);
		} else {
			cacheSprite1[10].drawSprite(30 + xOffset - xOff, 82 - yOff);
		}
		if (level > 1_000_000_000) {
			infinity.drawSprite(11 + xOffset - xOff, 94 - yOff);
		} else {
			smallText.method382(getOrbTextColor((int) (percent * 100)), 14 + xOffset - xOff, level + "", 101 - yOff, true);
		}


	}

	private void loadRunOrb(int xOffset) {
		int current = Integer.parseInt(RSInterface.interfaceCache[22539].message.replaceAll("%", ""));
		int yOff = Configuration.osbuddyGameframe ? !isResized() ? 15 : 5
				: !isResized() ? 1 : -4;
		int xMinus = Configuration.osbuddyGameframe ? !isResized() ? 11 : 5
				: !isResized() ? -1 : -6;
		Sprite bg = getOldSchoolSprite(runHover ? 1072 : 1071);
		boolean running = variousSettings[173] == 1;
		Sprite fg = cacheSprite1[running ? 4 : 3];
		bg.drawSprite(10 + xOffset - xMinus, 109 - yOff);
		fg.drawSprite(37 + xOffset - xMinus, 113 - yOff);
		int level = current;
		double percent = level / (double) 100;
		cacheSprite1[14].myHeight = (int) (26 * (1 - percent));
		cacheSprite1[14].drawSprite(37 + xOffset - xMinus, 113 - yOff);
		if (percent <= .25) {
			cacheSprite1[running ? 12 : 11].drawSprite(43 + xOffset - xMinus, 117 - yOff);
		} else {
			cacheSprite1[running ? 12 : 11].drawSprite(43 + xOffset - xMinus, 117 - yOff);
		}
		smallText.method382(getOrbTextColor((int) (percent * 100)), 25 + xOffset - xMinus, level + "", 135 - yOff,
				true);
	}

	private Sprite venomOrb;

	private void loadAllOrbs(int xOffset) {
		try {
			loadHpOrb(xOffset);
			loadPrayerOrb(xOffset);
			loadRunOrb(xOffset);
			drawSpecialOrb(xOffset);
		} catch (Exception e) {}
		if (drawExperienceCounter) {
			if (counterHover) {
				getOldSchoolSprite(1199).drawSprite(
						drawOrbs && !isResized() ? 0 + xOffset : canvasWidth - 211,
						!isResized() ? 21 : 25);
			} else {
				getOldSchoolSprite(1198).drawSprite(
						drawOrbs && !isResized() ? 0 + xOffset : canvasWidth - 211,
						!isResized() ? 21 : 25);
			}
		} else {
			if (counterHover) {
				getOldSchoolSprite(1197).drawSprite(
						drawOrbs && !isResized() ? 0 + xOffset : canvasWidth - 211,
						!isResized() ? 21 : 25);
			} else {
				getOldSchoolSprite(1196).drawSprite(
						drawOrbs && !isResized() ? 0 + xOffset : canvasWidth - 211,
						!isResized() ? 21 : 25);
			}
		}
//		Rasterizer2D.drawAlphaBox(xOffset, 0, 1, 200, 0x332B16, 250);
	}

	public boolean runClicked = false;

	public int getOrbTextColor(int i) {
		if (i >= 75 && i <= 0x7fffffff)
			return 65280;
		if (i >= 50 && i <= 74)
			return 0xffff00;
		return i < 25 || i > 49 ? 0xff0000 : 0xFFB400;
	}

	private Sprite mapIcon9, mapIcon7, mapIcon8, mapIcon6, mapIcon5;

	public int getOrbFill(int statusInt) {
		if (statusInt <= Integer.MAX_VALUE && statusInt >= 97)
			return 0;
		else if (statusInt <= 96 && statusInt >= 93)
			return 1;
		else if (statusInt <= 92 && statusInt >= 89)
			return 2;
		else if (statusInt <= 88 && statusInt >= 85)
			return 3;
		else if (statusInt <= 84 && statusInt >= 81)
			return 4;
		else if (statusInt <= 80 && statusInt >= 77)
			return 5;
		else if (statusInt <= 76 && statusInt >= 73)
			return 6;
		else if (statusInt <= 72 && statusInt >= 69)
			return 7;
		else if (statusInt <= 68 && statusInt >= 65)
			return 8;
		else if (statusInt <= 64 && statusInt >= 61)
			return 9;
		else if (statusInt <= 60 && statusInt >= 57)
			return 10;
		else if (statusInt <= 56 && statusInt >= 53)
			return 11;
		else if (statusInt <= 52 && statusInt >= 49)
			return 12;
		else if (statusInt <= 48 && statusInt >= 45)
			return 13;
		else if (statusInt <= 44 && statusInt >= 41)
			return 14;
		else if (statusInt <= 40 && statusInt >= 37)
			return 15;
		else if (statusInt <= 36 && statusInt >= 33)
			return 16;
		else if (statusInt <= 32 && statusInt >= 29)
			return 17;
		else if (statusInt <= 28 && statusInt >= 25)
			return 18;
		else if (statusInt <= 24 && statusInt >= 21)
			return 19;
		else if (statusInt <= 20 && statusInt >= 17)
			return 20;
		else if (statusInt <= 16 && statusInt >= 13)
			return 21;
		else if (statusInt <= 12 && statusInt >= 9)
			return 22;
		else if (statusInt <= 8 && statusInt >= 7)
			return 23;
		else if (statusInt <= 6 && statusInt >= 5)
			return 24;
		else if (statusInt <= 4 && statusInt >= 3)
			return 25;
		else if (statusInt <= 2 && statusInt >= 1)
			return 26;
		else if (statusInt <= 0)
			return 27;
		return 0;
	}

	public Sprite xpSprite;
	public Sprite xpbg1 = new Sprite("487");
	public Sprite xpbg2 = new Sprite("488");

	private Queue<ExperienceDrop> experienceDrops = new LinkedList<>();

	public static int getProgressColor(int percent) {
		if(percent <= 15) {
			return 0x808080;
		}
		if(percent <= 45) {
			return 0x7f7f00;
		}
		if(percent <= 65) {
			return 0x999900;
		}
		if(percent <= 75) {
			return 0xb2b200;
		}
		if(percent <= 90) {
			return 0x007f00;
		}
		return 31744;
	}
	private void processExperienceCounter() {
		if (loopCycle % 1 <= 1 && !experienceDrops.isEmpty()) {
			Collection<ExperienceDrop> remove = new ArrayList<>();
			for (ExperienceDrop drop : experienceDrops) {
				drop.pulse();
				if (drop.getYPosition() == -1) {
					experienceCounter += drop.getAmount();
					remove.add(drop);
				}
			}
			experienceDrops.removeAll(remove);
		}

		if (!drawExperienceCounter || openInterfaceID > -1) {
			return;
		}
		SkillOrbs.process();
		for (ExperienceDrop drop : experienceDrops) {
			String text = drop.toString();
			int x = (!isResized() ? 507 : canvasWidth - 246)
					- newSmallFont.getTextWidth(text);
			int y = drop.getYPosition() - 15;
			int transparency = 256;
			newSmallFont.drawString(text, x, y, 0xFFFFFF, 0x000000, 256);

			for (int skill : drop.getSkills()) {
/*				if(skill==22){
					continue;
				}*/

				Sprite sprite = smallXpSprites[skill];
				x -= sprite.myWidth + 3;
				y -= sprite.myHeight - 4;
				sprite.drawAdvancedSprite(x, y, transparency);
				y += sprite.myHeight - 4;
			}
		}

		String experience = NumberFormat.getInstance().format(experienceCounter);

		xpbg1.drawAdvancedSprite(!isResized() ? 395 : canvasWidth - 365, 6);
		xpbg2.drawSprite(!isResized() ? 398 : canvasWidth - 363, 9);

		newSmallFont.drawBasicString(experience, (!isResized() ? 510 : canvasWidth - 252)
				- newSmallFont.getTextWidth(experience), 24, 0xFFFFFF, 0x000000);
	}

	private boolean drawExperienceCounter = true;

	public void npcScreenPos(Entity entity, int i) {
		calcEntityScreenPos(entity.x, i, entity.y);
	}

	public void calcEntityScreenPos(int i, int j, int l) {

		if ((i < 128) || (l < 128) || (i > 13056) || (l > 13056)) {
			this.spriteDrawX = -1;
			this.spriteDrawY = -1;
			return;
		}
		int i1 = getCenterHeight(this.plane, l, i) - j; //getCenterHeight
		i -= this.xCameraPos;
		i1 -= this.zCameraPos;
		l -= this.yCameraPos;
		int j1 = Model.SINE[this.yCameraCurve];
		int k1 = Model.COSINE[this.yCameraCurve];
		int l1 = Model.SINE[this.xCameraCurve];
		int i2 = Model.COSINE[this.xCameraCurve];
		int j2 = l * l1 + i * i2 >> 16;
		l = l * i2 - i * l1 >> 16;
		i = j2;
		j2 = i1 * k1 - l * j1 >> 16;
		l = i1 * j1 + l * k1 >> 16;
		i1 = j2;
		if (l >= 50) {
			spriteDrawX = (Rasterizer3D.originViewX + i * Rasterizer3D.fieldOfView / l) + (!isResized() ? 4 : 0);
			spriteDrawY = (Rasterizer3D.originViewY + i1 * Rasterizer3D.fieldOfView / l) + (!isResized() ? 4 : 0);
		} else {
			spriteDrawX = -1;
			spriteDrawY = -1;
		}
	}

	public void method130(int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2) {
		SpawnedObject spawnedObject = null;
		for (spawnedObject = (SpawnedObject) spawns
				.reverseGetFirst(); spawnedObject != null; spawnedObject = (SpawnedObject) spawns
				.reverseGetNext()) {
			if (spawnedObject.plane != l1 || spawnedObject.x != i2 || spawnedObject.y != j1
					|| spawnedObject.group != i1)
				continue;
			spawnedObject = spawnedObject;
			break;
		}

		if (spawnedObject == null) {
			spawnedObject = new SpawnedObject();
			spawnedObject.plane = l1;
			spawnedObject.group = i1;
			spawnedObject.x = i2;
			spawnedObject.y = j1;
			method89(spawnedObject);
			spawns.insertHead(spawnedObject);
		}
		spawnedObject.objectId = k;
		spawnedObject.type = k1;
		spawnedObject.orientation = l;
		spawnedObject.delay = j2;
		spawnedObject.getLongetivity = j;
	}

	public boolean interfaceIsSelected(RSInterface class9) {
		if (class9.anIntArray245 == null)
			return false;
		for (int i = 0; i < class9.anIntArray245.length; i++) {
			int j = extractInterfaceValues(class9, i);
			int k = class9.anIntArray212[i];
			if (class9.anIntArray245[i] == 2) {
				if (j >= k)
					return false;
			} else if (class9.anIntArray245[i] == 3) {
				if (j <= k)
					return false;
			} else if (class9.anIntArray245[i] == 4) {
				if (j == k)
					return false;
			} else if (j != k)
				return false;
		}

		return true;
	}



	public static final int method1004(int i) {
		return (int) (Math.log((double) i * 0.00390625) * 868.5889638065036 + 0.5);
	}

	public static final void method684(boolean bool, int i, int volume, byte[] is) {
		if (midi_player != null) {
			if (anInt478 >= 0) {
				anInt2200 = i;
				if (anInt478 != 0) {
					int i_4_ = method1004(anInt478);
					i_4_ -= anInt155;
					anInt720 = (i_4_ + 3600) / i;
					if (anInt720 < 1)
						anInt720 = 1;
				} else
					anInt720 = 1;
				music_payload = is;
				jmp_volume = volume;
				aBoolean475 = bool;
			} else if (anInt720 == 0)
				method853(volume, is, bool);
			else {
				jmp_volume = volume;
				aBoolean475 = bool;
				music_payload = is;
			}
		}
	}

	public static final void method899(int i, int i_29_, boolean bool, byte[] payload, int i_30_) {
		if (midi_player != null) {
			if (i_29_ >= (anInt478 ^ 0xffffffff)) {
				i -= 20;
				if (i < 1)
					i = 1;
				anInt720 = i;
				if (anInt478 == 0)
					anInt2200 = 0;
				else {
					int i_31_ = method1004(anInt478);
					i_31_ -= anInt155;
					anInt2200 = ((anInt2200 - 1 + (i_31_ + 3600)) / anInt2200);//((anInt2200 - 1 + (i_31_ + 3600)) / anInt2200)
				}
				aBoolean475 = bool;
				music_payload = payload;
				jmp_volume = i_30_;
			} else if (anInt720 != 0) {
				aBoolean475 = bool;
				music_payload = payload;
				jmp_volume = i_30_;
			} else
				method853(i_30_, payload, bool);
		}
	}

	public static final synchronized void processMusic() {
		if (musicIsntNull()) {
			if (fetchMusic) {
				byte[] payload = music_payload;
				if (payload != null) {
					if (fadeDuration >= 0) {
						method684(repeatMusic, fadeDuration, music_volume, payload);
					} else if (anInt139 >= 0) {
						method899(anInt139, -1, repeatMusic, payload, music_volume);
					} else {
						method853(music_volume, payload, repeatMusic);
					}
					fetchMusic = false;
				}
			}
			method368(0);
		}
	}
	public static final void method790() {
		if (midi_player != null) {
			method891(false);
			if (anInt720 > 0) {
				midi_player.method831(256);
				anInt720 = 0;
			}
			midi_player.remove();
			midi_player = null;
		}
	}

	public static void setVolumeMusic(int volume) {
		int finalVolume = (int) ((volume / 10.0) * 255);
		if (midi_player != null) {
			if (anInt720 == 0) {
				if (anInt478 >= 0) {
					anInt478 = finalVolume;
					midi_player.method830(finalVolume, 0);
				}
			} else if (music_payload != null) {
				jmp_volume = finalVolume;
			}
		}
		music_volume = musicVolume = finalVolume;
	}

	final synchronized void requestMusic(int musicId) {
		setVolumeMusic(Preferences.getPreferences().musicEnabled && getGameState().equals(GameState.LOGIN_SCREEN) ? 5 : Preferences.getPreferences().musicVolume);
		if (musicIsntNull()) {
			nextSong = musicId;
			resourceProvider.provide(2, nextSong);
			music_volume = musicVolume;
			anInt139 = -1;
			repeatMusic = true;
			fadeDuration = 100;//default value of -1
		}
	}

	public static final void method891(boolean bool) {
		method853(0, null, bool);
	}

	public static final void method853(int i_2_, byte[] payload, boolean bool) {
		if (midi_player != null) {
			if (anInt478 >= 0) {
				midi_player.stop();
				anInt478 = -1;
				music_payload = null;
				anInt720 = 20;
				anInt155 = 0;
			}
			if (payload != null) {
				if (anInt720 > 0) {
					midi_player.method831(i_2_);
					anInt720 = 0;
				}
				anInt478 = i_2_;
				midi_player.method827(i_2_, payload, 0, bool);
			}
		}
	}

	public static final void method368(int i) {
		if (midi_player != null) {
			if (anInt478 < i) {
				if (anInt720 > 0) {
					anInt720--;
					if (anInt720 == 0) {
						if (music_payload == null)
							midi_player.method831(256);
						else {
							midi_player.method831(jmp_volume);
							anInt478 = jmp_volume;
							midi_player.method827(jmp_volume, music_payload, 0, aBoolean475);
							music_payload = null;
						}
						anInt155 = 0;
					}
				}
			} else if (anInt720 > 0) {
				anInt155 += anInt2200;
				midi_player.method830(anInt478, anInt155);
				anInt720--;
				if (anInt720 == 0) {
					midi_player.stop();
					anInt720 = 20;
					anInt478 = -1;
				}
			}
			midi_player.method832(i - 122);
		}
	}

	static final boolean musicIsntNull() {
		if (midi_player == null)
			return false;

		return true;
	}

	private boolean saveWave(byte[] data, int id) {
		return false;
		//return data == null || SignLink.wavesave(data, id);
	}

	private void processSoundEffects() {
		for (int count = 0; count < soundCount; count++) {
			boolean replay = false;
			try {
				Buffer stream = SoundEffects.data(soundLoops[count], sounds[count]);
				new SoundPlayer(new ByteArrayInputStream(stream.payload, 0, stream.currentPosition), soundVolume[count], soundDelay[count]);
				if (System.currentTimeMillis() + (long) (stream.currentPosition / 22) > soundTimer + (long) (currentSoundTime / 22)) {
					currentSoundTime = stream.currentPosition;
					soundTimer = System.currentTimeMillis();
					if (saveWave(stream.payload, stream.currentPosition)) {
						currentSoundPlaying = sounds[count];
						currentSoundLoop = soundLoops[count];
					} else {
						replay = true;
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			if (!replay || soundDelay[count] == -5) {
				soundCount--;
				for (int index = count; index < soundCount; index++) {
					sounds[index] = sounds[index + 1];
					soundLoops[index] = soundLoops[index + 1];
					soundDelay[index] = soundDelay[index + 1];
					soundVolume[index] = soundVolume[index + 1];
				}
				count--;
			} else {
				soundDelay[count] = -5;
			}
		}
	}

	public void method134(Buffer stream) {
		int j = stream.readBits(8);
		if (j < playerCount) {
			for (int k = j; k < playerCount; k++)
				anIntArray840[anInt839++] = playerIndices[k];

		}
		if (j > playerCount) {
			Signlink.reporterror(myUsername + " Too many players");
			throw new RuntimeException("eek");
		}
		playerCount = 0;
		for (int l = 0; l < j; l++) {
			int i1 = playerIndices[l];
			Player player = players[i1];
			int j1 = stream.readBits(1);
			if (j1 == 0) {
				playerIndices[playerCount++] = i1;
				player.anInt1537 = loopCycle;
			} else {
				int k1 = stream.readBits(2);
				if (k1 == 0) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = i1;
				} else if (k1 == 1) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					int l1 = stream.readBits(3);
					player.moveInDir(false, l1);
					int j2 = stream.readBits(1);
					if (j2 == 1)
						anIntArray894[anInt893++] = i1;
				} else if (k1 == 2) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					int i2 = stream.readBits(3);
					player.moveInDir(true, i2);
					int k2 = stream.readBits(3);
					player.moveInDir(true, k2);
					int l2 = stream.readBits(1);
					if (l2 == 1)
						anIntArray894[anInt893++] = i1;
				} else if (k1 == 3)
					anIntArray840[anInt839++] = i1;
			}
		}
	}

	private Map<String, Sprite> screenImages;

	private final void createScreenImages() {
		if (screenImages != null) {
			return;
		}
		screenImages = new HashMap<>();
		screenImages.put("background", new Sprite("Login/background")); // not used
	}

	public Sprite loginAsset0;
	public Sprite loginAsset1;
	public Sprite loginAsset2;
	public Sprite loginAsset3;
	public Sprite loginAsset4;
	public Sprite loginAsset5;
	public Sprite loginAsset6;
	public Sprite loginAsset7;
	public Sprite loginAsset8;
	public Sprite loginAsset9;
	public Sprite loginAsset10;
	public Sprite loginAsset11;
	public Sprite loginAsset12;
	public Sprite loginAsset13;
	public Sprite loginAsset14;
	public Sprite loginAsset15;
	public Sprite loginAsset16;
	private String firstLoginMessage = "Please enter your username & password.";
	Image icon = null;

	public boolean rememberMeHover;
	long lastLogin = 0;
	long loadDelay = 0;
	private Sprite loginScreenBackground;
	private Sprite loginScreenBackgroundCaptcha;
	private Sprite captchaExit;
	private Sprite captchaExitHover;
	private Sprite logo2021;
	boolean[] accountDeletion = new boolean[3];
	boolean[] accountHovers = new boolean[3];
	private final AccountManager accountManager;
	private Sprite captcha;

	private static boolean viewNewsPanel = true;

	public static ArrayList<Bubble> bubbles = new ArrayList<Bubble>();

	public void drawLoginScreen(boolean flag) {

		if (AccountScreen) {
			OpenAccountScreen();
			return;
		}

		if (AvatarScreen) {
			OpenAvatarScreen();
			return;
		}

		if (ErrorScreen) {
			OpenErrorScreen();
			return;
		}

		int centerX = canvasWidth / 2, centerY = canvasHeight / 2;

		if(System.currentTimeMillis() - lastLogin > 50) {
			if (loginScreenState == LoginScreenState.CAPTCHA)
				loginScreenBackgroundCaptcha.drawAdvancedSprite(0,0);
			else
				loginScreenBackground.drawAdvancedSprite(0,0);
		}

		if (Configuration.developerMode) {
			newSmallFont.drawString("FPS: " + fps, 4, 12, Integer.MAX_VALUE, 0, 255);
			newSmallFont.drawString("Mouse: [" + MouseHandler.mouseX + ", " + MouseHandler.mouseY + "]", 4, 12 + 12, Integer.MAX_VALUE, 0, 255);
		}

		int j = centerY - 40;

		if (loginScreenState == LoginScreenState.CAPTCHA) {
			if (firstLoginMessage.length() > 0) {
				newBoldFont.drawCenteredString(firstLoginMessage, centerX +90, j - 75, 0xffffff, 0x191919, 255);
				j += 30;
			}

			if (captcha != null)
				captcha.drawAdvancedSpriteCentered(382, 446);

			newBoldFont.drawString(
					captchaInput + ((loopCycle % 40 < 20) ? "|" : ""),
					(canvasWidth / 2) - 100, canvasHeight / 2 -15, 0xffffff, 0x191919, 255);


			int exitX = 494;
			int exitY = 236;

		} else if (loginScreenState == LoginScreenState.LOGIN) {
			if (firstLoginMessage.length() > 0) {
				newBoldFont.drawCenteredString(firstLoginMessage, centerX, j - 50, 0xffffff, 0x191919, 255);
				j += 60;
			}

//			newBoldFont.drawString("Online!", 108,448,0x00ff00,0x191919, 255);

			newBoldFont.drawString(
					myUsername + ((loginScreenCursorPos == 0) & (loopCycle % 40 < 20) ? "|" : ""),
					(canvasWidth / 2) - 20, canvasHeight / 2 - 20, 0xffffff, 0x191919, 255);
			j += 15;
			newBoldFont.drawString(
					StringUtils.passwordAsterisks(getPassword())
							+ ((loginScreenCursorPos == 1) & (loopCycle % 40 < 20) ? "|" : ""),
					(canvasWidth / 2) - 20, canvasHeight / 2 + 45, 0xffffff, 0x191919, 255);

			int rememberYOffset = 6;

			int extraPos = 18;
			worldswitcher = mouseInRegion(centerX - 52, centerY + 132, centerX + 27, centerY + 158);
			rememberMeHover = mouseInRegion(centerX - 26, centerY - 41, centerX + 203, centerY - 11);
			rememberPasswordHover = mouseInRegion(centerX - 26, centerY + 23, centerX + 203, centerY + 52);
			loginbutton = mouseInRegion(350, 350, 545, 380);

/*			rememberMeHover = mouseInRegion(263, 290, 280, 306);
			rememberPasswordHover = mouseInRegion(394, 291, 411, 306);*/

			if (firstLoginMessage.equalsIgnoreCase("")) {
				firstLoginMessage = "Welcome to Exiled!";
			}

			if (rememberMeHover) {
				loginAsset0.drawTransparentSprite(356, 210,50);
				addTooltip("Enter your username");
			}

			if (rememberPasswordHover) {
				loginAsset1.drawTransparentSprite(356, 274,50);
				addTooltip("Enter your password");
			}

			if (!loginbutton) {
				loginAsset6.drawSprite(350, 350);
			} else {
				loginAsset7.drawSprite(350, 350);
				addTooltip("Login.");
			}

			if (informationFile.isUsernameRemembered()) {
				loginAsset14.drawSprite(450,320);
			} else {
				loginAsset13.drawSprite(450,320);
			}
			if (mouseInRegion(449,320,467,330)) {
				addTooltip("Toggle remember account details");
			}

			if (mouseInRegion(centerX + 116, centerY + 136, centerX + 147, centerY + 157)) {
				loginAsset15.drawTransparentSprite(505,387,50);
				addTooltip("Toggle Music.");
			} else {
				loginAsset16.drawTransparentSprite(505,387,255);
			}

			if (!worldswitcher) {
				loginAsset4.drawTransparentSprite(330,386,50);
			} else {
				loginAsset5.drawSprite(330, 386);
				addTooltip("Change Worlds.");
			}
			newRegularFont.drawString(Configuration.CONNECTION.name, 356,406,0x4FB533,0x191919, 255);

			int i1 = canvasWidth - 80;
			int l1 = canvasHeight + 50;
			i1 = canvasWidth / 2 + 80;
			titleButton.draw(i1 - 73, l1 - 20);
			drawAccount();
		}
	}
	private void drawAccount() {
		Rasterizer2D.fillRectangle(155, 100, 164, 328, 0x382b2b, 150);

		int centerX = canvasWidth / 2, centerY = canvasHeight / 2;
		int yPos = centerY - 70;
		for (int index = 0; index < 3; index++, yPos += 105) {
			Account account = AccountManager.get(index);

			if (account != null) {
				if (mouseInRegion(centerX - 80, yPos, centerX - loginAsset9.myWidth - 45, yPos + 20)) {
					loginAsset9.drawAdvancedSprite(centerX - 80, yPos);
					addTooltip("Delete profile");
				} else {
					loginAsset8.drawAdvancedSprite(centerX - 80, yPos);
				}
				Sprite AvatarSprite = new Sprite ("loginscreen2/avatar/"+account.avatar);
				AvatarSprite.drawAdvancedSprite(centerX - 182, yPos - 80);
				loginAsset10.drawAdvancedSprite(centerX - 223, yPos - 2);
				int rank = (account.rank - 1);
				String name = account.username;
				if (rank <= -1) {
					newSmallFont.drawCenteredString(Utility.formatName(name), centerX - 145, yPos + 16);
				} else {
					latoBold.drawCenteredString("@cr" + rank + "@", centerX - 145, yPos + 13, 0x6495ed, 1);
					newSmallFont.drawCenteredString("<col=ffffff>" + Utility.formatName(name.toLowerCase()), centerX - 145, yPos + 22);
				}
				if (mouseInRegion(centerX - 227, yPos - 2, centerX - 83, yPos + loginAsset10.myHeight)) {
					Rasterizer2D.drawRectangle(centerX - 227, yPos - 2, loginAsset10.myWidth, loginAsset10.myHeight, 0xab3232);
					Rasterizer2D.fillRectangle(centerX - 227, yPos - 2, loginAsset10.myWidth, loginAsset10.myHeight, 0x995353, 50);
					addTooltip("Manage profile details");
				}
				if (mouseInRegion(centerX - 230, yPos - 84, centerX - 63, yPos - 5)) {
					Rasterizer2D.drawRectangle(155, yPos - 81, 164, 80, 0xab3232);
					Rasterizer2D.fillRectangle(155, yPos - 81, 164, 80, 0x995353, 50);
					addTooltip("Log into game with profile");
				}
			}
		}
	}
	protected void addTooltip(String message) {
		int x = Client.instance.getMouseX();
		int y = Client.instance.getMouseY();
		int boxWith = Client.instance.newSmallFont.getTextWidth(message) + 30;

		if (x > 638)
			x = 638;
		if (y > 467)
			y = 467;

		Rasterizer2D.drawTransparentBox(x + 15, y + 10, boxWith, 22, 0x5c2d2d, 185);
		Client.instance.newSmallFont.drawBasicString(message, x + 30, y + 27, 16777215, 1);
	}
	private void drawFlames() {
		try {
			long l = System.currentTimeMillis();
			int i = 0;
			int j = 20;
			while (aBoolean831) {
				anInt1208++;
				if (++i > 10) {
					long l1 = System.currentTimeMillis();
					int k = (int) (l1 - l) / 10 - j;
					j = 40 - k;
					if (j < 5)
						j = 5;
					i = 0;
					l = l1;
				}
				try {
					Thread.sleep(j);
				} catch (Exception _ex) {
				}
			}
		} catch (Exception _ex) {
			drawingFlames = false;
		}
	}

	public void method137(Buffer stream, int j) {
		if (j == 84) {
			int k = stream.readUnsignedByte();
			int j3 = anInt1268 + (k >> 4 & 7);
			int i6 = anInt1269 + (k & 7);
			int l8 = stream.readUShort();
			int k11 = stream.readUShort();
			int l13 = stream.readUShort();// edit
			if (j3 >= 0 && i6 >= 0 && j3 < 104 && i6 < 104) {
				Deque class19_1 = groundItems[plane][j3][i6];
				if (class19_1 != null) {
					for (Item tileItem = (Item) class19_1
							.reverseGetFirst(); tileItem != null; tileItem = (Item) class19_1
							.reverseGetNext()) {
						if (tileItem.ID != (l8 & 0x7fff) || tileItem.itemCount != k11)
							continue;
						callbacks.post(new ItemQuantityChanged(tileItem, new com.client.Tile(getCenterHeight(plane, i6 * 128 + 64, j3 * 128 + 64), j3, i6), tileItem.itemCount, l13));
						tileItem.itemCount = l13;
						break;
					}
					updateGroundItems(j3, i6);
				}
			}
			return;
		}
		if (j == 105) {
			int l = stream.readUnsignedByte();
			int k3 = anInt1268 + (l >> 4 & 7);
			int j6 = anInt1269 + (l & 7);
			int i9 = stream.readUShort();
			int l11 = stream.readUnsignedByte();
			int i14 = l11 >> 4 & 0xf;
			int i16 = l11 & 7;
			if (localPlayer.pathX[0] >= k3 - i14 && localPlayer.pathX[0] <= k3 + i14 && localPlayer.pathY[0] >= j6 - i14
					&& localPlayer.pathY[0] <= j6 + i14 && soundEffectVolume != 0 && aBoolean848 && !Client.lowMem
					&& soundCount < 50) {
				sounds[soundCount] = i9;
				soundLoops[soundCount] = i16;
				soundDelay[soundCount] = SoundEffects.delays[i9];
				// aClass26Array1468[soundCount] = null;
				soundCount++;
			}
		}
		if (j == 215) {
			int i1 = stream.readUShortA();
			int l3 = stream.method428();
			int k6 = anInt1268 + (l3 >> 4 & 7);
			int j9 = anInt1269 + (l3 & 7);
			int i12 = stream.readUShortA();
			int j14 = stream.readUShort();
			if (k6 >= 0 && j9 >= 0 && k6 < 104 && j9 < 104 && i12 != localPlayerIndex) {
				Item tileItem = new Item();
				tileItem.ID = i1;
				tileItem.itemCount = j14;

				tileItem.itemHeight = plane;
				if (groundItems[plane][k6][j9] == null)
					groundItems[plane][k6][j9] = new Deque();

				groundItems[plane][k6][j9].insertHead(tileItem);
				updateGroundItems(k6, j9);
			}
			return;
		}
		if (j == 156) {
			int j1 = stream.method426();
			int i4 = anInt1268 + (j1 >> 4 & 7);
			int l6 = anInt1269 + (j1 & 7);
			int k9 = stream.readUShort();
			if (i4 >= 0 && l6 >= 0 && i4 < 104 && l6 < 104) {
				Deque class19 = groundItems[plane][i4][l6];
				if (class19 != null) {
					for (Item item = (Item) class19.reverseGetFirst(); item != null; item = (Item) class19
							.reverseGetNext()) {
						if (item.ID != k9)
							continue;

						//getCallbacks().post(new ItemDespawned(new com.client.Tile(getCenterHeight(plane, l6 * 128 + 64, i4 * 128 + 64), i4, l6), item));

						item.remove();
						getCallbacks().post(new ItemDespawned(new com.client.Tile(getCenterHeight(plane, l6 * 128 + 64, i4 * 128 + 64), i4, l6), item));
						//getCallbacks().post(new ItemDespawned(item.getTile(), item));
						break;
					}

					if (class19.reverseGetFirst() == null)
						groundItems[plane][i4][l6] = null;
					updateGroundItems(i4, l6);
				}
			}
			return;
		}
		if (j == 160) {
			int k1 = stream.method428();
			int j4 = anInt1268 + (k1 >> 4 & 7);
			int i7 = anInt1269 + (k1 & 7);
			int l9 = stream.method428();
			int objectType = l9 >> 2;
			int orientation = l9 & 3;
			int objectTypeGroup = objectTypes[objectType];
			int animationId = stream.readUShortA();
			if (j4 >= 0 && i7 >= 0 && j4 < 103 && i7 < 103) {
				int j18 = tileHeights[plane][j4][i7];
				int i19 = tileHeights[plane][j4 + 1][i7];
				int l19 = tileHeights[plane][j4 + 1][i7 + 1];
				int k20 = tileHeights[plane][j4][i7 + 1];
				if (objectTypeGroup == 0) {
					WallObject class10 = scene.getWallObject(plane, j4, i7);
					if (class10 != null) {
						int k21 = ObjectKeyUtil.getObjectId(class10.uid);
						if (objectType == 2) {
							class10.renderable1 = new SceneObject(k21, 4 + orientation, 2, i19, l19, j18, k20, animationId,
									false);
							class10.renderable2 = new SceneObject(k21, orientation + 1 & 3, 2, i19, l19, j18, k20,
									animationId, false);
						} else {
							class10.renderable1 = new SceneObject(k21, orientation, objectType, i19, l19, j18, k20, animationId,
									false);
						}
					}
				}
				if (objectTypeGroup == 1) {
					WallDecoration class26 = scene.getWallDecoration(j4, i7, plane);
					if (class26 != null)
						class26.renderable = new SceneObject(ObjectKeyUtil.getObjectId(class26.uid), 0, 4, i19, l19,
								j18, k20, animationId, false);
				}
				if (objectTypeGroup == 2) {
					GameObject class28 = scene.getGameObject(j4, i7, plane);
					if (objectType == 11)
						objectType = 10;
					if (class28 != null) {
						class28.renderable = new SceneObject(ObjectKeyUtil.getObjectId(class28.uid), orientation, objectType, i19,
								l19, j18, k20, animationId, false);
					}
				}
				if (objectTypeGroup == 3) {
					GroundDecoration class49 = scene.getGroundDecoration(i7, j4, plane);
					if (class49 != null)
						class49.renderable = new SceneObject(ObjectKeyUtil.getObjectId(class49.uid), orientation, 22, i19,
								l19, j18, k20, animationId, false);
				}
			}
			return;
		}
		if (j == 147) {
			int l1 = stream.method428();
			int k4 = anInt1268 + (l1 >> 4 & 7);
			int j7 = anInt1269 + (l1 & 7);
			int i10 = stream.readUShort();
			byte byte0 = stream.method430();
			int l14 = stream.method434();
			byte byte1 = stream.method429();
			int k17 = stream.readUShort();
			int k18 = stream.method428();
			int j19 = k18 >> 2;
			int i20 = k18 & 3;
			int l20 = objectTypes[j19];
			byte byte2 = stream.readSignedByte();
			int l21 = stream.readUShort();
			byte byte3 = stream.method429();
			Player player;
			if (i10 == localPlayerIndex)
				player = localPlayer;
			else
				player = players[i10];
			if (player != null) {
				ObjectDefinition class46 = ObjectDefinition.lookup(l21);
				int i22 = tileHeights[plane][k4][j7];
				int j22 = tileHeights[plane][k4 + 1][j7];
				int k22 = tileHeights[plane][k4 + 1][j7 + 1];
				int l22 = tileHeights[plane][k4][j7 + 1];
				Model model = class46.modelAt(j19, i20, i22, j22, k22, l22, -1, null, -1);
				if (model != null) {
					method130(k17 + 1, -1, 0, l20, j7, 0, plane, k4, l14 + 1);
					player.anInt1707 = l14 + loopCycle;
					player.anInt1708 = k17 + loopCycle;
					player.aModel_1714 = model;
					int i23 = class46.sizeX;
					int j23 = class46.sizeY;
					if (i20 == 1 || i20 == 3) {
						i23 = class46.sizeY;
						j23 = class46.sizeX;
					}
					player.anInt1711 = k4 * 128 + i23 * 64;
					player.anInt1713 = j7 * 128 + j23 * 64;
					player.anInt1712 = getCenterHeight(plane, player.anInt1713, player.anInt1711);
					if (byte2 > byte0) {
						byte byte4 = byte2;
						byte2 = byte0;
						byte0 = byte4;
					}
					if (byte3 > byte1) {
						byte byte5 = byte3;
						byte3 = byte1;
						byte1 = byte5;
					}
					player.anInt1719 = k4 + byte2;
					player.anInt1721 = k4 + byte0;
					player.anInt1720 = j7 + byte3;
					player.anInt1722 = j7 + byte1;
				}
			}
		}
		if (j == 151) {
			int i2 = stream.method426();
			int l4 = anInt1268 + (i2 >> 4 & 7);
			int k7 = anInt1269 + (i2 & 7);
			int j10 = stream.method434();
			int k12 = stream.method428();
			int i15 = k12 >> 2;
			int k16 = k12 & 3;
			int l17 = objectTypes[i15];
			if (l4 >= 0 && k7 >= 0 && l4 < 104 && k7 < 104)
				method130(-1, j10, k16, l17, k7, i15, plane, l4, 0);
			return;
		}
		if (j == 4) {
			int j2 = stream.readUnsignedByte();
			int i5 = anInt1268 + (j2 >> 4 & 7);
			int l7 = anInt1269 + (j2 & 7);
			int k10 = stream.readUShort();
			int l12 = stream.readUnsignedByte();
			int j15 = stream.readUShort();
			if (i5 >= 0 && l7 >= 0 && i5 < 104 && l7 < 104) {
				i5 = i5 * 128 + 64;
				l7 = l7 * 128 + 64;
				GraphicObject class30_sub2_sub4_sub3 = new GraphicObject(plane, loopCycle, j15, k10,
						getCenterHeight(plane, l7, i5) - l12, l7, i5);
				incompleteAnimables.insertHead(class30_sub2_sub4_sub3);
			}
			return;
		}
		if (j == 44) {
			int k2 = stream.method436();
			int j5 = stream.method439();
			int i8 = stream.readUnsignedByte();
			int l10 = anInt1268 + (i8 >> 4 & 7);
			int i13 = anInt1269 + (i8 & 7);
			if (l10 >= 0 && i13 >= 0 && l10 < 104 && i13 < 104) {
				Item tileItem = new Item();
				tileItem.ID = k2;
				tileItem.itemHeight = plane;
				tileItem.itemCount = j5;

				if (groundItems[plane][l10][i13] == null)
					groundItems[plane][l10][i13] = new Deque();
				groundItems[plane][l10][i13].insertHead(tileItem);

				//callbacks.post(new ItemSpawned(tileItem.getTile(), tileItem));
				updateGroundItems(l10, i13);
				callbacks.post(new ItemSpawned(new com.client.Tile(getCenterHeight(plane, i13 * 128 + 64, l10 * 128 + 64), l10, i13), tileItem));
			}
			return;
		}
		if (j == 101) {
			int l2 = stream.method427();
			int k5 = l2 >> 2;
			int j8 = l2 & 3;
			int i11 = objectTypes[k5];
			int j13 = stream.readUnsignedByte();
			int k15 = anInt1268 + (j13 >> 4 & 7);
			int l16 = anInt1269 + (j13 & 7);
			if (k15 >= 0 && l16 >= 0 && k15 < 104 && l16 < 104) {
				method130(-1, -1, j8, i11, l16, k5, plane, k15, 0);
			}
			return;
		}
		if (j == 117) {
			int offset = stream.readUnsignedByte();
			int x1 = anInt1268 + (offset >> 4 & 7);
			int y1 = anInt1269 + (offset & 7);
			int x2 = x1 + stream.readSignedByte();
			int y2 = y1 + stream.readSignedByte();
			int target = stream.readSignedWord();
			int gfxMoving = stream.readUShort();
			int startHeight = stream.readUnsignedByte() * 4;
			int endHeight = stream.readUnsignedByte() * 4;
			int startDelay = stream.readUShort();
			int speed = stream.readUShort();
			int initialSlope = stream.readUnsignedByte();
			int frontOffset = stream.readUnsignedByte();
			if (x1 >= 0 && y1 >= 0 && x1 < 104 && y1 < 104 && x2 >= 0 && y2 >= 0 && x2 < 104 && y2 < 104
					&& gfxMoving != 65535) {
				x1 = x1 * 128 + 64;
				y1 = y1 * 128 + 64;
				x2 = x2 * 128 + 64;
				y2 = y2 * 128 + 64;

				Projectile projectile = new Projectile(initialSlope, endHeight, startDelay + loopCycle, speed + loopCycle,
						frontOffset, plane, getCenterHeight(plane, y1, x1) - startHeight, y1, x1, target, gfxMoving);

				projectile.calculateIncrements(startDelay + loopCycle, y2, getCenterHeight(plane, y2, x2) - endHeight, x2);
				projectiles.insertHead(projectile);
			}
		}
	}

	public void method139(Buffer stream) {
		try {
			stream.initBitAccess();
			int localNpcListSize = stream.readBits(8);
			if (localNpcListSize < npcCount) {
				for (int l = localNpcListSize; l < npcCount; l++)
					anIntArray840[anInt839++] = npcIndices[l];

			}
			if (localNpcListSize > npcCount) {
				Signlink.reporterror(myUsername + " Too many npcs");
				throw new RuntimeException("eek");
			}
			npcCount = 0;
			for (int i1 = 0; i1 < localNpcListSize; i1++) {
				int npcIndex = npcIndices[i1];
				Npc npc = npcs[npcIndex];
				int k1 = stream.readBits(1);
				if (k1 == 0) {
					npcIndices[npcCount++] = npcIndex;
					npc.anInt1537 = loopCycle;
				} else {
					int l1 = stream.readBits(2);
					if (l1 == 0) {
						npcIndices[npcCount++] = npcIndex;
						npc.anInt1537 = loopCycle;
						anIntArray894[anInt893++] = npcIndex;
					} else if (l1 == 1) {
						npcIndices[npcCount++] = npcIndex;
						npc.anInt1537 = loopCycle;
						int i2 = stream.readBits(3);
						npc.moveInDir(false, i2);
						int k2 = stream.readBits(1);
						if (k2 == 1)
							anIntArray894[anInt893++] = npcIndex;
					} else if (l1 == 2) {
						npcIndices[npcCount++] = npcIndex;
						npc.anInt1537 = loopCycle;
						int j2 = stream.readBits(3);
						npc.moveInDir(true, j2);
						int l2 = stream.readBits(3);
						npc.moveInDir(true, l2);
						int i3 = stream.readBits(1);
						if (i3 == 1)
							anIntArray894[anInt893++] = npcIndex;
					} else if (l1 == 3)
						anIntArray840[anInt839++] = npcIndex;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean clickInRegion(int x1, int y1, int x2, int y2) {
		if (MouseHandler.saveClickX >= x1 && MouseHandler.saveClickX <= x2 && MouseHandler.saveClickY >= y1 && MouseHandler.saveClickY <= y2) {
			return true;
		}
		return false;
	}

	public boolean mouseInRegion(int x1, int y1, int x2, int y2) {
		if (MouseHandler.mouseX >= x1 && MouseHandler.mouseX <= x2 && MouseHandler.mouseY >= y1 && MouseHandler.mouseY <= y2) {
			return true;
		}
		return false;
	}

	public boolean rememberPasswordHover;
	public boolean worldswitcher;
	public boolean loginbutton;

	public static boolean clientLoaded = false;

	@SuppressWarnings("static-access")
	private void processLoginScreenInput() {
		if (!clientLoaded) {
			return;
		}
		if (AvatarScreen) {
			int centerX = canvasWidth /2;
			int centerY = canvasHeight /2;

			//Darth Vader
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX - 215, centerY - 136, centerX - 115, centerY - 62)) {
				select( 534);
			}
			//Skeleton
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX - 91, centerY - 136, centerX - 14, centerY - 62)) {
				select( 535);
			}
			//Cool dude
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX + 9, centerY - 136, centerX + 85, centerY - 62)) {
				select( 536);
			}
			//Gas mask
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX + 112, centerY - 136, centerX + 185, centerY - 62)) {
				select( 537);
			}
			//KFC
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX - 188, centerY - 46, centerX - 114, centerY + 30)) {
				select( 538);
			}
			//Dog
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX - 91, centerY - 46, centerX - 14, centerY + 30)) {
				select( 539);
			}
			//Monkey
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX + 9, centerY - 46, centerX + 85, centerY + 30)) {
				select( 540);
			}
			//Wolf
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX + 112, centerY - 46, centerX + 185, centerY + 30)) {
				select( 541);
			}
			//Joker
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX - 189, centerY + 45, centerX - 114, centerY + 122)) {
				select( 680);
			}
			//Demon
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX - 91, centerY + 45, centerX - 14, centerY + 122)) {
				select( 681);
			}
			//Hot girl 1
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX + 9, centerY + 45, centerX + 85, centerY + 122)) {
				select( 682);
			}
			//Hot girl 2
			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX + 112, centerY + 45, centerX + 185, centerY + 122)) {
				select( 683);
			}
			return;
		}

		if (AccountScreen) {
			int centerX = canvasWidth /2;
			int centerY = canvasHeight /2;

			if (clickMode3 == 1 && mouseInRegion(centerX - 181, centerY - 32, centerX - 84, centerY + 66)) {
				firstLoginMessage = "";
//				Load Avatar Selection Screen
				OpenAvatarScreen();
				return;
			}

			if (clickMode3 == 1 && mouseInRegion(centerX - 381, centerY - 249, centerX + 381, centerY + 245)) {
				firstLoginMessage = "";
//				LOAD MAIN SCREEN AGAIN
				AvatarScreen = false;
				AccountScreen = false;
			}


			return;
		}

		if (ErrorScreen) {
			int centerX = canvasWidth /2;
			int centerY = canvasHeight /2;

			if (MouseHandler.clickMode3 == 1 && mouseInRegion(centerX - 381, centerY - 249, centerX + 381, centerY + 245)) {
				firstLoginMessage = "";
//				LOAD MAIN SCREEN AGAIN
				AvatarScreen = false;
				AccountScreen = false;
				ErrorScreen = false;
			}

			return;
		}
		int j = canvasHeight / 2 - 40;
		j += 30;
		j += 25;
		if (MouseHandler.clickMode3 == 1 && MouseHandler.saveClickX >= 356 &&
				MouseHandler.saveClickX <= 530 && MouseHandler.saveClickY >= 210
				&& MouseHandler.saveClickY <= 240)
			loginScreenCursorPos = 0;
		j += 15;
		if (MouseHandler.clickMode3 == 1 && MouseHandler.saveClickX >= 356 &&
				MouseHandler.saveClickX <= 530 && MouseHandler.saveClickY >= 275
				&& MouseHandler.saveClickY <= 300)
			loginScreenCursorPos = 1;
		j += 15;
		int extraPos = 18;

		if (MouseHandler.clickMode3 == 1 && MouseHandler.saveClickY >= 387 && MouseHandler.saveClickY <= 409 &&
				MouseHandler.saveClickX >= 497 && MouseHandler.saveClickX <= 530) {
			try {
				Preferences.getPreferences().musicEnabled = !Preferences.getPreferences().musicEnabled;
				setVolumeMusic(Preferences.getPreferences().musicEnabled ? 5 : 0);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}

		if (loginScreenState == LoginScreenState.LOGIN) {//449,287,467,298
			if (MouseHandler.clickMode3 == 1 && MouseHandler.saveClickY >= 320 && MouseHandler.saveClickY <= 331
					&& MouseHandler.saveClickX >= 448 && MouseHandler.saveClickX <= 468) {
				informationFile.setUsernameRemembered(!informationFile.isUsernameRemembered());
				informationFile.setPasswordRemembered(!informationFile.isPasswordRemembered());
				Configuration.SAVE_ACCOUNTS = !Configuration.SAVE_ACCOUNTS;
			}

			if (MouseHandler.clickMode3 == 1 && MouseHandler.saveClickX >= 350
					&& MouseHandler.saveClickX <= 544 && MouseHandler.saveClickY >= 350
					&& MouseHandler.saveClickY <= 380) {

				// loginScreenState = 3;
				loginFailures = 0;
				login(myUsername, getPassword(), false);
				if (loggedIn)
					return;

				// loginScreenState = 2;
			}
		} else if (loginScreenState == LoginScreenState.CAPTCHA) {
			if (MouseHandler.clickMode3 == 1 && MouseHandler.saveClickX >= 277
					&& MouseHandler.saveClickX <= 486 && MouseHandler.saveClickY >= 256
					&& MouseHandler.saveClickY <= 288) {

				// loginScreenState = 3;
				loginFailures = 0;
				login(myUsername, getPassword(), false);
				if (loggedIn)
					return;

				// loginScreenState = 2;
			}
		}

		int centerX = canvasWidth / 2;
		int centerY = canvasHeight / 2;


		if (MouseHandler.clickMode3 == 1 && MouseHandler.saveClickX >= 326
				&& MouseHandler.saveClickX <= 406 && MouseHandler.saveClickY >= 388
				&& MouseHandler.saveClickY <= 410) {

//			System.out.println("World Switcher");
//			switch (Configuration.CONNECTION) {
//				case WORLD_1:
//					Configuration.CONNECTION = Connection.WORLD_2;
//					break;
//				case WORLD_2:
//					Configuration.CONNECTION = Connection.WORLD_1;
//					break;
//			}
			Interfaces.reloadInterfaces();
		}


		int yPos = centerY - 70;
		if (AccountManager.ACCOUNTS != null) {
			for (int index = 0; index < AccountManager.ACCOUNTS.size(); index++, yPos += 105) {
				Account accountData = AccountManager.ACCOUNTS.get(index);
				if (clickMode3 == 1 && mouseInRegion(centerX - 230, yPos - 84, centerX - 63, yPos - 5)) {
					lastAccount = accountData;
					myUsername = Utility.formatName(accountData.username.toLowerCase());
					myPassword = accountData.password;
					login(accountData.username, accountData.password, false);
					if (Client.loggedIn) {
						return;
					}
				}
				if (clickMode3 == 1 && mouseInRegion(centerX - 230, yPos - 2, centerX - 83, yPos + loginAsset10.myHeight)) {
					if (lastAccount == null) {
						firstLoginMessage = "There was an issue loading your account.";
						OpenErrorScreen();
						return;
					}
					lastAccount = accountData;
					firstLoginMessage = "Attempting to load " + accountData.username;
					OpenAccountScreen();
					return;
				}
				if (clickMode3 == 1 && mouseInRegion(centerX - 80, yPos, centerX - loginAsset9.myWidth - 45, yPos + 20)) {
					AccountManager.removeAccount(accountData);
				}
			}
		}


		do {
			int l1 = KeyHandler.instance.readChar();
			if (l1 == -1)
				break;
			boolean flag1 = false;
			for (int i2 = 0; i2 < validUserPassChars.length(); i2++) {
				if (l1 != validUserPassChars.charAt(i2))
					continue;
				flag1 = true;
				break;
			}

			if (loginScreenState == LoginScreenState.CAPTCHA) {
				synchronized (CAPTCHA_LOCK) {
					captchaInput = loginScreenInput(captchaInput, l1, flag1, 12,
							null,
							() -> login(myUsername, getPassword(), false)
					);
				}
			} else if (loginScreenState == LoginScreenState.LOGIN) {
				if (loginScreenCursorPos == 0) {
					myUsername = loginScreenInput(myUsername, l1, flag1, 12,
							() -> loginScreenCursorPos = 1,
							() -> loginScreenCursorPos = 1
					);
				} else if (loginScreenCursorPos == 1) {
					myPassword = loginScreenInput(myPassword, l1, flag1, 15,
							() -> loginScreenCursorPos = 0,
							() -> login(myUsername, getPassword(), false)
					);
				}
			}
		} while (true);
	}

	private String loginScreenInput(String current, int l1, boolean flag1, int maxWidth, Runnable tab, Runnable enter) {
		if (l1 == 8 && current.length() > 0)
			current = current.substring(0, current.length() - 1);
		if (l1 == 9) {
			if (tab != null)
				tab.run();
		} else if (l1 == 10 || l1 == 13) {
			if (enter != null)
				enter.run();
			return current;
		}
		if (flag1)
			current += (char) l1;
		if (current.length() > maxWidth)
			current = current.substring(0, maxWidth);
		return current;
	}

	private void markMinimap(Sprite sprite, int xPosition, int yPosition) {
		int k = viewRotation + minimapRotation & 0x7ff;
		int boundry = xPosition * xPosition + yPosition * yPosition;
		if (boundry > 5750)
			return;
		int i1 = Model.SINE[k];
		int j1 = Model.COSINE[k];
		i1 = (i1 * 256) / (minimapZoom + 256);
		j1 = (j1 * 256) / (minimapZoom + 256);
		int k1 = yPosition * i1 + xPosition * j1 >> 16;
		int l1 = yPosition * j1 - xPosition * i1 >> 16;

		try {
			int xOffset = !isResized() ? 516 : 0;
			if (!isResized())
				sprite.drawSprite(((97 + k1) - sprite.maxWidth / 2) + 30 + xOffset, 83 - l1 - sprite.maxHeight / 2 - 4 + 5);
			else
				sprite.drawSprite(((77 + k1) - sprite.maxWidth / 2) + 4 + (canvasWidth - 167) + xOffset,
						85 - l1 - sprite.maxHeight / 2 - 4);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private void removeObject(int y, int z, int k, int l, int x, int group, int previousId) {
		if (x >= 1 && y >= 1 && x <= 102 && y <= 102) {
			if (lowMem && z != plane)
				return;
			long key = 0L;
			if (group == 0)
				key = scene.getWallObjectUid(z, x, y);
			if (group == 1)
				key = scene.getWallDecorationUid(z, x, y);
			if (group == 2)
				key = scene.getGameObjectUid(z, x, y);
			if (group == 3)
				key = scene.getGroundDecorationUid(z, x, y);
			if (key != 0L) {

				int id = ObjectKeyUtil.getObjectId(key);
				int objectType = ObjectKeyUtil.getObjectType(key);
				int orientation = ObjectKeyUtil.getObjectOrientation(key);

				if (group == 0) {
					scene.removeWallObject(x, z, y);
					ObjectDefinition objectDef = ObjectDefinition.lookup(id);
					if (objectDef.interactType)
						collisionMaps[z].removeObject(orientation, objectType,
								objectDef.blocksProjectile, x, y);
				}
				if (group == 1)
					scene.removeWallDecoration(y, z, x);
				if (group == 2) {
					scene.removeTiledObject(z, x, y);
					ObjectDefinition objectDef = ObjectDefinition.lookup(id);
					if (x + objectDef.sizeX > 103 || y + objectDef.sizeX > 103
							|| x + objectDef.sizeY > 103
							|| y + objectDef.sizeY > 103)
						return;
					if (objectDef.interactType)
						collisionMaps[z].removeObject(orientation, objectDef.sizeX, x,
								y, objectDef.sizeY, objectDef.blocksProjectile);
				}
				if (group == 3) {
					scene.removeGroundDecoration(z, y, x);
					ObjectDefinition objectDef = ObjectDefinition.lookup(id);
					if (objectDef.interactType && objectDef.isInteractive)
						collisionMaps[z].removeFloorDecoration(y, x);
				}
			}
			if (previousId >= 0) {
				int plane = z;
				if (plane < 3 && (tileFlags[1][x][y] & 2) == 2)
					plane++;
				MapRegion.placeObject(scene, k, y, l, plane, collisionMaps[z], tileHeights,
						x, previousId, z);
			}
		}
	}

	public void updatePlayers(int i, Buffer stream) {
		anInt839 = 0;
		anInt893 = 0;
		method117(stream);
		method134(stream);
		method91(stream, i);
		method49(stream);
		for (int k = 0; k < anInt839; k++) {
			int l = anIntArray840[k];
			if (players[l].anInt1537 != loopCycle)
				players[l] = null;
		}

		if (stream.currentPosition != i) {
			Signlink.reporterror("Error packet size mismatch in getplayer pos:" + stream.currentPosition + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < playerCount; i1++)
			if (players[playerIndices[i1]] == null) {
				Signlink.reporterror(myUsername + " null entry in pl list - pos:" + i1 + " size:" + playerCount);
				throw new RuntimeException("eek");
			}

	}

	private void setCameraPos(int j, int k, int l, int i1, int j1, int k1) {
		int l1 = 2048 - k & 0x7ff;
		int i2 = 2048 - j1 & 0x7ff;
		int j2 = 0;
		int k2 = 0;
		int l2 = j;
		if (l1 != 0) {
			int i3 = Model.SINE[l1];
			int k3 = Model.COSINE[l1];
			int i4 = k2 * k3 - (int) (l2 * i3) >> 16;
			l2 = k2 * i3 + (int) (l2 * k3) >> 16;
			k2 = i4;
		}
		if (i2 != 0) {
			int j3 = Model.SINE[i2];
			int l3 = Model.COSINE[i2];
			int j4 = (int) (l2 * j3) + j2 * l3 >> 16;
			l2 = (int) (l2 * l3) - j2 * j3 >> 16;
			j2 = j4;
		}
		xCameraPos = l - j2;
		zCameraPos = i1 - k2;
		yCameraPos = k1 - l2;
		yCameraCurve = k;
		xCameraCurve = j1;
		onCameraPitchChanged(k);
	}

	public void updateStrings(String str, int i) {
		switch (i) {
			case 22499:
				sendFrame126(currentLevels[5]+"/"+ maximumLevels[5], 22499);
				break;
			case 49020:
				achievementCutoff = Integer.parseInt(str);
				break;
			case 1675:
				sendFrame126(str, 17508);
				break;// Stab
			case 1676:
				sendFrame126(str, 17509);
				break;// Slash
			case 1678:
				sendFrame126(str, 17511);
				break;// Magic
			case 1679:
				sendFrame126(str, 17512);
				break;// Range
			case 1680:
				sendFrame126(str, 17513);
				break;// Stab
			case 1681:
				sendFrame126(str, 17514);
				break;// Slash
			case 1682:
				sendFrame126(str, 17515);
				break;// Crush
			case 1683:
				sendFrame126(str, 17516);
				break;// Magic
			case 1684:
				sendFrame126(str, 17517);
				break;// Range
			case 1686:
				sendFrame126(str, 17518);
				break;// Strength
			case 1687:
				sendFrame126(str, 17519);
				break;// Prayer
		}
	}

	public void sendFrame126(String str, int i) {
		RSInterface component = RSInterface.interfaceCache[i];
		if (component != null) {
			component.message = str;
			if (component.type == 4 && component.atActionType == 1) {
				component.hoverText = str;
			}
			if (component.parentID == tabInterfaceIDs[tabID])
				needDrawTabArea = true;
		}
	}

	public void sendKeyboardAction(int action) {
		stream.createFrame(201);
		stream.writeByte(action);
	}

	public void sendFrame248(int interfaceID, int sideInterfaceID) {
		if (backDialogID != -1) {
			backDialogID = -1;
			inputTaken = true;
		}
		if (inputDialogState != 0) {
			inputDialogState = 0;
			inputTaken = true;
		}
		openInterfaceID = interfaceID;
		invOverlayInterfaceID = sideInterfaceID;
		needDrawTabArea = true;
		tabAreaAltered = true;
		aBoolean1149 = false;
	}

	private int groundItemValueLimit = 7_000;

	private static final Comparator<ExperienceDrop> HIGHEST_POSITION = new Comparator<ExperienceDrop>() {

		@Override
		public int compare(ExperienceDrop o1, ExperienceDrop o2) {
			return Integer.compare(o2.getYPosition(), o1.getYPosition());
		}

	};

	private boolean parsePacket() {
		if (socketStream == null)
			return false;
		try {
			int i = socketStream.available();
			if (i == 0)
				return false;
			if (incomingPacket == -1) {
				socketStream.flushInputStream(inStream.payload, 1);
				incomingPacket = inStream.payload[0] & 0xff;
				if (encryption != null)
					incomingPacket = incomingPacket - encryption.getNextKey() & 0xff;
				packetSize = SizeConstants.packetSizes[incomingPacket];
				i--;
			}
			if (packetSize == -1)
				if (i > 0) {
					socketStream.flushInputStream(inStream.payload, 1);
					packetSize = inStream.payload[0] & 0xff;
					i--;
				} else {
					return false;
				}
			if (packetSize == -2)
				if (i > 1) {
					socketStream.flushInputStream(inStream.payload, 2);
					inStream.currentPosition = 0;
					packetSize = inStream.readUShort();
					i -= 2;
				} else {
					return false;
				}
			if (i < packetSize) {
//				String message = "";
//				message = String.format("Available=%d less than packet-size=%d, ", i, packetSize);
//				message += " - Packet: " + incomingPacket + ", Packet Size: " + packetSize
//						+ " - Previous packet: " + previousPacket1 + " Previous packet size: " + previousPacketSize1
//						+ ", 2nd Previous packet: " + previousPacket2 + ", 2nd Previous packet size: "
//						+ previousPacketSize2;
//				System.err.println(message);
				return false;
			}

			inStream.currentPosition = 0;
			socketStream.flushInputStream(inStream.payload, packetSize);
			anInt1009 = 0;

			previousPacketSize2 = previousPacketSize1;
			previousPacket2 = previousPacket1;

			previousPacketSize1 = dealtWithPacketSize;
			previousPacket1 = dealtWithPacket;

			dealtWithPacket = incomingPacket;
			dealtWithPacketSize = packetSize;

			PacketLog.add(incomingPacket, packetSize, inStream.payload);

/*			if (incomingPacket != 65 && incomingPacket != 81) // Ignore the update packets
				logger.debug("Parsing packet: " + incomingPacket);*/
			switch (incomingPacket) {
				case 13: // Client script
					String types = inStream.readString();
					Object[] arguments = new Object[types.length()];
					List<String> stringInstructions = new ArrayList<>();
					List<Integer> intInstructions = new ArrayList<>();
					for (int index = 0; index < types.length(); index++) {
						if (types.charAt(index) == 's') {
							arguments[index] = inStream.readNullTerminatedString();
							stringInstructions.add((String) arguments[index]);
						} else { // 'i'
							arguments[index] = inStream.readDWord();
							intInstructions.add((Integer) arguments[index]);
						}
					}
					System.out.println("Script arguments = " + arguments.length);

					int scriptId = inStream.readUShort();
					if(InstructionId.fromId(scriptId) == InstructionId.NOTHING) {
						ScriptEvent scriptEvent = new ScriptEvent();
						scriptEvent.args = new Object[] {scriptId, arguments};
						InstructionProcessor.runScriptEvent(scriptEvent);
					} else {
						InstructionArgs instructionArgs = InstructionArgs.createFrom(intInstructions.toArray(new Integer[0]), stringInstructions.toArray(new String[0]));
						InstructionProcessor.invoke(scriptId, instructionArgs);
					}
					incomingPacket = -1;
					return true;
				case 12:
					int soundId = inStream.readUShort();
//					int areaType = inStream.readUnsignedByte();
					SoundType incomingSoundType = SoundType.values()[inStream.readUnsignedByte()];
					int entitySoundSource = inStream.readUShort();
					if (entitySoundSource == 0) {
						Sound.getSound().playSound(soundId, incomingSoundType, 0);
					} else {
						Entity entity;
						if (entitySoundSource >= Short.MAX_VALUE) {
							entitySoundSource -= Short.MAX_VALUE;
							entity = players[entitySoundSource];
						} else {
							entity = npcs[entitySoundSource];
						}

						if (entity != null) {
							Sound.getSound().playSound(soundId, incomingSoundType, localPlayer.getDistanceFrom(entity));
						} else {
							Sound.getSound().playSound(soundId, incomingSoundType, 0);
						}
					}
					incomingPacket = -1;
					return true;
				case 10:
					screenFlashDrawing = true;
					screenFlashOpacityDownward = false;
					screenFlashOpacity = 0d;
					screenFlashColor = inStream.readDWord();
					screenFlashMaxIntensity = inStream.readUnsignedByte();
					screenFlashAutoFadeOut = inStream.readUnsignedByte() == 0;
					incomingPacket = -1;
					return true;

				// Set strings inside string container
				case 5:
					int stringContainerId = inStream.readUShort();
					int strings = inStream.readUShort();
					RSInterface stringContainer = RSInterface.get(stringContainerId);
					Preconditions.checkState(stringContainer != null && stringContainer.stringContainer != null, "No string container at id: " + stringContainer);
					stringContainer.stringContainer.clear();
					for (int index = 0; index < strings; index++) {
						stringContainer.stringContainer.add(inStream.readString());
					}
					EventCalendar.getCalendar().onStringContainerUpdated(stringContainerId);
					incomingPacket = -1;
					return true;

				// Reset scroll position
				case 2:
					int resetScrollInterfaceId = inStream.readUShort();
					RSInterface.interfaceCache[resetScrollInterfaceId].scrollPosition = 0;
					incomingPacket = -1;
					return true;

				case 3:
					int setScrollMaxInterfaceId = inStream.readUShort();
					int scrollMax = inStream.readUShort();
					RSInterface.interfaceCache[setScrollMaxInterfaceId].scrollMax = scrollMax;
					incomingPacket = -1;
					return true;


				/**
				 * Progress Bar Update Packet
				 */
				case 77:
					while (inStream.currentPosition < packetSize) {
						int interfaceId = inStream.readDWord();
						byte progressBarState = inStream.readSignedByte();
						byte progressBarPercentage = inStream.readSignedByte();

						RSInterface rsInterface = RSInterface.interfaceCache[interfaceId];
						rsInterface.progressBarState = progressBarState;
						rsInterface.progressBarPercentage = progressBarPercentage;
					}
					incomingPacket = -1;
					return true;

				case 137:
					specialAttack = inStream.readUnsignedByte();
					incomingPacket = -1;
					return true;

				/**
				 * EntityTarget Update Packet
				 */
				case 222:
					byte entityState = (byte) inStream.readUnsignedByte();
					if (entityState != 0) {
						short entityIndex = (short) inStream.readUShort();
						short currentHealth = (short) inStream.readUShort();
						short maximumHealth = (short) inStream.readUShort();
						entityTarget = new EntityTarget(entityState, entityIndex, currentHealth, maximumHealth, newSmallFont);
					} else {
						if (entityTarget != null) {
							entityTarget.stop();
						}
						entityTarget = null;
					}
					incomingPacket = -1;
					return true;

				/**
				 * Timer Update Packet
				 */
				case 223:
					int timerId = inStream.readUnsignedByte();
					int secondsToAdd = inStream.readUShort();
					GameTimerHandler.getSingleton().startGameTimer(timerId, TimeUnit.SECONDS, secondsToAdd, 0);
					incomingPacket = -1;
					return true;

				case 224:
					timerId = inStream.readUShort();
					int itemId = inStream.readUShort();
					secondsToAdd = inStream.readUShort();
					GameTimerHandler.getSingleton().startGameTimer(timerId, TimeUnit.SECONDS, secondsToAdd, itemId);
					incomingPacket = -1;
					return true;

				case 11:
					long experience = inStream.readQWord();
					byte length = inStream.readSignedByte();
					int[] skills = new int[length];

					for (int j = 0; j < length; j++) {
						skills[j] = inStream.readSignedByte();
					}



					ExperienceDrop drop = new ExperienceDrop(experience, skills);

					if (!experienceDrops.isEmpty()) {
						List<ExperienceDrop> sorted = new ArrayList<ExperienceDrop>(experienceDrops);
						Collections.sort(sorted, HIGHEST_POSITION);
						ExperienceDrop highest = sorted.get(0);
						if (highest.getYPosition() >= ExperienceDrop.START_Y - 5) {
							drop.increasePosition(highest.getYPosition() - ExperienceDrop.START_Y + 20);
						}
					}

					experienceDrops.offer(drop);
					incomingPacket = -1;
					return true;

				case 81: // update player packet
					updatePlayers(packetSize, inStream);
					loadingMap = false;
					incomingPacket = -1;
					return true;

				case 176:
					daysSinceRecovChange = inStream.method427();
					unreadMessages = inStream.readUShortA();
					membersInt = inStream.readUnsignedByte();
					anInt1193 = inStream.method440();
					daysSinceLastLogin = inStream.readUShort();
					if (anInt1193 != 0 && openInterfaceID == -1) {
						Signlink.dnslookup(StringUtils.method586(anInt1193));
						clearTopInterfaces();
						char c = '\u028A';
						if (daysSinceRecovChange != 201 || membersInt == 1)
							c = '\u028F';
						reportAbuseInput = "";
						canMute = false;
						for (int k9 = 0; k9 < RSInterface.interfaceCache.length; k9++) {
							if (RSInterface.interfaceCache[k9] == null || RSInterface.interfaceCache[k9].contentType != c)
								continue;
							openInterfaceID = RSInterface.interfaceCache[k9].parentID;

						}
					}
					incomingPacket = -1;
					return true;

				case 64:
					anInt1268 = inStream.method427();
					anInt1269 = inStream.method428();
					for (int j = anInt1268; j < anInt1268 + 8; j++) {
						for (int l9 = anInt1269; l9 < anInt1269 + 8; l9++)
							if (groundItems[plane][j][l9] != null) {
								groundItems[plane][j][l9] = null;
								updateGroundItems(j, l9);
							}
					}
					for (SpawnedObject spawnedObject = (SpawnedObject) spawns
							.reverseGetFirst(); spawnedObject != null; spawnedObject = (SpawnedObject) spawns
							.reverseGetNext())
						if (spawnedObject.x >= anInt1268 && spawnedObject.x < anInt1268 + 8
								&& spawnedObject.y >= anInt1269 && spawnedObject.y < anInt1269 + 8
								&& spawnedObject.plane == plane)
							spawnedObject.getLongetivity = 0;
					incomingPacket = -1;
					return true;

				case 9:
					String text = inStream.readString();
					byte state = inStream.readSignedByte();
					byte seconds = inStream.readSignedByte();
					int drawingWidth = !Client.instance.isResized() ? 519 : Client.canvasWidth;
					int drawingHeight = !Client.instance.isResized() ? 338 : Client.canvasHeight;

					incomingPacket = -1;
					return true;

				case 185:
					int k = inStream.method436();
					RSInterface.interfaceCache[k].anInt233 = 3;
					if (localPlayer.npcDefinition == null)
						RSInterface.interfaceCache[k].mediaID = (localPlayer.anIntArray1700[0] << 25)
								+ (localPlayer.anIntArray1700[4] << 20) + (localPlayer.equipment[0] << 15)
								+ (localPlayer.equipment[8] << 10) + (localPlayer.equipment[11] << 5) + localPlayer.equipment[1];
					else
						RSInterface.interfaceCache[k].mediaID = (int) (0x12345678L + localPlayer.npcDefinition.npcId);
					incomingPacket = -1;
					return true;

				/* Clan message packet */
//				case 217:
//					try {
//						clanUsername = inStream.readString();
//						clanMessage = TextInput.processText(inStream.readString());
//						clanTitle = inStream.readString();
//						PlayerRights[] rights = PlayerRights.readRightsFromPacket(inStream);
//						channelRights = EnumSet.of(rights);
//						pushMessage(clanMessage, 12, clanUsername);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					incomingPacket = -1;
//					return true;

				case 107:
					inCutScene = false;
					for (int l = 0; l < 5; l++)
						aBooleanArray876[l] = false;
					xpCounter = 0;
					incomingPacket = -1;
					return true;

				case 72:
					int i1 = inStream.method434();
					RSInterface class9 = RSInterface.interfaceCache[i1];
					for (int k15 = 0; k15 < class9.inventoryItemId.length; k15++) {
						class9.inventoryItemId[k15] = -1;
						class9.inventoryItemId[k15] = 0;
					}
					incomingPacket = -1;
					return true;

				case 214:
					ignoreCount = packetSize / 8;
					for (int j1 = 0; j1 < ignoreCount; j1++)
						ignoreListAsLongs[j1] = inStream.readQWord();
					incomingPacket = -1;
					return true;

				case 166:
					inCutScene = true;
					x = inStream.readUnsignedByte();
					y = inStream.readUnsignedByte();
					height = inStream.readUShort();
					anInt1101 = inStream.readUnsignedByte();
					angle = inStream.readUnsignedByte();
					if (angle >= 100) {
						xCameraPos = x * 128 + 64;
						yCameraPos = y * 128 + 64;
						zCameraPos = getCenterHeight(plane, yCameraPos, xCameraPos) - height;
					}
					incomingPacket = -1;
					return true;

				case 134:
					needDrawTabArea = true;
					int skillId = inStream.readUnsignedByte();
					int experience2 = inStream.method439();
					int currentLevel = inStream.readUnsignedByte();
					int xp = currentExp[skillId];

					currentExp[skillId] = experience2;
					currentLevels[skillId] = currentLevel;
					maximumLevels[skillId] = 1;
					xpCounter += currentExp[skillId] - xp;
					expAdded = currentExp[skillId] - xp;
					try {
						callbacks.post(new StatChanged(
								Skill.valueOf(Skills.SKILL_NAMES_ORDER[skillId].toUpperCase()),
								experience2,
								currentLevel,
								3
						));
					} catch (Exception exception) {}
					for (int k20 = 0; k20 < 98; k20++)
						if (experience2 >= SKILL_EXPERIENCE[k20])
							maximumLevels[skillId] = k20 + 2;
					updateSkillStrings(skillId);
					incomingPacket = -1;
					return true;

				case 71:
					int l1 = inStream.readUShort();
					int j10 = inStream.method426();
					if (l1 == 65535)
						l1 = -1;
					tabInterfaceIDs[j10] = l1;
					needDrawTabArea = true;
					tabAreaAltered = true;
					incomingPacket = -1;
					return true;

				case 74:
					int songId = inStream.method434();
					if (songId == 65535) {
						songId = -1;
					}
					if (songId != -1 && currentSong != songId && musicVolume != 0 && prevSong == 0) {
						nextSong = songId;
						songChanging = true;
						resourceProvider.provide(2, nextSong);
					}
					currentSong = songId;
					incomingPacket = -1;
					return true;

				case 121:
					int j2 = inStream.method436();
					int k10 = inStream.readUShortA();
/*					if (Preferences.getPreferences().musicVolume > 0) {
						if (k10 == 65535) {
							k10 = -1;
						}
						if (k10 != -1) {
							requestMusic(j2);
							prevSong = j2 * 20;
						}
					}*/
					incomingPacket = -1;
					return true;

				case 7:
					int componentId = inStream.readDWord();
					byte spriteIndex = inStream.readSignedByte();
					RSInterface component = RSInterface.interfaceCache[componentId];
					if (component != null) {
						if (component.backgroundSprites != null && spriteIndex <= component.backgroundSprites.length - 1) {
							Sprite sprite = component.backgroundSprites[spriteIndex];
							if (sprite != null) {
								component.sprite1 = component.backgroundSprites[spriteIndex];
							}
						}
					}
					incomingPacket = -1;
					return true;

				case 109:
					resetLogout();
					incomingPacket = -1;
					return false;

				case 70:
					int k2 = inStream.readSignedWord();
					int l10 = inStream.method437();
					int i16 = inStream.method434();
					RSInterface class9_5 = RSInterface.interfaceCache[i16];
					class9_5.anInt263 = k2;
					class9_5.anInt265 = l10;
					incomingPacket = -1;
					return true;

				case 243:
					int fog = inStream.readUnsignedByte();
					fogEnabled = fog == 1;
					fogOpacity = inStream.readDWord();
					incomingPacket = -1;
					return true;

				case 225:
					int widgetId = inStream.readUShort();
//					System.out.println("widgetId " + widgetId);
					WheelOfFortune wheel = RSInterface.interfaceCache[widgetId].wheel;
					int xc = inStream.readUnsignedByte();
					int len = inStream.readUnsignedByte();
//					System.out.println("xc " + xc + " / len " + len);
					int[] items = new int[len];
					for (int j = 0; j < len; j++) {
						items[j] = inStream.readUShort();
					}
//					System.out.println("Items " + Arrays.toString(Arrays.stream(items).toArray()));
					wheel.setItems(items);
					wheel.setIndex(xc);
					wheel.init();
					incomingPacket = -1;
					return true;

				case 73:
				case 241:
					setGameState(GameState.LOADING);
					int mapRegionX = currentRegionX;
					int mapRegionY = currentRegionY;
					if (incomingPacket == 73) {
						mapRegionX = inStream.readUShortA();
						mapRegionY = inStream.readUShort();
						isDynamicRegion = false;
					}
					if (incomingPacket == 241) {
						mapRegionY = inStream.readUShortA();
						inStream.initBitAccess();
						for (int z = 0; z < 4; z++) {
							for (int x = 0; x < 13; x++) {
								for (int y = 0; y < 13; y++) {
									int visible = inStream.readBits(1);
									if (visible == 1)
										constructRegionData[z][x][y] = inStream.readBits(26);
									else
										constructRegionData[z][x][y] = -1;
								}
							}
						}
						inStream.finishBitAccess();
						mapRegionX = inStream.readUShort();
						isDynamicRegion = true;
					}
					if (currentRegionX == mapRegionX && currentRegionY == mapRegionY && loadingStage == 2) {
						incomingPacket = -1;
						return true;
					}
					currentRegionX = mapRegionX;
					currentRegionY = mapRegionY;
					baseX = (currentRegionX - 6) * 8;
					baseY = (currentRegionY - 6) * 8;
					handleRegionChangeMusic();
					inTutorialIsland = (currentRegionX / 8 == 48 || currentRegionX / 8 == 49) && currentRegionY / 8 == 48;
					if (currentRegionX / 8 == 48 && currentRegionY / 8 == 148)
						inTutorialIsland = true;
					loadingStage = 1;
					longStartTime = System.currentTimeMillis();

					setGameState(GameState.LOADING);

					if (incomingPacket == 73) {
						int regionCount = 0;
						for (int x = (currentRegionX - 6) / 8; x <= (currentRegionX + 6) / 8; x++) {
							for (int y = (currentRegionY - 6) / 8; y <= (currentRegionY + 6) / 8; y++)
								regionCount++;
						}
						terrainData = new byte[regionCount][];
						objectData = new byte[regionCount][];
						mapCoordinates = new int[regionCount];
						terrainIndices = new int[regionCount];
						objectIndices = new int[regionCount];
						regionCount = 0;
						List<Integer> mapFiles = Lists.newArrayList();
						for (int x = (currentRegionX - 6) / 8; x <= (currentRegionX + 6) / 8; x++) {
							for (int y = (currentRegionY - 6) / 8; y <= (currentRegionY + 6) / 8; y++) {
								mapCoordinates[regionCount] = (x << 8) + y;
								if (inTutorialIsland
										&& (y == 49 || y == 149 || y == 147 || x == 50 || x == 49 && y == 47)) {
									terrainIndices[regionCount] = -1;
									objectIndices[regionCount] = -1;
									regionCount++;
								} else {
									int map = terrainIndices[regionCount] = resourceProvider.getMapFiles(0, y, x);
									if (map != -1) {
										resourceProvider.provide(3, map);
										mapFiles.add(map);
									}
									int landscape = objectIndices[regionCount] = resourceProvider.getMapFiles(1, y, x);
									if (landscape != -1) {
										resourceProvider.provide(3, landscape);
										mapFiles.add(landscape);
									}
									regionCount++;
								}
							}
						}

						if (Configuration.developerMode) {
							System.out.println("Map files: " + mapFiles.toString());
						}
					}
					if (incomingPacket == 241) {
						int totalLegitChunks = 0;
						int totalChunks[] = new int[676];
						for (int z = 0; z < 4; z++) {
							for (int x = 0; x < 13; x++) {
								for (int y = 0; y < 13; y++) {
									int tileBits = constructRegionData[z][x][y];
									if (tileBits != -1) {
										int xCoord = tileBits >> 14 & 0x3ff;
										int yCoord = tileBits >> 3 & 0x7ff;
										int mapRegion = (xCoord / 8 << 8) + yCoord / 8;
										for (int idx = 0; idx < totalLegitChunks; idx++) {
											if (totalChunks[idx] != mapRegion)
												continue;
											mapRegion = -1;

										}
										if (mapRegion != -1)
											totalChunks[totalLegitChunks++] = mapRegion;
									}
								}
							}
						}
						terrainData = new byte[totalLegitChunks][];
						objectData = new byte[totalLegitChunks][];
						mapCoordinates = new int[totalLegitChunks];
						terrainIndices = new int[totalLegitChunks];
						objectIndices = new int[totalLegitChunks];
						for (int idx = 0; idx < totalLegitChunks; idx++) {
							int region = mapCoordinates[idx] = totalChunks[idx];
							int localX = region >> 8 & 0xff;
							int localY = region & 0xff;
							int terrainMapId = terrainIndices[idx] = resourceProvider.getMapFiles(0, localY, localX);
							if (terrainMapId != -1)
								resourceProvider.provide(3, terrainMapId);
							int objectMapId = objectIndices[idx] = resourceProvider.getMapFiles(1, localY, localX);
							if (objectMapId != -1)
								resourceProvider.provide(3, objectMapId);
						}
					}
					int dx = baseX - previousAbsoluteX;
					int dy = baseY - previousAbsoluteY;
					previousAbsoluteX = baseX;
					previousAbsoluteY = baseY;
					for (int index = 0; index < 65536; index++) {
						Npc npc = npcs[index];
						if (npc != null) {
							for (int poiint = 0; poiint < 10; poiint++) {
								npc.pathX[poiint] -= dx;
								npc.pathY[poiint] -= dy;
							}
							npc.x -= dx * 128;
							npc.y -= dy * 128;
						}
					}
					for (int index = 0; index < maxPlayers; index++) {
						Player player = players[index];
						if (player != null) {
							for (int i31 = 0; i31 < 10; i31++) {
								player.pathX[i31] -= dx;
								player.pathY[i31] -= dy;
							}
							player.x -= dx * 128;
							player.y -= dy * 128;
						}
					}
					loadingMap = true;
					byte startX = 0;
					byte endX = 104;
					byte stepX = 1;
					if (dx < 0) {
						startX = 103;
						endX = -1;
						stepX = -1;
					}
					byte startY = 0;
					byte endY = 104;
					byte stepY = 1;
					if (dy < 0) {
						startY = 103;
						endY = -1;
						stepY = -1;
					}
					for (int x = startX; x != endX; x += stepX) {
						for (int y = startY; y != endY; y += stepY) {
							int shiftedX = x + dx;
							int shiftedY = y + dy;
							for (int plane = 0; plane < 4; plane++)
								if (shiftedX >= 0 && shiftedY >= 0 && shiftedX < 104 && shiftedY < 104)
									groundItems[plane][x][y] = groundItems[plane][shiftedX][shiftedY];
								else
									groundItems[plane][x][y] = null;
						}
					}
					for (SpawnedObject object = (SpawnedObject) spawns
							.reverseGetFirst(); object != null; object = (SpawnedObject) spawns
							.reverseGetNext()) {
						object.x -= dx;
						object.y -= dy;
						if (object.x < 0 || object.y < 0 || object.x >= 104
								|| object.y >= 104)
							object.remove();
					}
					if (destX != 0) {
						destX -= dx;
						destY -= dy;
					}
					inCutScene = false;
					incomingPacket = -1;
					return true;

				case 208:
					int i3 = inStream.readUShort();
					if (i3 == 65535)
						i3 = -1; // Changed to unsigned short so need to manually make it -1
					if (i3 >= 0)
						resetAnimation(i3);
					openWalkableWidgetID = i3;
					incomingPacket = -1;
					return true;

				case 99:
					minimapState = inStream.readUnsignedByte();
					incomingPacket = -1;
					return true;

				case 75:
					int j3 = inStream.method436();
					int j11 = inStream.method436();
					RSInterface.interfaceCache[j11].anInt233 = 2;
					RSInterface.interfaceCache[j11].mediaID = j3;
					incomingPacket = -1;
					return true;

				case 114:
					anInt1104 = inStream.method434() * 30;
					//broadcastActive = false;
					broadcastTimer = 0;
					incomingPacket = -1;
					return true;

				case 60:
					anInt1269 = inStream.readUnsignedByte();
					anInt1268 = inStream.method427();
					while (inStream.currentPosition < packetSize) {
						int k3 = inStream.readUnsignedByte();
						method137(inStream, k3);
					}
					incomingPacket = -1;
					return true;

				case 35:
					int l3 = inStream.readUnsignedByte();
					int k11 = inStream.readUnsignedByte();
					int j17 = inStream.readUnsignedByte();
					int k21 = inStream.readUnsignedByte();
					aBooleanArray876[l3] = true;
					aintIntTest[l3] = k11;
					anIntArray1203[l3] = j17;
					anIntArray928[l3] = k21;
					anIntArray1030[l3] = 0;
					incomingPacket = -1;
					return true;

				case 174:
					int id = inStream.readUShort();
					int type = inStream.readUnsignedByte();
					int delay = inStream.readUShort();
					if (soundEffectVolume != 0 && type != 0 && soundCount < 50) {
						sound[soundCount] = id;
						soundType[soundCount] = type;
						soundDelay[soundCount] = delay;
						// aClass26Array1468[soundCount] = null;
						soundCount++;
					}
					incomingPacket = -1;
					return true;

				case 104:
					int j4 = inStream.method427();
					int i12 = inStream.method426();
					String s6 = inStream.readString();
					if (j4 >= 1 && j4 <= atPlayerActions.length) {
						if (s6.equalsIgnoreCase("null"))
							s6 = null;
						atPlayerActions[j4 - 1] = s6;
						atPlayerArray[j4 - 1] = i12 == 0;

					}
					incomingPacket = -1;
					return true;

				case 78:
					destX = 0;
					incomingPacket = -1;
					return true;

				case 253:
					String s = inStream.readString();
					if(s.startsWith("[SPAM]")) {
						net.runelite.api.events.ChatMessage chatMessage = new net.runelite.api.events.ChatMessage();
						chatMessage.setType(ChatMessageType.GAMEMESSAGE);
						chatMessage.setType(ChatMessageType.SPAM);
						chatMessage.setMessage(s.replace("[SPAM]", ""));
						callbacks.post(chatMessage);
						incomingPacket = -1;
						return true;
					}
					if (s.startsWith("[COSMETICCOST]")) {
						String[] args = s.replace("[COSMETICCOST]", "").split("-");
						int item_id = Integer.parseInt(args[0]);
						int cosmeticcost = Integer.parseInt(args[1]);

						if (!cosmetic_cost.containsKey(item_id)) {
							cosmetic_cost.put(item_id, cosmeticcost);
						}

						incomingPacket = -1;
						return true;
					}
					if (s.startsWith("[YT]")) {
						String[] args = s.replace("[YT]", "").split("-");

						int totalVideos = Integer.parseInt(args[0]);
						try {
							YoutubeManager.videos.clear();
							for (int index = 0; index < totalVideos; index++) {
								String videoId = args[1];
								String uploader = args[2];
								String title = args[3];
								String description = args[4];

								YouTubeVideo video = new YouTubeVideo(videoId, uploader, title, description);

								System.out.println(videoId + ", " + uploader);

								YoutubeManager.addVideo(video);
							}

							YoutubeManager.update();
						} catch (Exception e) {
							e.printStackTrace();
						}

						incomingPacket = -1;
						return true;
					}
					if (s.equals(":prayertrue:")) {
						prayClicked = true;
					} else if (s.equals(":prayerfalse:")) {
						prayClicked = false;
					} else if (s.equals(":spin")) {
						setSpinClick(true);
						spin();
						incomingPacket = -1;
						return true;
					} else if (s.equals(":resetpost:")) {
						RSInterface listingWidget = RSInterface.interfaceCache[48020];
						if (listingWidget != null) {
							listingWidget.scrollPosition = 0;
						}
					}
					else if (s.equals(":resetBox")) {
						reset();
						incomingPacket = -1;
						return true;
					} else if (s.endsWith(":gamblereq:")) {
						String name = s.substring(0, s.indexOf(":"));
						long nameToLongFormat = longForName(name);
						boolean isIgnored = false;
						for (int index = 0; index < ignoreCount; index++) {
							if (ignoreListAsLongs[index] != nameToLongFormat)
								continue;
							isIgnored = true;
						}
						if (!isIgnored && anInt1251 == 0)
							pushMessage("wishes to Gamble with you.", 31, name);
					} else if (s.endsWith(":tradereq:")) {
						String s3 = s.substring(0, s.indexOf(":"));
						long l17 = longForName(s3);
						boolean flag2 = false;
						for (int j27 = 0; j27 < ignoreCount; j27++) {
							if (ignoreListAsLongs[j27] != l17)
								continue;
							flag2 = true;

						}
						if (!flag2 && anInt1251 == 0)
							pushMessage("wishes to trade with you.", 4, s3);
					} else if (s.startsWith("//")) {
						s = s.replaceAll("//", "");
						pushMessage(s, 13, "");
					} else if (s.startsWith("/")) {
						s = s.replaceFirst("/", "");
						pushMessage(s, 11, "");
					} else if (s.endsWith("#url#")) {
						String link = s.substring(0, s.indexOf("#"));
						pushMessage("Join us at: ", 9, link);
					} else if (s.endsWith(":duelreq:")) {
						String s4 = s.substring(0, s.indexOf(":"));
						long l18 = longForName(s4);
						boolean flag3 = false;
						for (int k27 = 0; k27 < ignoreCount; k27++) {
							if (ignoreListAsLongs[k27] != l18)
								continue;
							flag3 = true;

						}
						if (!flag3 && anInt1251 == 0)
							pushMessage("wishes to duel with you.", 8, s4);
					} else if (s.endsWith(":chalreq:")) {
						String s5 = s.substring(0, s.indexOf(":"));
						long l19 = longForName(s5);
						boolean flag4 = false;
						for (int l27 = 0; l27 < ignoreCount; l27++) {
							if (ignoreListAsLongs[l27] != l19)
								continue;
							flag4 = true;

						}
						if (!flag4 && anInt1251 == 0) {
							String s8 = s.substring(s.indexOf(":") + 1, s.length() - 9);
							pushMessage(s8, 8, s5);
						}
					} else {
						pushMessage(s, 0, "");
					}
					incomingPacket = -1;
					return true;

				case 1:
					for (int k4 = 0; k4 < players.length; k4++)
						if (players[k4] != null)
							players[k4].primarySeqID = -1;
					for (int j12 = 0; j12 < npcs.length; j12++)
						if (npcs[j12] != null)
							npcs[j12].primarySeqID = -1;
					incomingPacket = -1;
					return true;

				case 50:
					long l4 = inStream.readQWord();
					int i18 = inStream.readUnsignedByte();
					//String s7 = StringUtils.fixName(StringUtils.nameForLong(l4));
					for (int k24 = 0; k24 < friendsCount; k24++) {
						if (l4 != friendsListAsLongs[k24])
							continue;
						if (friendsNodeIDs[k24] != i18) {
							friendsNodeIDs[k24] = i18;
							needDrawTabArea = true;
							if (i18 > 0)
								pushMessage(friendsList[k24] + " has logged in.", 5, "");
							if (i18 == 0)
								pushMessage(friendsList[k24] + " has logged out.", 5, "");
						}
						//s7 = null;
						break;
					}
//					if (s7 != null && friendsCount < 200) {
//						friendsListAsLongs[friendsCount] = l4;
//						friendsList[friendsCount] = s7;
//						friendsNodeIDs[friendsCount] = i18;
//						friendsCount++;
//						needDrawTabArea = true;
//					}
					sortFriendsList();
					incomingPacket = -1;
					return true;

				case 18: // Add friend or ignore
					String displayName = inStream.readNullTerminatedString();
					long displayNameLong = longForName(displayName.toLowerCase());
					boolean friend = inStream.readSignedByte() == 1;
					int world = friend ? inStream.readUnsignedByte() : 0;

					if (friend) {
						friendsList[friendsCount] = displayName;
						friendsListAsLongs[friendsCount] = displayNameLong;
						friendsNodeIDs[friendsCount] = world;
						friendsCount++;
					} else {
						ignoreListAsLongs[ignoreCount++] = displayNameLong;
					}

					sortFriendsList();
					needDrawTabArea = true;
					incomingPacket = -1;
					return true;

				case 19: // Friend updates display name
					String oldDisplayName = inStream.readNullTerminatedString();
					String newDisplayName = inStream.readNullTerminatedString();
					long oldDisplayNameLong = longForName(oldDisplayName.toLowerCase());
					long newDisplayNameLong = longForName(newDisplayName.toLowerCase());

					for (int i22 = 0; i22 < friendsList.length; i22++) {
						if (friendsList[i22] == null)
							continue;
						if (friendsListAsLongs[i22] == oldDisplayNameLong) {
							friendsList[i22] = newDisplayName;
							friendsListAsLongs[i22] = newDisplayNameLong;
							needDrawTabArea = true;
							pushMessage(String.format("Friend '%s' updated display name to '%s'.", oldDisplayName, newDisplayName));
						}
					}

					sortFriendsList();
					incomingPacket = -1;
					return true;

				case 110:
					if (tabID == 12)
						needDrawTabArea = true;
					energy = inStream.readUnsignedByte();
					incomingPacket = -1;
					return true;

				case 254:
					hintType = inStream.readUnsignedByte();
					if (hintType == 1)
						anInt1222 = inStream.readUShort();
					/**
					 * For static icons
					 */
					if (hintType >= 2 && hintType <= 6) {
						if (hintType == 2) {
							anInt937 = 64;
							anInt938 = 64;
						}
						if (hintType == 3) {
							anInt937 = 0;
							anInt938 = 64;
						}
						if (hintType == 4) {
							anInt937 = 128;
							anInt938 = 64;
						}
						if (hintType == 5) {
							anInt937 = 64;
							anInt938 = 0;
						}
						if (hintType == 6) {
							anInt937 = 64;
							anInt938 = 128;
						}
						hintType = 2;
						hintIconXpos = inStream.readUShort();
						hintIconYpos = inStream.readUShort();
						hintIconFloorPos = inStream.readUnsignedByte();
					}
					if (hintType == 10)
						anInt933 = inStream.readUShort();
					incomingPacket = -1;
					return true;

				case 248:
					int i5 = inStream.readUShortA();
					int k12 = inStream.readUShort();
					if (backDialogID != -1) {
						backDialogID = -1;
						inputTaken = true;
					}
					if (inputDialogState != 0) {
						inputDialogState = 0;
						inputTaken = true;
					}
					if (i5 == 55000) {
						RSInterface.interfaceCache[55010].scrollPosition = 0;
						RSInterface.interfaceCache[55050].scrollPosition = 0;
					}
					openInterfaceID = i5;
					invOverlayInterfaceID = k12;
					needDrawTabArea = true;
					tabAreaAltered = true;
					aBoolean1149 = false;
					incomingPacket = -1;
					return true;

				case 79:
					int j5 = inStream.method434();
					int l12 = inStream.readUShortA();
					RSInterface class9_3 = RSInterface.interfaceCache[j5];
					if (class9_3 != null && class9_3.type == 0) {
						if (l12 < 0)
							l12 = 0;
						if (l12 > class9_3.scrollMax - class9_3.height)
							l12 = class9_3.scrollMax - class9_3.height;
						class9_3.scrollPosition = l12;
					}
					incomingPacket = -1;
					return true;

				case 68:
					incomingPacket = -1;
					return true;

				case 196:
					String l5 = inStream.readNullTerminatedString();
					long l5Long = longForName(l5.toLowerCase());
					inStream.readDWord();
					Pair<Integer, PlayerRights[]> rights = PlayerRights.readRightsFromPacket(inStream);
					boolean flag5 = false;
					if (!PlayerRights.hasRightsBetween(rights.getRight(), 1, 4)) {
						for (int l29 = 0; l29 < ignoreCount; l29++) {
							if (ignoreListAsLongs[l29] != l5Long)
								continue;
							flag5 = true;

						}
					}
					if (!flag5 && anInt1251 == 0)
						try {
							String s9 = TextInput.method525(packetSize - 4 - (l5.length() + 1) - rights.getLeft() - 1, inStream);
							String rightsString = PlayerRights.buildCrownString(rights.getRight());
							pushMessage(s9, 7, rightsString + l5);
							if (Preferences.getPreferences().pmNotifications && !appFrame.isFocused()) {
								Notify.create()
										.title(Configuration.CLIENT_TITLE + " private message from " + l5)
										.text(s9)
										.position(Pos.BOTTOM_RIGHT)
										.onAction( new ActionHandler<Notify>() {
											@Override
											public void handle(Notify value) {
												pmTabToReply(l5);
											}
										})
										.hideAfter(5000)
										.shake(250, 5)
										.darkStyle()      // There are two default themes darkStyle() and default.
										.showConfirm();   // You can use warnings and error as well.
							}
						} catch (Exception exception1) {
							exception1.printStackTrace();
							Signlink.reporterror("cde1");
						}
					incomingPacket = -1;
					return true;

				case 85:
					anInt1269 = inStream.method427();
					anInt1268 = inStream.method427();
					incomingPacket = -1;
					return true;

				case 24:
					tabID = inStream.readUnsignedByte();
					incomingPacket = -1;
					return true;

				case 246:
					int i6 = inStream.method434();
					int i13 = inStream.readUShort();
					int k18 = inStream.readUShort();
					if (i6 >= 59951 && i6 <= 59960 || i6 == 59973) {
						updateProgressBar(i6, k18, i13, 0);
						incomingPacket = -1;
						return true;
					}
					if (i6 >= 24520 && i6 <= 24524) {
						updateProgressBar(i6, k18, i13, 0);
						incomingPacket = -1;
						return true;
					}
					if (i6 >= 43000) {
//						System.out.println("Interface ID: " + i6 + ", Npc ID: " + k18 + ", Zoom: " +i13);
						RSInterface npcOnInsterface1 = RSInterface.interfaceCache[i6];
						npcOnInsterface1.type = 6;
						npcOnInsterface1.contentType = 32921;
						PetSystem.petSelected = k18;
						npcOnInsterface1.modelZoom = i13;
						incomingPacket = -1;
						return true;
					}
					if (k18 == 65535) {
						RSInterface.interfaceCache[i6].anInt233 = 0;
						incomingPacket = -1;
						return true;
					} else {
						ItemDefinition itemDef = ItemDefinition.lookup(k18);
						RSInterface.interfaceCache[i6].anInt233 = 4;
						RSInterface.interfaceCache[i6].mediaID = k18;
						RSInterface.interfaceCache[i6].modelRotation1 = itemDef.xan2d;
						RSInterface.interfaceCache[i6].modelRotation2 = itemDef.yan2d;
						RSInterface.interfaceCache[i6].modelZoom = (itemDef.zoom2d * 100) / i13;
						incomingPacket = -1;
						return true;
					}

				case 171:
					boolean flag1 = inStream.readUnsignedByte() == 1;
					int j13 = inStream.readUShort();
					if (RSInterface.interfaceCache[j13] != null) {
						RSInterface.interfaceCache[j13].isMouseoverTriggered = flag1;
						RSInterface.interfaceCache[j13].interfaceHidden = flag1;
					}
					incomingPacket = -1;
					return true;

				case 142:
					int j6 = inStream.readUShort();
					if (j6 != 0) {
						resetAnimation(j6);
					}
					if (backDialogID != -1) {
						backDialogID = -1;
						inputTaken = true;
					}
					if (inputDialogState != 0) {
						inputDialogState = 0;
						inputTaken = true;
					}
					invOverlayInterfaceID = j6;
					needDrawTabArea = true;
					tabAreaAltered = true;
					openInterfaceID = -1;
					aBoolean1149 = false;
					incomingPacket = -1;
					return true;

				case 126:
					try {
						text = inStream.readString();
						int frame = inStream.readUShortA();

						handleScrollPosition(text, frame);
						handleRunePouch(text, frame);

						if (text.startsWith("www.") || text.startsWith("http")) {
							launchURL(text);
							incomingPacket = -1;
							return true;
						}
						if (text.startsWith(":pollHeight")) {
							int rows = Integer.parseInt(text.split("-")[1]);
							RSInterface rsi = RSInterface.interfaceCache[21406];
							rsi.childY[0] = (rows * 14);
							incomingPacket = -1;
							return true;
						}
						if (text.startsWith(":pollOn")) {
							String option[] = text.split("-");
							pollActive = Boolean.parseBoolean(option[1]);
							incomingPacket = -1;
							return true;
						}
						if (text.startsWith("sendGifChange")) {
							String[] args = text.split(" ");
							int gifChildId = Integer.parseInt(args[1]);
							String gifName = args[2];

							RSInterface gif = RSInterface.interfaceCache[gifChildId];
							gif.gifLocation = Signlink.getCacheDirectory() + "sprites/gifs/" + gifName + ".gif";

							SpriteLoader.resetAnimatedSprite(gif.gifLocation);
							incomingPacket = -1;
							return true;

						}
						if (text.startsWith("task:")) {
							System.out.println(text);
							String[] args = text.split(" ");
							String[] agz = {args[1], args[2], args[3], args[4]};
							CommandExecuted commandExecuted = new CommandExecuted("task", agz);
							VarbitChanged varbitChanged = new VarbitChanged();
							varbitChanged.setVarbitId(4068);
							varbitChanged.setValue(Integer.parseInt(args[4]));
							callbacks.post(varbitChanged);
							VarbitChanged varbitChanged1 = new VarbitChanged();
							varbitChanged1.setVarbitId(4069);
							varbitChanged1.setValue(Integer.parseInt(args[3]));
							callbacks.post(varbitChanged1);
							callbacks.post(commandExecuted);
							text = capitalizeJustFirst(args[1].replace("_", " ")) + " " + args[2];
						}
						updateStrings(text, frame);
						sendFrame126(text, frame);
						if (frame >= 18144 && frame <= 18244) {
							clanList[frame - 18144] = text;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					incomingPacket = -1;
					return true;

				case 170:
					try {
						text = inStream.readString();
						// broadcastActive = true;
						// broadcastMessage = text;
						// broadcastTimer = System.currentTimeMillis();
					} catch (Exception e) {
						e.printStackTrace();
					}
					incomingPacket = -1;
					return true;

				case 179: {
					try {
						int broadcastType = inStream.readUnsignedByte();

						int broadcastIndex = inStream.readUnsignedByte();

						if (broadcastType == -1) {
							BroadcastManager.removeIndex(broadcastIndex);
							return true;
						}

						Broadcast broadcast = new Broadcast();

						broadcast.type = broadcastType;

						broadcast.index = broadcastIndex;

						broadcast.message = inStream.readString();

						if (broadcastType == 1) {
							broadcast.url = inStream.readString();
						} else if (broadcastType == 2) {
							broadcast.x = inStream.readDWord();
							broadcast.y = inStream.readDWord();
							broadcast.z = inStream.readUnsignedByte();
						}
						broadcast.setExpireDelay();
						BroadcastManager.addBoradcast(broadcast);
						onBroadcast(broadcast.message);
					} catch (Exception e) {
						e.printStackTrace();
					}
					incomingPacket = -1;
					return true;
				}

				case 206:

					publicChatMode = inStream.readUnsignedByte();
					privateChatMode = inStream.readUnsignedByte();
					tradeMode = inStream.readUnsignedByte();
					inputTaken = true;
					incomingPacket = -1;
					return true;
				case 204:

					specialAttack = inStream.readSignedByte();
					specialEnabled = inStream.readSignedByte();
					incomingPacket = -1;
					return true;

				case 240:
					if (tabID == 12)
						needDrawTabArea = true;
					weight = inStream.readSignedWord();
					incomingPacket = -1;
					return true;

				case 8:
					int k6 = inStream.method436();
					int l13 = inStream.readUShort();
					RSInterface.interfaceCache[k6].anInt233 = 1;
					RSInterface.interfaceCache[k6].mediaID = l13;
					incomingPacket = -1;
					return true;

				case 122:
					int l6 = inStream.method436();
					int i14 = inStream.method436();
					int i19 = i14 >> 10 & 0x1f;
					int i22 = i14 >> 5 & 0x1f;
					int l24 = i14 & 0x1f;
					RSInterface.interfaceCache[l6].textColor = (i19 << 19) + (i22 << 11) + (l24 << 3);
					incomingPacket = -1;
					return true;

				case 53:
					needDrawTabArea = true;
					int i7 = inStream.readUShort();
					RSInterface class9_1 = RSInterface.interfaceCache[i7];
					int j19 = inStream.readUShort();

					try {
//						System.out.println("Item container size: " + j19);
						for (int j22 = 0; j22 < j19; j22++) {
							int i25 = inStream.readUnsignedByte();
							if (i25 == 255) {
								i25 = inStream.method440();
							}
							class9_1.inventoryItemId[j22] = inStream.method436();
							class9_1.inventoryAmounts[j22] = i25;
//							System.out.println(String.format("Added item [%d, %d] to container %d", class9_1.inventoryItemId[j22], i25, i7));

						}
						for (int j25 = j19; j25 < class9_1.inventoryItemId.length; j25++) {
							class9_1.inventoryItemId[j25] = 0;
							class9_1.inventoryAmounts[j25] = 0;
						}


						callbacks.post(new ItemContainerChanged(i7, getItemContainer(i7)));
/*						if (i7 == 3214) {
							List<WidgetItem> widgetItems = new ArrayList<>();
							for (int j = 0; j < 28; j++) {
								widgetItems.add(new WidgetItem(class9_1.inventoryItemId[j], class9_1.inventoryAmounts[j],j,null, getWidget(WidgetInfo.INVENTORY),null));
							}

							callbacks.drawInterface(WidgetID.INVENTORY_GROUP_ID,widgetItems);
						}*/

					} catch (Exception e) {
						System.err.println("Error in container " + i7 + ", length " + j19 + ", actual length " + class9_1.inventoryItemId.length);
						e.printStackTrace();
					}
					incomingPacket = -1;
					return true;

				case 230:
					int j7 = inStream.readUShortA();
					int j14 = inStream.readUShort();
					int k19 = inStream.readUShort();
					int k22 = inStream.method436();
					RSInterface.interfaceCache[j14].modelRotation1 = k19;
					RSInterface.interfaceCache[j14].modelRotation2 = k22;
					RSInterface.interfaceCache[j14].modelZoom = j7;
					incomingPacket = -1;
					return true;

				case 221:
					anInt900 = inStream.readUnsignedByte();
					needDrawTabArea = true;
					incomingPacket = -1;
					return true;

				case 177:
					// setNorth();
					inCutScene = true;
					cinematicCamXViewpointLoc = inStream.readUnsignedByte();
					cinematicCamYViewpointLoc = inStream.readUnsignedByte();
					cinematicCamZViewpointLoc = inStream.readUShort();
					anInt998 = inStream.readUnsignedByte();
					anInt999 = inStream.readUnsignedByte();
					if (anInt999 >= 100) {
						int k7 = cinematicCamXViewpointLoc * 128 + 64;
						int k14 = cinematicCamYViewpointLoc * 128 + 64;
						int i20 = getCenterHeight(plane, k14, k7) - cinematicCamZViewpointLoc;
						int l22 = k7 - xCameraPos;
						int k25 = i20 - zCameraPos;
						int j28 = k14 - yCameraPos;
						int i30 = (int) Math.sqrt(l22 * l22 + j28 * j28);
						yCameraCurve = (int) (Math.atan2(k25, i30) * 325.94900000000001D) & 0x7ff;
						xCameraCurve = (int) (Math.atan2(l22, j28) * -325.94900000000001D) & 0x7ff;
						if (yCameraCurve < 128)
							yCameraCurve = 128;
						if (yCameraCurve > 383)
							yCameraCurve = 383;
					}
					incomingPacket = -1;
					return true;

				case 249:
					anInt1046 = inStream.method426();
					localPlayerIndex = inStream.method436();
					incomingPacket = -1;
					return true;

				case 65:
					updateNPCs(inStream, packetSize);
					incomingPacket = -1;
					return true;

				case 27:
					enterInputInChatString = inStream.readString();
					messagePromptRaised = false;
					inputDialogState = 1;
					amountOrNameInput = "";
					inputTaken = true;
					incomingPacket = -1;
					return true;

				case 187:
					enterInputInChatString = inStream.readString();
					messagePromptRaised = false;
					inputDialogState = 2;
					amountOrNameInput = "";
					inputTaken = true;
					incomingPacket = -1;
					return true;

				case 191:
					messagePromptRaised = false;
					inputDialogState = 7;
					amountOrNameInput = "";
					inputTaken = true;
					incomingPacket = -1;
					return true;

				case 192:
					messagePromptRaised = false;
					inputDialogState = 8;
					amountOrNameInput = "";
					inputTaken = true;
					incomingPacket = -1;
					return true;

				case 97:
					int l7 = inStream.readUShort();
					resetAnimation(l7);
					if (invOverlayInterfaceID != 0) {
						invOverlayInterfaceID = 0;
						needDrawTabArea = true;
						tabAreaAltered = true;
					}
					if (backDialogID != -1) {
						backDialogID = -1;
						inputTaken = true;
					}
					if (inputDialogState != 0) {
						inputDialogState = 0;
						inputTaken = true;
					}
					// 17511 = Question Type
					// 15819 = Christmas Type
					// 15812 = Security Type
					// 15801 = Item Scam Type
					// 15791 = Password Safety ?
					// 15774 = Good/Bad Password
					// 15767 = Drama Type ????
					if (l7 == 15244) {
						openInterfaceID = 15767;
						fullscreenInterfaceID = 15244;
					} else {
						openInterfaceID = l7;
					}
					aBoolean1149 = false;
					incomingPacket = -1;
					return true;

				case 15:
					autocast = inStream.readUnsignedByte() == 1 ? true : false;
					incomingPacket = -1;
					return true;

				case 218:
					int i8 = inStream.method438();
					dialogID = i8;
					inputTaken = true;
					incomingPacket = -1;
					return true;

				case 211:
					int frame = inStream.method434();
					int amount = inStream.readDWord();
//					System.out.println("Slayer Task Amount packet -> "+ frame + " / " +amount);
					return true;

				case 87:
					int j8 = inStream.method434();
					int l14 = inStream.method439();
					Bank.onConfigChanged(j8, l14);
					Nightmare.instance.handleConfig(j8, l14);
					Autocast.getSingleton().onConfigChanged(j8, l14);
					DailyRewards.get().onConfigReceived(j8, l14);
					DonatorRewards.getInstance().onConfigChanged(j8, l14);
					if (variousSettings[j8] != l14) {
						QuestTab.onConfigChanged(j8, l14);
						MonsterDropViewer.onConfigChanged(j8, l14);
						variousSettings[j8] = l14;
//						System.out.println("Varbit [" + Varbits.varbitNames.getOrDefault(j8, "UNIDENTIFIED{" + j8 + "}") + "] value is [" +l14 + "]");
						VarbitChanged varbitChanged = new VarbitChanged();
						varbitChanged.setVarbitId(j8);
						varbitChanged.setVarpId(j8);
						varbitChanged.setValue(l14);
						callbacks.post(varbitChanged);
						method33(j8);
						needDrawTabArea = true;
						if (dialogID != -1)
							inputTaken = true;
					}
//					else {
//						System.out.println("Varbit [" + Varbits.varbitNames.getOrDefault(j8, "UNIDENTIFIED{" + j8 + "}") + "] value is already [" +l14 + "]");
//					}
					incomingPacket = -1;
					return true;

				case 36:
					int k8 = inStream.method434();
					byte byte0 = inStream.readSignedByte();
					Bank.onConfigChanged(k8, byte0);
					EventCalendar.getCalendar().onConfigReceived(k8, byte0);
					Nightmare.instance.handleConfig(k8, byte0);
					Autocast.getSingleton().onConfigChanged(k8, byte0);
					DailyRewards.get().onConfigReceived(k8, byte0);
					DonatorRewards.getInstance().onConfigChanged(k8, byte0);
					if (variousSettings[k8] != byte0) {
						QuestTab.onConfigChanged(k8, byte0);
						MonsterDropViewer.onConfigChanged(k8, byte0);
						variousSettings[k8] = byte0;

						VarbitChanged varbitChanged = new VarbitChanged();
						varbitChanged.setVarbitId(k8);
						varbitChanged.setVarpId(k8);
						varbitChanged.setValue(byte0);
						callbacks.post(varbitChanged);
						method33(k8);
						needDrawTabArea = true;
						if (dialogID != -1)
							inputTaken = true;
					}
					incomingPacket = -1;
					return true;

				case 61:
					anInt1055 = inStream.readUnsignedByte();
					incomingPacket = -1;
					return true;

				case 197:
					String message = inStream.readString();
					int npcIndex = inStream.readDWord();
					NPCDropInfo info = new NPCDropInfo();
					info.message = message;
					info.npcIndex = npcIndex;
					NPCDropInfo.addEntry(info);
					System.err.println(" "+message+" - "+npcIndex);
					pushMessage(message, 99, "");
					incomingPacket = -1;
					return true;

				case 200:
					int l8 = inStream.readUShort();
					int i15 = inStream.readSignedWord();
					RSInterface class9_4 = RSInterface.interfaceCache[l8];
					class9_4.disabledAnimationId = i15;
					if (i15 == 591 || i15 == 588) {
						class9_4.modelZoom = 900; // anInt269
					}
					if (i15 == -1) {
						class9_4.anInt246 = 0;
						class9_4.anInt208 = 0;
					}
					incomingPacket = -1;
					return true;

				case 219:
					if (invOverlayInterfaceID != 0) {
						invOverlayInterfaceID = 0;
						needDrawTabArea = true;
						tabAreaAltered = true;
					}
					if (backDialogID != -1) {
						backDialogID = -1;
						inputTaken = true;
					}
					if (inputDialogState != 0) {
						inputDialogState = 0;
						inputTaken = true;
					}
					if (this.isFieldInFocus()) {
						this.resetInputFieldFocus();
						Client.inputString = "";
					}
					openInterfaceID = -1;
					aBoolean1149 = false;
					incomingPacket = -1;
					return true;

				case 34:
					needDrawTabArea = true;
					int i9 = inStream.readUShort();
					if (handledPacket34(i9)) {
						incomingPacket = -1;
						return true;
					}
					RSInterface class9_2 = RSInterface.interfaceCache[i9];
					while (inStream.currentPosition < packetSize) {
						int j20 = inStream.readDWord();
						int i23 = inStream.readUShort(); //Item ID
						int l25 = inStream.readUnsignedByte(); // Amount
						if (l25 == 255)
							l25 = inStream.readDWord();
						if (j20 >= 0 && j20 < class9_2.inventoryItemId.length) {
							class9_2.inventoryItemId[j20] = i23;
							class9_2.inventoryAmounts[j20] = l25;
						}
//						System.out.println(i9 + " / " + i23 + " / " + l25  + " / " + j20);
					}
					incomingPacket = -1;
					return true;

				case 4:
				case 44:
				case 84:
				case 101:
				case 105:
				case 117:
				case 147:
				case 151:
				case 156:
				case 160:
				case 215:
					method137(inStream, incomingPacket);
					incomingPacket = -1;
					return true;

				case 106:
					tabID = inStream.method427();
					needDrawTabArea = true;
					tabAreaAltered = true;
					incomingPacket = -1;
					return true;

				case 164:
					int j9 = inStream.method434();
					resetAnimation(j9);
					if (invOverlayInterfaceID != 0) {
						invOverlayInterfaceID = 0;
						needDrawTabArea = true;
						tabAreaAltered = true;
					}
					backDialogID = j9;
					inputTaken = true;
					openInterfaceID = -1;
					aBoolean1149 = false;
					incomingPacket = -1;
					return true;

			}

			Signlink.reporterror("T1 - Packet: " + incomingPacket + ", Packet Size: " + packetSize
					+ " - Previous packet: " + previousPacket1 + " Previous packet size: " + previousPacketSize1
					+ ", 2nd Previous packet: " + previousPacket2 + ", 2nd Previous packet size: "
					+ previousPacketSize2);
			PacketLog.log();
			resetLogout();
		} catch (IOException _ex) {
			PacketLog.log();
			_ex.printStackTrace();
			dropClient();
		} catch (Exception exception) {
			PacketLog.log();
			exception.printStackTrace();
			String s2 = "T2 - " + incomingPacket + "," + previousPacket1 + "," + previousPacket2 + " - " + packetSize
					+ "," + (baseX + localPlayer.pathX[0]) + "," + (baseY + localPlayer.pathY[0]) + " - ";
			for (int j15 = 0; j15 < packetSize && j15 < 50; j15++)
				s2 = s2 + inStream.payload[j15] + ",";
			Signlink.reporterror(s2);
			resetLogout();

		}
		return true;
	}

	public static final int INTERFACE_ID = 47000;
	public static final int BOXES64 = 28; // 28 * 64 boxes
	private boolean spinClick;
	private int spins;
	private int spinNum;

	public void setSpinClick(boolean spinClick) { this.spinClick = spinClick; }

	public void spin() {
		if (openInterfaceID != INTERFACE_ID || !spinClick) {
			return;
		}

		RSInterface items = RSInterface.interfaceCache[47100];
		RSInterface boxes = RSInterface.interfaceCache[47200];
		if (spins < 100) {
			shift(items, boxes, 8);
		}
		else if (spins < 200) {
			shift(items, boxes, 5);
		}
		else if (spins < 300) {
			shift(items, boxes, 4);
		}
		else if (spins < 400) {
			shift(items, boxes, 3);
		}
		else if (spins < 488) {
			shift(items, boxes, 2);
		}
		else if (spins < 562) {
			shift(items, boxes, 1);
		}
		else {
			spinComplete();
		}
	}

	private void shift(RSInterface items, RSInterface boxes, int shiftAmount) {
		items.childX[0] -= shiftAmount;
		for(int i=0; i<BOXES64; i++){ boxes.childX[i] -= shiftAmount; }
		spins++;
	}

	private void spinComplete() {
		// Reset
		spins = 0;
		spinClick = false;
		spinNum++;
		// Notify server: spin complete
		stream.createFrame(145);
		stream.method432(696969);
		stream.method432(0);
		stream.method432(0);
	}

	public boolean handledPacket34(int frame) {
		if (openInterfaceID != INTERFACE_ID) {
			return false;
		}

		RSInterface items = RSInterface.interfaceCache[frame];
		while (inStream.currentPosition < packetSize) {
			int slot = inStream.readSmart();
			int itemId = inStream.readUShort();


			inStream.readUnsignedByte();
			int amount = inStream.readDWord();

			if (slot >= 0 && slot < items.inventoryItemId.length) {
				items.inventoryItemId[slot] = itemId;
				items.inventoryAmounts[slot] = amount;

//				System.out.println("interface: "+slot+", item: "+itemId+", amount: "+amount);
			}
		}
		return true;
	}

	public void reset() {
		//System.out.println("test1");
		if (spinClick) {
			return;
		}

		//System.out.println("test2");

		spinNum = 0;
		RSInterface items = RSInterface.interfaceCache[47100];
		RSInterface boxes = RSInterface.interfaceCache[47200];
		items.childX[0] = 0;
		int x = 0;
		for (int z=0; z<BOXES64; z++) {
			boxes.childX[z] = x;
			x += 2880;
		}
	}

	private String coloredItemName = "";
	private int coloredItemColor = 0xffffff;

	public int currentFog = 0;
	public int[] rainbowFog = {0xFF0000,0xFF7F00,0xFFFF00,0x00FF00,0x0000FF,0x4B0082,0x9400D3};
	public long lastFog=0;

	public boolean runHover;

	public static int cameraZoom = 600;



	public void moveCameraWithPlayer() {
		anInt1265++;
		method47(true);
		method26(true);
		method47(false);
		method26(false);
		method55();
		updateGraphicObjects();
		if (!inCutScene) {
			int i = camAngleYY;
			if (field556 / 256 > i) {
				i = field556 / 256;
			}
			if (aBooleanArray876[4] && anIntArray1203[4] + 128 > i) {
				i = anIntArray1203[4] + 128;
			}

			int k = viewRotation + viewRotationOffset & 0x7ff;
			setCameraPos(600 + i * 3,
					i, anInt1014, getCenterHeight(plane, localPlayer.y, localPlayer.x) - 50, k, anInt1015);
		}

		int j;
		if (!inCutScene)
			j = method120();
		else
			j = method121();

		int l = xCameraPos;
		int i1 = zCameraPos;
		int j1 = yCameraPos;
		int k1 = yCameraCurve;
		int l1 = xCameraCurve;

		for (int i2 = 0; i2 < 5; i2++) {
			if (aBooleanArray876[i2]) {
				int j2 = (int) ((Math.random() * (aintIntTest[i2] * 2 + 1) - aintIntTest[i2])
						+ Math.sin(anIntArray1030[i2] * (anIntArray928[i2] / 100D)) * anIntArray1203[i2]);
				if (i2 == 0)
					xCameraPos += j2;
				if (i2 == 1)
					zCameraPos += j2;
				if (i2 == 2)
					yCameraPos += j2;
				if (i2 == 3)
					xCameraCurve = xCameraCurve + j2 & 0x7ff;
				if (i2 == 4) {
					yCameraCurve += j2;
					if (yCameraCurve < 128)
						yCameraCurve = 128;
					if (yCameraCurve > 383)
						yCameraCurve = 383;
				}
			}
		}

		Model.cursorCalculations();

		Rasterizer2D.clear();
		if (Rasterizer3D.fieldOfView != cameraZoom) {
			Rasterizer3D.fieldOfView = cameraZoom;
		}

		Rasterizer2D.setDrawingArea(getViewportHeight(),
				(!isResized() ? 0 : 0),
				getViewportWidth(),
				(!isResized() ? 0 : 0));


		scene.render(xCameraPos, yCameraPos, xCameraCurve, zCameraPos, j, yCameraCurve);
		rasterProvider.setRaster();
		scene.clearGameObjectCache();

		updateEntities();
		drawHeadIcon();

		((TextureProvider)Rasterizer3D.textureLoader).animate(tickDelta);
		callbacks.drawAboveOverheads();

		draw3dScreen();

		if (getUserSettings().isGroundItemOverlay()) {
			displayGroundItems();
		}

		drawMinimap();
		drawTabArea();
		drawChatArea();
		devConsole.draw_console();

		processExperienceCounter();

		xCameraPos = l;
		zCameraPos = i1;
		yCameraPos = j1;
		yCameraCurve = k1;
		xCameraCurve = l1;

		if (!isResized()) {
			callbacks.drawInterface(WidgetID.FIXED_VIEWPORT_GROUP_ID, Collections.emptyList());
		} else {
			callbacks.drawInterface(WidgetID.RESIZABLE_VIEWPORT_OLD_SCHOOL_BOX_GROUP_ID, Collections.emptyList());
		}
	}

	public void clearTopInterfaces() {
		stream.createFrame(130);
		if (invOverlayInterfaceID != 0) {
			invOverlayInterfaceID = 0;
			needDrawTabArea = true;
			aBoolean1149 = false;
			tabAreaAltered = true;
		}
		if (backDialogID != -1) {
			backDialogID = -1;
			inputTaken = true;
			aBoolean1149 = false;
		}
		openInterfaceID = -1;
		fullscreenInterfaceID = -1;
	}
	private Sprite[] chatButtons;

	public Client() {
		accountManager = new AccountManager();
		fullscreenInterfaceID = -1;
		firstLoginMessage = "";
		xpAddedPos = expAdded = 0;
		xpLock = false;
		experienceCounter = 0;
		sounds = new int[50];
		soundLoops = new int[50];
		soundType = new int[50];
		soundDelay = new int[50];
		soundVolume = new int[50];
		sound = new int[50];
		chatTypeView = 0;
		clanChatMode = 0;
		channelButtonHoverPosition = -1;
		channelButtonClickPosition = 0;
		anIntArrayArray825 = new int[104][104];
		friendsNodeIDs = new int[200];
		groundItems = new Deque[4][104][104];
		aBoolean831 = false;
		aStream_834 = new Buffer(new byte[5000]);
		npcs = new Npc[65536];
		npcIndices = new int[65536];
		anIntArray840 = new int[1000];
		aStream_847 = Buffer.create();
		aBoolean848 = true;
		openInterfaceID = -1;
		currentExp = new int[Skills.SKILLS_COUNT];
		aBooleanArray876 = new boolean[5];
		drawFlames = false;
		reportAbuseInput = "";
		localPlayerIndex = -1;
		menuOpen = false;
		inputString = "";
		maxPlayers = 2048;
		maxPlayerCount = 2047;
		players = new Player[maxPlayers];
		playerIndices = new int[maxPlayers];
		anIntArray894 = new int[maxPlayers];
		aStreamArray895s = new Buffer[maxPlayers];
		anIntArrayArray901 = new int[104][104];
		aByteArray912 = new byte[65536];
		currentLevels = new int[Skills.SKILLS_COUNT];
		ignoreListAsLongs = new long[100];
		loadingError = false;
		anIntArray928 = new int[5];
		anIntArrayArray929 = new int[104][104];
		chatButtons = new Sprite[4];
		aBoolean954 = true;
		friendsListAsLongs = new long[200];
		currentSong = -1;
		drawingFlames = false;
		spriteDrawX = -1;
		spriteDrawY = -1;
		anIntArray968 = new int[33];
		anIntArray969 = new int[256];
		decompressors = new Decompressor[5];
		variousSettings = new int[100_000];
		aBoolean972 = false;
		anInt975 = 50;
		anIntArray976 = new int[anInt975];
		anIntArray977 = new int[anInt975];
		anIntArray978 = new int[anInt975];
		anIntArray979 = new int[anInt975];
		anIntArray980 = new int[anInt975];
		anIntArray981 = new int[anInt975];
		anIntArray982 = new int[anInt975];
		aStringArray983 = new String[anInt975];
		anInt985 = -1;
		hitMarks = new Sprite[20];
		anIntArray990 = new int[5];
		aBoolean994 = false;
		amountOrNameInput = "";
		projectiles = new Deque();
		aBoolean1017 = false;
		openWalkableWidgetID = -1;
		anIntArray1030 = new int[5];
		aBoolean1031 = false;
		mapFunctions = new Sprite[119];
		dialogID = -1;
		maximumLevels = new int[Skills.SKILLS_COUNT];
		aBoolean1047 = true;
		anIntArray1052 = new int[152];
		anIntArray1229 = new int[152];
		incompleteAnimables = new Deque();
		anIntArray1057 = new int[33];
		chatboxInterface = new RSInterface();
		chatboxInterface.id = 100037;
		menuManager = new MenuManager(this);
		menuManager.start();
		mapScenes = new IndexedImage[188];
		barFillColor = 0x4d4233;
		myAppearance = new int[7];
		anIntArray1072 = new int[1000];
		anIntArray1073 = new int[1000];
		loadingMap = false;
		friendsList = new String[200];
		inStream = Buffer.create();
		expectedCRCs = new int[9];
		headIcons = new Sprite[20];
		skullIcons = new Sprite[20];
		headIconsHint = new Sprite[20];
		tabAreaAltered = false;
		aString1121 = "";
		atPlayerActions = new String[8];
		atPlayerArray = new boolean[8];
		constructRegionData = new int[4][13][13];
		mapIconSprite = new Sprite[1000];
		inTutorialIsland = false;
		aBoolean1149 = false;
		crosses = new Sprite[8];
		needDrawTabArea = false;
		loggedIn = false;
		canMute = false;
		isDynamicRegion = false;
		inCutScene = false;
		myUsername = "";
		setPassword("");
		captchaInput = "";
		genericLoadingError = false;
		reportAbuseInterfaceID = -1;
		spawns = new Deque();
		camAngleYY = 128;
		invOverlayInterfaceID = 0;
		stream = Buffer.create();
		anIntArray1203 = new int[5];
		chatAreaScrollLength = 78;
		promptInput = "";
		modIcons = new Sprite[94];
		tabID = 3;
		inputTaken = false;
		songChanging = true;
		collisionMaps = new CollisionMap[4];
		aBoolean1242 = false;
		rsAlreadyLoaded = false;
		welcomeScreenRaised = true;
		messagePromptRaised = false;
		backDialogID = -1;
		bigX = new int[4000];
		bigY = new int[4000];
		chatMessages = new ChatMessage[500];
	}

	public int getLocalPlayerX() {
		return baseX + (localPlayer.x - 6 >> 7);
	}

	public int getLocalPlayerY() {
		return baseY + (localPlayer.y - 6 >> 7);
	}

	public int xpCounter;
	public int expAdded;
	public int xpAddedPos;
	public boolean xpLock;

	private Sprite chatArea;
	private Sprite infinity;
	private Sprite shopSprite;
	private Sprite shopSpriteHover;

	public String name;
	public String message;
	public int chatTypeView;
	public int clanChatMode;
	public int duelMode;
	public int autocastId = 0;
	public boolean autocast = false;

	public static Sprite[] cacheSprite, cacheSprite1, cacheSprite2, cacheSprite3, cacheSprite4;
	public static Sprite[] cacheInterface;

	private IndexedImage titleButton;
	private int ignoreCount;
	private long longStartTime;
	private int[][] anIntArrayArray825;
	public int[] friendsNodeIDs;
	private Deque[][][] groundItems;
	private int[] anIntArray828;
	private int[] anIntArray829;
	private volatile boolean aBoolean831;
	private LoginScreenState loginScreenState = LoginScreenState.LOGIN;
	private Buffer aStream_834;
	public Npc[] npcs;
	private int npcCount;
	private int[] npcIndices;
	private int anInt839;
	private int[] anIntArray840;
	private int dealtWithPacket;
	private int dealtWithPacketSize;
	private int previousPacket1;
	private int previousPacket2;
	private int previousPacketSize2;
	private int previousPacketSize1;
	private String clickToContinueString;
	private int privateChatMode;
	private Buffer aStream_847;
	private boolean aBoolean848;
	private static int anInt849;
	private int[] anIntArray850;
	private int[] anIntArray851;
	private int[] anIntArray852;
	private int[] anIntArray853;
	private static int anInt854;
	private int hintType;
	public static int openInterfaceID;
	private int xCameraPos;
	private int zCameraPos;
	private int yCameraPos;
	private int yCameraCurve;
	private int xCameraCurve;
	public final int[] currentExp;
	private Sprite mapFlag;
	private Sprite mapMarker;

	private final boolean[] aBooleanArray876;
	private int weight;
	private MouseDetection mouseDetection;
	private volatile boolean drawFlames;
	private String reportAbuseInput;
	private int localPlayerIndex;
	public boolean menuOpen;
	private int anInt886;
	public static String inputString;
	private final int maxPlayers;
	public final int maxPlayerCount;
	public Player[] players;
	private int playerCount;
	public int[] playerIndices;
	private int anInt893;
	private int[] anIntArray894;
	private Buffer[] aStreamArray895s;
	private int viewRotationOffset;
	public int friendsCount;
	private int anInt900;
	private int[][] anIntArrayArray901;
	private byte[] aByteArray912;
	private int anInt913;
	private int crossX;
	private int crossY;
	private int crossIndex;
	private int crossType;
	private int plane;
	public final int[] currentLevels;
	private int anInt924;
	private final long[] ignoreListAsLongs;
	private boolean loadingError;
	private final int[] anIntArray928;
	private int[][] anIntArrayArray929;
	private Sprite aClass30_Sub2_Sub1_Sub1_931;
	private Sprite aClass30_Sub2_Sub1_Sub1_932;
	private int anInt933;
	private int hintIconXpos;
	private int hintIconYpos;
	private int hintIconFloorPos;
	private int anInt937;
	private int anInt938;
	public ChatMessage[] chatMessages;
	public int tickDelta;
	private SceneGraph scene;
	private int menuScreenArea;
	private int menuOffsetX;
	private int menuOffsetY;
	private int menuWidth;
	private int menuHeight;

	private int clickedIdx = -1;
	private int submenuX = 0;
	private int submenuY = 0;
	private int submenuWidth = 0;
	private int submenuHeight = 0;
	private int submenuScroll = 0;
	private static int submenuScrollMax = 0;
	private int submenuIdx = -1;
	private int menuScroll = 0;

	private static int menuScrollMax = 0;
	private long aLong953;
	private boolean aBoolean954;
	public long[] friendsListAsLongs;
	private int currentSong;
	private static int nodeID = 1;
	static int portOff;
	static boolean clientData;
	private static boolean isMembers = true;
	private static boolean lowMem;
	private volatile boolean drawingFlames;
	private int spriteDrawX;
	private int spriteDrawY;
	private final int[] anIntArray965 = { 0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff };
	private final int[] anIntArray968;
	private final int[] anIntArray969;
	final Decompressor[] decompressors;
	public int variousSettings[];
	private boolean aBoolean972;
	private final int anInt975;
	private final int[] anIntArray976;
	private final int[] anIntArray977;
	private final int[] anIntArray978;
	private final int[] anIntArray979;
	private final int[] anIntArray980;
	private final int[] anIntArray981;
	private final int[] anIntArray982;
	private final String[] aStringArray983;
	private int field556;
	private int anInt985;
	private int anInt986;
	private Sprite[] hitMarks;
	private int anInt989;
	private final int[] anIntArray990;
	private final boolean aBoolean994;
	private int cinematicCamXViewpointLoc;
	private int cinematicCamYViewpointLoc;
	private int cinematicCamZViewpointLoc;
	private int anInt998;
	private int anInt999;
	private ISAACRandomGen encryption;
	private Sprite mapEdge;
	public static int[][] anIntArrayArray1003 = {
			{ 6798, 107, 10283, 16, 4797, 7744, 5799, 4634, -31839, 22433, 2983, -11343, 8, 5281, 10438, 3650, -27322,
					-21845, 200, 571, 908, 21830, 28946, -15701, -14010 },
			{ 8741, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 25239,
					8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010 },
			{ 25238, 8742, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533,
					8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010 },
			{ 4626, 11146, 6439, 12, 4758, 10270 },

			{ 4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574, 0, 12821, 100 } };

	private Sprite multiOverlay;
	public String amountOrNameInput;
	private int anInt1005;
	private int daysSinceLastLogin;
	private int packetSize;
	private int incomingPacket;
	private int anInt1009;
	private int anInt1010;
	private int anInt1011;
	private Deque projectiles;
	private int anInt1014;
	private int anInt1015;
	private int anInt1016;
	private boolean aBoolean1017;
	public int openWalkableWidgetID;
	private static final int[] SKILL_EXPERIENCE;
	private int minimapState;
	private int anInt1022;
	private int loadingStage;
	private Sprite scrollBar1;
	private Sprite scrollBar2;
	private int anInt1026;
	private final int[] anIntArray1030;
	private boolean aBoolean1031;
	private static Sprite[] mapFunctions;
	static int baseX;
	static int baseY;
	private int previousAbsoluteX;
	private int previousAbsoluteY;
	private int loginFailures;
	private int anInt1039;
	private int anInt1040;
	private int anInt1041;
	private int dialogID;
	public final int[] maximumLevels;
	private int anInt1046;
	private boolean aBoolean1047;
	public TextDrawingArea smallText;
	public TextDrawingArea XPFONT;
	public TextDrawingArea aTextDrawingArea_1271;
	public TextDrawingArea chatTextDrawingArea;
	public TextDrawingArea aTextDrawingArea_1273;
	public RSFont newSmallFont;
	public RSFont newRegularFont;
	public RSFont newBoldFont;
	public RSFont newFancyFont;

	/**
	 * New fonts
	 */
	public static RSFont lato, latoBold, kingthingsPetrock, kingthingsPetrockLight;

	private int anInt1048;
	private String aString1049;
	private static int anInt1051;
	private final int[] anIntArray1052;
	private FileArchive titleStreamLoader;
	private int anInt1055;
	public Deque incompleteAnimables;
	private final int[] anIntArray1057;
	public final RSInterface chatboxInterface;
	private IndexedImage[] mapScenes;
	private static int anInt1061;
	private final int barFillColor;
	private int friendsListAction;
	private final int[] myAppearance;
	private int mouseInvInterfaceIndex;
	private int lastActiveInvInterface;
	public OnDemandFetcher resourceProvider;
	public int currentRegionX;
	public int currentRegionY;
	private int mapIconAmount;
	private int[] anIntArray1072;
	private int[] anIntArray1073;

	private Sprite[] mapDots;
	private int anInt1079;
	private boolean loadingMap;
	private String[] friendsList;
	private Buffer inStream;
	private int draggingItemInterfaceId;
	private int itemDraggingSlot;
	private int activeInterfaceType;
	private int anInt1087;
	private int anInt1088;
	public static int anInt1089;
	public static int rotation;
	private final int[] expectedCRCs;
	private Sprite[] headIcons;
	private Sprite[] skullIcons;
	private Sprite[] headIconsHint;
	private static int anInt1097;
	private int x;
	private int y;
	private int height;
	private int anInt1101;
	private int angle;
	public static boolean tabAreaAltered;

	private int anInt1104;

	private static int anInt1117;
	private int membersInt;
	private String aString1121;
	private String enterInputInChatString;
	public static Player localPlayer;
	public static int[] myHeadAndJaw = new int[2];
	public boolean broadcastActive;
	public long broadcastTimer;
	public String broadcastMessage;
	private final String[] atPlayerActions;
	private final boolean[] atPlayerArray;
	private final int[] atPlayerOptions = new int[8];
	private final int[][][] constructRegionData;
	public final static int[] tabInterfaceIDs = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
	private int cameraOffsetY;
	public int menuActionRow;
	private static int anInt1134;
	public int spellSelected;
	private int anInt1137;
	private int spellUsableOn;
	private String spellTooltip;
	private Sprite[] mapIconSprite;
	private boolean inTutorialIsland;
	private static int anInt1142;
	private int energy;
	private boolean aBoolean1149;
	private Sprite[] crosses;
	private IndexedImage[] aBackgroundArray1152s;
	public static boolean needDrawTabArea;
	private int unreadMessages;
	private static int anInt1155;
	private static boolean fpsOn;
	private static boolean drawGrid;
	public static boolean loggedIn;
	private boolean canMute;
	private boolean isDynamicRegion;
	private boolean inCutScene;
	public static int loopCycle;
	private static final String validUserPassChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";

	private AbstractRasterProvider chatAreaGraphicsBuffer;
	private int daysSinceRecovChange;
	private RSSocket socketStream;
	public static int minimapZoom;
	private static int anInt1175;
	private boolean genericLoadingError;
	private final int[] objectTypes = { 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3 };
	private int reportAbuseInterfaceID;
	private Deque spawns;
	private int[] anIntArray1180;
	private int[] anIntArray1181;
	private int[] anIntArray1182;
	private byte[][] terrainData;
	private int camAngleYY;
	private static int viewRotation;
	private int anInt1186;
	private int anInt1187;
	private static int anInt1188;
	private int invOverlayInterfaceID;
	private int[] anIntArray1190;
	private int[] anIntArray1191;
	public static Buffer stream;
	private int anInt1193;
	private boolean splitPrivateChat = true;
	public String[] clanList = new String[100];


	@Getter
	private MenuManager menuManager;
	private Sprite aClass30_Sub2_Sub1_Sub1_1201;
	private Sprite aClass30_Sub2_Sub1_Sub1_1202;
	private final int[] anIntArray1203;
	public static int anIntArray1204[] = { 9104, 10275, 7595, 3610, 7975, 8526, 918, -26734, 24466, 10145, -6882, 5027,
			1457, 16565, -30545, 25486, 24, 5392, 10429, 3673, -27335, -21957, 192, 687, 412, 21821, 28835, -15460,
			-14019 };
	private static boolean flagged;
	private int anInt1208;
	private int minimapRotation;
	public static int chatAreaScrollLength;
	private String promptInput;
	private int mouseClickCount;
	private int[][][] tileHeights;
	private long aLong1215;
	private int loginScreenCursorPos;
	private final Sprite[] modIcons;
	private long aLong1220;
	public static int tabID;
	private int anInt1222;
	public static boolean inputTaken;
	public static int inputDialogState;
	private static int anInt1226;
	private int nextSong;
	private boolean songChanging;
	private final int[] anIntArray1229;
	private CollisionMap[] collisionMaps;
	public static int anIntArray1232[];
	private int[] mapCoordinates;
	private int[] terrainIndices;
	private int[] objectIndices;
	private int anInt1237;
	private int anInt1238;
	public final int anInt1239 = 100;
	private boolean aBoolean1242;
	private int atInventoryLoopCycle;
	private int atInventoryInterface;
	private int atInventoryIndex;
	private int atInventoryInterfaceType;
	private byte[][] objectData;
	private int tradeMode;
	private int gameMode;
	private int anInt1249;
	private int anInt1251;
	private final boolean rsAlreadyLoaded;
	private int anInt1253;
	private boolean welcomeScreenRaised;
	private boolean messagePromptRaised;
	private byte[][][] tileFlags;
	private int prevSong;
	private int destX;
	private int destY;
	private Sprite minimapImage;
	public Sprite backgroundFix;
	private int anInt1264;
	private int anInt1265;
	private int anInt1268;
	private int anInt1269;
	private int anInt1275;
	public static int backDialogID;
	private int cameraOffsetX;
	private int[] bigX;
	private int[] bigY;
	public int itemSelected;
	private int anInt1283;
	private int anInt1284;
	private int anInt1285;
	private String selectedItemName;
	private int publicChatMode;
	private static int anInt1288;
	public static int anInt1290;
	private int hoverShopTile = -1;
	public static String server = Configuration.CONNECTION.address;
	public static int port = Integer.parseInt(Configuration.CONNECTION.port);
	public static boolean controlIsDown;
	public int drawCount;
	public int fullscreenInterfaceID;
	public int anInt1044;// 377
	public int anInt1129;// 377
	public int anInt1315;// 377
	public int anInt1500;// 377
	public int anInt1501;// 377
	public int[] fullScreenTextureArray;
	public final ArrayList<RSInterface> parallelWidgetList = new ArrayList<>();

	private void drawParallelWidgets() {
		if (this.parallelWidgetList.isEmpty())
			return;
		for (RSInterface widget : this.parallelWidgetList) {
			if (widget != null) {
				if(widget.id == 46500) {
					if(isResized()) {
						drawInterface(0, (getCanvasWidth() / 2) - 36, widget, (int) NotificationInterface.currentY);
					} else {
						drawInterface(0, 99, widget, (int) NotificationInterface.currentY);
					}
				} else {
					int xPosition = 512 / 2 - widget.width / 2;
					int yPosition = 334 / 2 - widget.height / 2;
					RSInterface.interfaceCache[HealthHud.WIDGET_ID].x = xPosition;
					RSInterface.interfaceCache[HealthHud.WIDGET_ID].y = yPosition;
					if (widget.id == HealthHud.WIDGET_ID) {
						if(getGameCycle() >= HealthHud.healthHudTimeoutTick)
							continue;
						xPosition = !isResized() ? 0 : getViewportWidth() / 2 - 358;
						yPosition = 0;
					}
					xPosition += widget.getRelativeX();
					yPosition += widget.getRelativeY();
					drawInterface(0, xPosition, widget, yPosition);
				}
			}
		}
	}
	private String myUsername;
	private String myPassword;

	private static final Object CAPTCHA_LOCK = new Object();
	private String captchaInput;

	public void setPassword(String password) {
		myPassword = password;
	}

	public String getPassword() {
		return myPassword;
	}

	public void launchURL(String url) {
/*		if (server.equals("173.185.70.167")) {
			javax.swing.JOptionPane.showMessageDialog(this, "Staff just tried to direct you to: " + url);
			return;
		}*/
		if (!url.toLowerCase().startsWith("http")) {
			url = "http://" + url;
		}
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				@SuppressWarnings("rawtypes")
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				@SuppressWarnings("unchecked")
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			} else if (osName.startsWith("Windows"))
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			else { // assume Unix or Linux
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					Desktop.getDesktop().browse(new URI(url));
				}
			}
		} catch (Exception e) {
			pushMessage("Failed to open URL.", 0, "");
			System.err.println("Failed to launcher url on operating system " + osName);
			e.printStackTrace();
		}
	}
	private void drawInputField(RSInterface child, int xPosition, int yPosition, int width, int height) {
		int clickX = MouseHandler.saveClickX, clickY = MouseHandler.saveClickY;
		Sprite[] inputSprites = this.inputSprites;
		int xModification = 0, yModification = 0;
		for (int row = 0; row < width; row += 12) {
			if (row + 12 > width)
				row -= 12 - (width - row);
			inputSprites[6].drawSprite(xModification <= 0 ? xPosition + row : xPosition + xModification, yPosition);
			for (int collumn = 0; collumn < height; collumn += 12) {
				if (collumn + 12 > height)
					collumn -= 12 - (height - collumn);
				inputSprites[6].drawSprite(xPosition + row,
						yModification <= 0 ? yPosition + collumn : yPosition + yModification);
			}
		}
		inputSprites[1].drawSprite(xPosition, yPosition);
		inputSprites[0].drawSprite(xPosition, yPosition + height - 8);
		inputSprites[2].drawSprite(xPosition + width - 4, yPosition);
		inputSprites[3].drawSprite(xPosition + width - 4, yPosition + height - 8);
		xModification = 0;
		yModification = 0;
		for (int top = 0; top < width; top += 8) {
			if (top + 8 > width)
				top -= 8 - (width - top);
			inputSprites[5].drawSprite(xPosition + top, yPosition);
			inputSprites[5].drawSprite(xPosition + top, yPosition + height - 1);
		}
		for (int bottom = 0; bottom < height; bottom += 8) {
			if (bottom + 8 > height)
				bottom -= 8 - (height - bottom);
			inputSprites[4].drawSprite(xPosition, yPosition + bottom);
			inputSprites[4].drawSprite(xPosition + width - 1, yPosition + bottom);
		}
		String message = child.message;
		if (aTextDrawingArea_1271.getTextWidth(message) > child.width - 10)
			message = message.substring(message.length() - (child.width / 10) - 1, message.length());
		if (child.displayAsterisks)
			this.aTextDrawingArea_1271.method389(false, (xPosition + 4), child.textColor,
					new StringBuilder().append("").append(StringUtils.passwordAsterisks(message))
							.append(((!child.isInFocus ? 0 : 1) & (loopCycle % 40 < 20 ? 1 : 0)) != 0 ? "|" : "")
							.toString(),
					(yPosition + (height / 2) + 6));
		else
			this.aTextDrawingArea_1271.method389(false, (xPosition + 4), child.textColor,
					new StringBuilder().append("").append(message)
							.append(((!child.isInFocus ? 0 : 1) & (loopCycle % 40 < 20 ? 1 : 0)) != 0 ? "|" : "")
							.toString(),
					(yPosition + (height / 2) + 6));


		boolean clicked = false;
		if (drawingTabArea && !isResized()) {
			int x = 516;
			int y = 168;
			clicked = clickX >= xPosition + x && clickX <= xPosition + x + child.width && clickY >= yPosition + y && clickY <= yPosition + y + child.height;
		} else {
			clicked = clickX >= xPosition && clickX <= xPosition + child.width && clickY >= yPosition && clickY <= yPosition + child.height;
		}

		if (clicked && !child.isInFocus && getInputFieldFocusOwner() != child) {
			if ((MouseHandler.instance.clickMode2 == 1 && !menuOpen)) {
				RSInterface.currentInputFieldId = child.id;
				setInputFieldFocusOwner(child);
				if (child.message != null && child.message.equals(child.defaultInputFieldText))
					child.message = "";
				if (child.message == null)
					child.message = "";
			}
		}
	}
	public void setInputFieldFocusOwner(RSInterface owner) {
		for (RSInterface rsi : RSInterface.interfaceCache)
			if (rsi != null)
				if (rsi == owner)
					rsi.isInFocus = true;
				else
					rsi.isInFocus = false;
	}

	public RSInterface getInputFieldFocusOwner() {
		for (RSInterface rsi : RSInterface.interfaceCache)
			if (rsi != null)
				if (rsi.isInFocus)
					return rsi;
		return null;
	}

	public void resetInputFieldFocus() {
		for (RSInterface rsi : RSInterface.interfaceCache)
			if (rsi != null)
				rsi.isInFocus = false;
		RSInterface.currentInputFieldId = -1;
	}

	public boolean lockChatbox = false;



	public boolean isFieldInFocus() {
		if (openInterfaceID == -1 && invOverlayInterfaceID <= 0) {
			return false;
		}
		for (RSInterface rsi : RSInterface.interfaceCache) {
			if (rsi != null) {
				if (rsi.type == 16 && rsi.isInFocus) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean scrollbarVisible(RSInterface widget) {
		if (widget.id == 55010) {
			if (RSInterface.interfaceCache[55024].message.length() <= 0) {
				return false;
			}
		} else if (widget.id == 55050) {
			if (RSInterface.interfaceCache[55064].message.length() <= 0) {
				return false;
			}
		}
		return true;
	}

	public void mouseWheelDragged(int i, int j) {
		this.anInt1186 += i * 3;
		this.anInt1187 += (j << 1);
	}

	public static String lastCommand;
	public static int anInt1401 = 256;


	public static Class56 midi_player;

	public static int anInt720 = 0;
	public static boolean fetchMusic = false;
	public static int music_volume = 50;
	public static int anInt478 = -1;
	public static byte[] music_payload;
	public static int anInt155 = 0;
	public static int anInt2200 = 0;
	public static int jmp_volume;
	public static boolean aBoolean475;
	public static int fadeDuration;
	public static boolean repeatMusic;
	public static int anInt139;
	public static int musicVolume = 255;
	public static long aLong1432;
	private final int[] sound;
	public int soundCount;

	private final int[] soundLoops;
	private final int[] sounds;
	private final int[] soundDelay;
	private final int[] soundType;
	private final int[] soundVolume;
	private int currentSoundTime;
	private long soundTimer;
	@SuppressWarnings("unused")
	private int currentSoundLoop;
	@SuppressWarnings("unused")
	private int currentSoundPlaying;
	private static int soundEffectVolume = 127;
	public static int[] anIntArray385 = new int[] { 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800,
			12800, 12800, 12800, 12800, 12800, 12800, 12800 };
	public static boolean LOOP_MUSIC = false;

	public static final void sleep(long time) {
		if (time > 0L) {
			if (time % 10L != 0L) {
				threadSleep(time);
			} else {
				threadSleep(time - 1L);
				threadSleep(1L);
			}
		}
	}

	private static final void threadSleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
		}
	}

	static {
		SKILL_EXPERIENCE = new int[99];
		int i = 0;
		for (int j = 0; j < 99; j++) {
			int l = j + 1;
			int i1 = (int) (l + 300D * Math.pow(2D, l / 7D));
			i += i1;
			SKILL_EXPERIENCE[j] = i / 4;
		}
		anIntArray1232 = new int[32];
		i = 2;
		for (int k = 0; k < 32; k++) {
			anIntArray1232[k] = i - 1;
			i += i;
		}

		hidelocalnpcs = false;
		hideentities = false;
		hideothers = false;
		hideothers2d = false;
		hidefriends = false;
		hidefriendclan = false;
		hideignores = false;
		hidelocalplayers = false;
		hidelocal2dplayers = false;
		hide2dnpc = false;
		hidepets = false;
		hideprojectiles = false;
		Interpreter_arrayLengths = new int[5];
		Interpreter_arrays = new int[5][5000];
		Interpreter_intStack = new int[1000];
		Interpreter_stringStack = new String[1000];
		Interpreter_frameDepth = 0;
		Interpreter_frames = new ScriptFrame[50];


		scriptEvents = new NodeDeque();
		scriptEvents2 = new NodeDeque();
		scriptEvents3 = new NodeDeque();
	}

	static NodeDeque scriptEvents;
	@ObfuscatedName("qy")
	@ObfuscatedSignature(
			descriptor = "Lps;"
	)
	@Export("scriptEvents2")
	static NodeDeque scriptEvents2;
	@ObfuscatedName("qf")
	@ObfuscatedSignature(
			descriptor = "Lps;"
	)
	@Export("scriptEvents3")
	static NodeDeque scriptEvents3;
	public static String md5Hash(String md5) {
		try {

			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());

			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}

			return sb.toString();

		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	public int getLevelForXPSlayer(int exp) {
		int points = 0;
		int output;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	public void updateSkillStrings(int i) {
		switch (i) {
			case 0:
				sendFrame126("" + currentLevels[0] + "", 4004);
				sendFrame126("" + getLevelForXP(currentExp[0]) + "", 4005);
				sendFrame126("" + currentExp[0] + "", 4044);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[0]) + 1) + "", 4045);
				break;

			case 1:
				sendFrame126("" + currentLevels[1] + "", 4008);
				sendFrame126("" + getLevelForXP(currentExp[1]) + "", 4009);
				sendFrame126("" + currentExp[1] + "", 4056);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[1]) + 1) + "", 4057);
				break;

			case 2:
				sendFrame126("" + currentLevels[2] + "", 4006);
				sendFrame126("" + getLevelForXP(currentExp[2]) + "", 4007);
				sendFrame126("" + currentExp[2] + "", 4050);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[2]) + 1) + "", 4051);
				break;

			case 3:
				sendFrame126("" + currentLevels[3] + "", 4016);
				sendFrame126("" + getLevelForXP(currentExp[3]) + "", 4017);
				sendFrame126("" + currentExp[3] + "", 4080);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[3]) + 1) + "", 4081);
				break;

			case 4:
				sendFrame126("" + currentLevels[4] + "", 4010);
				sendFrame126("" + getLevelForXP(currentExp[4]) + "", 4011);
				sendFrame126("" + currentExp[4] + "", 4062);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[4]) + 1) + "", 4063);
				break;

			case 5:
				sendFrame126("" + currentLevels[5] + "", 4012);
				sendFrame126("" + getLevelForXP(currentExp[5]) + "", 4013);
				sendFrame126("" + currentExp[5] + "", 4068);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[5]) + 1) + "", 4069);
				sendFrame126("" + currentLevels[5] + "/" + getLevelForXP(currentExp[5]) + "", 687);// Prayer
				// frame
				break;

			case 6:
				sendFrame126("" + currentLevels[6] + "", 4014);
				sendFrame126("" + getLevelForXP(currentExp[6]) + "", 4015);
				sendFrame126("" + currentExp[6] + "", 4074);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[6]) + 1) + "", 4075);
				break;

			case 7:
				sendFrame126("" + currentLevels[7] + "", 4034);
				sendFrame126("" + getLevelForXP(currentExp[7]) + "", 4035);
				sendFrame126("" + currentExp[7] + "", 4134);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[7]) + 1) + "", 4135);
				break;

			case 8:
				sendFrame126("" + currentLevels[8] + "", 4038);
				sendFrame126("" + getLevelForXP(currentExp[8]) + "", 4039);
				sendFrame126("" + currentExp[8] + "", 4146);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[8]) + 1) + "", 4147);
				break;

			case 9:
				sendFrame126("" + currentLevels[9] + "", 4026);
				sendFrame126("" + getLevelForXP(currentExp[9]) + "", 4027);
				sendFrame126("" + currentExp[9] + "", 4110);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[9]) + 1) + "", 4111);
				break;

			case 10:
				sendFrame126("" + currentLevels[10] + "", 4032);
				sendFrame126("" + getLevelForXP(currentExp[10]) + "", 4033);
				sendFrame126("" + currentExp[10] + "", 4128);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[10]) + 1) + "", 4129);
				break;

			case 11:
				sendFrame126("" + currentLevels[11] + "", 4036);
				sendFrame126("" + getLevelForXP(currentExp[11]) + "", 4037);
				sendFrame126("" + currentExp[11] + "", 4140);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[11]) + 1) + "", 4141);
				break;

			case 12:
				sendFrame126("" + currentLevels[12] + "", 4024);
				sendFrame126("" + getLevelForXP(currentExp[12]) + "", 4025);
				sendFrame126("" + currentExp[12] + "", 4104);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[12]) + 1) + "", 4105);
				break;

			case 13:
				sendFrame126("" + currentLevels[13] + "", 4030);
				sendFrame126("" + getLevelForXP(currentExp[13]) + "", 4031);
				sendFrame126("" + currentExp[13] + "", 4122);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[13]) + 1) + "", 4123);
				break;

			case 14:
				sendFrame126("" + currentLevels[14] + "", 4028);
				sendFrame126("" + getLevelForXP(currentExp[14]) + "", 4029);
				sendFrame126("" + currentExp[14] + "", 4116);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[14]) + 1) + "", 4117);
				break;

			case 15:
				sendFrame126("" + currentLevels[15] + "", 4020);
				sendFrame126("" + getLevelForXP(currentExp[15]) + "", 4021);
				sendFrame126("" + currentExp[15] + "", 4092);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[15]) + 1) + "", 4093);
				break;

			case 16:
				sendFrame126("" + currentLevels[16] + "", 4018);
				sendFrame126("" + getLevelForXP(currentExp[16]) + "", 4019);
				sendFrame126("" + currentExp[16] + "", 4086);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[16]) + 1) + "", 4087);
				break;

			case 17:
				sendFrame126("" + currentLevels[17] + "", 4022);
				sendFrame126("" + getLevelForXP(currentExp[17]) + "", 4023);
				sendFrame126("" + currentExp[17] + "", 4098);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[17]) + 1) + "", 4099);
				break;

			case 18:
				sendFrame126("" + currentLevels[18] + "", 12166);
				sendFrame126("" + getLevelForXPSlayer(currentExp[18]) + "", 12167);
				sendFrame126("" + currentExp[18] + "", 12171);
				sendFrame126("" + getXPForLevel(getLevelForXPSlayer(currentExp[18]) + 1) + "", 12172);
				break;

			case 19:
				sendFrame126("" + currentLevels[19] + "", 13926);
				sendFrame126("" + getLevelForXP(currentExp[19]) + "", 13927);
				sendFrame126("" + currentExp[19] + "", 13921);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[19]) + 1) + "", 13922);
				break;

			case 20: // runecraft
				sendFrame126("" + currentLevels[20] + "", 4152);
				sendFrame126("" + getLevelForXP(currentExp[20]) + "", 4153);
				sendFrame126("" + currentExp[20] + "", 4157);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[20]) + 1) + "", 4159);
				break;

			// hunter is 21
		}
	}


	/**
	 * Runelite
	 */
	public DrawCallbacks drawCallbacks;
	@javax.inject.Inject
	private Callbacks callbacks;

	private boolean gpu = false;

	@Override
	public Callbacks getCallbacks() {
		return callbacks;
	}

	@Override
	public DrawCallbacks getDrawCallbacks() {
		return drawCallbacks;
	}

	@Override
	public void setDrawCallbacks(DrawCallbacks drawCallbacks) {
		this.drawCallbacks = drawCallbacks;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public String getBuildID() {
		return "1";
	}

	@Override
	public List<net.runelite.api.Player> getPlayers() {
		return Arrays.asList(players);
	}

	@Override
	public List<NPC> getNpcs() {
		List<NPC> npcs = new ArrayList<NPC>(npcCount);
		for (int i = 0; i < npcCount; ++i)
		{
			npcs.add(this.npcs[npcIndices[i]]);
		}
		return npcs;
	}

	@Override
	public RSNPC[] getCachedNPCs() {
		return npcs;
	}

	@Override
	public RSPlayer[] getCachedPlayers() {
		return players;
	}

	@Override
	public int getLocalInteractingIndex() {
		return 0;
	}

	@Override
	public void setLocalInteractingIndex(int idx) {

	}

	@Override
	public RSNodeDeque getTilesDeque() {
		return null;
	}

	@Override
	public RSNodeDeque[][][] getGroundItemDeque() {
		return new RSNodeDeque[0][][];
	}

	@Override
	public RSNodeDeque getProjectilesDeque() {
		return null;
	}

	@Override
	public RSNodeDeque getGraphicsObjectDeque() {
		return null;
	}

	@Override
	public String getUsername() {
		return myUsername;
	}

	@Override
	public int getBoostedSkillLevel(Skill skill) {
		int[] boostedLevels = getBoostedSkillLevels();
		return boostedLevels[skill.ordinal()];
	}

	@Override
	public int getRealSkillLevel(Skill skill) {
		return this.maximumLevels[skill.ordinal()];
	}

	@Override
	public int getTotalLevel() {
		int totalLevel = 0;
		int[] realLevels = getRealSkillLevels();
		int lastSkillIdx = Skill.FORTUNE.ordinal();
		for (int i = 0; i < realLevels.length; i++) {
			if (i <= lastSkillIdx)
				totalLevel += realLevels[i];
		}
		return totalLevel;
	}

	@Override
	public MessageNode addChatMessage(ChatMessageType type, String name, String message, String sender) {
		return addChatMessage(type, name, message, sender, false);
	}

	@Override
	public MessageNode addChatMessage(ChatMessageType type, String name, String message, String sender,
									  boolean postEvent) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.type = type.getType();
		chatMessage.setName(name);
		chatMessage.setValue(message);
		chatMessage.setSender(sender);
		chatMessage = pushMessage(chatMessage);
		if(postEvent) {
			net.runelite.api.events.ChatMessage chatMessageEvent = new net.runelite.api.events.ChatMessage();
			chatMessageEvent.setType(chatMessage.getType());
			chatMessageEvent.setMessage(chatMessage.getValue());
			chatMessageEvent.setMessageNode(chatMessage);
			chatMessageEvent.setName(chatMessage.getName());
			chatMessageEvent.setTimestamp(chatMessage.getTimestamp());
			chatMessageEvent.setSender(chatMessage.getSender());
			callbacks.post(chatMessageEvent);
		}
		return chatMessage;
	}
	public int gameState = -1;
	@Override
	public GameState getGameState() {
		return GameState.of(gameState);
	}

	@Override
	public CinematicState getCinematicState() {
		return null;
	}

	@Override
	public int getRSGameState() {
		return gameState;
	}

	@Override
	public void setRSGameState(int state) {
		gameState = state;
	}

	@Override
	public void setCheckClick(boolean checkClick) {
		scene.clicked = checkClick;
	}

	@Override
	public void setMouseCanvasHoverPositionX(int x) {
		MouseHandler.mouseX = x;
	}

	@Override
	public void setMouseCanvasHoverPositionY(int y) {
		MouseHandler.mouseY = y;
	}

	@Override
	public void setGameState(GameState state) {
		assert this.isClientThread() : "setGameState must be called on client thread";
		gameState = state.getState();
		GameStateChanged event = new GameStateChanged();
		event.setGameState(state);
		if(callbacks != null) {
			callbacks.post(event);
		}
	}

	@Override
	public void setCinematicState(CinematicState gameState) {

	}

	@Override
	public void setGameState(int gameState) {
		loadingStage = gameState;
	}

	@Override
	public void stopNow() {
	}

	@Override
	public void setUsername(String name) {
		myUsername = name;
	}

	@Override
	public void setOtp(String otp) {

	}

	@Override
	public int getCurrentLoginField() {
		return 0;
	}

	@Override
	public int getLoginIndex() {
		return 0;
	}

	@Override
	public AccountType getAccountType() {
		return AccountType.NORMAL;
	}

	@Override
	public int getFPS() {
		return fps;
	}

	@Override
	public int getCameraX() {
		return this.xCameraPos;
	}

	@Override
	public int getCameraY() {
		return this.yCameraPos;
	}

	@Override
	public int getCameraZ() {
		return this.zCameraPos;
	}

	@Override
	public int getCameraPitch() {
		return yCameraCurve;
	}

	@Override
	public void setCameraPitch(int cameraPitch) {
		yCameraCurve = cameraPitch;
	}

	@Override
	public int getCameraYaw() {
		return xCameraCurve;
	}

	@Override
	public int getWorld() {
		return 1;
	}

	@Override
	public int getCanvasHeight() {
		return canvasHeight;
	}

	@Override
	public int getCanvasWidth() {
		return canvasWidth;
	}

	@Override
	public int getViewportHeight() {
		return !isResized() ? 334 : canvasHeight;
	}

	@Override
	public int getViewportWidth() {
		return !isResized() ? 512 : canvasWidth;
	}

	@Override
	public int getViewportXOffset() {
		return !isResized() ? 4 : 0;
	}

	@Override
	public int getViewportYOffset() {
		return !isResized() ? 4 : 0;
	}

	@Override
	public int getScale() {
		return Rasterizer3D.fieldOfView;
	}

	@Override
	public net.runelite.api.Point getMouseCanvasPosition() {
		return new net.runelite.api.Point(MouseHandler.mouseX, MouseHandler.mouseY);
	}

	@Override
	public int[][][] getTileHeights() {
		return tileHeights;
	}

	@Override
	public byte[][][] getTileSettings() {
		return tileFlags;
	}

	@Override
	public int getPlane() {
		return plane;
	}

	@Override
	public SceneGraph getScene() {
		return scene;
	}

	@Override
	public RSPlayer getLocalPlayer() {
		return localPlayer;
	}

	@Override
	public int getLocalPlayerIndex() {
		return 0;
	}

	@Override
	public int getNpcIndexesCount() {
		return 0;
	}

	@Override
	public int[] getNpcIndices() {
		return new int[0];
	}

	@Override
	public ItemComposition getItemComposition(int id) {
		return (ItemComposition) ItemDefinition.lookup(id);
	}

	@Override
	public ItemComposition getItemDefinition(int id) {
		return (ItemComposition) ItemDefinition.lookup(id);
	}

	@Override
	public RSSpritePixels createItemSprite(int itemId, int quantity, int border, int shadowColor, int stackable,
										   boolean noted, int scale) {
		assert isClientThread() : "createItemSprite must be called on client thread";
		return ItemDefinition.getSprite(itemId, quantity,scale);
	}

	public Sprite getOldSchoolSprite(int spriteId) {
		return (Sprite) spriteOverrides.getOrDefault(spriteId, OSRSCacheLoader.getOsrsSprite().getSprite(spriteId));
	}
	@Override
	public RSSpritePixels getSprites(IndexDataBase source, int archiveId, int fileId) {
		return (RSSpritePixels) spriteOverrides.getOrDefault(archiveId, OSRSCacheLoader.getOsrsSprite().getSprite(archiveId));
	}

	@Override
	public RSArchive getIndexSprites() {
		return null;
	}

	@Override
	public RSArchive getIndexScripts() {
		return null;
	}

	@Override
	public RSArchive getIndexConfig() {
		return null;
	}

	@Override
	public RSArchive getMusicTracks() {
		return null;
	}

	@Override
	public int getBaseX() {
		return baseX;
	}

	@Override
	public int getBaseY() {
		return baseY;
	}

	@Override
	public int getMouseCurrentButton() {
		return 0;
	}

	@Override
	public int getSelectedSceneTileX() {
		return scene.clickedTileX;
	}

	@Override
	public void setSelectedSceneTileX(int selectedSceneTileX) {
		scene.clickedTileX = selectedSceneTileX;
	}

	@Override
	public int getSelectedSceneTileY() {
		return scene.clickedTileY;
	}

	@Override
	public void setSelectedSceneTileY(int selectedSceneTileY) {
		scene.clickedTileY = selectedSceneTileY;
	}

	@Override
	public Tile getSelectedSceneTile() {
		int tileX = scene.hoverX;
		int tileY = scene.hoverY;

		if (tileX == -1 || tileY == -1)
		{
			return null;
		}

		return getScene().getTiles()[getPlane()][tileX][tileY];

	}

	@Override
	public boolean isDraggingWidget() {
		return false;
	}

	@Override
	public RSWidget getDraggedWidget() {
		return null;
	}

	@Override
	public RSWidget getDraggedOnWidget() {
		return null;
	}

	@Override
	public void setDraggedOnWidget(net.runelite.api.widgets.Widget widget) {
	}

	@Override
	public RSWidget[][] getWidgets() {
		return new RSWidget[0][];
	}

	@Override
	public RSWidget[] getGroup(int groupId) {
		return new RSWidget[0];
	}

	@Override
	public int getTopLevelInterfaceId() {
		return openInterfaceID;
	}

	@Override
	public RSWidget[] getWidgetRoots() {
		return null;
	}

	@Override
	public RSWidget getWidget(WidgetInfo widget) {
		int groupId = widget.getGroupId();
		int childId = widget.getChildId();

		return getWidget(groupId, childId);
	}

	@Override
	public RSWidget getWidget(int groupId, int childId) {
		return getWidget(groupId);
	}

	@Override
	public RSWidget getWidget(int packedID) {
		if(RSInterface.interfaceCache == null)
			return null;
		if(packedID == 100037)
			return chatboxInterface;
		return RSInterface.interfaceCache[packedID];
	}

	@Override
	public int[] getWidgetPositionsX() {
		return null;
	}

	@Override
	public int[] getWidgetPositionsY() {
		return null;
	}

	@Override
	public boolean isMouseCam() {
		return false;
	}

	@Override
	public int getCamAngleDX() {
		return anInt1187;
	}

	@Override
	public void setCamAngleDX(int angle) {
		anInt1187 = angle;
	}

	@Override
	public int getCamAngleDY() {
		return anInt1186;
	}

	@Override
	public void setCamAngleDY(int angle) {
		anInt1186 = angle;
	}

	@Override
	public RSWidget createWidget() {
		return new RSInterface();
	}

	@Override
	public void revalidateWidget(net.runelite.api.widgets.Widget w) {

	}

	@Override
	public void revalidateWidgetScroll(net.runelite.api.widgets.Widget[] group, net.runelite.api.widgets.Widget w, boolean postEvent) {

	}

	@Override
	public int getEntitiesAtMouseCount() {
		return 0;
	}

	@Override
	public void setEntitiesAtMouseCount(int i) {

	}

	@Override
	public long[] getEntitiesAtMouse() {
		return new long[0];
	}

	@Override
	public int getViewportMouseX() {
		return 0;
	}

	@Override
	public int getViewportMouseY() {
		return 0;
	}

	@Override
	public void setMenuOpen(boolean open) {
		this.menuOpen = open;
	}

	@Override
	public int getMenuScroll() {
		return menuScroll;
	}

	@Override
	public void setMenuScroll(int scroll) {
		this.menuScroll = scroll;
	}

	@Override
	public int getSubmenuX() {
		return submenuX;
	}

	@Override
	public net.runelite.api.MenuEntry getClickedMenuEntry() {
		net.runelite.api.MenuEntry toRet = null;
		if (clickedIdx != -1)
		{
			toRet = menuManager.getMenuEntries()[clickedIdx];
			clickedIdx = -1;
		}
		return toRet;
	}

	@Override
	public int getSubmenuY() {
		return submenuY;
	}

	@Override
	public int getSubmenuWidth() {
		return submenuWidth;
	}

	@Override
	public int getSubmenuHeight() {
		return submenuHeight;
	}

	@Override
	public int getSubmenuScroll() {
		return submenuScroll;
	}

	@Override
	public int getSubmenuIdx() {
		return submenuIdx;
	}

	@Override
	public void setSubmenuX(int x) {
		submenuX = x;
	}

	@Override
	public void setSubmenuY(int y) {
		submenuY = y;
	}

	@Override
	public void setSubmenuWidth(int w) {
		submenuWidth = w;
	}

	@Override
	public void setSubmenuHeight(int h) {
		submenuHeight = h;
	}

	@Override
	public void setSubmenuScroll(int s) {
		submenuScroll = s;
	}

	@Override
	public void setSubmenuScrollMax(int max) {
		submenuScrollMax = max;
	}

	@Override
	public void setSubmenuIdx(int idx) {
		submenuIdx = idx;
	}

	@Override
	public void setMenuX(int x) {
		menuOffsetX = x;
	}

	@Override
	public void setMenuY(int y) {
		menuOffsetY = y;
	}

	@Override
	public void setMenuHeight(int h) {
		menuHeight = h;
	}

	@Override
	public void setMenuWidth(int w) {
		menuWidth = w;
	}

	@Override
	public int getEnergy() {
		return Integer.parseInt(RSInterface.interfaceCache[22539].message.replaceAll("%", ""));
	}

	@Override
	public int getWeight() {
		return 0;
	}

	@Override
	public String[] getPlayerOptions() {
		return atPlayerActions;
	}

	@Override
	public boolean[] getPlayerOptionsPriorities() {
		return atPlayerArray;
	}

	@Override
	public int[] getPlayerMenuTypes() {
		return atPlayerOptions;
	}

	@Override
	public int getMouseX() {
		return MouseHandler.mouseX;
	}

	@Override
	public int getMouseY() {
		return MouseHandler.mouseY;
	}

	@Override
	public int getMouseX2() {
		return scene.clickScreenX;
	}

	@Override
	public int getMouseY2() {
		return scene.clickScreenY;
	}

	@Override
	public boolean containsBounds(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		return scene.inBounds(var0, var1, var2, var3, var4, var5, var6, var7);
	}

	@Override
	public boolean isCheckClick() {
		return SceneGraph.clicked;
	}

	@Override
	public RSWorld[] getWorldList() {
		return null;
	}
	public void sortMenuEntries(int left, int right)
	{
		MenuEntry entry = menuManager.getMenuEntry(left);
		menuManager.getMenuEntries()[left] = menuManager.getMenuEntries()[right];
		menuManager.getMenuEntries()[right] = entry;
		menuManager.getMenuEntries()[left].setIdx(left);
		menuManager.getMenuEntries()[right].setIdx(left);
	}
	@Override
	public net.runelite.api.MenuEntry createMenuEntry(int idx) {
		if (idx < 0) {
			idx = getMenuOptionCount();
			if (idx < 0) {
				idx = getMenuOptionCount() + 1;
				//throw new IllegalArgumentException();
			}
		}

		MenuEntry entry = new MenuEntry(idx);
		menuManager.addEntry(entry);
		setMenuOptionCount(idx + 1);
		return menuManager.getMenuEntry(idx);
	}

	@Override
	public net.runelite.api.MenuEntry createMenuEntry(String option, String target, int identifier, int opcode, int param1, int param2, boolean forceLeftClick)
	{
		MenuEntry menuEntry = new MenuEntry();

		menuEntry.setOption(option);
		menuEntry.setTarget(target);
		menuEntry.setIdentifier(identifier);
		menuEntry.setType(MenuAction.of(opcode));
		menuEntry.setParam0(param1);
		menuEntry.setParam1(param2);
		menuEntry.setConsumer(null);
		menuEntry.setForceLeftClick(forceLeftClick);


		return menuEntry;
	}


	@Override
	public void addRSChatMessage(int type, String name, String message, String sender) {

	}

	@Override
	public RSObjectComposition getRSObjectComposition(int objectId) {
		return null;
	}

	@Override
	public RSNPCComposition getRSNpcComposition(int npcId) {
		return null;
	}



	@Override
	public net.runelite.api.MenuEntry[] getMenuEntries()
	{
		RSRuneLiteMenuEntry[] menuEntries = Arrays.copyOf(menuManager.getMenuEntries(), getMenuOptionCount());
		int idx = 0;
		for (RSRuneLiteMenuEntry menuEntry : menuEntries)
		{
			if(idx >= 500)
				break;
			if(menuEntry == null) {
				menuEntry = menuManager.getMenuEntries()[idx];
			}
			if (menuEntry.getOption() == null)
			{
				menuEntry.setOption("null");
			}
			idx++;
		}

		return menuEntries;
	}

	@Override
	public int getMenuOptionCount() {
		return Math.min(499, menuActionRow);
	}

	@Override
	public void setMenuEntries(net.runelite.api.MenuEntry[] entries) {
		menuManager.reset();
		for (int i = 0; i < entries.length; ++i) {
			net.runelite.api.MenuEntry menuEntry = entries[i];
			((MenuEntry) menuEntry).setIdx(i);
			if(menuEntry.getOption().equals("null"))
				menuEntry.setOption(null);
			if(menuEntry.getTarget().equals("null"))
				menuEntry.setTarget(null);
			menuManager.addEntry((MenuEntry) menuEntry);
			setMenuOptionCount(getMenuOptionCount() + 1);
			callbacks.post(new MenuEntryAdded(menuEntry));
		}
	}

	@Override
	public void setMenuOptionCount(int count) {
		this.menuActionRow = Math.min(499, count);
	}

	@Override
	public String[] getMenuOptions() {
		String[] menuActionOption = new String[500];
		final int[] idx = {0};
		Arrays.stream(menuManager.getMenuEntries()).forEach(entry -> {
			if(entry == null)
				return;
			menuActionOption[idx[0]] = entry.getOption();
			idx[0]++;
		});
		return menuActionOption;
	}

	@Override
	public String[] getMenuTargets() {
		String[] menuActionTarget = new String[500];
		final int[] idx = {0};
		Arrays.stream(menuManager.getMenuEntries()).forEach(entry -> {
			if(entry == null)
				return;
			menuActionTarget[idx[0]] = entry.getTarget();
			idx[0]++;
		});
		return menuActionTarget;
	}

	@Override
	public long[] getMenuIdentifiers() {
		long[] menuAction = new long[500];
		final int[] idx = {0};
		Arrays.stream(menuManager.getMenuEntries()).forEach(entry -> {
			if(entry == null)
				return;
			menuAction[idx[0]] = entry.getIdentifier();
			idx[0]++;
		});
		return menuAction;
	}

	@Override
	public int[] getMenuOpcodes() {
		int[] menuAction = new int[500];
		final int[] idx = {0};
		Arrays.stream(menuManager.getMenuEntries()).forEach(entry -> {
			if(entry == null)
				return;
			menuAction[idx[0]] = entry.getOpcode();
			idx[0]++;
		});
		return menuAction;
	}

	@Override
	public int[] getMenuArguments1() {
		int[] menuAction = new int[500];
		final int[] idx = {0};
		Arrays.stream(menuManager.getMenuEntries()).forEach(entry -> {
			if(entry == null)
				return;
			menuAction[idx[0]] = entry.getParam0();
			idx[0]++;
		});
		return menuAction;
	}

	@Override
	public int[] getMenuArguments2() {
		int[] menuAction = new int[500];
		final int[] idx = {0};
		Arrays.stream(menuManager.getMenuEntries()).forEach(entry -> {
			if(entry == null)
				return;
			menuAction[idx[0]] = entry.getParam1();
			idx[0]++;
		});
		return menuAction;
	}

	@Override
	public boolean[] getMenuForceLeftClick() {
		boolean[] menuAction = new boolean[500];
		final int[] idx = {0};
		Arrays.stream(menuManager.getMenuEntries()).forEach(entry -> {
			if(entry == null)
				return;
			menuAction[idx[0]] = entry.isForceLeftClick();
			idx[0]++;
		});
		return menuAction;
	}

	@Override
	public boolean isMenuOpen() {
		return menuOpen;
	}

	@Override
	public int getMenuX() {
		return menuOffsetX;
	}

	@Override
	public int getMenuY() {
		return menuOffsetY;
	}

	@Override
	public int getMenuHeight() {
		return menuHeight;
	}

	@Override
	public int getMenuWidth() {
		return menuWidth;
	}

	@Override
	public net.runelite.rs.api.RSFont getFontBold12() {
		return newBoldFont;
	}

	@Override
	public void rasterizerDrawHorizontalLine(int x, int y, int w, int rgb) {

	}

	@Override
	public void rasterizerDrawHorizontalLineAlpha(int x, int y, int w, int rgb, int a) {

	}

	@Override
	public void rasterizerDrawVerticalLine(int x, int y, int h, int rgb) {

	}

	@Override
	public void rasterizerDrawVerticalLineAlpha(int x, int y, int h, int rgb, int a) {

	}

	@Override
	public void rasterizerDrawGradient(int x, int y, int w, int h, int rgbTop, int rgbBottom) {

	}

	@Override
	public void rasterizerDrawGradientAlpha(int x, int y, int w, int h, int rgbTop, int rgbBottom, int alphaTop, int alphaBottom) {

	}

	@Override
	public void rasterizerFillRectangleAlpha(int x, int y, int w, int h, int rgb, int a) {
		Rasterizer2D.drawTransparentBox(x,y,w,h,rgb,a);
	}

	@Override
	public void rasterizerDrawRectangle(int x, int y, int w, int h, int rgb) {
		Rasterizer2D.drawRectangle(x, y, w, h, rgb);
	}

	@Override
	public void rasterizerDrawRectangleAlpha(int x, int y, int w, int h, int rgb, int a) {

	}

	@Override
	public void rasterizerDrawCircle(int x, int y, int r, int rgb) {

	}

	@Override
	public void rasterizerDrawCircleAlpha(int x, int y, int r, int rgb, int a) {

	}

	@Override
	public RSEvictingDualNodeHashTable getHealthBarCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getHealthBarSpriteCache() {
		return null;
	}

	@Override
	public int getMapAngle() {
		return 0;
	}

	@Override
	public void setCameraYawTarget(int cameraYawTarget) {

	}

	public void setResized(boolean resized) {
		this.resized = resized;
		resizeGame();
	}

	private boolean resized = false;

	@Override
	public boolean isResized() {
		return resized;
	}

	@Override
	public int getRevision() {
		return 1;
	}

	@Override
	public int[] getMapRegions() {
		if(resourceProvider == null)
			return null;
		return resourceProvider.getMapIndices1();
	}

	@Override
	public int[][][] getInstanceTemplateChunks() {
		return constructRegionData;
	}

	@Override
	public int[][] getXteaKeys() {
		return null;
	}

	@Override
	public int getCycleCntr() {
		return 0;
	}

	@Override
	public void setChatCycle(int value) {

	}

	@Override
	public int[] getVarps() {
		return variousSettings;
	}

	@Override
	public RSVarcs getVarcs() {
		return null;
	}

	@Override
	public Map<Integer, Object> getVarcMap() {
		return null;
	}

	@Override
	public int getVar(VarPlayer varPlayer) {
		return 0;
	}

	@Override
	public int getVar(Varbits varbit) {
		return 1;
	}

	@Override
	public int getVar(VarClientInt varClientInt) {
		return 0;
	}

	public HashMap<VarClientStr, String> Vars = new HashMap<>();

	@Override
	public String getVar(VarClientStr varClientStr) {
		if (Vars.containsKey(varClientStr)) {
			return Vars.get(varClientStr);
		}
		return null;
	}

	@Override
	public int getVarbitValue(int varbitId) {
		return variousSettings[varbitId];
	}

	@Override
	public int getVarcIntValue(int varcIntId) {
		return 0;
	}

	@Override
	public String getVarcStrValue(int varcStrId) {
		return null;
	}

	@Override
	public void setVar(VarClientStr varClientStr, String value) {
		if (Vars.containsKey(varClientStr)) {
			Vars.replace(varClientStr, Vars.get(varClientStr), value);
		} else {
			Vars.put(varClientStr, value);
		}
	}

	@Override
	public void setVar(VarClientInt varClientStr, int value) {
	}

	@Override
	public void setVarbit(Varbits varbit, int value) {
	}

	@Override
	public VarbitComposition getVarbit(int id) {
		return null;
	}

	@Override
	public int getVarbitValue(int[] varps, int varbitId) {
		return 0;
	}

	@Override
	public int getVarpValue(int[] varps, int varpId) {
		return 0;
	}

	@Override
	public int getVarpValue(int i) {
		return variousSettings[i];
	}

	@Override
	public void setVarbitValue(int[] varps, int varbit, int value) {
	}

	@Override
	public void queueChangedVarp(int varp) {
	}

	@Override
	public RSNodeHashTable getWidgetFlags() {
		return null;
	}

	@Override
	public RSNodeHashTable getComponentTable() {
		return null;
	}

	@Override
	public RSGrandExchangeOffer[] getGrandExchangeOffers() {
		return null;
	}

	@Override
	public boolean isPrayerActive(Prayer prayer) {
		return false;
	}

	@Override
	public int getSkillExperience(net.runelite.api.Skill skill) {
		return 1;
	}

	@Override
	public long getOverallExperience() {
		return 1;
	}

	@Override
	public void refreshChat() {
	}

	@Override
	public Map<Integer, ChatLineBuffer> getChatLineMap() {
		return null;
	}

	@Override
	public Widget getChatboxInterface() {
		if(messagePromptRaised || inputDialogState != 0)
			return RSInterface.interfaceCache[968];
		if(backDialogID != -1) {
			return RSInterface.interfaceCache[backDialogID];
		}
		if(dialogID != -1) {
			return RSInterface.interfaceCache[dialogID];
		}
		return null;
	}

	@Override
	public List getMessages() {
		return Arrays.asList(chatMessages);
	}

	@Override
	public ObjectComposition getObjectDefinition(int objectId) {
		return ObjectDefinition.lookup(objectId);
	}

	@Override
	public NPCComposition getNpcDefinition(int npcId) {
		return NpcDefinition.lookup(npcId);
	}

	@Override
	public StructComposition getStructComposition(int structID) {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getStructCompositionCache() {
		return null;
	}

	@Override
	public RSWorldMapElement[] getMapElementConfigs() {
		return null;
	}

	@Override
	public RSIndexedSprite[] getMapScene() {
		return null;
	}


	@Override
	public RSSpritePixels[] getMapDots() {
		return mapDots;
	}

	@Override
	public int getGameCycle() {
		return loopCycle;
	}

	@Override
	public RSSpritePixels[] getMapIcons() {
		return null;
	}

	@Override
	public RSIndexedSprite[] getModIcons() {
		return null;
	}

	@Override
	public void setRSModIcons(RSIndexedSprite[] modIcons) {

	}

	@Override
	public void setModIcons(IndexedSprite[] modIcons) {

	}

	@Override
	public RSIndexedSprite createIndexedSprite() {
		return null;
	}

	@Override
	public RSSpritePixels createSpritePixels(int[] pixels, int width, int height) {
		Sprite sprite = new Sprite(pixels, width, height);
		return sprite;
	}

	@Override
	public int getDestinationX() {
		return 0;
	}

	@Override
	public int getDestinationY() {
		return 0;
	}

	@Override
	public RSSoundEffect[] getAudioEffects() {
		return new RSSoundEffect[0];
	}

	@Override
	public int[] getQueuedSoundEffectIDs() {
		return new int[0];
	}

	@Override
	public int[] getSoundLocations() {
		return new int[0];
	}

	@Override
	public int[] getQueuedSoundEffectLoops() {
		return new int[0];
	}

	@Override
	public int[] getQueuedSoundEffectDelays() {
		return new int[0];
	}

	@Override
	public int getQueuedSoundEffectCount() {
		return 0;
	}

	@Override
	public void setQueuedSoundEffectCount(int queuedSoundEffectCount) {

	}

	@Override
	public void queueSoundEffect(int id, int numLoops, int delay) {

	}

	@Override
	public LocalPoint getLocalDestinationLocation() {
		int tileX = this.destX;
		int tileY = this.destY;
		if (tileX <= -1 || tileY <= -1)
			return null;
		if(getPlane() > 3 || tileX > 103 || tileY > 103)
			return null;
		RSTile rSTile = getScene().getTiles()[getPlane()][tileX][tileY];
		return (rSTile == null) ? null : rSTile.getLocalLocation();
	}

	@Override
	public List<net.runelite.api.Projectile> getProjectiles() {
		List<net.runelite.api.Projectile> projectileList = new ArrayList<>();
		for (net.runelite.api.Projectile projectile = (net.runelite.api.Projectile) projectiles
				.reverseGetFirst(); projectile != null; projectile = (net.runelite.api.Projectile) projectiles.reverseGetNext()) {
			projectileList.add(projectile);
		}
		return projectileList;
	}

	@Override
	public List<GraphicsObject> getGraphicsObjects() {
		List<net.runelite.api.GraphicsObject> list = new ArrayList<>();//oh you nub
		for (GraphicsObject projectile = (GraphicsObject) incompleteAnimables
				.reverseGetFirst(); projectile != null; projectile = (GraphicsObject) incompleteAnimables.reverseGetNext()) {
			list.add(projectile);
		}
		return list;
	}

	public RuneLiteObject createRuneLiteObject() {
		int x = this.currentRegionX;
		int y = this.currentRegionY;
		x = x * 128 + 64;
		y = y * 128 + 64;
		RuneLiteObjectImpl runeLiteObject = new RuneLiteObjectImpl(this.plane, loopCycle, 0, 369, getCenterHeight(this.plane, y, x), y, x);
		getCallbacks().post(new GraphicsObjectCreated(runeLiteObject));
		this.incompleteAnimables.insertHead(runeLiteObject);
		updateGraphicObjects();
		return (RuneLiteObject)runeLiteObject;
	}
	public ReferenceCache tmpModelDataCache = new ReferenceCache(64);
	public Model loadModel(int id) {
		return Model.getModel(id);
	}

	public Model loadModel(int id, short[] colorToFind, short[] colorToReplace) {
		Model model = Model.getModel(id);
		if (model == null)
			return null;
		if(colorToFind != null) {
			for (int i = 0; i < colorToFind.length; i++) {
				if (colorToFind[0] != 0)
					model.recolor(colorToFind[i], colorToReplace[i]);
			}
		}
		return (Model)model;
	}

	@Override
	public net.runelite.api.Animation loadAnimation(int id) {
		SeqDefinition animation = SeqDefinition.get(id);
		if(animation == null)
			System.out.println("loadAnimation failed for " + id);
		return SeqDefinition.get(id);
	}

	@Override
	public int getMusicVolume() {
		return 0;
	}

	@Override
	public void setMusicVolume(int volume) {
	}

	@Override
	public void playSoundEffect(int id) {

	}

	@Override
	public void playSoundEffect(int id, int x, int y, int range) {
	}

	@Override
	public void playSoundEffect(int id, int x, int y, int range, int delay) {
	}

	@Override
	public void playSoundEffect(int id, int volume) {

	}

	@Override
	public RSAbstractRasterProvider getBufferProvider() {
		return rasterProvider;
	}

	@Override
	public int getMouseIdleTicks() {
		return MouseHandler.idleCycles;
	}

	@Override
	public long getMouseLastPressedMillis() {
		return MouseHandler.lastPressed;
	}

	public long getClientMouseLastPressedMillis() {
		return MouseHandler.lastPressed;
	}

	public void setClientMouseLastPressedMillis(long mills) {
		MouseHandler.lastPressed = mills;
	}

	@Override
	public int getKeyboardIdleTicks() {
		return KeyHandler.idleCycles;
	}

	@Override
	public void changeMemoryMode(boolean lowMemory) {
		setLowMemory(lowMemory);
		setSceneLowMemory(lowMemory);
		setAudioHighMemory(true);
		setObjectDefinitionLowDetail(lowMemory);
		if (getGameState() == GameState.LOGGED_IN)
		{
			setGameState(1);
		}
	}

	public HashMap<Integer, ItemContainer> containers = new HashMap<Integer, ItemContainer>();

	@Override
	public ItemContainer getItemContainer(InventoryID inventory) {
		return getItemContainer(inventory.getId());
	}

	@Override
	public ItemContainer getItemContainer(int id) {
		RSInterface widget = RSInterface.get(id);
		List<net.runelite.api.Item> items = new ArrayList<>();
		for(int i = 0; i < widget.getItemIds().length; i++) {
			try {
				items.add(new net.runelite.api.Item(widget.getItemIds()[i]-1, widget.getItemQuantities()[i]));
			} catch (Exception e) {
				System.out.println("Caught error adding item container Item");
			}
		}
		return new ItemContainer() {
			@Override
			public int getId() {
				return id;
			}

			@Override
			public net.runelite.api.Item[] getItems() {
				return items.toArray(new net.runelite.api.Item[0]);
			}

			@Override
			public net.runelite.api.Item getItem(int slot) {
				if(slot >= widget.getItemIds().length)
					return null;
				return new net.runelite.api.Item(widget.getItemIds()[slot], widget.getItemQuantities()[slot]);
			}

			@Override
			public boolean contains(int itemId) {
				return items.stream().filter(it -> it.getId() == itemId).count() > 0;
			}

			@Override
			public int count(int itemId) {
				int amount = 0;
				for(net.runelite.api.Item item : getItems()) {
					if(item.getId() == itemId)
						amount += item.getQuantity();
				}
				return amount;
			}

			@Override
			public int size() {
				return items.size();
			}

			@Override
			public Node getNext() {
				return null;
			}

			@Override
			public Node getPrevious() {
				return null;
			}

			@Override
			public void remove() {

			}

			@Override
			public long getHash() {
				return 0;
			}
		};
	}

	@Override
	public RSNodeHashTable getItemContainers() {
		return null;
	}

	@Override
	public RSItemComposition getRSItemDefinition(int itemId) {
		return ItemDefinition.lookup(itemId);
	}

	@Override
	public RSSpritePixels createRSItemSprite(int itemId, int quantity, int thickness, int borderColor, int stackable, boolean noted) {
		return null;
	}

	@Override
	public void sendMenuAction(int n2, int n3, int n4, int n5, String string, String string2, int n6, int n7) {

	}

	@Override
	public void decodeSprite(byte[] data) {

	}

	@Override
	public int getIndexedSpriteCount() {
		return 0;
	}

	@Override
	public int getIndexedSpriteWidth() {
		return 0;
	}

	@Override
	public int getIndexedSpriteHeight() {
		return 0;
	}

	@Override
	public int[] getIndexedSpriteOffsetXs() {
		return new int[0];
	}

	@Override
	public void setIndexedSpriteOffsetXs(int[] indexedSpriteOffsetXs) {

	}

	@Override
	public int[] getIndexedSpriteOffsetYs() {
		return new int[0];
	}

	@Override
	public void setIndexedSpriteOffsetYs(int[] indexedSpriteOffsetYs) {

	}

	@Override
	public int[] getIndexedSpriteWidths() {
		return new int[0];
	}

	@Override
	public void setIndexedSpriteWidths(int[] indexedSpriteWidths) {

	}

	@Override
	public int[] getIndexedSpriteHeights() {
		return new int[0];
	}

	@Override
	public void setIndexedSpriteHeights(int[] indexedSpriteHeights) {

	}

	@Override
	public byte[][] getSpritePixels() {
		return new byte[0][];
	}

	@Override
	public void setSpritePixels(byte[][] spritePixels) {

	}

	@Override
	public int[] getIndexedSpritePalette() {
		return new int[0];
	}

	@Override
	public void setIndexedSpritePalette(int[] indexedSpritePalette) {

	}

	static int[] Interpreter_arrayLengths;
	static int[][] Interpreter_arrays;

	static int Interpreter_intStackSize;
	static int[] Interpreter_intStack;
	static int[] Interpreter_intLocals;
	@Override
	public int getIntStackSize() {
		return Interpreter_intStackSize;
	}

	@Override
	public void setIntStackSize(int stackSize) {
		Interpreter_intStackSize = stackSize;
	}

	@Override
	public int[] getIntStack() {
		return Interpreter_intStack;
	}

	static String[] Interpreter_stringStack;
	static String[] Interpreter_stringLocals;
	static int Interpreter_stringStackSize;
	@Override
	public int getStringStackSize() {
		return Interpreter_stringStackSize;
	}

	@Override
	public void setStringStackSize(int stackSize) {
		this.Interpreter_stringStackSize = stackSize;
	}

	@Override
	public String[] getStringStack() {
		return Interpreter_stringStack;
	}

	public static void runScriptLogic(ScriptEvent var0, Script var1, int var2, int var3) {
		Object[] var4 = var0.args;
		Interpreter_intStackSize = 0;
		Interpreter_stringStackSize = 0;
		int var5 = -1;
		int[] var6 = var1.getInstructions();
		int[] var7 = var1.getIntOperands();
		System.out.println("Script [" + var6.length + "] [" + var7.length + "]");
		byte var8 = -1;
		boolean var9 = false;
		int var10 = 0;
		boolean var29 = false;

		label934: {
			label935: {
				try {
					int var13;
					try {
						var29 = true;
						Interpreter_intLocals = new int[var1.getIntStackCount()];
						int var11 = 0;
						Interpreter_stringLocals = new String[var1.getStringStackCount()];
						int var12 = 0;

						int var14;
						String var20;
						for (var13 = 1; var13 < var4.length; ++var13) {
							if (var4[var13] instanceof Integer) {
								var14 = (Integer)var4[var13];
								if (var14 == -2147483647) {
									var14 = var0.mouseX;
								}

								if (var14 == -2147483646) {
									var14 = var0.mouseY;
								}

								if (var14 == -2147483645) {
									var14 = var0.widget != null ? var0.widget.id : -1;
								}

								if (var14 == -2147483644) {
									var14 = var0.opIndex;
								}

								if (var14 == -2147483643) {
									var14 = var0.widget != null ? var0.widget.getChildIndex() : -1;
								}

								if (var14 == -2147483642) {
									var14 = var0.dragTarget != null ? var0.dragTarget.id : -1;
								}

								if (var14 == -2147483641) {
									var14 = var0.dragTarget != null ? var0.dragTarget.getChildIndex() : -1;
								}

								if (var14 == -2147483640) {
									var14 = var0.keyTyped;
								}

								if (var14 == -2147483639) {
									var14 = var0.keyPressed;
								}

								Interpreter_intLocals[var11++] = var14;
							} else if (var4[var13] instanceof String) {
								var20 = (String)var4[var13];
								if (var20.equals("event_opbase")) {
									var20 = var0.targetName;
								}

								Interpreter_stringLocals[var12++] = var20;
							}
						}
						while (true) {
							++var10;
							if (var10 > var2) {
								throw new RuntimeException();
							}

							++var5;
							int var32 = var6[var5];
							System.out.println("Var32=" + var32);
							if (var32 >= 100) {
								boolean var34;
								if (var1.getIntOperands()[var5] == 1) {
									var34 = true;
								} else {
									var34 = false;
								}

								var14 = ScriptMethods.method3186(var32, var1, var34);
								switch(var14) {
									case 0:
										var29 = false;
										break label934;
									case 1:
									default:
										break;
									case 2:
										throw new IllegalStateException();
								}
							} else if (var32 == 0) {
								Interpreter_intStack[++Interpreter_intStackSize - 1] = var7[var5];
							} else if (var32 == 1) {
								var13 = var7[var5];
								Interpreter_intStack[++Interpreter_intStackSize - 1] = instance.variousSettings[var13];
							} else if (var32 == 2) {
								var13 = var7[var5];
								instance.variousSettings[var13] = Interpreter_intStack[--Interpreter_intStackSize];
								//class11.changeGameOptions(var13);
							} else if (var32 == 3) {
								Interpreter_stringStack[++Interpreter_stringStackSize - 1] = var1.getStringOperands()[var5];
							} else if (var32 == 6) {
								var5 += var7[var5];
							} else if (var32 == 7) {
								Interpreter_intStackSize -= 2;
								if (Interpreter_intStack[Interpreter_intStackSize] != Interpreter_intStack[Interpreter_intStackSize + 1]) {
									var5 += var7[var5];
								}
							} else if (var32 == 8) {
								Interpreter_intStackSize -= 2;
								if (Interpreter_intStack[Interpreter_intStackSize] == Interpreter_intStack[Interpreter_intStackSize + 1]) {
									var5 += var7[var5];
								}
							} else if (var32 == 9) {
								Interpreter_intStackSize -= 2;
								if (Interpreter_intStack[Interpreter_intStackSize] < Interpreter_intStack[Interpreter_intStackSize + 1]) {
									var5 += var7[var5];
								}
							} else if (var32 == 10) {
								Interpreter_intStackSize -= 2;
								if (Interpreter_intStack[Interpreter_intStackSize] > Interpreter_intStack[Interpreter_intStackSize + 1]) {
									var5 += var7[var5];
								}
							} else if (var32 == 21) {
								if (Interpreter_frameDepth == 0) {
									var29 = false;
									break;
								}

								ScriptFrame var39 = Interpreter_frames[--Interpreter_frameDepth];
								var1 = var39.script;
								var6 = var1.getInstructions();
								var7 = var1.getIntOperands();
								var5 = var39.pc;
								Interpreter_intLocals = var39.intLocals;
								Interpreter_stringLocals = var39.stringLocals;
							} else if (var32 == 25) {
								var13 = var7[var5];
								Interpreter_intStack[++Interpreter_intStackSize - 1] = getInstance().getVarbitValue(var13);
							} else if (var32 == 27) {
								var13 = var7[var5];
								instance.variousSettings[var13] = Interpreter_intStack[--Interpreter_intStackSize];
							} else if (var32 == 31) {
								Interpreter_intStackSize -= 2;
								if (Interpreter_intStack[Interpreter_intStackSize] <= Interpreter_intStack[Interpreter_intStackSize + 1]) {
									var5 += var7[var5];
								}
							} else if (var32 == 32) {
								Interpreter_intStackSize -= 2;
								if (Interpreter_intStack[Interpreter_intStackSize] >= Interpreter_intStack[Interpreter_intStackSize + 1]) {
									var5 += var7[var5];
								}
							} else if (var32 == 33) {
								Interpreter_intStack[++Interpreter_intStackSize - 1] = Interpreter_intLocals[var7[var5]];
							} else if (var32 == 34) {
								Interpreter_intLocals[var7[var5]] = Interpreter_intStack[--Interpreter_intStackSize];
							} else if (var32 == 35) {
								System.out.println("Got text [" + Interpreter_stringLocals[var7[var5]] + "]");
								Interpreter_stringStack[++Interpreter_stringStackSize - 1] = Interpreter_stringLocals[var7[var5]];
							} else if (var32 == 36) {
								Interpreter_stringLocals[var7[var5]] = Interpreter_stringStack[--Interpreter_stringStackSize];
							} else if (var32 == 37) {
								var13 = var7[var5];
								Interpreter_stringStackSize -= var13;
								var20 = method7032(Interpreter_stringStack, Interpreter_stringStackSize, var13);
								Interpreter_stringStack[++Interpreter_stringStackSize - 1] = var20;
							} else if (var32 == 38) {
								--Interpreter_intStackSize;
							} else if (var32 == 39) {
								--Interpreter_stringStackSize;
							} else {
								int var17;
								if (var32 != 40) {
									if (var32 == 42) {
										//Interpreter_intStack[++Interpreter_intStackSize - 1] = class130.varcs.getInt(var7[var5]);
									} else if (var32 == 43) {
										//class130.varcs.setInt(var7[var5], Interpreter_intStack[--Interpreter_intStackSize]);
									} else if (var32 == 44) {
										var13 = var7[var5] >> 16;
										var14 = var7[var5] & 65535;
										int var23 = Interpreter_intStack[--Interpreter_intStackSize];
										if (var23 < 0 || var23 > 5000) {
											throw new RuntimeException();
										}

										Interpreter_arrayLengths[var13] = var23;
										byte var24 = -1;
										if (var14 == 105) {
											var24 = 0;
										}

										for (var17 = 0; var17 < var23; ++var17) {
											Interpreter_arrays[var13][var17] = var24;
										}
									} else if (var32 == 45) {
										var13 = var7[var5];
										var14 = Interpreter_intStack[--Interpreter_intStackSize];
										if (var14 < 0 || var14 >= Interpreter_arrayLengths[var13]) {
											throw new RuntimeException();
										}

										Interpreter_intStack[++Interpreter_intStackSize - 1] = Interpreter_arrays[var13][var14];
									} else if (var32 == 46) {
										var13 = var7[var5];
										Interpreter_intStackSize -= 2;
										var14 = Interpreter_intStack[Interpreter_intStackSize];
										if (var14 < 0 || var14 >= Interpreter_arrayLengths[var13]) {
											throw new RuntimeException();
										}

										Interpreter_arrays[var13][var14] = Interpreter_intStack[Interpreter_intStackSize + 1];
									} else {
										/*String var21;
										if (var32 == 47) {
											//var21 = class130.varcs.getStringOld(var7[var5]);
											//if (var21 == null) {
												var21 = "null";
											//}

											Interpreter_stringStack[++Interpreter_stringStackSize - 1] = var21;
										} else if (var32 == 48) {
											//class130.varcs.setStringOld(var7[var5], Interpreter_stringStack[--Interpreter_stringStackSize]);
										} else if (var32 == 49) {
											//var21 = class130.varcs.getString(var7[var5]);
											//Interpreter_stringStack[++Interpreter_stringStackSize - 1] = var21;
										} else if (var32 == 50) {
											//class130.varcs.setString(var7[var5], Interpreter_stringStack[--Interpreter_stringStackSize]);
										} else if (var32 == 60) {
											IterableNodeHashTable var37 = var1.switches[var7[var5]];
											IntegerNode var36 = (IntegerNode)var37.get((long)Interpreter_intStack[--Interpreter_intStackSize]);
											if (var36 != null) {
												var5 += var36.getValue();
											}
										} else {
											Integer var38;
											if (var32 == 74) {
												var38 = HttpRequestTask.field84.getTitleGroupValue(var7[var5]);
												if (var38 == null) {
													Interpreter_intStack[++Interpreter_intStackSize - 1] = -1;
												} else {
													Interpreter_intStack[++Interpreter_intStackSize - 1] = var38;
												}
											} else {
												if (var32 != 76) {
													throw new IllegalStateException();
												}

												var38 = PendingSpawn.field1189.method8494(var7[var5]);
												if (var38 == null) {
													Interpreter_intStack[++Interpreter_intStackSize - 1] = -1;
												} else {
													Interpreter_intStack[++Interpreter_intStackSize - 1] = var38;
												}
											}
										}*/
									}
								} else {
									var13 = var7[var5];
									Script var35 = Script.scripts[var13];
									int[] var15 = new int[var35.getLocalIntCount()];
									String[] var16 = new String[var35.getLocalStringCount()];

									for (var17 = 0; var17 < var35.getIntStackCount(); ++var17) {
										var15[var17] = Interpreter_intStack[var17 + (Interpreter_intStackSize - var35.getIntStackCount())];
									}

									for (var17 = 0; var17 < var35.getStringStackCount(); ++var17) {
										var16[var17] = Interpreter_stringStack[var17 + (Interpreter_stringStackSize - var35.getStringStackCount())];
									}

									Interpreter_intStackSize -= var35.getIntStackCount();
									Interpreter_stringStackSize -= var35.getStringStackCount();
									ScriptFrame var22 = new ScriptFrame();
									var22.script = var1;
									var22.pc = var5;
									var22.intLocals = Interpreter_intLocals;
									var22.stringLocals = Interpreter_stringLocals;
									Interpreter_frames[++Interpreter_frameDepth - 1] = var22;
									var1 = var35;
									var6 = var35.getInstructions();
									var7 = var35.getIntOperands();
									var5 = -1;
									Interpreter_intLocals = var15;
									Interpreter_stringLocals = var16;
								}
							}
						}
					} catch (Exception var30) {
						var9 = true;
						StringBuilder var26 = new StringBuilder(30);
						var26.append("").append(var1.getId()).append(" ");

						for (var13 = Interpreter_frameDepth - 1; var13 >= 0; --var13) {
							var26.append("").append(Interpreter_frames[var13].script.getId()).append(" ");
						}

						var26.append("").append(var8);
						System.out.println(var26.toString());
						var29 = false;
						break label935;
					}
				} finally {
					if (var29) {
						if (!var9 && var3 > 0 && var10 >= var3) {
							System.out.println("Warning: Script " + var1.field1008 + " finished at op count " + var10 + " of max " + var2);
						}

					}
				}
			}

			if (!var9 && var3 > 0 && var10 >= var3) {
				System.out.println("Warning: Script " + var1.field1008 + " finished at op count " + var10 + " of max " + var2);
			}

			return;
		}


		if (!var9 && var3 > 0 && var10 >= var3) {
			System.out.println("Warning: Script " + var1.field1008 + " finished at op count " + var10 + " of max " + var2);
		}
	}


	static ScriptFrame[] Interpreter_frames;
	static int Interpreter_frameDepth;


	public static String method7032(CharSequence[] var0, int var1, int var2) {
		if (var2 == 0) {
			return "";
		} else if (var2 == 1) {
			CharSequence var10 = var0[var1];
			return var10 == null ? "null" : var10.toString();
		} else {
			int var3 = var2 + var1;
			int var4 = 0;

			for (int var5 = var1; var5 < var3; ++var5) {
				CharSequence var9 = var0[var5];
				if (var9 == null) {
					var4 += 4;
				} else {
					var4 += var9.length();
				}
			}

			StringBuilder var8 = new StringBuilder(var4);

			for (int var6 = var1; var6 < var3; ++var6) {
				CharSequence var7 = var0[var6];
				if (var7 == null) {
					var8.append("null");
				} else {
					var8.append(var7);
				}
			}

			return var8.toString();
		}
	}

	@Override
	public RSFriendSystem getFriendManager() {
		return null;
	}

	@Override
	public RSWidget getScriptActiveWidget() {
		return null;
	}

	@Override
	public RSWidget getScriptDotWidget() {
		return null;
	}

	@Override
	public RSScriptEvent createRSScriptEvent(Object... args) {
		return null;
	}

	@Override
	public void runScriptEvent(RSScriptEvent event) {

	}

	@Override
	public RSEvictingDualNodeHashTable getScriptCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getRSStructCompositionCache() {
		return null;
	}

	@Override
	public RSStructComposition getRSStructComposition(int id) {
		return null;
	}

	@Override
	public RSParamComposition getRSParamComposition(int id) {
		return null;
	}

	@Override
	public void setMouseLastPressedMillis(long time) {

	}

	@Override
	public int getRootWidgetCount() {
		return 0;
	}

	@Override
	public int getWidgetClickX() {
		return 0;
	}

	@Override
	public int getWidgetClickY() {
		return 0;
	}

	@Override
	public int getStaffModLevel() {
		return 0;
	}

	@Override
	public int getTradeChatMode() {
		return 0;
	}

	@Override
	public int getPublicChatMode() {
		return 0;
	}

	@Override
	public int getClientType() {
		return 0;
	}

	@Override
	public boolean isOnMobile() {
		return false;
	}

	@Override
	public boolean hadFocus() {
		return false;
	}

	@Override
	public int getMouseCrossColor() {
		return 0;
	}

	@Override
	public void setMouseCrossColor(int color) {

	}

	@Override
	public int getLeftClickOpensMenu() {
		return 0;
	}

	@Override
	public boolean getShowMouseOverText() {
		return false;
	}

	@Override
	public void setShowMouseOverText(boolean showMouseOverText) {

	}

	@Override
	public int[] getDefaultRotations() {
		return new int[0];
	}

	@Override
	public boolean getShowLoadingMessages() {
		return false;
	}

	@Override
	public void setShowLoadingMessages(boolean showLoadingMessages) {

	}

	@Override
	public void setStopTimeMs(long time) {

	}

	@Override
	public void clearLoginScreen(boolean shouldClear) {

	}

	@Override
	public void setLeftTitleSprite(SpritePixels background) {

	}

	@Override
	public void setRightTitleSprite(SpritePixels background) {

	}

	@Override
	public RSBuffer newBuffer(byte[] bytes) {
		return null;
	}

	@Override
	public RSVarbitComposition newVarbitDefinition() {
		return null;
	}

	@Override
	public boolean[] getPressedKeys() {
		return null;
	}

	public boolean lowMemoryMusic = false;
	@Override
	public void setLowMemory(boolean lowMemory) {
		this.lowMem = lowMemory;
	}

	@Override
	public void setSceneLowMemory(boolean lowMemory) {
		MapRegion.lowMem = lowMemory;
		SceneGraph.lowMem = lowMemory;
		Rasterizer3D.lowMem = lowMemory;
		((TextureProvider)Rasterizer3D.textureLoader).setTextureSize(Rasterizer3D.lowMem ? 64 : 128);

	}

	@Override
	public void setAudioHighMemory(boolean highMemory) {
		lowMemoryMusic = highMemory;
	}

	@Override
	public void setObjectDefinitionLowDetail(boolean lowDetail) {
		ObjectDefinition.lowMem = lowDetail;
	}


	@Override
	public boolean isFriended(String name, boolean mustBeLoggedIn) {
		return false;
	}

	@Override
	public RSFriendsChat getFriendsChatManager() {
		return null;
	}

	@Override
	public RSLoginType getLoginType() {
		return null;
	}

	@Override
	public RSUsername createName(String name, RSLoginType type) {
		return null;
	}

	@Override
	public int rs$getVarbit(int varbitId) {
		return 0;
	}

	@Override
	public RSEvictingDualNodeHashTable getVarbitCache() {
		return null;
	}

	@Override
	public FriendContainer getFriendContainer() {
		return null;
	}

	@Override
	public NameableContainer<Ignore> getIgnoreContainer() {
		return null;
	}

	@Override
	public RSClientPreferences getPreferences() {
		return null;
	}

	@Override
	public int getCameraPitchTarget() {
		return camAngleYY;
	}

	@Override
	public void setCameraPitchTarget(int pitch) {
		this.camAngleYY = pitch;
	}

	@Override
	public void setPitchSin(int v) {
		scene.camUpDownY = v;
	}

	@Override
	public void setPitchCos(int v) {
		scene.camUpDownX = v;
	}

	@Override
	public void setYawSin(int v) {
		scene.camLeftRightY = v;
	}

	@Override
	public void setYawCos(int v) {
		scene.camLeftRightX = v;
	}

	static int lastPitch = 128;
	static int lastPitchTarget = 128;

	@Override
	public void setCameraPitchRelaxerEnabled(boolean enabled) {

		if (pitchRelaxEnabled == enabled)
		{
			return;
		}
		pitchRelaxEnabled = enabled;
		if (!enabled)
		{
			int pitch = getCameraPitchTarget();
			if (pitch > STANDARD_PITCH_MAX)
			{
				setCameraPitchTarget(STANDARD_PITCH_MAX);
			}
		}

	}

	@Override
	public void setInvertYaw(boolean state) {
		invertYaw = state;
	}

	@Override
	public void setInvertPitch(boolean state) {
		invertPitch = state;
	}

	@Override
	public RSWorldMap getRenderOverview() {
		return null;
	}

	private static boolean stretchedEnabled;

	private static boolean stretchedFast;

	private static boolean stretchedIntegerScaling;

	private static boolean stretchedKeepAspectRatio;

	private static double scalingFactor;

	private static Dimension cachedStretchedDimensions;

	private static Dimension cachedRealDimensions;

	@Override
	public boolean isStretchedEnabled()
	{
		return stretchedEnabled;
	}

	@Override
	public void setStretchedEnabled(boolean state)
	{
		stretchedEnabled = state;
	}

	@Override
	public boolean isStretchedFast()
	{
		return stretchedFast;
	}

	@Override
	public void setStretchedFast(boolean state)
	{
		stretchedFast = state;
	}

	@Override
	public void setStretchedIntegerScaling(boolean state)
	{
		stretchedIntegerScaling = state;
	}

	@Override
	public void setStretchedKeepAspectRatio(boolean state)
	{
		stretchedKeepAspectRatio = state;
	}

	@Override
	public void setScalingFactor(int factor)
	{
		scalingFactor = 1 + (factor / 100D);
	}

	@Override
	public double getScalingFactor()
	{
		return scalingFactor;
	}

	@Override
	public Dimension getRealDimensions()
	{
		if (!isStretchedEnabled())
		{
			return getCanvas().getSize();
		}

		if (cachedRealDimensions == null)
		{
			if (isResized())
			{
				Container canvasParent = getCanvas().getParent();

				int parentWidth = canvasParent.getWidth();
				int parentHeight = canvasParent.getHeight();

				int newWidth = (int) (parentWidth / scalingFactor);
				int newHeight = (int) (parentHeight / scalingFactor);

				if (newWidth < Constants.GAME_FIXED_WIDTH || newHeight < Constants.GAME_FIXED_HEIGHT)
				{
					double scalingFactorW = (double)parentWidth / Constants.GAME_FIXED_WIDTH;
					double scalingFactorH = (double)parentHeight / Constants.GAME_FIXED_HEIGHT;
					double scalingFactor = Math.min(scalingFactorW, scalingFactorH);

					newWidth = (int) (parentWidth / scalingFactor);
					newHeight = (int) (parentHeight / scalingFactor);
				}

				cachedRealDimensions = new Dimension(newWidth, newHeight);
			}
			else
			{
				cachedRealDimensions = Constants.GAME_FIXED_SIZE;
			}
		}

		return cachedRealDimensions;
	}

	@Override
	public Dimension getStretchedDimensions()
	{
		if (cachedStretchedDimensions == null)
		{
			Container canvasParent = getCanvas().getParent();

			int parentWidth = canvasParent.getWidth();
			int parentHeight = canvasParent.getHeight();

			Dimension realDimensions = getRealDimensions();

			if (stretchedKeepAspectRatio)
			{
				double aspectRatio = realDimensions.getWidth() / realDimensions.getHeight();

				int tempNewWidth = (int) (parentHeight * aspectRatio);

				if (tempNewWidth > parentWidth)
				{
					parentHeight = (int) (parentWidth / aspectRatio);
				}
				else
				{
					parentWidth = tempNewWidth;
				}
			}

			if (stretchedIntegerScaling)
			{
				if (parentWidth > realDimensions.width)
				{
					parentWidth = parentWidth - (parentWidth % realDimensions.width);
				}
				if (parentHeight > realDimensions.height)
				{
					parentHeight = parentHeight - (parentHeight % realDimensions.height);
				}
			}

			cachedStretchedDimensions = new Dimension(parentWidth, parentHeight);
		}

		return cachedStretchedDimensions;
	}

	@Override
	public void invalidateStretching(boolean resize)
	{
		cachedRealDimensions = null;
		cachedStretchedDimensions = null;

		if (resize && isResized())
		{
			/*
				Tells the game to run resizeCanvas the next frame.

				This is useful when resizeCanvas wouldn't usually run,
				for example when we've only changed the scaling factor
				and we still want the game's canvas to resize
				with regards to the new maximum bounds.
			 */
			setResizeCanvasNextFrame(true);
		}
	}

	@Override
	public void changeWorld(World world) {

	}

	@Override
	public RSWorld createWorld() {
		return null;
	}

	@Override
	public void setAnimOffsetX(int animOffsetX) {

	}

	@Override
	public void setAnimOffsetY(int animOffsetY) {

	}

	@Override
	public void setAnimOffsetZ(int animOffsetZ) {

	}

	@Override
	public RSSpritePixels drawInstanceMap(int z) {
		return null;
	}

	@Override
	public void setMinimapReceivesClicks(boolean minimapReceivesClicks) {

	}

	@Override
	public void runScript(Object... args) {

	}

	@Override
	public net.runelite.api.ScriptEvent createScriptEvent(Object... args) {
		return null;
	}

	@Override
	public boolean hasHintArrow() {
		return false;
	}

	@Override
	public HintArrowType getHintArrowType() {
		return null;
	}

	@Override
	public void clearHintArrow() {

	}

	@Override
	public void setHintArrow(WorldPoint point) {

	}

	@Override
	public void setHintArrow(net.runelite.api.Player player) {

	}

	@Override
	public void setHintArrow(net.runelite.api.NPC npc) {

	}

	@Override
	public WorldPoint getHintArrowPoint() {
		return null;
	}

	@Override
	public net.runelite.api.Player getHintArrowPlayer() {
		return null;
	}

	@Override
	public net.runelite.api.NPC getHintArrowNpc() {
		return null;
	}

	@Override
	public boolean isInterpolatePlayerAnimations() {
		return false;
	}

	@Override
	public void setInterpolatePlayerAnimations(boolean interpolate) {

	}

	@Override
	public boolean isInterpolateNpcAnimations() {
		return false;
	}

	@Override
	public void setInterpolateNpcAnimations(boolean interpolate) {

	}

	@Override
	public boolean isInterpolateObjectAnimations() {
		return false;
	}

	@Override
	public void setInterpolateObjectAnimations(boolean interpolate) {

	}

	@Override
	public boolean isInterpolateWidgetAnimations() {
		return false;
	}

	@Override
	public void setInterpolateWidgetAnimations(boolean interpolate) {

	}

	@Override
	public boolean isInInstancedRegion() {
		return plane > 4;
	}

	@Override
	public int getItemPressedDuration() {
		return 0;
	}

	@Override
	public void setItemPressedDuration(int duration) {

	}

	@Override
	public int getFlags() {
		return 0;
	}


	@Override
	public void setIsHidingEntities(boolean state)
	{
		hideentities = state;
	}

	@Override
	public void setOthersHidden(boolean state)
	{
		hideothers = state;
	}

	@Override
	public void setOthersHidden2D(boolean state)
	{
		hideothers2d = state;
	}

	@Override
	public void setFriendsHidden(boolean state)
	{
		hidefriends = state;
	}

	@Override
	public void setFriendsChatMembersHidden(boolean state)
	{
		hidefriendclan = state;
	}

	@Override
	public void setIgnoresHidden(boolean state)
	{
		hideignores = state;
	}

	@Override
	public void setLocalPlayerHidden(boolean state)
	{
		hidelocalplayers = state;
	}

	@Override
	public void setLocalPlayerHidden2D(boolean state)
	{
		hidelocal2dplayers = state;
	}

	@Override
	public void setNPCsHidden(boolean state)
	{
		hidelocalnpcs = state;
		hide2dnpc = state;
	}

	@Override
	public void setNPCsHidden2D(boolean state)
	{
		hide2dnpc = state;
	}

	@Override
	public void setHideSpecificPlayers(List<String> players)
	{

	}


	@Override
	public void setHiddenNpcIndices(List<Integer> npcIndices)
	{

	}

	@Override
	public List<Integer> getHiddenNpcIndices()
	{
		return null;
	}

	@Override
	public void setPetsHidden(boolean state)
	{
		hidepets = state;
	}

	@Override
	public void setAttackersHidden(boolean state)
	{

	}

	@Override
	public void setProjectilesHidden(boolean state)
	{
		hideprojectiles = state;
	}

	@Override
	public void setDeadNPCsHidden(boolean state)
	{

	}

	@Override
	public void addHiddenNpcName(String npc)
	{

	}

	@Override
	public void removeHiddenNpcName(String npc)
	{

	}


	@Override
	public void setBlacklistDeadNpcs(Set<Integer> blacklist) {

	}

	public boolean addEntityMarker(int x, int y, RSRenderable entity)
	{

		return true;
	}

	public boolean shouldDraw(Object entity, boolean drawingUI)
	{


		return true;
	}

	private static boolean invertPitch;
	private static boolean invertYaw;

	private int changedSkillsCount;

	@Override
	public RSCollisionMap[] getCollisionMaps() {
		return collisionMaps;
	}

	@Override
	public int getPlayerIndexesCount() {
		return 0;
	}

	@Override
	public int[] getPlayerIndices() {
		return new int[0];
	}

	@Override
	public int[] getBoostedSkillLevels() {
		return this.currentLevels;
	}

	@Override
	public int[] getRealSkillLevels() {
		return this.maximumLevels;
	}

	@Override
	public int[] getSkillExperiences() {
		return this.currentExp;
	}

	@Override
	public int[] getChangedSkills() {
		return new int[0];
	}

	@Override
	public int getChangedSkillsCount() {
		return this.changedSkillsCount;
	}
	@Override
	public void setChangedSkillsCount(int i) {
		this.changedSkillsCount = i;
	}

	@Override
	public void queueChangedSkill(Skill skill) {
		int[] skills = getChangedSkills();
		int count = getChangedSkillsCount();
		skills[++count - 1 & 31] = skill.ordinal();
		setChangedSkillsCount(count);
	}
	public Map<Integer, SpritePixels> spriteOverrides = new HashMap<>();
	public Map<Integer, SpritePixels> widgetSpriteOverrides = new HashMap<>();
	@Override
	public Map<Integer, SpritePixels> getSpriteOverrides() {
		return spriteOverrides;
	}

	@Override
	public Map<Integer, SpritePixels> getWidgetSpriteOverrides() {
		return widgetSpriteOverrides;
	}

	@Override
	public void setCompass(SpritePixels spritePixels) {
		this.compassImage = (Sprite) spritePixels;
	}

	@Override
	public RSEvictingDualNodeHashTable getWidgetSpriteCache() {
		return null;
	}

	int tickCount;

	@Override
	public int getTickCount() {
		return tickCount;
	}

	@Override
	public void setTickCount(int tickCount) {
		this.tickCount = tickCount;
	}

	private int dragDelay = 5;
	@Override
	public void setInventoryDragDelay(int delay) {
		this.dragDelay = delay;
	}


	@Override
	public boolean isHdMinimapEnabled() {
		return scene.hdMinimapEnabled;
	}

	@Override
	public void setHdMinimapEnabled(boolean enabled) {
		scene.hdMinimapEnabled = enabled;
	}

	@Override
	public EnumSet<WorldType> getWorldType() {
		return EnumSet.of(WorldType.MEMBERS);
	}

	@Override
	public int getOculusOrbState() {
		return 0;
	}

	@Override
	public void setOculusOrbState(int state) {

	}

	@Override
	public void setOculusOrbNormalSpeed(int speed) {

	}

	@Override
	public int getOculusOrbFocalPointX() {
		return xCameraPos;
	}

	@Override
	public int getOculusOrbFocalPointY() {
		return yCameraPos;
	}

	@Override
	public void setOculusOrbFocalPointX(int xPos) {

	}

	@Override
	public void setOculusOrbFocalPointY(int yPos) {

	}

	@Override
	public RSTileItem getLastItemDespawn() {
		return null;
	}

	@Override
	public void setLastItemDespawn(RSTileItem lastItemDespawn) {

	}

	@Override
	public void openWorldHopper() {

	}

	@Override
	public void hopToWorld(World world) {

	}

	@Override
	public void setSkyboxColor(int skyboxColor) {
		scene.skyboxColor = skyboxColor;
	}

	@Override
	public int getSkyboxColor() {
		return scene.skyboxColor;
	}

	@Override
	public boolean isGpu() {
		return gpu;
	}

	@Override
	public void setGpu(boolean gpu) {
		this.gpu = gpu;
	}


	@Override
	public int get3dZoom() {
		return cameraZoom;
	}

	@Override
	public void set3dZoom(int zoom) {
		if(zoom < 195)
			zoom = 195;
		if(zoom > 1105)
			zoom = 1105;
		this.cameraZoom = zoom;
	}

	@Override
	public int getCenterX() {
		return getViewportWidth() / 2;
	}

	@Override
	public int getCenterY() {
		return getViewportHeight() / 2;
	}

	@Override
	public int getCameraX2() {
		return SceneGraph.xCameraPos;
	}

	@Override
	public int getCameraY2() {
		return SceneGraph.zCameraPos;
	}

	@Override
	public int getCameraZ2() {
		return SceneGraph.yCameraPos;
	}

	@Override
	public RSTextureProvider getTextureProvider() {
		return ((TextureProvider)Rasterizer3D.textureLoader);
	}

	@Override
	public int[][] getOccupiedTilesTick() {
		return new int[0][];
	}

	@Override
	public RSEvictingDualNodeHashTable getObjectDefinitionModelsCache() {
		return null;
	}

	@Override
	public int getCycle() {
		return scene.cycle;
	}

	@Override
	public void setCycle(int cycle) {
		scene.cycle = cycle;
	}

	@Override
	public boolean[][][][] getVisibilityMaps() {
		return scene.visibilityMap;
	}

	@Override
	public RSEvictingDualNodeHashTable getCachedModels2() {
		return null;
	}

	@Override
	public void setRenderArea(boolean[][] renderArea) {
		scene.renderArea = renderArea;
	}

	@Override
	public void setCameraX2(int cameraX2) {
		scene.xCameraPos = cameraX2;
	}

	@Override
	public void setCameraY2(int cameraY2) {
		scene.zCameraPos = cameraY2;
	}

	@Override
	public void setCameraZ2(int cameraZ2) {
		scene.yCameraPos = cameraZ2;
	}

	@Override
	public void setScreenCenterX(int screenCenterX) {
		scene.screenCenterX = screenCenterX;
	}

	@Override
	public void setScreenCenterZ(int screenCenterZ) {
		scene.screenCenterZ = screenCenterZ;
	}

	@Override
	public void setScenePlane(int scenePlane) {
		scene.currentRenderPlane = scenePlane;
	}

	@Override
	public void setMinTileX(int i) {
		scene.minTileX = i;
	}

	@Override
	public void setMinTileZ(int i) {
		scene.minTileZ = i;
	}

	@Override
	public void setMaxTileX(int i) {
		scene.maxTileX = i;
	}

	@Override
	public void setMaxTileZ(int i) {
		scene.maxTileZ = i;
	}

	@Override
	public int getTileUpdateCount() {
		return scene.tileUpdateCount;
	}

	@Override
	public void setTileUpdateCount(int tileUpdateCount) {
		scene.tileUpdateCount = tileUpdateCount;
	}

	@Override
	public boolean getViewportContainsMouse() {
		return false;
	}

	@Override
	public int getRasterizer3D_clipMidX2() {
		return Rasterizer2D.viewportCenterX;
	}

	@Override
	public int getRasterizer3D_clipNegativeMidX() {
		return -Rasterizer2D.viewportCenterX;
	}

	@Override
	public int getRasterizer3D_clipNegativeMidY() {
		return -Rasterizer2D.viewportCenterY;
	}

	@Override
	public int getRasterizer3D_clipMidY2() {
		return Rasterizer2D.viewportCenterY;
	}

	@Override
	public void checkClickbox(net.runelite.api.Model model, int orientation, int pitchSin, int pitchCos, int yawSin,
							  int yawCos, int x, int y, int z, long hash) {

	}

	@Override
	public RSWidget getIf1DraggedWidget() {
		return null;
	}

	@Override
	public int getIf1DraggedItemIndex() {
		return 0;
	}

	@Override
	public void setSpellSelected(boolean selected) {

	}

	@Override
	public RSEnumComposition getRsEnum(int id) {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getItemCompositionCache() {
		return null;
	}

	@Override
	public RSSpritePixels[] getCrossSprites() {
		return null;
	}

	@Override
	public EnumComposition getEnum(int id) {
		return null;
	}

	@Override
	public void draw2010Menu(int alpha) {

	}

	@Override
	public int[] getGraphicsPixels() {
		return null;
	}

	@Override
	public int getGraphicsPixelsWidth() {
		return 0;
	}

	@Override
	public int getGraphicsPixelsHeight() {
		return 0;
	}

	@Override
	public void rasterizerFillRectangle(int x, int y, int w, int h, int rgb) {
		Rasterizer2D.drawBox(x,y,w,h,rgb);
	}

	@Override
	public int getStartX() {
		return 0;
	}

	@Override
	public int getStartY() {
		return 0;
	}

	@Override
	public int getEndX() {
		return 0;
	}

	@Override
	public int getEndY() {
		return 0;
	}

	@Override
	public void drawOriginalMenu(int alpha) {
		int x = getMenuX();
		int y = getMenuY();
		int w = getMenuWidth();
		int h = getMenuHeight();
		drawOriginalMenuNest(null, x, y, w, h, getMenuScroll(), alpha);
	}
	private static final int ORIGINAL_BG = 0x5D5447;

	private void drawOriginalMenuNest(net.runelite.api.MenuEntry parent, int x, int y, int w, int h, int scroll, int alpha)
	{
		if (alpha != 255)
		{
			rasterizerFillRectangleAlpha(x, y, w, h, ORIGINAL_BG, alpha);
			rasterizerFillRectangleAlpha(x + 1, y + 1, w - 2, 16, 0, alpha);
			rasterizerDrawRectangleAlpha(x + 1, y + 18, w - 2, h - 19, 0, alpha);
		}
		else
		{
			rasterizerFillRectangle(x, y, w, h, ORIGINAL_BG);
			rasterizerFillRectangle(x + 1, y + 1, w - 2, 16, 0);
			rasterizerDrawRectangle(x + 1, y + 18, w - 2, h - 19, 0);
		}

		net.runelite.rs.api.RSFont font = getFontBold12();
		String option = parent == null ? "Choose Option" : parent.getTarget();
		font.drawTextLeftAligned(option, x + 3, y + 14, ORIGINAL_BG, -1);

		net.runelite.api.MenuEntry[] entries = getMenuEntries();

		int mouseX = getMouseX();
		int mouseY = getMouseY();
		int realCount = 0;
		for (int i = 0; i < entries.length; i++)
		{
			if (entries[i].getParent() == parent)
			{
				realCount++;
			}
		}
		int realIdx = 0;
		for (int i = 0; i < entries.length; i++)
		{
			net.runelite.api.MenuEntry entry = entries[i];
			if (entry.getParent() == parent && realCount - 1 - realIdx >= scroll)
			{
				int rowY = y + (realCount - 1 - realIdx - scroll) * 15 + 31;
				realIdx++;
				String s = entry.getOption();
				if (entry.getTarget() != null && !entry.getTarget().isEmpty() && !entry.getTarget().equalsIgnoreCase("null"))
				{
					s = s + " " + entry.getTarget();
				}

				if (entry.getType() == MenuAction.RUNELITE_SUBMENU)
				{
					s = s + " <col=ffffff><gt>";
				}
				int highlight = 0xFFFFFF;
				if (mouseX > x && mouseX < w + x && mouseY > rowY - 13 && mouseY < rowY + 3)
				{
					highlight = 0xFFFF00;
					if (entry.getType() == MenuAction.RUNELITE_SUBMENU)
					{
						setSubmenuIdx(i);
						initSubmenu(x + w, rowY - 31);
					}
				}
				font.drawTextLeftAligned(s, x + 3, rowY, highlight, 0);
				if (instance.getSubmenuIdx() == i)
				{
					this.drawOriginalMenuNest(entry, getSubmenuX(), getSubmenuY(), getSubmenuWidth(), getSubmenuHeight(), getSubmenuScroll(), alpha);
				}
			}
		}
	}

	void initSubmenu(int x, int y)
	{
		Client client = instance;
		if (client.getSubmenuIdx() != -1)
		{
			RSRuneLiteMenuEntry rootEntry = (RSRuneLiteMenuEntry) client.getMenuEntries()[client.getSubmenuIdx()];
			String s = rootEntry.getOption();
			if (!rootEntry.getTarget().isEmpty())
			{
				s = s + " " + rootEntry.getTarget();
			}

			if (rootEntry.getType() == MenuAction.RUNELITE_SUBMENU)
			{
				s = s + " <col=ffffff><gt>";
			}
			int tempWidth = client.getFontBold12().getTextWidth(s);
			int realCount = 0;

			for (net.runelite.api.MenuEntry me : client.getMenuEntries())
			{
				if (me.getParent() == null) continue;
				if (me.getParent().getIdx() == rootEntry.getIdx())
				{
					realCount++;
					tempWidth = Math.max(client.getFontBold12().getTextWidth(
							(me.getTarget() != null && me.getTarget().length() > 0 ? me.getOption() + " " + me.getTarget() : me.getOption())
					), tempWidth);
				}
			}
			tempWidth += 8;
			int var4 = realCount * 15 + 22;
			int var5 = x/* - tempWidth / 2*/;
			if (var5 + tempWidth > client.getCanvasWidth())
			{
				//var5 = client.getCanvasWidth() - tempWidth;
				var5 = x - tempWidth - getMenuWidth();
			}

			if (var5 < 0)
			{
				var5 = 0;
			}

			int var6 = y;
			if (var4 + y > client.getCanvasHeight())
			{
				var6 = client.getCanvasHeight() - var4;
			}

			if (var6 < 0)
			{
				var6 = 0;
			}

			client.setSubmenuX(var5);
			client.setSubmenuY(var6);
			client.setSubmenuWidth(tempWidth);
			client.setSubmenuHeight(realCount * 15 + 22);

			client.setSubmenuScroll(0);
			client.setSubmenuScrollMax(0);
			if (var4 > this.getCanvasHeight())
			{
				client.setSubmenuScrollMax(var4 - this.getCanvasHeight() + 14 / 15);
			}
		}
	}

	@Override
	public void resetHealthBarCaches() {

	}

	@Override
	public boolean getRenderSelf() {
		return false;
	}

	@Override
	public void setRenderSelf(boolean enabled) {

	}

	@Override
	public void invokeMenuAction(String option, String target, int identifier, int opcode, int param0, int param1) {

	}

	@Override
	public RSMouseRecorder getMouseRecorder() {
		return null;
	}

	@Override
	public void setPrintMenuActions(boolean b) {

	}

	@Override
	public String getSelectedSpellName() {
		return null;
	}

	@Override
	public void setSelectedSpellName(String name) {

	}

	@Override
	public boolean getSpellSelected() {
		return false;
	}

	@Override
	public RSSoundEffect getTrack(RSAbstractArchive indexData, int id, int var0) {
		return null;
	}

	@Override
	public RSRawPcmStream createRawPcmStream(RSRawSound audioNode, int var0, int volume) {
		return null;
	}

	@Override
	public RSPcmStreamMixer getSoundEffectAudioQueue() {
		return null;
	}

	@Override
	public RSArchive getIndexCache4() {
		return null;
	}

	@Override
	public RSDecimator getSoundEffectResampler() {
		return null;
	}

	@Override
	public void setMusicTrackVolume(int volume) {

	}

	@Override
	public void setViewportWalking(boolean viewportWalking) {

	}

	@Override
	public void playMusicTrack(int var0, RSAbstractArchive var1, int var2, int var3, int var4, boolean var5) {

	}

	@Override
	public RSMidiPcmStream getMidiPcmStream() {
		return null;
	}

	@Override
	public int getCurrentTrackGroupId() {
		return 0;
	}

	@Override
	public String getSelectedSpellActionName() {
		return null;
	}

	@Override
	public int getSelectedSpellFlags() {
		return 0;
	}

	@Override
	public void setHideFriendAttackOptions(boolean yes) {

	}

	@Override
	public void setHideFriendCastOptions(boolean yes) {

	}

	@Override
	public void setHideClanmateAttackOptions(boolean yes) {
	}

	@Override
	public void setHideClanmateCastOptions(boolean yes) {

	}

	@Override
	public void setUnhiddenCasts(Set<String> casts) {

	}

	@Override
	public void addFriend(String name) {

	}

	@Override
	public void removeFriend(String name) {

	}

	@Override
	public void setModulus(BigInteger modulus) {

	}

	@Override
	public BigInteger getModulus() {
		return null;
	}

	@Override
	public int getItemCount() {
		return 0;
	}

	@Override
	public void setAllWidgetsAreOpTargetable(boolean value) {

	}

	@Override
	public void insertMenuItem(String action, String target, int opcode, int identifier, int argument1, int argument2,
							   boolean forceLeftClick) {

	}

	@Override
	public void setSelectedItemID(int id) {

	}

	@Override
	public int getSelectedItemWidget() {
		return 0;
	}

	@Override
	public void setSelectedItemWidget(int widgetID) {

	}

	@Override
	public int getSelectedItemSlot() {
		return 0;
	}

	@Override
	public void setSelectedItemSlot(int idx) {

	}

	@Override
	public int getSelectedSpellWidget() {
		return 0;
	}

	@Override
	public int getSelectedSpellChildIndex() {
		return 0;
	}

	@Override
	public void setSelectedSpellWidget(int widgetID) {

	}

	@Override
	public void setSelectedSpellChildIndex(int index) {

	}

	@Override
	public void scaleSprite(int[] canvas, int[] pixels, int color, int pixelX, int pixelY, int canvasIdx,
							int canvasOffset, int newWidth, int newHeight, int pixelWidth, int pixelHeight, int oldWidth) {

	}

	@Override
	public void promptCredentials(boolean clearPass) {

	}

	@Override
	public RSVarpDefinition getVarpDefinition(int id) {
		return null;
	}

	@Override
	public RSTileItem newTileItem() {
		return null;
	}

	@Override
	public RSNodeDeque newNodeDeque() {
		return null;
	}

	@Override
	public void updateItemPile(int localX, int localY) {

	}

	@Override
	public void setHideDisconnect(boolean dontShow) {

	}

	@Override
	public void setTempMenuEntry(net.runelite.api.MenuEntry entry) {

	}

	@Override
	public void setShowMouseCross(boolean show) {

	}

	@Override
	public int getDraggedWidgetX() {
		return 0;
	}

	@Override
	public int getDraggedWidgetY() {
		return 0;
	}

	@Override
	public int[] getChangedSkillLevels() {
		return new int[0];
	}

	@Override
	public void setMouseIdleTicks(int cycles) {
		MouseHandler.idleCycles = cycles;
	}

	@Override
	public void setKeyboardIdleTicks(int cycles) {
		KeyHandler.idleCycles = cycles;
	}

	@Override
	public void setGeSearchResultCount(int count) {
	}

	@Override
	public void setGeSearchResultIds(short[] ids) {

	}

	@Override
	public void setGeSearchResultIndex(int index) {

	}

	@Override
	public void setComplianceValue(String key, boolean value) {

	}

	@Override
	public boolean getComplianceValue(String key) {
		return false;
	}

	@Override
	public boolean isMirrored() {
		return false;
	}

	@Override
	public void setMirrored(boolean isMirrored) {

	}

	@Override
	public boolean isComparingAppearance() {
		return false;
	}

	@Override
	public void setComparingAppearance(boolean comparingAppearance) {

	}

	@Override
	public void setLoginScreen(SpritePixels pixels) {

	}

	@Override
	public void setShouldRenderLoginScreenFire(boolean val) {

	}

	@Override
	public boolean shouldRenderLoginScreenFire() {
		return false;
	}

	public int keyID = -1;

	@Override
	public boolean isKeyPressed(int keycode) {
        return keycode == keyID;
    }

	@Override
	public int getFollowerIndex() {
		return 0;
	}

	@Override
	public int isItemSelected() {
		return 0;
	}

	@Override
	public String getSelectedItemName() {
		return null;
	}

	@Override
	public RSWidget getMessageContinueWidget() {
		return null;
	}

	@Override
	public void setMusicPlayerStatus(int var0) {

	}

	@Override
	public void setMusicTrackArchive(RSAbstractArchive var0) {

	}

	@Override
	public void setMusicTrackGroupId(int var0) {

	}

	@Override
	public void setMusicTrackFileId(int var0) {

	}

	@Override
	public void setMusicTrackBoolean(boolean var0) {

	}

	@Override
	public void setPcmSampleLength(int var0) {

	}

	@Override
	public int[] getChangedVarps() {
		return new int[0];
	}

	@Override
	public int getChangedVarpCount() {
		return 0;
	}

	@Override
	public void setChangedVarpCount(int changedVarpCount) {

	}

	@Override
	public void setOutdatedScript(String outdatedScript) {

	}

	@Override
	public List<String> getOutdatedScripts() {
		return null;
	}

	@Override
	public RSFrames getFrames(int frameId) {
		return null;
	}

	@Override
	public RSSpritePixels getMinimapSprite() {
		return minimapImage;
	}

	@Override
	public void setMinimapSprite(SpritePixels spritePixels) {

	}

	@Override
	public void drawObject(int z, int x, int y, int randomColor1, int randomColor2) {

	}

	@Override
	public RSScriptEvent createScriptEvent() {
		return null;
	}

	@Override
	public void runScript(RSScriptEvent ev, int ex, int var2) {

	}

	@Override
	public void setHintArrowTargetType(int value) {
		
	}

	@Override
	public int getHintArrowTargetType() {
		return 0;
	}

	@Override
	public void setHintArrowX(int value) {
		
	}

	@Override
	public int getHintArrowX() {
		return 0;
	}

	@Override
	public void setHintArrowY(int value) {
		
	}

	@Override
	public int getHintArrowY() {
		return 0;
	}

	@Override
	public void setHintArrowOffsetX(int value) {
		
	}

	@Override
	public void setHintArrowOffsetY(int value) {
		
	}

	@Override
	public void setHintArrowNpcTargetIdx(int value) {
		
	}

	@Override
	public int getHintArrowNpcTargetIdx() {
		return 0;
	}

	@Override
	public void setHintArrowPlayerTargetIdx(int value) {
		
	}

	@Override
	public int getHintArrowPlayerTargetIdx() {
		return 0;
	}

	@Override
	public RSSequenceDefinition getSequenceDefinition(int id) {
		return null;
	}

	@Override
	public RSIntegerNode newIntegerNode(int contents) {
		return null;
	}

	@Override
	public RSObjectNode newObjectNode(Object contents) {
		return null;
	}

	@Override
	public RSIterableNodeHashTable newIterableNodeHashTable(int size) {
		return null;
	}

	@Override
	public RSVarbitComposition getVarbitComposition(int id) {
		return null;
	}

	@Override
	public RSAbstractArchive getSequenceDefinition_skeletonsArchive() {
		return null;
	}

	@Override
	public RSAbstractArchive getSequenceDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getSequenceDefinition_animationsArchive() {
		return null;
	}

	@Override
	public RSAbstractArchive getNpcDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getObjectDefinition_modelsArchive() {
		return null;
	}

	@Override
	public RSAbstractArchive getObjectDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getItemDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getKitDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getKitDefinition_modelsArchive() {
		return null;
	}

	@Override
	public RSAbstractArchive getSpotAnimationDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getSpotAnimationDefinition_modelArchive() {
		return null;
	}

	@Override
	public RSBuffer createBuffer(byte[] initialBytes) {
		return null;
	}

	@Override
	public RSSceneTilePaint createSceneTilePaint(int swColor, int seColor, int neColor, int nwColor, int texture, int rgb, boolean isFlat) {
		return null;
	}

	@Override
	public long[] getCrossWorldMessageIds() {
		return null;
	}

	@Override
	public int getCrossWorldMessageIdsIndex() {
		return 0;
	}

	@Override
	public RSClanChannel[] getCurrentClanChannels() {
		return new RSClanChannel[0];
	}

	@Override
	public RSClanSettings[] getCurrentClanSettingsAry() {
		return new RSClanSettings[0];
	}

	@Override
	public RSClanChannel getClanChannel() {
		return null;
	}

	@Override
	public RSClanChannel getGuestClanChannel() {
		return null;
	}

	@Override
	public RSClanSettings getClanSettings() {
		return null;
	}

	@Override
	public RSClanSettings getGuestClanSettings() {
		return null;
	}

	@Override
	public ClanRank getClanRankFromRs(int rank) {
		return null;
	}

	@Override
	public RSIterableNodeHashTable readStringIntParameters(RSBuffer buffer, RSIterableNodeHashTable table) {
		return null;
	}

	@Override
	public int getRndHue() {
		return 0;
	}

	@Override
	public short[][][] getTileUnderlays() {
		return scene.getUnderlayIds();
	}

	@Override
	public short[][][] getTileOverlays() {
		return scene.getOverlayIds();
	}

	@Override
	public byte[][][] getTileShapes() {
		return scene.getTileShapes();
	}

	@Override
	public RSSpotAnimationDefinition getSpotAnimationDefinition(int id) {
		return null;
	}

	@Override
	public RSModelData getModelData(RSAbstractArchive var0, int var1, int var2) {
		return null;
	}

	@Override
	public boolean isCameraLocked() {
		return false;
	}

	@Override
	public boolean getCameraPitchRelaxerEnabled() {
		return pitchRelaxEnabled;
	}

	public static boolean unlockedFps;
	public long delayNanoTime;
	public long lastNanoTime;
	public static double tmpCamAngleY;
	public static double tmpCamAngleX;

	@Override
	public boolean isUnlockedFps() {
		return unlockedFps;
	}

	@Override
	public long getUnlockedFpsTarget() {
		return delayNanoTime;
	}

	public void updateCamera()
	{
		if (unlockedFps)
		{
			long nanoTime = System.nanoTime();
			long diff = nanoTime - this.lastNanoTime;
			this.lastNanoTime = nanoTime;

			if (this.getGameState() == GameState.LOGGED_IN)
			{
				this.interpolateCamera(diff);
			}
		}
	}

	public static final int STANDARD_PITCH_MIN = 128;
	public static final int STANDARD_PITCH_MAX = 383;
	public static final int NEW_PITCH_MAX = 512;

	public void interpolateCamera(long var1)
	{
		double angleDX = diffToDangle(getCamAngleDY(), var1);
		double angleDY = diffToDangle(getCamAngleDX(), var1);

		tmpCamAngleY += angleDX / 2;
		tmpCamAngleX += angleDY / 2;
		tmpCamAngleX = Doubles.constrainToRange(tmpCamAngleX, Perspective.UNIT * STANDARD_PITCH_MIN, getCameraPitchRelaxerEnabled() ? Perspective.UNIT * NEW_PITCH_MAX : Perspective.UNIT * STANDARD_PITCH_MAX);

		int yaw = toCameraPos(tmpCamAngleY);
		int pitch = toCameraPos(tmpCamAngleX);

		setCameraYawTarget(yaw);
		setCameraPitchTarget(pitch);
	}

	public static int toCameraPos(double var0)
	{
		return (int) (var0 / Perspective.UNIT) & 2047;
	}


	public static double diffToDangle(int var0, long var1)
	{
		double var2 = var0 * Perspective.UNIT;
		double var3 = (double) var1 / 2.0E7D;

		return var2 * var3;
	}

	@Override
	public void posToCameraAngle(int var0, int var1) {
		tmpCamAngleY = var0 * Perspective.UNIT;
		tmpCamAngleX = var1 * Perspective.UNIT;
	}

	static void onCameraPitchTargetChanged(int idx)
	{
		int newPitch = instance.getCameraPitchTarget();
		int pitch = newPitch;
		if (pitchRelaxEnabled)
		{
			// This works because the vanilla camera movement code only moves %2
			if (lastPitchTarget > STANDARD_PITCH_MAX && newPitch == STANDARD_PITCH_MAX)
			{
				pitch = lastPitchTarget;
				if (pitch > NEW_PITCH_MAX)
				{
					pitch = NEW_PITCH_MAX;
				}
				instance.setCameraPitchTarget(pitch);
			}
		}
		lastPitchTarget = pitch;
	}

	static void onCameraPitchChanged(int idx)
	{
		int newPitch = instance.getCameraPitch();
		int pitch = newPitch;
		if (pitchRelaxEnabled)
		{
			// This works because the vanilla camera movement code only moves %2
			if (lastPitch > STANDARD_PITCH_MAX && newPitch == STANDARD_PITCH_MAX)
			{
				pitch = lastPitch;
				if (pitch > NEW_PITCH_MAX)
				{
					pitch = NEW_PITCH_MAX;
				}
				instance.setCameraPitch(pitch);
			}
		}
		lastPitch = pitch;
	}

	@Override
	public RSClanChannel getClanChannel(int clanId) {
		return null;
	}

	@Override
	public RSClanSettings getClanSettings(int clanId) {
		return null;
	}

	@Override
	public void setUnlockedFps(boolean unlock) {
		unlockedFps = unlock;
	}

	@Override
	public void setUnlockedFpsTarget(int fps) {
		delayNanoTime = fps;
	}

	@Override
	public net.runelite.api.Deque<AmbientSoundEffect> getAmbientSoundEffects() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getEnumDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getFloorUnderlayDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getFloorOverlayDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getHitSplatDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getHitSplatDefinitionSpritesCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getHitSplatDefinitionDontsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getInvDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getItemDefinitionModelsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getItemDefinitionSpritesCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getKitDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getNpcDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getNpcDefinitionModelsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getObjectDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getObjectDefinitionModelDataCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getObjectDefinitionEntitiesCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getParamDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getPlayerAppearanceModelsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSequenceDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSequenceDefinitionFramesCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSequenceDefinitionModelsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSpotAnimationDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSpotAnimationDefinitionModlesCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getVarcIntCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getVarpDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getModelsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getFontsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSpriteMasksCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSpritesCache() {
		return null;
	}

	@Override
	public RSIterableNodeHashTable createIterableNodeHashTable(int size) {
		return null;
	}

	@Override
	public int getSceneMaxPlane() {
		return 0;
	}

	@Override
	public void setIdleTimeout(int id) {

	}

	@Override
	public int getIdleTimeout() {
		return 0;
	}

	public boolean canZoomMap = false;

	@Override
	public void setMinimapZoom(boolean minimapZoom) {
		this.canZoomMap = minimapZoom;
	}

	@Override
	public double getMinimapZoom() {
		return minimapZoom;
	}

	@Override
	public boolean isMinimapZoom() {
		return canZoomMap;
	}

	@Override
	public void setMinimapZoom(double zoom) {
		minimapZoom = (int) zoom;
	}

	@Override
	public void setScale(int scale) {

	}

	@Override
	public boolean isChatboxLocked() {//this is why you cant click, ahh ok
		return lockChatbox;
	}

	@Override
	public void setChatboxLocked(boolean locked) {
		this.lockChatbox = locked;
	}

	@Override
	public net.runelite.api.Model mergeModels(net.runelite.api.Model shipBoat0, net.runelite.api.Model shipSail0) {
		return new Model(new Model[]{(Model) shipBoat0, (Model) shipSail0});
	}
	private long accountHash;
	@Override
	public long getAccountHash() {
		if(!loggedIn)
			return -1;
		long hash = longForName(myUsername);
		//if(accountHash != hash)
			//getCallbacks().post(new AccountHashChanged());
		accountHash = hash;
		return accountHash;
	}

	public static String capitalizeJustFirst(String str) {
		str = str.toLowerCase();
		if (str.length() > 1) {
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		} else {
			return str.toUpperCase();
		}
		return str;
	}

	public List<FeedItem> feedItems = new ArrayList<>();

	public void onBroadcast(String message) {
		if (message.contains("groot") && feedItems.stream().noneMatch(feedItem -> feedItem.getType() == FeedItemType.GROOT)) {
			feedItems.add(new FeedItem(FeedItemType.GROOT, null, "Groot Spawned", ";;groot to kill them", "", System.currentTimeMillis()));
		}

		if (message.contains("dono") && feedItems.stream().noneMatch(feedItem -> feedItem.getType() == FeedItemType.DONOR_BOSS)) {
			feedItems.add(new FeedItem(FeedItemType.DONOR_BOSS, null, "Donor Boss spawned", ";;db to kill them", "", System.currentTimeMillis()));
		}

		if (message.contains("shooting star") && !message.contains("dwarfs decided to mine") && feedItems.stream().noneMatch(feedItem -> feedItem.getType() == FeedItemType.SHOOTING_STAR)) {
			feedItems.add(new FeedItem(FeedItemType.SHOOTING_STAR, null, "Shooting Star spawned", ";;star to get to the star", "", System.currentTimeMillis()));
		}

		if (message.contains("vote") && feedItems.stream().noneMatch(feedItem -> feedItem.getType() == FeedItemType.VOTE_BOSS)) {
			feedItems.add(new FeedItem(FeedItemType.VOTE_BOSS, null, "Vote Boss spawned", ";;vb to kill them", "", System.currentTimeMillis()));
		}

		if (message.contains("seasonal") && feedItems.stream().noneMatch(feedItem -> feedItem.getType() == FeedItemType.SEASONAL)) {
			feedItems.add(new FeedItem(FeedItemType.SEASONAL, null, "Seasonal event has spawned", ";;seasonal to get there", "", System.currentTimeMillis()));
		}

		if (message.contains("wildyevent") && feedItems.stream().noneMatch(feedItem -> feedItem.getType() == FeedItemType.WILDY_BOSS)) {
			feedItems.add(new FeedItem(FeedItemType.WILDY_BOSS, null, "Wildy event is active", ";;wildyevent to get there", "", System.currentTimeMillis()));
		}
		if (message.contains("hespori") && feedItems.stream().noneMatch(feedItem -> feedItem.getType() == FeedItemType.HESPORI_BOSS)) {
			feedItems.add(new FeedItem(FeedItemType.HESPORI_BOSS, null, "Hespori is active", ";;worldevent to get there.", "", System.currentTimeMillis()));
		}

        feedItems.removeIf(feedItem -> feedItem.getTimestamp() < System.currentTimeMillis());

		FeedRecieved feedRecieved = new FeedRecieved(feedItems);

		callbacks.post(feedRecieved);
	}

	public Deque getIncompleteAnimables() {
		return incompleteAnimables;
	}
}
