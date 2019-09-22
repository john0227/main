package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class AddParticipantCommand extends AddCommand {

     /* Possible Fields */
     public static String ENTITY_TYPE = "participant";
     private Participant participant;

    public AddParticipantCommand(Participant participant) {
        requireNonNull(participant);
        this.participant = participant;
    }

    @Override
    public CommandResult execute(EntityList entityList) throws CommandException {
        requireNonNull(entityList);

        // See AddIssueCommand

        return new CommandResult("");
    }

}
