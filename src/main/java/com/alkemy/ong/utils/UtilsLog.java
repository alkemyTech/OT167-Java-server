package com.alkemy.ong.utils;

import com.alkemy.ong.enums.TipLog;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class UtilsLog {
    private static Logger log = Logger.getLogger(UtilsLog.class);

    @SuppressWarnings("rawtypes")
    public static void registrarInfo(Class clase, TipLog tip, String message)
    {
        log = LogManager.getLogger(clase);

        switch (tip)
        {
            case DEBUG:
                log.debug(message);
                break;
            case ERROR:
                log.error(message);
                break;
            case FATAL:
                log.fatal(message);
                break;
            case INFO:
                log.info(message);
                break;
            case WARNING:
                log.warn(message);
        }
    }
}
