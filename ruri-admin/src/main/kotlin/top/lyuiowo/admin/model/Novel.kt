package top.lyuiowo.admin.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDateTime

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
