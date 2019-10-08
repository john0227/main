package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Name;

/**
 * Adds a {@link Participant} to Alfred.
 */
public class AddParticipantCommand extends AddCommand {

    public static final String COMMAND_WORD = "addParticipant";
    private static final String MESSAGE_SUCCESS = "New participant added: %s";
    private static final String MESSAGE_DUPLICATE_PARTICIPANT = "This participant already exists in this Hackathon";

    private Participant participant;
    private Name teamName;

    public AddParticipantCommand(Participant participant) {
        requireNonNull(participant);
        this.participant = participant;
    }

    public AddParticipantCommand(Participant participant, Name teamName) {
        CollectionUtil.requireAllNonNull(participant, teamName);
        this.participant = participant;
        this.teamName = teamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.teamName != null) {
            // find team (or throw Exception)
            // Add participant to team
            // Return CommandResult
        }

        try {
            model.addParticipant(this.participant);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_DUPLICATE_PARTICIPANT);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.participant.toString()));
    }

}
