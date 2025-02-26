package community.flock.workshop.app.note

import arrow.core.right
import com.ninjasquad.springmockk.MockkBean
import community.flock.workshop.api.generated.GetNotesByUserIdEndpoint
import community.flock.workshop.api.generated.PostUserEndpoint
import community.flock.workshop.app.environment.WithContainers
import community.flock.workshop.app.note.NoteMother.note
import community.flock.workshop.app.note.downstream.LiveNoteAdapter
import community.flock.workshop.app.note.upstream.NoteController
import community.flock.workshop.app.user.UserMother.USER_ID
import community.flock.workshop.app.user.UserMother.user
import community.flock.workshop.app.user.upstream.UserController
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.coEvery
import org.junit.Ignore
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@Ignore
@SpringBootTest(webEnvironment = RANDOM_PORT)
class NoteControllerTest : WithContainers() {
    @MockkBean
    private lateinit var noteAdapter: LiveNoteAdapter

    @Autowired
    private lateinit var userController: UserController

    @Autowired
    private lateinit var noteController: NoteController

    @Test
    fun testNoteController(): Unit =
        runBlocking {
            coEvery { noteAdapter.getNotesByUserId(any()) } returns listOf(note).right()

            val request = PostUserEndpoint.Request(body = user)

            userController.postUser(request)
            noteController
                .getNotesByUserId(GetNotesByUserIdEndpoint.Request(potentialUserId = USER_ID))
                .shouldBeTypeOf<GetNotesByUserIdEndpoint.Response200>()
                .body
                .first()
                .run {
                    id shouldBe "0ec33ba3-64a1-4f34-a33d-6cab4b43baeb"
                    title shouldBe "title"
                    description shouldBe "description"
                    email shouldBe USER_ID
                    user shouldBe "FirstName LastName"
                    done shouldBe true
                }
        }
}
