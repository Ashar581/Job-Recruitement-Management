package com.ashar.job.recruitment.management.Util;

public class HtmlUtil {

    public static String generateJobApplicationsHtml(String name, String status, String role) {

        String statusColor;
         if ("Hold".equalsIgnoreCase(status)) {
            statusColor = "orange";
        } else if ("Applied".equalsIgnoreCase(status)) {
            statusColor = "blue";
        } else if ("selected".equalsIgnoreCase(status)) {
            statusColor = "green";
        } else {
            statusColor = "red";
        }

        String htmlTemplate = "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Job Application Status</title>"
                + "    <style>"
                + "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }"
                + "        .container { background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 80%; max-width: 600px; margin: auto; }"
                + "        h1 { color: #333; text-align: center; }"
                + "        .status { font-size: 18px; font-weight: bold; margin-top: 20px; }"
                + "        .role { font-size: 16px; margin-top: 10px; }"
                + "        .footer { text-align: center; font-size: 14px; color: #888; margin-top: 30px; }"
                + "        .footer a { color: #555; text-decoration: none; }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"container\">"
                + "        <h1>Job Application Status</h1>"
                + "        <p>Dear <strong>" + name + "</strong>,</p>"
                + "        <p>We would like to inform you that your application for the position of <strong>" + role + "</strong> has been processed.</p>"
                + "        <div class=\"status\">"
                + "            Status: <span style=\"color: " + statusColor + "\">" + status + "</span>"
                + "        </div>"
                + "        <div class=\"role\">"
                + "            Role: <strong>" + role + "</strong>"
                + "        </div>"
                + "        <div class=\"footer\">"
                + "            <p>Thank you for applying to our company. We will keep you updated on the next steps.</p>"
                + "            <p>Best regards, <br> The Recruitment Team</p>"
                + "        </div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        return htmlTemplate;
    }

    public static String generateUsernameAndPasswordHtml(String email, String password) {
        // HTML template with placeholders for email and password
        String htmlTemplate = "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Account Credentials</title>"
                + "    <style>"
                + "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }"
                + "        .container { background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 80%; max-width: 600px; margin: auto; }"
                + "        h1 { color: #333; text-align: center; }"
                + "        .content { font-size: 16px; margin-top: 20px; }"
                + "        .details { margin-top: 20px; background-color: #f9f9f9; padding: 10px; border-radius: 5px; border: 1px solid #ddd; }"
                + "        .footer { text-align: center; font-size: 14px; color: #888; margin-top: 30px; }"
                + "        .footer a { color: #555; text-decoration: none; }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"container\">"
                + "        <h1>Welcome to Our Job Portal!</h1>"
                + "        <p class=\"content\">Dear User,</p>"
                + "        <p class=\"content\">We are pleased to inform you that your account has been successfully created. Below are your account credentials:</p>"
                + "        <div class=\"details\">"
                + "            <p><strong>Email (Username):</strong> " + email + "</p>"
                + "            <p><strong>Password:</strong> " + password + "</p>"
                + "        </div>"
                + "        <div class=\"footer\">"
                + "            <p>If you did not create this account, please contact us immediately.</p>"
                + "            <p>Best regards, <br> The Team</p>"
                + "        </div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        return htmlTemplate;
    }
}

