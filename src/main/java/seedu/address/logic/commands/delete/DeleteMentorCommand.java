package seedu.address.logic.commands.delete;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class DeleteMentorCommand extends DeleteCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "mentor";

    private Mentor mentor;

    public DeleteMentorCommand(Mentor mentor) {
        requireNonNull(mentor)
        this.mentor = mentor;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}