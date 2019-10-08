package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;

/**
 * Shows detailed view of the {@link Mentor} at specified ID.
 */
public class ViewMentorCommand extends ViewCommand {

    public static final String MESSAGE_SUCCESS = "Showed specified mentor";
    public static final String MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX = "The mentor index provided is invalid";

    public ViewMentorCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Mentor mentorToView;
        try {
            mentorToView = model.getMentor(this.id);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX);
        }
        viewEntity(mentorToView);

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
