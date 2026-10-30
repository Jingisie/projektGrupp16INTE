package se.grupp.projekt;

public class WeighingService {

    private static final int MAX_ATTEMPTS = 3;

    private final Scale scale;

    public WeighingService(Scale scale) {
        if (scale == null) {
            throw new IllegalArgumentException("Scale cannot be null");
        }
        this.scale = scale;
    }

    public Money priceOf(WeightPricedItem item) throws ScaleException {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        ScaleException lastException = null;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                return item.getPriceFor(scale.weigh());
            } catch (ScaleException e) {
                lastException = e;
            }
        }
        throw lastException;
    }
}