package top.lyuiowo.admin.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.lyuiowo.admin.model.Chapter
import top.lyuiowo.admin.service.ChapterService
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.LoggerManager
import top.lyuiowo.admin.utils.ResultCode
import top.lyuiowo.admin.utils.TokenManager
import java.util.UUID

@RestController
@RequestMapping("api/chapter")
class ChapterController(
    private val chapterService: ChapterService
) {

    private val loggerManager = LoggerManager

    @GetMapping("/search")
    fun searchChapter(
        request: HttpServletRequest,
        @RequestParam id: Int,
        @RequestParam key: Int
    ): ApiManager<out List<Chapter?>?> {
        val existingChapter = when (key) {
            1 -> chapterService.getInfoByChapterID(id)
            2 -> chapterService.getInfoByNovelID(id)
            else -> ApiManager(ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, emptyList())
        }
        loggerManager.getLogger(request, existingChapter.code)

        return existingChapter
    }

    @PostMapping("/create")
    fun createChapter(
        request: HttpServletRequest,
        @RequestParam id: Int,
        @RequestParam title: String,
        @RequestParam content: String,
        @RequestParam token: String
    ): ApiManager<List<Chapter>> {
        val existingChapter = chapterService.createChapter(id, title, content, token)
        loggerManager.getLogger(request, existingChapter.code)

        return existingChapter
    }

    @PostMapping("/update")
    fun updateChapter(
        request: HttpServletRequest,
        @RequestParam id: Int,
        @RequestParam title: String,
        @RequestParam content: String
    ): ApiManager<List<Chapter>?> {
        val existingChapter = chapterService.updateChapter(id, title, content)
        loggerManager.getLogger(request, existingChapter.code)

        return existingChapter
    }

    @PostMapping("/delete")
    fun deleteChapter(
        request: HttpServletRequest,
        @RequestParam id: Int,
        @RequestParam token: String
    ): ApiManager<List<Chapter>> {
        val existingChapter = chapterService.deleteChapter(id, token)
        loggerManager.getLogger(request, existingChapter.code)

        return existingChapter
    }
}