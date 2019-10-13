package seedu.address.logic.commands.csvcommand;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export"; // or any other suggestions
    public static final String MESSAGE_SUCCESS = "Exported all data to %s"; // %s -> file name
    public static final String MESSAGE_IO_EXCEPTION = "An IOException was caught: %s"; // %s -> exception message
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": exports Alfred data to a CSV file. "
            + "Parameters: "
            + "[FILE_PATH] "   // PrefixType
            + "[FILE_NAME]\n"  // PrefixType
            + "Example 1: " + COMMAND_WORD
            + " (Creates a CSV file named Alfred_Entity_List.csv at current working directory)\n"
            + "Example 2: " + COMMAND_WORD + " [Valid path]\n" // TODO: fill path in later (with PrefixType)
            + "Example 3: " + COMMAND_WORD + " [Valid path] Alfred_CSV" // TODO: fill path in later (with PrefixType)
            + " (Creates a CSV file named Alfred_CSV.csv at specified path)";

    public static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + File.separator + "AlfredData";
    public static final String DEFAULT_FILE_NAME = "Alfred_Entity_List";
    private String csvFileName;

    public ExportCommand() {
        this(DEFAULT_FILE_PATH, DEFAULT_FILE_NAME);
    }

    public ExportCommand(String csvFilePath) {
        this(csvFilePath, DEFAULT_FILE_NAME);
    }

    public ExportCommand(String csvFilePath, String csvFileName) {
        if (csvFilePath.isEmpty()) {
            csvFilePath = DEFAULT_FILE_PATH;
        }
        this.csvFileName = csvFilePath + File.separator + csvFileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        BufferedWriter csvWriter;
        try {
            File csvFile = new File(this.csvFileName);
            FileUtil.createIfMissing(csvFile.toPath());
            csvWriter = new BufferedWriter(new FileWriter(csvFile));
            this.writeToCsv(csvWriter, model);
            csvWriter.close();
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, ioe.toString()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.csvFileName));
    }

    private void writeToCsv(BufferedWriter csvWriter, Model model) throws CommandException {
        try {
            CsvUtil.writeMentors(csvWriter, model.getMentorList());
            CsvUtil.writeParticipants(csvWriter, model.getParticipantList());
            CsvUtil.writeTeams(csvWriter, model.getTeamList());
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, e.toString()));
        }
    }

}
