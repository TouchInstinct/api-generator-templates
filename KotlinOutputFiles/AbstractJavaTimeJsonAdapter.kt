import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

abstract class AbstractJavaTimeJsonAdapter<T : TemporalAccessor>(
        formats: Array<String>
) : AbstractCalendarJsonAdapter<T, DateTimeFormatter>(formats) {
    override fun createFormatter(pattern: String): DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)

    override fun toJsonInner(value: T?, dateTimeFormatter: DateTimeFormatter): String? {
        value ?: return null

        return dateTimeFormatter.format(value)
    }
}
