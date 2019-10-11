import android.graphics.Color
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ColorAdapter {

    @ToJson
    fun toJson(@HexColor color: Int): String = "#${Integer.toHexString(color)}"

    @FromJson
    @HexColor
    fun fromJson(color: String): Int = Color.parseColor(color)

}
