package top.lyuiowo.admin.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.lyuiowo.admin.model.Novel
import top.lyuiowo.admin.service.NovelService
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.ResultCode

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
    fun getNovelByType(
        @RequestParam q: String
    ): ApiManager<List<Novel>> {
        val bookWithName = novelService.findNovelByName(q).result
        val bookWithAuthor = novelService.findNovelByAuthorName(q).result
        var bookResult = mutableListOf<Novel>()

        bookWithName?.let { bookResult.addAll(it) }
        bookWithAuthor?.let { bookResult.addAll(it) }

        bookResult = bookResult
            .distinctBy { it.novelID }
            .sortedBy { it.novelID }
            .toMutableList()

        return ApiManager(
            ResultCode.SUCCESS.code,
            ResultCode.SUCCESS.msg,
            bookResult
        )
    }
}