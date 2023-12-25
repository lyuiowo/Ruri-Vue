package top.lyuiowo.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import top.lyuiowo.admin.model.Idea
interface IdeaRepository: JpaRepository<Idea, Int> {
    fun findByIdeaID(ideaID: Int): Idea?

    @Query("select i from Idea i join i.author a where a.username like %:username% and i.isHidden = false ")
    fun findByAuthorUsername(username: String): List<Idea>?

    fun findAllByIsHiddenIsTrue(): List<Idea>?
}