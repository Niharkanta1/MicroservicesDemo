package com.nihar.microservices.currencyexchangeservice;

import com.nihar.microservices.currencyexchangeservice.bean.ExchangeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository repository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue getExchangeValue(@PathVariable String from, @PathVariable String to) {
        logger.info("Fetching Exchange Values from::{} to::{}", from, to);
        ExchangeValue value = repository.findByFromAndTo(from, to);
        if(value == null) {
            throw new RuntimeException("Unable to find the Data for from::"+from+ " to::"+to);
        }
        value.setEnvironment(environment.getProperty("local.server.port"));
        return value;
    }
}
