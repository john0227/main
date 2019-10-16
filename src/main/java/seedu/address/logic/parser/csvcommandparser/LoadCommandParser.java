package seedu.address.logic.parser.csvcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_NAME;

import seedu.address.logic.commands.csvcommand.LoadCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses {@code userInput} into a LoadCommand.
 */
public class LoadCommandParser implements Parser<LoadCommand> {

    @Override
    public LoadCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE_NAME);
        if (!argMultimap.getPreamble().isBlank()
                || !AlfredParserUtil.arePrefixesPresent(argMultimap, PREFIX_FILE_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }

        String fileName = argMultimap.getValue(PREFIX_FILE_NAME).orElse("");
        if (!fileName.isEmpty() && !fileName.toLowerCase().endsWith(".csv")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }

        return new LoadCommand(fileName);
    }
}
