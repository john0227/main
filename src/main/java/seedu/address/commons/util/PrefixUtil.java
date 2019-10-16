package seedu.address.commons.util;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.ReadOnlyEntityList;
import seedu.address.model.entitylist.TeamList;

/**
 * Retrieves the corresponding {@link PrefixType} of an {@code Entity} or an {@code EntityList}.
 */
public class PrefixUtil {

    public static PrefixType getPrefixOf(Entity entity) {
        if (entity instanceof Mentor) {
            return PrefixType.M;
        } else if (entity instanceof Participant) {
            return PrefixType.P;
        } else if (entity instanceof Team) {
            return PrefixType.T;
        } else {
            // Should never reach here
            throw new RuntimeException();
        }
    }

    public static PrefixType getPrefixOf(ReadOnlyEntityList entityList) {
        if (entityList instanceof MentorList) {
            return PrefixType.M;
        } else if (entityList instanceof ParticipantList) {
            return PrefixType.P;
        } else if (entityList instanceof TeamList) {
            return PrefixType.T;
        } else {
            // Should never reach here
            throw new RuntimeException();
        }
    }

}
