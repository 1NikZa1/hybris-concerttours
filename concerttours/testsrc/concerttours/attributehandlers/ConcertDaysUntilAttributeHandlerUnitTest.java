package concerttours.attributehandlers;

import concerttours.model.ConcertModel;
import de.hybris.bootstrap.annotations.UnitTest;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@UnitTest
public class ConcertDaysUntilAttributeHandlerUnitTest {
    @Test
    public void testGetFutureConcertDate() {
        final ConcertModel concert = new ConcertModel();
        final ConcertDaysUntilAttributeHandler handler = new ConcertDaysUntilAttributeHandler();
        final Date futureDate = new Date(new Date().getTime() + 49 * 60 * 60 * 1000);
        concert.setDate(futureDate);
        assertEquals("Wrong value for concert in the future", 2L, handler.get(concert).longValue());
    }

    @Test
    public void testGetNullConcertDate() {
        final ConcertModel concert = new ConcertModel();
        final ConcertDaysUntilAttributeHandler handler = new ConcertDaysUntilAttributeHandler();
        assertNull(handler.get(concert));
    }

    @Test
    public void testGetPastConcertDate() {
        final ConcertModel concert = new ConcertModel();
        final ConcertDaysUntilAttributeHandler handler = new ConcertDaysUntilAttributeHandler();
        final Date futureDate = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
        concert.setDate(futureDate);
        assertEquals("Wrong value for concert in the past", 0L, handler.get(concert).longValue());
    }
}