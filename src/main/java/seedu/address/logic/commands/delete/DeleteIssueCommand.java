package seedu.address.logic.commands.delete;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class DeleteIssueCommand extends DeleteCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "issue";

    private Issue issue;

    public DeleteIssueCommand(Issue issue) {
        requireNonNull(issue);
        this.issue = issue;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}
