package com.microservices.licensingservice.service;

import com.microservices.licensingservice.model.License;
import com.microservices.licensingservice.repository.LicenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class LicenseService {

    @Autowired
    MessageSource messageSource;
    @Autowired
    LicenseRepository licenseRepository;
    public License getLicense(String licenseId, String organizationId) {
        Optional<License> optionalLicense = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        log.info("License found with licenseId: {} and organizationId: {}", licenseId, organizationId);
        return optionalLicense.orElse(null);
    }

    public String createLicense(License license, Locale locale) {
        String responseMessage = null;
        if(license != null) {
            license.setLicenseId(UUID.randomUUID().toString());
            licenseRepository.save(license);
            responseMessage = String.format(messageSource.getMessage("license.create.message", null, locale), license.toString());
            log.info("License with licenseId: {} saved to database", license.getLicenseId());
        } else {
            log.error("Unable to save license with licenseId: {} to data base", "NULL");
        }
        return  responseMessage;
    }

    public String updateLicense(License license, Locale locale) {
        String responseMessage = null;
        if(license != null) {
            licenseRepository.save(license);
            responseMessage = String.format(messageSource.getMessage("license.update.message", null, locale), license.getLicenseId());
            log.info("License with licenseId: {} saved to database", license.getLicenseId());
        } else {
            log.error("Unable to update license with licenseId: {} to database", "NULL");
        }
        return responseMessage;
    }

    public String deleteLicense(String licenseId, Locale locale) {
        String responseMessage = null;
        Optional<License> optionalLicense = licenseRepository.findById(licenseId);
        if(optionalLicense.isPresent()) {
            License license = optionalLicense.get();
            licenseRepository.delete(license);
            responseMessage = String.format(messageSource.getMessage("license.delete.message", null, locale), licenseId);
            log.info("Deleted the license with licenseId: {}", license.getLicenseId());
        } else {
            log.error("License with the licenseId: {} is not present", licenseId);
        }
        return responseMessage;
    }
}
