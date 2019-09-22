package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class EditIssueCommand extends EditCommand {

    /* Possible Fields */
    private EditIssueDescriptor editIssueDescriptor;

    public EditIssueCommand(Id id, EditIssueDescriptor editIssueDescriptor) {
        super(id);
        requireNonNull(editIssueDescriptor);
        /*
         * Parser should check whether there is anything to edit
         */
        this.editIssueDescriptor = editIssueDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // IssueList issueList = model.getIssueList();
        Issue issueToEdit;
        try {
            issueToEdit = issueList.get(this.id);
        } catch (Exception e) {
            // Refine exception later
            // Invalid ID exception (or smth like that)
            throw new CommandException("ERROR_MESSAGE to be defined later");
        }
        Issue editedIssue = this.createEditedIssue(issueToEdit, this.editIssueDescriptor);

        /*
         * TODO: IssueList#update() should check if issue at given ID and editedIssue are not same
         *  and should check if editedIssue is already contained in the IssueList
         * TODO: Make EntityListInterface#update() return boolean value?
         */
        if (model.update(issueToEdit.getId(), editedIssue)) {
//            return new CommandResult(MESSAGE_SUCCESS);
        }

        // return MESSAGE_FAILURE
        return new CommandResult("");
    }

    private Issue createEditedIssue(Issue issueToEdit, EditIssueDescriptor editIssueDescriptor) {
        // Set each field to updated value
        // See EditCommand#EditPersonDescriptor for more context

        return new Issue(/* Necessary Fields */);
    }

    public static class EditIssueDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

        /*
         * Implement getters and setters
         */

    }

}
