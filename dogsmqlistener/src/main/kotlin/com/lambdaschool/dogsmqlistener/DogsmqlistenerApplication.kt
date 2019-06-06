package com.lambdaschool.dogsmqlistener

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DogsmqlistenerApplication {

    companion object {
        const val EXCHANGE_NAME = "LambdaServer"
        const val QUEUE_NAME_LOW = "LowPriorityQueue"
        const val QUEUE_NAME_HIGH = "HighPriorityQueue"

        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<DogsmqlistenerApplication>(*args)
        }
    }

    @Bean
    fun appExchange(): TopicExchange {
        return TopicExchange(EXCHANGE_NAME)
    }

    @Bean
    fun appQueueLow(): Queue {
        return Queue(QUEUE_NAME_LOW)
    }

    @Bean
    fun declareBindingLow(): Binding {
        return BindingBuilder.bind(appQueueLow()).to(appExchange()).with(QUEUE_NAME_LOW)
    }

    @Bean
    fun appQueueHigh(): Queue {
        return Queue(QUEUE_NAME_HIGH)
    }

    @Bean
    fun declareBindingHigh(): Binding {
        return BindingBuilder.bind(appQueueHigh()).to(appExchange()).with(QUEUE_NAME_HIGH)
    }

    @Bean
    fun produceJackson2MessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }
}


