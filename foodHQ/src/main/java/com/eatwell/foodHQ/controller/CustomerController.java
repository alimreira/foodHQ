package com.eatwell.foodHQ.controller;

import com.eatwell.foodHQ.domain.Customer;
import com.eatwell.foodHQ.exception.ResourceNotFoundException;
import com.eatwell.foodHQ.payload.CustomerDto;
import com.eatwell.foodHQ.payload.ReservationDto;
import com.eatwell.foodHQ.payload.paginationCustomResponse.CustomerPaginationResponse;
import com.eatwell.foodHQ.payload.paginationCustomResponse.ReservationPaginationResponse;
import com.eatwell.foodHQ.service.CustomerService;
import com.eatwell.foodHQ.service.serviceImpl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api/booker")
public class CustomerController {

    private CustomerServiceImpl customerService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer/create")
    public ResponseEntity<CustomerDto> createCustomerForReservation (@RequestBody CustomerDto customerDto,@RequestParam long reservationId) {
        CustomerDto toHandle = customerService.createACustomerForAReservation(customerDto,reservationId);
        return new ResponseEntity<>(toHandle, HttpStatus.CREATED);
    }


    @PostMapping("/customers/create")
    public ResponseEntity<List<CustomerDto>> createCustomersForReservationId (@RequestBody List<CustomerDto> dtoList, @RequestBody List<Long> reservationIds){
        List<CustomerDto> dto = customerService.createCustomersForReservations(dtoList,reservationIds);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }


    @PutMapping("/customers/modify/{customerId}/{reservationId}")
    public ResponseEntity<CustomerDto> modifyCustomer (@RequestBody CustomerDto customerDto,
                                                       @PathVariable(value = "customerId") Long customerId,
                                                       @PathVariable(value="reservationId") Long reservationId){
        CustomerDto dto = customerService.updateCustomerByReservation(customerDto,customerId,reservationId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/customer/fetch")
    public ResponseEntity<CustomerDto> fetchACustomerWithReservationId (@RequestParam long customerId,
                                                                        @RequestParam long reservationId) {
        CustomerDto dto = customerService.fetchACustomerByReservation(customerId,reservationId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

//    @GetMapping("/customers/reservation")
//    public ResponseEntity<List<CustomerDto>> fetchCustomersByReservationId (@RequestParam long reservationId) {
//        List<CustomerDto> customerDtos = customerService.findByReservationId(reservationId);
//        return new ResponseEntity<>(customerDtos,HttpStatus.OK);
//    }

    @GetMapping("/customers/reservation")
    public ResponseEntity<List<CustomerDto>> fetchCustomersByReservationIds (@RequestBody List<Long> reservationIds){
        List<CustomerDto> reserve = customerService.findByReservationIdIn(reservationIds);
        return new ResponseEntity<>(reserve,HttpStatus.OK);
    }

    @GetMapping("/customers/pages/{pageNo}/{pageSize}")
    public ResponseEntity<List<CustomerDto>> fetchCustomerPages (@PathVariable(value ="pageNo") int pageNo,
                                                                 @PathVariable(value="pageSize") int pageSize) {
        List<CustomerDto> dto = customerService.fetchWithPagination(pageNo,pageSize);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/customers/custom")
    public ResponseEntity<CustomerPaginationResponse> customeResponse (@RequestParam int pageNo,@RequestParam int pageSize) {
        CustomerPaginationResponse cp = customerService.customCustomerPg(pageNo,pageSize);
        return new ResponseEntity<>(cp,HttpStatus.OK);

    }

    @GetMapping("/customers/sorted/{sortDir}/{sortBy}")
    public ResponseEntity<List<CustomerDto>> fetchSortedCustomers (@PathVariable String sortDir, @PathVariable String sortBy) {
       List<CustomerDto> dto =  customerService.sortedCustomers(sortDir,sortBy);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/customers/pages")
    public ResponseEntity<List<CustomerDto>> fetchPageSort (@RequestParam int pageNo, @RequestParam int pageSize,@RequestParam String sortDir, @RequestParam String sortBy){
       List<CustomerDto> customers =  customerService.fetchPainationAndSort(pageNo,pageSize,sortDir,sortBy);
       return new ResponseEntity<>(customers,HttpStatus.OK);
    }

    @DeleteMapping("/customers/delete")
    public ResponseEntity<String> deleteCustomer (@RequestParam long customerId, @RequestParam long reservationId){
        String message ="Customer with reservation " + reservationId + " is successfully deleted";
        customerService.deleteCustomerByReservation(reservationId,customerId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }




}
