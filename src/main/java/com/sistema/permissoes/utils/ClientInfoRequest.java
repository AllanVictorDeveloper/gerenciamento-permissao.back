package com.sistema.permissoes.utils;

import jakarta.servlet.http.HttpServletRequest;

public class ClientInfoRequest {
    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // Se o IP for passado como uma lista (caso de múltiplos proxies), pega o primeiro
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        // Converter localhost IPv6 para IPv4
        if ("0:0:0:0:0:0:0:1".equals(ipAddress)) {
            ipAddress = "127.0.0.1";
        }

        return ipAddress;
    }


    public static String getClientBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            return "Desconhecido";
        }

        String browser = "Desconhecido";
        String version = "";

        // Identificação do navegador e extração da versão
        if (userAgent.contains("Chrome") && !userAgent.contains("Edg")) { // Exclui Edge baseado no Chromium
            browser = "Chrome";
            version = extractVersion(userAgent, "Chrome/");
        } else if (userAgent.contains("Firefox")) {
            browser = "Firefox";
            version = extractVersion(userAgent, "Firefox/");
        } else if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) { // Exclui Chrome
            browser = "Safari";
            version = extractVersion(userAgent, "Version/");
        } else if (userAgent.contains("Edg")) {
            browser = "Edge";
            version = extractVersion(userAgent, "Edg/");
        } else if (userAgent.contains("Opera") || userAgent.contains("OPR")) {
            browser = "Opera";
            version = extractVersion(userAgent, userAgent.contains("OPR") ? "OPR/" : "Opera/");
        } else if (userAgent.contains("Trident") || userAgent.contains("MSIE")) {
            browser = "Internet Explorer";
            version = extractVersion(userAgent, userAgent.contains("MSIE") ? "MSIE " : "rv:");
        }

        // Retorno formatado
        return browser + (version.isEmpty() ? "" : " (versão: " + version + ")");
    }

    public static String getClientOperatingSystem(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            return "Desconhecido";
        }

        // Verificar alguns sistemas operacionais comuns
        if (userAgent.contains("Windows NT 10.0")) {
            return "Windows 10";
        } else if (userAgent.contains("Windows NT 6.3")) {
            return "Windows 8.1";
        } else if (userAgent.contains("Windows NT 6.2")) {
            return "Windows 8";
        } else if (userAgent.contains("Windows NT 6.1")) {
            return "Windows 7";
        } else if (userAgent.contains("Mac OS X")) {
            return "macOS";
        } else if (userAgent.contains("Linux")) {
            return "Linux";
        } else if (userAgent.contains("Android")) {
            return "Android";
        } else if (userAgent.contains("iPhone")) {
            return "iOS (iPhone)";
        } else if (userAgent.contains("iPad")) {
            return "iOS (iPad)";
        }

        return "Sistema Operacional Desconhecido";
    }

    // Função auxiliar para extrair a versão do navegador
    private static String extractVersion(String userAgent, String token) {
        try {
            int startIndex = userAgent.indexOf(token);
            if (startIndex != -1) {
                startIndex += token.length();
                int endIndex = userAgent.indexOf(" ", startIndex);
                if (endIndex == -1) {
                    endIndex = userAgent.length();
                }
                return userAgent.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            return ""; // Retorna vazio caso ocorra erro
        }
        return "";
    }
}
