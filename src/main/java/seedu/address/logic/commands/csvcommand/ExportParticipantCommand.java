package seedu.address.logic.commands.csvcommand;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ExportParticipantCommand extends ExportCommand {

    public static final String COMMAND_WORD = "export participant";
    public static final String MESSAGE_SUCCESS = "Exported all participants to %s"; // %s -> file name
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": exports all the participants in Alfred to a CSV file. "
            + "Parameters: "
            + "[FILE_PATH] "   // PrefixType
            + "[FILE_NAME]\n"  // PrefixType
            + "Example 1: " + COMMAND_WORD
            + " (Creates AlfredData/Alfred_Entity_List.csv at current working directory)\n"
            + "Window Example 2: " + COMMAND_WORD + " C:/Users/USER"
            + " (Creates Alfred_Entity_List.csv at C:/Users/USER)" // TODO: Add other examples on different platforms
            + "Example 3: " + COMMAND_WORD + " C:/Users/USER Alfred_CSV"
            + " (Creates a CSV file named Alfred_CSV.csv at C:/Users/USER)\n"
            + "Note the path does not have to be absolute (i.e. can be relative). "
            + "Hence, if you leave path as blank, Alfred will create the CSV file at current working directory.\n"
            + "Following \"export\", first parameter will always be treated as the path "
            + "and the second will always be treated as the file name.";

    public ExportParticipantCommand() {
        super();
    }

    public ExportParticipantCommand(String csvFilePath) {
        super(csvFilePath);
    }

    public ExportParticipantCommand(String csvFilePath, String csvFileName) {
        super(csvFilePath, csvFileName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        BufferedWriter csvWriter;
        try {
            File csvFile = new File(this.csvFileName);
            FileUtil.createIfMissing(csvFile.toPath());
            csvWriter = new BufferedWriter(new FileWriter(csvFile));
            this.writeParticipantsToCsv(csvWriter, model);
            csvWriter.close();
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, ioe.toString()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.csvFileName));
    }

    private void writeParticipantsToCsv(BufferedWriter csvWriter, Model model) throws CommandException {
        try {
            CsvUtil.writeParticipants(csvWriter, model.getParticipantList());
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, ioe.toString()));
        }
    }

}
