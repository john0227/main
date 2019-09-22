package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ViewParticipantCommand extends ViewCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "participant";

    public ViewParticipantCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // See ViewIssueCommand

        return new CommandResult("");
    }

}
