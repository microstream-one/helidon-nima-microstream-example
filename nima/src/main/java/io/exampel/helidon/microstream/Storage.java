package io.exampel.helidon.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
    private static final int NUM_ENTRIES = 1000;
    private final ReentrantReadWriteLock.ReadLock readLock;
    private final EmbeddedStorageManager storageManger;
    private final DataRoot dataRoot;

    private final Random random = new Random();

    public Storage() {
        storageManger = EmbeddedStorage.start();
        dataRoot = initializeStorage();

        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
    }

    private DataRoot initializeStorage() {
        if (storageManger.root() == null) {
            DataRoot dataRoot = new DataRoot();
            for (int i = 0; i < NUM_ENTRIES; i++) {
                dataRoot.dataMap1().put("key " + i, "Map1 value " + i);
            }
            storageManger.setRoot(dataRoot);
            storageManger.storeRoot();

            Logger.getLogger(Storage.class.getName()).log(Level.INFO, "Created a new microstream storage populated with " + NUM_ENTRIES + " elements.");
        } else {
            Logger.getLogger(Storage.class.getName()).log(Level.INFO, "Loaded existing microstream storage.");
        }

        return (DataRoot) storageManger.root();
    }

    public String getRandomValue() {
        readLock.lock();
        try {
            int rnd = random.nextInt(dataRoot.dataMap1().values().size());
            return dataRoot.dataMap1().get("key " + rnd);
        } finally {
            readLock.unlock();
        }
    }
}
