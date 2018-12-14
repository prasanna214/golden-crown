package com.goldencrown.model;

import java.util.Objects;

//Represents a territory ruled by a king
public class Kingdom {
    private String name;
    private String emblem;
    private King ruler;

    public Kingdom(String name, String emblem) {
        this.name = name;
        this.emblem = emblem;
    }

    public String getName() {
        return name;
    }

    public String getEmblem() {
        return emblem;
    }

    public King getRuler() {
        return ruler;
    }

    public void setRuler(King ruler) {
        this.ruler = ruler;
    }
}
