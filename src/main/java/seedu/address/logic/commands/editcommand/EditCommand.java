package seedu.address.logic.commands.editcommand;

import seedu.address.logic.commands.Command;

public abstract class EditCommand extends Command {

    /* Possible Fields */

    public static final String COMMAND_TYPE = "edit";

    public static class EditEntityDescriptor {
        /*
         * Contain fields common to all entities
         *   - Name/Description
         *   - Id
         * Have subclasses implement their own unique attributes
         */
    }

}
