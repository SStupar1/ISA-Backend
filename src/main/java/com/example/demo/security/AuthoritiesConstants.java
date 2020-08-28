package com.example.demo.security;

public final class AuthoritiesConstants {
    public static final String ADMIN_ROLE = "hasAuthority('ADMIN')";

    public static final String PATIENT_ROLE = "hasAuthority('PATIENT')";

    public static final String MEDICAL_ROLE = "hasAuthority('MEDICAL')";

    public static final String ADMIN_PATIENT_ROLE = "hasAnyAuthority('ADMIN', 'PATIENT')";

    public static final String ADMIN_PATIENT_MEDICAL_ROLE = "hasAnyAuthority('ADMIN', 'PATIENT', 'MEDICAL')";
}
