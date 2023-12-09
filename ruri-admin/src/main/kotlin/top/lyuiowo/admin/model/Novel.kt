package top.lyuiowo.admin.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import top.lyuiowo.admin.repository.ChapterRepository
import top.lyuiowo.admin.service.ChapterService
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Novel (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val novelID: Int ?= null,
    var name: String,
    var description: String,
    @ManyToOne @JoinColumn(name = "authorID") @get:JsonIgnore
    val author: User,
    val createAt: LocalDateTime,
    var updateAt: LocalDateTime,
    @get:JsonIgnore
    var isHidden: Boolean = false,
    var totalChapterNum: Int ?= null
) {

    @Transient
    @JsonProperty("authorName")
    fun getAuthorName(): String {
        return author.username
    }
}
