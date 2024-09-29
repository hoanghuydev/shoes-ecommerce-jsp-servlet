package com.ltweb_servlet_ecommerce.log;

import com.ltweb_servlet_ecommerce.model.LogModel;

import java.util.List;

/**
 * A manager for logging operations, extends ThreadManager.
 */
public class LogManager extends ThreadManager {
    // The singleton instance of LogManager
    private static LogManager instance = null;

    /**
     * Private constructor for LogManager.
     * Initializes the instance and starts listening for new items.
     */
    private LogManager() {
    }

    /**
     * Gets the singleton instance of LogManager.
     * If the instance is null, creates a new instance and starts listening.
     * @return The singleton instance of LogManager.
     */
    public static LogManager getInstance() {
        if (instance == null) {
            synchronized (LogManager.class) {
                if (instance == null) {
                    instance = new LogManager();
                    instance.listen();
                }
            }
        }
        return instance;
    }

    /**
     * Processes the list of items.
     * Creates a LogThread for each item and submits it to the executor service.
     * @param items The list of items to process.
     */
    @Override
    public void doProcess(List<LogModel> items) {
        LogThread logThread = new LogThread();
        logThread.setItems(items);
        executorService.submit(logThread);
    }
}
