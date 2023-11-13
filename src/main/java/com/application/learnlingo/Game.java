package com.application.learnlingo;

public interface Game {
    void init();
    void start();
    void notice(boolean isWon);
    void resume();
    void pause();
}
