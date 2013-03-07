package com.sopovs.moradanen.fan.domain;

import java.util.Map;

public interface I18nedDomain<T extends I18nDomain<?>> {
    Map<Lang, T> getI18ns();

    // Good candidate for default method :)
    T getI18n(String lang);
}
