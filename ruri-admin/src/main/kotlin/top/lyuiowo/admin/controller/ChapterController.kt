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

@RestController
@RequestMapping("api/chapter")
class ChapterController(
    private val chapterService: ChapterService
) {

    private val loggerManager = LoggerManager

    @GetMapping("/search")
    fun search(
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

    @PostMapping("/update")
    fun update(
        request: HttpServletRequest,
        @RequestParam id: Int,
        @RequestParam title: String,
        @RequestParam content: String
    ): ApiManager<List<Chapter>?> {
        val existingChapter = chapterService.updateChapter(id, title, content)
        loggerManager.getLogger(request, existingChapter.code)

        return existingChapter
    }
}