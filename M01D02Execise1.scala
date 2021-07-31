class Seat(var passengerName: Option[String] = Option.empty){
  def setPassengerName(passengerName: String): Unit = {
    this.passengerName = Option(passengerName)
  }

  override def toString: String = passengerName.getOrElse("empty")
}

class Wagon(val seats: List[Seat]) {
  def this(numberOfSeats: Int) {
    this((1 to numberOfSeats).map(_ => new Seat()).toList);
  }

  def findEmptySeat(): Option[Seat] = {
    seats.find(seat => seat.passengerName.isEmpty)
  }

  override def toString: String = {
    seats.map(seat => seat.toString).mkString(" :: ")
  }
}

class Train(val name: String, val wagons: List[Wagon]) {
  private def findEmptySeat(): Option[Seat] = {
    wagons
      .map(wagon => wagon.findEmptySeat())
      .find(wagon => wagon.isDefined)
      .map(seat => seat.get)
  }

  def addPassenger(passengerName: String): Unit = {
    val emptySeat: Option[Seat] = findEmptySeat()
    if (emptySeat.isDefined){
      emptySeat.get.setPassengerName(passengerName)
    }
  }

  override def toString: String = {
    val wagonsStr:String = wagons.map(wagon => s"[ $wagon ] ").mkString(" - ")
    s"$name: $wagonsStr"
  }
}

// Create a class that represents a train
// the train is defined by a name and a list of wagons
// each wagon is defined by a list of seats
// the train should have a method to add passengers to any free seat
// the train should have a to string method
object Exercise1 {
  def main(args: Array[String]): Unit = {
    val train = new Train("TGV 3427", List(new Wagon(3), new Wagon(2)))

    println(train)
    train.addPassenger("Paul")
    println(train)
    train.addPassenger("Antoine")
    println(train)
  }
}
