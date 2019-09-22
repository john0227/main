package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;

public class ViewMentorCommand extends ViewCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "mentor";

    public ViewMentorCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(EntityList entityList) {
        requireNonNull(model);

        // See ViewIssueCommand

        return new CommandResult("");
    }

}
