package src.sample.classes.utilmodules;

import java.util.Random;

public class Utils {
    public static Random random = new Random();

    public static int getRandomInteger(int size) {
        return random.nextInt(size);
    }

    public static int getRandomInteger(int l, int r) {
        return l + random.nextInt(r - l + 1);
    }

    public static String getRandString(int size) {
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] chars = new char[size];
        for(int i = 0; i<chars.length;i++)
            chars[i]=letters.charAt(getRandomInteger(letters.length()-1));
        return new String(chars);
    }
    public static boolean getRandBool()
    {
        return random.nextBoolean();
    }
}
