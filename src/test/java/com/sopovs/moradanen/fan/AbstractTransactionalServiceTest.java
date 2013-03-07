package com.sopovs.moradanen.fan;

import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TransactionConfiguration(defaultRollback = true)
public abstract class AbstractTransactionalServiceTest extends AbstractServiceTest {

}