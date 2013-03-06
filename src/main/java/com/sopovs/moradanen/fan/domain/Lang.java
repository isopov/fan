package com.sopovs.moradanen.fan.domain;

//TODO Whether something standard is worth using here?
public enum Lang {
    EN("en"), RU("ru");

    private final String lang;

    private Lang(String lang) {
        this.lang = lang;
    }

    public static Lang fromLang(String lang) {
        for (Lang l : values()) {
            if (l.lang.equals(lang)) {
                return l;
            }
        }
        throw new java.lang.IllegalArgumentException();
    }
}
