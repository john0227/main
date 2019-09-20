package seedu.address.logic.commands.editcommand;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class EditParticipantCommand extends EditCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "participant";

    private Id id;
    private EditParticipantDescriptor editParticipantDescriptor;

    public EditParticipantCommand(Id id, EditParticipantDescriptor editParticipantDescriptor) {
        this.id = id;
        this.editParticipantDescriptor = editParticipantDescriptor;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("");
    }

    public static class EditParticipantDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
