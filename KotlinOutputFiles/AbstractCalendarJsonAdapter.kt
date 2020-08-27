import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.util.Arrays

abstract class AbstractCalendarJsonAdapter<T, FORMATTER>(
        private val formats: Array<String>
) : JsonAdapter<T>() {

    companion object {
        private const val CANT_FIND_ANY_FORMATTERS = "Can't find any DateTimeFormatter"
    }

    private val formatters = formats.map(::createFormatter)

    abstract fun createFormatter(pattern: String): FORMATTER

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
        } ?: throw JsonEncodingException("Value can't be null")
    }

    abstract fun fromJsonInner(value: String, dateTimeFormatter: FORMATTER): T

    abstract fun toJsonInner(value: T?, dateTimeFormatter: FORMATTER): String?

}
