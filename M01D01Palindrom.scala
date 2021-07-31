import scala.io.StdIn.readLine;

object Palindrom_corrige {

  def reverseString(input: String): String = {
    var reversedStr = ""
    var currentChar = ""
    for (idx <- 0 until input.length) {
      currentChar = input(idx).toString()
      reversedStr = s"$currentChar$reversedStr"
      // println(s"itération ${idx+1} : $reversedStr")
    }
    reversedStr
  }

  def isPalindrom(input: String): Boolean = {
    reverseString(input) == input
  }

  def main(args: Array[String]): Unit = {
    println("Please enter a string and press enter.")
    val userInput = readLine()

    val reversedStr = reverseString(userInput)
    println(f"The reversed string is: $reversedStr")

    val isPalindrome = isPalindrom(userInput)
    println(f"Is $userInput%s a palindrom? $isPalindrome%s")
  }
}
