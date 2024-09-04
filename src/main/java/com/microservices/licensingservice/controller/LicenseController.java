package com.microservices.licensingservice.controller;

import com.microservices.licensingservice.model.License;
import com.microservices.licensingservice.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {
    @Autowired
    private LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) throws Exception{
        License license = licenseService.getLicense(licenseId, organizationId);
        license.add(linkTo(methodOn(LicenseController.class).getLicense(organizationId, licenseId)).withSelfRel());
        license.add(linkTo(methodOn(LicenseController.class).createLicense(organizationId, license, null)).withRel("createLicense"));
        license.add(linkTo(methodOn(LicenseController.class).updateLicense(organizationId, license, null)).withRel("updateLicense"));
        license.add(linkTo(methodOn(LicenseController.class).deleteLicense(organizationId, licenseId, null)).withRel("deleteLicense"));
        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<?> updateLicense(@PathVariable("organizationId") String organizationId, @RequestBody License license, @RequestHeader(value = "Accept-Language", required = false)Locale locale) throws Exception {
        String responseMessage = licenseService.updateLicense(license, organizationId, locale);
        if (responseMessage == null) {
            throw new Exception();
        } else {
            return ResponseEntity.ok(responseMessage);
        }
    }

    @PostMapping
    public ResponseEntity<?> createLicense(@PathVariable("organizationId") String organizationId, @RequestBody License license, @RequestHeader(value = "Accept-Language", required = false)Locale locale) throws Exception {
        String responseMessage = licenseService.createLicense(license, organizationId, locale);
        if(responseMessage == null) {
            throw new Exception();
        } else {
            return ResponseEntity.ok(responseMessage);
        }
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<?> deleteLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId, @RequestHeader(value = "Accept-Language", required = false)Locale locale) throws Exception{
        String responseMessage = licenseService.deleteLicense(licenseId, organizationId, locale);
        if(responseMessage == null) {
            throw new Exception();
        } else {
            return ResponseEntity.ok(responseMessage);
        }
    }
}
