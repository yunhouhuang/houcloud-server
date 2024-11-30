package com.houcloud.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.util.Date;
import java.util.Random;

public class IDUtil {
    final static Snowflake snowflake = IdUtil.getSnowflake(1, 1);
    private final static IDGenerator idGenerator = new IDGenerator(9L);

    public static String SID() {
        return snowflake.nextIdStr();
    }

    public static Long LID() {
        return snowflake.nextId();
    }

    public static Long nextId() {
        return idGenerator.nextId();
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    //生成id
    public static String shortCode() {
        //得到id 很长且高位很长部分是一样的数
        long id = SnowflakeIdWorker.generateId();
        StringBuilder sb = new StringBuilder(String.valueOf(id));
        //将id翻转：我们发现id很长，且高位很长部分是一样的数
        StringBuilder reverse = sb.reverse();
        //切去部分长度
        id = Long.parseLong(reverse.toString()) / 100;
        //1999999999以内的10位或9位或8位id;....
        while (id > 99999999999L) {
            id /= 10;
        }
        if (id < 10000000000L) {
            return addZeroForNum(String.valueOf(id), 11);
        }
        return String.valueOf(id);
    }

    private static final Random random = new Random();

    public static long generateUniqueFleetId() {
        long currentTimeMillis = System.currentTimeMillis();
        long randomPart = random.nextInt(10000); // 生成0到9999的随机数
        long id = (currentTimeMillis % 1000000000L) * 10000 + randomPart;

        // 确保ID是10位数
        while (String.valueOf(id).length() < 10) {
            id = id * 10 + random.nextInt(10);
        }

        return id;
    }

    //生成id
    public static Long shortLongId() {
        long id = SnowflakeIdWorker.generateId();//得到id 很长且高位很长部分是一样的数
        StringBuilder sb = new StringBuilder(String.valueOf(id));
        StringBuilder reverse = sb.reverse();//将id翻转：我们发现id很长，且高位很长部分是一样的数
        id = Long.parseLong(reverse.toString()) / 100;//切去部分长度
        while (id > 99999999999L) {//1999999999以内的10位或9位或8位id;....
            id /= 10;
        }
        return id;
    }

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            StringBuilder strBuilder = new StringBuilder(str);
            while (strLen < strLength) {
                //左补0
                strBuilder.insert(0, "0");
                strLen = strBuilder.length();
            }
            str = strBuilder.toString();
        }
        return str;
    }

    public static String getRandomCode() {
        return String.valueOf(new Random().nextInt(899999) + 100000);

    }

    public static String dateTimeId(String first) {
        String value = String.valueOf(new Random().nextInt(899) + 100);
        return String.format("%s%s%s", first, DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"), value);
    }
}


