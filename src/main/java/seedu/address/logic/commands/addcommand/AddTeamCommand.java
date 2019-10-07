package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Team;

/**
 * Adds a {@link Team} to Alfred.
 */
public class AddTeamCommand extends AddCommand {

    public static final String COMMAND_WORD = "addTeam";
    private static final String MESSAGE_SUCCESS = "New team added: %s";
    private static final String MESSAGE_DUPLICATE_TEAM = "This team already exists in this Hackathon";

    private Team team;

    public AddTeamCommand(Team team) {
        requireNonNull(team);
        this.team = team;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.addTeam(this.team);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_DUPLICATE_TEAM);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.team.toString()));
    }

}
