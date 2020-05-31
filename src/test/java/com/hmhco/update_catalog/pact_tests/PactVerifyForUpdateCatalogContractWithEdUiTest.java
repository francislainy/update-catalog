package com.hmhco.update_catalog.pact_tests;

import au.com.dius.pact.provider.junit.*;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.apache.http.HttpRequest;
import org.junit.runner.RunWith;

@RunWith(PactRunner.class)
@Provider("UPDATE-CATALOG")
@Consumer("ED-UI")
@PactBroker(host = "pact-broker-hmh.devel.hmheng-qe.brnp.internal", consumers = {"ED-UI"})
public class PactVerifyForUpdateCatalogContractWithEdUiTest {

    private String longLastingSifTokenToken;

    @State("v1/updates")
    public void sampleState() {
        longLastingSifTokenToken = "SIF_HMACSHA256 QUtuMVY4SHBzd3JUZDVMLmhtaGNvLmNvbTpoWlF0VHhWaml5Sk5pNTMyTzBsV053NFdad2t2MFVvVWIrU3FBd3h2OHNnPQo=";
    }

    @TestTarget
    public final Target target = new HttpTarget("https", "api.cert.eng.hmhco.com/update-catalog", 443);

    @TargetRequestFilter
    public void exampleRequestFilter(HttpRequest request) {
        request.addHeader("Authorization", longLastingSifTokenToken);
    }
}
