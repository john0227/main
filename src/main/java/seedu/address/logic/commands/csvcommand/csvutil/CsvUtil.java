package seedu.address.logic.commands.csvcommand.csvutil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.AppUtil;
import seedu.address.commons.util.PrefixUtil;
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

/**
 * Helper functions to facilitate interactions between {@code Alfred} and a CSV file.
 * Converts {@code Entity} objects to a {@code CSV String} and parses a {@code CSV String}
 * into an {@code Entity} object.
 */
public class CsvUtil {

    /*
     * Headers to show user which column corresponds to which value.
     */
    public static final String HEADER_MENTOR = "EntityType,ID,Name,Phone,Email,Organization,SubjectName";
    public static final String HEADER_PARTICIPANT = "EntityType,ID,Name,Phone,Email";
    public static final String HEADER_TEAM =
            "EntityType,ID,Name,Participants,Mentor,SubjectName,Score,ProjectName,ProjectType,Location";

    public static final String ASSERTION_FAILED_NOT_CSV = "File given is not a CSV file.";
    public static final String MESSAGE_INVALID_ENTITY = "Entity given is invalid";
    public static final String CSV_SEPARATOR = ",";
    public static final String CSV_SEPARATOR_REGEX = "\\s*,\\s*"; // comma surrounded by arbitrary number of spaces

    // =================================== Parser Methods ================================================

    /**
     * Parses given line of data (split by commas) into relevant fields of a {@code Mentor}.
     * <b>Precondition: </b> {@code data} contains attribute data as {@code String}s in the order of
     * {@code EntityType(T), ID, Name, Phone, Email, Organization, SubjectName}. {@code ID} may be left
     * empty, in which Alfred will generate a valid ID for the mentor.
     *
     * @param data Array containing {@code Mentor} attribute data as {@code String}s.
     * @return A valid {@code Mentor} with attributes set corresponding to {@code data}.
     * @throws IllegalArgumentException If any field does not pass {@link AppUtil#checkArgument(Boolean, String)}
     *                                  or if enum constant is invalid
     */
    public static Mentor parseToMentor(String[] data) {
        // EntityType (M), ID (may be blank), Name, Phone, Email, Organization, SubjectName
        if (data.length != 7) {
            throw new IllegalArgumentException();
        }
        if (!data[0].toUpperCase().equals("M")) {
            throw new IllegalArgumentException();
        }
        Id mentorId = retrieveId(data[1], PrefixType.M);
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
     * {@code EntityType(T), ID, Name, Phone, Email}. {@code ID} may be left empty, in which
     * Alfred will generate a valid ID for the participant.
     *
     * @param data Array containing {@code Participant} attribute data as {@code String}s.
     * @return A valid {@code Participant} with attributes set corresponding to {@code data}.
     * @throws IllegalArgumentException If any field does not pass {@link AppUtil#checkArgument(Boolean, String)}
     *                                  or if enum constant is invalid
     */
    public static Participant parseToParticipant(String[] data) {
        // EntityType (P), ID, Name, Phone, Email
        if (data.length != 5) {
            throw new IllegalArgumentException();
        }
        if (!data[0].toUpperCase().equals("P")) {
            throw new IllegalArgumentException();
        }
        Id participantId = retrieveId(data[1], PrefixType.P);
        Name participantName = new Name(data[2]);
        Phone participantPhone = new Phone(data[3]);
        Email participantEmail = new Email(data[4]);
        return new Participant(participantName, participantId, participantEmail, participantPhone);
    }

    /**
     * Parses given line of data (split by commas) into relevant fields of a {@code Team}.
     * <b>Precondition: </b> {@code data} contains attribute data as {@code String}s in the order of
     * {@code EntityType(T), ID, Name, Participants, Mentor, SubjectName, Score, ProjectName, ProjectType, Location}.
     * {@code ID, Participants} and {@code Mentor} may be left empty. If {@code ID} is left empty, Alfred will
     * generate a valid ID for the team. If {@code Participants} and {@code Mentor} are left empty, the team
     * will not have connections to any {@code Participants} and {@code Mentor}.
     *
     * @param data Array containing {@code Team} attribute data as {@code String}s.
     * @param model {@code Model} to operate on.
     * @return A valid {@code Team} with attributes set corresponding to {@code data}.
     * @throws IllegalArgumentException If any field does not pass {@link AppUtil#checkArgument(Boolean, String)}
     *                                  or if enum constant is invalid
     */
    public static Team parseToTeam(String[] data, Model model) {
        // EntityType (T), ID, Name, Participants, Mentor, SubjectName, Score, ProjectName, ProjectType, Location
        //    cannot bulk register list of participants/mentor to Team (-> accomplish via AddToTeam)
        if (data.length != 10) {
            throw new IllegalArgumentException();
        }
        if (!data[0].toUpperCase().equals("T")) {
            throw new IllegalArgumentException();
        }
        Id teamId = retrieveId(data[1], PrefixType.T);
        Name teamName = new Name(data[2]);
        List<Participant> participants = parseToParticipants(data[3], model);
        Optional<Mentor> mentor = parseToMentor(data[4], model);
        SubjectName teamSubject = SubjectName.valueOf(data[5].toUpperCase());
        Score teamScore = new Score(Integer.parseInt(data[6])); // NFException subclass of IAException
        Name teamProjectName = new Name(data[7]);
        ProjectType teamProjectType = ProjectType.valueOf(data[8].toUpperCase());
        Location teamLocation = new Location(Integer.parseInt(data[9])); // NFException subclass of IAException
        return new Team(
                teamId,
                teamName,
                participants,
                mentor,
                teamSubject,
                teamScore,
                teamProjectName,
                teamProjectType,
                teamLocation
        );
    }

    private static List<Participant> parseToParticipants(String data, Model model) {
        List<Participant> participants = new ArrayList<>();
        if (data.isBlank()) {
            return participants;
        }
        data = data.replace("[", "").replace("]", "").trim();
        for (String strId : data.split("\\s*\\|\\s*")) {
            try {
                if (!Id.isValidString(strId)) {
                    strId = "P-" + strId;
                }
                Id id = Id.toId(strId);
                participants.add(model.getParticipant(id));
            } catch (AlfredException e) {
                throw new IllegalArgumentException();
            }
        }
        return participants;
    }

    private static Optional<Mentor> parseToMentor(String data, Model model) {
        if (data.isBlank()) {
            return Optional.empty();
        }
        data = data.replace("[", "").replace("]", "").trim();
        try {
            if (!Id.isValidString(data)) {
                data = "M-" + data;
            }
            Id id = Id.toId(data);
            return Optional.of(model.getMentor(id));
        } catch (AlfredException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Retrieves the {@code Id} from give {@code strId}.
     * If {@code strId} is invalid, generate a valid {@code Id} from respective {@code EntityList}.
     *
     * @param strId {@code String Id} to parse into {@code Id}.
     * @param prefixType {@code PrefixType} to indicate the {@code Entity} type of the {@code Id} to be generated.
     * @return Generated {@code Id}.
     */
    private static Id retrieveId(String strId, PrefixType prefixType) {
        // A valid Id can be just a number (i.e. 1, 2, 3) or a String form of an Id (i.e. M-1, P-1, T-1)
        Id entityId;
        try {
            if (!Id.isValidString(strId)) {
                strId = prefixType.toString() + "-" + strId;
            }
            entityId = Id.toId(strId);
        } catch (IllegalValueException ive) {
            switch (prefixType) {
            case M:
                entityId = MentorList.generateId();
                break;
            case P:
                entityId = ParticipantList.generateId();
                break;
            case T:
                entityId = TeamList.generateId();
                break;
            default:
                // Should never reach here
                throw new RuntimeException();
            }
        }
        return entityId;
    }

    // =================================== Writer Methods ================================================

    /**
     * Converts each {@code Entity} in the given {@code model}
     * into a {@code CSV String} and writes it into the CSV file provided.
     *
     * @param csvFile CSV file to write each {@code Entity} to.
     * @param model {@code Model} containing {@code Entity} data to export.
     * @throws IOException If something goes wrong while writing to the {@code csvFile}.
     */
    public static void writeToCsv(File csvFile, Model model) throws IOException {
        writeToCsv(csvFile, model.getMentorList(), false);
        writeToCsv(csvFile, model.getParticipantList(), true);
        writeToCsv(csvFile, model.getTeamList(), true);
    }

    /**
     * Converts each {@code Entity} in the given {@code entityList}
     * into a {@code CSV String} and writes it into the CSV file provided.
     *
     * @param csvFile CSV file to write each {@code Entity} to.
     * @param entityList A {@code ReadOnlyEntityList} containing {@code Entity} data to export.
     * @throws IOException If something goes wrong while writing to the {@code csvFile}.
     */
    public static void writeToCsv(File csvFile, ReadOnlyEntityList entityList) throws IOException {
        writeToCsv(csvFile, entityList, false);
    }

    /**
     * Converts each {@code Entity} in the given {@code entityList}
     * into a {@code CSV String} and writes it into the CSV file provided.
     *
     * @param csvFile CSV file to write each {@code Entity} to.
     * @param entityList A {@code ReadOnlyEntityList} containing {@code Entity} data to export.
     * @param shouldAppend A {@code boolean} value indicating whether to append to the given file or not.
     * @throws IOException If something goes wrong while writing to the {@code csvFile}.
     */
    public static void writeToCsv(File csvFile,
            ReadOnlyEntityList entityList, boolean shouldAppend) throws IOException {
        assert csvFile.toString().toLowerCase().endsWith(".csv") : ASSERTION_FAILED_NOT_CSV;
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvFile, shouldAppend));
        csvWriter.write(getHeader(PrefixUtil.getPrefixOf(entityList)) + "\n");
        for (Entity e : entityList.list()) {
            String entityToCsvString = toCsvString(e);
            csvWriter.write(entityToCsvString + "\n");
        }
        csvWriter.close();
    }

    private static String getHeader(PrefixType prefixType) {
        switch (prefixType) {
        case M:
            return HEADER_MENTOR;
        case P:
            return HEADER_PARTICIPANT;
        case T:
            return HEADER_TEAM;
        default:
            // Should never reach here
            throw new RuntimeException(MESSAGE_INVALID_ENTITY);
        }
    }

    /**
     * Converts the given {@code entity} into a {CSV String}.
     *
     * @param entity {@code Entity} to convert.
     * @return A {@code CSV String} corresponding to {@code entity}.
     */
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

    /**
     * Converts the given {@code mentor} into a {@code CSV String}.
     *
     * @param mentor {@code Mentor} to convert.
     * @return A {@code CSV String} corresponding to {@code mentor}.
     */
    private static String toCsvString(Mentor mentor) {
        return new StringBuilder("M").append(CSV_SEPARATOR)
                .append(mentor.getId().getNumber()).append(CSV_SEPARATOR)
                .append(mentor.getName().toStorageValue()).append(CSV_SEPARATOR)
                .append(mentor.getPhone().toStorageValue()).append(CSV_SEPARATOR)
                .append(mentor.getEmail().toStorageValue()).append(CSV_SEPARATOR)
                .append(mentor.getOrganization().toStorageValue()).append(CSV_SEPARATOR)
                .append(mentor.getSubject().toStorageValue())
                .toString();
    }

    /**
     * Converts the given {@code participant} into a {@code CSV String}.
     *
     * @param participant {@code Participant} to convert.
     * @return A {@code CSV String} corresponding to {@code participant}.
     */
    private static String toCsvString(Participant participant) {
        return new StringBuilder("P").append(CSV_SEPARATOR)
                .append(participant.getId().getNumber()).append(CSV_SEPARATOR)
                .append(participant.getName().toStorageValue()).append(CSV_SEPARATOR)
                .append(participant.getPhone().toStorageValue()).append(CSV_SEPARATOR)
                .append(participant.getEmail().toStorageValue())
                .toString();
    }

    /**
     * Converts the given {@code team} into a {@code CSV String}.
     *
     * @param team {@code Team} to convert.
     * @return A {@code CSV String} corresponding to {@code team}.
     */
    private static String toCsvString(Team team) {
        return new StringBuilder("T").append(CSV_SEPARATOR)
                .append(team.getId().getNumber()).append(CSV_SEPARATOR)
                .append(team.getName().toStorageValue()).append(CSV_SEPARATOR)
                .append(toCsvString(team.getParticipants())).append(CSV_SEPARATOR)
                .append(toCsvString(team.getMentor())).append(CSV_SEPARATOR)
                .append(team.getSubject().toStorageValue()).append(CSV_SEPARATOR)
                .append(team.getScore().toStorageValue()).append(CSV_SEPARATOR)
                .append(team.getProjectName().toStorageValue()).append(CSV_SEPARATOR)
                .append(team.getProjectType().toStorageValue()).append(CSV_SEPARATOR)
                .append(team.getLocation().toStorageValue())
                .toString();
    }

    private static String toCsvString(List<Participant> participants) {
        return "[" + participants.stream().map(p -> p.getId().toString()).collect(Collectors.joining("|")) + "]";
    }

    private static String toCsvString(Optional<Mentor> mentor) {
        if (mentor.isEmpty()) {
            return "";
        }
        return mentor.get().getId().toString();
    }

}
