package com.goldencrown.controller;

import com.goldencrown.model.Message;

public interface MessageValidationStrategy {
    boolean isValid(Message message);
}
