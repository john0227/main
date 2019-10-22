package seedu.address.logic.commands.csvcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.FileUtil;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalParticipants;

public class ExportParticipantCommandTest {

    /**
     * Initializes participant list.
     */
    private void initializeParticipants(Model model) throws AlfredException {
        model.addParticipant(TypicalParticipants.A);
        model.addParticipant(TypicalParticipants.B);
        model.addParticipant(TypicalParticipants.C);
    }

    @Test
    public void equals_sameCommands_returnTrue() throws CommandException {
        // Same command returns true
        ExportParticipantCommand command1 = new ExportParticipantCommand(
                "src/main/test",
                "Alfred.csv"
        );
        assertEquals(command1, command1);

        // Same parameter returns true
        ExportParticipantCommand command2 = new ExportParticipantCommand(
                "src/main/test",
                "Alfred.csv"
        );
        assertEquals(command1, command2);

        // Empty strings result in default names
        command1 = new ExportParticipantCommand("", "");
        command2 = new ExportParticipantCommand(
                ExportParticipantCommand.DEFAULT_FILE_PATH,
                ExportParticipantCommand.DEFAULT_FILE_NAME
        );
        assertEquals(command1, command2);

        // Path ending with File.separator has no difference on the outcome
        String filePath = ".";
        command1 = new ExportParticipantCommand(filePath + File.separator, "Alfred.csv");
        command2 = new ExportParticipantCommand(filePath, "Alfred.csv");
        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentCommands_returnFalse() throws CommandException {
        // Different class returns false
        ExportParticipantCommand command1 = new ExportParticipantCommand(
                "src/main/test",
                "Alfred.csv"
        );
        ExportTeamCommand exportTeamCommand = new ExportTeamCommand("src/main/test", "Alfred.csv");
        assertNotEquals(command1, exportTeamCommand);

        // Different parameters returns false
        ExportParticipantCommand command2 = new ExportParticipantCommand("src/main/test", "Alfred1.csv");
        assertNotEquals(command1, command2);
    }

    @Test
    public void constructor_nonCsvFilePassed_assertionErrorThrown() {
        String fileName = "Alfred.txt";
        assertThrows(AssertionError.class, () -> new ExportParticipantCommand("", fileName));
    }

    @Test
    public void execute_emptyModelPassed_successWithNoFileCreated() throws AlfredException {
        Model emptyModel = new ModelManagerStub();
        String filePath = TestUtil.getFilePathInCsvUtilTestFolder("").toString();
        String fileName = "Alfred.csv";
        assertEquals(
                ExportParticipantCommand.MESSAGE_EMPTY_DATA,
                new ExportParticipantCommand(filePath, fileName).execute(emptyModel).getFeedbackToUser()
        );
        assertFalse(TestUtil.getFilePathInCsvUtilTestFolder(fileName).toFile().exists());
    }

    @Test
    public void execute_validParametersPassed_success() throws AlfredException, IOException {
        Model model = new ModelManagerStub();
        initializeParticipants(model);

        File expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedParticipants.csv").toFile();
        String filePath = TestUtil.getFilePathInSandboxFolder("").toString();
        String fileName = "Alfred.csv";
        String expectedMessage = String.format(
                ExportParticipantCommand.MESSAGE_SUCCESS,
                filePath + File.separator + fileName
        );

        assertEquals(
                expectedMessage,
                new ExportParticipantCommand(filePath, fileName).execute(model).getFeedbackToUser()
        );

        File outcomeFile = TestUtil.getFilePathInSandboxFolder("Alfred.csv").toFile();
        outcomeFile.deleteOnExit();
        assertTrue(FileUtil.hasEqualContents(expectedFile, outcomeFile));
    }

}
