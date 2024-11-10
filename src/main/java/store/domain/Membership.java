package store.domain;

public class Membership {
    private static final float DISCOUNT_RATE = 0.3F;
    private static final int DISCOUNT_MAX = 8000;

    private final boolean isMembership;

    public Membership(boolean isMembership) {
        this.isMembership = isMembership;
    }

    public int calculateDiscount(int price) {
        if(isMembership) {
            int discountPrice = (int) (price * DISCOUNT_RATE);
            return Math.min(discountPrice, DISCOUNT_MAX);
        }
        return 0;
    }

}
