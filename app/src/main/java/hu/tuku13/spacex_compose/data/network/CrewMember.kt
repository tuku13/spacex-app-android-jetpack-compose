package hu.tuku13.spacex_compose.data.network

data class CrewMember(
    val agency: String = "",
    val id: String = "",
    val image: String = "",
    val launches: List<String> = listOf(),
    val name: String = "",
    val status: String = "",
    val wikipedia: String = ""
)