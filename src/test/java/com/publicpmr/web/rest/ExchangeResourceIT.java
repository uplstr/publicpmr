package com.publicpmr.web.rest;

import com.publicpmr.PublicpmrApp;
import com.publicpmr.domain.Exchange;
import com.publicpmr.domain.Rates;
import com.publicpmr.repository.ExchangeRepository;
import com.publicpmr.service.ExchangeService;
import com.publicpmr.service.dto.ExchangeDTO;
import com.publicpmr.service.mapper.ExchangeMapper;
import com.publicpmr.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.publicpmr.web.rest.TestUtil.sameInstant;
import static com.publicpmr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExchangeResource} REST controller.
 */
@SpringBootTest(classes = PublicpmrApp.class)
public class ExchangeResourceIT {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Mock
    private ExchangeRepository exchangeRepositoryMock;

    @Autowired
    private ExchangeMapper exchangeMapper;

    @Mock
    private ExchangeService exchangeServiceMock;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restExchangeMockMvc;

    private Exchange exchange;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExchangeResource exchangeResource = new ExchangeResource(exchangeService);
        this.restExchangeMockMvc = MockMvcBuilders.standaloneSetup(exchangeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exchange createEntity(EntityManager em) {
        Exchange exchange = new Exchange()
            .date(DEFAULT_DATE);
        // Add required entity
        Rates rates;
        if (TestUtil.findAll(em, Rates.class).isEmpty()) {
            rates = RatesResourceIT.createEntity(em);
            em.persist(rates);
            em.flush();
        } else {
            rates = TestUtil.findAll(em, Rates.class).get(0);
        }
        exchange.getRates().add(rates);
        return exchange;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exchange createUpdatedEntity(EntityManager em) {
        Exchange exchange = new Exchange()
            .date(UPDATED_DATE);
        // Add required entity
        Rates rates;
        if (TestUtil.findAll(em, Rates.class).isEmpty()) {
            rates = RatesResourceIT.createUpdatedEntity(em);
            em.persist(rates);
            em.flush();
        } else {
            rates = TestUtil.findAll(em, Rates.class).get(0);
        }
        exchange.getRates().add(rates);
        return exchange;
    }

    @BeforeEach
    public void initTest() {
        exchange = createEntity(em);
    }

    @Test
    @Transactional
    public void createExchange() throws Exception {
        int databaseSizeBeforeCreate = exchangeRepository.findAll().size();

        // Create the Exchange
        ExchangeDTO exchangeDTO = exchangeMapper.toDto(exchange);
        restExchangeMockMvc.perform(post("/api/exchanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeDTO)))
            .andExpect(status().isCreated());

        // Validate the Exchange in the database
        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeCreate + 1);
        Exchange testExchange = exchangeList.get(exchangeList.size() - 1);
        assertThat(testExchange.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createExchangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exchangeRepository.findAll().size();

        // Create the Exchange with an existing ID
        exchange.setId(1L);
        ExchangeDTO exchangeDTO = exchangeMapper.toDto(exchange);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExchangeMockMvc.perform(post("/api/exchanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exchange in the database
        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = exchangeRepository.findAll().size();
        // set the field null
        exchange.setDate(null);

        // Create the Exchange, which fails.
        ExchangeDTO exchangeDTO = exchangeMapper.toDto(exchange);

        restExchangeMockMvc.perform(post("/api/exchanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeDTO)))
            .andExpect(status().isBadRequest());

        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExchanges() throws Exception {
        // Initialize the database
        exchangeRepository.saveAndFlush(exchange);

        // Get all the exchangeList
        restExchangeMockMvc.perform(get("/api/exchanges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exchange.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllExchangesWithEagerRelationshipsIsEnabled() throws Exception {
        ExchangeResource exchangeResource = new ExchangeResource(exchangeServiceMock);
        when(exchangeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restExchangeMockMvc = MockMvcBuilders.standaloneSetup(exchangeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restExchangeMockMvc.perform(get("/api/exchanges?eagerload=true"))
        .andExpect(status().isOk());

        verify(exchangeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllExchangesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ExchangeResource exchangeResource = new ExchangeResource(exchangeServiceMock);
            when(exchangeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restExchangeMockMvc = MockMvcBuilders.standaloneSetup(exchangeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restExchangeMockMvc.perform(get("/api/exchanges?eagerload=true"))
        .andExpect(status().isOk());

            verify(exchangeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getExchange() throws Exception {
        // Initialize the database
        exchangeRepository.saveAndFlush(exchange);

        // Get the exchange
        restExchangeMockMvc.perform(get("/api/exchanges/{id}", exchange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exchange.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingExchange() throws Exception {
        // Get the exchange
        restExchangeMockMvc.perform(get("/api/exchanges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExchange() throws Exception {
        // Initialize the database
        exchangeRepository.saveAndFlush(exchange);

        int databaseSizeBeforeUpdate = exchangeRepository.findAll().size();

        // Update the exchange
        Exchange updatedExchange = exchangeRepository.findById(exchange.getId()).get();
        // Disconnect from session so that the updates on updatedExchange are not directly saved in db
        em.detach(updatedExchange);
        updatedExchange
            .date(UPDATED_DATE);
        ExchangeDTO exchangeDTO = exchangeMapper.toDto(updatedExchange);

        restExchangeMockMvc.perform(put("/api/exchanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeDTO)))
            .andExpect(status().isOk());

        // Validate the Exchange in the database
        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeUpdate);
        Exchange testExchange = exchangeList.get(exchangeList.size() - 1);
        assertThat(testExchange.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingExchange() throws Exception {
        int databaseSizeBeforeUpdate = exchangeRepository.findAll().size();

        // Create the Exchange
        ExchangeDTO exchangeDTO = exchangeMapper.toDto(exchange);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExchangeMockMvc.perform(put("/api/exchanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exchange in the database
        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExchange() throws Exception {
        // Initialize the database
        exchangeRepository.saveAndFlush(exchange);

        int databaseSizeBeforeDelete = exchangeRepository.findAll().size();

        // Delete the exchange
        restExchangeMockMvc.perform(delete("/api/exchanges/{id}", exchange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exchange> exchangeList = exchangeRepository.findAll();
        assertThat(exchangeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
