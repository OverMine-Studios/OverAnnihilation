package dev.risas.dencore.controllers.mongo;

public interface MongoSavable<T> {
    T toSavable(Object object);
}
