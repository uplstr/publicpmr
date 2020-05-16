package com.publicpmr.web.rest;

import com.publicpmr.PublicpmrApp;
import com.publicpmr.domain.Curs;
import com.publicpmr.domain.Rates;
import com.publicpmr.repository.CursRepository;
import com.publicpmr.service.CursService;
import com.publicpmr.service.dto.CursDTO;
import com.publicpmr.service.mapper.CursMapper;
import com.publicpmr.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.publicpmr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.publicpmr.domain.enumeration.CurrencyName;
/**
 * Integration tests for the {@link CursResource} REST controller.
 */
@SpringBootTest(classes = PublicpmrApp.class)
public class CursResourceIT {

    private static final Double DEFAULT_PURCHASE = 1D;
    private static final Double UPDATED_PURCHASE = 2D;

    private static final Double DEFAULT_SALE = 1D;
    private static final Double UPDATED_SALE = 2D;

    private static final CurrencyName DEFAULT_CURRENCY = CurrencyName.RUP;
    private static final CurrencyName UPDATED_CURRENCY = CurrencyName.USD;

    @Autowired
    private CursRepository cursRepository;

    @Autowired
    private CursMapper cursMapper;

    @Autowired
    private CursService cursService;

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

    private MockMvc restCursMockMvc;

    private Curs curs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CursResource cursResource = new CursResource(cursService);
        this.restCursMockMvc = MockMvcBuilders.standaloneSetup(cursResource)
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
    public static Curs createEntity(EntityManager em) {
        Curs curs = new Curs()
            .purchase(DEFAULT_PURCHASE)
            .sale(DEFAULT_SALE)
            .currency(DEFAULT_CURRENCY);
        // Add required entity
        Rates rates;
        if (TestUtil.findAll(em, Rates.class).isEmpty()) {
            rates = RatesResourceIT.createEntity(em);
            em.persist(rates);
            em.flush();
        } else {
            rates = TestUtil.findAll(em, Rates.class).get(0);
        }
        curs.getRates().add(rates);
        return curs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Curs createUpdatedEntity(EntityManager em) {
        Curs curs = new Curs()
            .purchase(UPDATED_PURCHASE)
            .sale(UPDATED_SALE)
            .currency(UPDATED_CURRENCY);
        // Add required entity
        Rates rates;
        if (TestUtil.findAll(em, Rates.class).isEmpty()) {
            rates = RatesResourceIT.createUpdatedEntity(em);
            em.persist(rates);
            em.flush();
        } else {
            rates = TestUtil.findAll(em, Rates.class).get(0);
        }
        curs.getRates().add(rates);
        return curs;
    }

    @BeforeEach
    public void initTest() {
        curs = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurs() throws Exception {
        int databaseSizeBeforeCreate = cursRepository.findAll().size();

        // Create the Curs
        CursDTO cursDTO = cursMapper.toDto(curs);
        restCursMockMvc.perform(post("/api/curs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursDTO)))
            .andExpect(status().isCreated());

        // Validate the Curs in the database
        List<Curs> cursList = cursRepository.findAll();
        assertThat(cursList).hasSize(databaseSizeBeforeCreate + 1);
        Curs testCurs = cursList.get(cursList.size() - 1);
        assertThat(testCurs.getPurchase()).isEqualTo(DEFAULT_PURCHASE);
        assertThat(testCurs.getSale()).isEqualTo(DEFAULT_SALE);
        assertThat(testCurs.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    public void createCursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cursRepository.findAll().size();

        // Create the Curs with an existing ID
        curs.setId(1L);
        CursDTO cursDTO = cursMapper.toDto(curs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCursMockMvc.perform(post("/api/curs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Curs in the database
        List<Curs> cursList = cursRepository.findAll();
        assertThat(cursList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPurchaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = cursRepository.findAll().size();
        // set the field null
        curs.setPurchase(null);

        // Create the Curs, which fails.
        CursDTO cursDTO = cursMapper.toDto(curs);

        restCursMockMvc.perform(post("/api/curs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursDTO)))
            .andExpect(status().isBadRequest());

        List<Curs> cursList = cursRepository.findAll();
        assertThat(cursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = cursRepository.findAll().size();
        // set the field null
        curs.setSale(null);

        // Create the Curs, which fails.
        CursDTO cursDTO = cursMapper.toDto(curs);

        restCursMockMvc.perform(post("/api/curs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursDTO)))
            .andExpect(status().isBadRequest());

        List<Curs> cursList = cursRepository.findAll();
        assertThat(cursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = cursRepository.findAll().size();
        // set the field null
        curs.setCurrency(null);

        // Create the Curs, which fails.
        CursDTO cursDTO = cursMapper.toDto(curs);

        restCursMockMvc.perform(post("/api/curs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursDTO)))
            .andExpect(status().isBadRequest());

        List<Curs> cursList = cursRepository.findAll();
        assertThat(cursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurs() throws Exception {
        // Initialize the database
        cursRepository.saveAndFlush(curs);

        // Get all the cursList
        restCursMockMvc.perform(get("/api/curs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curs.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchase").value(hasItem(DEFAULT_PURCHASE.doubleValue())))
            .andExpect(jsonPath("$.[*].sale").value(hasItem(DEFAULT_SALE.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())));
    }
    
    @Test
    @Transactional
    public void getCurs() throws Exception {
        // Initialize the database
        cursRepository.saveAndFlush(curs);

        // Get the curs
        restCursMockMvc.perform(get("/api/curs/{id}", curs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(curs.getId().intValue()))
            .andExpect(jsonPath("$.purchase").value(DEFAULT_PURCHASE.doubleValue()))
            .andExpect(jsonPath("$.sale").value(DEFAULT_SALE.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurs() throws Exception {
        // Get the curs
        restCursMockMvc.perform(get("/api/curs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurs() throws Exception {
        // Initialize the database
        cursRepository.saveAndFlush(curs);

        int databaseSizeBeforeUpdate = cursRepository.findAll().size();

        // Update the curs
        Curs updatedCurs = cursRepository.findById(curs.getId()).get();
        // Disconnect from session so that the updates on updatedCurs are not directly saved in db
        em.detach(updatedCurs);
        updatedCurs
            .purchase(UPDATED_PURCHASE)
            .sale(UPDATED_SALE)
            .currency(UPDATED_CURRENCY);
        CursDTO cursDTO = cursMapper.toDto(updatedCurs);

        restCursMockMvc.perform(put("/api/curs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursDTO)))
            .andExpect(status().isOk());

        // Validate the Curs in the database
        List<Curs> cursList = cursRepository.findAll();
        assertThat(cursList).hasSize(databaseSizeBeforeUpdate);
        Curs testCurs = cursList.get(cursList.size() - 1);
        assertThat(testCurs.getPurchase()).isEqualTo(UPDATED_PURCHASE);
        assertThat(testCurs.getSale()).isEqualTo(UPDATED_SALE);
        assertThat(testCurs.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void updateNonExistingCurs() throws Exception {
        int databaseSizeBeforeUpdate = cursRepository.findAll().size();

        // Create the Curs
        CursDTO cursDTO = cursMapper.toDto(curs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCursMockMvc.perform(put("/api/curs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Curs in the database
        List<Curs> cursList = cursRepository.findAll();
        assertThat(cursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurs() throws Exception {
        // Initialize the database
        cursRepository.saveAndFlush(curs);

        int databaseSizeBeforeDelete = cursRepository.findAll().size();

        // Delete the curs
        restCursMockMvc.perform(delete("/api/curs/{id}", curs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Curs> cursList = cursRepository.findAll();
        assertThat(cursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
