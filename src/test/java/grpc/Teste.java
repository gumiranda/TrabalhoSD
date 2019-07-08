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

    public Teste( String testName ) {
      super(testName);
    }

    public static Test suite(){
      return new TestSuite(Teste.class);
    }

   
}
