package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class DeleteParticipantCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "participant";

    private Id id;

    public DeleteParticipantCommand(Id id) {
        requireNonNull(id);
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}
