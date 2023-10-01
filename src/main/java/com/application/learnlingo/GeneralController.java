package com.application.learnlingo;

public class GeneralController {
    private final static String DEFAULT_DICT_DBMS_PATH
            ="./src/main/resources/com/application/learnlingo/database/dict_hh.db";

    protected static DictDMBS evDict = new DictDMBS(DEFAULT_DICT_DBMS_PATH,"av");
    protected static DictDMBS veDict = new DictDMBS(DEFAULT_DICT_DBMS_PATH,"va");
}
