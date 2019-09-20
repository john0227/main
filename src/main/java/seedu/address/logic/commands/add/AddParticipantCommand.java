package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class AddParticipantCommand extends AddCommand {

     /* Possible Fields */
     public static String ENTITY_TYPE = "participant";
     private Participant participant;

    public AddParticipantCommand(Participant participant) {
        requireNonNull(participant)
        this.participant = participant;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model)
        return new CommandResult("");
    }

}
