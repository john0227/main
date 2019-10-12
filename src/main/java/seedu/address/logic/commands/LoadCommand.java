package seedu.address.logic.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.util.AppUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.ProjectType;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;

/**
 * Supports bulk registration via a CSV file.
 * This command aims to facilitate registration of entities onto Alfred.
 */
public class LoadCommand extends Command {

    private String csvFileName;
    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads data in CSV file into Alfred"
            + " Parameters "
            + "CSV_FILE_NAME\n"
            + "Example..."; // TODO : instantiate
    public static final String MESSAGE_SUCCESS = "Successfully loaded CSV file into Alfred";
    public static final String MESSAGE_PARTIAL_SUCCESS = "Following line(s) were unable to be loaded into Alfred\n"
             + "Possible reasons include incorrect formatting or adding of duplicate Entity:";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found at %s"; // %s -> this.csvFileName
    public static final String MESSAGE_IO_EXCEPTION = "An IOException was caught: %s"; // %s -> exception message
    public static final String MESSAGE_INVALID_DATA = "CSV file contains invalid data";

    public LoadCommand(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Details must not be empty (except for ID)
        BufferedReader csvReader;
        try {
            File csvFile = new File(this.csvFileName);
            csvReader = new BufferedReader(new FileReader(this.csvFileName));
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, this.csvFileName));
        }
        return parseFile(csvReader, model);
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
    private CommandResult parseFile(BufferedReader csvReader, Model model) throws CommandException {
        List<ErrorTracker> errors = new ArrayList<>();
        int lineNumber = 1;
        String line;
        try {
            while ((line = csvReader.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    addEntity(data, model);
                } catch (IllegalArgumentException | AlfredException e) {
                    // If something went wrong while parsing the data of an Entity
                    // Or if there exists duplicate Entity while adding
                    errors.add(new ErrorTracker(lineNumber, line));
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
        return new CommandResult(MESSAGE_SUCCESS);
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
            Mentor mentor = parseToMentor(data);
            model.addMentor(mentor);
            break;
        case "P":
            Participant participant = parseToParticipant(data);
            model.addParticipant(participant);
            break;
        case "T":
            Team team = parseToTeam(data);
            model.addTeam(team);
            break;
        default:
            // If Entity Type is incorrect
            throw new CommandException(MESSAGE_INVALID_DATA);
        }
    }

    /**
     * Parses given line of data (split by commas) into relevant fields of a {@code Mentor}.
     * <b>Precondition: </b> {@code data} contains attribute data as {@code String}s in the order of
     * {@code EntityType(T), ID, Name, Phone, Email, Organization, SubjectName}.
     *
     * @param data Array containing {@code Mentor} attribute data as {@code String}s.
     * @return A valid {@code Mentor} with attributes set corresponding to {@code data}.
     * @throws IllegalArgumentException If any field does not pass {@link AppUtil#checkArgument(Boolean, String)}
     *                                  or if enum constant is invalid
     */
    private Mentor parseToMentor(String[] data) {
        // EntityType (M), ID (may be blank), Name, Phone, Email, Organization, SubjectName
        Id mentorId;
        try {
            mentorId = new Id(PrefixType.M, Integer.parseInt(data[1]));
        } catch (NumberFormatException nfe) {
            mentorId = MentorList.generateId();
        }
        Name mentorName = new Name(data[2]);
        Phone mentorPhone = new Phone(data[3]);
        Email mentorEmail = new Email(data[4]);
        Name mentorOrganization = new Name(data[5]);
        SubjectName mentorSubject = SubjectName.valueOf(data[6].toUpperCase());
        return new Mentor(mentorName, mentorId, mentorPhone, mentorEmail, mentorOrganization, mentorSubject);
    }

    /**
     * Parses given line of data (split by commas) into relevant fields of a {@code Participant}.
     * <b>Precondition: </b> {@code data} contains attribute data as {@code String}s in the order of
     * {@code EntityType(T), ID, Name, Phone, Email}.
     *
     * @param data Array containing {@code Participant} attribute data as {@code String}s.
     * @return A valid {@code Participant} with attributes set corresponding to {@code data}.
     * @throws IllegalArgumentException If any field does not pass {@link AppUtil#checkArgument(Boolean, String)}
     *                                  or if enum constant is invalid
     */
    private Participant parseToParticipant(String[] data) {
        // EntityType (P), ID, Name, Phone, Email
        Id participantId;
        try {
            participantId = new Id(PrefixType.P, Integer.parseInt(data[1]));
        } catch (NumberFormatException nfe) {
            participantId = ParticipantList.generateId();
        }
        Name participantName = new Name(data[2]);
        Phone participantPhone = new Phone(data[3]);
        Email participantEmail = new Email(data[4]);
        return new Participant(participantName, participantId, participantEmail, participantPhone);
    }

    /**
     * Parses given line of data (split by commas) into relevant fields of a {@code Team}.
     * <b>Precondition: </b> {@code data} contains attribute data as {@code String}s in the order of
     * {@code EntityType(T), ID, Name, SubjectName, Score, ProjectName, ProjectType, Location}.
     * {@code Participant}s and {@code Mentor} cannot be added to {@code Team} via CSV file and should
     * be added via {@code AddCommand}.
     *
     * @param data Array containing {@code Team} attribute data as {@code String}s.
     * @return A valid {@code Team} with attributes set corresponding to {@code data}.
     * @throws IllegalArgumentException If any field does not pass {@link AppUtil#checkArgument(Boolean, String)}
     *                                  or if enum constant is invalid
     */
    private Team parseToTeam(String[] data) {
        // EntityType (T), ID, Name, SubjectName, Score, ProjectName, ProjectType, Location
        //    cannot bulk register list of participants/mentor to Team (-> accomplish via AddToTeam)
        Id teamId;
        try {
            teamId = new Id(PrefixType.T, Integer.parseInt(data[1]));
        } catch (NumberFormatException nfe) {
            teamId = TeamList.generateId();
        }
        Name teamName = new Name(data[2]);
        SubjectName teamSubject = SubjectName.valueOf(data[3].toUpperCase());
        Score teamScore = new Score(Integer.parseInt(data[4])); // NFException subclass of IAException
        Name teamProjectName = new Name(data[5]);
        ProjectType teamProjectType = ProjectType.valueOf(data[6]);
        Location teamLocation = new Location(Integer.parseInt(data[7])); // NFException subclass of IAException
        return new Team(
                teamId,
                teamName,
                new ArrayList<>(), // should I pass in null?
                Optional.empty(),  // should I pass in null?
                teamSubject,
                teamScore,
                teamProjectName,
                teamProjectType,
                teamLocation
        );
    }

    /**
     * A tracker to show user which line in the CSV file was not able to be loaded.
     */
    static class ErrorTracker {

        private int lineNumber;
        private String csvLine;

        ErrorTracker(int lineNumber, String csvLine) {
            this.lineNumber = lineNumber;
            this.csvLine = csvLine;
        }

        @Override
        public String toString() {
            return String.format("    Line %d: %s", this.lineNumber, this.csvLine);
        }

    }

}
