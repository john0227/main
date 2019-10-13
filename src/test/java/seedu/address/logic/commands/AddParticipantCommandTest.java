package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.addcommand.AddParticipantCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.Assert;
import seedu.address.testutil.ParticipantBuilder;

public class AddParticipantCommandTest {

    @Test
    public void constructor_nullParticipant_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddParticipantCommand(null));
    }

    @Test
    public void execute_participantAcceptedByModel_addSuccessful() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Participant validParticipant = new ParticipantBuilder().build();

        CommandResult commandResult = new AddParticipantCommand(validParticipant).execute(modelStub);

        assertEquals(String.format(AddParticipantCommand.MESSAGE_SUCCESS, validParticipant),
                commandResult.getFeedbackToUser());
        assertEquals(validParticipant, modelStub.getParticipant(new Id(PrefixType.P, 1)));
    }

    @Test
    public void execute_duplicateParticipant_throwsCommandException() throws AlfredException {
        Participant validParticipant = new ParticipantBuilder().build();
        AddParticipantCommand addParticipantCommand = new AddParticipantCommand(validParticipant);
        ModelManagerStub modelStub = new ModelManagerStub();
        addParticipantCommand.execute(modelStub);

        Assert.assertThrows(CommandException.class,
                AddParticipantCommand.MESSAGE_DUPLICATE_PARTICIPANT, () -> addParticipantCommand.execute(modelStub));
    }

}
