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
import seedu.address.testutil.TypicalTeams;

public class ExportTeamCommandTest {

    /**
     * Initializes team list.
     */
    private void initializeTeams(Model model) throws AlfredException {
        model.addTeam(TypicalTeams.A);
        model.addTeam(TypicalTeams.B);
        model.addTeam(TypicalTeams.C);
    }

    @Test
    public void equals_sameCommands_returnTrue() throws CommandException {
        // Same command returns true
        ExportTeamCommand command1 = new ExportTeamCommand("src/main/test", "Alfred.csv");
        assertEquals(command1, command1);

        // Same parameter returns true
        ExportTeamCommand command2 = new ExportTeamCommand("src/main/test", "Alfred.csv");
        assertEquals(command1, command2);

        // Empty strings result in default names
        command1 = new ExportTeamCommand("", "");
        command2 = new ExportTeamCommand(ExportTeamCommand.DEFAULT_FILE_PATH, ExportTeamCommand.DEFAULT_FILE_NAME);
        assertEquals(command1, command2);

        // Path ending with File.separator has no difference on the outcome
        String filePath = ".";
        command1 = new ExportTeamCommand(filePath + File.separator, "Alfred.csv");
        command2 = new ExportTeamCommand(filePath, "Alfred.csv");
        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentCommands_returnFalse() throws CommandException {
        // Different class returns false
        ExportTeamCommand command1 = new ExportTeamCommand("src/main/test", "Alfred.csv");
        ExportMentorCommand exportMentorCommand = new ExportMentorCommand("src/main/test", "Alfred.csv");
        assertNotEquals(command1, exportMentorCommand);

        // Different parameters returns false
        ExportTeamCommand command2 = new ExportTeamCommand("src/main/test", "Alfred1.csv");
        assertNotEquals(command1, command2);
    }

    @Test
    public void constructor_nonCsvFilePassed_assertionErrorThrown() {
        String fileName = "Alfred.txt";
        assertThrows(AssertionError.class, () -> new ExportTeamCommand("", fileName));
    }

    @Test
    public void execute_emptyModelPassed_successWithNoFileCreated() throws AlfredException {
        Model emptyModel = new ModelManagerStub();
        String filePath = TestUtil.getFilePathInCsvUtilTestFolder("").toString();
        String fileName = "Alfred.csv";
        assertEquals(
                ExportTeamCommand.MESSAGE_EMPTY_DATA,
                new ExportTeamCommand(filePath, fileName).execute(emptyModel).getFeedbackToUser()
        );
        assertFalse(TestUtil.getFilePathInCsvUtilTestFolder(fileName).toFile().exists());
    }

    @Test
    public void execute_validParametersPassed_success() throws AlfredException, IOException {
        Model model = new ModelManagerStub();
        initializeTeams(model);

        File expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedTeams.csv").toFile();
        String filePath = TestUtil.getFilePathInSandboxFolder("").toString();
        String fileName = "Alfred.csv";
        String expectedMessage = String.format(ExportTeamCommand.MESSAGE_SUCCESS, filePath + File.separator + fileName);

        assertEquals(expectedMessage, new ExportTeamCommand(filePath, fileName).execute(model).getFeedbackToUser());

        File outcomeFile = TestUtil.getFilePathInSandboxFolder("Alfred.csv").toFile();
        outcomeFile.deleteOnExit();
        assertTrue(FileUtil.hasEqualContents(expectedFile, outcomeFile));
    }

}
