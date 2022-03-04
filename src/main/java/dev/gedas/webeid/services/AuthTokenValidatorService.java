package dev.gedas.webeid.services;

import eu.webeid.security.exceptions.JceException;
import eu.webeid.security.validator.AuthTokenValidator;
import eu.webeid.security.validator.AuthTokenValidatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AuthTokenValidatorService {

    @Bean
    public AuthTokenValidator validator() {
        try {
            return new AuthTokenValidatorBuilder()
                    .withSiteOrigin(URI.create(System.getenv("ORIGIN_URL")))
                    .withTrustedCertificateAuthorities(loadTrustedCACertificatesFromCerFiles())
                    .build();
        } catch (JceException e) {
            throw new RuntimeException("Error building the Web eID auth token validator.", e);
        }
    }

    private X509Certificate[] loadTrustedCACertificatesFromCerFiles() {
        List<X509Certificate> caCertificates = new ArrayList<>();

        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

            File[] files = new File("/certs").listFiles((f, n) -> n.endsWith(".cer"));
            if (files != null) {
                for (File file : files) {
                    try (InputStream stream = new FileInputStream(file)) {
                        X509Certificate caCertificate = (X509Certificate) certFactory.generateCertificate(stream);
                        caCertificates.add(caCertificate);
                    }
                }
            }

        } catch (CertificateException | IOException e) {
            throw new RuntimeException("Error initializing trusted CA certificates.", e);
        }

        return caCertificates.toArray(new X509Certificate[0]);
    }
}
