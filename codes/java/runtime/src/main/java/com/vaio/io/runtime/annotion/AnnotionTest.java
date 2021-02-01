package com.vaio.io.runtime.annotion;

public class AnnotionTest {
    @VaioAnnotionWithArgs(age = 2, name = "vaio")
    public  void  getInfo(){

    }

    @VaioAnnotionWithDefaultArgs
    public void getInfoM(){

    }
}
