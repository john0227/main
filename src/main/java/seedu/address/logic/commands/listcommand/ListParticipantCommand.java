package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Participant;

/**
 * Lists every {@link Participant} in Alfred.
 */
public class ListParticipantCommand extends ListCommand {

    public static final String COMMAND_WORD = "list participant";
    public static final String MESSAGE_SUCCESS = "Listed all participants";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all of the participants.\n"
            + "Example: " + COMMAND_WORD;
    private static final String MESSAGE_HEADER = "List of all participants:";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        System.out.println(MESSAGE_HEADER);
        model.getParticipantList().list().forEach(this::listEntity);

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
