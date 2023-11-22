package top.lyuiowo.admin.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.lyuiowo.admin.model.Novel
import top.lyuiowo.admin.service.NovelService
import top.lyuiowo.admin.utils.ApiManager

@RestController
@RequestMapping("api/novel")
class NovelController (
    private val novelService: NovelService
) {
    @GetMapping("/all")
    fun getAllBook(): ApiManager<List<Novel>> {
        return novelService.findAllNovel()
    }

    @GetMapping("/search")
    fun getNovelByName(
        @RequestParam novelName: String
    ): ApiManager<List<Novel>> {
        return novelService.findNovelByName(novelName)
    }

    @GetMapping("/searchAuthor")
    fun getNovelByAuthor(
        @RequestParam authorName: String
    ): ApiManager<List<Novel>> {
        return novelService.findNovelByAuthorName(authorName)
    }
}