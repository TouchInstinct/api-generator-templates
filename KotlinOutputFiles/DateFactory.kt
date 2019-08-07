import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.lang.reflect.Type

class DateFactory : JsonAdapter.Factory {

    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        return when (type.typeName) {
            DateTime::class.java.typeName -> DateTimeJsonAdapter(getPatterns(annotations))
            LocalDate::class.java.typeName -> LocalDateJsonAdapter(getPatterns(annotations))
            else -> null
        }
    }

    private fun getPatterns(annotations: MutableSet<out Annotation>) = annotations
            .map { it as? Format }
            .firstOrNull()
            ?.patterns
            ?: throw IllegalArgumentException("You should use Format annotation for DateTime and LocalDate fields")

}
