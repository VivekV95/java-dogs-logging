package com.lambdaschool.dogsmqlistener.Services

import com.lambdaschool.dogsmqlistener.DogsmqlistenerApplication
import com.lambdaschool.dogsmqlistener.Models.MessageDetail
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MessageListener {

    @RabbitListener(queues = [DogsmqlistenerApplication.QUEUE_NAME_HIGH])
    fun receiveHighMessage(msg: MessageDetail) {
        println("Received High Queue message ${msg.toString()}")
    }

    @RabbitListener(queues = [DogsmqlistenerApplication.QUEUE_NAME_LOW])
    fun receiveLowMessage(msg: MessageDetail) {
        println("Received Low  Queue message ${msg.toString()}")
    }
}