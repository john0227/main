package seedu.address.logic.commands.editcommand;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class EditMentorCommand extends EditCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "participant";

    private Id id;
    private EditMentorDescriptor editMentorDescriptor;

    public EditParticipantCommand(Id id, EditMentorDescriptor editMentorDescriptor) {
        this.id = id;
        this.editMentorDescriptor = editMentorDescriptor;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("");
    }

    public static class EditMentorDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
