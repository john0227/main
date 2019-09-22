package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class DeleteTeamCommand extends DeleteCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "team";

    public DeleteTeamCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(EntityList entityList) throws CommandException {
        requireNonNull(entityList);

        // See DeleteIssueCommand

        return new CommandResult("");
    }

}
