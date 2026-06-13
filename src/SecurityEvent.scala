enum EventType: 
    case LoginSuccessful
    case LoginFailed

import java.time.Instant

case class SecurityEvent(
    userId: String,
    ipAddress: String,
    eventType: EventType,
    timeStamp: Instant
)

case class Alert(
    userId: String,
    failedAttempts: Int,
    message: String
)