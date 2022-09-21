package com.yskc.docservice.utils;

import com.yskc.common.enums.DataSourceEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.UUID;

/**
 *数据库事务处理
 * @author huronghua
 */
@Component
public class TransactionUtils implements ApplicationContextAware {
    private static DataSourceTransactionManager rsDataSourceTransactionManager;
    private static DataSourceTransactionManager msDataSourceTransactionManager;
    /**
     * 获得事务状态
     * 默认 transactionManager
     * @return
     */
    public static TransactionStatus newTransaction(DataSourceEnum dataSourceEnum){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        String name= UUID.randomUUID().toString();
        def.setName(name );
        // 事物隔离级别，开启新事务，这样会比较安全些。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 获得事务状态
        if(dataSourceEnum== DataSourceEnum.RS){
            return rsDataSourceTransactionManager.getTransaction(def);
        }else {
            return msDataSourceTransactionManager.getTransaction(def);
        }
    }

    /**
     * 提交事务
     * 默认 transactionManager
     * @param status 事务状态
     */
    public static void commit(DataSourceEnum dataSourceEnum, TransactionStatus status) {
        if ( status != null) {
            if(dataSourceEnum== DataSourceEnum.MS){
                msDataSourceTransactionManager.commit(status);
            }else {
                rsDataSourceTransactionManager.commit(status);
            }
        }
    }

    /**
     * 回滚事务
     * 默认 transactionManager
     * @param status 事务状态
     */
    public static void rollback(DataSourceEnum dataSourceEnum, TransactionStatus status) {
        if( status != null) {
            if(dataSourceEnum== DataSourceEnum.MS){
                msDataSourceTransactionManager.rollback(status);
            }else {
                rsDataSourceTransactionManager.rollback(status);
            }
        }
    }

    /**
     * 获取上下文
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        rsDataSourceTransactionManager = (DataSourceTransactionManager) applicationContext
                .getBean(DataSourceEnum.RS.getBeanName());
        msDataSourceTransactionManager = (DataSourceTransactionManager) applicationContext
                .getBean(DataSourceEnum.MS.getBeanName());
    }
}