package com.mercadolibre.kotlin.exceptions;

import com.mercadolibre.kotlin.models.Powers;

public class LockedException extends Throwable {
    public LockedException(String message) {
        super(message);
    }
}
