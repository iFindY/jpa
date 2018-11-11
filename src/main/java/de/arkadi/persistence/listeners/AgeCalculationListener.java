package de.arkadi.persistence.listeners;

import de.arkadi.persistence.model.Artist;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.YEAR;

public class AgeCalculationListener {

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculateAge(Artist artist) {
        System.out.println(".AgeCalculationListener calculateAge()");
        if (artist.getDateOfBirth() == null) {
            artist.setAge(null);
            return;
        }

        Calendar birth = new GregorianCalendar();
        birth.setTime(artist.getDateOfBirth());
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        int adjust = 0;
        if (now.get(DAY_OF_YEAR) - birth.get(DAY_OF_YEAR) < 0) {
            adjust = -1;
        }
        artist.setAge(now.get(YEAR) - birth.get(YEAR) + adjust);
    }
}