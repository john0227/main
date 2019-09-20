package seedu.address.logic.commands.delete;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class DeleteParticipantCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "participant";

    private Participant participant;

    public DeleteParticipantCommand(Participant participant) {
        requireNonNull(participant);
        this.participant = participant;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}
