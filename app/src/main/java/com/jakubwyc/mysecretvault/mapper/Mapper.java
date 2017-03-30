package com.jakubwyc.mysecretvault.mapper;


public interface Mapper<F,T> {

    T map(F data);

}
