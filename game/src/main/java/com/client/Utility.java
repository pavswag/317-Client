package com.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.client.particle.ParticleDefinition.RANDOM;

public class Utility {
    public static int[] d2Tod1(int[][] array) {
        int[] newArray = new int[array.length*array[0].length];

        for (int i = 0; i < array.length; ++i)
            for (int j = 0; j < array[i].length; ++j) {
                newArray[i*array[0].length+j] = array[i][j];
            }

        return newArray;
    }

    public static int[][] d1Tod2(int[] array, int width) {
        int[][] newArray = new int[array.length/width][width];

        for (int i = 0; i < array.length; ++i) {
            newArray[i/width][i%width] = array[i];
        }

        return newArray;
    }
    public static String getDate() {
        return new SimpleDateFormat("EE MMM dd yyyy").format(new Date());
    }

    public static <T> T randomElement(T[] array) {
        return array[(int) (RANDOM.nextDouble() * array.length)];
    }

    public static int randomElement(int[] array) {
        return array[(int) (RANDOM.nextDouble() * array.length)];
    }

    public static String formatName(final String name) {
        if (name.length() > 0) {
            char first = name.charAt(0);
            StringBuilder fixed = new StringBuilder("" + Character.toUpperCase(first));
            for (int index = 1; index < name.length(); index++) {
                char character = name.charAt(index);
                if (character == '_' || character == ' ') {
                    character = ' ';
                    fixed.append(character);
                    if (index + 1 < name.length() && name.charAt(index + 1) >= 'a' && name.charAt(index + 1) <= 'z') {
                        fixed.append(Character.toUpperCase(name.charAt(index + 1)));
                        index++;
                    }
                } else {
                    fixed.append(character);
                }
            }
            return fixed.toString();
        } else {
            return name;
        }
    }

    public static String getRank(int rank) {
        switch (rank) {
            case 0:
                return "Moderator";
            case 1:
                return "Administrator";
            case 2:
                return "Staff Manager";
            case 15:
                return "Developer";
        }
        return "Player";
    }
}
