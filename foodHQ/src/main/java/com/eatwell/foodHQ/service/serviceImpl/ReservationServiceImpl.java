package com.eatwell.foodHQ.service.serviceImpl;

import com.eatwell.foodHQ.domain.Reservation;
import com.eatwell.foodHQ.exception.ResourceNotFoundException;
import com.eatwell.foodHQ.payload.ReservationDto;
import com.eatwell.foodHQ.payload.paginationCustomResponse.ReservationPaginationResponse;
import com.eatwell.foodHQ.repository.ReservationRepository;
import com.eatwell.foodHQ.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private ModelMapper modelMapper;

    private ReservationPaginationResponse reservationPaginationResponse;
    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ModelMapper modelMapper,
                                  ReservationPaginationResponse reservationPaginationResponse) {
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
        this.reservationPaginationResponse = reservationPaginationResponse;
    }

    @Override
    public ReservationDto makeReservation(ReservationDto reservationDto) {
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        Reservation reservationSaved = reservationRepository.save(reservation);
        ReservationDto dto = modelMapper.map(reservationSaved,ReservationDto.class);
        return dto;
    }

    @Override
    public List<ReservationDto> multipleReservations(List<ReservationDto> reservationDtoList) {
        List<Reservation> reservations = reservationDtoList.stream().map((reservationDto)->modelMapper.map(reservationDto,Reservation.class))
                .collect(Collectors.toList());
       List<Reservation> reserveToDb = (List<Reservation>) reservationRepository.saveAll(reservations);
       List<ReservationDto> dto =  reserveToDb.stream().map((transfer)-> modelMapper.map(transfer,ReservationDto.class)).collect(Collectors.toList());
        return dto;
    }

    @Override
    public ReservationDto updateReservation(ReservationDto reservationDto, long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Reservation","Id",id));
        reservation.setEmail(reservationDto.getEmail());
        reservation.setDateTime(reservationDto.getDateTime());
        reservation.setTimeUpdate(reservationDto.getTimeUpdate());
        Reservation reservationSaved = reservationRepository.save(reservation);
        ReservationDto dto= modelMapper.map(reservationSaved,ReservationDto.class);
        return dto;
    }

    @Override
    public ReservationDto getReservationById(long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Reservation","id", id));
        return modelMapper.map(reservation,ReservationDto.class);
    }

    @Override
    public List<ReservationDto> getMultiReservations() {
        List<Reservation> reserves = reservationRepository.findAll();
       List<ReservationDto> dto = reserves.stream().map((reserve)->modelMapper.map(reserve,ReservationDto.class)).collect(Collectors.toList());
       return dto;
    }

    @Override
    public List<ReservationDto> getWithPagination(int pageNo, int pageSize) {
        Page<Reservation> reservationPage= reservationRepository.findAll(PageRequest.of(pageNo,pageSize));
        List<Reservation> reservationList = reservationPage.getContent();
        List<ReservationDto> dtos =reservationList.stream().map((list)->modelMapper.map(list,ReservationDto.class)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public ReservationPaginationResponse customResponse(int pageNo, int pageSize) {
        Page<Reservation> page = reservationRepository.findAll(PageRequest.of(pageNo,pageSize));
        List<ReservationDto> dto = page.getContent().stream().map((str)->modelMapper.map(str,ReservationDto.class)).collect(Collectors.toList());
        reservationPaginationResponse.setContent(page.getContent());
        reservationPaginationResponse.setPageNo(page.getNumber());
        reservationPaginationResponse.setPageSize(page.getSize());
        reservationPaginationResponse.setTotalElements(page.getTotalElements());
        reservationPaginationResponse.setTotalPages(page.getTotalPages());
        reservationPaginationResponse.setFirst(page.isFirst());
        reservationPaginationResponse.setLast(page.isLast());
        return reservationPaginationResponse;
    }

    @Override
    public List<ReservationDto> getWithSorting(String sortDir,String sortBy) {
        List<Reservation> reservationList = reservationRepository.findAll(Sort.by(Sort.Direction.fromString(sortDir),sortBy));
        List<ReservationDto> dto = reservationList.stream().map((r)-> modelMapper.map(r,ReservationDto.class)).collect(Collectors.toList());
        return dto;
    }

    @Override
    public List<ReservationDto> paginatingAndSorting(int pageNo, int pageSize, String sortDir, String sortBy) {
        Page<Reservation> reserve =  reservationRepository.findAll(PageRequest.of(pageNo,pageSize,Sort.by(Sort.Direction.fromString(sortDir),sortBy)));
        List<Reservation> list = reserve.getContent();
        return reserve.stream().map((lst)-> modelMapper.map(lst,ReservationDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteAReservationById(long id) {
        Reservation reserve = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Reservation","id",id));
        reservationRepository.deleteById(id);

    }


}
