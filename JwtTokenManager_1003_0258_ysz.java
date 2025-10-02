// 代码生成时间: 2025-10-03 02:58:22
package com.example.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Date;
import play.mvc.Controller;
import play.mvc.Result;

public class JwtTokenManager extends Controller {

    private static final String SECRET_KEY = "your-secret-key"; // Replace with your secret key
    private static final long EXPIRATION_TIME = 3600L; // Token expiration in seconds

    public Result createToken(String username) {
        try {
            // Create the token
            String token = JWT.create()
                    .withIssuer("YourIssuer")
                    .withClaim("username", username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000))
                    .sign(Algorithm.HMAC256(SECRET_KEY));

            // Return the token as a response
            return ok(token);
        } catch (Exception e) {
            // Handle any exceptions that occur during token creation
            return internalServerError("Error creating token: " + e.getMessage());
        }
    }

    public Result verifyToken(String token) {
        try {
            // Verify the token
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("YourIssuer")
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            // Return the decoded token as a response
            return ok(jwt.toString());
        } catch (JWTVerificationException exception) {
            // Handle token verification exceptions
            return unauthorized("Invalid token: " + exception.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions that occur during token verification
            return internalServerError("Error verifying token: " + e.getMessage());
        }
    }

    // Additional methods for token refresh or revocation can be added here

}
