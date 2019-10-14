package seedu.address.logic.commands.viewcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TeamBuilder;

public class ViewTeamCommandTest {

    private Model modelOneTeam;
    private Model expectedOneTeam;
    private Team teamToView;
    private final ByteArrayOutputStream modelOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream expectedOut = new ByteArrayOutputStream();
    private final PrintStream modelPrintStream = new PrintStream(modelOut);
    private final PrintStream expectedPrintStream = new PrintStream(expectedOut);

    @BeforeEach
    public void setup() throws AlfredException {
        this.teamToView = new TeamBuilder().build();
        modelOneTeam = new ModelManagerStub();
        modelOneTeam.addTeam(teamToView);
        expectedOneTeam = new ModelManagerStub();
        expectedOneTeam.addTeam(teamToView);
    }

    @Test
    public void execute_emptyModel_throwCommandException() {
        Model emptyModel = new ModelManagerStub();
        Executable viewTeamCommand = () ->
                new ViewTeamCommand(new Id(PrefixType.T, 1)).execute(emptyModel);
        assertThrows(
                CommandException.class,
                viewTeamCommand,
                ViewTeamCommand.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX
        );
    }

    @Test
    public void execute_invalidId_throwCommandException() {
        Executable viewTeamCommand = () ->
                new ViewTeamCommand(new Id(PrefixType.T, 2)).execute(modelOneTeam);
        assertThrows(
                CommandException.class,
                viewTeamCommand,
                ViewTeamCommand.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX
        );
    }

    @Test
    public void execute_validParameters_success() throws CommandException {
        // Collect console output of ViewTeamCommand of modelOneTeam
        System.setOut(modelPrintStream);
        new ViewTeamCommand(this.teamToView.getId()).execute(modelOneTeam);
        String output = modelOut.toString();
        // Configure correct output
        String expectedOutput = new StringBuilder()
                .append(String.format("Viewing %s\r\n", this.teamToView.getName()))
                .append(String.format("\t%s\r\n", this.teamToView.toString()))
                .toString();
        // Test and reset OutputStream
        assertEquals(expectedOutput, output);
        modelOut.reset();
    }

    @Test
    public void execute_modelWithSameTeam_sameOutput() throws CommandException {
        // Collect console output of ViewTeamCommand of modelOneTeam
        System.setOut(modelPrintStream);
        new ViewTeamCommand(this.teamToView.getId()).execute(modelOneTeam);
        String output1 = modelOut.toString();
        // Collect console output of ViewTeamCommand of expectedOneTeam
        System.setOut(expectedPrintStream);
        new ViewTeamCommand(this.teamToView.getId()).execute(expectedOneTeam);
        String output2 = expectedOut.toString();
        // Test and reset OutputStreams
        assertEquals(output1, output2);
        modelOut.reset();
        expectedOut.reset();
    }

}
