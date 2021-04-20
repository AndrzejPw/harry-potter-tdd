object PriceCalculator {
    const val BASE_BOOK_PRICE = 800
    val BOOKS_ID_RANGE = 1..5

    fun price(vararg books: Int): Int {
        books.forEach { if (it !in BOOKS_ID_RANGE) throw IllegalArgumentException("Book id should be 1...5") }
        val bookSeries = putBooksIntoSeries(books)
        optimizeSeries(bookSeries)
        var total = 0
        for (bookSerie in bookSeries) {
            if (bookSerie.size == 1) {
                total += BASE_BOOK_PRICE
            } else {
                total += (bookSerie.size * BASE_BOOK_PRICE * (1 - discountBasedOnCount((bookSerie.size)))).toInt()
            }
        }

        return total
    }

    // because 2 series of 4 are better than 5 + 3 it will try to move book from 5-sets to 3-sets
    private fun optimizeSeries(bookSeries: List<MutableSet<Int>>) {
        //we know that list is in descending order of set size
        var startIndex = 0
        var endIndex = bookSeries.size - 1
        while (startIndex < endIndex) {
            if (bookSeries[startIndex].size < 5 || bookSeries[endIndex].size > 3) {
                return
            }
            if (bookSeries[endIndex].size == 3) {
                val bookToMove = bookSeries[startIndex].find { !bookSeries[endIndex].contains(it) }!!
                bookSeries[startIndex].remove(bookToMove)
                bookSeries[endIndex].add(bookToMove)
                startIndex++
            }
            endIndex--
        }
    }

    private fun discountBasedOnCount(count: Int): Double = when (count) {
        1 -> 0.0
        2 -> 0.05
        3 -> 0.1
        4 -> 0.2
        5 -> 0.25
        else -> throw IllegalStateException("No more than 5 elements in serie")
    }

    private fun putBooksIntoSeries(books: IntArray): MutableList<MutableSet<Int>> {
        val bookSeries = mutableListOf<MutableSet<Int>>()
        for (book in books) {
            var addedToExistingSerie = tryPutBookIntoExistingSerie(book, bookSeries)
            if (!addedToExistingSerie) {
                bookSeries.add(mutableSetOf(book))
            }
        }
        return bookSeries
    }

    private fun tryPutBookIntoExistingSerie(book: Int, bookSeries: List<MutableSet<Int>>): Boolean {
        for (bookSerie in bookSeries) {
            if (!bookSerie.contains(book)) {
                bookSerie.add(book)
                return true
            }
        }
        return false
    }
}