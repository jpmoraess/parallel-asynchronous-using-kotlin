import br.com.moraesit.parallelstreams.stringTransform
import br.com.moraesit.parallelstreams.stringTransform_parellel
import br.com.moraesit.util.CommonUtil.Companion.startTimer
import br.com.moraesit.util.CommonUtil.Companion.timeTaken
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ParallelStreamsExampleTest {

    @Test
    fun stringTransformTest() {
        //given
        val names = listOf("João", "Pedro", "Moraes", "Santos")

        //when
        startTimer()
        val resultList = stringTransform(names)
        timeTaken()

        //then
        assertEquals(4, resultList.size)
        resultList.forEach { assertTrue(it.contains("-")) }
    }

    @Test
    fun stringTransformParallelTest() {
        //given
        val names = listOf("João", "Pedro", "Moraes", "Santos")

        //when
        startTimer()
        val resultList = stringTransform_parellel(names)
        timeTaken()

        //then
        assertEquals(4, resultList.size)
        resultList.forEach { assertTrue(it.contains("-")) }
    }
}