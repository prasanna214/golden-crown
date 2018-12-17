package com.goldencrown.controller;

import com.goldencrown.view.IO;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.goldencrown.controller.helpers.KingdomFactory.kingdomMap;

public class CandidateRegistry {
    private static final String INPUT_MESSAGE = "Enter the kingdoms competing to be the ruler (names separated space):";
    private static final String INVALID_CANDIDATE_MESSAGES = "Please enter valid candidate names";

    private IO io;
    private Set<String> candidateNames;

    public CandidateRegistry(IO io) {
        this.io = io;
    }

    public void registerCandidates(){
        io.display(INPUT_MESSAGE);
        Set<String> candidateNames = getCandidateNamesFromInput(io);

        if (isValidInput(candidateNames)) {
            io.display(INVALID_CANDIDATE_MESSAGES);
            registerCandidates();
        }
        this.candidateNames = candidateNames;
    }

    private boolean isValidInput(Set<String> candidateNames) {
        return !kingdomMap.keySet().containsAll(candidateNames);
    }

    private Set<String> getCandidateNamesFromInput(IO io) {
        String[] names = io.getInput().split(" +");
        return Arrays.stream(names).map(name ->
                name.trim().toLowerCase()).collect(Collectors.toSet());
    }

    public Set<String> getCandidateNames(){
        return candidateNames;
    }

    public void setCandidateNames(Set<String> candidateNames) {
        this.candidateNames = candidateNames;
    }
}
