package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Team;

/**
 * Deletes a {@link Team} in Alfred.
 */
public class DeleteTeamCommand extends DeleteCommand {

    private static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The team ID provided is invalid";
    private static final String MESSAGE_DELETE_TEAM_SUCCESS = "Deleted Person: %1$s";

    public DeleteTeamCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Team teamToBeDeleted;
        try {
            teamToBeDeleted = model.deleteTeam(this.id);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TEAM_SUCCESS,
                teamToBeDeleted.toString()));
    }

}
