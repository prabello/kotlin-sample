package com.mercadolibre.kotlin.exceptions;

import com.mercadolibre.kotlin.domains.Human;

public class LockedException extends Throwable {
    public LockedException(String message) {
        super(message);
    }
}
