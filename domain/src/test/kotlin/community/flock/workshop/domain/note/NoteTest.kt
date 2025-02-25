package community.flock.workshop.domain.note

import community.flock.workshop.domain.common.component1
import community.flock.workshop.domain.note.model.Description
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.note.model.Title
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class NoteTest {
    @Test
    fun testId() {
        val (id) = Note.Id("c6d40eb4-f582-42e4-8b57-ff461186ea32").shouldBeRight()

        "$id" shouldBe "c6d40eb4-f582-42e4-8b57-ff461186ea32"
    }

    @Test
    fun testTitle() {
        val (title) = Title("Some Title").shouldBeRight()

        title shouldBe "Some Title"
    }

    @Test
    fun testDescription() {
        val (description) = Description("Some Description").shouldBeRight()

        description shouldBe "Some Description"
    }
}
