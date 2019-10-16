package seedu.address.logic.commands.csvcommand;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports every {@code Participant} stored inside {@code Alfred} into an external CSV file.
 */
public class ExportParticipantCommand extends ExportCommand {

    public static final String MESSAGE_SUCCESS = "Exported all participants to %s"; // %s -> file name

    public ExportParticipantCommand(String csvFilePath, String csvFileName) {
        super(csvFilePath, csvFileName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.getParticipantList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_DATA);
        }
        try {
            File csvFile = new File(this.csvFileName);
            FileUtil.createIfMissing(csvFile.toPath());
            CsvUtil.writeToCsv(csvFile, model.getParticipantList());
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, ioe.toString()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.csvFileName));
    }

}
