package com.goldencrown.controller.actions;

import com.goldencrown.controller.BalletMessageConstructor;
import com.goldencrown.controller.BalletMessageValidation;
import com.goldencrown.controller.CountingStation;
import com.goldencrown.controller.ElectionCoordinator;
import com.goldencrown.model.Kingdom;
import com.goldencrown.model.Message;
import com.goldencrown.model.Universe;
import com.goldencrown.view.IO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.goldencrown.controller.helpers.KingdomFactory.kingdomMap;
import static java.util.Objects.isNull;

public class Election implements Action {
    private static final String INPUT_MESSAGE = "Enter the kingdoms competing to be the ruler (names separated space):";
    private static final String INVALID_CANDIDATE_MESSAGES = "Please enter valid candidate names";
    private static final int SIX = 6;
    private static final int ONE = 1;
    private List<Kingdom> candidates;

    private ElectionCoordinator electionCoordinator;
    private BalletMessageConstructor messageConstructor;
    private BalletMessageValidation balletMessageValidation;
    private CountingStation countingStation;
    private Integer round = ONE;

    public Election(ElectionCoordinator electionCoordinator, BalletMessageConstructor messageConstructor,
                    BalletMessageValidation balletMessageValidation, CountingStation countingStation) {
        this.electionCoordinator = electionCoordinator;
        this.messageConstructor = messageConstructor;
        this.balletMessageValidation = balletMessageValidation;
        this.countingStation = countingStation;
    }

    @Override
    public void execute(Universe universe, IO io) {
        if (isNull(universe) || isNull(io)) {
            return;
        }
        electionSetUp(universe, io);

        if (candidates.size() == 1) {
            universe.setRuler(candidates.get(0));
            return;
        }
        startElection(universe, io);
    }

    private void electionSetUp(Universe universe, IO io) {
        universe.setRuler(null);
        addCandidatesFromInput(io);
        balletMessageValidation.setElectionNominees(candidates);
        countingStation.setCandidates(candidates);
        setBalletMessageValidationStrategyForAllKingdoms();
        round = 1;
    }

    private void addCandidatesFromInput(IO io) {
        candidates = new ArrayList<>();
        io.display(INPUT_MESSAGE);
        List<String> candidateNames = getCandidateNames(io);
        if (!kingdomMap.keySet().containsAll(candidateNames)) {
            io.display(INVALID_CANDIDATE_MESSAGES);
            addCandidatesFromInput(io);
            return;
        }

        candidateNames.forEach(name -> {
            candidates.add(kingdomMap.get(name));
        });
    }

    private List<String> getCandidateNames(IO io) {
        List<String> candidateNames = Arrays.asList(io.getInput().split(" "));
        candidateNames = candidateNames.stream()
                .map(name -> name.trim().toLowerCase()).collect(Collectors.toList());
        return candidateNames;
    }

    private void setBalletMessageValidationStrategyForAllKingdoms() {
        kingdomMap.values().forEach(candidate ->
                candidate.setMessageValidationStrategy(balletMessageValidation));
    }

    private void startElection(Universe universe, IO io) {
        candidates.forEach(candidate -> candidate.getAllies().clear());
        collectAndDistributeMessages();
        countingStation.displayResults(round++, io);
        List<Kingdom> qualifiedCandidates = countingStation.getQualifiedCandidates();

        if (countingStation.isNextRoundNeeded()) {
            this.candidates = qualifiedCandidates;
            startElection(universe, io);
            return;
        }
        universe.setRuler(qualifiedCandidates.get(0));
    }

    private void collectAndDistributeMessages() {
        List<Message> balletBox = getMessagesFromCandidates();
        List<Message> messages = electionCoordinator.pickRandomMessages(balletBox, SIX);
        electionCoordinator.distributeToReceivers(messages);
    }

    private List<Message> getMessagesFromCandidates() {
        List<Message> messages = new ArrayList<>();
        candidates.forEach(candidate -> {
            messages.addAll(messageConstructor.constructMessages(candidate));
        });
        return messages;
    }

    public List<Kingdom> getCandidates() {
        return candidates;
    }
}
