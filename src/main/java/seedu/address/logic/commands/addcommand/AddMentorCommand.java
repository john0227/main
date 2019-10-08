package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;

/**
 * Adds a {@link Mentor} to Alfred.
 */
public class AddMentorCommand extends AddCommand {

    public static final String COMMAND_WORD = "addMentor";
    private static final String MESSAGE_SUCCESS = "New mentor added: %s";
    private static final String MESSAGE_DUPLICATE_MENTOR = "This mentor already exists in this Hackathon";

    private Mentor mentor;
    private Name teamName;

    public AddMentorCommand(Mentor mentor) {
        requireNonNull(mentor);
        this.mentor = mentor;
    }

    public AddMentorCommand(Mentor mentor, Name teamName) {
        CollectionUtil.requireAllNonNull(mentor, teamName);
        this.mentor = mentor;
        this.teamName = teamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.teamName != null) {
            // find team (or throw Exception)
            // add Mentor to team
            // return CommandResult
        }
        
        try {
            model.addMentor(this.mentor);
        } catch (Exception e) {
            // Should I return new CommandResult(MESSAGE_DUPLICATE_MENTOR) instead?
            throw new CommandException(MESSAGE_DUPLICATE_MENTOR);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.mentor.toString()));
    }

}
