package com.goldencrown.controller;

import com.goldencrown.model.Kingdom;
import com.goldencrown.model.Message;

import java.util.List;

import static java.util.Objects.isNull;

public class BalletMessageValidation extends BasicMessageValidation {
    private List<Kingdom> electionNominees;

    @Override
    public boolean isValid(Message message) {
        if (isNull(electionNominees) || electionNominees.isEmpty()) {
            return false;
        }

        boolean isForElectionNominee = electionNominees.contains(message.getReceiver());
        return !isForElectionNominee && super.isValid(message);
    }

    public void setElectionNominees(List<Kingdom> electionNominees) {
        this.electionNominees = electionNominees;
    }
}
