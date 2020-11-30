package com.smartwf.sm.config.util;

import java.util.Date;
import java.util.Objects;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class HS256Utils {
	
	 /**
     * 创建秘钥
     */
    private static final byte[] SECRET = "6MNSobSMARTWFO0fS6MNSobBRCHGIO0fS".getBytes();

    /**
     * 过期时间120秒
     */
    private static final long EXPIRE_TIME = 1000 * 120;


    /**
     * 生成Token
     * @param account
     * @return
     */
    public static String buildJWT() {
        try {
            /**
             * 1.创建一个32-byte的密匙
             */
            MACSigner macSigner = new MACSigner(SECRET);
            /**
             * 2. 建立payload 载体
             */
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject("iot")
                    .issuer("https://portal.windmagics.com")
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .claim("ACCOUNT","ACCOUNT")
                    .build();
            /**
             * 3. 建立签名
             */
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(macSigner);

            /**
             * 4. 生成token
             */
            String token = signedJWT.serialize();
            return token;
        } catch (KeyLengthException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    /**
     * 校验token
     * @param token
     * @return
     * @throws java.text.ParseException 
     * @throws JOSEException 
     */
    public static boolean vaildToken(String token ) {
    	try {
    		SignedJWT jwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET);
            //校验是否有效
            if (!jwt.verify(verifier)) {
                return false;
            }
            //校验超时
            Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
            if (new Date().after(expirationTime)) {
                return false;
            }
            //获取载体中的数据
            Object account = jwt.getJWTClaimsSet().getClaim("ACCOUNT");
            //是否有openUid
            if (Objects.isNull(account)){
                return false;
            }
            return true;
		} catch (Exception e) {
			//e.printStackTrace();
		}
        return false;
    }

}
