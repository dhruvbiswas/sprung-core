package com.sprung.core.runners;

public abstract class CommandLineRunner implements Runner {

    @Override
    public abstract void run(String[] args) throws Exception;

}
