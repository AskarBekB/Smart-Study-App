package dev.androidbroadcast.smartstudy.data.local

data class BookResponse(
    val items: List<BookItem> = emptyList()
)

data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String> = emptyList(),
    val imageLinks: ImageLinks? = null
)

data class ImageLinks(
    val thumbnail: String? = null
)
