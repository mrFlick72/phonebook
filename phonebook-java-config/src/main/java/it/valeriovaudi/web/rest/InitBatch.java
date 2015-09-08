package it.valeriovaudi.web.rest;

import it.valeriovaudi.support.DefaultUserStarterSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Valerio on 31/03/2015.
 */
@RestController
@RequestMapping("/service/initBatch")
public class InitBatch {

    @Autowired
    private DefaultUserStarterSupport defaultUserStarterSupport;

    @RequestMapping
    public ResponseEntity<Void> init(){
        System.out.println("init");
        defaultUserStarterSupport.init();
        return ResponseEntity.ok().build();
    }
}
