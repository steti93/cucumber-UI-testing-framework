package com.steti.hooks;

import com.steti.CucumberConfigurationDEMO;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CucumberConfigurationDEMO.class})
@Ignore
public class AbstractSpringTest {
}
