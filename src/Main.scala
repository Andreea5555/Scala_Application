import java.time.Instant

@main
def runThreatStream(): Unit = {

  println("ThreatStream")
  println("Monitoring login events...")
  println()

  val detector = new ThreatDetector()

  val events = List(
    SecurityEvent(
      userId = "andreea",
      ipAddress = "192.168.1.10",
      eventType = EventType.LoginSuccessful,
      timeStamp = Instant.now()
    ),
    SecurityEvent(
      userId = "admin",
      ipAddress = "203.0.113.99",
      eventType = EventType.LoginFailed,
      timeStamp = Instant.now()
    ),
    SecurityEvent(
      userId = "admin",
      ipAddress = "203.0.113.99",
      eventType = EventType.LoginFailed,
      timeStamp = Instant.now()
    ),
    SecurityEvent(
      userId = "admin",
      ipAddress = "203.0.113.99",
      eventType = EventType.LoginFailed,
      timeStamp = Instant.now()
    )
  )

  events.foreach { event =>

    println(
      s"${event.eventType} | user=${event.userId} | ip=${event.ipAddress}"
    )

    detector.process(event).foreach { alert =>
      println()
      println("===================================")
      println("SECURITY ALERT")
      println(alert.message)
      println(s"Failed attempts: ${alert.failedAttempts}")
      println("===================================")
      println()
    }
  }
}