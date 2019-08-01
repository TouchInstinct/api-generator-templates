import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.Arrays

abstract class AbstractDateJsonAdapter<T>(
        private val formats: Array<String>
) : JsonAdapter<T>() {

    companion object {
        const val CANT_FIND_ANY_FORMATTERS = "Can't find any DateTimeFormatter"
        const val LOCAL_DATE_CANNOT_BE_NULL = "LocalDate value can't be null"
    }

    private val formatters = formats.map(DateTimeFormat::forPattern)

    override fun fromJson(reader: JsonReader): T? {
        val dateTimeString = reader.nextString()
        formatters.forEachIndexed { index, dateTimeFormatter ->
            try {
                return fromJsonInner(dateTimeString, dateTimeFormatter)
            } catch (e: IllegalArgumentException) {
                if (index == formatters.lastIndex) {
                    throw JsonEncodingException("$dateTimeString doesn't fit any of the formats ${Arrays.toString(formats)}")
                }
            }
        }
        throw IllegalArgumentException(CANT_FIND_ANY_FORMATTERS)
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        if (formatters.isEmpty()) throw IllegalArgumentException(CANT_FIND_ANY_FORMATTERS)
        value?.let {
            writer.value(toJsonInner(value, formatters.first()))
        } ?: throw JsonEncodingException(LOCAL_DATE_CANNOT_BE_NULL)
    }

    abstract fun fromJsonInner(value: String, dateTimeFormatter: DateTimeFormatter): T

    abstract fun toJsonInner(value: T?, dateTimeFormatter: DateTimeFormatter) : String?

}
