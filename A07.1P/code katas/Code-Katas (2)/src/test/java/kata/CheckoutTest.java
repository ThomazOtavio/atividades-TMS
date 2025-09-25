package kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests based on the classic CodeKata #9 examples:
 * Unit prices: A=50, B=30, C=20, D=15
 * Offers: A: 3 for 130, B: 2 for 45
 */
public class CheckoutTest {

    PricingRules rules;

    @BeforeEach
    void setup() {
        rules = new PricingRules()
            .add('A', 50, new Offer(3, 130))
            .add('B', 30, new Offer(2, 45))
            .add('C', 20)
            .add('D', 15);
    }

    private int price(String goods) {
        Checkout co = new Checkout(rules);
        co.scan(goods);
        return co.total();
    }

    @ParameterizedTest
    @CsvSource({
        "'',0",
        "A,50",
        "AB,80",
        "CDBA,115",
        "AA,100",
        "AAA,130",
        "AAAA,180",
        "AAAAA,230",
        "AAAAAA,260",
        "AAAB,160",
        "AAABB,175",
        "AAABBD,190",
        "DABABA,190"
    })
    void totalsMatchKataExamples(String goods, int expected) {
        assertEquals(expected, price(goods));
    }

    @Test
    void scanningIncrementallyYieldsExpectedRunningTotals() {
        Checkout co = new Checkout(rules);
        assertEquals(0, co.total());
        co.scan('A'); assertEquals(50, co.total());
        co.scan('B'); assertEquals(80, co.total());
        co.scan('A'); assertEquals(130, co.total());
        co.scan('A'); assertEquals(160, co.total()); // A: 3 for 130 + B:30 => 160
        co.scan('B'); assertEquals(175, co.total()); // B: 2 for 45
    }

    @Test
    void unknownSkuIsRejected() {
        Checkout co = new Checkout(rules);
        co.scan("AX");
        assertThrows(IllegalArgumentException.class, co::total);
    }
}
