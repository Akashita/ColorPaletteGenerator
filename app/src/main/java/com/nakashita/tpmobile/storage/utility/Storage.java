package com.nakashita.tpmobile.storage.utility;

import java.util.List;

public interface Storage<T> {

    void insert(T object);

    List<T> findAll();

    T find(int id);

    int size();

    void update(int id, T object);

    void delete(int id);
}
