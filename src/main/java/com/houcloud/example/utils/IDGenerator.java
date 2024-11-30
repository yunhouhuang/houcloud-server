package com.houcloud.example.utils;

public class IDGenerator {

    /**
     * 服务序列号
     */
    private long serviceSequence = 1L;

    /**
     * 时间戳开始计时时间 2013-04-08 04:48:16
     */
    private final long startTimestamp = 1365367696L;

    /**
     * 默认顺序位的初始值
     */
    private long sequence = 1L;
    /**
     * 顺序位2进制长度
     */
    private final long sequenceBits = 10L;

    /**
     * 服务序列号2进制长度
     */
    private final long serviceSequenceBits = 12L;


    /**
     * 服务序列号右移位数
     */
    private final long serviceSequenceShift = sequenceBits;

    /**
     * 时间戳右移位数
     */
    private final long timestampShift = sequenceBits + serviceSequenceBits;

    /**
     * 生成序列的掩码(10位所对应的最大整数值)，这里为1023 (0b1111111111=1023)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public IDGenerator(Long serviceSequence) {
        this.serviceSequence = serviceSequence;
    }

    /**
     * 生成唯一的趋势递增的id
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1L) & sequenceMask;
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - startTimestamp) << timestampShift) | (serviceSequence << serviceSequenceShift) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis() / 1000L;
    }
}
