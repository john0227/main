package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class DeleteMentorCommand extends DeleteCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "mentor";

    private Id id;

    public DeleteMentorCommand(Id id) {
        requireNonNull(id);
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}