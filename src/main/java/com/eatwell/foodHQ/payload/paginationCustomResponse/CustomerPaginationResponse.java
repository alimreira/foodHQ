package com.eatwell.foodHQ.payload.paginationCustomResponse;

import com.eatwell.foodHQ.domain.Customer;
import com.eatwell.foodHQ.domain.Reservation;
import com.eatwell.foodHQ.payload.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CustomerPaginationResponse {
    private List<Customer> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
    private boolean isFirst;
}
