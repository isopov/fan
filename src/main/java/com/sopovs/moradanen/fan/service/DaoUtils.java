package com.sopovs.moradanen.fan.service;

import com.google.common.collect.Iterables;

import javax.persistence.TypedQuery;

public class DaoUtils {


    public static <T> T getSingleResultOrNull(final TypedQuery<T> query) {
        return Iterables.getOnlyElement(query.getResultList(), null);
    }
}
