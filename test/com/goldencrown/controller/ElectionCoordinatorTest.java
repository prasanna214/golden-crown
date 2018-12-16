package com.goldencrown.controller;

import com.goldencrown.model.Kingdom;
import com.goldencrown.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ElectionCoordinatorTest {
    private ElectionCoordinator electionCoordinator;

    @BeforeEach
    void setUp() {
        electionCoordinator = new ElectionCoordinator();
    }

    @Test
    void returnNullIfBalletBoxIsNull() {
        assertNull(electionCoordinator.pickRandomMessages(null, 1));
    }

    @Test
    void returnEmptyListIfBalletIsEmpty() {
        List<Message> emptyBallet = new ArrayList<>();
        assertEquals(emptyBallet, electionCoordinator.pickRandomMessages(emptyBallet, 1));
    }

    @Test
    void returnShuffledMessagesFromBallet() {
        Message message = mock(Message.class);
        Message message1 = mock(Message.class);
        Message message2 = mock(Message.class);
        Message message3 = mock(Message.class);
        List<Message> ballet = Arrays.asList(message, message1, message2, message3);

        int requirement = 4;
        List<Message> firstRandomList = electionCoordinator.pickRandomMessages(ballet, requirement);
        assertEquals(requirement, firstRandomList.size());
        assertTrue(ballet.containsAll(firstRandomList));

        List<Message> secondRandomList = electionCoordinator.pickRandomMessages(ballet, requirement);
        assertEquals(requirement, secondRandomList.size());
        assertTrue(ballet.containsAll(secondRandomList));

        assertNotEquals(firstRandomList, secondRandomList);
    }

    @Test
    void returnRequiredNumberOfMessagesFromBallet() {
        Message message = mock(Message.class);
        Message message1 = mock(Message.class);
        Message message2 = mock(Message.class);
        Message message3 = mock(Message.class);
        List<Message> ballet = Arrays.asList(message, message1, message2, message3);

        int requirement = 2;
        List<Message> messages = electionCoordinator.pickRandomMessages(ballet, requirement);
        assertEquals(requirement, messages.size());
        assertTrue(ballet.containsAll(messages));
    }

    @Test
    void returnWholeBalletWhenRequirementIsGreaterThanBalletSize() {
        ArrayList<Message> ballet = new ArrayList<>();
        Message message = mock(Message.class);
        ballet.add(message);

        assertEquals(ballet, electionCoordinator.pickRandomMessages(ballet, 2));
    }

    @Test
    void doNothingIfToBeDistributedMessageListIsNull() {
        try {
            electionCoordinator.distributeToReceivers(null);
        } catch (NullPointerException exception) {
            fail("Exception thrown");
        }
    }

    @Test
    void distributeMessageToTheirReceivers() {
        Message message = mock(Message.class);
        Kingdom receiver1 = mock(Kingdom.class);
        Kingdom receiver2 = mock(Kingdom.class);
        when(message.getReceiver()).thenReturn(receiver1).thenReturn(receiver2);
        List<Message> messages = Arrays.asList(message, message);

        electionCoordinator.distributeToReceivers(messages);

        verify(receiver1).processAllyInvite(message);
        verify(receiver2).processAllyInvite(message);
    }
}
