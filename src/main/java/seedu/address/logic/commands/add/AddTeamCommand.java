package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class AddTeamCommand extends AddCommand {

    /* Possible Fields: */

    public static final String ENTITY_TYPE = "team";

    private Team team;

    public AddTeamCommand(Team team) {
        requireNonNull(team)
        this.team = team;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}
