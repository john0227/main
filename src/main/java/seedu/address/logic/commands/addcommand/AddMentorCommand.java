package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;

/**
 * Adds a {@link Mentor} to Alfred.
 */
public class AddMentorCommand extends AddCommand {

    public static final String COMMAND_WORD = "addMentor";
    private static final String MESSAGE_SUCCESS = "New mentor added: %s";
    private static final String MESSAGE_DUPLICATE_MENTOR = "This mentor already exists in this Hackathon";

    private Mentor mentor;

    public AddMentorCommand(Mentor mentor) {
        requireNonNull(mentor);
        this.mentor = mentor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.addMentor(this.mentor);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_DUPLICATE_MENTOR);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.mentor.toString()));
    }

}
