package com.novoda.frankboylan.meetingseating;

import java.io.IOException;

/**
 * System Ping Google to check if connected to internet
 */
class ConnectionStatus {
    static boolean hasActiveInternetConnection() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
