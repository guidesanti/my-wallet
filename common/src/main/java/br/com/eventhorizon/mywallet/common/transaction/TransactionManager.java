package br.com.eventhorizon.mywallet.common.transaction;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface TransactionManager {

    <T> T transact(Callable<T> task) throws Exception;
}
