package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ListIssueCommand extends ListCommand {

    /* Possible Fields? */

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Possible format
        /*
         * IssueList issueList = model.getIssueList();
         * for (Issue issue : issueList) {
         *     print(issue); // implement toString()
         * }
         */
        /*
         * model.getIssueList()
         *      .list()
         *      .stream()
         *      .forEach(Entity::viewMinimal()); // overriding for correct method call
         */

        return new CommandResult("");
    }

}
