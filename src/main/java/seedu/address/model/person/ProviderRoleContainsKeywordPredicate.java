package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Staff}'s {@code Role} matches the keyword given.
 */
public class ProviderRoleContainsKeywordPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ProviderRoleContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase("NA", keyword))) {
            return false;
        }
        if (person instanceof HealthcareStaff) {
            HealthcareStaff staff = (HealthcareStaff) person;
            return keywords.stream()
                .anyMatch(keyword -> StringUtil
                    .containsPartialWordIgnoreCase(staff.getProviderRole().toString(), keyword));
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProviderRoleContainsKeywordPredicate)) {
            return false;
        }

        ProviderRoleContainsKeywordPredicate otherNameContainsKeywordsPredicate =
            (ProviderRoleContainsKeywordPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
