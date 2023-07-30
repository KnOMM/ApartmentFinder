
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.zip.GZIPInputStream;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String cookie = "seastate=TGFzdFNlYXJjaA==:ZmFsc2UsMTY4OTI2OTc1Nzk3MiwvZGUvaGFtYnVyZy9oYW1idXJnL3dvaG51bmctbWlldGVuP251bWJlcm9mcm9vbXM9My4wLTUuMCZwcmljZT0tMTEwMC4wJmxpdmluZ3NwYWNlPTUwLjAtJnByaWNldHlwZT1yZW50cGVybW9udGg=; feature_ab_tests=DiscoveryTests@16=Default; reese84=3:CJK9ZodPEiftsvREwVrtzQ==:jrEzxR+JTRdKze2ZOvQY5rBtObvd8F8atFJz0E2vk7hedqxOXoaDCKE9CxqUl4std5HhyrKNvTmFaSdibOCG/KMq3fpo713MThWa0eGYKyYQQ62obIlAJ6O17TjadOmnHor66W8A4PXSNVf9kWZ1ZdvODvko1iFCzEJkpnaTX2o0OKu+ZGyDpHqP6RpjUMBGjzNWWzD745h2DqCPHyTPPZUY18mxCCCLnlw0cgeJf/02jBN2FqhLk/NGQM16Kwdb1RaOFysGnj2XtGceb36t862S6dCXQVLSuF9Dwb6yGw0apCwQvwxCXDBcjOe/9vYDjG0pL2hsfRukAoeSpWI9tMwy84j7Nx63mTFlLbuBl+FH2uei5A0r/MiIX82O3Vd8PdKWLjomtNKi2gzf5giVNYNJn+7KA412B4AzNaTdSn0N4UaF4QrTbPVgKgOComQ5lxlU0GWfO/zZPwlbl8ZyVDE+U2Pt+wApxHnszqOwi9RmHBTGh/MNT9aVfPxeAHwUnqMYei9K0e5T/TGcthmnIAsKDjHxl4KvovmRbQW55FI=:8kwoMpqYaaEOA5iSXAgxz4Dmq0/IPv7dnC0wz76Rl6I=; is24_search_experiment_visitor_id=4812cd42-5d71-44c1-b24f-356afa5a4713; longUnreliableState=\"dWlkcg==:MTI5MzcxMzU3\"; utag_main=v_id:018946de4307000fe0b17c4d569b0504e00b701100bd0$_sn:6$_se:2$_ss:0$_st:1689274279359$psn_usr_login_cookie:true%3Bexp-1720788558758$cdp_ab_splittest:GroupA$dc_visit:5$ses_id:1689272472385%3Bexp-session$_pn:1%3Bexp-session$referrer_to_lp:%3Bexp-session$geo_bln_session:hamburg%3Bexp-session$geo_krs_session:hamburg%3Bexp-session$ga_cd_application_requirements:profile0%3Bexp-session$ga_cd_userrole_profile_status:basic%3Bexp-session$obj_listFirstStatus:false%3Bexp-session$obj_privateOffer:false%3Bexp-session$ga_cd_cxp_referrer:RESULT_LIST_LISTING%3Bexp-session; _pbjs_userid_consent_data=4434006396307405; _tq_id.TV-27098109-1.3d49=8676a3ff5ae6016e.1689111121.0.1689272475..; _gcl_au=1.1.2074651774.1689111121; _ga_BFT95EK8KY=GS1.1.1689272479.7.1.1689272480.0.0.0; _ga=GA1.1.1117753730.1689111122; AWSALB=8CoLc/E8QHQXpJQJGDLYqrRKEPxtBHOcJLNrSkm9EF7lM6GfUQgOnwcGEzEX1O8a2D3ACW8OZuFXdeA1t1hhkduED7JHQ1rOSceD4qrY3k2p29L6aFhGYOVIeTVz; AWSALBCORS=8CoLc/E8QHQXpJQJGDLYqrRKEPxtBHOcJLNrSkm9EF7lM6GfUQgOnwcGEzEX1O8a2D3ACW8OZuFXdeA1t1hhkduED7JHQ1rOSceD4qrY3k2p29L6aFhGYOVIeTVz; IS24VisitId=vid78ea5f69-fa7f-4ae0-8174-7c1ff50edd78; is24_experiment_visitor_id=8075ad6c-1fb9-4fbc-aea8-fe51d178fa49; ABNTEST=1689245639; _lr_sampling_rate=0; ssoOptimizelyUniqueVisitorId=7411ef89-973f-4403-af3c-257ab35c67fa; is24_experiment_reporting=expo-2277_price_reduction%3Dexpo-2277-price-reduction-off; IS24VisitIdSC=sid0eddaa88-ee49-49f0-9c8a-d3a39cdcbd3b; websessionid=10967AE5C217FE0EB7E9F59AB644827A; SSO=KhcAAAEgS9077zHMpN90kcFO1qsHtQ5MySdoQGgi3MN0gIdEnQy2NciFnxDDMuUnNB7PyhWwIcIrNaQRHnCpaGZGbvH-7-gXCjseB-oqUbHZvdbGWKMf6aCQJXKsbsRsw_eHQLOnM6eiciwjZn9OKd6xtaWYAfH9FMye_AD0Aw5PjDk1zvJJXi4i2YLdVQnp0t6lclWPccLQHXHiNpk0fwf4srJzuoJ2wmdgztzyG-ybpo2r9HWDytQO-3pl8LvpdG_cTNoZaiQ11z2299fd733Ts77Bnydjfg86Igk8hMBc-7BPUJrljXxEAexZCDzB0ijTwIW1HpMPixcoHaYwQ3ShVfQ0sKjzo-ae4LGCoFbnNaICrxOXRZaeaNWQhx5o_h14A3z4AAAADmtleS0wNS0wNi0yMDEzAAABAJxqddKyoJUZ_e9dWiLBtPr9_oP0Lq3NMi63r0xViCstv6JY8UEPL5MiPKGuWh4tc74-qg48NkbDeMUkt6OnfNtM1PFgpCko-EGw6mg78I9gfeMgP6tzbv72ElwZORLgG6NCaiOfuLoayL3tzq3ZYOMFBApzNMfDlUXf0kwVE5wN7QSWl28vzSdOXjKiYJpxE84rUtTPgzsGnIO3_18TahwGe4u83kTuyEQf5lNByjccPTU-NT8c9wJeCfwfcbpBjedewmo-4hDc-SONx06hsvBTpLcnSnSxRYMgf1N6VYMYz1ow3Z6AEJISvS8FA-mAH2QBZ7fDtP_hG4iquNDpHg0AAAAOa2V5LTA2LTA2LTIwMTMAAAEAY1nJf4KmSu6pynEhiGFX6eMjOUNzLSeVxPtbosaDEEpGe7-wmvejr2bl0aCRnJZaxl4UY1RZO0xcghNVbw3qZZA7vQfv87oKRctsEl98vHctJ1ENVkW2c8atc598CL66bRq8WR3tlQiY3q0dmmMZEA9-NcmEPUZwpRtVSjN5mk-45aLym5peoN9MxXnnmUdH05e-L5qPTmDFbAEyGQrZHFjS__8Bg726D3vXJBP3yNW07kJ2F3kwysynHN6RtwLKkjhk0wAvSvDZklRuo0SnRpLdsRtFyjLt1VsaWHDx3bzB8n61mZvUgXN7Glg7vo4xsDI21KLmNp422thXXjD4UQ; SSO-HMAC=Nb6nQgW2JpoUockiPHsSLj1QchdG9UD4u1l-FNIbBE4; user-info=eyJzc29JZCI6MCwiZW1haWwiOiJ2aXJhLnpodWhhbi5kZUBnbWFpbC5jb20iLCJjYW5IaWRlRHBhQ2hlY2tib3giOnRydWV9; properSessionStorage=eyJ1dWlkIjoiNmMyMzMwMTctMWZhZi00ZWY0LWI4NDAtMTdiYzA5OWNhNDJjIiwiZGVwdGgiOjEsInJlZmVycmVyIjoiIiwiZ2NsaWQiOiIiLCJmYmNsaWQiOiIiLCJ1dG1fY2FtcGFpZ24iOiIiLCJ1dG1fc291cmNlIjoiIiwidXRtX21lZGl1bSI6IiIsInV0bV90ZXJtIjoiIiwidXRtX2NvbnRlbnQiOiIiLCJ1dG1fdGVtcGxhdGUiOiIiLCJ1dG1fcmVmZXJyZXIiOiIiLCJ1dG1fYWRzZXQiOiIiLCJ1dG1fc3ViaWQiOiIiLCJyZXZlbnVlIjowLCJiaWRfYXZnIjp7fSwibm9fYmlkX2NudCI6e30sImF1Y3Rpb25fY291bnQiOjAsImxhc3RfdGhyZXNob2xkIjowfQ%3D%3D; _uetsid=5d83bae0201e11ee9d244fc5d2e67770; _uetvid=a5ed18001a8511ee8386ff4a3aecde98";

        String url = "https://www.immobilienscout24.de/expose/144085272#/basicContact/email";

        try {
            // Create an HTTP client
            HttpClient httpClient = HttpClient.newHttpClient();

            // Create the JSON payload
            String jsonPayload = "{\"sendUserProfile\":true,\"suspiciousRequest\":false,\"personalData\":{\"salutation\":\"FEMALE\",\"firstName\":\"Vira\",\"lastName\":\"Zhuhan\",\"phoneNumber\":\"+49 160 95026706\",\"emailAddress\":\"vira.zhuhan.de@gmail.com\",\"postcode\":\"21107\",\"city\":\"Hamburg\",\"street\":\"Dorothea-Gartmann-Straße\",\"houseNumber\":\"1\",\"message\":\"Sehr geehrte Damen und Herren, \\nhiermit möchte ich mich für die Wohnung...... bewerben. \\nWir kommen aus der Ukraine (Stadt Saporischschja) und haben vor in Hamburg arbeiten und leben. Mein Mann ist LKW- Fahrer und ich bin von Beruf Gutachterin. Wir haben 3 Kinder (7, 11 und 14 Jahre alt), die gut erzogen sind. Wir achten auf die Sauberkeit und haben eine Sphynx-Katze,  eine haarlose kanadische Katzenrasse. Die Miete und Kaution werden vollständig vom Jobcenter übernommen. \\nIch würde mich über einen Besichtigungstermin sehr freuen.\\nMit freundlichen Grüßen \\nVira Zhuhan\",\"counterOfferMessage\":\"\",\"numberOfPersons\":\"FAMILY\",\"moveInDate\":null,\"moveInDateType\":\"FROM_NOW\",\"petsInHousehold\":\"Sphynx-Katze,  eine haarlose kanadische Katzenrasse\",\"employmentRelationship\":\"OTHER\",\"income\":\"OVER_2000_UPTO_3000\",\"applicationPackageCompleted\":false,\"profileType\":\"BASIC\",\"dataProtectionAlreadyAccepted\":false,\"homeOwnerMessage\":null,\"hasPets\":true,\"image\":null,\"promotionCode\":null,\"registrationDisabled\":false,\"streetAndHouseNumber\":\"Dorothea-Gartmann-Straße 1\",\"extendedFieldSetNotEmpty\":true},\"nonceToken\":\"MTY4OTI1MzIzMTgwMTpjYjc3ZmY3MjBlZTU0ZWM2MDQ0MjIzNGY5NGU0ZDFkYzZlYzRjYmQ3NzE4MmQxMzQ1MmM4MDg5MmE4NTE5YTAwOjE0NDE0NzU3OSwwOmQxOTQ5ZTI5ZWQ4YjI5ZjM4MjQ5MjUxYWE1NzNmZjIyOTc5YWRjMDhmNDkzNmY3YmRkNmY0NDQwZjc2NzllZDAzY2MzM2NkNmIxMTZiMzFmNGJlYTEwYmU3ZTY3NjViZGJjNGY4YmI3YWE0OTFkYzYxZWVkYmFiZmZlODhkYTUw\",\"showPrivacyPolicyAcceptanceCheckbox\":false,\"captchaData\":{\"captchaId\":\"\",\"userAnswer\":\"\"},\"sendButtonDelay\":0,\"contactType\":\"basic\",\"editingUserProfile\":false,\"isFromCounterOfferModal\":false,\"userProfileExists\":true}";

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Cookie", cookie)
                    .header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/114.0")
                    .header("Accept", "application/json, text/plain, */*")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Content-Type", "application/json;charset=utf-8")
                    .header("Origin", "https://www.immobilienscout24.de")
                    .header("Dnt", "1")
                    .header("Referer", "https://www.immobilienscout24.de/expose/144147579?referrer=RESULT_LIST_LISTING&searchId=6493d697-5e1e-38f5-930f-6d5c40c6e5f5&searchType=district")
                    .header("Sec-Fetch-Dest", "empty")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .header("Te", "trailers")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Send the HTTP request and get the response
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

            // Get the response body and headers
            HttpHeaders responseHeaders = response.headers();

            // Print the response status code and body
            System.out.println("Response Status Code: " + response.statusCode());

            byte[] responseBodyBytes = response.body();
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(responseBodyBytes));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream))) {

                // Read the decompressed response body
                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                // Print the decompressed response body
                System.out.println("Decompressed Response Body: " + responseBody.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        // Set request body
//            String requestBody = "{\"sendUserProfile\":true,\"suspiciousRequest\":false,\"personalData\":{\"salutation\":\"MALE\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"phoneNumber\":null,\"emailAddress\":\"zaranunknown@gmail.com\",\"postcode\":\"d\",\"city\":\"dd\",\"street\":\"dd\",\"houseNumber\":\"d\",\"message\":null,\"counterOfferMessage\":\"\",\"profileType\":\"BASIC\",\"dataProtectionAlreadyAccepted\":true,\"homeOwnerMessage\":null,\"image\":null,\"promotionCode\":null,\"registrationDisabled\":false,\"streetAndHouseNumber\":\"dd d\",\"extendedFieldSetNotEmpty\":true},\"nonceToken\":\"MTY4ODEzMTUxMjcwMzoxNjg0YzNiNTI3OWNhZmZlNzlkMzhhMzc4YjI4ZTY3ZGM5OTY5N2ViYThkZjYwNmEyZWIzMGJmNWVkODU0OGVjOjcyODcxOTc2LDA6MGMzOTcyODNlNTIzMWJjZGY4MWIyYWFkODdiZDcwMzZhYzQxNTI4MmMxZjlhMWJmYmNhMmVhNzBkYTgxZWMyYWRiNmYzZGRhZmE0ZWZhZmJkYWRiYzIxYmRiMDRkYTZhNGZmY2IwNjUxYjZhMjM3ZDc4ZjA4MjVkMjM1ZWRhMDM=\",\"showPrivacyPolicyAcceptanceCheckbox\":false,\"captchaData\":{\"captchaId\":\"\",\"userAnswer\":\"\"},\"sendButtonDelay\":0,\"contactType\":\"basic\",\"editingUserProfile\":true,\"isFromCounterOfferModal\":false,\"userProfileExists\":true}";

}
