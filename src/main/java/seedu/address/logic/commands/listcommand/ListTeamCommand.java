package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;

public class ListTeamCommand extends ListCommand {

    /* Possible Fields */
    public static final String ENTITY_TYPE = "team";

    @Override
    public CommandResult execute(EntityList entityList) {
        requireNonNull(entityList);

        // see ListIssueCommand

        return new CommandResult("");
    }

}
