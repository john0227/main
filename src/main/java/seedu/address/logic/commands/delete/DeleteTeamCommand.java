package seedu.address.logic.commands.delete;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class DeleteTeamCommand extends DeleteCommand {

    /* Possible Fields */

    public static final String ENTITY_TYPE = "team";

    private Team team;

    public DeleteTeamCommand(Team team) {
        requireNonNull(team);
        this.team = team;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("");
    }

}
