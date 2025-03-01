package community.flock.workshop.app.note

import com.ninjasquad.springmockk.MockkBean
import community.flock.workshop.app.environment.WithContainers
import community.flock.workshop.app.note.NoteMother.note
import community.flock.workshop.app.user.UserController
import community.flock.workshop.app.user.UserMother.USER_ID
import community.flock.workshop.app.user.UserMother.user
import io.kotest.common.runBlocking
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteControllerTest : WithContainers() {
    @MockkBean
    private lateinit var noteAdapter: NoteAdapter

    @Autowired
    private lateinit var userController: UserController

    @Autowired
    private lateinit var noteController: NoteController

    @Test
    fun testNoteController(): Unit =
        runBlocking {
            coEvery { noteAdapter.getNotesByUserId(any()) } returns listOf(note)

            userController.postUser(user)
            noteController.getNotesByUserId(USER_ID).shouldNotBeEmpty().first().run {
                id shouldBe "id"
                title shouldBe "title"
                description shouldBe "description"
                email shouldBe USER_ID
                user shouldBe "firstName lastName"
                done shouldBe true
            }
        }
}
