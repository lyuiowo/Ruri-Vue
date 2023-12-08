package top.lyuiowo.admin.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.lyuiowo.admin.model.Novel
import top.lyuiowo.admin.service.NovelService
import top.lyuiowo.admin.service.UserService
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.ResultCode
import top.lyuiowo.admin.utils.TokenManager

@RestController
@RequestMapping("api/novel")
class NovelController (
    private val novelService: NovelService,
    private val userService: UserService
) {
    @GetMapping("/all")
    fun getAllBook(): ApiManager<List<Novel>> {
        return novelService.findAllNovel()
    }

    @GetMapping("/search")
    fun search(
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

    @GetMapping("/searchMyShelf")
    fun searchMyShelf(
        @RequestParam token: String
    ): ApiManager<List<Novel>> {
        val userID = TokenManager.extractUserIDFromToken(token)
        val username = userID?.let { userService.findUserByID(it) }?.result?.get(0)?.username
        val existingNovel = username?.let { novelService.findNovelByAuthorName(it) }?.result
        return ApiManager(
            ResultCode.SUCCESS.code,
            ResultCode.SUCCESS.msg,
            existingNovel
        )
    }
}