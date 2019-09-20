package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class DeleteIssueCommand extends DeleteCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "issue";

    private Id id;

    public DeleteIssueCommand(Id id) {
        requireNonNull(id);
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}
