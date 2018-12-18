package com.tameofthrones.controller;

import com.tameofthrones.model.Message;

public interface MessageValidationStrategy {
    boolean isValid(Message message);
}
