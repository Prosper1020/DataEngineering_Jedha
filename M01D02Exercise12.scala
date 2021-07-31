import scala.io.StdIn.readLine

object Exercise2{

  def isInt(str: String): Boolean = { str.forall(_.isDigit)}

  def main(args: Array[String]): Unit = {
    val in = readLine("Enter either a string or an Int: ")
    val result: Either[String,Int] = if (isInt(in)) Right(in.toInt) else Left(in)

    println( result match {
      case Right(x) => "You wrote an Int: " + x
      case Left(x) => "You wrote a String: " + x
    })
  }
}