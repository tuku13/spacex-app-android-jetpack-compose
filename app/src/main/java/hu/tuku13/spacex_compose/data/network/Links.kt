package hu.tuku13.spacex_compose.data.network

data class Links(
    val article: String? = "",
    val flickr: Flickr = Flickr(),
    val patch: Patch = Patch(),
    val presskit: Any? = Any(),
    val reddit: Reddit = Reddit(),
    val webcast: String? = "",
    val wikipedia: Any? = Any(),
    val youtube_id: Any? = Any()
)