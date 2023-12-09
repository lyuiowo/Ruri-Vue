package top.lyuiowo.admin.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Chapter(
    @Id
    val chapterID: Int? = null,
    val novelID: Int,
    var title: String,
    var content: String,
    val createAt: LocalDateTime,
    var status: Int,
    @get:JsonIgnore
    var isDeleted: Boolean = false
)
