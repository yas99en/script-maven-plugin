package io.github.yas99en.mojo.script;

import org.apache.maven.plugin.logging.Log;

public final class LogWrapper {
    private Log log;

    public LogWrapper(Log log) {
        this.log = log;
    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public void debug(Object content) {
        if(content instanceof Throwable) {
            log.debug((Throwable)content);
        } else {
            log.debug(String.valueOf(content));
        }
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public void info(Object content) {
        if(content instanceof Throwable) {
            log.info((Throwable)content);
        } else {
            log.info(String.valueOf(content));
        }
    }

    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    public void warn(Object content) {
        if(content instanceof Throwable) {
            log.warn((Throwable)content);
        } else {
            log.warn(String.valueOf(content));
        }
    }

    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    public void error(Object content) {
        if(content instanceof Throwable) {
            log.error((Throwable)content);
        } else {
            log.error(String.valueOf(content));
        }
    }
}
