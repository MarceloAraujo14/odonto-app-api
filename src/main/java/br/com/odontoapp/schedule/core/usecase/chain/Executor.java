package br.com.odontoapp.schedule.core.usecase.chain;

public interface Executor<T> {

    T execute(T input);
}
