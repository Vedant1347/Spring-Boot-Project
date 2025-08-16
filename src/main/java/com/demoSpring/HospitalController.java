package com.demoSpring;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {

    private final ApplicationException applicationException;

	private final DemoSpringApplication demoSpringApplication;

	@Autowired
	HospitalJPA jpa;

	HospitalController(DemoSpringApplication demoSpringApplication, ApplicationException applicationException) {
		this.demoSpringApplication = demoSpringApplication;
		this.applicationException = applicationException;
	}

	@PostMapping("/create")
	public Hospital createHospital(@RequestBody Hospital h) {
		return jpa.save(h);
	}

	@GetMapping("/get/{name}/{location}")
	public List<Hospital> getByNameLocation(@PathVariable String name, @PathVariable String location) {
		return jpa.findByNameLocation(name, location);
	}

	@GetMapping("/findall")
	public List<Hospital> getAllInfo() {
		return jpa.findAll();
	}

	@GetMapping("/findAll")
	public ResponseEntity<ResponseStructure<List<Hospital>>> getHospitalAllData() {
		List<Hospital> hospitals = jpa.findAll();

		ResponseStructure<List<Hospital>> res = new ResponseStructure<>();
		res.setLocaldatetime(LocalDateTime.now());

		if (!hospitals.isEmpty()) {
			res.setData(hospitals);
			res.setMessage("Success");
			res.setStatuscode(HttpStatus.OK.value());
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			res.setData(null);
			res.setMessage("Data not Found");
			res.setStatuscode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
	}

//	@GetMapping("/getall/{page}/{size}")
//	public Page<Hospital> getHospital(@PathVariable int page, @PathVariable int size){
//		PageRequest pagable = PageRequest.of(page, size);
//		return jpa.findAll(pagable);
//	}

	@GetMapping("/getall/{page}/{size}")
	public ResponseEntity<ResponseStructure<Page<Hospital>>> getHospital(@PathVariable int page,
			@PathVariable int size) {
		PageRequest pageable = PageRequest.of(page, size);
		Page<Hospital> hospitalPage = jpa.findAll(pageable);

		ResponseStructure<Page<Hospital>> res = new ResponseStructure<>();
		res.setData(hospitalPage);
		res.setLocaldatetime(LocalDateTime.now());

		if (hospitalPage.hasContent()) {
			res.setMessage("Success");
			res.setStatuscode(HttpStatus.OK.value());
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			res.setMessage("No Data Found");
			res.setStatuscode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getid/{id}")
	public Optional<Hospital> getById(@PathVariable int id) {
		return jpa.findById(id);
	}

	@GetMapping("/getbyid")
	public ResponseEntity<ResponseStructure<Hospital>> findbyID(@RequestParam int id){
		Optional<Hospital> option = jpa.findById(id);
		if(option.isPresent()) {
			Hospital h = option.get();
			ResponseStructure<Hospital> res = new ResponseStructure<>();
			res.setData(h);
			res.setLocaldatetime(LocalDateTime.now());
			res.setMessage("Success");
			res.setStatuscode(HttpStatus.FOUND.value());
			ResponseEntity<ResponseStructure<Hospital>> rs = new ResponseEntity<ResponseStructure<Hospital>>(res,HttpStatus.FOUND);
			return rs;
		}
		else {
//			ResponseStructure<Hospital> res = new ResponseStructure<>();
//			res.setLocaldatetime(LocalDateTime.now());
//			res.setMessage("DatNotFound");
//			res.setStatuscode(HttpStatus.NOT_FOUND.value());
//			ResponseEntity<ResponseStructure<Hospital>> rs = new ResponseEntity<ResponseStructure<Hospital>>(res,HttpStatus.FOUND);
//			return rs;
			
			throw new IdNotPresent();
		}
	}
}