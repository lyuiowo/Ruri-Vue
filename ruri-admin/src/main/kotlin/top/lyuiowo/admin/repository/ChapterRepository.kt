package top.lyuiowo.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.RequestParam
import top.lyuiowo.admin.model.Chapter

interface ChapterRepository: JpaRepository<Chapter, Int> {
    fun findByChapterID(chapterID: Int): Chapter?
    fun findByNovelID(novelID: Int): List<Chapter?>?

    @Query("select c from Chapter c where c.novelID = :novelID and c.chapterID = :chapterID")
    fun findByChapterIDAndNovelID(@Param("chapterID") chapterID: Int, @Param("novelID") novelID: Int): Chapter?
}