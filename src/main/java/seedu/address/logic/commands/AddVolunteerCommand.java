package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Volunteer;

/**
 * Adds a volunteer to the database.
 */
public class AddVolunteerCommand extends Command {

    public static final String COMMAND_WORD = "add_volunteer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a volunteer to the database. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_AGE + "AGE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_AGE + "20 "
            + PREFIX_TAG + "new "
            + PREFIX_TAG + "undergradStudent";

    public static final String MESSAGE_SUCCESS = "New volunteer added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This volunteer already exists in the database";

    private final Volunteer toAdd;

    /**
     * Creates an AddVolunteerCommand to add to the specified {@code volunteer}
     */
    public AddVolunteerCommand(Volunteer volunteer) {
        requireNonNull(volunteer);
        toAdd = volunteer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddVolunteerCommand // instanceof handles nulls
                && toAdd.equals(((AddVolunteerCommand) other).toAdd));
    }
}
