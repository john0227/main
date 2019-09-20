package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class EditIssueCommand extends EditCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "issue";

    private EditIssueDescriptor editIssueDescriptor;

    public EditIssueCommand(Id id, EditIssueDescriptor editIssueDescriptor) {
        super(id);
        requireNonNull(editIssueDescriptor);
        this.editIssueDescriptor = editIssueDescriptor;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

    public static class EditIssueDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
