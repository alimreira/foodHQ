package com.eatwell.foodHQ.payload.paginationCustomResponse;

import com.eatwell.foodHQ.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ReservationPaginationResponse {
    private List<Reservation> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
    private boolean isFirst;

//    public ReservationPaginationResponse(List<Reservation> content, int pageNo, int pageSize, long totalElements, int totalPages, boolean isLast, boolean isFirst) {
//        System.out.println("bean is created");
//        this.content = content;
//        this.pageNo = pageNo;
//        this.pageSize = pageSize;
//        this.totalElements = totalElements;
//        this.totalPages = totalPages;
//        this.isLast = isLast;
//        this.isFirst = isFirst;
//    }
}
