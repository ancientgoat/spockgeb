package com.apptricity

import com.apptricity.service.ExpenseReportService
import com.jayway.restassured.RestAssured
import com.jayway.restassured.response.Response
import com.jayway.restassured.response.ResponseBody
import com.jayway.restassured.specification.RequestSpecification
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

import static com.jayway.restassured.RestAssured.given

@SpringApplicationConfiguration(classes = ExpenseManagement.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ITestHello {

    public static Logger log = LoggerFactory.getLogger(ITestHello.class)

    @Autowired
    public ExpenseReportService expenseReportService;

    @Value('${local.server.port}')
    private int port;// = 8080;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testXtest() {

        println '===================================================='
        println '== : ' + expenseReportService;
        println '===================================================='

        String THE_URL = "http://localhost:${port}/expense";

        // formParam("formParamName", "value1").
        // queryParam("queryParamName", "value2").

//        Response response =

        println '==E: ================================================'

        RequestSpecification requestSpecification = given()
                .contentType("application/json")
                .body('''
{ "merchant" :
    { "name" : "Johnson" }
, "amount" : "1.01"
, "expenseDateTime" : "2016-01-01T01:01:01-0700"
}
    ''')
        ;

        println '==D: ================================================'

        Response response = requestSpecification.when()
                .post(THE_URL)
        ;

        println '==C: ================================================'

        ResponseBody responseBody = response.getBody();
        println '==B: ================================================'

        String s1 = responseBody.prettyPrint()
        println '==A: ================================================'
        String s2 = responseBody.print()
        println '==1: ================================================'
        println s1
        println '==2: ================================================'
        println s2
        println '==3: ================================================'
    }
}
