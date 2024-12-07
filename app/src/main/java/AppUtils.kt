object AppUtils {

    const val BASE_URL: String = "https://api.thecatapi.com/v1/images/"
    const val API_KEY: String =
        "live_jrTbny573SI6ma5HpGDrRaR9JmRlbiQ2rP6DkvEiwYPf0qmgjIycT8fdgTEExNEK"

    const val MAX_RETRIES: Long = 3L
    private const val INITIAL_BACKOFF: Long = 2000L

    fun getBackoffDelay(attempt: Long): Long =
        INITIAL_BACKOFF * (attempt + 1)


}
