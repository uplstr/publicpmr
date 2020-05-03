package com.publicpmr.web.rest;

import com.publicpmr.PublicpmrApp;
import com.publicpmr.domain.Rates;
import com.publicpmr.domain.Curs;
import com.publicpmr.domain.Exchange;
import com.publicpmr.repository.RatesRepository;
import com.publicpmr.service.RatesService;
import com.publicpmr.service.dto.RatesDTO;
import com.publicpmr.service.mapper.RatesMapper;
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
import java.util.ArrayList;
import java.util.List;

import static com.publicpmr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.publicpmr.domain.enumeration.BankName;
/**
 * Integration tests for the {@link RatesResource} REST controller.
 */
@SpringBootTest(classes = PublicpmrApp.class)
public class RatesResourceIT {

    private static final BankName DEFAULT_BANK_SYSTEM_NAME = BankName.PRB;
    private static final BankName UPDATED_BANK_SYSTEM_NAME = BankName.AGR;

    @Autowired
    private RatesRepository ratesRepository;

    @Mock
    private RatesRepository ratesRepositoryMock;

    @Autowired
    private RatesMapper ratesMapper;

    @Mock
    private RatesService ratesServiceMock;

    @Autowired
    private RatesService ratesService;

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

    private MockMvc restRatesMockMvc;

    private Rates rates;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RatesResource ratesResource = new RatesResource(ratesService);
        this.restRatesMockMvc = MockMvcBuilders.standaloneSetup(ratesResource)
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
    public static Rates createEntity(EntityManager em) {
        Rates rates = new Rates()
            .bankSystemName(DEFAULT_BANK_SYSTEM_NAME);
        // Add required entity
        Curs curs;
        if (TestUtil.findAll(em, Curs.class).isEmpty()) {
            curs = CursResourceIT.createEntity(em);
            em.persist(curs);
            em.flush();
        } else {
            curs = TestUtil.findAll(em, Curs.class).get(0);
        }
        rates.getCurs().add(curs);
        // Add required entity
        Exchange exchange;
        if (TestUtil.findAll(em, Exchange.class).isEmpty()) {
            exchange = ExchangeResourceIT.createEntity(em);
            em.persist(exchange);
            em.flush();
        } else {
            exchange = TestUtil.findAll(em, Exchange.class).get(0);
        }
        rates.getExchanges().add(exchange);
        return rates;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rates createUpdatedEntity(EntityManager em) {
        Rates rates = new Rates()
            .bankSystemName(UPDATED_BANK_SYSTEM_NAME);
        // Add required entity
        Curs curs;
        if (TestUtil.findAll(em, Curs.class).isEmpty()) {
            curs = CursResourceIT.createUpdatedEntity(em);
            em.persist(curs);
            em.flush();
        } else {
            curs = TestUtil.findAll(em, Curs.class).get(0);
        }
        rates.getCurs().add(curs);
        // Add required entity
        Exchange exchange;
        if (TestUtil.findAll(em, Exchange.class).isEmpty()) {
            exchange = ExchangeResourceIT.createUpdatedEntity(em);
            em.persist(exchange);
            em.flush();
        } else {
            exchange = TestUtil.findAll(em, Exchange.class).get(0);
        }
        rates.getExchanges().add(exchange);
        return rates;
    }

    @BeforeEach
    public void initTest() {
        rates = createEntity(em);
    }

    @Test
    @Transactional
    public void createRates() throws Exception {
        int databaseSizeBeforeCreate = ratesRepository.findAll().size();

        // Create the Rates
        RatesDTO ratesDTO = ratesMapper.toDto(rates);
        restRatesMockMvc.perform(post("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesDTO)))
            .andExpect(status().isCreated());

        // Validate the Rates in the database
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeCreate + 1);
        Rates testRates = ratesList.get(ratesList.size() - 1);
        assertThat(testRates.getBankSystemName()).isEqualTo(DEFAULT_BANK_SYSTEM_NAME);
    }

    @Test
    @Transactional
    public void createRatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ratesRepository.findAll().size();

        // Create the Rates with an existing ID
        rates.setId(1L);
        RatesDTO ratesDTO = ratesMapper.toDto(rates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatesMockMvc.perform(post("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rates in the database
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBankSystemNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratesRepository.findAll().size();
        // set the field null
        rates.setBankSystemName(null);

        // Create the Rates, which fails.
        RatesDTO ratesDTO = ratesMapper.toDto(rates);

        restRatesMockMvc.perform(post("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesDTO)))
            .andExpect(status().isBadRequest());

        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRates() throws Exception {
        // Initialize the database
        ratesRepository.saveAndFlush(rates);

        // Get all the ratesList
        restRatesMockMvc.perform(get("/api/rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rates.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankSystemName").value(hasItem(DEFAULT_BANK_SYSTEM_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRatesWithEagerRelationshipsIsEnabled() throws Exception {
        RatesResource ratesResource = new RatesResource(ratesServiceMock);
        when(ratesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRatesMockMvc = MockMvcBuilders.standaloneSetup(ratesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRatesMockMvc.perform(get("/api/rates?eagerload=true"))
        .andExpect(status().isOk());

        verify(ratesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRatesWithEagerRelationshipsIsNotEnabled() throws Exception {
        RatesResource ratesResource = new RatesResource(ratesServiceMock);
            when(ratesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRatesMockMvc = MockMvcBuilders.standaloneSetup(ratesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRatesMockMvc.perform(get("/api/rates?eagerload=true"))
        .andExpect(status().isOk());

            verify(ratesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRates() throws Exception {
        // Initialize the database
        ratesRepository.saveAndFlush(rates);

        // Get the rates
        restRatesMockMvc.perform(get("/api/rates/{id}", rates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rates.getId().intValue()))
            .andExpect(jsonPath("$.bankSystemName").value(DEFAULT_BANK_SYSTEM_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRates() throws Exception {
        // Get the rates
        restRatesMockMvc.perform(get("/api/rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRates() throws Exception {
        // Initialize the database
        ratesRepository.saveAndFlush(rates);

        int databaseSizeBeforeUpdate = ratesRepository.findAll().size();

        // Update the rates
        Rates updatedRates = ratesRepository.findById(rates.getId()).get();
        // Disconnect from session so that the updates on updatedRates are not directly saved in db
        em.detach(updatedRates);
        updatedRates
            .bankSystemName(UPDATED_BANK_SYSTEM_NAME);
        RatesDTO ratesDTO = ratesMapper.toDto(updatedRates);

        restRatesMockMvc.perform(put("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesDTO)))
            .andExpect(status().isOk());

        // Validate the Rates in the database
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeUpdate);
        Rates testRates = ratesList.get(ratesList.size() - 1);
        assertThat(testRates.getBankSystemName()).isEqualTo(UPDATED_BANK_SYSTEM_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRates() throws Exception {
        int databaseSizeBeforeUpdate = ratesRepository.findAll().size();

        // Create the Rates
        RatesDTO ratesDTO = ratesMapper.toDto(rates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatesMockMvc.perform(put("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rates in the database
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRates() throws Exception {
        // Initialize the database
        ratesRepository.saveAndFlush(rates);

        int databaseSizeBeforeDelete = ratesRepository.findAll().size();

        // Delete the rates
        restRatesMockMvc.perform(delete("/api/rates/{id}", rates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
