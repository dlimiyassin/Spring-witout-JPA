package com.example.resume_management.contollers;

import com.example.resume_management.dto.requests.CvRequest;
import com.example.resume_management.dto.requests.CvUpdate;
import com.example.resume_management.dto.responses.CvResponse;
import com.example.resume_management.entities.Cv;
import com.example.resume_management.services.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Controller
@RequestMapping("/api")
public class CvController {

    @Autowired
    private CvService cvService;


    @PostMapping()
    public ResponseEntity<CvResponse> addCv(@RequestBody CvRequest cv) {
        return new ResponseEntity<>(cvService.addCv(cv), HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<CvResponse> GetCv(@RequestParam("id") int id) {
        return new ResponseEntity<>(cvService.GetCv(id),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CvResponse>> GetAllCv() {
        return new ResponseEntity<>(cvService.GetAllCv(),HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<CvResponse> editCv(@RequestBody CvUpdate cv) {
        return new ResponseEntity<>(cvService.editCv(cv), HttpStatus.ACCEPTED);
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteCv(@RequestParam("id") int id) {
        cvService.deleteCv(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
