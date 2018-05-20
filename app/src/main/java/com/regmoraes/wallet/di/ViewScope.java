package com.regmoraes.wallet.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by regmoraes on 11/6/16.
 */

@Scope
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ViewScope {}