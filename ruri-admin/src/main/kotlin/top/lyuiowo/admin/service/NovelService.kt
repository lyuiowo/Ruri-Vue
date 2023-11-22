package top.lyuiowo.admin.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.model.Novel
import top.lyuiowo.admin.repository.NovelRepository
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.ResultCode

@Service
class NovelService(
    private val novelRepository: NovelRepository
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

    fun findAllNovel(): ApiManager<List<Novel>> {
        val NovelList = novelRepository.findAll()
        return ApiManager(
            ResultCode.SUCCESS.code,
            "查询图书成功",
            NovelList
        )
    }
}