package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.exception.AppException;
import com.dreamers.the_dreamers.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String recipientEmail, String token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String verificationLink = "http://localhost:8080/api/auth/verify?token=" + token;

            String htmlContent = "<html><body>"
                    + "<div style='text-align: center;'>"
                    + "    <img src='https://i.imgur.com/9xyTEQA.png' alt='Logo Câu Lạc Bộ' style='width: 100%; height: auto; max-width: 600px; margin-bottom: 20px;'>"
                    + "</div>"
                    + "<div style='font-family: Arial, sans-serif; font-size: 16px; color: #333; padding: 20px;'>"
                    + "    <p>Xin chào!</p>"
                    + "    <p>Bạn đã đăng ký thành công tài khoản tại <b>The Dreamers Team</b>.</p>"
                    + "    <hr style='border: 1px solid #eee; margin: 20px 0;'>"
                    + "    <div style='background-color: #f8f9fa; padding: 15px; border-radius: 8px;'>"
                    + "        <p style='font-weight: bold; text-align: center;'>𝐃𝐫𝐞𝐚𝐦 𝐇𝐢𝐠𝐡, 𝐅𝐥𝐲 𝐇𝐢𝐠𝐡𝐞𝐫 - 𝐊𝐡𝐨̛̉𝐢 𝐝̄𝐚̂̀𝐮 𝐜𝐡𝐨 𝐦𝐨̣̂𝐭 𝐡𝐚̀𝐧𝐡 𝐭𝐫𝐢̀𝐧𝐡 𝐝̄𝐚̂̀𝐲 𝐲́ 𝐧𝐠𝐡𝐢̃𝐚!</p>"
                    + "        <p>Xin chào mọi người, chúng mình là <b>𝗗𝗥𝗘𝗔𝗠𝗘𝗥𝗦</b> - nhóm sinh viên trẻ với ước mơ và hoài bão lớn lao, mong muốn đem lại những giá trị tốt đẹp cho xã hội. Được thôi thúc bởi tình yêu thương và tinh thần trách nhiệm, chúng mình đã bắt tay vào một hành trình mới, muôn thử thách nhưng cũng đầy cảm hứng.</p>"
                    + "        <p>Chúng mình cũng tin rằng những gì mình đang làm không chỉ giúp các em nhỏ có hoàn cảnh dặc biệt có được môi trường học tập tốt hơn mà còn lan tỏa tinh thần lạc quan, yêu đời. <b>𝗗𝗥𝗘𝗔𝗠𝗘𝗥𝗦</b> mong muốn mang đến cho các em cơ hội để học hỏi, trưởng thành, và nhìn nhận cuộc sống bằng góc nhìn tích cực.</p>"
                    + "        <p><b>𝗗𝗥𝗘𝗔𝗠𝗘𝗥𝗦</b> luôn tin rằng <b>𝐦𝐨̣𝐢 𝐝̄𝐮̛́𝐚 𝐭𝐫𝐞̉ 𝐝̄𝐞̂̀𝐮 𝐜𝐨́ 𝐪𝐮𝐲𝐞̂̀𝐧 𝐦𝐨̛ 𝐮̛𝐨̛́𝐜 𝐯𝐚̀ 𝐩𝐡𝐚́𝐭 𝐭𝐫𝐢𝐞̂̉𝐧</b>. DREAMERS không chỉ là một nhóm bạn trẻ nhiệt huyết, mà còn là những người bạn đồng hành, luôn lắng nghe và sẻ chia. Mỗi ngày, chúng mình sẽ không ngừng nỗ lực để cùng các em xây dựng một tương lai tốt đẹp hơn.</p>"
                    + "    </div>"
                    + "    <hr style='border: 1px solid #eee; margin: 20px 0;'>"
                    + "    <p>Để hoàn tất đăng ký, vui lòng nhấp vào liên kết dưới đây:</p>"
                    + "    <p style='text-align: center; margin: 30px 0;'>"
                    + "        <a href='" + verificationLink + "' style='background-color: #007bff; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold;'>Xác Nhận Tài Khoản</a>"
                    + "    </p>"
                    + "    <p>Nếu bạn không yêu cầu đăng ký này, vui lòng bỏ qua email.</p>"
                    + "    <br>"
                    + "    <p>Trân trọng,</p>"
                    + "    <p><b>The Dreamers Team</b></p>"
                    + "</div>"
                    + "<div style='text-align: center; margin-top: 20px;'>"
                    + "    <img src='https://i.imgur.com/N6uywtN.png' alt='Chữ ký' style='width: 60%; height: auto; margin-top: 10px; display: block;'>"
                    + "</div>"
                    + "</body></html>";

            helper.setTo(recipientEmail);
            helper.setSubject("Xác Nhận Đăng Ký Tài Khoản - The Dreamers");
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("Email xác nhận đã được gửi thành công đến: {}", recipientEmail);
        } catch (Exception e) {
            log.error("Lỗi khi gửi email xác nhận đến {}: {}", recipientEmail, e.getMessage());
            throw new AppException(ErrorCode.EMAIL_SENDING_FAILED);
        }
    }

    public void sendPasswordResetEmail(String recipientEmail, String token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String resetLink = "http://localhost:3000/reset-password?token=" + token; // Cập nhật URL của frontend

            String htmlContent = "<html><body>"
                    + "<div style='font-family: Arial, sans-serif; font-size: 16px; color: #333; padding: 20px;'>"
                    + "    <p>Xin chào!</p>"
                    + "    <p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu của bạn.</p>"
                    + "    <p>Vui lòng nhấp vào liên kết dưới đây để tạo mật khẩu mới:</p>"
                    + "    <p style='text-align: center; margin: 30px 0;'>"
                    + "        <a href='" + resetLink + "' style='background-color: #ffc107; color: #333; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold;'>Đặt Lại Mật Khẩu</a>"
                    + "    </p>"
                    + "    <p>Liên kết này sẽ hết hạn sau 15 phút. Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>"
                    + "    <br>"
                    + "    <p>Trân trọng,</p>"
                    + "    <p><b>The Dreamers Team</b></p>"
                    + "</div>"
                    + "</body></html>";

            helper.setTo(recipientEmail);
            helper.setSubject("Yêu Cầu Đặt Lại Mật Khẩu");
            helper.setText(htmlContent, true); // true để bật chế độ HTML

            mailSender.send(mimeMessage);
            log.info("Email đặt lại mật khẩu đã được gửi thành công đến: {}", recipientEmail);
        } catch (Exception e) {
            log.error("Lỗi khi gửi email đặt lại mật khẩu đến {}: {}", recipientEmail, e.getMessage());
            throw new AppException(ErrorCode.EMAIL_SENDING_FAILED);
        }
    }
}