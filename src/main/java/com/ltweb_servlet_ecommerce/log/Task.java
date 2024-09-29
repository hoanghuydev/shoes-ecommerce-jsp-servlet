package com.ltweb_servlet_ecommerce.log;

import lombok.Data;

import java.util.List;
import java.util.concurrent.Callable;
/**
 * An abstract class representing a task.
 * Implements Callable interface for handling threads.
 * @param <E> The type of items to process.
 */
@Data
public abstract class Task<E> implements Callable<Integer> {
    private List<E> items;
}

