import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern
import scala.Option.empty
import scala.concurrent.duration.{Duration, MINUTES}
import scala.io.StdIn.{readInt, readLine}

object CalendarCLI {
  var calendar: List[(String, LocalDateTime, Duration)] = List()

  def displayMenu(): Unit = {
    println("1. View Calendar")
    println("2. Add entry")
    println("3. Modify entry")
    println("4. Delete entry")
    println("5. Exit")
    print("> ")
  }


  def displayCalendar(): Unit = {
    println("-------------")
    calendar.zipWithIndex.foreach { case (e, i) => println(i + ". " + e._2 + " - " + e._1 + " - " + e._3) }
    println("-------------")

  }

  def deleteEntry(entryId: Int): Unit = {
    calendar = calendar.patch(entryId, Nil, 1)
  }

  // In this function we use Option to avoid code duplication. Most of the code is similar for creat and modify an entry
  // It is totally acceptable for the students to implement it
  // twice, once for the modify entry, once for the create entry.
  def createEntry(name: Option[String], start: Option[LocalDateTime], duration: Option[Duration]): Unit = {

    println("Enter the name of the entry " + name.map(n => f"(Press enter to keep $n)").getOrElse(""))
    print("> ")
    val entryNameInput: String = readLine()
    val entryName = if (entryNameInput.length == 0) name.get else entryNameInput

    println("Enter the starting date of the entry YYYY-MM-DD HH:mm " + start.map(s => s"(Press enter to keep $s)").getOrElse(""))
    print("> ")
    val entryStartInput: String = readLine()
    val entryStart = if (entryStartInput.length == 0) start.get else LocalDateTime.parse(entryStartInput, ofPattern("yyyy-MM-dd HH:mm"))

    println("Enter the duration of the entry in minutes " + duration.map(d => s"(Press enter to keep $d)").getOrElse(""))
    print("> ")
    val entryDurationInput: String = readLine()
    val entryDuration = if (entryDurationInput.length == 0) duration.get else Duration(entryDurationInput.toInt, MINUTES)
    calendar = calendar.appended((entryName, entryStart, entryDuration))
  }

  // In this function we process the input.
  // Each value of the userInput will trigger a different action.
  def processInput(userInput: Int): Unit = {
    if (userInput == 1) {
      displayCalendar()
    } else if (userInput == 2) {
      createEntry(Some("default_name"), empty, empty)
    } else if (userInput == 3) {
      displayCalendar()
      println("What calendar entry do you want to modify")
      print("> ")
      val entryId = readInt()
      val entry = calendar(entryId)
      deleteEntry(entryId)
      createEntry(Option(entry._1), Option(entry._2), Option(entry._3))
    } else if (userInput == 4) {
      displayCalendar()
      println("What calendar entry do you want to delete")
      print("> ")
      deleteEntry(readInt())
    } else if (userInput == 5) {
      System.exit(1)
    }
  }

  def main(args: Array[String]): Unit = {
    // This is the main loop that keeps the program runing until the users decides to exit.
    while (true) {
      displayMenu()
      val userInput = readInt()
      processInput(userInput)
    }
  }
}
