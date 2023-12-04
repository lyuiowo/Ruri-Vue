package top.lyuiowo.admin.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Transient
import java.sql.Timestamp

@Entity
data class Novel (
    @Id
    val novelID: String,
    val name: String,
    val description: String,
    @ManyToOne @JoinColumn(name = "authorID") @get:JsonIgnore
    val author: User,
    val createAt: Timestamp,
    var updateAt: Timestamp,
    @get:JsonIgnore
    var isHidden: Boolean = false,
) {
    @Transient
    @JsonProperty("authorName")
    fun getAuthorName(): String {
        return author.username
    }
}
