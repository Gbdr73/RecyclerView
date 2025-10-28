package otus.gpb.recyclerview

data class ChatItem(
    val id: Int,
    val name: String,
    val status: String,
    val message: String,
    val counter: Int,
    val verified: Boolean,
    val mute: Boolean,
    val scam: Boolean,
    val delivered: Boolean,
    val read: Boolean,
    val time: String,
)
