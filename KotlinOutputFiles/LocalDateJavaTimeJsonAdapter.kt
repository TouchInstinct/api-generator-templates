import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateJavaTimeJsonAdapter(
        formats: Array<String>
) : AbstractJavaTimeJsonAdapter<LocalDate>(formats) {

    override fun fromJsonInner(value: String, dateTimeFormatter: DateTimeFormatter): LocalDate {
        return LocalDate.parse(value, dateTimeFormatter)
    }

}
