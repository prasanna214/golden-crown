package com.goldencrown.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//Represents a territory ruled by a king
public class Kingdom {
    private String name;
    private String emblem;
    private King ruler;
    private Set<Kingdom> allies;

    public Kingdom(String name, String emblem) {
        this.name = name;
        this.emblem = emblem;
        this.allies = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kingdom)) return false;
        Kingdom kingdom = (Kingdom) o;
        return Objects.equals(getName(), kingdom.getName()) &&
                Objects.equals(getEmblem(), kingdom.getEmblem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmblem());
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

    public Set<Kingdom> getAllies() {
        return allies;
    }

    public void joinAllies(Kingdom kingdom) {
        if (Objects.isNull(kingdom)) {
            return;
        }
        this.allies.add(kingdom);
    }
}
