package com.client;

import com.client.graphics.loaders.SpriteLoader;
import com.client.sign.Signlink;
import com.client.util.AssetUtils;
import com.client.util.Strings;
import org.apache.commons.io.IOUtils;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RSFont extends Rasterizer2D implements net.runelite.rs.api.RSFont {

	public int baseCharacterHeight = 0;
	public int anInt3494;
	public int anInt3512;
	public int[] characterDrawYOffsets;
	public int[] characterHeights;
	public int[] characterDrawXOffsets;
	public int[] characterWidths;
	public int[] iconWidths;
	public byte[] aByteArray4151;
	public byte[][] fontPixels;
	public int[] characterScreenWidths;
	public static Sprite[] chatImages;
	public static Sprite[] clanImages;
	public static Sprite[] iconPack;
	public static String aRSString_4135;
	public static String startTransparency;
	public static String startDefaultShadow;
	public static String endShadow = "/shad";
	public static String endEffect;
	public static String aRSString_4143;
	public static String endStrikethrough = "/str";
	public static String aRSString_4147;
	public static String startColor;
	public static String lineBreak;
	public static String startStrikethrough;
	public static String endColor;
	public static String startImage;
	public static String startOldschoolImage;
	public static String startIcon;
	public static String startClanImage;
	public static String endUnderline;
	public static String defaultStrikethrough;
	public static String startShadow;
	public static String startEffect;
	public static String aRSString_4162;
	public static String aRSString_4163;
	public static String endTransparency;
	public static String aRSString_4165;
	public static String startUnderline;
	public static String startDefaultUnderline;
	public static String aRSString_4169;
	public static String[] splitTextStrings;
	public static int defaultColor;
	public static int textShadowColor;
	public static int strikethroughColor;
	public static int defaultTransparency;
	public static int anInt4175;
	public static int underlineColor;
	public static int defaultShadow;
	public static int anInt4178;
	public static int transparency;
	public static int textColor;

	private static StringBuffer aStringBuffer3500 = new StringBuffer(100);
	public RSFont(boolean TypeFont, String s, FileArchive archive) {
		fontPixels = new byte[256][];
		characterWidths = new int[256];
		characterHeights = new int[256];
		characterDrawXOffsets = new int[256];
		characterDrawYOffsets = new int[256];
		characterScreenWidths = new int[256];
		Buffer stream = new Buffer(archive.getArchiveData(s + ".dat"));
		Buffer stream_1 = new Buffer(archive.getArchiveData("index.dat"));
		stream_1.currentPosition = stream.readUShort() + 4;
		int k = stream_1.readUnsignedByte();
		if (k > 0) {
			stream_1.currentPosition += 3 * (k - 1);
		}
		for (int l = 0; l < 256; l++) {
			characterDrawXOffsets[l] = stream_1.readUnsignedByte();
			characterDrawYOffsets[l] = stream_1.readUnsignedByte();
			int i1 = characterWidths[l] = stream_1.readUShort();
			int j1 = characterHeights[l] = stream_1.readUShort();
			int k1 = stream_1.readUnsignedByte();
			int l1 = i1 * j1;
			fontPixels[l] = new byte[l1];
			if (k1 == 0) {
				for (int i2 = 0; i2 < l1; i2++) {
					fontPixels[l][i2] = stream.readSignedByte();
				}

			} else if (k1 == 1) {
				for (int j2 = 0; j2 < i1; j2++) {
					for (int l2 = 0; l2 < j1; l2++) {
						fontPixels[l][j2 + l2 * i1] = stream.readSignedByte();
					}

				}

			}
			if (j1 > baseCharacterHeight && l < 128) {
				baseCharacterHeight = j1;
			}
			characterDrawXOffsets[l] = 1;
			characterScreenWidths[l] = i1 + 2;
			int k2 = 0;
			for (int i3 = j1 / 7; i3 < j1; i3++) {
				k2 += fontPixels[l][i3 * i1];
			}

			if (k2 <= j1 / 7) {
				characterScreenWidths[l]--;
				characterDrawXOffsets[l] = 0;
			}
			k2 = 0;
			for (int j3 = j1 / 7; j3 < j1; j3++) {
				k2 += fontPixels[l][(i1 - 1) + j3 * i1];
			}

			if (k2 <= j1 / 7) {
				characterScreenWidths[l]--;
			}
		}

		if (TypeFont) {
			characterScreenWidths[32] = characterScreenWidths[73];
		} else {
			characterScreenWidths[32] = characterScreenWidths[105];
		}
		int i = 2147483647;
		int i_173_ = -2147483648;
		for (int i_174_ = 0; i_174_ < 256; i_174_++) {
			if (characterDrawYOffsets[i_174_] < i && characterHeights[i_174_] != 0) {
				i = characterDrawYOffsets[i_174_];
			}
			if (characterDrawYOffsets[i_174_] + characterHeights[i_174_] > i_173_) {
				i_173_ = characterDrawYOffsets[i_174_] + characterHeights[i_174_];
			}
		}

		anInt3494 = baseCharacterHeight - i;
		anInt3512 = i_173_ - baseCharacterHeight;
	}
	public int getCharacterWidth(int i) {
		if (i == 160) {
			i = 32;
		}

		return characterScreenWidths[i & 0xff];
	}
	public int paragraphHeigth(String text, int width) {
		return method1486(text, new int[] { width }, splitTextStrings);
	}
	public int drawInterfaceText(final String text, final int x, final int y, final int width, final int height, final int textColor, final int shadowColor, final int transparency, final int horizontalAlignment, int verticalAlignment, int verticalSpacing) {
		if (text == null) {
			System.out.println("DrawInterfaceText text null");
			return 0;
		}

		setDefaultTextEffectValues(textColor, shadowColor, transparency);
		if (verticalSpacing == 0) {
			verticalSpacing = baseCharacterHeight;
		}

		int[] is = { width };
		if (height < anInt3494 + anInt3512 + verticalSpacing && height < verticalSpacing + verticalSpacing) {
			//is = null;
		}
		final int lineCount = method1486(text, is, splitTextStrings);
		if (verticalAlignment == 3 && lineCount == 1) {
			verticalAlignment = 1;
		}
		int textY;
		if (verticalAlignment == 0) {
			textY = y + anInt3494;
		} else if (verticalAlignment == 1) {
			textY = y + anInt3494 + (height - anInt3494 - anInt3512 - (lineCount - 1) * verticalSpacing) / 2;
		} else if (verticalAlignment == 2) {
			textY = y + height - anInt3512 - (lineCount - 1) * verticalSpacing;
		} else {
			int i_72_ = (height - anInt3494 - anInt3512 - (lineCount - 1) * verticalSpacing) / (lineCount + 1);
			if (i_72_ < 0) {
				i_72_ = 0;
			}
			textY = y + anInt3494 + i_72_;
			verticalSpacing += i_72_;
		}
		for (int lineId = 0; lineId < lineCount; lineId++) {
			if (horizontalAlignment == 0) {
				drawBasicString(splitTextStrings[lineId], x, textY);
			} else if (horizontalAlignment == 1) {
				drawBasicString(splitTextStrings[lineId], x + (width - getTextWidth(splitTextStrings[lineId])) / 2, textY);
			} else if (horizontalAlignment == 2) {
				drawBasicString(splitTextStrings[lineId], x + width - getTextWidth(splitTextStrings[lineId]), textY);
			} else if (lineId == lineCount - 1) {
				drawBasicString(splitTextStrings[lineId], x, textY);
			} else {
				method1464(splitTextStrings[lineId], width);
				drawBasicString(splitTextStrings[lineId], x, textY);
				anInt4178 = 0;
			}
			textY += verticalSpacing;
		}
		return lineCount;
	}
	private final void method1464(final String string, final int width) {
		int spaceCount = 0;
		boolean effectStart = false;
		int length = string.length();
		for (int charIndex = 0; charIndex < length; charIndex++) {
			final char c = string.charAt(charIndex);
			if (c == '<') {
				effectStart = true;
			} else if (c == '>') {
				effectStart = false;
			} else if (!effectStart && c == ' ') {
				spaceCount++;
			}
		}
		if (spaceCount > 0) {
			anInt4178 = (width - getTextWidth(string) << 8) / spaceCount;
		}
	}
	final int method1486(final String string, final int[] is, final String[] strings) {
		if (string == null) {
			return 0;
		}

		aStringBuffer3500.setLength(0);
		int width = 0;
		int i_175_ = 0;
		int i_176_ = -1;
		int i_177_ = 0;
		int i_178_ = 0;
		int effectStartIndex = -1;
		int stringsPos = 0;
		final int length = string.length();
		for (int id = 0; id < length; id++) {
			char character = string.charAt(id);
			if (character == 60) {//<
				effectStartIndex = id;
			} else {
				if (character == 62 && effectStartIndex != -1) {//>
					final String effect = string.substring(effectStartIndex + 1, id).toLowerCase();
					effectStartIndex = -1;
					aStringBuffer3500.append('<');
					aStringBuffer3500.append(effect);
					aStringBuffer3500.append('>');
					if (effect.equals("br")) {
						strings[stringsPos] = aStringBuffer3500.toString().substring(i_175_, aStringBuffer3500.length());
						stringsPos++;
						i_175_ = aStringBuffer3500.length();
						width = 0;
						i_176_ = -1;
					} else if (effect.equals("lt")) {
						width += getCharacterWidth('<');
					} else if (effect.equals("gt")) {
						width += getCharacterWidth('>');
					} else if (effect.equals("nbsp")) {
						width += getCharacterWidth('\u00a0');
					} else if (effect.equals("shy")) {
						width += getCharacterWidth('\u00ad');
					} else if (effect.equals("times")) {
						width += getCharacterWidth('\u00d7');
					} else if (effect.equals("euro")) {
						width += getCharacterWidth('\u20ac');
					} else if (effect.equals("copy")) {
						width += getCharacterWidth('\u00a9');
					} else if (effect.equals("reg")) {
						width += getCharacterWidth('\u00ae');
					} else if (effect.startsWith("img=")) {
						try {
							final int nameIconid = Strings.stringToInt10(effect.substring(4));
							width += chatImages[nameIconid].myWidth;
						} catch (final Exception exception) {
							/* empty */
						}
					}
					character = '\0';
				}
				if (effectStartIndex == -1) {
					if (character != 0) {
						aStringBuffer3500.append(character);
						width += getCharacterWidth(character);
					}
					if (character == 32) {//Space
						i_176_ = aStringBuffer3500.length();
						i_177_ = width;
						i_178_ = 1;
					}
					if (is != null && width > is[stringsPos < is.length ? stringsPos : is.length - 1] && i_176_ >= 0) {
						strings[stringsPos] = aStringBuffer3500.toString().substring(i_175_, i_176_ - i_178_);
						stringsPos++;
						i_175_ = i_176_;
						i_176_ = -1;
						width -= i_177_;
					}
					if (character == 45) {//-
						i_176_ = aStringBuffer3500.length();
						i_177_ = width;
						i_178_ = 0;
					}
				}
			}
		}
		if (aStringBuffer3500.length() > i_175_) {
			strings[stringsPos] = aStringBuffer3500.toString().substring(i_175_, aStringBuffer3500.length());
			stringsPos++;
		}
		return stringsPos;
	}


	public RSFont(boolean TypeFont, String s) {
		fontPixels = new byte[256][];
		characterWidths = new int[256];
		characterHeights = new int[256];
		characterDrawXOffsets = new int[256];
		characterDrawYOffsets = new int[256];
		characterScreenWidths = new int[256];
		Buffer datBuf = null;
		Buffer idxBuf = null;
		try {
			InputStream in = AssetUtils.INSTANCE.getResource(s + ".dat").openStream();
			InputStream in1 = AssetUtils.INSTANCE.getResource("index.dat").openStream();
			datBuf = new Buffer(IOUtils.toByteArray(in));
			idxBuf = new Buffer(IOUtils.toByteArray(in1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		idxBuf.currentPosition = datBuf.readUShort() + 4;
		int k = idxBuf.readUnsignedByte();

		if (k > 0) {
			idxBuf.currentPosition += 3 * (k - 1);
		}

		for (int l = 0; l < 256; l++) {
			characterDrawXOffsets[l] = idxBuf.readUnsignedByte();
			characterDrawYOffsets[l] = idxBuf.readUnsignedByte();
			int i1 = characterWidths[l] = idxBuf.readUShort();
			int j1 = characterHeights[l] = idxBuf.readUShort();
			int k1 = idxBuf.readUnsignedByte();
			int l1 = i1 * j1;
			fontPixels[l] = new byte[l1];

			if (k1 == 0) {
				for (int i2 = 0; i2 < l1; i2++) {
					fontPixels[l][i2] = datBuf.readSignedByte();
				}
			} else if (k1 == 1) {
				for (int j2 = 0; j2 < i1; j2++) {
					for (int l2 = 0; l2 < j1; l2++) {
						fontPixels[l][j2 + l2 * i1] = datBuf.readSignedByte();
					}
				}
			}

			if (j1 > baseCharacterHeight && l < 128) {
				baseCharacterHeight = j1;
			}

			characterDrawXOffsets[l] = 1;
			characterScreenWidths[l] = i1 + 2;
			int k2 = 0;

			for (int i3 = j1 / 7; i3 < j1; i3++) {
				k2 += fontPixels[l][i3 * i1];
			}

			if (k2 <= j1 / 7) {
				characterScreenWidths[l]--;
				characterDrawXOffsets[l] = 0;
			}

			k2 = 0;

			for (int j3 = j1 / 7; j3 < j1; j3++) {
				k2 += fontPixels[l][(i1 - 1) + j3 * i1];
			}

			if (k2 <= j1 / 7) {
				characterScreenWidths[l]--;
			}
		}

		if (TypeFont) {
			characterScreenWidths[32] = characterScreenWidths[73];
		} else {
			characterScreenWidths[32] = characterScreenWidths[105];
		}
		int i = 2147483647;
		int i_173_ = -2147483648;
		for (int i_174_ = 0; i_174_ < 256; i_174_++) {
			if (characterDrawYOffsets[i_174_] < i && characterHeights[i_174_] != 0) {
				i = characterDrawYOffsets[i_174_];
			}
			if (characterDrawYOffsets[i_174_] + characterHeights[i_174_] > i_173_) {
				i_173_ = characterDrawYOffsets[i_174_] + characterHeights[i_174_];
			}
		}

		anInt3494 = baseCharacterHeight - i;
		anInt3512 = i_173_ - baseCharacterHeight;
	}

	public String[] wrap(String text, int maximumWidth) {
		String[] words = text.split(" ");

		if (words.length == 0) {
			return new String[] { text };
		}

		List<String> lines = new ArrayList<>();

		String line = new String();

		int lineWidth = 0;

		int spaceWidth = getTextWidth(" ");

		for (String word : words) {
			if (word.isEmpty()) {
				continue;
			}
			int wordWidth = getTextWidth(word);
			boolean isLastWord = word.equals(words[words.length - 1]);

			if (wordWidth + lineWidth >= maximumWidth && !isLastWord) {
				lines.add(line.trim());
				line = new String(word.concat(" "));
				lineWidth = wordWidth + spaceWidth;
			} else if (isLastWord) {
				if (wordWidth + lineWidth > maximumWidth) {
					lines.add(line.trim());
					lines.add(word);
				} else {
					lines.add(line.concat(word));
				}
			} else {
				line = line.concat(word).concat(" ");
				lineWidth += wordWidth + spaceWidth;
			}
		}

		return lines.toArray(new String[lines.size()]);
	}

	public void drawStringMoveY(String string, int drawX, int drawY, int color, int shadow, int randomMod,
			int randomMod2) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			double d = 7.0 - randomMod2 / 8.0;
			if (d < 0.0) {
				d = 0.0;
			}
			int[] yOffset = new int[string.length()];
			for (int index = 0; index < string.length(); index++) {
				yOffset[index] = (int) (Math.sin(index / 1.5 + randomMod) * d);
			}
			drawBaseStringMoveXY(string, drawX - getTextWidth(string) / 2, drawY, null, yOffset);
		}
	}

	public void setDefaultTextEffectValues(int color, int shadow, int trans) {
		strikethroughColor = -1;
		underlineColor = -1;
		textShadowColor = defaultShadow = shadow;
		textColor = defaultColor = color;
		transparency = defaultTransparency = trans;
		anInt4178 = 0;
		anInt4175 = 0;
	}

	public static int method1014(byte[][] is, byte[][] is_27_, int[] is_28_, int[] is_29_, int[] is_30_, int i,
			int i_31_) {
		int i_32_ = is_28_[i];
		int i_33_ = i_32_ + is_30_[i];
		int i_34_ = is_28_[i_31_];
		int i_35_ = i_34_ + is_30_[i_31_];
		int i_36_ = i_32_;
		if (i_34_ > i_32_) {
			i_36_ = i_34_;
		}
		int i_37_ = i_33_;
		if (i_35_ < i_33_) {
			i_37_ = i_35_;
		}
		int i_38_ = is_29_[i];
		if (is_29_[i_31_] < i_38_) {
			i_38_ = is_29_[i_31_];
		}
		byte[] is_39_ = is_27_[i];
		byte[] is_40_ = is[i_31_];
		int i_41_ = i_36_ - i_32_;
		int i_42_ = i_36_ - i_34_;
		for (int i_43_ = i_36_; i_43_ < i_37_; i_43_++) {
			int i_44_ = is_39_[i_41_++] + is_40_[i_42_++];
			if (i_44_ < i_38_) {
				i_38_ = i_44_;
			}
		}
		return -i_38_;
	}

	public void drawCenteredStringMoveXY(String string, int drawX, int drawY, int color, int shadow, int randomMod) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			int[] xMods = new int[string.length()];
			int[] yMods = new int[string.length()];
			for (int index = 0; index < string.length(); index++) {
				xMods[index] = (int) (Math.sin(index / 5.0 + randomMod / 5.0) * 5.0);
				yMods[index] = (int) (Math.sin(index / 3.0 + randomMod / 5.0) * 5.0);
			}
			drawBaseStringMoveXY(string, drawX - getTextWidth(string) / 2, drawY, xMods, yMods);
		}
	}

	public void drawCenteredStringMoveY(String class100, int drawX, int drawY, int color, int shadow, int i_54_) {
		if (class100 != null) {
			setColorAndShadow(color, shadow);
			int[] yOffset = new int[class100.length()];
			for (int index = 0; index < class100.length(); index++) {
				yOffset[index] = (int) (Math.sin(index / 2.0 + i_54_ / 5.0) * 5.0);
			}
			drawBaseStringMoveXY(class100, drawX - getTextWidth(class100) / 2, drawY, null, yOffset);
		}
	}

	public static void unpackImages(Sprite[] icons, Sprite[] clan, Sprite[] iconPack) {
		chatImages = icons;
		clanImages = clan;
		RSFont.iconPack = iconPack;
	}

	public void drawString(String string, int x, int y, int color, int shadow, int trans) {
		if (transparency < 0 || transparency > 256) {
			transparency = defaultTransparency;
		}
		setColorAndShadow(color, shadow);
		transparency = trans;
		drawBasicString(string, x, y);
	}

	public void drawCenteredString(String string, int drawX, int drawY, int color, int shadow, int trans) {
		if (transparency < 0 || transparency > 256) {
			transparency = defaultTransparency;
		}
		if (string != null) {
			setColorAndShadow(color, shadow);
			string = handleOldSyntax(string);
			transparency = trans;
			drawBasicString(string, drawX - getTextWidth(string) / 2, drawY);
		}
	}
	public void drawCenteredString(String s, int i, int j) {
		if (s != null)
			drawBasicString(s, i - getTextWidth(s) / 2, j);
	}
	public void drawBasicString(String string, int drawX, int drawY) {
		drawBasicString(string, drawX, drawY, true);
	}

	public void drawBasicString(String string, int drawX, int drawY, boolean textEffects) {
		drawY -= baseCharacterHeight;
		int startIndex = -1;
		string = handleOldSyntax(string);
		for (int currentCharacter = 0; currentCharacter < string.length(); currentCharacter++) {
			int character = string.charAt(currentCharacter);
			if (character > 255) {
				character = 32;
			}

			// Effect
			if (textEffects) {
				if (character == 60) {
					startIndex = currentCharacter;
				} else {
					if (character == 62 && startIndex != -1) {
						String effectString = string.substring(startIndex + 1, currentCharacter);
						startIndex = -1;
						if (effectString.equals(startEffect)) {
							character = 60;
						} else if (effectString.equals(endEffect)) {
							character = 62;
						} else if (effectString.equals(aRSString_4135)) {
							character = 160;
						} else if (effectString.equals(aRSString_4162)) {
							character = 173;
						} else if (effectString.equals(aRSString_4165)) {
							character = 215;
						} else if (effectString.equals(aRSString_4147)) {
							character = 128;
						} else if (effectString.equals(aRSString_4163)) {
							character = 169;
						} else if (effectString.equals(aRSString_4169)) {
							character = 174;
						} else {
							if (effectString.startsWith(startImage)) {
								try {
									String sub = effectString.substring(4);
									if (sub.length() > 0) {
										int imageId = Integer.parseInt(sub);
										if (imageId > -1) {
											Sprite icon = chatImages[imageId];

											int iconModY = icon.myHeight;
											if (imageId >= 40 && imageId <= 50) {
												icon = SpriteLoader.fetchAnimatedSprite(Signlink.getCacheDirectory()+"/sprites/gifs/" +imageId+".gif").getInstance(icon.myWidth, icon.myHeight);
												iconModY -=2;
											}
											if (transparency == 256) {
												icon.drawAdvancedSprite(drawX, (drawY + baseCharacterHeight - iconModY));
											} else {
												icon.drawAdvancedSprite(drawX, (drawY + baseCharacterHeight - iconModY), transparency);
											}
											drawX += icon.myWidth;
										}
									}
								} catch (Exception exception) {
									exception.printStackTrace();
								}
							} else if (effectString.startsWith(startIcon)) {
								try {
									String sub = effectString.substring(5);
									if (sub.length() > 0) {
										int imageId = Integer.parseInt(sub);
										if (imageId > -1) {
											Sprite icon = iconPack[imageId];
											int iconModY = icon.myHeight;
											if (transparency == 256) {
												icon.drawSprite(drawX, (drawY + baseCharacterHeight - iconModY));
											} else {
												icon.drawSprite(drawX, (drawY + baseCharacterHeight - iconModY), transparency);
											}
											drawX += icon.myWidth;
										}
									}
								} catch (Exception exception) {
									exception.printStackTrace();
								}
							} else if (effectString.startsWith(startOldschoolImage)) {
								try {
									String sub = effectString.substring(8);
									if (sub.length() > 0) {
										int imageId = Integer.parseInt(sub);
										if (imageId > -1) {
											Sprite icon = Client.instance.getOldSchoolSprite(imageId);
											int iconModY = icon.myHeight;
											if (transparency == 256) {
												icon.drawSprite(drawX, (drawY + baseCharacterHeight - iconModY));
											} else {
												icon.drawSprite(drawX, (drawY + baseCharacterHeight - iconModY), transparency);
											}
											drawX += icon.myWidth;
										}
									}
								} catch (Exception exception) {
									exception.printStackTrace();
								}
							} else if (effectString.startsWith(startClanImage)) {
								try {
									String sub = effectString.substring(5);
									if (sub.length() > 0) {
										int imageId = Integer.parseInt(sub);
										if (imageId > -1) {
											Sprite icon = clanImages[imageId];
											int iconModY = icon.myHeight + icon.drawOffsetY + 1;
											if (transparency == 256) {
												icon.drawSprite(drawX, (drawY + baseCharacterHeight - iconModY));
											} else {
												icon.drawSprite(drawX, (drawY + baseCharacterHeight - iconModY), transparency);
											}
											drawX += 11;
										}
									}
								} catch (Exception exception) {
									exception.printStackTrace();
								}
							} else {
								setTextEffects(effectString);
							}
							continue;
						}
					}
				}
			}

			// Draw character
			if (startIndex == -1) {
				int width = characterWidths[character];
				int height = characterHeights[character];
				if (character != 32) {
					if (transparency == 256) {
						if (textShadowColor != -1) {
							drawCharacter(character, drawX + characterDrawXOffsets[character] + 1,
									drawY + characterDrawYOffsets[character] + 1, width, height, textShadowColor,
									true);
						}
						drawCharacter(character, drawX + characterDrawXOffsets[character],
								drawY + characterDrawYOffsets[character], width, height, textColor, false);
					} else {
						if (textShadowColor != -1) {
							drawTransparentCharacter(character, drawX + characterDrawXOffsets[character] + 1,
									drawY + characterDrawYOffsets[character] + 1, width, height, textShadowColor,
									transparency, true);
						}
						drawTransparentCharacter(character, drawX + characterDrawXOffsets[character],
								drawY + characterDrawYOffsets[character], width, height, textColor, transparency,
								false);
					}
				} else if (anInt4178 > 0) {
					anInt4175 += anInt4178;
					drawX += anInt4175 >> 8;
					anInt4175 &= 0xff;
				}
				int lineWidth = characterScreenWidths[character];
				if (strikethroughColor != -1) {
					drawHorizontalLine(drawY + (int) ((double) baseCharacterHeight * 0.6999999999),
							strikethroughColor, lineWidth, drawX);
				}
				if (underlineColor != -1) {

					drawHorizontalLine(drawX, drawY + baseCharacterHeight, lineWidth, underlineColor);
				}
				drawX += lineWidth;
			}

		}
	}

	public void drawRAString(String string, int drawX, int drawY, int color, int shadow) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			drawBasicString(string, drawX - getTextWidth(string), drawY);
		}
	}

	public void drawBaseStringMoveXY(String string, int drawX, int drawY, int[] xModifier, int[] yModifier) {
		drawY -= baseCharacterHeight;
		int startIndex = -1;
		int modifierOffset = 0;
		for (int currentCharacter = 0; currentCharacter < string.length(); currentCharacter++) {
			int character = string.charAt(currentCharacter);
			if (character == 60) {
				startIndex = currentCharacter;
			} else {
				if (character == 62 && startIndex != -1) {
					String effectString = string.substring(startIndex + 1, currentCharacter);
					startIndex = -1;
					if (effectString.equals(startEffect)) {
						character = 60;
					} else if (effectString.equals(endEffect)) {
						character = 62;
					} else if (effectString.equals(aRSString_4135)) {
						character = 160;
					} else if (effectString.equals(aRSString_4162)) {
						character = 173;
					} else if (effectString.equals(aRSString_4165)) {
						character = 215;
					} else if (effectString.equals(aRSString_4147)) {
						character = 128;
					} else if (effectString.equals(aRSString_4163)) {
						character = 169;
					} else if (effectString.equals(aRSString_4169)) {
						character = 174;
					} else {
						if (effectString.startsWith(startImage)) {
							try {
								int xModI;
								if (xModifier != null) {
									xModI = xModifier[modifierOffset];
								} else {
									xModI = 0;
								}
								int yMod;
								if (yModifier != null) {
									yMod = yModifier[modifierOffset];
								} else {
									yMod = 0;
								}
								modifierOffset++;
								int iconId = Integer.valueOf(effectString.substring(4));
								Sprite icon = chatImages[iconId];
								int iconOffsetY = icon.maxHeight;

								if (iconId >= 40 && iconId <= 50) {
									icon = SpriteLoader.fetchAnimatedSprite(Signlink.getCacheDirectory() + "/sprites/gifs/" + iconId + ".gif").getInstance(icon.myWidth, icon.myHeight);
									iconId -= 2;
								}

								if (transparency == 256) {
									icon.drawAdvancedSprite(drawX + xModI, (drawY + baseCharacterHeight - iconOffsetY + yMod));
								} else {
									icon.drawAdvancedSprite(drawX + xModI, (drawY + baseCharacterHeight - iconOffsetY + yMod),
											transparency);
								}
								drawX += icon.maxWidth;
							} catch (Exception exception) {
								exception.printStackTrace();
							}
						} else if (effectString.startsWith(startOldschoolImage)) {
								try {
									int xModI;
									if (xModifier != null) {
										xModI = xModifier[modifierOffset];
									} else {
										xModI = 0;
									}
									int yMod;
									if (yModifier != null) {
										yMod = yModifier[modifierOffset];
									} else {
										yMod = 0;
									}
									modifierOffset++;
									int iconId = Integer.valueOf(effectString.substring(8));
									Sprite icon = Client.getInstance().getOldSchoolSprite(iconId);
									int iconOffsetY = icon.maxHeight;

									if (transparency == 256) {
										icon.drawAdvancedSprite(drawX + xModI, (drawY + baseCharacterHeight - iconOffsetY + yMod));
									} else {
										icon.drawAdvancedSprite(drawX + xModI, (drawY + baseCharacterHeight - iconOffsetY + yMod),
												transparency);
									}
									drawX += icon.maxWidth;
								} catch (Exception exception) {
									exception.printStackTrace();
								}
						} else if (effectString.startsWith(startIcon)) {
							try {
								int xModI;
								if (xModifier != null) {
									xModI = xModifier[modifierOffset];
								} else {
									xModI = 0;
								}
								int yMod;
								if (yModifier != null) {
									yMod = yModifier[modifierOffset];
								} else {
									yMod = 0;
								}
								modifierOffset++;
								int iconId = Integer.valueOf(effectString.substring(5));
								Sprite icon = iconPack[iconId];
								int iconOffsetY = icon.maxHeight;
								if (transparency == 256) {
									icon.drawSprite(drawX + xModI, (drawY + baseCharacterHeight - iconOffsetY + yMod));
								} else {
									icon.drawSprite(drawX + xModI, (drawY + baseCharacterHeight - iconOffsetY + yMod),
											transparency);
								}
								drawX += icon.maxWidth;
							} catch (Exception exception) {
								exception.printStackTrace();
							}
						} else {
							setTextEffects(effectString);
						}
						continue;
					}
				}
				if (startIndex == -1) {
					int width = characterWidths[character];
					int height = characterHeights[character];
					int xOff;
					if (xModifier != null) {
						xOff = xModifier[modifierOffset];
					} else {
						xOff = 0;
					}
					int yOff;
					if (yModifier != null) {
						yOff = yModifier[modifierOffset];
					} else {
						yOff = 0;
					}
					modifierOffset++;
					if (character != 32) {
						if (transparency == 256) {
							if (textShadowColor != -1) {
								drawCharacter(character, (drawX + characterDrawXOffsets[character] + 1 + xOff),
										(drawY + characterDrawYOffsets[character] + 1 + yOff), width, height,
										textShadowColor, true);
							}
							drawCharacter(character, drawX + characterDrawXOffsets[character] + xOff,
									drawY + characterDrawYOffsets[character] + yOff, width, height, textColor, false);
						} else {
							if (textShadowColor != -1) {
								drawTransparentCharacter(character,
										(drawX + characterDrawXOffsets[character] + 1 + xOff),
										(drawY + characterDrawYOffsets[character] + 1 + yOff), width, height,
										textShadowColor, transparency, true);
							}
							drawTransparentCharacter(character, drawX + characterDrawXOffsets[character] + xOff,
									drawY + characterDrawYOffsets[character] + yOff, width, height, textColor,
									transparency, false);
						}
					} else if (anInt4178 > 0) {
						anInt4175 += anInt4178;
						drawX += anInt4175 >> 8;
						anInt4175 &= 0xff;
					}
					int i_109_ = characterScreenWidths[character];
					if (strikethroughColor != -1) {
						Rasterizer2D.drawHorizontalLine(drawX, drawY + (int) (baseCharacterHeight * 0.7), i_109_,
								strikethroughColor);
					}
					if (underlineColor != -1) {
						Rasterizer2D.drawHorizontalLine(drawX, drawY + baseCharacterHeight, i_109_, underlineColor);
					}
					drawX += i_109_;
				}
			}
		}
	}

	public void setTextEffects(String string) {
		do {
			try {
				if (string.startsWith(startColor)) {
					String color = string.substring(4);
					textColor = color.length() < 6 ? Color.decode(color).getRGB() : Integer.parseInt(color, 16);
				} else if (string.equals(endColor)) {
					textColor = defaultColor;
				} else if (string.startsWith(startTransparency)) {
					transparency = Integer.valueOf(string.substring(6));
				} else if (string.equals(endTransparency)) {
					transparency = defaultTransparency;
				} else if (string.startsWith(startStrikethrough)) {
					strikethroughColor = Integer.valueOf(string.substring(4));
				} else if (string.equals(defaultStrikethrough)) {
					strikethroughColor = 8388608;
				} else if (string.equals(endStrikethrough)) {
					strikethroughColor = -1;
				} else if (string.startsWith(startUnderline)) {
					underlineColor = Integer.valueOf(string.substring(2));
				} else if (string.equals(startDefaultUnderline)) {
					underlineColor = 0;
				} else if (string.equals(endUnderline)) {
					underlineColor = -1;
				} else if (string.startsWith(startShadow)) {
					textShadowColor = Integer.valueOf(string.substring(5));
				} else if (string.equals(startDefaultShadow)) {
					textShadowColor = 0;
				} else if (string.equals(endShadow)) {
					textShadowColor = defaultShadow;
				} else {
					if (!string.equals(lineBreak)) {
						break;
					}
					setDefaultTextEffectValues(defaultColor, defaultShadow, defaultTransparency);
				}
			} catch (Exception exception) {
				break;
			}
			break;
		} while (false);
	}

	public void setColorAndShadow(int color, int shadow) {
		strikethroughColor = -1;
		underlineColor = -1;
		textShadowColor = defaultShadow = shadow;
		textColor = defaultColor = color;
		transparency = defaultTransparency = 256;
		anInt4178 = 0;
		anInt4175 = 0;
	}

	public void setColorAndShadowAndTrans(int color, int shadow, int trasparency) {
		strikethroughColor = -1;
		underlineColor = -1;
		textShadowColor = defaultShadow = shadow;
		textColor = defaultColor = color;
		transparency = trasparency == 0 ? 256 : transparency;
		defaultTransparency = transparency;
		;
		anInt4178 = 0;
		anInt4175 = 0;
	}

	public int getTextWidth(String string) {
		if (string == null) {
			return 0;
		}
		string = handleOldSyntax(string);
		int startIndex = -1;
		int finalWidth = 0;
		for (int currentCharacter = 0; currentCharacter < string.length(); currentCharacter++) {
			int character = string.charAt(currentCharacter);
			if (character > 255) {
				character = 32;
			}
			if (character == 60) {
				startIndex = currentCharacter;
			} else {
				if (character == 62 && startIndex != -1) {
					String effectString = string.substring(startIndex + 1, currentCharacter);
					startIndex = -1;
					if (effectString.equals(startEffect)) {
						character = 60;
					} else if (effectString.equals(endEffect)) {
						character = 62;
					} else if (effectString.equals(aRSString_4135)) {
						character = 160;
					} else if (effectString.equals(aRSString_4162)) {
						character = 173;
					} else if (effectString.equals(aRSString_4165)) {
						character = 215;
					} else if (effectString.equals(aRSString_4147)) {
						character = 128;
					} else if (effectString.equals(aRSString_4163)) {
						character = 169;
					} else if (effectString.equals(aRSString_4169)) {
						character = 174;
					} else {
						if (effectString.startsWith(startImage)) {
							try {// <img=
								int iconId = Integer.valueOf(effectString.substring(4));

								Sprite icon = chatImages[iconId];
								if (iconId >= 40 && iconId <= 50) {
									icon = SpriteLoader.fetchAnimatedSprite(Signlink.getCacheDirectory()+"/sprites/gifs/" +iconId+".gif").getInstance(icon.myWidth, icon.myHeight);
								}
								finalWidth += icon.maxWidth;
							} catch (Exception exception) {
								exception.printStackTrace();
							}
						}
						if (effectString.startsWith(startClanImage)) {
							try {// <img=
								int iconId = Integer.valueOf(effectString.substring(5));
								if(iconId >= 0 && iconId < clanImages.length)
									finalWidth += clanImages[iconId].maxWidth;
							} catch (Exception exception) {
								exception.printStackTrace();
							}
						}
						if (effectString.startsWith(startIcon)) {
							try {// <img=
								int iconId = Integer.valueOf(effectString.substring(5));
								finalWidth += iconPack[iconId].maxWidth;
							} catch (Exception exception) {
								exception.printStackTrace();
							}
						}
						continue;
					}
				}
				if (startIndex == -1) {
					finalWidth += characterScreenWidths[character];
				}
			}
		}
		return finalWidth;
	}

	@Override
	public int getBaseline() {
		return 0;
	}

	@Override
	public void drawTextLeftAligned(String text, int x, int y, int fontColor, int shadowColor) {
		drawBasicString(text, x, y, fontColor, shadowColor, true);
	}

	public void drawBasicString(String string, int drawX, int drawY, int color, int shadow) {
		drawBasicString(string, drawX, drawY, color, shadow, true);
	}

	public void drawBasicString(String string, int drawX, int drawY, int color, int shadow, boolean textEffects) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			drawBasicString(string, drawX, drawY, textEffects);
		}
	}

	public void drawCenteredString(String string, int drawX, int drawY, int color, int shadow) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			string = handleOldSyntax(string);
			drawBasicString(string, drawX - getTextWidth(string) / 2, drawY);
		}
	}

	private static String replace(String text, String replace, String replaceWith) {
		if (text.contains(replace)) {
			return text.replaceAll(replace, replaceWith);
		}
		return text;
	}

	public static String handleOldSyntax(String text) {
		if(text == null)
			return null;
		if (text.contains("@")) {
			text = replace(text, "@pur@", "<col=A10081>");
			text = replace(text, "@red@", "<col=ff0000>");
			text = replace(text, "@gre@", "<col=65280>");
			text = replace(text, "@blu@", "<col=255>");
			text = replace(text, "@bl2@", "<col=0F0085>");
			text = replace(text, "@bl3@", "<col=00AFFF>");
			text = replace(text, "@yel@", "<col=ffff00>");
			text = replace(text, "@cya@", "<col=65535>");
			text = replace(text, "@mag@", "<col=ff00ff>");
			text = replace(text, "@whi@", "<col=ffffff>");
			text = replace(text, "@lre@", "<col=ff9040>");
			text = replace(text, "@dre@", "<col=800000>");
			text = replace(text, "@bla@", "<col=0>");
			text = replace(text, "@or0@", "<col=A67711>");
			text = replace(text, "@or1@", "<col=ffb000>");
			text = replace(text, "@or2@", "<col=ff7000>");
			text = replace(text, "@or3@", "<col=ff3000>");
			text = replace(text, "@or4@", "<col=FF9933>");
			text = replace(text, "@gr0@", "<col=148200>");
			text = replace(text, "@gr1@", "<col=c0ff00>");
			text = replace(text, "@gr2@", "<col=80ff00>");
			text = replace(text, "@gr3@", "<col=40ff00>");
			text = replace(text, "@OR0", "<col=<A67711>");
			text = replace(text, "@PUR@", "<col=A10081>");
			text = replace(text, "@RED@", "<col=ffff00>");
			text = replace(text, "@GRE@", "<col=65280>");
			text = replace(text, "@BLU@", "<col=255>");
			text = replace(text, "@YEL@", "<col=ff0000>");
			text = replace(text, "@CYA@", "<col=65535>");
			text = replace(text, "@MAG@", "<col=ff00ff>");
			text = replace(text, "@WHI@", "<col=ffffff>");
			text = replace(text, "@LRE@", "<col=ff9040>");
			text = replace(text, "@DRE@", "<col=800000>");
			text = replace(text, "@BLA@", "<col=0>");
			text = replace(text, "@OR1@", "<col=ffb000>");
			text = replace(text, "@OR2@", "<col=ff7000>");
			text = replace(text, "@OR3@", "<col=ff3000>");
			text = replace(text, "@GR1@", "<col=c0ff00>");
			text = replace(text, "@GR2@", "<col=80ff00>");
			text = replace(text, "@GR3@", "<col=40ff00>");
			if (text.contains("@cr")) {
				text = replace(text, "@cr0@", "<img=39>");//mod
				text = replace(text, "@cr1@", "<img=37>");//admin
				text = replace(text, "@cr2@", "<img=36>");//owner
				text = replace(text, "@cr3@", "<img=37>");//
				text = replace(text, "@cr4@", "<img=48>");//$20
				text = replace(text, "@cr5@", "<img=5>");//
				text = replace(text, "@cr6@", "<img=47>");//$50
				text = replace(text, "@cr7@", "<img=42>");//$1000
				text = replace(text, "@cr8@", "<img=46>");//$1000
				text = replace(text, "@cr9@", "<img=9>");//HC Iron
				text = replace(text, "@cr10@", "<img=38>");//Support
				text = replace(text, "@cr11@", "<img=11>");//hitbox
				text = replace(text, "@cr12@", "<img=12>");//im
				text = replace(text, "@cr13@", "<img=13>");//uim
				text = replace(text, "@cr14@", "<img=14>");//yt
				text = replace(text, "@cr15@", "<img=15>");//Owner
				text = replace(text, "@cr16@", "<img=45>");//$250
				text = replace(text, "@cr17@", "<img=44>");//$500
				text = replace(text, "@cr18@", "<img=18>");//
				text = replace(text, "@cr19@", "<img=19>");
				text = replace(text, "@cr20@", "<img=20>");//Notification
				text = replace(text, "@cr21@", "<img=21>");
				text = replace(text, "@cr22@", "<img=22>");//OSRS
				text = replace(text, "@cr23@", "<img=23>");
				text = replace(text, "@cr24@", "<img=24>");//Rogue
				text = replace(text, "@cr25@", "<img=25>");//Rogue ironman
				text = replace(text, "@cr26@", "<img=26>");//Rogue HC Ironman
				text = replace(text, "@cr27@", "<img=27>");//GIM
				text = replace(text, "@cr28@", "<img=28>");//event
				text = replace(text, "@cr29@", "<img=28>");
				text = replace(text, "@cr30@", "<img=30>");//osrs iron
				text = replace(text, "@cr31@", "<img=43>");
				text = replace(text, "@cr32@", "<img=41>");//$1500
				text = replace(text, "@cr33@", "<img=40>");//$2000
				text = replace(text, "@cr34@", "<img=49>");
				text = replace(text, "@cr35@", "<img=35>");
				text = replace(text, "@cr36@", "<img=36>");
				text = replace(text, "@cr37@", "<img=37>");
				text = replace(text, "@cr38@", "<img=38>");
				text = replace(text, "@cr39@", "<img=39>");
				text = replace(text, "@cr40@", "<img=40>");
				text = replace(text, "@cr41@", "<img=41>");
				text = replace(text, "@cr42@", "<img=42>");
				text = replace(text, "@cr43@", "<img=43>");
				text = replace(text, "@cr44@", "<img=44>");
				text = replace(text, "@cr45@", "<img=45>");
				text = replace(text, "@cr46@", "<img=46>");
				text = replace(text, "@cr47@", "<img=47>");
				text = replace(text, "@cr48@", "<img=92>");
				text = replace(text, "@cr49@", "<img=49>");
				text = replace(text, "@cr50@", "<img=92>");
				text = replace(text, "@cr51@", "<img=92>");
				text = replace(text, "@cr52@", "<img=92>");
				text = replace(text, "@cr53@", "<img=92>");
				text = replace(text, "@cr54@", "<img=92>");
				text = replace(text, "@cr55@", "<img=92>");
				text = replace(text, "@cr56@", "<img=92>");
				text = replace(text, "@cr57@", "<img=92>");
				text = replace(text, "@cr58@", "<img=92>");
				text = replace(text, "@cr59@", "<img=92>");
				text = replace(text, "@cr60@", "<img=92>");
				text = replace(text, "@cr61@", "<img=92>");
				text = replace(text, "@cr62@", "<img=92>");
				text = replace(text, "@cr63@", "<img=92>");
				text = replace(text, "@cr64@", "<img=92>");
				text = replace(text, "@cr65@", "<img=92>");
				text = replace(text, "@cr66@", "<img=92>");
				text = replace(text, "@cr67@", "<img=92>");
				text = replace(text, "@cr68@", "<img=92>");
				text = replace(text, "@cr69@", "<img=92>");
				text = replace(text, "@cr70@", "<img=92>");
				text = replace(text, "@cr71@", "<img=92>");
				text = replace(text, "@cr72@", "<img=92>");
				text = replace(text, "@cr73@", "<img=92>");
				text = replace(text, "@cr74@", "<img=92>");
				text = replace(text, "@cr75@", "<img=92>");
				text = replace(text, "@cr76@", "<img=92>");
				text = replace(text, "@cr77@", "<img=92>");
				text = replace(text, "@cr78@", "<img=92>");
				text = replace(text, "@cr79@", "<img=92>");
				text = replace(text, "@cr80@", "<img=92>");
				text = replace(text, "@cr81@", "<img=92>");
				text = replace(text, "@cr82@", "<img=92>");
				text = replace(text, "@cr83@", "<img=92>");
				text = replace(text, "@cr84@", "<img=92>");
				text = replace(text, "@cr85@", "<img=92>");
				text = replace(text, "@cr86@", "<img=92>");
				text = replace(text, "@cr87@", "<img=92>");
				text = replace(text, "@cr88@", "<img=92>");
				text = replace(text, "@cr89@", "<img=92>");
				text = replace(text, "@cr90@", "<img=92>");
				text = replace(text, "@cr91@", "<img=92>");
				text = replace(text, "@cr92@", "<img=92>");
				text = replace(text, "@cr93@", "<img=93>");
			}
		}
		return text;
	}

	public static void nullLoader() {
		startEffect = null;
		endEffect = null;
		aRSString_4135 = null;
		aRSString_4162 = null;
		aRSString_4165 = null;
		aRSString_4147 = null;
		aRSString_4163 = null;
		aRSString_4169 = null;
		startImage = null;
		startOldschoolImage = null;
		startIcon = null;
		lineBreak = null;
		startColor = null;
		endColor = null;
		startTransparency = null;
		endTransparency = null;
		startUnderline = null;
		startDefaultUnderline = null;
		endUnderline = null;
		startShadow = null;
		startDefaultShadow = null;
		endShadow = null;
		startStrikethrough = null;
		defaultStrikethrough = null;
		endStrikethrough = null;
		aRSString_4143 = null;
		splitTextStrings = null;
	}

	public static void createTransparentCharacterPixels(int[] is, byte[] is_0_, int i, int i_1_, int i_2_, int i_3_,
			int i_4_, int i_5_, int i_6_, int i_7_) {
		i = ((i & 0xff00ff) * i_7_ & ~0xff00ff) + ((i & 0xff00) * i_7_ & 0xff0000) >> 8;
		i_7_ = 256 - i_7_;
		for (int i_8_ = -i_4_; i_8_ < 0; i_8_++) {
			for (int i_9_ = -i_3_; i_9_ < 0; i_9_++) {
				if (is_0_[i_1_++] != 0) {
					int i_10_ = is[i_2_];
					drawAlpha(is, i_2_++, (((i_10_ & 0xff00ff) * i_7_ & 0xff00ff00) + ((i_10_ & 0xff00) * i_7_ & 0xff0000) >> 8)
							+ i, 255);
				} else {
					i_2_++;
				}
			}
			i_2_ += i_5_;
			i_1_ += i_6_;
		}
	}

	public void drawTransparentCharacter(int i, int i_11_, int i_12_, int i_13_, int i_14_, int i_15_, int i_16_,
			boolean bool) {
		int i_17_ = i_11_ + i_12_ * Rasterizer2D.width;
		int i_18_ = Rasterizer2D.width - i_13_;
		int i_19_ = 0;
		int i_20_ = 0;
		if (i_12_ < Rasterizer2D.topY) {
			int i_21_ = Rasterizer2D.topY - i_12_;
			i_14_ -= i_21_;
			i_12_ = Rasterizer2D.topY;
			i_20_ += i_21_ * i_13_;
			i_17_ += i_21_ * Rasterizer2D.width;
		}
		if (i_12_ + i_14_ > Rasterizer2D.bottomY) {
			i_14_ -= i_12_ + i_14_ - Rasterizer2D.bottomY;
		}
		if (i_11_ < Rasterizer2D.leftX) {
			int i_22_ = Rasterizer2D.leftX - i_11_;
			i_13_ -= i_22_;
			i_11_ = Rasterizer2D.leftX;
			i_20_ += i_22_;
			i_17_ += i_22_;
			i_19_ += i_22_;
			i_18_ += i_22_;
		}
		if (i_11_ + i_13_ > Rasterizer2D.bottomX) {
			int i_23_ = i_11_ + i_13_ - Rasterizer2D.bottomX;
			i_13_ -= i_23_;
			i_19_ += i_23_;
			i_18_ += i_23_;
		}
		if (i_13_ > 0 && i_14_ > 0) {
			createTransparentCharacterPixels(Rasterizer2D.pixels, fontPixels[i], i_15_, i_20_, i_17_, i_13_, i_14_,
					i_18_, i_19_, i_16_);
		}
	}

	private void createCharacterPixels(int ai[], byte abyte0[], int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1 = -(l >> 2);
		l = -(l & 3);
		for(int i2 = -i1; i2 < 0; i2++) {
			for(int j2 = l1; j2 < 0; j2++) {
				if(abyte0[j++] != 0)
					drawAlpha(ai, k++, i, 255);
				else
					k++;
				if(abyte0[j++] != 0)
					drawAlpha(ai, k++, i, 255);
				else
					k++;
				if(abyte0[j++] != 0)
					drawAlpha(ai, k++, i, 255);
				else
					k++;
				if(abyte0[j++] != 0)
					drawAlpha(ai, k++, i, 255);
				else
					k++;
			}
			for(int k2 = l; k2 < 0; k2++)
				if(abyte0[j++] != 0)
					drawAlpha(ai, k++, i, 255);
				else
					k++;

			k += j1;
			j += k1;
		}
	}


	public void drawCharacter(int character, int i_35_, int i_36_, int i_37_, int i_38_, int i_39_, boolean bool) {
		int i_40_ = i_35_ + i_36_ * Rasterizer2D.width;
		int i_41_ = Rasterizer2D.width - i_37_;
		int i_42_ = 0;
		int i_43_ = 0;
		if (i_36_ < Rasterizer2D.topY) {
			int i_44_ = Rasterizer2D.topY - i_36_;
			i_38_ -= i_44_;
			i_36_ = Rasterizer2D.topY;
			i_43_ += i_44_ * i_37_;
			i_40_ += i_44_ * Rasterizer2D.width;
		}
		if (i_36_ + i_38_ > Rasterizer2D.bottomY) {
			i_38_ -= i_36_ + i_38_ - Rasterizer2D.bottomY;
		}
		if (i_35_ < Rasterizer2D.leftX) {
			int i_45_ = Rasterizer2D.leftX - i_35_;
			i_37_ -= i_45_;
			i_35_ = Rasterizer2D.leftX;
			i_43_ += i_45_;
			i_40_ += i_45_;
			i_42_ += i_45_;
			i_41_ += i_45_;
		}
		if (i_35_ + i_37_ > Rasterizer2D.bottomX) {
			int i_46_ = i_35_ + i_37_ - Rasterizer2D.bottomX;
			i_37_ -= i_46_;
			i_42_ += i_46_;
			i_41_ += i_46_;
		}
		if (i_37_ > 0 && i_38_ > 0) {
			createCharacterPixels(Rasterizer2D.pixels, fontPixels[character], i_39_, i_43_, i_40_, i_37_, i_38_, i_41_,
					i_42_);

		}
	}

	static {
		startTransparency = "trans=";
		startStrikethrough = "str=";
		startDefaultShadow = "shad";
		startColor = "col=";
		lineBreak = "br";
		defaultStrikethrough = "str";
		endUnderline = "/currentY";
		startImage = "img=";
		startOldschoolImage = "osrsimg=";
		startIcon = "icon=";
		startClanImage = "clan=";
		startShadow = "shad=";
		startUnderline = "currentY=";
		endColor = "/col";
		startDefaultUnderline = "currentY";
		endTransparency = "/trans";

		aRSString_4143 = Integer.toString(100);
		aRSString_4135 = "nbsp";
		aRSString_4169 = "reg";
		aRSString_4165 = "times";
		aRSString_4162 = "shy";
		aRSString_4163 = "copy";
		endEffect = "gt";
		aRSString_4147 = "euro";
		startEffect = "lt";
		defaultTransparency = 256;
		defaultShadow = -1;
		anInt4175 = 0;
		textShadowColor = -1;
		textColor = 0;
		defaultColor = 0;
		strikethroughColor = -1;
		splitTextStrings = new String[100];
		underlineColor = -1;
		anInt4178 = 0;
		transparency = 256;
	}
}