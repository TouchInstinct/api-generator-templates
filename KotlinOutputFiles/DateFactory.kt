import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.lang.reflect.Type

class DateFactory : JsonAdapter.Factory {

    override fun create(type: Type, annotations: Set<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        val typeName = when(type) {
            is Class<*> -> type.canonicalName
            else -> type.toString()
        }

        return when (typeName) {
            DateTime::class.java.canonicalName -> DateTimeJsonAdapter(getPatterns(annotations))
            LocalDate::class.java.canonicalName -> LocalDateJsonAdapter(getPatterns(annotations))
            else -> null
        }
    }

    private fun getPatterns(annotations: Set<out Annotation>) = annotations
            .map { it as? Format }
            .firstOrNull()
            ?.patterns
            ?: throw IllegalArgumentException("You should use Format annotation for DateTime and LocalDate fields")

}
