package com.eatwell.foodHQ.service;

import com.eatwell.foodHQ.payload.ReservationDto;
import com.eatwell.foodHQ.payload.paginationCustomResponse.ReservationPaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

public interface ReservationService {

    ReservationDto makeReservation(ReservationDto reservationDto);

    List<ReservationDto> multipleReservations(List<ReservationDto> reservationDtoList);
    ReservationDto updateReservation(ReservationDto reservationDto, long id);

    ReservationDto getReservationById(long id);

    List<ReservationDto> getMultiReservations();

    List<ReservationDto> getWithPagination(int pageNo, int pageSize);

    ReservationPaginationResponse customResponse(int pageNo, int pageSize);

    List<ReservationDto> getWithSorting(String sortDir, String sortBy);

    List<ReservationDto> paginatingAndSorting(int pageNo, int pageSize, String sortDir,String sortBy);
    void deleteAReservationById(long id);

}