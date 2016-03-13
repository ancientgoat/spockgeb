package com.apptricity

import com.apptricity.controller.ExpenseReportController
import com.apptricity.entity.ExpenseReport
import com.apptricity.service.ExpenseReportService
import groovy.json.JsonOutput
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.data.domain.PageImpl
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

// import static org.hamcrest.Matchers.*;
@SpringApplicationConfiguration(classes = ExpenseManagement.class)
//@ContextConfiguration(classes = {TestContext.class})//, WebAppContext.class})
//@ContextConfiguration(classes = {TestContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestEmptyTest { //extends ApplicationTests {

    private MockMvc mockMvc;

    @Mock
    public ExpenseReportService mockErService

    //@Autowired
    @InjectMocks
    public ExpenseReportController controller;

    // @Autowired
    // private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        // mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        // RestAssuredMockMvc.mockMvc = mockMvc;
        // mockMvc = MockMvcBuilders.standaloneSetup(controller)
        // Pageable
        // mockMvc = MockMvcBuilders.standaloneSetup(mockErService)
        //         .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        //         .setViewResolvers(new ViewResolver() {
        //     @Override
        //     public View resolveViewName(String viewName, Locale locale) throws Exception {
        //         return new MappingJackson2JsonView();
        //     }
        // })
        //         .build();

    }

    /**
     * Blank/Empty test
     */
    @Test
    public void testXtest() {

        // Page<ExpenseReport> findAll(final Pageable pageable) {
        ExpenseReport erOne = new ExpenseReport()
                .setAmount(11.11)
                .setExpenseDateTime(new Date())

        ExpenseReport erTwo = new ExpenseReport()
                .setAmount(22.22)
                .setExpenseDateTime(new Date())

        // =====================================================================
        // .findAll(Pageable))
        // .findAll(org.springframework.data.domain.Pageable))
        // .findAll(String))
        // Arrays.asList(erOne, erTwo));
        // =====================================================================

        final ExpenseReportService mock = org.mockito.Mockito.mock(ExpenseReportService.class);
        final List<ExpenseReport> list = Arrays.asList(erOne, erTwo)

        // def thenReturn = when(mock.findAll()).thenReturn(list)
        // def thenReturn = when(mock.findAll(Pageable)).thenReturn(list)
        // def thenReturn = when(mockMvc.findAll(Pageable)).thenReturn((Page) list)

        def pageImpl = new PageImpl(list)
        //def thenReturn = when(mockMvc.findAll()).thenReturn(list)
        def thenReturn = when(mockErService.findAll()).thenReturn(list)

        // def thenReturn = when(mockMvc.findAll(Pageable)).thenReturn(pageImpl)
        // def thenReturn = when(mockMvc.findAll(Pageable)).thenReturn(pageImpl)

        println "========= mockMvc : " + mockMvc
        println "========= mockMvc : " + mockMvc
        println "========= mockMvc : " + mockMvc

        final MvcResult andReturn = mockMvc.perform(get("/expenses2"))
                .andExpect(status().isOk())
                .andReturn()

        final MockHttpServletResponse response = andReturn.getResponse()
        final String json = response.getContentAsString()
        final String jsonPrettyPrint = JsonOutput.prettyPrint(json)

        println "==: ++++++++++++++++++++++++++++++++++++++++++++++++++"
        println "==: ++++++++++++++++++++++++++++++++++++++++++++++++++"
        println "==: ++++++++++++++++++++++++++++++++++++++++++++++++++"
        println jsonPrettyPrint

        // =====================================================================
        // Todo first = new TodoBuilder()
        //         .id(1L)
        //         .description("Lorem ipsum")
        //         .title("Foo")
        //         .build();
        // Todo second = new TodoBuilder()
        //         .id(2L)
        //         .description("Lorem ipsum")
        //         .title("Bar")
        //         .build();
        //
        // when(todoServiceMock.findAll()).thenReturn(Arrays.asList(first, second));
        //
        // mockMvc.perform(get("/api/todo"))
        //         .andExpect(status().isOk())
        //         .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
        //         .andExpect(jsonPath("$", hasSize(2)))
        //         .andExpect(jsonPath("$[0].id", is(1)))
        //         .andExpect(jsonPath("$[0].description", is("Lorem ipsum")))
        //         .andExpect(jsonPath("$[0].title", is("Foo")))
        //         .andExpect(jsonPath("$[1].id", is(2)))
        //         .andExpect(jsonPath("$[1].description", is("Lorem ipsum")))
        //         .andExpect(jsonPath("$[1].title", is("Bar")));
        //
        // verify(todoServiceMock, times(1)).findAll();
        // verifyNoMoreInteractions(todoServiceMock);
        // =====================================================================

        return;
    }
}
