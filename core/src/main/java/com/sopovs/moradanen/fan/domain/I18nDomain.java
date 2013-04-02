package com.sopovs.moradanen.fan.domain;

import java.io.Serializable;

public interface I18nDomain<T extends I18nedDomain<?>> extends Serializable {

    Lang getLang();
}
