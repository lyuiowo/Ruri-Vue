package top.lyuiowo.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import top.lyuiowo.admin.model.Novel
import java.util.UUID

interface NovelRepository : JpaRepository<Novel, String> {
    override fun findAll(): MutableList<Novel>

    @Query("select n from Novel n where n.name like %:novelName%")
    fun findNovelByName(@Param("novelName") novelName: String): List<Novel>?

    @Query("select n from Novel n join n.author a where a.username like %:username%")
    fun findByAuthorUsername(username: String): List<Novel>?
}