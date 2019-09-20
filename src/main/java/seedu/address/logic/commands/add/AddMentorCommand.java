package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class AddMentorCommand extends AddCommand {

    /* Possible Fields: */

    public static final String ENTITY_TYPE = "mentor";

    private Mentor mentor;

    public AddMentorCommand(Mentor mentor) {
        requireNonNull(mentor)
        this.mentor = mentor;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model)
        return new CommandResult("");
    }

}
