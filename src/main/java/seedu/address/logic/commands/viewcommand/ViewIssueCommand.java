package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ViewIssueCommand extends ViewCommand {

    /* Possible Fields? */

    public ViewIssueCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        IssueList issueList = model.getIssueList();
        // TODO: Decide on which class updates the GUI
        try {
            issueList.get(this.id).view();
        } catch (Exception e) {
            // Refine exception type later
            // Invalid ID Exception ...
            throw new CommandException("ERROR_MESSAGE to be determined");
        }

        return new CommandResult("");
    }

}
