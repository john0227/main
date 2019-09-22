package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class DeleteParticipantCommand extends DeleteCommand {

    /* Possible Fields? */

    public DeleteParticipantCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // See DeleteIssueCommand

        return new CommandResult("");
    }

}
