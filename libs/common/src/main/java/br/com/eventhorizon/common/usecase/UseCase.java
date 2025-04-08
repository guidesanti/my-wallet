package br.com.eventhorizon.common.usecase;

import java.util.concurrent.Future;

public interface UseCase<INPUT, OUTPUT> {

    Future<OUTPUT> call(INPUT input);
}
