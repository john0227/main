package seedu.address.logic.commands.editcommand;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class EditTeamCommand extends EditCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "team";

    private Id id;
    private EditTeamDescriptor editTeamDescriptor;

    public EditTeamCommand(Id id, EditTeamDescriptor editTeamDescriptor) {
        this.id = id;
        this.editTeamDescriptor = editTeamDescriptor;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("");
    }

    public static class EditTeamDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
