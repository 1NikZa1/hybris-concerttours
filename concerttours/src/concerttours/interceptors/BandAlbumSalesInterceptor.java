package concerttours.interceptors;

import static de.hybris.platform.servicelayer.model.ModelContextUtils.getItemModelContext;

import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import concerttours.events.BandAlbumSalesEvent;
import concerttours.model.BandModel;

public class BandAlbumSalesInterceptor implements ValidateInterceptor<BandModel>, PrepareInterceptor<BandModel> {
    private static final long BIG_SALES = 50000L;
    private static final long NEGATIVE_SALES = 0L;
    @Autowired
    private EventService eventService;

    @Override
    public void onValidate(BandModel model, final InterceptorContext ctx) throws InterceptorException {
        final Long sales = model.getAlbumSales();
        if (sales != null && sales < NEGATIVE_SALES) {
            throw new InterceptorException("Album sales must be positive");
        }
    }

    @Override
    public void onPrepare(BandModel model, final InterceptorContext ctx) {
        if (hasBecomeBig(model, ctx)) {
            eventService.publishEvent(new BandAlbumSalesEvent(model.getCode(), model.getName(), model.getAlbumSales()));
        }
    }

    private boolean hasBecomeBig(final BandModel band, final InterceptorContext ctx) {
        final Long sales = band.getAlbumSales();
        if (sales != null && sales >= BIG_SALES) {
            if (ctx.isNew(band)) {
                return true;
            } else {
                final Long oldValue = getItemModelContext(band).getOriginalValue(BandModel.ALBUMSALES);
                if (oldValue == null || oldValue.intValue() < BIG_SALES) {
                    return true;
                }
            }
        }
        return false;
    }
}