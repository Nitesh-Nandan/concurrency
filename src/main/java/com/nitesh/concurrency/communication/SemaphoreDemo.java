package com.nitesh.concurrency.communication;

/**
 * Semaphore doesn't have a notion of owner thread
 * Many thread can acquire a permit
 * The same thread can acquire the semaphore multiple times
 * Difference between locks and semaphores
 *  1. Any thread can release a semaphore (no ownership)
 * Inter-thread communication for producer consumer, using semaphore
 *
 */
public class SemaphoreDemo {
}
