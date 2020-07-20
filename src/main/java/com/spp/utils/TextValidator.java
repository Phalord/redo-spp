package com.spp.utils;

import org.apache.commons.validator.routines.EmailValidator;

public class TextValidator {

    public static boolean validatePractitionerEnrollment(String studentEnrollment) {
        String regex = "^[s][0-9]{8}";
        return studentEnrollment.matches(regex);
    }

    public static boolean validateProfessorEmployeeNumber(String professorEmployeeNumber) {
        String regex = "^[p][0-9]{8}";
        return professorEmployeeNumber.matches(regex);
    }

    public static boolean validateCoordinatorEmployeeNumber(String coordinatorEmployeeNumber) {
        String regex = "^[c][0-9]{8}";
        return coordinatorEmployeeNumber.matches(regex);
    }
    
    public static boolean validateAdministratorEmployeeNumber(String administratorEmployeeNumber) {
        String regex = "^[r][0-9]{8}";
        return administratorEmployeeNumber.matches(regex);
    }

    public static boolean validateEmail(String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);
    }

    public static boolean validateTelephoneNumber(String telephoneNumber) {
        String regex = "[0-9]{3}[-. ][0-9]{3}[-. ][0-9]{4}";
        return telephoneNumber.matches(regex);
    }
}
