package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class AddIssueCommand extends AddCommand {

    /* Possible Fields: */

    public static final String ENTITY_TYPE = "issue";

    private Issue issue;

    public AddIssueCommand(Issue issue) {
        requireNonNull(issue);
        this.issue = issue;
    }

    @Override
    public CommandResult execute(EntityList entityList) throws CommandException {
        requireNonNull(entityList);

        // IssueList issueList = entityList.getIssueList();
        issueList.add(this.issue);

        return new CommandResult("");
    }

}
