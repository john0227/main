package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ListCommand extends Command {

    /* Possible Fields */

    public static final String COMMAND_TYPE = "list";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}
