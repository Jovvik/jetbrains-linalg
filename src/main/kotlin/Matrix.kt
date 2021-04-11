import java.util.*
import kotlin.math.abs

class Matrix(val data: Array<DoubleArray>) {
    val width: Int = data.size
    val height: Int = if (data.isEmpty()) 0 else data[0].size

    init {
        if (data.asSequence().map { arr -> arr.size }.distinct().count() > 1) {
            throw IllegalArgumentException("Matrix rows are of different sizes")
        }
    }

    constructor() : this(emptyArray())
    constructor(width: Int, height: Int) : this(Array(width) { DoubleArray(height) })

    operator fun plus(other: Matrix): Matrix {
        return applyBinaryFunction(other) { a, b -> a + b }
    }

    operator fun minus(other: Matrix): Matrix {
        return applyBinaryFunction(other) { a, b -> a - b }
    }

    /**
     * Naive matrix multiplication in O(n^3)
     */
    operator fun times(other: Matrix): Matrix {
        if (height != other.width) {
            throwSize(other)
        }

        val result = Matrix(width, other.height)
        for (i in 0 until width) {
            for (j in 0 until other.height) {
                for (k in 0 until height) {
                    result[i, j] += this[i, k] * other[k, j]
                }
            }
        }
        return result
    }

    operator fun get(i: Int, j: Int): Double {
        return data[i][j]
    }

    operator fun set(i: Int, j: Int, v: Double) {
        data[i][j] = v
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Matrix) {
            data.contentEquals(other.data)
        } else {
            false
        }
    }

    /**
     * Elementwise comparison to another matrix with epsilon precision.
     */
    fun approxEquals(other: Matrix, epsilon: Double = 1e-8): Boolean {
        for (i in 0 until width) {
            for (j in 0 until height) {
                if (abs(data[i][j] - other.data[i][j]) >= epsilon) {
                    return false
                }
            }
        }
        return true
    }

    private fun applyBinaryFunction(other: Matrix, f: (Double, Double) -> Double): Matrix {
        if (other.height != height && other.width != width) {
            throwSize(other)
        }
        val result = Matrix(width, height)
        for (i in 0 until width) {
            for (j in 0 until height) {
                result[i, j] = f(this[i, j], other[i, j])
            }
        }
        return result
    }

    /**
     * Throws because of a size mismatch
     */
    private fun throwSize(other: Matrix) {
        throw IllegalArgumentException(
            "Matrix size mismatch: (${other.width} x ${other.height}) vs (${width} x ${height})")
    }

    override fun toString(): String {
        val mainSJ = StringJoiner(System.lineSeparator(), "[", "]")
        for (i in 0 until width) {
            val rowSJ = StringJoiner(",", "[", "]")
            for (j in 0 until height) {
                rowSJ.add(this[i, j].toString())
            }
            mainSJ.add(rowSJ.toString())
        }
        return mainSJ.toString()
    }

    override fun hashCode(): Int {
        var result = data.contentDeepHashCode()
        result = 31 * result + width
        result = 31 * result + height
        return result
    }
}
