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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kingdom)) return false;
        Kingdom kingdom = (Kingdom) o;
        return Objects.equals(getName(), kingdom.getName()) &&
                Objects.equals(getEmblem(), kingdom.getEmblem());
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
