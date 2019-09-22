package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;

public abstract class EditCommand extends Command {

    /* Possible Fields */

    public static final String COMMAND_TYPE = "edit";

    protected Id id;

    EditCommand(Id id) {
        requireNonNull(id);
        this.id = id;
    }

    public static class EditEntityDescriptor {
        /*
         * Contain fields common to all entities
         *   - Name/Description
         *   - Id
         * Have subclasses implement their own unique attributes
         */
    }

}
