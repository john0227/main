package seedu.address.logic.commands.csvcommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_NAME;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.csvcommand.csvutil.ErrorTracker;
import seedu.address.logic.commands.csvcommand.csvutil.ErrorTracker.Error;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * Supports bulk registration via a CSV file.
 * This command aims to facilitate registration of entities onto Alfred.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_SUCCESS = "Successfully loaded CSV file into Alfred";
    public static final String MESSAGE_PARTIAL_SUCCESS = "Following line(s) were unable to be loaded into Alfred\n"
             + "Possible reasons include incorrect formatting or adding of duplicate Entity:";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found at %s"; // %s -> this.csvFileName
    public static final String MESSAGE_IO_EXCEPTION = "An IOException was caught: %s"; // %s -> exception message
    public static final String MESSAGE_INVALID_DATA = "CSV file contains invalid data";
    public static final String MESSAGE_INVALID_FORMAT = "CSV file must contain Entity data in the following format:\n"
            + "\tMentors: " + CsvUtil.HEADER_MENTOR + "\n"
            + "\tParticipants: " + CsvUtil.HEADER_PARTICIPANT + "\n"
            + "\tTeams: " + CsvUtil.HEADER_TEAM;
    public static final String CAUSE_INVALID_DATA = "Invalid data format";
    public static final String CAUSE_DUPLICATE_ENTITY = "This entity already exists in Alfred";
    public static final String ASSERTION_FAILED_NOT_CSV = "File given is not a CSV file.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads data in CSV file into Alfred"
            + " Parameters: "
            + PREFIX_FILE_NAME + "CSV_FILE_NAME\n"
            + "\tExample (Windows): " + COMMAND_WORD
            + " " + PREFIX_FILE_NAME + "C:/Users/USER/AlfredData/Alfred.csv\n"
            + "Note the path does not have to be absolute (i.e. can be relative). "
            + "Hence, if a drive is not specified, Alfred will search for the file in current working directory.";

    private String csvFileName;

    public LoadCommand(String csvFileName) {
        assert csvFileName.toLowerCase().endsWith(".csv") : ASSERTION_FAILED_NOT_CSV;
        this.csvFileName = csvFileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Details must not be empty (except for ID)
        File csvFile = new File(this.csvFileName);
        if (!FileUtil.isFileExists(csvFile.toPath())) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, this.csvFileName));
        }
        ErrorTracker errors = new ErrorTracker();
        try {
            this.parseFile(csvFile, errors, model);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IO_EXCEPTION, ioe.toString()));
        }
        if (!errors.isEmpty()) {
            String message = String.join("\n", MESSAGE_PARTIAL_SUCCESS, errors.toString(), MESSAGE_INVALID_FORMAT);
            throw new CommandException(message);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Parses a CSV file located at given {@link #csvFileName} to {@code Entity} objects.
     *
     * @param csvFile The CSV file to parse.
     * @param errors {@link ErrorTracker} to store potential errors that may arise while parsing the CSV file.
     * @param model {@code Model} to add the {@code Entity} objects.
     */
    private void parseFile(File csvFile, ErrorTracker errors, Model model) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        int lineNumber = 1;
        String line;
        while ((line = csvReader.readLine()) != null) {
            Entity entityToAdd = this.parseLineToEntity(lineNumber, line, errors);
            if (entityToAdd == null) {
                continue;
            }
            try {
                this.addEntity(entityToAdd, model);
            } catch (AlfredException e) {
                errors.add(new Error(lineNumber, line, CAUSE_DUPLICATE_ENTITY));
            }
            lineNumber++;
        }
        csvReader.close();
    }

    /**
     * Parses given line into the corresponding {@code Entity}.
     *
     * @param lineNumber Line number of given line in the CSV file.
     * @param line Line in the CSV file.
     * @param errors {@code ErrorTracker} to keep track of potential errors while parsing.
     * @return Corresponding {@code Entity}.
     */
    private Entity parseLineToEntity(int lineNumber, String line, ErrorTracker errors) {
        if (line.equals(CsvUtil.HEADER_MENTOR)
                || line.equals(CsvUtil.HEADER_PARTICIPANT)
                || line.equals(CsvUtil.HEADER_TEAM)) {
            return null;
        }
        String[] data = line.split(",");
        try {
            switch (data[0].toUpperCase()) {
            case "M":
                return CsvUtil.parseToMentor(data);
            case "P":
                return CsvUtil.parseToParticipant(data);
            case "T":
                return CsvUtil.parseToTeam(data);
            default:
                // If Entity Type is incorrect
                errors.add(new Error(lineNumber, line, CAUSE_INVALID_DATA));
            }
        } catch (IllegalArgumentException e) {
            errors.add(new Error(lineNumber, line, CAUSE_INVALID_DATA));
        }
        return null;
    }

    /**
     * Adds an {@code Entity} corresponding to given {@code data}.
     *
     * @param entityToAdd Entity to add into {@code model}.
     * @param model {@code Model} to add {@code Entity} to.
     * @throws AlfredException If the parsed Entity is already contained in {@code Model} (i.e. duplicate Entity).
     */
    private void addEntity(Entity entityToAdd, Model model) throws AlfredException {
        // instanceof takes care of null value
        if (entityToAdd instanceof Mentor) {
            model.addMentor((Mentor) entityToAdd);
        } else if (entityToAdd instanceof Participant) {
            model.addParticipant((Participant) entityToAdd);
        } else if (entityToAdd instanceof Team) {
            model.addTeam((Team) entityToAdd);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LoadCommand)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        return this.csvFileName.equalsIgnoreCase(((LoadCommand) other).csvFileName);
    }

}
