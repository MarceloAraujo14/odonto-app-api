package br.com.odontoapp.schedule.core.usecase.chain;

import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public class ExecutorChain<T> {

    @Singular
    List<Executor<T>> chains;

    public T execute(final T input){
        chains.forEach(operation -> operation.execute(input));
        return input;
    }
}
