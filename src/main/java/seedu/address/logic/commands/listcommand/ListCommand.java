package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/*
 * This Command is for listing everything (issue, mentor, participant, team).
 */
public class ListCommand extends Command {

    /* Possible Fields */

    public static final String COMMAND_TYPE = "list";

    @Override
    public CommandResult execute(EntityList entityList) throws CommandException {
        requireNonNull(entityList);

        // Possible format
        // TeamList teamList = entityList.getTeamList();

        // I think Team should have a method that lists its connections (e.g. Team#showConnection)
        // i.e.
        // Team: <Team Name> (Mentor: <Mentor Name>)
        // Participants:
        //    <Participant 1> (in this team)
        //    ...
        //    <Participant n>

        /*
         * // List teams and its connections
         * List<Team> teams = teamList.list();
         * for (Team team : teams) {
         *     print(team.showConnection());
         * }
         * // or teamList.list().stream().forEach(Team::showConnection());
         *
         * // List issues
         * (new ListIssueCommand()).execute(entityList);
         */

        return new CommandResult("");
    }

}
