package kr.or.ddit;

import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class WebMail {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. JavaMail을 사용하여 이메일을 보내는 코드

        // 2. 메인 메소드를 정의하여 사용자 입력을 처리하고 이메일을 보냅니다.
        System.out.println("수신자 Email 서비스 선택");
        System.out.println("1. 직접 입력");
        System.out.println("2. Naver");
        System.out.println("3. Gmail");
        System.out.println("4. 예약 Email");
        System.out.print("옵션을 선택해주세요(1/2/3/4) : ");
        int option = Integer.parseInt(scanner.nextLine());

        String recipient = "";
        String emailContent = "";
        String emailSubject = "";

        System.out.print("Email 제목 : ");
        emailSubject = scanner.nextLine();

        // 3. 사용자가 수신자의 이메일 서비스를 선택하도록 합니다.
        // 4. 사용자로부터 이메일 제목과 내용을 입력하도록 요청합니다.
        // 5. 선택한 옵션에 따라 수신자의 이메일 주소와 이메일 내용을 설정합니다.
        switch (option) {
            case 1: //수신자 이메일 주소를 직접 입력합니다.
                System.out.print("받는 분의 Email : ");
                recipient = scanner.nextLine();

                System.out.print("Email 내용 : ");
                emailContent = scanner.nextLine();
                break;

            case 2: //네이버 이메일 주소를 사용합니다.
                System.out.print("받는 분의 naver ID(+@naver.com) : ");
                recipient = scanner.nextLine();
                recipient += "@naver.com"; // 실제 수신자 이메일로 대체
                emailContent = "JAVA WEB MAIL로 작성된 네이버 메일 내용입니다.";
                break;

            case 3: //Gmail 이메일 주소를 사용합니다.
                System.out.print("받는 분의 gmail ID(+@gmail.com) : ");
                recipient = scanner.nextLine();
                recipient += "@gmail.com"; // 실제 수신자 이메일로 대체
                emailContent = "JAVA WEB MAIL로 작성된 Gmail 메일 내용입니다.";
                break;

            case 4: // 예약 이메일을 위해 사용됩니다(예약된 이메일 로직으로 대체)
                recipient = "dlalwl419@naver.com"; // 실제 수신자 이메일로 대체
                emailContent = "JAVA WEB MAIL로 작성된 예약 이메일 내용입니다.";
                break;

            default:
                System.out.println("선택한 옵션이 없습니다. 프로그램을 종료합니다...");
                scanner.close();
                return;
        }



        // 6. sendEmail 메소드를 호출하여 JavaMail API를 사용하여 이메일을 보냅니다.
        sendEmail(recipient, emailSubject, emailContent);

        // 8. 프로그램은 이메일이 성공적으로 전송되었음을 사용자에게 알립니다.
        System.out.println("Email 전송이 완료되었습니다.");
        // 9. Scanner가 자원 누출을 방지하기 위해 닫힙니다.
        scanner.close(); 
    }

    public static void sendEmail(String recipient, String emailSubject, String emailContent) {
        //7-1. 발신자의 이메일 주소와 비밀번호를 설정합니다 (`user`와 `password`를 실제 발신자의 이메일과 비밀번호로 바꿉니다).
        final String user = new Id().getId();
        final String password = new Id().getPass();

        // 7-2. 선택한 이메일 서비스에 기반하여 SMTP 서버의 속성을 구성합니다.
        Properties prop = new Properties();
        // 네이버 SMTP 설정
        prop.put("mail.smtp.host", "smtp.naver.com");
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        // Gmail SMTP 설정
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.port", 587);
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.starttls.enable", "true");
        /*
        Google 계정 설정 열기: Google 계정 설정으로 이동합니다.

        보안 탭으로 이동: 왼쪽 메뉴에서 '보안' 탭을 선택합니다.

        보안 수준이 낮은 앱의 액세스 허용 옵션 찾기: 페이지를 아래로 스크롤하여 "보안 수준이 낮은 앱의 액세스" 부분을 찾습니다.

        보안 수준이 낮은 앱의 액세스 허용 설정 변경: "보안 수준이 낮은 앱의 액세스" 옵션을 활성화합니다. 이렇게 하면 Gmail 계정으로부터 Java 애플리케이션과 같은 보안 수준이 낮은 앱에서도 이메일을 보낼 수 있게 됩니다.

        설정 저장: 변경 사항을 저장합니다.

         */

        //7-3. 발신자의 이메일과 비밀번호를 사용하여 Session 인스턴스를 생성합니다.
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        //7-4. 수신자, 제목 및 내용을 포함하여 이메일을 작성하기 위해 MimeMessage 객체를 생성합니다.
        //7-5. Transport 클래스를 사용하여 이메일을 전송합니다.
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(user));

            // 수신자 메일 주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Subject
            message.setSubject(emailSubject);

            // Mail Text
            message.setText(emailContent);

            Transport.send(message); // send message
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

/*
JSP와 Spring과 함께 사용하는 방법에 대한 설명도 포함되어 있습니다:

- JSP에서는 사용자 입력 폼을 통해 수신자 이메일, 제목 및 내용을 입력할 수 있습니다.
- 폼이 제출되면 Spring 백엔드에서 "/sendEmail"로 POST 요청을 보냅니다.
- Spring의 EmailController가 요청을 처리하고 수신자, 제목 및 내용을 요청 매개변수에서 추출합니다.
- 그런 다음 EmailController가 WebMail의 sendEmail 메소드를 호출하고 추출한 매개변수를 전달합니다.
- sendEmail 메소드는 이전에 설명한 대로 이메일 전송 로직을 처리합니다.
- 이메일이 전송된 후, EmailController는 사용자를 다시 폼 페이지나 기타 페이지로 리디렉션할 수 있습니다. "redirect:/form"을 사용하여 폼 페이지로 리디렉션한다고 가정합니다 (만약 "/form"이 폼 페이지의 URL이라면).

참고: 코드가 올바르게 작동하려면 `sendEmail` 메소드에서 `user`와 `password` 변수에 유효한 발신자 이메일 주소와 비밀번호를 제공해야 합니다. 또한 Gmail을 사용하는 경우 "보안 수준이 낮은 앱"을 허용하거나 Gmail 계정에서 "앱 비밀번호"를 생성하여 애플리케이션이 Gmail 계정을 사용하여 이메일을 보낼 수 있도록 해야 합니다.

또한, 중요한 정보 (이메일 및 비밀번호와 같은)를 코드에 직접 하드코딩하지 않도록 주의하십시오. 환경 변수 또는 설정 파일을 사용하여 이러한 정보를 안전하게 유지하는 것이 좋습니다.

    /////////// JSP 예시 코드 ///////////

    <form action="/sendEmail" method="post">
        <label for="recipient">받는 분 이메일:</label>
        <input type="email" name="recipient" id="recipient" required>

        <label for="emailSubject">이메일 제목:</label>
        <input type="text" name="emailSubject" id="emailSubject" required>

        <label for="emailContent">이메일 내용:</label>
        <textarea name="emailContent" id="emailContent" required></textarea>

        <button type="submit">이메일 보내기</button>
    </form>


    /////////// SPRING 예시 코드 ///////////

    @Controller
    public class EmailController {

        @PostMapping("/sendEmail")
        public String sendEmail(
            @RequestParam("recipient") String recipient,
            @RequestParam("emailSubject") String emailSubject,
            @RequestParam("emailContent") String emailContent
        ) {
            // 입력값으로 WebMail 클래스의 sendEmail 메소드 호출
            WebMail.sendEmail(recipient, emailSubject, emailContent);

            // 폼 페이지나 다른 페이지로 리다이렉트
            return "redirect:/form"; // "/form"은 폼 페이지의 URL로 대체
        }
    }
 */
