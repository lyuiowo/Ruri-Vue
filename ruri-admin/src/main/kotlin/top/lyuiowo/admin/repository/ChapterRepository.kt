package top.lyuiowo.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import top.lyuiowo.admin.model.Chapter

interface ChapterRepository: JpaRepository<Chapter, Int> {
    fun findByChapterID(chapterID: Int): Chapter?
    fun findByNovelID(novelID: Int): List<Chapter?>?
}