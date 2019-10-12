package seedu.address.logic.commands.csvcommand.csvutil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import seedu.address.commons.util.AppUtil;
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
    public static Participant parseToParticipant(String[] data) {
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
    public static Team parseToTeam(String[] data) {
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

// =================================== Writer Methods ================================================

    public static void writeMentors(BufferedWriter csvWriter, ReadOnlyEntityList mentorList) throws IOException {
        csvWriter.write(HEADER_MENTOR);
        for (Entity e : mentorList.list()) {
            String mentorToCsvString = toCsvString((Mentor) e);
            csvWriter.write(mentorToCsvString + "\n");
        }
    }

    private static String toCsvString(Mentor mentor) {
        return new StringBuilder("M,")
                .append(mentor.getId().toString()).append(",")
                .append(mentor.getName().toString()).append(",")
                .append(mentor.getPhone().toString()).append(",")
                .append(mentor.getEmail().toString()).append(",")
                .append(mentor.getOrganization().toString()).append(",")
                .append(mentor.getSubject().toString())
                .toString();
    }

    public static void writeParticipants(BufferedWriter csvWriter, ReadOnlyEntityList participantList)
            throws IOException {
        csvWriter.write(HEADER_PARTICIPANT);
        for (Entity e : participantList.list()) {
            String participantToCsvString = toCsvString((Participant) e);
            csvWriter.write(participantToCsvString + "\n");
        }
    }

    private static String toCsvString(Participant participant) {
        return new StringBuilder("P,")
                .append(participant.getId().toString()).append(",")
                .append(participant.getName().toString()).append(",")
                .append(participant.getPhone().toString()).append(",")
                .append(participant.getEmail().toString())
                .toString();
    }

    public static void writeTeams(BufferedWriter csvWriter, ReadOnlyEntityList teamList) throws IOException {
        csvWriter.write(HEADER_TEAM);
        for (Entity e : teamList.list()) {
            String teamToCsvString = toCsvString((Team) e);
            csvWriter.write(teamToCsvString + "\n");
        }
    }

    private static String toCsvString(Team team) {
        return new StringBuilder("T,")
                .append(team.getId().toString()).append(",")
                .append(team.getName().toString()).append(",")
                .append(team.getSubject().toString()).append(",")
                .append(team.getScore().toString()).append(",")
                .append(team.getProjectName().toString()).append(",")
                .append(team.getProjectType().toString()).append(",")
                .append(team.getLocation().toString())
                .toString();
    }

}
