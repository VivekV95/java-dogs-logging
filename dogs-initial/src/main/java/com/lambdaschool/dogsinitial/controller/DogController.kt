package com.lambdaschool.dogsinitial.controller


import com.lambdaschool.dogsinitial.CheckDog
import com.lambdaschool.dogsinitial.model.Dog
import com.lambdaschool.dogsinitial.DogsinitialApplication
import com.lambdaschool.dogsinitial.exception.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/dogs")
class DogController {
    // localhost:8080/dogs/dogs
    val allDogs: ResponseEntity<*>
        @GetMapping(value = ["/dogs"], produces = ["application/json"])
        get() = ResponseEntity(DogsinitialApplication.ourDogList.dogList, HttpStatus.OK)


    // localhost:8080/dogs/{id}
    @Throws(ResourceNotFoundException::class)
    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun getDogDetail(@PathVariable id: Long): ResponseEntity<*> {
        val rtnDog = DogsinitialApplication.ourDogList.findDog(CheckDog
        { d -> d.id == id }) ?: throw ResourceNotFoundException("Dog with id $id not found")
        return ResponseEntity(rtnDog, HttpStatus.OK)
    }

    // localhost:8080/dogs/breeds/{breed}
    @GetMapping(value = ["/breeds/{breed}"], produces = ["application/json"])
    fun getDogBreeds(@PathVariable breed: String): ResponseEntity<*> {
//        val rtnDogs = DogsinitialApplication.getOurDogList().findDogs({ d -> d.getBreed().toUpperCase().equals(breed.toUpperCase()) })
        val rtnDogs = DogsinitialApplication.ourDogList.findDogs(CheckDog { d -> d.breed?.toUpperCase() == breed.toUpperCase() })
        return ResponseEntity(rtnDogs, HttpStatus.OK)
    }
    @GetMapping(value = ["/dogtable"], produces = ["application/json"])
    fun displayDogTable(): ModelAndView {
        val mav = ModelAndView()
        mav.viewName = "dogs"
        mav.addObject("dogList", DogsinitialApplication.ourDogList.dogList.sortedBy { it.breed })

        return mav
    }

    @GetMapping(value = ["/apartmentdogtable"], produces = ["application/json"])
    fun displayApartmentDogTable(): ModelAndView {
        val mav = ModelAndView()
        mav.viewName = "dogs"
        mav.addObject("dogList",
                DogsinitialApplication.ourDogList.dogList.filter { it.isApartmentSuitable }.sortedBy { it.breed })

        return mav
    }






}