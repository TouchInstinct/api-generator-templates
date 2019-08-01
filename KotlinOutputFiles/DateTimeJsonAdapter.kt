import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter

class DateTimeJsonAdapter(
        formats: Array<String>
) : AbstractDateJsonAdapter<DateTime>(formats) {

    override fun fromJsonInner(value: String, dateTimeFormatter: DateTimeFormatter): DateTime {
        return DateTime.parse(value, dateTimeFormatter)
    }

    override fun toJsonInner(value: DateTime?, dateTimeFormatter: DateTimeFormatter): String? {
        return value?.toString(dateTimeFormatter)
    }

}