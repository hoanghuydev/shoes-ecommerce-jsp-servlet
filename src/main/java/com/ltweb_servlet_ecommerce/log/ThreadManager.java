package com.ltweb_servlet_ecommerce.log;

import com.ltweb_servlet_ecommerce.model.LogModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A manager for handling threads and processing items from a queue.
 */
public abstract class ThreadManager {
    // The maximum number of items to process in a batch
    private final int BATCH_SIZE = 10;

    // The timeout for waiting for an item from the queue
    private final long WAIT_TIME_OUT = 1000; //ms

    // The timeout for the process cycle
    private final long TIME_OUT = 2 * 1000; //ms

    // The queue for storing items
    private final BlockingQueue<LogModel> sourceQueue = new LinkedBlockingQueue<>();

    // The list to store items to process in a batch
    protected List<LogModel> items = new ArrayList<>(BATCH_SIZE);

    // Flag indicating whether the manager should continue working
    protected AtomicBoolean shouldWork = new AtomicBoolean(true);

    // Flag indicating whether the manager is currently running
    protected AtomicBoolean isRunning = new AtomicBoolean(true);

    // Flag indicating whether the manager is listening for new items
    private boolean listening = false;

    // Executor service for handling threads
    protected ExecutorService executorService = Executors.newFixedThreadPool(5);

    // The main thread for processing items
    private Thread mainThread;

    /**
     * Constructor for ThreadManager.
     * Initializes the main thread for processing items.
     */
    public ThreadManager() {
        mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Set the flag indicating that the manager is running
                isRunning.set(true);
                // Initialize variables for tracking items and time
                int recNum = 0;
                long lgnStart = System.currentTimeMillis();

                // Loop until the manager should continue working
                while (shouldWork.get()) {
                    try {
                        // Attempt to retrieve an item from the queue with a timeout
                        LogModel item = sourceQueue.poll(WAIT_TIME_OUT, TimeUnit.MILLISECONDS);
                        if (item != null) {
                            // Add the retrieved item to the list and increment the count
                            items.add(item);
                            recNum++;
                        }

                        // Check if the batch size is reached or a timeout occurred
                        if (recNum >= BATCH_SIZE || timedOut(lgnStart)) {
                            if (!items.isEmpty()) {
                                // Process the batch of items
                                doProcess(items);

                                // Clear the list and reset the time and count
                                items = new ArrayList<>(BATCH_SIZE);
                                lgnStart = System.currentTimeMillis();
                                recNum = 0;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // Set the flag indicating that the manager is not running
                    isRunning.set(false);
                }
            }

            /**
             * Checks if a timeout has occurred since the start time.
             * @param startTime The start time.
             * @return True if a timeout has occurred, false otherwise.
             */
            private boolean timedOut(Long startTime) {
                return System.currentTimeMillis() - startTime > TIME_OUT;
            }
        });

    }

    /**
     * Abstract method for processing items.
     *
     * @param items The list of items to process.
     */
    public abstract void doProcess(List<LogModel> items);

    /**
     * Starts listening for new items to process from the queue.
     */
    protected synchronized void listen() {

        if (!listening) {
            mainThread.start();
            listening = true;
        }
    }

    /**
     * Stop thread
     */
    public void stop() {
        this.shouldWork.set(false);
        try {
            mainThread.join(); // Chờ cho mainThread kết thúc
        } catch (InterruptedException e) {
            // Xử lý ngoại lệ
            Thread.currentThread().interrupt(); // Khôi phục interrupted status
        }
        executorService.shutdown(); // Dừng executorService
    }

    /**
     * Submits an item to the queue for processing.
     * @param item The item to be processed.
     */
    public void submit(LogModel item) {
        sourceQueue.offer(item);
    }
}

