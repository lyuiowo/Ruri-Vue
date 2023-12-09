package top.lyuiowo.admin.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.model.Chapter
import top.lyuiowo.admin.repository.ChapterRepository
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.ResultCode
import java.util.*

@Service
class ChapterService(
    private val chapterRepository: ChapterRepository
) {
    /**
     * 搜索对应章节信息
     * @param chapterID 章节 ID
     * @return 对应章节的信息
     */
    fun getInfoByChapterID(chapterID: Int): ApiManager<List<Chapter>?> {
        val existingChapter =  chapterRepository.findByChapterID(chapterID)

        if (existingChapter != null) {
            return ApiManager(
                ResultCode.SUCCESS.code,
                ResultCode.SUCCESS.msg,
                listOf(existingChapter)
            )
        }
        return ApiManager(ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, emptyList())
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
     * 更新章节信息
     * @param chapter 章节对象
     * @param title 标题
     * @param content 内容
     * @return 更新结果
     */
    fun updateChapter(chapterID: Int, title: String, content: String): ApiManager<List<Chapter>?> {
        val chapter = chapterRepository.findByChapterID(chapterID)
        if (chapter != null && !chapter.isDeleted) {
            chapter.title = title
            chapter.content = content
            val updatedChapter = chapterRepository.save(chapter)
            return ApiManager(
                ResultCode.SUCCESS.code, "更新成功", listOf(updatedChapter)
            )
        }
        return ApiManager(ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, emptyList())
    }

    /**
     * 删除章节内容
     * @param chapterID 章节 ID
     * @return 删除结果
     */
    fun deleteChapter(chapterID: Int): ApiManager<List<Chapter>> {
        val chapter = chapterRepository.findByChapterID(chapterID)
        if (chapter != null && !chapter.isDeleted) {
            chapter.isDeleted = true
            val deleteChapter = chapterRepository.save(chapter)
            return ApiManager(
                ResultCode.SUCCESS.code, "删除成功", listOf(deleteChapter)
            )
        }
        return ApiManager(ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, emptyList())
    }
}