package com.eatwell.foodHQ.service;

import com.eatwell.foodHQ.domain.Customer;
import com.eatwell.foodHQ.payload.CustomerDto;
import com.eatwell.foodHQ.payload.ReservationDto;
import com.eatwell.foodHQ.payload.paginationCustomResponse.CustomerPaginationResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CustomerService {

    public CustomerDto createACustomerForAReservation (CustomerDto customerDto, long reservationId);

    public CustomerDto createACustomer (CustomerDto customerDto);

    public List<CustomerDto> createCustomersForReservations (List<CustomerDto> customerDtos, List<Long> reservationIds);

    public CustomerDto updateCustomerByReservation (CustomerDto customerDto,Long customerId, Long reservationId);

    public CustomerDto fetchACustomerByReservation (long customerId, long reservationId);

    //public  List<CustomerDto> findByReservationId (long reservationIds);

    List<CustomerDto> findByReservationIdIn (List<Long> reservationIds);


    public List<CustomerDto> fetchWithPagination (int pageNo,int pageSize);

    public CustomerPaginationResponse customCustomerPg (int pageNo,int pageSize);

    public List<CustomerDto> sortedCustomers (String sortDir, String sortBy);

    public List<CustomerDto> fetchPainationAndSort (int pageNo, int pageSize, String sortDir,String sortBy);

    public void deleteCustomerByReservation (long reservationId, long customerId);
}
