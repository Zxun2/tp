package seedu.address.storage.elderly;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FriendlyLink;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.information.Address;
import seedu.address.model.person.information.Age;
import seedu.address.model.person.information.Email;
import seedu.address.model.person.information.Name;
import seedu.address.model.person.information.Nric;
import seedu.address.model.person.information.Phone;
import seedu.address.model.person.information.RiskLevel;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonAdaptedPerson;
import seedu.address.storage.JsonAdaptedTag;
import seedu.address.storage.JsonSerializable;

/**
 * Jackson-friendly version of {@link Elderly}.
 */
public class JsonAdaptedElderly extends JsonAdaptedPerson implements JsonSerializable<Elderly> {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Elderly's %s field is missing!";
    private final String nric;
    private final String age;
    private final String riskLevel;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedElderly(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("nric") String nric, @JsonProperty("age") String age,
            @JsonProperty("riskLevel") String riskLevel, @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {

        super(name, phone, email, address, tagged);
        this.nric = nric;
        this.age = age;
        this.riskLevel = riskLevel;
    }

    /**
     * Converts a given {@code Elderly} into this class for Jackson use.
     */
    public JsonAdaptedElderly(Elderly source) {
        super(source);
        nric = source.getNric().value;
        age = source.getAge().value;
        riskLevel = String.valueOf(source.getRiskLevel().riskStatus);
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Elderly toModelType(FriendlyLink friendlyLink) throws IllegalValueException {
        Name modelName = super.getModelName();
        Phone modelPhone = super.getModelPhone();
        Email modelEmail = super.getModelEmail();
        Address modelAddress = super.getModelAddress();
        Set<Tag> modelTags = super.getTagSet(friendlyLink);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
        }
        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }
        if (riskLevel == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RiskLevel.class.getSimpleName()));
        }
        if (!RiskLevel.isValidRisk(riskLevel)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }

        return new Elderly(modelName, modelPhone, modelEmail, modelAddress,
                new Nric(nric), new Age(age), new RiskLevel(riskLevel), modelTags);
    }
}