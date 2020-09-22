import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

abstract class AbstractJodaTimeJsonAdapter<T>(
        formats: Array<String>
) : AbstractCalendarJsonAdapter<T, DateTimeFormatter>(formats) {
    override fun createFormatter(pattern: String): DateTimeFormatter = DateTimeFormat.forPattern(pattern)
}
