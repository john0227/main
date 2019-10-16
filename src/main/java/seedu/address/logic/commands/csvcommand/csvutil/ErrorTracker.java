package seedu.address.logic.commands.csvcommand.csvutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A tracker to show user which line in the CSV file was not able to be loaded into Alfred.
 */
public class ErrorTracker {

    private List<Error> errors;

    public ErrorTracker() {
        this.errors = new ArrayList<>();
    }

    public ErrorTracker(Error... errors) {
        this.errors = Arrays.asList(errors);
    }

    public ErrorTracker(ErrorTracker errorTrackerToCopy) {
        this.errors = errorTrackerToCopy.errors;
    }

    public void add(Error error) {
        this.errors.add(error);
    }

    public boolean isEmpty() {
        return this.errors.isEmpty();
    }

    @Override
    public String toString() {
        return errors.stream().map(Error::toString).collect(Collectors.joining("\n"));
    }

    /**
     * Encapsulates an error arisen while parsing a CSV file into {@code Entity} objects.
     */
    public static class Error {
        private int lineNumber;
        private String csvLine;
        private String cause;

        public Error(int lineNumber, String csvLine, String cause) {
            this.lineNumber = lineNumber;
            this.csvLine = csvLine;
            this.cause = cause;
        }

        @Override
        public String toString() {
            return String.format("    Line %d: %s (Cause: %s)", this.lineNumber, this.csvLine, this.cause);
        }
    }

}
