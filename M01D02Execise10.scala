// Implement a reverse string function using recursion.
//
// Tip:
// val s:String
// v.tail gives you every char of the string without the first element
// v.head give you the first char of a string
object Exercise0 {

  def reverse(s: String): String = {
    if (s.isEmpty) "" else reverse(s.tail) + s.head
  }

  def main(args: Array[String]): Unit = {
    println(reverse("jedha"))
    println(reverse("bootcamp"))
  }

}