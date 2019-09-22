package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;

public class ViewParticipantCommand extends ViewCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "participant";

    public ViewParticipantCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(EntityList entityList) {
        requireNonNull(entityList);

        // See ViewIssueCommand

        return new CommandResult("");
    }

}
