import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.lang.reflect.Type

class DateFactory : JsonAdapter.Factory {

    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        val dateTimeName = DateTime::class.java.typeName
        val localDateName = LocalDate::class.java.typeName
        val typeName = type.typeName

        return if (typeName == dateTimeName || typeName == localDateName) {
            val formatAnnotation = annotations
                    .map { it as? Format }
                    .firstOrNull()
                    ?: throw IllegalArgumentException("You should use Format annotation for DateTime and LocalDate fields")

            if (typeName == dateTimeName) DateTimeJsonAdapter(formatAnnotation.patterns)
            else LocalDateJsonAdapter(formatAnnotation.patterns)

        } else null
    }

}
