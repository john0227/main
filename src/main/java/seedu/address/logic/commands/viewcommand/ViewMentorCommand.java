package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;

public class ViewMentorCommand extends ViewCommand {

    /* Possible Fields? */

    public ViewMentorCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // See ViewIssueCommand

        return new CommandResult("");
    }

}