import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MuscleGroup (
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
)