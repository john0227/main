package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;

public class ViewIssueCommand extends ViewCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "issue";

    public ViewIssueCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(EntityList entityList) {
        requireNonNull(entityList);

        // IssueList issueList = entityList.getIssueList();
        // TODO: Decide on which class updates the GUI
        issueList.get(this.id).view();

        return new CommandResult("");
    }

}
