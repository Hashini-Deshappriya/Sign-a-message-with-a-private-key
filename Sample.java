package org.sample;

import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import java.util.Map;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;


import java.util.ArrayList;

/**
 * This class is a sample to obtain private key. You need to customize the logic as required.
 */
public class Sample extends AbstractMediator {

    public boolean mediate(MessageContext messageContext) {

       try {
            Map headers = (Map) ((Axis2MessageContext) messageContext).getAxis2MessageContext()
                    .getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);


            String path = (headers.get("Home_path").toString()) + "/repository/resources/security/";

            log.info(path);




           FileInputStream is = new FileInputStream(path+"your_keystore.p12");
           KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
           keystore.load(is, "wso2carbon".toCharArray());

           String alias = "newcert";
           Key key = keystore.getKey(alias, "wso2carbon".toCharArray());
           if (key instanceof PrivateKey) {
               // Get certificate of public key
               Certificate cert = keystore.getCertificate(alias);

               // Get public key
               PublicKey publicKey = cert.getPublicKey();

               // Return a key pair
               new KeyPair(publicKey, (PrivateKey) key);
           }


        } catch (Exception e) {
            log.error("Error while logging TRP Headers", e);
            return false;
        }
        return true;



    }


}

