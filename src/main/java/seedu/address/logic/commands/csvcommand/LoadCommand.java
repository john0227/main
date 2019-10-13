package seedu.address.logic.commands.csvcommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.util.AppUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * Supports bulk registration via a CSV file.
 * This command aims to facilitate registration of entities onto Alfred.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads data in CSV file into Alfred"
            + " Parameters "
            + "CSV_FILE_NAME\n"
            + "Example..."; // TODO: instantiate and show header order (see CsvUtil fields)
    public static final String MESSAGE_SUCCESS = "Successfully loaded CSV file into Alfred";
    public static final String MESSAGE_PARTIAL_SUCCESS = "Following line(s) were unable to be loaded into Alfred\n"
             + "Possible reasons include incorrect formatting or adding of duplicate Entity:";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found at %s"; // %s -> this.csvFileName
    public static final String MESSAGE_IO_EXCEPTION = "An IOException was caught: %s"; // %s -> exception message
    public static final String MESSAGE_INVALID_DATA = "CSV file contains invalid data";
    public static final String CAUSE_INVALID_DATA = "Invalid data format";
    public static final String CAUSE_DUPLICATE_ENTITY = "This entity already exists in Alfred";
    public static final String ASSERTION_FAILED_NOT_CSV = "File given is not a CSV file.";
    private String csvFileName;

    public LoadCommand(String csvFileName) {
        // Should Command or Parser check if file extension is valid (i.e. is a CSV file)?
        /*
         * if (!csvFileName.toLowerCase().endsWith(".csv")) {
         *     throw exception
         * }
         */
        assert csvFileName.toLowerCase().endsWith(".csv") : ASSERTION_FAILED_NOT_CSV;
        this.csvFileName = csvFileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Details must not be empty (except for ID)
        BufferedReader csvReader;
        try {
            File csvFile = new File(this.csvFileName);
            if (!FileUtil.isFileExists(csvFile.toPath())) {
                throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, this.csvFileName));
            }
            csvReader = new BufferedReader(new FileReader(csvFile));
            this.parseFile(csvReader, model);
            csvReader.close();
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, ioe.toString()));
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Parses a CSV file located at given {@link #csvFileName} to {@code Entity} objects.
     *
     * @param csvReader Reader to read the CSV file.
     * @param model {@code Model} to add the {@code Entity} objects.
     * @return A {@code CommandResult} indicating success if everything went well.
     * @throws CommandException If all or some lines in the CSV file failed to be parsed into an {@code Entity}.
     *                          Exception message contains the line number (of CSV file)
     *                          and the content of the line that failed to be parsed.
     */
    private void parseFile(BufferedReader csvReader, Model model) throws CommandException {
        List<ErrorTracker> errors = new ArrayList<>();
        int lineNumber = 1;
        String line;
        try {
            while ((line = csvReader.readLine()) != null) {
                if (line.equals(CsvUtil.HEADER_MENTOR)
                        || line.equals(CsvUtil.HEADER_PARTICIPANT)
                        || line.equals(CsvUtil.HEADER_TEAM)) {
                    continue;
                }
                String[] data = line.split(",");
                try {
                    this.addEntity(data, model);
                } catch (IllegalArgumentException | CommandException e) {
                    errors.add(new ErrorTracker(lineNumber, line, CAUSE_INVALID_DATA));
                } catch (AlfredException e) {
                    errors.add(new ErrorTracker(lineNumber, line, CAUSE_DUPLICATE_ENTITY));
                }
                lineNumber++;
            }
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, ioe.toString()));
        }
        if (!errors.isEmpty()) {
            String message = errors.stream().map(ErrorTracker::toString).collect(Collectors.joining("\n"));
            throw new CommandException(String.join("\n", MESSAGE_PARTIAL_SUCCESS, message));
        }
    }

    /**
     * Adds an {@code Entity} corresponding to given {@code data}.
     *
     * @param data Array containing {@code Entity} attribute data as {@code String}s.
     * @param model {@code Model} to add {@code Entity} to.
     * @throws AlfredException If the parsed Entity is already contained in {@code Model} (i.e. duplicate Entity).
     * @throws IllegalArgumentException If any field does not pass {@link AppUtil#checkArgument(Boolean, String)}
     *                                  or if enum constant is invalid
     */
    private void addEntity(String[] data, Model model) throws AlfredException {
        // can throw IllegalArgumentException, CommandException
        switch (data[0]) {
        case "M":
            Mentor mentor = CsvUtil.parseToMentor(data);
            model.addMentor(mentor);
            break;
        case "P":
            Participant participant = CsvUtil.parseToParticipant(data);
            model.addParticipant(participant);
            break;
        case "T":
            Team team = CsvUtil.parseToTeam(data);
            model.addTeam(team);
            break;
        default:
            // If Entity Type is incorrect
            throw new CommandException(MESSAGE_INVALID_DATA);
        }
    }

    /**
     * A tracker to show user which line in the CSV file was not able to be loaded.
     */
    static class ErrorTracker {

        private int lineNumber;
        private String csvLine;
        private String cause;

        ErrorTracker(int lineNumber, String csvLine, String cause) {
            this.lineNumber = lineNumber;
            this.csvLine = csvLine;
            this.cause = cause;
        }

        @Override
        public String toString() {
            return String.format("    Line %d: %s (Cause: %s)", this.lineNumber, this.csvLine, this.cause);
        }

    }

}
