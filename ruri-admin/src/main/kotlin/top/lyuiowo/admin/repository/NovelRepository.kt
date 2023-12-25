package top.lyuiowo.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import top.lyuiowo.admin.model.Novel
import java.util.UUID

interface NovelRepository : JpaRepository<Novel, String> {

    fun findNovelByNovelID(novelID: Int): Novel?

    @Query("select n from Novel n where n.name like %:novelName% and n.isHidden = false")
    fun findNovelByName(@Param("novelName") novelName: String): List<Novel>?

    @Query("select n from Novel n join n.author a where a.username like %:username% and n.isHidden = false")
    fun findByAuthorAndHiddenIsFalse(username: String): List<Novel>?

    @Query("select n from Novel n join n.author a where a.userID = :userID and n.isHidden = false")
    fun findBySelf(userID: UUID): List<Novel>?


}