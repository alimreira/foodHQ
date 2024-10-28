package com.eatwell.foodHQ.service.serviceImpl;

import com.eatwell.foodHQ.domain.Customer;
import com.eatwell.foodHQ.domain.Reservation;
import com.eatwell.foodHQ.exception.FoodHqValidationException;
import com.eatwell.foodHQ.exception.ResourceNotFoundException;
import com.eatwell.foodHQ.payload.CustomerDto;
import com.eatwell.foodHQ.payload.paginationCustomResponse.CustomerPaginationResponse;
import com.eatwell.foodHQ.repository.CustomerRepository;
import com.eatwell.foodHQ.repository.ReservationRepository;
import com.eatwell.foodHQ.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@Component
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private ReservationRepository reservationRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ReservationRepository reservationRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CustomerDto createACustomerForAReservation(CustomerDto customerDto, long reservationId) {
       Customer customer = modelMapper.map(customerDto,Customer.class);
       Reservation reserve =reservationRepository.findById(reservationId).orElseThrow(()->new ResourceNotFoundException("Reservation","id",reservationId));
       if(customer.getReservation() != null){
           throw new IllegalStateException();
       }
        customer.setReservation(reserve);
        Customer saved = customerRepository.save(customer);
       CustomerDto dto = modelMapper.map(saved,CustomerDto.class);
        return dto;
    }

    @Override
    public CustomerDto createACustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto,Customer.class);
        customerRepository.save(customer);
        CustomerDto dto = modelMapper.map(customer,CustomerDto.class);
        return dto;
    }

    @Override
    public List<CustomerDto> createCustomersForReservations(List<CustomerDto> customerDtos, List<Long> reservationIds) {
         List<Customer> customers = customerDtos.stream().map((customerDto)->modelMapper.map(customerDto,Customer.class)).collect(Collectors.toList());
        reservationIds.stream().forEach((id)-> {
            Reservation reserve = reservationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Reservation","id",id));
                customers.stream().forEach((customer)-> customer.setReservation(reserve));
            });
        List<Customer> customers1 = customerRepository.saveAll(customers);
        List<CustomerDto> dto = customers1.stream().map((customer)-> modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList());
        return dto;
    }

    @Override
    public CustomerDto updateCustomerByReservation(CustomerDto customerDto, Long customerId, Long reservationId) {
       Customer customer =  customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer","id",customerId));
        Reservation reservation =reservationRepository.findById(reservationId).orElseThrow(()->new ResourceNotFoundException("Reservation","id",reservationId));
        if(!customer.getReservation().equals(reservation)){
            throw new FoodHqValidationException(HttpStatus.BAD_REQUEST,"Reservation does not belong to customer");
        }
        // Update specific fields from customerDto
            customer.setName(customerDto.getName());
            customer.setEmailAddress(customerDto.getEmailAddress());
         //customer = modelMapper.map(customerDto,Customer.class);
        //customer.setReservation(reservation);
        Customer saved = customerRepository.save(customer);
        CustomerDto dto = modelMapper.map(saved,CustomerDto.class);
        return dto;
    }



    @Override
    public CustomerDto fetchACustomerByReservation(long customerId, long reservationId) {
        Customer customer =  customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer","id",customerId));
        Reservation reservation =reservationRepository.findById(reservationId).orElseThrow(()->new ResourceNotFoundException("Reservation","id",reservationId));
        if(customer.getReservation().getId() != reservation.getId()){
            throw new FoodHqValidationException(HttpStatus.BAD_REQUEST,"reservation does not belong to customer");
        }
       CustomerDto dto = modelMapper.map(customer,CustomerDto.class);
        return dto;
    }

    @Override
    public List<CustomerDto> findByReservationIdIn(List<Long> reservationIds) {
        List<Customer> customers = customerRepository.findByReservationIdIn(reservationIds);
        List<CustomerDto> dto = customers.stream().map((customer)-> modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList());
        return dto;
    }

//    @Override
//    public List<CustomerDto> findByReservationId(long reservationId) {
//          List<Customer> customers =  customerRepository.findByReservationId(reservationId);
//          List<CustomerDto> dtos =  customers.stream().map((customer)->modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList());
//        return dtos;
//    }

    @Override
    public List<CustomerDto> fetchWithPagination(int pageNo, int pageSize) {
        Page<Customer> customerPage = customerRepository.findAll(PageRequest.of(pageNo,pageSize));
        List<Customer> customerList = customerPage.getContent();
        List<CustomerDto> dto = customerList.stream().map((customer)->modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList());
        return dto;
    }

    @Override
    public CustomerPaginationResponse customCustomerPg(int pageNo, int pageSize) {
        Page<Customer> customers =customerRepository.findAll(PageRequest.of(pageNo,pageSize));
        List<Customer> customers1 = customers.getContent();
        List<CustomerDto> customers2 = customers1.stream().map((customer)->modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList());
        CustomerPaginationResponse customerPaginationResponse = new CustomerPaginationResponse();
        customerPaginationResponse.setContent(customers.getContent());
        customerPaginationResponse.setPageNo(customers.getNumber());
        customerPaginationResponse.setPageSize(customers.getSize());
        customerPaginationResponse.setTotalElements(customers.getTotalElements());
        customerPaginationResponse.setTotalPages(customers.getTotalPages());
        customerPaginationResponse.setFirst(customers.isFirst());
        customerPaginationResponse.setLast(customers.isLast());
        return customerPaginationResponse;
    }

    @Override
    public List<CustomerDto> sortedCustomers(String sortDir, String sortBy) {
      List<Customer> customerLisst = customerRepository.findAll(Sort.by(Sort.Direction.fromString(sortDir),sortBy));
      List<CustomerDto> dto = customerLisst.stream().map((customer)->modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList());
        return dto;
    }

    @Override
    public List<CustomerDto> fetchPainationAndSort(int pageNo, int pageSize, String sortDir, String sortBy) {
        Page<Customer> pg = customerRepository.findAll(PageRequest.of(pageNo,pageSize,Sort.by(Sort.Direction.fromString(sortDir),sortBy)));
        List<Customer> customers = pg.getContent();
       List<CustomerDto> dto =  customers.stream().map((customer)-> modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList());
        return dto;
    }

    @Override
    public void deleteCustomerByReservation(long reservationId, long customerId) {
       Reservation reserve =  reservationRepository.findById(reservationId).orElseThrow(()->new ResourceNotFoundException("Reservation","id",reservationId));
      Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer","id",customerId));

      if(reserve.getId() != customer.getReservation().getId()){
          throw new FoodHqValidationException(HttpStatus.BAD_REQUEST,"reservation does not belong to customer");
      }
      customerRepository.deleteById(customerId);

    }
}