package utils.log;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author oshoukry
 */
public final class LogHelper {

    private LogHelper() {
    }

    /**
     * This method initializes the loggers, currently just reseting the Mock Appender captured events.
     */
    public static void initializeLoggers() {
        MockAppenderLog4J.restEvents();
        BasicConfigurator.resetConfiguration();
        Properties props = new Properties();
        try {
            props.load(LogHelper.class.getResourceAsStream("/utils/log/test.log4j.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyConfigurator.configure(props);
    }

    /**
     * This method returns the counts on trace events by source.
     *
     * @param source
     *            Source of the logs
     * @return Total count recieved
     */
    public static Integer getTraceCountBySource(final String source) {
        return MockAppenderLog4J.getCountBySourceByPriority(source, Level.TRACE);
    }

    /**
     * This method returns the counts on debug events by source.
     *
     * @param source
     *            Source of the logs
     * @return Total count recieved
     */
    public static Integer getDebugCountBySource(final String source) {
        return MockAppenderLog4J.getCountBySourceByPriority(source, Level.DEBUG);
    }

    /**
     * This method returns the counts on info events by source.
     *
     * @param source
     *            Source of the logs
     * @return Total count recieved
     */
    public static Integer getInfoCountBySource(final String source) {
        return MockAppenderLog4J.getCountBySourceByPriority(source, Level.INFO);
    }

    /**
     * This method returns the counts on warn events by source.
     *
     * @param source
     *            Source of the logs
     * @return Total count recieved
     */
    public static Integer getWarnCountBySource(final String source) {
        return MockAppenderLog4J.getCountBySourceByPriority(source, Level.WARN);
    }

    /**
     * This method returns the counts on error events by source.
     *
     * @param source
     *            Source of the logs
     * @return Total count recieved
     */
    public static Integer getErrorCountBySource(final String source) {
        return MockAppenderLog4J.getCountBySourceByPriority(source, Level.ERROR);
    }

    /**
     * This method returns the counts on fatal events by source.
     *
     * @param source
     *            Source of the logs
     * @return Total count recieved
     */
    public static Integer getFatalCountBySource(final String source) {
        return MockAppenderLog4J.getCountBySourceByPriority(source, Level.FATAL);
    }

    /**
     * This method returns all the counts by source.
     *
     * @param source
     *            Source of the logs
     * @return Total count recieved
     */
    public static Integer getCountBySource(final String source) {
        return MockAppenderLog4J.getCountBySource(source);
    }

    /**
     * This method returns the trace logged events for a given source.
     *
     * @param source
     *            Source of the logs.
     * @return List of logged events recieved.
     */
    public static List<LoggedEvent> getTraceEvents(final String source) {
        return MockAppenderLog4J.getLoggedEventsBySourceByPriority(source, Level.TRACE);
    }

    /**
     * This method returns the debug logged events for a given source.
     *
     * @param source
     *            Source of the logs.
     * @return List of logged events recieved.
     */
    public static List<LoggedEvent> getDebugEvents(final String source) {
        return MockAppenderLog4J.getLoggedEventsBySourceByPriority(source, Level.DEBUG);
    }

    /**
     * This method returns the info logged events for a given source.
     *
     * @param source
     *            Source of the logs.
     * @return List of logged events recieved.
     */
    public static List<LoggedEvent> getInfoEvents(final String source) {
        return MockAppenderLog4J.getLoggedEventsBySourceByPriority(source, Level.INFO);
    }

    /**
     * This method returns the warn logged events for a given source.
     *
     * @param source
     *            Source of the logs.
     * @return List of logged events recieved.
     */
    public static List<LoggedEvent> getWarnEvents(final String source) {
        return MockAppenderLog4J.getLoggedEventsBySourceByPriority(source, Level.WARN);
    }

    /**
     * This method returns the error logged events for a given source.
     *
     * @param source
     *            Source of the logs.
     * @return List of logged events recieved.
     */
    public static List<LoggedEvent> getErrorEvents(final String source) {
        return MockAppenderLog4J.getLoggedEventsBySourceByPriority(source, Level.ERROR);
    }

    /**
     * This method returns the fatal logged events for a given source.
     *
     * @param source
     *            Source of the logs.
     * @return List of logged events recieved.
     */
    public static List<LoggedEvent> getFatalEvents(final String source) {
        return MockAppenderLog4J.getLoggedEventsBySourceByPriority(source, Level.FATAL);
    }

}
