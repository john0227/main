package seedu.address.stub;

import seedu.address.AlfredException;
import seedu.address.model.ModelManager;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;
import seedu.address.model.entitylist.ParticipantList;

/**
 * Stub for ModelManager in order to avoid saving of Entities created during tests.
 */
public class ModelManagerStub extends ModelManager {

    private ParticipantList participantList;

    public ModelManagerStub() {
        participantList = new ParticipantList();
    }

    @Override
    public void addParticipant(Participant participant) throws AlfredException {
        this.participantList.add(participant);
    }

    @Override
    public boolean updateParticipant(Id id, Participant participant) {
        return this.participantList.update(id, participant);
    }

    @Override
    public Participant deleteParticipant(Id id) throws AlfredException {
        return this.participantList.delete(id);
    }

}
