package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;

public class ListIssueCommand extends ListCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "issue";

    @Override
    public CommandResult execute(EntityList entityList) {
        requireNonNull(entityList);

        // Possible format
        /*
         * IssueList issueList = entityList.getIssueList();
         * for (Issue issue : issueList) {
         *     print(issue); // implement toString()
         * }
         */
        /*
         * entityList.getIssueList()
         *           .list()
         *           .stream()
         *           .forEach(Issue::toString());
         */

        return new CommandResult("");
    }

}
