package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.addcommand.AddTeamCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TeamBuilder;

public class AddTeamCommandTest {

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTeamCommand(null));
    }

    @Test
    public void execute_teamAcceptedByModel_addSuccessful() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Team validTeam = new TeamBuilder().build();

        CommandResult commandResult = new AddTeamCommand(validTeam).execute(modelStub);

        assertEquals(String.format(AddTeamCommand.MESSAGE_SUCCESS, validTeam),
                commandResult.getFeedbackToUser());
        assertEquals(validTeam, modelStub.getTeam(new Id(PrefixType.T, 1)));
    }

    @Test
    public void execute_duplicateTeam_throwsCommandException() throws AlfredException {
        Team validTeam = new TeamBuilder().build();
        AddTeamCommand addTeamCommand = new AddTeamCommand(validTeam);
        ModelManagerStub modelStub = new ModelManagerStub();
        addTeamCommand.execute(modelStub);

        Assert.assertThrows(CommandException.class,
                AddTeamCommand.MESSAGE_DUPLICATE_TEAM, () -> addTeamCommand.execute(modelStub));
    }

}
