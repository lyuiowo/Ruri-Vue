package top.lyuiowo.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import top.lyuiowo.admin.model.Chapter

interface ChapterRepository: JpaRepository<Chapter, String> {
    fun findByChapterID(chapterID: String): Chapter?
}