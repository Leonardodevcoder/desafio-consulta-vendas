package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;

	@GetMapping("/report")
	public ResponseEntity<Page<SaleMinDTO>> findSalesReport(
			@RequestParam(value = "minDate", defaultValue = "") String minDateStr,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDateStr,
			@RequestParam(value = "name", defaultValue = "") String name,
			Pageable pageable) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate minDate = minDateStr.isEmpty() ? null : LocalDate.parse(minDateStr, formatter);
		LocalDate maxDate = maxDateStr.isEmpty() ? null : LocalDate.parse(maxDateStr, formatter);
		Page<SaleMinDTO> result = service.findSalesReport(minDate, maxDate, name.trim(), pageable);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/summary")
	public ResponseEntity<List<SaleSummaryDTO>> findSalesSummary(
			@RequestParam(value = "minDate", defaultValue = "") String minDateStr,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate minDate = minDateStr.isEmpty() ? null : LocalDate.parse(minDateStr, formatter);
		LocalDate maxDate = maxDateStr.isEmpty() ? null : LocalDate.parse(maxDateStr, formatter);
		List<SaleSummaryDTO> result = service.findSalesSummary(minDate, maxDate);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
}