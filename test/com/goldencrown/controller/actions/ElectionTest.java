package com.goldencrown.controller.actions;

import com.goldencrown.controller.BalletMessageConstructor;
import com.goldencrown.controller.BalletMessageValidation;
import com.goldencrown.controller.CountingStation;
import com.goldencrown.controller.ElectionCoordinator;
import com.goldencrown.model.Kingdom;
import com.goldencrown.model.Universe;
import com.goldencrown.view.IO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.goldencrown.controller.helpers.KingdomFactory.kingdomMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ElectionTest {
    private static final String INPUT_MESSAGE = "Enter the kingdoms competing to be the ruler (names separated space):";
    private static final String INVALID_CANDIDATE_MESSAGES = "Please enter valid candidate names";

    private ElectionCoordinator electionCoordinator;
    private BalletMessageConstructor balletMessageConstructor;
    private BalletMessageValidation balletMessageValidation;
    private CountingStation countingStation;
    private Universe universe;
    private IO consoleIO;

    private Election election;

    @BeforeEach
    void setUp() {
        electionCoordinator = mock(ElectionCoordinator.class);
        balletMessageConstructor = mock(BalletMessageConstructor.class);
        balletMessageValidation = mock(BalletMessageValidation.class);
        countingStation = mock(CountingStation.class);
        universe = mock(Universe.class);
        consoleIO = mock(IO.class);
        when(consoleIO.getInput()).thenReturn("ice");
        when(countingStation.getQualifiedCandidates()).thenReturn(Collections.singletonList(mock(Kingdom.class)));
        when(countingStation.isNextRoundNeeded()).thenReturn(false);

        election = new Election(electionCoordinator, balletMessageConstructor, balletMessageValidation, countingStation);
    }

    @Test
    void doNothingWhenUniverseIsNull() {
        try {
            election.execute(null, consoleIO);
        } catch (NullPointerException exception) {
            fail("Exception thrown");
        }
    }

    @Test
    void doNothingWhenIOIsNull() {
        try {
            election.execute(universe, null);
        } catch (NullPointerException exception) {
            fail("Exception thrown");
        }
    }

    @Test
    void clearRulerOfTheUniverse() {
        election.execute(universe, consoleIO);

        verify(universe).setRuler(null);
    }

    @Test
    void displayInputMessageOnIO() {
        election.execute(universe, consoleIO);

        verify(consoleIO).display(INPUT_MESSAGE);
    }

    @Test
    void getCandidateNamesFromInput() {
        election.execute(universe, consoleIO);

        verify(consoleIO).getInput();
    }

    @Test
    void displayInvalidCandidateMessageGivenInvalidInput() {
        when(consoleIO.getInput()).thenReturn("invalid candidate names").thenReturn("fire");

        election.execute(universe, consoleIO);

        verify(consoleIO).display(INVALID_CANDIDATE_MESSAGES);
    }

    @Test
    void recurseUntilValidInputIsGiven() {
        when(consoleIO.getInput()).thenReturn("invalid input").thenReturn("invalid again")
                .thenReturn("ice air").thenReturn("invalid input");

        election.execute(universe, consoleIO);

        verify(consoleIO, times(3)).display(INPUT_MESSAGE);
        verify(consoleIO, times(3)).getInput();
        verify(consoleIO, times(2)).display(INVALID_CANDIDATE_MESSAGES);
    }

    @Test
    void addCandidateKingdomsGivenValidKingdomNames() {
        when(consoleIO.getInput()).thenReturn("Air Ice");

        election.execute(universe, consoleIO);

        List<Kingdom> candidates = election.getCandidates();
        assertNotNull(candidates);
        assertEquals(2, candidates.size());
        assertEquals("Air", candidates.get(0).getName());
        assertEquals("Ice", candidates.get(1).getName());
    }

    @Test
    void setCandidatesForCountingStationAndMessageValidationStrategy() {
        election.execute(universe, consoleIO);
        List<Kingdom> candidates = election.getCandidates();

        verify(countingStation).setCandidates(candidates);
        verify(balletMessageValidation).setElectionNominees(candidates);
    }

    @Test
    void setBalletMessageValidationStrategyForAllKingdoms() {
        election.execute(universe, consoleIO);

        kingdomMap.values().forEach(kingdom -> {
            assertEquals(balletMessageValidation.getClass(), kingdom.getMessageValidationStrategy().getClass());
        });
    }

    @Test
    void setUniverseRulerWhenThereIsOnlyOneCandidate() {
        when(consoleIO.getInput()).thenReturn("Air");
        Kingdom ruler = kingdomMap.get("air");

        election.execute(universe, consoleIO);

        verify(universe).setRuler(ruler);
        assertTrue(ruler.getAllies().isEmpty());
    }

    @Test
    void clearExistingAlliesForCandidatesIfAny() {
        when(consoleIO.getInput()).thenReturn("Air water");
        Kingdom airKingdom = kingdomMap.get("air");
        Kingdom waterKingdom = kingdomMap.get("water");
        airKingdom.joinAllies(mock(Kingdom.class));

        election.execute(universe, consoleIO);

        assertTrue(airKingdom.getAllies().isEmpty());
        assertTrue(waterKingdom.getAllies().isEmpty());
    }

    @Test
    void constructMessagesForAllCandidates() {
        when(consoleIO.getInput()).thenReturn("Air ice");
        Kingdom airKingdom = kingdomMap.get("air");
        Kingdom iceKingdom = kingdomMap.get("ice");

        election.execute(universe, consoleIO);

        verify(balletMessageConstructor).constructMessages(airKingdom);
        verify(balletMessageConstructor).constructMessages(iceKingdom);
    }

    @Test
    void electionCoordinatorShouldPickRandomMessages() {
        when(consoleIO.getInput()).thenReturn("ice fire");

        election.execute(universe, consoleIO);

        verify(electionCoordinator).pickRandomMessages(anyList(), eq(6));
    }

    @Test
    void electionCoordinatorShouldDistributeRandomMessages() {
        when(consoleIO.getInput()).thenReturn("ice water");
        ArrayList randomMessages = new ArrayList();
        when(electionCoordinator.pickRandomMessages(anyList(), eq(6))).thenReturn(randomMessages);

        election.execute(universe, consoleIO);

        verify(electionCoordinator).distributeToReceivers(randomMessages);
    }

    @Test
    void countingStationShouldDisplayResults() {
        when(consoleIO.getInput()).thenReturn("ice water");

        election.execute(universe, consoleIO);

        verify(countingStation).displayResults(1, consoleIO);
    }

    @Test
    void roundShouldBeIncrementedForEveryElectionRound() {
        when(consoleIO.getInput()).thenReturn("water land");
        when(countingStation.isNextRoundNeeded()).thenReturn(true).thenReturn(false);

        election.execute(universe, consoleIO);

        verify(countingStation).displayResults(1, consoleIO);
        verify(countingStation).displayResults(2, consoleIO);
    }

    @Test
    void startElectionWithQualifiedCandidatesInCaseOfTie() {
        when(consoleIO.getInput()).thenReturn("water land");
        when(countingStation.isNextRoundNeeded()).thenReturn(true).thenReturn(false);
        Kingdom qualifier1 = mock(Kingdom.class);
        Kingdom qualifier2 = mock(Kingdom.class);
        when(countingStation.getQualifiedCandidates()).thenReturn(Arrays.asList(qualifier1, qualifier2));

        election.execute(universe, consoleIO);

        verify(balletMessageConstructor).constructMessages(qualifier1);
        verify(balletMessageConstructor).constructMessages(qualifier2);
    }

    @Test
    void setRulerOfTheUniverseIfNoFurtherRoundsAreNeeded() {
        when(consoleIO.getInput()).thenReturn("water land");
        when(countingStation.isNextRoundNeeded()).thenReturn(false);
        Kingdom ruler = mock(Kingdom.class);
        when(countingStation.getQualifiedCandidates()).thenReturn(Collections.singletonList(ruler));

        election.execute(universe, consoleIO);

        verify(universe).setRuler(ruler);
    }
}
