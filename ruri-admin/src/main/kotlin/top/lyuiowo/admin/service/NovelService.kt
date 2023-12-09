package top.lyuiowo.admin.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.model.Novel
import top.lyuiowo.admin.model.User
import top.lyuiowo.admin.repository.NovelRepository
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.ResultCode
import top.lyuiowo.admin.utils.TokenManager
import java.time.LocalDateTime
import java.util.UUID

@Service
class NovelService(
    private val novelRepository: NovelRepository,
    private val userService: UserService
) {
    /**
     * @param novelName 小说名字
     * @return 图书信息
     */
    fun findNovelByName(novelName: String): ApiManager<List<Novel>> {
        val existingNovel = novelRepository.findNovelByName("%$novelName%")
        if (existingNovel != null) {
            return ApiManager(
                ResultCode.SUCCESS.code,
                "查询图书成功",
                existingNovel
            )
        }
        return ApiManager(
            ResultCode.COMMON_FAIL.code,
            "查询失败",
            emptyList()
        )
    }

    /**
     * @param authorName 作者名字
     * @return 图书信息
     */
    fun findNovelByAuthorName(authorName: String): ApiManager<List<Novel>> {
        val existingNovel = novelRepository.findByAuthorUsername("%$authorName%")
        if (existingNovel != null) {
            return ApiManager(
                ResultCode.SUCCESS.code,
                "查询图书成功",
                existingNovel
            )
        }
        return ApiManager(
            ResultCode.COMMON_FAIL.code,
            "查询失败",
            emptyList()
        )
    }

    /**
     * 根据关键字搜索全部相关小说
     * @param value 搜索值
     * @return 搜索结果
     */
    fun findAllNovel(value: String): ApiManager<List<Novel>> {
        val bookWithName = findNovelByName(value).result
        val bookWithAuthor = findNovelByAuthorName(value).result
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

    /**
     * 搜索用户名下的小说
     * @param token 用户访问令牌
     * @return 搜索结果
     */
    fun findMyShelf(token: String): ApiManager<List<Novel>> {
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString("")
        val username = userService.findUserByID(userID).result?.get(0)?.username
        val novelList = if (username != null) {
            findNovelByAuthorName(username).result!!
        } else {
            emptyList()
        }

        return ApiManager(
            ResultCode.SUCCESS.code,
            ResultCode.SUCCESS.msg,
            novelList
        )
    }

    /**
     * 创建小说书名
     * @param novelName 小说书名
     * @param novelDesc 小说简介
     * @param token 用户访问令牌
     * @return 创建结果
     */
    fun createNovel(novelName: String, novelDesc: String, token: String): ApiManager<List<Novel>?> {
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString("")
        val author = userService.findUserByID(userID).result?.get(0)
        val currentTime = LocalDateTime.now()
        return if (author != null) {
            val newNovel = Novel(
                name = novelName,
                description = novelDesc,
                createAt = currentTime,
                author = author,
                updateAt = currentTime
            )
            ApiManager(ResultCode.SUCCESS.code, "创建成功", listOf(novelRepository.save(newNovel)))
        } else {
            ApiManager(ResultCode.COMMON_FAIL.code, "创建失败", emptyList())
        }
    }

    /**
     * 更新小说基本信息
     * @param novelID 小说 ID
     * @param novelName 小说书名
     * @param novelDesc 小说介绍
     * @return 更新结果
     */
    fun updateNovel(novelID: Int, novelName: String, novelDesc: String, token: String): ApiManager<List<Novel>?> {

        val existingNovel = novelRepository.findNovelByNovelID(novelID)
        val currentTime = LocalDateTime.now()
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString("")

        if (existingNovel != null) {
            if (existingNovel.author.userID == userID) {
                if (!existingNovel.isHidden) {
                    existingNovel.updateAt = currentTime
                    existingNovel.description = novelDesc
                    existingNovel.name = novelName
                    val newNovel = novelRepository.save(existingNovel)
                    return ApiManager(ResultCode.SUCCESS.code, "更新成功", listOf(newNovel))
                }
            }
        }

        return ApiManager(ResultCode.SUCCESS.code, "更新失败", emptyList())
    }

    /**
     * 删除小说 - 逻辑删除
     * @param novelID 小说 ID
     * @param token 用户访问令牌
     * @return 删除信息
     */
    fun deleteNovel(novelID: Int, token: String): ApiManager<List<Novel>?> {
        val existingNovel = novelRepository.findNovelByNovelID(novelID)
        val currentTime = LocalDateTime.now()
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString("")

        if (existingNovel != null) {
            if (existingNovel.author.userID == userID) {
                if (!existingNovel.isHidden) {
                    existingNovel.updateAt = currentTime
                    existingNovel.isHidden = true
                    val newNovel = novelRepository.save(existingNovel)
                    return ApiManager(ResultCode.SUCCESS.code, "删除成功", listOf(newNovel))
                }
            }
        }

        return ApiManager(ResultCode.SUCCESS.code, "删除失败", emptyList())
    }
}