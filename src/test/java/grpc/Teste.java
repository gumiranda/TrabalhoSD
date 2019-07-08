/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grpc;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Samsung
 */
public class Teste extends TestCase {

    public Teste(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(Teste.class);
    }
    
    
    public void testApp() throws Exception
    {
        System.out.println("Testando");
    	String[] args = new String[]{"127.0.0.1","59043", "127.0.0.1", "59044", "127.0.0.1", "59045","teste"};
    	Cliente.main(args);
    }  

}
