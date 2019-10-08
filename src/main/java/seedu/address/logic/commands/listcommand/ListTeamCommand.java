package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Team;

/**
 * Lists every {@link Team} in Alfred.
 */
public class ListTeamCommand extends ListCommand {

    /* Possible Fields? */
    public static final String MESSAGE_SUCCESS = "Listed all teams";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.getTeamList().list().forEach(t -> listEntity(t.viewMinimal()));

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
