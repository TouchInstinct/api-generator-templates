
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeJavaTimeJsonAdapter(
        formats: Array<String>
) : AbstractJavaTimeJsonAdapter<ZonedDateTime>(formats) {

    override fun fromJsonInner(value: String, dateTimeFormatter: DateTimeFormatter): ZonedDateTime {
        return ZonedDateTime.parse(value, dateTimeFormatter)
    }

}
