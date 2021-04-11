import kotlin.test.*

class MatrixTest {
    private val m1 = Matrix(arrayOf(
        doubleArrayOf(1.0, 0.5),
        doubleArrayOf(-0.3, 1.7)
    ))
    private val m2 = Matrix(arrayOf(
        doubleArrayOf(1.0, -0.8),
        doubleArrayOf(4.0, 1.7)
    ))
    private val mSum = Matrix(arrayOf(
        doubleArrayOf(2.0, -0.3),
        doubleArrayOf(3.7, 3.4)
    ))
    private val mDiff = Matrix(arrayOf(
        doubleArrayOf(0.0, 1.3),
        doubleArrayOf(-4.3, 0.0)
    ))
    private val m3 = Matrix(arrayOf(
        doubleArrayOf(0.2, -0.6),
        doubleArrayOf(0.1, 0.4),
        doubleArrayOf(0.8, 0.1),
    ))
    private val mProd = Matrix(arrayOf(
        doubleArrayOf(0.38, -0.92),
        doubleArrayOf(-0.02, 0.73),
        doubleArrayOf(0.77, 0.57)
    ))

    @Test
    fun sum() {
        assertEquals(Matrix() + Matrix(), Matrix());
        assertTrue((m1 + m2).approxEquals(mSum));
    }

    @Test
    fun mDiff() {
        assertEquals(Matrix() - Matrix(), Matrix());
        assertTrue((m1 - m2).approxEquals(mDiff));
    }

    @Test
    fun prod() {
        assertEquals(Matrix() * Matrix(), Matrix());
        assertTrue((m3 * m1).approxEquals(mProd));
    }

    @Test
    fun constructor() {
        assertFailsWith<IllegalArgumentException> {
            Matrix(arrayOf(
                doubleArrayOf(0.1, 0.2),
                doubleArrayOf(0.3),
            ))
        }
    }

    @Test
    fun approxEquals() {
        assertTrue(m1.approxEquals(m1));
        assertTrue(m2.approxEquals(m2));
        assertFalse(m1.approxEquals(m2));
    }
}