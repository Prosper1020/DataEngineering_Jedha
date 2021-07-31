class Fraction(val numerator: Int, val denominator: Int){
  def multiply(fraction: Fraction): Fraction = {
    new Fraction(fraction.numerator * numerator, fraction.denominator * denominator)
  }

  def add(fraction: Fraction): Fraction = {
    new Fraction(fraction.numerator * denominator + numerator * fraction.denominator, fraction.denominator * denominator)
  }

  def approximation(): Double = {
    numerator / denominator
  }

  override def toString = s"$numerator / $denominator"

}

// Create a class that represents fractions and methods to
// - multiply
// - add
// - approximate the result
// - toString method
object Exercise0 {
  def main(args: Array[String]): Unit = {
    val frac1 = new Fraction(1,2)
    val frac2 = new Fraction(4,5)

    val sumFrac = frac1.add(frac2)

    println(sumFrac)
    println(sumFrac.approximation())
  }
}