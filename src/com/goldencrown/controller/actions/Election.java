package com.goldencrown.controller.actions;

import com.goldencrown.controller.BalletMessageValidation;
import com.goldencrown.controller.CandidateRegistry;
import com.goldencrown.controller.CountingStation;
import com.goldencrown.controller.ElectionCoordinator;
import com.goldencrown.model.Kingdom;
import com.goldencrown.model.Universe;
import com.goldencrown.view.IO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.goldencrown.controller.helpers.KingdomFactory.kingdomMap;
import static java.util.Objects.isNull;

public class Election implements Action {
    private static final int SIX = 6;
    private static final int ONE = 1;
    private static final String NO_VOTERS = "No voters, No Election";
    private List<Kingdom> candidates;

    private CandidateRegistry registry;
    private ElectionCoordinator coordinator;
    private BalletMessageValidation messageValidation;
    private CountingStation countingStation;
    private Integer round = ONE;

    public Election(ElectionCoordinator coordinator, CandidateRegistry registry,
                    BalletMessageValidation messageValidation, CountingStation countingStation) {
        this.coordinator = coordinator;
        this.registry = registry;
        this.messageValidation = messageValidation;
        this.countingStation = countingStation;
    }

    @Override
    public void execute(Universe universe, IO io) {
        if (isNull(universe) || isNull(io)) {
            return;
        }
        electionSetUp(io);
        if (candidates.size() == ONE) {
            universe.setRuler(candidates.get(0));
            return;
        }
        if (candidates.size() == SIX) {
            io.display(NO_VOTERS);
            return;
        }
        clearRulerOfTheUniverse(universe);
        startElection(universe, io);
    }

    private void electionSetUp(IO io) {
        registry.registerCandidates(io);
        this.candidates = getRegisteredCandidates(registry.getCandidateNames());
        messageValidation.setCandidates(candidates);
        setBalletMessageValidationStrategyForAllKingdoms();
        round = ONE;
    }

    private List<Kingdom> getRegisteredCandidates(Set<String> candidateNames) {
        return candidateNames.stream().map(name ->
                kingdomMap.get(name)).collect(Collectors.toList());
    }

    private void setBalletMessageValidationStrategyForAllKingdoms() {
        kingdomMap.values().forEach(candidate ->
                candidate.setMessageValidationStrategy(messageValidation));
    }

    private void startElection(Universe universe, IO io) {
        coordinator.conductElection(candidates);
        countingStation.setCandidates(candidates);
        countingStation.displayResults(round++, io);
        List<Kingdom> qualifiedCandidates = countingStation.getQualifiedCandidates();
        if (countingStation.isNextRoundNeeded()) {
            this.candidates = qualifiedCandidates;
            startElection(universe, io);
            return;
        }
        universe.setRuler(qualifiedCandidates.get(0));
    }

    private void clearRulerOfTheUniverse(Universe universe) {
        universe.setRuler(null);
    }

    public List<Kingdom> getCandidates() {
        return candidates;
    }
}
