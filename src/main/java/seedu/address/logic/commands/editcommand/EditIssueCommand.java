package seedu.address.logic.commands.editcommand;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class EditIssueCommand extends EditCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "issue";

    private Id id;
    private EditIssueDescriptor editIssueDescriptor;

    public EditIssueCommand(Id id, EditIssueDescriptor editIssueDescriptor) {
        this.id = id;
        this.editIssueDescriptor = editIssueDescriptor;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("");
    }

    public static class EditIssueDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
