package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class AddMentorCommand extends AddCommand {

    /* Possible Fields: */

    public static final String ENTITY_TYPE = "mentor";

    private Mentor mentor;

    public AddMentorCommand(Mentor mentor) {
        requireNonNull(mentor);
        this.mentor = mentor;
    }

    @Override
    public CommandResult execute(EntityList entityList) throws CommandException {
        requireNonNull(entityList);

        // See AddIssueCommand

        return new CommandResult("");
    }

}
