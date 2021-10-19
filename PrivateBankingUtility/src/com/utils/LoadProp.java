package com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProp implements ConstantsI{

    public static String socketServiceIp;
    public static String socketServicePort;
    public static String runUtility;
    public static String sleepDuration;
    public static String logPath;

    static {
        Properties properties = new Properties();
        InputStream in;
        try {
            in = new FileInputStream(configPath);
            properties.load(in);
            socketServiceIp = properties.getProperty(SOCKET_IP);
            socketServicePort = properties.getProperty(SOCKET_PORT);
            runUtility = properties.getProperty(RUN_UTILITY);
            sleepDuration = properties.getProperty(SLEEP_TIME);
            logPath = properties.getProperty(LOG_PATH_FIELD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
