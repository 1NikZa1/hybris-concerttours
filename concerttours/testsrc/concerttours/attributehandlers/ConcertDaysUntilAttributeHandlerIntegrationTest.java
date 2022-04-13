package concerttours.attributehandlers;

import concerttours.model.ConcertModel;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@IntegrationTest
public class ConcertDaysUntilAttributeHandlerIntegrationTest extends ServicelayerTransactionalTest
{
    @Resource
    private ModelService modelService;
    @Test
    public void testGetFutureConcertDate() {
        final ConcertModel concert = modelService.create(ConcertModel.class);
        final Date futureDate = new Date(new Date().getTime() + 49 * 60 * 60 * 1000);
        concert.setDate(futureDate);
        assertEquals("Wrong value for concert in the future: " + concert.getDaysUntil(), 2L, concert.getDaysUntil().longValue());
    }
    @Test
    public void testGetNullConcertDate()
    {
        final ConcertModel concert = modelService.create(ConcertModel.class);
        assertNull("No concert date does not return null: "+ concert.getDaysUntil(), concert.getDaysUntil());
    }
    @Test
    public void testGetPastConcertDate() {
        final ConcertModel concert = modelService.create(ConcertModel.class);
        final Date pastDate = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
        concert.setDate(pastDate);
        assertEquals("Wrong value for concert in the past: "+ concert.getDaysUntil(), 0L, concert.getDaysUntil().longValue());
    }
}