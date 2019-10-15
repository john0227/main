package seedu.address.logic.commands.csvcommand.csvutil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import seedu.address.commons.util.AppUtil;
import seedu.address.model.Model;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Entity;
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
import seedu.address.model.entitylist.ReadOnlyEntityList;
import seedu.address.model.entitylist.TeamList;

public class CsvUtil {

    public static final String HEADER_MENTOR = "EntityType,ID,Name,Phone,Email,Organization,SubjectName";
    public static final String HEADER_PARTICIPANT = "EntityType,ID,Name,Phone,Email";
    public static final String HEADER_TEAM = "EntityType,ID,Name,SubjectName,Score,ProjectName,ProjectType,Location";

    public static final String ASSERTION_FAILED_NOT_CSV = "File given is not a CSV file.";
    public static final String MESSAGE_INVALID_LIST = "List given is invalid";
    public static final String MESSAGE_INVALID_ENTITY = "Entity given is invalid";

// =================================== Parser Methods ================================================

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
    public static Mentor parseToMentor(String[] data) {
        // EntityType (M), ID (may be blank), Name, Phone, Email, Organization, SubjectName
        Id mentorId;
        try {
            mentorId = new Id(PrefixType.M, Integer.parseInt(data[1]));
        } catch (NumberFormatException e) {
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
    public static Participant parseToParticipant(String[] data) {
        // EntityType (P), ID, Name, Phone, Email
        Id participantId;
        try {
            participantId = new Id(PrefixType.P, Integer.parseInt(data[1]));
        } catch (NumberFormatException e) {
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
    public static Team parseToTeam(String[] data) {
        // EntityType (T), ID, Name, SubjectName, Score, ProjectName, ProjectType, Location
        //    cannot bulk register list of participants/mentor to Team (-> accomplish via AddToTeam)
        Id teamId;
        try {
            teamId = new Id(PrefixType.T, Integer.parseInt(data[1]));
        } catch (NumberFormatException e) {
            teamId = TeamList.generateId();
        }
        Name teamName = new Name(data[2]);
        SubjectName teamSubject = SubjectName.valueOf(data[3].toUpperCase());
        Score teamScore = new Score(Integer.parseInt(data[4])); // NFException subclass of IAException
        Name teamProjectName = new Name(data[5]);
        ProjectType teamProjectType = ProjectType.valueOf(data[6].toUpperCase());
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

// =================================== Writer Methods ================================================

    /**
     * Converts each {@code Entity} in the given {@code Model}
     * into a {@code CSV String} and writes it into a CSV file provided.
     *
     * @param csvFile CSV file to write each {@code Entity} to.
     * @param model {@code Model} which contains {@code Entity} data.
     * @throws IOException If something goes wrong while writing to the {@code csvFile}.
     */
    public static void writeToCsv(File csvFile, Model model) throws IOException {
        assert csvFile.toString().toLowerCase().endsWith(".csv") : ASSERTION_FAILED_NOT_CSV;
        writeToCsv(csvFile, model.getMentorList(), false);
        writeToCsv(csvFile, model.getParticipantList(), true);
        writeToCsv(csvFile, model.getTeamList(), true);
    }

    public static void writeToCsv(File csvFile, ReadOnlyEntityList entityList) throws IOException {
        writeToCsv(csvFile, entityList, false);
    }

    public static void writeToCsv(File csvFile,
            ReadOnlyEntityList entityList, boolean shouldAppend) throws IOException {
        assert csvFile.toString().toLowerCase().endsWith(".csv") : ASSERTION_FAILED_NOT_CSV;
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvFile, shouldAppend));
        csvWriter.write(getHeader(entityList) + "\n");
        for (Entity e : entityList.list()) {
            String entityToCsvString = toCsvString(e);
            csvWriter.write(entityToCsvString + "\n");
        }
    }

    private static String getHeader(ReadOnlyEntityList entityList) {
        if (entityList instanceof ParticipantList) {
            return HEADER_PARTICIPANT;
        } else if (entityList instanceof MentorList) {
            return HEADER_MENTOR;
        } else if (entityList instanceof TeamList) {
            return HEADER_TEAM;
        } else {
            // Should never reach here
            throw new RuntimeException(MESSAGE_INVALID_LIST);
        }
    }

    private static String toCsvString(Entity entity) {
        if (entity instanceof Mentor) {
            return toCsvString((Mentor) entity);
        } else if (entity instanceof Participant) {
            return toCsvString((Participant) entity);
        } else if (entity instanceof Team) {
            return toCsvString((Team) entity);
        } else {
            // Should never reach here
            throw new RuntimeException(MESSAGE_INVALID_ENTITY);
        }
    }

    private static String toCsvString(Mentor mentor) {
        return new StringBuilder("M,")
                .append(mentor.getId().getNumber()).append(",")
                .append(mentor.getName().toStorageValue()).append(",")
                .append(mentor.getPhone().toStorageValue()).append(",")
                .append(mentor.getEmail().toStorageValue()).append(",")
                .append(mentor.getOrganization().toStorageValue()).append(",")
                .append(mentor.getSubject().toStorageValue())
                .toString();
    }

    private static String toCsvString(Participant participant) {
        return new StringBuilder("P,")
                .append(participant.getId().getNumber()).append(",")
                .append(participant.getName().toStorageValue()).append(",")
                .append(participant.getPhone().toStorageValue()).append(",")
                .append(participant.getEmail().toStorageValue())
                .toString();
    }

    private static String toCsvString(Team team) {
        return new StringBuilder("T,")
                .append(team.getId().getNumber()).append(",")
                .append(team.getName().toStorageValue()).append(",")
                .append(team.getSubject().toStorageValue()).append(",")
                .append(team.getScore().toStorageValue()).append(",")
                .append(team.getProjectName().toStorageValue()).append(",")
                .append(team.getProjectType().toStorageValue()).append(",")
                .append(team.getLocation().toStorageValue())
                .toString();
    }

}
