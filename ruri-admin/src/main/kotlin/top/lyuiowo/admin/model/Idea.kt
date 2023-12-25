package top.lyuiowo.admin.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Idea(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val ideaID: Int? = null,
    @ManyToOne @JoinColumn(name = "authorID") @get:JsonIgnore
    val author: User,
    var title: String,
    var content: String,
    val createAt: LocalDateTime,
    var updateAt: LocalDateTime,
    var isHidden: Boolean
) {
    @Transient
    @JsonProperty("authorName")
    fun getAuthorName(): String {
        return author.username
    }
}
