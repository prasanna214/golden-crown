package com.goldencrown.controller;

import com.goldencrown.model.Kingdom;
import com.goldencrown.model.Message;
import com.goldencrown.view.IO;

import java.util.List;

public interface MessageConstructor {
    List<Message> constructMessages(Kingdom sender, IO io);
}
