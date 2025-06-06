package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.HealthcareStaff;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProviderRole;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String role; // NEW: Stores role (STAFF/PATIENT)
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String remark;
    private final String nokName;
    private final String nokPhone;
    private final String doctorInCharge;
    private final String department;
    private final String providerRole;

    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("role") String role,
                             @JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("remark") String remark,
                             @JsonProperty("nok_name") String nokName,
                             @JsonProperty("nok_phone") String nokPhone,
                             @JsonProperty("doctorInCharge") String doctorInCharge,
                             @JsonProperty("department") String department,
                             @JsonProperty("providerRole") String providerRole) {
        this.role = role; // Store role from JSON
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.nokName = nokName;
        this.nokPhone = nokPhone;
        this.doctorInCharge = doctorInCharge;
        this.department = department;
        this.providerRole = providerRole;

        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        role = source.getRole().toString(); // Save role when converting to JSON
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        remark = source.getRemark().value;
        String derivedProviderRole = null;

        if (source instanceof Patient) {
            Patient p = (Patient) source;
            nokName = p.getNextofKin().getName().toString();
            nokPhone = p.getNextofKin().getPhone().toString();
            doctorInCharge = p.getDoctorInCharge();
            department = p.getDepartment().toString();
        } else if (source instanceof HealthcareStaff) {
            HealthcareStaff s = (HealthcareStaff) source;
            derivedProviderRole = s.getProviderRole().toString();
            nokName = null;
            nokPhone = null;
            doctorInCharge = null;
            department = s.getDepartment().toString();
        } else {
            nokName = null;
            nokPhone = null;
            doctorInCharge = null;
            department = null;
        }
        this.providerRole = derivedProviderRole;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }

        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);
        final Remark modelRemark = new Remark(remark);
        final Set<Tag> modelTags = new HashSet<>(personTags);

        // FIX: Ensure correct role is restored
        if (role == null) {
            throw new IllegalValueException("Missing role field in JSON.");
        } else if (role.equalsIgnoreCase("PATIENT")) {
            String inputNokName = nokName == null ? "NA" : nokName;
            String inputNokPhone = nokPhone == null ? "000" : nokPhone;
            NextOfKin inputNextOfKin = new NextOfKin(new Name(inputNokName), new Phone(inputNokPhone));
            return new Patient(
                modelName,
                modelPhone,
                modelEmail,
                modelAddress,
                modelRemark,
                doctorInCharge != null ? doctorInCharge : "",
                inputNextOfKin,
                department != null ? new Department(department) : new Department("")
            );
        } else if (role.equalsIgnoreCase("STAFF")) {
            return new HealthcareStaff(
                modelName,
                new ProviderRole(providerRole != null ? providerRole : ""),
                department != null ? new Department(department) : new Department("NA"),
                modelPhone,
                modelEmail,
                modelAddress,
                modelRemark
            );
        }

        return new Person(new Role(role), modelName, modelPhone, modelEmail, modelAddress, modelRemark);
    }
}

