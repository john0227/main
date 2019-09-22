package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AddIssueCommand extends AddCommand {

    /* Possible Fields: */

    public static final String ENTITY_TYPE = "issue";

    private Issue issue;

    public AddIssueCommand(Issue issue) {
        requireNonNull(issue);
        this.issue = issue;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            IssueList issueList = model.getIssueList();
            issueList.add(this.issue);
        } catch (Exception e) {
            // I don't think this Exception will be ever caught
        }

        return new CommandResult("");
    }

}
