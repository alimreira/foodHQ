package com.eatwell.foodHQ.controller;

import com.eatwell.foodHQ.payload.ReservationDto;
import com.eatwell.foodHQ.payload.paginationCustomResponse.ReservationPaginationResponse;
import com.eatwell.foodHQ.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/booking")
public class ReservationController {
    private ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create/reserve")
    public ResponseEntity<ReservationDto> makeReservation (@RequestBody ReservationDto reservationDto) {
        ReservationDto dto = reservationService.makeReservation(reservationDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/create/reservations")
    public ResponseEntity<List<ReservationDto>> makeReservations (@RequestBody List<ReservationDto> reservationDtoList) {
        List<ReservationDto> dto = reservationService.multipleReservations(reservationDtoList);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @PutMapping("/modify/reservation/{id}")
    public ResponseEntity<ReservationDto> updateReservation (@RequestBody ReservationDto reservationDto, @PathVariable(value = "id") long id) {
        ReservationDto dto = reservationService.updateReservation(reservationDto,id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/fetch/reservation/{id}")
    public ResponseEntity<ReservationDto> fetchAReservation (@PathVariable(value= "id") long id) {
        ReservationDto dto = reservationService.getReservationById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/fetch/reservations")
    //@PreAuthorize("hasAuthority('CUSTOMER_READ')")
    public ResponseEntity<List<ReservationDto>> fetchReservations () {
       List<ReservationDto> dto =  reservationService.getMultiReservations();
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("fetch/pages/reserve")
    public ResponseEntity<List<ReservationDto>> getWithPage (@RequestParam int pageNo,@RequestParam int pageSize) {
        List<ReservationDto> dto = reservationService.getWithPagination(pageNo, pageSize);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("fetch/page/reserve")
    public ResponseEntity<ReservationPaginationResponse> fetchReservationPages (@RequestParam int pageNo, @RequestParam int pageSize) {
        ReservationPaginationResponse response = reservationService.customResponse(pageNo,pageSize);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("fetch/reserve/{sortDir}/{sortBy}")
    public ResponseEntity<List<ReservationDto>> fetchSortedReservations (@PathVariable String sortDir,@PathVariable String sortBy) {
        List<ReservationDto> dto = reservationService.getWithSorting(sortDir,sortBy);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/fetch/reservers")
    public ResponseEntity<List<ReservationDto>> fetchWithPgAndSort (@RequestParam int pageNo,@RequestParam int pageSize,
                                                                    @RequestParam String sortDir, @RequestParam String sortBy){
        List<ReservationDto> dto = reservationService.paginatingAndSorting(pageNo,pageSize,sortDir,sortBy);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/reservation")
    public ResponseEntity<String> deleteReservation (@RequestParam long id) {
        String message ="Reservation is successfully deleted";
        reservationService.deleteAReservationById(id);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }



}
