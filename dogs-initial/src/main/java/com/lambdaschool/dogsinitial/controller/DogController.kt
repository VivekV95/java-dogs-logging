package com.lambdaschool.dogsinitial.controller


import com.lambdaschool.dogsinitial.CheckDog
import com.lambdaschool.dogsinitial.model.Dog
import com.lambdaschool.dogsinitial.DogsinitialApplication
import com.lambdaschool.dogsinitial.exception.ResourceNotFoundException
import com.lambdaschool.dogsinitial.model.MessageDetail
import mu.KotlinLogging
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

@RestController
@RequestMapping("/dogs")
class DogController {

    companion object{
        private val logger = KotlinLogging.logger{}
    }

    @Autowired
    lateinit var rt: RabbitTemplate

    // localhost:8080/dogs/dogs
    val allDogs: ResponseEntity<*>
        @GetMapping(value = ["/dogs"], produces = ["application/json"])
        get() {
            logger.info { "dogs/dogs accessed" +
                    " on ${SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(Date())}" }
            val message = MessageDetail("/dogs/dogs accessed on" +
                    " ${Date()}", 7, false)
            rt.convertAndSend(DogsinitialApplication.QUEUE_NAME_HIGH, message)
            return ResponseEntity(DogsinitialApplication.ourDogList.dogList, HttpStatus.OK)
        }


    // localhost:8080/dogs/{id}
    @Throws(ResourceNotFoundException::class)
    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun getDogDetail(@PathVariable id: Long): ResponseEntity<*> {
        logger.info { "dogs/$id accessed on ${SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(Date())}" }
        val message = MessageDetail("/dogs/$id accessed on" +
                " ${Date()}", 2, true)
        rt.convertAndSend(DogsinitialApplication.QUEUE_NAME_LOW, message)
        val rtnDog = DogsinitialApplication.ourDogList.findDog(CheckDog
        { d -> d.id == id }) ?: throw ResourceNotFoundException("Dog with id $id not found")
        return ResponseEntity(rtnDog, HttpStatus.OK)
    }

    // localhost:8080/dogs/breeds/{breed}
    @GetMapping(value = ["/breeds/{breed}"], produces = ["application/json"])
    fun getDogBreeds(@PathVariable breed: String): ResponseEntity<*> {
        logger.info { "dogs/$breed accessed on" +
                " ${SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(Date())}" }
        val rtnDogs = DogsinitialApplication.ourDogList.findDogs(CheckDog { d -> d.breed?.toUpperCase() == breed.toUpperCase() })
        return ResponseEntity(rtnDogs, HttpStatus.OK)
    }

    @GetMapping(value = ["/dogtable"], produces = ["application/json"])
    fun displayDogTable(): ModelAndView {
        logger.info { "dogs/dogtable accessed on " +
                " ${SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(Date())}" }
        val mav = ModelAndView()
        mav.viewName = "dogs"
        mav.addObject("dogList", DogsinitialApplication.ourDogList.dogList.sortedBy { it.breed })

        return mav
    }

    @GetMapping(value = ["/apartmentdogtable"], produces = ["application/json"])
    fun displayApartmentDogTable(): ModelAndView {
        logger.info { "dogs/apartmentdogtable accessed on" +
                " ${SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(Date())}" }
        val mav = ModelAndView()
        mav.viewName = "dogs"
        mav.addObject("dogList",
                DogsinitialApplication.ourDogList.dogList.filter { it.isApartmentSuitable }.sortedBy { it.breed })

        return mav
    }
}