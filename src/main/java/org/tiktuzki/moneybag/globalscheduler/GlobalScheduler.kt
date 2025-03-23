package org.tiktuzki.moneybag.globalscheduler

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import mu.KLogging
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

data class IntervalTask(
    val name: String,
    val interval: Duration,
    val task: Consumer<MutableMap<String, Any>>,
    val kwargs: MutableMap<String, Any> = mutableMapOf(),
    var lastExecuted: Long? = null
)

@Component
class GlobalScheduler {
    val taskQueue = LinkedBlockingQueue<IntervalTask>()
    val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    @PostConstruct
    fun init() {
        executor.scheduleAtFixedRate(this::execute, 0, 10, TimeUnit.SECONDS)
        println("GlobalScheduler initialized")
    }

    fun addTask(
        taskName: String,
        interval: Duration,
        kwargs: MutableMap<String, Any> = mutableMapOf(),
        task: Consumer<MutableMap<String, Any>>
    ) {
        taskQueue.add(IntervalTask(taskName, interval, task, kwargs))
    }

    fun removeTask(taskName: String) {
        logger.info { "Removing task $taskName" }
        taskQueue.removeIf { it.name == taskName }
    }

    fun execute() {
        val now = System.currentTimeMillis()
        for (taskConfig in taskQueue) {
            if (taskConfig.lastExecuted == null
                || now >= taskConfig.lastExecuted!! + taskConfig.interval.toMillis()
            ) {
                try {
                    logger.info { "Executing task ${taskConfig.name}, interval ${taskConfig.interval.toMillis()} : ${taskConfig.lastExecuted} : ${now}" }
                    taskConfig.task.accept(taskConfig.kwargs)
                } catch (e: Exception) {
                    logger.error(e.message, e)
                }
                taskConfig.lastExecuted = now
            }
        }
    }

    @PreDestroy
    fun destroy() {
        executor.shutdown()
    }


    companion object : KLogging()
}