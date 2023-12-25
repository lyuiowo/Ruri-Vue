package top.lyuiowo.admin.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.lyuiowo.admin.model.Idea
import top.lyuiowo.admin.service.IdeaService
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.LoggerManager

@RestController
@RequestMapping("api/idea")
class IdeaController(
    private val ideaService: IdeaService
) {

    private val loggerManager = LoggerManager

    @GetMapping("/search")
    fun searchIdea(
        request: HttpServletRequest,
        @RequestParam ideaID: Int
    ): ApiManager<Idea?> {
        val existingIdea = ideaService.findIdeaByID(ideaID)

        loggerManager.getLogger(request, existingIdea.code)
        return existingIdea
    }

    @GetMapping("/all")
    fun findAllIdea(
        request: HttpServletRequest
    ): ApiManager<List<Idea>> {
        val existingIdeaList = ideaService.findAllIdea()

        loggerManager.getLogger(request, existingIdeaList.code)
        return existingIdeaList
    }

    @GetMapping("/searchShelf")
    fun searchShelf(
        request: HttpServletRequest,
        @RequestParam token: String
    ): ApiManager<List<Idea>> {
        val existingIdea = ideaService.findMyShelf(token)
        loggerManager.getLogger(request, existingIdea.code)

        return existingIdea
    }

    @PostMapping("/create")
    fun createIdea(
        request: HttpServletRequest,
        @RequestParam title: String,
        @RequestParam content: String,
        @RequestParam hidden: Boolean,
        @RequestParam token: String
    ): ApiManager<List<Idea>> {
        val existingIdea = ideaService.createIdea(title, content, hidden, token)

        loggerManager.getLogger(request, existingIdea.code)
        return existingIdea
    }

    @PostMapping("/update")
    fun updateIdea(
        request: HttpServletRequest,
        @RequestParam token: String,
        @RequestParam content: String,
        @RequestParam title: String,
        @RequestParam hidden: Boolean,
        @RequestParam ideaID: Int
    ): ApiManager<Idea> {
        val existingIdea = ideaService.updateIdea(title, content, hidden, token, ideaID)

        loggerManager.getLogger(request, existingIdea.code)
        return existingIdea
    }

    @PostMapping("/delete")
    fun deleteIdea(
        request: HttpServletRequest,
        @RequestParam token: String,
        @RequestParam ideaID: Int
    ): ApiManager<Idea> {
        val existingIdea = ideaService.deleteIdea(ideaID, token)

        loggerManager.getLogger(request, existingIdea.code)
        return existingIdea
    }
}