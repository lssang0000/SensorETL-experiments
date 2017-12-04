package com.ssu.ssetl;

import com.ssu.ssetl.collector.Collector;
import com.ssu.ssetl.emsys.Emsys;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Collector start" );
//        Emsys em = new Emsys(Constants.ANALYZER_HEART);     
//        Emsys em = new Emsys(Constants.ANALYZER_MARKETING);   
        Emsys em = new Emsys(Constants.ANALYZER_SSU);
        em.sendAll( Collector.getInstance());
        System.out.println("runtime : "+em.getRunTime() + " ms");
    }
}
        
