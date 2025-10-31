package com.niladri.book_my_show.service;

public interface ICacheService {

    void setKey(String key, Object value);

    Object getKey(String key);

    void deleteKey(String key);

    void getAllKeyAndValues();

    void deleteAll();
}
