package seedu.address.logic.parser.cvscommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.csvcommand.ExportCommand;
import seedu.address.logic.commands.csvcommand.ExportMentorCommand;
import seedu.address.logic.commands.csvcommand.ExportParticipantCommand;
import seedu.address.logic.commands.csvcommand.ExportTeamCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.csvcommandparser.ExportCommandParser;

public class ExportCommandParserTest {

    private static final String PREFIX_FILE_PATH = " " + CliSyntax.PREFIX_FILE_PATH;
    private static final String PREFIX_FILE_NAME = " " + CliSyntax.PREFIX_FILE_NAME;
    private static final String PREAMBLE_MENTOR = "mentor";
    private static final String PREAMBLE_PARTICIPANT = "participant";
    private static final String PREAMBLE_TEAM = "team";
    private final Parser parser = new ExportCommandParser();

    @Test
    public void parse_validParametersPassedIn_exportCommandReturned() throws CommandException {
        String filePath = "";
        String fileName = "";
        // Empty file path and file name -> default file path and file name
        assertParseSuccess(
                parser,
                "",
                new ExportCommand(filePath, fileName)
        );

        // Empty file path -> default file path
        fileName = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREFIX_FILE_NAME + fileName,
                new ExportCommand(filePath, fileName)
        );

        // Empty file name -> default file name
        fileName = "";
        filePath = ".";
        assertParseSuccess(
                parser,
                PREFIX_FILE_PATH + filePath,
                new ExportCommand(filePath, fileName)
        );

        // Pass in both file path and file name
        fileName = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREFIX_FILE_NAME + fileName + PREFIX_FILE_PATH + filePath,
                new ExportCommand(filePath, fileName)
        );
    }

    @Test
    public void parse_validParametersPassedIn_exportMentorCommandReturned() throws CommandException {
        String filePath = "";
        String fileName = "";
        // Empty file path and file name -> default file path and file name
        assertParseSuccess(
                parser,
                PREAMBLE_MENTOR,
                new ExportMentorCommand(filePath, fileName)
        );

        // Empty file path -> default file path
        fileName = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREAMBLE_MENTOR + PREFIX_FILE_NAME + fileName,
                new ExportMentorCommand(filePath, fileName)
        );

        // Empty file name -> default file name
        fileName = "";
        filePath = ".";
        assertParseSuccess(
                parser,
                PREAMBLE_MENTOR + PREFIX_FILE_PATH + filePath,
                new ExportMentorCommand(filePath, fileName)
        );

        // Pass in both file path and file name
        fileName = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREAMBLE_MENTOR + PREFIX_FILE_NAME + fileName + PREFIX_FILE_PATH + filePath,
                new ExportMentorCommand(filePath, fileName)
        );
    }

    @Test
    public void parse_validParametersPassedIn_exportParticipantCommandReturned() throws CommandException {
        String filePath = "";
        String fileName = "";
        // Empty file path and file name -> default file path and file name
        assertParseSuccess(
                parser,
                PREAMBLE_PARTICIPANT,
                new ExportParticipantCommand(filePath, fileName)
        );

        // Empty file path -> default file path
        fileName = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREAMBLE_PARTICIPANT + PREFIX_FILE_NAME + fileName,
                new ExportParticipantCommand(filePath, fileName)
        );

        // Empty file name -> default file name
        fileName = "";
        filePath = ".";
        assertParseSuccess(
                parser,
                PREAMBLE_PARTICIPANT + PREFIX_FILE_PATH + filePath,
                new ExportParticipantCommand(filePath, fileName)
        );

        // Pass in both file path and file name
        fileName = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREAMBLE_PARTICIPANT + PREFIX_FILE_NAME + fileName + PREFIX_FILE_PATH + filePath,
                new ExportParticipantCommand(filePath, fileName)
        );
    }

    @Test
    public void parse_validParametersPassedIn_exportTeamCommandReturned() throws CommandException {
        String filePath = "";
        String fileName = "";
        // Empty file path and file name -> default file path and file name
        assertParseSuccess(
                parser,
                PREAMBLE_TEAM,
                new ExportTeamCommand(filePath, fileName)
        );

        // Empty file path -> default file path
        fileName = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREAMBLE_TEAM + PREFIX_FILE_NAME + fileName,
                new ExportTeamCommand(filePath, fileName)
        );

        // Empty file name -> default file name
        fileName = "";
        filePath = ".";
        assertParseSuccess(
                parser,
                PREAMBLE_TEAM + PREFIX_FILE_PATH + filePath,
                new ExportTeamCommand(filePath, fileName)
        );

        // Pass in both file path and file name
        fileName = "Alfred.csv";
        assertParseSuccess(
                parser,
                PREAMBLE_TEAM + PREFIX_FILE_NAME + fileName + PREFIX_FILE_PATH + filePath,
                new ExportTeamCommand(filePath, fileName)
        );
    }

    @Test
    public void parse_invalidParametersPassedIn_parseExceptionThrown() {
        // Non-csv file passed
        String invalidFileName = "Alfred.txt";
        assertParseFailure(
                parser,
                PREFIX_FILE_NAME + invalidFileName,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE)
        );

        // Invalid preamble/entity
        String invalidPreamble = "Participants";
        String validFileName = "Alfred.csv";
        assertParseFailure(
                parser,
                invalidPreamble + PREFIX_FILE_NAME + validFileName,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE)
        );
    }

}
