package top.lyuiowo.admin.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.lyuiowo.admin.model.Novel
import top.lyuiowo.admin.service.NovelService
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.LoggerManager

@RestController
@RequestMapping("api/novel")
class NovelController (
    private val novelService: NovelService
) {

    private val loggerManager = LoggerManager

    @GetMapping("/search")
    fun search(
        request: HttpServletRequest,
        @RequestParam q: String
    ): ApiManager<List<Novel>> {
        val existingNovelList = novelService.findAllNovel(q)
        loggerManager.getLogger(request, existingNovelList.code)

        return existingNovelList
    }

    @GetMapping("/searchMyShelf")
    fun searchMyShelf(
        request: HttpServletRequest,
        @RequestParam token: String
    ): ApiManager<List<Novel>> {
        val existingNovelList = novelService.findMyShelf(token)
        loggerManager.getLogger(request, existingNovelList.code)

        return existingNovelList
    }

    @PostMapping("/create")
    fun createNovel(
        request: HttpServletRequest,
        @RequestParam token: String,
        @RequestParam name: String,
        @RequestParam desc: String
    ): ApiManager<List<Novel>?> {
        val existingNovel = novelService.createNovel(name, desc, token)
        loggerManager.getLogger(request, existingNovel.code)

        return existingNovel
    }

    @PostMapping("/update")
    fun updateNovel(
        request: HttpServletRequest,
        @RequestParam id: Int,
        @RequestParam token: String,
        @RequestParam name: String,
        @RequestParam desc: String
    ): ApiManager<List<Novel>?> {
        val  existingNovel = novelService.updateNovel(id, name, desc, token)
        loggerManager.getLogger(request, existingNovel.code)

        return existingNovel
    }

    @PostMapping("/delete")
    fun deleteNovel(
        request: HttpServletRequest,
        @RequestParam id: Int,
        @RequestParam token: String
    ): ApiManager<List<Novel>?> {
        val existingNovel = novelService.deleteNovel(id, token)
        loggerManager.getLogger(request, existingNovel.code)

        return existingNovel
    }
}