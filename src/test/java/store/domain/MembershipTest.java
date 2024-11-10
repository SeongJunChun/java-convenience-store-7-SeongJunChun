package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MembershipTest {

    @DisplayName("입력한 금액에 대해 멤버쉽 할인 금액을 반환한다")
    @Test
    void membershipDiscountTest() {
        Membership membership = new Membership(true);
        assertThat(membership.calculateDiscount(10000)).isEqualTo(3000);
    }

    @DisplayName("할인 금액이 한도를 넘으면 최대한 한도까지만 적용한다")
    @Test
    void membershipDiscountRangeTest() {
        Membership membership = new Membership(true);
        assertThat(membership.calculateDiscount(100000)).isEqualTo(8000);
    }

    @DisplayName("멤버쉽 미적용시 할인이 적용되지 않는다")
    @Test
    void NonMembershipDiscountTest() {
        Membership membership = new Membership(false);
        assertThat(membership.calculateDiscount(10000)).isEqualTo(0);
    }

}