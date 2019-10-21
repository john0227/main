package seedu.address.logic.commands.csvcommand;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.editcommand.EditMentorCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports every {@code Mentor} stored inside {@code Alfred} into an external CSV file.
 */
public class ExportMentorCommand extends ExportCommand {

    public static final String MESSAGE_SUCCESS = "Exported all mentors to %s"; // %s -> file name

    public ExportMentorCommand(String csvFilePath, String csvFileName) {
        super(csvFilePath, csvFileName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.getMentorList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_DATA);
        }
        try {
            File csvFile = new File(this.csvFileName);
            FileUtil.createIfMissing(csvFile.toPath());
            CsvUtil.writeToCsv(csvFile, model.getMentorList());
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, ioe.toString()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.csvFileName));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ExportMentorCommand && super.equals(other);
    }

}
