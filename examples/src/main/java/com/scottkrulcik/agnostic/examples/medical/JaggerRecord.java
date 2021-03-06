package com.scottkrulcik.agnostic.examples.medical;

import com.scottkrulcik.agnostic.DAO;
import com.scottkrulcik.agnostic.SanitizingFactory;
import com.scottkrulcik.agnostic.annotations.ContextScope;
import com.scottkrulcik.agnostic.examples.medical.Model.ConsentForm;
import com.scottkrulcik.agnostic.examples.medical.Model.Doctor;
import com.scottkrulcik.agnostic.examples.medical.Model.Person;
import com.scottkrulcik.agnostic.examples.medical.Model.Record;

import javax.inject.Inject;

import static com.scottkrulcik.agnostic.examples.medical.Model.REDACTED;

/**
 * Hard-coded version of the code that would be generated for the medical records application.
 */
final class JaggerRecord extends Record {
    private final Factory creator;
    private final Record delegate;

    public JaggerRecord(Record delegate, Factory creator) {
        // TODO preconditions
        this.delegate = delegate;
        this.creator = creator;
    }

    @Override
    public Person patient() {
        return delegate.patient();
    }

    @Override
    public Person provider() {
        return delegate.provider();
    }

    @Override
    public String condition() {
        if (psychNoteRule(creator.requester, creator.consentFormDao)) {
            return delegate.condition();
        }
        return REDACTED;
    }

    @Override
    public boolean isPsychNote() {
        return delegate.isPsychNote();
    }

    @ContextScope
    public static final class Factory implements SanitizingFactory<Record> {

        private final @Doctor Person requester;
        private final DAO<ConsentForm> consentFormDao;

        @Inject
        Factory(@Doctor Person requester, DAO<ConsentForm> consentFormDao) {
            this.requester = requester;
            this.consentFormDao = consentFormDao;
        }

        @Override
        public JaggerRecord wrap(Record raw) {
            return new JaggerRecord(raw, this);
        }
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

}
