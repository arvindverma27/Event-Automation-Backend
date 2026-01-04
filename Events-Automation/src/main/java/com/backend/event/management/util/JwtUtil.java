//package com.backend.event.management.util;
//
//import java.util.Date;
//
//import com.backend.event.management.dto.LoginResponse;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//
//public class JwtUtil {
//
//	private static final String SECRET_KEY = "S3jhdf8#HD839jsdfDFsdf_89dfg*hsdfhUH9898HVDHS";
//
//
//
//        public static String generateToken(int userId, String role) {
//            long expiration = 1000 * 60 * 60 * 10; // 10 hours
//
//            return Jwts.builder()
//                    .claim("id", userId)
//                    .claim("role", role)
//                    .setIssuedAt(new Date())
//                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                    .compact();
//        }
//    public static String extractRole(String token) {
//        return (String) Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody()
//                .get("role");
//    }
//
//    public static boolean isValidToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
