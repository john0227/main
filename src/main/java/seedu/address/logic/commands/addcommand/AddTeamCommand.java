package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class AddTeamCommand extends AddCommand {

    /* Possible Fields: */

    public static final String ENTITY_TYPE = "team";

    private Team team;

    public AddTeamCommand(Team team) {
        requireNonNull(team)
        this.team = team;
    }

    @Override
    public CommandResult execute(EntityList entityList) throws CommandException {
        requireNonNull(entityList);

        // See AddIssueCommand

        return new CommandResult("");
    }

}
