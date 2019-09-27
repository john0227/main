package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class DeleteIssueCommand extends DeleteCommand {

    // Possible Fields?

    public DeleteIssueCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            IssueList issueList = model.getIssueList();
            issueList.delete(this.id);
        } catch (Exception e) {
            // Revise Exception later
            // throw CommandException
        }

        return new CommandResult("");
    }

}
