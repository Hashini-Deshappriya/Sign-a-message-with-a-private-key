package org.wso2.tests;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

public class SignMessage extends AbstractMediator {


	public boolean mediate(MessageContext messageContext) {
		//You can call the signJWT() and perform the message signing
		return false;
	}

	public byte[] signJwt(String assertion, PrivateKey privateKey, String signatureAlgorithm){

		try {
			//initialize signature with private key and algorithm
			Signature signature = Signature.getInstance(signatureAlgorithm);
			try {
				signature.initSign(privateKey);
			} catch (InvalidKeyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			//update signature with data to be signed
			byte[] dataInBytes = assertion.getBytes(Charset.defaultCharset());
			try {
				signature.update(dataInBytes);
			} catch (SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//sign the assertion and return the signature
			try {
				return signature.sign();
			} catch (SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {

		}
		//do not log
		return null;
	}
}
