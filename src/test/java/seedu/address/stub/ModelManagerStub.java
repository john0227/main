package seedu.address.stub;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.model.ModelManager;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * Stub for ModelManager in order to avoid saving of Entities created during tests.
 */
public class ModelManagerStub extends ModelManager {

    // TODO: Update when ModelManager gets updated

    public ModelManagerStub() {
        super();
    }

    //========== Entity Methods =============================

    /* Participant Methods */

    /**
     * Adds the participant into the list.
     */
    @Override
    public void addParticipant(Participant participant) throws AlfredException {
        this.participantList.add(participant);
    }

    /**
     * Updates the participant in the list, if any.
     */
    @Override
    public void updateParticipant(Id id, Participant participant) throws AlfredException {
        try {
            // Update the participant in the team list as well
            Team targetTeam = this.getTeamByParticipantId(id);
            boolean isSuccessful = targetTeam.updateParticipant(participant);
            if (!isSuccessful) {
                return;
            }
            this.participantList.update(id, participant);
        } catch (AlfredException e) {
            return;
        }
    }

    /**
     * Deletes the participant by id.
     *
     */
    @Override
    public Participant deleteParticipant(Id id) throws AlfredException {
        Participant deletedParticipant;
        try {
            Team targetTeam = this.getTeamByParticipantId(id);
            Participant participantToDelete = this.getParticipant(id);
            boolean isSuccessful = targetTeam.deleteParticipant(participantToDelete);
            if (!isSuccessful) {
                throw new AlfredModelException("Participant does not exist");
            }
        } catch (AlfredException e) {
            // nothing
        } finally {
            deletedParticipant = this.participantList.delete(id);
        }
        return deletedParticipant;
    }

    /* Team Methods*/

    /**
     * Updates the team with the given teamID.
     */
    @Override
    public void updateTeam(Id teamId, Team updatedTeam) throws AlfredException {
        this.teamList.update(teamId, updatedTeam);
    }

    /**
     * Adds the team.
     */
    @Override
    public void addTeam(Team team) throws AlfredException {
        this.teamList.add(team);
    }

    /**
     * Adds the participant to the given team.
     */
    @Override
    public void addParticipantToTeam(Id teamId, Participant participant) throws AlfredException {
        Team targetTeam = this.getTeam(teamId);
        boolean isSuccessful = targetTeam.addParticipant(participant);
        if (!isSuccessful) {
            throw new AlfredModelException("Participant is already present in team");
        }
    }

    /**
     * Adds the participant to the given team.
     *
     * @param teamId
     * @param mentor
     * @throws AlfredException if the team does not exist.
     */
    @Override
    public void addMentorToTeam(Id teamId, Mentor mentor) throws AlfredException {
        // TODO: Change when ModelManager is updated
        Team targetTeam = this.getTeam(teamId);
        boolean isSuccessful = targetTeam.addMentor(mentor);
        if (!isSuccessful) {
            throw new AlfredModelException("Team already has a mentor");
        }
    }

    /**
     * Deletes the team.
     */
    @Override
    public Team deleteTeam(Id id) throws AlfredException {
        Team teamToDelete = this.teamList.delete(id);
        return teamToDelete;
    }

    /* Mentor Methods */

    /**
     * Adds mentor into the list.
     */
    @Override
    public void addMentor(Mentor mentor) throws AlfredException {
        this.mentorList.add(mentor);
    }

    /**
     * Updates the mentor.
     */
    @Override
    public void updateMentor(Id id, Mentor updatedMentor) throws AlfredException {
        // TODO: Update if ModelManager updates.
        try {
            Team targetTeam = this.getTeamByMentorId(id);
            targetTeam.updateMentor(updatedMentor);
            this.mentorList.update(id, updatedMentor);
        } catch (AlfredException e) {
            return;
        }
        this.mentorList.update(id, updatedMentor);
    }

    /**
     * Deletes the mentor.
     */
    @Override
    public Mentor deleteMentor(Id id) throws AlfredException {
        Mentor mentorToDelete = this.mentorList.delete(id);
        return mentorToDelete;
    }

}
