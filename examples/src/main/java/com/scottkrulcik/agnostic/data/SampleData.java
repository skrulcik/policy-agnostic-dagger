package com.scottkrulcik.agnostic.data;

import com.google.auto.value.AutoValue;
import com.scottkrulcik.agnostic.annotations.SafeDefault;
import com.scottkrulcik.agnostic.annotations.Restrict;
import com.scottkrulcik.agnostic.annotations.Restriction;

import java.time.Instant;
import java.util.Date;

/**
 * Simple test class to test the {@link Restrict} and {@link Restriction} annotations.
 */
@AutoValue
public abstract class SampleData {
    @SafeDefault("name")
    public static final String ANONYMOUS = "Anonymous";
    @SafeDefault("creationDate")
    public static final Date DEFAULT_DATE = Date.from(Instant.EPOCH);

    @Restrict("creationDate")
    public abstract Date creationDate();

    @Restriction("creationDate")
    public final boolean isCreationDateVisible() {
        return false;
    }

    @Restrict("name")
    public abstract String name();

    @Restriction(value = "name", dependencies = {"creationDate"})
    public final boolean isNameVisible() {
        return true;
    }

    public abstract SampleData withName(String name);

    public abstract SampleData withCreationDate(Date creationDate);

    protected SampleData sanitize() {
        SampleData sanitized = this;
        if (!isNameVisible()) {
            sanitized = this.withName(ANONYMOUS);
        }
        if (!isCreationDateVisible()) {
            sanitized = this.withCreationDate(DEFAULT_DATE);
        }
        return sanitized;
    }

}
