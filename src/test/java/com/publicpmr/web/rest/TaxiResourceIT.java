package com.publicpmr.web.rest;

import com.publicpmr.PublicpmrApp;
import com.publicpmr.domain.Taxi;
import com.publicpmr.repository.TaxiRepository;
import com.publicpmr.service.TaxiService;
import com.publicpmr.service.dto.TaxiDTO;
import com.publicpmr.service.mapper.TaxiMapper;
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

/**
 * Integration tests for the {@link TaxiResource} REST controller.
 */
@SpringBootTest(classes = PublicpmrApp.class)
public class TaxiResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE = 1L;
    private static final Long UPDATED_PHONE = 2L;

    private static final Float DEFAULT_ACTIVE = 1F;
    private static final Float UPDATED_ACTIVE = 2F;

    @Autowired
    private TaxiRepository taxiRepository;

    @Autowired
    private TaxiMapper taxiMapper;

    @Autowired
    private TaxiService taxiService;

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

    private MockMvc restTaxiMockMvc;

    private Taxi taxi;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaxiResource taxiResource = new TaxiResource(taxiService);
        this.restTaxiMockMvc = MockMvcBuilders.standaloneSetup(taxiResource)
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
    public static Taxi createEntity(EntityManager em) {
        Taxi taxi = new Taxi()
            .title(DEFAULT_TITLE)
            .phone(DEFAULT_PHONE)
            .active(DEFAULT_ACTIVE);
        return taxi;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taxi createUpdatedEntity(EntityManager em) {
        Taxi taxi = new Taxi()
            .title(UPDATED_TITLE)
            .phone(UPDATED_PHONE)
            .active(UPDATED_ACTIVE);
        return taxi;
    }

    @BeforeEach
    public void initTest() {
        taxi = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxi() throws Exception {
        int databaseSizeBeforeCreate = taxiRepository.findAll().size();

        // Create the Taxi
        TaxiDTO taxiDTO = taxiMapper.toDto(taxi);
        restTaxiMockMvc.perform(post("/api/taxis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxiDTO)))
            .andExpect(status().isCreated());

        // Validate the Taxi in the database
        List<Taxi> taxiList = taxiRepository.findAll();
        assertThat(taxiList).hasSize(databaseSizeBeforeCreate + 1);
        Taxi testTaxi = taxiList.get(taxiList.size() - 1);
        assertThat(testTaxi.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTaxi.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testTaxi.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createTaxiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxiRepository.findAll().size();

        // Create the Taxi with an existing ID
        taxi.setId(1L);
        TaxiDTO taxiDTO = taxiMapper.toDto(taxi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxiMockMvc.perform(post("/api/taxis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Taxi in the database
        List<Taxi> taxiList = taxiRepository.findAll();
        assertThat(taxiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxiRepository.findAll().size();
        // set the field null
        taxi.setTitle(null);

        // Create the Taxi, which fails.
        TaxiDTO taxiDTO = taxiMapper.toDto(taxi);

        restTaxiMockMvc.perform(post("/api/taxis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxiDTO)))
            .andExpect(status().isBadRequest());

        List<Taxi> taxiList = taxiRepository.findAll();
        assertThat(taxiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxiRepository.findAll().size();
        // set the field null
        taxi.setPhone(null);

        // Create the Taxi, which fails.
        TaxiDTO taxiDTO = taxiMapper.toDto(taxi);

        restTaxiMockMvc.perform(post("/api/taxis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxiDTO)))
            .andExpect(status().isBadRequest());

        List<Taxi> taxiList = taxiRepository.findAll();
        assertThat(taxiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxiRepository.findAll().size();
        // set the field null
        taxi.setActive(null);

        // Create the Taxi, which fails.
        TaxiDTO taxiDTO = taxiMapper.toDto(taxi);

        restTaxiMockMvc.perform(post("/api/taxis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxiDTO)))
            .andExpect(status().isBadRequest());

        List<Taxi> taxiList = taxiRepository.findAll();
        assertThat(taxiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxis() throws Exception {
        // Initialize the database
        taxiRepository.saveAndFlush(taxi);

        // Get all the taxiList
        restTaxiMockMvc.perform(get("/api/taxis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxi.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTaxi() throws Exception {
        // Initialize the database
        taxiRepository.saveAndFlush(taxi);

        // Get the taxi
        restTaxiMockMvc.perform(get("/api/taxis/{id}", taxi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taxi.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxi() throws Exception {
        // Get the taxi
        restTaxiMockMvc.perform(get("/api/taxis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxi() throws Exception {
        // Initialize the database
        taxiRepository.saveAndFlush(taxi);

        int databaseSizeBeforeUpdate = taxiRepository.findAll().size();

        // Update the taxi
        Taxi updatedTaxi = taxiRepository.findById(taxi.getId()).get();
        // Disconnect from session so that the updates on updatedTaxi are not directly saved in db
        em.detach(updatedTaxi);
        updatedTaxi
            .title(UPDATED_TITLE)
            .phone(UPDATED_PHONE)
            .active(UPDATED_ACTIVE);
        TaxiDTO taxiDTO = taxiMapper.toDto(updatedTaxi);

        restTaxiMockMvc.perform(put("/api/taxis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxiDTO)))
            .andExpect(status().isOk());

        // Validate the Taxi in the database
        List<Taxi> taxiList = taxiRepository.findAll();
        assertThat(taxiList).hasSize(databaseSizeBeforeUpdate);
        Taxi testTaxi = taxiList.get(taxiList.size() - 1);
        assertThat(testTaxi.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTaxi.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testTaxi.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxi() throws Exception {
        int databaseSizeBeforeUpdate = taxiRepository.findAll().size();

        // Create the Taxi
        TaxiDTO taxiDTO = taxiMapper.toDto(taxi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxiMockMvc.perform(put("/api/taxis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Taxi in the database
        List<Taxi> taxiList = taxiRepository.findAll();
        assertThat(taxiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxi() throws Exception {
        // Initialize the database
        taxiRepository.saveAndFlush(taxi);

        int databaseSizeBeforeDelete = taxiRepository.findAll().size();

        // Delete the taxi
        restTaxiMockMvc.perform(delete("/api/taxis/{id}", taxi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Taxi> taxiList = taxiRepository.findAll();
        assertThat(taxiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
