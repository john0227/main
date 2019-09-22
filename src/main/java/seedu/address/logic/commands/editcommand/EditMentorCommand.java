package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

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
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // see EditIssueCommand

        return new CommandResult("");
    }

    private Mentor createEditedMentor(Mentor mentorToEdit, EditMentorDescriptor editMentorDescriptor) {
        // Set each field to updated value
        // See EditCommand#EditPersonDescriptor for more context

        return new Mentor(/* Necessary Fields */);
    }

    public static class EditMentorDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
