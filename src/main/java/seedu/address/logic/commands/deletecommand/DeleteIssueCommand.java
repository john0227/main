package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class DeleteIssueCommand extends DeleteCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "issue";

    public DeleteIssueCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(EntityList entityList) throws CommandException {
        requireNonNull(entityList);

        // IssueList issueList = entityList.getIssueList();
        issueList.delete(this.id);

        return new CommandResult("");
    }

}
