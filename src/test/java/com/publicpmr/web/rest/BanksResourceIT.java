package com.publicpmr.web.rest;

import com.publicpmr.PublicpmrApp;
import com.publicpmr.domain.Banks;
import com.publicpmr.repository.BanksRepository;
import com.publicpmr.service.BanksService;
import com.publicpmr.service.dto.BanksDTO;
import com.publicpmr.service.mapper.BanksMapper;
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

import com.publicpmr.domain.enumeration.BankName;
/**
 * Integration tests for the {@link BanksResource} REST controller.
 */
@SpringBootTest(classes = PublicpmrApp.class)
public class BanksResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final BankName DEFAULT_SYSTEM_NAME = BankName.PRB;
    private static final BankName UPDATED_SYSTEM_NAME = BankName.AGR;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private BanksRepository banksRepository;

    @Autowired
    private BanksMapper banksMapper;

    @Autowired
    private BanksService banksService;

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

    private MockMvc restBanksMockMvc;

    private Banks banks;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BanksResource banksResource = new BanksResource(banksService);
        this.restBanksMockMvc = MockMvcBuilders.standaloneSetup(banksResource)
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
    public static Banks createEntity(EntityManager em) {
        Banks banks = new Banks()
            .title(DEFAULT_TITLE)
            .systemName(DEFAULT_SYSTEM_NAME)
            .status(DEFAULT_STATUS);
        return banks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banks createUpdatedEntity(EntityManager em) {
        Banks banks = new Banks()
            .title(UPDATED_TITLE)
            .systemName(UPDATED_SYSTEM_NAME)
            .status(UPDATED_STATUS);
        return banks;
    }

    @BeforeEach
    public void initTest() {
        banks = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanks() throws Exception {
        int databaseSizeBeforeCreate = banksRepository.findAll().size();

        // Create the Banks
        BanksDTO banksDTO = banksMapper.toDto(banks);
        restBanksMockMvc.perform(post("/api/banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banksDTO)))
            .andExpect(status().isCreated());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeCreate + 1);
        Banks testBanks = banksList.get(banksList.size() - 1);
        assertThat(testBanks.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBanks.getSystemName()).isEqualTo(DEFAULT_SYSTEM_NAME);
        assertThat(testBanks.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBanksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = banksRepository.findAll().size();

        // Create the Banks with an existing ID
        banks.setId(1L);
        BanksDTO banksDTO = banksMapper.toDto(banks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanksMockMvc.perform(post("/api/banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = banksRepository.findAll().size();
        // set the field null
        banks.setTitle(null);

        // Create the Banks, which fails.
        BanksDTO banksDTO = banksMapper.toDto(banks);

        restBanksMockMvc.perform(post("/api/banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banksDTO)))
            .andExpect(status().isBadRequest());

        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSystemNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = banksRepository.findAll().size();
        // set the field null
        banks.setSystemName(null);

        // Create the Banks, which fails.
        BanksDTO banksDTO = banksMapper.toDto(banks);

        restBanksMockMvc.perform(post("/api/banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banksDTO)))
            .andExpect(status().isBadRequest());

        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = banksRepository.findAll().size();
        // set the field null
        banks.setStatus(null);

        // Create the Banks, which fails.
        BanksDTO banksDTO = banksMapper.toDto(banks);

        restBanksMockMvc.perform(post("/api/banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banksDTO)))
            .andExpect(status().isBadRequest());

        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBanks() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList
        restBanksMockMvc.perform(get("/api/banks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banks.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].systemName").value(hasItem(DEFAULT_SYSTEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBanks() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get the banks
        restBanksMockMvc.perform(get("/api/banks/{id}", banks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banks.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.systemName").value(DEFAULT_SYSTEM_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBanks() throws Exception {
        // Get the banks
        restBanksMockMvc.perform(get("/api/banks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanks() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        int databaseSizeBeforeUpdate = banksRepository.findAll().size();

        // Update the banks
        Banks updatedBanks = banksRepository.findById(banks.getId()).get();
        // Disconnect from session so that the updates on updatedBanks are not directly saved in db
        em.detach(updatedBanks);
        updatedBanks
            .title(UPDATED_TITLE)
            .systemName(UPDATED_SYSTEM_NAME)
            .status(UPDATED_STATUS);
        BanksDTO banksDTO = banksMapper.toDto(updatedBanks);

        restBanksMockMvc.perform(put("/api/banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banksDTO)))
            .andExpect(status().isOk());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
        Banks testBanks = banksList.get(banksList.size() - 1);
        assertThat(testBanks.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBanks.getSystemName()).isEqualTo(UPDATED_SYSTEM_NAME);
        assertThat(testBanks.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();

        // Create the Banks
        BanksDTO banksDTO = banksMapper.toDto(banks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksMockMvc.perform(put("/api/banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBanks() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        int databaseSizeBeforeDelete = banksRepository.findAll().size();

        // Delete the banks
        restBanksMockMvc.perform(delete("/api/banks/{id}", banks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
