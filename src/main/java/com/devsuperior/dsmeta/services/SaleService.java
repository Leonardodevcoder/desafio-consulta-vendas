package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public Page<SaleMinDTO> findSalesReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {
		if (minDate == null) {
			minDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusDays(365);
		}
		if (maxDate == null) {
			maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		return repository.findSalesReport(minDate, maxDate, name, pageable);
	}

	public List<SaleSummaryDTO> findSalesSummary(LocalDate minDate, LocalDate maxDate) {
		if (minDate == null) {
			minDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusDays(365);
		}
		if (maxDate == null) {
			maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		return repository.findSalesSummary(minDate, maxDate);
	}

	public SaleMinDTO findById(Long id) {
		Sale sale = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
		return new SaleMinDTO(sale);
	}
}