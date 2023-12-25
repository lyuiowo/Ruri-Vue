package top.lyuiowo.admin.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.io.Serializable
import java.time.LocalDateTime

@Entity
data class Chapter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val chapterID: Int? = null,
    val novelID: Int,
    var title: String,
    var content: String,
    val createAt: LocalDateTime,
    var updateAt: LocalDateTime,
    var status: Int,
    @get:JsonIgnore
    var isDeleted: Boolean = false
)
