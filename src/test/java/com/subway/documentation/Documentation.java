package com.subway.documentation;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // defined port로 지정시 인수테스트와 충돌이 발생함
@ExtendWith(RestDocumentationExtension.class)
public class Documentation {

    @LocalServerPort
    private int port;

    protected RequestSpecification spec;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation).operationPreprocessors()
                        .withRequestDefaults(prettyPrint()) // json을 예쁘게 출력
                        .withResponseDefaults(prettyPrint())) // json을 예쁘게 출력
                .build();
    }
}
