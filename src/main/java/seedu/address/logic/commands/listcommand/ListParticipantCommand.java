package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Participant;

/**
 * Lists every {@link Participant} in Alfred.
 */
public class ListParticipantCommand extends ListCommand {

    /* Possible Fields? */
    public static final String MESSAGE_SUCCESS = "Listed all participants";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.getParticipantList().list().forEach(p -> listEntity(p));

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
