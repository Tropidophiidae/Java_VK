package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class log4j2 {
    private static final Logger logger = LogManager.getLogger();

    public static Logger log(){
        return logger;
    }
}
