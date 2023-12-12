package top.lyuiowo.admin.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.model.Chapter
import top.lyuiowo.admin.repository.ChapterRepository
import top.lyuiowo.admin.repository.NovelRepository
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.ResultCode
import top.lyuiowo.admin.utils.TokenManager
import java.time.LocalDateTime
import java.util.*

@Service
class ChapterService(
    private val chapterRepository: ChapterRepository,
    private val novelRepository: NovelRepository
) {

    /**
     * 搜索对应章节信息
     * @param chapterID 章节 ID
     * @return 对应章节的信息
     */
    fun getInfoByChapterID(chapterID: Int, novelID: Int): ApiManager<Chapter>? {
        val existingChapter =  chapterRepository.findByChapterIDAndNovelID(chapterID, novelID)

        if (existingChapter != null) {
            return ApiManager(
                ResultCode.SUCCESS.code,
                ResultCode.SUCCESS.msg,
                existingChapter
            )
        }
        return ApiManager(ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, null)
    }

    /**
     * 搜索一本小说下的全部章节信息
     * @param novelID 小说 ID
     * @return 同本小说下全部章节信息
     */
    fun getInfoByNovelID(novelID: Int): ApiManager<List<Chapter?>> {
        val existingChapter = chapterRepository.findByNovelID(novelID)

        return ApiManager(
            ResultCode.SUCCESS.code,
            ResultCode.SUCCESS.msg,
            existingChapter
        )
    }

    /**
     *
     */
    fun getInfoByTitle(novelID: Int, title: String): ApiManager<Chapter>? {
        val existingChapter = chapterRepository.findByNovelIDAndTitle(novelID, title)

        return ApiManager(
            ResultCode.SUCCESS.code,
            ResultCode.SUCCESS.msg,
            existingChapter
        )
    }

    fun createChapter(novelID: Int, title: String, content: String, token: String): ApiManager<List<Chapter>> {
        val userID = TokenManager.extractUserIDFromToken(token)
        val existingNovel = novelRepository.findNovelByNovelID(novelID)
        val currentTime = LocalDateTime.now()
        if (userID != null && existingNovel != null) {
            val newChapter = Chapter(
                novelID = novelID,
                title = title,
                content = content,
                createAt = currentTime,
                status = 1,
                isDeleted = false
            )

            return ApiManager(ResultCode.SUCCESS.code, "创建成功", listOf(chapterRepository.save(newChapter)))
        }

        return ApiManager(ResultCode.INVALID_USER.code, ResultCode.INVALID_USER.msg, emptyList())
    }

    /**
     * 更新章节信息
     * @param chapter 章节对象
     * @param title 标题
     * @param content 内容
     * @return 更新结果
     */
    fun updateChapter(chapterID: Int, title: String, content: String, status: Int): ApiManager<List<Chapter>?> {
        val chapter = chapterRepository.findByChapterID(chapterID)
        if (chapter != null && !chapter.isDeleted) {
            chapter.title = title
            chapter.content = content

            if (status != 0) {
                chapter.status = status
            }

            val updatedChapter = chapterRepository.save(chapter)
            return ApiManager(
                ResultCode.SUCCESS.code, "更新成功", listOf(updatedChapter)
            )
        }
        return ApiManager(ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, emptyList())
    }

    /**
     * 删除章节内容 - 逻辑删除
     * @param chapterID 章节 ID
     * @return 删除结果
     */
    fun deleteChapter(chapterID: Int, token: String): ApiManager<List<Chapter>> {
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString("")
        val chapter = chapterRepository.findByChapterID(chapterID)
        if (chapter != null && !chapter.isDeleted) {
            val author = novelRepository.findNovelByNovelID(chapter.novelID)?.author?.userID
            if (author == userID) {
                chapter.isDeleted = true
                val deleteChapter = chapterRepository.save(chapter)
                return ApiManager(
                    ResultCode.SUCCESS.code, "删除成功", listOf(deleteChapter)
                )
            }
        }
        return ApiManager(ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, emptyList())
    }

    fun removeChapter(chapterID: Int, token: String): ApiManager<List<Chapter>> {
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString("")
        val chapter = chapterRepository.findByChapterID(chapterID)
        if (chapter != null) {
            val author = novelRepository.findNovelByNovelID(chapter.novelID)?.author?.userID
            if (author != null && author == userID) {
                chapterRepository.delete(chapter)
                return ApiManager(ResultCode.SUCCESS.code, "删除成功", emptyList())
            }
        }
        return ApiManager(ResultCode.COMMON_FAIL.code, "删除失败", emptyList())
    }
}