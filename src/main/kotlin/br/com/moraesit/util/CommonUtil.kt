package br.com.moraesit.util

import org.apache.commons.lang3.time.StopWatch
import java.util.*
import java.util.concurrent.TimeUnit

class CommonUtil {
    companion object {
        private val stopWatch = StopWatch()
        fun delay(ms: Long) {
            try {
                TimeUnit.MILLISECONDS.sleep(ms)
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
            }
        }

        fun transform(s: String): String {
            delay(500)
            return s.uppercase(Locale.getDefault())
        }

        fun startTimer() = stopWatch.start()

        fun timeTaken() {
            stopWatch.stop()
            println("Total Time Taken: ${stopWatch.time}")
        }

        fun stopWatchReset() = stopWatch.reset()

        fun noOfCores(): Int = Runtime.getRuntime().availableProcessors()
    }
}