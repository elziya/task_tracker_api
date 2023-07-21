package ru.kpfu.itis.repositories;

public interface BlackListRepository {

    void save(String token);

    boolean exists(String token);
}
