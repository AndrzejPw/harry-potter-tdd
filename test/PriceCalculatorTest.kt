import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/*
One copy of any of the five books costs 8 EUR. If, however, you buy two different books from the series,
you get a 5% discount on those two books. If you buy 3 different books, you get a 10% discount.
With 4 different books, you get a 20% discount. If you go the whole hog, and buy all 5, you get a huge 25% discount.

Note that if you buy, say, four books, of which 3 are different titles,
you get a 10% discount on the 3 that form part of a set, but the fourth book still costs 8 EUR.

Potter mania is sweeping the country and parents of teenagers everywhere are queueing up with shopping baskets
overflowing with Potter books. Your mission is to write a piece of code to calculate the price of any conceivable
shopping basket, giving as big a discount as possible.

For example, how much does this basket of books cost?

2 copies of the first book
2 copies of the second book
2 copies of the third book
1 copy of the fourth book
1 copy of the fifth book
answer :

  (4 * 8) - 20% [first book, second book, third book, fourth book]
+ (4 * 8) - 20% [first book, second book, third book, fifth book]
= 25.6 * 2
= 51.20
 */

class PriceCalculatorTest {

    @Test
    fun `first book should cost 8 EUR`() {
        val total = PriceCalculator.price(1)
        assertThat(total).isEqualTo(800)
    }

    @Test
    fun `two of the same book should cost 16`() {
        val total = PriceCalculator.price(1, 1)
        assertThat(total).isEqualTo(1600)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `book not in Harry Potter series should not be accepted`() {
        PriceCalculator.price(6)
    }

    @Test
    fun `two different books should have 5 percent discount`() {
        val total = PriceCalculator.price(1, 2)
        assertThat(total).isEqualTo(1520)
    }

    @Test
    fun `two different books should have 5 percent discount one should be not discouted`() {
        val total = PriceCalculator.price(1, 2, 1)
        assertThat(total).isEqualTo(2320)
    }

    @Test
    fun `three different books should have 10 percent discount and one not be discounted`() {
        val total = PriceCalculator.price(1, 2, 3, 1)
        assertThat(total).isEqualTo(2960)
    }

    @Test
    fun `two groups of five should be calculated`() {
        val total = PriceCalculator.price(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)
        assertThat(total).isEqualTo((10 * 800 * 0.75).toInt())
    }

    @Test
    fun `2 series of 4 are better than series of 5 and series of 3 `() {
        val total = PriceCalculator.price(1, 1, 2, 2, 3, 3, 4, 5)
        assertThat(total).isEqualTo((5120).toInt())
    }

    @Test
    fun `another edge case`() {
        val total = PriceCalculator.price(1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5)
        assertThat(total).isEqualTo((3 * (800 * 5 * 0.75) + 2 * (800 * 4 * 0.8)).toInt())
    }
}