//> using scala "3.3.8"
import java.time.Instant
import scala.concurrent.duration.*

class ThreatDetector(
    failureTreshold: Int = 3,
    window: FiniteDuration = 30.seconds
){
    private var failedLogins: Map[String, Vector[SecurityEvent]] = 
    Map.empty

    def process(event: SecurityEvent): Option[Alert]= {
        event.eventType match{

            case EventType.LoginSuccessful =>
            failedLogins = failedLogins - event.userId
            None

            case EventType.LoginFailed =>
            processFailedLogin(event) 
        }
    }


    private def processFailedLogin(
    event: SecurityEvent
    ): Option[Alert] =
    {
     val cutoffTime= event.timeStamp.minusSeconds(window.toSeconds)

     val previousFailures = 
        failedLogins.getOrElse(
            event.userId,
            Vector.empty
        )

     val recentFailures =
        previousFailures.filterNot{previousEvent =>
            previousEvent.timeStamp.isBefore(cutoffTime)
        }

     val updatedFailures = 
        recentFailures :+event

     failedLogins= failedLogins.updated(
        event.userId,
        updatedFailures
    )

     if(updatedFailures.size >= failureTreshold){
        Some(
            Alert(
                userId=event.userId,
                failedAttempts = updatedFailures.size,
                message = s"Possible brute force attack against ${event.userId}"
            )
        )
    }
    else {
        None
    }
    
        }

}