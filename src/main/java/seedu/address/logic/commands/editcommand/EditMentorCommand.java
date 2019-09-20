package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class EditMentorCommand extends EditCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "mentor";

    private EditMentorDescriptor editMentorDescriptor;

    public EditMentorCommand(Id id, EditMentorDescriptor editMentorDescriptor) {
        super(id);
        requireNonNull(editMentorDescriptor);
        this.editMentorDescriptor = editMentorDescriptor;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

    public static class EditMentorDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
