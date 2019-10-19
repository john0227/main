package seedu.address.logic.parser.cvscommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.csvcommand.LoadCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.csvcommandparser.LoadCommandParser;
import seedu.address.testutil.TestUtil;

public class LoadCommandParserTest {

    private final Parser parser = new LoadCommandParser();

    @Test
    public void parse_validParameterPassedIn_validCommandReturned() {
        String path = TestUtil.getFilePathInSandboxFolder("Alfred.csv").toString();
        assertParseSuccess(parser, " " + PREFIX_FILE_NAME + path, new LoadCommand(path));
    }

    @Test
    public void parse_invalidParametersPassedIn_exceptionThrown() {
        // Only csv files should be passed in
        String path = " " + PREFIX_FILE_NAME + TestUtil.getFilePathInSandboxFolder("Alfred.txt").toString();
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));

        // Preamble must be blank
        path = "mentor" + PREFIX_FILE_NAME + "Alfred.csv";
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));

        // PREFIX_FILE_NAME is not present
        path = " " + TestUtil.getFilePathInSandboxFolder("Alfred.csv").toString();
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));

        // File name is blank
        path = " " + PREFIX_FILE_NAME.getPrefix();
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
    }

}
