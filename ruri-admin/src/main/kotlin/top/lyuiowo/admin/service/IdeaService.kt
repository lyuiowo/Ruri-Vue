package top.lyuiowo.admin.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.model.Idea
import top.lyuiowo.admin.repository.IdeaRepository
import top.lyuiowo.admin.repository.UserRepository
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.ResultCode
import top.lyuiowo.admin.utils.TokenManager
import java.time.LocalDateTime
import java.util.*

@Service
class IdeaService(
    private val ideaRepository: IdeaRepository, private val userRepository: UserRepository
) {

    val notUser = "00000000-0000-0000-0000-000000000000"

    fun findAllIdea(): ApiManager<List<Idea>> {
        val existingIdeaList = ideaRepository.findAllByIsHiddenIsTrue()

        return ApiManager(
            ResultCode.SUCCESS.code, ResultCode.SUCCESS.msg, existingIdeaList
        )
    }

    /**
     * 根据 ID 查找灵感
     * @param ideaID 灵感 ID
     * @return 灵感详细
     */
    fun findIdeaByID(ideaID: Int): ApiManager<Idea?> {
        val existingIdea = ideaRepository.findByIdeaID(ideaID)
        return if (existingIdea != null) {
            ApiManager(ResultCode.SUCCESS.code, "查询成功", existingIdea)
        } else {
            ApiManager(ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, null)
        }
    }

    /**
     * 创建灵感
     * @param title 标题
     * @param content 内容
     * @param token 用户令牌
     * @return 创建结果
     */
    fun createIdea(title: String, content: String, hidden: Boolean, token: String): ApiManager<List<Idea>> {
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString(notUser)
        val author = userRepository.findByUserID(userID)
        val currentTime = LocalDateTime.now()

        return if (author != null) {
            val existingIdea = Idea(
                title = title, content = content, author = author, createAt = currentTime, updateAt = currentTime, isHidden = hidden
            )

            ideaRepository.save(existingIdea)
            ApiManager(ResultCode.SUCCESS.code, "创建成功", listOf(existingIdea))
        } else {
            ApiManager(ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, null)
        }
    }

    /**
     * 更新灵感
     * @param title 标题
     * @param content 内容
     * @param token 用户令牌
     * @param ideaID 灵感 ID
     * @return 更新结果
     */
    fun updateIdea(title: String, content: String, hidden: Boolean, token: String, ideaID: Int): ApiManager<Idea> {
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString(notUser)
        val currentTime = LocalDateTime.now()

        val existingIdea = ideaRepository.findByIdeaID(ideaID)

        if (existingIdea != null) {
            if (existingIdea.author.userID == userID) {
                existingIdea.updateAt = currentTime
                existingIdea.title = title
                existingIdea.content = content
                existingIdea.isHidden = hidden
                val newIdea = ideaRepository.save(existingIdea)
                return ApiManager(ResultCode.SUCCESS.code, "更新成功", newIdea)
            }
        }
        return ApiManager(ResultCode.COMMON_FAIL.code, "更新失败", null)
    }

    /**
     * 删除灵感
     * @param token 用户令牌
     * @param ideaID 灵感 ID
     * @return 删除结果
     */
    fun deleteIdea(ideaID: Int, token: String): ApiManager<Idea> {
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString(notUser)
        val existingIdea = ideaRepository.findByIdeaID(ideaID)

        if (existingIdea != null) {
            if (existingIdea.author.userID == userID) {
                ideaRepository.delete(existingIdea)
                return ApiManager(ResultCode.SUCCESS.code, "删除成功", existingIdea)
            }
        }
        return ApiManager(ResultCode.COMMON_FAIL.code, "删除失败", null)
    }

    /**
     * 通过 token 搜索个人灵感库内容
     * @param token 用户令牌
     * @return 灵感库信息
     */
    fun findMyShelf(token: String): ApiManager<List<Idea>> {
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString(notUser)
        val username = userRepository.findByUserID(userID)?.username
        val ideaList = username?.let { ideaRepository.findByAuthorUsername(it) }

        return ApiManager(
            ResultCode.SUCCESS.code, ResultCode.SUCCESS.msg, ideaList
        )
    }
}