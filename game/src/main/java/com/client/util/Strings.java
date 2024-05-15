package com.client.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

public final class Strings {

    private static final String[] searchList = {
            "@pur@",
            "@red@",
            "@gre@",
            "@blu@",
            "@yel@",
            "@cya@",
            "@mag@",
            "@whi@",
            "@lre@",
            "@dre@",
            "@bla@",
            "@or0@",
            "@or1@",
            "@or2@",
            "@or3@",
            "@gr0@",
            "@gr1@",
            "@gr2@",
            "@gr3@",
            "@gra@",
            "@OR0",
            "@PUR@",
            "@RED@",
            "@GRE@",
            "@BLU@",
            "@YEL@",
            "@CYA@",
            "@MAG@",
            "@WHI@",
            "@LRE@",
            "@DRE@",
            "@BLA@",
            "@OR1@",
            "@OR2@",
            "@OR3@",
            "@GR1@",
            "@GR2@",
            "@GR3@",
            "@cr1@",
            "@cr2@",
            "@cr3@",
            "@cr4@",
            "@cr5@",
            "@cr6@",
            "@cr7@",
            "@cr8@",
            "@cr9@",
            "@cr10@",
            "@cr11@",
            "@cr12@",
            "@cr13@",
            "@cr14@",
            "@cr15@",
            "@cr16@",
            "@cr17@",
            "@cr18@",
            "@cr19@",
            "@cr20@",
    };

    private static final String[] replacementList = {
            "<col=A10081>",
            "<col=ff0000>",
            "<col=FF00>",
            "<col=ff>",
            "<col=ffff00>",
            "<col=ffff>",
            "<col=ff00ff>",
            "<col=ffffff>",
            "<col=ff9040>",
            "<col=800000>",
            "<col=0>",
            "<col=A67711>",
            "<col=ffb000>",
            "<col=ff7000>",
            "<col=ff3000>",
            "<col=148200>",
            "<col=c0ff00>",
            "<col=80ff00>",
            "<col=40ff00>",
            "<col=D9D9D9>",
            "<col=A67711>",
            "<col=A10081>",
            "<col=ffff00>",
            "<col=FF00>",
            "<col=FF>",
            "<col=ff0000>",
            "<col=FFFF>",
            "<col=ff00ff>",
            "<col=ffffff>",
            "<col=ff9040>",
            "<col=800000>",
            "<col=0>",
            "<col=ffb000>",
            "<col=ff7000>",
            "<col=ff3000>",
            "<col=c0ff00>",
            "<col=80ff00>",
            "<col=40ff00>",
            "<img=0>",
            "<img=1>",
            "<img=2>",
            "<img=3>",
            "<img=4>",
            "<img=5>",
            "<img=6>",
            "<img=7>",
            "<img=8>",
            "<img=9>",
            "<img=10>",
            "<img=11>",
            "<img=12>",
            "<img=13>",
            "<img=14>",
            "<img=15>",
            "<img=16>",
            "<img=17>",
            "<img=18>",
            "<img=19>",
    };

    public static String handleOldSyntax(String text) {
        return StringUtils.replaceEach(text, searchList, replacementList);
    }

    public static String removeMarkup(String s) {
        boolean stopAdding = false;
        final StringBuilder stringbuffer = new StringBuilder(s.length());
        for (int charIndex = 0; charIndex < s.length(); charIndex++) {
            final char c = s.charAt(charIndex);
            if (c == '<') {
                stopAdding = true;
            } else if (c == '>') {
                stopAdding = false;
            } else if (!stopAdding) {
                stringbuffer.append(c);
            }
        }
        return stringbuffer.toString();
    }

    private static DecimalFormat formatter;

    public static String formatTimePlayed(Stopwatch stopwatch) {
        final long seconds = stopwatch.elapsedTime(TimeUnit.SECONDS) % 60;
        final long minutes = stopwatch.elapsedTime(TimeUnit.MINUTES) % 60;
        final long hours = stopwatch.elapsedTime(TimeUnit.HOURS) % 24;

        final StringBuilder builder = new StringBuilder();

        if (hours < 10) {
            builder.append("0");
        }
        builder.append(hours).append(":");

        if (minutes < 10) {
            builder.append("0");
        }
        builder.append(minutes).append(":");

        if (seconds < 10) {
            builder.append("0");
        }

        builder.append(seconds);
        return builder.toString();
    }

    public static String passwordAsterisks(String s) {
        int length = s.length();
        StringBuilder result = new StringBuilder(length);
        for (int j = 0; j < length; j++) {
            result.append("*");
        }

        return result.toString();
    }

    public static String formatName(final String name) {
        if (name.isEmpty()) {
            return name;
        }

        int length = name.length();
        StringBuilder sb = new StringBuilder(length);
        sb.append(Character.toUpperCase(name.charAt(0)));

        for (int index = 1; index < length; index++) {
            char character = name.charAt(index);

            if (character == '_' || character == ' ') {
                sb.append(character);

                int nextIndex = index + 1;
                if (nextIndex < length) {
                    character = name.charAt(nextIndex);
                    if (character >= 'a' && character <= 'z') {
                        sb.append(Character.toUpperCase(character));
                        index++;
                    }
                }
            } else {
                sb.append(character);
            }
        }

        return sb.toString();
    }

    public static String formatNumbersWithCommas(long number) {
        return formatter.format(number);
    }

    static {
        DecimalFormatSymbols separator = new DecimalFormatSymbols();
        separator.setGroupingSeparator(',');
        formatter = new DecimalFormat("#,###,###,###,###,###", separator);
    }

    public static int stringToIntRadix(String string, int radix, boolean canStartWithPlus) {
        // Make sure radix isn't out of range
        if (radix < 2 || radix > 36) {
            throw new IllegalArgumentException("Invalid radix:" + radix);
        }

        boolean negative = false;
        boolean resultFound = false;
        int oldResult = 0;
        int stringLength = string.length();
        for (int charIndex = 0; charIndex < stringLength; charIndex++) {
            int value = string.charAt(charIndex);

            // Check if out string contains any header symbols
            if (charIndex == 0) {
                if (value == 45) {// Minus sign(-)
                    negative = true;
                    continue;
                }

                if (value == 43 && canStartWithPlus) {// Plus sign(+)
                    continue;
                }
            }

            // Convert char code value to decimal value
            if (value >= 48 && value <= 57) {//[0,9]
                value -= 48;
            } else if (value >= 65 && value <= 90) {//[A,Z]
                value -= 55;
            } else if (value >= 97 && value <= 122) {//[a,z]
                value -= 87;
            } else {
                throw new NumberFormatException();
            }

            // Check if our value doesn't overflow radix
            if (radix <= value) {
                throw new NumberFormatException();
            }

            // If we parsing negative number, negate the value
            if (negative) {
                value = -value;
            }

            // Multiply the last result with our radix and add the new value
            int result = oldResult * radix + value;

            // Honestly no idea why this check is here
            if (result / radix != oldResult) {
                throw new NumberFormatException();
            }

            resultFound = true;
            oldResult = result;
        }

        if (!resultFound) {
            throw new NumberFormatException();
        }

        return oldResult;
    }

    public static int stringToInt16(String string) {
        return stringToIntRadix(string, 16, true);
    }

    public static int stringToInt10(String string) {
        return stringToIntRadix(string, 10, true);
    }

}

