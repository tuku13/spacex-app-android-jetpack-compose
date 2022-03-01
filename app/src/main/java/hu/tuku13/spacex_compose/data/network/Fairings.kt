package hu.tuku13.spacex_compose.data.network

data class Fairings(
    val recovered: Any? = Any(),
    val recovery_attempt: Any? = Any(),
    val reused: Any? = Any(),
    val ships: List<Any> = listOf()
)