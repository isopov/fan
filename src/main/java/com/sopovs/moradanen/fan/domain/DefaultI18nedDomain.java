package com.sopovs.moradanen.fan.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.Map;

@MappedSuperclass
public abstract class DefaultI18nedDomain<T extends I18nDomain> extends AbstractEntity implements I18nedDomain<T> {
    private static final long serialVersionUID = 1L;


    @Override
    public T getI18n(String lang) {
        return getI18n(getI18ns(), (lang));
    }

    public static <T> T getI18n(Map<Lang, T> i18ns, String lang) {
        return i18ns.get(Lang.fromLang(lang));
    }
}
