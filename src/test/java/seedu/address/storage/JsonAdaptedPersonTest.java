package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_ROLE = "staff";
    private static final String INVALID_NAME = "R@chel=";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_ROLE = "staff";
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final String VALID_NOKNAME = "Dummy Guardian";
    private static final String VALID_NOKPHONE = "65123478";
    private static final String VALID_DOCTOR = "Dummy Doctor";
    private static final String VALID_DEPARTMENT = "Dummy Department";
    private static final String VALID_PROVIDER_ROLE = "Cardiologist";

    @Test
    @Disabled("temporarily disable")
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_ROLE, INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_REMARK, VALID_NOKNAME, VALID_NOKPHONE, VALID_DOCTOR, VALID_DEPARTMENT,
                        VALID_PROVIDER_ROLE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_ROLE, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_REMARK, VALID_NOKNAME, VALID_NOKPHONE, VALID_DOCTOR, VALID_DEPARTMENT,
                VALID_PROVIDER_ROLE);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_ROLE, VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_REMARK, VALID_NOKNAME, VALID_NOKPHONE, VALID_DOCTOR, VALID_DEPARTMENT,
                        VALID_PROVIDER_ROLE);

        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_ROLE, VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_REMARK, VALID_NOKNAME, VALID_NOKPHONE, VALID_DOCTOR, VALID_DEPARTMENT,
                VALID_PROVIDER_ROLE);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_ROLE, VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_REMARK, VALID_NOKNAME, VALID_NOKPHONE, VALID_DOCTOR, VALID_DEPARTMENT,
                        VALID_PROVIDER_ROLE);

        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_ROLE, VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_REMARK, VALID_NOKNAME, VALID_NOKPHONE, VALID_DOCTOR, VALID_DEPARTMENT,
                VALID_PROVIDER_ROLE);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_ROLE, VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_REMARK, VALID_NOKNAME, VALID_NOKPHONE, VALID_DOCTOR, VALID_DEPARTMENT,
                        VALID_PROVIDER_ROLE);

        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_ROLE, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_REMARK, VALID_NOKNAME, VALID_NOKPHONE, VALID_DOCTOR, VALID_DEPARTMENT,
                VALID_PROVIDER_ROLE);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
