import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormatter

class LocalDataJsonAdapter(
        formats: Array<String>
) : AbstractDateJsonAdapter<LocalDate>(formats) {

    override fun fromJsonInner(value: String, dateTimeFormatter: DateTimeFormatter): LocalDate {
        return LocalDate.parse(value, dateTimeFormatter)
    }

    override fun toJsonInner(value: LocalDate?, dateTimeFormatter: DateTimeFormatter): String? {
        return value?.toString(dateTimeFormatter)
    }

}
