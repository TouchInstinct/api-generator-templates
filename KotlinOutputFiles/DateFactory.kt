import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class DateFactory : JsonAdapter.Factory {

    override fun create(type: Type, annotations: Set<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        val typeName: String = when(type) {
            is Class<*> -> type.canonicalName ?: type.toString()
            else -> type.toString()
        }

        return getCalendarAdapter(typeName, getPatterns(annotations))
    }

    private fun getPatterns(annotations: Set<out Annotation>) = annotations
            .mapNotNull { it as? Format }
            .firstOrNull()
            ?.patterns
            ?: throw IllegalArgumentException("You should use Format annotation for DateTime and LocalDate fields")

}
