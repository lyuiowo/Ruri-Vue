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

@RestController
@RequestMapping("api/chapter")
class ChapterController(
    private val chapterService: ChapterService
) {

    private val loggerManager = LoggerManager

    @GetMapping("/search")
    fun searchChapter(
        request: HttpServletRequest,
        @RequestParam nid: Int,
        @RequestParam cid: Int? = null,
        @RequestParam title: String? = null
    ): ApiManager<out Any>? {
        val existingChapter = if (cid != null) {
            if (title != null) {
                chapterService.findChapterByNovelIDAndTitle(nid, title)
            } else {
                chapterService.findChapterByID(cid, nid)
            }
        } else {
            chapterService.findChapterByNovelID(nid)
        }
        loggerManager.getLogger(request, 200)
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
        @RequestParam content: String,
        @RequestParam status: Int,
        @RequestParam token: String
    ): ApiManager<List<Chapter>?> {
        val existingChapter = chapterService.updateChapter(id, title, content, status, token)
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