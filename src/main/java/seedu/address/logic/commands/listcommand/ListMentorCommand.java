package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;

public class ListMentorCommand extends ListCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "mentor";

    @Override
    public CommandResult execute(EntityList entityList) {
        requireNonNull(entityList);

        // See ListIssueCommand

        return new CommandResult("");
    }

}
