package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class EditParticipantCommand extends EditCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "participant";

    private EditParticipantDescriptor editParticipantDescriptor;

    public EditParticipantCommand(Id id, EditParticipantDescriptor editParticipantDescriptor) {
        super(id);
        requireNonNull(editParticipantDescriptor);
        this.editParticipantDescriptor = editParticipantDescriptor;
    }

    @Override
    public CommandResult execute(EntityList entityList) throws CommandException {
        requireNonNull(entityList);

        // see EditIssueCommand

        return new CommandResult("");
    }

    private Participant createEditedParticipant(Participant participantToEdit,
                                                EditParticipantDescriptor editParticipantDescriptor) {
        // Set each field to updated value
        // See EditCommand#EditPersonDescriptor for more context

        return new Participant(/* Necessary Fields */);
    }

    public static class EditParticipantDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
