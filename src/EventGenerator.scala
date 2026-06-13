import java.time.Instant
import scala.util.Random

object EventGenerator{

private val users= Vector("andreea","admin","guest","developer")

private val ipAddresses= Vector("192.168.1.10",
    "10.0.0.25",
    "203.0.113.50")

def generateNormalEvent(): SecurityEvent =
  val eventType =
    if Random.nextDouble() < 0.75 then
      EventType.LoginSuccessful
    else
      EventType.LoginFailed

  SecurityEvent(
    userId = users(Random.nextInt(users.size)),
    ipAddress =
      ipAddresses(Random.nextInt(ipAddresses.size)),
    eventType = eventType,
    timeStamp = Instant.now()
  )
def generateAttack(
    userId: String
): List[SecurityEvent] =
  List.fill(4)(
    SecurityEvent(
      userId = userId,
      ipAddress = "203.0.113.99",
      eventType = EventType.LoginFailed,
      timeStamp = Instant.now()
    )
  )
}