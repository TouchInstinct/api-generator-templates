import com.squareup.moshi.JsonQualifier

@JsonQualifier
@Target(AnnotationTarget.FIELD)
annotation class Format(val patterns: Array<String>)
