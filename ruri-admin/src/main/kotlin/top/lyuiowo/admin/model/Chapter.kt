package top.lyuiowo.admin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Chapter(
    @Id
    val chapterID: String,
    val novelID: Int,
    val title: String,
    val content: String,
    val createAt: LocalDateTime
)
