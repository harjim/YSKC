package com.yskc.rs.utils;

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
 * @ProjectName: rs-service
 * @Package: com.yskc.rs.utils;
 * @ClassName: TransactionUtil
 * @Description: 数据库事务处理
 * @Author: xiaohuzi
 * @CreateDate: 2019/3/28 16:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/3/28 16:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Component
public class TransactionUtils implements ApplicationContextAware {
    private static  ApplicationContext context;
    private static DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 获得事务状态
     * 默认 transactionManager
     * @return
     */
    public static TransactionStatus newTransaction(){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        String name=UUID.randomUUID().toString();
        def.setName(name );
        // 事物隔离级别，开启新事务，这样会比较安全些。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 获得事务状态
        TransactionStatus status = dataSourceTransactionManager.getTransaction(def);
        return status ;
    }

    /**
     * 提交事务
     * 默认 transactionManager
     * @param status 事务状态
     */
    public static void commit(TransactionStatus status) {
        if ( status != null) {
            dataSourceTransactionManager.commit(status);
        }
    }

    /**
     * 回滚事务
     * 默认 transactionManager
     * @param status 事务状态
     */
    public static void rollback(TransactionStatus status) {
        if( status != null) {
            dataSourceTransactionManager.rollback(status);
        }
    }

    /**
     * 获取上下文
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        dataSourceTransactionManager = (DataSourceTransactionManager) applicationContext
                .getBean("transactionManager");
    }
}