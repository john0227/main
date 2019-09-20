package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ViewMentorCommand extends ViewCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "mentor";

    public ViewMentorCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}
