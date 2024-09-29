package com.ltweb_servlet_ecommerce.utils;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.LogModel;
import com.ltweb_servlet_ecommerce.model.OrderModel;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendMailUtil {

    /*
     * Mỗi khi gọi phương thức sendMail, một Runnable được gửi đến ExecutorService
     * để thực thi việc gửi email trên một thread riêng biệt. ExecutorService giúp
     * tạo và quản lý các thread để thực hiện công việc gửi email một cách bất
     * đồng bộ
     */
    private static ExecutorService executorService = Executors.newFixedThreadPool(3); // Số lượng thread tùy chọn

    public static void sendMail(String toEmailAddress, String titleMail, String templateMail) {
        executorService.submit(() -> {

            ResourceBundle resourceBundle = ResourceBundle.getBundle("env");
//            String fromEmail = resourceBundle.getString("EMAIL_ADDRESS");
            String username = resourceBundle.getString("EMAIL_ADDRESS");
            String password = resourceBundle.getString("EMAIL_PASSWORD");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "*");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            javax.mail.Session sessionEmail = javax.mail.Session.getInstance(props, new Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(username, password);
                }
            });
            try {
                MimeMessage message = new MimeMessage(sessionEmail);
                message.addHeader("Context-type", "text/HTML; charset=UTF-8");
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress));
                message.setSubject(titleMail, "UTF-8");
                message.setContent(templateMail, "text/html;charset=UTF-8");
                Transport.send(message);
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static String templateMailDanger(LogModel logModel) {
        StringBuilder content = new StringBuilder();
        content.append("<p>Subject: Critical Error Report on Server</p>");
        content.append("<p><strong>Dear Admin,</strong></p>");
        content.append("<p>We regret to inform you that our server has encountered a critical error that requires immediate attention. Below are the details of the error:</p>");
        content.append("<ol>");
        content.append("<li><p><strong>Error:</strong> ").append(logModel.getValue().get(SystemConstant.STATUS_LOG)).append("</p></li>");
        content.append("<li><p><strong>Time of Error:</strong> ").append(logModel.getCreateAt().toString()).append("</p></li>");
        content.append("<li><p><strong>Error Message:</strong> ").append(logModel.getValue().get(SystemConstant.VALUE_LOG)).append("</p></li>");
        content.append("</ol>");
        content.append("<p>Your prompt action is highly appreciated to address this issue and ensure the smooth operation of our website. If further assistance or information is needed, please do not hesitate to contact us.</p>");
        content.append("<p>Thank you for your attention and cooperation.</p>");
        content.append("<p>Sincerely,</p>");
        content.append("<p><strong>Nai Shoes &amp; Sneakers</strong><br /><br /></p>");
        return content.toString();
    }

    public static String templateMailOrderNotProcess(List<OrderModel> list) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        StringBuilder contentHtml = new StringBuilder();
        contentHtml.append("<p><strong>Gửi quản trị viên,</strong></p>");
        contentHtml.append("<p>Tôi hy vọng email này đến được với bạn. Tôi viết thư này để lưu ý bạn về một vấn đề liên quan đến các đơn đặt hàng đang chờ xử lý chưa được xử lý trong hơn năm ngày.");
        contentHtml.append("<p>Báo cáo bao gồm các thông tin liên quan như số đơn đặt hàng, ngày đặt hàng và mọi ghi chú thích hợp.</p>");

        contentHtml.append("<table style=\"width: 505.562px;\">");
        contentHtml.append("<tbody><tr style=\"height: 13px;\"><td style=\"width: 71px; height: 13px;text-align: center;\"><strong>#</strong></td>");
        contentHtml.append("<td style=\"width: 92px; height: 13px; text-align: center;\"><strong>Trạng thái</strong></td>");
        contentHtml.append("<td style=\"width: 172px; height: 13px; text-align: center;\"><strong>Ngày tạo đơn</strong></td>");
        contentHtml.append("<td style=\"width: 180.562px; height: 13px; text-align: center;\"><strong>Trị giá</strong></td>");
        contentHtml.append("</tr>");
        for (OrderModel model : list) {
            contentHtml.append("<tr style=\"height: 13px;\">");
            contentHtml.append("<td style=\"width: 71px; height: 13px;; text-align: center;\">").append(model.getId()).append("</td>");
            contentHtml.append("<td style=\"width: 100px; height: 13px;text-align: center;\">").append(StatusMapUtil.getStatusValue(model.getStatus())).append("</td>");
            contentHtml.append("<td style=\"width: 200px; height: 13px;text-align: center;\">").append(model.getCreateAt().toString()).append("</td>");
            contentHtml.append("<td style=\"width: 180.562px; height: 13px; text-align: center;\">").append(currencyFormat.format(model.getTotalAmount())).append("</td>");
            contentHtml.append("</tr>");
        }
        contentHtml.append("</tbody></table>");
        contentHtml.append("<a href=\"http://localhost:8080/admin/order/list\" target=\"_blank\">Kiểm tra tại đây</a>");
        contentHtml.append("<p>Cảm ơn sự quan tâm và hợp tác của bạn.</p>");
        contentHtml.append("<p>Trân trọng,</p>");
        contentHtml.append("<p><strong>Nai Shoes &amp; Sneakers</strong><br /><br /></p>");
        return contentHtml.toString();
    }

    public static String templateMailContact(String fullName, String email, String message) {
        return "<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                "<head>\n" +
                "<title></title>\n" +
                "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
                "<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]-->\n" +
                "<style>\n" +
                "\t\t* {\n" +
                "\t\t\tbox-sizing: border-box;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tbody {\n" +
                "\t\t\tmargin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\ta[x-apple-data-detectors] {\n" +
                "\t\t\tcolor: inherit !important;\n" +
                "\t\t\ttext-decoration: inherit !important;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t#MessageViewBody a {\n" +
                "\t\t\tcolor: inherit;\n" +
                "\t\t\ttext-decoration: none;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tp {\n" +
                "\t\t\tline-height: inherit\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t.desktop_hide,\n" +
                "\t\t.desktop_hide table {\n" +
                "\t\t\tmso-hide: all;\n" +
                "\t\t\tdisplay: none;\n" +
                "\t\t\tmax-height: 0px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t.image_block img+div {\n" +
                "\t\t\tdisplay: none;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t@media (max-width:520px) {\n" +
                "\t\t\t.desktop_hide table.icons-inner {\n" +
                "\t\t\t\tdisplay: inline-block !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.icons-inner {\n" +
                "\t\t\t\ttext-align: center;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.icons-inner td {\n" +
                "\t\t\t\tmargin: 0 auto;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.mobile_hide {\n" +
                "\t\t\t\tdisplay: none;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.row-content {\n" +
                "\t\t\t\twidth: 100% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.stack .column {\n" +
                "\t\t\t\twidth: 100%;\n" +
                "\t\t\t\tdisplay: block;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.mobile_hide {\n" +
                "\t\t\t\tmin-height: 0;\n" +
                "\t\t\t\tmax-height: 0;\n" +
                "\t\t\t\tmax-width: 0;\n" +
                "\t\t\t\toverflow: hidden;\n" +
                "\t\t\t\tfont-size: 0px;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.desktop_hide,\n" +
                "\t\t\t.desktop_hide table {\n" +
                "\t\t\t\tdisplay: table !important;\n" +
                "\t\t\t\tmax-height: none !important;\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body style=\"background-color: #FFFFFF; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-radius: 0; color: #000000; width: 500px; margin: 0 auto;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td class=\"pad\">\n" +
                "<div style=\"color:#444a5b;direction:ltr;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;font-size:16px;font-weight:400;letter-spacing:0px;line-height:120%;text-align:left;mso-line-height-alt:19.2px;\">\n" +
                "<p style=\"margin: 0;\"><strong>" + fullName + "</strong> just send you contact form</p>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-radius: 0; color: #000000; width: 500px; margin: 0 auto;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td class=\"pad\">\n" +
                "<div style=\"color:#444a5b;direction:ltr;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;font-size:16px;font-weight:400;letter-spacing:0px;line-height:120%;text-align:left;mso-line-height-alt:19.2px;\">\n" +
                "<p style=\"margin: 0;\"><strong>Details message :</strong></p>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-radius: 0; color: #000000; width: 500px; margin: 0 auto;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td class=\"pad\">\n" +
                "<div style=\"color:#444a5b;direction:ltr;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;font-size:16px;font-weight:400;letter-spacing:0px;line-height:120%;text-align:left;mso-line-height-alt:19.2px;\">\n" +
                "<p style=\"margin: 0;\">Fullname : " + fullName + "</p>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-4\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-radius: 0; color: #000000; width: 500px; margin: 0 auto;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td class=\"pad\">\n" +
                "<div style=\"color:#444a5b;direction:ltr;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;font-size:16px;font-weight:400;letter-spacing:0px;line-height:120%;text-align:left;mso-line-height-alt:19.2px;\">\n" +
                "<p style=\"margin: 0;\">Email : " + email + "</p>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-5\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-radius: 0; color: #000000; width: 500px; margin: 0 auto;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td class=\"pad\">\n" +
                "<div style=\"color:#444a5b;direction:ltr;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;font-size:16px;font-weight:400;letter-spacing:0px;line-height:120%;text-align:left;mso-line-height-alt:19.2px;\">\n" +
                "<p style=\"margin: 0;\">Message : " + message + "</p>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-6\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-radius: 0; color: #000000; width: 500px; margin: 0 auto;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"button_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td class=\"pad\">\n" +
                "<div align=\"center\" class=\"alignment\"><!--[if mso]>\n" +
                "<v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"https://mail.google.com/mail/u/0/?fs=1&to=" + email + "&su=Support-Nai%20Shoes%20&%20Sneaker&body=Hi%20" + fullName + ",%0AThank%20you%20for%20reaching%20out%20to%20us,%20and%20we%20appreciate%20your%20interest%20in%20" + fullName + ".%20We%20understand%20that%20placing%20an%20order%20can%20sometimes%20be%20challenging,%20and%20we%20are%20here%20to%20provide%20the%20assistance%20you%20need.%0ABelow,%20I%27ve%20outlined%20a%20simple%20guide&tf=cm\" style=\"height:42px;width:123px;v-text-anchor:middle;\" arcsize=\"10%\" stroke=\"false\" fillcolor=\"#7747ff\">\n" +
                "<w:anchorlock/>\n" +
                "<v:textbox inset=\"0px,0px,0px,0px\">\n" +
                "<center style=\"color:#ffffff; font-family:Arial, sans-serif; font-size:16px\">\n" +
                "<![endif]--><a href=\"https://mail.google.com/mail/u/0/?fs=1&to=" + email + "&su=Support-Nai%20Shoes%20&%20Sneaker&body=Hi%20" + fullName + ",%0AThank%20you%20for%20reaching%20out%20to%20us,%20and%20we%20appreciate%20your%20interest%20in%20" + fullName + ".%20We%20understand%20that%20placing%20an%20order%20can%20sometimes%20be%20challenging,%20and%20we%20are%20here%20to%20provide%20the%20assistance%20you%20need.%0ABelow,%20I%27ve%20outlined%20a%20simple%20guide&tf=cm\" style=\"text-decoration:none;display:inline-block;color:#ffffff;background-color:#7747ff;border-radius:4px;width:auto;border-top:0px solid transparent;font-weight:400;border-right:0px solid transparent;border-bottom:0px solid transparent;border-left:0px solid transparent;padding-top:5px;padding-bottom:5px;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;font-size:16px;text-align:center;mso-border-alt:none;word-break:keep-all;\" target=\"_blank\"><span style=\"padding-left:20px;padding-right:20px;font-size:16px;display:inline-block;letter-spacing:normal;\"><span style=\"word-break: break-word; line-height: 32px;\">Relpy email</span></span></a><!--[if mso]></center></v:textbox></v:roundrect><![endif]--></div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-7\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; width: 500px; margin: 0 auto;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"icons_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td class=\"pad\" style=\"vertical-align: middle; color: #1e0e4b; font-family: 'Inter', sans-serif; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td class=\"alignment\" style=\"vertical-align: middle; text-align: center;\"><!--[if vml]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->\n" +
                "<!--[if !vml]><!-->\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table><!-- End -->\n" +
                "</body>\n" +
                "</html>";
    }

    public static String templateContentMail(String title, String content) {
        return "<!DOCTYPE html>\n" +
                "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "\t<title></title>\n" +
                "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]--><!--[if !mso]><!-->\n" +
                "\t<link href=\"https://fonts.googleapis.com/css?family=Oswald\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "\t<link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\n" +
                "\t<style>\n" +
                "\t\t* {\n" +
                "\t\t\tbox-sizing: border-box;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tbody {\n" +
                "\t\t\tmargin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\ta[x-apple-data-detectors] {\n" +
                "\t\t\tcolor: inherit !important;\n" +
                "\t\t\ttext-decoration: inherit !important;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t#MessageViewBody a {\n" +
                "\t\t\tcolor: inherit;\n" +
                "\t\t\ttext-decoration: none;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tp {\n" +
                "\t\t\tline-height: inherit\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t.desktop_hide,\n" +
                "\t\t.desktop_hide table {\n" +
                "\t\t\tmso-hide: all;\n" +
                "\t\t\tdisplay: none;\n" +
                "\t\t\tmax-height: 0px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t.image_block img+div {\n" +
                "\t\t\tdisplay: none;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t@media (max-width:670px) {\n" +
                "\t\t\t.desktop_hide table.icons-inner {\n" +
                "\t\t\t\tdisplay: inline-block !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.icons-inner {\n" +
                "\t\t\t\ttext-align: center;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.icons-inner td {\n" +
                "\t\t\t\tmargin: 0 auto;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.mobile_hide {\n" +
                "\t\t\t\tdisplay: none;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.row-content {\n" +
                "\t\t\t\twidth: 100% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.stack .column {\n" +
                "\t\t\t\twidth: 100%;\n" +
                "\t\t\t\tdisplay: block;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.mobile_hide {\n" +
                "\t\t\t\tmin-height: 0;\n" +
                "\t\t\t\tmax-height: 0;\n" +
                "\t\t\t\tmax-width: 0;\n" +
                "\t\t\t\toverflow: hidden;\n" +
                "\t\t\t\tfont-size: 0px;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.desktop_hide,\n" +
                "\t\t\t.desktop_hide table {\n" +
                "\t\t\t\tdisplay: table !important;\n" +
                "\t\t\t\tmax-height: none !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.row-4 .column-1 .block-1.paragraph_block td.pad>div {\n" +
                "\t\t\t\tfont-size: 11px !important;\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"margin: 0; background-color: #c3f1ff; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                "\t<table class=\"nl-container\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #c3f1ff; background-repeat: no-repeat; background-image: none; background-position: top left; background-size: auto;\">\n" +
                "\t\t<tbody>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<table class=\"row row-1\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #c3f1ff;\">\n" +
                "\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-position: center top; color: #000000; background-repeat: no-repeat; background-image: url('https://d1oco4z2z1fhwp.cloudfront.net/templates/default/6081/Header_Background_01.png'); width: 650px; margin: 0 auto;\" width=\"650\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-top: 30px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"heading_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-left:10px;padding-right:10px;text-align:center;width:100%;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h2 style=\"margin: 0; color: #0f7085; direction: ltr; font-family: Oswald, Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 24px; font-weight: normal; letter-spacing: 5px; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 28.799999999999997px;\"><span class=\"tinyMce-placeholder\">SEND YOUR</span></h2>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"divider_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"50%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 2px dashed #0F7085;\"><span>&#8202;</span></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"heading_block block-3\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-left:10px;padding-right:10px;text-align:center;width:100%;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h1 style=\"margin: 0; color: #cb0c0c; direction: ltr; font-family: Oswald, Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 64px; font-weight: normal; letter-spacing: 1px; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 76.8px;\"><span class=\"tinyMce-placeholder\">A MAIL</span></h1>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"divider_block block-4\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"50%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 2px dashed #0F7085;\"><span>&#8202;</span></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"heading_block block-5\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-left:10px;padding-right:10px;text-align:center;width:100%;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h2 style=\"margin: 0; color: #0f7085; direction: ltr; font-family: Oswald, Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 24px; font-weight: normal; letter-spacing: 5px; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 28.799999999999997px;\"><span class=\"tinyMce-placeholder\"></span></h2>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"spacer_block block-6\" style=\"height:30px;line-height:30px;font-size:1px;\">&#8202;</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"image_block block-7\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"width:100%;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\" style=\"line-height:10px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"max-width: 650px;\"><img src=\"https://d1oco4z2z1fhwp.cloudfront.net/templates/default/6081/Header_Overlay_01.png\" style=\"display: block; height: auto; border: 0; width: 100%;\" width=\"650\" alt=\"Image Of Love Header\" title=\"Image Of Love Header\"></div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t<table class=\"row row-2\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-position: center top; color: #000000; background-repeat: no-repeat; background-color: #ffffff; background-image: url('https://d1oco4z2z1fhwp.cloudfront.net/templates/default/6081/PliegueSobre_03.png'); width: 650px; margin: 0 auto;\" width=\"650\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 50px; padding-left: 20px; padding-right: 20px; padding-top: 10px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"paragraph_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-left:10px;padding-right:10px;padding-top:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"color:#0f7085;font-family:Oswald, Arial, Helvetica Neue, Helvetica, sans-serif;font-size:20px;letter-spacing:1px;line-height:120%;text-align:center;mso-line-height-alt:24px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin: 0;\">" + title + "</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"paragraph_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-bottom:10px;padding-left:10px;padding-right:10px;padding-top:5px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"color:#4e8c9d;font-family:'Lato',Tahoma,Verdana,Segoe,sans-serif;font-size:16px;line-height:150%;text-align:center;mso-line-height-alt:24px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin: 0;\">" + content + "</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"divider_block block-3\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-bottom:30px;padding-left:10px;padding-right:10px;padding-top:30px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"50%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 2px dashed #0F7085;\"><span>&#8202;</span></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"button_block block-4\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\"><!--[if mso]>\n" +
                "<v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"http://localhost:8080/sign-in\" style=\"height:50px;width:253px;v-text-anchor:middle;\" arcsize=\"0%\" stroke=\"false\" fillcolor=\"#cb0c0c\">\n" +
                "<w:anchorlock/>\n" +
                "<v:textbox inset=\"0px,0px,0px,0px\">\n" +
                "<center style=\"color:#ffffff; font-family:Arial, sans-serif; font-size:20px\">\n" +
                "<![endif]--><a href=\"http://localhost:8080/sign-in\" target=\"_blank\" style=\"text-decoration:none;display:inline-block;color:#ffffff;background-color:#cb0c0c;border-radius:0px;width:auto;border-top:0px solid transparent;font-weight:undefined;border-right:0px solid transparent;border-bottom:0px solid transparent;border-left:0px solid transparent;padding-top:5px;padding-bottom:5px;font-family:Oswald, Arial, Helvetica Neue, Helvetica, sans-serif;font-size:20px;text-align:center;mso-border-alt:none;word-break:keep-all;\"><span style=\"padding-left:20px;padding-right:20px;font-size:20px;display:inline-block;letter-spacing:4px;\"><span style=\"word-break: break-word; line-height: 40px;\">Go Back To Website</span></span></a><!--[if mso]></center></v:textbox></v:roundrect><![endif]--></div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t<table class=\"row row-3\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; width: 650px; margin: 0 auto;\" width=\"650\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"spacer_block block-1\" style=\"height:25px;line-height:25px;font-size:1px;\">&#8202;</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"image_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\" style=\"line-height:10px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"max-width: 130px;\"><img src=\"https://www.transparentpng.com/thumb/nike-logo/UZcQ8m-nike-logo-vector.png\" style=\"display: block; height: auto; border: 0; width: 100%;\" width=\"130\" alt=\"Your Logo Here\" title=\"Your Logo Here\"></div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"spacer_block block-3\" style=\"height:25px;line-height:25px;font-size:1px;\">&#8202;</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t<table class=\"row row-4\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-radius: 0; color: #000000; width: 650px; margin: 0 auto;\" width=\"650\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"paragraph_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"color:#101112;direction:ltr;font-family:Oswald, Arial, Helvetica Neue, Helvetica, sans-serif;font-size:16px;font-weight:400;letter-spacing:2px;line-height:120%;text-align:center;mso-line-height-alt:19.2px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin: 0;\"><span id=\"8d2fd8da-be13-4c61-8d61-71483becc841\">@Copyright by hoanghuydev</span></p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t</table>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</tbody>\n" +
                "\t</table><!-- End -->\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    public static String templateOTPMail(String OTP) {
        return "<!DOCTYPE html>\n" +
                "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "\t<title></title>\n" +
                "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]--><!--[if !mso]><!-->\n" +
                "\t<link href=\"https://fonts.googleapis.com/css?family=Oswald\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "\t<link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\n" +
                "\t<style>\n" +
                "\t\t* {\n" +
                "\t\t\tbox-sizing: border-box;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tbody {\n" +
                "\t\t\tmargin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\ta[x-apple-data-detectors] {\n" +
                "\t\t\tcolor: inherit !important;\n" +
                "\t\t\ttext-decoration: inherit !important;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t#MessageViewBody a {\n" +
                "\t\t\tcolor: inherit;\n" +
                "\t\t\ttext-decoration: none;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tp {\n" +
                "\t\t\tline-height: inherit\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t.desktop_hide,\n" +
                "\t\t.desktop_hide table {\n" +
                "\t\t\tmso-hide: all;\n" +
                "\t\t\tdisplay: none;\n" +
                "\t\t\tmax-height: 0px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t.image_block img+div {\n" +
                "\t\t\tdisplay: none;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t@media (max-width:670px) {\n" +
                "\t\t\t.desktop_hide table.icons-inner {\n" +
                "\t\t\t\tdisplay: inline-block !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.icons-inner {\n" +
                "\t\t\t\ttext-align: center;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.icons-inner td {\n" +
                "\t\t\t\tmargin: 0 auto;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.mobile_hide {\n" +
                "\t\t\t\tdisplay: none;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.row-content {\n" +
                "\t\t\t\twidth: 100% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.stack .column {\n" +
                "\t\t\t\twidth: 100%;\n" +
                "\t\t\t\tdisplay: block;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.mobile_hide {\n" +
                "\t\t\t\tmin-height: 0;\n" +
                "\t\t\t\tmax-height: 0;\n" +
                "\t\t\t\tmax-width: 0;\n" +
                "\t\t\t\toverflow: hidden;\n" +
                "\t\t\t\tfont-size: 0px;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.desktop_hide,\n" +
                "\t\t\t.desktop_hide table {\n" +
                "\t\t\t\tdisplay: table !important;\n" +
                "\t\t\t\tmax-height: none !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.row-4 .column-1 .block-1.paragraph_block td.pad>div {\n" +
                "\t\t\t\tfont-size: 11px !important;\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"margin: 0; background-color: #c3f1ff; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                "\t<table class=\"nl-container\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #c3f1ff; background-repeat: no-repeat; background-image: none; background-position: top left; background-size: auto;\">\n" +
                "\t\t<tbody>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<table class=\"row row-1\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #c3f1ff;\">\n" +
                "\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-position: center top; color: #000000; background-repeat: no-repeat; background-image: url('https://d1oco4z2z1fhwp.cloudfront.net/templates/default/6081/Header_Background_01.png'); width: 650px; margin: 0 auto;\" width=\"650\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-top: 30px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"heading_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-left:10px;padding-right:10px;text-align:center;width:100%;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h2 style=\"margin: 0; color: #0f7085; direction: ltr; font-family: Oswald, Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 24px; font-weight: normal; letter-spacing: 5px; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 28.799999999999997px;\"><span class=\"tinyMce-placeholder\">SEND YOUR</span></h2>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"divider_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"50%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 2px dashed #0F7085;\"><span>&#8202;</span></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"heading_block block-3\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-left:10px;padding-right:10px;text-align:center;width:100%;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h1 style=\"margin: 0; color: #cb0c0c; direction: ltr; font-family: Oswald, Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 64px; font-weight: normal; letter-spacing: 1px; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 76.8px;\"><span class=\"tinyMce-placeholder\">VERTIFY MAIL</span></h1>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"divider_block block-4\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"50%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 2px dashed #0F7085;\"><span>&#8202;</span></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"heading_block block-5\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-left:10px;padding-right:10px;text-align:center;width:100%;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h2 style=\"margin: 0; color: #0f7085; direction: ltr; font-family: Oswald, Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 24px; font-weight: normal; letter-spacing: 5px; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 28.799999999999997px;\"><span class=\"tinyMce-placeholder\">THANK FOR REGISTER</span></h2>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"spacer_block block-6\" style=\"height:30px;line-height:30px;font-size:1px;\">&#8202;</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"image_block block-7\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"width:100%;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\" style=\"line-height:10px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"max-width: 650px;\"><img src=\"https://d1oco4z2z1fhwp.cloudfront.net/templates/default/6081/Header_Overlay_01.png\" style=\"display: block; height: auto; border: 0; width: 100%;\" width=\"650\" alt=\"Image Of Love Header\" title=\"Image Of Love Header\"></div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t<table class=\"row row-2\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-position: center top; color: #000000; background-repeat: no-repeat; background-color: #ffffff; background-image: url('https://d1oco4z2z1fhwp.cloudfront.net/templates/default/6081/PliegueSobre_03.png'); width: 650px; margin: 0 auto;\" width=\"650\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 50px; padding-left: 20px; padding-right: 20px; padding-top: 10px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"paragraph_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-left:10px;padding-right:10px;padding-top:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"color:#0f7085;font-family:Oswald, Arial, Helvetica Neue, Helvetica, sans-serif;font-size:20px;letter-spacing:1px;line-height:120%;text-align:center;mso-line-height-alt:24px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin: 0;\">You're almost there!</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"paragraph_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-bottom:10px;padding-left:10px;padding-right:10px;padding-top:5px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"color:#4e8c9d;font-family:'Lato',Tahoma,Verdana,Segoe,sans-serif;font-size:16px;line-height:150%;text-align:center;mso-line-height-alt:24px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin: 0;\">Here is your otp code : " + OTP + "</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"divider_block block-3\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-bottom:30px;padding-left:10px;padding-right:10px;padding-top:30px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"50%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 2px dashed #0F7085;\"><span>&#8202;</span></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"button_block block-4\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\"><!--[if mso]>\n" +
                "<v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"http://localhost:8080/sign-in\" style=\"height:50px;width:253px;v-text-anchor:middle;\" arcsize=\"0%\" stroke=\"false\" fillcolor=\"#cb0c0c\">\n" +
                "<w:anchorlock/>\n" +
                "<v:textbox inset=\"0px,0px,0px,0px\">\n" +
                "<center style=\"color:#ffffff; font-family:Arial, sans-serif; font-size:20px\">\n" +
                "<![endif]--><a href=\"http://localhost:8080/sign-in\" target=\"_blank\" style=\"text-decoration:none;display:inline-block;color:#ffffff;background-color:#cb0c0c;border-radius:0px;width:auto;border-top:0px solid transparent;font-weight:undefined;border-right:0px solid transparent;border-bottom:0px solid transparent;border-left:0px solid transparent;padding-top:5px;padding-bottom:5px;font-family:Oswald, Arial, Helvetica Neue, Helvetica, sans-serif;font-size:20px;text-align:center;mso-border-alt:none;word-break:keep-all;\"><span style=\"padding-left:20px;padding-right:20px;font-size:20px;display:inline-block;letter-spacing:4px;\"><span style=\"word-break: break-word; line-height: 40px;\">Go Back To Website</span></span></a><!--[if mso]></center></v:textbox></v:roundrect><![endif]--></div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t<table class=\"row row-3\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; width: 650px; margin: 0 auto;\" width=\"650\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"spacer_block block-1\" style=\"height:25px;line-height:25px;font-size:1px;\">&#8202;</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"image_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\" style=\"line-height:10px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"max-width: 130px;\"><img src=\"https://www.transparentpng.com/thumb/nike-logo/UZcQ8m-nike-logo-vector.png\" style=\"display: block; height: auto; border: 0; width: 100%;\" width=\"130\" alt=\"Your Logo Here\" title=\"Your Logo Here\"></div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"spacer_block block-3\" style=\"height:25px;line-height:25px;font-size:1px;\">&#8202;</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t<table class=\"row row-4\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                "\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-radius: 0; color: #000000; width: 650px; margin: 0 auto;\" width=\"650\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"paragraph_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"color:#101112;direction:ltr;font-family:Oswald, Arial, Helvetica Neue, Helvetica, sans-serif;font-size:16px;font-weight:400;letter-spacing:2px;line-height:120%;text-align:center;mso-line-height-alt:19.2px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin: 0;\"><span id=\"8d2fd8da-be13-4c61-8d61-71483becc841\">@Copyright by hoanghuydev</span></p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t</table>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</tbody>\n" +
                "\t</table><!-- End -->\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    public static void templateMailAccessCount(List<LogModel> list, String toMail) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder content = new StringBuilder();
        content.append("<p><strong>Xin ch&agrave;o Admin,</strong></p>");
        content.append("<p>Ch&uacute;ng t&ocirc;i nhận thấy một lượng truy cập lớn đối với t&agrave;i nguy&ecirc;n của bạn v&agrave;o l&uacute;c n&agrave;y.</p>");
        content.append("<p>Đ&acirc;y l&agrave; th&ocirc;ng tin chi tiết:</p>");
        content.append("<table style=\"width: 100%;\">");
        content.append("<tbody><tr style=\"\"><td style=\"width: 71px; text-align: center;\"><strong>STT</strong></td>");
        content.append("<td style=\"width: 100px;text-align: center;\"><strong>Địa chỉ IP</strong></td>");
        content.append("<td style=\"width: 170px;text-align: center;\"><strong>Quốc gia</strong></td>");
        content.append("<td style=\"text-align: center;\"><strong>T&agrave;i nguy&ecirc;n bị truy cập</strong></td>");
        content.append("<td style=\"width: 180.562px;text-align: center;\"><strong>Số lần truy cập / gi&acirc;y</strong></td>");
        content.append("<td style=\"width: 180.562px;text-align: center;\"><strong>Thời gian</strong></td>");
        content.append("</tr>");
        for (int i = 0; i < list.size(); i++) {
            LogModel logModel = list.get(i);
            content.append("<tr style=\"\">");
            content.append("<td style=\"text-align: center;\">").append(i + 1).append("</td>");
            content.append("<td style=\"text-align: center;\">").append(logModel.getIp()).append("</td>");
            content.append("<td style=\"\">").append(logModel.getLocation()).append("</td>");
            content.append("<td style=\"\">").append(logModel.getResource()).append("</td>");
            content.append("<td style=\"text-align: center;\">").append(logModel.getAccessCount()).append("</td>");
            content.append("<td style=\"text-align: center;\">").append(formatter.format(logModel.getCreateAt().getTime())).append("</td>");
            content.append("</tr>");
        }
        content.append("</tbody></table>");
        content.append("<p>Ch&uacute;ng t&ocirc;i đề xuất kiểm tra v&agrave; đ&aacute;nh gi&aacute; lại lượng truy cập n&agrave;y để đảm bảo rằng kh&ocirc;ng c&oacute; h&agrave;nh vi kh&ocirc;ng hợp lệ n&agrave;o đang xảy ra. Nếu cần, vui l&ograve;ng thực hiện c&aacute;c biện ph&aacute;p bảo mật ph&ugrave; hợp để đảm bảo an to&agrave;n cho hệ thống.</p>");
        content.append("<p>Trân trọng.</p>");
        String subject = "Thông báo: Lượng Truy Cập Lớn Bất Thường";
        sendMail(toMail, subject, content.toString());
    }
}
