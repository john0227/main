package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Team;

/**
 * Lists every {@link Team} in Alfred.
 */
public class ListTeamCommand extends ListCommand {

    public static final String COMMAND_WORD = "list team";
    public static final String MESSAGE_SUCCESS = "Listed all teams";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all of the mentors.\n"
            + "Example: " + COMMAND_WORD;
    private static final String MESSAGE_HEADER = "List of all teams:";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        System.out.println(MESSAGE_HEADER);
        model.getTeamList().list().forEach(this::listEntity);

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
