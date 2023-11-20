package com.application.learnlingo.Model;

public interface Game {
    void init();

    void start();

    void notice(boolean isWon);

    void resume();

    void pause();
}
