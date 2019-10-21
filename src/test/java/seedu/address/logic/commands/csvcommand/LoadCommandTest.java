package seedu.address.logic.commands.csvcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.csvcommand.csvutil.ErrorTracker.Error;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalMentors;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;

public class LoadCommandTest {

    private final File entityCsv = TestUtil.getFilePathInCsvUtilTestFolder("LoadEntities.csv").toFile();
    private Model model = new ModelManagerStub();

    @Test
    public void execute_validFilePassedIn_success() throws AlfredException {
        // Initialize fields
        MentorList mentorList = new MentorList();
        mentorList.add(TypicalMentors.A);
        mentorList.add(TypicalMentors.B);
        mentorList.add(TypicalMentors.C);
        ParticipantList participantList = new ParticipantList();
        participantList.add(TypicalParticipants.A);
        participantList.add(TypicalParticipants.B);
        participantList.add(TypicalParticipants.C);
        TeamList teamList = new TeamList();
        teamList.add(TypicalTeams.A);
        teamList.add(TypicalTeams.B);
        teamList.add(TypicalTeams.C);

        new LoadCommand(entityCsv.getAbsolutePath()).execute(model);
        assertEquals(mentorList, model.getMentorList());
        assertEquals(participantList, model.getParticipantList());
        assertEquals(teamList, model.getTeamList());
    }

    @Test
    public void execute_nonCsvFilePassedIn_assertionErrorThrown() {
        File tempFile = TestUtil.getFilePathInSandboxFolder("temp.txt").toFile();
        tempFile.deleteOnExit();
        assertThrows(AssertionError.class, () -> new LoadCommand(tempFile.getAbsolutePath()),
                LoadCommand.ASSERTION_FAILED_NOT_CSV);
    }

    @Test
    public void execute_nonexistentFilePassedIn_commandExceptionThrown() {
        File tempFile = TestUtil.getFilePathInSandboxFolder("nonexistent.csv").toFile();
        tempFile.deleteOnExit();
        assertThrows(CommandException.class, () -> new LoadCommand(tempFile.getAbsolutePath()).execute(model),
                LoadCommand.MESSAGE_FILE_NOT_FOUND);
    }

    @Test
    public void execute_invalidFormattingOfFile_commandExceptionThrownWithErrorTrackerMessage() {
        File tempFile = TestUtil.getFilePathInCsvUtilTestFolder("InvalidFormat.csv").toFile();
        String expected = String.join(
                "\n",
                LoadCommand.MESSAGE_PARTIAL_SUCCESS,
                new Error(2, "M,,Alfred^^,+65 12345678,,org,Health", LoadCommand.CAUSE_INVALID_DATA).toString(),
                new Error(3, "P,,,,,,,,,,", LoadCommand.CAUSE_INVALID_DATA).toString(),
                new Error(4, "T,,", LoadCommand.CAUSE_INVALID_DATA).toString(),
                new Error(6, "M,,Alfred,+65 1234,A@mail.com,o1,Health", LoadCommand.CAUSE_DUPLICATE_ENTITY).toString(),
                new Error(7, "IN,,,,,,", LoadCommand.CAUSE_INVALID_DATA).toString(),
                LoadCommand.MESSAGE_INVALID_FORMAT
        );
        assertThrows(CommandException.class, () -> new LoadCommand(tempFile.getAbsolutePath()).execute(model),
                expected);
    }

}
