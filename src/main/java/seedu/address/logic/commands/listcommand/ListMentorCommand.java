package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;

/**
 * Lists every {@link Mentor} in Alfred.
 */
public class ListMentorCommand extends ListCommand {

    /* Possible Fields? */
    public static final String MESSAGE_SUCCESS = "Listed all mentors";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.getMentorList().list().forEach(m -> listEntity(m.viewMinimal()));

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
