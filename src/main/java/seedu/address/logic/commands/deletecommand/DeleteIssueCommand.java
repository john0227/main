package seedu.address.logic.commands.deletecommand;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class DeleteIssueCommand extends DeleteCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "issue";

    public DeleteIssueCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}
