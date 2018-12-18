package com.tameofthrones.controller;

import com.tameofthrones.model.Kingdom;
import com.tameofthrones.model.Message;

import java.util.List;

public interface MessageConstructor {
    List<Message> constructMessages(Kingdom sender);
}
