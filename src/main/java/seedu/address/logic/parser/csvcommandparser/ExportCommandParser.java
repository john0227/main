package seedu.address.logic.parser.csvcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import seedu.address.logic.commands.csvcommand.ExportCommand;
import seedu.address.logic.commands.csvcommand.ExportMentorCommand;
import seedu.address.logic.commands.csvcommand.ExportParticipantCommand;
import seedu.address.logic.commands.csvcommand.ExportTeamCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses {@code userInput} into an ExportCommand.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    private static final String ENTITY_NOT_SPECIFIED = "";

    @Override
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE_PATH, PREFIX_FILE_NAME);
        String entity = argMultimap.getPreamble();
        String filePath = argMultimap.getValue(PREFIX_FILE_PATH).orElse("");
        String fileName = argMultimap.getValue(PREFIX_FILE_NAME).orElse("");

        if (!fileName.isEmpty() && !fileName.toLowerCase().endsWith(".csv")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        try {
            switch (entity.toLowerCase()) {
            case CliSyntax.ENTITY_MENTOR:
                return new ExportMentorCommand(filePath, fileName);
            case CliSyntax.ENTITY_PARTICIPANT:
                return new ExportParticipantCommand(filePath, fileName);
            case CliSyntax.ENTITY_TEAM:
                return new ExportTeamCommand(filePath, fileName);
            case ENTITY_NOT_SPECIFIED:
                return new ExportCommand(filePath, fileName);
            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
            }
        } catch (CommandException ce) {
            throw new ParseException(ce.getMessage());
        }
    }

}
