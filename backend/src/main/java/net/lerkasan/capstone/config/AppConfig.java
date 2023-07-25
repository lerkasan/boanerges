package net.lerkasan.capstone.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.sts.StsClient;

import java.time.format.DateTimeFormatter;
import java.util.Collections;


@Configuration
public class AppConfig {

    @Value("${openai.chatgpt.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Qualifier("ChatGptWebClient")
    public WebClient chatGptWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public PollyClient pollyClient() {
        return PollyClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public StsClient stsClient() {
        return StsClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
//                .credentialsProvider(AwsSessionCredentials.builder().accessKeyId().secretAccessKey().sessionToken().build())
//                .credentialsProvider(stsService.assumeGivenRole("Role-Session-Name"))
                .credentialsProvider(ProfileCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .build();
    }


//    @Bean
//    public ResourceBundleMessageSource emailMessageSource() {
//        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("mail/MailMessages");
//        return messageSource;
//    }

    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

//    @Bean
//    public ITemplateResolver htmlTemplateResolver() {
//        final var templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setOrder(Integer.valueOf(2));
//        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
////        templateResolver.setPrefix("/mail/");
////        templateResolver.setSuffix(".html");
////        templateResolver.setTemplateMode(TemplateMode.HTML);
////        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
////        templateResolver.setCacheable(false);
//        return templateResolver;
//    }

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
//        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//
//        return builder -> {
//
//            // formatter
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//            // deserializers
//            builder.deserializers(new LocalDateDeserializer(dateFormatter));
//            builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));
//
//            // serializers
//            builder.serializers(new LocalDateSerializer(dateFormatter));
//            builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
//        };
//
//    }

}
