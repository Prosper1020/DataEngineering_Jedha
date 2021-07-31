import scala.util.Random

class RandomList(val numbers: List[Int]) {
  def this(n: Int){
    this((1 to n).map(_ => Random.nextInt(100)).toList)
  }

  private def isPrime(n: Int): Boolean = ! ((2 until n-1) exists (n % _ == 0))

  def findBiggestPrime(): Option[Int] = {
    numbers.filter(number => isPrime(number)).reduceOption(_ max _)
  }
}

// Implement an Object RandomList that generates a list of n Int when constructed
// Create a function that will find the biggest prime number in the list. Remember to use container types when
// no prime number is defined in the list.
object Exercise1 {

  def main(args: Array[String]): Unit = {
    val randomNumbers = new RandomList(4)
    println(randomNumbers.findBiggestPrime().getOrElse("no prime number"))
  }
}