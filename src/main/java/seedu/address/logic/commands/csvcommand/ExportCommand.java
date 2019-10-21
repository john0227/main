package seedu.address.logic.commands.csvcommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports Alfred data into an external CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export"; // or any other suggestions
    public static final String MESSAGE_SUCCESS = "Exported all data to %s"; // %s -> file name
    public static final String MESSAGE_IO_EXCEPTION = "An IOException was caught: %s"; // %s -> exception message
    public static final String MESSAGE_INVALID_PATH_EXCEPTION =
            "Given file path, %s, is invalid"; // %s -> this.csvFileName
    public static final String MESSAGE_EMPTY_DATA = "No data to export. File was not created.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": exports Alfred data to a CSV file. "
            + "Parameters: "
            + "[ENTITY] "
            + PREFIX_FILE_PATH + "[FILE_PATH] "
            + PREFIX_FILE_NAME + "[FILE_NAME]\n"
            + "\tExample 1: " + COMMAND_WORD
            + " (Creates AlfredData/Alfred_Entity_List.csv at current working directory)\n"
            + "\tExample 2 (Windows): " + COMMAND_WORD + " C:/Users/USER"
            + " (Creates Alfred_Entity_List.csv at C:/Users/USER)\n" // TODO: Add other examples on different platforms
            + "\tExample 3 (Windows): " + COMMAND_WORD + " C:/Users/USER Alfred_CSV"
            + " (Creates a CSV file named Alfred_CSV.csv at C:/Users/USER)\n"
            + "Note the path does not have to be absolute (i.e. can be relative). "
            + "Hence, if . is inputted, Alfred will create the CSV file at current working directory.\n";
    public static final String ASSERTION_FAILED_NOT_CSV = "File given is not a CSV file.";

    public static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + File.separator + "AlfredData";
    public static final String DEFAULT_FILE_NAME = "Alfred_Data.csv";

    protected Path csvFileName;

    public ExportCommand(String csvFilePath, String csvFileName) throws CommandException {
        assert csvFileName.isBlank() || csvFileName.toLowerCase().endsWith(".csv") : ASSERTION_FAILED_NOT_CSV;
        // If either one of filePath or fileName was not specified, go with default values
        if (csvFilePath.isBlank()) {
            csvFilePath = DEFAULT_FILE_PATH;
        }
        if (csvFileName.isBlank()) {
            csvFileName = DEFAULT_FILE_NAME;
        }
        try {
            this.csvFileName = Path.of(csvFilePath, csvFileName);
        } catch (InvalidPathException ipe) {
            throw new CommandException(String.format(
                    MESSAGE_INVALID_PATH_EXCEPTION,
                    String.join(File.separator, csvFilePath, csvFileName)
            ));
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_DATA);
        }
        try {
            File csvFile = this.csvFileName.toFile();
            FileUtil.createIfMissing(this.csvFileName);
            CsvUtil.writeToCsv(csvFile, model);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, ioe.toString()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.csvFileName.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ExportCommand)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        ExportCommand command = (ExportCommand) other;
        return this.csvFileName.equals(command.csvFileName);
    }

}
